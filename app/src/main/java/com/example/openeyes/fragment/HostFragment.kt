package com.example.openeyes.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.RadioGroup
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import androidx.viewpager2.widget.ViewPager2.OnPageChangeCallback
import com.example.openeyes.R
import com.example.openeyes.adapter.FragmentPagerAdapter

/**
 * description ： 首页的首Fragment
 * author : lfy
 * email : 1623658271@qq.com
 * date : 2022/7/14 22:20
 */
class HostFragment: Fragment(), RadioGroup.OnCheckedChangeListener{
    lateinit var viewPager2:ViewPager2
    lateinit var radioGroup: RadioGroup
    lateinit var pagerAdapter:FragmentPagerAdapter
    lateinit var fragmentHomepage:HomepageFragment
    lateinit var fragmentDiscover:DiscoverFragment
    lateinit var fragmentMine:MineFragment
    lateinit var v:View
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        v = inflater.inflate(R.layout.layout_host_fragment,container,false)
        return v
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }

    private fun initView() {
        viewPager2 = v.findViewById(R.id.host_view_pager2)
        radioGroup = v.findViewById(R.id.radio_group_host)
        pagerAdapter = FragmentPagerAdapter(this)
        fragmentHomepage = HomepageFragment()
        fragmentDiscover = DiscoverFragment()
        fragmentMine = MineFragment()
        pagerAdapter.addFragment(fragmentHomepage)
        pagerAdapter.addFragment(fragmentDiscover)
        pagerAdapter.addFragment(fragmentMine)
        viewPager2.adapter = pagerAdapter
        viewPager2.currentItem = 0
        viewPager2.offscreenPageLimit = 1
        radioGroup.setOnCheckedChangeListener(this)
        //页面与RadioButton按钮联动
        viewPager2.registerOnPageChangeCallback(object : OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                when (position) {
                    0 -> (v.findViewById<View>(R.id.rb_homepage) as RadioButton).isChecked = true
                    1 -> (v.findViewById<View>(R.id.rb_findmore) as RadioButton).isChecked = true
                    2 -> (v.findViewById<View>(R.id.rb_mine) as RadioButton).isChecked = true
                }
            }
        })
    }

    override fun onCheckedChanged(group: RadioGroup?, checkedId: Int) {
        when (checkedId) {
            R.id.rb_homepage -> viewPager2.currentItem = 0
            R.id.rb_findmore -> viewPager2.currentItem = 1
            R.id.rb_mine -> viewPager2.currentItem = 2
        }
    }
}