package com.aun.tela.alphabets.application.gui.fragments;

import android.animation.Animator;
import android.content.res.ColorStateList;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.aun.tela.alphabets.R;
import com.aun.tela.alphabets.application.entities.Factory;
import com.aun.tela.alphabets.application.generic.DoubleConsumer;
import com.aun.tela.alphabets.application.generic.QuatroCollector;
import com.aun.tela.alphabets.application.gui.activity.Activity;
import com.aun.tela.alphabets.application.gui.adapter.GenericItemAdapter;
import com.aun.tela.alphabets.application.gui.custom.ArialTextView;
import com.aun.tela.alphabets.application.gui.custom.CircularColorView;
import com.aun.tela.alphabets.application.util.Log;
import com.aun.tela.alphabets.application.util.ViewAnimator;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import io.meengle.androidutil.gui.fragment.Fragtivity;
import se.emilsjolander.flipview.FlipView;

/**
 * Created by Joey on 28/02/16 at 1:15 AM.
 * Project : TELA.
 * Copyright (c) 2016 Meengle. All rights reserved.
 */
public class BookReadingFragment extends Fragtivity {

    FlipView flipView;
    CircularColorView back, left, right;
    int textColor, borderColor;
    Factory.Book book;
    GenericItemAdapter<Integer, ViewHolder> adapter;
    boolean peaked = false;

    public BookReadingFragment setInstanceStuff(int textColor, int borderColor, Factory.Book book){
        this.textColor = textColor; this.borderColor = borderColor; this.book = book; return this;
    }

    public static BookReadingFragment getInstance(int textColor, int borderColor, Factory.Book book){
        return new BookReadingFragment().setInstanceStuff(textColor, borderColor, book);
    }

    @Override
    public int layoutId() {
        return R.layout.fragment_bookview;
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
        flipView = (FlipView) findViewById(R.id.flipView);
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
        ViewAnimator.popInZero(right, 500, 300);
        //ViewAnimator.popInZero(left, 300, 300);
        ViewAnimator.upDownify(back, 10, 100, 1000);
        ViewAnimator.upDownify(left, 10, 300, 1000);
        ViewAnimator.upDownify(right, 10, 500, 1000);
        ViewAnimator.fadeIn(findViewById(R.id.ui), 0, 300);

        ViewAnimator.springify(back, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                exit();
            }
        });

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

        List<Integer> images = new ArrayList<>(book.getPageCount());
        for(int i : book.getPages()){
            images.add(i);
        }

        adapter = GenericItemAdapter.<Integer, ViewHolder>getInstance()
                .setIdConsumer(new DoubleConsumer<Long, Integer, Integer>() {
                    @Override
                    public Long consume(Integer res, Integer integer2) {
                        return integer2.longValue();
                    }
                })
                .setViewCollector(new QuatroCollector<ViewHolder, Integer, Integer, Boolean>() {
                    @Override
                    public void collect(final ViewHolder viewHolder, Integer res, final Integer integer2, Boolean aBoolean) {
                        viewHolder.progressBar.setVisibility(View.VISIBLE);
                        viewHolder.pageNumber.setText(String.valueOf(integer2+1));
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                            viewHolder.progressBar.getIndeterminateDrawable().setColorFilter(
                                    textColor,
                                    android.graphics.PorterDuff.Mode.SRC_IN);
                            viewHolder.progressBar.setProgressTintList(ColorStateList.valueOf(textColor));
                        }
                        Picasso.with(BookReadingFragment.this.getContext()).load(res).into(viewHolder.image, new Callback() {
                            @Override
                            public void onSuccess() {
                                try {
                                    viewHolder.progressBar.setVisibility(View.GONE);
                                    if (!peaked && integer2 == 0) {
                                        peaked = true;
                                        viewHolder.image.postDelayed(new Runnable() {
                                            @Override
                                            public void run() {
                                                try {
                                                    flipView.peakNext(true);
                                                }catch (Exception e){

                                                }
                                            }
                                        }, 500);
                                    }
                                }catch (Exception e){

                                }
                            }

                            @Override
                            public void onError() {
                                viewHolder.progressBar.setVisibility(View.VISIBLE);
                            }
                        });
                        viewHolder.image.setTag(integer2);
                    }
                })
                .setViewConsumer(new DoubleConsumer<ViewHolder, ViewGroup, Integer>() {
                    @Override
                    public ViewHolder consume(ViewGroup viewGroup, Integer integer) {
                        return new ViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.flip_item_page, viewGroup, false));
                    }
                })
                .setItems(images);
        flipView.setAdapter(adapter);
        flipView.setOnFlipListener(new FlipView.OnFlipListener() {
            @Override
            public void onFlippedToPage(FlipView v, int position, long id) {
                Log.d("Flipped to position "+position);
                if(position==0){
                    if(left.getAlpha()> 0){
                        ViewAnimator.popOutZero(left, 0, 300);
                    }
                }
                if(position > 0){
                    if(left.getAlpha() <1){
                        ViewAnimator.popInZero(left, 0, 300);
                    }
                }
                if(position == (book.getPageCount()-1)){
                    if(right.getAlpha() >0){
                        ViewAnimator.popOutZero(right, 0, 300);
                    }
                }
                if(position < (book.getPageCount()-1)){
                    if(right.getAlpha() < 1){
                        ViewAnimator.popInZero(right, 0, 300);
                    }
                }
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

        ImageView image;
        ProgressBar progressBar;
        ArialTextView pageNumber;

        public ViewHolder(View itemView) {
            super(itemView);
            image = (ImageView) findViewById(R.id.image);
            progressBar = (ProgressBar) findViewById(R.id.progress);
            pageNumber = (ArialTextView) findViewById(R.id.pageNumber);
        }

        private View findViewById(int resId){
            return itemView.findViewById(resId);
        }
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
                Activity.replace(ReadingNavigationFragment.getInstance(textColor, borderColor));
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
    }

    void next(){
        try {
            flipView.smoothFlipTo(flipView.getCurrentPage() + 1);
        }catch (Exception e){

        }
    }

    void prev(){
        try {
            flipView.smoothFlipTo(flipView.getCurrentPage() - 1);
        }catch (Exception e){

        }
    }
}

