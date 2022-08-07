package com.example.openeyes.viewmodel

import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.openeyes.activity.MyApplication
import com.example.openeyes.respository.MyRepository
import com.example.openeyes.utils.LoadState

/**
 * description ： viewModel基类
 * author : lfy
 * email : 1623658271@qq.com
 * date : 2022/8/6 15:39
 */
open class BaseViewModel:ViewModel() {
    //初始化加载状态
    var loadState = MutableLiveData<LoadState>()
    val state: LiveData<LoadState>
        get() = loadState
    //加载更多的状态
    var loadStateMore = MutableLiveData<LoadState>()
    val stateMore: LiveData<LoadState>
        get() = loadStateMore
    //仓库
    val myRepository by lazy {
        MyRepository()
    }

    fun showNetWorkError() {
        Toast.makeText(MyApplication.context!!,"请检查你的网络!", Toast.LENGTH_SHORT).show()
    }
}