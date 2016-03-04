package com.aun.tela.alphabets.application.gui.fragments;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.os.Build;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.aun.tela.alphabets.R;

/**
 * Created by Joey on 19/02/16 at 9:02 AM.
 * Project : TELA.
 * Copyright (c) 2016 Meengle. All rights reserved.
 */
public class LetterTraceView extends View{

    int width,  height;
    Bitmap drawingBitmap;
    Canvas drawingCanvas;
    Path drawingPath, circlePath;
    Paint drawingBitmapPaint, paint, penPaint;

    public LetterTraceView(Context context) {
        super(context);

        init();
    }

    public LetterTraceView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public LetterTraceView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public LetterTraceView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    void init(){
        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setDither(true);
        paint.setColor(Color.RED);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeJoin(Paint.Join.ROUND);
        paint.setStrokeCap(Paint.Cap.ROUND);
        paint.setStrokeWidth(getResources().getDimensionPixelSize(R.dimen.text_size_display1));
        drawingPath = new Path();
        drawingBitmapPaint = new Paint(Paint.DITHER_FLAG);
        penPaint = new Paint();
        circlePath = new Path();
        penPaint.setAntiAlias(true);
        penPaint.setColor(0xFFCCCCCC);
        penPaint.setStyle(Paint.Style.STROKE);
        penPaint.setStrokeJoin(Paint.Join.MITER);
        penPaint.setStrokeWidth(2f);
    }

    public void setColor(int color){
        paint.setColor(color);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        drawingBitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        drawingCanvas = new Canvas(drawingBitmap);
    }

    public void erase(){
        onSizeChanged(width, height, width, height);
        invalidate();
        setDrawingCacheEnabled(true);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        width = canvas.getWidth();
        height = canvas.getHeight();
        canvas.drawBitmap(drawingBitmap, 0, 0, drawingBitmapPaint);
        canvas.drawPath(drawingPath, paint);
        canvas.drawPath(circlePath, penPaint);

    }

    private float mX, mY;
    private static final float TOUCH_TOLERANCE = 4;

    private void touch_start(float x, float y) {
        drawingPath.reset();
        drawingPath.moveTo(x, y);
        mX = x;
        mY = y;
    }

    private void touch_move(float x, float y) {
        float dx = Math.abs(x - mX);
        float dy = Math.abs(y - mY);
        if (dx >= TOUCH_TOLERANCE || dy >= TOUCH_TOLERANCE) {
            drawingPath.quadTo(mX, mY, (x + mX) / 2, (y + mY) / 2);
            mX = x;
            mY = y;

            circlePath.reset();
            circlePath.addCircle(mX, mY, 30, Path.Direction.CW);
        }
    }

    private void touch_up() {
        drawingPath.lineTo(mX, mY);
        circlePath.reset();
        // commit the path to our offscreen
        drawingCanvas.drawPath(drawingPath, paint);
        // kill this so we don't double draw
        drawingPath.reset();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float x = event.getX();
        float y = event.getY();

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                touch_start(x, y);
                invalidate();
                break;
            case MotionEvent.ACTION_MOVE:
                touch_move(x, y);
                invalidate();
                break;
            case MotionEvent.ACTION_UP:
                touch_up();
                invalidate();
                break;
        }
        return true;
    }
}
