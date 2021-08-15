package com.wlk.myblog.ViewModel

import androidx.lifecycle.ViewModel
import com.wlk.myblog.Repository.CollectionRepository
import com.wlk.myblog.Util.MapToJson
import com.wlk.myblog.Util.TOKEN
import com.wlk.myblog.model.CreateBoxResponse
import com.wlk.myblog.model.Owner

class CollectionFormOwnerFragmentViewModel: ViewModel() {

    var boxList = ArrayList<Owner>()
    var box_name: String = ""
    var box_info: String = ""

    suspend fun getBox(token: String,type: String,limit: Int) {
        val collectionBoxResponse = CollectionRepository.getbox(token,type,limit)

        if(collectionBoxResponse.data.owner.isNotEmpty()){
            boxList = collectionBoxResponse.data.owner as ArrayList<Owner>
        }
    }

    suspend fun createBox(): CreateBoxResponse? {
        val map = HashMap<String,String>()
        map["name"]=box_name
        map["groupPermission"] = "7"
        map["everyonePermission"] = "7"
        val createBoxResponse = CollectionRepository.createbox(TOKEN, MapToJson(map))
        if(createBoxResponse.code == 0){
            return createBoxResponse
        }else {
            return null
        }
    }

}