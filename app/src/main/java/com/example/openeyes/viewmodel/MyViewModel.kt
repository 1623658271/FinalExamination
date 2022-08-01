package com.example.openeyes.viewmodel

import android.util.Log
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.openeyes.MyApplication
import com.example.openeyes.api.URL
import com.example.openeyes.bean.*
import com.example.openeyes.respository.MyRepository
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observer
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers

/**
 * description ： ViewModel类
 * author : lfy
 * email : 1623658271@qq.com
 * date : 2022/7/15 14:29
 */
class MyViewModel: ViewModel() {

    //发现更多的分类
    private var findMoreClassBeanLiveData: MutableLiveData<FindMoreClassBean>? = null
    //每日精选
    private var dailyHandpickBeanLiveData: MutableLiveData<DailyHandpickBean>? = null
    //视频的评论
    private var commentsLiveData:MutableLiveData<CommentBean>? = null
    //每日精选加载更多
    private var relatedLiveData:MutableLiveData<RelatedVideoBean>? = null
    //热搜
    private var hotSearchLiveData:MutableLiveData<HotSearchBean>? = null
    //搜索详情
    private var searchLiveData:MutableLiveData<SearchBean>? = null
    //分类页面数据
    private var classInLiveData:MutableLiveData<ClassDeepMsgBean>? = null
    //分类页面更多数据
    private var classInMoreLiveData:MutableLiveData<ClassDeepMoreMsgBean>? = null
    //社区推荐
    private var recLiveData:MutableLiveData<SocialRecommendBean>? = null

    private val a by lazy {
        getRecLiveData()
    }

    private val TAG = "lfy"

    fun getRecLiveData():MutableLiveData<SocialRecommendBean>{
        if(recLiveData==null){
            recLiveData = MutableLiveData()
            updateRecLiveData()
        }
        return recLiveData!!
    }

    fun getClassInLiveData(path:String,udid:String):MutableLiveData<ClassDeepMsgBean>{
        if(classInLiveData==null){
            classInLiveData = MutableLiveData()
            updateClassInLiveData(path,udid)
        }
        return classInLiveData!!
    }

    fun getClassInMoreLiveData(url: String,start:Int,num:Int,udid:String):MutableLiveData<ClassDeepMoreMsgBean>{
        if(classInMoreLiveData==null){
            classInMoreLiveData = MutableLiveData()
            updateClassInMoreLiveData(url,start,num,udid)
        }
        return classInMoreLiveData!!
    }

