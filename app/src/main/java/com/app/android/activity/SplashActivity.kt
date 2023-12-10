package com.app.android.activity

import android.os.Bundle
import android.os.Handler
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AppCompatActivity
import com.app.android.R
import com.app.android.databinding.ActivitySplashBinding

class SplashActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySplashBinding
    private var topAnim: Animation? = null
    private var bottomAnim:Animation? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initView()

    }

    private fun initView() {

        topAnim = AnimationUtils.loadAnimation(this, R.anim.right_anim)
        bottomAnim = AnimationUtils.loadAnimation(this, R.anim.left_anim)

        binding.splashText.animation = topAnim
        binding.splashText2.animation = bottomAnim

        Handler().postDelayed({
            DashboardActivity.start(this)
        }, 3000)
    }
}