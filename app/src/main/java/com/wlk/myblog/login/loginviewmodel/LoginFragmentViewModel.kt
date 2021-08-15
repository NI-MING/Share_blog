package com.wlk.myblog.login.loginviewmodel

import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.wlk.myblog.MyApplication
import com.wlk.myblog.Repository.LoginRepository
import com.wlk.myblog.Util.MapToJson

//"eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJsZXZlbCI6MSwidXNlcklEIjo0LCJpYXQiOjE2MjgwNDM4NjEsImV4cCI6MTYyODA3Mzg2MX0.uBDabTN2eSMcjt4aCP6zxj0pi-539wJhH7E7ronGep8"

class LoginFragmentViewModel: ViewModel() {

    var phone = MutableLiveData<String>()
    var password = MutableLiveData<String>()

    fun setPhone(_phone: String){
        phone.value = _phone
    }

    fun setPassword(_password: String) {
        password.value = _password
    }

    suspend fun gotoLogin(): String {
        if (phone.value == null || phone.value!!.length < 11) {
            return "请输入手机号"
        } else {
            if (password.value == null) {
                return "请输入密码"
            } else {
                val map = HashMap<String, String>()
                map.put("phone", phone.value!!)
                map.put("password", password.value!!)
                val loginResponse = LoginRepository.login("application/json", MapToJson(map))
                val code = loginResponse.code
                if (code == 0) {
                    val editor =
                        MyApplication.context.getSharedPreferences("token",Context.MODE_PRIVATE).edit()
                    editor.putString("token",loginResponse.data.token)
                    editor.apply()
                    return "登陆成功"
                } else if (code == 16) {
                    return "该手机号尚未注册"
                } else {
                    Log.e("TAG",code.toString())
                    return "密码错误"
                }
            }
        }
    }
}