package com.example.openeyes.utils

import com.example.openeyes.activity.CrashActivity
import com.example.openeyes.activity.MyApplication

/**
 * description ： 全局捕获异常
 * author : lfy
 * email : 1623658271@qq.com
 * date : 2022/8/7 15:04
 */
object CrashHandler : Thread.UncaughtExceptionHandler {

    override fun uncaughtException(t: Thread, e: Throwable) {
        CrashActivity.start(MyApplication.context!!,e.toString())
    }
}