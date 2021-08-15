package com.wlk.myblog.login
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.view.View
import android.view.Window
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import com.wlk.myblog.MyApplication
import com.wlk.myblog.R
import com.wlk.myblog.Repository.CollectionRepository
import com.wlk.myblog.activity.MainActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class LoginActivity: AppCompatActivity() {


//    val handler = object : Handler(){
//        override fun handleMessage(msg: Message) {
//            when(msg.what){
//                1 -> {
//                    if(msg.obj == 0){
//
//                    }
//                }
//            }
//        }
//    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login_activity)

        if (Build.VERSION.SDK_INT >= 21) {
            val decorView = window.decorView
            val option = (View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN)
            decorView.systemUiVisibility = option
            window.statusBarColor = Color.TRANSPARENT
        }

        val prefs = MyApplication.context.getSharedPreferences("token",Context.MODE_PRIVATE)
        val token = prefs.getString("token",null)
        if(token != null){
            var code: Int
            CoroutineScope(Dispatchers.Default).launch {
                val response = CollectionRepository.getbox(token,"owner",20)
                code = response.code
//                val msg = Message.obtain()
//                msg.what = 1
//                msg.obj = code
                if(code == 0){
                    val intent = Intent(this@LoginActivity,MainActivity::class.java)
                    startActivity(intent)
                    finish()
                }
            }
        }
    }

}