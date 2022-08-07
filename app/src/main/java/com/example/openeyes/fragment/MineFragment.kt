package com.example.openeyes.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.example.openeyes.R
import com.example.openeyes.activity.HistoryActivity
import com.example.openeyes.databinding.LayoutMineFragmentBinding

/**
 * description ： 我的Fragment
 * author : lfy
 * email : 1623658271@qq.com
 * date : 2022/7/15 08:51
 */
class MineFragment:Fragment() {
    private lateinit var binding:LayoutMineFragmentBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater,R.layout.layout_mine_fragment,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    private fun init(){
        binding.tvHistory.setOnClickListener {
            val intent = Intent(activity!!, HistoryActivity::class.java)
            startActivity(intent)
        }
    }
}