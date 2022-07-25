package com.example.openeyes.fragment

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity.RESULT_OK
import android.content.ContentUris
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.database.Cursor
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.DocumentsContract
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.example.openeyes.R
import com.example.openeyes.databinding.LayoutMineFragmentBinding
import permissions.dispatcher.NeedsPermission
import java.io.File
import java.io.FileNotFoundException

/**
 * description ： 我的Fragment
 * author : lfy
 * email : 1623658271@qq.com
 * date : 2022/7/15 08:51
 */
class MineFragment:Fragment() {
    private lateinit var binding:LayoutMineFragmentBinding
    private lateinit var file:File
    private lateinit var context0:Context
    private var imageUrl: Uri? = null
    private lateinit var mSharedPreferences: SharedPreferences
    private lateinit var mEditor: SharedPreferences.Editor
    private val mOpenAlum = 1
    private lateinit var mBitMap:Bitmap
    private var mImagePath: String? = null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater,R.layout.layout_mine_fragment,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        context0 = context!!
        mSharedPreferences = context0.getSharedPreferences("data2", Context.MODE_PRIVATE)
        mEditor = context0.getSharedPreferences("data2",Context.MODE_PRIVATE).edit()


        file = File(context0.externalCacheDir,"the头像.jpg")
        if (file.exists()) {
            if (Build.VERSION.SDK_INT >= 24) {
                imageUrl =
                    FileProvider.getUriForFile(context0, "com.wayeal.wateraffair.user2.fileprovider", file)
            } else {
                imageUrl = Uri.fromFile(file)
            }
            var bitmap: Bitmap? = null
            try {
                bitmap = BitmapFactory.decodeStream(context0.contentResolver.openInputStream(imageUrl!!))
            } catch (e: FileNotFoundException) {
                e.printStackTrace()
            }
            binding.mineImage.setImageBitmap(bitmap)
            displayImage(mSharedPreferences.getString("imagePath","")!!)
        }
        binding.mineImage.setOnClickListener {
            openAlum()
        }

    }

    @NeedsPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
    private fun openAlum() {
        if (ContextCompat.checkSelfPermission(
                context0,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                requireActivity(),
                arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
                1
            )
        } else {
            val intent = Intent(Intent.ACTION_PICK)
            intent.type = "image/*"
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
            intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION)
            intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUrl)
            startActivityForResult(intent, mOpenAlum)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when(requestCode) {
            mOpenAlum -> {
                if(resultCode == RESULT_OK) {
                    if(Build.VERSION.SDK_INT >= 19){
                        if (data != null) {
                            handleImageOnKitKat(data)
                        }
                    }else{
                        if (data != null) {
                            handleImageBeforeKitKat(data)
                        }
                    }
                }
            }
        }
    }

    fun setImage(){
        try {
            val bitmap = BitmapFactory.decodeStream(context0.getContentResolver().openInputStream(imageUrl!!))
            binding.mineImage.setImageBitmap(bitmap)
            mBitMap = bitmap
        } catch (e: Exception) {
            e.printStackTrace()
        }
        mEditor.apply {
            if(mImagePath!=null) {
                putString("imagePath", mImagePath)
            }
            apply()
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String?>, grantResults: IntArray){
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            mOpenAlum -> if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                openAlum()
            } else {
                Toast.makeText(context0, "已拒绝访问相册权限", Toast.LENGTH_SHORT).show()
            }
        }
    }

    //老版本<4.4
    private fun handleImageBeforeKitKat(data: Intent) {
        val uri = data.data
        val imagePath = getImagePath(uri!!, null)
        mImagePath = imagePath!!
        displayImage(imagePath!!)
    }

    //新版本>4.4
    private fun handleImageOnKitKat(data: Intent) {
        var imagePath: String? = null
        val uri = data.data
        if (DocumentsContract.isDocumentUri(context0, uri)) {
            val docId = DocumentsContract.getDocumentId(uri)
            if ("com.android.providers.media.documents" == uri!!.authority) {
                val id = docId.split(":").toTypedArray()[1]
                val selection = MediaStore.Images.Media._ID + "=" + id
                imagePath = getImagePath(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, selection)
            } else if ("com.android.providers.download.documents" == uri.authority) {
                val contentUri = ContentUris.withAppendedId(
                    Uri.parse("content://downloads/public_downloads"),
                    docId.toLong()
                )
                imagePath = getImagePath(contentUri, null)
            }
        } else if ("content".equals(uri!!.scheme, ignoreCase = true)) {
            imagePath = getImagePath(uri, null)
        } else if ("file".equals(uri.scheme, ignoreCase = true)) {
            imagePath = uri.path
        }
        mImagePath = imagePath!!
        displayImage(imagePath!!)
    }

    private fun displayImage(imagePath: String) {
        if(imagePath!="") {
            try {
                if (file.exists()) {
                    file.delete()
                }
                file.createNewFile()
            } catch (e: java.lang.Exception) {
                e.printStackTrace()
            }
            imageUrl = if (Build.VERSION.SDK_INT >= 24) {
                FileProvider.getUriForFile(
                    context0,
                    "com.wayeal.wateraffair.user2.fileprovider",
                    file
                )
            } else {
                Uri.fromFile(file)
            }
            val uri =
                FileProvider.getUriForFile(
                    context0,
                    "com.wayeal.wateraffair.user2.fileprovider",
                    File(imagePath)
                )
            imageUrl = uri
            setImage()
        }
    }

    //获得图片路径
    @SuppressLint("Range")
    private fun getImagePath(uri: Uri, selection: String?): String? {
        var path: String? = null
        val cursor: Cursor? = context0.getContentResolver().query(uri, null, selection, null, null)
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA))
            }
            cursor.close()
        }
        return path
    }
}