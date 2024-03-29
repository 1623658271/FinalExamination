package com.example.openeyes.adapter

import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.viewpager.widget.PagerAdapter
import com.bumptech.glide.Glide
import com.example.openeyes.model.VideoBean

/**
 * description ： 自定义的PagerAdapter，用于处理轮播图
 * author : lfy
 * email : 1623658271@qq.com
 * date : 2022/7/20 16:33
 */
class CirVp2Adapter(var imageUrlList:MutableList<VideoBean>): PagerAdapter() {

    //为给定的position位置创建view
    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val imageView = ImageView(container.context)
        imageView.scaleType = ImageView.ScaleType.FIT_XY
        Glide.with(container.context).load(imageUrlList[position % imageUrlList.size].coverUrl).into(imageView)
        container.addView(imageView)
        imageView.setOnClickListener{bannerClickListener!!.onBannerClick(imageUrlList,position % imageUrlList.size)}
        return imageView
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as View?)
    }
    override fun getCount(): Int = Integer.MAX_VALUE

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view === `object`
    }

    fun getDataResultSize(): Int = imageUrlList.size

    private var bannerClickListener:OnBannerClickListener? = null

    interface OnBannerClickListener{
        fun onBannerClick(imageUrlList:MutableList<VideoBean>,position: Int)
    }

    fun setBannerClickListener(onBannerClickListener: OnBannerClickListener){
        this.bannerClickListener = onBannerClickListener
    }
}