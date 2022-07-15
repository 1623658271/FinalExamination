package com.example.openeyes.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.openeyes.api.ApiService
import com.example.openeyes.api.RetrofitClient
import com.example.openeyes.api.URL
import com.example.openeyes.model.FindMoreBean
import com.example.openeyes.respository.MyRepository

/**
 * description ： TODO:类的作用
 * author : lfy
 * email : 1623658271@qq.com
 * date : 2022/7/15 14:29
 */
class FindMoreViewModel: ViewModel() {

    private var findMoreBeanMutableLiveData:MutableLiveData<FindMoreBean> = MutableLiveData()

    fun getFindMoreLiveData():MutableLiveData<FindMoreBean>{
        return findMoreBeanMutableLiveData
    }

}