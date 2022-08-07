package com.example.openeyes.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.example.openeyes.R
import com.example.openeyes.adapter.FragmentPagerAdapter
import com.example.openeyes.databinding.LayoutHomepageFragmentBinding
import com.google.android.material.tabs.TabLayoutMediator

/**
 * description ： 首页
 * author : lfy
 * email : 1623658271@qq.com
 * date : 2022/8/6 19:28
 */
class HomepageFragment:Fragment() {
    private lateinit var data:MutableList<String>
    private lateinit var binding:LayoutHomepageFragmentBinding
    private lateinit var adapter: FragmentPagerAdapter
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.layout_homepage_fragment,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    private fun init(){
        data = ArrayList()
        data.add("日报")
        data.add("主题")
        adapter = FragmentPagerAdapter(this)
        adapter.add { HomepageHandpickFragment() }
        adapter.add{ HomepageDynFragment() }
        binding.vp2Homepage.adapter = adapter
        TabLayoutMediator(
            binding.tlHomepage, binding.vp2Homepage
        ) { tab, position -> tab.text = data[position] }.attach()
        binding.vp2Homepage.setPageTransformer(RankListFragment.SquareBoxTransformer())
        binding.vp2Homepage.offscreenPageLimit = 2
    }
}