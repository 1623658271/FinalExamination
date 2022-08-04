package com.example.openeyes.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.view.animation.LayoutAnimationController
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.openeyes.R
import com.example.openeyes.adapter.PictureRVAdapter
import com.example.openeyes.databinding.LayoutSpeFragmentBinding
import com.example.openeyes.model.SpecialPicBean
import com.example.openeyes.utils.LoadState
import com.example.openeyes.viewmodel.DiscoverPageViewModel

/**
 * description ： 发现的专题页面
 * author : lfy
 * email : 1623658271@qq.com
 * date : 2022/8/4 17:06
 */
class DiscoverSpeFragment:Fragment() {
    private lateinit var binding:LayoutSpeFragmentBinding
    private val discoverPageViewModel:DiscoverPageViewModel by viewModels()
    private lateinit var adapter:PictureRVAdapter
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.layout_spe_fragment,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
        initObserver()
    }

    private fun initObserver() {
        discoverPageViewModel.apply {
            specialPics.observe(activity!!){
                adapter.setData(it)
                Log.e("lfy", "initObserver:$it ", )
            }
            state3.observe(activity!!){
                hideAll()
                when(it){
                    LoadState.SUCCESS -> {
                        binding.rvSpe.visibility = View.VISIBLE
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
        binding.refreshSpe.setOnRefreshListener {
            discoverPageViewModel.loadSpecialMsg()
            binding.refreshSpe.isRefreshing = false
        }
        adapter.setListener(object :PictureRVAdapter.OnIVClickListener{
            override fun onClick(bean: SpecialPicBean) {
                val toSpecialActivity = DiscoverFragmentDirections.actionDiscoverFragmentToSpecialActivity(bean.id.toString())
                findNavController().navigate(toSpecialActivity)
            }
        })
    }

    fun init(){
        adapter = PictureRVAdapter()
        binding.rvSpe.adapter = adapter
        binding.rvSpe.layoutManager = LinearLayoutManager(context,RecyclerView.VERTICAL,false)
        binding.rvSpe.layoutAnimation = // 入场动画
            LayoutAnimationController(
                AnimationUtils.loadAnimation(
                    context,
                    R.anim.recycler_fade_in
                )
            )
    }

    /**
     * 隐藏所有状态布局
     */
    fun hideAll(){
        binding.stateLoading.root.visibility = View.GONE
        binding.stateLoadError.root.visibility = View.GONE
        binding.rvSpe.visibility = View.GONE
    }
}