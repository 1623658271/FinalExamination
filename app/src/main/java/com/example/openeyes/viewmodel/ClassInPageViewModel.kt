package com.example.openeyes.viewmodel

import android.text.TextUtils
import androidx.lifecycle.*
import com.example.openeyes.model.ClassDeepMoreMsgBean
import com.example.openeyes.model.ClassDeepMsgBean
import com.example.openeyes.model.PersonalBean
import com.example.openeyes.model.VideoBean
import com.example.openeyes.utils.DefaultUtil
import com.example.openeyes.utils.LoadState
import io.reactivex.rxjava3.core.Observer
import io.reactivex.rxjava3.disposables.Disposable

/**
 * description ： 分类进入的页面的viewModel
 * author : lfy
 * email : 1623658271@qq.com
 * date : 2022/8/3 15:34
 */
class ClassInPageViewModel:BaseViewModel() {
    //数据
    private val classInLD:MutableLiveData<MutableList<Map<String,Any>>> by lazy {
        MutableLiveData()
    }
    val mapList:LiveData<MutableList<Map<String,Any>>>
    get() = classInLD

    //下一页数据Url
    private val nextPageUrl:MutableLiveData<String> by lazy {
        MutableLiveData()
    }

    /**
     * 加载初始数据
     */
    fun loadClassInMsg(id:String,udid:String){
        loadState.value = LoadState.LOADING
        myRepository.getClassInMsg(id,udid,object :Observer<ClassDeepMsgBean>{
            override fun onSubscribe(d: Disposable) {
            }

            override fun onNext(t: ClassDeepMsgBean) {
                nextPageUrl.value = t.nextPageUrl?:""
                val mapList:MutableList<Map<String,Any>> = ArrayList()
                for(m in t.itemList) {
                    if (m.type == "textCard") {
                        val map = HashMap<String,Any>()
                        map["type"] = m.type ?: ""
                        map["content"] = m.data.text ?: ""
                        mapList.add(map)

                    }else{
                        val map = HashMap<String,Any>()
                        map["type"] = m.type ?:""
                        map["content"] =
                            VideoBean(
                                m.data.content?.data?.id ?: m.data.id ?: 0,
                                m.data.content?.data?.title ?: m.data.title ?: "",
                                m.data.header?.description ?: m.data.author?.name ?: "",
                                m.data.content?.data?.cover?.feed ?: m.data.cover?.feed ?: "",
                                m.data.content?.data?.playUrl ?: m.data.playUrl ?: "",
                                m.data.content?.data?.description ?: m.data.description ?: "",
                                PersonalBean(
                                    m.data.content?.data?.author?.id ?: m.data.author?.id ?: 0,
                                    m.data.content?.data?.author?.icon ?: m.data.author?.icon
                                    ?: "",
                                    DefaultUtil.defaultCoverUrl,
                                    m.data.content?.data?.author?.description
                                        ?: m.data.author?.description ?: "",
                                    m.data.content?.data?.author?.name ?: m.data.author?.name
                                    ?: "",
                                    "", ""
                                ), m.data.consumption
                            )
                        mapList.add(map)
                    }
                }
                classInLD.value = mapList
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
     * 加载更多数据
     */
    fun loadClassInMoreMsg(pathId:String,udid:String){
        loadStateMore.value = LoadState.LOADING
        if(!TextUtils.isEmpty(nextPageUrl.value)) {
            val m = nextPageUrl.value!!.split('?').last().split('&')
            val start = m[0].filter { it.isDigit() }.toInt()
            val num = m[1].filter { it.isDigit() }.toInt()
            myRepository.getClassInMoreMsg(pathId, start, num, udid,object : Observer<ClassDeepMoreMsgBean> {
                override fun onSubscribe(d: Disposable) {

                }

                override fun onNext(t: ClassDeepMoreMsgBean) {
                    nextPageUrl.value = t.nextPageUrl ?: ""
                    val mapList: MutableList<Map<String, Any>> = ArrayList()
                    mapList.addAll(classInLD.value!!)
                    for (m in t.itemList) {
                        val map = HashMap<String, Any>()
                        map["type"] = m.type ?: ""
                        map["content"] =
                            VideoBean(
                                m.data.content.data.id ?: 0,
                                m.data.content.data.title ?: "",
                                m.data.header.description ?: "",
                                m.data.content.data.cover.feed ?: "",
                                m.data.content.data.playUrl ?: "",
                                m.data.content.data.description ?: "",
                                PersonalBean(
                                    m.data.content.data.author.id ?: 0,
                                    m.data.content.data.author.icon ?: "",
                                    DefaultUtil.defaultCoverUrl,
                                    m.data.content.data.author.description ?: "",
                                    m.data.content.data.author.name ?: "",
                                    "", ""
                                ), m.data.content.data.consumption)
                        mapList.add(map)
                    }
                    if(mapList.size==classInLD.value!!.size){
                        loadStateMore.value = LoadState.EMPTY
                    }else{
                        classInLD.value = mapList
                        loadStateMore.value = LoadState.SUCCESS
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
}