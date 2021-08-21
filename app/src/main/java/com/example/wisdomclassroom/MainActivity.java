package com.example.wisdomclassroom;

import android.Manifest;
import android.app.Activity;
import android.app.Presentation;
import android.content.Context;
import android.content.Intent;
import android.hardware.display.DisplayManager;
import android.media.projection.MediaProjection;
import android.media.projection.MediaProjectionManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import com.blankj.utilcode.util.ToastUtils;
import com.example.adapter.VPFragmentAdapter;
import com.example.base.BaseActivity;
import com.example.base.BaseLazyFragment;
import com.example.base.Constants;

import com.example.eventbus.EventCenter;
import com.example.fragment.TeachingFragment;
import com.example.fragment.MainFragment;
import com.example.presentation.MyPresentation;

import com.example.service.GroupFourService;
import com.example.service.GroupOneService;
import com.example.service.GroupThreeService;
import com.example.service.GroupTwoService;
import com.example.service.MediaReaderService;
import com.example.service.MediaReaderOneService;
import com.example.service.MediaReaderTwoService;
import com.example.service.MediaReaderThreeService;
import com.example.service.MediaReaderFourService;
import com.example.widget.XViewPager;

import java.util.ArrayList;
import java.util.List;

import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;

import static com.blankj.utilcode.util.ServiceUtils.startService;


public class MainActivity extends BaseActivity implements EasyPermissions.PermissionCallbacks {


    private XViewPager viewPager;

    private VPFragmentAdapter vpFragmentAdapter;
    private List<BaseLazyFragment> fragments = new ArrayList<>();
    private DisplayManager mDisplayManager;
    private Presentation mPresentation;
    private MediaProjectionManager mediaProjectionManager;
    private int REQUEST_MEDIA_PROJECTION = 18;
    private MyApplication myApplication;
    private static final int RC_SCREEN_PERM = 124;
    private Intent intent1;
    private Intent intent2;
    private Intent intent3;
    private Intent intent4;


    @Override
    protected void getBundleExtras(Bundle extras) {

    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_main;
    }

    @Override
    protected void onEventComming(EventCenter eventCenter) {
        if (eventCenter.getEventCode() == Constants.EVENT_VIEWPAGER) {
            boolean flag = (boolean) eventCenter.getData();
            viewPager.setEnableScroll(flag);
        }
    }

    @Override
    protected View getLoadingTargetView() {
        return null;
    }

    @Override
    protected void initViewsAndEvents() {
        myApplication = (MyApplication) getApplication();
        viewPager = find(R.id.viewPager);
        initFragment();
        initView();

        mDisplayManager = (DisplayManager) this.getSystemService(Context.DISPLAY_SERVICE);
        initSocket();
        checPermiss();
    }

    private void initSocket() {

        startService(new Intent(getApplicationContext(), GroupOneService.class));
        startService(new Intent(getApplicationContext(), GroupTwoService.class));
        startService(new Intent(getApplicationContext(), GroupFourService.class));
        startService(new Intent(getApplicationContext(), GroupThreeService.class));

    }

