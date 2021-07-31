package com.wlk.myblog.network.service

import com.wlk.myblog.model.LoginResponse
import com.wlk.myblog.model.RegisterResponse
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.*

interface LoginService {


    @POST("api/login")
    fun login(@Header("Content-Type") contentType: String,
              @Body body: RequestBody
    ): Call<LoginResponse>


    @PUT("api/create")
    @Headers("Content-Type: application/json")
    fun register(@Body body: RequestBody): Call<RegisterResponse>

}