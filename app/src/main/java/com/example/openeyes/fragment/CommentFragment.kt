package com.example.openeyes.fragment

import android.app.Application
import android.os.Bundle
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
import com.example.openeyes.PersonMessageActivity
import com.example.openeyes.R
import com.example.openeyes.adapter.CommentsRVAdapter
import com.example.openeyes.databinding.LayoutVideoCommentFragmentBinding
import com.example.openeyes.bean.CommentNormalBean
import com.example.openeyes.bean.PersonalBean
import com.example.openeyes.bean.VideoBean
import com.example.openeyes.utils.DefaultUtil
import com.example.openeyes.viewmodel.MyViewModel
import kotlinx.android.synthetic.*

/**
 * description ： 评论Fragment
 * author : lfy
 * email : 1623658271@qq.com
 * date : 2022/7/17 21:05
 */
class CommentFragment(val videoBean: VideoBean,val application: Application):Fragment() {
    private lateinit var binding:LayoutVideoCommentFragmentBinding
    private lateinit var viewModel: MyViewModel
    private lateinit var adapter:CommentsRVAdapter
    private var commentList:MutableList<CommentNormalBean>? = null
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
//        Log.d(TAG, "onViewCreated: ${videoBean.id}")
        viewModel = ViewModelProvider(this,ViewModelProvider.AndroidViewModelFactory(application))[MyViewModel::class.java]
        viewModel.getCommentsLiveData(videoBean.id).observe(viewLifecycleOwner) {
            val commentModel = it
            var list:MutableList<CommentNormalBean> = ArrayList()
            if (commentModel?.itemList != null) {
//                Log.d(TAG, "onViewCreated: " + commentModel.itemList)
                for (m in commentModel.itemList) {
                    if (m.type == "reply" && m.data.user != null) {
                        list.add(
                            CommentNormalBean(
                                m.data.message?:"", m.data.likeCount?:0, PersonalBean(
                                    m.data.user.uid, m.data.user.avatar,
                                    m.data.user.cover?.toString()?:DefaultUtil.defaultCoverUrl,
                                    m.data.user.description?.toString()?:"", m.data.user.nickname,
                                    m.data.user.city?.toString()?:"",m.data.user.job?.toString()?:""
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
                commentList!!.addAll(list)
                adapter.notifyDataSetChanged()
//                Log.d(TAG, "onViewCreated: ${list.size}")
                if(commentList!!.size==0){
                    binding.tvNoComment.visibility = View.VISIBLE
                }else{
                    binding.tvNoComment.visibility = View.GONE
                }
            }
        }
        binding.refreshComment.setOnRefreshListener {
            commentList!!.clear()
            viewModel.updateCommentsViewModel(videoBean.id)
            binding.refreshComment.isRefreshing = false
        }
        commentList = ArrayList()
        adapter = CommentsRVAdapter(commentList!!)
        binding.rvContent.adapter = adapter
        binding.rvContent.layoutManager = LinearLayoutManager(MyApplication.context,RecyclerView.VERTICAL,false)
        adapter.setListener(object :CommentsRVAdapter.OnCommentClickListener{
            override fun onClick(personalBean: PersonalBean) {
                PersonMessageActivity.fragmentStartVideoPlayActivity(MyApplication.context!!,activity!!,personalBean)
            }

        })
    }

    //释放资源
    override fun onDestroy() {
        commentList?.clear()
        commentList = null
        super.onDestroy()
    }
}