package com.example.openeyes.adapter

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

/**
 * description ： TODO:类的作用
 * author : lfy
 * email : 1623658271@qq.com
 * date : 2022/7/15 10:21
 */

class FragmentPagerAdapter(fragmentActivity: Fragment) : FragmentStateAdapter(fragmentActivity) {
    private var fragments:MutableList<Fragment> = ArrayList()

    override fun getItemCount(): Int = fragments.size

    fun addFragment(fragment: Fragment){
        fragments.add(fragment)
    }

    override fun createFragment(position: Int): Fragment {
        return fragments[position]
    }

}
