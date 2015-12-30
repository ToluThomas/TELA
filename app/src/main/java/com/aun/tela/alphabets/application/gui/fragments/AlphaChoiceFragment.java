package com.aun.tela.alphabets.application.gui.fragments;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.animation.ValueAnimator;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AnticipateOvershootInterpolator;
import android.widget.AbsListView;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.TextView;

import com.aun.tela.alphabets.R;
import com.aun.tela.alphabets.application.generic.DoubleRetriever;
import com.aun.tela.alphabets.application.generic.QuatroConsumer;
import com.aun.tela.alphabets.application.gui.activity.Activity;
import com.aun.tela.alphabets.application.gui.adapter.GenericItemAdapter;
import com.aun.tela.alphabets.application.gui.custom.CircularView;
import com.aun.tela.alphabets.application.util.Color;
import com.aun.tela.alphabets.application.util.Log;
import com.aun.tela.alphabets.application.util.ViewAnimator;

import java.util.List;

import io.meengle.androidutil.gui.fragment.Fragtivity;
import io.meengle.util.Value;

/**
 * Created by Joseph Dalughut on 29/12/15 at 6:22 PM.
 * Project name : Alphabets.
 * Copyright (c) 2015 Meengle. All rights reserved.
 */
public class AlphaChoiceFragment extends Fragtivity {

    View back, scrollDown, scrollUp;
    String exitString = null;
    Integer exitPosition = null;
    GridView alphaGrid;
    GenericItemAdapter<String, ViewHolder> adapter;
    List<String> items = Value.As.<String>LIST("A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z");

    @Override
    public int layoutId() {
        return R.layout.fragment_alphabet_layout;
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
        back = findViewById(R.id.back);
        scrollDown = findViewById(R.id.scrollDown);
        scrollUp = findViewById(R.id.scrollUp);
        alphaGrid = (GridView) findViewById(R.id.alphaGrid);
    }

