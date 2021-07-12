package com.example.base;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Paint;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.widget.Toolbar;
import android.text.TextPaint;
import android.text.TextUtils;
import android.util.Base64;
import android.util.TypedValue;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.wisdomclassroom.R;
import com.trello.rxlifecycle2.LifecycleTransformer;


import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

public abstract class BaseActivity extends BaseAppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }


    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);

    }


    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    protected void onPause() {
        super.onPause();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();


    }

    /*获取View*/
    public <T extends View> T find(int viewId) {
        return (T) findViewById(viewId);
    }

    //默认返回true
    protected boolean isApplyKitKatTranslucency() {
        return true;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_MENU) {
//            super.openOptionsMenu();  // 调用这个，就可以弹出菜单
            return true;
        }
        return super.onKeyDown(keyCode, event); // 返回super，让其他键默认
    }

    /**
     * 隐藏输入法
     */
    @Override
    public void hideInputMethod() {
        // 隐藏输入法
        InputMethodManager imm = (InputMethodManager) getApplicationContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm.isActive()) { //是否打开输入法
            //隐藏输入法
            try {
                if (this.getCurrentFocus() != null)
                    imm.hideSoftInputFromWindow(this.getCurrentFocus().getWindowToken(), 0);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 打开输入法
     */
    public void showInputMethod() {
        InputMethodManager imm = (InputMethodManager) mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
    }

    /**
     * 设置字体粗细
     *
     * @param textView
     */
    public void setTextPaint(TextView textView) {
        TextPaint paint = textView.getPaint();
        paint.setStyle(Paint.Style.FILL_AND_STROKE);
        paint.setStrokeWidth(0.5f);
    }

    /* @Override
     public <T> LifecycleTransformer<T> bindToLife() {
         return this.<T>bindToLifecycle();
     }*/
    protected boolean needCommonToolbar() {
        return false;
    }

}
