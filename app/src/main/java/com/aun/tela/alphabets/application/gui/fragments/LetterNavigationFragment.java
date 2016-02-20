package com.aun.tela.alphabets.application.gui.fragments;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ImageView;

import com.aun.tela.alphabets.R;
import com.aun.tela.alphabets.application.generic.Collector;
import com.aun.tela.alphabets.application.generic.DoubleConsumer;
import com.aun.tela.alphabets.application.generic.QuatroCollector;
import com.aun.tela.alphabets.application.gui.activity.Activity;
import com.aun.tela.alphabets.application.gui.adapter.GenericItemAdapter;
import com.aun.tela.alphabets.application.gui.custom.ArialTextView;
import com.aun.tela.alphabets.application.gui.custom.BarColorView;
import com.aun.tela.alphabets.application.gui.custom.CircularColorView;
import com.aun.tela.alphabets.application.gui.custom.HeaderFooterGridView;
import com.aun.tela.alphabets.application.gui.custom.SquareImageView;
import com.aun.tela.alphabets.application.util.ViewAnimator;

import java.util.ArrayList;
import java.util.List;

import io.meengle.androidutil.gui.fragment.Fragtivity;

/**
 * Created by Joey on 18/02/16 at 3:07 PM.
 * Project : TELA.
 * Copyright (c) 2016 Meengle. All rights reserved.
 */
public class LetterNavigationFragment extends Fragtivity {

    HeaderFooterGridView grid;
    CircularColorView scrollUp, scrollDown, back;
    ImageView grass1, grass2, grass3, grass4;
    Collector backCollector;
    GenericItemAdapter<Item, ViewHolder> adapter;
    int textColor = com.aun.tela.alphabets.application.util.Color.random(), borderColor = Color.WHITE;

    public static LetterNavigationFragment getInstance(int textColor, int borderColor, Collector backCollector){
        return new LetterNavigationFragment().setBackCollector(backCollector).setColors(textColor, borderColor);
    }

    public LetterNavigationFragment setBackCollector(Collector collector){
        this.backCollector = collector; return this;
    }

    public LetterNavigationFragment setColors(int textColor, int borderColor){this.textColor = textColor; this.borderColor = borderColor; return this;}

    @Override
    public int layoutId() {
        return R.layout.fragment_navigation_letter;
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
        grid = (HeaderFooterGridView) findViewById(R.id.grid);
        scrollUp = (CircularColorView) findViewById(R.id.scrollUp);
        scrollDown = (CircularColorView) findViewById(R.id.scrollDown);
        back = (CircularColorView) findViewById(R.id.back);

        grass1 = (ImageView) findViewById(R.id.grass1);
        grass2 = (ImageView) findViewById(R.id.grass2);
        grass3 = (ImageView) findViewById(R.id.grass3);
        grass4 = (ImageView) findViewById(R.id.grass4);

    }

