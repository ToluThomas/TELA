package com.aun.tela.alphabets.application.gui.fragments;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.animation.ValueAnimator;
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
import android.widget.TextView;

import com.aun.tela.alphabets.R;
import com.aun.tela.alphabets.application.entities.Factory;
import com.aun.tela.alphabets.application.generic.DoubleConsumer;
import com.aun.tela.alphabets.application.generic.QuatroCollector;
import com.aun.tela.alphabets.application.gui.activity.Activity;
import com.aun.tela.alphabets.application.gui.adapter.GenericItemAdapter;
import com.aun.tela.alphabets.application.gui.custom.BarColorView;
import com.aun.tela.alphabets.application.gui.custom.CircularColorView;
import com.aun.tela.alphabets.application.gui.custom.HeaderFooterGridView;
import com.aun.tela.alphabets.application.util.Color;
import com.aun.tela.alphabets.application.util.ViewAnimator;

import java.util.List;
import java.util.Random;

import io.meengle.androidutil.gui.fragment.Fragtivity;
import io.meengle.util.Value;

<<<<<<< HEAD
=======
/**
 * Created by Joseph Dalughut on 29/12/15 at 6:22 PM.
 * Project name : Alphabets.
 * Copyright (c) 2015 Meengle. All rights reserved.
 */

/**
 * This fragment displays a grid of alphabets from which the user can select an alphabet to learn about
 */
>>>>>>> 6f985d95ba92fb5c71815fabe8a04fe66a0f7d7a
public class AlphaChoiceFragment extends Fragtivity {

    View backButton, scrollDownButton, scrollUpButton;
    CircularColorView backCircularColorView, scrollDownCircularColorView, scrollUpCircularColorView;
    HeaderFooterGridView alphabetGrid;
    int textColor, borderColor;
    GenericItemAdapter<String, ViewHolder> adapter;

    public static AlphaChoiceFragment getInstance(int textColor, int borderColor){
        return new AlphaChoiceFragment().setTextColor(textColor).setBorderColor(borderColor);
    }

    public AlphaChoiceFragment setTextColor(int textColor){
        this.textColor = textColor; return this;
    }

