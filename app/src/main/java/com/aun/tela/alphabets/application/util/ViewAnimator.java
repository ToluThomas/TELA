package com.aun.tela.alphabets.application.util;
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

public class ViewAnimator {

    public static final class Constants {

        public static final double DEFAULT_TENSION = 800;
        public static final double DEFAULT_DAMPER = 40;

    }

    public static void springify(View view, View.OnClickListener listener){
        springify(view, listener, Constants.DEFAULT_TENSION, Constants.DEFAULT_DAMPER);
    }

    public static void springify(final View view, final View.OnClickListener clicklistener, double tension, double damper){

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
                        if(!Value.NULL(clicklistener)){
                            v.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    clicklistener.onClick(v);
                                }
                            }, 50);
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

    public static void upDownify(View v, float extra, long startDelay, long duration){
        float ty = v.getTranslationY();
        //PropertyValuesHolder x = PropertyValuesHolder.ofFloat("translationX", tx, tx + extra);
        PropertyValuesHolder y = PropertyValuesHolder.ofFloat("translationY", ty, ty + extra);
        ValueAnimator animator = ObjectAnimator.ofPropertyValuesHolder(v, y);
        animator.setRepeatMode(ValueAnimator.REVERSE);
        animator.setRepeatCount(ValueAnimator.INFINITE);
        animator.setInterpolator(new AccelerateDecelerateInterpolator());
        animator.setStartDelay(startDelay);
        animator.setDuration(duration);
        animator.start();
    }

    public static ValueAnimator fadeOut(View v, long startDelay, long duration){
        ValueAnimator animator = ObjectAnimator.ofFloat(v, "alpha", v.getAlpha(), 0f);
        animator.setStartDelay(startDelay);
        animator.setDuration(duration);
        animator.setInterpolator(new DecelerateInterpolator());
        animator.start();
        return animator;
    }

    public static ValueAnimator fadeIn(View v, long startDelay, long duration){
        ValueAnimator animator = ObjectAnimator.ofFloat(v, "alpha", v.getAlpha(), 1f);
        animator.setStartDelay(startDelay);
        animator.setDuration(duration);
        animator.setInterpolator(new AccelerateInterpolator());
        animator.start();
        return animator;
    }

    public static ValueAnimator popIn(View v, long startDelay, long duration){
        PropertyValuesHolder a = PropertyValuesHolder.ofFloat("alpha", v.getAlpha(), 1f);
        PropertyValuesHolder x = PropertyValuesHolder.ofFloat("scaleX", v.getScaleX(), 1f);
        PropertyValuesHolder y = PropertyValuesHolder.ofFloat("scaleY", v.getScaleY(), 1f);
        ValueAnimator animator = ObjectAnimator.ofPropertyValuesHolder(v, a, x, y);
        animator.setStartDelay(startDelay);
        animator.setDuration(duration);
        animator.setInterpolator(new AccelerateInterpolator());
        animator.start();
        return animator;
    }

    public static ValueAnimator popOut(View v, long startDelay, long duration){
        PropertyValuesHolder a = PropertyValuesHolder.ofFloat("alpha", v.getAlpha(), 0f);
        PropertyValuesHolder x = PropertyValuesHolder.ofFloat("scaleX", v.getScaleX(), 0f);
        PropertyValuesHolder y = PropertyValuesHolder.ofFloat("scaleY", v.getScaleY(), 0f);
        ValueAnimator animator = ObjectAnimator.ofPropertyValuesHolder(v, a, x, y);
        animator.setStartDelay(startDelay);
        animator.setDuration(duration);
        animator.setInterpolator(new DecelerateInterpolator());
        animator.start();
        return animator;
    }

    public static ValueAnimator popInZero(View v, long startDelay, long duration){
        PropertyValuesHolder a = PropertyValuesHolder.ofFloat("alpha", 0f, 1f);
        PropertyValuesHolder x = PropertyValuesHolder.ofFloat("scaleX", 0f, 1f);
        PropertyValuesHolder y = PropertyValuesHolder.ofFloat("scaleY", 0f, 1f);
        ValueAnimator animator = ObjectAnimator.ofPropertyValuesHolder(v, a, x, y);
        animator.setStartDelay(startDelay);
        animator.setDuration(duration);
        animator.setInterpolator(new AccelerateInterpolator());
        v.setClickable(true);
        v.setEnabled(true);
        animator.start();
        return animator;
    }

    public static ValueAnimator popOutZero(View v, long startDelay, long duration){
        PropertyValuesHolder a = PropertyValuesHolder.ofFloat("alpha", 1f, 0f);
        PropertyValuesHolder x = PropertyValuesHolder.ofFloat("scaleX", 1f, 0f);
        PropertyValuesHolder y = PropertyValuesHolder.ofFloat("scaleY", 1f, 0f);
        ValueAnimator animator = ObjectAnimator.ofPropertyValuesHolder(v, a, x, y);
        animator.setStartDelay(startDelay);
        animator.setDuration(duration);
        animator.setInterpolator(new DecelerateInterpolator());
        v.setClickable(false);
        v.setEnabled(false);
        animator.start();
        return animator;
    }

    public static ValueAnimator color(View v, String property, long startDelay, long duration, int from, int to){
        ValueAnimator animator = ObjectAnimator.ofInt(v, property, from, to);
        animator.setEvaluator(new ArgbEvaluator());
        animator.setDuration(duration);
        animator.setStartDelay(startDelay);
        animator.start();
        return animator;
    }

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
