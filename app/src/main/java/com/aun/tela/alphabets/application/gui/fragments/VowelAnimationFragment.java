package com.aun.tela.alphabets.application.gui.fragments;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.animation.ValueAnimator;
import android.graphics.Color;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AnticipateOvershootInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.aun.tela.alphabets.R;
import com.aun.tela.alphabets.application.entities.Factory;
import com.aun.tela.alphabets.application.generic.Retriever;
import com.aun.tela.alphabets.application.gui.custom.ArialTextView;
import com.aun.tela.alphabets.application.util.Log;
import com.aun.tela.alphabets.application.util.Playback;
import com.aun.tela.alphabets.application.util.ViewAnimator;

import java.util.HashMap;
import java.util.Map;

import io.meengle.androidutil.gui.fragment.Fragtivity;

/**
 * Created by Joey on 27/02/16 at 12:36 PM.
 * Project : TELA.
 * Copyright (c) 2016 Meengle. All rights reserved.
 */
public class VowelAnimationFragment extends Fragtivity {

    public Factory.Alphabets.Consonant consonant;
    private int textColor, borderColor;
    ArialTextView centerText, topTextStub, text;
    LinearLayout centerTextLayout;
    ImageView image;


    public static VowelAnimationFragment getInstance(int textColor, int borderColor, Factory.Alphabets.Consonant consonant){
        return new VowelAnimationFragment().setInstanceStuff(textColor, borderColor, consonant);
    }

    private VowelAnimationFragment setInstanceStuff(int textColor, int borderColor, Factory.Alphabets.Consonant consonant){
        this.textColor = textColor; this.borderColor = borderColor; this.consonant = consonant; return this;
    }

    @Override
    public int layoutId() {
        return R.layout.fragment_animation_consonant;
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
        centerText = (ArialTextView) findViewById(R.id.centerText);
        topTextStub = (ArialTextView) findViewById(R.id.topTextStub);
        centerTextLayout = (LinearLayout) findViewById(R.id.centerTextLayout);
        text = (ArialTextView) findViewById(R.id.text);
        image = (ImageView) findViewById(R.id.image);
    }

