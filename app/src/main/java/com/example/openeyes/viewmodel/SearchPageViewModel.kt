package com.example.openeyes.viewmodel

import android.text.TextUtils
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.openeyes.MyApplication
import com.example.openeyes.model.*
import com.example.openeyes.respository.MyRepository
import com.example.openeyes.utils.DecodeUtil
import com.example.openeyes.utils.DefaultUtil
import com.example.openeyes.utils.LoadState
import io.reactivex.rxjava3.core.Observer
import io.reactivex.rxjava3.disposables.Disposable

/**
 * description ： TODO:类的作用
 * author : lfy
 * email : 1623658271@qq.com
 * date : 2022/8/3 16:57
 */
class SearchPageViewModel:ViewModel() {
    //加载状态
    private var loadState = MutableLiveData<LoadState>()
    val state: LiveData<LoadState>
        get() = loadState
    //仓库
    private val myRepository by lazy {
        MyRepository()
    }
    //热搜关键词
    private val hotWordLD:MutableLiveData<HotSearchBean> by lazy {
        MutableLiveData<HotSearchBean>().also {
            loadHotWords()
        }
    }
    val hotWordsList:LiveData<HotSearchBean>
    get() = hotWordLD

    //搜索结果
    private val searchVideoLD:MutableLiveData<MutableList<VideoBean>> by lazy {
        MutableLiveData()
    }
    val videoBean:LiveData<MutableList<VideoBean>>
    get() = searchVideoLD
    //下一页数据url
    private val nextPageUrl:MutableLiveData<String> by lazy {
        MutableLiveData()
    }

    /**
     * 获取热搜关键词
     */
    fun loadHotWords(){
        myRepository.getHotSearchWordMsg(object :Observer<HotSearchBean>{
            override fun onSubscribe(d: Disposable) {
            }

            override fun onNext(t: HotSearchBean) {
                hotWordLD.value = t
            }

            override fun onError(e: Throwable) {
                showNetWorkError()
            }

            override fun onComplete() {
            }

        })
    }

    /**
     * 获取搜索数据
     */
    fun loadSearchMsg(query:String){
        loadState.value = LoadState.LOADING
        myRepository.getSearchMsg(query,object :Observer<SearchBean>{
            override fun onSubscribe(d: Disposable) {
            }

            override fun onNext(t: SearchBean) {
                searchVideoLD.value?.clear()
                val listResult:MutableList<VideoBean> = ArrayList()
                for(m in t.itemList){
                    if(m.type=="followCard"){
                        listResult.add(VideoBean(m.data.content!!.data.id?:0,m.data.content.data.title,m.data.content.data.author?.name?:"",
                            m.data.content.data.cover.feed,m.data.content.data.playUrl,m.data.content.data.description,
                            PersonalBean(m.data.content.data.author?.id?:0,m.data.content.data.author?.icon?:"",
                                DefaultUtil.defaultCoverUrl,m.data.content.data.author?.description?:"",
                                m.data.content.data.author?.name?:"","","")
                            ,m.data.content.data.consumption)
                        )
                    }
                }
                nextPageUrl.value = DecodeUtil.urlDecode(t.nextPageUrl?:"")
                searchVideoLD.value = listResult
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
     * 更多数据
     */
    fun loadSearchMore():Boolean{
        return if(!TextUtils.isEmpty(nextPageUrl.value)) {
            val url = nextPageUrl.value!!.split("?").last().split("&")
            val start = url[0].filter { it.isDigit() }.toInt()
            val num = url[1].filter { it.isDigit() }.toInt()
            val query = url[2].split("=").last()
            myRepository.getSearchMoreMsg(start,num,query,object :Observer<SearchMoreBean>{
                override fun onSubscribe(d: Disposable) {
                }

                override fun onNext(t: SearchMoreBean) {
                    nextPageUrl.value = DecodeUtil.urlDecode(t.nextPageUrl?:"")
                    val listResult:MutableList<VideoBean> = ArrayList()
                    listResult.addAll(searchVideoLD.value!!)
                    for (m in t.itemList) {
                        if (m.type == "followCard") {
                            listResult.add(
                                VideoBean(
                                    m.data.content.data.id ?: 0,
                                    m.data.content.data.title,
                                    m.data.content.data.author?.name ?: "",
                                    m.data.content.data.cover.feed ?: "",
                                    m.data.content.data.playUrl ?: "",
                                    m.data.content.data.description ?: "",
                                    PersonalBean(
                                        m.data.content.data.author?.id ?: 0,
                                        m.data.content.data.author?.icon ?: "",
                                        DefaultUtil.defaultCoverUrl,
                                        m.data.content.data.author?.description ?: "",
                                        m.data.content.data.author?.name ?: "","",""
                                    )
                                    ,m.data.content.data.consumption)
                            )
                        }
                    }
                    searchVideoLD.value = listResult
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