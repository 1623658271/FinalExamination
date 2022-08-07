package com.example.openeyes.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.openeyes.model.PersonalBean
import com.example.openeyes.model.SpecialInBean
import com.example.openeyes.model.SpecialPageBean
import com.example.openeyes.model.VideoBean
import com.example.openeyes.utils.DefaultUtil
import com.example.openeyes.utils.LoadState
import io.reactivex.rxjava3.core.Observer
import io.reactivex.rxjava3.disposables.Disposable

/**
 * description ： 专题活动的viewModel
 * author : lfy
 * email : 1623658271@qq.com
 * date : 2022/8/4 16:23
 */
class SpecialInPageViewModel:BaseViewModel() {
    //专题数据
    private val specialInLD:MutableLiveData<SpecialPageBean> by lazy {
        MutableLiveData()
    }
    val specialInData:LiveData<SpecialPageBean>
    get() = specialInLD

    /**
     * 加载专题数据
     */
    fun loadSpecialInMsg(id:String){
        loadState.value = LoadState.LOADING
        myRepository.getSpecialInMsg(id,object :Observer<SpecialInBean>{
            override fun onSubscribe(d: Disposable) {
            }

            override fun onNext(t: SpecialInBean) {
                val list:MutableList<VideoBean> = ArrayList()
                val bean:SpecialPageBean = SpecialPageBean(
                    t.headerImage,t.brief,t.text, with(list) {
                        for (m in t.itemList) {
                            add(
                                VideoBean(
                                    m.data.content.data.id?:0,
                                    m.data.content.data.title?:"",
                                    m.data.content.data.author?.name?:"",
                                    m.data.content.data.cover?.feed?:"",
                                    m.data.content.data.playUrl?:"",
                                    m.data.content.data.description?:"",
                                    PersonalBean(
                                        m.data.content.data.author?.id?:0,
                                        m.data.content.data.author?.icon?:"",
                                        DefaultUtil.defaultCoverUrl,
                                        m.data.content.data.author?.description?:"",
                                        m.data.content.data.author?.name?:"",
                                        "", ""
                                    ),
                                    m.data.content.data.consumption
                                )
                            )
                        }
                        this
                    }
                )
                specialInLD.value = bean
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
}