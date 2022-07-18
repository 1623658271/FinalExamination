package com.example.openeyes.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.LifecycleObserver
import androidx.recyclerview.widget.RecyclerView
import com.example.openeyes.MyApplication
import com.example.openeyes.R
import com.example.openeyes.databinding.ItemHomepageVideoBinding
import com.example.openeyes.databinding.LayoutLoadMessageBinding
import com.example.openeyes.model.VideoBean


/**
 * description ： TODO:类的作用
 * author : lfy
 * email : 1623658271@qq.com
 * date : 2022/7/16 19:43
 */
class HomePageRVAdapter(val videoBeanList: MutableList<VideoBean>):
    RecyclerView.Adapter<RecyclerView.ViewHolder>(),LifecycleObserver {
    private val context: Context? = MyApplication.context
    //普通布局
    private val TYPE_ITEM = 1
    //脚布局
    private val TYPE_FOOTER = 2
    //当前加载状态，默认为加载完成
    private var loadState = 0
    // 正在加载
    val LOADING = 1
    // 加载完成
    val LOADING_COMPLETE = 2
    // 加载到底
    val LOADING_END = 3

    inner class MyViewHolder(itemView:ItemHomepageVideoBinding):RecyclerView.ViewHolder(itemView.root){
        var binding:ItemHomepageVideoBinding = itemView
    }
    inner class FootViewHolder(itemView:LayoutLoadMessageBinding):RecyclerView.ViewHolder(itemView.root){
        val binding = itemView
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        if(viewType==TYPE_ITEM) {
            val binding: ItemHomepageVideoBinding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.item_homepage_video,
                parent,
                false
            )
            return MyViewHolder(binding)
        }else{
            val loadBinding: LayoutLoadMessageBinding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.layout_load_message,
                parent,
                false
            )
            return FootViewHolder(loadBinding)
        }
    }

    override fun getItemCount(): Int = videoBeanList.size+1

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if(holder is MyViewHolder) {
            holder.binding.message = videoBeanList[position]
            holder.binding.ivItemCoverVideo.setOnClickListener { clickListener.onVideoImageClickedListener(holder.binding.root,holder,position,videoBeanList) }
            holder.binding.itemPersonCoverCircleImage.setOnClickListener { clickListener.onAvatarImageClickedListener(holder.binding.root,holder,position,videoBeanList) }
        }else if(holder is FootViewHolder){
            when(loadState) {
                LOADING -> {
                    holder.binding.tvLoad.visibility = View.VISIBLE
                    holder.binding.tvLoad.text = "加载中..."
                }
                LOADING_COMPLETE -> {
                    holder.binding.tvLoad.visibility = View.VISIBLE
                    holder.binding.tvLoad.text = "加载完成"
                }
                LOADING_END -> {
                    holder.binding.tvLoad.visibility = View.VISIBLE
                    holder.binding.tvLoad.text = "亲！到底线了~~~"
                }

            }
        }
    }
    fun setLoadState(loadState:Int){
        this.loadState = loadState
        notifyDataSetChanged()
    }

    override fun getItemViewType(position: Int): Int {
        return if (position + 1 == itemCount) {
            TYPE_FOOTER
        } else {
            TYPE_ITEM
        }
    }

    private lateinit var clickListener:OnSomethingClickedListener
    interface OnSomethingClickedListener{
        fun onVideoImageClickedListener(view:View,holder:RecyclerView.ViewHolder,position: Int,videoBeanList: MutableList<VideoBean>)

        fun onAvatarImageClickedListener(view:View,holder:RecyclerView.ViewHolder,position: Int,videoBeanList: MutableList<VideoBean>)
    }

    fun setClickListener(clickListener:OnSomethingClickedListener){
        this.clickListener = clickListener
    }
}