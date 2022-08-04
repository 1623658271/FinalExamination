package com.example.openeyes.viewmodel

import android.view.View
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.openeyes.MyApplication
import com.example.openeyes.model.CommentBean
import com.example.openeyes.model.CommentNormalBean
import com.example.openeyes.model.PersonalBean
import com.example.openeyes.model.RelatedVideoBean
import com.example.openeyes.respository.MyRepository
import com.example.openeyes.utils.DefaultUtil
import com.example.openeyes.utils.LoadState
import io.reactivex.rxjava3.core.Observer
import io.reactivex.rxjava3.disposables.Disposable

/**
 * description ： 视频播放页的viewModel
 * author : lfy
 * email : 1623658271@qq.com
 * date : 2022/8/3 10:12
 */
class VideoPlayPageViewModel:ViewModel() {

    //加载状态
    private var loadState = MutableLiveData<LoadState>()
    val state:LiveData<LoadState>
        get() = loadState
    //仓库
    private val myRepository by lazy {
        MyRepository()
    }
    //相关视频数据
    private val relatedVideoListLD:MutableLiveData<MutableList<RelatedVideoBean.Item>> by lazy {
        MutableLiveData()
    }
    val relatedList:LiveData<MutableList<RelatedVideoBean.Item>>
        get() = relatedVideoListLD
    //评论数据
    private val commentListLD:MutableLiveData<MutableList<CommentNormalBean>> by lazy {
        MutableLiveData()
    }
    val commentList:LiveData<MutableList<CommentNormalBean>>
        get() = commentListLD

    /**
     * 加载相关视频数据
     */
    fun loadRelatedMsg(id:Int){
        loadState.value = LoadState.LOADING
        myRepository.getRelatedVideoMsg(id,object :Observer<RelatedVideoBean>{
            override fun onSubscribe(d: Disposable) {
            }

            override fun onNext(t: RelatedVideoBean) {
                relatedVideoListLD.value = t.itemList as MutableList<RelatedVideoBean.Item>
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
     * 加载评论数据
     */
    fun loadCommentMsg(id:Int){
        myRepository.getVideoCommentMsg(id,object :Observer<CommentBean>{
            override fun onSubscribe(d: Disposable) {
            }

            override fun onNext(t: CommentBean) {
                var list:MutableList<CommentNormalBean> = ArrayList()
                for (m in t.itemList) {
                    if (m.type == "reply" && m.data.user != null) {
                        list.add(
                            CommentNormalBean(
                                m.data.message?:"", m.data.likeCount?:0, PersonalBean(
                                    m.data.user.uid, m.data.user.avatar,
                                    m.data.user.cover?.toString()?: DefaultUtil.defaultCoverUrl,
                                    m.data.user.description?.toString()?:"", m.data.user.nickname,
                                    m.data.user.city?.toString()?:"",m.data.user.job?.toString()?:""
                                )
                            )
                        )
                    }
                }
                for(i in list.indices){
                    for (j in i + 1 until list.size) {
                        if (list[i].likeCount < list[j].likeCount) {
                            var temp = list[i]
                            list[i] = list[j]
                            list[j] = temp
                        }
                    }
                }
                commentListLD.value = list
            }

            override fun onError(e: Throwable) {
                showNetWorkError()
            }

            override fun onComplete() {
            }

        })
    }

    private fun showNetWorkError() {
        Toast.makeText(MyApplication.context!!,"请检查你的网络!", Toast.LENGTH_SHORT).show()
    }
}