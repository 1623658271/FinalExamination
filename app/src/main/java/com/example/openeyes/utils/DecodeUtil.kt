package com.example.openeyes.utils

import java.net.URLDecoder

/**
 * description ： TODO:类的作用
 * author : lfy
 * email : 1623658271@qq.com
 * date : 2022/7/18 13:24
 */
object DecodeUtil {
    fun urlDecode(url:String):String = URLDecoder.decode(
        url.replace(regex = "%(?![0-9a-fA-F]{2})".toRegex(),
            "%25"), "UTF-8")
}