    //获取屏幕权限
    @AfterPermissionGranted(RC_SCREEN_PERM)
    private void checPermiss() {
        String[] perms = {Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.RECORD_AUDIO};
        if (EasyPermissions.hasPermissions(this, perms)) {
            requestCapturePermission();
        } else {
            EasyPermissions.requestPermissions(this, "需要访问存储权限",
                    RC_SCREEN_PERM, perms);
        }
    }


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
                MediaReaderService.ServerStatus serverStatus = myApplication.getServerStatus();
                serverStatus = MediaReaderService.ServerStatus.STARTED;
            }
        }
    }


    @Override
    protected void onNetworkDisConnected() {

    }

    @Override
    protected boolean isBindEventBusHere() {
        return true;
    }

    @Override
    protected boolean toggleOverridePendingTransition() {
        return false;
    }

    @Override
    protected TransitionMode getOverridePendingTransitionMode() {
        return null;
    }


    /**
     * 开启
     */
    private void startServer() {
       /* Intent intent = new Intent(this, MediaReaderService.class);
        intent.putExtra("CMD", 1);
        startService(intent);
*/
        intent1 = new Intent(this, MediaReaderOneService.class);
        intent1.putExtra("CMD", 1);
        startService(intent1);

        intent2 = new Intent(this, MediaReaderTwoService.class);
        intent2.putExtra("CMD", 1);
        startService(intent2);

        intent3 = new Intent(this, MediaReaderThreeService.class);
        intent3.putExtra("CMD", 1);
        startService(intent3);

        intent4 = new Intent(this, MediaReaderFourService.class);
        intent4.putExtra("CMD", 1);
        startService(intent4);
    }


    private void initFragment() {

        fragments.add(new MainFragment());
        fragments.add(new TeachingFragment());
    }

    private void initView() {

        vpFragmentAdapter = new VPFragmentAdapter(getSupportFragmentManager(), fragments);
        viewPager.setOffscreenPageLimit(fragments.size());
        viewPager.setAdapter(vpFragmentAdapter);
        viewPager.setCurrentItem(1);
    }

    @Override
    protected void onResume() {
        super.onResume();

        // Register to receive events from the display manager.
        mDisplayManager.registerDisplayListener(mDisplayListener, null);
        ShowPresentationByDisplaymanager();
    }

    @Override
    protected void onPause() {
        super.onPause();

        mDisplayManager.unregisterDisplayListener(mDisplayListener);
    }

    @Override
    protected void onStop() {
        super.onStop();
        // Dismiss the presentation when the activity is not visible.
        if (mPresentation != null) {
            Log.i("MainActivity", "Dismissing presentation because the activity is no longer visible.");
            mPresentation.dismiss();
            mPresentation = null;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mPresentation != null) {
            Log.i("MainActivity", "Dismissing presentation because the activity is no longer visible.");
            mPresentation.dismiss();
            mPresentation = null;
        }
        if (null!=intent1){
            stopService(intent1);
        }
        if (null!=intent2){
            stopService(intent2);
        }
        if (null!=intent3){
            stopService(intent3);
        }
        if (null!=intent4){
            stopService(intent4);
        }
    }

    //DisplayManager检测HDMI线的拔出和插入用的。
    private final DisplayManager.DisplayListener mDisplayListener =
            new DisplayManager.DisplayListener() {
                @Override
                public void onDisplayAdded(int displayId) {
                    Log.d("MainActivity", "Display #" + displayId + " added.");
                    ShowPresentationByDisplaymanager();
                }

                @Override
                public void onDisplayChanged(int displayId) {
                    Log.d("MainActivity", "Display #" + displayId + " changed.");
                    ShowPresentationByDisplaymanager();
                }

                @Override
                public void onDisplayRemoved(int displayId) {
                    Log.d("MainActivity", "Display #" + displayId + " removed.");
                    ShowPresentationByDisplaymanager();
                }
            };


    private void ShowPresentationByDisplaymanager() {
        Display[] presentationDisplays = mDisplayManager.getDisplays(DisplayManager.DISPLAY_CATEGORY_PRESENTATION);
        if (presentationDisplays.length > 0) {
            // If there is more than one suitable presentation display, then we could consider
            // giving the user a choice.  For this example, we simply choose the first display
            // which is the one the system recommends as the preferred presentation display.
            Display display = presentationDisplays[0];
            showPresentation(display);
        }
    }

    private void showPresentation(Display presentationDisplay) {
        // Dismiss the current presentation if the display has changed.
        if (mPresentation != null && mPresentation.getDisplay() != presentationDisplay) {
            Log.i("MainActivity", "Dismissing presentation because the current route no longer "
                    + "has a presentation display.");
            mPresentation.dismiss();
            mPresentation = null;
        }
        // Show a new presentation if needed.
        if (mPresentation == null && presentationDisplay != null) {
            Log.i("MainActivity", "Showing presentation on display: " + presentationDisplay);
            mPresentation = new MyPresentation(this, presentationDisplay);
            //  mPresentation.setOnDismissListener(mOnDismissListener);
            try {
                mPresentation.show();
            } catch (WindowManager.InvalidDisplayException ex) {
                Log.w("MainActivity", "Couldn't show presentation!  Display was removed in "
                        + "the meantime.", ex);
                mPresentation = null;
            }
        }
    }


    @Override
    public void onPermissionsGranted(int requestCode, List<String> perms) {

    }

    @Override
    public void onPermissionsDenied(int requestCode, List<String> perms) {
        if (EasyPermissions.somePermissionPermanentlyDenied(this, perms)) {
            ToastUtils.showLong("没有相关权限，打开应用程序设置界面修改应用程序权限");
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

}