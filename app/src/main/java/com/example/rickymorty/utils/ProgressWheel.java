package com.example.rickymorty.utils;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.rickymorty.R;

public class ProgressWheel extends LinearLayout {
    private TextView title;

    public ProgressWheel(Context context) {
        super(context);
        init();
        this.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return true;
            }
        });
    }

    public ProgressWheel(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public ProgressWheel(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    private void init() {
        inflate(getContext(), R.layout.progress_wheel, this);
        this.title = (TextView)findViewById(R.id.title_view);

    }
    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        Log.d("ERROR", "onInterceptTouchEvent");
        return true;
    }
    public void setTitleText(String titleText) {
        if(this.title != null) {
            this.title.setText(titleText);
            this.title.setVisibility(View.VISIBLE);
        }
    }
    public void hideTitleView() {
        if(this.title != null) {
            this.title.setVisibility(View.GONE);
        }
    }
}
