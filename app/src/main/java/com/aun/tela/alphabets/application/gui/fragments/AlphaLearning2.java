package com.aun.tela.alphabets.application.gui.fragments;

import android.animation.Animator;
import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.animation.ValueAnimator;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.animation.AnticipateOvershootInterpolator;
import android.widget.TextView;

import com.aun.tela.alphabets.R;
import com.aun.tela.alphabets.application.entities.Factory;
import com.aun.tela.alphabets.application.generic.Collector;
import com.aun.tela.alphabets.application.generic.Consumer;
import com.aun.tela.alphabets.application.util.Log;
import com.aun.tela.alphabets.application.util.Speech;
import com.aun.tela.alphabets.application.util.ViewAnimator;

import java.util.HashMap;
import java.util.Map;

import io.meengle.androidutil.gui.fragment.Fragtivity;

public class AlphaLearning2 extends Fragtivity implements Collector<View> {

    TextView alphabetCenter, alphabetUppercaseCenter, alphabetLowercaseCenter;
    Factory.Alphabets.Alphabet alphabet;
    int textColor, borderColor;
    Collector<Boolean> finishCollector;
    Map<String, Boolean> states = new HashMap<>();

    public static AlphaLearning2 getInstance(Factory.Alphabets.Alphabet alphabet, int textColor, int borderColor, Collector<Boolean> finishCollector){
        AlphaLearning2 f = new AlphaLearning2().setAlphabet(alphabet).setTextColor(textColor).setBorderColor(borderColor).setFinishCollector(finishCollector);
        return f;
    }

    public AlphaLearning2 setAlphabet(Factory.Alphabets.Alphabet alphabet){
        this.alphabet = alphabet; return this;
    }

    public AlphaLearning2 setTextColor(int color){
        this.textColor = color; return this;
    }

    public AlphaLearning2 setBorderColor(int color){
        this.borderColor = color; return this;
    }

    public AlphaLearning2 setFinishCollector(Collector<Boolean> finishCollector){
        this.finishCollector = finishCollector; return this;
    }

    @Override
    public int layoutId() {
        return R.layout.alpha_learning_layout2;
    }

    @Override
    public void destroy() {

    }

    @Override
    public void destroyView() {

    }

    @Override
    public void bundle(Bundle bundle) {

    }

    @Override
    public void findViews() {
        alphabetCenter = (TextView) findViewById(R.id.alphabetCenter);
        alphabetUppercaseCenter = (TextView) findViewById(R.id.alphabetUppercaseCenter);
        alphabetLowercaseCenter = (TextView) findViewById(R.id.alphabetLowercaseCenter);
    }

    ViewTreeObserver.OnGlobalLayoutListener layoutListener;

