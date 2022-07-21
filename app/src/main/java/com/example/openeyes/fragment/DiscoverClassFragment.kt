package com.example.openeyes.fragment

import android.annotation.SuppressLint
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.openeyes.MyApplication
import com.example.openeyes.R
import com.example.openeyes.adapter.DiscoverClassRVAdapter
import com.example.openeyes.databinding.LayoutDiscoveryClassFragmentBinding
import com.example.openeyes.model.ClassModel
import com.example.openeyes.viewmodel.MyViewModel
import java.net.URLDecoder


/**
 * description ： 发现页的分类Fragment
 * author : lfy
 * email : 1623658271@qq.com
 * date : 2022/7/15 21:52
 */
class DiscoverClassFragment:Fragment() {
    private val TAG = "lfy"
    private lateinit var binding:LayoutDiscoveryClassFragmentBinding
    private lateinit var list:MutableList<ClassModel>
    private lateinit var adapter:DiscoverClassRVAdapter
    private lateinit var viewModel:MyViewModel
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.layout_discovery_class_fragment,container,false);
        return binding.root
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(
            this,
            AndroidViewModelFactory(MyApplication.application!!)
        )[MyViewModel::class.java]
        binding.rvDiscover.layoutManager = StaggeredGridLayoutManager(2, RecyclerView.VERTICAL)
        list = ArrayList()
        adapter = DiscoverClassRVAdapter(list)
        binding.rvDiscover.adapter = adapter
        binding.srDiscover.setOnRefreshListener {
            viewModel.updateFindMoreViewModel()
            binding.srDiscover.isRefreshing = false
        }
        adapter.setOnItemClickListener(object:DiscoverClassRVAdapter.OnItemClickListener{
            override fun onItemClick(
                view: View?,
                holder: RecyclerView.ViewHolder?,
                position: Int,
                classlist: MutableList<ClassModel>
            ) {
                val toClassInActivity = DiscoverFragmentDirections.actionDiscoverFragmentToClassInActivity(classlist[position])
                findNavController().navigate(toClassInActivity)
            }

            override fun onItemLongClick(
                view: View?,
                holder: RecyclerView.ViewHolder?,
                position: Int
            ): Boolean {
                return true
            }

        })
        viewModel.getFindMoreLiveData().observe(viewLifecycleOwner, Observer {
            val dataList = it.itemList
            list.clear()
            for (m in dataList) {
                if (!TextUtils.isEmpty(m.data.icon) && !TextUtils.isEmpty(m.data.title)) {
                    list.add(
                        ClassModel(
                            m.data.id,
                            m.data.icon,
                            m.data.title,
                            m.data.description,
                            m.data.actionUrl,
                            m.data.follow.itemId
                        )
                    )
                }
            }
            adapter.notifyDataSetChanged()
        })
    }

        //额,好像没什么用，必须要安装了应用才行
    fun openDeeplink(context: Context, deepLink:String) {
        val url:String = deepLink
        val deep = url.replace(regex = "%(?![0-9a-fA-F]{2})".toRegex(), "%25")
        Log.d(TAG, "openDeeplink: $deep")
        val urlStr: String = URLDecoder.decode(deep, "UTF-8")
        Log.d(TAG, "openDeeplink:url $urlStr")
        var intent: Intent? = null
        if (null == context || TextUtils.isEmpty(urlStr))
            return
        try {
            intent = Intent.parseUri(urlStr, Intent.URI_INTENT_SCHEME)
        } catch (e:Exception) {
            e.printStackTrace()
        }
        if (intent != null) {
            intent.setComponent(null)
            try {
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            } catch (e: ActivityNotFoundException) {
                e.printStackTrace()
            }
        }

    }
}