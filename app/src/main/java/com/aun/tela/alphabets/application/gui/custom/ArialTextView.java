package com.aun.tela.alphabets.application.gui.custom;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Typeface;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by Joey on 07/02/16 at 10:13 PM.
 * Project : TELA.
 * Copyright (c) 2016 Meengle. All rights reserved.
 */
public class ArialTextView extends TextView {
    public ArialTextView(Context context) {
        super(context);
        init();
    }

    public ArialTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public ArialTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public ArialTextView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    void init(){
        if(isInEditMode()) return;
        Typeface typeface = Typeface.createFromAsset(getResources().getAssets(), "arial.ttf");
        this.setTypeface(typeface);
        this.setShadowLayer(8.0f, 0.0f, 4.0f, 0x20000000);
    }
}
