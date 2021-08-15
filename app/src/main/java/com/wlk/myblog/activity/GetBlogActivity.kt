package com.wlk.myblog.activity

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.webkit.CookieManager
import android.webkit.CookieSyncManager
import android.webkit.WebViewClient
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.wlk.myblog.R
import com.wlk.myblog.Repository.CollectionRepository
import com.wlk.myblog.Util.MapToJson
import com.wlk.myblog.Util.TOKEN
import com.wlk.myblog.ViewModel.MineViewModel
import com.wlk.myblog.adapter.CollectionBoxAdapter2
import kotlinx.android.synthetic.main.activity_get_blog.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.regex.Pattern


class GetBlogActivity : AppCompatActivity(), CollectionBoxAdapter2.MyClickListener{


    private val viewModel by lazy {
        ViewModelProvider(this).get(MineViewModel::class.java)
    }

    private var flag = 0

    private lateinit var adapter: CollectionBoxAdapter2
    private lateinit var url: String
    private var title: String = ""

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_get_blog)
        window.decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR)

        blog_content.settings.javaScriptEnabled = true
        blog_title.text = title

        val intent = intent
        val action = intent.action
        val type = intent.type

        if (Intent.ACTION_SEND == action && type != null) {
            if ("text/plain" == type) {
                handleSendText(intent) // Handle text being sent
            }
        }

        collection.setOnClickListener {
            val bottomSheetDialog = BottomSheetDialog(this)
            bottomSheetDialog.setContentView(R.layout.box_list)
            bottomSheetDialog.show()
            adapter = CollectionBoxAdapter2(viewModel.list,this)
            adapter.setMyClickListener(this)
            val layoutManager = LinearLayoutManager(this)
            val recycler = bottomSheetDialog.findViewById<RecyclerView>(R.id.recycler)
            if (recycler != null) {
                recycler.layoutManager = layoutManager
                recycler.adapter = adapter
            }

            showList()
            viewModel.listLivaData.observe(this){ result ->
                val box = result.getOrNull()
                if(box != null){
                    viewModel.list.clear()
                    viewModel.list.addAll(box)
                    adapter.notifyDataSetChanged()
                    Log.e("TAG","adapter refresh")
                }
            }
        }
    }

    private fun handleSendText(intent: Intent) {
        val sharedText = intent.getStringExtra(Intent.EXTRA_TEXT)
        if (sharedText != null) {
            Log.e("TAG", sharedText)
        }
        if (sharedText != null) {
            for (i in 0..sharedText.length){
                url = sharedText.substring(i,sharedText.length)
                if(url.startsWith("https://")){
                    break
                }
            }
            if(url.startsWith("https://blog.csdn.net")) {
                val firstindex = sharedText.indexOfFirst { char ->
                    char == '《'
                }

                val lastindex = sharedText.indexOfLast { char ->
                    char == '》'
                }
                title = sharedText.substring(firstindex + 1,lastindex)
                blog_title.text = title
            }


            if(url.startsWith("https://juejin.cn")) {
                val pattern = "/(?<= 的文章 ).+(?=h)/g"
                val matcher = Pattern.compile(pattern).matcher(sharedText)
                while (matcher.find()){
                    title = matcher.group()
                }
                blog_title.text = title
            }
            Log.e("TAG", url)
            blog_content.loadUrl(url)
        }
    }


    private fun showList(){
        Log.e("TAG","flag++")
        flag++
        viewModel.getBoxList(flag)
    }

    override fun OnClick(cid: Int) {
        val urllast = url.indexOfLast { char ->
            char == '?'
        }
        val mUrl = url.substring(0,urllast)
        // 按理来说此处放在ViewModel中
        val fileds = HashMap<String,String>()
        fileds["name"] = title
        fileds["url"] = mUrl
        fileds["author"] = "熊喵先生"
        fileds["title"] = title
        val array = arrayOf(fileds)
        val hashMap = HashMap<String,Any>()
        hashMap["fileds"] = array
        hashMap["cid"] = cid
        Log.e("TAG",cid.toString())
        CoroutineScope(Dispatchers.Default).launch {
            val responseBody = CollectionRepository.addBlog(TOKEN, MapToJson(hashMap))
            Log.e("TAG",responseBody.string())
        }
        Log.e("TAG","???")
    }



}