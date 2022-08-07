package com.example.openeyes.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.view.animation.LayoutAnimationController
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.openeyes.activity.MyApplication
import com.example.openeyes.R
import com.example.openeyes.adapter.HomePageRVAdapter
import com.example.openeyes.databinding.LayoutHomepageHandpickFragmentBinding
import com.example.openeyes.model.VideoBean
import com.example.openeyes.utils.LoadState
import com.example.openeyes.viewmodel.HomePageViewModel

/**
 * description ： 首页的Fragement
 * author : lfy
 * email : 1623658271@qq.com
 * date : 2022/7/15 08:49
 */
class HomepageHandpickFragment:Fragment() {
    private lateinit var binding: LayoutHomepageHandpickFragmentBinding
    private lateinit var adapter:HomePageRVAdapter
    private val homePageViewModel:HomePageViewModel by viewModels()
//    private val TAG = "lfy"
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater,R.layout.layout_homepage_handpick_fragment,container,false)
        return binding.root
    }

    /**
     * view创建后进行了一系列的操作(绑定RV，获取viewmodel，设置监听等)
     */
    @SuppressLint("NotifyDataSetChanged")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
        initObserver()
        setRecyclerOnScrollListener()
    }

    private fun initObserver() {
        homePageViewModel.apply {
            dataList.observe(activity!!) {
                adapter.setData(it)
            }
            state.observe(activity!!) {
                hideAll()
                when (it) {
                    LoadState.SUCCESS -> {
                        binding.rvHomepage.visibility = View.VISIBLE
                    }
                    LoadState.LOADING -> {
                        binding.stateLoading.root.visibility = View.VISIBLE
                    }
                    LoadState.ERROR -> {
                        binding.stateLoadError.root.visibility = View.VISIBLE
                    }
                    else -> {}
                }
            }
            stateMore.observe(activity!!) {
                when (it) {
                    LoadState.LOADING -> adapter.setLoadState(adapter.LOADING)
                    LoadState.SUCCESS -> adapter.setLoadState(adapter.LOADING_COMPLETE)
                    else -> adapter.setLoadState(adapter.LOADING_END)
                }
            }
        }
        binding.srHomepage.setOnRefreshListener {
            homePageViewModel.loadDailyMsg()
            binding.srHomepage.isRefreshing = false
        }
    }

    /**
     * 隐藏所有状态布局
     */
    fun hideAll(){
        binding.stateLoading.root.visibility = View.GONE
        binding.stateLoadError.root.visibility = View.GONE
        binding.rvHomepage.visibility = View.GONE
    }

    /**
     * 初始化
     */
    fun init(){
        adapter = HomePageRVAdapter()
        binding.rvHomepage.adapter = adapter
        binding.rvHomepage.layoutManager = LinearLayoutManager(MyApplication.context,RecyclerView.VERTICAL,false)
        adapter.setClickListener(object :HomePageRVAdapter.OnSomethingClickedListener{
            override fun onVideoImageClickedListener(
                videoBean: VideoBean
            ) {
                val toVideoPlayActivity = HomepageFragmentDirections.actionHomepageFragmentToVideoPlayActivity(videoBean)
                findNavController().navigate(toVideoPlayActivity)
            }

            override fun onAvatarImageClickedListener(
                videoBean: VideoBean
            ) {
                val personMessage = videoBean.personalBean
                val toPersonMessageActivity =
                    HomepageFragmentDirections.actionHomepageFragmentToPersonMessageActivity(
                        personMessage
                    )
                findNavController().navigate(toPersonMessageActivity)
            }

        })
        binding.rvHomepage.layoutAnimation = // 入场动画
            LayoutAnimationController(
                AnimationUtils.loadAnimation(
                    context,
                    R.anim.recycler_fade_in
                )
            )
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

    /**
     * 加载首页更多的视频精选
     */
    fun loadingMore(){
        homePageViewModel.loadDailyMore()
    }

}