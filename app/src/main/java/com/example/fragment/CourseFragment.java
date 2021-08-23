package com.example.fragment;

import android.graphics.Color;
import android.view.View;
import android.widget.TextView;

import com.example.base.BaseFragment;
import com.example.eventbus.EventCenter;
import com.example.widget.PressImageView;
import com.example.wisdomclassroom.R;
import com.shuyu.gsyvideoplayer.GSYVideoManager;
import com.shuyu.gsyvideoplayer.model.VideoOptionModel;
import com.shuyu.gsyvideoplayer.video.StandardGSYVideoPlayer;

import java.util.ArrayList;
import java.util.List;

import tv.danmaku.ijk.media.player.IjkMediaPlayer;

public class CourseFragment extends BaseFragment {

    private StandardGSYVideoPlayer livePlayer;
    private PressImageView movieModel;
    private PressImageView resourceModel;
    private PressImageView startRecord;
    private PressImageView pauseRecord;
    private PressImageView stopRecord;
    private PressImageView overTime;
    private TextView recordStatus;
    private TextView countDown;
    private int recordModel = 0;//0电影模式1资源模式
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
        initPlayserOptions(livePlayer);
        initPlayer();
    }

    private void initId() {
        livePlayer = find(R.id.livePlayer);
        movieModel = find(R.id.movieModel);
        resourceModel = find(R.id.resourceModel);
        startRecord = find(R.id.startRecord);
        pauseRecord = find(R.id.pauseRecord);
        stopRecord = find(R.id.stopRecord);
        overTime = find(R.id.overTime);
        recordStatus = getView().findViewById(R.id.recordStatus);
        countDown = getView().findViewById(R.id.countDown);
        movieModel.setBackgroundColor(recordModel==0?Color.parseColor("#0B70E0") : Color.parseColor("#484A6D"));
        resourceModel.setBackgroundColor(recordModel==1?Color.parseColor("#0B70E0") : Color.parseColor("#484A6D"));
        movieModel.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                if(recordModel!=0) recordModel = 0;
                movieModel.setBackgroundColor(recordModel==0?Color.parseColor("#0B70E0") : Color.parseColor("#484A6D"));
                resourceModel.setBackgroundColor(recordModel==1?Color.parseColor("#0B70E0") : Color.parseColor("#484A6D"));
            }
        });
        resourceModel.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                if(recordModel!=1) recordModel = 1;
                movieModel.setBackgroundColor(recordModel==0?Color.parseColor("#0B70E0") : Color.parseColor("#484A6D"));
                resourceModel.setBackgroundColor(recordModel==1?Color.parseColor("#0B70E0") : Color.parseColor("#484A6D"));
            }
        });
    }

    private void initPlayserOptions(StandardGSYVideoPlayer livePlayer) {
        /**此中内容：优化加载速度，降低延迟*/
        List<VideoOptionModel> list = new ArrayList<>();
//        list.add(new VideoOptionModel(IjkMediaPlayer.OPT_CATEGORY_FORMAT, "rtsp_transport", "tcp"));
//        list.add(new VideoOptionModel(IjkMediaPlayer.OPT_CATEGORY_FORMAT, "rtsp_flags", "prefer_tcp"));
//        list.add(new VideoOptionModel(IjkMediaPlayer.OPT_CATEGORY_FORMAT, "allowed_media_types", "video")); //根据媒体类型来配置
//        list.add(new VideoOptionModel(IjkMediaPlayer.OPT_CATEGORY_FORMAT, "timeout", 20000));
//        list.add(new VideoOptionModel(IjkMediaPlayer.OPT_CATEGORY_FORMAT, "buffer_size", 1316));
//        list.add(new VideoOptionModel(IjkMediaPlayer.OPT_CATEGORY_FORMAT, "infbuf", 1));  // 无限读
        //设置是否开启变调
        //list.add(new VideoOptionModel(IjkMediaPlayer.OPT_CATEGORY_PLAYER, "soundtouch", isModifyTone ? 0 : 1));
        //设置是否开启环路过滤: 0开启，画面质量高，解码开销大，48关闭，画面质量差点，解码开销小
//        list.add(new VideoOptionModel(IjkMediaPlayer.OPT_CATEGORY_CODEC, "skip_loop_filter", 0));
        //设置播放前的最大探测时间
        list.add(new VideoOptionModel(IjkMediaPlayer.OPT_CATEGORY_FORMAT, "analyzemaxduration", 100));
        //设置播放前的探测时间 1,达到首屏秒开效果
        list.add(new VideoOptionModel(IjkMediaPlayer.OPT_CATEGORY_FORMAT, "analyzeduration", 1));
        //播放前的探测Size，默认是1M, 改小一点会出画面更快
        list.add(new VideoOptionModel(IjkMediaPlayer.OPT_CATEGORY_FORMAT, "probesize", 1024 * 10));
        //每处理一个packet之后刷新io上下文
//        list.add(new VideoOptionModel(IjkMediaPlayer.OPT_CATEGORY_FORMAT, "flush_packets", 1));
        //是否开启预缓冲，一般直播项目会开启，达到秒开的效果，不过带来了播放丢帧卡顿的体验，也有人说必须关闭，否则会出现播放一段时间后，一直卡主，控制台打印 FFP_MSG_BUFFERING_START
//        list.add(new VideoOptionModel(IjkMediaPlayer.OPT_CATEGORY_PLAYER, "packet-buffering", 0));
        //播放重连次数
        list.add(new VideoOptionModel(IjkMediaPlayer.OPT_CATEGORY_PLAYER, "reconnect", 5));
        //最大缓冲大小,单位kb
        list.add(new VideoOptionModel(IjkMediaPlayer.OPT_CATEGORY_PLAYER, "max-buffer-size", 2048));
        //跳帧处理,放CPU处理较慢时，进行跳帧处理，保证播放流程，画面和声音同步
        list.add(new VideoOptionModel(IjkMediaPlayer.OPT_CATEGORY_PLAYER, "framedrop", 1));
        //最大fps
        list.add(new VideoOptionModel(IjkMediaPlayer.OPT_CATEGORY_PLAYER, "max-fps", 30));
        //设置硬解码方式：jkPlayer支持硬解码和软解码。 软解码时不会旋转视频角度这时需要你通过onInfo的what == IMediaPlayer.MEDIA_INFO_VIDEO_ROTATION_CHANGED去获取角度，自己旋转画面。或者开启硬解硬解码，不过硬解码容易造成黑屏无声（硬件兼容问题），下面是设置硬解码相关的代码
//        list.add(new VideoOptionModel(IjkMediaPlayer.OPT_CATEGORY_PLAYER, "mediacodec", 1));
        list.add(new VideoOptionModel(IjkMediaPlayer.OPT_CATEGORY_PLAYER, "mediacodec-auto-rotate", 1));
//        list.add(new VideoOptionModel(IjkMediaPlayer.OPT_CATEGORY_PLAYER, "mediacodec-handle-resolution-change", 1));
        //SeekTo设置优化：某些视频在SeekTo的时候，会跳回到拖动前的位置，这是因为视频的关键帧的问题，通俗一点就是FFMPEG不兼容，视频压缩过于厉害，seek只支持关键帧，出现这个情况就是原始的视频文件中i 帧比较少
//        list.add(new VideoOptionModel(IjkMediaPlayer.OPT_CATEGORY_PLAYER, "enable-accurate-seek", 1));
        //解决m3u8文件拖动问题 比如:一个3个多少小时的音频文件，开始播放几秒中，然后拖动到2小时左右的时间，要loading 10分钟
//        list.add(new VideoOptionModel(IjkMediaPlayer.OPT_CATEGORY_FORMAT, "fflags", "fastseek"));//设置seekTo能够快速seek到指定位置并播放
        GSYVideoManager.instance().setOptionModelList(list);
        /**配置项配置结束，下面设置播放器*/

    }

    private void initPlayer() {

//        String source1 = "rtmp://192.168.107.88:1935/live/sub";
        livePlayer.setVisibility(View.VISIBLE);
        //增加title
        livePlayer.getTitleTextView().setVisibility(View.GONE);
        //设置返回键
        livePlayer.getBackButton().setVisibility(View.GONE);
        //小屏时不触摸滑动
        livePlayer.setIsTouchWiget(false);
        livePlayer.setOnClickListener(null);
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
