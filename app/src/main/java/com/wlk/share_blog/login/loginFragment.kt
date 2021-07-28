package com.wlk.myblog.login

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.Message
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.wlk.myblog.MyApplication
import com.wlk.myblog.R
import com.wlk.myblog.activity.MainActivity
import com.wlk.myblog.login.loginviewmodel.LoginFragmentViewModel
import kotlinx.android.synthetic.main.login_fragment_layout.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class loginFragment: Fragment() {

    private val SHOWTOAST = 1
    private val SHOWDIALOG = 2
    private val viewModel by lazy { ViewModelProvider(this).get(LoginFragmentViewModel::class.java) }

    private val handler =  object :Handler(Looper.getMainLooper()){
        override fun handleMessage(msg: Message) {
            when(msg.what){
                SHOWTOAST -> {
                    if(!msg.obj.toString().equals("该手机号尚未注册")){
                        Toast.makeText(context,msg.obj.toString(),Toast.LENGTH_SHORT).show()
                    }
                }
                SHOWDIALOG ->{
                    AlertDialog.Builder(activity).apply {
                        setMessage("该手机号尚未注册,是否以该手机号注册用户?")
                        setCancelable(true)

                        setPositiveButton("注册并登陆") { dialog, which ->

                        }
                        setNegativeButton("不,谢谢") { dialog,which ->

                        }
                        show()
                    }
                }
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.login_fragment_layout,container,false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        input_phone.addTextChangedListener {
            viewModel.setPhone(it.toString())
            Log.e("TAG",viewModel.phone.value.toString())
        }

        input_password.addTextChangedListener {
            viewModel.setPassword(it.toString())
            Log.e("TAG",viewModel.password.value.toString())
        }

        goto_login.setOnClickListener {
            CoroutineScope(Dispatchers.Default).launch{
                val toast = viewModel.gotoLogin()
                val msg = Message.obtain()
                msg.what = SHOWTOAST
                msg.obj = toast
                handler.sendMessage(msg)
                if(toast == "登陆成功"){
                    val intent = Intent(activity,MainActivity::class.java)
                    startActivity(intent)
                    activity?.finish()
                }
                if(toast == "该手机号尚未注册"){
                    val message = Message.obtain()
                    message.what = SHOWDIALOG
                    handler.sendMessage(message)
                }
            }
        }
    }
}