    public AlphaChoiceFragment setBorderColor(int borderColor){
        this.borderColor = borderColor; return this;
    }

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
        backButton = findViewById(R.id.back);
        scrollDownButton = findViewById(R.id.scrollDown);
        scrollUpButton = findViewById(R.id.scrollUp);
        alphabetGrid = (HeaderFooterGridView) findViewById(R.id.alphaGrid);
        backCircularColorView = (CircularColorView) findViewById(R.id.backCircularView);
        scrollDownCircularColorView = (CircularColorView) findViewById(R.id.scrollDownCircularView);
        scrollUpCircularColorView = (CircularColorView) findViewById(R.id.scrollUpCircularView);
    }

    @Override
    public void setupViews() {

        Activity.setColor(borderColor); //set our statusbar color

        //set the color of our buttons based on the two colors that were passed
        backCircularColorView.setBorderColor(borderColor);
        backCircularColorView.setCircularColor(textColor);
        scrollDownCircularColorView.setBorderColor(borderColor);
        scrollDownCircularColorView.setCircularColor(textColor);
        scrollUpCircularColorView.setCircularColor(textColor);
        scrollUpCircularColorView.setBorderColor(borderColor);

        //add spring animations to our buttons
        ViewAnimator.springify(backButton, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Activity.replace(new MainFragment());
            }
        });
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
        scrollUpButton.setAlpha(0);

        //add up-down animation to our buttons
        ViewAnimator.upDownify(backButton, 20, 300, 700);
        ViewAnimator.upDownify(scrollDownButton, -20, 500, 700);
        ViewAnimator.upDownify(scrollUpButton, 20, 500, 700);

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
                        ViewAnimator.springify(viewHolder.itemView, new View.OnClickListener() {
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
        alphabetGrid.setAdapter(adapter);

        //register a scroll listener to our gridview so we can show scrollUIp and scrollDown buttons only when needed
        alphabetGrid.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                ValueAnimator a = Value.Same.INTEGER(firstVisibleItem, 0) && scrollUpButton.getAlpha() < 1 ? ViewAnimator.popOut(scrollUpButton, 0, 300) : ViewAnimator.popIn(scrollUpButton, 0, 300);
                ValueAnimator b = Value.Same.INTEGER(firstVisibleItem + visibleItemCount, totalItemCount) && scrollDownButton.getAlpha() < 1 ? ViewAnimator.popOut(scrollDownButton, 0, 300) : ViewAnimator.popIn(scrollDownButton, 0, 300);
            }
        });
    }

    /**
     * Add header and footer views to our gridview.
     * @param color
     * @param borderColor
     */
    void addHeader(int color, int borderColor){
        View v = LayoutInflater.from(Activity.getInstance()).inflate(R.layout.alpha_choice_header, null, false); //inflate a layout from resources

        //the inflated layout contains a textview and a barColorView which we use here

        TextView textView = (TextView) v.findViewById(R.id.text);
        textView.setTextColor(color);
        BarColorView barColorView = (BarColorView) v.findViewById(R.id.barColorView);
        barColorView.setBorderColor(borderColor);
        barColorView.setBarColor(0xFFFFFFFF);
        alphabetGrid.addHeaderView(v);
        ViewAnimator.upDownify(v, 10, 500, 1000);
        View u = LayoutInflater.from(Activity.getInstance()).inflate(R.layout.alpha_choice_header, null, false);
        TextView t = (TextView) u.findViewById(R.id.text);
        u.findViewById(R.id.barColorView).setVisibility(View.INVISIBLE);
        t.setTextColor(getColor(R.color.transparent));
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
        CircularColorView circularColorView;

        public ViewHolder(View itemView) {
            super(itemView);
            text = (TextView) findViewById(R.id.text);
            circularColorView = (CircularColorView) findViewById(R.id.circularView);
        }

        View findViewById(int resId){
            return this.itemView.findViewById(resId);
        }

        public static ViewHolder inflateDefault(ViewGroup parent){
            return new ViewHolder(LayoutInflater.from(Activity.getInstance()).inflate(R.layout.item_alpha_w, parent, false));
        }

        public static ViewHolder setup(ViewHolder viewHolder, String string, Integer position, Boolean isLast){
            viewHolder.text.setText(string.toUpperCase());
            viewHolder.text.setTextColor(Color.random());
            viewHolder.itemView.setClickable(true);
            viewHolder.text.setTag(position);
            viewHolder.circularColorView.setTag(position);
            return viewHolder;
        }

    }

    /**
     * Animate into the next fragment
     * @param string the selected alphabet
     * @param view the view that would be used for the animation
     */
    private void exit(final String string, View view){
        adapter.notifyDataSetChanged();
        alphabetGrid.setEnabled(false);
        final CircularColorView circularColorView = (CircularColorView) view.findViewById(R.id.circularView);
        final TextView textView = (TextView) view.findViewById(R.id.text);

        //hide our buttons
        scrollUpButton.setClickable(false);
        scrollDownButton.setClickable(false);
        backButton.setClickable(false);
        ViewAnimator.popOut(scrollUpButton, 0, 200);
        ViewAnimator.popOut(scrollDownButton, 0, 200);
        ViewAnimator.popOut(backButton, 0, 200);

        //get center coordinates of the view.
        int x = (getRootView().getWidth() / 2);
        int y = (getRootView().getHeight()/ 2);
        float textX = textView.getX();
        float textY = textView.getY();

        float midX = view.getX() + (view.getWidth() / 2) - getResources().getDimensionPixelSize(R.dimen.activity_horizontal_margin) + getResources().getDimensionPixelSize(R.dimen.buttonSize);
        float midY = view.getY() + (view.getHeight() / 2);

        ((FrameLayout) view).removeView(textView);
        alphabetGrid.removeViewInLayout(view);

        //create a new view at the selected views x and y coordinates, then animate it into the next view
        //basically what i did was add a new circularColorview and scale it a lot until it's out of the view
        //then add a textview which is moved to the center of the screen from the position of the textview in the
        //selected view.

        FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) textView.getLayoutParams();
        params.gravity = Gravity.TOP | Gravity.START | Gravity.LEFT;
        CircularColorView newView = new CircularColorView(getContext());
        newView.setCircularColor(circularColorView.getCircularColor());
        newView.setBorderColor(circularColorView.getBorderColor());
        newView.addShadow();
        newView.setBorderWidth((circularColorView.getBorderWidth()));
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
        PropertyValuesHolder vsy = PropertyValuesHolder.ofFloat("scaleY", view.getScaleY(), getRootView().getHeight()+ paramsNew.height / paramsNew.height);
        int targetSX = getResources().getDimensionPixelSize(R.dimen.text_size_display4) / textView.getWidth();
        int targetSy = getResources().getDimensionPixelSize(R.dimen.text_size_display4) / textView.getHeight();
        PropertyValuesHolder sx = PropertyValuesHolder.ofFloat("scaleX", textView.getScaleX(), targetSX);
        PropertyValuesHolder sy = PropertyValuesHolder.ofFloat("scaleX", textView.getScaleY(), targetSy);
        ValueAnimator centerTextAnimator = ObjectAnimator.ofPropertyValuesHolder(textView, tx, ty);
        ValueAnimator centerViewAnimator = ObjectAnimator.ofPropertyValuesHolder(view, vtx, vty, vsx, vsy);
        centerViewAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
        centerViewAnimator.setDuration(2500);
        centerTextAnimator.setInterpolator(new AnticipateOvershootInterpolator());
        centerTextAnimator.setDuration(durationByDistance);
        Activity.setColor(circularColorView.getBorderColor());
        centerViewAnimator.start();
        centerTextAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                Activity.replace(AlphaLearningFragment.getInstance(Factory.Alphabets.build(string), textView.getCurrentTextColor(), circularColorView.getBorderColor()));
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