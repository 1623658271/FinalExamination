package com.example.openeyes.bean

/**
 * description ： TODO:类的作用
 * author : lfy
 * email : 1623658271@qq.com
 * date : 2022/8/1 21:20
 */
data class HotVideoBean(
    val itemList: List<Item>,
    val count: Int, // 10
    val total: Int, // 0
    val nextPageUrl: Any?, // null
    val adExist: Boolean // false
) {
    data class Item(
        val type: String, // video
        val `data`: Data,
        val trackingData: Any?, // null
        val tag: Any?, // null
        val id: Int, // 0
        val adIndex: Int // -1
    ) {
        data class Data(
            val dataType: String, // VideoBeanForClient
            val id: Int, // 312277
            val title: String, // 倒车倒不好，都要被「马」嘲笑
            val description: String, // 如此魔性的笑声只为告诉你：如果停车技术太烂，马都是会笑话你。这是一则来自大众汽车的广告，它的拖车辅助操纵系统 Trailer Assist 可以帮助更加精准快速的进行倒车入库的操作。From Alan Day Group
            val library: String, // DAILY
            val tags: List<Tag>,
            val consumption: ConsumptionBean,
            val resourceType: String, // video
            val slogan: String?,
            val provider: Provider,
            val category: String, // 广告
            val author: Author,
            val cover: Cover,
            val playUrl: String, // http://baobab.kaiyanapp.com/api/v1/playUrl?vid=312277&resourceType=video&editionType=default&source=aliyun&playUrlType=url_oss&udid=
            val thumbPlayUrl: String?,
            val duration: Int, // 60
            val webUrl: WebUrl,
            val releaseTime: Long, // 1658538000000
            val playInfo: List<PlayInfo>,
            val campaign: Any?, // null
            val waterMarks: Any?, // null
            val ad: Boolean, // false
            val adTrack: List<Any>,
            val type: String, // NORMAL
            val titlePgc: String, // 广告 马
            val descriptionPgc: String,
            val remark: String?, // https://www.xinpianchang.com/a12012752?from=ArticleList
            val ifLimitVideo: Boolean, // false
            val searchWeight: Int, // 0
            val brandWebsiteInfo: Any?, // null
            val videoPosterBean: Any?, // null
            val idx: Int, // 0
            val shareAdTrack: Any?, // null
            val favoriteAdTrack: Any?, // null
            val webAdTrack: Any?, // null
            val date: Long, // 1658538000000
            val promotion: Any?, // null
            val label: Any?, // null
            val labelList: List<Any>,
            val descriptionEditor: String, // 如此魔性的笑声只为告诉你：如果停车技术太烂，马都是会笑话你。这是一则来自大众汽车的广告，它的拖车辅助操纵系统 Trailer Assist 可以帮助更加精准快速的进行倒车入库的操作。From Alan Day Group
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
                val id: Int, // 748
                val name: String, // 这些广告超有梗
                val actionUrl: String, // eyepetizer://tag/748/?title=%E8%BF%99%E4%BA%9B%E5%B9%BF%E5%91%8A%E8%B6%85%E6%9C%89%E6%A2%97
                val adTrack: Any?, // null
                val desc: String?, // 为广告人的精彩创意点赞
                val bgPicture: String, // http://img.kaiyanapp.com/9056413cfeffaf0c841d894390aa8e08.jpeg?imageMogr2/quality/60/format/jpg
                val headerImage: String, // http://img.kaiyanapp.com/ff0f6d0ad5f4b6211a3f746aaaffd916.jpeg?imageMogr2/quality/60/format/jpg
                val tagRecType: String, // IMPORTANT
                val childTagList: Any?, // null
                val childTagIdList: Any?, // null
                val haveReward: Boolean, // false
                val ifNewest: Boolean, // false
                val newestEndTime: Any?, // null
                val communityIndex: Int // 0
            )

            data class Provider(
                val name: String, // YouTube
                val alias: String, // youtube
                val icon: String // http://img.kaiyanapp.com/fa20228bc5b921e837156923a58713f6.png
            )

            data class Author(
                val id: Int, // 2162
                val icon: String, // http://img.kaiyanapp.com/98beab66d3885a139b54f21e91817c4f.jpeg
                val name: String, // 全球广告精选
                val description: String, // 我们精选世界最好看的广告，为全世界广告人的精彩创意点赞。
                val link: String,
                val latestReleaseTime: Long, // 1659315614000
                val videoNum: Int, // 2017
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
                    val itemId: Int, // 2162
                    val followed: Boolean // false
                )

                data class Shield(
                    val itemType: String, // author
                    val itemId: Int, // 2162
                    val shielded: Boolean // false
                )
            }

            data class Cover(
                val feed: String, // http://img.kaiyanapp.com/e813c2407b71e543d6f94f5ba8b7a9ec.jpeg?imageMogr2/quality/60/format/jpg
                val detail: String, // http://img.kaiyanapp.com/e813c2407b71e543d6f94f5ba8b7a9ec.jpeg?imageMogr2/quality/60/format/jpg
                val blurred: String, // http://img.kaiyanapp.com/b7478f236fed9dbde0c5a58ef80eac60.jpeg?imageMogr2/quality/60/format/jpg
                val sharing: Any?, // null
                val homepage: String? // http://img.kaiyanapp.com/e813c2407b71e543d6f94f5ba8b7a9ec.jpeg?imageView2/1/w/720/h/560/format/jpg/q/75|watermark/1/image/aHR0cDovL2ltZy5rYWl5YW5hcHAuY29tL2JsYWNrXzMwLnBuZw==/dissolve/100/gravity/Center/dx/0/dy/0|imageslim
            )

            data class WebUrl(
                val raw: String, // http://www.eyepetizer.net/detail.html?vid=312277
                val forWeibo: String // https://m.eyepetizer.net/u1/video-detail?video_id=312277&resource_type=video&utm_campaign=routine&utm_medium=share&utm_source=weibo&uid=0
            )

            data class PlayInfo(
                val height: Int, // 480
                val width: Int, // 854
                val urlList: List<Url>,
                val name: String, // 标清
                val type: String, // normal
                val url: String // http://baobab.kaiyanapp.com/api/v1/playUrl?vid=312277&resourceType=video&editionType=normal&source=aliyun&playUrlType=url_oss&udid=
            ) {
                data class Url(
                    val name: String, // aliyun
                    val url: String, // http://baobab.kaiyanapp.com/api/v1/playUrl?vid=312277&resourceType=video&editionType=normal&source=aliyun&playUrlType=url_oss&udid=
                    val size: Int // 3710138
                )
            }
        }
    }
}