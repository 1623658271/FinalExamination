package com.example.openeyes.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.openeyes.R
import com.example.openeyes.databinding.ItemTextCardBinding
import com.example.openeyes.databinding.ItemVideoCardBinding
import com.example.openeyes.databinding.LayoutVideoDetailBinding
import com.example.openeyes.model.PersonalBean
import com.example.openeyes.model.RelatedVideoBean
import com.example.openeyes.model.VideoBean
import com.example.openeyes.utils.DefaultUtil

/**
 * description ： 精选的更多数据的RV适配器
 * author : lfy
 * email : 1623658271@qq.com
 * date : 2022/7/19 11:07
 */
class RelatedRVAdapter(var nowMessage:VideoBean): RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var itemList:MutableList<RelatedVideoBean.Item> = ArrayList()

    val TYPE_TEXT = 0
    val TYPE_VIDEO = 1
    val TYPE_NOW = 3
//    val TAG = "lfy"

    inner class NowMessageViewHolder(val binding: LayoutVideoDetailBinding):RecyclerView.ViewHolder(binding.root){
    }

    inner class TextCardViewHolder(itemTextCardBinding: ItemTextCardBinding):RecyclerView.ViewHolder(itemTextCardBinding.root){
        val binding = itemTextCardBinding
    }

    inner class VideoCardViewHolder(itemVideoCardBinding: ItemVideoCardBinding):RecyclerView.ViewHolder(itemVideoCardBinding.root){
        val binding = itemVideoCardBinding
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        when (viewType) {
            TYPE_NOW -> {
                val binding:LayoutVideoDetailBinding = DataBindingUtil.inflate(
                    LayoutInflater.from(parent.context),
                    R.layout.layout_video_detail,
                    parent,
                    false
                )
                return NowMessageViewHolder(binding)
            }
            TYPE_TEXT -> {
                val binding:ItemTextCardBinding = DataBindingUtil.inflate(
                    LayoutInflater.from(parent.context),
                    R.layout.item_text_card,
                    parent,
                    false
                )
                return TextCardViewHolder(binding)
            }
            else -> {
                val binding:ItemVideoCardBinding = DataBindingUtil.inflate(
                    LayoutInflater.from(parent.context),
                    R.layout.item_video_card,
                    parent,
                    false
                )
                return VideoCardViewHolder(binding)
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, p: Int) {
        val position = p - 1
        when (holder) {
            is NowMessageViewHolder -> {
                holder.binding.message = nowMessage
                holder.binding.detailsPersonCircleImage.setOnClickListener { clickedListener?.onCircleImageClick(nowMessage) }
            }
            is TextCardViewHolder -> {
                holder.binding.name = itemList[position].data.text
            }
            is VideoCardViewHolder -> {
                val m = itemList[position]
//                Log.d(TAG, "onBindViewHolder: ${m.data}")
                holder.binding.message = VideoBean(m.data.id?:0,m.data.title?:"",m.data.author?.name?:"",m.data.cover?.feed?:"",
                    m.data.playUrl?:"",m.data.description?:"", PersonalBean(m.data.author?.id?:0,m.data.author?.icon?:"",DefaultUtil.defaultCoverUrl,m.data.author?.description?:"",
                        m.data.author?.name?:"","","")
                ,m.data.consumption)
                val videoBean = VideoBean(m.data.id?:0,m.data.title?:"",m.data.author?.name?:"",m.data.cover?.feed?:"",m.data.playUrl?:"",
                m.data.description?:"",PersonalBean(m.data.author?.id?:0,m.data.author?.icon?:"",DefaultUtil.defaultCoverUrl,m.data.author?.description?:"",
                    m.data.author?.name?:"","",""),m.data.consumption)
                holder.binding.llVideoCard.setOnClickListener { clickedListener?.onVideoImageClickedListener(videoBean) }
            }
        }
    }

    override fun getItemCount(): Int = itemList.size + 1

    override fun getItemViewType(position: Int): Int {
        return if(position == 0){
            TYPE_NOW
        }else if(itemList[position-1].type=="textCard"){
            TYPE_TEXT
        }else{
            TYPE_VIDEO
        }
    }

    /**
     * 点击监听的接口，头像点击和视频图片点击
     */
    interface OnSomethingClickedListener{
        fun onVideoImageClickedListener(videoBean:VideoBean)
        fun onCircleImageClick(videoBean:VideoBean)
    }

    private var clickedListener:OnSomethingClickedListener?=null
    /**
     * 设置点击监听
     */
    fun setClickListener(clickListener:OnSomethingClickedListener){
        this.clickedListener = clickListener
    }

    fun setData(list:MutableList<RelatedVideoBean.Item>){
        val before = itemList.size
        itemList.clear()
        itemList.addAll(list)
        notifyItemRangeChanged(before,itemList.size-before)
    }
}