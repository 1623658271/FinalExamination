package com.example.openeyes.viewmodel

import android.text.TextUtils
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.openeyes.model.*
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
class HomePageViewModel:BaseViewModel(){
    private val loadState2:MutableLiveData<LoadState> by lazy {
        MutableLiveData()
    }
    val state2:LiveData<LoadState>
    get() = loadState2

    //首页精选数据
    private val homepageListLD: MutableLiveData<MutableList<VideoBean>> by lazy {
        MutableLiveData<MutableList<VideoBean>>().also {
            loadDailyMsg()
        }
    }
    val dataList: LiveData<MutableList<VideoBean>>
    get() = homepageListLD
    //主题数据
    private val allDynLD:MutableLiveData<MutableList<AllDynamicsBean.TabInfo.Tab>> by lazy {
        MutableLiveData()
    }
    val allDynList:LiveData<MutableList<AllDynamicsBean.TabInfo.Tab>>
    get() = allDynLD
    //推荐主题数据
    val recListLD:MutableLiveData<MutableList<DynamicMsgBean.Item>> by lazy {
        MutableLiveData()
    }
    val recDynList:LiveData<MutableList<DynamicMsgBean.Item>>
    get() = recListLD

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
                val list:MutableList<VideoBean> = ArrayList()
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
//                    else if(m.type == "autoPlayFollowCard"){
//                        list.add(
//                            VideoBean(
//                                m.data.content.data.id,
//                                m.data.content.data.description,
//                                m.data.header.issuerName,
//                                m.data.content.data.cover.feed,
//                                m.data.content.data.playUrl,
//                                m.data.content.data.description,
//                                PersonalBean(
//                                    m.data.content.data.owner.uid ?: 0,
//                                    m.data.content.data.owner.avatar ?: "",
//                                    m.data.content.data.owner.cover?:DefaultUtil.defaultCoverUrl,
//                                    m.data.content.data.owner.description ?: "",
//                                    m.data.content.data.owner.nickname ?: "",
//                                    m.data.content.data.owner.city?:"",
//                                    m.data.content.data.owner.job?:""
//                                ), m.data.content.data.consumption
//                            )
//                        )
//                    }
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
    fun loadDailyMore(){
        loadStateMore.value = LoadState.LOADING
        if(!TextUtils.isEmpty(nextPageUrl.value)) {
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

                    if(list.size==homepageListLD.value!!.size){
                        loadStateMore.value = LoadState.EMPTY
                    }else{
                        loadStateMore.value = LoadState.SUCCESS
                        homepageListLD.value = list
                    }
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
        }
    }

    /**
     * 获取所有主题列表
     */
    fun loadAllDyn(){
        loadState2.value = LoadState.LOADING
        myRepository.getAllDynamics(object :Observer<AllDynamicsBean>{
            override fun onSubscribe(d: Disposable) {
            }

            override fun onNext(t: AllDynamicsBean) {
                allDynLD.value = t.tabInfo.tabList.toMutableList()
                loadState2.value = LoadState.SUCCESS
            }

            override fun onError(e: Throwable) {
                showNetWorkError()
                loadState2.value = LoadState.ERROR
            }

            override fun onComplete() {
            }

        })
    }
    /**
     * 获取推荐主题数据
     */
    fun loadRecDyn(){
        loadState2.value = LoadState.LOADING
        myRepository.getRecDynamicMsg(object :Observer<DynamicMsgBean>{
            override fun onSubscribe(d: Disposable) {
            }

            override fun onNext(t: DynamicMsgBean) {
                recListLD.value = t.itemList.toMutableList()
                loadState2.value = LoadState.SUCCESS
            }

            override fun onError(e: Throwable) {
                showNetWorkError()
                loadState2.value = LoadState.ERROR
            }

            override fun onComplete() {
            }

        })
    }
}