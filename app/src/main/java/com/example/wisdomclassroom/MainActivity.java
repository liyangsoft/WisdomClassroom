package com.example.wisdomclassroom;

import android.app.Presentation;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.hardware.display.DisplayManager;
import android.media.MediaRouter;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;

import com.example.adapter.VPFragmentAdapter;
import com.example.base.BaseActivity;
import com.example.base.BaseLazyFragment;
import com.example.eventbus.EventCenter;
import com.example.fragment.TeachingFragment;
import com.example.fragment.MainFragment;
import com.example.presentation.MyPresentation;
import com.example.widget.XViewPager;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends BaseActivity {


    private XViewPager viewPager;

    private VPFragmentAdapter vpFragmentAdapter;
    private List<BaseLazyFragment> fragments = new ArrayList<>();
    private DisplayManager mDisplayManager;
    private Presentation mPresentation;

    @Override
    protected void getBundleExtras(Bundle extras) {

    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_main;
    }

    @Override
    protected void onEventComming(EventCenter eventCenter) {

    }

    @Override
    protected View getLoadingTargetView() {
        return null;
    }

    @Override
    protected void initViewsAndEvents() {
        viewPager = find(R.id.viewPager);
        initFragment();
        initView();

        mDisplayManager = (DisplayManager) this.getSystemService(Context.DISPLAY_SERVICE);
    }

    @Override
    protected void onNetworkDisConnected() {

    }

    @Override
    protected boolean isBindEventBusHere() {
        return false;
    }

    @Override
    protected boolean toggleOverridePendingTransition() {
        return false;
    }

    @Override
    protected TransitionMode getOverridePendingTransitionMode() {
        return null;
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

}