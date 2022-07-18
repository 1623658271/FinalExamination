package com.example.openeyes.model

/**
 * description ： TODO:类的作用
 * author : lfy
 * email : 1623658271@qq.com
 * date : 2022/7/16 15:59
 */
data class VideoBean(
    var id:Int,
    var bigTitle: String,
    var smallTitle:String,
    var coverUrl: String,
    var playUrl: String,
    var description:String,
    var personalModel: PersonalModel?
)