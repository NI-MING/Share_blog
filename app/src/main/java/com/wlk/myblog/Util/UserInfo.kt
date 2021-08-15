package com.wlk.myblog.Util

import android.content.Context
import com.wlk.myblog.MyApplication

fun getToken(): String {
    val prefs = MyApplication.context.getSharedPreferences("token",Context.MODE_PRIVATE)
    val token = prefs.getString("token",null)
    return token!!
}

val TOKEN = getToken()