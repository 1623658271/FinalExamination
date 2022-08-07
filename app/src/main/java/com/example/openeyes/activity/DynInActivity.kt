package com.example.openeyes.activity

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.animation.AnimationUtils
import android.view.animation.LayoutAnimationController
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.openeyes.R
import com.example.openeyes.adapter.RecRVAdapter
import com.example.openeyes.databinding.ActivityDynInBinding
import com.example.openeyes.model.PersonalBean
import com.example.openeyes.model.PicsBean
import com.example.openeyes.model.VideoBean
import com.example.openeyes.utils.LoadState
import com.example.openeyes.utils.toast
import com.example.openeyes.viewmodel.DynamicInMsgViewModel
import kotlinx.android.synthetic.main.activity_history.view.*

class DynInActivity : BaseActivity() {
    private lateinit var binding:ActivityDynInBinding
    private val dynamicInMsgViewModel:DynamicInMsgViewModel by viewModels()
    private lateinit var adapter0:RecRVAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_dyn_in)
        init()
    }

    private fun init(){
        val id = intent.getIntExtra("id",0)
        adapter0 = RecRVAdapter()
        binding.rvDynIn.apply {
            adapter = adapter0
            layoutManager = LinearLayoutManager(context,RecyclerView.VERTICAL,false)
            layoutAnimation = // 入场动画
                LayoutAnimationController(
                    AnimationUtils.loadAnimation(
                        context,
                        R.anim.recycler_fade_in
                    )
                )
        }
        binding.dynMsgInToolbar.setNavigationOnClickListener { finish() }
        dynamicInMsgViewModel.apply {
            loadDynInMsg(id)
            dynMapList.observe(this@DynInActivity){
                adapter0.setData(it)
            }
            state.observe(this@DynInActivity){
                hideAll()
                when(it){
                    LoadState.SUCCESS -> {
                        binding.rvDynIn.visibility = View.VISIBLE
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
            stateMore.observe(this@DynInActivity){
                when (it) {
                    LoadState.LOADING -> adapter0.setLoadState(adapter0.LOADING)
                    LoadState.SUCCESS -> adapter0.setLoadState(adapter0.LOADING_COMPLETE)

                    else -> adapter0.setLoadState(adapter0.LOADING_END)
                }
            }
        }
        adapter0.setClickListener(object :RecRVAdapter.OnClickListener{
            override fun onVideoClick(videoBean: VideoBean) {
                VideoPlayActivity.startVideoPlayActivity(this@DynInActivity,videoBean)
            }

            override fun onPicClick(picsBean: PicsBean) {
                PicWatchActivity.startPicWatchActivity(this@DynInActivity,picsBean)
            }

            override fun onPersonImageClick(personalBean: PersonalBean) {
                PersonMessageActivity.startPersonMessageActivity(this@DynInActivity,personalBean)
            }

        })
        setRecyclerOnScrollListener()
        binding.refreshDynIn.setOnRefreshListener {
            dynamicInMsgViewModel.loadDynInMsg(id)
            binding.refreshDynIn.isRefreshing = false
        }
    }
    companion object{
        fun startDynInActivity(context: Context, id: Int){
            val intent = Intent(context, DynInActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            intent.putExtra("id",id)
            context.startActivity(intent)
        }
        fun fragmentDynInActivity(context: Context, activity: Activity, id:Int){
            val intent = Intent(activity, DynInActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            intent.putExtra("id",id)
            context.startActivity(intent)
        }
    }

    private fun hideAll(){
        binding.rvDynIn.visibility = View.GONE
        binding.stateLoading.root.visibility = View.GONE
        binding.stateLoadError.root.visibility = View.GONE
    }

    /**
     * 设置滑动监听，以检查上滑状态以更新数据
     */
    fun setRecyclerOnScrollListener(){
        var isUp:Boolean = false
        binding.rvDynIn.addOnScrollListener(object :RecyclerView.OnScrollListener(){
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                val manager = recyclerView.layoutManager as LinearLayoutManager?
                //当不滑动时
                if (newState === RecyclerView.SCROLL_STATE_IDLE) {
                    //获取最后一个完全显示的itemPosition
                    val lastItemPosition = manager!!.findLastCompletelyVisibleItemPosition()
                    val itemCount = manager.itemCount
                    // 判断是否滑动到了最后一个item，并且是向上滑动
                    if (lastItemPosition == itemCount - 1 && isUp) {
                        if(dynamicInMsgViewModel.stateMore.value!=LoadState.LOADING){
                            loadingMore()
                        }else{
                            "正在加载，不要着急嘛".toast()
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

    private fun loadingMore() {
        dynamicInMsgViewModel.loadDynInMoreMsg()
    }
}