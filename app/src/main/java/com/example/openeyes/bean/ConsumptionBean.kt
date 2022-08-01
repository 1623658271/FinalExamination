package com.example.openeyes.bean

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/**
 * description ： 记录分享数，回复数等信息的数据类
 * author : lfy
 * email : 1623658271@qq.com
 * date : 2022/7/22 18:21
 */
@Parcelize
data class ConsumptionBean(
    val collectionCount: Int,
    val shareCount: Int,
    val replyCount: Int,
    val realCollectionCount: Int
):Parcelable