package com.example.openeyes.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.openeyes.MyApplication
import com.example.openeyes.R
import com.example.openeyes.databinding.ItemPicsCardBinding
import com.example.openeyes.databinding.ItemVideCardBinding
import com.example.openeyes.databinding.LayoutLoadMessageBinding
import com.example.openeyes.bean.PersonalBean
import com.example.openeyes.bean.PicsBean
import com.example.openeyes.bean.VideoBean


/**
 * description ： 推荐页RV的适配器
 * author : lfy
 * email : 1623658271@qq.com
 * date : 2022/7/22 18:52
 */
class RecRVAdapter(var mapList:MutableList<Map<String,Any>>):RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    val TYPE_PIC = 0
    val TYPE_VIDEO = 1
    val TYPE_LOAD = 2

    private var loadState = 0

    // 正在加载
    val LOADING = 1
    // 加载完成
    val LOADING_COMPLETE = 2
    // 加载到底
    val LOADING_END = 3

    inner class VideoCardHolder(val binding: ItemVideCardBinding):RecyclerView.ViewHolder(binding.root){
    }
    inner class PicCardHolder(val binding: ItemPicsCardBinding):RecyclerView.ViewHolder(binding.root){
    }
    inner class FootViewHolder(val binding: LayoutLoadMessageBinding):RecyclerView.ViewHolder(binding.root){
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        when(viewType){
            TYPE_PIC -> {
                return PicCardHolder(DataBindingUtil.inflate(
                    LayoutInflater.from(parent.context),
                    R.layout.item_pics_card,
                    parent,
                    false
                ))
            }
            TYPE_LOAD ->{
                return FootViewHolder(DataBindingUtil.inflate(
                    LayoutInflater.from(parent.context),
                    R.layout.layout_load_message,
                    parent,
                    false
                ))
            }
            else ->{
                return VideoCardHolder(DataBindingUtil.inflate(
                    LayoutInflater.from(parent.context),
                    R.layout.item_vide_card,
                    parent,
                    false
                ))
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if(holder is PicCardHolder){
            val picsBean = mapList[position]["message"] as PicsBean
            holder.binding.message = picsBean

            val layoutParams: ViewGroup.LayoutParams = holder.binding.ivPic.layoutParams
            Glide.with(MyApplication.application!!).load(picsBean.coverUrl)
                .override(layoutParams.width, Int.MAX_VALUE)
                .into(holder.binding.ivPic)

            holder.binding.ivPic.setOnClickListener { listener?.onPicClick(picsBean) }
            holder.binding.picItemPersonCoverCircleImage.setOnClickListener { listener?.onPersonImageClick(picsBean.personalBean!!) }
        }else if(holder is VideoCardHolder){
            val videoBean = mapList[position]["message"] as VideoBean
            holder.binding.message = videoBean

            val layoutParams: ViewGroup.LayoutParams = holder.binding.ivRecCoverVideo.layoutParams
            Glide.with(MyApplication.application!!).load(videoBean.coverUrl)
                .override(layoutParams.width, Int.MAX_VALUE)
                .into(holder.binding.ivRecCoverVideo)

            holder.binding.ivRecCoverVideo.setOnClickListener { listener?.onVideoClick(videoBean) }
            holder.binding.videItemPersonCoverCircleImage.setOnClickListener { listener?.onPersonImageClick(videoBean.personalBean!!) }
        }else if(holder is FootViewHolder){
            when(loadState) {
                LOADING -> {
                    holder.binding.tvLoad.visibility = View.VISIBLE
                    holder.binding.tvLoad.text = "加载中..."
                }
                LOADING_COMPLETE -> {
                    holder.binding.tvLoad.visibility = View.GONE
                    holder.binding.tvLoad.text = "加载完成"
                }
                LOADING_END -> {
                    holder.binding.tvLoad.visibility = View.VISIBLE
                    holder.binding.tvLoad.text = "亲！到底线了~~~"
                }

            }
        }
    }

    override fun getItemCount(): Int = mapList.size + 1

    override fun getItemViewType(position: Int): Int {
        return if(position + 1 == itemCount){
            TYPE_LOAD
        }
        else if(mapList[position]["type"] as String == "ugcPicture"){
            TYPE_PIC
        }else{
            TYPE_VIDEO
        }
    }

    private var listener:OnClickListener? = null

    interface OnClickListener{
        fun onVideoClick(videoBean: VideoBean)
        fun onPicClick(picsBean: PicsBean)
        fun onPersonImageClick(personalBean: PersonalBean)
    }

    fun setClickListener(listener: OnClickListener){
        this.listener = listener
    }

    fun setLoadState(state:Int){
        this.loadState = state
    }

}