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
 * This implementation of {@link FrameLayout}'s height is always same as its width
 */
public class SquareFrameLayoutW extends FrameLayout {
    public SquareFrameLayoutW(Context context) {
        super(context);
    }

    public SquareFrameLayoutW(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SquareFrameLayoutW(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public SquareFrameLayoutW(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width = MeasureSpec.getSize(widthMeasureSpec);
        setMeasuredDimension(width, width);
    }
}
