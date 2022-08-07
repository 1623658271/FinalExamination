package com.example.openeyes.model

/**
 * description ： 主题单个深入的bean类
 * author : lfy
 * email : 1623658271@qq.com
 * date : 2022/8/5 16:52
 */
data class DynamicInBean(
    val itemList: List<Item>,
    val count: Int, // 9
    val total: Int, // 0
    val nextPageUrl: String, // http://baobab.kaiyanapp.com/api/v6/tag/dynamics?start=10&num=10&id=874
    val adExist: Boolean // false
) {
    data class Item(
        val type: String, // pictureFollowCard
        val `data`: Data,
        val trackingData: Any?, // null
        val tag: Any?, // null
        val id: Int, // 811648
        val adIndex: Int // -1
    ) {
        data class Data(
            val dataType: String, // FollowCard
            val header: Header,
            val content: Content,
            val adTrack: List<Any>?
        ) {
            data class Header(
                val id: Int, // 811648
                val actionUrl: String, // eyepetizer://pgc/detail/303642346/?title=lImIna&userType=NORMAL&tabIndex=0
                val labelList: Any?, // null
                val icon: String, // http://img.kaiyanapp.com/9ca56714ab976b4b9c323461db2b0927.jpeg?imageMogr2/quality/60/format/jpg
                val iconType: String, // round
                val time: Long, // 1654259788000
                val showHateVideo: Boolean, // false
                val followType: String, // user
                val tagId: Int, // 0
                val tagName: Any?, // null
                val issuerName: String, // lImIna
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
                    val id: Int, // 811648
                    val title: String,
                    val description: String, // 来杯小熊奶茶吧～
                    val library: String, // NOT_RECOMMEND
                    val tags: List<Tag>,
                    val consumption: Consumption,
                    val resourceType: String, // ugc_picture
                    val uid: Int?, // 303642346
                    val createTime: Long, // 1654259788000
                    val updateTime: Long, // 1654260885000
                    val collected: Boolean, // false
                    val reallyCollected: Boolean, // false
                    val owner: Owner?,
                    val cover: Cover,
                    val selectedTime: Any?, // null
                    val checkStatus: String?, // UNCHECKED
                    val area: String?,
                    val city: String?,
                    val longitude: Double?, // 114.0551980
                    val latitude: Double?, // 22.5209220
                    val ifMock: Boolean?, // false
                    val validateStatus: String?, // SUBMIT
                    val validateResult: String?, // DEFAULT
                    val width: Int?, // 3024
                    val height: Int?, // 4032
                    val addWatermark: Boolean?, // true
                    val recentOnceReply: RecentOnceReply?,
                    val privateMessageActionUrl: Any?, // null
                    val source: String?,
                    val url: String?, // http://img.kaiyanapp.com/0-0f46735ce6e167b5500b00aee964d6a4.jpeg?imageMogr2/quality/100!/format/jpg
                    val urls: List<String>?,
                    val status: Any, // 1
                    val releaseTime: Long, // 1654259788000
                    val urlsWithWatermark: List<String>?,
                    val slogan: Any?, // null
                    val provider: Provider?,
                    val category: String?, // 生活
                    val author: Author?,
                    val playUrl: String?, // http://baobab.kaiyanapp.com/api/v1/playUrl?vid=288549&resourceType=video&editionType=default&source=aliyun&playUrlType=url_oss&udid=
                    val thumbPlayUrl: Any?, // null
                    val duration: Int?, // 960
                    val webUrl: WebUrl?,
                    val playInfo: List<PlayInfo>?,
                    val campaign: Any?, // null
                    val waterMarks: Any?, // null
                    val ad: Boolean?, // false
                    val adTrack: List<Any>?,
                    val type: String?, // NORMAL
                    val titlePgc: String?, // 花式创意！50 个烹饪鸡蛋的方法
                    val descriptionPgc: String?, // 如果你喜欢吃鸡蛋，那你一定不能错过这些有趣的创意！
                    val remark: Any?, // null
                    val ifLimitVideo: Boolean?, // false
                    val searchWeight: Int?, // 0
                    val brandWebsiteInfo: Any?, // null
                    val videoPosterBean: VideoPosterBean?,
                    val idx: Int?, // 0
                    val shareAdTrack: Any?, // null
                    val favoriteAdTrack: Any?, // null
                    val webAdTrack: Any?, // null
                    val date: Long?, // 1635912326000
                    val promotion: Any?, // null
                    val label: Any?, // null
                    val labelList: List<Any>?,
                    val descriptionEditor: String?,
                    val played: Boolean?, // false
                    val subtitles: List<Any>?,
                    val lastViewTime: Any?, // null
                    val playlists: Any?, // null
                    val src: Any?, // null
                    val recallSource: Any?, // null
                    val recall_source: Any?, // null
                    val candidateId: Int?, // 0
                    val sourceUrl: String?, // https://www.youtube.com/watch?v=nClA4aFDn2U
                    val tailTimePoint: Int?, // 0
                    val infoStatus: String?, // NOT_CONFIRMED
                    val showLabel: Boolean?, // false
                    val premiereDate: Any?, // null
                    val areaSet: List<Any>?,
                    val subtitleStatus: String?, // CRAWLER_NONE
                    val translateStatus: String? // NO_NEED
                ) {
                    data class Tag(
                        val id: Int, // 874
                        val name: String, // 晚餐
                        val actionUrl: String, // eyepetizer://tag/874/?title=%E6%99%9A%E9%A4%90
                        val adTrack: Any?, // null
                        val desc: String?, // 在开眼分享你的厨艺
                        val bgPicture: String, // http://img.kaiyanapp.com/9b2de38fec73b2085c2d60aaf12ec3d0.jpeg?imageMogr2/quality/60/format/jpg
                        val headerImage: String, // http://img.kaiyanapp.com/9b2de38fec73b2085c2d60aaf12ec3d0.jpeg?imageMogr2/quality/60/format/jpg
                        val tagRecType: String, // NORMAL
                        val childTagList: Any?, // null
                        val childTagIdList: Any?, // null
                        val haveReward: Boolean, // false
                        val ifNewest: Boolean, // false
                        val newestEndTime: Any?, // null
                        val communityIndex: Int, // 0
                        val alias: Any?, // null
                        val type: String?, // DEFAULT
                        val tagStatus: String, // HIDDEN
                        val level: Int, // 0
                        val top: Int, // 0
                        val parentId: Int, // 0
                        val recWeight: Double, // 1.0
                        val ifShow: Boolean, // false
                        val relationType: Int // 0
                    )

                    data class Consumption(
                        val collectionCount: Int, // 39
                        val shareCount: Int, // 0
                        val replyCount: Int, // 0
                        val realCollectionCount: Int, // 0
                        val playCount: Int // 1
                    )

                    data class Owner(
                        val uid: Int, // 303642346
                        val nickname: String, // lImIna
                        val avatar: String, // http://img.kaiyanapp.com/9ca56714ab976b4b9c323461db2b0927.jpeg?imageMogr2/quality/60/format/jpg
                        val userType: String, // NORMAL
                        val ifPgc: Boolean, // false
                        val description: String, // 喜欢摄影
                        val area: Any?, // null
                        val gender: String?, // female
                        val registDate: Long, // 1582110535000
                        val releaseDate: Long, // 1656518984000
                        val cover: String, // http://img.kaiyanapp.com/266eb6b9b1f2ac80e93b8e4e75887287.jpeg?imageMogr2/quality/60/format/jpg
                        val actionUrl: String, // eyepetizer://pgc/detail/303642346/?title=lImIna&userType=NORMAL&tabIndex=0
                        val followed: Boolean, // false
                        val limitVideoOpen: Boolean, // false
                        val library: String, // BLOCK
                        val birthday: Long?, // 0
                        val country: String?,
                        val city: String?,
                        val university: String?,
                        val job: String?,
                        val expert: Boolean, // false
                        val username: String, // 15986729755
                        val uploadStatus: String, // NORMAL
                        val bannedDate: Any?, // null
                        val bannedDays: Int?, // 0
                        val tagIds: Any?, // null
                        val userMedalBeanList: Any? // null
                    )

                    data class Cover(
                        val feed: String, // http://img.kaiyanapp.com/0-0f46735ce6e167b5500b00aee964d6a4.jpeg?imageMogr2/quality/60/format/jpg
                        val detail: String, // http://img.kaiyanapp.com/0-0f46735ce6e167b5500b00aee964d6a4.jpeg?imageMogr2/quality/60/format/jpg
                        val blurred: String?, // http://img.kaiyanapp.com/c59bd8c1bec118d77505fd8ccbf9d960.jpeg?imageMogr2/quality/60/format/jpg
                        val sharing: Any?, // null
                        val homepage: Any? // null
                    )

                    data class RecentOnceReply(
                        val dataType: String, // SimpleHotReplyCard
                        val message: String, // 热狗别吃太多，上火
                        val nickname: String, // 闹市隐者
                        val actionUrl: String, // eyepetizer://pgc/detail/301811746/?title=%E9%97%B9%E5%B8%82%E9%9A%90%E8%80%85&userType=NORMAL&tabIndex=0
                        val contentType: Any? // null
                    )

                    data class Provider(
                        val name: String, // 定制来源
                        val alias: String, // CustomSrc
                        val icon: String,
                        val id: Int, // 30
                        val status: String // SHOW
                    )

                    data class Author(
                        val id: Int, // 504
                        val icon: String, // http://img.kaiyanapp.com/27de87930b4baab545271ae0f2faf3f8.jpeg?imageMogr2/quality/60/format/jpg
                        val name: String, // 5-Minute Crafts
                        val description: String, // 教你有趣的 DIY 小技巧，体验亲自动手的无尽乐趣
                        val link: String,
                        val latestReleaseTime: Long, // 1641184607000
                        val videoNum: Int, // 187
                        val adTrack: Any?, // null
                        val follow: Follow,
                        val shield: Shield,
                        val approvedNotReadyVideoCount: Int, // 0
                        val ifPgc: Boolean, // true
                        val recSort: Int, // 0
                        val expert: Boolean, // false
                        val cover: Any?, // null
                        val authorType: String, // MOCK
                        val authorStatus: String, // ONLINE
                        val library: String, // DEFAULT
                        val pendingVideoCount: Int, // 0
                        val amplifiedLevelId: Any? // null
                    ) {
                        data class Follow(
                            val itemType: String, // author
                            val itemId: Int, // 504
                            val followed: Boolean // false
                        )

                        data class Shield(
                            val itemType: String, // author
                            val itemId: Int, // 504
                            val shielded: Boolean // false
                        )
                    }

                    data class WebUrl(
                        val raw: String, // http://www.eyepetizer.net/detail.html?vid=288549
                        val forWeibo: String // https://m.eyepetizer.net/u1/video-detail?video_id=288549&resource_type=video&utm_campaign=routine&utm_medium=share&utm_source=weibo&uid=0
                    )

                    data class PlayInfo(
                        val height: Int, // 720
                        val width: Int, // 720
                        val urlList: List<Url>,
                        val id: Int, // 707126
                        val videoId: Int, // 288549
                        val name: String, // 标清
                        val type: String, // normal
                        val url: String, // http://baobab.kaiyanapp.com/api/v1/playUrl?vid=288549&resourceType=video&editionType=normal&source=aliyun&playUrlType=url_oss&udid=
                        val duration: Int, // 960
                        val bitRate: Int, // 1192552
                        val dimension: String, // 720x720
                        val size: Int // 143124474
                    ) {
                        data class Url(
                            val name: String, // aliyun
                            val url: String, // http://baobab.kaiyanapp.com/api/v1/playUrl?vid=288549&resourceType=video&editionType=normal&source=aliyun&playUrlType=url_oss&udid=
                            val size: Int // 143124474
                        )
                    }

                    data class VideoPosterBean(
                        val scale: Double, // 0.725
                        val url: String, // http://eyepetizer-videos.oss-cn-beijing.aliyuncs.com/video_poster_share/f96f2b189dd6ad81c84e872a1ec30050.mp4
                        val fileSizeStr: String // 4.25MB
                    )
                }
            }
        }
    }
}