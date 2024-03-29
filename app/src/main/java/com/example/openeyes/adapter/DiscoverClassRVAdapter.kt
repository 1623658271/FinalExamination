package com.example.openeyes.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.openeyes.R
import com.example.openeyes.databinding.ItemRvDiscoverBinding
import com.example.openeyes.model.ClassBean


/**
 * description ： 发现页的RV适配器，传入ClassModel以加载数据
 * author : lfy
 * email : 1623658271@qq.com
 * date : 2022/7/15 13:03
 */
class DiscoverClassRVAdapter:RecyclerView.Adapter<DiscoverClassRVAdapter.MyViewHolder>() {
    private val classModelList:MutableList<ClassBean> = ArrayList()

    inner class MyViewHolder(val itemRvDiscoverBinding:ItemRvDiscoverBinding) : RecyclerView.ViewHolder(itemRvDiscoverBinding.root)

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
        holder.itemRvDiscoverBinding.findClass = classModel
    }

    override fun getItemCount(): Int = classModelList.size

    var mOnItemClickListener: OnItemClickListener? = null
    fun setOnItemClickListener(onItemClickListener: OnItemClickListener?) {
        mOnItemClickListener = onItemClickListener
    }

    interface OnItemClickListener {
        fun onItemClick(view: View?, holder: RecyclerView.ViewHolder?, position: Int,classlist:MutableList<ClassBean>)
        fun onItemLongClick(view: View?, holder: RecyclerView.ViewHolder?, position: Int): Boolean
    }

    fun setData(list:MutableList<ClassBean>){
        classModelList.clear()
        classModelList.addAll(list)
        notifyDataSetChanged()
    }
}