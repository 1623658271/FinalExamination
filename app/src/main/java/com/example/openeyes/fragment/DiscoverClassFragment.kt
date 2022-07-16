package com.example.openeyes.fragment

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
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.openeyes.MyApplication
import com.example.openeyes.R
import com.example.openeyes.adapter.DiscoverClassRVAdapter
import com.example.openeyes.api.URL
import com.example.openeyes.databinding.LayoutDiscoveryClassFragmentBinding
import com.example.openeyes.model.ClassModel
import com.example.openeyes.model.FindMoreClassBean
import com.example.openeyes.respository.MyRepository
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.net.URLDecoder
import kotlin.math.log

/**
 * description ： TODO:类的作用
 * author : lfy
 * email : 1623658271@qq.com
 * date : 2022/7/15 21:52
 */
class DiscoverClassFragment:Fragment() {
    private val TAG = "lfy"
    private lateinit var binding:LayoutDiscoveryClassFragmentBinding
    private lateinit var list:MutableList<ClassModel>
    private lateinit var adapter:DiscoverClassRVAdapter
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.layout_discovery_class_fragment,container,false);
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.rvDiscover.layoutManager = StaggeredGridLayoutManager(2, RecyclerView.VERTICAL)
        list = ArrayList()
        adapter = DiscoverClassRVAdapter(list)
        binding.rvDiscover.adapter = adapter
        binding.srDiscover.setOnRefreshListener {
            updateMessage(false)
            binding.srDiscover.isRefreshing = false
        }
        binding.rvDiscover.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val lastVisibleItemPosition = LinearLayoutManager(MyApplication.context).findLastVisibleItemPosition()
                val count = adapter.itemCount
                if (lastVisibleItemPosition + 1 == count ) {
                    updateMessage(true)
                }
            }
        })
        adapter.setOnItemClickListener(object:DiscoverClassRVAdapter.OnItemClickListener{
            override fun onItemClick(
                view: View?,
                holder: RecyclerView.ViewHolder?,
                position: Int,
                classlist: MutableList<ClassModel>
            ) {
                //MyApplication.context?.let { openDeeplink(it,classlist[position].actionUrl) }

            }


            override fun onItemLongClick(
                view: View?,
                holder: RecyclerView.ViewHolder?,
                position: Int
            ): Boolean {
                return true
            }

        })
        updateMessage(false)
    }

//    //额,好像没什么用，必须要安装了应用才行
//    fun openDeeplink(context:Context, deepLink:String) {
//        val url:String = deepLink
//        val deep = url.replace(regex = "%(?![0-9a-fA-F]{2})".toRegex(), "%25")
//        Log.d(TAG, "openDeeplink: $deep")
//        val urlStr: String = URLDecoder.decode(deep, "UTF-8")
//        Log.d(TAG, "openDeeplink:url $urlStr")
//        var intent:Intent? = null
//        if (null == context || TextUtils.isEmpty(urlStr))
//            return
//        try {
//            intent = Intent.parseUri(urlStr, Intent.URI_INTENT_SCHEME)
//        } catch (e:Exception) {
//            e.printStackTrace()
//        }
//        if (intent != null) {
//            intent.setComponent(null)
//            try {
//                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                context.startActivity(intent);
//            } catch (e: ActivityNotFoundException) {
//                e.printStackTrace()
//            }
//        }
//
//    }

    fun updateMessage(flag:Boolean){
        Thread {
            MyRepository(URL.FindMoreClassUrl)
                .getService()
                .getFindMoreClassMsg()
                .enqueue(object : Callback<FindMoreClassBean> {
                    override fun onResponse(
                        call: Call<FindMoreClassBean>,
                        response: Response<FindMoreClassBean>
                    ) {
                        Log.d(TAG, "onResponse: "+response.body())
                        var modellist = response?.body()!!.itemList
                        if(!flag) {
                            list.clear()
                        }
                        for (m in modellist) {
                            if (!TextUtils.isEmpty(m.data.icon) && !TextUtils.isEmpty(m.data.title)) {
                                Log.d(TAG, "onResponse:2 "+m.data)
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
                    }

                    override fun onFailure(call: Call<FindMoreClassBean>, t: Throwable) {
                        Log.d(TAG, "onFailure: ")
                    }

                })
        }.start()
    }
}