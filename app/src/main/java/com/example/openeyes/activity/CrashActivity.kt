package com.example.openeyes.activity

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.openeyes.R
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import kotlin.system.exitProcess


class CrashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_crash)

        val eString = intent.getStringExtra("eString")
        MaterialAlertDialogBuilder(this)
            .setTitle("程序崩溃了！以下是错误信息:")
            .setMessage(eString)
            .setNegativeButton("退出应用") { _, _ ->
                exitProcess(0)
            }
            .setPositiveButton("复制错误信息") { _, _ ->
                Toast.makeText(this,"错误信息已复制",Toast.LENGTH_SHORT).show()
                //获取剪贴板管理器：
                val cm: ClipboardManager = getSystemService(CLIPBOARD_SERVICE) as ClipboardManager
                // 创建普通字符型ClipData
                val mClipData = ClipData.newPlainText("Label", eString)
                // 将ClipData内容放到系统剪贴板里。
                cm.setPrimaryClip(mClipData)
            }.show()
    }

    companion object {
        fun start(context: Context,eString: String) {
            val intent = Intent(context, CrashActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            intent.putExtra("eString",eString)
            context.startActivity(intent)
            exitProcess(0)
        }
    }
}