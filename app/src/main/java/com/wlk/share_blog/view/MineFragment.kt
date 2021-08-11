package com.wlk.myblog.view

import android.os.*
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AccelerateDecelerateInterpolator
import android.view.animation.Animation
import android.view.animation.RotateAnimation
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentStatePagerAdapter
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager.widget.ViewPager
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.tabs.TabLayout
import com.wlk.myblog.MyApplication
import com.wlk.myblog.R
import com.wlk.myblog.ViewModel.CollectionFormOwnerFragmentViewModel
import com.wlk.myblog.ViewModel.MineViewModel
import kotlinx.android.synthetic.main.mine_fragment_layout.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*

class MineFragment: Fragment() {

    private val mineViewModel by lazy {
        ViewModelProvider(this).get(MineViewModel::class.java)
    }

    private val collectionViewModel by lazy {
        ViewModelProvider(this).get(CollectionFormOwnerFragmentViewModel::class.java)
    }

    private val SHOW_TOAST = 0


    private val handler = object : Handler(Looper.getMainLooper()){
        override fun handleMessage(msg: Message) {
            when(msg.what) {
                SHOW_TOAST -> {
                    Toast.makeText(context,msg.obj.toString(),Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private lateinit var tab: TabLayout
    private lateinit var viewPager: ViewPager
    private lateinit var bottomSheetDialog: BottomSheetDialog
    private lateinit var showBoxListFragment: ShowBoxListFragment


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.mine_fragment_layout,container,false)
        tab = view.findViewById(R.id.tab)
        viewPager = view.findViewById(R.id.viewpager)
        showBoxListFragment = ShowBoxListFragment()

        return view
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val fragmentList = arrayListOf(showBoxListFragment, ShowBoxListFragment())
        val tabLayoutTextList = arrayListOf("我的收藏夹","关注的收藏夹")

        create_box.setOnClickListener {
            viewPager.currentItem = 0
            bottomSheetDialog = context?.let { it1 -> BottomSheetDialog(it1) }!!
            bottomSheetDialog.setContentView(R.layout.botton_dialog_layout)
            bottomSheetDialog.show()

            val create = bottomSheetDialog.findViewById<TextView>(R.id.create)
            val inputName = bottomSheetDialog.findViewById<EditText>(R.id.input_name)

            if (inputName != null) {
                if (create != null) {
                    inputName.addTextChangedListener {
                        collectionViewModel.box_name = inputName.text.toString()
                        if(inputName.text.isNotEmpty() && !inputName.text.startsWith(" ")){
                            create.setTextColor(MyApplication.context.getColor(R.color.my_blue_create))
                            create.isEnabled = true
                        }else {
                            create.setTextColor(MyApplication.context.getColor(R.color.my_blue_2))
                            create.isEnabled = false
                        }
                    }
                }
                inputName.isFocusable = true
                inputName.isFocusableInTouchMode = true
                inputName.requestFocus()
            }
            val timer = Timer()
            timer.schedule(
                object : TimerTask() {
                    override fun run() {
                        val inputManager = inputName?.context?.getSystemService(
                            AppCompatActivity.INPUT_METHOD_SERVICE
                        ) as InputMethodManager
                        inputManager.showSoftInput(inputName, 0)
                    }
                }, 300)

            create?.setOnClickListener {
                CoroutineScope(Dispatchers.Default).launch {
                    val createBoxResponse = collectionViewModel.createBox()
                    if(createBoxResponse != null){
                        if (inputName != null) {
                            val msg = Message.obtain()
                            msg.what = SHOW_TOAST
                            msg.obj = "创建成功"
                            handler.sendMessage(msg)
                            showBoxListFragment.addBox("",7,7,
                                createBoxResponse.data.cid,
                                inputName.text.toString())
                        }
                    }else {
                        val msg = Message.obtain()
                        msg.what = SHOW_TOAST
                        msg.obj = "创建失败"
                        handler.sendMessage(msg)
                    }
                    bottomSheetDialog.dismiss()

                }

            }

        }

        mineViewModel.userName.observe(viewLifecycleOwner) {
            user_name.text = it
        }

        user_icon?.setOnClickListener {
            startRotate() }


//        unlogin.setOnClickListener {
//            val editor = MyApplication.context.getSharedPreferences("token",Context.MODE_PRIVATE).edit()
//            editor.putString("token",null)
//            editor.apply()
//            val intent = Intent(activity,LoginActivity::class.java)
//            startActivity(intent)
//            activity?.finish()
//        }

        for(i in tabLayoutTextList) {
            tab.addTab(tab.newTab().setText(i))
        }

        val fragmentStatePagerAdapter: FragmentStatePagerAdapter =
            object : FragmentStatePagerAdapter(activity?.supportFragmentManager!!,
                BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {
                override fun getCount(): Int {
                    return fragmentList.size
                }

                override fun getItem(position: Int): Fragment {
                    return fragmentList[position]
                }

                override fun getPageTitle(position: Int): CharSequence? {
                    return tabLayoutTextList[position]
                }
            }

        viewpager.adapter = fragmentStatePagerAdapter
        tab.setupWithViewPager(viewPager,false)
    }

    private fun startRotate() {
        val rotateAnimation = RotateAnimation(0f,
            360f * 150,
            Animation.RELATIVE_TO_SELF,
            0.5f,
            Animation.RELATIVE_TO_SELF,
            0.5f)

        val Interpolator = AccelerateDecelerateInterpolator()
        rotateAnimation.apply { interpolator = Interpolator
        duration = 5000
        fillAfter = true}
        user_icon?.startAnimation(rotateAnimation)
    }

}