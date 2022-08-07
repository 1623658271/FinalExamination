package com.example.openeyes.model

/**
 * description ： 进入主题后的bean类
 * author : lfy
 * email : 1623658271@qq.com
 * date : 2022/8/6 14:30
 */
data class DynamicMsgBean(
    val itemList: List<Item>,
    val count: Int, // 9
    val total: Int, // 0
    val nextPageUrl: String, // http://baobab.kaiyanapp.com/api/v7/tag/childTab/6?start=10&num=10
    val adExist: Boolean // false
) {
    data class Item(
        val type: String, // briefCard
        val `data`: Data,
        val trackingData: Any?, // null
        val tag: Any?, // null
        val id: Int, // 0
        val adIndex: Int // -1
    ) {
        data class Data(
            val dataType: String, // TagBriefCard
            val id: Int, // 1604
            val icon: String, // http://img.kaiyanapp.com/0c5a833dd888f324ec20458babce2ed0.jpeg?imageMogr2/quality/60/format/jpg
            val iconType: String, // square
            val title: String, // #跟着电影去旅行
            val subTitle: Any?, // null
            val description: String?, // 打卡荧幕上的同款风景
            val actionUrl: String, // eyepetizer://tag/1604/?title=%E8%B7%9F%E7%9D%80%E7%94%B5%E5%BD%B1%E5%8E%BB%E6%97%85%E8%A1%8C
            val adTrack: Any?, // null
            val follow: Follow,
            val ifPgc: Boolean, // false
            val uid: Int, // 0
            val ifShowNotificationIcon: Boolean, // false
            val switchStatus: Boolean, // false
            val medalIcon: Boolean, // false
            val haveReward: Boolean, // false
            val ifNewest: Boolean, // false
            val newestEndTime: Any?, // null
            val expert: Boolean // false
        ) {
            data class Follow(
                val itemType: String, // tag
                val itemId: Int, // 1604
                val followed: Boolean // false
            )
        }
    }
}