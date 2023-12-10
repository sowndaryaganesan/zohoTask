package com.app.android.activity

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.webkit.WebViewClient
import com.app.android.databinding.ActivityNewsDetailBinding

class NewsDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityNewsDetailBinding

    companion object {
        fun start(activity: Activity, url : String) {
            val intent = Intent(activity, NewsDetailActivity::class.java)
            intent.putExtra("URL", url)
            activity.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNewsDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initView()
    }


    private fun initView() {
        val url = intent.getStringExtra("URL")

        if (!url.isNullOrBlank()) {
            binding.webView.settings.javaScriptEnabled = true

            // Set a WebViewClient to handle events inside the WebView
            binding.webView.webViewClient = WebViewClient()

            binding.webView.loadUrl(url)
        } else {

            finish()
        }
    }


    override fun onBackPressed() {
        if (binding.webView.canGoBack()) {
            binding.webView.goBack()
        } else {
            super.onBackPressed()
        }
    }
}