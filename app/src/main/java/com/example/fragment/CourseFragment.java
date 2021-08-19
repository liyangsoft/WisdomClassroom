package com.example.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.base.BaseFragment;
import com.example.eventbus.EventCenter;
import com.example.wisdomclassroom.R;
import com.shuyu.gsyvideoplayer.video.StandardGSYVideoPlayer;

public class CourseFragment extends BaseFragment {

    private StandardGSYVideoPlayer livePlayer;
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
        livePlayer = find(R.id.livePlayer);
        initPlayer();
    }

    private void initPlayer() {
        String source1 = "rtmp://192.168.3.221/live/mix";
        livePlayer.setUp(source1, true, "");
        livePlayer.startPlayLogic();
    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.fragment_course;
    }

    @Override
    protected void onEventComming(EventCenter eventCenter) {

    }

    @Override
    protected boolean isBindEventBusHere() {
        return false;
    }
}
