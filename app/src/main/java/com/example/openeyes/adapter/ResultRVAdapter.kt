package com.example.openeyes.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.openeyes.R
import com.example.openeyes.databinding.ItemSearchResultBinding
import com.example.openeyes.databinding.LayoutLoadMessageBinding
import com.example.openeyes.model.VideoBean

/**
 * description ： 搜索页的RV适配器
 * author : lfy
 * email : 1623658271@qq.com
 * date : 2022/8/4 13:20
 */
class ResultRVAdapter: RecyclerView.Adapter<RecyclerView.ViewHolder>(){
    private val list:MutableList<VideoBean> = ArrayList()

    //普通布局
    private val TYPE_ITEM = 1
    //脚布局
    private val TYPE_FOOTER = 2

    // 正在加载
    val LOADING = 1

    // 加载完成
    val LOADING_COMPLETE = 2

    // 加载到底
    val LOADING_END = 3

    inner class FootViewHolder(itemView: LayoutLoadMessageBinding): RecyclerView.ViewHolder(itemView.root){
        val binding = itemView
    }

    inner class MyViewHolder(itemView: ItemSearchResultBinding): RecyclerView.ViewHolder(itemView.root){
        var binding: ItemSearchResultBinding = itemView
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if(viewType==TYPE_ITEM){
            val binding: ItemSearchResultBinding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.item_search_result,
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

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, p: Int) {
        val position = p
        if(holder is MyViewHolder){
            holder.binding.message = list[position]
            holder.binding.ivItemResultVideo.setOnClickListener { listener?.onClick(list[position]) }
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

    override fun getItemCount(): Int = list.size + 1

    private var listener: OnItemClickListener? = null

    interface OnItemClickListener{
        fun onClick(viewBean: VideoBean)
    }

    fun setListener(listener:OnItemClickListener){
        this.listener = listener
    }

    private var loadState = 0

    /**
     * 设置加载状态，以显示或隐藏脚布局
     */
    fun setLoadState(loadState:Int){
        this.loadState = loadState
        notifyDataSetChanged()
    }

    override fun getItemViewType(position: Int): Int {
        return if(position + 1 == itemCount){
            TYPE_FOOTER
        } else{
            TYPE_ITEM
        }
    }

    fun setData(list:MutableList<VideoBean>){
        this.list.clear()
        this.list.addAll(list)
        notifyDataSetChanged()
    }
}