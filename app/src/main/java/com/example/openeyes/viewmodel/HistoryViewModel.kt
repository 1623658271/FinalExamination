package com.example.openeyes.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.openeyes.model.VideoBean
import com.example.openeyes.respository.MyRepository
import com.example.openeyes.utils.LoadState

/**
 * description ： 历史数据的ViewModel
 * author : lfy
 * email : 1623658271@qq.com
 * date : 2022/8/5 15:48
 */
class HistoryViewModel:BaseViewModel() {
    private val historyListLD:MutableLiveData<MutableList<VideoBean>> by lazy {
        MutableLiveData()
    }
    val historyList:LiveData<MutableList<VideoBean>>
    get() = historyListLD

    /**
     * 获取历史数据
     */
    fun loadHistoryMsg(){
        loadState.value = LoadState.LOADING
        val list = myRepository.getHistoryMsg("Video")
        if(list.size!=0){
            list.reverse()
            historyListLD.value = list
            loadState.value = LoadState.SUCCESS
        }else{
            loadState.value = LoadState.EMPTY
        }
    }
}