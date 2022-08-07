package com.example.openeyes.viewmodel

import android.text.TextUtils
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.openeyes.model.DynamicMsgBean
import com.example.openeyes.utils.LoadState
import com.example.openeyes.utils.toast
import io.reactivex.rxjava3.core.Observer
import io.reactivex.rxjava3.disposables.Disposable

/**
 * description ： 主题页viewModel
 * author : lfy
 * email : 1623658271@qq.com
 * date : 2022/8/6 15:37
 */
class DynamicMsgPageViewModel:BaseViewModel() {
    private val dynamicMsgLD:MutableLiveData<MutableList<DynamicMsgBean.Item>> by lazy {
        MutableLiveData()
    }
    val dynamicItem:LiveData<MutableList<DynamicMsgBean.Item>>
    get() = dynamicMsgLD

    //下一页数据url
    private val nextPageUrl:MutableLiveData<String> by lazy {
        MutableLiveData()
    }
    val next:LiveData<String>
    get() = nextPageUrl

    /**
     * 加载各主题数据
     */
    fun loadDynamicMsg(pathId:String){
        loadState.value = LoadState.LOADING
        myRepository.getDynamicMsg(pathId,object :Observer<DynamicMsgBean>{
            override fun onSubscribe(d: Disposable) {
            }

            override fun onNext(t: DynamicMsgBean) {
                nextPageUrl.value = t.nextPageUrl?:""
                dynamicMsgLD.value = t.itemList.toMutableList()
                loadState.value = LoadState.SUCCESS
            }

            override fun onError(e: Throwable) {
                showNetWorkError()
                loadState.value = LoadState.ERROR
            }

            override fun onComplete() {
            }

        })
    }

    /**
     * 更多主题
     */
    fun loadDynamicMoreMsg(){
        if(!TextUtils.isEmpty(nextPageUrl.value)){
            loadStateMore.value = LoadState.LOADING
            val url = nextPageUrl.value!!.split('?').last().split('&')
            val start = url[0].filter { it.isDigit() }.toInt()
            val num = url[1].filter { it.isDigit() }.toInt()
            val pathId = nextPageUrl.value!!.split('/').last().split('?').first().toString()

            myRepository.getDynamicMoreMsg(start,num,pathId,object :Observer<DynamicMsgBean>{
                override fun onSubscribe(d: Disposable) {
                }

                override fun onNext(t: DynamicMsgBean) {
                    nextPageUrl.value = t.nextPageUrl?:""
                    val list:MutableList<DynamicMsgBean.Item> = ArrayList()
                    list.addAll(dynamicMsgLD.value!!)
                    list.addAll(t.itemList)
                    dynamicMsgLD.value = list
                    loadStateMore.value = LoadState.SUCCESS
                }

                override fun onError(e: Throwable) {
                    showNetWorkError()
                    loadStateMore.value = LoadState.ERROR
                }

                override fun onComplete() {
                }

            })
        }else{
            loadStateMore.value = LoadState.EMPTY
            "已经没有数据了呢".toast()
        }
    }
}