package com.example.wisdomclassroom;

import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.adapter.VPFragmentAdapter;
import com.example.entity.MenuBean;
import com.example.fragment.ControlFragment;
import com.example.fragment.CourseFragment;
import com.example.fragment.LongFragment;
import com.example.fragment.ScreenFragment;
import com.example.fragment.SetFragment;
import com.example.widget.XViewPager;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class MainActivity extends AppCompatActivity {

    private XViewPager viewPager;
    VPFragmentAdapter vpFragmentAdapter;
    List<Fragment> fragments = new ArrayList<>();
    private ImageView ivCourse;
    private TextView tvCourse;
    private RelativeLayout rlCourse;
    private ImageView ivScreen;
    private TextView tvScreen;
    private RelativeLayout rlScreen;
    private ImageView ivLong;
    private TextView tvLong;
    private RelativeLayout rlLong;
    private ImageView ivControl;
    private TextView tvControl;
    private RelativeLayout rlControl;
    private ImageView ivSet;
    private TextView tvSet;
    private RelativeLayout rlSet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initId();

        initFragment();
        initView();

        init();
    }

    private void initId() {
        viewPager = findViewById(R.id.viewPager);
        ivCourse = findViewById(R.id.iv_course);
        tvCourse = findViewById(R.id.tv_course);
        rlCourse = findViewById(R.id.rl_course);

        ivScreen = findViewById(R.id.iv_screen);
        tvScreen = findViewById(R.id.tv_screen);
        rlScreen = findViewById(R.id.rl_screen);

        ivLong = findViewById(R.id.iv_long);
        tvLong = findViewById(R.id.tv_long);
        rlLong = findViewById(R.id.rl_long);

        ivControl = findViewById(R.id.iv_control);
        tvControl = findViewById(R.id.tv_control);
        rlControl = findViewById(R.id.rl_control);

        ivSet = findViewById(R.id.iv_set);
        tvSet = findViewById(R.id.tv_control);
        rlSet = findViewById(R.id.rl_set);

        cutIcon(0);
        rlCourse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cutIcon(0);
            }
        });
        rlScreen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cutIcon(1);
            }
        });
        rlLong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cutIcon(2);
            }
        });
        rlControl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cutIcon(3);
            }
        });
        rlSet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cutIcon(4);
            }
        });

    }

    private void cutIcon(int position) {
        viewPager.setCurrentItem(position, false);
        ivCourse.setImageResource(position == 0 ? R.drawable.course_select : R.drawable.course_normal);
        tvCourse.setTextColor(position == 0 ? Color.parseColor("#00D5FF") : Color.parseColor("#2787C8"));
        rlCourse.setBackground(position == 0 ? getResources().getDrawable(R.drawable.shape_tab_click) : null);

        ivScreen.setImageResource(position == 1 ? R.drawable.screen_select : R.drawable.screen_normal);
        tvScreen.setTextColor(position == 1 ? Color.parseColor("#00D5FF") : Color.parseColor("#2787C8"));
        rlScreen.setBackground(position == 1 ? getResources().getDrawable(R.drawable.shape_tab_click) : null);

        ivLong.setImageResource(position == 2 ? R.drawable.long_select : R.drawable.long_normal);
        tvLong.setTextColor(position == 2 ? Color.parseColor("#00D5FF") : Color.parseColor("#2787C8"));
        rlLong.setBackground(position == 2 ? getResources().getDrawable(R.drawable.shape_tab_click) : null);

        ivControl.setImageResource(position == 3 ? R.drawable.control_select : R.drawable.control_normal);
        tvControl.setTextColor(position == 3 ? Color.parseColor("#00D5FF") : Color.parseColor("#2787C8"));
        rlControl.setBackground(position == 3 ? getResources().getDrawable(R.drawable.shape_tab_click) : null);

        ivSet.setImageResource(position == 4 ? R.drawable.set_select : R.drawable.set_normal);
        tvSet.setTextColor(position == 4 ? Color.parseColor("#00D5FF") : Color.parseColor("#2787C8"));
        rlSet.setBackground(position == 4 ? getResources().getDrawable(R.drawable.shape_tab_click) : null);
       /* switch (position) {
            case 0:
                ivCourse.setImageResource(R.drawable.course_select);
                tvCourse.setTextColor(Color.parseColor("#00D5FF"));

                ivScreen.setImageResource(R.drawable.screen_normal);
                tvCourse.setTextColor(Color.parseColor("#2787C8 "));
                break;
        }*/
    }

    private void initFragment() {
        fragments.add(new CourseFragment());
        fragments.add(new ScreenFragment());
        fragments.add(new LongFragment());
        fragments.add(new ControlFragment());
        fragments.add(new SetFragment());
    }

    private void initView() {

        vpFragmentAdapter = new VPFragmentAdapter(getSupportFragmentManager(), fragments);
        viewPager.setEnableScroll(false);
        viewPager.setOffscreenPageLimit(fragments.size());
        viewPager.setAdapter(vpFragmentAdapter);

    }

    private void init() {

    }


}