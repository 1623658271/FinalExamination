package com.example.openeyes.activity

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import androidx.activity.viewModels
import androidx.core.view.ViewCompat
import androidx.core.view.WindowCompat
import com.bumptech.glide.Glide
import com.example.openeyes.databinding.ActivitySplashBinding
import com.example.openeyes.viewmodel.SplashViewModel
import kotlinx.coroutines.Runnable

class SplashActivity : BaseActivity() {
    private val vm:SplashViewModel by viewModels()
    private val _binding: ActivitySplashBinding by lazy {
        ActivitySplashBinding.inflate(layoutInflater)
    }
    val binding: ActivitySplashBinding get() = _binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        cancelStatusBar()
        vm.dailyImgBean.observe(this) {
            //加载图片（每日一图）
            val url = "http://cn.bing.com${it.images[0].url}"
            Glide
                .with(binding.ivSplash)
                .load(url)
                .into(binding.ivSplash)
            //加载title
            binding.tvSplashTitle.text = it.images[0].title
            //加载copyright
            binding.tvSplashCopyright.text = it.images[0].copyright
        }
        window.decorView.postDelayed(Runnable {
            val intent = Intent(this@SplashActivity, MainActivity::class.java)
            startActivity(intent)
            finish()
        },2000)
    }

    private fun cancelStatusBar() {
        val window = this.window
        val decorView = window.decorView

        // 这是 Android 做了兼容的 Compat 包
        // 注意，使用了下面这个方法后，状态栏不会再有东西占位，
        // 可以给根布局加上 android:fitsSystemWindows=true
        // 不同布局该属性效果不同，请给合适的布局添加
        WindowCompat.setDecorFitsSystemWindows(window, false)
        val windowInsetsController = ViewCompat.getWindowInsetsController(decorView)
        windowInsetsController?.isAppearanceLightStatusBars = true // 设置状态栏字体颜色为黑色
        window.statusBarColor = Color.TRANSPARENT //把状态栏颜色设置成透明
    }

    override fun onPause() {
        super.onPause()
    }
}