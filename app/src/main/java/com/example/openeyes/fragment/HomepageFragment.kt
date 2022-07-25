package com.example.openeyes.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.openeyes.MyApplication
import com.example.openeyes.R
import com.example.openeyes.VideoPlayActivity
import com.example.openeyes.adapter.HomePageRVAdapter
import com.example.openeyes.databinding.LayoutHomepageFragmentBinding
import com.example.openeyes.model.HomepageMoreBean
import com.example.openeyes.model.PersonalModel
import com.example.openeyes.model.VideoBean
import com.example.openeyes.respository.MyRepository
import com.example.openeyes.utils.DefaultUtil
import com.example.openeyes.viewmodel.MyViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers

/**
 * description ： 首页的Fragement
 * author : lfy
 * email : 1623658271@qq.com
 * date : 2022/7/15 08:49
 */
class HomepageFragment:Fragment() {
    private lateinit var binding:LayoutHomepageFragmentBinding
    private lateinit var adapter:HomePageRVAdapter
    private lateinit var beanList:MutableList<VideoBean>
    private lateinit var viewModel: MyViewModel
    private lateinit var nextUrl:String
    private lateinit var imageUrlList:MutableList<VideoBean>
//    private val TAG = "lfy"
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater,R.layout.layout_homepage_fragment,container,false)
        return binding.root
    }

    /**
     * view创建后进行了一系列的操作(绑定RV，获取viewmodel，设置监听等)
     */
    @SuppressLint("NotifyDataSetChanged")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        beanList = ArrayList()
        imageUrlList = ArrayList()
        viewModel = ViewModelProvider(
            this,
            ViewModelProvider.AndroidViewModelFactory(MyApplication.application!!)
        )[MyViewModel::class.java]
        adapter = HomePageRVAdapter(beanList,imageUrlList)
        binding.rvHomepage.adapter = adapter
        binding.rvHomepage.layoutManager = LinearLayoutManager(MyApplication.context,RecyclerView.VERTICAL,false)
        binding.srHomepage.setOnRefreshListener {
            viewModel.updateDailyHandpickViewModel()
            binding.srHomepage.isRefreshing = false
        }
        viewModel.getDailyHandpickLiveData().observe(viewLifecycleOwner, Observer {
            val dataList = it.itemList
            beanList.clear()
            imageUrlList.clear()
            var i = 0
            for (m in dataList) {
                    if (m.type == "followCard") {
                        if(i<7){
                            i+=1
                            imageUrlList.add(
                                VideoBean(
                                    m.data.content.data.id,
                                    m.data.content.data.title,
                                    m.data.header.title,
                                    m.data.content.data.cover.feed,
                                    m.data.content.data.playUrl,
                                    m.data.content.data.description,
                                    PersonalModel(
                                        m.data.content.data.author.id ?: 0,
                                        m.data.content.data.author.icon ?: "",
                                        DefaultUtil.defaultCoverUrl,
                                        m.data.content.data.author.description ?: "",
                                        m.data.content.data.author.name ?: "","",""
                                    )
                                ,m.data.content.data.consumption)
                            )
                        }else {
                            beanList.add(
                                VideoBean(
                                    m.data.content.data.id,
                                    m.data.content.data.title,
                                    m.data.header.title,
                                    m.data.content.data.cover.feed,
                                    m.data.content.data.playUrl,
                                    m.data.content.data.description,
                                    PersonalModel(
                                        m.data.content.data.author.id ?: 0,
                                        m.data.content.data.author.icon ?: "",
                                        DefaultUtil.defaultCoverUrl,
                                        m.data.content.data.author.description ?: "",
                                        m.data.content.data.author.name ?: "","",""
                                    )
                                ,m.data.content.data.consumption)
                            )
                        }
                }
            }
            nextUrl = it.nextPageUrl
            adapter.notifyDataSetChanged()
        })
        adapter.setClickListener(object :HomePageRVAdapter.OnSomethingClickedListener{
            override fun onVideoImageClickedListener(
                view: View,
                holder: RecyclerView.ViewHolder,
                position: Int,
                videoBeanList: MutableList<VideoBean>
            ) {
                VideoPlayActivity.startVideoPlayActivity(MyApplication.context!!,videoBeanList[position])
            }

            override fun onAvatarImageClickedListener(
                view: View,
                holder: RecyclerView.ViewHolder,
                position: Int,
                videoBeanList: MutableList<VideoBean>
            ) {
                val personMessage = videoBeanList[position].personalModel
                val toPersonMessageActivity =
                    HomepageFragmentDirections.actionHomepageFragmentToPersonMessageActivity(
                        personMessage
                    )
                findNavController().navigate(toPersonMessageActivity)
            }

        })
        lifecycle.addObserver(adapter)
        setRecyclerOnScrollListener()
    }

    /**
     * 设置滑动监听，以检查上滑状态以更新数据
     */
    fun setRecyclerOnScrollListener(){
        var isUp:Boolean = false
        binding.rvHomepage.addOnScrollListener(object :RecyclerView.OnScrollListener(){
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                val manager = recyclerView.layoutManager as LinearLayoutManager?
                //当不滑动时
                if (newState === RecyclerView.SCROLL_STATE_IDLE) {
                    //获取最后一个完全显示的itemPosition
                    val lastItemPosition = manager!!.findLastCompletelyVisibleItemPosition()
                    val itemCount = manager.itemCount
                    // 判断是否滑动到了最后一个item，并且是向上滑动
                    if (lastItemPosition == itemCount - 1 && isUp) {
                        if(nextUrl.isEmpty()){
                            adapter.setLoadState(adapter.LOADING_END)
                            }else {
                            loadingMore()
                        }
                    }
                }
            }

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                isUp = dy > 0
            }
        })
    }

    /**
     * 加载首页更多的视频精选
     */
    fun loadingMore(){
        adapter.setLoadState(adapter.LOADING)
        val url = nextUrl.split('?').last().split('&')
        val date = url[0].filter { it.isDigit() }
        val num = url[1].filter { it.isDigit() }
//        Log.d(TAG, "loadingMore: $url")
        MyRepository("http://baobab.kaiyanapp.com/api/v5/index/tab/feed/")
            .getService()
            .getMoreHomepageMsg(date.toLong(),num.toInt())
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : io.reactivex.rxjava3.core.Observer<HomepageMoreBean> {
                override fun onSubscribe(d: Disposable) {

                }

                override fun onNext(t: HomepageMoreBean) {
                    val dataList = t.itemList
                    for (m in dataList) {
                        if(m.data!=null) {
                            if (true) {
                                if (m.type == "followCard" && m.data.content!=null) {
                                    beanList.add(
                                        VideoBean(
                                            m.data.content.data.id,
                                            m.data.content.data.title,
                                            m.data.header!!.title,
                                            m.data.content.data.cover.feed,
                                            m.data.content.data.playUrl,
                                            m.data.content.data.description,
                                            PersonalModel(m.data.content.data.author.id,m.data.content.data.author.icon,DefaultUtil.defaultCoverUrl,m.data.content.data.author.description,m.data.content.data.author.name,"","")
                                        ,m.data.content.data.consumption)
                                    )
                                }
                            }
                        }
                    }
                    nextUrl = t.nextPageUrl?:""
                    adapter.setLoadState(adapter.LOADING_COMPLETE)
                    adapter.notifyDataSetChanged()
                }

                override fun onError(e: Throwable) {
                    adapter.setLoadState(adapter.LOADING_END)
                    adapter.notifyDataSetChanged()
//                    Log.d(TAG, "onError: $e")
                }

                override fun onComplete() {

                }

            })
    }

}