package com.example.openeyes.customeview

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import androidx.viewpager.widget.PagerAdapter
import androidx.viewpager.widget.ViewPager
import com.example.openeyes.R
import com.example.openeyes.adapter.CirVp2Adapter

/**
 * description ： 自定义view，用于实现轮播图
 * author : lfy
 * email : 1623658271@qq.com
 * date : 2022/7/20 19:43
 */
class CirLayout:LinearLayout {
    lateinit var viewPager:LopViewpager
    private lateinit var linearLayout:LinearLayout
    private lateinit var title:TextView
    private lateinit var innoAdapter:CirVp2Adapter
    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )

    init {
        init()
    }

    fun init(){
        LayoutInflater.from(context).inflate(R.layout.layout_cir,this,true)
        viewPager = this.findViewById(R.id.looper_vp_cir)
        linearLayout = this.findViewById(R.id.looper_ll)
        title = this.findViewById(R.id.looper_tv_title)
    }

    //根据数据个数和当前页面生成不同的小圆点
    fun updateIndicator(){
        if(innoAdapter!=null){
            linearLayout.removeAllViews()
            val count = innoAdapter.getDataResultSize()
            for (i in 0 until count) {
                val view = View(context)
                if(viewPager.currentItem % count == i ){
                    view.background = resources.getDrawable(R.drawable.circle_indicator2)
                }else{
                    view.background = resources.getDrawable(R.drawable.circle_indicator)
                }
                        val layoutParams = LinearLayout.LayoutParams(dip2x(context, 5f), dip2x(context, 4f))
                layoutParams.setMargins(dip2x(context,5f),0,dip2x(context,5f),0)
                view.layoutParams = layoutParams
                linearLayout.addView(view)
            }
        }
    }

    fun setData(pagerAdapter: PagerAdapter,listener: BindTitleListener){
        viewPager.adapter = pagerAdapter
        viewPager.currentItem = Integer.MAX_VALUE / 2 + 3
        title.text = listener.getTitle(0)
        innoAdapter = pagerAdapter as CirVp2Adapter
        updateIndicator()
        pagerAdapter.notifyDataSetChanged()
        this.bindTitleListener = listener
        viewPager.addOnPageChangeListener(object :ViewPager.OnPageChangeListener{
            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
                //切换的回调
            }

            override fun onPageSelected(position: Int) {
                //切换停下来的回调
                title.text = listener.getTitle(position)
                pagerAdapter.notifyDataSetChanged()
            }

            override fun onPageScrollStateChanged(state: Int) {
                //切换状态改变的回调
                updateIndicator()
            }
        })
    }

    interface BindTitleListener{
        fun getTitle(position:Int):String
    }
    private lateinit var bindTitleListener: BindTitleListener

    //像素计算，网上嫖的
    fun dip2x(context: Context?,dpValue:Float):Int{
        val scale = context!!.resources.displayMetrics.density
        return (dpValue * scale + 0.5f).toInt()
    }
}