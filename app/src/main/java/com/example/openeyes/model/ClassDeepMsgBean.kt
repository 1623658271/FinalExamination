package com.example.openeyes.model

/**
 * description ： 分类页面进入的数据类
 * author : lfy
 * email : 1623658271@qq.com
 * date : 2022/7/21 18:57
 */
data class ClassDeepMsgBean(
    val itemList: List<Item>,
    val count: Int,
    val total: Int,
    val nextPageUrl: String,
    val adExist: Boolean
) {
    data class Item(
        val type: String,
        val `data`: Data,
        val trackingData: Any?,
        val tag: Any?,
        val id: Int,
        val adIndex: Int
    ) {
        data class Data(
            val dataType: String,
            val id: Int?,
            val type: String? = null,
            val text: String? = null,
            val subTitle: Any? = null,
            val actionUrl: String? = null,
            val adTrack: List<Any>? = null,
            val follow: Any? = null,
            val header: Header? = null,
            val content: Content? = null,
            val title: String? = null,
            val description: String? = null,
            val library: String? = null,
            val tags: List<Tag>? = null,
            val consumption: ConsumptionBean? = null,
            val resourceType: String? = null,
            val slogan: Any? = null,
            val provider: Provider? = null,
            val category: String? = null,
            val author: Author? = null,
            val cover: Cover? = null,
            val playUrl: String? = null,
            val thumbPlayUrl: Any? = null,
            val duration: Int? = null,
            val webUrl: WebUrl? = null,
            val releaseTime: Long? = null,
            val playInfo: List<PlayInfo>? = null,
            val campaign: Any? = null,
            val waterMarks: Any? = null,
            val ad: Boolean? = null,
            val titlePgc: Any? = null,
            val descriptionPgc: Any? = null,
            val remark: Any? = null,
            val ifLimitVideo: Boolean? = null,
            val searchWeight: Int? = null,
            val brandWebsiteInfo: Any? = null,
            val videoPosterBean: VideoPosterBean? = null,
            val idx: Int? = null,
            val shareAdTrack: Any? = null,
            val favoriteAdTrack: Any? = null,
            val webAdTrack: Any? = null,
            val date: Long? = null,
            val promotion: Any? = null,
            val label: Any? = null,
            val labelList: List<Any>? = null,
            val descriptionEditor: String?= null,
            val collected: Boolean?= null,
            val reallyCollected: Boolean?= null,
            val played: Boolean?= null,
            val subtitles: List<Any>?= null,
            val lastViewTime: Any?= null,
            val playlists: Any?= null,
            val src: Int?= null,
            val recallSource: String?= null,
            val recall_source: String?= null
        ) {
            data class Header(
                val id: Int,
                val title: String,
                val font: Any?,
                val subTitle: Any?,
                val subTitleFont: Any?,
                val textAlign: String,
                val cover: Any?,
                val label: Any?,
                val actionUrl: String,
                val labelList: Any?,
                val rightText: Any?,
                val icon: String,
                val iconType: String,
                val description: String,
                val time: Long,
                val showHateVideo: Boolean
            )

            data class Content(
                val type: String,
                val `data`: Data,
                val trackingData: Any?,
                val tag: Any?,
                val id: Int,
                val adIndex: Int
            ) {
                data class Data(
                    val dataType: String,
                    val id: Int,
                    val title: String,
                    val description: String,
                    val library: String,
                    val tags: List<Tag>,
                    val consumption: ConsumptionBean,
                    val resourceType: String,
                    val slogan: String?,
                    val provider: Provider,
                    val category: String,
                    val author: Author,
                    val cover: Cover,
                    val playUrl: String,
                    val thumbPlayUrl: String?,
                    val duration: Int,
                    val webUrl: WebUrl,
                    val releaseTime: Long,
                    val playInfo: List<PlayInfo>,
                    val campaign: Any?,
                    val waterMarks: Any?,
                    val ad: Boolean,
                    val adTrack: List<Any>,
                    val type: String,
                    val titlePgc: String?,
                    val descriptionPgc: String,
                    val remark: String?,
                    val ifLimitVideo: Boolean,
                    val searchWeight: Int,
                    val brandWebsiteInfo: Any?,
                    val videoPosterBean: VideoPosterBean?,
                    val idx: Int,
                    val shareAdTrack: Any?,
                    val favoriteAdTrack: Any?,
                    val webAdTrack: Any?,
                    val date: Long,
                    val promotion: Any?,
                    val label: Any?,
                    val labelList: List<Any>,
                    val descriptionEditor: String,
                    val collected: Boolean,
                    val reallyCollected: Boolean,
                    val played: Boolean,
                    val subtitles: List<Any>,
                    val lastViewTime: Any?,
                    val playlists: Any?,
                    val src: Int?,
                    val recallSource: String?,
                    val recall_source: String?
                ) {
                    data class Tag(
                        val id: Int,
                        val name: String,
                        val actionUrl: String,
                        val adTrack: Any?,
                        val desc: String?,
                        val bgPicture: String,
                        val headerImage: String,
                        val tagRecType: String,
                        val childTagList: Any?,
                        val childTagIdList: Any?,
                        val haveReward: Boolean,
                        val ifNewest: Boolean,
                        val newestEndTime: Any?,
                        val communityIndex: Int
                    )

                    data class Provider(
                        val name: String,
                        val alias: String,
                        val icon: String
                    )

                    data class Author(
                        val id: Int,
                        val icon: String,
                        val name: String,
                        val description: String,
                        val link: String,
                        val latestReleaseTime: Long,
                        val videoNum: Int,
                        val adTrack: Any?,
                        val follow: Follow,
                        val shield: Shield,
                        val approvedNotReadyVideoCount: Int,
                        val ifPgc: Boolean,
                        val recSort: Int,
                        val expert: Boolean
                    ) {
                        data class Follow(
                            val itemType: String,
                            val itemId: Int,
                            val followed: Boolean
                        )

                        data class Shield(
                            val itemType: String,
                            val itemId: Int,
                            val shielded: Boolean
                        )
                    }

                    data class Cover(
                        val feed: String,
                        val detail: String,
                        val blurred: String,
                        val sharing: Any?,
                        val homepage: String?
                    )

                    data class WebUrl(
                        val raw: String,
                        val forWeibo: String
                    )

                    data class PlayInfo(
                        val height: Int,
                        val width: Int,
                        val urlList: List<Url>,
                        val name: String,
                        val type: String,
                        val url: String
                    ) {
                        data class Url(
                            val name: String,
                            val url: String,
                            val size: Int
                        )
                    }

                    data class VideoPosterBean(
                        val scale: Double,
                        val url: String,
                        val fileSizeStr: String
                    )
                }
            }

            data class Tag(
                val id: Int,
                val name: String,
                val actionUrl: String,
                val adTrack: Any?,
                val desc: String?,
                val bgPicture: String,
                val headerImage: String,
                val tagRecType: String,
                val childTagList: Any?,
                val childTagIdList: Any?,
                val haveReward: Boolean,
                val ifNewest: Boolean,
                val newestEndTime: Any?,
                val communityIndex: Int
            )

            data class Provider(
                val name: String,
                val alias: String,
                val icon: String
            )

            data class Author(
                val id: Int,
                val icon: String,
                val name: String,
                val description: String,
                val link: String,
                val latestReleaseTime: Long,
                val videoNum: Int,
                val adTrack: Any?,
                val follow: Follow,
                val shield: Shield,
                val approvedNotReadyVideoCount: Int,
                val ifPgc: Boolean,
                val recSort: Int,
                val expert: Boolean
            ) {
                data class Follow(
                    val itemType: String,
                    val itemId: Int,
                    val followed: Boolean
                )

                data class Shield(
                    val itemType: String,
                    val itemId: Int,
                    val shielded: Boolean
                )
            }

            data class Cover(
                val feed: String,
                val detail: String,
                val blurred: String,
                val sharing: Any?,
                val homepage: Any?
            )

            data class WebUrl(
                val raw: String,
                val forWeibo: String
            )

            data class PlayInfo(
                val height: Int,
                val width: Int,
                val urlList: List<Url>,
                val name: String,
                val type: String,
                val url: String
            ) {
                data class Url(
                    val name: String,
                    val url: String,
                    val size: Int
                )
            }

            data class VideoPosterBean(
                val scale: Double,
                val url: String,
                val fileSizeStr: String
            )
        }
    }
}