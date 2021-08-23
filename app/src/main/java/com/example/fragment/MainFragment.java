package com.example.fragment;

import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.adapter.VPFragmentAdapter;
import com.example.base.BaseFragment;
import com.example.base.BaseLazyFragment;
import com.example.eventbus.EventCenter;
import com.example.widget.XViewPager;
import com.example.wisdomclassroom.R;

import java.util.ArrayList;
import java.util.List;

public class MainFragment extends BaseFragment {

    private XViewPager viewPager;
    VPFragmentAdapter vpFragmentAdapter;
    List<BaseLazyFragment> fragments = new ArrayList<>();
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
    private Button button;
    private TextView classroomName;
    private TextView className;
    private TextView teacherName;
    private TextView classTime;

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
        initId();
        initFragment();
        initView();
        init();
    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.fragment_main;
    }

    @Override
    protected void onEventComming(EventCenter eventCenter) {

    }

    @Override
    protected boolean isBindEventBusHere() {
        return false;
    }

    private void initId() {

        button = getView().findViewById(R.id.button);

        viewPager = getView().findViewById(R.id.viewPager);
        ivCourse = getView().findViewById(R.id.iv_course);
        tvCourse = getView().findViewById(R.id.tv_course);
        rlCourse = getView().findViewById(R.id.rl_course);

        ivScreen = getView().findViewById(R.id.iv_screen);
        tvScreen = getView().findViewById(R.id.tv_screen);
        rlScreen = getView().findViewById(R.id.rl_screen);

        ivLong = getView().findViewById(R.id.iv_long);
        tvLong = getView().findViewById(R.id.tv_long);
        rlLong = getView().findViewById(R.id.rl_long);

        ivControl = getView().findViewById(R.id.iv_control);
        tvControl = getView().findViewById(R.id.tv_control);
        rlControl = getView().findViewById(R.id.rl_control);

        ivSet = getView().findViewById(R.id.iv_set);
        tvSet = getView().findViewById(R.id.tv_control);
        rlSet =getView(). findViewById(R.id.rl_set);

        classroomName = getView().findViewById(R.id.classroomName);
        className = getView().findViewById(R.id.className);
        teacherName = getView().findViewById(R.id.teacherName);
        classTime = getView().findViewById(R.id.classTime);

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

        button.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        button.setBackgroundColor(Color.parseColor("#00D5FF"));
                        break;
                    case MotionEvent.ACTION_UP:
                        button.setBackgroundColor(Color.parseColor("#5090B9"));
                        break;
                    default:
                        break;
                }
                return true;
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

        vpFragmentAdapter = new VPFragmentAdapter(getChildFragmentManager(), fragments);
        viewPager.setEnableScroll(false);
        viewPager.setOffscreenPageLimit(fragments.size());
        viewPager.setAdapter(vpFragmentAdapter);

    }

    private void init() {

    }
}
