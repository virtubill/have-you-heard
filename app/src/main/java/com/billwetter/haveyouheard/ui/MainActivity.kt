package com.billwetter.haveyouheard.ui

import android.os.Bundle
import com.billwetter.haveyouheard.R
import com.billwetter.haveyouheard.databinding.MainActivityBinding
import com.billwetter.haveyouheard.ui.bookmarks.BookmarksFragment
import com.billwetter.haveyouheard.ui.common.BaseActivity
import com.billwetter.haveyouheard.ui.trending.TrendingFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.main_activity.*

class MainActivity : BaseActivity<MainViewModel, MainActivityBinding>(MainViewModel::class.java, R.layout.main_activity) {
    
    private val onNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_home -> {
                supportFragmentManager.beginTransaction()
                    .replace(R.id.content, TrendingFragment())
                    .commitNow()
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_bookmarks -> {
                supportFragmentManager.beginTransaction()
                    .replace(R.id.content, BookmarksFragment())
                    .commitNow()
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_settings -> {
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.content, TrendingFragment())
                .commitNow()
        }
    }

    override fun prepareView() {
        nav_view.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener)
    }
}
