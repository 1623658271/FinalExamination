package com.example.openeyes.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

/**
 * description ： 每日一图
 * author : lfy
 * email : 1623658271@qq.com
 * date : 2022/8/4 21:20
 */
class DailyImgBean(
    val images: List<Image>,
    val tooltips: Tooltips
) {
    data class Image(
        val bot: Int,
        val copyright: String,
        val copyrightlink: String,
        val drk: Int,
        val enddate: String,
        val fullstartdate: String,
        val hs: List<Any>,
        val hsh: String,
        val quiz: String,
        val startdate: String,
        val title: String,
        val top: Int,
        val url: String,
        val urlbase: String,
        val wp: Boolean
    )
    data class Tooltips(
        val loading: String,
        val next: String,
        val previous: String,
        val walle: String,
        val walls: String
    )
}