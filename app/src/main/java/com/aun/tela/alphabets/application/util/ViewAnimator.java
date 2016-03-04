package com.aun.tela.alphabets.application.util;

import android.animation.Animator;
import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.animation.ValueAnimator;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;

import com.aun.tela.alphabets.application.generic.Collector;
import com.facebook.rebound.Spring;
import com.facebook.rebound.SpringConfig;
import com.facebook.rebound.SpringListener;
import com.facebook.rebound.SpringSystem;

import io.meengle.util.Value;

/**
 * Created by Joseph Dalughut on 29/12/15 at 6:59 PM.
 * Project name : Alphabets.
 * Copyright (c) 2015 Meengle. All rights reserved.
 */

/**
 * A class for simple Animations using static methods
 */
public class ViewAnimator {

    public static final class Constants {

        public static final double DEFAULT_TENSION = 800; //default tension for spring.
        public static final double DEFAULT_DAMPER = 40; //default friction for spring.

    }

    /**
     * Make a view execute a springy animation when touched.
     * @param view the view to animate
     * @param onClickListener the onClickListener to be notified when this view is touched
     *                        @see http://facebook.github.io/rebound/
     */
    public static void springify(View view, View.OnClickListener onClickListener){
        springify(view, onClickListener, Constants.DEFAULT_TENSION, Constants.DEFAULT_DAMPER);
    }

