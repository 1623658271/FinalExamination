package com.example.openeyes.bean

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/**
 * description ： 照片分享者的数据类
 * author : lfy
 * email : 1623658271@qq.com
 * date : 2022/7/22 18:42
 */
@Parcelize
data class PicsBean(
    val description:String,
    val coverUrl:String,
    val picUrls:MutableList<String>,
    val consumptionBean: ConsumptionBean?,
    val personalBean: PersonalBean?
):Parcelable