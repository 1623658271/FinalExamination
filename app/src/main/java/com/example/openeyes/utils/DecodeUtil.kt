package com.example.openeyes.utils

import java.net.URLDecoder

/**
 * description ： url解码工具类
 * author : lfy
 * email : 1623658271@qq.com
 * date : 2022/7/18 13:24
 */
object DecodeUtil {
    fun urlDecode(url:String):String{
        return if(!url.isEmpty()){
            URLDecoder.decode(
                url.replace(regex = "%(?![0-9a-fA-F]{2})".toRegex(),
                    "%25"), "UTF-8")
        }else{
            ""
        }
    }
}