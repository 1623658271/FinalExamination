package com.example.openeyes.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.openeyes.R
import com.example.openeyes.databinding.ItemHomepageVideoBinding
import com.example.openeyes.databinding.ItemSpecialPageBinding
import com.example.openeyes.model.SpecialPageBean
import com.example.openeyes.model.VideoBean

/**
 * description ： 专题进入页的RV适配器
 * author : lfy
 * email : 1623658271@qq.com
 * date : 2022/8/4 15:02
 */
class SpecialRVAdapter:RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private lateinit var specialBean:SpecialPageBean
    private val list:MutableList<VideoBean> = ArrayList()
    //页面类型
    private val TYPE_TOP = 0
    private val TYPE_NORMAL = 1

    inner class NormalViewHolder(val binding: ItemHomepageVideoBinding):RecyclerView.ViewHolder(binding.root)

    inner class TopViewHolder(val binding:ItemSpecialPageBinding):RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if(viewType==TYPE_NORMAL){
            val binding: ItemHomepageVideoBinding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.item_homepage_video,
                parent,
                false
            )
            return NormalViewHolder(binding)
        }else{
            val binding: ItemSpecialPageBinding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.item_special_page,
                parent,
                false
            )
            return TopViewHolder((binding))
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if(holder is TopViewHolder){
            holder.binding.message = specialBean
        }else if(holder is NormalViewHolder){
            holder.binding.message = list[position-1]
            holder.binding.itemPersonCoverCircleImage.setOnClickListener { clickListener.onAvatarImageClickedListener(list[position-1]) }
            holder.binding.ivItemCoverVideo.setOnClickListener { clickListener.onVideoImageClickedListener(list[position-1]) }
        }
    }

    override fun getItemCount() = list.size + 1

    override fun getItemViewType(position: Int): Int {
        return if(position==0){
            TYPE_TOP
        }else{
            TYPE_NORMAL
        }
    }

    fun setData(bean:SpecialPageBean){
        specialBean = bean
        list.clear()
        list.addAll(bean.videoBeanList)
        notifyDataSetChanged()
    }

    private lateinit var clickListener:OnSomethingClickedListener

    /**
     * 点击监听的接口，头像点击和视频图片点击
     */
    interface OnSomethingClickedListener {
        fun onVideoImageClickedListener(videoBean: VideoBean)

        fun onAvatarImageClickedListener(videoBean: VideoBean)
    }

    /**
     * 设置点击监听
     */
    fun setClickListener(clickListener:OnSomethingClickedListener){
        this.clickListener = clickListener
    }
}