package com.wlk.myblog;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Test extends AppCompatActivity {

    public WebView webView;



    @Override
    protected void onCreate (Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.test);

        webView = findViewById(R.id.web_view);
        webView.getSettings().setJavaScriptEnabled(true);

        webView.setWebViewClient(new WebViewClient()/*{
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view,url);
                view.loadUrl("javascript:" +
                        "var script = document.body.removeChild(document.querySelector('#csdn-toolbar'));"
                + "var script = document.querySelector('#operate').style.display='none';"
                + "var script = document.querySelector(\"#main\").style.margin=\"0\"");
            }
        }*/);

        Intent intent = getIntent();
        String action = intent.getAction();
        String type = intent.getType();

        if (Intent.ACTION_SEND.equals(action) && type != null) {
            if ("text/plain".equals(type)) {
                handleSendText(intent); // Handle text being sent
            }
        }
    }

    void handleSendText(Intent intent) {
        String sharedText = intent.getStringExtra(Intent.EXTRA_TEXT);
        StringBuilder Url = new StringBuilder();
        if (sharedText != null) {
            Log.e("TAG",sharedText);
            webView.loadUrl("https://blog.csdn.net/leohels/article/details/80908761");
        }
    }
}
