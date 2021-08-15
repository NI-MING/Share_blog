package com.wlk.myblog.model

data class CollectionBoxResopnse(
    val code: Int,
    val data: CollectionData,
    val msg: String
)

data class CollectionData(
    val owner: List<Owner>,
    val ownerSum: Int
)

data class Owner(
    val createDate: String,
    val everyonePermission: Int,
    val groupPermission: Int,
    val id: Int,
    val name: String
)