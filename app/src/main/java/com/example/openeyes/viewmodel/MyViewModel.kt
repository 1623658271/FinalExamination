package com.example.openeyes.viewmodel

import android.util.Log
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.openeyes.MyApplication
import com.example.openeyes.api.URL
import com.example.openeyes.model.CommentModel
import com.example.openeyes.model.DailyHandpickBean
import com.example.openeyes.model.FindMoreClassBean
import com.example.openeyes.model.RelatedRecommendationModel
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

    private var findMoreClassBeanLiveData: MutableLiveData<FindMoreClassBean>? = null
    private var dailyHandpickBeanLiveData: MutableLiveData<DailyHandpickBean>? = null
    private var commentsLiveData:MutableLiveData<CommentModel>? = null
    private var relatedLiveData:MutableLiveData<RelatedRecommendationModel>? = null
    private val TAG = "lfy"

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

    fun getCommentsLiveData(id:Int):MutableLiveData<CommentModel>{
        if(commentsLiveData == null){
            commentsLiveData = MutableLiveData()
            updateCommentsViewModel(id)
        }
        return commentsLiveData!!
    }

    fun getRelatedLiveData(id:Int): MutableLiveData<RelatedRecommendationModel> {
        if(relatedLiveData == null){
            relatedLiveData = MutableLiveData()
            updateRelatedViewModel(id)
        }
        return relatedLiveData!!
    }

    fun updateRelatedViewModel(id:Int){
        MyRepository(URL.RelatedUrl)
            .getService()
            .getRelatedVideoMsg(id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object :Observer<RelatedRecommendationModel>{
                override fun onSubscribe(d: Disposable) {

                }

                override fun onNext(t: RelatedRecommendationModel) {
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
            .subscribe(object :Observer<CommentModel>{
                override fun onSubscribe(d: Disposable) {

                }

                override fun onNext(t: CommentModel) {
                    commentsLiveData!!.value = t
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