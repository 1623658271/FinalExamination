package com.example.openeyes

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.webkit.URLUtil
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.openeyes.adapter.ClassInRVAdapter
import com.example.openeyes.api.URL
import com.example.openeyes.databinding.ActivityClassInBinding
import com.example.openeyes.model.*
import com.example.openeyes.respository.MyRepository
import com.example.openeyes.utils.DecodeUtil
import com.example.openeyes.utils.DefaultUtil
import com.example.openeyes.viewmodel.MyViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observer
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers

class ClassInActivity : AppCompatActivity() {
    private lateinit var binding:ActivityClassInBinding
    private lateinit var viewModel: MyViewModel
    private lateinit var adapter:ClassInRVAdapter
    private lateinit var list: MutableList<VideoBean>
    private lateinit var mapList:MutableList<Map<String,String>>
    private lateinit var listMore:MutableList<ClassDeepMoreMsgModel.Item>
    private lateinit var classModel:ClassModel
    private var fistGet = true
    private val TAG = "lfy"
    private var nextPageUrl:String = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_class_in)
        initData()
    }

    private fun initData(){
        viewModel = ViewModelProvider(this,ViewModelProvider.AndroidViewModelFactory(application))[MyViewModel::class.java]
        list = ArrayList()
        listMore = ArrayList()
        mapList = ArrayList()
        adapter = ClassInRVAdapter(list,mapList)
        binding.classDeepToolbar.setNavigationOnClickListener { finish() }
        binding.rvClassIn.adapter = adapter
        binding.rvClassIn.layoutManager = LinearLayoutManager(this,RecyclerView.VERTICAL,false)
        classModel = intent.getParcelableExtra("classModel")!!
        viewModel.getClassInLiveData(classModel.id.toString(), URL.udid).observe(this){
            for(m in it.itemList){
                val map = HashMap<String,String>()
                map["type"] = m.type?:""
                map["text"] = m.data.text?:""
                mapList.add(map)
                list.add(VideoBean(
                    m.data.content?.data?.id?:m.data.id?:0,
                    m.data.content?.data?.title?:m.data.title?:"",
                    m.data.header?.description?:m.data.author?.name?:"",
                    m.data.content?.data?.cover?.feed?:m.data.cover?.feed?:"",
                    m.data.content?.data?.playUrl?:m.data.playUrl?:"",
                    m.data.content?.data?.description?:m.data.description?:"",
                    PersonalModel(
                        m.data.content?.data?.author?.id?:m.data.author?.id?:0,
                        m.data.content?.data?.author?.icon?:m.data.author?.icon?:"",
                        DefaultUtil.defaultCoverUrl,
                        m.data.content?.data?.author?.description?:m.data.author?.description?:"",
                        m.data.content?.data?.author?.name?:m.data.author?.name?:""
                    )
                ))
            }
            adapter.notifyDataSetChanged()
            nextPageUrl = DecodeUtil.urlDecode(it.nextPageUrl)
            Log.e(TAG, "initData: ${it.nextPageUrl}", )
            Log.e(TAG, "first next $nextPageUrl")
        }
        binding.refreshClassIn.setOnRefreshListener {
            viewModel.updateClassInLiveData(classModel.id.toString(), URL.udid)
            binding.refreshClassIn.isRefreshing = false
            adapter.notifyDataSetChanged()
        }
        adapter.setClickListener(object :ClassInRVAdapter.OnSomethingClickedListener{
            override fun onVideoImageClickedListener(videoBean: VideoBean) {
                VideoPlayActivity.startVideoPlayActivity(this@ClassInActivity,videoBean)
            }

            override fun onAvatarImageClickedListener(videoBean: VideoBean) {
                PersonMessageActivity.startPersonMessageActivity(this@ClassInActivity,videoBean.personalModel!!)
            }
        })
        setRecyclerOnScrollListener()
    }

    fun loadMore() {
        adapter.setClassInLoadState(adapter.LOADING)
        val url = nextPageUrl.split('?').first() + '/'
        Log.e(TAG, "loadMore:url: $nextPageUrl")
        val m = nextPageUrl.split('?').last().split('&')
        val start = m[0].filter { it.isDigit() }.toInt()
        val num = m[1].filter { it.isDigit() }.toInt()

        if (fistGet) {
            viewModel.getClassInMoreLiveData(url, start, num, URL.udid).observe(this) {
                for (m in it.itemList) {
                    val map = HashMap<String, String>()
                    map["type"] = m.type ?: ""
                    map["text"] = ""
                    mapList.add(map)
                    list.add(
                        VideoBean(
                            m.data.content.data.id ?: 0,
                            m.data.content.data.title ?: "",
                            m.data.header.description ?: "",
                            m.data.content.data.cover.feed ?: "",
                            m.data.content.data.playUrl ?: "",
                            m.data.content.data.description ?: "",
                            PersonalModel(
                                m.data.content.data.author.id ?: 0,
                                m.data.content.data.author.icon ?: "",
                                DefaultUtil.defaultCoverUrl,
                                m.data.content.data.author.description ?: "",
                                m.data.content.data.author.name ?: ""
                            )
                        )
                    )
                }
                Log.e(TAG, "loadMore: ${it.nextPageUrl}")
                nextPageUrl = DecodeUtil.urlDecode(it.nextPageUrl ?: "")
                Log.e(TAG, "loadMore: $nextPageUrl")
                adapter.setClassInLoadState(adapter.COMPLETE)
                adapter.notifyDataSetChanged()
            }
            fistGet = false
        } else {
            viewModel.updateClassInMoreLiveData(url, start, num, URL.udid)
        }
    }
    /**
     * 设置滑动监听，以检查上滑状态以更新数据
     */
    fun setRecyclerOnScrollListener(){
        var isUp:Boolean = false
        binding.rvClassIn.addOnScrollListener(object :RecyclerView.OnScrollListener(){
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
                        if(nextPageUrl.isEmpty()){
                            adapter.setClassInLoadState(adapter.END)
                        }else {
                            loadMore()
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
}