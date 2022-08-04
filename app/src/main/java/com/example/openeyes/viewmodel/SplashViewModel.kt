package com.example.openeyes.viewmodel

import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.openeyes.MyApplication
import com.example.openeyes.model.DailyImgBean
import com.example.openeyes.respository.MyRepository
import io.reactivex.rxjava3.core.Observer
import io.reactivex.rxjava3.disposables.Disposable

/**
 * description ： TODO:类的作用
 * author : lfy
 * email : 1623658271@qq.com
 * date : 2022/8/4 21:18
 */
class SplashViewModel : ViewModel() {

    private val repository by lazy {
        MyRepository()
    }

    private val dailyImgLiveData:MutableLiveData<DailyImgBean> by lazy {
        MutableLiveData<DailyImgBean>().also {
            getDailyImgData()
        }
    }
    val dailyImgBean: LiveData<DailyImgBean>
    get() = dailyImgLiveData

    private fun getDailyImgData() {
        repository.getDailyImg(object:Observer<DailyImgBean>{
            override fun onSubscribe(d: Disposable) {
            }

            override fun onNext(t: DailyImgBean) {
                dailyImgLiveData.value = t
            }

            override fun onError(e: Throwable) {
                showNetWorkError()
            }

            override fun onComplete() {
            }

        })
    }

    fun showNetWorkError(){
        Toast.makeText(MyApplication.context!!,"加载每日一图失败!",Toast.LENGTH_SHORT).show()
    }
}