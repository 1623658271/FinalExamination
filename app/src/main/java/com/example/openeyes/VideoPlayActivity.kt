package com.example.openeyes

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.bumptech.glide.Glide
import com.example.openeyes.adapter.FragmentPagerAdapter
import com.example.openeyes.databinding.LayoutVideoPlayBinding
import com.example.openeyes.fragment.CommentFragment
import com.example.openeyes.fragment.DetailsFragment
import com.example.openeyes.model.VideoBean
import com.google.android.material.tabs.TabLayoutMediator
import fm.jiecao.jcvideoplayer_lib.JCVideoPlayer
import fm.jiecao.jcvideoplayer_lib.JCVideoPlayer.SCREEN_LAYOUT_NORMAL

class VideoPlayActivity : AppCompatActivity() {
    private lateinit var binding:LayoutVideoPlayBinding
    private var id:Int=0
    private lateinit var playUrl:String
    private lateinit var coverUrl:String
    private lateinit var bigTitle:String
    private lateinit var smallTitle:String
    private lateinit var description:String
    private lateinit var fragmentPagerAdapter: FragmentPagerAdapter
    private val TAG = "lfy"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.layout_video_play)
        if(intent!=null) {
            val videoMessages = intent.getStringArrayListExtra("video")
            id = intent.getIntExtra("id", 0)
            playUrl = videoMessages?.get(0)?:""
            coverUrl = videoMessages?.get(1)?:""
            bigTitle = videoMessages?.get(2)?:""
            smallTitle = videoMessages?.get(3)?:""
            description = videoMessages?.get(4)?:""
            videoPlay()
            Log.d(TAG, "onCreate: $description")
            var data:MutableList<String> = ArrayList()
            data.apply {
                add("详情")
                add("评论")
            }
            fragmentPagerAdapter = FragmentPagerAdapter(this)
            fragmentPagerAdapter.addFragment(DetailsFragment(VideoBean(id,bigTitle,smallTitle,coverUrl,playUrl,description,null)))
            fragmentPagerAdapter.addFragment(CommentFragment(id,application))
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

    private fun videoPlay() {
        binding.jcVideo.setUp(playUrl,SCREEN_LAYOUT_NORMAL)
        binding.jcVideo.backButton.setOnClickListener { finish() }
        binding.jcVideo.startVideo()
        Glide.with(this).load(coverUrl).into(binding.jcVideo.thumbImageView)
    }

    companion object{
        fun startVideoPlayActivity(context: Context,videoMessages:ArrayList<String>,id:Int){
            val intent = Intent(context,VideoPlayActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            intent.putStringArrayListExtra("video",videoMessages)
            intent.putExtra("id",id)
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
        super.onDestroy()
        JCVideoPlayer.releaseAllVideos()
    }
}