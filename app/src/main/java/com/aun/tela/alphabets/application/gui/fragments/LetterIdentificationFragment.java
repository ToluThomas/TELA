package com.aun.tela.alphabets.application.gui.fragments;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.animation.ValueAnimator;
import android.content.res.ColorStateList;
import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AnticipateInterpolator;
import android.view.animation.BounceInterpolator;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.aun.tela.alphabets.R;
import com.aun.tela.alphabets.application.entities.Factory;
import com.aun.tela.alphabets.application.generic.Collector;
import com.aun.tela.alphabets.application.gui.activity.Activity;
import com.aun.tela.alphabets.application.gui.custom.ArialTextView;
import com.aun.tela.alphabets.application.gui.custom.BarColorView;
import com.aun.tela.alphabets.application.gui.custom.CircularColorView;
import com.aun.tela.alphabets.application.util.Color;
import com.aun.tela.alphabets.application.util.Log;
import com.aun.tela.alphabets.application.util.ViewAnimator;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import io.meengle.androidutil.gui.fragment.Fragtivity;

/**
 * Created by Joey on 14/02/16 at 7:08 AM.
 * Project : TELA.
 * Copyright (c) 2016 Meengle. All rights reserved.
 */
public class LetterIdentificationFragment extends Fragtivity {

    ImageView grass1, grass2, grass3, grass4;
    CircularColorView back, next, prev, done_next, done_prev;
    int level = 5;
    Collector backCollector;
    FrameLayout animationContainer;
    ViewTreeObserver.OnGlobalLayoutListener layoutListener;
    BarColorView starBar, winPanel;
    FrameLayout animationBar;
    Factory.Alphabets.Alphabet alphabet = Factory.Alphabets.build("a");
    ArialTextView basketAlpha1, basketAlpha2, basketAlpha3, basketAlpha4, basketAlpha5;
    int score = 0;
    ImageButton star1, star2, star3;
    boolean animating = true;
    int textColor = Color.random(), borderColor = android.graphics.Color.WHITE;
    int fail = 0;


    public LetterIdentificationFragment setColors(int textColor, int borderColor){
        this.textColor = textColor; this.borderColor = borderColor;
        return this;
    }



    public LetterIdentificationFragment setBackCollector(Collector collector) {
        this.backCollector = collector;
        return this;
    }

    public static LetterIdentificationFragment getInstance(Factory.Alphabets.Alphabet alphabet, int textColor, int borderColor, Collector backCollector){
        return new LetterIdentificationFragment().setAlphabet(alphabet).setBackCollector(backCollector).setColors(textColor, borderColor);
    }

    public LetterIdentificationFragment setAlphabet(Factory.Alphabets.Alphabet alphabet){
        if(alphabet!=null)
            this.alphabet = alphabet;
        return this;
    }

    @Override
    public int layoutId() {
        return R.layout.fragment_letter_identification;
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
        grass1 = (ImageView) findViewById(R.id.grass1);
        grass2 = (ImageView) findViewById(R.id.grass2);
        grass3 = (ImageView) findViewById(R.id.grass3);
        grass4 = (ImageView) findViewById(R.id.grass4);
        back = (CircularColorView) findViewById(R.id.back);
        next = (CircularColorView) findViewById(R.id.next);
        prev = (CircularColorView) findViewById(R.id.prev);
        starBar = (BarColorView) findViewById(R.id.starBar);
        winPanel = (BarColorView) findViewById(R.id.winPanel);
        done_next = (CircularColorView) findViewById(R.id.done_next);
        done_prev = (CircularColorView) findViewById(R.id.done_prev);
        animationBar = (FrameLayout) findViewById(R.id.animationBar);
        animationContainer = (FrameLayout) findViewById(R.id.animationContainer);
        //animationBar = (BarColorView) findViewById(R.id.animationBar);
        //currentAlphabet = (ArialTextView) findViewById(R.id.currentAlphabet);
        //currentAlphabetBar = (BarColorView) findViewById(R.id.currentAlphabetBar);

        star1 = (ImageButton) findViewById(R.id.star1);
        star2 = (ImageButton) findViewById(R.id.star2);
        star3 = (ImageButton) findViewById(R.id.star3);

        basketAlpha1 = (ArialTextView) findViewById(R.id.basketAlpha1);
        basketAlpha2 = (ArialTextView) findViewById(R.id.basketAlpha2);
        basketAlpha3 = (ArialTextView) findViewById(R.id.basketAlpha3);
        basketAlpha4 = (ArialTextView) findViewById(R.id.basketAlpha4);
        basketAlpha5 = (ArialTextView) findViewById(R.id.basketAlpha5);
    }

