package com.example.openeyes.model

/**
 * description ： 个人信息数据类
 * author : lfy
 * email : 1623658271@qq.com
 * date : 2022/7/17 10:08
 */
data class PersonalModel(
    val uid:Int,
    val avatar: String,
    val cover: String,
    val description: String,
    val nickname: String,
)