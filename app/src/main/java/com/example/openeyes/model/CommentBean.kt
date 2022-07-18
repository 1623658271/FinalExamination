package com.example.openeyes.model

/**
 * description ： TODO:类的作用
 * author : lfy
 * email : 1623658271@qq.com
 * date : 2022/7/18 09:36
 */
data class CommentBean(
    val content:String,
    val likeCount:Int,
    val personalModel: PersonalModel
)