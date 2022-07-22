package com.example.openeyes.view

import android.content.Context
import android.os.Handler
import android.os.Looper
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.view.animation.Interpolator
import android.widget.Scroller
import androidx.interpolator.view.animation.LinearOutSlowInInterpolator
import androidx.viewpager.widget.ViewPager
import kotlinx.coroutines.Delay
import kotlinx.coroutines.delay
import kotlin.math.abs
import kotlin.math.max

/**
 * description ： 自定义ViewPager
 * author : lfy
 * email : 1623658271@qq.com
 * date : 2022/7/20 21:21
 */
class LopViewpager:ViewPager {
    private lateinit var handler0:Handler
    private val TAG = "lfy"
    private var isStop = false
    private lateinit var scroller:Scroller

    constructor(context: Context) : super(context){
        init()
    }
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs){
        init()
    }
    fun init(){
        scroller = Scroller(context)
        handler0 = Handler(Looper.getMainLooper())
        setOnTouchListener(object :View.OnTouchListener{
            override fun onTouch(v: View?, event: MotionEvent?): Boolean {
                val action = event?.action
                when(action){
                    MotionEvent.ACTION_DOWN,MotionEvent.ACTION_MOVE->{
                        if(isStop){

                        }else {
                            stopLooper()
                            Log.d(TAG ,"onTouch: stop")
                        }
                    }
                    MotionEvent.ACTION_CANCEL,MotionEvent.ACTION_UP ->{
                        startLooper()
                        Log.d(TAG ,"onTouch: start")
                    }
                }
                return false
            }

        })
        setPageTransformer(false,MyPagerTransformer())
    }

    override fun setCurrentItem(item: Int) {
        super.setCurrentItem(item,true)
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        startLooper()
    }

    private fun startLooper() {
        isStop = false
        handler0.postDelayed(runnable,3000)
    }

    private val runnable = object : Runnable {
        override fun run() {
            currentItem += 1
            handler0.postDelayed(this, 3000)
        }
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        stopLooper()
    }

    private fun stopLooper() {
        isStop = true
        handler0.removeCallbacks(runnable)
        removeCallbacks(runnable)
    }
    class MyPagerTransformer : ViewPager.PageTransformer {
        override fun transformPage(page: View, position: Float) {
            page.apply {
                when {
                    position < -1 -> {
                        //向左滑动时，左边的page，由于看不见，所以设不设置都无所谓
                    }
                    position <= 1 -> {
                        //[-1,1]
                        //向左滑动时，代表中间的page和右边的page
                        //向右滑动时，代表中间的page和左边的page
                        val scaleValue = max(0.8f,1 - abs(position))
                        scaleX = scaleValue
                        scaleY = scaleValue
                    }
                    else -> {
                        // position>1
                        // 向右滑动时，右边的page，还是看不见，所以可以不用设置
                    }
                }
            }
        }
    }
}