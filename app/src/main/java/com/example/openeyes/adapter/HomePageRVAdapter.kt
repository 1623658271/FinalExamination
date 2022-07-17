package com.example.openeyes.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.OnLifecycleEvent
import androidx.recyclerview.widget.RecyclerView
import com.example.openeyes.MyApplication
import com.example.openeyes.R
import com.example.openeyes.databinding.ItemHomepageVideoBinding
import com.example.openeyes.databinding.LayoutLoadMessageBinding
import com.example.openeyes.model.VideoBean
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.*


/**
 * description ： TODO:类的作用
 * author : lfy
 * email : 1623658271@qq.com
 * date : 2022/7/16 19:43
 */
class HomePageRVAdapter(val videoBeanList: MutableList<VideoBean>):
    RecyclerView.Adapter<RecyclerView.ViewHolder>(),LifecycleObserver {

    inner class MyViewHolder(itemView:ItemHomepageVideoBinding):RecyclerView.ViewHolder(itemView.root){
        var binding:ItemHomepageVideoBinding = itemView
    }
    inner class LoadHolder(itemView:LayoutLoadMessageBinding):RecyclerView.ViewHolder(itemView.root){

    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

            val binding:ItemHomepageVideoBinding  = DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.item_homepage_video,
                parent,
                false
            )
            return MyViewHolder(binding)
//         else {
//            val loadBinding: LayoutLoadMessageBinding = DataBindingUtil.inflate(
//                LayoutInflater.from(parent.context),
//                R.layout.layout_load_message,
//                parent,
//                false
//            )
//            return LoadHolder(loadBinding)
//        }
    }

    override fun getItemCount(): Int = videoBeanList.size

    override fun unregisterAdapterDataObserver(observer: RecyclerView.AdapterDataObserver) {
        super.unregisterAdapterDataObserver(observer)
    }
    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    fun onPause(owner: LifecycleOwner?) {

    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if(holder is MyViewHolder) {
            holder.binding.message = videoBeanList[position]
        }else{

        }
    }

    interface LoadCallbackListener{
        fun loadMoreMsg()
    }
}