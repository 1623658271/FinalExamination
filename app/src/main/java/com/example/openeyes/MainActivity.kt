package com.example.openeyes

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import cn.jzvd.JzvdStd
import com.example.openeyes.adapter.HomePageRVAdapter
import com.example.openeyes.api.ApiService
import com.example.openeyes.api.RetrofitClient
/**
 * description ： TODO:类的作用
 * author : lfy
 * email : 1623658271@qq.com
 * date : 2022/7/14 22:03
 */
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_main_activity)
    }

    override fun onBackPressed() {
        super.onBackPressed()
        JzvdStd.backPress()
    }
}