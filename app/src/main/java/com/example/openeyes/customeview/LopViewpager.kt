package com.example.openeyes.customeview

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import androidx.viewpager.widget.ViewPager
import com.example.openeyes.utils.loge
import kotlin.math.abs
import kotlin.math.max

/**
 * description ： 自定义ViewPager
 * author : lfy
 * email : 1623658271@qq.com
 * date : 2022/7/20 21:21
 */
class LopViewpager:ViewPager {
    init {
        setPageTransformer(false,MyPagerTransformer())
    }
    private val view = this
//    private val TAG = "lfy"
    private var isStop = false

    constructor(context: Context) : super(context){
    }
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs){
    }

    override fun onTouchEvent(ev: MotionEvent?): Boolean {
        when(ev?.action){
            MotionEvent.ACTION_DOWN,MotionEvent.ACTION_MOVE->{
                if(!isStop){
                    stopLooper()
                }
            }
            MotionEvent.ACTION_CANCEL,MotionEvent.ACTION_UP ->{
                startLooper()
//                        Log.d(TAG ,"onTouch: start")
            }
        }
        return super.onTouchEvent(ev)
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
        view.postDelayed(runnable,3000)
    }

    private val runnable = object : Runnable {
        override fun run() {
            currentItem += 1
            view.postDelayed(this,3000)
        }
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        stopLooper()
    }

    private fun stopLooper() {
        isStop = true
        view.postDelayed(runnable,3000)
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