package com.example.openeyes

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.openeyes.adapter.HomePageRVAdapter
import com.example.openeyes.api.ApiService
import com.example.openeyes.api.RetrofitClient
import kotlinx.android.synthetic.*

/**
 * description ： 第一个活动，包含首页、发现、我的三个子fragment
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
    }

    override fun onDestroy() {
        super.onDestroy()
    }
}