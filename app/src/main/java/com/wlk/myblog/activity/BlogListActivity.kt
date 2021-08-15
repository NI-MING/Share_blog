package com.wlk.myblog.activity

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.OnScrollListener
import com.bumptech.glide.Glide
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.wlk.myblog.R
import com.wlk.myblog.ViewModel.BlogListViewModel
import com.wlk.myblog.adapter.BlogListAdapter
import com.wlk.myblog.model.DataX
import kotlinx.android.synthetic.main.activity_url_list.*
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class BlogListActivity : AppCompatActivity(),
    BlogListAdapter.MyOnClickListener,
    BlogListAdapter.MyOnLongClickListener{

    private val viewModel by lazy {
        ViewModelProvider(this).get(BlogListViewModel::class.java)
    }

    private lateinit var adapter: BlogListAdapter



    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_url_list)
        window.decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR)

        val cid = intent.getIntExtra("cid",-1)
        val boxName = intent.getStringExtra("box_name")
        box_name.text = boxName

        val layoutManager = LinearLayoutManager(this)
        all_blog.layoutManager = layoutManager
        adapter = BlogListAdapter(this)
        all_blog.adapter = adapter

        lifecycleScope.launch {
            viewModel.getBlogList(cid).collectLatest { pagingData ->
                adapter.submitData(pagingData)
            }
        }

        setting_box_info.setOnClickListener {
            val bottomSheetDialog = BottomSheetDialog(this)
            bottomSheetDialog.setContentView(R.layout.setting_box_info)
            bottomSheetDialog.show()

            bottomSheetDialog.findViewById<ImageView>(R.id.delete)?.setOnClickListener {
                AlertDialog.Builder(this).apply {
                    setMessage("删除收藏夹后不可恢复，是否确认删除")
                    setCancelable(true)
                    setPositiveButton("确认") { dialog, which ->
                        finish()
                    }
                    show()
                    bottomSheetDialog.dismiss()
                }
            }
        }



//        adapter.addLoadStateListener {
//            when(it.refresh){
//                is LoadState.NotLoading -> {
//                    Toast.makeText(this, "NotLoading", Toast.LENGTH_SHORT).show()
//                }
//                is LoadState.Loading -> {
//                    Toast.makeText(this, "Loading", Toast.LENGTH_SHORT).show()
//                }
//                is LoadState.Error -> {
//                    val state = it.refresh as LoadState.Error
//                    Toast.makeText(this, "Load Error: ${state.error.message}", Toast.LENGTH_SHORT).show()
//                }
//            }
//        }




        adapter.setOnClickListener(this)
        adapter.setOnLongClickListener(this)
    }

    override fun onClick(blog: DataX) {
        val intent = Intent(this,ShowBlogContent::class.java)
        intent.putExtra("url",blog.url)
        intent.putExtra("blog_title",blog.title)
        startActivity(intent)
    }

    override fun onLongClick(blog: DataX, position: Int) {
        Log.e("TAG","remove")
        adapter.notifyItemRemoved(position)
    }
}