package com.example.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.ImageView;

import com.example.wisdomclassroom.R;

public class PressImageView extends android.support.v7.widget.AppCompatImageView {

    private Context context;
    private TypedArray typedArray;
    private int clickImage;
    private int upImage;

    public PressImageView(Context context) {
        super(context);
        this.context = context;
//        init();
    }

    public PressImageView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        typedArray = context.obtainStyledAttributes(attrs, R.styleable.PressImageView);
        clickImage = typedArray.getResourceId(R.styleable.PressImageView_pressImage, 0);
        upImage = typedArray.getResourceId(R.styleable.PressImageView_upImage, 0);

    }

    public PressImageView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;

        typedArray = context.obtainStyledAttributes(attrs, R.styleable.PressImageView);
        clickImage = typedArray.getResourceId(R.styleable.PressImageView_pressImage, 0);
        upImage = typedArray.getResourceId(R.styleable.PressImageView_upImage, 0);

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                setImageResource(clickImage);
                break;
            case MotionEvent.ACTION_UP:
                setImageResource(upImage);
                break;
            default:
                break;
        }
        return true;
    }

}
