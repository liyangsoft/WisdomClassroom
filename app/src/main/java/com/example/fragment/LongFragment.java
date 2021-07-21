package com.example.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.base.BaseFragment;
import com.example.eventbus.EventCenter;
import com.example.wisdomclassroom.R;

public class LongFragment extends BaseFragment {


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

    @Override
    protected void initViewsAndEvents() {
        TextView textView = getView().findViewById(R.id.text);
        textView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        textView.setBackgroundColor(Color.parseColor("#00D5FF"));
                        break;
                    case MotionEvent.ACTION_UP:
                        textView.setBackgroundColor(Color.parseColor("#5090B9"));
                        break;
                    default:
                        break;
                }
                return true;
            }
        });
    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.fragment_long;
    }

    @Override
    protected void onEventComming(EventCenter eventCenter) {

    }

    @Override
    protected boolean isBindEventBusHere() {
        return false;
    }
}
