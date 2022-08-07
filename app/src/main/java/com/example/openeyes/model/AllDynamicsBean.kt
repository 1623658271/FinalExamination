package com.example.openeyes.model

/**
 * description ： 获取所有主题的bean类
 * author : lfy
 * email : 1623658271@qq.com
 * date : 2022/8/6 14:29
 */
data class AllDynamicsBean(
    val tabInfo: TabInfo
) {
    data class TabInfo(
        val tabList: List<Tab>,
        val defaultIdx: Int // 0
    ) {
        data class Tab(
            val id: Int, // -1
            val name: String, // 推荐
            val apiUrl: String, // http://baobab.kaiyanapp.com/api/v7/tag/childTab/0?isRecTab=true
            val tabType: Int, // 0
            val nameType: Int, // 0
            val adTrack: Any? // null
        )
    }
}