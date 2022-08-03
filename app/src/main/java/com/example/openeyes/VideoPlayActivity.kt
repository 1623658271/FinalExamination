package com.example.openeyes

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import cn.jzvd.Jzvd
import com.bumptech.glide.Glide
import com.example.openeyes.adapter.FragmentPagerAdapter
import com.example.openeyes.databinding.LayoutVideoPlayBinding
import com.example.openeyes.fragment.CommentFragment
import com.example.openeyes.fragment.DetailsFragment
import com.example.openeyes.model.VideoBean
//import com.example.openeyes.room.MyDatabase
import com.example.openeyes.utils.ActivityController
import com.example.openeyes.utils.LoadState
import com.example.openeyes.viewmodel.VideoPlayPageViewModel
import com.google.android.material.tabs.TabLayoutMediator

/**
 * description ： 点击视频播放后进入的活动
 * author : lfy
 * email : 1623658271@qq.com
 * date : 2022/7/14 15:27
 */
class VideoPlayActivity : BaseActivity() {
    private lateinit var binding:LayoutVideoPlayBinding
    private lateinit var fragmentPagerAdapter: FragmentPagerAdapter
    private lateinit var videoBean: VideoBean
    private val videoPlayPageViewModel:VideoPlayPageViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.layout_video_play)
        ActivityController.addActivity(this)
        if(intent!=null) {
            videoBean = intent.getParcelableExtra("video")!!
            videoPlay()
            var data:MutableList<String> = ArrayList()
            data.apply {
                add("详情")
                add("评论")
            }
            fragmentPagerAdapter = FragmentPagerAdapter(this)
            fragmentPagerAdapter.add{DetailsFragment(videoBean)}
            fragmentPagerAdapter.add{CommentFragment(videoBean)}
            binding.vp2Video.adapter = fragmentPagerAdapter
            binding.vp2Video.offscreenPageLimit = 2
            TabLayoutMediator(binding.tlVideo,binding.vp2Video){
                    tab,position->tab.text = data[position]
            }.attach()
        }else{
            Toast.makeText(this,"读取视频信息出错！",Toast.LENGTH_SHORT).show()
            finish()
        }
        binding.videoToolbar.setOnMenuItemClickListener {
            when(it.itemId){
                R.id.video_go_home -> {
                    ActivityController.removeAllVideoActivity()
                }
            }
            true
        }
        binding.videoToolbar.setNavigationOnClickListener {
            ActivityController.removeActivity(this)
        }
    }


    /**
     * 播放视频
     */
    private fun videoPlay() {
        binding.jzVideo.setUp(videoBean.playUrl,videoBean.bigTitle)
        Glide.with(this).load(videoBean.coverUrl).into(binding.jzVideo.posterImageView)
        binding.jzVideo.startVideo()
    }

    /**
     * 友好的进入方式
     */
    companion object{
        fun startVideoPlayActivity(context: Context,videoBean:VideoBean){
            val intent = Intent(context,VideoPlayActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            intent.putExtra("video",videoBean)
            context.startActivity(intent)
        }
        fun fragmentStartVideoPlayActivity(context: Context,activity: Activity,videoBean: VideoBean){
            val mIntent = Intent(activity, VideoPlayActivity::class.java)
            mIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            mIntent.putExtra("video",videoBean)
            context.startActivity(mIntent)
        }
    }

    override fun onResume() {
        Jzvd.goOnPlayOnResume()
        super.onResume()
    }
    override fun onPause() {
        Jzvd.goOnPlayOnPause()
        super.onPause()
    }

    override fun onBackPressed() {
        if (Jzvd.backPress()) {
            return
        }
        super.onBackPressed()
    }

    override fun onDestroy() {
        Jzvd.releaseAllVideos()
        super.onDestroy()
    }

    override fun finish() {
        Jzvd.releaseAllVideos()
        super.finish()
    }
}