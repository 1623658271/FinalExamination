package com.example.openeyes.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.openeyes.api.URL
import com.example.openeyes.model.DailyHandpickBean
import com.example.openeyes.model.FindMoreClassBean
import com.example.openeyes.respository.MyRepository
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observer
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers
import kotlin.math.log

/**
 * description ： TODO:类的作用
 * author : lfy
 * email : 1623658271@qq.com
 * date : 2022/7/15 14:29
 */
class MyViewModel: ViewModel() {

    private var findMoreClassBeanLiveData: MutableLiveData<FindMoreClassBean>? = null
    private var dailyHandpickBeanLiveData: MutableLiveData<DailyHandpickBean>? = null
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


    fun updateFindMoreViewModel() {
        MyRepository(URL.FindMoreClassUrl)
            .getService()
            .getFindMoreClassMsg()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : Observer<FindMoreClassBean> {
                override fun onSubscribe(d: Disposable) {

                }

                override fun onNext(t: FindMoreClassBean) {
                    findMoreClassBeanLiveData!!.value = t
                    Log.d(TAG, "find onNext: ")
                }

                override fun onError(e: Throwable) {

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
                    Log.d(TAG, "onNext: ")
                }

                override fun onError(e: Throwable) {
                    Log.d(TAG, "onError: $e")
                }

                override fun onComplete() {

                }

            })
    }
}