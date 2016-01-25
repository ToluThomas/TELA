package com.aun.tela.alphabets.application.gui.fragments;

import android.animation.ValueAnimator;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.AbsListView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.aun.tela.alphabets.R;
import com.aun.tela.alphabets.application.generic.DoubleConsumer;
import com.aun.tela.alphabets.application.generic.QuatroCollector;
import com.aun.tela.alphabets.application.gui.activity.Activity;
import com.aun.tela.alphabets.application.gui.adapter.GenericItemAdapter;
import com.aun.tela.alphabets.application.gui.custom.BarColorView;
import com.aun.tela.alphabets.application.gui.custom.CircularColorView;
import com.aun.tela.alphabets.application.util.Color;
import com.aun.tela.alphabets.application.util.ViewAnimator;
import com.sothree.slidinguppanel.SlidingUpPanelLayout;

import java.util.List;
import java.util.Random;

import io.meengle.androidutil.gui.fragment.Fragtivity;
import io.meengle.util.Value;

<<<<<<< HEAD
=======
/**
 * Created by Joseph Dalughut on 03/01/16 at 11:12 AM.
 * Project name : TELA.
 * Copyright (c) 2015 Meengle. All rights reserved.
 */

/**
 * First fragment that is shown when activity starts. It contains a listView of items which direct the
 * user to a suitable learning screen
 */
>>>>>>> 6f985d95ba92fb5c71815fabe8a04fe66a0f7d7a
public class MainFragment extends Fragtivity implements SlidingUpPanelLayout.PanelSlideListener{

    View scrollDownButton, scrollUpButton;
    CircularColorView scrollDownButtonCircularColorView, scrollUpButtonCircularColorView;
    ImageButton bottomListRevealButton;
    SlidingUpPanelLayout slidingUpPanelLayout;
    BarColorView titleBar;
    TextView title;
    ListView listView;
    int textColor, borderColor;
    GenericItemAdapter<AdapterItem, ViewHolder> adapter;

    @Override
    public int layoutId() {
        return R.layout.fragment_main;
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
        scrollDownButton = findViewById(R.id.scrollDown);
        scrollUpButton = findViewById(R.id.scrollUp);
        bottomListRevealButton = (ImageButton) findViewById(R.id.chevron);
        slidingUpPanelLayout = (SlidingUpPanelLayout) findViewById(R.id.slidingLayout);
        titleBar = (BarColorView) findViewById(R.id.titleBar);
        title = (TextView) findViewById(R.id.title);
        listView = (ListView) findViewById(R.id.list);
        scrollDownButtonCircularColorView = (CircularColorView) findViewById(R.id.scrollDownCircularView);
        scrollUpButtonCircularColorView = (CircularColorView) findViewById(R.id.scrollUpCircularView);    }

    ViewTreeObserver.OnGlobalLayoutListener listener;

