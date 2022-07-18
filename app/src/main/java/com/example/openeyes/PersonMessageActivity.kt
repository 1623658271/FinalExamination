package com.example.openeyes

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.example.openeyes.databinding.LayoutPersonMessageBinding
import com.example.openeyes.model.PersonalModel
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
        if(intent!=null){
            val messages = intent.getStringArrayListExtra("message")
            val avatar = messages?.get(0)?:""
            val cover =  messages?.get(1)?:""
            val description = messages?.get(2)?:""
            val nickname = messages?.get(3)?:""
            binding.person = PersonalModel(0,avatar, cover, description, nickname)
        }
    }

    /**
     * 友好的进入方式
     */
    companion object{
        fun startPersonMessageActivity(context: Context,messages:ArrayList<String>){
            val intent = Intent(context,PersonMessageActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            intent.putStringArrayListExtra("message",messages)
            context.startActivity(intent)
        }
    }
}