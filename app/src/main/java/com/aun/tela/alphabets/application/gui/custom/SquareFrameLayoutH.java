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

/**
 * An implementation of {@link FrameLayout} whose width will always matches its height
 */
public class SquareFrameLayoutH extends FrameLayout {

    public SquareFrameLayoutH(Context context) {
        super(context);
    }

    public SquareFrameLayoutH(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SquareFrameLayoutH(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public SquareFrameLayoutH(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);
        setMeasuredDimension(height, height);
    }
}
