package com.example.openeyes.viewmodel

import android.text.TextUtils
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.openeyes.MyApplication
import com.example.openeyes.model.*
import io.reactivex.rxjava3.core.Observer
import io.reactivex.rxjava3.disposables.Disposable
import com.example.openeyes.respository.MyRepository
import com.example.openeyes.utils.DefaultUtil
import com.example.openeyes.utils.LoadState

/**
 * description ： TODO:类的作用
 * author : lfy
 * email : 1623658271@qq.com
 * date : 2022/8/2 17:10
 */
class DiscoverPageViewModel:ViewModel() {
    //初始化状态
    private var loadState1 = MutableLiveData<LoadState>()
    val state1:LiveData<LoadState>
        get() = loadState1
    private var loadState2 = MutableLiveData<LoadState>()
    val state2:LiveData<LoadState>
        get() = loadState2
    //仓库
    private val myRepository by lazy {
        MyRepository()
    }
    //发现的推荐页数据
    private val discoverRecList: MutableLiveData<MutableList<Map<String,Any>>> by lazy {
        MutableLiveData<MutableList<Map<String, Any>>>().also {
            loadRecMsg()
        }
    }
    val dataList: LiveData<MutableList<Map<String,Any>>>
        get() = discoverRecList
    //下一页url
    private val nextPageUrl: MutableLiveData<String> by lazy {
        MutableLiveData()
    }
    //发现页的分类数据
    private val discoverClassList:MutableLiveData<MutableList<ClassBean>> by lazy {
        MutableLiveData<MutableList<ClassBean>>().also {
            loadClassMsg()
        }
    }
    val classList:LiveData<MutableList<ClassBean>>
        get() = discoverClassList


    /**
     * 加载推荐数据
     */
    fun loadRecMsg(){
        loadState1.value = LoadState.LOADING
        myRepository.getRecMsg(object :Observer<SocialRecommendBean>{
            override fun onSubscribe(d: Disposable) {
            }
            override fun onNext(t: SocialRecommendBean) {
                nextPageUrl.value = t.nextPageUrl?:""
                val mapListX:MutableList<Map<String,Any>> = ArrayList()
                for(m in t.itemList){
                    if(m.data.content!=null) {
                        if (m.data.content.type == "ugcPicture"){
                            val map = HashMap<String,Any>()
                            map["type"] = "ugcPicture"
                            map["message"] = PicsBean(
                                m.data.content.data.description,
                                m.data.content.data.cover.feed,
                                m.data.content.data.urls!!.toMutableList(),
                                m.data.content.data.consumption,
                                PersonalBean(
                                    m.data.content.data.owner.uid,
                                    m.data.content.data.owner.avatar,
                                    m.data.content.data.owner.cover?: DefaultUtil.defaultCoverUrl,
                                    m.data.content.data.owner.description?:"",
                                    m.data.content.data.owner.nickname,
                                    m.data.content.data.owner.city?:"",
                                    m.data.content.data.owner.job?:""
                                )
                            )
                            mapListX.add(map)
                        }else if(m.data.content.type == "video"){
                            val map = HashMap<String,Any>()
                            map["type"] = "video"
                            map["message"] = VideoBean(
                                m.data.content.data.id,
                                m.data.content.data.description,
                                m.data.content.data.owner.nickname,
                                m.data.content.data.cover.feed,
                                m.data.content.data.playUrl?:"",
                                m.data.content.data.description,
                                PersonalBean(
                                    m.data.content.data.owner.uid,
                                    m.data.content.data.owner.avatar,
                                    m.data.content.data.owner.cover?: DefaultUtil.defaultCoverUrl,
                                    m.data.content.data.owner.description?:"",
                                    m.data.content.data.owner.nickname,
                                    m.data.content.data.owner.city?:"",
                                    m.data.content.data.owner.job?:""
                                ),
                                m.data.content.data.consumption
                            )
                            mapListX.add(map)
                        }
                    }
                }
                discoverRecList.value = mapListX
                loadState1.value = LoadState.SUCCESS
            }

            override fun onError(e: Throwable) {
                loadState1.value = LoadState.ERROR
            }

            override fun onComplete() {
            }

        })
    }

