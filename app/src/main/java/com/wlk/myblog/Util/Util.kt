package com.wlk.myblog.Util

import android.util.Log
import com.google.gson.Gson
import okhttp3.MediaType
import okhttp3.RequestBody

fun MapToJson(map: HashMap<String, String>): RequestBody {
    val json = Gson()
    val jstr = json.toJson(map)
    val requestBody =
        RequestBody.create(MediaType.parse("application/json; Accept: application/json"), jstr)
    return requestBody
}

@JvmName("MapToJson1")
fun MapToJson(map: HashMap<String, Any>): RequestBody {
    val json = Gson()
    val jstr = json.toJson(map)
    Log.e("TAG",jstr)
    val requestBody =
        RequestBody.create(MediaType.parse("application/json; Accept: application/json"), jstr)
    return requestBody
}