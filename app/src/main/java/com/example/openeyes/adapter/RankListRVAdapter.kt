package com.example.openeyes.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.openeyes.R
import com.example.openeyes.databinding.ItemHomepageVideoBinding
import com.example.openeyes.model.VideoBean

/**
 * description ： 排行榜的RV适配器
 * author : lfy
 * email : 1623658271@qq.com
 * date : 2022/8/4 13:20
 */
class RankListRVAdapter:RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private val videoBeanList:MutableList<VideoBean> = ArrayList()

    inner class MyViewHolder(item:ItemHomepageVideoBinding):RecyclerView.ViewHolder(item.root){
        val binding = item
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding: ItemHomepageVideoBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.item_homepage_video,
            parent,
            false
        )
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val h = holder as MyViewHolder
        h.binding.message = videoBeanList[position]
        h.binding.ivItemCoverVideo.setOnClickListener { clickListener.onVideoImageClickedListener(videoBeanList[position]) }
        h.binding.itemPersonCoverCircleImage.setOnClickListener { clickListener.onAvatarImageClickedListener(videoBeanList[position]) }
    }

    override fun getItemCount() = videoBeanList.size

    private lateinit var clickListener:OnSomethingClickedListener

    /**
     * 点击监听的接口，头像点击和视频图片点击
     */
    interface OnSomethingClickedListener{
        fun onVideoImageClickedListener(videoBean:VideoBean)

        fun onAvatarImageClickedListener(videoBean:VideoBean)
    }

    /**
     * 设置点击监听
     */
    fun setClickListener(clickListener:OnSomethingClickedListener){
        this.clickListener = clickListener
    }

    fun setData(list:MutableList<VideoBean>){
        videoBeanList.clear()
        videoBeanList.addAll(list)
        notifyDataSetChanged()
    }
}