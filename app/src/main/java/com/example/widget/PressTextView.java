package com.example.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.wisdomclassroom.R;

import java.util.logging.Handler;

public class PressTextView extends android.support.v7.widget.AppCompatTextView {

    private Context context;
    private TypedArray typedArray;
    private int clickImage;
    private int upImage;

    public PressTextView(Context context) {
        super(context);
    }

    public PressTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        typedArray = context.obtainStyledAttributes(attrs, R.styleable.PressImageView);
        clickImage = typedArray.getResourceId(R.styleable.PressImageView_pressImage, 0);
        upImage = typedArray.getResourceId(R.styleable.PressImageView_upImage, 0);
    }

    public PressTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        typedArray = context.obtainStyledAttributes(attrs, R.styleable.PressImageView);
        clickImage = typedArray.getResourceId(R.styleable.PressImageView_pressImage, 0);
        upImage = typedArray.getResourceId(R.styleable.PressImageView_upImage, 0);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                setBackgroundDrawable(getResources().getDrawable(clickImage));
                this.getHandler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        setBackgroundDrawable(getResources().getDrawable(upImage));
                    }
                },100);
                break;
            case MotionEvent.ACTION_UP:
                setBackgroundDrawable(getResources().getDrawable(upImage));
                break;
            default:
                break;
        }
        return false;
    }
}
