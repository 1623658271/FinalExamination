package com.example.openeyes.fragment

import android.app.Application
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.openeyes.MyApplication
import com.example.openeyes.R
import com.example.openeyes.adapter.CommentsRVAdapter
import com.example.openeyes.databinding.LayoutVideoCommentFragmentBinding
import com.example.openeyes.model.CommentBean
import com.example.openeyes.model.PersonalModel
import com.example.openeyes.utils.DefaultUtil
import com.example.openeyes.viewmodel.MyViewModel

/**
 * description ： TODO:类的作用
 * author : lfy
 * email : 1623658271@qq.com
 * date : 2022/7/17 21:05
 */
class CommentFragment(val videoId: Int,val application: Application):Fragment() {
    private lateinit var binding:LayoutVideoCommentFragmentBinding
    private lateinit var viewModel: MyViewModel
    private lateinit var adapter:CommentsRVAdapter
    private lateinit var commentList:MutableList<CommentBean>
    private val TAG = "lfy"
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
        viewModel = ViewModelProvider(this,ViewModelProvider.AndroidViewModelFactory(application))[MyViewModel::class.java]
        viewModel.getCommentsLiveData(videoId).observe(viewLifecycleOwner) {
            val commentModel = it
            var list:MutableList<CommentBean> = ArrayList()
            if (commentModel?.itemList != null) {
                Log.d(TAG, "onViewCreated: " + commentModel.itemList)
                for (m in commentModel.itemList) {
                    if (m.type == "reply" && m.data.user != null) {
                        list.add(
                            CommentBean(
                                m.data.message, m.data.likeCount, PersonalModel(
                                    m.data.user.uid, m.data.user.avatar,
                                    DefaultUtil.defaultCoverUrl,
                                    "", m.data.user.nickname
                                )
                            )
                        )
                    }
                }
                for(i in list.indices){
                    for (j in i + 1 until list.size) {
                        if (list[i].likeCount < list[j].likeCount) {
                            var temp = list[i]
                            list[i] = list[j]
                            list[j] = temp
                        }
                    }
                }
                commentList.addAll(list)
                adapter.notifyDataSetChanged()
            }
        }
        commentList = ArrayList()
        adapter = CommentsRVAdapter(commentList)
        binding.rvContent.adapter = adapter
        binding.rvContent.layoutManager = LinearLayoutManager(MyApplication.context,RecyclerView.VERTICAL,false)
    }
}