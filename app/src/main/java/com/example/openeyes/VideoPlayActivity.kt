package com.example.openeyes

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.os.Parcelable
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.openeyes.adapter.FragmentPagerAdapter
import com.example.openeyes.adapter.RelatedRVAdapter
import com.example.openeyes.databinding.LayoutVideoPlayBinding
import com.example.openeyes.fragment.CommentFragment
import com.example.openeyes.fragment.DetailsFragment
import com.example.openeyes.model.PersonalModel
import com.example.openeyes.model.RelatedRecommendationModel
import com.example.openeyes.model.VideoBean
import com.example.openeyes.viewmodel.MyViewModel
import com.google.android.material.tabs.TabLayoutMediator
import fm.jiecao.jcvideoplayer_lib.JCVideoPlayer
import fm.jiecao.jcvideoplayer_lib.JCVideoPlayer.SCREEN_LAYOUT_NORMAL
import kotlinx.android.synthetic.*

/**
 * description ： 点击视频播放后进入的活动
 * author : lfy
 * email : 1623658271@qq.com
 * date : 2022/7/14 15:27
 */
class VideoPlayActivity : AppCompatActivity() {
    private lateinit var binding:LayoutVideoPlayBinding
    private lateinit var fragmentPagerAdapter: FragmentPagerAdapter
    private lateinit var videoBean: VideoBean
    private val TAG = "lfy"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.layout_video_play)
        if(intent!=null) {
            videoBean = intent.getParcelableExtra("video")!!
            videoPlay()
            var data:MutableList<String> = ArrayList()
            data.apply {
                add("详情")
                add("评论")
            }
            fragmentPagerAdapter = FragmentPagerAdapter(this)
            fragmentPagerAdapter.addFragment(DetailsFragment(videoBean))
            fragmentPagerAdapter.addFragment(CommentFragment(videoBean,application))
            binding.vp2Video.adapter = fragmentPagerAdapter
            binding.vp2Video.offscreenPageLimit = 2
            TabLayoutMediator(binding.tlVideo,binding.vp2Video){
                tab,position->tab.text = data[position]
            }.attach()
        }else{
            Toast.makeText(this,"读取视频信息出错！",Toast.LENGTH_SHORT).show()
            finish()
        }
    }

    /**
     * 播放视频
     */
    private fun videoPlay() {
        binding.jcVideo.setUp(videoBean.playUrl,SCREEN_LAYOUT_NORMAL)
        binding.jcVideo.backButton.setOnClickListener { finish() }
        binding.jcVideo.startVideo()
        Glide.with(this).load(videoBean.coverUrl).into(binding.jcVideo.thumbImageView)
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
    }

    override fun onResume() {
        super.onResume()
        binding.jcVideo.startVideo()
    }
    override fun onPause() {
        super.onPause()
        binding.jcVideo.release()
    }

    override fun onBackPressed() {
        if (JCVideoPlayer.backPress()) {
            return;
        }
        super.onBackPressed()
    }

    override fun onDestroy() {
        JCVideoPlayer.releaseAllVideos()
        binding.jcVideo.release()
        super.onDestroy()
        Log.d(TAG, "onDestroy: video")
    }
}