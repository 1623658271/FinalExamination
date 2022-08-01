package com.example.openeyes.bean

/**
 * description ： 推荐
 * author : lfy
 * email : 1623658271@qq.com
 * date : 2022/7/22 17:16
 */
data class SocialRecommendBean(
    val itemList: List<Item>,
    val count: Int, // 10
    val total: Int, // 0
    val nextPageUrl: String, // http://baobab.kaiyanapp.com/api/v7/community/tab/rec?startScore=1656249888000&pageCount=2
    val adExist: Boolean // false
) {
    data class Item(
        val type: String, // communityColumnsCard
        val `data`: Data,
        val trackingData: Any?, // null
        val tag: Any?, // null
        val id: Int, // 819424
        val adIndex: Int // -1
    ) {
        data class Data(
            val dataType: String, // FollowCard
            val header: Header,
            val content: Content,
            val adTrack: Any?, // null
            val itemList:List<Item>?,
            val subTitle:String?,
            val title:String?,
            val actionUrl:String?,
            val bgPicture:String?
        ) {
            data class Header(
                val id: Int, // 819424
                val actionUrl: String, // eyepetizer://pgc/detail/301331120/?title=hi&userType=NORMAL&tabIndex=0
                val labelList: Any?, // null
                val icon: String, // http://img.kaiyanapp.com/d87b11a4d65dd71bd33f028ce1e9b353.jpeg?imageMogr2/quality/60/format/jpg
                val iconType: String, // round
                val time: Long, // 1656487755000
                val showHateVideo: Boolean, // false
                val followType: String, // user
                val tagId: Int, // 0
                val tagName: Any?, // null
                val issuerName: String, // hi
                val topShow: Boolean // false
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
                    val id: Int, // 819424
                    val title: String,
                    val description: String, // 一直珍藏着高中时期和朋友往来的书信，最原始的沟通方式却最饱含着感情，珍藏着我酸甜苦辣的青春岁月。
                    val library: String, // DEFAULT
                    val tags: List<Tag>?,
                    val consumption: ConsumptionBean,
                    val resourceType: String, // ugc_picture
                    val uid: Int, // 301331120
                    val createTime: Long, // 1656487755000
                    val updateTime: Long, // 1656488562000
                    val collected: Boolean, // false
                    val reallyCollected: Boolean, // false
                    val owner: Owner,
                    val cover: Cover,
                    val selectedTime: Long?, // 1656306009000
                    val checkStatus: String, // CHECKED
                    val area: String,
                    val city: String,
                    val longitude: Double, // 0E-7
                    val latitude: Double, // 0E-7
                    val ifMock: Boolean, // false
                    val validateStatus: String, // SUBMIT
                    val validateResult: String, // DEFAULT
                    val width: Int, // 1284
                    val height: Int, // 934
                    val addWatermark: Boolean, // true
                    val recentOnceReply: RecentOnceReply?,
                    val privateMessageActionUrl: Any?, // null
                    val source: String,
                    val url: String?, // http://img.kaiyanapp.com/0-792bcf7809349c680e73ffd4c738b515.jpeg?imageMogr2/quality/100!/format/jpg
                    val urls: List<String>?,
                    val status: Any, // 1
                    val releaseTime: Long, // 1656487755000
                    val urlsWithWatermark: List<String>?,
                    val playUrl: String?, // http://baobab.kaiyanapp.com/api/v1/playUrl?vid=308070&resourceType=ugc_video&editionType=default&source=aliyun&playUrlType=url_oss&udid=
                    val duration: Int?, // 40
                    val transId: Any?, // null
                    val type: String?, // COMMON
                    val validateTaskId: String?, // vi48zjbFCZHEh4vV0JXaM179-1wq2Ue
                    val playUrlWatermark: String? // http://baobab.kaiyanapp.com/api/v1/playUrl?vid=308070&resourceType=ugc_video&editionType=default&source=aliyun&playUrlType=play_url_watermark&udid=
                ) {
                    data class Tag(
                        val id: Int, // 930
                        val name: String, // 摄影师日常
                        val actionUrl: String, // eyepetizer://tag/930/?title=%E6%91%84%E5%BD%B1%E5%B8%88%E6%97%A5%E5%B8%B8
                        val adTrack: Any?, // null
                        val desc: String, // 透过镜头，我看到了更加真实的世界
                        val bgPicture: String, // http://img.kaiyanapp.com/586d2cdc4a807eb84bed71fc523ed24b.jpeg?imageMogr2/quality/60/format/jpg
                        val headerImage: String, // http://img.kaiyanapp.com/586d2cdc4a807eb84bed71fc523ed24b.jpeg?imageMogr2/quality/60/format/jpg
                        val tagRecType: String, // NORMAL
                        val childTagList: Any?, // null
                        val childTagIdList: Any?, // null
                        val haveReward: Boolean, // false
                        val ifNewest: Boolean, // false
                        val newestEndTime: Any?, // null
                        val communityIndex: Int // 12
                    )

                    data class Owner(
                        val uid: Int, // 301331120
                        val nickname: String, // hi
                        val avatar: String, // http://img.kaiyanapp.com/d87b11a4d65dd71bd33f028ce1e9b353.jpeg?imageMogr2/quality/60/format/jpg
                        val userType: String, // NORMAL
                        val ifPgc: Boolean, // false
                        val description: String?, // 独立摄影师/艺术记录表达者公众号：lridium小红书/微博：Qiyii_
                        val area: Any?, // null
                        val gender: String?, // female
                        val registDate: Long, // 1453332245000
                        val releaseDate: Long, // 1658404362000
                        val cover: String?, // http://img.kaiyanapp.com/d754eacc78b820eb4eb31af689606e66.jpeg
                        val actionUrl: String, // eyepetizer://pgc/detail/301331120/?title=hi&userType=NORMAL&tabIndex=0
                        val followed: Boolean, // false
                        val limitVideoOpen: Boolean, // false
                        val library: String, // BLOCK
                        val birthday: Long?, // 879264000000
                        val country: String?,
                        val city: String?,
                        val university: String?,
                        val job: String?, // 摄影师
                        val expert: Boolean // false
                    )

                    data class Cover(
                        val feed: String, // http://img.kaiyanapp.com/0-792bcf7809349c680e73ffd4c738b515.jpeg?imageMogr2/quality/60/format/jpg
                        val detail: String, // http://img.kaiyanapp.com/0-792bcf7809349c680e73ffd4c738b515.jpeg?imageMogr2/quality/60/format/jpg
                        val blurred: Any?, // null
                        val sharing: Any?, // null
                        val homepage: Any? // null
                    )

                    data class RecentOnceReply(
                        val dataType: String, // SimpleHotReplyCard
                        val message: String, // 求BGM
                        val nickname: String, // 星柯
                        val actionUrl: String, // eyepetizer://pgc/detail/302295807/?title=%E6%98%9F%E6%9F%AF&userType=NORMAL&tabIndex=0
                        val contentType: Any? // null
                    )
                }
            }
        }
    }
}