package com.example.openeyes.viewmodel

import android.text.TextUtils
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.openeyes.MyApplication
import com.example.openeyes.model.DailyHandpickBean
import com.example.openeyes.model.HomepageMoreBean
import com.example.openeyes.model.PersonalBean
import com.example.openeyes.model.VideoBean
import com.example.openeyes.respository.MyRepository
import com.example.openeyes.utils.DefaultUtil
import com.example.openeyes.utils.LoadState
import io.reactivex.rxjava3.core.Observer
import io.reactivex.rxjava3.disposables.Disposable

/**
 * description ： 首页的viewModel
 * author : lfy
 * email : 1623658271@qq.com
 * date : 2022/8/2 15:10
 */
class HomePageViewModel:ViewModel(){
    //初始化状态
    private var loadState = MutableLiveData<LoadState>()
    val state:LiveData<LoadState>
    get() = loadState
    //仓库
    private val myRepository by lazy {
        MyRepository()
    }
    //首页精选数据
    private val homepageListLD: MutableLiveData<MutableList<VideoBean>> by lazy {
        MutableLiveData<MutableList<VideoBean>>().also {
            loadDailyMsg()
        }
    }
    val dataList: LiveData<MutableList<VideoBean>>
    get() = homepageListLD
    //下一页数据url
    private val nextPageUrl:MutableLiveData<String> by lazy {
        MutableLiveData()
    }

    /**
     * 加载首页精选内容数据
     */
    fun loadDailyMsg(){
        loadState.value = LoadState.LOADING
        myRepository.getDailyHandpickMsg(object :Observer<DailyHandpickBean>{
            override fun onSubscribe(d: Disposable) {
            }
            override fun onNext(t: DailyHandpickBean) {
                nextPageUrl.value = t.nextPageUrl?:""
                var list:MutableList<VideoBean> = ArrayList()
                for (m in t.itemList) {
                    if (m.type == "followCard") {
                        list.add(
                            VideoBean(
                                m.data.content.data.id,
                                m.data.content.data.title,
                                m.data.header.title,
                                m.data.content.data.cover.feed,
                                m.data.content.data.playUrl,
                                m.data.content.data.description,
                                PersonalBean(
                                    m.data.content.data.author.id ?: 0,
                                    m.data.content.data.author.icon ?: "",
                                    DefaultUtil.defaultCoverUrl,
                                    m.data.content.data.author.description ?: "",
                                    m.data.content.data.author.name ?: "", "", ""
                                ), m.data.content.data.consumption
                            )
                        )

                    }
                }
                homepageListLD.value = list
                loadState.value = LoadState.SUCCESS
            }

            override fun onError(e: Throwable) {
                loadState.value = LoadState.ERROR
            }

            override fun onComplete() {
            }
        })

    }

    /**
     * 加载首页精选更多内容
     */
    fun loadDailyMore():Boolean{
        return if(!TextUtils.isEmpty(nextPageUrl.value)) {
            val url = nextPageUrl.value!!.split('?').last().split('&')
            val date = url[0].filter { it.isDigit() }.toLong()
            val num = url[1].filter { it.isDigit() }.toInt()
            myRepository.getDailyHandpickMoreMsg(date,num,object :Observer<HomepageMoreBean>{
                override fun onSubscribe(d: Disposable) {
                }

                override fun onNext(t: HomepageMoreBean) {
                    nextPageUrl.value = t.nextPageUrl?:""
                    val list:MutableList<VideoBean> = ArrayList()
                    list.addAll(homepageListLD.value!!)
                    for (m in t.itemList) {
                        if(m.data!=null) {
                            if (m.type == "followCard" && m.data.content != null) {
                                list.add(
                                    VideoBean(
                                        m.data.content.data.id,
                                        m.data.content.data.title,
                                        m.data.header!!.title,
                                        m.data.content.data.cover.feed,
                                        m.data.content.data.playUrl,
                                        m.data.content.data.description,
                                        PersonalBean(
                                            m.data.content.data.author.id,
                                            m.data.content.data.author.icon,
                                            DefaultUtil.defaultCoverUrl,
                                            m.data.content.data.author.description,
                                            m.data.content.data.author.name,
                                            "",
                                            ""
                                        ), m.data.content.data.consumption
                                    )
                                )
                            }

                        }
                    }
                    homepageListLD.value = list
                }

                override fun onError(e: Throwable) {
                    showNetWorkError()
                }

                override fun onComplete() {

                }

            })

            true
        }else{
            false
        }
    }
    private fun showNetWorkError() {
        Toast.makeText(MyApplication.context!!,"请检查你的网络!", Toast.LENGTH_SHORT).show()
    }
}