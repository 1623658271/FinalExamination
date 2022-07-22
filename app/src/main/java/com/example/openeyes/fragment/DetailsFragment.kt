package com.example.openeyes.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.openeyes.MyApplication
import com.example.openeyes.PersonMessageActivity
import com.example.openeyes.R
import com.example.openeyes.VideoPlayActivity
import com.example.openeyes.adapter.RelatedRVAdapter
import com.example.openeyes.databinding.LayoutVideoDetailsFragmentBinding
import com.example.openeyes.model.RelatedRecommendationModel
import com.example.openeyes.model.VideoBean
import com.example.openeyes.viewmodel.MyViewModel

/**
 * description ： 视频详情Fragment
 * author : lfy
 * email : 1623658271@qq.com
 * date : 2022/7/17 21:05
 */
class DetailsFragment(val videoBean: VideoBean):Fragment() {
    private lateinit var binding:LayoutVideoDetailsFragmentBinding
    private lateinit var adapter: RelatedRVAdapter
    private lateinit var list:MutableList<RelatedRecommendationModel.Item>
    private lateinit var viewModel: MyViewModel
    private val TAG = "lfy"

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
        list = ArrayList()
        adapter = RelatedRVAdapter(videoBean,list)
        binding.rvDetailsMore.adapter = adapter
        binding.rvDetailsMore.layoutManager = LinearLayoutManager(MyApplication.context,RecyclerView.VERTICAL,false)
        viewModel = ViewModelProvider(this,
            ViewModelProvider.AndroidViewModelFactory(MyApplication.application!!))[MyViewModel::class.java]
        viewModel.getRelatedLiveData(videoBean.id).observe(viewLifecycleOwner){
            list.clear()
            list.addAll(it.itemList)
            Log.d(TAG, "onViewCreated: ${list.size}")
            adapter.notifyDataSetChanged()
        }
        adapter.setClickListener(object :RelatedRVAdapter.OnSomethingClickedListener{
            override fun onVideoImageClickedListener(
                videoBean: VideoBean
            ) {
                VideoPlayActivity.fragmentStartVideoPlayActivity(MyApplication.context!!,activity!!,videoBean)
            }

            override fun onCircleImageClick(videoBean: VideoBean) {
                PersonMessageActivity.fragmentStartVideoPlayActivity(MyApplication.context!!,activity!!,videoBean.personalModel!!)
            }
        })
    }
}