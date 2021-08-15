package com.wlk.myblog.model

data class BlogListResponse(
    val code: Int,
    val data: ListData,
    val msg: String
)

data class ListData(
    val count: String,
    val data: List<DataX>
)

data class DataX(
    val author: String,
    val collectionsId: Int,
    val id: Int,
    val name: String,
    val title: String,
    val url: String,
    val webId: Int
)
