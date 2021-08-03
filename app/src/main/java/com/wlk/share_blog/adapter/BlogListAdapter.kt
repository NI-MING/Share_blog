package com.wlk.myblog.adapter

import android.app.Activity
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.CycleInterpolator
import android.view.animation.TranslateAnimation
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.wlk.myblog.MyApplication
import com.wlk.myblog.R
import com.wlk.myblog.model.DataX
import com.wlk.myblog.wight.RotateLayout

class BlogListAdapter(val activity: Activity): PagingDataAdapter<DataX, BlogListAdapter.ViewHolder>(COMPARATOR) {

    inner class ViewHolder(view: View): RecyclerView.ViewHolder(view) {
        val blogTitle: TextView = view.findViewById(R.id.blog_title)
        val rotateLayout: RotateLayout = view.findViewById(R.id.rotateLayout)
    }

    private var mOnClickListener: MyOnClickListener? = null
    private var mOnLongClickListener: MyOnLongClickListener? = null


    fun setOnClickListener(onClickListener: MyOnClickListener) {
        mOnClickListener = onClickListener
    }

    fun setOnLongClickListener(onLongClickListener: MyOnLongClickListener) {
        mOnLongClickListener = onLongClickListener
    }

    companion object {
        private val COMPARATOR = object : DiffUtil.ItemCallback<DataX>() {
            override fun areItemsTheSame(oldItem: DataX, newItem: DataX): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: DataX, newItem: DataX): Boolean {
                return oldItem == newItem
            }
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.blog_item,parent,false)
        return ViewHolder(view)
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val blog = getItem(position)
        if (blog != null) {
            holder.blogTitle.text = blog.title
            holder.itemView.setOnClickListener {
                mOnClickListener?.onClick(blog)
            }
            holder.itemView.setOnLongClickListener {
                true
            }
            if(position % 2 == 1){
                holder.rotateLayout.setText("CSDN")
//                holder.blogTag.background = MyApplication.context.getDrawable(R.drawable.radius_red)
                holder.rotateLayout.setPaintColor(MyApplication.context.getColor(R.color.csdn))
            }else {
                holder.rotateLayout.setText("掘 金")
//                holder.blogTag.background = MyApplication.context.getDrawable(R.drawable.radius_red)
                holder.rotateLayout.setPaintColor(MyApplication.context.getColor(R.color.juejin))
            }
        }
    }

    interface MyOnClickListener{
        fun onClick(blog: DataX)
    }
    interface MyOnLongClickListener{
        fun onLongClick(blog: DataX,position: Int)
    }
}