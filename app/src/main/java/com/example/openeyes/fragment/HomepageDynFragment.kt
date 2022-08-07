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
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.openeyes.R
import com.example.openeyes.activity.DynInActivity
import com.example.openeyes.adapter.DynRVAdapter
import com.example.openeyes.databinding.LayoutDynFragmentBinding
import com.example.openeyes.utils.LoadState
import com.example.openeyes.viewmodel.HomePageViewModel

/**
 * description ： 首页主题页
 * author : lfy
 * email : 1623658271@qq.com
 * date : 2022/8/6 15:15
 */
class HomepageDynFragment:Fragment() {
    private lateinit var binding:LayoutDynFragmentBinding
    private lateinit var adapter:DynRVAdapter
    private val homePageViewModel:HomePageViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.layout_dyn_fragment,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
        initObserver()
    }

    private fun initObserver() {
        homePageViewModel.apply {
            loadAllDyn()
            loadRecDyn()
            allDynList.observe(activity!!){
                adapter.setAllDynData(it)
            }
            recDynList.observe(activity!!){
                adapter.setRecDynData(it)
            }
            state2.observe(activity!!) {
                hideAll()
                when (it) {
                    LoadState.SUCCESS -> {
                        binding.rvDynHomepage.visibility = View.VISIBLE
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
        adapter.setListener(object :DynRVAdapter.OnItemClickListener{
            override fun onClick(id: Int) {
                DynInActivity.startDynInActivity(context!!,id)
            }
        })
        binding.refreshDynFragment.setOnRefreshListener {
            homePageViewModel.apply {
                loadAllDyn()
                loadRecDyn()
            }
            binding.refreshDynFragment.isRefreshing = false
        }
    }

    private fun hideAll(){
        binding.stateLoading.root.visibility = View.GONE
        binding.stateLoadError.root.visibility = View.GONE
        binding.rvDynHomepage.visibility = View.GONE
    }

    private fun init(){
        adapter = DynRVAdapter(false)
        adapter.setLoadState(adapter.LOADING_END)
        binding.rvDynHomepage.adapter = adapter
        binding.rvDynHomepage.layoutManager = LinearLayoutManager(context,RecyclerView.VERTICAL,false)
        binding.rvDynHomepage.layoutAnimation = // 入场动画
            LayoutAnimationController(
                AnimationUtils.loadAnimation(
                    context,
                    R.anim.recycler_fade_in
                )
            )
    }
}