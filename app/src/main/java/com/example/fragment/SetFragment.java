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

import com.example.wisdomclassroom.R;

public class SetFragment extends Fragment {

    private View view;
    private RelativeLayout rlPlatform;
    private RelativeLayout rlIp;
    private RelativeLayout rlBlue;
    private RelativeLayout rlWifi;
    private TextView tvPlatform;
    private TextView tvIp;
    private TextView tvBlue;
    private TextView tvWifi;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_set, container, false);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        rlPlatform = view.findViewById(R.id.rl_platform);
        tvPlatform = view.findViewById(R.id.tv_platform);
        rlIp = view.findViewById(R.id.rl_ip);
        tvIp = view.findViewById(R.id.tv_ip);
        rlBlue = view.findViewById(R.id.rl_blue);
        tvBlue = view.findViewById(R.id.tv_blue);

        rlWifi = view.findViewById(R.id.rl_wifi);
        tvWifi = view.findViewById(R.id.tv_wifi);
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
