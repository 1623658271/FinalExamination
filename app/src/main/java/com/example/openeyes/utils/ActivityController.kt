package com.example.openeyes.utils

import android.app.Activity

/**
 * description ：管理播放的活动
 * author : lfy
 * email : 1623658271@qq.com
 * date : 2022/7/21 15:57
 */
object ActivityControl {
    var list:MutableList<Activity> = ArrayList()

    fun addActivity(activity:Activity) {
        list.add(activity)
    }
    fun removeAllVideoActivity(){
        for(i in list){
            i.finish()
        }
    }
    fun removeActivity(activity: Activity){
        activity.finish()
        list.remove(activity)
    }
}