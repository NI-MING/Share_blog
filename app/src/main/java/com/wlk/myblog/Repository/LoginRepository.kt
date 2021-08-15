package com.wlk.myblog.Repository
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.wlk.myblog.network.MyBlogNetWork
import kotlinx.coroutines.Dispatchers
import okhttp3.RequestBody
import java.lang.Exception
import java.lang.RuntimeException

object LoginRepository {

    suspend fun login(contentType: String, body: RequestBody) =
        MyBlogNetWork.login(contentType, body)

}




