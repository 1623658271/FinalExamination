package com.example.openeyes.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.openeyes.activity.MyApplication
import com.example.openeyes.activity.PersonMessageActivity
import com.example.openeyes.R
import com.example.openeyes.activity.VideoPlayActivity
import com.example.openeyes.adapter.RelatedRVAdapter
import com.example.openeyes.databinding.LayoutVideoDetailsFragmentBinding
import com.example.openeyes.model.VideoBean
import com.example.openeyes.viewmodel.VideoPlayPageViewModel

/**
 * description ： 视频详情Fragment
 * author : lfy
 * email : 1623658271@qq.com
 * date : 2022/7/17 21:05
 */
class DetailsFragment:Fragment() {
    private lateinit var videoBean: VideoBean
    private lateinit var binding:LayoutVideoDetailsFragmentBinding
    private lateinit var adapter: RelatedRVAdapter
    private val videoPlayPageViewModel: VideoPlayPageViewModel by activityViewModels()
//    private val TAG = "lfy"

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
        init()
        initObserver()
    }

    fun initObserver(){
        videoPlayPageViewModel.apply {
            relatedList.observe(activity!!){
                adapter.setData(it)
            }
            loadRelatedMsg(videoBean.id)
        }
    }
    fun init(){
        videoBean = arguments?.getParcelable("videoBean")!!
        adapter = RelatedRVAdapter(videoBean)
        binding.rvDetailsMore.adapter = adapter
        binding.rvDetailsMore.layoutManager = LinearLayoutManager(MyApplication.context,RecyclerView.VERTICAL,false)
        adapter.setClickListener(object :RelatedRVAdapter.OnSomethingClickedListener{
            override fun onVideoImageClickedListener(
                videoBean: VideoBean
            ) {
                VideoPlayActivity.fragmentStartVideoPlayActivity(MyApplication.context!!,activity!!,videoBean)
            }

            override fun onCircleImageClick(videoBean: VideoBean) {
                PersonMessageActivity.fragmentStartVideoPlayActivity(MyApplication.context!!,activity!!,videoBean.personalBean!!)
            }
        })
    }
}