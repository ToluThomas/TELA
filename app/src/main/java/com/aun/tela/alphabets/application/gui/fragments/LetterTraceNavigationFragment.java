package com.aun.tela.alphabets.application.gui.fragments;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.TextView;

import com.aun.tela.alphabets.R;
import com.aun.tela.alphabets.application.entities.Factory;
import com.aun.tela.alphabets.application.generic.Collector;
import com.aun.tela.alphabets.application.generic.DoubleConsumer;
import com.aun.tela.alphabets.application.generic.QuatroCollector;
import com.aun.tela.alphabets.application.gui.activity.Activity;
import com.aun.tela.alphabets.application.gui.adapter.GenericItemAdapter;
import com.aun.tela.alphabets.application.gui.custom.CircularColorView;
import com.aun.tela.alphabets.application.gui.custom.HeaderFooterGridView;
import com.aun.tela.alphabets.application.gui.custom.SquareImageView;
import com.aun.tela.alphabets.application.util.ViewAnimator;

import java.util.List;
import java.util.Random;

import io.meengle.androidutil.gui.fragment.Fragtivity;
import io.meengle.util.Value;

/**
 * Created by Joseph Dalughut on 29/12/15 at 6:22 PM.
 * Project name : Alphabets.
 * Copyright (c) 2015 Meengle. All rights reserved.
 */

/**
 * This fragment displays a grid of alphabets from which the user can select an alphabet to learn about
 */
public class LetterTraceNavigationFragment extends Fragtivity {

    CircularColorView back, scrollDown, scrollUp;
    HeaderFooterGridView alphabetGrid;
    int textColor, borderColor;
    GenericItemAdapter<String, ViewHolder> adapter;
    Collector backCollector;

    public static LetterTraceNavigationFragment getInstance(int textColor, int borderColor, Collector backCollector){
        return new LetterTraceNavigationFragment().setTextColor(textColor).setBorderColor(borderColor).setBackCollector(backCollector);
    }

    public LetterTraceNavigationFragment setBackCollector(Collector backCollector){
        this.backCollector = backCollector; return this;
    }

    public LetterTraceNavigationFragment setTextColor(int textColor){
        this.textColor = textColor; return this;
    }

    public LetterTraceNavigationFragment setBorderColor(int borderColor){
        this.borderColor = borderColor; return this;
    }

    @Override
    public int layoutId() {
        return R.layout.fragment_watch_letter;
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
        back = (CircularColorView) findViewById(R.id.back);
        alphabetGrid = (HeaderFooterGridView) findViewById(R.id.alphaGrid);
        scrollDown = (CircularColorView) findViewById(R.id.scrollDown);
        scrollUp = (CircularColorView) findViewById(R.id.scrollUp);
    }

    @Override
    public void setupViews() {

        Activity.setColor(borderColor); //set our statusbar color

        //set the color of our buttons based on the two colors that were passed
        //back.setBorderColor(borderColor);
        back.setCircularColor(textColor);
        //scrollDown.setBorderColor(borderColor);
        scrollDown.setCircularColor(textColor);
        scrollUp.setCircularColor(textColor);
        //scrollUp.setBorderColor(borderColor);

        //add spring animations to our buttons
        ViewAnimator.springify(back, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Activity.replace(LetterNavigationFragment.getInstance(textColor, borderColor, new Collector() {
                    @Override
                    public void collect(Object o) {
                        Activity.replace(new NavigationFragment());
                    }
                }));
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

        //add up-down animation to our buttons
        ViewAnimator.upDownify(back, 20, 300, 700);
        ViewAnimator.upDownify(scrollDown, -20, 500, 700);
        ViewAnimator.upDownify(scrollUp, 20, 500, 700);
        ViewAnimator.popInZero(back, 20, 300);

        //get a copy of the alphabets from Factory class
        List<String> items = Factory.Alphabets.copyAlphabetsUppercase();
        final Random rand = new Random();

        //create adapter of alphabets to set to our gridview
        adapter = GenericItemAdapter.<String, ViewHolder>getInstance()
                .setItems(items)
                .setIdConsumer(new DoubleConsumer<Long, String, Integer>() {
                    @Override
                    public Long consume(String s, Integer integer) {
                        return Value.LONG(integer);
                    }
                }).setViewConsumer(new DoubleConsumer<ViewHolder, ViewGroup, Integer>() {
                    @Override
                    public ViewHolder consume(ViewGroup viewGroup, Integer integer) {
                        return ViewHolder.inflateDefault(viewGroup);
                    }
                }).setViewCollector(new QuatroCollector<ViewHolder, String, Integer, Boolean>() {
                    @Override
                    public void collect(ViewHolder viewHolder, final String s, final Integer integer, Boolean aBoolean) {
                        ViewHolder.setup(viewHolder, s, integer, aBoolean);
                        ViewAnimator.springifyScalable(viewHolder.itemView, new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                exit(s, v);
                            }
                        });
                        if (Value.NULL(viewHolder.itemView.getAnimation())) {
                            ViewAnimator.upDownify(viewHolder.itemView, 10, rand.nextInt(500), 800 + rand.nextInt(200));
                        }
                    }
                });


