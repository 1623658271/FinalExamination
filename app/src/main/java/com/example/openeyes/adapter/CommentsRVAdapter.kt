package com.example.openeyes.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.openeyes.R
import com.example.openeyes.databinding.ItemCommentBinding
import com.example.openeyes.model.CommentNormalBean
import com.example.openeyes.model.PersonalBean

/**
 * description ： 评论RV的适配器，传入一个CommentBean以加载评论
 * author : lfy
 * email : 1623658271@qq.com
 * date : 2022/7/18 09:18
 */
class CommentsRVAdapter: RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var commentList:MutableList<CommentNormalBean> = ArrayList()

    inner class MyViewHolder(val binding:ItemCommentBinding):RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding:ItemCommentBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.item_comment,
            parent,
            false
        )
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if(holder is MyViewHolder){
            holder.binding.comment = commentList[position]
            holder.binding.commentPerson.setOnClickListener { listener?.onClick(commentList[position].personalBean) }
        }
    }

    override fun getItemCount(): Int = commentList.size

    private var listener:OnCommentClickListener?=null

    interface OnCommentClickListener{
        fun onClick(personalBean: PersonalBean)
    }

    fun setListener(listener:OnCommentClickListener){
        this.listener = listener
    }

    fun setData(list:MutableList<CommentNormalBean>){
        commentList.clear()
        commentList.addAll(list)
        notifyDataSetChanged()
    }
}