    /**
     * 加载发现推荐更多数据
     */
    fun loadRecMoreMsg():Boolean{
        return if(!TextUtils.isEmpty(nextPageUrl.value)) {
            val m = nextPageUrl.value!!.split("?").last().split("&")
            val start = m[0].filter { it.isDigit() }.toLong()
            val page = m[1].filter { it.isDigit() }.toInt()
            myRepository.getRecMore(start, page,object :Observer<SocialRecommendBean>{
                override fun onSubscribe(d: Disposable) {
                }

                override fun onNext(t: SocialRecommendBean) {
                    nextPageUrl.value = t.nextPageUrl?:""
                    val mapListX:MutableList<Map<String,Any>> = ArrayList()
                    mapListX.addAll(discoverRecList.value!!)
                    for(m in t.itemList){
                        if(m.data.content!=null) {
                            if (m.data.content.type == "ugcPicture"){
                                val map = HashMap<String,Any>()
                                map["type"] = "ugcPicture"
                                map["message"] = PicsBean(
                                    m.data.content.data.description,
                                    m.data.content.data.cover.feed,
                                    m.data.content.data.urls!!.toMutableList(),
                                    m.data.content.data.consumption,
                                    PersonalBean(
                                        m.data.content.data.owner.uid,
                                        m.data.content.data.owner.avatar,
                                        m.data.content.data.owner.cover?: DefaultUtil.defaultCoverUrl,
                                        m.data.content.data.owner.description?:"",
                                        m.data.content.data.owner.nickname,
                                        m.data.content.data.owner.city?:"",
                                        m.data.content.data.owner.job?:""
                                    )
                                )
                                mapListX.add(map)
                            }else if(m.data.content.type == "video"){
                                val map = HashMap<String,Any>()
                                map["type"] = "video"
                                map["message"] = VideoBean(
                                    m.data.content.data.id,
                                    m.data.content.data.description,
                                    m.data.content.data.owner.nickname,
                                    m.data.content.data.cover.feed,
                                    m.data.content.data.playUrl?:"",
                                    m.data.content.data.description,
                                    PersonalBean(
                                        m.data.content.data.owner.uid,
                                        m.data.content.data.owner.avatar,
                                        m.data.content.data.owner.cover?: DefaultUtil.defaultCoverUrl,
                                        m.data.content.data.owner.description?:"",
                                        m.data.content.data.owner.nickname,
                                        m.data.content.data.owner.city?:"",
                                        m.data.content.data.owner.job?:""
                                    ),
                                    m.data.content.data.consumption
                                )
                                mapListX.add(map)
                            }
                        }
                    }
                    discoverRecList.value = mapListX
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


    /**
     * 加载分类页数据
     */
    fun loadClassMsg(){
        loadState2.value = LoadState.LOADING
        myRepository.getFindMoreClassMsg(object :Observer<FindMoreClassBean>{
            override fun onSubscribe(d: Disposable) {
            }

            override fun onNext(t: FindMoreClassBean) {
                val list:MutableList<ClassBean> = ArrayList()
                for (m in t.itemList) {
                    if (!TextUtils.isEmpty(m.data.icon) && !TextUtils.isEmpty(m.data.title)) {
                        list.add(
                            ClassBean(
                                m.data.id,
                                m.data.icon,
                                m.data.title,
                                m.data.description,
                                m.data.actionUrl,
                                m.data.follow.itemId
                            )
                        )
                    }
                }
                discoverClassList.value = list
                loadState2.value = LoadState.SUCCESS
            }

            override fun onError(e: Throwable) {
                loadState2.value = LoadState.ERROR
                showNetWorkError()
            }

            override fun onComplete() {
            }

        })
    }

    private fun showNetWorkError() {
        Toast.makeText(MyApplication.context!!,"请检查你的网络!",Toast.LENGTH_SHORT).show()
    }
}