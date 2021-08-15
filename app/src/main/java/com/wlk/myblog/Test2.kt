package com.wlk.myblog

import android.os.Bundle
import android.util.Log
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.test.*

class Test2 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test2)

        val intent = intent
        val data = intent.data
        Log.e("TAG", data.toString())
        web_view.settings.javaScriptEnabled = true
        web_view.webViewClient = WebViewClient()
        web_view.loadUrl(data.toString())

    }
}
