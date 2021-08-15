package com.wlk.myblog.Repository

import androidx.lifecycle.liveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.wlk.myblog.model.BlogListResponse
import com.wlk.myblog.model.ListData
import com.wlk.myblog.network.MyBlogNetWork
import com.wlk.myblog.pagingSource.BlogListPagingSource
import kotlinx.coroutines.Dispatchers
import okhttp3.RequestBody
import java.lang.RuntimeException
import java.util.concurrent.Flow

object CollectionRepository {

    suspend fun getbox(token: String,type: String,limit: Int) = MyBlogNetWork.getBox(token, type,limit)

    suspend fun createbox(token: String,body: RequestBody) = MyBlogNetWork.createBox(token, body)

    suspend fun addBlog(token: String,body: RequestBody) = MyBlogNetWork.addBlog(token, body)

    fun getBlogList(token: String,cid: Int) = liveData(Dispatchers.IO){
        val result = try {
            val blogListResponse = MyBlogNetWork.getBlogList(token, cid,1,50)
            if(blogListResponse.code == 0){
                val blogList = blogListResponse.data
                Result.success(blogList)
            }else{
                Result.failure(RuntimeException(blogListResponse.msg))
            }
        }catch (e:Exception){
            Result.failure(e)
        }
        emit(result)
    }


    fun getBox(token: String,type: String,limit: Int) = liveData(Dispatchers.IO) {
        val result = try {
            val collectionBoxResponse = MyBlogNetWork.getBox(token, type, limit)
            if(collectionBoxResponse.code == 0){
                val boxList = collectionBoxResponse.data.owner
                Result.success(boxList)
            }else{
                Result.failure(RuntimeException(collectionBoxResponse.msg))
            }
        }catch (e: Exception){
            Result.failure(e)
        }
        emit(result)
    }

}