        addHeader(textColor, borderColor); // attach a header to the top of our gridview

        //set our created adapter to our gridView
        alphabetGrid.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                if(firstVisibleItem != 0 ){
                    if(scrollUp.getAlpha() < 1){
                        ViewAnimator.popInZero(scrollUp, 0, 300);
                    }
                }else{
                    if(scrollUp.getAlpha() > 0){
                        ViewAnimator.popOutZero(scrollUp, 0, 300);
                    }
                }
                if(view.getLastVisiblePosition()+1 != totalItemCount ){
                    if(scrollDown.getAlpha() < 1){
                        ViewAnimator.popInZero(scrollDown, 0, 300);
                    }
                }else{
                    if(scrollDown.getAlpha() > 0){
                        ViewAnimator.popOutZero(scrollDown, 0, 300);
                    }
                }
            }
        });
        alphabetGrid.setAdapter(adapter);
        alphabetGrid.smoothScrollToPosition(0);

        alphabetGrid.postDelayed(new Runnable() {
            @Override
            public void run() {
                if(alphabetGrid.getLastVisiblePosition()+1 != adapter.getCount() ){
                    if(scrollDown.getAlpha() < 1){
                        ViewAnimator.popInZero(scrollDown, 0, 300);
                    }
                }else{
                    if(scrollDown.getAlpha() > 0){
                        ViewAnimator.popOutZero(scrollDown, 0, 300);
                    }
                }
            }
        }, 500);
        //register a scroll listener to our gridview so we can show scrollUIp and scrollDown buttons only when needed
    }

    /**
     * Add header and footer views to our gridview.
     * @param color
     * @param borderColor
     */
    void addHeader(int color, int borderColor){
        View v = LayoutInflater.from(Activity.getInstance()).inflate(R.layout.alpha_image_header, null, false); //inflate a layout from resources

        //the inflated layout contains a textview and a barColorView which we use here
        SquareImageView i =  (SquareImageView) v.findViewById(R.id.detailImage);
        i.setImageResource(R.drawable.letter_trace_bevel);
        alphabetGrid.addHeaderView(v);
        ViewAnimator.springify(i, null);
        //ViewAnimator.upDownify(v, 10, 500, 1000);
        View u = LayoutInflater.from(Activity.getInstance()).inflate(R.layout.alpha_empty_footer, null, false);
        alphabetGrid.addFooterView(u);
    }

    //scroll down the grid by moving to a position that offsets the current position by 3, since we have three columns for our grid
    void scrollDown(){
        alphabetGrid.smoothScrollToPosition(alphabetGrid.getLastVisiblePosition() + 3);
    }

    //scroll up the grid by moving to a position that offsets the current position by -3, since we have three columns for our grid
    void scrollUp(){
        alphabetGrid.smoothScrollToPosition(alphabetGrid.getFirstVisiblePosition() - 3);
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


    /*
    Viewholder class for our gridview. Read more about ViewHolder pattern online
     */
    static class ViewHolder extends RecyclerView.ViewHolder {

        TextView text;

        public ViewHolder(View itemView) {
            super(itemView);
            text = (TextView) findViewById(R.id.text);
        }

        View findViewById(int resId){
            return this.itemView.findViewById(resId);
        }

        public static ViewHolder inflateDefault(ViewGroup parent){
            return new ViewHolder(LayoutInflater.from(Activity.getInstance()).inflate(R.layout.grid_item_trace_text, parent, false));
        }

        public static ViewHolder setup(ViewHolder viewHolder, String string, Integer position, Boolean isLast){
            viewHolder.text.setText(string.toUpperCase());

            viewHolder.itemView.setClickable(true);
            viewHolder.text.setTag(position);
            return viewHolder;
        }

    }

    /**
     * Animate into the next fragment
     * @param string the selected alphabet
     * @param view the view that would be used for the animation
     */
    private void exit(final String string, final View view){
        Activity.replace(LetterTraceFragment.getInstance(textColor, borderColor, Factory.Alphabets.build(string), new Collector() {
            @Override
            public void collect(Object o) {
                Activity.replace(LetterTraceNavigationFragment.getInstance(textColor, borderColor, new Collector() {
                    @Override
                    public void collect(Object o) {
                        Activity.replace(LetterNavigationFragment.getInstance(textColor, borderColor, new Collector() {
                            @Override
                            public void collect(Object o) {
                                Activity.replace(new NavigationFragment());
                            }
                        }));
                    }
                }));
            }
        }));
    }



}
