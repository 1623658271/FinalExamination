package com.example.openeyes.model

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

/**
 * description ： 数据库
 * author : lfy
 * email : 1623658271@qq.com
 * date : 2022/8/5 14:19
 */
class MyDatabaseHelper(val context: Context,name:String,version:Int):SQLiteOpenHelper(context,name,null,version) {
    private val createVideo = "create table Video ("+
            "id integer primary key autoincrement,"+
            "videoId text," +
            "bigTitle text," +
            "smallTitle text," +
            "coverUrl text," +
            "playUrl text," +
            "video_description text," +
            "uid integer," +
            "avatar text," +
            "cover text," +
            "person_description text," +
            "nickname text)"

    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(createVideo)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {

    }

}