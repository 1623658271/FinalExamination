package com.example.openeyes.utils

import android.content.ContentValues
import android.content.Context
import android.util.Log
import com.example.openeyes.model.MyDatabaseHelper
import com.example.openeyes.model.PersonalBean
import com.example.openeyes.model.VideoBean

/**
 * description ： 封装的一个数据库操作类
 * author : lfy
 * email : 1623658271@qq.com
 * date : 2022/8/5 14:29
 */
object MySQLiteHelper {
    private var dbHelper:MyDatabaseHelper? = null
    fun initDatabase(context: Context,name:String,version:Int){
        if(dbHelper==null){
            dbHelper = MyDatabaseHelper(context,name,version)
        }
        dbHelper!!.writableDatabase
        Log.e("lfy", "initDatabase: ", )
    }

    /**
     * 插入数据
     */
    fun insertHistoryToDatabase(name:String,videoBean: VideoBean){
        if(findHistory(name,videoBean)){
            deleteHistory(name,videoBean)
        }
        dbHelper!!.writableDatabase.insert(
            name,null,
            ContentValues().apply {
                put("videoId",videoBean.id)
                put("bigTitle",videoBean.bigTitle)
                put("smallTitle",videoBean.smallTitle)
                put("coverUrl",videoBean.coverUrl)
                put("playUrl",videoBean.playUrl)
                put("video_description",videoBean.description)
                put("nickname",videoBean.personalBean?.nickname?:"")
                put("cover",videoBean.personalBean?.cover?:DefaultUtil.defaultCoverUrl)
                put("person_description",videoBean.personalBean?.description?:"")
                put("uid",videoBean.personalBean?.uid?:0)
                put("avatar",videoBean.personalBean?.avatar?:"")
            }
        )
    }
    /**
     * 删除数据
     */
    fun deleteHistory(name:String,videoBean: VideoBean){
        dbHelper!!.writableDatabase.delete(
            name,"videoId = ?", arrayOf(videoBean.id.toString())
        )
    }
    /**
     * 是否已存在数据
     */
    fun findHistory(name: String,videoBean: VideoBean):Boolean{
        var flag = false
        val cursor = dbHelper!!.writableDatabase.query(name,null,null,null,null,null,null)
        if(cursor.moveToFirst()){
            do{
                if(videoBean.id==cursor.getInt(cursor.getColumnIndex("videoId"))){
                    flag=true
                    break
                }
            }while (cursor.moveToNext())
        }
        cursor.close()
        return flag
    }

    /**
     * 从数据库中获取VideoBean集合
     */
    fun getHistoryVideoBeanList(name:String):MutableList<VideoBean>{
        val cursor = dbHelper!!.writableDatabase.query(name,null,null,null,null,null,null)
        val list:MutableList<VideoBean> = ArrayList()
        if(cursor.moveToFirst()){
            do {
                list.add(
                    VideoBean(
                        cursor.getInt(cursor.getColumnIndex("videoId")),
                        cursor.getString(cursor.getColumnIndex("bigTitle")),
                        cursor.getString(cursor.getColumnIndex("smallTitle")),
                        cursor.getString(cursor.getColumnIndex("coverUrl")),
                        cursor.getString(cursor.getColumnIndex("playUrl")),
                        cursor.getString(cursor.getColumnIndex("video_description")),
                        PersonalBean(
                            cursor.getInt(cursor.getColumnIndex("uid")),
                            cursor.getString(cursor.getColumnIndex("avatar")),
                            cursor.getString(cursor.getColumnIndex("cover")),
                            cursor.getString(cursor.getColumnIndex("person_description")),
                            cursor.getString(cursor.getColumnIndex("nickname")),
                            "", ""
                        ),
                        null
                    )
                )
            }while (cursor.moveToNext())
        }
        cursor.close()
        return list
    }

    /**
     * 清除所有数据
     */
    fun deleteAll(name:String){
        dbHelper!!.writableDatabase.delete(name,null,null)
    }
}