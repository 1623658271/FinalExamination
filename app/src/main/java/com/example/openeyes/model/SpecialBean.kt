package com.example.openeyes.model

/**
 * description ： 专题图片数据
 * author : lfy
 * email : 1623658271@qq.com
 * date : 2022/8/4 15:51
 */
data class SpecialBean(
    val itemList: List<Item>,
    val count: Int, // 10
    val total: Int, // 0
    val nextPageUrl: String, // http://baobab.kaiyanapp.com/api/v3/specialTopics?start=10&num=10
    val adExist: Boolean // false
) {
    data class Item(
        val type: String, // banner2
        val `data`: Data,
        val trackingData: Any?, // null
        val tag: Any?, // null
        val id: Int, // 0
        val adIndex: Int // -1
    ) {
        data class Data(
            val dataType: String, // Banner
            val id: Int, // 692
            val title: String,
            val description: String,
            val image: String, // http://img.kaiyanapp.com/bbbbd1e1f2e03eafdda051ffa8673ae9.png?imageMogr2/quality/60/format/jpg
            val actionUrl: String, // eyepetizer://webview/?title=%E5%A4%8F%E6%97%A5%E9%87%8D%E7%8E%B0%EF%BC%81%E5%8A%A8%E7%94%BB%E9%87%8C%E5%90%B9%E7%9A%84%E9%A3%8E%E9%83%BD%E6%98%AF%E7%83%AD%E7%83%AD%E7%9A%84&url=http%3A%2F%2Fwww.eyepetizer.net%2Fvideos_article.html%3Fnid%3D692%26shareable%3Dtrue
            val adTrack: List<Any>,
            val shade: Boolean, // false
            val label: Label,
            val labelList: List<Any>,
            val header: Any?, // null
            val autoPlay: Boolean // false
        ) {
            data class Label(
                val text: String,
                val card: String,
                val detail: Any? // null
            )
        }
    }
}