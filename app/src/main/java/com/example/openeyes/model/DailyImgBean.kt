package com.example.openeyes.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

/**
 * description ： TODO:类的作用
 * author : lfy
 * email : 1623658271@qq.com
 * date : 2022/8/4 21:20
 */
class DailyImgBean(
    @SerializedName("images")
    val images: List<Image>,
    @SerializedName("tooltips")
    val tooltips: Tooltips
) : Serializable {
    data class Image(
        @SerializedName("bot")
        val bot: Int,
        @SerializedName("copyright")
        val copyright: String,
        @SerializedName("copyrightlink")
        val copyrightlink: String,
        @SerializedName("drk")
        val drk: Int,
        @SerializedName("enddate")
        val enddate: String,
        @SerializedName("fullstartdate")
        val fullstartdate: String,
        @SerializedName("hs")
        val hs: List<Any>,
        @SerializedName("hsh")
        val hsh: String,
        @SerializedName("quiz")
        val quiz: String,
        @SerializedName("startdate")
        val startdate: String,
        @SerializedName("title")
        val title: String,
        @SerializedName("top")
        val top: Int,
        @SerializedName("url")
        val url: String,
        @SerializedName("urlbase")
        val urlbase: String,
        @SerializedName("wp")
        val wp: Boolean
    ) : java.io.Serializable

    data class Tooltips(
        @SerializedName("loading")
        val loading: String,
        @SerializedName("next")
        val next: String,
        @SerializedName("previous")
        val previous: String,
        @SerializedName("walle")
        val walle: String,
        @SerializedName("walls")
        val walls: String
    ) : java.io.Serializable
}