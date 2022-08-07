package com.example.openeyes.adapter

import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.openeyes.R
import com.example.openeyes.activity.DynMsgActivity
import com.example.openeyes.activity.MyApplication
import com.example.openeyes.databinding.ItemDynNormalBinding
import com.example.openeyes.databinding.ItemDynRvBinding
import com.example.openeyes.databinding.ItemTextCardBinding
import com.example.openeyes.databinding.LayoutLoadMessageBinding
import com.example.openeyes.model.AllDynamicsBean
import com.example.openeyes.model.DynamicMsgBean

/**
 * description ： 首页主题页的RV适配器
 * author : lfy
 * email : 1623658271@qq.com
 * date : 2022/8/6 18:51
 */
class DynRVAdapter(val flag:Boolean):RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    val TYPE_CLASS = 0
    val TYPE_NORMAL = 1
    val TYPE_TEXT = 2

    //当前加载状态，默认为加载完成
    private var loadState = 2
    // 正在加载
    val LOADING = 1
    // 加载完成
    val LOADING_COMPLETE = 2
    // 加载到底
    val LOADING_END = 3

    companion object {
        //脚布局
        private const val TYPE_FOOTER = 3
    }
    private val adapter:DynClassRVAdapter = DynClassRVAdapter()
    private var first = true
    private val allList:MutableList<AllDynamicsBean.TabInfo.Tab> = ArrayList()
    private val recList:MutableList<DynamicMsgBean.Item> = ArrayList()

    inner class FootViewHolder(var binding: LayoutLoadMessageBinding):RecyclerView.ViewHolder(binding.root)

    inner class TextViewHolder(val binding:ItemTextCardBinding):RecyclerView.ViewHolder(binding.root)

    inner class ClassViewHolder(val binding:ItemDynRvBinding):RecyclerView.ViewHolder(binding.root)

    inner class NormalViewHolder(val binding:ItemDynNormalBinding):RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        when (viewType) {
            TYPE_CLASS -> {
                val binding:ItemDynRvBinding = DataBindingUtil.inflate(
                    LayoutInflater.from(parent.context),
                    R.layout.item_dyn_rv,
                    parent,
                    false
                )
                return ClassViewHolder(binding)
            }
            TYPE_TEXT -> {
                val binding:ItemTextCardBinding = DataBindingUtil.inflate(
                    LayoutInflater.from(parent.context),
                    R.layout.item_text_card,
                    parent,
                    false
                )
                return TextViewHolder(binding)
            }
            TYPE_FOOTER->{
                val loadBinding: LayoutLoadMessageBinding = DataBindingUtil.inflate(
                    LayoutInflater.from(parent.context),
                    R.layout.layout_load_message,
                    parent,
                    false
                )
                return FootViewHolder(loadBinding)
            }
            else -> {
                val binding:ItemDynNormalBinding = DataBindingUtil.inflate(
                    LayoutInflater.from(parent.context),
                    R.layout.item_dyn_normal,
                    parent,
                    false
                )
                return NormalViewHolder(binding)
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is ClassViewHolder -> {
                if(first){
                    first = false
                    holder.binding.rvDynItem.adapter = adapter
                    holder.binding.rvDynItem.layoutManager = GridLayoutManager(MyApplication.context!!,2,RecyclerView.VERTICAL,false)
                    adapter.setData(allList)
                    if(adapter.getListener()==null){
                        adapter.setListener(object :DynClassRVAdapter.OnItemClickListener{
                            override fun onClick(bean: AllDynamicsBean.TabInfo.Tab) {
                                DynMsgActivity.startDynMsgActivity(MyApplication.context!!,bean.id.toString(),bean.name)
                            }
                        })
                    }
                }else{
                    holder.binding.rvDynItem.adapter = adapter
                    holder.binding.rvDynItem.layoutManager = GridLayoutManager(MyApplication.context!!,2,RecyclerView.VERTICAL,false)
                    adapter.setData(allList)
                }
            }
            is TextViewHolder -> {
                holder.binding.tvTextCard.setBackgroundColor(Color.BLUE)
                if(position==0){
                    holder.binding.name = "主题分类"
                }else{
                    holder.binding.name = "推荐主题"
                }
            }
            is NormalViewHolder -> {
                val p = if(!flag){
                    position - 3
                }else{
                    position
                }
                holder.binding.message = recList[p]
                holder.binding.llDynNormal.setOnClickListener {
                    listener?.onClick(recList[p].data.id)
                }
            }
            is FootViewHolder -> {
                when(loadState) {
                    LOADING -> {
                        holder.binding.tvLoad.visibility = View.VISIBLE
                        holder.binding.tvLoad.text = "加载中..."
                    }
                    LOADING_COMPLETE -> {
                        holder.binding.tvLoad.visibility = View.GONE
                        holder.binding.tvLoad.text = "加载完成"
                    }
                    LOADING_END -> {
                        holder.binding.tvLoad.visibility = View.VISIBLE
                        holder.binding.tvLoad.text = "亲！到底线了~~~"
                    }

                }
            }
        }
    }

    override fun getItemCount():Int{
        return if(!flag){
            recList.size + 3 + 1
        }else{
            recList.size + 1
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if(flag){
            if(position+1 == itemCount){
                TYPE_FOOTER
            }else{
                TYPE_NORMAL
            }
        }else{
            if(position+1 == itemCount){
                TYPE_FOOTER
            }else {
                when (position) {
                    0, 2 -> {
                        TYPE_TEXT
                    }
                    1 -> {
                        TYPE_CLASS
                    }
                    else -> {
                        TYPE_NORMAL
                    }
                }
            }
        }

    }

    fun setAllDynData(allList:MutableList<AllDynamicsBean.TabInfo.Tab>){
        val before = this.allList.size
        this.allList.clear()
        this.allList.addAll(allList)
        notifyItemRangeChanged(before,this.allList.size-before)
    }
    fun setRecDynData(recList:MutableList<DynamicMsgBean.Item>){
        val before = this.recList.size
        this.recList.clear()
        this.recList.addAll(recList)
        notifyItemRangeChanged(before,this.recList.size-before)
    }

    private var listener:OnItemClickListener? = null

    interface OnItemClickListener{
        fun onClick(id:Int)
    }

    fun setListener(listener:OnItemClickListener){
        this.listener = listener
    }

    /**
     * 设置加载状态，以显示或隐藏脚布局
     */
    fun setLoadState(loadState:Int){
        if(this.loadState!=loadState){
            this.loadState = loadState
            notifyItemChanged(itemCount-1)
        }
    }
}