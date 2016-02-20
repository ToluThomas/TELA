package com.aun.tela.alphabets.application.gui.custom;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import android.util.AttributeSet;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;

import com.aun.tela.alphabets.R;

import java.util.Random;

/**
 * Created by Joey on 14/02/16 at 11:41 AM.
 * Project : TELA.
 * Copyright (c) 2016 Meengle. All rights reserved.
 */
public class BunnyImageView extends ImageView {

    boolean animate, animating;
    int interval = 10000;
    ValueAnimator animator;
    boolean firstAttach = false;

    public BunnyImageView(Context context) {
        super(context);
        init(null);
    }

    public BunnyImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public BunnyImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public BunnyImageView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(attrs);
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        if(firstAttach)
            return;
        firstAttach = true;
        stopAnimationIfAble();
        reschedule();
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        stopAnimationIfAble();
    }

    void init(AttributeSet attr){
        if(isInEditMode() || attr==null)
            return;
        TypedArray array = getContext().obtainStyledAttributes(attr, R.styleable.BunnyImageView);
        animate = array.getBoolean(R.styleable.BunnyImageView_animate, false);
        interval = array.getInt(R.styleable.BunnyImageView_interval, 10000);
        array.recycle();
    }

    void startAnimationIfAble(){
        //Log.d("Animating bunny");
        if(!animate) return;
        //Log.d("Bunny animatable");
        this.setLayerType(LAYER_TYPE_HARDWARE, null);
        animating = true;
        stopAnimationIfAble();
        float ty = getTranslationY();
        //Log.d("Current translation : "+ty);
        Random rand = new Random();
        int zero = rand.nextBoolean() ? 0 : rand.nextInt(getHeight() / 4);
        int vis = rand.nextBoolean() ? getHeight() : getHeight() / 3 + (rand.nextInt( getHeight() - getHeight() / 3));
        animator = ObjectAnimator.ofFloat(this, "translationY", ty, ty > (getHeight() / 2) ? zero : vis);
        animator.setInterpolator(rand.nextBoolean() ? new DecelerateInterpolator() : new AccelerateInterpolator());
        int min = 1000;
        int max = 5000;
        int random = min + rand.nextInt( max - min);
        //Log.d("Duration is "+random);
        animator.setDuration(random);
        animator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                stopAnimationIfAble();
                reschedule();
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        animator.start();
    }

    void stopAnimationIfAble(){
        this.setLayerType(LAYER_TYPE_NONE, null);
        if(animator != null && animator.isRunning()){
            animator.cancel();
            animator = null;
        }
        animating = false;
    }

    void reschedule(){
        //Log.d("Scheduling animation");
        if(animate){
            postDelayed(new Runnable() {
                @Override
                public void run() {
                    startAnimationIfAble();
                }
            }, interval );
        }
    }

}
