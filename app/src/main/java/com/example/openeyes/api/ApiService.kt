package com.example.openeyes.api

import com.example.openeyes.model.*
import io.reactivex.rxjava3.core.Observable
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * description ： Retrofit网络请求的必要接口
 * author : lfy
 * email : 1623658271@qq.com
 * date : 2022/7/15 08:46
 */
interface ApiService {
    //获取所有发现更多的信息
    @GET("discovery")
    fun getFindMoreMsg():Observable<FindMoreBean>

    //获取发现更多的分类部分
    @GET("list")
    fun getFindMoreClassMsg():Observable<FindMoreClassBean>

    //获取日报精选
    @GET("feed")
    fun getDailyHandpickMsg():Observable<DailyHandpickBean>

    //获取视频的评论信息
    @GET("video")
    fun getVideoComments(@Query("videoId")id:Int):Observable<CommentModel>

    //搜索相关
    @GET("search")
    fun getSearchMsg(@Query("query")query:String):Observable<SearchModel>

    //搜索下滑更新下一个page
    @GET(".")
    fun getMoreSearchMsg(@Query("start")start:Int,@Query("num")num:Int,@Query("query")query: String):Observable<SearchMoreModel>

    //相关视频推荐
    @GET("related")
    fun getRelatedVideoMsg(@Query("id")id:Int):Observable<RelatedRecommendationModel>

    //加载日报下一个page
    @GET(".")
    fun getMoreHomepageMsg(@Query("date")date:Long,@Query("num")num:Int):Observable<HomepageMoreBean>

    //热搜
    @GET("hot")
    fun getHotSearchMsg():Observable<HotSearchModel>

    //分类点击深入获取数据
    @GET("{id}")
    fun getClassDeepMsg(@Path("id")id:String,@Query("udid")udid:String):Observable<ClassDeepMsgModel>

    //分类点击深入后的额外数据
    @GET(".")
    fun getClassMoreMsg(@Query("start")start:Int,@Query("num")num:Int,@Query("udid")udid: String):Observable<ClassDeepMoreMsgModel>
}