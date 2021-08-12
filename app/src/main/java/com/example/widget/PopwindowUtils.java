package com.example.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.ColorDrawable;
import android.os.Handler;
import android.support.v4.widget.PopupWindowCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.ScreenUtils;
import com.blankj.utilcode.util.SizeUtils;
import com.example.base.Constants;
import com.example.entity.ColorEntity;
import com.example.entity.LongEntity;
import com.example.wisdomclassroom.R;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.ArrayList;
import java.util.List;

public class PopwindowUtils {

    private static ImageView ivThin;
    private static ImageView ivMedium;
    private static ImageView ivMax;


    public static void showPop(Context context, RelativeLayout relativeLayout, ColorPenListener listener) {
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_pen, null);
        RecyclerView recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new GridLayoutManager(context, 4));
        List<ColorEntity> colorList = new ArrayList<>();
        colorList.add(new ColorEntity("#192269"));
        colorList.add(new ColorEntity("#FFFFFF"));
        colorList.add(new ColorEntity("#FA8D68"));
        colorList.add(new ColorEntity("#FDE954"));
        colorList.add(new ColorEntity("#1306FF"));
        colorList.add(new ColorEntity("#0180FB"));
        colorList.add(new ColorEntity("#71D53F"));
        colorList.add(new ColorEntity("#CDFFAE"));
        colorList.add(new ColorEntity("#FF0EF8"));
        colorList.add(new ColorEntity("#52C3E0"));
        colorList.add(new ColorEntity("#BBB699"));
        colorList.add(new ColorEntity("#BBB699"));
        recyclerView.setAdapter(new CommonAdapter<ColorEntity>(context, R.layout.item_color, colorList) {

            @SuppressLint("ResourceType")
            @Override
            protected void convert(ViewHolder holder, ColorEntity entity, int position) {
                RelativeLayout relativeLayout = holder.getView(R.id.rl_color);
                ImageView imageView = holder.getView(R.id.image);
                imageView.setBackgroundColor(Color.parseColor(entity.getColor()));

                relativeLayout.setBackground(entity.isFlag() ? context.getResources().getDrawable(R.drawable.shape_color) : context.getResources().getDrawable(R.drawable.shape_null));

                holder.itemView.setOnClickListener(v -> {
                    for (int i = 0; i < colorList.size(); i++) {
                        if (i == position) {
                            colorList.get(i).setFlag(true);
                        } else {
                            colorList.get(i).setFlag(false);
                        }
                    }
                    notifyDataSetChanged();
                    listener.colorSet(entity.getColor());
                });
            }
        });


        //获取PopupWindow中View的宽高
        view.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
        int measuredWidth = view.getMeasuredWidth();
        int measuredHeight = view.getMeasuredHeight();
        PopupWindow popupWindow = new PopupWindow(view, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        popupWindow.setFocusable(true);//popupwindow设置焦点

        popupWindow.setBackgroundDrawable(new ColorDrawable(0xaa000000));//设置背景
        popupWindow.setOutsideTouchable(true);//点击外面窗口消失
        // popupWindow.showAsDropDown(v,0,0);
        //获取点击View的坐标
        int[] location = new int[2];
        relativeLayout.getLocationOnScreen(location);
        popupWindow.showAtLocation(relativeLayout, Gravity.NO_GRAVITY, location[0] + relativeLayout.getWidth() / 2, location[1] - measuredHeight);

        ivThin = view.findViewById(R.id.iv_thin);
        ivMedium = view.findViewById(R.id.iv_medium);
        ivMax = view.findViewById(R.id.iv_max);
        view.findViewById(R.id.iv_thin).setOnClickListener(v -> {
            cutPen(1);
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    listener.penSet(Constants.penThinSize);
                }
            }, 200);
        });
        view.findViewById(R.id.iv_medium).setOnClickListener(v -> {
            cutPen(2);
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    listener.penSet(Constants.penMediumSize);
                }
            }, 200);
        });
        view.findViewById(R.id.iv_max).setOnClickListener(v -> {
            cutPen(3);
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    listener.penSet(Constants.penMaxSize);
                }
            }, 200);
        });

    }

    private static void cutPen(int position) {
        ivThin.setImageResource(position == 1 ? R.drawable.pen_thin_select_icon : R.drawable.pen_thin_icon);
        ivMedium.setImageResource(position == 2 ? R.drawable.pen_medium_select_icon : R.drawable.pen_medium_icon);
        ivMax.setImageResource(position == 3 ? R.drawable.pen_max_select_icon : R.drawable.pen_max_icon);
    }

    public interface ColorPenListener {
        void penSet(int size);

        void colorSet(String color);
    }


    public static void showLongPop(Context context, RelativeLayout linearLayout,RelativeLayout relativeLayout) {
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_long, null);
        MyPopupWindow popupWindow = new MyPopupWindow(view, ViewGroup.LayoutParams.WRAP_CONTENT, ScreenUtils.getScreenHeight()-relativeLayout.getHeight());
        RelativeLayout rl1 = view.findViewById(R.id.rl_1);
        RelativeLayout rl2 = view.findViewById(R.id.rl_2);
        RelativeLayout rl3 = view.findViewById(R.id.rl_3);
        RelativeLayout rl4 = view.findViewById(R.id.rl_4);
        RelativeLayout rl5 = view.findViewById(R.id.rl_5);
        RelativeLayout rl6 = view.findViewById(R.id.rl_6);
        RelativeLayout rl7 = view.findViewById(R.id.rl_7);
        RelativeLayout rl8 = view.findViewById(R.id.rl_8);
        RelativeLayout rl9 = view.findViewById(R.id.rl_9);
        RelativeLayout rl0 = view.findViewById(R.id.rl_0);
        RelativeLayout rlDelete = view.findViewById(R.id.rl_delete);
        RelativeLayout rlDot = view.findViewById(R.id.rl_dot);
        RelativeLayout rlStar = view.findViewById(R.id.rl_star);
        RelativeLayout rlAdd = view.findViewById(R.id.rl_add);
        EditText editText = view.findViewById(R.id.editText);
        TextView textView=view.findViewById(R.id.text);

        textView.setOnClickListener(v -> {
            popupWindow.dismiss();
            //切换界面
            View rtmpView = LayoutInflater.from(context).inflate(R.layout.rtmp_show, null);
            MyPopupWindow rtmpPopupWindow = new MyPopupWindow(rtmpView, ViewGroup.LayoutParams.MATCH_PARENT, ScreenUtils.getScreenHeight()-relativeLayout.getHeight());
            rtmpPopupWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));//设置背景
            rtmpPopupWindow.setFocusable(true);//popupwindow设置焦点
            PopupWindowCompat.showAsDropDown(rtmpPopupWindow, relativeLayout, 0,0, Gravity.TOP);
            MeetingDeal meetingDeal=new MeetingDeal();
            meetingDeal.createMeeting(rtmpView);

            LinearLayout linearLayout1=rtmpView.findViewById(R.id.closeMeeting);
            linearLayout1.setOnClickListener(v1 -> {
                try {
                    meetingDeal.changeToMeet(rtmpView);
                    rtmpPopupWindow.dismiss();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        });
        rlAdd.setOnClickListener(v -> {
            popupWindow.dismiss();
            //切换界面
            View rtmpView = LayoutInflater.from(context).inflate(R.layout.rtmp_show, null);
            MyPopupWindow rtmpPopupWindow = new MyPopupWindow(rtmpView, ViewGroup.LayoutParams.MATCH_PARENT, ScreenUtils.getScreenHeight()-relativeLayout.getHeight());
            rtmpPopupWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));//设置背景
            rtmpPopupWindow.setFocusable(true);//popupwindow设置焦点
            PopupWindowCompat.showAsDropDown(rtmpPopupWindow, relativeLayout, 0,0, Gravity.TOP);
            MeetingDeal meetingDeal=new MeetingDeal();
            meetingDeal.joinMeeting(editText.getText().toString(),rtmpView);

            LinearLayout linearLayout1=rtmpView.findViewById(R.id.closeMeeting);
            linearLayout1.setOnClickListener(v1 -> {
                try {
                    meetingDeal.changeToMeet(rtmpView);
                    rtmpPopupWindow.dismiss();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        });
        rl1.setOnClickListener(v -> {
            setEdittext(editText, "1");
        });
        rl2.setOnClickListener(v -> {
            setEdittext(editText, "2");
        });
        rl3.setOnClickListener(v -> {
            setEdittext(editText, "3");
        });
        rl4.setOnClickListener(v -> {
            setEdittext(editText, "4");
        });
        rl5.setOnClickListener(v -> {
            setEdittext(editText, "5");
        });
        rl6.setOnClickListener(v -> {
            setEdittext(editText, "6");
        });
        rl7.setOnClickListener(v -> {
            setEdittext(editText, "7");
        });
        rl8.setOnClickListener(v -> {
            setEdittext(editText, "8");
        });
        rl9.setOnClickListener(v -> {
            setEdittext(editText, "9");
        });
        rl0.setOnClickListener(v -> {
            setEdittext(editText, "0");
        });
        rlDot.setOnClickListener(v -> {
            setEdittext(editText, ".");
        });
        rlStar.setOnClickListener(v -> {
            setEdittext(editText, "*");
        });
        rlDelete.setOnClickListener(v -> {
            String string = editText.getText().toString();
            if (TextUtils.isEmpty(string)) {
                return;
            }

            editText.setText(string.substring(0, string.length() - 1));
        });
        RecyclerView historyRecyclerView = view.findViewById(R.id.history_recycler);
        historyRecyclerView.setLayoutManager(new LinearLayoutManager(context));
        List<LongEntity> arrayList = new ArrayList<>();
        arrayList.add(new LongEntity("马克思", "今日12:00"));
        arrayList.add(new LongEntity("马克思", "今日12:00"));
        arrayList.add(new LongEntity("马克思", "今日12:00"));
        arrayList.add(new LongEntity("马克思", "今日12:00"));
        arrayList.add(new LongEntity("马克思", "今日12:00"));
        arrayList.add(new LongEntity("马克思", "今日12:00"));
        historyRecyclerView.setAdapter(new CommonAdapter<LongEntity>(context, R.layout.item_long, arrayList) {

            @SuppressLint("ResourceType")
            @Override
            protected void convert(ViewHolder holder, LongEntity entity, int position) {

            }
        });


        RecyclerView makeRecyclerView = view.findViewById(R.id.make_recycler);

        makeRecyclerView.setLayoutManager(new LinearLayoutManager(context));
        List<LongEntity> arrayMakeList = new ArrayList<>();
        arrayMakeList.add(new LongEntity("学术讨论1", "今日12:00"));
        makeRecyclerView.setAdapter(new CommonAdapter<LongEntity>(context, R.layout.item_long, arrayMakeList) {

            @SuppressLint("ResourceType")
            @Override
            protected void convert(ViewHolder holder, LongEntity entity, int position) {

            }
        });

        //获取PopupWindow中View的宽高
        view.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
        //获取PopupWindow中View的宽高
        int measuredWidth = view.getMeasuredWidth();
        int measuredHeight = view.getMeasuredHeight();
        popupWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));//设置背景
        popupWindow.setFocusable(true);//popupwindow设置焦点
        popupWindow.setOutsideTouchable(true);//点击外面窗口消失
        int[] location = new int[2];
//        linearLayout.getLocationOnScreen(location);
//        relativeLayout.getLocationOnScreen(location);
//        popupWindow.showAtLocation(linearLayout, Gravity.NO_GRAVITY, location[0] + linearLayout.getWidth(), location[1]);
//        popupWindow.showAtLocation(relativeLayout, Gravity.NO_GRAVITY, location[0] , location[1] - relativeLayout.getHeight());
//        popupWindow.showAtLocation(relativeLayout,Gravity.NO_GRAVITY,location[0] ,measuredHeight);
//        popupWindow.showAsDropDown(relativeLayout, 0, 0);
      int  offsetY = -(popupWindow.getContentView().getMeasuredHeight()+relativeLayout.getHeight());
        PopupWindowCompat.showAsDropDown(popupWindow, relativeLayout, 0,0, Gravity.TOP);



    }

    public static void setEdittext(EditText editText, String str) {
        editText.setText(editText.getText().toString() + str);
    }
}
