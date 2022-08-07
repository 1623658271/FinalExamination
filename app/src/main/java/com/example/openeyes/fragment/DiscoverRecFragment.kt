package com.example.openeyes.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.view.animation.LayoutAnimationController
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.openeyes.activity.PicWatchActivity
import com.example.openeyes.R
import com.example.openeyes.adapter.RecRVAdapter
import com.example.openeyes.databinding.LayoutDiscoveryRecFragmentBinding
import com.example.openeyes.model.PersonalBean
import com.example.openeyes.model.PicsBean
import com.example.openeyes.model.VideoBean
import com.example.openeyes.utils.LoadState
import com.example.openeyes.utils.toast
import com.example.openeyes.viewmodel.DiscoverPageViewModel

/**
 * description ： 发现页的推荐页fragment
 * author : lfy
 * email : 1623658271@qq.com
 * date : 2022/7/22 13:23
 */
class DiscoverRecFragment:Fragment() {
    private lateinit var binding:LayoutDiscoveryRecFragmentBinding
    private val discoverRecViewModel:DiscoverPageViewModel by viewModels()
    private lateinit var adapter0:RecRVAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.layout_discovery_rec_fragment,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
        initObserver()
    }

    private fun initObserver() {
        discoverRecViewModel.apply {
            dataList.observe(activity!!){
                adapter0.setData(it)
            }
            state1.observe(activity!!){
                hideAll()
                when(it){
                    LoadState.LOADING -> binding.stateLoading.root.visibility = View.VISIBLE
                    LoadState.SUCCESS -> binding.rvRec.visibility = View.VISIBLE
                    LoadState.ERROR -> binding.stateLoadError.root.visibility = View.VISIBLE
                    else->{}
                }
            }
            state.observe(activity!!){
                when(it){
                    LoadState.LOADING->adapter0.setLoadState(adapter0.LOADING)
                    LoadState.SUCCESS->adapter0.setLoadState(adapter0.LOADING_COMPLETE)
                    else ->adapter0.setLoadState(adapter0.LOADING_END)
                }
            }
        }
        binding.refreshRec.setOnRefreshListener {
            discoverRecViewModel.loadRecMsg()
            binding.refreshRec.isRefreshing = false
        }
    }

    fun hideAll(){
        binding.rvRec.visibility = View.GONE
        binding.stateLoading.root.visibility = View.GONE
        binding.stateLoadError.root.visibility = View.GONE
    }

    private fun init() {
        adapter0 = RecRVAdapter()
        binding.rvRec.apply {
            adapter = adapter0
            layoutManager = StaggeredGridLayoutManager(2,RecyclerView.VERTICAL)
            setHasFixedSize(true)
        }

        setRecyclerOnScrollListener()
        binding.rvRec.layoutAnimation = // 入场动画
            LayoutAnimationController(
                AnimationUtils.loadAnimation(
                    context,
                    R.anim.recycler_fade_in
                )
            )

        adapter0.setClickListener(object :RecRVAdapter.OnClickListener{
            override fun onVideoClick(videoBean: VideoBean) {
                val toVideoPlayActivity =
                    DiscoverFragmentDirections.actionDiscoverFragmentToVideoPlayActivity(videoBean)
                findNavController().navigate(toVideoPlayActivity)
            }

            override fun onPicClick(picsBean: PicsBean) {
                PicWatchActivity.fragmentStartPicWatchActivity(context!!,activity!!,picsBean)
            }

            override fun onPersonImageClick(personalBean: PersonalBean) {
                val toPersonMessageActivity =
                    DiscoverFragmentDirections.actionDiscoverFragmentToPersonMessageActivity(
                        personalBean
                    )
                findNavController().navigate(toPersonMessageActivity)
            }

        })
    }

    /**
     * 设置滑动监听，以检查上滑状态以更新数据
     */
    fun setRecyclerOnScrollListener(){
        var isUp:Boolean = false
        binding.rvRec.addOnScrollListener(object :RecyclerView.OnScrollListener(){
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                val manager = recyclerView.layoutManager as StaggeredGridLayoutManager?
                //当不滑动时
                if (newState === RecyclerView.SCROLL_STATE_IDLE) {
                    //获取最后一个完全显示的itemPosition
                    var intArray = IntArray(2)
                    manager!!.findLastCompletelyVisibleItemPositions(intArray)
//                    Log.e(TAG, "onScrollStateChanged: ${intArray[0]} +${intArray[1]} ${manager.itemCount-1}", )
                    val itemCount = manager.itemCount
                    // 判断是否滑动到了最后一个item，并且是向上滑动
//                    Log.e("lfy", "onScrollStateChanged:${intArray.toSet()} = = ${itemCount-1} ", )
                    if (((intArray[0]==itemCount-2) || intArray[0] == itemCount - 1) || (intArray[1] == itemCount - 1) || (intArray[1] == itemCount -2)&& isUp) {
//                        Log.e("lfy", "onScrollStateChanged: "+discoverRecViewModel.state.value )
                        if(discoverRecViewModel.state.value!=LoadState.LOADING){
                            loadingMore()
                        }else{
                            "正在加载,不要着急嘛".toast()
                        }
                    }
                }
            }

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                isUp = dy > 0
            }
        })
    }

    fun loadingMore(){
        discoverRecViewModel.loadRecMoreMsg()
    }
}