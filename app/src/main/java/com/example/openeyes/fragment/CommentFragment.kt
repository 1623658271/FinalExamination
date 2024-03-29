package com.example.openeyes.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.observe
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.openeyes.activity.MyApplication
import com.example.openeyes.activity.PersonMessageActivity
import com.example.openeyes.R
import com.example.openeyes.adapter.CommentsRVAdapter
import com.example.openeyes.databinding.LayoutVideoCommentFragmentBinding
import com.example.openeyes.model.PersonalBean
import com.example.openeyes.model.VideoBean
import com.example.openeyes.viewmodel.VideoPlayPageViewModel

/**
 * description ： 评论Fragment
 * author : lfy
 * email : 1623658271@qq.com
 * date : 2022/7/17 21:05
 */
class CommentFragment:Fragment() {
    private lateinit var videoBean: VideoBean
    private lateinit var binding:LayoutVideoCommentFragmentBinding
    private val videoPlayPageViewModel: VideoPlayPageViewModel by activityViewModels()
    private lateinit var adapter:CommentsRVAdapter
//    private val TAG = "lfy"
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.layout_video_comment_fragment,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
        initObserver()
    }

    private fun initObserver() {
        videoPlayPageViewModel.apply {
            commentList.observe(activity!!){
                adapter.setData(it)
                if(it.size==0){
                    binding.tvNoComment.visibility = View.VISIBLE
                }else{
                    binding.tvNoComment.visibility = View.GONE
                }
            }
            loadCommentMsg(videoBean.id)
        }
        binding.refreshComment.setOnRefreshListener {
            videoPlayPageViewModel.loadCommentMsg(videoBean.id)
            binding.refreshComment.isRefreshing = false
        }
    }

    fun init(){
        videoBean = arguments?.getParcelable("videoBean")!!
        adapter = CommentsRVAdapter()
        binding.rvContent.adapter = adapter
        binding.rvContent.layoutManager = LinearLayoutManager(MyApplication.context,RecyclerView.VERTICAL,false)
        adapter.setListener(object :CommentsRVAdapter.OnCommentClickListener{
            override fun onClick(personalBean: PersonalBean) {
                PersonMessageActivity.fragmentStartVideoPlayActivity(MyApplication.context!!,activity!!,personalBean)
            }
        })
    }
}