package com.example.openeyes.fragment

import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.openeyes.MyApplication
import com.example.openeyes.R
import com.example.openeyes.adapter.DiscoverRVAdapter
import com.example.openeyes.api.URL
import com.example.openeyes.databinding.LayoutDiscoveryFragmentBinding
import com.example.openeyes.model.FindMoreBean
import com.example.openeyes.model.HotClassModel
import com.example.openeyes.respository.MyRepository
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.math.log

/**
 * description ： TODO:类的作用
 * author : lfy
 * email : 1623658271@qq.com
 * date : 2022/7/15 08:50
 */
class discoverFragment:Fragment() {
    private lateinit var binding:LayoutDiscoveryFragmentBinding
    private lateinit var list:MutableList<HotClassModel>
    private lateinit var adapter:DiscoverRVAdapter
    private val TAG = "lfy"
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.layout_discovery_fragment,container,false);
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.rvDiscover.layoutManager = StaggeredGridLayoutManager(2,RecyclerView.VERTICAL)
        list = ArrayList()
        adapter = DiscoverRVAdapter(list)
        binding.rvDiscover.adapter = adapter
        binding.srDiscover.setOnRefreshListener {
            updateMessage()
            binding.srDiscover.isRefreshing = false
        }
        updateMessage()
    }

    fun updateMessage(){
        Thread {
            MyRepository(URL().FindMoreUrl)
                .getService()
                .getFindMoreMsg()
                .enqueue(object : Callback<FindMoreBean> {
                    override fun onResponse(
                        call: Call<FindMoreBean>,
                        response: Response<FindMoreBean>
                    ) {
                        Log.d(TAG, "onResponse: "+response.body())
                        var modellist = response?.body()!!.itemList
                        list.clear()
                        for (m in modellist) {
                            if (!TextUtils.isEmpty(m.data.icon) && !TextUtils.isEmpty(m.data.title)) {
                                Log.d(TAG, "onResponse:2 "+m.data)
                                list.add(
                                    HotClassModel(
                                        0,
                                        m.data.icon,
                                        m.data.title,
                                        "111",
                                        "222",
                                        1
                                    )
                                )
                            }
                        }
                        adapter.notifyDataSetChanged()
                    }

                    override fun onFailure(call: Call<FindMoreBean>, t: Throwable) {
                        Log.d(TAG, "onFailure: ")
                    }

                })
        }.start()
    }
}