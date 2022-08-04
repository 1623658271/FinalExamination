package com.example.openeyes.activity

import android.os.Bundle
import android.view.View
import android.view.animation.AnimationUtils
import android.view.animation.LayoutAnimationController
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.openeyes.activity.MyApplication.Companion.context
import com.example.openeyes.R
import com.example.openeyes.adapter.ClassInRVAdapter
import com.example.openeyes.api.URL
import com.example.openeyes.databinding.ActivityClassInBinding
import com.example.openeyes.model.*
import com.example.openeyes.utils.LoadState
import com.example.openeyes.viewmodel.ClassInPageViewModel

class ClassInActivity : BaseActivity() {
    private lateinit var binding:ActivityClassInBinding
    private lateinit var adapter:ClassInRVAdapter
    private lateinit var classModel:ClassBean
    private val classInPageViewModel:ClassInPageViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_class_in)
        init()
        initObserver()
    }

    private fun initObserver() {
        classInPageViewModel.apply {
            loadClassInMsg(classModel.id.toString(),URL.udid)
            mapList.observe(this@ClassInActivity){
                adapter.setData(it)
            }
            state.observe(this@ClassInActivity){
                hideAll()
                when(it){
                    LoadState.SUCCESS -> {
                        binding.rvClassIn.visibility = View.VISIBLE
                    }
                    LoadState.LOADING -> {
                        binding.stateLoading.root.visibility = View.VISIBLE
                    }
                    LoadState.ERROR -> {
                        binding.stateLoadError.root.visibility = View.VISIBLE
                    }
                    else->{}
                }
            }
        }
        binding.refreshClassIn.setOnRefreshListener {
            classInPageViewModel.loadClassInMsg(classModel.id.toString(),URL.udid)
            binding.refreshClassIn.isRefreshing = false
        }
    }

    fun hideAll(){
        binding.rvClassIn.visibility = View.GONE
        binding.stateLoading.root.visibility = View.GONE
        binding.stateLoadError.root.visibility = View.GONE
    }

    private fun init(){
        adapter = ClassInRVAdapter()
        binding.classDeepToolbar.setNavigationOnClickListener { finish() }
        binding.rvClassIn.adapter = adapter
        binding.rvClassIn.layoutManager = LinearLayoutManager(this,RecyclerView.VERTICAL,false)
        classModel = intent.getParcelableExtra("classModel")!!
        binding.classDeepToolbar.title = "openEyes   分类:《${classModel.title.split('#').last()}》"
        adapter.setClickListener(object :ClassInRVAdapter.OnSomethingClickedListener{
            override fun onVideoImageClickedListener(videoBean: VideoBean) {
                VideoPlayActivity.startVideoPlayActivity(this@ClassInActivity, videoBean)
            }

            override fun onAvatarImageClickedListener(videoBean: VideoBean) {
                PersonMessageActivity.startPersonMessageActivity(
                    this@ClassInActivity,
                    videoBean.personalBean!!
                )
            }
        })
        setRecyclerOnScrollListener()
        binding.rvClassIn.layoutAnimation = // 入场动画
            LayoutAnimationController(
                AnimationUtils.loadAnimation(
                    context,
                    R.anim.recycler_fade_in
                )
            )
    }

//    companion object{
//        fun fragmentStartClassInActivity(context: Context, activity: Activity, classModel: ClassBean){
//            val mIntent = Intent(activity, ClassInActivity::class.java)
//            mIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
//            mIntent.putExtra("classModel",classModel)
//            context.startActivity(mIntent)
//        }
//    }

    fun loadMore() {
        adapter.setClassInLoadState(adapter.LOADING)
        val success = classInPageViewModel.loadClassInMoreMsg(classModel.id.toString(),URL.udid)
        if(success){
            adapter.setClassInLoadState(adapter.COMPLETE)
        }else{
            adapter.setClassInLoadState(adapter.END)
        }
    }
    /**
     * 设置滑动监听，以检查上滑状态以更新数据
     */
    fun setRecyclerOnScrollListener(){
        var isUp:Boolean = false
        binding.rvClassIn.addOnScrollListener(object :RecyclerView.OnScrollListener(){
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
                        loadMore()
                    }
                }
            }

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                isUp = dy > 0
            }
        })
    }
}