    @Override
    public void setupViews() {
        back.setCircularColor(textColor);
        scrollDown.setCircularColor(textColor);
        scrollUp.setCircularColor(textColor);
        Activity.setColor(textColor);
        List<Item> items = new ArrayList<>();
        items.add(new Item(0, R.drawable.letter_watch_bevel));
        items.add(new Item(1, R.drawable.letter_identification_bevel));
        items.add(new Item(2, R.drawable.letter_trace_bevel));

        ViewAnimator.popInZero(back, 200, 300);
        ViewAnimator.upDownify(back, 10, 200, 800);
        ViewAnimator.upDownify(scrollDown, 10, 1100, 800);
        ViewAnimator.upDownify(scrollUp, 10, 2000, 800);

        ViewAnimator.springify(back, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (backCollector != null) {
                    backCollector.collect(null);
                    return;
                }
                Activity.replace(new NavigationFragment());
            }
        });

        ViewAnimator.springify(scrollDown, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                scrollDown();
            }
        });
        ViewAnimator.springify(scrollUp, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                scrollUp();
            }
        });


        adapter = GenericItemAdapter.<Item, ViewHolder>getInstance()
                .setIdConsumer(new DoubleConsumer<Long, Item, Integer>() {
                    @Override
                    public Long consume(Item item, Integer integer) {
                        return integer.longValue();
                    }
                })
                .setItems(items)
                .setViewCollector(new QuatroCollector<ViewHolder, Item, Integer, Boolean>() {
                    @Override
                    public void collect(ViewHolder viewHolder, final Item item, Integer integer, Boolean aBoolean) {
                        viewHolder.getImageView().setImageResource(item.imageRes);
                        viewHolder.itemView.setClickable(true);
                        ViewAnimator.springify(viewHolder.itemView, new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                select(item.id);
                            }
                        });
                    }
                })
                .setViewConsumer(new DoubleConsumer<ViewHolder, ViewGroup, Integer>() {
                    @Override
                    public ViewHolder consume(ViewGroup viewGroup, Integer integer) {
                        return ViewHolder.inflateDefault(viewGroup);
                    }
                });
        addHeaders();
        grid.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                if (grid.getChildAt(0)!=null && grid.getChildAt(0).getY() < 0) {
                    if (scrollUp.getAlpha() < 1) {
                        ViewAnimator.popInZero(scrollUp, 0, 300);
                    }
                } else {
                    if (scrollUp.getAlpha() > 0) {
                        ViewAnimator.popOutZero(scrollUp, 0, 300);
                    }
                }
                if (view.getLastVisiblePosition() + 1 != totalItemCount) {
                    if (scrollDown.getAlpha() < 1) {
                        ViewAnimator.popInZero(scrollDown, 0, 300);
                    }
                } else {
                    if (scrollDown.getAlpha() > 0) {
                        ViewAnimator.popOutZero(scrollDown, 0, 300);
                    }
                }
            }
        });

        grid.setAdapter(adapter);
        grid.smoothScrollToPosition(0);

        grid.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (grid.getLastVisiblePosition() + 1 != adapter.getCount()) {
                    if (scrollDown.getAlpha() < 1) {
                        ViewAnimator.popInZero(scrollDown, 0, 300);
                    }
                } else {
                    if (scrollDown.getAlpha() > 0) {
                        ViewAnimator.popOutZero(scrollDown, 0, 300);
                    }
                }
            }
        }, 500);

        ViewAnimator.leftRightify(grass1, 4, 1000, 4000);
        ViewAnimator.leftRightify(grass2, 4, 450, 2500);
        ViewAnimator.leftRightify(grass3, 4, 200, 3000);
        ViewAnimator.leftRightify(grass4, 4, 800, 3500);
    }

    void scrollDown(){
        grid.smoothScrollBy(grid.getChildAt(0).getHeight(), 300);
        //grid.smoothScrollToPosition(grid.getLastVisiblePosition() + 3);
    }

    //scroll up the grid by moving to a position that offsets the current position by -3, since we have three columns for our grid
    void scrollUp(){
        grid.smoothScrollToPosition(grid.getFirstVisiblePosition() - 3);
    }

    void addHeaders(){
        View v = LayoutInflater.from(Activity.getInstance()).inflate(R.layout.alpha_choice_header, grid, false); //inflate a layout from resources

        //the inflated layout contains a textview and a barColorView which we use here
        ArialTextView text = (ArialTextView) v.findViewById(R.id.text);
        BarColorView bar = (BarColorView) v.findViewById(R.id.bar);
        ViewAnimator.springify(bar, null);
        bar.setBarColor(textColor);
        bar.setBorderColor(Color.WHITE);
        text.setTextColor(Color.WHITE);
        text.setText("ABC");
        grid.addHeaderView(v);
        //ViewAnimator.upDownify(v, 10, 500, 1000);
        View u = LayoutInflater.from(Activity.getInstance()).inflate(R.layout.alpha_empty_footer, grid, false);
        grid.addFooterView(u);
    }

    void select(int id){
        switch (id){
            case 0:
                Activity.replace(LetterWatchFragment.getInstance(textColor, borderColor, new Collector() {
                    @Override
                    public void collect(Object o) {
                        Activity.replace(new LetterNavigationFragment());
                    }
                }));
                break;
            case 1:
                Activity.replace(LetterIdentificationNavigationFragment.getInstance(textColor, borderColor, new Collector() {
                    @Override
                    public void collect(Object o) {
                        Activity.replace(new LetterNavigationFragment());
                    }
                }));
                break;
            case 2:
                Activity.replace(LetterTraceNavigationFragment.getInstance(textColor, borderColor, new Collector() {
                    @Override
                    public void collect(Object o) {
                        Activity.replace(new LetterNavigationFragment());
                    }
                }));
                break;
        }
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

    private class Item {
        private int imageRes;
        private int id;
        public Item(int id, int imageRes){
            this.id = id; this.imageRes = imageRes;
        }
    }

    private static class ViewHolder extends RecyclerView.ViewHolder{

        SquareImageView image;

        public SquareImageView getImageView(){
            return this.image;
        }

        public static ViewHolder inflateDefault(ViewGroup viewGroup){
            return new ViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.grid_item_image, viewGroup, false));
        }

        public ViewHolder(View itemView) {
            super(itemView);
            image = (SquareImageView) itemView.findViewById(R.id.image);
        }
    }
}
