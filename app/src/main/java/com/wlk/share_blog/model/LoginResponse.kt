package com.wlk.myblog.model

data class LoginResponse(val message: String, val code: Int,val data: Data)

data class Data(val token: String,val username: String,val phone: String,val createDate: String)