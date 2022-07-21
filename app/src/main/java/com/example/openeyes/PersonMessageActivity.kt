package com.example.openeyes

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.example.openeyes.databinding.LayoutPersonMessageBinding
import com.example.openeyes.model.PersonalModel
import com.example.openeyes.model.VideoBean
import kotlinx.android.synthetic.*

/**
 * description ： 展示个人信息的fragment
 * author : lfy
 * email : 1623658271@qq.com
 * date : 2022/7/14 15:27
 */
class PersonMessageActivity : AppCompatActivity() {
    private lateinit var binding:LayoutPersonMessageBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.layout_person_message)
        binding.person = intent.getParcelableExtra("person_msg")
    }
    companion object {
        fun startPersonMessageActivity(context: Context, personalModel: PersonalModel) {
            val intent = Intent(context, PersonMessageActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            intent.putExtra("person_msg", personalModel)
            context.startActivity(intent)
        }
        fun fragmentStartVideoPlayActivity(context: Context, activity: Activity, personalModel: PersonalModel){
            val mIntent = Intent(activity, PersonMessageActivity::class.java)
            mIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            mIntent.putExtra("person_msg",personalModel)
            context.startActivity(mIntent)
        }
    }
}