package com.example.openeyes.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.openeyes.R
import com.example.openeyes.databinding.ItemRvDiscoverBinding
import com.example.openeyes.model.ClassModel
import com.example.openeyes.model.FindMoreClassBean

/**
 * description ： TODO:类的作用
 * author : lfy
 * email : 1623658271@qq.com
 * date : 2022/7/15 13:03
 */
class DiscoverClassRVAdapter(val classModelList:MutableList<ClassModel>):RecyclerView.Adapter<DiscoverClassRVAdapter.MyViewHolder>() {
    private var maxHeight = 0
    private var maxWidth = 0

    inner class MyViewHolder(itemView:ItemRvDiscoverBinding) : RecyclerView.ViewHolder(itemView.root) {
        var itemRvDiscoverBinding:ItemRvDiscoverBinding = itemView
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemRvDiscoverBinding:ItemRvDiscoverBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.context),
            R.layout.item_rv_discover,
            parent,
            false
            )
        val holder = MyViewHolder(itemRvDiscoverBinding)

        itemRvDiscoverBinding.itemImageDiscover.setOnClickListener {
            mOnItemClickListener?.onItemClick(itemRvDiscoverBinding.root,holder,holder.adapterPosition,classModelList)
        }
        return holder
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val classModel = classModelList[position]
        holder.itemRvDiscoverBinding.itemImageDiscover
        holder.itemRvDiscoverBinding.findClass = classModel
    }

    override fun getItemCount(): Int = classModelList.size-1

    var mOnItemClickListener: OnItemClickListener? = null
    fun setOnItemClickListener(onItemClickListener: OnItemClickListener?) {
        mOnItemClickListener = onItemClickListener
    }

    interface OnItemClickListener {
        fun onItemClick(view: View?, holder: RecyclerView.ViewHolder?, position: Int,classlist:MutableList<ClassModel>)
        fun onItemLongClick(view: View?, holder: RecyclerView.ViewHolder?, position: Int): Boolean
    }
}