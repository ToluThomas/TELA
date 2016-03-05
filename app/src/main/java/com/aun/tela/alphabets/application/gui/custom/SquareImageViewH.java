package com.aun.tela.alphabets.application.gui.custom;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.ImageView;

import com.aun.tela.alphabets.application.util.Log;

/**
 * Created by Joey on 18/02/16 at 7:54 PM.
 * Project : TELA.
 * Copyright (c) 2016 Meengle. All rights reserved.
 */
public class SquareImageViewH extends ImageView {
    public SquareImageViewH(Context context) {
        super(context);
    }

    public SquareImageViewH(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SquareImageViewH(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public SquareImageViewH( Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        Log.d("OnMeasureCalled SquareImageView");
        int width = MeasureSpec.getSize(heightMeasureSpec);
        setMeasuredDimension(width, width);
    }
}
