package com.example.openeyes.customeview

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import androidx.recyclerview.widget.RecyclerView

/**
 * description ： TODO:类的作用
 * author : lfy
 * email : 1623658271@qq.com
 * date : 2022/8/7 13:01
 */
class HomepageHandpickRV(context: Context, attrs: AttributeSet?, defStyleAttr: Int) :
    RecyclerView(context, attrs, defStyleAttr) {
    override fun onInterceptTouchEvent(e: MotionEvent?): Boolean {

        return super.onInterceptTouchEvent(e)
    }
}