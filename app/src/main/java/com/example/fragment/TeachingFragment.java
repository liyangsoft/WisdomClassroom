package com.example.fragment;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.media.Image;
import android.opengl.Visibility;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.ConvertUtils;
import com.example.base.BaseFragment;
import com.example.base.Constants;
import com.example.entity.GroupEntity;
import com.example.eventbus.EventCenter;
import com.example.widget.ChildClickableLinearLayout;
import com.example.widget.DialogUtils;
import com.example.widget.PopwindowUtils;
import com.example.wisdomclassroom.R;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import org.greenrobot.eventbus.EventBus;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

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

/**
 * 教学页面
 */
public class TeachingFragment extends BaseFragment {

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

    private boolean isChoice = false;//是否多选
    private int maxChoiceNum = 4;//最大选择
    private int choiceNum = 0;//选择数量

    private boolean isDraw = false;//是否处于画板状态


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

    }

    private void inidRecycler() {
        arrayList = new ArrayList<>();
        arrayList.clear();
        arrayList.add(new GroupEntity("分组一"));
        arrayList.add(new GroupEntity("分组二"));
        arrayList.add(new GroupEntity("分组三"));
        arrayList.add(new GroupEntity("分组四"));
        arrayList.add(new GroupEntity("分组五"));
        arrayList.add(new GroupEntity("分组六"));
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        recyclerView.setAdapter(new CommonAdapter<GroupEntity>(mContext, R.layout.item_group, arrayList) {

            @Override
            protected void convert(ViewHolder holder, GroupEntity groupEntity, int position) {
                holder.setText(R.id.tv_name, groupEntity.getName());
                RelativeLayout relativeLayout = holder.getView(R.id.rl_bg);
                relativeLayout.setBackgroundResource(groupEntity.isCheck() ? R.drawable.shape_group_select : R.drawable.shape_group_normal);

                holder.itemView.setOnClickListener(v -> {
                    if (choiceNum >= 4) {
                        showToast(mContext, "最多选择4个");
                        return;
                    }
                    for (int i = 0; i < arrayList.size(); i++) {

                        if (isChoice) {
                            if (position == i) {
                                choiceNum++;
                                arrayList.get(i).setCheck(true);
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
                });

            }
        });
    }

    private void setClick() {
        llGroup.setOnClickListener(v -> {
            inidRecycler();
            cutIcon(1);
        });
        rlBroadcast.setOnClickListener(v -> {
            cutIcon(2);
        });
        rlContrast.setOnClickListener(v -> {
            inidRecycler();
            cutIcon(3);
        });
        rlSpeak.setOnClickListener(v -> {
            cutIcon(4);
        });
        rlNotebook.setOnClickListener(v -> {
            cutIcon(5);
        });
        rlTeacher.setOnClickListener(v -> {
            cutIcon(6);
        });
        rlWifi.setOnClickListener(v -> {
            cutIcon(7);
        });
        rlLong.setOnClickListener(v -> {
            cutIcon(8);
        });
        rlMore.setOnClickListener(v -> {
            cutIcon(9);
        });

        //电子白板
        llPlank.setOnClickListener(v -> {
            isDraw = true;
            cutDrawing(1);
            noClickExceptDrawing();
            EventBus.getDefault().post(new EventCenter<>(Constants.EVENT_VIEWPAGER, false));
            opendrawing();

        });

        //圈点
        llCircle.setOnClickListener(v -> {
            isDraw = true;
            cutDrawing(2);
            noClickExceptDrawing();
            EventBus.getDefault().post(new EventCenter<>(Constants.EVENT_VIEWPAGER, false));
        });

        //画笔
        rlPen.setOnClickListener(v -> {
            if (!isDraw) {
                return;
            }
            cutPen(1);
            PopwindowUtils.showPop(mContext, rlPen, new PopwindowUtils.ColorPenListener() {
                @Override
                public void penSet(int size) {
                    if (null!=mDoodle){
                        mDoodle.setSize(size);
                    }

                }

                @Override
                public void colorSet(String color) {
                    if (null!=mDoodle){
                        mDoodle.setColor(new DoodleColor(Color.parseColor(color)));
                    }

                }
            });


        });
        //橡皮擦
        rlEraser.setOnClickListener(v -> {
            if (!isDraw) {
                return;
            }
            cutPen(2);
        });
        //上一步
        rlBack.setOnClickListener(v -> {
            if (!isDraw) {
                return;
            }
            cutPen(3);
            if (frameLayout.getVisibility() == View.VISIBLE && mDoodle != null) {
                mDoodle.undo();
            }
        });
        //下一步
        rlGo.setOnClickListener(v -> {
            if (!isDraw) {
                return;
            }
            cutPen(4);
            if (frameLayout.getVisibility() == View.VISIBLE && mDoodle != null) {
                mDoodle.redo(1);

            }
        });
        //增加
        rlAdd.setOnClickListener(v -> {
            if (!isDraw) {
                return;
            }
            cutPen(5);
        });
        //上一页
        rlLast.setOnClickListener(v -> {
            if (!isDraw) {
                return;
            }
            cutPen(6);
        });
        //下一页
        rlNext.setOnClickListener(v -> {
            if (!isDraw) {
                return;
            }
            cutPen(7);
        });
        //矩阵
        rlOrthogon.setOnClickListener(v -> {
            if (!isDraw) {
                return;
            }
            cutPen(8);
        });

        //上课
        button.setOnClickListener(v -> {
            button.setText(button.getText().toString().equals("上课") ? "下课" : "上课");

        });

        //退出画板
        rlExit.setOnClickListener(v -> {
            isDraw = false;
            if (null != mDoodle) {
                mDoodle.clear();
            }

            allowClickExceptDrawing();
            cutPen(0);
            cutDrawing(0);
            EventBus.getDefault().post(new EventCenter<>(Constants.EVENT_VIEWPAGER, true));


        });
        //隐藏工具栏
        rlHide.setOnClickListener(v -> {
            showHideUtil(false);

        });
        //显示工具栏
        ivRecover.setOnClickListener(v -> {
            showHideUtil(true);

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

    }

    /**
     * 除画板工具外不可点击
     */
    private void noClickExceptDrawing() {
        frameLayout.setVisibility(View.VISIBLE);
        rlExit.setVisibility(View.VISIBLE);
        llLeftUtil.setChildClickable(false);
        button.setEnabled(false);

    }

    /**
     * 工具可点击
     */
    private void allowClickExceptDrawing() {
        frameLayout.setVisibility(View.GONE);
        rlExit.setVisibility(View.INVISIBLE);
        llLeftUtil.setChildClickable(true);
        button.setEnabled(true);

    }

    /**
     * U显示/隐藏工具栏
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
     * 左侧工具
     *
     * @param position
     */
    private void cutIcon(int position) {
        llGroup.setBackgroundColor(position == 1 ? Color.parseColor("#0B70E0") : Color.parseColor("#484A6D"));
        rlBroadcast.setBackgroundColor(position == 2 ? Color.parseColor("#0B70E0") : Color.parseColor("#484A6D"));
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
    }

    /**
     * 底方画板  0全不选中
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
     * 底部画笔等配置  0全不选中
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
     * 开启画板
     */
    private void opendrawing() {

        ViewTreeObserver viewTreeObserver = frameLayout.getViewTreeObserver();
        viewTreeObserver.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                frameLayout.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                //获取绘制完之后的宽度
                Bitmap mBitmap = Bitmap.createBitmap(frameLayout.getMeasuredWidth(), frameLayout.getMeasuredHeight(), Bitmap.Config.ARGB_8888);//创建一个新空白位图
                Canvas canvasBg = new Canvas(mBitmap);
                canvasBg.drawColor(Color.WHITE);
                mDoodle = mDoodleView = new DoodleView(mContext, mBitmap, true, new IDoodleListener() {
                    @Override
                    public void onSaved(IDoodle doodle, Bitmap doodleBitmap, Runnable callback) {


                    }

                    @Override
                    public void onReady(IDoodle doodle) {
                        // 设置初始值
                        mDoodle.setSize(5);
                        // 选择画笔
                        mDoodle.setPen(DoodlePen.BRUSH);
                        mDoodle.setShape(DoodleShape.HAND_WRITE);
                        mDoodle.setColor(new DoodleColor(mDoodleParams.mPaintColor));
                        mDoodle.setZoomerScale(mDoodleParams.mZoomerScale);//放大倍数
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
        });


    }

    /**
     * 初始化画笔
     */
    private void initPaint() {
        // 涂鸦参数
        mDoodleParams = new DoodleParams();
        mDoodleParams.mIsFullScreen = true;
        // 图片路径
//        params.mImagePath = list.get(0);
        // 初始画笔大小
        mDoodleParams.mPaintUnitSize = DoodleView.DEFAULT_SIZE;
        // 画笔颜色
        mDoodleParams.mPaintColor = Color.RED;
        // 是否支持缩放item
        mDoodleParams.mSupportScaleItem = true;
    }


    @Override
    protected int getContentViewLayoutID() {
        return R.layout.fragment_teaching;
    }

    @Override
    protected void onEventComming(EventCenter eventCenter) {

    }

    @Override
    protected boolean isBindEventBusHere() {
        return false;
    }
}