    @Override
    public void setupViews() {
        listener = new ViewTreeObserver.OnGlobalLayoutListener() { //this listener receives a callback when the view is
        // attched to the window. At this point, layoutAttributes like height and width should be ready, so we can setup
        // our views here, especially the ones which depend on layoutAttributes of other layouts.
            @Override
            public void onGlobalLayout() {
                //when we receive the first callback, remove the listener so as to prevent unexpected behaviour
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    getRootView().getViewTreeObserver().removeOnGlobalLayoutListener(listener);
                }else{
                    getRootView().getViewTreeObserver().removeGlobalOnLayoutListener(listener);
                }
                setup();
            }
        };
        getRootView().getViewTreeObserver().addOnGlobalLayoutListener(listener);
    }

    /**
     * This is the method which sets up the views for this layout
     */
    void setup(){
        textColor = Color.random();
        borderColor = Color.random();
        Activity.setColor(borderColor); //set the color for the status bar on android versions >= Kitkat

        //apply the same colors to other views
        scrollDownButtonCircularColorView.setBorderColor(borderColor);
        scrollDownButtonCircularColorView.setCircularColor(textColor);
        scrollUpButtonCircularColorView.setCircularColor(textColor);
        scrollUpButtonCircularColorView.setBorderColor(borderColor);

        //apply spring animation to our buttons
        ViewAnimator.springify(scrollDownButton, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                scrollDown();
            }
        });
        ViewAnimator.springify(scrollUpButton, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                scrollUp();
            }
        });

        //make scrollUpButton invisible, since at start the list will always be at it's topmost position, so no scrollingUp for now
        scrollUpButton.setAlpha(0);

        //apply up-down animation movement for our buttons
        ViewAnimator.upDownify(scrollDownButton, -10, 500, 700);
        ViewAnimator.upDownify(scrollUpButton, 10, 500, 700);

        //register an onscrollListener, so that we can make the scrollUp and scrollDown buttons visible when they need to be.
        listView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                ValueAnimator a = Value.Same.INTEGER(firstVisibleItem, 0) && scrollUpButton.getAlpha() < 1 ? ViewAnimator.popOut(scrollUpButton, 0, 300) : ViewAnimator.popIn(scrollUpButton, 0, 300);
                ValueAnimator b = Value.Same.INTEGER(firstVisibleItem + visibleItemCount, totalItemCount) && scrollDownButton.getAlpha() < 1 ? ViewAnimator.popOut(scrollDownButton, 0, 300) : ViewAnimator.popIn(scrollDownButton, 0, 300);
            }
        });

        //set slidingUpPanelLayout's parallax offset to the views height so that when it's scrolled up,
        // the whole view moves up, kinda like pagination
        slidingUpPanelLayout.setParallaxOffset(getRootView().getHeight());


        slidingUpPanelLayout.setEnabled(false);
       // ViewAnimator.popInZero(bottomListRevealButton, 500, 300);
       // ViewAnimator.upDownify(bottomListRevealButton, 10, 400, 700);
        slidingUpPanelLayout.setPanelSlideListener(this);
        ViewAnimator.upDownify(findViewById(R.id.titleBarLayout), 10, 500, 1000);
        title.setTextColor(textColor);
        titleBar.setBarColor(0xFFFFFFFF);
        titleBar.setBorderColor(borderColor);
        bottomListRevealButton.setClickable(false);
        slidingUpPanelLayout.setDragView(bottomListRevealButton);

        //create an adapter instance and set it to our listview.
        List<AdapterItem> items = Value.As.<AdapterItem>LIST(new AdapterItem(0, getString(R.string.alphabets), textColor, borderColor)/*, new AdapterItem(1, getString(R.string.vowels), textColor, b), new AdapterItem(2, getString(R.string.numbers), textColor, b)*/);
        final Random rand = new Random();
        adapter = GenericItemAdapter.<AdapterItem, ViewHolder>getInstance()
                .setItems(items)
                .setIdConsumer(new DoubleConsumer<Long, AdapterItem, Integer>() {
                    @Override
                    public Long consume(AdapterItem adapterItem, Integer integer) {
                        return integer.longValue();
                    }
                })
                .setViewCollector(new QuatroCollector<ViewHolder, AdapterItem, Integer, Boolean>() {
                    @Override
                    public void collect(ViewHolder viewHolder, final AdapterItem adapterItem, Integer integer, Boolean aBoolean) {
                        ViewHolder.setup(viewHolder, adapterItem, integer, aBoolean);
                        viewHolder.itemView.setOnTouchListener(new View.OnTouchListener() {
                            @Override
                            public boolean onTouch(View v, MotionEvent event) {
                                BarColorView titleBar = (BarColorView) v.findViewById(R.id.titleBar);
                                switch (event.getAction()) {
                                    case MotionEvent.ACTION_DOWN:
                                        titleBar.setBarColor(titleBar.getBorderColor());
                                        break;
                                    case MotionEvent.ACTION_UP:
                                        titleBar.setBarColor(0xFFFFFFFF);
                                        break;
                                }
                                return false;
                            }
                        });
                        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                exit(adapterItem, v);
                            }
                        });
                        if (Value.NULL(viewHolder.itemView.getAnimation())) {
                            ViewAnimator.upDownify(viewHolder.itemView, 4, rand.nextInt(500), 800 + rand.nextInt(200));
                        }
                    }
                })
                .setViewConsumer(new DoubleConsumer<ViewHolder, ViewGroup, Integer>() {
                    @Override
                    public ViewHolder consume(ViewGroup viewGroup, Integer integer) {
                        return ViewHolder.inflateDefault(viewGroup);
                    }
                });
        listView.setAdapter(adapter);
    }

    /**
     * Simple class to represent a listview item.
     */
    class AdapterItem {
        public int id;
        public String title;
        public int borderColor;
        public int textColor;

        public AdapterItem(int id, String title, int textColor, int borderColor){
            this.id = id; this.title = title; this.borderColor = borderColor; this.textColor = textColor;
        }
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        public static ViewHolder inflateDefault(ViewGroup viewGroup){
            return new ViewHolder(LayoutInflater.from(Activity.getInstance()).inflate(R.layout.item_list_navigation, viewGroup, false));
        }

        TextView title;
        BarColorView titleBar;

        public static ViewHolder setup(ViewHolder viewHolder, AdapterItem adapterItem, Integer position, Boolean isLast){
                viewHolder.titleBar.setBorderColor(Color.random());
                viewHolder.titleBar.setBarColor(0xFFFFFFFF);
                viewHolder.title.setText(adapterItem.title.toUpperCase());
                viewHolder.title.setTextColor(Color.random());
                viewHolder.itemView.setClickable(true);
                viewHolder.title.setTag(position);
                viewHolder.titleBar.setTag(position);
                return viewHolder;
        }

        public ViewHolder(View itemView) {
            super(itemView);
            title = (TextView) findViewById(R.id.title);
            titleBar = (BarColorView) findViewById(R.id.titleBar);
        }

        View findViewById(int resId){
            return itemView.findViewById(resId);
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

    @Override
    public void onPanelSlide(View panel, float slideOffset) {
        bottomListRevealButton.setRotation(slideOffset * 180f);
    }

    @Override
    public void onPanelCollapsed(View panel) {

    }

    @Override
    public void onPanelExpanded(View panel) {

    }

    @Override
    public void onPanelAnchored(View panel) {

    }

    @Override
    public void onPanelHidden(View panel) {

    }

    /**
     * Exit the fragment to a destination fragment based on the adapterItem that was selected
     * @param adapterItem
     * @param view
     */
    void exit(final AdapterItem adapterItem, View view){
        final BarColorView bar = (BarColorView) view.findViewById(R.id.titleBar);
        TextView title = (TextView) view.findViewById(R.id.title);
        final int textColor = title.getCurrentTextColor();
        final int borderColor = bar.getBorderColor();

        switch (adapterItem.id) {
            case 0:
                Activity.replace(AlphaChoiceFragment.getInstance(textColor, borderColor));
                break;
        }
    }

    void scrollDown(){
        listView.smoothScrollToPosition(listView.getLastVisiblePosition() + 1);
    }

    void scrollUp(){
        listView.smoothScrollToPosition(listView.getFirstVisiblePosition() - 1);
    }
}