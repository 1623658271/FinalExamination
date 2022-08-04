package com.example.openeyes.model

/**
 * description ： 专题深入页数据模板
 * author : lfy
 * email : 1623658271@qq.com
 * date : 2022/8/4 15:15
 */
data class SpecialPageBean(
    val headerUrl:String,
    val brief:String,
    val text:String,
    val videoBeanList: MutableList<VideoBean>
)