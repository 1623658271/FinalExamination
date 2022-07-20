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
class HostFragment: Fragment(){
    lateinit var v:View
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        v = inflater.inflate(R.layout.layout_host_fragment,container,false)
        return v
    }
}