    @Override
    public void setupViews() {
        alphabetCenter.setTextColor(textColor);
        alphabetUppercaseCenter.setTextColor(textColor);
        alphabetLowercaseCenter.setTextColor(textColor);
        alphabetUppercaseCenter.setVisibility(View.INVISIBLE);
        alphabetLowercaseCenter.setVisibility(View.INVISIBLE);
        alphabetCenter.setText(alphabet.getUppercase());
        alphabetUppercaseCenter.setText(alphabet.getUppercase());
        alphabetLowercaseCenter.setText(alphabet.getLowerCase());
        layoutListener = new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    getRootView().getViewTreeObserver().removeOnGlobalLayoutListener(layoutListener);
                }else{
                    getRootView().getViewTreeObserver().removeGlobalOnLayoutListener(layoutListener);
                }
                shiftAnimate();
            }
        };
        getRootView().getViewTreeObserver().addOnGlobalLayoutListener(layoutListener);

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void onKeyboardShown(int i) {

    }

    @Override
    public void onKeyboardHidden() {

    }

    @Override
    public boolean shouldWatchKeyboard() {
        return false;
    }

    void shiftAnimate(){
        final String animate = "shiftAnimate";
        final String sound = "playShiftAnimate";
        final Consumer<Boolean> finishConsumer = new Consumer<Boolean>() {
            @Override
            public Boolean consume() {
                return states.get(animate) && states.get(sound);
            }
        };
        states.put(animate, false);
        states.put(sound, false);
        alphabetCenter.measure(View.MeasureSpec.makeMeasureSpec(getRootView().getMeasuredWidth(), View.MeasureSpec.AT_MOST), View.MeasureSpec.makeMeasureSpec(getRootView().getMeasuredHeight(), View.MeasureSpec.AT_MOST));
        alphabetUppercaseCenter.measure(View.MeasureSpec.makeMeasureSpec(getRootView().getMeasuredWidth(), View.MeasureSpec.AT_MOST), View.MeasureSpec.makeMeasureSpec(getRootView().getMeasuredHeight(), View.MeasureSpec.AT_MOST));
        alphabetLowercaseCenter.measure(View.MeasureSpec.makeMeasureSpec(getRootView().getMeasuredWidth(), View.MeasureSpec.AT_MOST), View.MeasureSpec.makeMeasureSpec(getRootView().getMeasuredHeight(), View.MeasureSpec.AT_MOST));
        float x = alphabetCenter.getTranslationX();
        Log.d("x is: "+x);
        x = x - alphabetCenter.getMeasuredWidth() / 2f;
        Log.d("x going is: "+x);
        ValueAnimator animator = ObjectAnimator.ofFloat(alphabetCenter, "translationX", alphabetCenter.getTranslationX(), x);
        animator.setDuration(700);
        animator.setInterpolator(new AnticipateOvershootInterpolator());
        animator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                playShiftAnimate(new Speech.VoiceCallback() {
                    @Override
                    public void onStart(String id) {

                    }

                    @Override
                    public void onDone(String id) {
                        states.put(sound, true);
                        if (finishConsumer.consume())
                            getRootView().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    animateInfo();
                                }
                            }, 500);

                    }

                    @Override
                    public void onError(String id, Integer errorCode) {

                    }
                });
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                alphabetUppercaseCenter.setVisibility(View.VISIBLE);
                alphabetCenter.setVisibility(View.GONE);
                popLowercase(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animator animation) {
                        states.put(animate, true);
                        if (finishConsumer.consume())
                            getRootView().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    animateInfo();
                                }
                            }, 500);
                    }

                    @Override
                    public void onAnimationCancel(Animator animation) {

                    }

                    @Override
                    public void onAnimationRepeat(Animator animation) {

                    }
                });
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

    void playShiftAnimate(Speech.VoiceCallback voiceCallback){
        Speech.play(alphabet.presentation, null, voiceCallback);
    }

    void popLowercase(Animator.AnimatorListener listener){
        alphabetLowercaseCenter.setAlpha(0f);
        alphabetLowercaseCenter.setVisibility(View.VISIBLE);
        ValueAnimator animator = ViewAnimator.popIn(alphabetLowercaseCenter, 0, 300);
        animator.addListener(listener);
    }

    void animateInfo(){
        final String animate = "animateInfo";
        final String sound = "playAnimateInfo";
        final Consumer<Boolean> finishConsumer = new Consumer<Boolean>() {
            @Override
            public Boolean consume() {
                return states.get(animate) && states.get(sound);
            }
        };
        states.put(animate, false);
        states.put(sound, false);
        ValueAnimator cap = ObjectAnimator.ofInt(alphabetUppercaseCenter,"textColor", textColor, borderColor, textColor, borderColor, textColor);
        cap.setEvaluator(new ArgbEvaluator());
        ValueAnimator low = ObjectAnimator.ofInt(alphabetLowercaseCenter,"textColor", textColor, borderColor, textColor, borderColor, textColor);
        low.setEvaluator(new ArgbEvaluator());
        cap.setDuration(3000);
        low.setDuration(3000);
        low.setStartDelay(500);
        cap.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                playInfo(new Speech.VoiceCallback() {
                    @Override
                    public void onStart(String id) {

                    }

                    @Override
                    public void onDone(String id) {
                        states.put(sound, true);
                        if (finishConsumer.consume())
                            getRootView().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    end();
                                }
                            }, 500);

                    }

                    @Override
                    public void onError(String id, Integer errorCode) {

                    }
                });
            }

            @Override
            public void onAnimationEnd(Animator animation) {


            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        low.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                states.put(animate, true);
                if(finishConsumer.consume())
                getRootView().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        end();
                    }
                }, 500);
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        cap.start();
        low.start();
    }

    void playInfo(Speech.VoiceCallback voiceCallback){
        Speech.play(alphabet.presentationInfo, null, voiceCallback);
    }

    void animateUp(){
        float top = getResources().getDimensionPixelSize(R.dimen.activity_horizontal_margin);
        float size = getResources().getInteger(R.integer.text_size_display3);
        float from = getResources().getInteger(R.integer.text_size_display4);
        PropertyValuesHolder uts = PropertyValuesHolder.ofFloat("textSize", from, size);
        PropertyValuesHolder lts = PropertyValuesHolder.ofFloat("textSize", from, size);
        Log.d("Translationy y: "+alphabetUppercaseCenter.getY());
        PropertyValuesHolder uty = PropertyValuesHolder.ofFloat("Y", alphabetUppercaseCenter.getY(), top);
        PropertyValuesHolder lty = PropertyValuesHolder.ofFloat("Y", alphabetLowercaseCenter.getY(), top);
        ValueAnimator cap = ObjectAnimator.ofPropertyValuesHolder(alphabetUppercaseCenter, uts, uty);
        ValueAnimator low = ObjectAnimator.ofPropertyValuesHolder(alphabetLowercaseCenter, lts, lty);
        low.setInterpolator(new AnticipateOvershootInterpolator());
        cap.setInterpolator(new AnticipateOvershootInterpolator());
        low.setStartDelay(20);
        cap.setDuration(1000);
        low.setDuration(1000);
        low.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                finish();
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        cap.start();
        low.start();
    }

    void end(){
        finishCollector.collect(true);
    }

    @Override
    public void collect(final View view) {
        view.post(new Runnable() {
            @Override
            public void run() {
                ViewAnimator.popOutZero(view, 0, 200);
            }
        });
        animateUp();
    }

    private void finish(){
        ((AlphaLearning)getParentFragment()).nextStateAndBuild();
    }
}