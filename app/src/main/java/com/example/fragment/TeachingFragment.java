package com.example.fragment;

import android.graphics.Color;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.base.BaseFragment;
import com.example.entity.GroupEntity;
import com.example.eventbus.EventCenter;
import com.example.wisdomclassroom.R;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.ArrayList;
import java.util.List;

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

    private boolean isChoice = false;//是否多选
    private int maxChoiceNum = 4;//最大选择
    private int choiceNum = 0;//选择数量
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
            cutDrawing(1);
        });

        //圈点
        llCircle.setOnClickListener(v -> {
            cutDrawing(2);
        });

        //画笔
        ivPen.setOnClickListener(v -> {
            cutPen(1);
        });
        //橡皮擦
        ivEraser.setOnClickListener(v -> {
            cutPen(2);
        });
        //矩阵
        ivOrthogon.setOnClickListener(v -> {
            cutPen(3);
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

        ivPen = find(R.id.iv_pen);
        ivEraser = find(R.id.iv_eraser);
        ivOrthogon = find(R.id.iv_orthogon);

        frameLayout = find(R.id.doodle_container);

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
     * 底方画板
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
     * 底部画笔等配置
     */
    private void cutPen(int position) {
        ivPen.setImageResource(position == 1 ? R.drawable.blue_pen_icon : R.drawable.white_pen_icon);
        ivEraser.setImageResource(position == 2 ? R.drawable.blue_eraser_icon : R.drawable.white_eraser_icon);
        ivOrthogon.setImageResource(position == 3 ? R.drawable.blue_orthogon_icon : R.drawable.white_orthogon_icon);
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
