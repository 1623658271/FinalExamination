package com.example.openeyes

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.ImageButton
import android.widget.Toolbar
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupActionBarWithNavController
import com.example.openeyes.adapter.HomePageRVAdapter
import com.example.openeyes.api.ApiService
import com.example.openeyes.api.RetrofitClient
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.*
import kotlinx.android.synthetic.main.layout_main_activity.view.*

/**
 * description ： 第一个活动，包含首页、发现、我的三个子fragmen的fragment
 * author : lfy
 * email : 1623658271@qq.com
 * date : 2022/7/14 22:03
 */
class MainActivity : AppCompatActivity() {
    private lateinit var controller: NavController
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_main_activity)
        controller = findNavController(R.id.nav_host_fragment_container)
        val navigationView:BottomNavigationView = findViewById(R.id.nav_view)
        NavigationUI.setupWithNavController(navigationView, controller)
        val toolbar:androidx.appcompat.widget.Toolbar = findViewById(R.id.toolbar)
        toolbar.setOnMenuItemClickListener {
            when(it.itemId){
                R.id.search -> {
                    startActivity(Intent(this,SearchActivity::class.java))
                }
            }
            true
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        return controller.navigateUp() || super.onSupportNavigateUp()
    }

}