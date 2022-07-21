package com.example.openeyes

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.openeyes.adapter.ClassInRVAdapter
import com.example.openeyes.api.URL
import com.example.openeyes.databinding.ActivityClassInBinding
import com.example.openeyes.model.ClassDeepMoreMsgModel
import com.example.openeyes.model.ClassDeepMsgModel
import com.example.openeyes.model.ClassModel
import com.example.openeyes.model.VideoBean
import com.example.openeyes.viewmodel.MyViewModel

class ClassInActivity : AppCompatActivity() {
    private lateinit var binding:ActivityClassInBinding
    private lateinit var viewModel: MyViewModel
    private lateinit var adapter:ClassInRVAdapter
    private lateinit var list: MutableList<ClassDeepMsgModel.Item>
    private lateinit var listMore:MutableList<ClassDeepMoreMsgModel.Item>
    private lateinit var classModel:ClassModel
    private var nextPageUrl:String = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_class_in)
        initData()
    }

    private fun initData(){
        viewModel = ViewModelProvider(this,ViewModelProvider.AndroidViewModelFactory(application))[MyViewModel::class.java]
        list = ArrayList()
        listMore = ArrayList()
        adapter = ClassInRVAdapter(list)
        binding.classDeepToolbar.setNavigationOnClickListener { finish() }
        binding.rvClassIn.adapter = adapter
        binding.rvClassIn.layoutManager = LinearLayoutManager(this,RecyclerView.VERTICAL,false)
        classModel = intent.getParcelableExtra("classModel")!!
        viewModel.getClassInLiveData(classModel.id.toString(), URL.udid).observe(this){
            list.addAll(it.itemList)
            adapter.notifyDataSetChanged()
            nextPageUrl = it.nextPageUrl
        }
        binding.refreshClassIn.setOnRefreshListener {
            viewModel.updateClassInLiveData(classModel.id.toString(), URL.udid)
            binding.refreshClassIn.isRefreshing = false
            adapter.notifyDataSetChanged()
        }
        adapter.setClickListener(object :ClassInRVAdapter.OnSomethingClickedListener{
            override fun onVideoImageClickedListener(videoBean: VideoBean) {
                VideoPlayActivity.startVideoPlayActivity(this@ClassInActivity,videoBean)
            }

            override fun onAvatarImageClickedListener(videoBean: VideoBean) {
                PersonMessageActivity.startPersonMessageActivity(this@ClassInActivity,videoBean.personalModel!!)
            }
        })
    }
}