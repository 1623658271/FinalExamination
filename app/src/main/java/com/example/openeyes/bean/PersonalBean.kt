package com.example.openeyes.bean

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/**
 * description ： 个人信息数据类
 * author : lfy
 * email : 1623658271@qq.com
 * date : 2022/7/17 10:08
 */
@Parcelize
data class PersonalBean(
    val uid:Int,
    val avatar: String,
    val cover: String,
    val description: String,
    val nickname: String,
    val city:String,
    val job:String
):Parcelable