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
import com.example.base.BaseActivity;
import com.example.base.BaseLazyFragment;
import com.example.entity.MenuBean;
import com.example.eventbus.EventCenter;
import com.example.fragment.CompositeFragment;
import com.example.fragment.ControlFragment;
import com.example.fragment.CourseFragment;
import com.example.fragment.LongFragment;
import com.example.fragment.MainFragment;
import com.example.fragment.ScreenFragment;
import com.example.fragment.SetFragment;
import com.example.widget.XViewPager;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;


public class MainActivity extends BaseActivity {


    XViewPager viewPager;

    VPFragmentAdapter vpFragmentAdapter;
    List<BaseLazyFragment> fragments = new ArrayList<>();


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
        fragments.add(new CompositeFragment());
        fragments.add(new MainFragment());
    }

    private void initView() {

        vpFragmentAdapter = new VPFragmentAdapter(getSupportFragmentManager(), fragments);
        viewPager.setOffscreenPageLimit(fragments.size());
        viewPager.setAdapter(vpFragmentAdapter);

    }

    private void init() {

    }


}