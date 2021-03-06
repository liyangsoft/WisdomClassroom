package com.example.fragment;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.hardware.display.DisplayManager;
import android.hardware.display.VirtualDisplay;
import android.media.Image;
import android.media.ImageReader;
import android.media.projection.MediaProjection;
import android.media.projection.MediaProjectionManager;
import android.opengl.Visibility;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.blankj.utilcode.util.ConvertUtils;
import com.blankj.utilcode.util.ImageUtils;
import com.blankj.utilcode.util.ScreenUtils;
import com.blankj.utilcode.util.SizeUtils;
import com.blankj.utilcode.util.ToastUtils;

import com.example.base.BaseFragment;
import com.example.base.Constants;
import com.example.camera.JCameraView;

import com.example.entity.GroupEntity;

import com.example.eventbus.EventCenter;

import com.example.service.GroupFourService;
import com.example.service.GroupOneService;
import com.example.service.GroupThreeService;
import com.example.service.GroupTwoService;
import com.example.service.MediaReaderService;
import com.example.widget.ChildClickableLinearLayout;
import com.example.widget.PopwindowUtils;
import com.example.wisdomclassroom.R;
import com.sc.lesa.mediashar.jlib.server.SocketClientThread;
import com.sc.lesa.mediashar.jlib.threads.VideoPlayThread;
import com.shuyu.gsyvideoplayer.GSYVideoManager;
import com.shuyu.gsyvideoplayer.listener.GSYVideoShotListener;
import com.shuyu.gsyvideoplayer.video.StandardGSYVideoPlayer;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import org.greenrobot.eventbus.EventBus;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import cn.hzw.doodle.DoodleColor;
import cn.hzw.doodle.DoodleOnTouchGestureListener;
import cn.hzw.doodle.DoodleParams;
import cn.hzw.doodle.DoodlePen;
import cn.hzw.doodle.DoodleShape;
import cn.hzw.doodle.DoodleTouchDetector;
import cn.hzw.doodle.DoodleView;
import cn.hzw.doodle.IDoodleListener;
import cn.hzw.doodle.core.IDoodle;
import cn.hzw.doodle.core.IDoodleItem;
import cn.hzw.doodle.core.IDoodleSelectableItem;
import cn.hzw.doodle.core.IDoodleTouchDetector;
import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;

import static com.blankj.utilcode.util.ServiceUtils.startService;
import static io.reactivex.internal.schedulers.SchedulerPoolFactory.start;

/**
 * ????????????
 */
public class TeachingFragment extends BaseFragment implements EasyPermissions.PermissionCallbacks, SurfaceHolder.Callback {

    private RecyclerView recyclerView;
    private RelativeLayout rlRecyclerView;
    private LinearLayout llGroup;
    private RelativeLayout rlBroadcast;
    private RelativeLayout rlContrast;
    private RelativeLayout rlSpeak;
    private RelativeLayout rlNotebook;
    private RelativeLayout rlTeacher;
    private RelativeLayout rlWifi;
    private RelativeLayout rlLong;
    private RelativeLayout rlMore;

    private List<GroupEntity> arrayList;
    private LinearLayout llPlank;
    private ImageView ivPlank;
    private TextView tvPlank;
    private LinearLayout llCircle;
    private ImageView ivCircle;
    private TextView tvCircle;
    private ImageView ivPen;
    private ImageView ivEraser;
    private ImageView ivOrthogon;
    private FrameLayout frameLayout;
    private Button button;
    private ImageView ivRecover;
    private RelativeLayout rlHide;
    private RelativeLayout rlBottomUtil;
    private ChildClickableLinearLayout llLeftUtil;
    private RelativeLayout rlPen;
    private RelativeLayout rlEraser;
    private RelativeLayout rlBack;
    private ImageView ivBack;
    private RelativeLayout rlGo;
    private ImageView ivGo;
    private RelativeLayout rlAdd;
    private ImageView ivAdd;
    private RelativeLayout rlLast;
    private ImageView ivLast;
    private RelativeLayout rlNext;
    private ImageView ivNext;
    private RelativeLayout rlOrthogon;

    private DoodleParams mDoodleParams;
    private DoodleView mDoodleView;
    private IDoodle mDoodle;
    private DoodleOnTouchGestureListener mTouchGestureListener;
    private RelativeLayout rlExit;

    private boolean isChoice = false;//????????????
    private int maxChoiceNum = 4;//????????????
    private int choiceNum = 0;//????????????

