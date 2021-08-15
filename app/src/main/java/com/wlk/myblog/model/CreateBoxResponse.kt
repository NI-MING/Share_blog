package com.wlk.myblog.model

data class CreateBoxResponse(
    val code: Int,
    val data: BData,
    val msg: String
)

data class BData(
    val cid: Int
)