package com.wlk.myblog.activity

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.webkit.WebViewClient
import androidx.annotation.RequiresApi
import com.wlk.myblog.R
import kotlinx.android.synthetic.main.activity_show_blog_content.*
import kotlinx.android.synthetic.main.test.*

class ShowBlogContent : AppCompatActivity() {
    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_show_blog_content)
        window.decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR)

        val url = intent.getStringExtra("url")
        val blogTitle = intent.getStringExtra("blog_title")
        blog_title.text = blogTitle
        show_content.settings.javaScriptEnabled = true
        show_content.webViewClient = WebViewClient()
        if (url != null) {
            show_content.loadUrl(url)
        }

    }
}