    private boolean isDraw = false;//????????????????????????
    private RelativeLayout rlVideo;
    private JCameraView jCameraView;


    private static final int RC_CAMERA_PERM = 123;


    private boolean isBroadcast = false; //??????????????????
    private boolean isContrast = false; //??????????????????

    private int currentLookGrooup = -1;//?????????????????????position
    private StandardGSYVideoPlayer videoPlayer;
    private SurfaceView mSurfaceView;
    private SurfaceHolder mSurfaceHolder;
    private VideoPlayThread mdiaPlayThread;
    private VideoPlayThread mdiaPlayThread1;
    private VideoPlayThread mdiaPlayThread2;
    private VideoPlayThread mdiaPlayThread3;
    private VideoPlayThread mdiaPlayThread4;


    private SocketClientThread socketClientThread;
    private LinearLayout contrast;

    private SurfaceHolder surfaceHolder1;

    private SurfaceHolder surfaceHolder2;
    private SurfaceHolder surfaceHolder3;
    private SurfaceHolder surfaceHolder4;
    private SocketClientThread socketClientThread1;
    private SocketClientThread socketClientThread2;
    private SocketClientThread socketClientThread3;
    private SocketClientThread socketClientThread4;
    private LinearLayout llSufaceTop;
    private LinearLayout llSufaceBottom;

