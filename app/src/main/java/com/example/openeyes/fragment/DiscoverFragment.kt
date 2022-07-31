package com.example.openeyes.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.example.openeyes.R
import com.example.openeyes.adapter.FragmentPagerAdapter
import com.example.openeyes.databinding.LayoutDiscoveryFragmentBinding
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

/**
 * description ： 发现页的首Fragment
 * author : lfy
 * email : 1623658271@qq.com
 * date : 2022/7/15 08:50
 */
class DiscoverFragment:Fragment() {
    private lateinit var binding:LayoutDiscoveryFragmentBinding
    private lateinit var discoverClassFragment: DiscoverClassFragment
    private lateinit var discoverRecFragment: DiscoverRecFragment
    private lateinit var data:MutableList<String>
    private lateinit var adapter: FragmentPagerAdapter
//    private val TAG = "lfy"
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.layout_discovery_fragment,container,false);
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        data = ArrayList()
        data.add("推荐")
        data.add("分类")
        adapter = FragmentPagerAdapter(this)
        discoverRecFragment = DiscoverRecFragment()
        discoverClassFragment = DiscoverClassFragment()
        adapter.add { discoverRecFragment }
        adapter.add{ discoverClassFragment }
        binding.discoverViewpager2.adapter = adapter
        TabLayoutMediator(
            binding.tlFindmore, binding.discoverViewpager2
        ) { tab, position -> tab.text = data[position] }.attach()
        binding.discoverViewpager2.offscreenPageLimit = 2
    }
}