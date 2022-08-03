package com.example.openeyes

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import com.example.openeyes.databinding.LayoutPersonMessageBinding
import com.example.openeyes.model.PersonalBean
import com.example.openeyes.model.PicsBean

/**
 * description ： 展示个人信息的fragment
 * author : lfy
 * email : 1623658271@qq.com
 * date : 2022/7/14 15:27
 */
class PersonMessageActivity : BaseActivity() {
    private lateinit var binding:LayoutPersonMessageBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.layout_person_message)
        val h = intent.getParcelableExtra<PersonalBean>("person")
        binding.person = h
        if(binding.tvCity.text.isEmpty()){
            binding.llCity.visibility = View.GONE
        }
        if(binding.tvJob.text.isEmpty()){
            binding.llJob.visibility = View.GONE
        }
        binding.civAvatar.setOnClickListener {
            val list = ArrayList<String>()
            list.add(h!!.avatar)
            PicWatchActivity.startPicWatchActivity(this, PicsBean("",h.avatar,list,null,null))
        }
        binding.ivBackground.setOnClickListener {
            val list = ArrayList<String>()
            list.add(h!!.cover)
            PicWatchActivity.startPicWatchActivity(this, PicsBean("",h.avatar,list,null,null))
        }
    }
    companion object {
        fun startPersonMessageActivity(context: Context, personalBean: PersonalBean) {
            val intent = Intent(context, PersonMessageActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            intent.putExtra("person", personalBean)
            context.startActivity(intent)
        }
        fun fragmentStartVideoPlayActivity(context: Context, activity: Activity, personalBean: PersonalBean){
            val mIntent = Intent(activity, PersonMessageActivity::class.java)
            mIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            mIntent.putExtra("person",personalBean)
            context.startActivity(mIntent)
        }
    }
}