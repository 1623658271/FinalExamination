package com.example.openeyes.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.openeyes.R
import com.example.openeyes.databinding.ItemRvDiscoverBinding
import com.example.openeyes.model.HotClassModel

/**
 * description ： TODO:类的作用
 * author : lfy
 * email : 1623658271@qq.com
 * date : 2022/7/15 13:03
 */
class DiscoverRVAdapter(val hotClassModelList:MutableList<HotClassModel>):RecyclerView.Adapter<DiscoverRVAdapter.MyViewHolder>() {

    inner class MyViewHolder(itemView:ItemRvDiscoverBinding) : RecyclerView.ViewHolder(itemView.root) {
        var itemRvDiscoverBinding:ItemRvDiscoverBinding = itemView
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemRvDiscoverBinding:ItemRvDiscoverBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.context),
            R.layout.item_rv_discover,
            parent,
            false
            )
        return MyViewHolder(itemRvDiscoverBinding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val hotClassModel = hotClassModelList[position]
        holder.itemRvDiscoverBinding.hotclass = hotClassModel
    }

    override fun getItemCount(): Int = hotClassModelList.size
}