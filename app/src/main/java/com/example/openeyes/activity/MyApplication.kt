package com.example.openeyes.activity

import android.app.Application
import android.content.Context


/**
* description ： 全局获取context和application
* author : lfy
* email : 1623658271@qq.com
* date : 2022/7/15 15:27
*/
class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        //全局获取context和application
        mContext = applicationContext
        mApplication = this
    }

    companion object {
        private var mContext: Context? = null
        private var mApplication:Application? = null
        //创建一个静态的方法，以便获取context对象
        val context: Context?
            get() = mContext
        val application:Application?
            get() = mApplication
    }

}