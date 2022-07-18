package com.example.openeyes.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.openeyes.MyApplication
import com.example.openeyes.PersonMessageActivity
import com.example.openeyes.R
import com.example.openeyes.VideoPlayActivity
import com.example.openeyes.adapter.HomePageRVAdapter
import com.example.openeyes.api.URL
import com.example.openeyes.databinding.LayoutHomepageFragmentBinding
import com.example.openeyes.model.DailyHandpickBean
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
 * description ： TODO:类的作用
 * author : lfy
 * email : 1623658271@qq.com
 * date : 2022/7/15 08:49
 */
class HomepageFragment:Fragment() {
    private lateinit var bing:LayoutHomepageFragmentBinding
    private lateinit var adapter:HomePageRVAdapter
    private lateinit var beanList:MutableList<VideoBean>
    private lateinit var viewModel: MyViewModel
    private lateinit var nextUrl:String
    private val TAG = "lfy"
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        bing = DataBindingUtil.inflate(inflater,R.layout.layout_homepage_fragment,container,false)
        return bing.root
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        beanList = ArrayList()
        viewModel = ViewModelProvider(
            this,
            ViewModelProvider.AndroidViewModelFactory(MyApplication.application!!)
        )[MyViewModel::class.java]
        adapter = HomePageRVAdapter(beanList)
        bing.rvHomepage.adapter = adapter
        bing.rvHomepage.layoutManager = LinearLayoutManager(MyApplication.context,RecyclerView.VERTICAL,false)
        bing.srHomepage.setOnRefreshListener {
            updateMessage()
            bing.srHomepage.isRefreshing = false
        }
        viewModel.getDailyHandpickLiveData().observe(viewLifecycleOwner, Observer {
            val dataList = it.itemList
            beanList.clear()
            for (m in dataList) {
                if(m.data!=null) {
                    if (true) {
                        if (m.type == "followCard") {
                            beanList.add(
                                VideoBean(
                                    m.data.content.data.id,
                                    m.data.content.data.title,
                                    m.data.header.title,
                                    m.data.content.data.cover.feed,
                                    m.data.content.data.playUrl,
                                    m.data.content.data.description,
                                    PersonalModel(m.data.content.data.author.id,m.data.content.data.author.icon,DefaultUtil.defaultCoverUrl,m.data.content.data.author.description,m.data.content.data.author.name)
                                )
                            )
                        }
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
                val videoMessages = ArrayList<String>()
                videoMessages.apply {
                    add(videoBeanList[position].playUrl)
                    add(videoBeanList[position].coverUrl)
                    add(videoBeanList[position].bigTitle)
                    add(videoBeanList[position].smallTitle)
                    add(videoBeanList[position].description)
                }
                val id = videoBeanList[position].id
                Log.d(TAG, "onVideoImageClickedListener: $videoMessages")
                VideoPlayActivity.startVideoPlayActivity(MyApplication.context!!,videoMessages,id)
            }

            override fun onAvatarImageClickedListener(
                view: View,
                holder: RecyclerView.ViewHolder,
                position: Int,
                videoBeanList: MutableList<VideoBean>
            ) {
                var messages = ArrayList<String>()
                messages.apply {
                    add(videoBeanList[position].personalModel!!.avatar)
                    add(videoBeanList[position].personalModel!!.cover)
                    add(videoBeanList[position].personalModel!!.description)
                    add(videoBeanList[position].personalModel!!.nickname)
                }
                PersonMessageActivity.startPersonMessageActivity(MyApplication.context!!,messages)
            }

        })
        lifecycle.addObserver(adapter)
        setRecyclerOnScrollListener()
    }

    fun updateMessage(){
        viewModel.updateDailyHandpickViewModel()
    }

    fun setRecyclerOnScrollListener(){
        var isUp:Boolean = false
        bing.rvHomepage.addOnScrollListener(object :RecyclerView.OnScrollListener(){
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
                        loadingMore()
                    }
                }
            }

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                isUp = dy > 0
            }
        })
    }

    fun loadingMore(){
        adapter.setLoadState(adapter.LOADING)
        val url = nextUrl.split('?').last().split('&')
        val date = url[0].filter { it.isDigit() }
        val num = url[1].filter { it.isDigit() }
        Log.d(TAG, "loadingMore: $url")
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
                                            PersonalModel(m.data.content.data.author.id,m.data.content.data.author.icon,DefaultUtil.defaultCoverUrl,m.data.content.data.author.description,m.data.content.data.author.name)
                                        )
                                    )
                                }
                            }
                        }
                    }
                    nextUrl = t.nextPageUrl
                    adapter.setLoadState(adapter.LOADING_COMPLETE)
                    adapter.notifyDataSetChanged()
                }

                override fun onError(e: Throwable) {
                    adapter.setLoadState(adapter.LOADING_END)
                    adapter.notifyDataSetChanged()
                    Log.d(TAG, "onError: $e")
                }

                override fun onComplete() {

                }

            })
    }

}