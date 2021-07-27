package com.example.fragment

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.databinding.BaseObservable
import android.databinding.Bindable
import android.databinding.DataBindingUtil
import android.os.Build
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v4.app.DialogFragment
import android.view.*
import com.example.base.BaseFragment
import com.example.eventbus.EventCenter
import com.example.wisdomclassroom.MainActivity
import com.example.wisdomclassroom.R
import com.example.wisdomclassroom.WatchContect
import com.example.wisdomclassroom.databinding.DialoInputadreassBinding
import kotlinx.android.synthetic.main.fragment_test.*

class TesFragment : BaseFragment() {

    val mInputModel=InputModel()
    lateinit var inputDialog: InputDialog


    override fun onFirstUserVisible() {

    }

    override fun onUserVisible() {

    }

    override fun onUserInvisible() {

    }

    override fun getLoadingTargetView(): View? {
        return null
    }

    override fun initViewsAndEvents() {
        inputDialog=InputDialog(this)
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.M) {
            CheckAndSetPermissions(activity)
        }
//        guang.setOnClickListener(View.OnClickListener { v: View? -> requestCapturePermission() })
    }

    override fun getContentViewLayoutID(): Int {
        return R.layout.fragment_test
    }

    override fun onEventComming(eventCenter: EventCenter<*>?) {

    }

    override fun isBindEventBusHere(): Boolean {
        return false
    }

    @SuppressLint("ValidFragment")
    class  InputDialog(val mainactivity: TesFragment): DialogFragment() {
        lateinit var binding: DialoInputadreassBinding


        override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
            binding= DataBindingUtil.inflate(inflater,R.layout.dialo_inputadreass,null,false)
            binding.model=mainactivity.mInputModel
            binding.callback=this
            return binding.root
        }

        override fun onStart() {
            super.onStart()
            val window = dialog.window!!
            val params: WindowManager.LayoutParams = window.getAttributes()
            params.gravity = Gravity.CENTER
            // 使用ViewGroup.LayoutParams，以便Dialog 宽度充满整个屏幕
            // 使用ViewGroup.LayoutParams，以便Dialog 宽度充满整个屏幕
            params.width = ViewGroup.LayoutParams.MATCH_PARENT
            params.height = ViewGroup.LayoutParams.WRAP_CONTENT
            window.attributes = params
        }

        fun onClick(){
//            val intent = WatchContect.buildIntent(Intent(mainactivity,WatchContect::class.java),
//                    mainactivity.mInputModel.ipaddr)
//            mainactivity.startActivity(intent)
//            dismiss()
        }

        fun onCancle(){
            dismiss()
        }

    }

    class InputModel: BaseObservable(){
        @Bindable
        var ipaddr="172.31.98.211"
            set(value) {
                field=value
                notifyChange()
            }
    }

    companion object {
        fun CheckAndSetPermissions(activity: Activity?) {
            val REQUEST = 1
            val PERMISSIONS = arrayOf(
                    "android.permission.READ_EXTERNAL_STORAGE",
                    "android.permission.WRITE_EXTERNAL_STORAGE",
                    "android.permission.FOREGROUND_SERVICE",
                    Manifest.permission.RECORD_AUDIO
            )
            for (ps in PERMISSIONS) { //检测是否有写的权限
                val permission = ActivityCompat.checkSelfPermission(activity!!, ps)
                if (permission != PackageManager.PERMISSION_GRANTED) { // 没有写的权限，去申请写的权限，会弹出对话框
                    ActivityCompat.requestPermissions(activity, PERMISSIONS, REQUEST)
                }
            }
        }
    }
}