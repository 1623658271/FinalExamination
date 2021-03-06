package com.example.openeyes.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.openeyes.R
import com.example.openeyes.databinding.ItemCommentBinding
import com.example.openeyes.databinding.ItemHomepageVideoBinding
import com.example.openeyes.model.CommentBean
import com.example.openeyes.model.PersonalModel

/**
 * description ： 评论RV的适配器，传入一个CommentBean以加载评论
 * author : lfy
 * email : 1623658271@qq.com
 * date : 2022/7/18 09:18
 */
class CommentsRVAdapter(val commentList:MutableList<CommentBean>): RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    inner class MyViewHolder(itemView:ItemCommentBinding):RecyclerView.ViewHolder(itemView.root){
        var binding: ItemCommentBinding = itemView
    }

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
            holder.binding.commentPerson.setOnClickListener { listener?.onClick(commentList[position].personalModel) }
        }
    }

    override fun getItemCount(): Int = commentList.size

    private var listener:OnCommentClickListener?=null

    interface OnCommentClickListener{
        fun onClick(personalModel: PersonalModel)
    }

    fun setListener(listener:OnCommentClickListener){
        this.listener = listener
    }
}