package com.wlk.myblog.adapter

import android.app.Activity
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.wlk.myblog.R
import com.wlk.myblog.activity.BlogListActivity
import com.wlk.myblog.model.DataX
import com.wlk.myblog.model.Owner

class CollectionBoxAdapter(val list: ArrayList<Owner>, val activity: Activity): RecyclerView.Adapter<CollectionBoxAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val name = itemView.findViewById<TextView>(R.id.box_name)
    }

    private var mOnLongClickListener: BlogListAdapter.MyOnLongClickListener? = null


    fun setOnLongClickListener(onLongClickListener: BlogListAdapter.MyOnLongClickListener) {
        mOnLongClickListener = onLongClickListener
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
            val intent = Intent(activity, BlogListActivity::class.java)
            intent.putExtra("cid",item.id)
            intent.putExtra("box_name",item.name)
            activity.startActivity(intent)
        }

        holder.itemView.setOnLongClickListener {
            notifyItemRemoved(holder.absoluteAdapterPosition)
            true
        }

    }

    override fun getItemCount() = list.size

    interface MyOnLongClickListener{
        fun onLongClick(item: Owner, position: Int)
    }
}