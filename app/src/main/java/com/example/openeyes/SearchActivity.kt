package com.example.openeyes

import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.openeyes.databinding.*
import com.example.openeyes.bean.PersonalBean
import com.example.openeyes.bean.SearchMoreBean
import com.example.openeyes.bean.VideoBean
import com.example.openeyes.respository.MyRepository
import com.example.openeyes.utils.DecodeUtil
import com.example.openeyes.utils.DefaultUtil
import com.example.openeyes.viewmodel.MyViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observer
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers

class SearchActivity : BaseActivity() {
    private lateinit var binding:LayoutSearchActivityBinding
    private lateinit var viewModel: MyViewModel
    private lateinit var listHot:MutableList<String>
    private lateinit var adapterHot:HotRVAdapter
    private lateinit var adapterResult:ResultRVAdapter
    private lateinit var listResult:MutableList<VideoBean>
    private var firstSearch = true
    private var nextPageUrl:String = ""
    private var hasSearch = false
    private var nowText = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initView()
        initData()
    }


    private fun initView(){
        binding = DataBindingUtil.setContentView(this,R.layout.layout_search_activity)
        viewModel = ViewModelProvider(this,ViewModelProvider.AndroidViewModelFactory(application))[MyViewModel::class.java]
        listHot = ArrayList()
        listResult = ArrayList()
        adapterResult = ResultRVAdapter(listResult)
        adapterHot = HotRVAdapter(listHot)
        binding.rvSearchResult.adapter = adapterResult
        adapterResult.setListener(object :ResultRVAdapter.OnItemClickListener{
            override fun onClick(viewBean: VideoBean) {
                VideoPlayActivity.startVideoPlayActivity(this@SearchActivity,viewBean)
            }
        })
        binding.rvSearchResult.layoutManager = LinearLayoutManager(this,RecyclerView.VERTICAL,false)
        binding.rvHotSearch.adapter = adapterHot
        binding.rvHotSearch.layoutManager = StaggeredGridLayoutManager(3, RecyclerView.VERTICAL)
    }


    private fun initData() {
        viewModel.getHotSearchLiveData().observe(this){
            listHot.addAll(it.toMutableList())
            adapterHot.notifyDataSetChanged()
        }
        adapterHot.setListener(object :HotRVAdapter.OnItemClickListener{
            override fun onClick(text: String) {
                binding.searchView.setQuery(text,false)
                nowText = text
                doSearch(text)
            }
        })
        binding.searchBack.setOnClickListener { finish() }
        binding.searchView.apply {
            setIconifiedByDefault(false)
            setOnQueryTextListener(object :SearchView.OnQueryTextListener{
                override fun onQueryTextSubmit(query: String?): Boolean {
                    if(TextUtils.isEmpty(query)){

                    }else {
                        doSearch(query!!)
                        nowText = query
                    }
                    return false
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    if(TextUtils.isEmpty(query)){
                        binding.llHotEarch.visibility = View.VISIBLE
                        binding.rvSearchResult.visibility = View.GONE
                    }else{

                    }
                    return false
                }

            })
        }
        setRecyclerOnScrollListener()
        binding.refreshSearch.setOnRefreshListener {
            if(hasSearch) {
                viewModel.updateSearchLiveData(nowText)
            }
            binding.refreshSearch.isRefreshing = false
        }
    }

    fun doSearch(query:String){
        hasSearch = true
        binding.llHotEarch.visibility = View.GONE
        if(firstSearch){
            firstSearch=false
            viewModel.getSearchLiveData(query).observe(this){
                listResult.clear()
                for(m in it.itemList){
                    if(m.type=="followCard"){
                        listResult.add(VideoBean(m.data.content!!.data.id?:0,m.data.content.data.title,m.data.content.data.author?.name?:"",
                            m.data.content.data.cover.feed,m.data.content.data.playUrl,m.data.content.data.description,
                            PersonalBean(m.data.content.data.author?.id?:0,m.data.content.data.author?.icon?:"",
                                DefaultUtil.defaultCoverUrl,m.data.content.data.author?.description?:"",
                                m.data.content.data.author?.name?:"","","")
                        ,m.data.content.data.consumptionBean)
                        )
                    }
                }
                nextPageUrl = DecodeUtil.urlDecode(it.nextPageUrl?:"")
                adapterResult.notifyDataSetChanged()
                binding.rvSearchResult.visibility = View.VISIBLE
            }
        }else{
            viewModel.updateSearchLiveData(query)
        }
    }


    class HotRVAdapter(var list:MutableList<String>):RecyclerView.Adapter<RecyclerView.ViewHolder>(){
        inner class MyViewHolder(itemView: ItemHotSearchBinding):RecyclerView.ViewHolder(itemView.root){
            var binding: ItemHotSearchBinding = itemView
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
            val binding:ItemHotSearchBinding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.item_hot_search,
                parent,
                false
            )
            return MyViewHolder(binding)
        }

        override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
            if(holder is MyViewHolder){
                holder.binding.tvHot.apply {
                    text = list[position]
                    setOnClickListener { listener?.onClick(list[position]) }
                }
            }
        }

        override fun getItemCount(): Int = list.size

        private var listener:OnItemClickListener? = null

        interface OnItemClickListener{
            fun onClick(text:String)
        }

        fun setListener(listener:OnItemClickListener){
            this.listener = listener
        }
    }

    class ResultRVAdapter(var list:MutableList<VideoBean>):RecyclerView.Adapter<RecyclerView.ViewHolder>(){
        //普通布局
        private val TYPE_ITEM = 1
        //脚布局
        private val TYPE_FOOTER = 2

        // 正在加载
        val LOADING = 1

        // 加载完成
        val LOADING_COMPLETE = 2

        // 加载到底
        val LOADING_END = 3

        inner class FootViewHolder(itemView: LayoutLoadMessageBinding):RecyclerView.ViewHolder(itemView.root){
            val binding = itemView
        }

        inner class MyViewHolder(itemView: ItemSearchResultBinding):RecyclerView.ViewHolder(itemView.root){
            var binding: ItemSearchResultBinding = itemView
        }
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
            if(viewType==TYPE_ITEM){
                val binding:ItemSearchResultBinding = DataBindingUtil.inflate(
                    LayoutInflater.from(parent.context),
                    R.layout.item_search_result,
                    parent,
                    false
                )
                return MyViewHolder(binding)
            }else{
                val loadBinding: LayoutLoadMessageBinding = DataBindingUtil.inflate(
                    LayoutInflater.from(parent.context),
                    R.layout.layout_load_message,
                    parent,
                    false
                )
                return FootViewHolder(loadBinding)
            }
        }

        override fun onBindViewHolder(holder: RecyclerView.ViewHolder, p: Int) {
            val position = p
            if(holder is MyViewHolder){
                holder.binding.message = list[position]
                holder.binding.ivItemResultVideo.setOnClickListener { listener?.onClick(list[position]) }
            }else if(holder is FootViewHolder){
                when(loadState) {
                    LOADING -> {
                        holder.binding.tvLoad.visibility = View.VISIBLE
                        holder.binding.tvLoad.text = "加载中..."
                    }
                    LOADING_COMPLETE -> {
                        holder.binding.tvLoad.visibility = View.VISIBLE
                        holder.binding.tvLoad.text = "加载完成"
                    }
                    LOADING_END -> {
                        holder.binding.tvLoad.visibility = View.VISIBLE
                        holder.binding.tvLoad.text = "亲！到底线了~~~"
                    }
                }
            }
        }

        override fun getItemCount(): Int = list.size + 1

        private var listener: OnItemClickListener? = null

        interface OnItemClickListener{
            fun onClick(viewBean:VideoBean)
        }

        fun setListener(listener:OnItemClickListener){
            this.listener = listener
        }

        private var loadState = 0

        /**
         * 设置加载状态，以显示或隐藏脚布局
         */
        fun setLoadState(loadState:Int){
            this.loadState = loadState
            notifyDataSetChanged()
        }

        override fun getItemViewType(position: Int): Int {
            return if(position + 1 == itemCount){
                TYPE_FOOTER
            } else{
                TYPE_ITEM
            }
        }
    }

    /**
     * 设置滑动监听，以检查上滑状态以更新数据
     */
    fun setRecyclerOnScrollListener(){
        var isUp:Boolean = false
        binding.rvSearchResult.addOnScrollListener(object :RecyclerView.OnScrollListener(){
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
                        if (nextPageUrl.isEmpty()) {
                            adapterResult.setLoadState(adapterResult.LOADING_END)
                        } else {
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

    fun loadingMore() {
        adapterResult.setLoadState(adapterResult.LOADING)
        val url = nextPageUrl.split("?").last().split("&")
        val start = url[0].filter { it.isDigit() }.toInt()
        val num = url[1].filter { it.isDigit() }.toInt()
        val query = url[2].split("=").last()
        MyRepository("http://baobab.kaiyanapp.com/api/v3/search/")
            .getService()
            .getMoreSearchMsg(start, num, query)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : Observer<SearchMoreBean> {
                override fun onSubscribe(d: Disposable) {

                }

                override fun onNext(t: SearchMoreBean) {
                    nextPageUrl = DecodeUtil.urlDecode(t.nextPageUrl?:"")
                    for (m in t.itemList) {
                        if (m.type == "followCard") {
                            listResult.add(
                                VideoBean(
                                    m.data.content.data.id ?: 0,
                                    m.data.content.data.title,
                                    m.data.content.data.author?.name ?: "",
                                    m.data.content.data.cover.feed ?: "",
                                    m.data.content.data.playUrl ?: "",
                                    m.data.content.data.description ?: "",
                                    PersonalBean(
                                        m.data.content.data.author?.id ?: 0,
                                        m.data.content.data.author?.icon ?: "",
                                        DefaultUtil.defaultCoverUrl,
                                        m.data.content.data.author?.description ?: "",
                                        m.data.content.data.author?.name ?: "","",""
                                    )
                                ,m.data.content.data.consumptionBean)
                            )
                        }
                    }
                    adapterResult.setLoadState(adapterResult.LOADING_COMPLETE)
                    adapterResult.notifyDataSetChanged()
                }

                override fun onError(e: Throwable) {
                    adapterResult.setLoadState(adapterResult.LOADING_END)
                    adapterResult.notifyDataSetChanged()
                }

                override fun onComplete() {

                }

            })

    }
}