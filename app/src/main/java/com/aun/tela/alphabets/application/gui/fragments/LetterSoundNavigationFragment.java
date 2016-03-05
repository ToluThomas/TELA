package com.aun.tela.alphabets.application.gui.fragments;

import android.animation.Animator;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.aun.tela.alphabets.R;
import com.aun.tela.alphabets.application.entities.Factory;
import com.aun.tela.alphabets.application.generic.DoubleConsumer;
import com.aun.tela.alphabets.application.generic.QuatroCollector;
import com.aun.tela.alphabets.application.gui.activity.Activity;
import com.aun.tela.alphabets.application.gui.adapter.GenericRecyclerViewItemAdapter;
import com.aun.tela.alphabets.application.gui.custom.CircularColorView;
import com.aun.tela.alphabets.application.util.Color;
import com.aun.tela.alphabets.application.util.Log;
import com.aun.tela.alphabets.application.util.ViewAnimator;

import java.util.List;
import java.util.Random;

import io.meengle.androidutil.gui.fragment.Fragtivity;
import io.meengle.util.Value;

/**
 * Created by Joey on 28/02/16 at 6:28 PM.
 * Project : TELA.
 * Copyright (c) 2016 Meengle. All rights reserved.
 */
public class LetterSoundNavigationFragment extends Fragtivity{

    int textColor, borderColor;
    RecyclerView recyclerView;
    GenericRecyclerViewItemAdapter<String, ViewHolder> adapter;
    CircularColorView back, left, right;

    public static LetterSoundNavigationFragment getInstance(int textColor, int borderColor){
        return new LetterSoundNavigationFragment().setInstanceStuff(textColor, borderColor);
    }

    private LetterSoundNavigationFragment setInstanceStuff(int textColor, int borderColor){
        this.textColor = textColor; this.borderColor = borderColor; return this;
    }

    ImageView cloudLeft, cloudRight;

    @Override
    public int layoutId() {
        return R.layout.fragment_letter_navigation_sound;
    }

    @Override
    public void destroy() {

    }

    @Override
    public void destroyView() {
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
        back = (CircularColorView) findViewById(R.id.back);
        left = (CircularColorView) findViewById(R.id.left);
        right = (CircularColorView) findViewById(R.id.right);

    }

    @Override
    public void setupViews() {
        Random random = new Random();
        ViewAnimator.leftRightify(cloudLeft, 10, random.nextInt(300), 2000);
        ViewAnimator.leftRightify(cloudRight, -10, random.nextInt(700), 2000);

        ViewAnimator.upDownify(findViewById(R.id.rainbow), -5, 200,  2000);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerView.setLayoutManager(layoutManager);

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

        ViewAnimator.popInZero(left, 130, 300);
        ViewAnimator.popInZero(right, 200, 300);
        ViewAnimator.popInZero(back, 0, 300);
        ViewAnimator.springify(back, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                exit();
            }
        });

        ViewAnimator.upDownify(back, 10, 0, 1000);
        ViewAnimator.upDownify(left, 10, 400, 1000);
        ViewAnimator.upDownify(right, 10, 200, 1000);

        ViewAnimator.springify(left, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                prev();
            }
        });

        ViewAnimator.springify(right, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                next();
            }
        });

        left.setCircularColor(textColor);
        right.setCircularColor(textColor);
        back.setCircularColor(textColor);

        ViewAnimator.fadeIn(findViewById(R.id.ui), 300, 300);

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
        try{
            recyclerView.smoothScrollBy(recyclerView.getChildAt(0).getWidth() * 3, 0);
        }catch (Exception e){

        }
    }

    void prev(){
        try{
            recyclerView.smoothScrollBy(-recyclerView.getChildAt(0).getWidth() * 3, 0);
        }catch (Exception e){

        }
    }

    void select(String letter){
        int currentPosition = Factory.Alphabets.getPosition(letter);
        Factory.Alphabets.LetterSound l = Factory.Alphabets.LetterSound.build(Factory.Alphabets.getAlphabetsUppercase().get(currentPosition));
        exit(l);
    }

    void exit(final Factory.Alphabets.LetterSound letterSound){
        View hud = findViewById(R.id.hud);
        View ui = findViewById(R.id.ui);
        ViewAnimator.fadeOut(hud, 0, 500);
        ViewAnimator.fadeOut(ui, 200, 500).addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                Activity.replace(LetterSoundFragment.getInstance(textColor, borderColor, letterSound));
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
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
                Activity.replace(LetterNavigationFragment.getInstance(textColor, borderColor, null));
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
