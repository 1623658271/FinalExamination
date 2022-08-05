package com.example.openeyes.model

/**
 * description ： TODO:类的作用
 * author : lfy
 * email : 1623658271@qq.com
 * date : 2022/8/5 16:52
 */
data class DynamicBean(
    val itemList: List<Item>,
    val count: Int, // 9
    val total: Int, // 0
    val nextPageUrl: String, // http://baobab.kaiyanapp.com/api/v6/tag/dynamics?start=10&num=10&id=1631
    val adExist: Boolean // false
) {
    data class Item(
        val type: String, // pictureFollowCard
        val `data`: Data,
        val trackingData: Any?, // null
        val tag: Any?, // null
        val id: Int, // 684465
        val adIndex: Int // -1
    ) {
        data class Data(
            val dataType: String, // FollowCard
            val header: Header,
            val content: Content,
            val adTrack: Any? // null
        ) {
            data class Header(
                val id: Int, // 684465
                val actionUrl: String, // eyepetizer://pgc/detail/301035153/?title=%E5%BC%80%E7%9C%BC%20Eyepetizer&userType=NORMAL&tabIndex=1
                val labelList: Any?, // null
                val icon: String, // http://img.kaiyanapp.com/52b3074dc3e217005ee86f9da9208289.png?imageMogr2/quality/60/format/jpg
                val iconType: String, // round
                val time: Long, // 1619168949000
                val showHateVideo: Boolean, // false
                val followType: String, // user
                val tagId: Int, // 0
                val tagName: Any?, // null
                val issuerName: String, // 开眼 Eyepetizer
                val topShow: Boolean // true
            )

            data class Content(
                val type: String, // ugcPicture
                val `data`: Data,
                val trackingData: Any?, // null
                val tag: Any?, // null
                val id: Int, // 0
                val adIndex: Int // -1
            ) {
                data class Data(
                    val dataType: String, // UgcPictureBean
                    val id: Int, // 684465
                    val title: String,
                    val description: String, // 复古港风的美，不仅仅是一种姿态，更是属于一个时代的自信表达。一首《处处吻》给我们带来了倾倒众生的港式美艳，今天的你「港」了吗？可以是 Red lip 复古红妆，也可以是你新烫的风情万种的波浪卷发，又或是碎花撞色的、随性大气的港风穿搭。快来「一日港风」的主题分享你的港味吧~
                    val library: String, // DEFAULT
                    val tags: List<Tag>,
                    val consumption: Consumption,
                    val resourceType: String, // ugc_picture
                    val uid: Int, // 301035153
                    val createTime: Long, // 1619168949000
                    val updateTime: Long, // 1652857282000
                    val collected: Boolean, // false
                    val reallyCollected: Boolean, // false
                    val owner: Owner,
                    val cover: Cover,
                    val selectedTime: Any?, // null
                    val checkStatus: String, // CHECKED
                    val area: String,
                    val city: String,
                    val longitude: Double, // 119.9498272
                    val latitude: Double, // 30.2579443
                    val ifMock: Boolean, // false
                    val validateStatus: String, // RECYCLED
                    val validateResult: String, // PASS
                    val width: Int, // 1380
                    val height: Int, // 1380
                    val addWatermark: Boolean, // true
                    val recentOnceReply: RecentOnceReply?,
                    val privateMessageActionUrl: Any?, // null
                    val source: String,
                    val url: String?, // http://img.kaiyanapp.com/0-c2c02fc37fa6b9c36c8e9b7f3ff60a94.jpeg?imageMogr2/quality/100!/format/jpg
                    val urls: List<String>?,
                    val status: Int, // 1
                    val releaseTime: Long, // 1619168949000
                    val urlsWithWatermark: List<String>?,
                    val playUrl: String?, // http://baobab.kaiyanapp.com/api/v1/playUrl?vid=309211&resourceType=ugc_video&editionType=default&source=aliyun&playUrlType=url_oss&udid=
                    val duration: Int?, // 6
                    val transId: Any?, // null
                    val type: String?, // COMMON
                    val validateTaskId: String?, // vi1P94L8phbYU4NJ@dkuJb4E-1wu3Q3
                    val playUrlWatermark: String? // http://baobab.kaiyanapp.com/api/v1/playUrl?vid=309211&resourceType=ugc_video&editionType=default&source=aliyun&playUrlType=play_url_watermark&udid=
                ) {
                    data class Tag(
                        val id: Int, // 1631
                        val name: String, // 一日港风
                        val actionUrl: String, // eyepetizer://tag/1631/?title=%E4%B8%80%E6%97%A5%E6%B8%AF%E9%A3%8E
                        val adTrack: Any?, // null
                        val desc: String, // 一吻便倾倒众生
                        val bgPicture: String, // http://img.kaiyanapp.com/662996ebf63a8f2d2540dec3085cf9f6.jpeg?imageMogr2/quality/60/format/jpg
                        val headerImage: String, // http://img.kaiyanapp.com/662996ebf63a8f2d2540dec3085cf9f6.jpeg?imageMogr2/quality/60/format/jpg
                        val tagRecType: String, // NORMAL
                        val childTagList: Any?, // null
                        val childTagIdList: Any?, // null
                        val haveReward: Boolean, // false
                        val ifNewest: Boolean, // false
                        val newestEndTime: Any?, // null
                        val communityIndex: Int, // 1
                        val alias: Any?, // null
                        val type: String, // DEFAULT
                        val tagStatus: String, // SHOW
                        val level: Int, // 0
                        val top: Int, // 0
                        val parentId: Int, // 0
                        val recWeight: Double, // 1.0
                        val ifShow: Boolean, // false
                        val relationType: Int // 0
                    )

                    data class Consumption(
                        val collectionCount: Int, // 229
                        val shareCount: Int, // 0
                        val replyCount: Int, // 3
                        val realCollectionCount: Int, // 6
                        val playCount: Int // 741
                    )

                    data class Owner(
                        val uid: Int, // 301035153
                        val nickname: String, // 开眼 Eyepetizer
                        val avatar: String, // http://img.kaiyanapp.com/52b3074dc3e217005ee86f9da9208289.png?imageMogr2/quality/60/format/jpg
                        val userType: String, // PGC
                        val ifPgc: Boolean, // true
                        val description: String?,
                        val area: Any?, // null
                        val gender: String?, // female
                        val registDate: Long, // 1505099839000
                        val releaseDate: Long, // 1638872515000
                        val cover: String?, // http://img.kaiyanapp.com/dc40005d83ccebcc920f393ef4fea9a4.png
                        val actionUrl: String, // eyepetizer://pgc/detail/301035153/?title=%E5%BC%80%E7%9C%BC%20Eyepetizer&userType=NORMAL&tabIndex=1
                        val followed: Boolean, // false
                        val limitVideoOpen: Boolean, // true
                        val library: String, // BLOCK
                        val birthday: Long?, // 996076800000
                        val country: String?,
                        val city: String?,
                        val university: String?,
                        val job: String?, // 学生
                        val expert: Boolean, // false
                        val username: String, // 18702830118
                        val uploadStatus: String, // NORMAL
                        val bannedDate: Any?, // null
                        val bannedDays: Int?, // 0
                        val tagIds: Any?, // null
                        val userMedalBeanList: Any? // null
                    )

                    data class Cover(
                        val feed: String, // http://img.kaiyanapp.com/0-c2c02fc37fa6b9c36c8e9b7f3ff60a94.jpeg?imageMogr2/quality/60/format/jpg
                        val detail: String, // http://img.kaiyanapp.com/0-c2c02fc37fa6b9c36c8e9b7f3ff60a94.jpeg?imageMogr2/quality/60/format/jpg
                        val blurred: Any?, // null
                        val sharing: Any?, // null
                        val homepage: Any? // null
                    )

                    data class RecentOnceReply(
                        val dataType: String, // SimpleHotReplyCard
                        val message: String, // 这个好熟悉
                        val nickname: String, // 张伟_zor
                        val actionUrl: String, // eyepetizer://pgc/detail/303267526/?title=%E5%BC%A0%E4%BC%9F_zor&userType=NORMAL&tabIndex=0
                        val contentType: Any? // null
                    )
                }
            }
        }
    }
}