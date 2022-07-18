package com.example.openeyes.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.example.openeyes.R
import com.example.openeyes.databinding.LayoutVideoDetailsFragmentBinding
import com.example.openeyes.model.VideoBean

/**
 * description ： 视频详情Fragment
 * author : lfy
 * email : 1623658271@qq.com
 * date : 2022/7/17 21:05
 */
class DetailsFragment(val videoBean: VideoBean):Fragment() {
    private lateinit var binding:LayoutVideoDetailsFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.layout_video_details_fragment,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.message = videoBean
    }
}