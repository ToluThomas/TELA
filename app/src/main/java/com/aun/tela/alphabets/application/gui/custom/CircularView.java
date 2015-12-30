package com.aun.tela.alphabets.application.gui.custom;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;

import com.aun.tela.alphabets.R;

import io.meengle.util.Value;

/**
 * Created by Joseph Dalughut on 29/12/15 at 8:16 PM.
 * Project name : Alphabets.
 * Copyright (c) 2015 Meengle. All rights reserved.
 */
public class CircularView extends View {

    Paint paint;
    Paint borderPaint;
    int borderWidth = 0;
    int borderColor = 0xFFFFFFFF;
    int circularColor = 0xFFFFFFFF;

    int canvasSize;

    public CircularView(Context context) {
        super(context);
        init();
    }

    public CircularView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public CircularView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public CircularView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(attrs);
    }

    private void init(){
        init(null);
    }

    private void init(AttributeSet attributeSet){
        paint = new Paint();
        borderPaint = new Paint();
        paint.setAntiAlias(true);
        borderPaint.setAntiAlias(true);
        if(!Value.NULL(attributeSet)) {
            TypedArray array = getResources().obtainAttributes(attributeSet, R.styleable.CircularView);
            circularColor = array.getColor(R.styleable.CircularView_circular_color, 0xFFFFFFFF);
            borderColor = array.getColor(R.styleable.CircularView_border_color, 0xFFEEEEEE);
            borderWidth = array.getDimensionPixelSize(R.styleable.CircularView_border_width, 0);
            if (array.getBoolean(R.styleable.CircularView_shadow, false))
                addShadow();
            if (Value.Same.INTEGER(array.getInteger(R.styleable.CircularView_border_color, -1), 0))
                setBorderColor(com.aun.tela.alphabets.application.util.Color.random());
            if (Value.Same.INTEGER(array.getInteger(R.styleable.CircularView_circular_color, -1), 0))
                setCircularColor(com.aun.tela.alphabets.application.util.Color.random());
            array.recycle();
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvasSize = canvas.getWidth();

        int rad1 = canvasSize / 2;
        int rad2 = rad1 - borderWidth;

        borderPaint.setStyle(Paint.Style.STROKE);
        paint.setStyle(Paint.Style.FILL);
        borderPaint.setColor(borderColor);
        borderPaint.setStrokeWidth((float) borderWidth);
        paint.setColor(circularColor);

        canvas.drawCircle(rad1, rad1, rad2, borderPaint);
        canvas.drawCircle(rad1, rad1, rad2, paint);

    }

    public void addShadow() {
        setLayerType(LAYER_TYPE_SOFTWARE, borderPaint);
        borderPaint.setShadowLayer(8.0f, 0.0f, 4.0f, 0x20000000);
    }

    public void setCircularColor(int circularColor){
        this.circularColor = circularColor;
    }

    public int getCircularColor(){
        return this.circularColor;
    }

    public void setBorderColor(int borderColor){
        this.borderColor = borderColor;
        this.invalidate();
    }

    public void setBorderWidth(int borderWidth){
        this.borderWidth = borderWidth;
        this.requestLayout();
        this.invalidate();
    }

    public int getBorderWidth(){return this.borderWidth;}

    public int getBorderColor(){return this.borderColor;}

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int height = measureHeight(heightMeasureSpec);
        int width = measureWidth(widthMeasureSpec);
        int size = height > width ? height : width;
        setMeasuredDimension(size, size);
    }

    private int measureWidth(int measureSpec) {
        int result = 0;
        int specMode = MeasureSpec.getMode(measureSpec);
        int specSize = MeasureSpec.getSize(measureSpec);

        if (specMode == MeasureSpec.EXACTLY) {
            // The parent has determined an exact size for the child.
            result = specSize;
        } else if (specMode == MeasureSpec.AT_MOST) {
            // The child can be as large as it wants up to the specified size.
            result = specSize;
        } else {
            // The parent has not imposed any constraint on the child.
            result = canvasSize;
        }

        return result;
    }

    private int measureHeight(int measureSpecHeight) {
        int result = 0;
        int specMode = MeasureSpec.getMode(measureSpecHeight);
        int specSize = MeasureSpec.getSize(measureSpecHeight);

        if (specMode == MeasureSpec.EXACTLY) {
            // We were told how big to be
            result = specSize;
        } else if (specMode == MeasureSpec.AT_MOST) {
            // The child can be as large as it wants up to the specified size.
            result = specSize;
        } else {
            // Measure the text (beware: ascent is a negative number)
            result = canvasSize;
        }

        return (result + 2);
    }

}
