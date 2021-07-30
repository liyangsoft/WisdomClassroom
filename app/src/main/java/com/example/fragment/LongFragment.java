package com.example.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.base.BaseFragment;
import com.example.eventbus.EventCenter;
import com.example.wisdomclassroom.R;

public class LongFragment extends BaseFragment {


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
        TextView textView = getView().findViewById(R.id.text);
        textView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        textView.setBackgroundColor(Color.parseColor("#00D5FF"));
                        break;
                    case MotionEvent.ACTION_UP:
                        textView.setBackgroundColor(Color.parseColor("#5090B9"));
                        break;
                    default:
                        break;
                }
                return true;
            }
        });
        initId();
    }

    private void initId() {
        EditText editText = find(R.id.editText);
        editText.setFocusableInTouchMode(false);//不可编辑
        editText.setKeyListener(null);//不可粘贴，长按不会弹出粘贴框
        editText.setClickable(false);//不可点击，但是这个效果我这边没体现出来，不知道怎没用
        editText.setFocusable(false);//不可编辑


        RelativeLayout rl1 = find(R.id.rl_1);
        RelativeLayout rl2 = find(R.id.rl_2);
        RelativeLayout rl3 = find(R.id.rl_3);
        RelativeLayout rl4 = find(R.id.rl_4);
        RelativeLayout rl5 = find(R.id.rl_5);
        RelativeLayout rl6 = find(R.id.rl_6);
        RelativeLayout rl7 = find(R.id.rl_7);
        RelativeLayout rl8 = find(R.id.rl_8);
        RelativeLayout rl9 = find(R.id.rl_9);
        RelativeLayout rl0 = find(R.id.rl_0);
        RelativeLayout rlDelete = find(R.id.rl_delete);
        RelativeLayout rlDot = find(R.id.rl_dot);
        RelativeLayout rlStar = find(R.id.rl_star);
        RelativeLayout rlAdd = find(R.id.rl_add);
        rlAdd.setOnClickListener(v -> {

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
    }

    public static void setEdittext(EditText editText, String str) {
        editText.setText(editText.getText().toString() + str);
    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.fragment_long;
    }

    @Override
    protected void onEventComming(EventCenter eventCenter) {

    }

    @Override
    protected boolean isBindEventBusHere() {
        return false;
    }
}
