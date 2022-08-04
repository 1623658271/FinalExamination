package com.example.openeyes.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.example.openeyes.R
import com.example.openeyes.adapter.FragmentPagerAdapter
import com.example.openeyes.databinding.LayoutRanklistFragmentBinding
import com.google.android.material.tabs.TabLayoutMediator
import kotlin.math.abs
import kotlin.math.max

/**
 * description ： 视频排行界面
 * author : lfy
 * email : 1623658271@qq.com
 * date : 2022/8/1 21:24
 */
class RankListFragment:Fragment() {
    private lateinit var binding:LayoutRanklistFragmentBinding
    private lateinit var weeklyFragment:HotVideoFragment
    private lateinit var monthlyFragment:HotVideoFragment
    private lateinit var historicalFragment:HotVideoFragment
    private lateinit var adapter: FragmentPagerAdapter
    private lateinit var data:MutableList<String>
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.layout_ranklist_fragment,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    fun init(){
        val bundleList:MutableList<Bundle> = ArrayList()
        bundleList.apply {
            add(with(Bundle()){
                putString("content","weekly")
                this
            })
            add(with(Bundle()){
                putString("content","monthly")
                this
            })
            add(with(Bundle()){
                putString("content","historical")
                this
            })
        }
        weeklyFragment = HotVideoFragment()
        weeklyFragment.arguments = bundleList[0]
        monthlyFragment = HotVideoFragment()
        monthlyFragment.arguments = bundleList[1]
        historicalFragment = HotVideoFragment()
        historicalFragment.arguments = bundleList[2]
        adapter = FragmentPagerAdapter(this)
        adapter.apply {
            add { weeklyFragment }
            add { monthlyFragment }
            add { historicalFragment }
        }
        binding.vp2RankList.adapter = adapter
        data = ArrayList()
        data.add("周排行")
        data.add("月排行")
        data.add("总排行")
        TabLayoutMediator(
            binding.tlRankList, binding.vp2RankList
        ) { tab, position -> tab.text = data[position] }.attach()
        binding.vp2RankList.setPageTransformer(SquareBoxTransformer())
        binding.vp2RankList.offscreenPageLimit = 3
    }
    class SquareBoxTransformer : ViewPager2.PageTransformer {
        override fun transformPage(page: View, position: Float) {
            page.apply {
                when {
                    position < -1 -> {

                    }
                    position <= 1 -> {
                        val scaleValue = max(0.8f,1 - abs(position))
                        scaleX = scaleValue
                        scaleY = scaleValue
                    }
                    else -> {

                    }
                }
            }
        }
    }
}