    private List<String> ipList = new ArrayList<>();
    private SurfaceView surfaceView4;
    private SurfaceView surfaceView3;
    String[] perms = {Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE};
    private String TAG = "teachering";
    private MediaProjectionManager mediaProjectionManager;
    private int REQUEST_MEDIA_PROJECTION = 20;
    private Bitmap currentBitmap;

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
        setClick();
        initPaint();
        initPlayer();
        openCamera();

    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.fragment_teaching;
    }

    private void initPlayer() {
        String source1 = "rtmp://192.168.107.88:1935/live/sub";
        videoPlayer.setUp(source1, true, "");
        videoPlayer.startPlayLogic();
    }

    @Override
    public void onStart() {
        super.onStart();
        //????????????
        if (Build.VERSION.SDK_INT >= 19) {
            View decorView = getActivity().getWindow().getDecorView();
            decorView.setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        } else {
            View decorView = getActivity().getWindow().getDecorView();
            int option = View.SYSTEM_UI_FLAG_FULLSCREEN;
            decorView.setSystemUiVisibility(option);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (null != jCameraView) {
            jCameraView.onResume();

        }

        videoPlayer.onVideoResume();

    }

    @Override
    public void onPause() {
        super.onPause();
        if (null != jCameraView) {
            jCameraView.onPause();

        }

        videoPlayer.onVideoPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        GSYVideoManager.releaseAllVideos();
        stopSocket();

    }

    private void inidRecycler() {
        arrayList = new ArrayList<>();
        arrayList.clear();
        arrayList.add(new GroupEntity("?????????", Constants.groupIP1));
        arrayList.add(new GroupEntity("?????????", Constants.groupIP2));
        arrayList.add(new GroupEntity("?????????", Constants.groupIP3));
        arrayList.add(new GroupEntity("?????????", Constants.groupIP4));
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        recyclerView.setAdapter(new CommonAdapter<GroupEntity>(mContext, R.layout.item_group, arrayList) {

            @Override
            protected void convert(ViewHolder holder, GroupEntity groupEntity, int position) {
                holder.setText(R.id.tv_name, groupEntity.getName());
                RelativeLayout relativeLayout = holder.getView(R.id.rl_bg);
                relativeLayout.setBackgroundResource(groupEntity.isCheck() ? R.drawable.shape_group_select : R.drawable.shape_group_normal);

                holder.itemView.setOnClickListener(v -> {
                    if (choiceNum >= 4) {
                        showToast(mContext, "????????????4???");
                        return;
                    }
                    for (int i = 0; i < arrayList.size(); i++) {

                        if (isChoice) {
                            if (position == i) {
                                choiceNum++;
                                arrayList.get(i).setCheck(true);
                                ipList.add(groupEntity.getIp());
                            }

                        } else {
                            if (position == i) {
                                arrayList.get(i).setCheck(true);
                            } else {
                                arrayList.get(i).setCheck(false);
                            }
                        }

                    }
                    notifyDataSetChanged();
                    ToastUtils.showLong("??????????????????????????????~");

                    //????????????
                    if (!isChoice) {
                        stopSocket();

                        currentLookGrooup = position;
                        initSocket(groupEntity.getIp(), Constants.mainPort);

                    } else {
                        if (ipList.size() == 2) {
                            llSufaceTop.setVisibility(View.VISIBLE);
                            initContrastSocket1(ipList.get(0), Constants.mainPort);
                            initContrastSocket2(ipList.get(1), Constants.mainPort);

                        } else if (ipList.size() == 3) {
                            llSufaceBottom.setVisibility(View.VISIBLE);

                            initContrastSocket3(ipList.get(2), Constants.mainPort);
                        } else if (ipList.size() == 4) {

                            initContrastSocket4(ipList.get(3), Constants.mainPort);
                        }

                    }

                });

            }
        });
    }

    private void initId() {

        recyclerView = find(R.id.recyclerView);
        rlRecyclerView = find(R.id.rl_recyclerView);
        llGroup = find(R.id.ll_group);
        rlBroadcast = find(R.id.rl_broadcast);
        rlContrast = find(R.id.rl_contrast);
        rlSpeak = find(R.id.rl_speak);
        rlNotebook = find(R.id.rl_notebook);
        rlTeacher = find(R.id.rl_teacher);
        rlWifi = find(R.id.rl_wifi);
        rlLong = find(R.id.rl_long);
        rlMore = find(R.id.rl_more);

        llPlank = find(R.id.ll_plank);
        ivPlank = find(R.id.iv_plank);
        tvPlank = find(R.id.tv_plank);
        llCircle = find(R.id.ll_circle);
        ivCircle = find(R.id.iv_circle);
        tvCircle = find(R.id.tv_circle);

        rlPen = find(R.id.rl_pen);
        ivPen = find(R.id.iv_pen);
        rlEraser = find(R.id.rl_eraser);
        ivEraser = find(R.id.iv_eraser);
        rlOrthogon = find(R.id.rl_orthogon);
        ivOrthogon = find(R.id.iv_orthogon);
        rlBack = find(R.id.rl_back);
        ivBack = find(R.id.iv_back);
        rlGo = find(R.id.rl_go);
        ivGo = find(R.id.iv_go);
        rlAdd = find(R.id.rl_add);
        ivAdd = find(R.id.iv_add);
        rlLast = find(R.id.rl_last);
        ivLast = find(R.id.iv_last);
        rlNext = find(R.id.rl_next);
        ivNext = find(R.id.iv_next);


        frameLayout = find(R.id.doodle_container);

        button = find(R.id.button);
        ivRecover = find(R.id.iv_recover);
        rlHide = find(R.id.line1);
        rlBottomUtil = find(R.id.rl_bottom);
        llLeftUtil = find(R.id.ll_left);
        rlExit = find(R.id.rl_exit);

        rlVideo = find(R.id.rl_video);

        jCameraView = find(R.id.jcameraview);

        videoPlayer = find(R.id.player);
        mSurfaceView = find(R.id.surfaceView_watch);
        mSurfaceHolder = mSurfaceView.getHolder();
        mSurfaceHolder.addCallback(this);

        contrast = find(R.id.contrast);
        llSufaceTop = find(R.id.ll_suface_top);
        SurfaceView surfaceView1 = find(R.id.surfaceView1);
        surfaceHolder1 = surfaceView1.getHolder();
        SurfaceView surfaceView2 = find(R.id.surfaceView2);
        surfaceHolder2 = surfaceView2.getHolder();

        llSufaceBottom = find(R.id.ll_suface_bottom);
        surfaceView3 = find(R.id.surfaceView3);
        surfaceHolder3 = surfaceView3.getHolder();
        surfaceView4 = find(R.id.surfaceView4);
        surfaceHolder4 = surfaceView4.getHolder();

    }

    private void setClick() {
        //??????
        llGroup.setOnClickListener(v -> {
            videoPlayer.onVideoPause();
            inidRecycler();

            cutIcon(1);
        });
        //??????
        rlBroadcast.setOnClickListener(v -> {
            if (isContrast) {
                ToastUtils.showLong("??????????????????");
                return;
            }
            String str;
            if (isBroadcast) {
                //????????????
                rlBroadcast.setBackgroundColor(Color.parseColor("#484A6D"));
                str = "0";
            } else {
                //????????????
                rlBroadcast.setBackgroundColor(Color.parseColor("#0B70E0"));
                str = "1";
            }
            //????????????
            isBroadcast = !isBroadcast;
            if (currentLookGrooup != -1) {
                switch (currentLookGrooup) {
                    case 1:
                        GroupTwoService.sendTcpMessage(str);
                        GroupThreeService.sendTcpMessage(str);
                        GroupFourService.sendTcpMessage(str);
                        break;
                    case 2:
                        GroupOneService.sendTcpMessage(str);
                        GroupThreeService.sendTcpMessage(str);
                        GroupFourService.sendTcpMessage(str);
                        break;
                    case 3:
                        GroupOneService.sendTcpMessage(str);
                        GroupTwoService.sendTcpMessage(str);
                        GroupFourService.sendTcpMessage(str);
                        break;
                    case 4:
                        GroupOneService.sendTcpMessage(str);
                        GroupTwoService.sendTcpMessage(str);
                        GroupThreeService.sendTcpMessage(str);
                        break;

                }
            } else {
                GroupOneService.sendTcpMessage(str);
                GroupTwoService.sendTcpMessage(str);
                GroupThreeService.sendTcpMessage(str);
                GroupFourService.sendTcpMessage(str);
            }


        });

        //??????
        rlContrast.setOnClickListener(v -> {

            if (isBroadcast) {
                ToastUtils.showLong("??????????????????");
                return;
            }

            videoPlayer.onVideoPause();
            inidRecycler();
            cutIcon(3);
        });
        rlSpeak.setOnClickListener(v -> {
            cutIcon(4);
        });
        rlNotebook.setOnClickListener(v -> {
            cutIcon(5);
        });

        //?????????
        rlTeacher.setOnClickListener(v -> {
            openCamera();

        });

        rlWifi.setOnClickListener(v -> {
            cutIcon(7);
        });

        //??????
        rlLong.setOnClickListener(v -> {
            cutIcon(8);
            PopwindowUtils.showLongPop(mContext, rlLong, rlBottomUtil);
        });
        rlMore.setOnClickListener(v -> {
            cutIcon(9);
        });

        //????????????
        llPlank.setOnClickListener(v -> {
            if (isDraw) {
                ToastUtils.showShort("????????????????????????????????????????????????????????????");
                return;
            }
            isDraw = true;
            cutDrawing(1);
            noClickExceptDrawing();
            EventBus.getDefault().post(new EventCenter<>(Constants.EVENT_VIEWPAGER, false));
            opendrawing(1);

        });

        //??????
        llCircle.setOnClickListener(v -> {
            if (isDraw) {
                ToastUtils.showShort("????????????????????????????????????????????????????????????");
                return;
            }
            isDraw = true;
            cutDrawing(2);
            noClickExceptDrawing();
            EventBus.getDefault().post(new EventCenter<>(Constants.EVENT_VIEWPAGER, false));
            opendrawing(2);
        });

        //??????
        rlPen.setOnClickListener(v -> {
            if (!isDraw) {
                return;
            }
            cutPen(1);
            PopwindowUtils.showPop(mContext, rlPen, new PopwindowUtils.ColorPenListener() {
                @Override
                public void penSet(int size) {
                    if (null != mDoodle) {
                        mDoodle.setSize(size);
                    }

                }

                @Override
                public void colorSet(String color) {
                    if (null != mDoodle) {
                        mDoodle.setColor(new DoodleColor(Color.parseColor(color)));
                    }

                }
            });


        });
        //?????????
        rlEraser.setOnClickListener(v -> {
            if (!isDraw) {
                return;
            }
            cutPen(2);
        });
        //?????????
        rlBack.setOnClickListener(v -> {
            if (!isDraw) {
                return;
            }
            cutPen(3);
            if (frameLayout.getVisibility() == View.VISIBLE && mDoodle != null) {
                mDoodle.undo();
            }
        });
        //?????????
        rlGo.setOnClickListener(v -> {
            if (!isDraw) {
                return;
            }
            cutPen(4);
            if (frameLayout.getVisibility() == View.VISIBLE && mDoodle != null) {
                mDoodle.redo(1);

            }
        });
        //??????
        rlAdd.setOnClickListener(v -> {
            if (!isDraw) {
                return;
            }
            cutPen(5);
        });
        //?????????
        rlLast.setOnClickListener(v -> {
            if (!isDraw) {
                return;
            }
            cutPen(6);
        });
        //?????????
        rlNext.setOnClickListener(v -> {
            if (!isDraw) {
                return;
            }
            cutPen(7);
        });
        //??????
        rlOrthogon.setOnClickListener(v -> {
            if (!isDraw) {
                return;
            }
            cutPen(8);
        });

        //??????
        button.setOnClickListener(v -> {
            button.setText(button.getText().toString().equals("??????") ? "??????" : "??????");

        });

        //????????????
        rlExit.setOnClickListener(v -> {
            isDraw = false;
            if (null != mDoodle) {
                mDoodle.clear();
            }
            if (null != currentBitmap) {
                currentBitmap.recycle();
            }
            allowClickExceptDrawing();
            cutPen(0);
            cutDrawing(0);
            EventBus.getDefault().post(new EventCenter<>(Constants.EVENT_VIEWPAGER, true));


        });
        //???????????????
        rlHide.setOnClickListener(v -> {
            showHideUtil(false);

        });
        //???????????????
        ivRecover.setOnClickListener(v -> {
            showHideUtil(true);

        });
    }

    private void stopSocket() {
        if (null != socketClientThread) {
            socketClientThread.exit();
            socketClientThread = null;
        }
        if (null != mdiaPlayThread) {
            mdiaPlayThread.exit();
            mdiaPlayThread = null;
        }

        if (null != socketClientThread1) {
            socketClientThread1.exit();
            socketClientThread1 = null;
        }
        if (null != mdiaPlayThread1) {
            mdiaPlayThread1.exit();
            mdiaPlayThread1 = null;
        }

        if (null != socketClientThread2) {
            socketClientThread2.exit();
            socketClientThread2 = null;
        }
        if (null != mdiaPlayThread2) {
            mdiaPlayThread2.exit();
            mdiaPlayThread2 = null;
        }

        if (null != socketClientThread3) {
            socketClientThread3.exit();
            socketClientThread3 = null;
        }
        if (null != mdiaPlayThread3) {
            mdiaPlayThread3.exit();
            mdiaPlayThread3 = null;
        }

        if (null != socketClientThread4) {
            socketClientThread4.exit();
            socketClientThread4 = null;
        }
        if (null != mdiaPlayThread4) {
            mdiaPlayThread4.exit();
            mdiaPlayThread4 = null;
        }
    }

    /**
     * ??????socket
     *
     * @param ip
     * @param port
     */
    private void initSocket(String ip, int port) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    socketClientThread = new ClientThread(ip, port);
                    socketClientThread.connect();
                    socketClientThread.start();

                    if (null == mdiaPlayThread) {
                        mdiaPlayThread = new VideoPlayThread(mSurfaceHolder.getSurface(), socketClientThread.getDataPackList());
                    }

                    mdiaPlayThread.start();
                } catch (Exception e) {

                    ToastUtils.showShort("????????????" + e.getMessage());
                }

            }
        }).start();

    }

    /**
     * ??????socket1
     *
     * @param ip
     * @param port
     */
    private void initContrastSocket1(String ip, int port) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    socketClientThread1 = new ClientThread(ip, port);
                    socketClientThread1.connect();
                    socketClientThread1.start();
                    if (null == mdiaPlayThread1) {
                        mdiaPlayThread1 = new VideoPlayThread(surfaceHolder1.getSurface(), socketClientThread1.getDataPackList());
                    }

                    mdiaPlayThread1.start();
                } catch (Exception e) {

                    ToastUtils.showShort("????????????" + e.getMessage());
                }

            }
        }).start();

    }

    /**
     * ??????socket2
     *
     * @param ip
     * @param port
     */
    private void initContrastSocket2(String ip, int port) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    socketClientThread2 = new ClientThread(ip, port);
                    socketClientThread2.connect();
                    socketClientThread2.start();
                    if (null == mdiaPlayThread2) {
                        mdiaPlayThread2 = new VideoPlayThread(surfaceHolder2.getSurface(), socketClientThread2.getDataPackList());
                    }

                    mdiaPlayThread2.start();
                } catch (Exception e) {

                    ToastUtils.showShort("????????????" + e.getMessage());
                }

            }
        }).start();

    }

    /**
     * ??????socket3
     *
     * @param ip
     * @param port
     */
    private void initContrastSocket3(String ip, int port) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    socketClientThread3 = new ClientThread(ip, port);
                    socketClientThread3.connect();
                    socketClientThread3.start();
                    if (null == mdiaPlayThread3) {
                        mdiaPlayThread3 = new VideoPlayThread(surfaceHolder3.getSurface(), socketClientThread3.getDataPackList());
                    }

                    mdiaPlayThread3.start();
                } catch (Exception e) {
                    ToastUtils.showShort("????????????" + e.getMessage());
                }

            }
        }).start();

    }

    /**
     * ??????socket4
     *
     * @param ip
     * @param port
     */
    private void initContrastSocket4(String ip, int port) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    socketClientThread4 = new ClientThread(ip, port);
                    socketClientThread4.connect();
                    socketClientThread4.start();
                    if (null == mdiaPlayThread4) {
                        mdiaPlayThread4 = new VideoPlayThread(surfaceHolder4.getSurface(), socketClientThread4.getDataPackList());
                    }

                    mdiaPlayThread4.start();
                } catch (Exception e) {

                    ToastUtils.showShort("????????????" + e.getMessage());
                }

            }
        }).start();

    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {

    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {

    }

    private class ClientThread extends SocketClientThread {

        public ClientThread(@NotNull String ip, int port) {
            super(ip, port);
        }

        @Override
        public void onError(@NotNull Throwable t) {
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    ToastUtils.showShort("??????:" + t.getMessage());
                }
            });
        }
    }

    /**
     * ??????????????????????????????
     */
    private void noClickExceptDrawing() {

        rlExit.setVisibility(View.VISIBLE);
        llLeftUtil.setChildClickable(false);
        button.setEnabled(false);

    }

    /**
     * ???????????????
     */
    private void allowClickExceptDrawing() {
        frameLayout.setVisibility(View.GONE);
        rlExit.setVisibility(View.INVISIBLE);
        llLeftUtil.setChildClickable(true);
        button.setEnabled(true);

    }

    /**
     * U??????/???????????????
     *
     * @param flag
     */
    private void showHideUtil(boolean flag) {

        rlBottomUtil.setVisibility(flag ? View.VISIBLE : View.INVISIBLE);
        button.setVisibility(flag ? View.VISIBLE : View.INVISIBLE);
        llLeftUtil.setVisibility(flag ? View.VISIBLE : View.INVISIBLE);
        ivRecover.setVisibility(flag ? View.GONE : View.VISIBLE);
    }

    /**
     * ????????????
     *
     * @param position
     */
    private void cutIcon(int position) {
        stopSocket();
        llGroup.setBackgroundColor(position == 1 ? Color.parseColor("#0B70E0") : Color.parseColor("#484A6D"));
//        rlBroadcast.setBackgroundColor(position == 2 ? Color.parseColor("#0B70E0") : Color.parseColor("#484A6D"));
        rlContrast.setBackgroundColor(position == 3 ? Color.parseColor("#0B70E0") : Color.parseColor("#484A6D"));
        rlSpeak.setBackgroundColor(position == 4 ? Color.parseColor("#0B70E0") : Color.parseColor("#484A6D"));
        rlNotebook.setBackgroundColor(position == 5 ? Color.parseColor("#0B70E0") : Color.parseColor("#484A6D"));
        rlTeacher.setBackgroundColor(position == 6 ? Color.parseColor("#0B70E0") : Color.parseColor("#484A6D"));
        rlWifi.setBackgroundColor(position == 7 ? Color.parseColor("#0B70E0") : Color.parseColor("#484A6D"));
        rlLong.setBackgroundColor(position == 8 ? Color.parseColor("#0B70E0") : Color.parseColor("#484A6D"));
        rlMore.setBackgroundColor(position == 9 ? Color.parseColor("#0B70E0") : Color.parseColor("#484A6D"));
        rlRecyclerView.setVisibility(position == 1 || position == 3 ? View.VISIBLE : View.GONE);
        isChoice = position == 3 ? true : false;
        choiceNum = 0;
        switch (position) {
            case 1:
                mSurfaceView.setVisibility(View.VISIBLE);
                jCameraView.setVisibility(View.GONE);
                break;
            case 3:
                videoPlayer.onVideoResume();
                videoPlayer.setVisibility(View.GONE);
                contrast.setVisibility(View.VISIBLE);
                isContrast = true;
                break;
            case 6:
                videoPlayer.onVideoPause();
                videoPlayer.setVisibility(View.GONE);
                mSurfaceView.setVisibility(View.GONE);
                jCameraView.setVisibility(View.VISIBLE);
                break;

            default:
                videoPlayer.onVideoResume();
                videoPlayer.setVisibility(View.VISIBLE);
                jCameraView.setVisibility(View.GONE);
                mSurfaceView.setVisibility(View.GONE);
                contrast.setVisibility(View.GONE);
                isContrast = false;

                break;
        }


    }

    /**
     * ????????????  0????????????
     *
     * @param position
     */
    private void cutDrawing(int position) {
        llPlank.setBackgroundResource(position == 1 ? R.drawable.shape_select_blue : R.drawable.shape_select_white);
        ivPlank.setImageResource(position == 1 ? R.drawable.blue_plank_icon : R.drawable.white_plank_icon);
        tvPlank.setTextColor(position == 1 ? Color.parseColor("#00D5FF") : Color.parseColor("#FFFFFF"));

        llCircle.setBackgroundResource(position == 2 ? R.drawable.shape_select_blue : R.drawable.shape_select_white);
        ivCircle.setImageResource(position == 2 ? R.drawable.blue_circle_icon : R.drawable.white_circle_icon);
        tvCircle.setTextColor(position == 2 ? Color.parseColor("#00D5FF") : Color.parseColor("#FFFFFF"));
    }


    /**
     * ?????????????????????  0????????????
     */
    private void cutPen(int position) {
        ivPen.setImageResource(position == 1 ? R.drawable.blue_pen_icon : R.drawable.white_pen_icon);
        ivEraser.setImageResource(position == 2 ? R.drawable.blue_eraser_icon : R.drawable.white_eraser_icon);
        ivBack.setImageResource(position == 3 ? R.drawable.blue_back_icon : R.drawable.white_back_icon);
        ivGo.setImageResource(position == 4 ? R.drawable.blue_go_icon : R.drawable.white_go_icon);
        ivAdd.setImageResource(position == 5 ? R.drawable.blue_add_icon : R.drawable.white_add_icon);
        ivLast.setImageResource(position == 6 ? R.drawable.blue_last_icon : R.drawable.white_last_icon);
        ivNext.setImageResource(position == 7 ? R.drawable.blue_next_icon : R.drawable.white_next_icon);
        ivOrthogon.setImageResource(position == 8 ? R.drawable.blue_orthogon_icon : R.drawable.white_orthogon_icon);
    }

    /**
     * ????????????
     *
     * @param type 1.????????????   2??????
     */
    private void opendrawing(int type) {

        if (type == 1) {
            frameLayout.setVisibility(View.VISIBLE);
            ViewTreeObserver viewTreeObserver = frameLayout.getViewTreeObserver();
            viewTreeObserver.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                @Override
                public void onGlobalLayout() {
                    frameLayout.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                    //??????????????????????????????
                    Bitmap mBitmap = Bitmap.createBitmap(frameLayout.getMeasuredWidth(), frameLayout.getMeasuredHeight(), Bitmap.Config.ARGB_8888);//???????????????????????????
                    Canvas canvasBg = new Canvas(mBitmap);
                    canvasBg.drawColor(Color.WHITE);

                    drawing(mBitmap);
                }
            });
        } else {

            startMediaProjection();

        }

    }


    /**
     * ????????????
     */
    private void startMediaProjection() {
        mediaProjectionManager = (MediaProjectionManager) mContext.getSystemService(
                Context.MEDIA_PROJECTION_SERVICE);
        startActivityForResult(mediaProjectionManager.createScreenCaptureIntent(),
                REQUEST_MEDIA_PROJECTION);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_MEDIA_PROJECTION) {
            if (resultCode == Activity.RESULT_OK && data != null) {
                MediaProjection mediaProjection = mediaProjectionManager.getMediaProjection(resultCode, data);
                if (mediaProjection == null) {
                    return;
                }

                int width = ScreenUtils.getAppScreenWidth();
                int height = ScreenUtils.getAppScreenHeight();
                ImageReader mImageReader = ImageReader.newInstance(width, height, PixelFormat.RGBA_8888, 2);
                VirtualDisplay virtualDisplay = mediaProjection.createVirtualDisplay("screen-mirror", width, height,
                        ScreenUtils.getScreenDensityDpi(), DisplayManager.VIRTUAL_DISPLAY_FLAG_AUTO_MIRROR, mImageReader.getSurface(), null, null);

                new Handler().postDelayed(new Runnable() {

                    private Image image;

                    @Override
                    public void run() {
                        try {
                            image = mImageReader.acquireLatestImage();
                            if (image != null) {
                                final Image.Plane[] planes = image.getPlanes();
                                final ByteBuffer buffer = planes[0].getBuffer();
                                int width = image.getWidth();
                                int height = image.getHeight();

                                int pixelStride = planes[0].getPixelStride();
                                int rowStride = planes[0].getRowStride();
                                int rowPadding = rowStride - pixelStride * width;
                                Bitmap bitmap = Bitmap.createBitmap(width + rowPadding / pixelStride, height, Bitmap.Config.ARGB_8888);
                                bitmap.copyPixelsFromBuffer(buffer);
                                bitmap = Bitmap.createScaledBitmap(bitmap, bitmap.getWidth(), bitmap.getHeight(), false);
                                if (bitmap != null) {
                                    Log.e(TAG, "??????????????????!" + "-------" + SizeUtils.dp2px(85) + "-------" + bitmap.getWidth() + "----------" + bitmap.getHeight());
                                    currentBitmap = Bitmap.createBitmap(bitmap, SizeUtils.dp2px(84), 0, bitmap.getWidth() - SizeUtils.dp2px(85), bitmap.getHeight() - SizeUtils.dp2px(85));

                                    frameLayout.setVisibility(View.VISIBLE);
                                    drawing(currentBitmap);

                                }

                            }
                        } catch (Exception e) {
                            Log.e(TAG, "?????????????????????" + e.toString());
                        } finally {
                            if (image != null) {
                                image.close();
                            }
                            if (mImageReader != null) {
                                mImageReader.close();
                            }
                            if (virtualDisplay != null) {
                                virtualDisplay.release();
                            }
                            //??????BufferQueueProducer: [ImageReader] dequeueBuffer: BufferQueue has been abandoned
                            mImageReader.setOnImageAvailableListener(null, null);
                            mediaProjection.stop();
                        }
                    }
                }, 100);

            }
        }
    }


    /**
     * ????????????
     *
     * @param mBitmap
     */
    private void drawing(Bitmap mBitmap) {
        mDoodle = mDoodleView = new DoodleView(mContext, mBitmap, true, new IDoodleListener() {
            @Override
            public void onSaved(IDoodle doodle, Bitmap doodleBitmap, Runnable callback) {


            }

            @Override
            public void onReady(IDoodle doodle) {
                // ???????????????
                mDoodle.setSize(5);
                // ????????????
                mDoodle.setPen(DoodlePen.BRUSH);
                mDoodle.setShape(DoodleShape.HAND_WRITE);
                mDoodle.setColor(new DoodleColor(mDoodleParams.mPaintColor));
                mDoodle.setZoomerScale(mDoodleParams.mZoomerScale);//????????????
                mTouchGestureListener.setSupportScaleItem(mDoodleParams.mSupportScaleItem);
            }
        });

        mTouchGestureListener = new DoodleOnTouchGestureListener(mDoodleView, new DoodleOnTouchGestureListener.ISelectionListener() {
            // save states before being selected

            @Override
            public void onSelectedItem(IDoodle doodle, IDoodleSelectableItem selectableItem, boolean selected) {

            }

            @Override
            public void onCreateSelectableItem(IDoodle doodle, float x, float y) {

            }
        });

        IDoodleTouchDetector detector = new DoodleTouchDetector(getActivity().getApplicationContext(), mTouchGestureListener);
        mDoodleView.setDefaultTouchDetector(detector);

        mDoodle.setIsDrawableOutside(mDoodleParams.mIsDrawableOutside);
        frameLayout.addView(mDoodleView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        mDoodle.setDoodleMinScale(mDoodleParams.mMinScale);
        mDoodle.setDoodleMaxScale(mDoodleParams.mMaxScale);
    }

    /**
     * ???????????????
     */
    private void initPaint() {
        // ????????????
        mDoodleParams = new DoodleParams();
        mDoodleParams.mIsFullScreen = true;
        // ????????????
//        params.mImagePath = list.get(0);
        // ??????????????????
        mDoodleParams.mPaintUnitSize = DoodleView.DEFAULT_SIZE;
        // ????????????
        mDoodleParams.mPaintColor = Color.RED;
        // ??????????????????item
        mDoodleParams.mSupportScaleItem = true;
    }


    @Override
    protected void onEventComming(EventCenter eventCenter) {

    }

    @Override
    protected boolean isBindEventBusHere() {
        return false;
    }

    @Override
    public void onPermissionsGranted(int requestCode, List<String> perms) {

    }


    @AfterPermissionGranted(RC_CAMERA_PERM)
    public void openCamera() {
        String[] perms = {Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE};
        if (EasyPermissions.hasPermissions(mContext, perms)) {
            // Have permission, do the thing!
            cutIcon(6);

        } else {
            // Ask for one permission
            EasyPermissions.requestPermissions(this, "???????????????????????????",
                    RC_CAMERA_PERM, perms);
        }
    }

    @Override
    public void onPermissionsDenied(int requestCode, List<String> perms) {
        if (EasyPermissions.somePermissionPermanentlyDenied(this, perms)) {
            ToastUtils.showLong("???????????????????????????????????????????????????????????????????????????");
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

}
