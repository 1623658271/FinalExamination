package com.example.openeyes.model

/**
 * description ： 专题深入数据
 * author : lfy
 * email : 1623658271@qq.com
 * date : 2022/8/4 15:54
 */
data class SpecialInBean(
    val id: Int, // 692
    val headerImage: String, // http://img.kaiyanapp.com/49ecc7a4212836323e267b0dcc09a860.jpeg?imageMogr2/quality/60/format/jpg
    val brief: String, // 夏日重现！动画里吹的风都是热热的
    val text: String, // 夏天来临，开眼为你挑选了这 10 条夏日动画，感受那蝉鸣与海边的夏日专属清凉。
    val shareLink: String, // http://www.eyepetizer.net/videos_article.html?nid=692&shareable=true
    val itemList: List<Item>,
    val count: Int, // 10
    val adTrack: Any? // null
) {
    data class Item(
        val type: String, // autoPlayFollowCard
        val `data`: Data,
        val trackingData: Any?, // null
        val tag: Any?, // null
        val id: Int, // 286413
        val adIndex: Int // -1
    ) {
        data class Data(
            val dataType: String, // FollowCard
            val header: Header,
            val content: Content,
            val adTrack: List<Any>
        ) {
            data class Header(
                val id: Int, // 286413
                val actionUrl: String, // eyepetizer://pgc/detail/5329/?title=%E7%89%B9%E5%88%AB%C2%B7%E7%89%88%E6%9C%AC%20Special%20Edition&userType=PGC&tabIndex=1
                val labelList: Any?, // null
                val icon: String, // http://img.kaiyanapp.com/6d8409b09b145ade2650168af7c64398.jpeg?imageMogr2/quality/60/format/jpg
                val iconType: String, // round
                val time: Long, // 1635382807000
                val showHateVideo: Boolean, // false
                val followType: String, // author
                val tagId: Int, // 0
                val tagName: Any?, // null
                val issuerName: String, // 特别·版本 Special Edition
                val topShow: Boolean // false
            )

            data class Content(
                val type: String, // video
                val `data`: Data,
                val trackingData: Any?, // null
                val tag: Any?, // null
                val id: Int, // 0
                val adIndex: Int // -1
            ) {
                data class Data(
                    val dataType: String, // VideoBeanForClient
                    val id: Int, // 286413
                    val title: String, // 吉卜力风治愈动画，温柔夏天的离别
                    val description: String, // 在夏末的阳光下，一个年轻人准备离开他的家。短片是法国 Gobelins 学校学生的毕业作品，其灵感主要来自日本艺术家们——如松本太阳、谷牛町六郎和汤浅正明等。导演：Tamerlan BEKMURZAYEV，Antoine CARRE，Rodrigo GOULÃO DE SOUSA，Alexandra PETIT，Martin ROBIC。From Gobelins
                    val library: String, // DAILY
                    val tags: List<Tag>,
                    val consumption: ConsumptionBean,
                    val resourceType: String, // video
                    val slogan: String?,
                    val provider: Provider,
                    val category: String, // 动画
                    val author: Author,
                    val cover: Cover,
                    val playUrl: String, // http://baobab.kaiyanapp.com/api/v1/playUrl?vid=286413&resourceType=video&editionType=default&source=aliyun&playUrlType=url_oss&udid=
                    val thumbPlayUrl: String?,
                    val duration: Int, // 517
                    val webUrl: WebUrl,
                    val releaseTime: Long, // 1635382807000
                    val playInfo: List<PlayInfo>,
                    val campaign: Any?, // null
                    val waterMarks: Any?, // null
                    val ad: Boolean, // false
                    val adTrack: List<Any>,
                    val type: String, // NORMAL
                    val titlePgc: String?,
                    val descriptionPgc: String?,
                    val remark: String?,
                    val ifLimitVideo: Boolean, // false
                    val searchWeight: Int, // 0
                    val brandWebsiteInfo: Any?, // null
                    val videoPosterBean: VideoPosterBean?,
                    val idx: Int, // 0
                    val shareAdTrack: Any?, // null
                    val favoriteAdTrack: Any?, // null
                    val webAdTrack: Any?, // null
                    val date: Long, // 1635382807000
                    val promotion: Any?, // null
                    val label: Any?, // null
                    val labelList: List<Any>,
                    val descriptionEditor: String, // 在夏末的阳光下，一个年轻人准备离开他的家。短片是法国 Gobelins 学校学生的毕业作品，其灵感主要来自日本艺术家们——如松本太阳、谷牛町六郎和汤浅正明等。导演：Tamerlan BEKMURZAYEV，Antoine CARRE，Rodrigo GOULÃO DE SOUSA，Alexandra PETIT，Martin ROBIC。From Gobelins
                    val collected: Boolean, // false
                    val reallyCollected: Boolean, // false
                    val played: Boolean, // false
                    val subtitles: List<Any>,
                    val lastViewTime: Any?, // null
                    val playlists: Any?, // null
                    val src: Any?, // null
                    val recallSource: Any?, // null
                    val recall_source: Any? // null
                ) {
                    data class Tag(
                        val id: Int, // 14
                        val name: String, // 动画梦工厂
                        val actionUrl: String, // eyepetizer://tag/14/?title=%E5%8A%A8%E7%94%BB%E6%A2%A6%E5%B7%A5%E5%8E%82
                        val adTrack: Any?, // null
                        val desc: String?, // 有趣的人永远不缺童心
                        val bgPicture: String, // http://img.kaiyanapp.com/afb9e7d7f061d10ade5ebcb524dc8679.jpeg?imageMogr2/quality/60/format/jpg
                        val headerImage: String, // http://img.kaiyanapp.com/f9eae3e0321fa1e99a7b38641b5536a2.jpeg?imageMogr2/quality/60/format/jpg
                        val tagRecType: String, // IMPORTANT
                        val childTagList: Any?, // null
                        val childTagIdList: Any?, // null
                        val haveReward: Boolean, // false
                        val ifNewest: Boolean, // false
                        val newestEndTime: Any?, // null
                        val communityIndex: Int // 0
                    )

                    data class Provider(
                        val name: String, // 投稿
                        val alias: String, // PGC2
                        val icon: String
                    )

                    data class Author(
                        val id: Int, // 5329
                        val icon: String, // http://img.kaiyanapp.com/6d8409b09b145ade2650168af7c64398.jpeg?imageMogr2/quality/60/format/jpg
                        val name: String, // 特别·版本 Special Edition
                        val description: String, // 全球最有趣、最劲爆的超新鲜短视频收纳站！
                        val link: String,
                        val latestReleaseTime: Long, // 1659402023000
                        val videoNum: Int, // 148
                        val adTrack: Any?, // null
                        val follow: Follow,
                        val shield: Shield,
                        val approvedNotReadyVideoCount: Int, // 0
                        val ifPgc: Boolean, // true
                        val recSort: Int, // 0
                        val expert: Boolean // false
                    ) {
                        data class Follow(
                            val itemType: String, // author
                            val itemId: Int, // 5329
                            val followed: Boolean // false
                        )

                        data class Shield(
                            val itemType: String, // author
                            val itemId: Int, // 5329
                            val shielded: Boolean // false
                        )
                    }

                    data class Cover(
                        val feed: String, // http://img.kaiyanapp.com/0392d92cf38a2b439a794537fe80d295.png?imageMogr2/quality/60/format/jpg
                        val detail: String, // http://img.kaiyanapp.com/0392d92cf38a2b439a794537fe80d295.png?imageMogr2/quality/60/format/jpg
                        val blurred: String, // http://img.kaiyanapp.com/819460d46f2fe830621a505543453247.png?imageMogr2/quality/60/format/jpg
                        val sharing: Any?, // null
                        val homepage: String? // http://img.kaiyanapp.com/0392d92cf38a2b439a794537fe80d295.png?imageView2/1/w/720/h/560/format/jpg/q/75|watermark/1/image/aHR0cDovL2ltZy5rYWl5YW5hcHAuY29tL2JsYWNrXzMwLnBuZw==/dissolve/100/gravity/Center/dx/0/dy/0|imageslim
                    )

                    data class WebUrl(
                        val raw: String, // http://www.eyepetizer.net/detail.html?vid=286413
                        val forWeibo: String // https://m.eyepetizer.net/u1/video-detail?video_id=286413&resource_type=video&utm_campaign=routine&utm_medium=share&utm_source=weibo&uid=0
                    )

                    data class PlayInfo(
                        val height: Int, // 480
                        val width: Int, // 854
                        val urlList: List<Url>,
                        val name: String, // 标清
                        val type: String, // normal
                        val url: String // http://baobab.kaiyanapp.com/api/v1/playUrl?vid=286413&resourceType=video&editionType=normal&source=aliyun&playUrlType=url_oss&udid=
                    ) {
                        data class Url(
                            val name: String, // aliyun
                            val url: String, // http://baobab.kaiyanapp.com/api/v1/playUrl?vid=286413&resourceType=video&editionType=normal&source=aliyun&playUrlType=url_oss&udid=
                            val size: Int // 36128440
                        )
                    }

                    data class VideoPosterBean(
                        val scale: Double, // 0.725
                        val url: String, // http://eyepetizer-videos.oss-cn-beijing.aliyuncs.com/video_poster_share/1eae66c3f4c7712c5124a9ab9913182e.mp4
                        val fileSizeStr: String // 2.32MB
                    )
                }
            }
        }
    }
}