package com.example.openeyes.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.openeyes.MyApplication
import com.example.openeyes.PicWatchActivity
import com.example.openeyes.R
import com.example.openeyes.VideoPlayActivity
import com.example.openeyes.adapter.RecRVAdapter
import com.example.openeyes.api.URL
import com.example.openeyes.databinding.LayoutDiscoveryRecFragmentBinding
import com.example.openeyes.model.PersonalModel
import com.example.openeyes.model.PicsModel
import com.example.openeyes.model.SocialRecommendModel
import com.example.openeyes.model.VideoBean
import com.example.openeyes.respository.MyRepository
import com.example.openeyes.utils.DefaultUtil
import com.example.openeyes.viewmodel.MyViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observer
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers

/**
 * description ： 发现页的推荐页fragment
 * author : lfy
 * email : 1623658271@qq.com
 * date : 2022/7/22 13:23
 */
class DiscoverRecFragment:Fragment() {
    private lateinit var binding:LayoutDiscoveryRecFragmentBinding
    private lateinit var viewModel: MyViewModel
    private lateinit var adapter0:RecRVAdapter
    private lateinit var mapList: MutableList<Map<String,Any>>
//    private val TAG = "lfy"
    private var nextPageUrl = ""
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
//        Log.e(TAG, "onCreateView: f", )
        binding = DataBindingUtil.inflate(inflater, R.layout.layout_discovery_rec_fragment,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViewAndData()
    }

    private fun initViewAndData() {
        viewModel = ViewModelProvider(this,ViewModelProvider.AndroidViewModelFactory(MyApplication.application!!))[MyViewModel::class.java]
        mapList = ArrayList()
        adapter0 = RecRVAdapter(mapList)
        binding.rvRec.apply {
            adapter = adapter0
            layoutManager = StaggeredGridLayoutManager(2,RecyclerView.VERTICAL)
            setHasFixedSize(true)
        }
        viewModel.getRecLiveData().observe(viewLifecycleOwner){
            mapList.clear()
            nextPageUrl = it.nextPageUrl?:""
//            Log.e(TAG, "initViewAndData: ${it.itemList.size}", )
            var mapListX:MutableList<Map<String,Any>> = ArrayList()
            for(m in it.itemList){
                if(m.data.content!=null) {
                    if (m.data.content.type == "ugcPicture"){
                        val map = HashMap<String,Any>()
                        map["type"] = "ugcPicture"
                        map["message"] = PicsModel(
                            m.data.content.data.description,
                            m.data.content.data.cover.feed,
                            m.data.content.data.urls!!.toMutableList(),
                            m.data.content.data.consumption,
                            PersonalModel(
                                m.data.content.data.owner.uid,
                                m.data.content.data.owner.avatar,
                                m.data.content.data.owner.cover?:DefaultUtil.defaultCoverUrl,
                                m.data.content.data.owner.description?:"",
                                m.data.content.data.owner.nickname,
                                m.data.content.data.owner.city?:"",
                                m.data.content.data.owner.job?:""
                            )
                        )
                        mapListX.add(map)
                    }else if(m.data.content.type == "video"){
                        val map = HashMap<String,Any>()
                        map["type"] = "video"
                        map["message"] = VideoBean(
                            m.data.content.data.id,
                            m.data.content.data.description,
                            m.data.content.data.owner.nickname,
                            m.data.content.data.cover.feed,
                            m.data.content.data.playUrl?:"",
                            m.data.content.data.description,
                            PersonalModel(
                                m.data.content.data.owner.uid,
                                m.data.content.data.owner.avatar,
                                m.data.content.data.owner.cover?:DefaultUtil.defaultCoverUrl,
                                m.data.content.data.owner.description?:"",
                                m.data.content.data.owner.nickname,
                                m.data.content.data.owner.city?:"",
                                m.data.content.data.owner.job?:""
                            ),
                            m.data.content.data.consumption
                        )
                        mapListX.add(map)
                    }
                }
            }
            mapList.addAll(mapListX)
            adapter0.notifyDataSetChanged()
        }
        setRecyclerOnScrollListener()
        binding.refreshRec.setOnRefreshListener {
            viewModel.updateRecLiveData()
            binding.refreshRec.isRefreshing = false
        }
        adapter0.setClickListener(object :RecRVAdapter.OnClickListener{
            override fun onVideoClick(videoBean: VideoBean) {
                val toVideoPlayActivity =
                    DiscoverFragmentDirections.actionDiscoverFragmentToVideoPlayActivity(videoBean)
                findNavController().navigate(toVideoPlayActivity)
            }

            override fun onPicClick(picsModel: PicsModel) {
                PicWatchActivity.fragmentStartPicWatchActivity(context!!,activity!!,picsModel)
            }

            override fun onPersonImageClick(personalModel: PersonalModel) {
                val toPersonMessageActivity =
                    DiscoverFragmentDirections.actionDiscoverFragmentToPersonMessageActivity(
                        personalModel
                    )
                findNavController().navigate(toPersonMessageActivity)
            }

        })
    }

