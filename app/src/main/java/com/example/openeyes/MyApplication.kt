package com.example.openeyes

import android.app.Application
import android.content.Context


/**
* description ： TODO:类的作用
* author : lfy
* email : 1623658271@qq.com
* date : 2022/7/15 15:27
*/
class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        //获取context
        mContext = applicationContext
    }

    companion object {
        private var mContext: Context? = null

        //创建一个静态的方法，以便获取context对象
        val context: Context?
            get() = mContext
    }
}