package com.example.fragment;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.projection.MediaProjection;
import android.media.projection.MediaProjectionManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.DialogFragment;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.blankj.utilcode.util.ToastUtils;
import com.example.base.BaseFragment;
import com.example.eventbus.EventCenter;
import com.example.service.MediaReaderService;
import com.example.wisdomclassroom.MyApplication;
import com.example.wisdomclassroom.R;
import com.example.wisdomclassroom.WatchContect;

import java.util.List;

import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;

import static com.blankj.utilcode.util.ServiceUtils.startService;

public class TestFragment extends BaseFragment implements EasyPermissions.PermissionCallbacks {
    @Override
    protected void onFirstUserVisible() {

    }

    @Override
    protected void onUserVisible() {

    }

    @Override
    protected void onUserInvisible() {

    }

    @Override
    protected View getLoadingTargetView() {
        return null;
    }

    private static final int RC_CAMERA_PERM = 123;

    @AfterPermissionGranted(RC_CAMERA_PERM)
    private void check() {
        String[] perms = {Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.FOREGROUND_SERVICE, Manifest.permission.RECORD_AUDIO};
        if (EasyPermissions.hasPermissions(getActivity(), perms)) {
            requestCapturePermission();
        } else {
            EasyPermissions.requestPermissions(this, "需要访问存储权限",
                    RC_CAMERA_PERM, perms);
        }
    }

    @Override
    protected void initViewsAndEvents() {
        myApplication = (MyApplication) getActivity().getApplication();
        TextView guang = find(R.id.guang);
        guang.setOnClickListener(v -> {
            check();
        });

        TextView fen1 = find(R.id.fen1);
        fen1.setOnClickListener(v -> {
           /* final EditText et = new EditText(getActivity());
            new AlertDialog.Builder(getActivity()).setTitle("请输入IP")
                    .setIcon(android.R.drawable.sym_def_app_icon)
                    .setView(et)
                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                           *//* val intent = WatchContect.buildIntent(Intent(mainactivity,WatchContect::class.java),
                            mainactivity.mInputModel.ipaddr)
                            mainactivity.startActivity(intent)*//*
                            Intent intent = new Intent(getActivity(), WatchContect.class);
                            Bundle bundle = new Bundle();
                            bundle.putString("Address", et.getText().toString());
                            intent.putExtras(bundle);
                            getActivity().startActivity(intent);
                        }
                    }).setNegativeButton("取消", null).show();*/
            Intent intent = new Intent(getActivity(), WatchContect.class);
            Bundle bundle = new Bundle();
            bundle.putString("Address", "192.168.3.212");
            intent.putExtras(bundle);
            getActivity().startActivity(intent);

        });

        TextView fen2 = find(R.id.fen2);
        fen2.setOnClickListener(v -> {
            final EditText et = new EditText(getActivity());
            /*new AlertDialog.Builder(getActivity()).setTitle("请输入IP")
                    .setIcon(android.R.drawable.sym_def_app_icon)
                    .setView(et)
                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                           *//* val intent = WatchContect.buildIntent(Intent(mainactivity,WatchContect::class.java),
                            mainactivity.mInputModel.ipaddr)
                            mainactivity.startActivity(intent)*//*
                            Intent intent = new Intent(getActivity(), WatchContect.class);
                            Bundle bundle = new Bundle();
                            bundle.putString("Address", et.getText().toString());
                            intent.putExtras(bundle);
                            getActivity().startActivity(intent);
                        }
                    }).setNegativeButton("取消", null).show();*/

            Intent intent = new Intent(getActivity(), WatchContect.class);
            Bundle bundle = new Bundle();
            bundle.putString("Address", "192.168.3.212");
            intent.putExtras(bundle);
            getActivity().startActivity(intent);

        });

    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.fragment_test;
    }

    @Override
    protected void onEventComming(EventCenter eventCenter) {

    }

    @Override
    protected boolean isBindEventBusHere() {
        return false;
    }

    @Override
    public void onPermissionsGranted(int requestCode, List<String> perms) {

    }

    @Override
    public void onPermissionsDenied(int requestCode, List<String> perms) {
        if (EasyPermissions.somePermissionPermanentlyDenied(this, perms)) {
            dialog();
        }
    }

    private void dialog() {

        ToastUtils.showShort("前往设置打开权限");
    }


    public enum ModelStatus {
        UNSTART,
        STARTING,
        STARTED
    }

    private MediaProjectionManager mediaProjectionManager;
    private int REQUEST_MEDIA_PROJECTION = 18;
    private MyApplication myApplication;


    private void requestCapturePermission() {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
            //5.0 之后才允许使用屏幕截图
            mediaProjectionManager = (MediaProjectionManager) mContext.getSystemService(
                    Context.MEDIA_PROJECTION_SERVICE);
            startActivityForResult(mediaProjectionManager.createScreenCaptureIntent(),
                    REQUEST_MEDIA_PROJECTION);
        } else {
            Toast.makeText(mContext, "系统版本低于5.0!", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_MEDIA_PROJECTION) {
            if (resultCode == Activity.RESULT_OK && data != null) {
                MediaProjection mediaProjection = mediaProjectionManager.getMediaProjection(resultCode, data);
                if (mediaProjection == null) {

                    return;
                }
                myApplication.mediaProjection = mediaProjection;
                startServer();
//                viewmodel.step = ModelStatus.STARTED;
                MediaReaderService.ServerStatus serverStatus = myApplication.getServerStatus();
                serverStatus = MediaReaderService.ServerStatus.STARTED;
            }
        }
    }

    private void startServer() {
        Intent intent = new Intent(getActivity(), MediaReaderService.class);
        intent.putExtra("CMD", 1);
        startService(intent);
    }
}
