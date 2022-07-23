package com.example.openeyes.adapter

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.graphics.drawable.toBitmap
import androidx.viewpager.widget.PagerAdapter
import com.bumptech.glide.Glide
import java.io.*
import java.net.URL


/**
 * description ： TODO:类的作用
 * author : lfy
 * email : 1623658271@qq.com
 * date : 2022/7/23 15:17
 */
class PicWatchPagerAdapter(var picList:MutableList<String>): PagerAdapter() {
    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val imageView = ImageView(container.context)
        imageView.scaleType = ImageView.ScaleType.FIT_CENTER
        Glide.with(container.context).load(picList[position]).into(imageView)
        imageView.setOnLongClickListener {
            imageOnLongClickListener?.onLongClick(picList[position])
            true
        }
        container.addView(imageView)
        return imageView
    }

    override fun getCount(): Int = picList.size

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as View?)
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view === `object`
    }

    private var imageOnLongClickListener: OnLongClickListener? = null

    interface OnLongClickListener {
        fun onLongClick(imageUrl: String)
    }

    fun setOnImageLongClickListener(listener: OnLongClickListener){
        this.imageOnLongClickListener = listener
    }

}