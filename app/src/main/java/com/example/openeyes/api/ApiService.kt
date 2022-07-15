package com.example.openeyes.api

import com.example.openeyes.model.FindMoreBean
import io.reactivex.rxjava3.core.Observable
import retrofit2.http.GET

/**
 * description ： TODO:类的作用
 * author : lfy
 * email : 1623658271@qq.com
 * date : 2022/7/15 08:46
 */
interface ApiService {
    @GET
    fun getFindMoreMsg():Observable<FindMoreBean>

}