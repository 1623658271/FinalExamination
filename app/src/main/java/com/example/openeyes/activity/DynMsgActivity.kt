package com.example.openeyes.activity

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.view.animation.AnimationUtils
import android.view.animation.LayoutAnimationController
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.openeyes.R
import com.example.openeyes.activity.MyApplication.Companion.context
import com.example.openeyes.adapter.DynRVAdapter
import com.example.openeyes.databinding.ActivityDynMsgBinding
import com.example.openeyes.utils.LoadState
import com.example.openeyes.utils.toast
import com.example.openeyes.viewmodel.DynamicMsgPageViewModel

class DynMsgActivity : BaseActivity() {
    private lateinit var binding:ActivityDynMsgBinding
    private lateinit var adapter:DynRVAdapter
    private val dynamicMsgPageViewModel:DynamicMsgPageViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_dyn_msg)
        init()
    }

    private fun init(){
        adapter = DynRVAdapter(true)
        binding.rvDynMsg.adapter = adapter
        binding.rvDynMsg.layoutManager = LinearLayoutManager(this,RecyclerView.VERTICAL,false)
        binding.rvDynMsg.layoutAnimation = // 入场动画
            LayoutAnimationController(
                AnimationUtils.loadAnimation(
                    context,
                    R.anim.recycler_fade_in
                )
            )
        binding.name = intent.getStringExtra("name")!!
        binding.dynMsgToolbar.setNavigationOnClickListener { finish() }

        dynamicMsgPageViewModel.apply {
            loadDynamicMsg(intent.getStringExtra("pathId")!!)
            dynamicItem.observe(this@DynMsgActivity){
                adapter.setRecDynData(it)
            }
            state.observe(this@DynMsgActivity){
                hideAll()
                when(it){
                    LoadState.SUCCESS -> {
                        binding.rvDynMsg.visibility = View.VISIBLE
                    }
                    LoadState.LOADING -> {
                        binding.stateLoading.root.visibility = View.VISIBLE
                    }
                    LoadState.ERROR -> {
                        binding.stateLoadError.root.visibility = View.VISIBLE
                    }
                    else -> {}
                }
            }
            stateMore.observe(this@DynMsgActivity) {
                when (it) {
                    LoadState.LOADING -> adapter.setLoadState(adapter.LOADING)
                    LoadState.SUCCESS -> adapter.setLoadState(adapter.LOADING_COMPLETE)
                    else -> adapter.setLoadState(adapter.LOADING_END)
                }
            }
        }
        adapter.setListener(object :DynRVAdapter.OnItemClickListener{
            override fun onClick(id: Int) {
                DynInActivity.startDynInActivity(this@DynMsgActivity,id)
            }
        })
        setRecyclerOnScrollListener()
        binding.refreshDynMsg.setOnRefreshListener {
            dynamicMsgPageViewModel.loadDynamicMsg(intent.getStringExtra("pathId")!!)
            binding.refreshDynMsg.isRefreshing = false
        }
    }

    companion object{
        fun startDynMsgActivity(context: Context, pathId: String,name:String){
            val intent = Intent(context, DynMsgActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            intent.putExtra("pathId",pathId)
            intent.putExtra("name",name)
            context.startActivity(intent)
        }
        fun fragmentDynMsgActivity(context: Context, activity: Activity, pathId:String,name: String){
            val intent = Intent(activity, DynMsgActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            intent.putExtra("pathId",pathId)
            intent.putExtra("name",name)
            context.startActivity(intent)
        }
    }
    private fun hideAll(){
        binding.rvDynMsg.visibility = View.GONE
        binding.stateLoading.root.visibility = View.GONE
        binding.stateLoadError.root.visibility = View.GONE
    }

    /**
     * 设置滑动监听，以检查上滑状态以更新数据
     */
    fun setRecyclerOnScrollListener(){
        var isUp:Boolean = false
        binding.rvDynMsg.addOnScrollListener(object :RecyclerView.OnScrollListener(){
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                val manager = recyclerView.layoutManager as LinearLayoutManager?
                //当不滑动时
                if (newState === RecyclerView.SCROLL_STATE_IDLE) {
                    //获取最后一个完全显示的itemPosition
                    val lastItemPosition = manager!!.findLastCompletelyVisibleItemPosition()
                    val itemCount = manager.itemCount
                    // 判断是否滑动到了最后一个item，并且是向上滑动
                    Log.e(TAG, "onScrollStateChanged: $lastItemPosition == ${itemCount-1}", )
                    if (lastItemPosition == itemCount - 1 && isUp) {
                        if(dynamicMsgPageViewModel.stateMore.value!=LoadState.LOADING ){
                            loadingMore()
                        }else{
                            "正在加载，不要着急嘛".toast()
                        }
                    }
                }
            }

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                isUp = dy >= 0
            }
        })
    }

    private fun loadingMore() {
        dynamicMsgPageViewModel.loadDynamicMoreMsg()
    }
}