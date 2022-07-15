package com.example.openeyes.api

import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

/**
 * description ： TODO:类的作用
 * author : lfy
 * email : 1623658271@qq.com
 * date : 2022/7/15 08:57
 */
class RetrofitClient {
    companion object{
        private lateinit var retrofitClient: RetrofitClient
        private lateinit var retrofit: Retrofit

        fun getInstance(url:String):RetrofitClient{
            createRetrofit(url)
            retrofitClient = RetrofitClient()
            return retrofitClient
        }

        private fun createRetrofit(url:String):Retrofit {
            retrofit = Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                .build()
            return retrofit
        }
    }
    fun <T> getSevers(sever: Class<T>): T {
        return retrofit.create(sever)
    }
}