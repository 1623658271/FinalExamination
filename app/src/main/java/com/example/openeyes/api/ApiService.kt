package com.example.openeyes.api

import com.example.openeyes.model.*
import io.reactivex.rxjava3.core.Observable
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

    companion object{
        val baseUrl = "http://baobab.kaiyanapp.com/api/"
    }
    //获取发现更多的分类部分
    @GET("v5/category/list")
    fun getFindMoreClassMsg():Observable<FindMoreClassBean>

    //获取日报精选
    @GET("v5/index/tab/feed")
    fun getDailyHandpickMsg():Observable<DailyHandpickBean>

    //加载日报下一个page
    @GET("v5/index/tab/feed")
    fun getMoreHomepageMsg(@Query("date")date:Long,@Query("num")num:Int):Observable<HomepageMoreBean>

    //获取视频的评论信息
    @GET("v2/replies/video")
    fun getVideoComments(@Query("videoId")id:Int):Observable<CommentBean>

    //搜索相关
    @GET("v3/search")
    fun getSearchMsg(@Query("query")query:String):Observable<SearchBean>

    //搜索下滑更新下一个page
    @GET("v3/search")
    fun getMoreSearchMsg(@Query("start")start:Int,@Query("num")num:Int,@Query("query")query: String):Observable<SearchMoreBean>

    //相关视频推荐
    @GET("v4/video/related")
    fun getRelatedVideoMsg(@Query("id")id:Int):Observable<RelatedVideoBean>

    //热搜
    @GET("v3/queries/hot")
    fun getHotSearchMsg():Observable<HotSearchBean>

    //分类点击深入获取数据
    @GET("v5/index/tab/category/{id}")
    fun getClassDeepMsg(@Path("id")id:String,@Query("udid")udid:String):Observable<ClassDeepMsgBean>

    //分类点击深入后的额外数据
    @GET("v5/index/tab/category/{id}")
    fun getClassMoreMsg(@Path("id")id:String,@Query("start")start:Int, @Query("num")num:Int, @Query("udid")udid: String):Observable<ClassDeepMoreMsgBean>

    //推荐
    @GET("v7/community/tab/rec")
    fun getSocialRecMsg():Observable<SocialRecommendBean>

    //推荐下一页
    @GET("v7/community/tab/rec")
    fun getSocialMore(@Query("startScore")start: Long,@Query("pageCount")page:Int):Observable<SocialRecommendBean>

    //获取视频排行数据
    @GET("v4/rankList/videos")
    fun getRankListMsg(@Query("strategy")strategy:String):Observable<RankListVideoBean>

    //获取专题数据
    @GET("v3/specialTopics")
    fun getSpecialMsg():Observable<SpecialBean>

    //专题进入后的数据
    @GET("v3/lightTopics/internal/{path}")
    fun getSpecialInMsg(@Path("path")path:String):Observable<SpecialInBean>

    //每日必应一图
    @GET("http://cn.bing.com/HPImageArchive.aspx?format=js&idx=0&n=1")
    fun getDailyImg(): Observable<DailyImgBean>

    //推荐主题详情数据
    @GET("v7/tag/childTab/0")
    fun getRecDynamic(@Query("isRecTab")isRecTab:Boolean):Observable<DynamicMsgBean>

    //主题所有分类
    @GET("v7/tag/tabList")
    fun getAllDynamics():Observable<AllDynamicsBean>

    //分类主题详情数据
    @GET("v7/tag/childTab/{path}")
    fun getDynamicMsg(@Path("path")pathId:String):Observable<DynamicMsgBean>

    //分类主题详情更多数据
    @GET("v7/tag/childTab/{path}")
    fun getDynamicMoreMsg(@Path("path")pathId: String,@Query("start")start:Int,@Query("num")num:Int):Observable<DynamicMsgBean>

    //主题最终页
    @GET("v6/tag/dynamics")
    fun getDynamicInMsg(@Query("id")id:Long):Observable<DynamicInBean>

    //主题最终页的加载更多
    @GET("v6/tag/dynamics")
    fun getDynamicInMoreMsg(@Query("start")start:Int,@Query("num")num:Int,@Query("id")id:Int):Observable<DynamicInBean>

}