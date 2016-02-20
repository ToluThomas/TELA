package com.aun.tela.alphabets.application.gui.fragments;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Canvas;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by Joey on 19/02/16 at 9:02 AM.
 * Project : TELA.
 * Copyright (c) 2016 Meengle. All rights reserved.
 */
public class LetterTraceView extends TextView {


    public LetterTraceView(Context context) {
        super(context);
    }

    public LetterTraceView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public LetterTraceView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public LetterTraceView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        //super.onDraw(canvas);


    }
}