    @Override
    public void setupViews() {
        centerText.setText(consonant.consonantSounds.get(0).alphabet);
        animateFirst();
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


    void animateFirst(){
        float to = getResources().getInteger(R.integer.text_size_display4);
        float from = getResources().getInteger(R.integer.text_size_display3);
        final Map<String, Boolean> locks = new HashMap<>(2);
        final String animate = "animation";
        final String sound = "sound";
        locks.put(animate, false);
        locks.put(sound, false);
        final Retriever<Boolean> retriever = new Retriever<Boolean>() {
            @Override
            public Boolean retrieve() {
                return locks.get(animate)&& locks.get(sound);
            }
        };

        ValueAnimator animator = ObjectAnimator.ofFloat(centerText, "textSize", from, to);
        animator.setInterpolator(new AccelerateDecelerateInterpolator());
        animator.setDuration(1000);
        animator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                Playback.play(consonant.consonantSounds.get(0).soundResId, null, new Playback.PlaybackListener() {
                    @Override
                    public void onStart(String id) {

                    }

                    @Override
                    public void onDone(String id) {
                        locks.put(sound, true);
                        if (retriever.retrieve()) {
                            getRootView().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    animateSound(1);
                                }
                            }, 500);
                        }
                    }

                    @Override
                    public void onError(String id, Integer errorCode) {

                    }
                });
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                locks.put(animate, true);
                if (retriever.retrieve()) {
                    getRootView().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            animateSound(1);
                        }
                    }, 500);
                }
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        Playback.play(animator);
    }


    void animateSound(final int position){
        if(position == consonant.consonantSounds.size()){
            getRootView().postDelayed(new Runnable() {
                @Override
                public void run() {
                    animateToTop();
                }
            }, 500);
            return;
        }

        final Factory.Alphabets.Consonant.ConsonantSound s = consonant.consonantSounds.get(position);
        final ArialTextView textView = new ArialTextView(getContext());
        textView.setTextSize(TypedValue.COMPLEX_UNIT_PX, getResources().getDimensionPixelSize(R.dimen.text_size_display4));
        textView.setTextColor(Color.WHITE);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.gravity = Gravity.CENTER;
        textView.setLayoutParams(params);
        textView.setText(s.alphabet);
        textView.setVisibility(View.INVISIBLE);
        textView.setAlpha(0f);
        centerTextLayout.addView(textView, params);
        textView.setVisibility(View.VISIBLE);

        final Map<String, Boolean> locks = new HashMap<>(2);
        final String animate = "animation";
        final String sound = "sound";
        locks.put(animate, false);
        locks.put(sound, false);
        final Retriever<Boolean> retriever = new Retriever<Boolean>() {
            @Override
            public Boolean retrieve() {
                return locks.get(animate)&& locks.get(sound);
            }
        };

        PropertyValuesHolder a = PropertyValuesHolder.ofFloat("alpha", 0f, 1f);
        PropertyValuesHolder x = PropertyValuesHolder.ofFloat("scaleX", 0f, 1f);
        PropertyValuesHolder y = PropertyValuesHolder.ofFloat("scaleY", 0f, 1f);
        ValueAnimator animator = ObjectAnimator.ofPropertyValuesHolder(textView, a, x, y);
        textView.setLayerType(View.LAYER_TYPE_HARDWARE, null);
        animator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                Playback.play(s.soundResId, null, new Playback.PlaybackListener() {
                    @Override
                    public void onStart(String id) {

                    }

                    @Override
                    public void onDone(String id) {
                        locks.put(sound, true);
                        if (retriever.retrieve())
                            getRootView().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    animateSound(position + 1);
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
                textView.setLayerType(View.LAYER_TYPE_NONE, null);
                locks.put(animate, true);
                if (retriever.retrieve())
                    getRootView().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            animateSound(position + 1);
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
        animator.setDuration(1000);
        animator.setInterpolator(new AccelerateDecelerateInterpolator());
        Playback.play(animator);
    }

    void animateToTop(){
        Log.d("Animationg to top");
        int height = topTextStub.getHeight();
        int currentHeight = centerTextLayout.getHeight();
        float scale = (float)height / (float)currentHeight;
        float y = topTextStub.getTranslationY();

        PropertyValuesHolder ty = PropertyValuesHolder.ofFloat("translationY", centerTextLayout.getTranslationY(), y);
        PropertyValuesHolder sx = PropertyValuesHolder.ofFloat("scaleX", centerTextLayout.getScaleX(), scale);
        PropertyValuesHolder sy = PropertyValuesHolder.ofFloat("scaleY", centerText.getScaleY(), scale);

        ValueAnimator animator = ObjectAnimator.ofPropertyValuesHolder(ty, sx, sy);
        animator.setInterpolator(new AnticipateOvershootInterpolator());
        animator.setDuration(1000);
        animator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                getRootView().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        animateImage();
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

        Playback.play(animator);

    }

    void animateImage(){
        image.setAlpha(0f);
        image.setVisibility(View.VISIBLE);

        image.setImageResource(consonant.imageResId);
        ValueAnimator animator = ViewAnimator.popInZero(image, 0, 500);
        animator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
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
        text.setAlpha(0f);
        text.setVisibility(View.VISIBLE);
        text.setText(consonant.accompanyingText);
        ValueAnimator textAnimator = ViewAnimator.fadeIn(text, 200, 800);
        textAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                getRootView().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        playAccompanyingText();
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
        Playback.play(animator);
    }

    void playAccompanyingText(){
        PropertyValuesHolder sx = PropertyValuesHolder.ofFloat("scaleX", text.getScaleX(), 1.2f);
        PropertyValuesHolder sy = PropertyValuesHolder.ofFloat("scaleY", text.getScaleY(), 1.2f);
        ValueAnimator animator = ObjectAnimator.ofPropertyValuesHolder(text, sx, sy);
        animator.setInterpolator(new AccelerateDecelerateInterpolator());
        Playback.play(consonant.accompanyingTextSoundResId, null, new Playback.PlaybackListener() {
            @Override
            public void onStart(String id) {

            }

            @Override
            public void onDone(String id) {

            }

            @Override
            public void onError(String id, Integer errorCode) {

            }
        });
        Playback.play(animator);
    }

}
