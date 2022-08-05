package com.example.openeyes.utils

import android.app.Activity
import android.util.Log

/**
 * description ：管理播放的活动
 * author : lfy
 * email : 1623658271@qq.com
 * date : 2022/7/21 15:57
 */
object ActivityController {
    var list:MutableList<Activity> = ArrayList()

    fun addActivity(activity:Activity) {
        list.add(activity)
    }
    fun removeAllVideoActivity(){
        for(i in list){
            i.finish()
        }
        list.clear()
    }
    fun removeActivity(activity: Activity){
        activity.finish()
        list.remove(activity)
    }
}