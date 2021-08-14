package com.wlk.myblog.ViewModel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.wlk.myblog.Repository.CollectionRepository
import com.wlk.myblog.Util.TOKEN
import com.wlk.myblog.model.Owner
import com.wlk.myblog.network.MyBlogNetWork

class MineViewModel: ViewModel() {

    val userName = MutableLiveData<String>()

    var list = ArrayList<Owner>()
    private val count = MutableLiveData<Int>()

    val listLivaData = Transformations.switchMap(count) {
        CollectionRepository.getBox(TOKEN,"owner",50)
    }

    fun change(name: String){
        userName.value = name
    }

//    suspend fun getBox(token: String,type: String,limit: Int): ArrayList<Owner> {
//        val collectionBoxResponse = CollectionRepository.getbox(token,type,limit)
//        if(collectionBoxResponse.data.owner.isNotEmpty()){
//            list = collectionBoxResponse.data.owner as ArrayList<Owner>
//        }
//        return list
//    }

    fun getBoxList(flag: Int) {
        count.value = flag
        Log.e("TA",count.value.toString())
    }

}