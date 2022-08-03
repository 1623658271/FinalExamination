package com.example.openeyes.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.openeyes.MyApplication
import com.example.openeyes.R
import com.example.openeyes.VideoPlayActivity
import com.example.openeyes.databinding.ItemHomepageVideoBinding
import com.example.openeyes.databinding.LayoutCirImageBinding
import com.example.openeyes.databinding.LayoutLoadMessageBinding
import com.example.openeyes.model.VideoBean
import com.example.openeyes.view.CirLayout


/**
 * description ： 首页RV的适配器，传入VideoBean以加载数据
 * author : lfy
 * email : 1623658271@qq.com
 * date : 2022/7/16 19:43
 */
class HomePageRVAdapter:
    RecyclerView.Adapter<RecyclerView.ViewHolder>(){
//    private val TAG = "lfy"
    private var videoBeanList: MutableList<VideoBean> = ArrayList()
    private var imageUrlList:MutableList<VideoBean> = ArrayList()
    private var adapter:CirVp2Adapter?=null

    companion object {
        //普通布局
        private const val TYPE_ITEM = 1
        //脚布局
        private const val TYPE_FOOTER = 2
        //轮播布局
        private const val TYPE_CIR = 4
    }


    //当前加载状态，默认为加载完成
    private var loadState = 0
    // 正在加载
    val LOADING = 1
    // 加载完成
    val LOADING_COMPLETE = 2
    // 加载到底
    val LOADING_END = 3
    inner class CirViewHolder(itemView: LayoutCirImageBinding):RecyclerView.ViewHolder(itemView.root){
        var binding:LayoutCirImageBinding = itemView
    }

    inner class MyViewHolder(itemView:ItemHomepageVideoBinding):RecyclerView.ViewHolder(itemView.root){
        var binding:ItemHomepageVideoBinding = itemView
    }
    inner class FootViewHolder(itemView:LayoutLoadMessageBinding):RecyclerView.ViewHolder(itemView.root){
        val binding = itemView
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        //对view类型进行判断，然后做相应处理
        when (viewType) {
            TYPE_CIR -> {
                val binding:LayoutCirImageBinding = DataBindingUtil.inflate(
                    LayoutInflater.from(parent.context),
                    R.layout.layout_cir_image,
                    parent,
                    false
                )
                return CirViewHolder(binding)
            }
            TYPE_ITEM -> {
                val binding: ItemHomepageVideoBinding = DataBindingUtil.inflate(
                    LayoutInflater.from(parent.context),
                    R.layout.item_homepage_video,
                    parent,
                    false
                )
                return MyViewHolder(binding)
            }
            else -> {
                val loadBinding: LayoutLoadMessageBinding = DataBindingUtil.inflate(
                    LayoutInflater.from(parent.context),
                    R.layout.layout_load_message,
                    parent,
                    false
                )
                return FootViewHolder(loadBinding)
            }
        }
    }

    override fun getItemCount(): Int = videoBeanList.size + 2

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, p: Int) {
        val position = p - 1
        when (holder) {
            is CirViewHolder -> {
                if(imageUrlList.size>0 && adapter==null) {
//                    Log.d(TAG, "onBindViewHolder: adapter_create")
                    adapter  = CirVp2Adapter(imageUrlList)
                    holder.binding.clBanner.setData(adapter!!,object : CirLayout.BindTitleListener {
                       override fun getTitle(position: Int): String {
                           return imageUrlList[position % imageUrlList.size].bigTitle
                       }
                    })
                    adapter!!.setBannerClickListener(object :CirVp2Adapter.OnBannerClickListener{
                        override fun onBannerClick(
                            imageUrlList: MutableList<VideoBean>,
                            position: Int
                        ) {
                            VideoPlayActivity.startVideoPlayActivity(MyApplication.context!!,imageUrlList[position])
                        }

                    })
                    holder.binding.clBanner.viewPager.currentItem = Integer.MAX_VALUE / 2
                    adapter!!.notifyDataSetChanged()
                }else if(imageUrlList.size>0 && adapter!=null){
                    holder.binding.clBanner.setData(adapter!!,object : CirLayout.BindTitleListener {
                        override fun getTitle(position: Int): String {
                            return imageUrlList[position % imageUrlList.size].bigTitle
                        }
                    })
                    adapter!!.setBannerClickListener(object :CirVp2Adapter.OnBannerClickListener{
                        override fun onBannerClick(
                            imageUrlList: MutableList<VideoBean>,
                            position: Int
                        ) {
                            VideoPlayActivity.startVideoPlayActivity(MyApplication.context!!,imageUrlList[position])
                        }

                    })
                    holder.binding.clBanner.viewPager.currentItem = Integer.MAX_VALUE / 2
                    adapter!!.notifyDataSetChanged()
                }
            }
            is MyViewHolder -> {
                holder.binding.message = videoBeanList[position]
                holder.binding.ivItemCoverVideo.setOnClickListener { clickListener.onVideoImageClickedListener(holder.binding.root,holder,position,videoBeanList) }
                holder.binding.itemPersonCoverCircleImage.setOnClickListener { clickListener.onAvatarImageClickedListener(holder.binding.root,holder,position,videoBeanList) }
            }
            is FootViewHolder -> {
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
    }

    /**
     * 设置加载状态，以显示或隐藏脚布局
     */
    fun setLoadState(loadState:Int){
        this.loadState = loadState
        notifyDataSetChanged()
    }

    override fun getItemViewType(position: Int): Int {
        return if (position == 0) {
            TYPE_CIR
        } else if(position + 1 == itemCount){
            TYPE_FOOTER
        } else{
            TYPE_ITEM
        }
    }

    private lateinit var clickListener:OnSomethingClickedListener

    /**
     * 点击监听的接口，头像点击和视频图片点击
     */
    interface OnSomethingClickedListener{
        fun onVideoImageClickedListener(view:View,holder:RecyclerView.ViewHolder,position: Int,videoBeanList: MutableList<VideoBean>)

        fun onAvatarImageClickedListener(view:View,holder:RecyclerView.ViewHolder,position: Int,videoBeanList: MutableList<VideoBean>)
    }

    /**
     * 设置点击监听
     */
    fun setClickListener(clickListener:OnSomethingClickedListener){
        this.clickListener = clickListener
    }

    fun setData(data:List<VideoBean>){
        imageUrlList.clear()
        videoBeanList.clear()
        if(data.size>6) {
            for (i in 0 until 5) {
                imageUrlList.add(data[i])
            }
            for (i in 5 until data.size) {
                videoBeanList.add(data[i])
            }
        }else{
            imageUrlList.addAll(data)
        }
        notifyDataSetChanged()
    }
}