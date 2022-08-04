package com.example.openeyes.viewmodel

import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.openeyes.activity.MyApplication
import com.example.openeyes.model.PersonalBean
import com.example.openeyes.model.RankListVideoBean
import com.example.openeyes.model.VideoBean
import com.example.openeyes.respository.MyRepository
import com.example.openeyes.utils.DefaultUtil
import com.example.openeyes.utils.LoadState
import io.reactivex.rxjava3.core.Observer
import io.reactivex.rxjava3.disposables.Disposable

/**
 * description ： 热门排行页面的viewModel
 * author : lfy
 * email : 1623658271@qq.com
 * date : 2022/8/4 12:15
 */
class RankListPageViewModel:ViewModel() {
    //加载状态
    private var loadState = MutableLiveData<LoadState>()
    val state: LiveData<LoadState>
        get() = loadState
    //仓库
    private val myRepository by lazy {
        MyRepository()
    }
    //周排行
    private val weeklyListLD:MutableLiveData<MutableList<VideoBean>> by lazy {
        MutableLiveData<MutableList<VideoBean>>().also {
            loadWeekListMsg()
        }
    }
    val weeklyVideoList:LiveData<MutableList<VideoBean>>
    get() = weeklyListLD

    //月排行
    private val monthlyListLD:MutableLiveData<MutableList<VideoBean>> by lazy {
        MutableLiveData<MutableList<VideoBean>>().also {
            loadMonthlyListMsg()
        }
    }
    val monthlyVideoList:LiveData<MutableList<VideoBean>>
        get() = monthlyListLD

    //总排行
    private val historicalListLD:MutableLiveData<MutableList<VideoBean>> by lazy {
        MutableLiveData<MutableList<VideoBean>>().also {
            loadHistoricalListMsg()
        }
    }
    val historicalVideoList:LiveData<MutableList<VideoBean>>
        get() = historicalListLD

    /**
     * 加载周排行
     */
    fun loadWeekListMsg(){
        loadState.value = LoadState.LOADING
        myRepository.getRankListMsg("weekly",object :Observer<RankListVideoBean>{
            override fun onSubscribe(d: Disposable) {
            }

            override fun onNext(t: RankListVideoBean) {
                val list:MutableList<VideoBean> = ArrayList()
                for(m in t.itemList){
                    list.add(
                        VideoBean(
                        m.data.id,
                        m.data.title,
                        m.data.author.name,
                        m.data.cover.feed,
                        m.data.playUrl,
                        m.data.description,
                        PersonalBean(m.data.author.id,m.data.author.icon,DefaultUtil.defaultCoverUrl,m.data.author.description,m.data.author.name,"",""),
                            m.data.consumption
                        )
                    )
                }
                weeklyListLD.value = list
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
     * 加载月排行
     */
    fun loadMonthlyListMsg(){
        loadState.value = LoadState.LOADING
        myRepository.getRankListMsg("monthly",object :Observer<RankListVideoBean>{
            override fun onSubscribe(d: Disposable) {
            }

            override fun onNext(t: RankListVideoBean) {
                val list:MutableList<VideoBean> = ArrayList()
                for(m in t.itemList){
                    list.add(
                        VideoBean(
                            m.data.id,
                            m.data.title,
                            m.data.author.name,
                            m.data.cover.feed,
                            m.data.playUrl,
                            m.data.description,
                            PersonalBean(m.data.author.id,m.data.author.icon,DefaultUtil.defaultCoverUrl,m.data.author.description,m.data.author.name,"",""),
                            m.data.consumption
                        )
                    )
                }
                monthlyListLD.value = list
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
     * 加载总排行
     */
    fun loadHistoricalListMsg(){
        loadState.value = LoadState.LOADING
        myRepository.getRankListMsg("historical",object :Observer<RankListVideoBean>{
            override fun onSubscribe(d: Disposable) {
            }

            override fun onNext(t: RankListVideoBean) {
                val list:MutableList<VideoBean> = ArrayList()
                for(m in t.itemList){
                    list.add(
                        VideoBean(
                            m.data.id,
                            m.data.title,
                            m.data.author.name,
                            m.data.cover.feed,
                            m.data.playUrl,
                            m.data.description,
                            PersonalBean(m.data.author.id,m.data.author.icon,DefaultUtil.defaultCoverUrl,m.data.author.description,m.data.author.name,"",""),
                            m.data.consumption
                        )
                    )
                }
                historicalListLD.value = list
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


    private fun showNetWorkError() {
        Toast.makeText(MyApplication.context!!,"请检查你的网络!", Toast.LENGTH_SHORT).show()
    }
}