package com.example.openeyes.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.openeyes.R
import com.example.openeyes.databinding.ItemPicturesBinding
import com.example.openeyes.model.SpecialPicBean

/**
 * description ： 发现的专题页RV适配器
 * author : lfy
 * email : 1623658271@qq.com
 * date : 2022/8/4 15:02
 */
class PictureRVAdapter:RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private val picList:MutableList<SpecialPicBean> = ArrayList()

    inner class MyViewHolder(item:ItemPicturesBinding):RecyclerView.ViewHolder(item.root){
        val binding = item
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding:ItemPicturesBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.item_pictures,
            parent,
            false
        )
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val h = holder as MyViewHolder
        h.binding.message = picList[position]
        h.binding.ivPics.setOnClickListener { listener?.onClick(picList[position]) }
    }

    override fun getItemCount() = picList.size

    fun setData(list: MutableList<SpecialPicBean>){
        picList.clear()
        picList.addAll(list)
        notifyDataSetChanged()
    }

    private var listener:OnIVClickListener?=null

    interface OnIVClickListener{
        fun onClick(bean:SpecialPicBean)
    }

    fun setListener(listener: OnIVClickListener){
        this.listener = listener
    }
}