package com.example.fragment;

import android.content.ServiceConnection;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.base.BaseFragment;
import com.example.eventbus.EventCenter;
import com.example.service.smartControl.SocketService;
import com.example.service.smartControl.entity.LightControl;
import com.example.wisdomclassroom.R;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;

import cn.hutool.core.util.RandomUtil;

public class ControlFragment extends BaseFragment {
    private ServiceConnection sc;
    public SocketService socketService;

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
        LinearLayout openLinearLayout=find(R.id.lightOpen);
        openLinearLayout.setOnClickListener(v -> {
            //开灯
            LightControl lightControl=new LightControl();
            lightControl.setCode(1002);
            lightControl.setId("010100124b001acca677");
            lightControl.setEp(3);
            lightControl.setPid(260);
            lightControl.setDid(0);
            lightControl.setSerial(RandomUtil.randomInt(32));
            Map<String, Object> control=new HashMap<String,Object>();
            control.put("on",true);
            lightControl.setControl(control);
            Gson gson=new Gson();
            String responseStr =gson.toJson(lightControl);
            new Thread(new Runnable(){
                @Override
                public void run() {
                    socketService.sendData(responseStr);
                }
            }).start();
        });

        LinearLayout closeLinearLayout=find(R.id.lightClose);
        closeLinearLayout.setOnClickListener(v -> {
            //关灯
            LightControl lightControl=new LightControl();
            lightControl.setCode(1002);
            lightControl.setId("010100124b001acca677");
            lightControl.setEp(3);
            lightControl.setPid(260);
            lightControl.setDid(0);
            lightControl.setSerial(RandomUtil.randomInt(32));
            Map<String, Object> control=new HashMap<String,Object>();
            control.put("on",false);
            lightControl.setControl(control);
            Gson gson=new Gson();
            String responseStr =gson.toJson(lightControl);
            new Thread(new Runnable(){
                @Override
                public void run() {
                    socketService.sendData(responseStr);
                }
            }).start();
        });

        LinearLayout openWindowLinearLayout=find(R.id.windowOpen);
        openWindowLinearLayout.setOnClickListener(v -> {
            //打开窗帘
            LightControl lightControl=new LightControl();
            lightControl.setCode(1002);
            lightControl.setId("010100124b001acca677");
            lightControl.setEp(3);
//            lightControl.setPid(260);
//            lightControl.setDid(0);
            lightControl.setSerial(RandomUtil.randomInt(32));
            Map<String, Object> control=new HashMap<String,Object>();
            control.put("cts",1);
            lightControl.setControl(control);
            Gson gson=new Gson();
            String responseStr =gson.toJson(lightControl);
            new Thread(new Runnable(){
                @Override
                public void run() {
                    socketService.sendData(responseStr);
                }
            }).start();
        });

        LinearLayout closeWindowLinearLayout=find(R.id.windowClose);
        closeWindowLinearLayout.setOnClickListener(v -> {
            //关灯
            LightControl lightControl=new LightControl();
            lightControl.setCode(1002);
            lightControl.setId("010100124b001acca677");
            lightControl.setEp(3);
//            lightControl.setPid(260);
//            lightControl.setDid(0);
            lightControl.setSerial(RandomUtil.randomInt(32));
            Map<String, Object> control=new HashMap<String,Object>();
            control.put("cts",0);
            lightControl.setControl(control);
            Gson gson=new Gson();
            String responseStr =gson.toJson(lightControl);
            new Thread(new Runnable(){
                @Override
                public void run() {
                    socketService.sendData(responseStr);
                }
            }).start();
        });
    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.fragment_control;
    }

    @Override
    protected void onEventComming(EventCenter eventCenter) {

    }

    @Override
    protected boolean isBindEventBusHere() {
        return false;
    }
}
