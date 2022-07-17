package com.example.openeyes

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.bumptech.glide.Glide
import com.example.openeyes.databinding.LayoutVideoPlayBinding
import com.example.openeyes.model.CommentModel
import fm.jiecao.jcvideoplayer_lib.JCVideoPlayer
import fm.jiecao.jcvideoplayer_lib.JCVideoPlayer.SCREEN_LAYOUT_NORMAL
import kotlinx.android.synthetic.main.layout_video_play.*
import kotlinx.android.synthetic.main.layout_video_play.view.*

class VideoPlayActivity : AppCompatActivity() {
    private lateinit var binding:LayoutVideoPlayBinding
    private var id:Int=0
    private lateinit var playUrl:String
    private lateinit var coverUrl:String
    private lateinit var bigTitle:String
    private lateinit var smallTitle:String
    private lateinit var description:String
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
        }else{
            Toast.makeText(this,"读取视频信息出错！",Toast.LENGTH_SHORT).show()
            finish()
        }
    }

    private fun videoPlay() {
        binding.jcVideo.setUp(playUrl,SCREEN_LAYOUT_NORMAL)
        binding.jcVideo.backButton.setOnClickListener { finish() }
        binding.jcVideo
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

    override fun onPause() {
        super.onPause()
        JCVideoPlayer.releaseAllVideos()
    }

    override fun onBackPressed() {
        if (JCVideoPlayer.backPress()) {
            return;
        }
        super.onBackPressed()
    }

    override fun finish() {
        super.finish()
        JCVideoPlayer.releaseAllVideos()
    }

    override fun onDestroy() {
        super.onDestroy()
        JCVideoPlayer.releaseAllVideos()
    }
}