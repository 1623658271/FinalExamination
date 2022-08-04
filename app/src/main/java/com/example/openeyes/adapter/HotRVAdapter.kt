package com.example.openeyes.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.openeyes.R
import com.example.openeyes.databinding.ItemHotSearchBinding

/**
 * description ： 热搜关键词的RV适配器
 * author : lfy
 * email : 1623658271@qq.com
 * date : 2022/8/4 13:19
 */
class HotRVAdapter: RecyclerView.Adapter<RecyclerView.ViewHolder>(){
    private val list:MutableList<String> = ArrayList()

    inner class MyViewHolder(itemView: ItemHotSearchBinding): RecyclerView.ViewHolder(itemView.root){
        var binding: ItemHotSearchBinding = itemView
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding: ItemHotSearchBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.item_hot_search,
            parent,
            false
        )
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if(holder is MyViewHolder){
            holder.binding.tvHot.apply {
                text = list[position]
                setOnClickListener { listener?.onClick(list[position]) }
            }
        }
    }

    override fun getItemCount(): Int = list.size

    private var listener:OnItemClickListener? = null

    interface OnItemClickListener{
        fun onClick(text:String)
    }

    fun setListener(listener:OnItemClickListener){
        this.listener = listener
    }

    fun setData(list:MutableList<String>){
        this.list.clear()
        this.list.addAll(list)
        notifyDataSetChanged()
    }
}