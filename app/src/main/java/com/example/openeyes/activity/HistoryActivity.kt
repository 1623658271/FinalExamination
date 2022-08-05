package com.example.openeyes.activity

import android.app.Dialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.animation.AnimationUtils
import android.view.animation.LayoutAnimationController
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.openeyes.R
import com.example.openeyes.adapter.HistoryRVAdapter
import com.example.openeyes.databinding.ActivityHistoryBinding
import com.example.openeyes.model.VideoBean
import com.example.openeyes.utils.LoadState
import com.example.openeyes.utils.MySQLiteHelper
import com.example.openeyes.viewmodel.HistoryViewModel

class HistoryActivity : AppCompatActivity() {
    private lateinit var binding:ActivityHistoryBinding
    private lateinit var adapter:HistoryRVAdapter
    private val historyViewModel:HistoryViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_history)
        init()
        initObserver()
    }

    private fun init(){
        adapter = HistoryRVAdapter()
        binding.rvHistory.adapter = adapter
        binding.rvHistory.layoutManager = LinearLayoutManager(this,RecyclerView.VERTICAL,false)
        binding.rvHistory.layoutAnimation = // 入场动画
            LayoutAnimationController(
                AnimationUtils.loadAnimation(
                    this,
                    R.anim.recycler_fade_in
                )
            )
    }

    private fun initObserver() {
        historyViewModel.apply {
            loadHistoryMsg()
            historyList.observe(this@HistoryActivity){
                adapter.setData(it)
            }
            state.observe(this@HistoryActivity){
                hideAll()
                when(it){
                    LoadState.SUCCESS -> {
                        binding.rvHistory.visibility = View.VISIBLE
                    }
                    LoadState.LOADING -> {
                        binding.stateLoading.root.visibility = View.VISIBLE
                    }
                    LoadState.EMPTY -> {
                        binding.stateLoadEmpty.root.visibility = View.VISIBLE
                    }
                    else->{}
                }
            }
        }
        binding.btnDelete.setOnClickListener {
            AlertDialog.Builder(this)
                .setTitle("清空历史数据?")
                .setNegativeButton("取消", null)
                .setPositiveButton("确定") { _, _ ->
                    MySQLiteHelper.deleteAll("Video")
                    historyViewModel.loadHistoryMsg()
                    Toast.makeText(this,"清除成功！",Toast.LENGTH_SHORT).show()
                }
                .show()
        }
        binding.refreshHistory.setOnRefreshListener {
            historyViewModel.loadHistoryMsg()
            binding.refreshHistory.isRefreshing = false
        }
    }

    fun hideAll(){
        binding.rvHistory.visibility = View.GONE
        binding.stateLoading.root.visibility = View.GONE
        binding.stateLoadEmpty.root.visibility = View.GONE
        adapter.setClickListener(object :HistoryRVAdapter.OnSomethingClickedListener{
            override fun onVideoImageClickedListener(videoBean: VideoBean) {
                VideoPlayActivity.startVideoPlayActivity(this@HistoryActivity,videoBean)
            }

        })
    }
}