    fun updateClassInMoreLiveData(url:String,start: Int, num:Int, udid: String) {
        MyRepository(url)
            .getService()
            .getClassMoreMsg(start,num,udid)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object :Observer<ClassDeepMoreMsgBean>{
                override fun onSubscribe(d: Disposable) {

                }

                override fun onNext(t: ClassDeepMoreMsgBean) {
                    classInMoreLiveData!!.value = t
                    Log.e(TAG, "onNext: ${t.nextPageUrl}", )
                }

                override fun onError(e: Throwable) {
                    showNetworkError()
                }

                override fun onComplete() {

                }

            })
    }

    fun updateClassInLiveData(path: String, udid: String) {
        MyRepository(URL.ClassDeepUrl)
            .getService()
            .getClassDeepMsg(path,udid)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object :Observer<ClassDeepMsgBean>{
                override fun onSubscribe(d: Disposable) {

                }

                override fun onNext(t: ClassDeepMsgBean) {
                    classInLiveData!!.value = t
                }

                override fun onError(e: Throwable) {
                    showNetworkError()
                }

                override fun onComplete() {

                }

            })
    }

    fun getFindMoreLiveData(): MutableLiveData<FindMoreClassBean> {
        if (findMoreClassBeanLiveData == null) {
            findMoreClassBeanLiveData = MutableLiveData()
            updateFindMoreViewModel()
        }
        return findMoreClassBeanLiveData!!
    }

    fun getDailyHandpickLiveData():MutableLiveData<DailyHandpickBean>{
        if(dailyHandpickBeanLiveData==null){
            dailyHandpickBeanLiveData = MutableLiveData()
            updateDailyHandpickViewModel()
        }
        return dailyHandpickBeanLiveData!!
    }

    fun getCommentsLiveData(id:Int):MutableLiveData<CommentBean>{
        if(commentsLiveData == null){
            commentsLiveData = MutableLiveData()
            updateCommentsViewModel(id)
        }
        return commentsLiveData!!
    }

    fun getRelatedLiveData(id:Int): MutableLiveData<RelatedVideoBean> {
        if(relatedLiveData == null){
            relatedLiveData = MutableLiveData()
            updateRelatedViewModel(id)
        }
        return relatedLiveData!!
    }

    fun getSearchLiveData(search:String):MutableLiveData<SearchBean>{
        if(searchLiveData==null){
            searchLiveData = MutableLiveData()
            updateSearchLiveData(search)
        }
        return searchLiveData!!
    }

    fun getHotSearchLiveData():MutableLiveData<HotSearchBean>{
        if(hotSearchLiveData==null){
            hotSearchLiveData = MutableLiveData()
            updateHotSearchViewModel()
        }
        return hotSearchLiveData!!
    }

    fun updateRecLiveData(){
        MyRepository(URL.RecUrl)
            .getService()
            .getSocialRecMsg()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object :Observer<SocialRecommendBean>{
                override fun onSubscribe(d: Disposable) {

                }

                override fun onNext(t: SocialRecommendBean) {
                    recLiveData!!.value = t
                    Log.e(TAG, "onNext: ${t.itemList.size}", )
                }

                override fun onError(e: Throwable) {
                    showNetworkError()
                    Log.e(TAG, "onError: $e", )
                }

                override fun onComplete() {

                }

            })
    }

    fun updateSearchLiveData(search: String) {
        MyRepository(URL.SearchUrl)
            .getService()
            .getSearchMsg(search)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object :Observer<SearchBean>{
                override fun onSubscribe(d: Disposable) {

                }

                override fun onNext(t: SearchBean) {
                    searchLiveData!!.value = t
                }

                override fun onError(e: Throwable) {
                    showNetworkError()
                }

                override fun onComplete() {

                }

            })

    }

    private fun updateHotSearchViewModel() {
        MyRepository(URL.HotSearchUrl)
            .getService()
            .getHotSearchMsg()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object :Observer<HotSearchBean>{
                override fun onSubscribe(d: Disposable) {

                }

                override fun onNext(t: HotSearchBean) {
                    hotSearchLiveData!!.value = t
                }

                override fun onError(e: Throwable) {
                    showNetworkError()
                }

                override fun onComplete() {

                }

            })
    }

    fun updateRelatedViewModel(id:Int){
        MyRepository(URL.RelatedUrl)
            .getService()
            .getRelatedVideoMsg(id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object :Observer<RelatedVideoBean>{
                override fun onSubscribe(d: Disposable) {

                }

                override fun onNext(t: RelatedVideoBean) {
                    relatedLiveData!!.value = t
                }

                override fun onError(e: Throwable) {
                    showNetworkError()
                    Log.d(TAG, "onError: $e")
                }

                override fun onComplete() {

                }

            })
    }

    fun updateCommentsViewModel(id:Int) {
        MyRepository(URL.CommentsUrl)
            .getService()
            .getVideoComments(id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object :Observer<CommentBean>{
                override fun onSubscribe(d: Disposable) {

                }

                override fun onNext(t: CommentBean) {
                    commentsLiveData!!.value = t
                    Log.d(TAG, "onNext: ${t.itemList.size}")
                }

                override fun onError(e: Throwable) {
                    showNetworkError()
                    Log.d(TAG, "onError: $e")
                }

                override fun onComplete() {

                }

            })
    }


    fun updateFindMoreViewModel() {
        MyRepository(URL.FindMoreClassUrl)
            .getService()
            .getFindMoreClassMsg()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object :Observer<FindMoreClassBean>{
                override fun onSubscribe(d: Disposable) {

                }

                override fun onNext(t: FindMoreClassBean) {
                    findMoreClassBeanLiveData!!.value = t
                }

                override fun onError(e: Throwable) {
                    showNetworkError()
                }

                override fun onComplete() {

                }

            })
    }
    fun updateDailyHandpickViewModel(){
        MyRepository(URL.DailyHandpickUrl)
            .getService()
            .getDailyHandpickMsg()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object :Observer<DailyHandpickBean>{
                override fun onSubscribe(d: Disposable) {

                }

                override fun onNext(t: DailyHandpickBean) {
                    dailyHandpickBeanLiveData!!.value = t
                }

                override fun onError(e: Throwable) {
                    showNetworkError()
                }

                override fun onComplete() {

                }

            })
    }
    fun showNetworkError(){
        Toast.makeText(MyApplication.context,"请检查你的网络！",Toast.LENGTH_SHORT).show()
    }
}