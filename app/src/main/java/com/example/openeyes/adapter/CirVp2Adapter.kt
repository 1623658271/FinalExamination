package com.example.openeyes.adapter

import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.viewpager.widget.PagerAdapter
import com.bumptech.glide.Glide

/**
 * description ： TODO:类的作用
 * author : lfy
 * email : 1623658271@qq.com
 * date : 2022/7/20 16:33
 */
class CirVp2Adapter(var imageUrlList:MutableList<String>): PagerAdapter() {

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val imageView = ImageView(container.context)
        imageView.scaleType = ImageView.ScaleType.FIT_XY
        Glide.with(container.context).load(imageUrlList[position % imageUrlList.size]).into(imageView)
        container.addView(imageView)
        return imageView
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as View?)
    }
    override fun getCount(): Int = Integer.MAX_VALUE

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view === `object`
    }

    fun setData(imageUrlList: MutableList<String>) {
        this.imageUrlList = imageUrlList
    }
    fun getDataResultSize(): Int = imageUrlList.size
}