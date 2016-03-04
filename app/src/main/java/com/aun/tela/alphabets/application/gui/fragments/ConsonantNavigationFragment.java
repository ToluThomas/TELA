package com.aun.tela.alphabets.application.gui.fragments;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.animation.ValueAnimator;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnticipateOvershootInterpolator;
import android.widget.FrameLayout;

import com.aun.tela.alphabets.R;
import com.aun.tela.alphabets.application.entities.Factory;
import com.aun.tela.alphabets.application.generic.Collector;
import com.aun.tela.alphabets.application.generic.DoubleConsumer;
import com.aun.tela.alphabets.application.generic.QuatroCollector;
import com.aun.tela.alphabets.application.gui.activity.Activity;
import com.aun.tela.alphabets.application.gui.adapter.GenericRecyclerViewItemAdapter;
import com.aun.tela.alphabets.application.gui.custom.ArialTextView;
import com.aun.tela.alphabets.application.gui.custom.CircularColorView;
import com.aun.tela.alphabets.application.util.Log;
import com.aun.tela.alphabets.application.util.ViewAnimator;

import java.util.List;
import java.util.Random;

import io.meengle.androidutil.gui.fragment.Fragtivity;
import io.meengle.util.Value;

/**
 * Created by Joey on 26/02/16 at 9:49 PM.
 * Project : TELA.
 * Copyright (c) 2016 Meengle. All rights reserved.
 */
public class ConsonantNavigationFragment extends Fragtivity {

    CircularColorView back, left, right;
    RecyclerView recyclerView;
    int textColor, borderColor;
    GenericRecyclerViewItemAdapter<String, ViewHolder> adapter;
    int state = 0;
    ConsonantAnimationFragment animationFragment;

    public static ConsonantNavigationFragment getInstance(int textColor, int borderColor){
        return new ConsonantNavigationFragment().setColors(textColor, borderColor);
    }

    public ConsonantNavigationFragment setColors(int textColor, int borderColor){
        this.textColor = textColor; this.borderColor = borderColor; return this;
    }

    @Override
    public int layoutId() {
        return R.layout.fragment_navigation_consonants;
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
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        back = (CircularColorView) findViewById(R.id.back);
        left = (CircularColorView) findViewById(R.id.left);
        right = (CircularColorView) findViewById(R.id.right);
    }

