package com.example.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.ColorDrawable;
import android.os.Handler;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;

import com.example.base.Constants;
import com.example.entity.ColorEntity;
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
}
