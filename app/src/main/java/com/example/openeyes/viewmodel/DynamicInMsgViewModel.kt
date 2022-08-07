package com.example.openeyes.viewmodel

import android.text.TextUtils
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.openeyes.model.*
import com.example.openeyes.utils.DefaultUtil
import com.example.openeyes.utils.LoadState
import io.reactivex.rxjava3.core.Observer
import io.reactivex.rxjava3.disposables.Disposable

/**
 * description ： 主题最终页的viewModel
 * author : lfy
 * email : 1623658271@qq.com
 * date : 2022/8/6 15:38
 */
class DynamicInMsgViewModel:BaseViewModel() {
    private val dynamicInMsgLD:MutableLiveData<MutableList<Map<String,Any>>> by lazy {
        MutableLiveData()
    }
    val dynMapList:LiveData<MutableList<Map<String,Any>>>
    get() = dynamicInMsgLD

    //下一页数据url
    private val nextPageUrl:MutableLiveData<String> by lazy {
        MutableLiveData()
    }

    /**
     * 加载主题最终页数据
     */
    fun loadDynInMsg(id:Int){
        loadState.value = LoadState.LOADING
        Log.e("lfy", "loadDynInMsg: id=$id", )
        myRepository.getDynamicInMsg(id,object :Observer<DynamicInBean>{
            override fun onSubscribe(d: Disposable) {
            }

            override fun onNext(t: DynamicInBean) {
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
                                ConsumptionBean(m.data.content.data.consumption.collectionCount,
                                    m.data.content.data.consumption.shareCount,
                                    m.data.content.data.consumption.replyCount,
                                    m.data.content.data.consumption.realCollectionCount),
                                PersonalBean(
                                    m.data.content.data.owner?.uid?:m.data.content.data.author?.id?:0,
                                    m.data.content.data.owner?.avatar?:m.data.content.data.author?.icon?:"",
                                    m.data.content.data.owner?.cover?: DefaultUtil.defaultCoverUrl,
                                    m.data.content.data.owner?.description?:"",
                                    m.data.content.data.owner?.nickname?:m.data.content.data.author?.name?:"",
                                    m.data.content.data.owner?.city?:"",
                                    m.data.content.data.owner?.job?:""
                                )
                            )
                            mapListX.add(map)
                        }else if(m.data.content.type == "video"){
                            val map = HashMap<String,Any>()
                            map["type"] = "video"
                            map["message"] = VideoBean(
                                m.data.content.data.id,
                                m.data.content.data.description,
                                m.data.content.data.owner?.nickname?:m.data.content.data.author?.name?:"",
                                m.data.content.data.cover.feed?:DefaultUtil.defaultCoverUrl,
                                m.data.content.data.playUrl?:"",
                                m.data.content.data.description,
                                PersonalBean(
                                    m.data.content.data.owner?.uid?:m.data.content.data.author?.id?:0,
                                    m.data.content.data.owner?.avatar?:m.data.content.data.author?.icon?:"",
                                    m.data.content.data.owner?.cover?: DefaultUtil.defaultCoverUrl,
                                    m.data.content.data.owner?.description?:"",
                                    m.data.content.data.owner?.nickname?:m.data.content.data.author?.name?:"",
                                    m.data.content.data.owner?.city?:"",
                                    m.data.content.data.owner?.job?:""
                                ),
                                ConsumptionBean(m.data.content.data.consumption.collectionCount,
                                    m.data.content.data.consumption.shareCount,
                                    m.data.content.data.consumption.replyCount,
                                    m.data.content.data.consumption.realCollectionCount)
                            )
                            mapListX.add(map)
                        }
                    }
                }
                dynamicInMsgLD.value = mapListX
                loadState.value = LoadState.SUCCESS
            }

            override fun onError(e: Throwable) {
                loadState.value = LoadState.ERROR
                showNetWorkError()
                Log.e("lfy", "onError:$e ", )
            }

            override fun onComplete() {
            }
        })
    }

    /**
     * 加载更多
     */
    fun loadDynInMoreMsg(){
        loadStateMore.value = LoadState.LOADING
        if(!TextUtils.isEmpty(nextPageUrl.value)){
            val url = nextPageUrl.value!!.split('?').last().split('&')
            val start = url[0].filter { it.isDigit() }.toInt()
            val num = url[1].filter { it.isDigit() }.toInt()
            val id = url[2].filter { it.isDigit() }.toInt()
            myRepository.getDynamicInMoreMsg(start,num,id,object :Observer<DynamicInBean>{
                override fun onSubscribe(d: Disposable) {
                }

                override fun onNext(t: DynamicInBean) {
                    nextPageUrl.value = t.nextPageUrl?:""
                    val mapListX:MutableList<Map<String,Any>> = ArrayList()
                    mapListX.addAll(dynamicInMsgLD.value!!)
                    for(m in t.itemList){
                        if(m.data.content!=null) {
                            if (m.data.content.type == "ugcPicture"){
                                val map = HashMap<String,Any>()
                                map["type"] = "ugcPicture"
                                map["message"] = PicsBean(
                                    m.data.content.data.description,
                                    m.data.content.data.cover.feed,
                                    m.data.content.data.urls!!.toMutableList(),
                                    ConsumptionBean(m.data.content.data.consumption.collectionCount,
                                        m.data.content.data.consumption.shareCount,
                                        m.data.content.data.consumption.replyCount,
                                        m.data.content.data.consumption.realCollectionCount),
                                    PersonalBean(
                                        m.data.content.data.owner?.uid?:m.data.content.data.author?.id?:0,
                                        m.data.content.data.owner?.avatar?:m.data.content.data.author?.icon?:"",
                                        m.data.content.data.owner?.cover?: DefaultUtil.defaultCoverUrl,
                                        m.data.content.data.owner?.description?:"",
                                        m.data.content.data.owner?.nickname?:m.data.content.data.author?.name?:"",
                                        m.data.content.data.owner?.city?:"",
                                        m.data.content.data.owner?.job?:""
                                    )
                                )
                                mapListX.add(map)
                            }else if(m.data.content.type == "video"){
                                val map = HashMap<String,Any>()
                                map["type"] = "video"
                                map["message"] = VideoBean(
                                    m.data.content.data.id,
                                    m.data.content.data.description,
                                    m.data.content.data.owner?.nickname?:m.data.content.data.author?.name?:"",
                                    m.data.content.data.cover.feed?:DefaultUtil.defaultCoverUrl,
                                    m.data.content.data.playUrl?:"",
                                    m.data.content.data.description,
                                    PersonalBean(
                                        m.data.content.data.owner?.uid?:m.data.content.data.author?.id?:0,
                                        m.data.content.data.owner?.avatar?:m.data.content.data.author?.icon?:"",
                                        m.data.content.data.owner?.cover?: DefaultUtil.defaultCoverUrl,
                                        m.data.content.data.owner?.description?:"",
                                        m.data.content.data.owner?.nickname?:m.data.content.data.author?.name?:"",
                                        m.data.content.data.owner?.city?:"",
                                        m.data.content.data.owner?.job?:""
                                    ),
                                    ConsumptionBean(m.data.content.data.consumption.collectionCount,
                                        m.data.content.data.consumption.shareCount,
                                        m.data.content.data.consumption.replyCount,
                                        m.data.content.data.consumption.realCollectionCount)
                                )
                                mapListX.add(map)
                            }
                        }
                    }
                    if(mapListX.size!=dynamicInMsgLD.value!!.size){
                        dynamicInMsgLD.value = mapListX
                        loadStateMore.value = LoadState.SUCCESS
                    }else{
                        loadStateMore.value = LoadState.EMPTY
                    }
                }

                override fun onError(e: Throwable) {
                    loadStateMore.value = LoadState.ERROR
                    Log.e("lfy", "onError: $e", )
                    showNetWorkError()
                }

                override fun onComplete() {
                }

            })
        }else{
            loadStateMore.value = LoadState.EMPTY
        }
    }
}