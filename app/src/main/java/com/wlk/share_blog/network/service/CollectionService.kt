package com.wlk.myblog.network.service

import com.wlk.myblog.model.BlogListResponse
import com.wlk.myblog.model.CollectionBoxResopnse
import com.wlk.myblog.model.CreateBoxResponse
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.*

interface CollectionService {

    @GET("collection/user")
    fun getOwnerCollectionBox(@Header("token") token: String,
                              @Query("type") user: String,
                              @Query("limit") limit: Int): Call<CollectionBoxResopnse>

    @PUT("collection")
    @Headers("Content-Type: application/json")
    fun createCollectionBox(@Header("token") token: String,
                            @Body body: RequestBody): Call<CreateBoxResponse>


    @GET("collection/item")
    @Headers("Content-Type: application/json")
    fun getAllblog(@Header("token") token: String,
                   @Query("cid") cid: Int,
                    @Query("page") page: Int,
                    @Query("limit") size: Int): Call<BlogListResponse>

    @PUT("collection/item")
    @Headers("Content-Type: application/json")
    fun addBlog(@Header("token") token: String,@Body body: RequestBody): Call<ResponseBody>

}