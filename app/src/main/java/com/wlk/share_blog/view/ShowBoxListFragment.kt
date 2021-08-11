package com.wlk.myblog.view

import android.os.*
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.wlk.myblog.MyApplication
import com.wlk.myblog.R
import com.wlk.myblog.ViewModel.MineViewModel
import com.wlk.myblog.adapter.CollectionBoxAdapter
import com.wlk.myblog.model.DataX
import com.wlk.myblog.model.Owner
import kotlinx.android.synthetic.main.show_boxlist_fragment.*


class ShowBoxListFragment: Fragment(),CollectionBoxAdapter.MyOnLongClickListener{

    // 静态常量
    companion object {
        const val UPDATA_BOX = 1
    }

    // 实例化ViewModel
    private val mineViewModel by lazy {
        activity?.let { ViewModelProvider(it).get(MineViewModel::class.java) }
    }

    private var flag = 0

    private lateinit var adapter: CollectionBoxAdapter

    private val handler = object : Handler(Looper.getMainLooper()){
        override fun handleMessage(msg: Message) {
            when(msg.what){
                UPDATA_BOX -> {
                    mineViewModel?.list?.add(0, msg.obj as Owner)
                    adapter.notifyItemInserted(0)
                    rv.smoothScrollToPosition(0)
                }
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.show_boxlist_fragment,container,false)
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val layoutManager = LinearLayoutManager(activity)
        rv.layoutManager = layoutManager
        adapter = mineViewModel?.let { activity?.let { it1 -> CollectionBoxAdapter(it.list, it1) } }!!
        rv.adapter = adapter
        refresh.setColorSchemeColors(MyApplication.context.getColor(R.color.my_blue))
        refresh.isRefreshing = true
        showList()
        mineViewModel?.listLivaData?.observe(viewLifecycleOwner){ result ->
            val box = result.getOrNull()
            if(box != null){
                mineViewModel!!.list.clear()
                mineViewModel!!.list.addAll(box)
                adapter.notifyDataSetChanged()
                refresh.isRefreshing = false
            }
        }

        refresh.setOnRefreshListener {
            showList()
        }
    }

    private fun showList(){
        flag++
        mineViewModel?.getBoxList(flag)
    }

    fun addBox(createData: String,everyonePermission: Int,
               groupPermission: Int,id: Int,name: String){
        val msg = Message.obtain()
        msg.what = UPDATA_BOX
        val owner = Owner(createData,everyonePermission,groupPermission,id,name)
        msg.obj = owner
        handler.sendMessage(msg)
    }

    override fun onLongClick(item: Owner, position: Int) {
        adapter.list.removeAt(position)
        adapter.notifyItemRemoved(position)

    }
}