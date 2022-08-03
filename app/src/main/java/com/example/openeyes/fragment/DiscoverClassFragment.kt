package com.example.openeyes.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.view.animation.LayoutAnimationController
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.openeyes.MyApplication
import com.example.openeyes.R
import com.example.openeyes.adapter.DiscoverClassRVAdapter
import com.example.openeyes.databinding.LayoutDiscoveryClassFragmentBinding
import com.example.openeyes.model.ClassBean
import com.example.openeyes.utils.LoadState
import com.example.openeyes.viewmodel.DiscoverPageViewModel


/**
 * description ： 发现页的分类Fragment
 * author : lfy
 * email : 1623658271@qq.com
 * date : 2022/7/15 21:52
 */
class DiscoverClassFragment:Fragment() {
//    private val TAG = "lfy"
    private lateinit var binding:LayoutDiscoveryClassFragmentBinding
    private lateinit var adapter:DiscoverClassRVAdapter
    private val discoverClassViewModel:DiscoverPageViewModel by viewModels()
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
        init()
        initObserver()
    }

    private fun initObserver() {
        discoverClassViewModel.apply {
            classList.observe(activity!!){
                adapter.setData(it)
            }
            state2.observe(activity!!){
                hideAll()
                when(it){
                    LoadState.LOADING -> binding.stateLoading.root.visibility = View.VISIBLE
                    LoadState.SUCCESS -> binding.rvDiscover.visibility = View.VISIBLE
                    LoadState.ERROR -> binding.stateLoadError.root.visibility = View.VISIBLE
                    else->{}
                }
            }
        }
        binding.srDiscover.setOnRefreshListener {
            discoverClassViewModel.loadClassMsg()
            binding.srDiscover.isRefreshing = false
        }
    }

    private fun hideAll() {
        binding.rvDiscover.visibility = View.GONE
        binding.stateLoading.root.visibility = View.GONE
        binding.stateLoadError.root.visibility = View.GONE
    }

    fun init(){
        binding.rvDiscover.layoutManager = StaggeredGridLayoutManager(2, RecyclerView.VERTICAL)
        adapter = DiscoverClassRVAdapter()
        binding.rvDiscover.adapter = adapter
        adapter.setOnItemClickListener(object:DiscoverClassRVAdapter.OnItemClickListener{
            override fun onItemClick(
                view: View?,
                holder: RecyclerView.ViewHolder?,
                position: Int,
                classlist: MutableList<ClassBean>
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
        binding.rvDiscover.layoutAnimation = // 入场动画
            LayoutAnimationController(
                AnimationUtils.loadAnimation(
                    context,
                    R.anim.recycler_fade_in
                )
            )
    }

//        //额,好像没什么用，必须要安装了应用才行
//    fun openDeeplink(context: Context, deepLink:String) {
//        val url:String = deepLink
//        val deep = url.replace(regex = "%(?![0-9a-fA-F]{2})".toRegex(), "%25")
////        Log.d(TAG, "openDeeplink: $deep")
//        val urlStr: String = URLDecoder.decode(deep, "UTF-8")
////        Log.d(TAG, "openDeeplink:url $urlStr")
//        var intent: Intent? = null
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
}