    @Override
    public void setupViews() {

        starBar.setBorderColor(textColor);
        next.setCircularColor(textColor);
        prev.setCircularColor(textColor);
        back.setCircularColor(textColor);
        winPanel.setBorderColor(textColor);
        done_next.setCircularColor(textColor);
        done_prev.setCircularColor(textColor);

        ViewAnimator.leftRightify(grass1, 4, 1000, 4000);
        ViewAnimator.leftRightify(grass2, 4, 450, 2500);
        ViewAnimator.leftRightify(grass3, 4, 200, 3000);
        ViewAnimator.leftRightify(grass4, 4, 800, 3500);

        ViewAnimator.upDownify(back, 20, 300, 1000);
        ViewAnimator.upDownify(next, 20, 600, 1000);
        ViewAnimator.upDownify(prev, 20, 420, 1000);
        //ViewAnimator.upDownify(starBar, 20, 283, 1000);
        ViewAnimator.popInZero(back, 0, 300);
        ViewAnimator.popInZero(starBar, 200, 300);
        if(!alphabet.getLowerCase().equals("z"))
            ViewAnimator.popInZero(next, 0, 500);
        if(!alphabet.getLowerCase().equals("a"))
            ViewAnimator.popInZero(prev, 0, 500);

        layoutListener = new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    getRootView().getViewTreeObserver().removeOnGlobalLayoutListener(layoutListener);
                }else{
                    getRootView().getViewTreeObserver().removeGlobalOnLayoutListener(layoutListener);
                }
                refresh();
            }
        };

        ViewAnimator.springify(back, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Activity.replace(LetterIdentificationNavigationFragment.getInstance(textColor, borderColor, null));
            }
        });

        ViewAnimator.springify(next, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = Factory.Alphabets.getPosition(alphabet.getLowerCase());
                position++;
                Activity.replace(LetterIdentificationFragment.getInstance(Factory.Alphabets.build(Factory.Alphabets.ALPHABETS_LOWERCASE.get(position)),textColor, borderColor, null));
            }
        });

        ViewAnimator.springify(prev, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = Factory.Alphabets.getPosition(alphabet.getLowerCase());
                position--;
                Activity.replace(LetterIdentificationFragment.getInstance(Factory.Alphabets.build(Factory.Alphabets.ALPHABETS_LOWERCASE.get(position)),textColor, borderColor, null));
            }
        });

        getRootView().getViewTreeObserver().addOnGlobalLayoutListener(layoutListener);
    }

    void refresh(){
        basketAlpha1.setText(alphabet.getUppercase());
        basketAlpha2.setText(alphabet.getUppercase());
        basketAlpha3.setText(alphabet.getUppercase());
        basketAlpha4.setText(alphabet.getUppercase());
        basketAlpha5.setText(alphabet.getUppercase());

        addRandomLetters();
    }

    void addRandomLetters(){
        Random random = new Random();
        Map<Integer, String>  alphabets = Factory.Alphabets.getPaddedLettersFrom(alphabet.getLetter(), level);
        Map<Integer, Integer[]> alphabetMap = getPositions(level);
        for(final Map.Entry<Integer, Integer[]> entry : alphabetMap.entrySet()){

            int size = getResources().getDimensionPixelSize(R.dimen.buttonSize);

            int position = entry.getKey() + 1;
            final int x = entry.getValue()[0];
            final int y = entry.getValue()[1];

            Log.d("X and Y: "+x +":"+y);

            final FrameLayout layout = (FrameLayout) LayoutInflater.from(this.getContext()).inflate(R.layout.egg_letter_id, null, false);
            ArialTextView textView = (ArialTextView) layout.findViewById(R.id.text);
            ImageView image = (ImageView) layout.findViewById(R.id.image);
            layout.setX(x);
            layout.setY(animationContainer.getHeight());
            layout.setClickable(true);
            switch ( random.nextInt(3)){
                case 0:
                    image.setImageResource(R.drawable.egg_green);
                    break;
                case 1:
                    image.setImageResource(R.drawable.egg_blue);
                    break;
                case 2:
                    image.setImageResource(R.drawable.egg_red);
                    break;
            }

            layout.setLayerType(View.LAYER_TYPE_HARDWARE, null);
            textView.setText(alphabets.get(entry.getKey()).toUpperCase());

            FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            params.gravity = Gravity.CENTER;
            params.height = size;
            params.width = size;
            layout.setLayoutParams(params);

            ViewAnimator.springify(layout, new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    select(layout, x, y);
                }
            });

            animationContainer.addView(layout);
            PropertyValuesHolder tX = PropertyValuesHolder.ofFloat("translationX", layout.getTranslationX(), x);
            PropertyValuesHolder tY = PropertyValuesHolder.ofFloat("translationY", layout.getTranslationY(), y);
            ValueAnimator animator = ObjectAnimator.ofPropertyValuesHolder(layout, tX, tY);
            animator.setInterpolator(new AccelerateDecelerateInterpolator());
            animator.setDuration(500);
            animator.setStartDelay(entry.getKey() * 100);
            if(position==alphabetMap.size()){
                animator.addListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animator animation) {
                        animating = false;
                    }

                    @Override
                    public void onAnimationCancel(Animator animation) {

                    }

                    @Override
                    public void onAnimationRepeat(Animator animation) {

                    }
                });
            }
            animator.start();
        }
    }


    void select(final FrameLayout layout, final int x_, final int y_){
        if(animating)
            return;
        animating = true;

        Log.d("Moving added from x: " + x_ + ", y:" + y_);
        float destX = 0/*animationContainer.getWidth()/2 - currentAlphabetBar.getWidth()/2 + animationContainer.getPaddingLeft()*/;
        float destY = basketAlpha5.getTranslationY();
        Log.d("Moving added to x: "+destX+", y:"+destY);
        PropertyValuesHolder x = PropertyValuesHolder.ofFloat("translationX", x_, destX);
        PropertyValuesHolder y = PropertyValuesHolder.ofFloat("translationY", y_, destY);

        ValueAnimator animator = ObjectAnimator.ofPropertyValuesHolder(layout, x, y);
        animator.setInterpolator(new AccelerateDecelerateInterpolator());
        animator.setDuration(500);
        animator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                compare((ArialTextView)layout.findViewById(R.id.text), new Collector<Boolean>() {
                    @Override
                    public void collect(final Boolean aBoolean) {
                        getRootView().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                if (aBoolean) {
                                    score(layout, x_, y_);
                                } else {
                                    fail(layout, x_, y_);
                                }
                            }
                        }, 500);
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


    void compare(ArialTextView text, final Collector<Boolean> collector){
        Log.d("Comparing");
        final boolean matched = alphabet.getLowerCase().equalsIgnoreCase(text.getText().toString());
        if (collector != null)
            collector.collect(matched);
        /*if(matched){
            PropertyValuesHolder x = PropertyValuesHolder.ofFloat("scaleX", 2f, 1f);
            PropertyValuesHolder y = PropertyValuesHolder.ofFloat("scaleY", 2f, 1f);
            PropertyValuesHolder dx = PropertyValuesHolder.ofFloat("scaleX", 1f, 2f);
            PropertyValuesHolder dy = PropertyValuesHolder.ofFloat("scaleY", 1f, 2f);
            ValueAnimator animator = ObjectAnimator.ofPropertyValuesHolder(text, x, y);
            ValueAnimator dAnimator = ObjectAnimator.ofPropertyValuesHolder(text, dx, dy);
            dAnimator.setDuration(500);
            dAnimator.setInterpolator(new AccelerateInterpolator());
            animator.setInterpolator(new BounceInterpolator());
            animator.setDuration(700);
            animator.addListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animation) {

                }

                @Override
                public void onAnimationEnd(Animator animation) {
                    if (collector != null)
                        collector.collect(matched);
                }

                @Override
                public void onAnimationCancel(Animator animation) {

                }

                @Override
                public void onAnimationRepeat(Animator animation) {

                }
            });
            AnimatorSet animatorSet = new AnimatorSet();
            animatorSet.play(animator).after(dAnimator);
            animatorSet.start();
        }else{
            if (collector != null)
                collector.collect(matched);
        }
        */
    }

    void score(FrameLayout layout, int x, int y){
        Log.d("Scoring");
        score++;
        final ImageButton star = score == 1 ? star1 : score == 2 ? star2 : star3;
        final ImageButton starnimate = new ImageButton(getContext());
        starnimate.setLayerType(View.LAYER_TYPE_HARDWARE, null);
        starnimate.setBackgroundColor(android.graphics.Color.TRANSPARENT);
        starnimate.setImageResource(R.drawable.star_50);
        //starnimate.setScaleType(ImageView.ScaleType.FIT_XY);
        starnimate.setImageTintList(ColorStateList.valueOf(getColor(R.color.gold)));
        FrameLayout.LayoutParams p = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        p.gravity = Gravity.CENTER;
        p.height = star.getHeight();
        p.width = star.getWidth();
        starnimate.setTranslationX(layout.getTranslationX());
        starnimate.setTranslationY(layout.getTranslationY());
        starnimate.setLayoutParams(p);
        starnimate.setScaleX(3f);
        starnimate.setScaleY(3f);
        starnimate.setAlpha(0f);
        ((FrameLayout)getRootView()).addView(starnimate);
        PropertyValuesHolder sX = PropertyValuesHolder.ofFloat("scaleX", 0f, 4f);
        PropertyValuesHolder sY = PropertyValuesHolder.ofFloat("scaleY", 0f, 4f);
        PropertyValuesHolder a = PropertyValuesHolder.ofFloat("alpha", 0f, 1f);
        ValueAnimator animator = ObjectAnimator.ofPropertyValuesHolder(starnimate, sX, sY);
        ValueAnimator alphaAnimator = ObjectAnimator.ofPropertyValuesHolder(starnimate, a);
        alphaAnimator.setDuration(300);
        animator.setDuration(1200);
        animator.setInterpolator(new BounceInterpolator());
        animator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                moveStarnimate(starnimate, star, new Collector() {
                    @Override
                    public void collect(Object o) {
                        removeLetters(true);
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
        alphaAnimator.start();
        animator.start();
    }


    void moveStarnimate(final ImageButton starnimate, final ImageButton star, final Collector collector){
        int[] locs = new int[2];
        star.getLocationInWindow(locs);
        float destX = getRootView().getWidth() / 2 - (getRootView().getWidth() - locs[0]) + (star.getWidth()/2);
        float destY = -(getRootView().getHeight() / 2) + (locs[1]);
        Log.d("Moving starnimate to : "+destX + " : "+ destY);
        PropertyValuesHolder x = PropertyValuesHolder.ofFloat("translationX", starnimate.getTranslationX(), destX);
        PropertyValuesHolder y = PropertyValuesHolder.ofFloat("translationY", starnimate.getTranslationY(), destY);
        PropertyValuesHolder sx = PropertyValuesHolder.ofFloat("scaleX", starnimate.getScaleX(), 1);
        PropertyValuesHolder sy = PropertyValuesHolder.ofFloat("scaleY", starnimate.getScaleY(), 1);

        ValueAnimator animator = ObjectAnimator.ofPropertyValuesHolder(starnimate, x, y, sx, sy);
        animator.setInterpolator(new AnticipateInterpolator());
        animator.setDuration(1000);
        animator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                star.setImageTintList(ColorStateList.valueOf(getColor(R.color.gold)));
                ((FrameLayout)getRootView()).removeView(starnimate);
                if (collector != null)
                    collector.collect(null);
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

    void fail(final FrameLayout layout, int x_, int y_){
        Log.d("Failing");
        fail++;

        PropertyValuesHolder x = PropertyValuesHolder.ofFloat("translationX", layout.getTranslationX(), x_);
        PropertyValuesHolder y = PropertyValuesHolder.ofFloat("translationY", layout.getTranslationY(), y_);
        ValueAnimator animator = ObjectAnimator.ofPropertyValuesHolder(layout, x, y);
        animator.setDuration(800);
        animator.setInterpolator(new AccelerateInterpolator());
        animator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                settle(false);
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

    void removeLetters(final boolean scoreOrFail){
        int childCount = animationContainer.getChildCount();
        for(int i = 0; i < childCount; i++){
            final View child = animationContainer.getChildAt(i);
            ValueAnimator animator = ViewAnimator.popOutZero(animationContainer.getChildAt(i), (i+1) * 100, 300 );
            animator.addListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animation) {

                }

                @Override
                public void onAnimationEnd(Animator animation) {
                    animationContainer.removeView(child);
                }

                @Override
                public void onAnimationCancel(Animator animation) {

                }

                @Override
                public void onAnimationRepeat(Animator animation) {

                }
            });
            if(i == childCount-1){
                animator.addListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animator animation) {
                        removeCurrentLetter(scoreOrFail);
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
    }

    void removeCurrentLetter(final boolean scoreOrFail){
        settle(scoreOrFail);
    }

    void settle(boolean scoreOrFail){
        animating = false;
        if(fail>2){
            failed();
        }else if(score>2){
            passed();
        }else {
            if(scoreOrFail){
                refresh();
            }
        }
    }

    void failed(){

    }

    void passed(){
        Log.d("Passed");
        if(alphabet.getLowerCase().equals("a")) {
            done_prev.setVisibility(View.GONE);
        }
        if(alphabet.getLowerCase().equals("z")){
            done_next.setVisibility(View.GONE);
        }
        ViewAnimator.upDownify(done_next, 10, 0, 1000);
        ViewAnimator.upDownify(done_prev, 10, 0, 1000);
        done_next.setClickable(true);
        done_prev.setClickable(true);
        ViewAnimator.popOutZero(animationContainer, 0, 300);
        ViewAnimator.popOutZero(animationBar, 0, 300);
        ViewAnimator.popOutZero(next, 0, 300);
        ViewAnimator.popOutZero(prev, 0, 300);
        ViewAnimator.popOutZero(starBar, 0, 400);
        ViewAnimator.popInZero(findViewById(R.id.winView), 0, 400);
        ViewAnimator.springify(done_next, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = Factory.Alphabets.getPosition(alphabet.getLowerCase());
                position++;
                Activity.replace(LetterIdentificationFragment.getInstance(Factory.Alphabets.build(Factory.Alphabets.ALPHABETS_LOWERCASE.get(position)), textColor, borderColor, null));
            }
        });

        ViewAnimator.springify(done_prev, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = Factory.Alphabets.getPosition(alphabet.getLowerCase());
                position--;
                Activity.replace(LetterIdentificationFragment.getInstance(Factory.Alphabets.build(Factory.Alphabets.ALPHABETS_LOWERCASE.get(position)), textColor, borderColor, null));
            }
        });
    }

    void reset(){
        score = 0;
        ColorStateList tint = ColorStateList.valueOf(getColor(R.color.ccc));
        star1.setImageTintList(tint);
        star2.setImageTintList(tint);
        star3.setImageTintList(tint);
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

    public LetterIdentificationFragment setLevel(int level){
        this.level = level; return this;
    }

    Map<Integer, Integer[]> getPositions( int level){

        int size = getResources().getDimensionPixelSize(R.dimen.buttonSize);

        int yTop = -(animationContainer.getHeight() / 2) + (size/2)  + animationContainer.getPaddingTop();
        int yMid = yTop + Math.abs((yTop)/4);
        int yBottom = 0;
        int xLeft = -(animationContainer.getWidth() / 2) + (size/2)  + animationContainer.getPaddingLeft();
        int xMidLeft = xLeft/2;
        int xMid = 0;
        int xRight = (animationContainer.getWidth()  / 2) - (size/2) - animationContainer.getPaddingRight();
        int xMidRight = xRight/2;

        Integer[] _1 = new Integer[]{xLeft, yBottom};
        Integer[] _2 = new Integer[]{xMidLeft, yMid};
        Integer[] _3 = new Integer[]{xMid, yTop};
        Integer[] _4 = new Integer[]{xMidRight, yMid};
        Integer[] _5 = new Integer[]{xRight, yBottom};
        Map<Integer, Integer[]> positions = new HashMap<>();
        switch (level){
            case 1:
                positions.put(0, _3);
                break;
            case 2 :
                positions.put(0, _2);
                positions.put(1, _4);
                break;
            case 3:
                positions.put(0, _2);
                positions.put(1, _3);
                positions.put(2, _4);
                break;
            case 4:
                positions.put(0, _1);
                positions.put(1, _2);
                positions.put(2, _4);
                positions.put(3, _5);
                break;
            case 5: default:
                positions.put(0, _1);
                positions.put(1, _2);
                positions.put(2, _3);
                positions.put(3, _4);
                positions.put(4, _5);
                break;
        }
        return positions;
    }

}
