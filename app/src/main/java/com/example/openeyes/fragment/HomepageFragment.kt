package com.example.openeyes.fragment

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.openeyes.MyApplication
import com.example.openeyes.R
import com.example.openeyes.VideoPlayActivity
import com.example.openeyes.adapter.HomePageRVAdapter
import com.example.openeyes.databinding.LayoutHomepageFragmentBinding
import com.example.openeyes.model.ClassModel
import com.example.openeyes.model.PersonalModel
import com.example.openeyes.model.VideoBean
import com.example.openeyes.viewmodel.MyViewModel

/**
 * description ： TODO:类的作用
 * author : lfy
 * email : 1623658271@qq.com
 * date : 2022/7/15 08:49
 */
class HomepageFragment:Fragment() {
    private lateinit var bing:LayoutHomepageFragmentBinding
    private lateinit var adapter:HomePageRVAdapter
    private lateinit var beanList:MutableList<VideoBean>
    private lateinit var viewModel: MyViewModel
    private val TAG = "lfy"
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        bing = DataBindingUtil.inflate(inflater,R.layout.layout_homepage_fragment,container,false)
        return bing.root
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        beanList = ArrayList()
        viewModel = ViewModelProvider(
            this,
            ViewModelProvider.AndroidViewModelFactory(MyApplication.application!!)
        )[MyViewModel::class.java]
        adapter = HomePageRVAdapter(beanList)
        bing.rvHomepage.adapter = adapter
        bing.rvHomepage.layoutManager = LinearLayoutManager(MyApplication.context,RecyclerView.VERTICAL,false)
        bing.srHomepage.setOnRefreshListener {
            updateMessage()
            bing.srHomepage.isRefreshing = false
        }
        viewModel.getDailyHandpickLiveData().observe(viewLifecycleOwner, Observer {
            val dataList = it.itemList
            beanList.clear()
            for (m in dataList) {
                if(m.data!=null) {
                    if (true) {
                        if (m.type == "followCard") {
                            beanList.add(
                                VideoBean(
                                    m.data.content.data.id,
                                    m.data.content.data.title,
                                    m.data.header.title,
                                    m.data.content.data.cover.feed,
                                    m.data.content.data.playUrl,
                                    m.data.content.data.description,
                                    PersonalModel(m.data.content.data.author.icon,"http://img.kaiyanapp.com/f9a3fddd3f0941404f4b1d30235c2952.png?imageMogr2/quality/60/format/jpg",m.data.content.data.author.description,m.data.content.data.author.name)
                                )
                            )
                        }
                    }
                }
            }
            adapter.notifyDataSetChanged()
        })
        adapter.setClickListener(object :HomePageRVAdapter.OnSomethingClickedListener{
            override fun onVideoImageClickedListener(
                view: View,
                holder: RecyclerView.ViewHolder,
                position: Int,
                videoBeanList: MutableList<VideoBean>
            ) {
                val videoMessages = ArrayList<String>()
                videoMessages.add(videoBeanList[position].playUrl)
                videoMessages.add(videoBeanList[position].coverUrl)
                videoMessages.add(videoBeanList[position].bigTitle)
                videoMessages.add(videoBeanList[position].smallTitle)
                videoMessages.add(videoBeanList[position].description)
                val id = videoBeanList[position].id
                Log.d(TAG, "onVideoImageClickedListener: $videoMessages")
                VideoPlayActivity.startVideoPlayActivity(MyApplication.context!!,videoMessages,id)
            }

            override fun onAvatarImageClickedListener(
                view: View,
                holder: RecyclerView.ViewHolder,
                position: Int,
                videoBeanList: MutableList<VideoBean>
            ) {

            }

        })
        lifecycle.addObserver(adapter)
    }

    fun updateMessage(){
        viewModel.updateDailyHandpickViewModel()
    }

    override fun onPause() {
        super.onPause()
    }

}