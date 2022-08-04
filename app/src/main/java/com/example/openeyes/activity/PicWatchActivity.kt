package com.example.openeyes.activity

import android.Manifest
import android.app.Activity
import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import androidx.viewpager.widget.ViewPager
import com.example.openeyes.R
import com.example.openeyes.activity.MyApplication.Companion.context
import com.example.openeyes.adapter.PicWatchPagerAdapter
import com.example.openeyes.databinding.ActivityPicWatchBinding
import com.example.openeyes.model.PicsBean
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Observer
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers
import permissions.dispatcher.NeedsPermission
import java.io.*
import java.net.HttpURLConnection
import java.net.MalformedURLException
import java.net.URL


class PicWatchActivity : BaseActivity() {
    private lateinit var file: File
    private lateinit var pics:PicsBean
    private lateinit var adapter:PicWatchPagerAdapter
    private lateinit var binding:ActivityPicWatchBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_pic_watch)
        initData()
    }

    private fun initData() {
        pics = intent.getParcelableExtra("pics")!!
        adapter = PicWatchPagerAdapter(pics.picUrls)
        binding.picWatchVp.adapter = adapter
        adapter.setOnImageLongClickListener(object :PicWatchPagerAdapter.OnLongClickListener{
            override fun onLongClick(imageUrl: String) {
                AlertDialog.Builder(this@PicWatchActivity)
                    .setTitle("保存图片?")
                    .setNegativeButton("取消", null)
                    .setPositiveButton("确定") { _, _ ->
                        saveImage(imageUrl)
                    }
                    .show()
            }
        })
        updateIndicator()
        binding.picWatchVp.addOnPageChangeListener(object :ViewPager.OnPageChangeListener{
            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {

            }

            override fun onPageSelected(position: Int) {

            }

            override fun onPageScrollStateChanged(state: Int) {
                updateIndicator()
            }

        })
    }

    fun updateIndicator(){
        val count = pics.picUrls.size
        binding.llPicIndicator.removeAllViews()
        for (i in 0 until count) {
            val view = View(context)
            if(binding.picWatchVp.currentItem % count == i ){
                view.background = resources.getDrawable(R.drawable.circle_indicator2)
            }else{
                view.background = resources.getDrawable(R.drawable.circle_indicator)
            }
            val layoutParams = LinearLayout.LayoutParams(dip2x(context, 8f), dip2x(context, 8f))
            layoutParams.setMargins(dip2x(context,8f),0,dip2x(context,8f),0)
            view.layoutParams = layoutParams
            binding.llPicIndicator.addView(view)
        }
    }

    //像素计算，网上嫖的
    fun dip2x(context: Context?,dpValue:Float):Int{
        val scale = context!!.resources.displayMetrics.density
        return (dpValue * scale + 0.5f).toInt()
    }

    @NeedsPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
    fun saveImage(image:String){
        getImage(image,object : HttpCallBackListener {
            override fun onFinish(bitmap: Bitmap?) {
                val saveUri =
                    contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, ContentValues())
                val outputStream: OutputStream? = contentResolver.openOutputStream(saveUri!!)

                if (bitmap!!.compress(Bitmap.CompressFormat.JPEG, 100, outputStream)) { //设置压缩比
                    //保存成功
                    val intent = Intent()
                    intent.data = saveUri
                    sendBroadcast(intent)
                    //通知图库更新
                    sendBroadcast(Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, saveUri))
                    Toast.makeText(context,"已保存",Toast.LENGTH_SHORT).show()
                }
            }

            override fun onError(e: Throwable) {
                Toast.makeText(context,"保存失败",Toast.LENGTH_SHORT).show()
            }
        })

    }

    //自定义一个回调接口
    interface HttpCallBackListener {
        fun onFinish(bitmap: Bitmap?)
        fun onError(e: Throwable)
    }

    fun getImage(path: String?, listener: HttpCallBackListener?) {
        var bitmap:Bitmap
        Observable.create<String> {
            it.onNext(path!!)
        }.map {
            var imageurl: URL? = null
            try {
                imageurl = URL(it)
            } catch (e: MalformedURLException) {
                e.printStackTrace()
            }
                val conn = imageurl!!.openConnection() as HttpURLConnection
                conn.doInput = true
                conn.connect()
                val stream = conn.inputStream
                bitmap = BitmapFactory.decodeStream(stream)
                stream.close()
            bitmap
        }.subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object :Observer<Bitmap>{
                override fun onSubscribe(d: Disposable) {

                }

                override fun onNext(t: Bitmap) {
                    listener?.onFinish(t)
                }

                override fun onError(e: Throwable) {
                    listener?.onError(e)
                }

                override fun onComplete() {

                }

            })

    }
    companion object{
        fun startPicWatchActivity(context: Context,picModel:PicsBean){
            val intent = Intent(context, PicWatchActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            intent.putExtra("pics",picModel)
            context.startActivity(intent)
        }
        fun fragmentStartPicWatchActivity(context: Context, activity: Activity, picModel:PicsBean){
            val intent = Intent(activity, PicWatchActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            intent.putExtra("pics",picModel)
            context.startActivity(intent)
        }
    }
}