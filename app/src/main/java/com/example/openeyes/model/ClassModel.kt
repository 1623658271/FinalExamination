package com.example.openeyes.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/**
 * description ：分类格式的数据类
 * author : lfy
 * email : 1623658271@qq.com
 * date : 2022/7/15 22:03
 */
@Parcelize
data class ClassModel(
    val id:Int,
    val icon:String,
    val title:String,
    val description:String,
    val actionUrl:String,
    val itemId:Int
):Parcelable