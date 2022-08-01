package com.example.openeyes

import android.content.pm.ActivityInfo
import android.os.Build
import android.os.Bundle
import android.transition.Fade
import android.util.Log
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil

/**
 * description ： 基类
 * author : lfy
 * email : 1623658271@qq.com
 * date : 2022/8/1 14:08
 */
open class BaseActivity:AppCompatActivity() {
    val TAG = "lfy"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        //Log.d(TAG, "onCreate: ${javaClass.simpleName}")
    }
}