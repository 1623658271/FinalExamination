package com.example.openeyes.model

/**
 * description ： 搜索页额外数据的数据类
 * author : lfy
 * email : 1623658271@qq.com
 * date : 2022/7/21 15:27
 */
data class SearchMoreBean(
    val itemList: List<Item>,
    val count: Int,
    val total: Int,
    val nextPageUrl: String,
    val adExist: Boolean
) {
    data class Item(
        val type: String,
        val `data`: Data,
        val trackingData: TrackingData,
        val tag: Any?,
        val id: Int,
        val adIndex: Int
    ) {
        data class Data(
            val dataType: String,
            val header: Header,
            val content: Content,
            val adTrack: List<Any>
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
                    val slogan: Any?,
                    val provider: Provider,
                    val category: String,
                    val author: Author,
                    val cover: Cover,
                    val playUrl: String,
                    val thumbPlayUrl: Any?,
                    val duration: Int,
                    val webUrl: WebUrl,
                    val releaseTime: Long,
                    val playInfo: List<PlayInfo>,
                    val campaign: Any?,
                    val waterMarks: Any?,
                    val ad: Boolean,
                    val adTrack: List<Any>,
                    val type: String,
                    val titlePgc: String,
                    val descriptionPgc: String,
                    val remark: Any?,
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
                    val src: Any?,
                    val recallSource: Any?,
                    val recall_source: Any?
                ) {
                    data class Tag(
                        val id: Int,
                        val name: String,
                        val actionUrl: String,
                        val adTrack: Any?,
                        val desc: String,
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

        data class TrackingData(
            val dataType: String,
            val `data`: Data
        ) {
            data class Data(
                val show: List<Show>,
                val click: List<Click>
            ) {
                data class Show(
                    val `data`: Data,
                    val sdk: String
                ) {
                    data class Data(
                        val element_index: Int,
                        val card_index: Int,
                        val element_title: String,
                        val card_title: String,
                        val element_label: String,
                        val page_type: String,
                        val element_id: Int,
                        val element_type: String,
                        val card_type: String,
                        val element_relative_index: Int,
                        val card_id: Int
                    )
                }

                data class Click(
                    val `data`: Data,
                    val sdk: String
                ) {
                    data class Data(
                        val element_index: Int,
                        val card_index: Int,
                        val element_label: String,
                        val page_type: String,
                        val element_id: Int,
                        val element_type: String,
                        val card_type: String,
                        val click_action: String,
                        val card_id: Int,
                        val element_title: String,
                        val card_title: String,
                        val click_action_url: String,
                        val element_relative_index: Int
                    )
                }
            }
        }
    }
}