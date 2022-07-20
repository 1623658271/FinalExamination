package com.example.openeyes.view

import android.content.Context
import android.os.Handler
import android.os.Looper
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import androidx.viewpager.widget.ViewPager
import kotlinx.coroutines.Delay
import kotlinx.coroutines.delay

/**
 * description ： TODO:类的作用
 * author : lfy
 * email : 1623658271@qq.com
 * date : 2022/7/20 21:21
 */
class LopViewpager:ViewPager {
    private lateinit var handler0:Handler
    constructor(context: Context) : super(context){
        init()
    }
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs){
        init()
    }
    fun init(){
        handler0 = Handler(Looper.getMainLooper())
        setOnTouchListener(object :View.OnTouchListener{
            override fun onTouch(v: View?, event: MotionEvent?): Boolean {
                val action = event?.action
                when(action){
                    MotionEvent.ACTION_DOWN->stopLooper()
                    MotionEvent.ACTION_CANCEL->startLooper()
                    MotionEvent.ACTION_UP ->startLooper()
                }
                return false
            }

        })
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        startLooper()
    }

    private fun startLooper() {
        handler0.postDelayed(runnable,3000)
    }

    private val runnable = object : Runnable {
        override fun run() {
            currentItem++
            handler0.postDelayed(this, 3000)
        }
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        stopLooper()
    }

    private fun stopLooper() {
        handler0.removeCallbacks(runnable)
        removeCallbacks(runnable)
    }
}