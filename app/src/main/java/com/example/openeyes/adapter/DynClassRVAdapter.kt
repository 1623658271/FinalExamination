package com.example.openeyes.adapter

import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.openeyes.R
import com.example.openeyes.databinding.ItemHotSearchBinding
import com.example.openeyes.model.AllDynamicsBean

/**
 * description ： 主题页分类的RV适配器
 * author : lfy
 * email : 1623658271@qq.com
 * date : 2022/8/6 19:08
 */
class DynClassRVAdapter:RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private val list:MutableList<AllDynamicsBean.TabInfo.Tab> = ArrayList()

    inner class MyViewHolder(val binding: ItemHotSearchBinding): RecyclerView.ViewHolder(binding.root)

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
                height = 120
                textSize = 12f
                setBackgroundColor(Color.DKGRAY)
                text = list[position+1].name
                setOnClickListener { listener?.onClick(list[position+1]) }
            }
        }
    }

    override fun getItemCount() = list.size - 1

    private var listener:OnItemClickListener? = null

    interface OnItemClickListener{
        fun onClick(bean:AllDynamicsBean.TabInfo.Tab)
    }

    fun setListener(listener:OnItemClickListener){
        this.listener = listener
    }

    fun setData(list: MutableList<AllDynamicsBean.TabInfo.Tab>) {
        val before = this.list.size
        this.list.clear()
        this.list.addAll(list)
        notifyItemRangeChanged(before,this.list.size-before)
    }

    fun getListener() = listener
}