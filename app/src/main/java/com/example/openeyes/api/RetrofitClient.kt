package com.example.openeyes.api

import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

/**
 * description ： retrofitClient
 * author : lfy
 * email : 1623658271@qq.com
 * date : 2022/7/15 08:57
 */
object RetrofitClient {
    val retrofit = Retrofit.Builder()
        .baseUrl(ApiService.baseUrl)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
        .build()
    val apiService = retrofit.create(ApiService::class.java)
}