    @Override
    public void setupViews() {
        Activity.setColor(Color.random());
        ViewAnimator.springify(back);
        ViewAnimator.springify(scrollDown);
        ViewAnimator.springify(scrollUp);
        ViewAnimator.upDownify(back, 20, 300, 700);
        ViewAnimator.upDownify(scrollDown, -20, 500, 700);
        ViewAnimator.upDownify(scrollUp, 20, 500, 700);
        adapter = GenericItemAdapter.<String, ViewHolder>getInstance()
                .setItems(items)
                .setIdRetriever(new DoubleRetriever<Long, String, Integer>() {
                    @Override
                    public Long retrieve(String s, Integer integer) {
                        return Value.LONG(integer);
                    }
                }).setViewRetriever(new DoubleRetriever<ViewHolder, ViewGroup, Integer>() {
                    @Override
                    public ViewHolder retrieve(ViewGroup viewGroup, Integer integer) {
                        return ViewHolder.inflateDefault(viewGroup);
                    }
                }).setViewConsumer(new QuatroConsumer<ViewHolder, String, Integer, Boolean>() {
                    @Override
                    public void consume(ViewHolder viewHolder, final String s, final Integer integer, Boolean aBoolean) {
                        ViewHolder.setup(viewHolder, s, integer, aBoolean);
                        viewHolder.itemView.setScaleX(1f);
                        viewHolder.itemView.setScaleY(1f);
                        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                exit(s, integer, v);
                            }
                        });
                        if(exiting()){
                            viewHolder.itemView.setClickable(false);
                        }
                    }
                });
        alphaGrid.setAdapter(adapter);
        scrollDown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                scrollDown();
            }
        });
        scrollUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                scrollUp();
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Activity.getInstance().onBackPressed();
            }
        });
        alphaGrid.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                ValueAnimator a = Value.Same.INTEGER(firstVisibleItem, 0) ? ViewAnimator.popOut(scrollUp, 0, 300) : ViewAnimator.popIn(scrollUp, 0, 300);
                ValueAnimator b = Value.Same.INTEGER(firstVisibleItem + visibleItemCount, totalItemCount) ? ViewAnimator.popOut(scrollDown, 0, 300) : ViewAnimator.popIn(scrollDown, 0, 300);
            }
        });
    }

    void scrollDown(){
        alphaGrid.smoothScrollToPosition(alphaGrid.getLastVisiblePosition() + 3);
    }

    void scrollUp(){
        alphaGrid.smoothScrollToPosition(alphaGrid.getFirstVisiblePosition() - 3);
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
        CircularView circularView;

        public ViewHolder(View itemView) {
            super(itemView);
            text = (TextView) findViewById(R.id.text);
            circularView = (CircularView) findViewById(R.id.circularView);
        }

        View findViewById(int resId){
            return this.itemView.findViewById(resId);
        }

        public static ViewHolder inflateDefault(ViewGroup parent){
            return new ViewHolder(LayoutInflater.from(Activity.getInstance()).inflate(R.layout.item_alpha, parent, false));
        }

        public static ViewHolder setup(ViewHolder viewHolder, String string, Integer position, Boolean isLast){
            String text = string.toUpperCase() + string.toLowerCase();
            viewHolder.text.setText(text);
            viewHolder.text.setTextColor(Color.random());
            ViewAnimator.springify(viewHolder.itemView);
            viewHolder.itemView.setClickable(true);
            viewHolder.text.setTag(position);
            viewHolder.circularView.setTag(position);
            return viewHolder;
        }

    }

    boolean exiting(){
        return !Value.NULL(exitString);
    }

    private void exit(String string, int position, View view){
        exitString = string;
        exitPosition = position;
        adapter.notifyDataSetChanged();
        alphaGrid.setEnabled(false);
        CircularView circularView = (CircularView) view.findViewById(R.id.circularView);
        final TextView textView = (TextView) view.findViewById(R.id.text);
        scrollUp.setClickable(false);
        scrollDown.setClickable(false);
        back.setClickable(false);
        ViewAnimator.popOut(scrollUp, 0, 200);
        ViewAnimator.popOut(scrollDown, 0, 200);
        ViewAnimator.popOut(back, 0, 200);

        int x = (getRootView().getWidth() / 2);
        int y = (getRootView().getHeight()/ 2);
        Log.d("Mid x: "+x);
        Log.d("Mid y: "+y);
        float textX = textView.getX();
        float textY = textView.getY();
        Log.d("TextView x: " + textX);
        Log.d("TextView y: " + textY);
        Log.d("View x:" + view.getX());
        Log.d("View y:" + view.getY());

        float midX = view.getX() + (view.getWidth() / 2) - getResources().getDimensionPixelSize(R.dimen.activity_horizontal_margin) + getResources().getDimensionPixelSize(R.dimen.actionBarSize);
        float midY = view.getY() + (view.getHeight() / 2);

        ((FrameLayout) view).removeView(textView);
        alphaGrid.removeViewInLayout(view);
        Rect viewRect = new Rect();
        view.getGlobalVisibleRect(viewRect);
        Log.d("Rect is: " + viewRect.toString());
        FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) textView.getLayoutParams();
        params.gravity = Gravity.TOP | Gravity.START | Gravity.LEFT;
        CircularView newView = new CircularView(getContext());
        newView.setCircularColor(circularView.getCircularColor());
        newView.setBorderColor(circularView.getBorderColor());
        newView.addShadow();
        newView.setBorderWidth((circularView.getBorderWidth()));
        FrameLayout.LayoutParams paramsNew = new FrameLayout.LayoutParams(view.getWidth(), view.getHeight());
        paramsNew.height = view.getHeight();
        paramsNew.width = view.getWidth();
        paramsNew.gravity = Gravity.TOP | Gravity.START | Gravity.LEFT;
        newView.setLayoutParams(paramsNew);

        view.setVisibility(View.GONE);
        view = newView;
        ((FrameLayout) getRootView()).addView(view, ((FrameLayout) getRootView()).getChildCount(), paramsNew);
        ((FrameLayout) getRootView()).addView(textView, ((FrameLayout) getRootView()).getChildCount(), params);
        view.setTranslationX(midX - (paramsNew.width / 2) + ((getResources().getDimensionPixelSize(R.dimen.activity_horizontal_margin)) * 2));
        view.setTranslationY(midY - (paramsNew.height / 2));
        textView.setTranslationX(midX - (textView.getWidth() / 2));
        textView.setTranslationY(midY - (textView.getWidth() / 2));
        Log.d("Txfirst is: " + textView.getTranslationX());
        Log.d("Tyfirst is: "+textView.getTranslationY());
        float tvx = textView.getTranslationX();
        float tvy = textView.getTranslationY();
        //float w = Math.abs(x - tvx);
        //float h = Math.abs(y - tvy);
        //Float hypotenuse =((Double)Math.sqrt( ((Float)((w * w) + (h * h))).doubleValue())).floatValue();
        long durationByDistance = /*10 * (hypotenuse.longValue() < 1 ? 1 : hypotenuse.longValue());*/ 800;
        PropertyValuesHolder tx = PropertyValuesHolder.ofFloat("translationX", tvx, x  - textView.getWidth() / 2);
        PropertyValuesHolder ty = PropertyValuesHolder.ofFloat("translationY", tvy, y  - textView.getHeight() / 2);
        PropertyValuesHolder vtx = PropertyValuesHolder.ofFloat("translationX", view.getTranslationX(), x  - view.getWidth() / 2);
        PropertyValuesHolder vty = PropertyValuesHolder.ofFloat("translationY", view.getTranslationY(), y  - view.getHeight() / 2);
        PropertyValuesHolder vsx = PropertyValuesHolder.ofFloat("scaleX", view.getScaleX(), getRootView().getWidth() + paramsNew.width / paramsNew.width);
        PropertyValuesHolder vsy = PropertyValuesHolder.ofFloat("scaleY", view.getScaleX(), getRootView().getHeight()+ paramsNew.height / paramsNew.height);
        int targetSX = getResources().getDimensionPixelSize(R.dimen.text_size_display4) / textView.getWidth();
        int targetSy = getResources().getDimensionPixelSize(R.dimen.text_size_display4) / textView.getHeight();
        PropertyValuesHolder sx = PropertyValuesHolder.ofFloat("scaleX", textView.getScaleX(), targetSX);
        PropertyValuesHolder sy = PropertyValuesHolder.ofFloat("scaleX", textView.getScaleY(), targetSy);
        ValueAnimator centerTextAnimator = ObjectAnimator.ofPropertyValuesHolder(textView, tx, ty);
        ValueAnimator centerViewAnimator = ObjectAnimator.ofPropertyValuesHolder(view, vtx, vty, vsx, vsy);
        centerViewAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
        centerViewAnimator.setDuration(1800);
        centerTextAnimator.setInterpolator(new AnticipateOvershootInterpolator());
        centerTextAnimator.setDuration(durationByDistance);
        Activity.setColor(circularView.getBorderColor());
        centerViewAnimator.start();
        centerTextAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                Activity.replace(AlphaLearning1.getInstance(exitString, textView));
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        centerTextAnimator.start();
    }



}
