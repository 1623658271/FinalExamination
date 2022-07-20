package com.example.openeyes.view

import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.view.marginStart
import androidx.viewpager.widget.PagerAdapter
import androidx.viewpager.widget.ViewPager
import com.example.openeyes.R
import com.example.openeyes.adapter.CirVp2Adapter
import org.w3c.dom.Text

/**
 * description ： TODO:类的作用
 * author : lfy
 * email : 1623658271@qq.com
 * date : 2022/7/20 19:43
 */
class CirLayout:LinearLayout {
    private lateinit var viewPager:LopViewpager
    private lateinit var linearLayout:LinearLayout
    private lateinit var title:TextView
    private lateinit var innoAdapter:CirVp2Adapter
    constructor(context: Context?) : super(context){
        init()
    }
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs){
        init()
    }
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ){
        init()
    }
    constructor(
        context: Context?,
        attrs: AttributeSet?,
        defStyleAttr: Int,
        defStyleRes: Int
    ) : super(context, attrs, defStyleAttr, defStyleRes){
        init()
    }

    fun init(){
        LayoutInflater.from(context).inflate(R.layout.layout_cir,this,true)
        viewPager = this.findViewById(R.id.looper_vp_cir)
        linearLayout = this.findViewById(R.id.looper_ll)
        title = this.findViewById(R.id.looper_tv_title)
    }

    fun updateIndicator(){
        if(innoAdapter!=null){
            linearLayout.removeAllViews()
            val count = innoAdapter.getDataResultSize()
            for (i in 0 until count) {
                val view = View(context)
                if(viewPager.currentItem % count == i ){
                    view.setBackgroundColor(Color.parseColor("#ff0000"))
                }else{
                    view.setBackgroundColor(Color.WHITE)
                }
                val layoutParams = LinearLayout.LayoutParams(dip2x(context, 5f), dip2x(context, 5f))
                layoutParams.setMargins(dip2x(context,10f),0,dip2x(context,10f),0)
                view.layoutParams = layoutParams
                linearLayout.addView(view)
            }
        }
    }

    fun setData(pagerAdapter: PagerAdapter,listener: BindTitleListener){
        viewPager.adapter = pagerAdapter
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

    fun dip2x(context: Context?,dpValue:Float):Int{
        val scale = context!!.resources.displayMetrics.density
        return (dpValue * scale + 0.5f).toInt()
    }
}