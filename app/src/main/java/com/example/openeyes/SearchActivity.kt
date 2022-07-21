package com.example.openeyes

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.bumptech.glide.Glide.init
import com.example.openeyes.databinding.ItemCommentBinding
import com.example.openeyes.databinding.ItemHotSearchBinding
import com.example.openeyes.databinding.ItemSearchResultBinding
import com.example.openeyes.databinding.LayoutSearchActivityBinding
import com.example.openeyes.model.PersonalModel
import com.example.openeyes.model.SearchModel
import com.example.openeyes.model.VideoBean
import com.example.openeyes.utils.DecodeUtil
import com.example.openeyes.utils.DefaultUtil
import com.example.openeyes.viewmodel.MyViewModel
import kotlinx.android.synthetic.main.layout_homepage_fragment.view.*
import kotlinx.android.synthetic.main.layout_search_activity.view.*

class SearchActivity : AppCompatActivity() {
    private lateinit var binding:LayoutSearchActivityBinding
    private lateinit var viewModel: MyViewModel
    private lateinit var listHot:MutableList<String>
    private lateinit var adapterHot:HotRVAdapter
    private lateinit var adapterResult:ResultRVAdapter
    private lateinit var listResult:MutableList<VideoBean>
    private var firstSearch = true
    private var nextPageUrl:String = ""
    private val TAG = "lfy"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initView()
        initData()
    }


    private fun initView(){
        binding = DataBindingUtil.setContentView(this,R.layout.layout_search_activity)
        viewModel = ViewModelProvider(this,ViewModelProvider.AndroidViewModelFactory(application))[MyViewModel::class.java]
        listHot = ArrayList()
        listResult = ArrayList()
        adapterResult = ResultRVAdapter(listResult)
        adapterHot = HotRVAdapter(listHot)
        binding.rvSearchResult.adapter = adapterResult
        adapterResult.setListener(object :ResultRVAdapter.OnItemClickListener{
            override fun onClick(viewBean: VideoBean) {
                VideoPlayActivity.startVideoPlayActivity(this@SearchActivity,viewBean)
            }
        })
        binding.rvSearchResult.layoutManager = LinearLayoutManager(this,RecyclerView.VERTICAL,false)
        binding.rvHotSearch.adapter = adapterHot
        binding.rvHotSearch.layoutManager = StaggeredGridLayoutManager(3, RecyclerView.VERTICAL)
    }


    private fun initData() {
        viewModel.getHotSearchLiveData().observe(this){
            listHot.addAll(it.toMutableList())
            adapterHot.notifyDataSetChanged()
            Log.d(TAG, "initData: $it")
        }
        adapterHot.setListener(object :HotRVAdapter.OnItemClickListener{
            override fun onClick(text: String) {
                binding.searchView.setQuery(text,false)
                doSearch(text)
            }
        })
        binding.searchView.apply {
            setIconifiedByDefault(false)
            setOnQueryTextListener(object :SearchView.OnQueryTextListener{
                override fun onQueryTextSubmit(query: String?): Boolean {
                    if(TextUtils.isEmpty(query)){

                    }else {
                        doSearch(query!!)
                    }
                    return false
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    if(TextUtils.isEmpty(query)){
                        binding.llHotEarch.visibility = View.VISIBLE
                        binding.rvSearchResult.visibility = View.GONE
                    }else{

                    }
                    return false
                }

            })
        }
    }

    fun doSearch(query:String){
        binding.llHotEarch.visibility = View.GONE
        if(firstSearch){
            firstSearch=false
            viewModel.getSearchLiveData(query).observe(this){
                listResult.clear()
                for(m in it.itemList){
                    if(m.type=="followCard"){
                        listResult.add(VideoBean(m.data.content!!.data.id?:0,m.data.content.data.title,m.data.content.data.author?.name?:"",
                            m.data.content.data.cover.feed,m.data.content.data.playUrl,m.data.content.data.description,
                            PersonalModel(m.data.content.data.author?.id?:0,m.data.content.data.author?.icon?:"",
                                DefaultUtil.defaultCoverUrl,m.data.content.data.author?.description?:"",
                                m.data.content.data.author?.name?:"")
                        )
                        )
                    }
                }
                nextPageUrl = DecodeUtil.urlDecode(it.nextPageUrl?:"")
                adapterResult.notifyDataSetChanged()
                binding.rvSearchResult.visibility = View.VISIBLE
            }
        }else{
            viewModel.updateSearchLiveData(query)
        }
    }


    class HotRVAdapter(var list:MutableList<String>):RecyclerView.Adapter<RecyclerView.ViewHolder>(){

        inner class MyViewHolder(itemView: ItemHotSearchBinding):RecyclerView.ViewHolder(itemView.root){
            var binding: ItemHotSearchBinding = itemView
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
            val binding:ItemHotSearchBinding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.item_hot_search,
                parent,
                false
            )
            return MyViewHolder(binding)
        }

        override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
            if(holder is MyViewHolder){
                holder.binding.tvHot.apply {
                    text = list[position]
                    setOnClickListener { listener?.onClick(list[position]) }
                }
            }
        }

        override fun getItemCount(): Int = list.size

        private var listener:OnItemClickListener? = null

        interface OnItemClickListener{
            fun onClick(text:String)
        }

        fun setListener(listener:OnItemClickListener){
            this.listener = listener
        }
    }

    class ResultRVAdapter(var list:MutableList<VideoBean>):RecyclerView.Adapter<RecyclerView.ViewHolder>(){
        inner class MyViewHolder(itemView: ItemSearchResultBinding):RecyclerView.ViewHolder(itemView.root){
            var binding: ItemSearchResultBinding = itemView
        }
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
            val binding:ItemSearchResultBinding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.item_search_result,
                parent,
                false
            )
            return MyViewHolder(binding)
        }

        override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
            if(holder is MyViewHolder){
                holder.binding.message = list[position]
                holder.binding.ivItemResultVideo.setOnClickListener { listener?.onClick(list[position]) }
            }
        }

        override fun getItemCount(): Int = list.size

        private var listener: OnItemClickListener? = null

        interface OnItemClickListener{
            fun onClick(viewBean:VideoBean)
        }

        fun setListener(listener:OnItemClickListener){
            this.listener = listener
        }
    }
}