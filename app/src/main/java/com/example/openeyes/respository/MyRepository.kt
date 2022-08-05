package com.example.openeyes.respository

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.openeyes.api.ApiService
import com.example.openeyes.api.RetrofitClient
import com.example.openeyes.model.*
import com.example.openeyes.utils.MySQLiteHelper
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observer
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers
import retrofit2.http.Query

/**
 * description ： 仓库层
 * author : lfy
 * email : 1623658271@qq.com
 * date : 2022/7/15 14:43
 */
class MyRepository{

    /**
     * 首页数据与更多的获取
     */
    //首页-精选更多
    fun getDailyHandpickMoreMsg(date:Long,num:Int,observer: Observer<HomepageMoreBean>) = RetrofitClient.apiService
            .getMoreHomepageMsg(date,num)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(observer)

    //首页-精选
    fun getDailyHandpickMsg(observer:Observer<DailyHandpickBean>) = RetrofitClient.apiService
        .getDailyHandpickMsg()
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(observer)


    /**
     * 更多页面的各种数据的获取
     */
    //更多-推荐
    fun getRecMsg(observer: Observer<SocialRecommendBean>) = RetrofitClient.apiService
        .getSocialRecMsg()
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(observer)

    //更多-推荐更多
    fun getRecMore(start:Long,page:Int,observer: Observer<SocialRecommendBean>) = RetrofitClient.apiService
        .getSocialMore(start,page)
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(observer)

    //更多-分类
    fun getFindMoreClassMsg(observer: Observer<FindMoreClassBean>) = RetrofitClient.apiService
        .getFindMoreClassMsg()
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(observer)
    //更多-专题
    fun getSpecialMsg(observer: Observer<SpecialBean>) = RetrofitClient.apiService
        .getSpecialMsg()
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(observer)


    /**
     * 分类进入后的数据获取
     */
    //分类界面数据
    fun getClassInMsg(pathId:String,udid: String,observer: Observer<ClassDeepMsgBean>) = RetrofitClient.apiService
        .getClassDeepMsg(pathId,udid)
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(observer)

    //分类界面更多数据
    fun getClassInMoreMsg(pathId: String,start:Int,num:Int,udid:String,observer: Observer<ClassDeepMoreMsgBean>) = RetrofitClient.apiService
        .getClassMoreMsg(pathId,start,num,udid)
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(observer)


    /**
     * 视频相关数据
     */
    //相关视频数据
    fun getRelatedVideoMsg(id:Int,observer: Observer<RelatedVideoBean>) = RetrofitClient.apiService
        .getRelatedVideoMsg(id)
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(observer)

    //评论数据
    fun getVideoCommentMsg(id:Int,observer: Observer<CommentBean>) = RetrofitClient.apiService
        .getVideoComments(id)
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(observer)


    /**
     * 搜索相关数据
     */
    //热搜数据
    fun getHotSearchWordMsg(observer: Observer<HotSearchBean>) = RetrofitClient.apiService
        .getHotSearchMsg()
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(observer)

    //搜索相关数据
    fun getSearchMsg(query:String,observer: Observer<SearchBean>) = RetrofitClient.apiService
        .getSearchMsg(query)
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(observer)

    //搜索中更多数据
    fun getSearchMoreMsg(start:Int,num:Int,query:String,observer: Observer<SearchMoreBean>) = RetrofitClient.apiService
        .getMoreSearchMsg(start,num,query)
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(observer)

    /**
     * 视频排行界面数据
     */
    fun getRankListMsg(strategy:String,observer: Observer<RankListVideoBean>) = RetrofitClient.apiService
        .getRankListMsg(strategy)
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(observer)

    /**
     * 专题页面数据
     */
    fun getSpecialInMsg(path:String,observer: Observer<SpecialInBean>) = RetrofitClient.apiService
        .getSpecialInMsg(path)
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(observer)

    /**
     * 每日一图
     */
    fun getDailyImg(observer: Observer<DailyImgBean>) = RetrofitClient.apiService
        .getDailyImg()
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(observer)

    /**
     * 获取历史观看数据
     */
    fun getHistoryMsg(name:String):MutableList<VideoBean>{
        val list = MySQLiteHelper.getHistoryVideoBeanList(name)
        return list
    }
}