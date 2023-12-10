package com.app.android.activity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.app.android.fragment.NewsFragment
import com.app.android.R
import com.app.android.fragment.WeatherFragment
import com.app.android.databinding.ActivityDashboardBinding
import com.google.android.material.bottomnavigation.BottomNavigationView


class DashboardActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDashboardBinding

    companion object {
        fun start(activity: Activity) {
            val intent = Intent(activity, DashboardActivity::class.java)
            activity.startActivity(intent)
            activity.finish()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDashboardBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportFragmentManager.beginTransaction().replace(R.id.mainContainer, NewsFragment())
            .commit()
        binding.bottomNavigation.setOnNavigationItemSelectedListener(navListener)

    }

    private val navListener =
        BottomNavigationView.OnNavigationItemSelectedListener { item: MenuItem ->
            var selectedFragment: Fragment? = null
            when (item.itemId) {
                R.id.navigation_news -> selectedFragment = NewsFragment()
                R.id.navigation_weather -> selectedFragment = WeatherFragment()

            }
            if (selectedFragment != null) {
                supportFragmentManager.beginTransaction()
                    .replace(R.id.mainContainer, selectedFragment).commit()
            }
            true
        }

}