    /**
     * 设置滑动监听，以检查上滑状态以更新数据
     */
    fun setRecyclerOnScrollListener(){
        var isUp:Boolean = false
        binding.rvRec.addOnScrollListener(object :RecyclerView.OnScrollListener(){
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                val manager = recyclerView.layoutManager as StaggeredGridLayoutManager?
                //当不滑动时
                if (newState === RecyclerView.SCROLL_STATE_IDLE) {
                    //获取最后一个完全显示的itemPosition
                    var intArray = IntArray(2)
                    manager!!.findLastCompletelyVisibleItemPositions(intArray)
//                    Log.e(TAG, "onScrollStateChanged: ${intArray[0]} +${intArray[1]} ${manager.itemCount-1}", )
                    val itemCount = manager.itemCount
                    // 判断是否滑动到了最后一个item，并且是向上滑动
                    if ((intArray[0] == itemCount - 1) ||(intArray[1] == itemCount - 1)&& isUp) {
                        if(nextPageUrl.isEmpty()){
                            adapter0.setLoadState(adapter0.LOADING_END)
                        }else {
//                            Log.e(TAG, "onScrollStateChanged: $nextPageUrl", )
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

    fun loadingMore(){
        adapter0.setLoadState(adapter0.LOADING)
        val m = nextPageUrl.split("?").last().split("&")
        val start = m[0].filter { it.isDigit() }.toLong()
        val page = m[1].filter { it.isDigit() }.toInt()
//        Log.e(TAG, "loadingMore: $start + $page", )
        MyRepository(URL.RecUrl)
            .getService()
            .getSocialMore(start,page)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : Observer<SocialRecommendModel>{
                override fun onSubscribe(d: Disposable) {

                }

                override fun onNext(t: SocialRecommendModel) {
//                    Log.e(TAG, "onNext: ${t.itemList.size}", )
                    nextPageUrl = t.nextPageUrl
                    var mapListX:MutableList<Map<String,Any>> = ArrayList()
                    for(m in t.itemList){
                        if(m.data.content!=null) {
                            if (m.data.content.type == "ugcPicture"){
                                val map = HashMap<String,Any>()
                                map["type"] = "ugcPicture"
                                map["message"] = PicsModel(
                                    m.data.content.data.description,
                                    m.data.content.data.cover.feed,
                                    m.data.content.data.urls!!.toMutableList(),
                                    m.data.content.data.consumption,
                                    PersonalModel(
                                        m.data.content.data.owner.uid,
                                        m.data.content.data.owner.avatar,
                                        m.data.content.data.owner.cover?:DefaultUtil.defaultCoverUrl,
                                        m.data.content.data.owner.description?:"",
                                        m.data.content.data.owner.nickname,
                                        m.data.content.data.owner.city?:"",
                                        m.data.content.data.owner.job?:""
                                    )
                                )
                                mapListX.add(map)
                            }else if(m.data.content.type == "video"){
                                val map = HashMap<String,Any>()
                                map["type"] = "video"
                                map["message"] = VideoBean(
                                    m.data.content.data.id,
                                    m.data.content.data.description,
                                    m.data.content.data.owner.nickname,
                                    m.data.content.data.cover.feed,
                                    m.data.content.data.playUrl?:"",
                                    m.data.content.data.description,
                                    PersonalModel(
                                        m.data.content.data.owner.uid,
                                        m.data.content.data.owner.avatar,
                                        m.data.content.data.owner.cover?:DefaultUtil.defaultCoverUrl,
                                        m.data.content.data.owner.description?:"",
                                        m.data.content.data.owner.nickname,
                                        m.data.content.data.owner.city?:"",
                                        m.data.content.data.owner.job?:""
                                    ),
                                    m.data.content.data.consumption
                                )
                                mapListX.add(map)
                            }
                        }
                    }
                    val size = mapList.size
                    mapList.addAll(mapListX)
                    adapter0.setLoadState(adapter0.LOADING_COMPLETE)
                    adapter0.notifyItemRangeChanged(size,mapListX.size)
                }

                override fun onError(e: Throwable) {
//                    Log.e(TAG, "onError: $e", )
                    Toast.makeText(MyApplication.context,"请检查你的网络！", Toast.LENGTH_SHORT).show()
                }

                override fun onComplete() {
                    
                }

            })
    }
}