package com.example.widget;

import android.javax.sdp.SdpException;
import android.javax.sip.InvalidArgumentException;
import android.javax.sip.SipException;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.sip.SipStackAndroid;
import com.example.wisdomclassroom.R;
import com.shuyu.gsyvideoplayer.GSYVideoManager;
import com.shuyu.gsyvideoplayer.video.StandardGSYVideoPlayer;

import java.text.ParseException;

public class MeetingDeal {
    private SipStackAndroid instance;

    //创建会议
    public void createMeeting(View view) {

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    instance = SipStackAndroid.getInstance();
                    instance.createMeeting();
                    //处理完成后给handler发送消息

                    while ("".equals(instance.rtmpUrl) || "".equals(instance.meetingId)) {
                        Thread.sleep(1000);
                    }
                    Message msg = new Message();
                    msg.what = 1;
                    msg.obj = view;
                    handler.sendMessage(msg);

                } catch (SdpException e) {
                    e.printStackTrace();
                } catch (InvalidArgumentException e) {
                    e.printStackTrace();
                } catch (ParseException e) {
                    e.printStackTrace();
                } catch (SipException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        }).start();
    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 1) {
                /*View view = (View) msg.obj;
                VideoView videoView = (VideoView) view.findViewById(R.id.video_view);
                videoView.setVisibility(View.VISIBLE);
                RelativeLayout rl = view.findViewById(R.id.meetWaiting);
                rl.setVisibility(View.GONE);
                videoView.setVideoPath(instance.rtmpUrl).getPlayer().start();
                TextView textView = view.findViewById(R.id.meetingId);
                textView.setText("会议号：" + instance.meetingId);
                textView.invalidate();
                instance.rtmpUrl = "";//播放之后将地址置空
                instance.meetingId = "";*/

                View view = (View) msg.obj;
                StandardGSYVideoPlayer videoView = (StandardGSYVideoPlayer) view.findViewById(R.id.video_view);
                videoView.setVisibility(View.VISIBLE);
                RelativeLayout rl = view.findViewById(R.id.meetWaiting);
                rl.setVisibility(View.GONE);
                videoView.setUp(instance.rtmpUrl, true, "");
                videoView.startPlayLogic();
                TextView textView = view.findViewById(R.id.meetingId);
                textView.setText("会议号：" + instance.meetingId);
                textView.invalidate();
                instance.rtmpUrl = "";//播放之后将地址置空
                instance.meetingId = "";

            }
        }
    };

    //返回会议
    public void changeToMeet(View view) {
        try {
            instance.sendRequestBye();
        } catch (SipException e) {
            e.printStackTrace();
        }
        //切换界面
       /* VideoView videoView = (VideoView) view.findViewById(R.id.video_view);
        if(videoView.getVideoInfo().getUri()!=null){
            videoView.getPlayer().release();
        }*/
        StandardGSYVideoPlayer videoView = (StandardGSYVideoPlayer) view.findViewById(R.id.video_view);
        GSYVideoManager.releaseAllVideos();

    }

    //加入会议
    public void joinMeeting(String meetingId, View view) {

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    instance = SipStackAndroid.getInstance();
                    instance.joinMetting(meetingId);
                    //处理完成后给handler发送消息

                    while ("".equals(instance.rtmpUrl)) {
                        Thread.sleep(1000);
                    }
                    Message msg = new Message();
                    msg.what = 1;
                    msg.obj = view;
                    handler.sendMessage(msg);

                } catch (SdpException e) {
                    e.printStackTrace();
                } catch (InvalidArgumentException e) {
                    e.printStackTrace();
                } catch (ParseException e) {
                    e.printStackTrace();
                } catch (SipException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        }).start();
    }
}
