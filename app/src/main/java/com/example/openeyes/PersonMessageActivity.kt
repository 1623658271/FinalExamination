package com.example.openeyes

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.example.openeyes.databinding.LayoutPersonMessageBinding
import com.example.openeyes.model.PersonalModel
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
}