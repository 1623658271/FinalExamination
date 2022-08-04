package com.example.openeyes.activity

import android.os.Bundle
import android.text.TextUtils
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.widget.SearchView
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.openeyes.R
import com.example.openeyes.adapter.HotRVAdapter
import com.example.openeyes.adapter.ResultRVAdapter
import com.example.openeyes.databinding.*
import com.example.openeyes.model.VideoBean
import com.example.openeyes.utils.LoadState
import com.example.openeyes.viewmodel.SearchPageViewModel

class SearchActivity : BaseActivity() {
    private lateinit var binding:LayoutSearchActivityBinding
    private val searchPageViewModel: SearchPageViewModel by viewModels()
    private lateinit var adapterHot:HotRVAdapter
    private lateinit var adapterResult:ResultRVAdapter
    private var hasSearch = false
    private var nowText = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initView()
        initObserver()
        initData()

    }

    private fun initObserver() {
        searchPageViewModel.apply {
            hotWordsList.observe(this@SearchActivity){
                adapterHot.setData(it)
            }
            videoBean.observe(this@SearchActivity){
                adapterResult.setData(it)
            }
        }
    }


    private fun initView(){
        binding = DataBindingUtil.setContentView(this, R.layout.layout_search_activity)
        adapterResult = ResultRVAdapter()
        adapterHot = HotRVAdapter()
        binding.rvSearchResult.adapter = adapterResult
        adapterResult.setListener(object : ResultRVAdapter.OnItemClickListener{
            override fun onClick(viewBean: VideoBean) {
                VideoPlayActivity.startVideoPlayActivity(this@SearchActivity, viewBean)
            }
        })
        binding.rvSearchResult.layoutManager = LinearLayoutManager(this,RecyclerView.VERTICAL,false)
        binding.rvHotSearch.adapter = adapterHot
        binding.rvHotSearch.layoutManager = StaggeredGridLayoutManager(3, RecyclerView.VERTICAL)
    }


    private fun initData() {
        adapterHot.setListener(object : HotRVAdapter.OnItemClickListener{
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
                searchPageViewModel.loadSearchMsg(nowText)
            }
            binding.refreshSearch.isRefreshing = false
        }
        searchPageViewModel.apply {
            state.observe(this@SearchActivity){
                hideAll()
                when(it){
                    LoadState.SUCCESS -> {
                        binding.rvSearchResult.visibility = View.VISIBLE
                    }
                    LoadState.LOADING -> {
                        binding.stateLoading.root.visibility = View.VISIBLE
                    }
                    LoadState.ERROR -> {
                        binding.stateLoadError.root.visibility = View.VISIBLE
                    }
                    else ->{}
                }
            }
        }
    }

    private fun hideAll() {
        binding.rvSearchResult.visibility = View.GONE
        binding.stateLoading.root.visibility = View.GONE
        binding.stateLoadError.root.visibility = View.GONE
    }

    fun doSearch(query:String){
        hasSearch = true
        binding.llHotEarch.visibility = View.GONE
        binding.rvSearchResult.visibility = View.VISIBLE
        binding.rvSearchResult.adapter = adapterResult
        searchPageViewModel.loadSearchMsg(query)
        binding.searchView.clearFocus()
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

    fun loadingMore() {
        adapterResult.setLoadState(adapterResult.LOADING)
        val success = searchPageViewModel.loadSearchMore()
        if(success){
            adapterResult.setLoadState(adapterResult.LOADING_COMPLETE)
        }else{
            adapterResult.setLoadState(adapterResult.LOADING_END)
        }
    }
}