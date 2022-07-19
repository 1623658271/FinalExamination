package com.example.openeyes.model

/**
 * description ： 评论真正接收的json格式的数据类
 * author : lfy
 * email : 1623658271@qq.com
 * date : 2022/7/17 15:19
 */
data class CommentModel(
    val itemList: List<Item>,
    val count: Int,
    val total: Int,
    val nextPageUrl: Any?,
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
            val text: String?,
            val font: String?,
            val actionUrl: Any?,
            val adTrack: Any?,
            val id: Long?,
            val videoId: Int?,
            val videoTitle: String?,
            val parentReplyId: Long?,
            val rootReplyId: Long?,
            val sequence: Int?,
            val message: String?,
            val replyStatus: String?,
            val createTime: Long?,
            val user: User?,
            val likeCount: Int?,
            val liked: Boolean?,
            val hot: Boolean?,
            val userType: Any?,
            val type: String?,
            val imageUrl: String?,
            val ugcVideoId: Any?,
            val recommendLevel: String?,
            val parentReply: Any?,
            val showParentReply: Boolean?,
            val showConversationButton: Boolean?,
            val ugcVideoUrl: Any?,
            val cover: Any?,
            val userBlocked: Boolean?,
            val sid: String?
        ) {
            data class User(
                val uid: Int,
                val nickname: String,
                val avatar: String,
                val userType: String,
                val ifPgc: Boolean,
                val description: Any?,
                val area: Any?,
                val gender: Any?,
                val registDate: Long,
                val releaseDate: Any?,
                val cover: Any?,
                val actionUrl: String,
                val followed: Boolean,
                val limitVideoOpen: Boolean,
                val library: String,
                val birthday: Any?,
                val country: Any?,
                val city: Any?,
                val university: Any?,
                val job: Any?,
                val expert: Boolean
            )
        }
    }
}