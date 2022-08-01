package com.example.openeyes.respository

import com.example.openeyes.api.ApiService
import com.example.openeyes.api.RetrofitClient
import com.example.openeyes.api.URL

/**
 * description ： 仓库层
 * author : lfy
 * email : 1623658271@qq.com
 * date : 2022/7/15 14:43
 */
class MyRepository(val url:String) {
    private var apiService:ApiService ?= null

    fun getService():ApiService{
        if(apiService==null){
            apiService = RetrofitClient.getInstance(url).getSevers(ApiService::class.java)
        }
        return apiService!!
    }
}