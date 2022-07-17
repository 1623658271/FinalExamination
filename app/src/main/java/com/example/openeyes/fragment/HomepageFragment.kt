package com.example.openeyes.fragment

import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import cn.jzvd.JzvdStd
import com.example.openeyes.MyApplication
import com.example.openeyes.R
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
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        bing = DataBindingUtil.inflate(inflater,R.layout.layout_homepage_fragment,container,false)
        return bing.root
    }

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
            viewModel.updateDailyHandpickViewModel()
            bing.srHomepage.isRefreshing = false
        }
        updateMessage()
        lifecycle.addObserver(adapter)
    }

    fun updateMessage(){
        viewModel.getDailyHandpickLiveData().observe(viewLifecycleOwner, Observer {
            val dataList = it.itemList
            beanList.clear()
            for (m in dataList) {
                if(m.data!=null) {
                    if (true) {
                        if (m.type == "followCard") {
                            beanList.add(
                                VideoBean(
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
        viewModel.updateDailyHandpickViewModel()
    }

    override fun onPause() {
        super.onPause()
    }

}