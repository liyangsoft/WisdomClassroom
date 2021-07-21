package com.example.widget;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RelativeLayout;

import com.example.wisdomclassroom.R;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.ArrayList;
import java.util.List;

public class DialogUtils {
    public static Dialog ShowDialog(Context context) {
        final Dialog dialog = new android.app.Dialog(context, R.style.DialogStyle);
        dialog.setCancelable(true);
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_pen, null);
        dialog.setContentView(view);
        RecyclerView recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new GridLayoutManager(context, 4));
        List<String> colorList = new ArrayList<>();
        colorList.add("#192269");
        colorList.add("#FFFFFF");
        colorList.add("#FA8D68");
        colorList.add("#FDE954");
        colorList.add("#1306FF");
        colorList.add("#0180FB");
        colorList.add("#71D53F");
        colorList.add("#CDFFAE");
        colorList.add("#FF0EF8");
        colorList.add("#52C3E0");
        colorList.add("#BBB699");
        colorList.add("#BBB699");
        recyclerView.setAdapter(new CommonAdapter<String>(context, R.layout.item_color, colorList) {

            @Override
            protected void convert(ViewHolder holder, String s, int position) {
                RelativeLayout relativeLayout = holder.getView(R.id.rl_color);
                relativeLayout.setBackgroundColor(Color.parseColor(s));
            }
        });


        dialog.show();
        return dialog;
    }
}
