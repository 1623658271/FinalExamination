package com.example.openeyes.utils

import android.widget.Toast
import com.example.openeyes.activity.MyApplication

/**
 * description ： TODO:类的作用
 * author : lfy
 * email : 1623658271@qq.com
 * date : 2022/8/6 20:59
 */
fun String.toast(){
    Toast.makeText(MyApplication.context,this,Toast.LENGTH_SHORT).show()
}