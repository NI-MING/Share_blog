package com.wlk.myblog.model

data class RegisterResponse(
    val code: Int,
    val data: Data,
    val msg: String
)

data class RData(
    val token: String
)