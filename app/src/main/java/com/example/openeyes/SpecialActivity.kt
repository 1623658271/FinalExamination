package com.example.openeyes

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.animation.AnimationUtils
import android.view.animation.LayoutAnimationController
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.openeyes.adapter.SpecialRVAdapter
import com.example.openeyes.databinding.ActivitySpecialBinding
import com.example.openeyes.model.VideoBean
import com.example.openeyes.utils.LoadState
import com.example.openeyes.viewmodel.SpecialInPageViewModel
import kotlin.math.log

class SpecialActivity : BaseActivity() {
    private lateinit var binding:ActivitySpecialBinding
    private lateinit var adapter:SpecialRVAdapter
    private val specialInPageViewModel:SpecialInPageViewModel by viewModels()
    private lateinit var id:String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_special)
        init()
        initObserver()
    }

    private fun initObserver() {
        specialInPageViewModel.apply {
            specialInData.observe(this@SpecialActivity){
                adapter.setData(it)
            }
            specialInPageViewModel.loadSpecialInMsg(id)
            state.observe(this@SpecialActivity){
                hideAll()
                when(it){
                    LoadState.SUCCESS -> {
                        binding.rvSpecial.visibility = View.VISIBLE
                    }
                    LoadState.LOADING -> {
                        binding.stateLoading.root.visibility = View.VISIBLE
                    }
                    LoadState.ERROR -> {
                        binding.stateLoadError.root.visibility = View.VISIBLE
                    }
                    else -> {}
                }
            }
        }
        binding.refreshSpecial.setOnRefreshListener {
            specialInPageViewModel.loadSpecialInMsg(id)
            binding.refreshSpecial.isRefreshing = false
        }
        adapter.setClickListener(object :SpecialRVAdapter.OnSomethingClickedListener{
            override fun onVideoImageClickedListener(videoBean: VideoBean) {
                VideoPlayActivity.startVideoPlayActivity(this@SpecialActivity,videoBean)
            }

            override fun onAvatarImageClickedListener(videoBean: VideoBean) {
                PersonMessageActivity.startPersonMessageActivity(this@SpecialActivity,videoBean.personalBean!!)
            }

        })
    }

    fun init(){
        id = intent.getStringExtra("id")?:"0"
        adapter = SpecialRVAdapter()
        binding.rvSpecial.adapter = adapter
        binding.rvSpecial.layoutManager = LinearLayoutManager(this,RecyclerView.VERTICAL,false)
        binding.rvSpecial.layoutAnimation = // 入场动画
            LayoutAnimationController(
                AnimationUtils.loadAnimation(
                    this,
                    R.anim.recycler_fade_in
                )
            )
        binding.specialToolbar.setNavigationOnClickListener {
            finish()
        }
    }

    fun hideAll(){
        binding.stateLoading.root.visibility = View.GONE
        binding.stateLoadError.root.visibility = View.GONE
        binding.rvSpecial.visibility = View.GONE
    }
}