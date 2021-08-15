package com.wlk.myblog.activity

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.annotation.RequiresApi
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.wlk.myblog.R
import com.wlk.myblog.view.ForumFragment
import com.wlk.myblog.view.HomeFragment
import com.wlk.myblog.view.MineFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(),
    BottomNavigationView.OnNavigationItemSelectedListener {

    private val homeFragment = HomeFragment()
    private val forumFragment = ForumFragment()
    private val mineFragment = MineFragment()

    @RequiresApi(Build.VERSION_CODES.R)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initView()
    }

    // fragment切换
    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        val transaction = supportFragmentManager.beginTransaction()
        when(item.itemId) {
            R.id.home -> {
                transaction.hide(forumFragment).hide(mineFragment)
                transaction.show(homeFragment)
                transaction.commit()
            }
            R.id.forum -> {
                transaction.hide(homeFragment).hide(mineFragment)
                transaction.show(forumFragment)
                transaction.commit()
            }
            R.id.mine -> {
                transaction.hide(homeFragment).hide(forumFragment)
                transaction.show(mineFragment)
                transaction.commit()
            }
        }
        return true
    }


    @RequiresApi(Build.VERSION_CODES.R)
    private fun initView() {
        bottom_bar.setOnNavigationItemSelectedListener(this)

        // fragment加载
        val transaction = supportFragmentManager.beginTransaction()
        transaction.add(R.id.fragment,homeFragment).add(R.id.fragment,forumFragment)
            .add(R.id.fragment,mineFragment)
        transaction.hide(forumFragment).hide(mineFragment)
        transaction.commit()

        // 状态栏颜色
        window.decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR)


    }


    override fun onBackPressed() {
        super.onBackPressed()
    }


}