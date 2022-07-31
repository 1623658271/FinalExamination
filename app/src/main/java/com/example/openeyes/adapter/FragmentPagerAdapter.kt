package com.example.openeyes.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter

/**
 * description ：vp2的适配器
 * author : lfy
 * email : 1623658271@qq.com
 * date : 2022/7/15 10:21
 */

class FragmentPagerAdapter private constructor(
    fragmentManager: FragmentManager,
    lifecycle: Lifecycle
): FragmentStateAdapter(fragmentManager,lifecycle) {
    constructor(activity: FragmentActivity) : this(activity.supportFragmentManager,activity.lifecycle)

    constructor(fragment: Fragment) : this(fragment.childFragmentManager, fragment.lifecycle)
    private val mFragments = arrayListOf<() -> Fragment>()

    fun add(fragment: () -> Fragment): FragmentPagerAdapter {
         mFragments.add(fragment)
         return this
    }
    fun add(fragment: Class<out Fragment>): FragmentPagerAdapter {
        mFragments.add {
            fragment.newInstance()
        }
        return this
    }

    override fun getItemCount(): Int = mFragments.size
    override fun createFragment(position: Int): Fragment = mFragments[position].invoke()
}