    /**
     *
     * Make a view execute a springy animation when touched.
     * @param view the view to animate
     * @param onClickListener the listener to be notified when this view is touched
     * @param tension the tension for this spring animation
     * @param damper the friction for this spring animation
     *               @see http://facebook.github.io/rebound/
     */
    public static void springify(final View view, final View.OnClickListener onClickListener, double tension, double damper){

        view.setClickable(true);
        SpringSystem springSystem = SpringSystem.create();
        final Spring spring = springSystem.createSpring();
        SpringConfig config = new SpringConfig(tension, damper);
        spring.setSpringConfig(config);
        final SpringListener listener = new SpringListener() {
            @Override
            public void onSpringUpdate(Spring spring) {
                float value = (float) spring.getCurrentValue();
                float scale = 1f - (value * 0.5f);
                view.setScaleX(scale);
                view.setScaleY(scale);
            }

            @Override
            public void onSpringAtRest(Spring spring) {

            }

            @Override
            public void onSpringActivate(Spring spring) {

            }

            @Override
            public void onSpringEndStateChange(Spring spring) {

            }
        };

        view.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(final View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        spring.setEndValue(1f);
                        break;
                    case MotionEvent.ACTION_UP:
                        spring.setEndValue(0f);
                        v.post(new Runnable() {
                            @Override
                            public void run() {
                                if(onClickListener!=null)
                                onClickListener.onClick(v);
                            }
                        });
                        break;
                    case MotionEvent.ACTION_CANCEL:
                        spring.setEndValue(0f);
                        break;
                }
                return false;
            }
        });
        spring.addListener(listener);
    }


    public static void springifyScalable(final View view, final View.OnClickListener onClickListener){

        SpringSystem springSystem = SpringSystem.create();
        final Spring spring = springSystem.createSpring();
        SpringConfig config = new SpringConfig(Constants.DEFAULT_TENSION, Constants.DEFAULT_DAMPER);
        spring.setSpringConfig(config);
        final SpringListener listener = new SpringListener() {
            @Override
            public void onSpringUpdate(Spring spring) {
                float value = (float) spring.getCurrentValue();
                float scale = 1f - (value * 0.5f);
                view.setScaleX(scale);
                view.setScaleY(scale);
            }

            @Override
            public void onSpringAtRest(Spring spring) {

            }

            @Override
            public void onSpringActivate(Spring spring) {

            }

            @Override
            public void onSpringEndStateChange(Spring spring) {

            }
        };

        view.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(final View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        spring.setEndValue(1f);
                        break;
                    case MotionEvent.ACTION_UP:
                        if(!Value.NULL(onClickListener)){
                            if(!spring.isAtRest())
                                spring.setAtRest();
                            v.post(new Runnable() {
                                @Override
                                public void run() {
                                    onClickListener.onClick(v);
                                }
                            });
                        }else{
                            spring.setEndValue(0f);
                        }
                        break;
                    case MotionEvent.ACTION_CANCEL:
                        spring.setEndValue(0f);
                        break;
                }
                return false;
            }
        });
        spring.addListener(listener);
    }

    /**
     * Animate a view periodically going up and down
     * @param v the view to animate
     * @param offset the offset to and from which this view would be moved
     * @param startDelay
     * @param duration
     */
    public static void upDownify(final View v, float offset, long startDelay, long duration){
        float ty = v.getTranslationY();
        //PropertyValuesHolder x = PropertyValuesHolder.ofFloat("translationX", tx, tx + offset);
        PropertyValuesHolder y = PropertyValuesHolder.ofFloat("translationY", ty, ty + offset);
        ValueAnimator animator = ObjectAnimator.ofPropertyValuesHolder(v, y);
        animator.setRepeatMode(ValueAnimator.REVERSE);
        animator.setRepeatCount(ValueAnimator.INFINITE);
        animator.setInterpolator(new AccelerateDecelerateInterpolator());
        animator.setStartDelay(startDelay);
        animator.setDuration(duration);
        v.setLayerType(View.LAYER_TYPE_HARDWARE, null);
        animator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                v.setLayerType(View.LAYER_TYPE_NONE, null);
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

    public static void leftRightify(final View v, float offset, long startDelay, long duration){
        float ty = v.getTranslationX();
        //PropertyValuesHolder x = PropertyValuesHolder.ofFloat("translationX", tx, tx + offset);
        PropertyValuesHolder y = PropertyValuesHolder.ofFloat("translationX", ty, ty + offset);
        ValueAnimator animator = ObjectAnimator.ofPropertyValuesHolder(v, y);
        animator.setRepeatMode(ValueAnimator.REVERSE);
        animator.setRepeatCount(ValueAnimator.INFINITE);
        animator.setInterpolator(new AccelerateDecelerateInterpolator());
        animator.setStartDelay(startDelay);
        animator.setDuration(duration);
        v.setLayerType(View.LAYER_TYPE_HARDWARE, null);
        animator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                v.setLayerType(View.LAYER_TYPE_NONE, null);
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


    /**
     * Animate a view by fading it out of visibility
     * @param v the view to fade out
     * @param startDelay
     * @param duration
     * @return
     */
    public static ValueAnimator fadeOut(final View v, long startDelay, long duration){
        ValueAnimator animator = ObjectAnimator.ofFloat(v, "alpha", v.getAlpha(), 0f);
        animator.setStartDelay(startDelay);
        animator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                v.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        animator.setDuration(duration);
        animator.setInterpolator(new DecelerateInterpolator());
        v.setLayerType(View.LAYER_TYPE_HARDWARE, null);
        animator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                v.setLayerType(View.LAYER_TYPE_NONE, null);
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        animator.start();
        return animator;
    }

    /**
     * Animate a view by fading it into visibility
     * @param v the view to be faded in
     * @param startDelay
     * @param duration
     * @return
     */
    public static ValueAnimator fadeIn(final View v, long startDelay, long duration){
        v.setAlpha(0);
        v.setVisibility(View.VISIBLE);
        ValueAnimator animator = ObjectAnimator.ofFloat(v, "alpha", 0f, 1f);
        animator.setStartDelay(startDelay);
        animator.setDuration(duration);
        animator.setInterpolator(new AccelerateInterpolator());
        v.setLayerType(View.LAYER_TYPE_HARDWARE, null);
        animator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                v.setLayerType(View.LAYER_TYPE_NONE, null);
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        animator.start();
        return animator;
    }

    /**
     * Animate a view by 'popping' it into visibility
     * @param v the view to be popped into visibility
     * @param startDelay
     * @param duration
     * @return
     */
    @Deprecated
    public static ValueAnimator popIn_(final View v, long startDelay, long duration){
        PropertyValuesHolder a = PropertyValuesHolder.ofFloat("alpha", v.getAlpha(), 1f);
        PropertyValuesHolder x = PropertyValuesHolder.ofFloat("scaleX", v.getScaleX(), 1f);
        PropertyValuesHolder y = PropertyValuesHolder.ofFloat("scaleY", v.getScaleY(), 1f);
        ValueAnimator animator = ObjectAnimator.ofPropertyValuesHolder(v, a, x, y);
        animator.setStartDelay(startDelay);
        animator.setDuration(duration);
        animator.setInterpolator(new AccelerateInterpolator());
        v.setLayerType(View.LAYER_TYPE_HARDWARE, null);
        animator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                v.setLayerType(View.LAYER_TYPE_NONE, null);
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        animator.start();
        return animator;
    }

    /**
     * Animate a view by 'pooping' it out of visibility
     * @param v the view to be popped out of visibility
     * @param startDelay
     * @param duration
     * @return
     */
    @Deprecated
    public static ValueAnimator popOut_(final View v, long startDelay, long duration){
        PropertyValuesHolder a = PropertyValuesHolder.ofFloat("alpha", v.getAlpha(), 0f);
        PropertyValuesHolder x = PropertyValuesHolder.ofFloat("scaleX", v.getScaleX(), 0f);
        PropertyValuesHolder y = PropertyValuesHolder.ofFloat("scaleY", v.getScaleY(), 0f);
        ValueAnimator animator = ObjectAnimator.ofPropertyValuesHolder(v, a, x, y);
        animator.setStartDelay(startDelay);
        animator.setDuration(duration);
        v.setLayerType(View.LAYER_TYPE_HARDWARE, null);
        animator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                v.setLayerType(View.LAYER_TYPE_NONE, null);
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        animator.setInterpolator(new DecelerateInterpolator());
        animator.start();
        return animator;
    }

    public static ValueAnimator popInZero(final View v, long startDelay, long duration){
        v.setClickable(true);
        v.setAlpha(0f);
        v.setVisibility(View.VISIBLE);
        PropertyValuesHolder a = PropertyValuesHolder.ofFloat("alpha", 0f, 1f);
        PropertyValuesHolder x = PropertyValuesHolder.ofFloat("scaleX", 0f, 1f);
        PropertyValuesHolder y = PropertyValuesHolder.ofFloat("scaleY", 0f, 1f);
        ValueAnimator animator = ObjectAnimator.ofPropertyValuesHolder(v, a, x, y);
        animator.setStartDelay(startDelay);
        v.setLayerType(View.LAYER_TYPE_HARDWARE, null);
        animator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                v.setLayerType(View.LAYER_TYPE_NONE, null);
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        animator.setDuration(duration);
        animator.setInterpolator(new AccelerateInterpolator());
        v.setEnabled(true);
        animator.start();
        return animator;
    }

    public static ValueAnimator popInZero(final View v, float scaleX, float scaleY, long startDelay, long duration){
        v.setClickable(true);
        v.setAlpha(0f);
        v.setVisibility(View.VISIBLE);
        PropertyValuesHolder a = PropertyValuesHolder.ofFloat("alpha", 0f, 1f);
        PropertyValuesHolder x = PropertyValuesHolder.ofFloat("scaleX", 0f, scaleX);
        PropertyValuesHolder y = PropertyValuesHolder.ofFloat("scaleY", 0f, scaleY);
        ValueAnimator animator = ObjectAnimator.ofPropertyValuesHolder(v, a, x, y);
        animator.setStartDelay(startDelay);
        v.setLayerType(View.LAYER_TYPE_HARDWARE, null);
        animator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                v.setLayerType(View.LAYER_TYPE_NONE, null);
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        animator.setDuration(duration);
        animator.setInterpolator(new AccelerateInterpolator());
        v.setEnabled(true);
        animator.start();
        return animator;
    }

    public static ValueAnimator popOutZero(final View v, long startDelay, long duration){
        v.setClickable(false);
        v.setAlpha(1f);
        v.setVisibility(View.VISIBLE);
        PropertyValuesHolder a = PropertyValuesHolder.ofFloat("alpha", 1f, 0f);
        PropertyValuesHolder x = PropertyValuesHolder.ofFloat("scaleX", 1f, 0f);
        PropertyValuesHolder y = PropertyValuesHolder.ofFloat("scaleY", 1f, 0f);
        ValueAnimator animator = ObjectAnimator.ofPropertyValuesHolder(v, a, x, y);
        animator.setStartDelay(startDelay);
        animator.setDuration(duration);
        animator.setInterpolator(new DecelerateInterpolator());
        v.setLayerType(View.LAYER_TYPE_HARDWARE, null);
        animator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                v.setLayerType(View.LAYER_TYPE_NONE, null);
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        v.setEnabled(false);
        animator.start();
        return animator;
    }

    /**
     * Change the color of a view by animating it
     * @param v
     * @param property the color property to change
     * @param startDelay
     * @param duration
     * @param from
     * @param to
     * @return
     */
    public static ValueAnimator color(final View v, String property, long startDelay, long duration, int from, int to){
        ValueAnimator animator = ObjectAnimator.ofInt(v, property, from, to);
        animator.setEvaluator(new ArgbEvaluator());
        animator.setDuration(duration);
        animator.setStartDelay(startDelay);
        v.setLayerType(View.LAYER_TYPE_HARDWARE, null);
        animator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                v.setLayerType(View.LAYER_TYPE_NONE, null);
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        animator.start();
        return animator;
    }

    /**
     * Animate a view by doing a springy pop animation.
     * @param view the view to be animated
     * @param finishCollector
     */
    public static void pop(final View view, final Collector<View> finishCollector){
        SpringSystem springSystem = SpringSystem.create();
        final Spring spring = springSystem.createSpring();
        SpringConfig config = new SpringConfig(400, 10);
        spring.setSpringConfig(config);
        final SpringListener listener = new SpringListener() {
            @Override
            public void onSpringUpdate(Spring spring) {
                float value = (float) spring.getCurrentValue();
                float scale = 1f - (value * 0.5f);
                view.setScaleX(scale);
                view.setScaleY(scale);
            }

            @Override
            public void onSpringAtRest(Spring spring) {
                spring.removeAllListeners();
                spring.addListener(new SpringListener() {
                    @Override
                    public void onSpringUpdate(Spring spring) {
                        float value = (float) spring.getCurrentValue();
                        float scale = 1f - (value * 0.5f);
                        view.setScaleX(scale);
                        view.setScaleY(scale);
                    }

                    @Override
                    public void onSpringAtRest(Spring spring) {
                        finishCollector.collect(view);
                    }

                    @Override
                    public void onSpringActivate(Spring spring) {

                    }

                    @Override
                    public void onSpringEndStateChange(Spring spring) {

                    }
                });
                spring.setEndValue(0f);
            }

            @Override
            public void onSpringActivate(Spring spring) {

            }

            @Override
            public void onSpringEndStateChange(Spring spring) {

            }
        };
        spring.addListener(listener);
        spring.setEndValue(1f);
    }


}
