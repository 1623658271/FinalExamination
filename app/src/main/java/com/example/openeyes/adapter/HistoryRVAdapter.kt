package com.example.openeyes.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.openeyes.R
import com.example.openeyes.databinding.ItemVideoCardBinding
import com.example.openeyes.model.VideoBean

/**
 * description ： TODO:类的作用
 * author : lfy
 * email : 1623658271@qq.com
 * date : 2022/8/5 15:19
 */
class HistoryRVAdapter:RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private val videoBeanList:MutableList<VideoBean> = ArrayList()

    inner class MyViewHolder(item:ItemVideoCardBinding):RecyclerView.ViewHolder(item.root){
        val binding = item
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding:ItemVideoCardBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.item_video_card,
            parent,
            false
        )
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val h = holder as MyViewHolder
        h.binding.message = videoBeanList[position]
        h.binding.llVideoCard.setOnClickListener { clickedListener?.onVideoImageClickedListener(videoBeanList[position]) }
    }

    override fun getItemCount() = videoBeanList.size

    /**
     * 点击监听的接口，头像点击和视频图片点击
     */
    interface OnSomethingClickedListener{
        fun onVideoImageClickedListener(videoBean:VideoBean)
    }

    private var clickedListener:OnSomethingClickedListener?=null
    /**
     * 设置点击监听
     */
    fun setClickListener(clickListener:OnSomethingClickedListener){
        this.clickedListener = clickListener
    }

    fun setData(list:MutableList<VideoBean>){
        videoBeanList.clear()
        videoBeanList.addAll(list)
        notifyDataSetChanged()
    }

}