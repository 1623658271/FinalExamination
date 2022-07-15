package com.example.openeyes.bindingadapter

import android.graphics.Color
import android.text.TextUtils
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.example.openeyes.R
import com.squareup.picasso.Picasso

/**
 * description ： TODO:类的作用
 * author : lfy
 * email : 1623658271@qq.com
 * date : 2022/7/15 11:49
 */
object RVImageBindingAdapter {
    @BindingAdapter("itemImage")
    @JvmStatic
    fun setImage(imageView: ImageView,imageUrl:String){
        if(!TextUtils.isEmpty(imageUrl)){
            Picasso.get()
                .load(imageUrl)
                .placeholder(R.drawable.ic_resource_default)
                .error(R.drawable.ic_resource_error)
                .into(imageView)
        }else{
            imageView.setBackgroundColor(Color.DKGRAY)
        }
    }
}