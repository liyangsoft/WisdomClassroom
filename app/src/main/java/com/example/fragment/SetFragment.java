package com.example.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.base.BaseFragment;
import com.example.eventbus.EventCenter;
import com.example.wisdomclassroom.R;

public class SetFragment extends BaseFragment {

    private RelativeLayout rlPlatform;
    private RelativeLayout rlIp;
    private RelativeLayout rlBlue;
    private RelativeLayout rlWifi;
    private TextView tvPlatform;
    private TextView tvIp;
    private TextView tvBlue;
    private TextView tvWifi;



    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        rlPlatform = getView().findViewById(R.id.rl_platform);
        tvPlatform = getView().findViewById(R.id.tv_platform);
        rlIp = getView().findViewById(R.id.rl_ip);
        tvIp = getView().findViewById(R.id.tv_ip);
        rlBlue = getView().findViewById(R.id.rl_blue);
        tvBlue = getView().findViewById(R.id.tv_blue);

        rlWifi = getView().findViewById(R.id.rl_wifi);
        tvWifi = getView().findViewById(R.id.tv_wifi);
        cutBg(0);
        rlPlatform.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cutBg(0);
            }
        });
        rlIp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cutBg(1);
            }
        });
        rlBlue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cutBg(2);
            }
        });
        rlWifi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cutBg(3);
            }
        });

    }

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

    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.fragment_set;
    }

    @Override
    protected void onEventComming(EventCenter eventCenter) {

    }

    @Override
    protected boolean isBindEventBusHere() {
        return false;
    }

    private void cutBg(int position) {
        rlPlatform.setBackground(position == 0 ? getResources().getDrawable(R.drawable.shape_set_bg) : null);
        tvPlatform.setTextColor(position==0?Color.parseColor("#000000"):Color.parseColor("#5090B9"));
        rlIp.setBackground(position == 1 ? getResources().getDrawable(R.drawable.shape_set_bg) : null);
        tvIp.setTextColor(position==1?Color.parseColor("#000000"):Color.parseColor("#5090B9"));
        rlBlue.setBackground(position == 2 ? getResources().getDrawable(R.drawable.shape_set_bg) : null);
        tvBlue.setTextColor(position==2?Color.parseColor("#000000"):Color.parseColor("#5090B9"));
        rlWifi.setBackground(position == 3 ? getResources().getDrawable(R.drawable.shape_set_bg) : null);
        tvWifi.setTextColor(position==3?Color.parseColor("#000000"):Color.parseColor("#5090B9"));

    }
}
