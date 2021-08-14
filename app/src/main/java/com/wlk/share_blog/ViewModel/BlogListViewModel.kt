package com.wlk.myblog.ViewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.wlk.myblog.Repository.CollectionRepository
import com.wlk.myblog.Util.TOKEN
import com.wlk.myblog.model.DataX
import com.wlk.myblog.model.ListData
import com.wlk.myblog.pagingSource.BlogListPagingSource

class BlogListViewModel: ViewModel() {



    fun getBlogList(cid: Int) = Pager(PagingConfig(20,
            enablePlaceholders = true,prefetchDistance = 1) ){
        BlogListPagingSource(cid)
    }.flow.cachedIn(viewModelScope)

}