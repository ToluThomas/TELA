package com.aun.tela.alphabets.application.gui.fragments;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.animation.ValueAnimator;
import android.graphics.Color;
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
import android.widget.ImageView;
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
import com.aun.tela.alphabets.application.util.Log;
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
public class LetterWatchFragment extends Fragtivity {

    CircularColorView back, scrollDown, scrollUp;
    HeaderFooterGridView alphabetGrid;
    int textColor, borderColor;
    GenericItemAdapter<String, ViewHolder> adapter;
    Collector backCollector;

    public static LetterWatchFragment getInstance(int textColor, int borderColor, Collector backCollector){
        return new LetterWatchFragment().setTextColor(textColor).setBorderColor(borderColor).setBackCollector(backCollector);
    }

    public LetterWatchFragment setBackCollector(Collector backCollector){
        this.backCollector = backCollector; return this;
    }

    public LetterWatchFragment setTextColor(int textColor){
        this.textColor = textColor; return this;
    }

    public LetterWatchFragment setBorderColor(int borderColor){
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
        i.setImageResource(R.drawable.letter_watch_bevel);
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
        ImageView image;

        public ViewHolder(View itemView) {
            super(itemView);
            text = (TextView) findViewById(R.id.text);
            image = (ImageView) findViewById(R.id.image);
        }

        View findViewById(int resId){
            return this.itemView.findViewById(resId);
        }

        public static ViewHolder inflateDefault(ViewGroup parent){
            return new ViewHolder(LayoutInflater.from(Activity.getInstance()).inflate(R.layout.grid_item_image_text, parent, false));
        }

        public static ViewHolder setup(ViewHolder viewHolder, String string, Integer position, Boolean isLast){
            viewHolder.text.setText(string.toUpperCase());
            viewHolder.text.setTextColor(viewHolder.text.getCurrentTextColor() == Color.WHITE ? com.aun.tela.alphabets.application.util.Color.random() : viewHolder.text.getCurrentTextColor());
            viewHolder.itemView.setClickable(true);
            viewHolder.text.setTag(position);
            int cs = Math.abs(((position+5)%4)-4);
            switch (cs){
                case 4:
                    viewHolder.image.setImageResource(R.drawable.tv_blue);
                    break;
                case 1:
                    viewHolder.image.setImageResource(R.drawable.tv_brown);
                    break;
                case 2:
                    viewHolder.image.setImageResource(R.drawable.tv_green);
                    break;
                case 3:
                    viewHolder.image.setImageResource(R.drawable.tv_purple);
                    break;
            }
            return viewHolder;
        }

    }

    /**
     * Animate into the next fragment
     * @param string the selected alphabet
     * @param view the view that would be used for the animation
     */
    private void exit(final String string, final View view){
        alphabetGrid.setEnabled(false);
        final TextView textView = (TextView) view.findViewById(R.id.text);

        ViewAnimator.popOutZero(scrollUp, 0, 200);
        ViewAnimator.popOutZero(scrollDown, 0, 200);
        ViewAnimator.popOutZero(back, 0, 200);

        //get center coordinates of the view.
        int x = (getRootView().getWidth() / 2);
        int y = (getRootView().getHeight()/ 2);

        float midX = view.getX() + (view.getWidth() / 2) - getResources().getDimensionPixelSize(R.dimen.activity_horizontal_margin) + getResources().getDimensionPixelSize(R.dimen.buttonSize);
        float midY = view.getY() + (view.getHeight() / 2);

        ((FrameLayout) view).removeView(textView);
        adapter.notifyDataSetChanged();
        alphabetGrid.removeViewInLayout(view);

        //create a new view at the selected views x and y coordinates, then animate it into the next view
        //basically what i did was add a new circularColorview and scale it a lot until it's out of the view
        //then add a textview which is moved to the center of the screen from the position of the textview in the
        //selected view.

        FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) textView.getLayoutParams();
        params.gravity = Gravity.TOP | Gravity.START | Gravity.LEFT;
        //ImageView newView = new SquareImageView(getContext());

        FrameLayout.LayoutParams paramsNew = new FrameLayout.LayoutParams(view.getWidth(), view.getHeight());
        paramsNew.height = view.getHeight();
        paramsNew.width = view.getWidth();
        paramsNew.gravity = Gravity.TOP | Gravity.START | Gravity.LEFT;
        //newView.setLayoutParams(paramsNew);
        //view = newView;
        ((FrameLayout) getRootView()).addView(view, ((FrameLayout) getRootView()).getChildCount(), paramsNew);
        ((FrameLayout) getRootView()).addView(textView, ((FrameLayout) getRootView()).getChildCount(), params);
        view.setTranslationX(midX - (paramsNew.width / 2) + ((getResources().getDimensionPixelSize(R.dimen.activity_horizontal_margin)) * 2));
        view.setTranslationY(midY - (paramsNew.height / 2));

        textView.setTranslationX(midX - (textView.getWidth() / 2));
        textView.setTranslationY(midY - (textView.getWidth() / 2));
        float tvx = textView.getTranslationX();
        float tvy = textView.getTranslationY();
        Log.d("View current Scale: "+view.getScaleX() + " : "+view.getScaleY());

        //float w = Math.abs(x - tvx);
        //float h = Math.abs(y - tvy);
        //Float hypotenuse =((Double)Math.sqrt( ((Float)((w * w) + (h * h))).doubleValue())).floatValue();
        long durationByDistance = /*10 * (hypotenuse.longValue() < 1 ? 1 : hypotenuse.longValue());*/ 800;
        PropertyValuesHolder tx = PropertyValuesHolder.ofFloat("translationX", tvx, x  - textView.getWidth() / 2);
        PropertyValuesHolder ty = PropertyValuesHolder.ofFloat("translationY", tvy, y  - textView.getHeight() / 2);
        PropertyValuesHolder vtx = PropertyValuesHolder.ofFloat("translationX", view.getTranslationX(), x  - view.getWidth() / 2);
        PropertyValuesHolder vty = PropertyValuesHolder.ofFloat("translationY", view.getTranslationY(), y  - view.getHeight() / 2);
        PropertyValuesHolder vsx = PropertyValuesHolder.ofFloat("scaleX", view.getScaleX(), getRootView().getWidth());
        PropertyValuesHolder vsy = PropertyValuesHolder.ofFloat("scaleY", view.getScaleY(), getRootView().getHeight());
        int targetSX = getResources().getDimensionPixelSize(R.dimen.text_size_display4) / textView.getWidth();
        int targetSy = getResources().getDimensionPixelSize(R.dimen.text_size_display4) / textView.getHeight();
        ValueAnimator centerTextAnimator = ObjectAnimator.ofPropertyValuesHolder(textView, tx, ty);
        final ValueAnimator centerViewAnimator = ObjectAnimator.ofPropertyValuesHolder(view, vtx, vty, vsx, vsy);
        centerViewAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
        centerViewAnimator.setDuration(2000);
        centerTextAnimator.setInterpolator(new AnticipateOvershootInterpolator());
        centerTextAnimator.setDuration(durationByDistance);
        Activity.setColor(textView.getCurrentTextColor());
        centerViewAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                Log.d("View current Scale: " + view.getScaleX() + " : " + view.getScaleY());
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        getRootView().post(new Runnable() {
            @Override
            public void run() {
                centerViewAnimator.start();
            }
        });
        centerTextAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                Activity.replace(AlphaLearningFragment.getInstance(Factory.Alphabets.build(string), textView.getCurrentTextColor(), textView.getCurrentTextColor()));
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
