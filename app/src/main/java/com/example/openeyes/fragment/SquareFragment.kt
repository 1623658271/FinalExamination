package com.example.openeyes.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.openeyes.R

/**
 * description ： 广场页Fragment
 * author : lfy
 * email : 1623658271@qq.com
 * date : 2022/7/15 08:51
 */
class SquareFragment:Fragment() {
    private lateinit var v:View
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        v = inflater.inflate(R.layout.layout_square_fragment,container,false)
        return v
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
}