    @Override
    public void setupViews() {
        back.setCircularColor(textColor);
        left.setCircularColor(textColor);
        right.setCircularColor(textColor);
        ViewAnimator.popInZero(back, 100, 300);
        ViewAnimator.popInZero(left, 300, 300);
        ViewAnimator.popInZero(right, 500, 300);
        ViewAnimator.upDownify(back, 10, 100, 1000);
        ViewAnimator.upDownify(left, 10, 300, 1000);
        ViewAnimator.upDownify(right, 10, 500, 1000);
        ViewAnimator.fadeIn(findViewById(R.id.ui), 0, 300);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerView.setLayoutManager(layoutManager);
        resetAdapter();
        recyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {

            }


        });
        ViewAnimator.springify(back, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                exit();
            }
        });
        ViewAnimator.springify(left, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                left();
            }
        });
        ViewAnimator.springify(right, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                right();
            }
        });
    }

    void exit(){
        switch(state){
            case 0:
                View hud = findViewById(R.id.hud);
                View ui = findViewById(R.id.ui);
                ViewAnimator.fadeOut(hud, 0, 500);
                ViewAnimator.fadeOut(ui, 200, 500).addListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animator animation) {
                        Activity.replace(LetterNavigationFragment.getInstance(textColor, borderColor, null));
                    }

                    @Override
                    public void onAnimationCancel(Animator animation) {

                    }

                    @Override
                    public void onAnimationRepeat(Animator animation) {

                    }
                });
                break;
            case 1:
                revertToList(animationFragment.consonant.value);
                break;
        }

    }

    void left(){
        switch (state) {
            case 0 :
                recyclerView.smoothScrollBy((-recyclerView.getChildAt(2).getWidth() * 2), 0);
                break;
            case 1:
                try {
                    switchToAnimation(Factory.Alphabets.CONSONANTS_LOWERCASE.get(Factory.Alphabets.Consonant.getPosition(animationFragment.consonant.value)-1).toUpperCase(), null);
                }catch (Exception e){

                }
                break;
        }
    }

    void right(){
        switch (state) {
            case 0:
                recyclerView.smoothScrollBy((recyclerView.getChildAt(2).getWidth() * 2), 0);
                break;
            case 1:
                try {
                    switchToAnimation(Factory.Alphabets.CONSONANTS_LOWERCASE.get(Factory.Alphabets.Consonant.getPosition(animationFragment.consonant.value)+1).toUpperCase(), null);
                }catch (Exception e){

                }
                break;
        }
    }

    void select(View v){
        final ArialTextView arialTextView = (ArialTextView) v.findViewById(R.id.text);
        final String alphabet = arialTextView.getText().toString();

        FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) arialTextView.getLayoutParams();
        int[] locs = new int[2];
        arialTextView.getLocationInWindow(locs);
        final float cx = locs[0] - getRootView().getWidth() / 2 ;
        final float cy = locs[1] - getRootView().getHeight()/2 ;
        Log.d("cx is "+cx);
        Log.d("cy is "+cy);

        final float vcx = 0;
        final float vcy = 0;

        Log.d("vcx is "+vcx);
        Log.d("vcy is " + vcy);

        ((FrameLayout) v).removeView(arialTextView);
        Log.d("View removed");
        arialTextView.setTranslationX(cx);
        arialTextView.setTranslationY(cy);

        ((FrameLayout)findViewById(R.id.animationContainer)).removeAllViews();
        findViewById(R.id.animationContainer).setVisibility(View.VISIBLE);
        findViewById(R.id.animationContainer).setAlpha(1);
        ((FrameLayout)findViewById(R.id.animationContainer)).addView(arialTextView, params);
        Log.d("View added to rootView");
        Log.d("View stuff now text : "+arialTextView.getText().toString() + ", x: "+arialTextView.getX() + " translationX : "+arialTextView.getTranslationX()
            + ", y : "+arialTextView.getY() + ", "+arialTextView.getTranslationY() + ", visibility: "+arialTextView.getVisibility()
        );

        ViewAnimator.fadeOut(findViewById(R.id.ui), 0, 500);
        PropertyValuesHolder x = PropertyValuesHolder.ofFloat("translationX", cx, vcx);
        PropertyValuesHolder y = PropertyValuesHolder.ofFloat("translationY", cy, vcy);
        float size = getResources().getInteger(R.integer.text_size_display4);
        float from = getResources().getInteger(R.integer.text_size_display3);
        //PropertyValuesHolder ts = PropertyValuesHolder.ofFloat("textSize", from, size);
        ValueAnimator animator = ObjectAnimator.ofPropertyValuesHolder(arialTextView, x, y);
        animator.setInterpolator(new AnticipateOvershootInterpolator());
        animator.setDuration(500);
        animator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                Log.d("animation ended, switching to animation container");
                try{
                    switchToAnimation(alphabet, new Collector() {
                        @Override
                        public void collect(Object o) {
                            findViewById(R.id.animationContainer).postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    try {
                                        ((FrameLayout) findViewById(R.id.animationContainer)).removeView(arialTextView);
                                    } catch (Exception e) {

                                    }
                                }
                            }, 200);
                        }
                    });
                }catch (Exception e){
                    Log.d("Exception was "+e.getMessage());
                }
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

    void switchToAnimation(String consonant, Collector collector){
        state = 1;
        if(animationFragment!=null)
            getChildFragmentManager().beginTransaction().remove(animationFragment).commitAllowingStateLoss();
        this.animationFragment = ConsonantAnimationFragment.getInstance(textColor, borderColor, Factory.Alphabets.Consonant.build(consonant));
        getChildFragmentManager().beginTransaction().replace(R.id.animationContainer, animationFragment).commitAllowingStateLoss();
        if(collector!=null)
            collector.collect(null);
    }

    void resetAdapter(){
        List<String> consonants = Factory.Alphabets.copyAlphabetConsonants();
        consonants.add(0, "");
        consonants.add(0, "");
        consonants.add(consonants.size(), "");
        consonants.add(consonants.size(), "");
        final Random rand = new Random();
        adapter = GenericRecyclerViewItemAdapter.<String, ViewHolder>getInstance()
                .setIdConsumer(new DoubleConsumer<Long, String, Integer>() {
                    @Override
                    public Long consume(String s, Integer integer) {
                        return integer.longValue();
                    }
                })
                .setItems(consonants)
                .setViewConsumer(new DoubleConsumer<ViewHolder, ViewGroup, Integer>() {
                    @Override
                    public ViewHolder consume(ViewGroup viewGroup, Integer integer) {
                        return ViewHolder.inflateDefault(viewGroup);
                    }
                })
                .setViewCollector(new QuatroCollector<ViewHolder, String, Integer, Boolean>() {
                    @Override
                    public void collect(ViewHolder viewHolder, String s, Integer integer, Boolean aBoolean) {
                        viewHolder.textView.setTag(integer);
                        if(s==null || s.isEmpty()) {
                            viewHolder.textView.setText("");
                            viewHolder.itemView.setVisibility(View.INVISIBLE);
                            return;
                        }
                        viewHolder.itemView.setVisibility(View.VISIBLE);
                        viewHolder.textView.setText(s.toUpperCase());
                        ViewAnimator.springify(viewHolder.itemView, new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                select(v);
                            }
                        });
                        if (Value.NULL(viewHolder.itemView.getAnimation())) {
                            ViewAnimator.upDownify(viewHolder.itemView, 5, rand.nextInt(500), 800 + rand.nextInt(200));
                        }
                    }
                });
        recyclerView.setAdapter(adapter);
    }

    void revertToList(final String consonant){
        state = 0;
        resetAdapter();
        ViewAnimator.fadeIn(findViewById(R.id.ui), 0, 500);
        ViewAnimator.fadeOut(findViewById(R.id.animationContainer), 0, 300).addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                try {
                    if (animationFragment != null)
                        getChildFragmentManager().beginTransaction().remove(animationFragment).commitAllowingStateLoss();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        recyclerView.post(new Runnable() {
            @Override
            public void run() {
                recyclerView.scrollToPosition(Factory.Alphabets.Consonant.getPosition(consonant) + 2);
            }
        });
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

    private static class ViewHolder extends RecyclerView.ViewHolder {

        ArialTextView textView;

        public ViewHolder(View itemView) {
            super(itemView);
            textView = (ArialTextView) findViewById(R.id.text);
        }

        private static ViewHolder inflateDefault(ViewGroup viewGroup){
            return new ViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_item_consonant, viewGroup, false));
        }

        private View findViewById(int resId){
            return itemView.findViewById(resId);
        }
    }

}
