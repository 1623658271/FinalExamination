package com.example.openeyes.model

/**
 * description ： TODO:类的作用
 * author : lfy
 * email : 1623658271@qq.com
 * date : 2022/7/14 23:03
 */
data class FindMoreBean(
    val itemlist: List<Itemlist>,
    val count:Int,
    var total:Int,
    var nextpageurl: String,
    var adexist:Boolean
){
    data class Itemlist (
        val type: String,
        val data: Data,
        val trackingdata: String,
        val tag: String,
        val id:Int,
        val adindex:Int,
    ){
        data class Data (
            val datatype: String,
            val itemlist: List<Itemlist>,
            val count:Int
        ){
            data class Itemlist (
                val type: String,
                val data: Data,
                val trackingdata: String,
                val tag: String,
                val id:Int,
                val adindex:Int,
            ){
                data class Data (
                    val datatype: String,
                    val id:Int,
                    val title: String,
                    val description: String,
                    val image: String,
                    val actionurl: String,
                    val adtrack: List<String>,
                    val shade:Boolean,
                    val label: Label,
                    val labellist: List<String>,
                    val header: Header,
                    val autoplay:Boolean,
                ){
                    data class Label (
                        val text: String,
                        val card: String,
                        val detail: String
                    )
                    data class Header (
                        private var id:Int,
                        private var title: String,
                        private var font: String,
                        private var subtitle: String,
                        private var subtitlefont: String,
                        private var textalign: String,
                        private var cover: String,
                        private var label: String,
                        private var actionurl: String,
                        private var labellist: String,
                        private var righttext: String,
                        private var icon: String,
                        private var description: String,
                    )
                }
            }
        }
    }
}