package com.aun.tela.alphabets.application.gui.fragments;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.animation.ValueAnimator;
import android.content.res.ColorStateList;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AnticipateOvershootInterpolator;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.aun.tela.alphabets.R;
import com.aun.tela.alphabets.application.entities.Factory;
import com.aun.tela.alphabets.application.generic.Collector;
import com.aun.tela.alphabets.application.generic.DoubleConsumer;
import com.aun.tela.alphabets.application.generic.QuatroCollector;
import com.aun.tela.alphabets.application.gui.activity.Activity;
import com.aun.tela.alphabets.application.gui.adapter.GenericRecyclerViewItemAdapter;
import com.aun.tela.alphabets.application.gui.custom.ArialTextView;
import com.aun.tela.alphabets.application.gui.custom.CircularColorView;
import com.aun.tela.alphabets.application.util.Color;
import com.aun.tela.alphabets.application.util.Log;
import com.aun.tela.alphabets.application.util.Playback;
import com.aun.tela.alphabets.application.util.ViewAnimator;
import com.sothree.slidinguppanel.SlidingUpPanelLayout;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import io.meengle.androidutil.gui.fragment.Fragtivity;
import io.meengle.util.Value;

/**
 * Created by Joey on 28/02/16 at 6:28 PM.
 * Project : TELA.
 * Copyright (c) 2016 Meengle. All rights reserved.
 */
public class LetterSoundFragment extends Fragtivity implements SlidingUpPanelLayout.PanelSlideListener {

    int textColor, borderColor;
    RecyclerView recyclerView;
    SlidingUpPanelLayout slidingLayout;
    GenericRecyclerViewItemAdapter<String, ViewHolder> adapter;
    CircularColorView back, next, slideUp, pause;
    ImageButton chevron, pauseButton, replayButton;
    boolean pausedByPanel = false;

    ArialTextView letterTop, letterCenter, letterCenterLow;
    LinearLayout letterContainer;
    LinearLayout wordContainer;

    Factory.Alphabets.LetterSound letterSound;
    Collector<Void> nextCollector, replayCollector;

    public static LetterSoundFragment getInstance(int textColor, int borderColor, Factory.Alphabets.LetterSound letterSound){
        return new LetterSoundFragment().setInstanceStuff(textColor, borderColor, letterSound);
    }

    private LetterSoundFragment setInstanceStuff(int textColor, int borderColor, Factory.Alphabets.LetterSound letterSound){
        this.letterSound = letterSound;
        this.textColor = textColor; this.borderColor = borderColor; return this;
    }

    ImageView cloudLeft, cloudRight;

    @Override
    public int layoutId() {
        return R.layout.fragment_letter_sound;
    }

    @Override
    public void destroy() {
    }

    @Override
    public void destroyView() {
        try {
            Playback.release();
        }catch (Exception e){

        }
        getRootView().removeCallbacks(null);
    }

    @Override
    public void bundle(Bundle bundle) {

    }

    @Override
    public void findViews() {
        cloudLeft = (ImageView) findViewById(R.id.cloud_left);
        cloudRight = (ImageView) findViewById(R.id.cloud_right);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        slidingLayout = (SlidingUpPanelLayout) findViewById(R.id.slidingLayout);
        back = (CircularColorView) findViewById(R.id.back);
        next = (CircularColorView) findViewById(R.id.right);
        slideUp = (CircularColorView) findViewById(R.id.slideUp);

        wordContainer = (LinearLayout) findViewById(R.id.wordContainer);
        letterCenter = (ArialTextView) findViewById(R.id.letterCenter);
        letterTop = (ArialTextView) findViewById(R.id.letterTop);
        chevron = (ImageButton) findViewById(R.id.chevron);
        letterCenterLow = (ArialTextView) findViewById(R.id.letterCenterLow);
        letterContainer = (LinearLayout) findViewById(R.id.letterContainer);

        pause = (CircularColorView) findViewById(R.id.pause);
        replayButton = (ImageButton) findViewById(R.id.replayButton);
        pauseButton = (ImageButton) findViewById(R.id.pauseButton);

    }

