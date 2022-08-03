package com.example.openeyes.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.openeyes.R
import com.example.openeyes.databinding.ItemHomepageVideoBinding
import com.example.openeyes.databinding.ItemTextCardBinding
import com.example.openeyes.databinding.LayoutLoadMessageBinding
import com.example.openeyes.model.VideoBean

/**
 * description ： 分类进入后的页面
 * author : lfy
 * email : 1623658271@qq.com
 * date : 2022/7/21 19:42
 */
class ClassInRVAdapter:RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var mapList:MutableList<Map<String,Any>> = ArrayList()

    val TYPE_ITEM = 0
    val TYPE_TEXT = 1
    val TYPE_LOAD = 2

    var loadState = 0
    val LOADING = 1
    val END = 2
    val COMPLETE = 3

    inner class itemViewHolder(itemBinding:ItemHomepageVideoBinding):RecyclerView.ViewHolder(itemBinding.root){
        val binding = itemBinding
    }

    inner class textViewHolder(textBinding:ItemTextCardBinding):RecyclerView.ViewHolder(textBinding.root){
        val binding = textBinding
    }

    inner class loadViewHolder(loadBinding:LayoutLoadMessageBinding):RecyclerView.ViewHolder(loadBinding.root){
        val binding = loadBinding
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when(viewType){
            TYPE_TEXT -> textViewHolder(
                DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.item_text_card,
                parent,
                false
            ))
            TYPE_ITEM -> itemViewHolder(DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.item_homepage_video,
                parent,
                false
            ))
            else -> loadViewHolder(DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.layout_load_message,
                parent,
                false
            ))
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(holder){
            is itemViewHolder->{
                val videoBean = mapList[position]["content"] as VideoBean
                holder.binding.message = videoBean
                holder.binding.ivItemCoverVideo.setOnClickListener { clickListener.onVideoImageClickedListener(videoBean) }
                holder.binding.itemPersonCoverCircleImage.setOnClickListener { clickListener.onAvatarImageClickedListener(videoBean) }
            }
            is textViewHolder->{
                holder.binding.name = mapList[position]["content"] as String
            }
            is loadViewHolder->{
                when(loadState) {
                    LOADING -> {
                        holder.binding.tvLoad.visibility = View.VISIBLE
                        holder.binding.tvLoad.text = "加载中..."
                    }
                    COMPLETE -> {
                        holder.binding.tvLoad.visibility = View.VISIBLE
                        holder.binding.tvLoad.text = "加载完成"
                    }
                    END -> {
                        holder.binding.tvLoad.visibility = View.VISIBLE
                        holder.binding.tvLoad.text = "亲！到底线了~~~"
                    }

                }
            }
        }
    }

    override fun getItemCount(): Int = mapList.size + 1

    override fun getItemViewType(position: Int): Int {
        return if(position+1==itemCount){
            TYPE_LOAD
        }else if(mapList[position]["type"] == "textCard"){
            TYPE_TEXT
        }else{
            TYPE_ITEM
        }
    }

    /**
     * 设置加载状态，以显示或隐藏脚布局
     */
    fun setClassInLoadState(loadState:Int){
        this.loadState = loadState
        notifyDataSetChanged()
    }

    private lateinit var clickListener:OnSomethingClickedListener

    /**
     * 点击监听的接口，头像点击和视频图片点击
     */
    interface OnSomethingClickedListener{
        fun onVideoImageClickedListener(videoBean: VideoBean)

        fun onAvatarImageClickedListener(videoBean: VideoBean)
    }

    /**
     * 设置点击监听
     */
    fun setClickListener(clickListener:OnSomethingClickedListener){
        this.clickListener = clickListener
    }

    fun setData(list:MutableList<Map<String,Any>>){
        val before = mapList.size
        mapList.clear()
        mapList.addAll(list)
        notifyItemRangeChanged(before,mapList.size-before)
    }
}