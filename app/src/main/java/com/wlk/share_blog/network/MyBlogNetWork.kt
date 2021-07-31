package com.wlk.myblog.network

import com.wlk.myblog.network.service.CollectionService
import com.wlk.myblog.network.service.LoginService
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Response
import java.lang.RuntimeException
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

object MyBlogNetWork {


    private val loginService = ServiceCreator.create<LoginService>()

    private val collectionService = ServiceCreator.create<CollectionService>()

    suspend fun login(
        contentType: String,
        body: RequestBody
    ) =
        loginService.login(contentType,body).awit()


    suspend fun getBox(token: String,type: String,limit: Int) = collectionService.getOwnerCollectionBox(token, type,limit).awit()

    suspend fun createBox(token: String,body: RequestBody) = collectionService.createCollectionBox(token, body).awit()

    suspend fun getBlogList(token: String,cid: Int,page:Int,size: Int) = collectionService.getAllblog(token, cid,page, size).awit()

    suspend fun addBlog(token: String,body: RequestBody) = collectionService.addBlog(token, body).awit()

    private suspend fun <T> Call<T>.awit(): T {
        return suspendCoroutine { continuation ->
            enqueue(object : retrofit2.Callback<T> {
                override fun onResponse(call: Call<T>, response: Response<T>) {
                    val body = response.body()
                    val headers = response.headers()
                    if(body != null) continuation.resume(body)
                    else continuation.resumeWithException(
                        RuntimeException("response is null")
                    )
                }
                override fun onFailure(call: Call<T>, t: Throwable) {
                    continuation.resumeWithException(t)
                }
            })
        }
    }
}