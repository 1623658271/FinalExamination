package com.example.openeyes.bindingadapter

import android.graphics.Color
import android.text.TextUtils
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.example.openeyes.activity.MyApplication
import com.example.openeyes.R

import de.hdodenhof.circleimageview.CircleImageView

/**
 * description ： 自定义DataBindingAdapter，主要是图片的显示
 * author : lfy
 * email : 1623658271@qq.com
 * date : 2022/7/15 11:49
 */
object ImageBindingAdapter {
    @BindingAdapter("ImageView")
    @JvmStatic
    fun setImage(imageView: ImageView,imageUrl:String){
        if(!TextUtils.isEmpty(imageUrl)){
            Glide.with(MyApplication.context!!)
                .load(imageUrl)
                .placeholder(R.drawable.ic_resource_default)
                .error(R.drawable.ic_resource_error)
                .into(imageView)
        }else{
            imageView.setBackgroundColor(Color.DKGRAY)
        }
    }

    @BindingAdapter("CircleImageView")
    @JvmStatic
    fun setCircleImage(circleImageView: CircleImageView,imageUrl: String){
        if(!TextUtils.isEmpty(imageUrl)){
            Glide.with(MyApplication.context!!)
                .load(imageUrl)
                .placeholder(R.drawable.ic_resource_default)
                .error(R.drawable.ic_resource_error)
                .into(circleImageView)
        }else{
            circleImageView.setBackgroundColor(Color.DKGRAY)
        }
    }
}