package com.example.openeyes.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.example.openeyes.R
import com.example.openeyes.databinding.LayoutDiscoveryRecFragmentBinding

/**
 * description ： 发现页的推荐页fragment
 * author : lfy
 * email : 1623658271@qq.com
 * date : 2022/7/22 13:23
 */
class DiscoverRecFragment:Fragment() {
    private lateinit var binding:LayoutDiscoveryRecFragmentBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.layout_discovery_rec_fragment,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
}