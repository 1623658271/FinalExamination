package com.example.openeyes.fragment

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
import com.example.openeyes.MyApplication
import com.example.openeyes.PersonMessageActivity
import com.example.openeyes.R
import com.example.openeyes.VideoPlayActivity
import com.example.openeyes.adapter.RankListRVAdapter
import com.example.openeyes.databinding.LayoutHotFragmentBinding
import com.example.openeyes.model.VideoBean
import com.example.openeyes.utils.LoadState
import com.example.openeyes.viewmodel.RankListPageViewModel

/**
 * description ： 排行
 * author : lfy
 * email : 1623658271@qq.com
 * date : 2022/8/1 21:18
 */
class HotVideoFragment:Fragment() {
    private lateinit var binding:LayoutHotFragmentBinding
    private lateinit var adapter:RankListRVAdapter
    private lateinit var strategy:String
    private val rankListPageViewModel:RankListPageViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.layout_hot_fragment,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
        initObserver()
    }

    fun initObserver(){
        rankListPageViewModel.apply {
            when(strategy){
                "weekly" -> weeklyVideoList.observe(activity!!){
                    adapter.setData(it)
                }
                "monthly" -> monthlyVideoList.observe(activity!!){
                    adapter.setData(it)
                }
                "historical" -> historicalVideoList.observe(activity!!){
                    adapter.setData(it)
                }
            }
            state.observe(activity!!){
                hideAll()
                when(it){
                    LoadState.SUCCESS -> {
                        binding.rvHotFragment.visibility = View.VISIBLE
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
        }
        binding.refreshHot.setOnRefreshListener {
            rankListPageViewModel.apply {
                when (strategy) {
                    "weekly" -> loadWeekListMsg()
                    "monthly" -> loadMonthlyListMsg()
                    "historical" -> loadHistoricalListMsg()
                }
            }
            binding.refreshHot.isRefreshing = false
        }
    }

    fun hideAll(){
        binding.stateLoading.root.visibility = View.GONE
        binding.stateLoadError.root.visibility = View.GONE
        binding.rvHotFragment.visibility = View.GONE
    }

    fun init(){
        strategy = arguments?.getString("content")?:""
        adapter = RankListRVAdapter()
        binding.rvHotFragment.adapter = adapter
        binding.rvHotFragment.layoutManager = LinearLayoutManager(context,RecyclerView.VERTICAL,false)
        binding.rvHotFragment.layoutAnimation = // 入场动画
            LayoutAnimationController(
                AnimationUtils.loadAnimation(
                    context,
                    R.anim.recycler_fade_in
                )
            )
        adapter.setClickListener(object :RankListRVAdapter.OnSomethingClickedListener{
            override fun onVideoImageClickedListener(videoBean: VideoBean) {
                val toVideoPlayActivity = RankListFragmentDirections.actionRankListFragmentToVideoPlayActivity(videoBean)
                findNavController().navigate(toVideoPlayActivity)
            }

            override fun onAvatarImageClickedListener(videoBean: VideoBean) {
                val toVideoPlayActivity = RankListFragmentDirections.actionRankListFragmentToPersonMessageActivity(videoBean.personalBean)
                findNavController().navigate(toVideoPlayActivity)
            }

        })
    }
}