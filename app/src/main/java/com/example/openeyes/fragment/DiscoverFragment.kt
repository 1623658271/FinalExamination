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
    private lateinit var discoverSpeFragment: DiscoverSpeFragment
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
        data.add("专题")
        adapter = FragmentPagerAdapter(this)
        discoverRecFragment = DiscoverRecFragment()
        discoverClassFragment = DiscoverClassFragment()
        discoverSpeFragment = DiscoverSpeFragment()
        adapter.add { discoverRecFragment }
        adapter.add{ discoverClassFragment }
        adapter.add{ discoverSpeFragment }
        binding.discoverViewpager2.adapter = adapter
        TabLayoutMediator(
            binding.tlFindmore, binding.discoverViewpager2
        ) { tab, position -> tab.text = data[position] }.attach()
        binding.discoverViewpager2.setPageTransformer(SquareBoxTransformer())
        binding.discoverViewpager2.offscreenPageLimit = 3
    }

    class SquareBoxTransformer : ViewPager2.PageTransformer {
        private val MAX_ROTATION = 90f
        private val MIN_SCALE = 0.9f
        override fun transformPage(page: View, position: Float) {
            page.apply {
                pivotY = height / 2f
                when {
                    position < -1 -> {
                        // This page is way off-screen to the left.
                        rotationY = -MAX_ROTATION
                        pivotX = width.toFloat()
                    }
                    position <= 1 -> {
                        rotationY = position * MAX_ROTATION
                        if (position < 0) {
                            pivotX = width.toFloat()
                            val scale =
                                MIN_SCALE + 4f * (1f - MIN_SCALE) * (position + 0.5f) * (position + 0.5f)
                            scaleX = scale
                            scaleY = scale
                        } else {
                            pivotX = 0f
                            val scale =
                                MIN_SCALE + 4f * (1f - MIN_SCALE) * (position - 0.5f) * (position - 0.5f)
                            scaleX = scale
                            scaleY = scale
                        }
                    }
                    else -> {
                        // This page is way off-screen to the right.
                        rotationY = MAX_ROTATION
                        pivotX = 0f
                    }
                }
            }
        }
    }
}