package com.wlk.myblog.adapter

import android.app.Activity
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.wlk.myblog.R
import com.wlk.myblog.activity.BlogListActivity
import com.wlk.myblog.model.DataX
import com.wlk.myblog.model.Owner

class CollectionBoxAdapter2(val list: ArrayList<Owner>, val activity: Activity): RecyclerView.Adapter<CollectionBoxAdapter2.ViewHolder>() {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val name = itemView.findViewById<TextView>(R.id.box_name)
    }

    private lateinit var myClickListener: MyClickListener

    fun setMyClickListener(clickListener: MyClickListener){
        myClickListener = clickListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.collectionbox_item,parent,false)
        val viewHolder = ViewHolder(view)
        return viewHolder
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = list[position]
        holder.name.text = item.name
        holder.itemView.setOnClickListener {
            myClickListener.OnClick(item.id)
        }
    }

    override fun getItemCount() = list.size

    interface MyClickListener{
        fun OnClick(cid: Int)
    }
}