    ViewTreeObserver.OnGlobalLayoutListener layoutListener = new ViewTreeObserver.OnGlobalLayoutListener() {
        @Override
        public void onGlobalLayout() {
            try{
                replayCollector = new Collector<Void>() {
                    @Override
                    public void collect(Void aVoid) {
                        playLetterIntro();
                    }
                };
                playLetterIntro();
            }catch (Exception e){
                Log.d("Exception is "+e.toString());
            }
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                getRootView().getViewTreeObserver().removeOnGlobalLayoutListener(layoutListener);
            }else{
                getRootView().getViewTreeObserver().removeGlobalOnLayoutListener(layoutListener);
            }
        }
    };

    @Override
    public void setupViews() {
        Random random = new Random();
        ViewAnimator.leftRightify(cloudLeft, 5, random.nextInt(300), 1500);
        ViewAnimator.leftRightify(cloudRight, -5, random.nextInt(500), 1500);

        slidingLayout.setPanelSlideListener(this);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerView.setLayoutManager(layoutManager);
        getRootView().getViewTreeObserver().addOnGlobalLayoutListener(layoutListener);

        List<String> items = Factory.Alphabets.LetterSound.copyLetterSounds();
        final Random rand = new Random();

        //create an adapter for the list that shows up at the bottom of the list.
        adapter = GenericRecyclerViewItemAdapter.<String, ViewHolder>getInstance()
                .setIdConsumer(new DoubleConsumer<Long, String, Integer>() {
                    @Override
                    public Long consume(String s, Integer integer) {
                        return integer.longValue();
                    }
                }).setViewCollector(new QuatroCollector<ViewHolder, String, Integer, Boolean>() {
                    @Override
                    public void collect(ViewHolder viewHolder, final String s, final Integer integer, Boolean aBoolean) {
                        ViewHolder.setup(viewHolder, s, integer, aBoolean);
                        viewHolder.itemView.setScaleX(1f);
                        viewHolder.itemView.setScaleY(1f);
                        ViewAnimator.springify(viewHolder.itemView, new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                select(s);
                            }
                        });
                        if (Value.NULL(viewHolder.itemView.getAnimation())) {
                            Log.d("Animation null, starting for view : " + viewHolder.itemView.toString());
                            ViewAnimator.leftRightify(viewHolder.itemView, 10, rand.nextInt(500), 800 + rand.nextInt(200));
                        }
                    }
                }).setViewConsumer(new DoubleConsumer<ViewHolder, ViewGroup, Integer>() {
                    @Override
                    public ViewHolder consume(ViewGroup viewGroup, Integer integer) {
                        return ViewHolder.inflateDefault(viewGroup);
                    }
                }).setItems(items);
        recyclerView.setAdapter(adapter);

        ViewAnimator.popInZero(back, 0, 300);
        ViewAnimator.popInZero(pause, 120, 300);
        ViewAnimator.springify(back, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                exit();
            }
        });

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            chevron.setImageTintList(ColorStateList.valueOf(textColor));
        }

        ViewAnimator.upDownify(back, 10, 0, 1000);
        ViewAnimator.upDownify(pause, 10, 200, 1000);
        ViewAnimator.upDownify(next, 10, 400, 1000);
        ViewAnimator.upDownify(slideUp, 10, 300, 1000);

        ViewAnimator.springify(next, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (nextCollector == null)
                    return;
                nextCollector.collect(null);
                replayButton.setVisibility(View.INVISIBLE);
                pauseButton.setVisibility(View.VISIBLE);
                nextCollector = null;
                ViewAnimator.popOutZero(next, 0, 300);
            }
        });

        slideUp.setBorderColor(textColor);
        next.setCircularColor(textColor);
        back.setCircularColor(textColor);
        pause.setCircularColor(textColor);

        ViewAnimator.fadeIn(findViewById(R.id.ui), 300, 300);
        letterCenter.setText(letterSound.alphabet.toUpperCase());
        letterCenterLow.setText(letterSound.alphabet.toLowerCase());

        ViewAnimator.springify(slideUp, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                slidingLayout.setPanelState(slidingLayout.getPanelState().equals(SlidingUpPanelLayout.PanelState.COLLAPSED) ? SlidingUpPanelLayout.PanelState.EXPANDED : SlidingUpPanelLayout.PanelState.COLLAPSED);
            }
        });

        ViewAnimator.springify(pause, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (pauseButton.getVisibility()) {
                    case View.VISIBLE:
                        boolean paused = Playback.paused();
                        if (!paused) {
                            Playback.pause();
                            pauseButton.setImageResource(R.drawable.media_play);
                        } else {
                            Playback.resume();
                            pauseButton.setImageResource(R.drawable.media_pause);
                        }
                        break;
                    default:
                        Playback.release();
                        ValueAnimator animator = ObjectAnimator.ofFloat(replayButton, "rotation", 0, 360);
                        animator.setDuration(200);
                        animator.addListener(new Animator.AnimatorListener() {
                            @Override
                            public void onAnimationStart(Animator animation) {

                            }

                            @Override
                            public void onAnimationEnd(Animator animation) {
                                replayButton.setRotation(0);
                                replayButton.setVisibility(View.INVISIBLE);
                                pauseButton.setVisibility(View.VISIBLE);
                                if(replayCollector!=null)
                                    replayCollector.collect(null);
                            }

                            @Override
                            public void onAnimationCancel(Animator animation) {

                            }

                            @Override
                            public void onAnimationRepeat(Animator animation) {

                            }
                        });
                        if (next.getAlpha() > 0.5f) {
                            ViewAnimator.popOutZero(next, 0, 200);
                        }
                        animator.start();
                        break;
                }
            }
        });

    }

    void playLetterIntro(){
        slidingLayout.setParallaxOffset(getRootView().getHeight() / 2);
        final String a = "a";
        final String s = "s";
        final Map<String, Boolean> dones = new HashMap<>(2);
        dones.put(a, false);
        dones.put(s, false);

        PropertyValuesHolder sX = PropertyValuesHolder.ofFloat("scaleX", 1f, 3f);
        PropertyValuesHolder sY = PropertyValuesHolder.ofFloat("scaleY", 1f, 3f);

        ValueAnimator animator = ObjectAnimator.ofPropertyValuesHolder(letterContainer, sX, sY);
        animator.setInterpolator(new AnticipateOvershootInterpolator());
        animator.setDuration(1500);
        animator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                pauseButton.setVisibility(View.VISIBLE);
                Playback.play(letterSound.introIdentResId, null, new Playback.PlaybackListener() {
                    @Override
                    public void onStart(String id) {

                    }

                    @Override
                    public void onDone(String id) {
                        dones.put(s, true);
                        if (dones.get(a) && dones.get(s)) {
                            getRootView().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    try {
                                        playLetterSoundIntro();
                                    }catch (Exception e){
                                        Log.d("Exception is "+e.toString());
                                    }
                                }
                            }, 800);
                        }
                    }

                    @Override
                    public void onError(String id, Integer errorCode) {

                    }
                });
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                dones.put(a, true);
                if (dones.get(a) && dones.get(s)) {
                    getRootView().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                playLetterSoundIntro();
                            }catch (Exception e){
                                Log.d("Exception is "+e.toString());
                            }
                        }
                    }, 800);
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


    void playLetterSoundIntro(){
        Playback.play(letterSound.introSoundResId, null, new Playback.PlaybackListener() {
            @Override
            public void onStart(String id) {

            }

            @Override
            public void onDone(String id) {
                getRootView().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            playLetterSound(0);
                        }catch (Exception e){
                            Log.d("Exception is "+e.toString());
                        }
                    }
                }, 500);
            }

            @Override
            public void onError(String id, Integer errorCode) {

            }
        });
    }


    void playLetterSound(final int ordinal){
        final String a = "a";
        final String s = "s";
        final Map<String, Boolean> dones = new HashMap<>(2);
        dones.put(a, false);
        dones.put(s, false);

        PropertyValuesHolder sX = PropertyValuesHolder.ofFloat("scaleX", 3f, 3.3f);
        PropertyValuesHolder sY = PropertyValuesHolder.ofFloat("scaleY", 3f, 3.3f);

        ValueAnimator animator = ObjectAnimator.ofPropertyValuesHolder(letterContainer, sX, sY);
        animator.setInterpolator(new AnticipateOvershootInterpolator());
        animator.setRepeatCount(1);
        animator.setRepeatMode(ValueAnimator.REVERSE);
        animator.setDuration(500);

        animator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                Playback.play(letterSound.soundResId, null, new Playback.PlaybackListener() {
                    @Override
                    public void onStart(String id) {

                    }

                    @Override
                    public void onDone(String id) {
                        dones.put(s, true);
                        if (dones.get(a) && dones.get(s)) {
                            getRootView().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    if (ordinal == 2) {
                                        nextCollector = new Collector<Void>() {
                                            @Override
                                            public void collect(Void aVoid) {
                                                replayCollector = new Collector<Void>() {
                                                    @Override
                                                    public void collect(Void aVoid) {
                                                        wordContainer.removeAllViews();
                                                        letterContainer.setX(0);
                                                        letterContainer.setY(0);
                                                        letterContainer.setScaleX(3f);
                                                        letterContainer.setScaleY(3f);
                                                        animateIntroduceWords();
                                                    }
                                                };

                                                try {
                                                    animateIntroduceWords();
                                                } catch (Exception e) {
                                                    Log.d("Exception is " + e.toString());
                                                }
                                            }
                                        };
                                        pauseButton.setVisibility(View.INVISIBLE);
                                        replayButton.setVisibility(View.VISIBLE);
                                        ViewAnimator.popInZero(next, 0, 300);
                                    } else {
                                        try {
                                            playLetterSound(ordinal + 1);
                                        } catch (Exception e) {
                                            Log.d("Exception is " + e.toString());
                                        }
                                    }
                                }
                            }, 800);
                        }
                    }

                    @Override
                    public void onError(String id, Integer errorCode) {

                    }
                });
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                dones.put(a, true);
                if (dones.get(a) && dones.get(s)) {
                    getRootView().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            if (ordinal == 2) {
                                nextCollector = new Collector<Void>() {
                                    @Override
                                    public void collect(Void aVoid) {
                                        try {
                                            replayCollector = new Collector<Void>() {
                                                @Override
                                                public void collect(Void aVoid) {
                                                    animateIntroduceWords();
                                                }
                                            };
                                            animateIntroduceWords();
                                        } catch (Exception e) {
                                            Log.d("Exception is " + e.toString());
                                        }
                                    }
                                };
                                pauseButton.setVisibility(View.INVISIBLE);
                                replayButton.setVisibility(View.VISIBLE);
                                ViewAnimator.popInZero(next, 0, 300);
                            } else {
                                try {
                                    playLetterSound(ordinal + 1);
                                } catch (Exception e) {
                                    Log.d("Exception is " + e.toString());
                                }
                            }

                        }
                    }, 800);
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

    void animateIntroduceWords(){
        Log.d("Animating introduce words");
        int[] locs = new int[2];
        letterTop.getLocationInWindow(locs);
        float x = locs[0] - letterTop.getWidth();
        float y = locs[1] - letterTop.getHeight();
        float fX = letterContainer.getX();
        float fY = letterContainer.getY();

        PropertyValuesHolder sX = PropertyValuesHolder.ofFloat("scaleX", 3f, 1f);
        PropertyValuesHolder sY = PropertyValuesHolder.ofFloat("scaleY", 3f, 1f);
        PropertyValuesHolder tX = PropertyValuesHolder.ofFloat("X", fX, x);
        PropertyValuesHolder tY = PropertyValuesHolder.ofFloat("Y", fY, y);

        ValueAnimator animator = ObjectAnimator.ofPropertyValuesHolder(letterContainer, sX, sY, tX, tY);
        animator.setInterpolator(new AnticipateOvershootInterpolator(0.5f));
        animator.setDuration(1000);
        animator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                Playback.play(letterSound.wordIntroResId, null, new Playback.PlaybackListener() {
                    @Override
                    public void onStart(String id) {

                    }

                    @Override
                    public void onDone(String id) {
                        getRootView().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    introduceWord(0);
                                } catch (Exception e) {
                                    Log.d("Exception is " + e.toString());
                                }
                            }
                        }, 300);
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
        Playback.play(animator);
    }

    void introduceWord(final int ordinal){
        Log.d("Introducing word at " + ordinal);
        if(ordinal==letterSound.words.length){
            returnCenter();
            return;
        }
        final Factory.Alphabets.LetterSound.Word word = letterSound.words[ordinal];
        final String a = "a";
        final String b = "b";
        final Map<String, Boolean> dones = new HashMap<>(2);
        dones.put(a, false);
        dones.put(b, false);
        Playback.play(word.audioResId, null, new Playback.PlaybackListener() {
            @Override
            public void onStart(String id) {

            }

            @Override
            public void onDone(String id) {
                dones.put(b, true);
                if (dones.get(a) && dones.get(b)) {
                    //finished word here;
                    getRootView().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                identifyLetters(word, ordinal, 0);
                            } catch (Exception e) {
                                Log.d("Exception is " + e.toString());
                            }
                        }
                    }, 600);
                }
            }

            @Override
            public void onError(String id, Integer errorCode) {

            }
        });
        animateWord(ordinal, word, dones, a, b);
    }

    void animateWord(final int ordinal, final Factory.Alphabets.LetterSound.Word word, final Map<String,Boolean> dones, final String a, final String b){
        Log.d("Animation word " + word.word);
        int count = word.word.length();
        int res = count <=4 ? R.dimen.text_size_display4 : count <= 8 ? R.dimen.text_size_display3 : count <= 12?  R.dimen.text_size_display2 : count <= 16? R.dimen.text_size_display1 : R.dimen.text_size_title;
        char[] chars = word.word.toCharArray();
        for(int i = 0; i < chars.length; i++){
            char c = chars[i];
            ArialTextView textView = new ArialTextView(getContext());
            textView.setTextColor(android.graphics.Color.WHITE);
            textView.setTextSize(getResources().getDimensionPixelSize(res));
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            params.gravity = Gravity.CENTER | Gravity.BOTTOM;
            textView.setText(String.valueOf(c));
            textView.setVisibility(View.INVISIBLE);
            wordContainer.addView(textView, params);
        }
        for(int i = 0; i < wordContainer.getChildCount(); i++){
            ArialTextView child = (ArialTextView) wordContainer.getChildAt(i);
            ValueAnimator an = ViewAnimator.popInZero(child, i * 100, 300);
            if(i == wordContainer.getChildCount()-1){
                an.addListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animator animation) {
                        dones.put(a, true);
                        if (dones.get(a) && dones.get(b)) {
                            //finished word here;
                            getRootView().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    try {
                                        identifyLetters(word, ordinal, 0);
                                    } catch (Exception e) {
                                        Log.d("Exception is " + e.toString());
                                    }
                                }
                            }, 600);
                        }
                    }

                    @Override
                    public void onAnimationCancel(Animator animation) {

                    }

                    @Override
                    public void onAnimationRepeat(Animator animation) {

                    }
                });
            }
            Playback.play(an);
        }
    }

    void identifyLetters(final Factory.Alphabets.LetterSound.Word word, final int ordinal, final int resound){
        Log.d("Identifying letters at ordinal " + ordinal + " and stuff");
        for(int i = 0; i < wordContainer.getChildCount(); i++){
            ArialTextView child = (ArialTextView) wordContainer.getChildAt(i);
            String a = child.getText().toString();
            Log.d("Child text is " + a);
            if(a.toLowerCase().equals(letterSound.alphabet.toLowerCase())){
                child.setTextColor(textColor);
                ArialTextView animatingBuddy = i == 0 ? letterCenter : letterCenterLow;
                animatingBuddy.setTextColor(textColor);
                PropertyValuesHolder sX = PropertyValuesHolder.ofFloat("scaleX", 1f, 1.1f);
                PropertyValuesHolder sY = PropertyValuesHolder.ofFloat("scaleY", 1f, 1.1f);

                ValueAnimator animator = ObjectAnimator.ofPropertyValuesHolder(child, sX, sY);
                ValueAnimator animator2 = ObjectAnimator.ofPropertyValuesHolder(animatingBuddy, sX, sY);
                animator.setInterpolator(new AnticipateOvershootInterpolator());
                animator.setRepeatCount(1);
                animator.setRepeatMode(ValueAnimator.REVERSE);
                animator.setDuration(500);
                animator2.setInterpolator(new AnticipateOvershootInterpolator());
                animator2.setRepeatCount(1);
                animator2.setRepeatMode(ValueAnimator.REVERSE);
                animator2.setDuration(500);
                Playback.play(animator, animator2);
            }
        }

        Playback.play(letterSound.soundResId, null, new Playback.PlaybackListener() {
            @Override
            public void onStart(String id) {

            }

            @Override
            public void onDone(String id) {
                getRootView().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            emphasize(word, ordinal, resound);
                        } catch (Exception e) {
                            Log.d("Exception is " + e.toString());
                        }
                    }
                }, 1500);
            }

            @Override
            public void onError(String id, Integer errorCode) {

            }
        });
    }

    void emphasize(final Factory.Alphabets.LetterSound.Word word, final int ordinal, final int resound){
        for(int i = 0; i < wordContainer.getChildCount(); i ++){
            View child = wordContainer.getChildAt(i);
            ValueAnimator animator = ObjectAnimator.ofFloat(child, "translationY", child.getTranslationY(), (child.getTranslationY() - 15f));
            animator.setInterpolator(new AccelerateDecelerateInterpolator());
            animator.setRepeatMode(ValueAnimator.REVERSE);
            animator.setRepeatCount(1);
            animator.setStartDelay(i * 100);
            animator.setDuration(500);
            Playback.play(animator);
        }
        Playback.play(word.audioResId, null, new Playback.PlaybackListener() {
            @Override
            public void onStart(String id) {

            }

            @Override
            public void onDone(String id) {
                if(resound == 2){
                    getRootView().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            nextCollector = new Collector<Void>() {
                                @Override
                                public void collect(Void aVoid) {
                                    try {
                                        replayCollector = new Collector<Void>() {
                                            @Override
                                            public void collect(Void aVoid) {
                                                removeLetters(ordinal);
                                            }
                                        };
                                        removeLetters(ordinal);
                                    }catch (Exception e){
                                        Log.d("Exception is "+e.toString());
                                    }
                                }
                            };
                            pauseButton.setVisibility(View.INVISIBLE);
                            replayButton.setVisibility(View.VISIBLE);
                            ViewAnimator.popInZero(next, 0, 300);
                        }
                    }, 800);
                }else{
                    getRootView().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                identifyLetters(word, ordinal, resound + 1);
                            }catch (Exception e){
                                Log.d("Exception is "+e.toString());
                            }
                        }
                    }, 1500);
                }
            }

            @Override
            public void onError(String id, Integer errorCode) {

            }
        });
    }

    void removeLetters(final int ordinal){
        letterCenter.setTextColor(android.graphics.Color.WHITE);
        letterCenterLow.setTextColor(android.graphics.Color.WHITE);
        for(int i = 0; i < wordContainer.getChildCount(); i++){
            View v = wordContainer.getChildAt(i);
            ValueAnimator an = ViewAnimator.popOutZero(v, i * 50, 300);
            if(i == wordContainer.getChildCount()-1){
                an.addListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animator animation) {
                        getRootView().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                wordContainer.removeAllViews();
                                try {
                                    introduceWord(ordinal + 1);
                                }catch (Exception e){
                                    Log.d("Exception is "+e.toString());
                                }
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
            Playback.play(an);
        }
    }

    void returnCenter(){
        int x = 0;
        int y = 0;
        float fX = letterContainer.getTranslationX();
        float fY = letterContainer.getTranslationY();

        PropertyValuesHolder tX = PropertyValuesHolder.ofFloat("translationX", fX, x);
        PropertyValuesHolder tY = PropertyValuesHolder.ofFloat("translationY", fY, y);
        PropertyValuesHolder sX = PropertyValuesHolder.ofFloat("scaleX", 1f, 3f);
        PropertyValuesHolder sY = PropertyValuesHolder.ofFloat("scaleY", 1f, 3f);

        ValueAnimator animator = ObjectAnimator.ofPropertyValuesHolder(letterContainer, tX, tY, sX, sY);
        animator.setInterpolator(new AnticipateOvershootInterpolator());
        animator.setDuration(1000);
        animator.setInterpolator(new AnticipateOvershootInterpolator());
        animator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                getRootView().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            finish();
                        } catch (Exception e) {

                        }
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

    void finish(){
        final String a = "a";
        final String s = "s";
        final Map<String, Boolean> dones = new HashMap<>(2);
        dones.put(a, false);
        dones.put(s, false);

        PropertyValuesHolder sX = PropertyValuesHolder.ofFloat("scaleX", 3f, 3.3f);
        PropertyValuesHolder sY = PropertyValuesHolder.ofFloat("scaleY", 3f, 3.3f);

        ValueAnimator animator = ObjectAnimator.ofPropertyValuesHolder(letterContainer, sX, sY);
        animator.setInterpolator(new AnticipateOvershootInterpolator(0.5f));
        animator.setRepeatCount(1);
        animator.setRepeatMode(ValueAnimator.REVERSE);
        animator.setDuration(500);
        animator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                Playback.play(letterSound.soundResId, null, new Playback.PlaybackListener() {
                    @Override
                    public void onStart(String id) {

                    }

                    @Override
                    public void onDone(String id) {
                        dones.put(s, true);
                        if (dones.get(a) && dones.get(s)) {
                            getRootView().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    nextCollector = new Collector<Void>() {
                                        @Override
                                        public void collect(Void aVoid) {
                                            replayCollector = new Collector<Void>() {
                                                @Override
                                                public void collect(Void aVoid) {
                                                    next();
                                                }
                                            };
                                            try{
                                                next();
                                            }catch (Exception e){

                                            }
                                        }
                                    };
                                    pauseButton.setVisibility(View.INVISIBLE);
                                    replayButton.setVisibility(View.VISIBLE);
                                    ViewAnimator.popInZero(next, 0, 300);
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
                dones.put(a, true);
                if (dones.get(a) && dones.get(s)) {
                    getRootView().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            nextCollector = new Collector<Void>() {
                                @Override
                                public void collect(Void aVoid) {
                                    replayCollector = new Collector<Void>() {
                                        @Override
                                        public void collect(Void aVoid) {
                                            next();
                                        }
                                    };
                                    try{
                                        next();
                                    }catch (Exception e){

                                    }
                                }
                            };
                            pauseButton.setVisibility(View.INVISIBLE);
                            replayButton.setVisibility(View.VISIBLE);
                            ViewAnimator.popInZero(next, 0, 300);
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

    @Override
    public void pause() {
        try {
            Playback.pause();
        }catch (Exception e){

        }
    }

    @Override
    public void resume() {
        try {
            Playback.resume();
        }catch (Exception e){

        }
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

    @Override
    public void onPanelSlide(View panel, float slideOffset) {
        chevron.setRotation(slideOffset * 180f);
    }

    @Override
    public void onPanelCollapsed(View panel) {
        ViewAnimator.popInZero(back, 0, 300);
        ViewAnimator.popInZero(pause, 0, 500);
        if(Playback.paused() && pausedByPanel) {
            Playback.resume();
        }
        pausedByPanel = false;
    }

    @Override
    public void onPanelExpanded(View panel) {
        ViewAnimator.popOutZero(back, 0, 300);
        ViewAnimator.popOutZero(pause, 0, 500);
        if(!Playback.paused()) {
            Playback.pause();
            pausedByPanel= true;
        }
    }

    @Override
    public void onPanelAnchored(View panel) {

    }

    @Override
    public void onPanelHidden(View panel) {

    }


    static class ViewHolder extends RecyclerView.ViewHolder {

        TextView text;
        ImageView image;

        public ViewHolder(View itemView) {
            super(itemView);
            text = (TextView) findViewById(R.id.text);
            image =  (ImageView) findViewById(R.id.image);
        }

        View findViewById(int resId) {
            return this.itemView.findViewById(resId);
        }

        public static ViewHolder inflateDefault(ViewGroup parent) {
            return new ViewHolder(LayoutInflater.from(Activity.getInstance()).inflate(R.layout.list_item_image_text_h, parent, false));
        }

        public static ViewHolder setup(ViewHolder viewHolder, String string, Integer position, Boolean isLast) {
            if (Value.EMPTY(string)) {
                viewHolder.text.setTag(position);
                viewHolder.itemView.setVisibility(View.INVISIBLE);
                viewHolder.text.setText(string);
                return viewHolder;
            } else {
                viewHolder.text.setText(string.toUpperCase());
                viewHolder.text.setTextColor(Color.random());
                viewHolder.itemView.setClickable(true);
                viewHolder.itemView.setVisibility(View.VISIBLE);
                viewHolder.text.setTag(position);
                return viewHolder;
            }
        }
    }

    void next(){
        int currentPosition = Factory.Alphabets.LetterSound.getPosition(letterSound.alphabet);
        if(currentPosition == Factory.Alphabets.LetterSound.LETTER_SOUND_ALPHABETS.size()){
            return;
        }
        currentPosition++;
        Factory.Alphabets.LetterSound l = Factory.Alphabets.LetterSound.build(Factory.Alphabets.getAlphabetsUppercase().get(currentPosition));
        Activity.replace(LetterSoundFragment.getInstance(textColor, borderColor, l));
    }

    void select(String letter){
        int currentPosition = Factory.Alphabets.getPosition(letter);
        Factory.Alphabets.LetterSound l = Factory.Alphabets.LetterSound.build(Factory.Alphabets.getAlphabetsUppercase().get(currentPosition));
        Activity.replace(LetterSoundFragment.getInstance(textColor, borderColor, l));
    }

    void exit(){
        View hud = findViewById(R.id.hud);
        View ui = findViewById(R.id.ui);
        ViewAnimator.fadeOut(hud, 0, 500);
        ViewAnimator.fadeOut(ui, 200, 500).addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                Activity.replace(LetterSoundNavigationFragment.getInstance(textColor, borderColor));
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
    }

}
