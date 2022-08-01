package com.example.openeyes

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.transition.Fade
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.google.android.material.bottomnavigation.BottomNavigationView


/**
 * description ： 第一个活动，包含首页、发现、我的三个子fragmen的fragment
 * author : lfy
 * email : 1623658271@qq.com
 * date : 2022/7/14 22:03
 */
class MainActivity : BaseActivity() {
    private lateinit var controller: NavController
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_main_activity)
        //1、先拿NavHostFragment
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment_container) as NavHostFragment
        //2、再拿NavController
        controller = navHostFragment.navController
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