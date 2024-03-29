package com.example.openeyes.model

import android.os.Parcelable

import kotlinx.android.parcel.Parcelize

/**
 * description ： 视频播放数据类
 * author : lfy
 * email : 1623658271@qq.com
 * date : 2022/7/16 15:59
 */

@Parcelize
data class VideoBean(
    var id:Int,
    var bigTitle: String,
    var smallTitle:String,
    var coverUrl: String,
    var playUrl: String,
    var description:String,
    var personalBean: PersonalBean?,
    var consumption:ConsumptionBean?
):Parcelable