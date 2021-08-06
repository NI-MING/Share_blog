package com.wlk.myblog.pagingSource

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.wlk.myblog.Util.TOKEN
import com.wlk.myblog.model.DataX
import com.wlk.myblog.model.ListData
import com.wlk.myblog.network.MyBlogNetWork
import com.wlk.myblog.network.service.CollectionService

class BlogListPagingSource(private val cid: Int):
    PagingSource<Int, DataX>() {

    override fun getRefreshKey(state: PagingState<Int, DataX>): Int? = null

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, DataX> {
        return try {
            val page = params.key ?: 1
            val pageSize = params.loadSize
            val blogListResponse = MyBlogNetWork.getBlogList(TOKEN,cid,page,pageSize)
            Log.e("TAG",blogListResponse.msg)
            val items = blogListResponse.data.data
            val preKey = if(page > 1) page -1 else null
            val nextKey = if(items.isNotEmpty()) page + 1 else null
            LoadResult.Page(items,preKey,nextKey)
        }catch (e: Exception){
            LoadResult.Error(e)
        }
    }
}