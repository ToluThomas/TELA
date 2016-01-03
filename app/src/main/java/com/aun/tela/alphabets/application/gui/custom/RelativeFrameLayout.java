package com.aun.tela.alphabets.application.gui.custom;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.FrameLayout;

/**
 * Created by Joseph Dalughut on 29/12/15 at 10:37 PM.
 * Project name : Alphabets.
 * Copyright (c) 2015 Meengle. All rights reserved.
 */
public class RelativeFrameLayout extends FrameLayout {
    public RelativeFrameLayout(Context context) {
        super(context);
    }

    public RelativeFrameLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public RelativeFrameLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public RelativeFrameLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int size = Math.max(MeasureSpec.getSize(heightMeasureSpec), MeasureSpec.getSize(widthMeasureSpec));
        setMeasuredDimension(size, size);
    }
}
