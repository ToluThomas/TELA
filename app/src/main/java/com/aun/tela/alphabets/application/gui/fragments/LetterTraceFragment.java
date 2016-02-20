package com.aun.tela.alphabets.application.gui.fragments;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.aun.tela.alphabets.R;
import com.aun.tela.alphabets.application.entities.Factory;
import com.aun.tela.alphabets.application.generic.Collector;
import com.aun.tela.alphabets.application.generic.DoubleConsumer;
import com.aun.tela.alphabets.application.generic.QuatroCollector;
import com.aun.tela.alphabets.application.gui.activity.Activity;
import com.aun.tela.alphabets.application.gui.adapter.GenericRecyclerViewItemAdapter;
import com.aun.tela.alphabets.application.gui.custom.CircularColorView;
import com.aun.tela.alphabets.application.util.ViewAnimator;
import com.sothree.slidinguppanel.SlidingUpPanelLayout;

import java.util.List;
import java.util.Random;

import io.meengle.androidutil.gui.fragment.Fragtivity;
import io.meengle.util.Value;

/**
 * Created by Joey on 19/02/16 at 9:01 AM.
 * Project : TELA.
 * Copyright (c) 2016 Meengle. All rights reserved.
 */
public class LetterTraceFragment extends Fragtivity implements SlidingUpPanelLayout.PanelSlideListener {

    CircularColorView back, next, prev, refresh, panelButton;
    ImageButton refreshButton, chevron;
    int textColor,  borderColor;
    LetterTraceView letterTraceView;
    TextView alphabetText;
    Collector backCollector;
    Factory.Alphabets.Alphabet alphabet;
    RecyclerView recyclerView;
    SlidingUpPanelLayout slidingLayout;
    GenericRecyclerViewItemAdapter<String, ViewHolder> adapter;
    ImageView grass4, grass3, grass2, grass1;

    public static LetterTraceFragment getInstance(int textColor, int borderColor, Factory.Alphabets.Alphabet alphabet, Collector backCollector){
        return new LetterTraceFragment().setColors(textColor, borderColor).setAlphabet(alphabet).setCollector(backCollector);
    }

    public LetterTraceFragment setColors(int textColor, int borderColor){this.textColor = textColor; this.borderColor = borderColor; return this;}

    public LetterTraceFragment setAlphabet(Factory.Alphabets.Alphabet alphabet){this.alphabet = alphabet; return this;}

    public LetterTraceFragment setCollector(Collector backCollector){this.backCollector = backCollector; return this;}


    @Override
    public int layoutId() {
        return R.layout.fragment_letter_trace;
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
        next = (CircularColorView) findViewById(R.id.next);
        prev = (CircularColorView) findViewById(R.id.prev);
        refresh = (CircularColorView) findViewById(R.id.refresh);
        panelButton = (CircularColorView) findViewById(R.id.panelButton);
        refreshButton = (ImageButton) findViewById(R.id.refreshButton);
        chevron = (ImageButton) findViewById(R.id.chevron);
        alphabetText = (TextView) findViewById(R.id.alphabet);
        Typeface typeface = Typeface.createFromAsset(getResources().getAssets(), "arial.ttf");
        alphabetText.setTypeface(typeface);
        letterTraceView = (LetterTraceView) findViewById(R.id.traceView);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        slidingLayout = (SlidingUpPanelLayout) findViewById(R.id.slidingLayout);
        grass1 = (ImageView) findViewById(R.id.grass1);
        grass2 = (ImageView) findViewById(R.id.grass2);
        grass3 = (ImageView) findViewById(R.id.grass3);
        grass4 = (ImageView) findViewById(R.id.grass4);
    }

    @Override
    public void setupViews() {
        ViewAnimator.leftRightify(grass1, 4, 1000, 4000);
        ViewAnimator.leftRightify(grass2, 4, 450, 2500);
        ViewAnimator.leftRightify(grass3, 4, 200, 3000);
        ViewAnimator.leftRightify(grass4, 4, 800, 3500);
        ViewAnimator.popInZero(back, 200, 300);
        if(!alphabet.getLowerCase().equals("z"))
            ViewAnimator.popInZero(next, 300, 300);
        if(!alphabet.getLowerCase().equals("a"))
            ViewAnimator.popInZero(prev, 270, 300);
        ViewAnimator.popInZero(refresh, 250, 300);
        ViewAnimator.popInZero(panelButton, 350, 300);

        ViewAnimator.upDownify(back, 10, 200, 800);
        if(!alphabet.getLowerCase().equals("z"))
            ViewAnimator.upDownify(next, 10, 200, 800);
        if(!alphabet.getLowerCase().equals("a"))
            ViewAnimator.upDownify(prev, 10, 200, 800);
        ViewAnimator.upDownify(refresh, 10, 200, 800);

        back.setCircularColor(textColor);
        next.setCircularColor(textColor);
        prev.setCircularColor(textColor);
        refresh.setCircularColor(textColor);
        panelButton.setBorderColor(textColor);

        letterTraceView.setColor(textColor);

        ViewAnimator.springify(back, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (backCollector == null) {
                    Activity.replace(LetterTraceNavigationFragment.getInstance(textColor, borderColor, null));
                    return;
                }
                backCollector.collect(null);
            }
        });
        alphabetText.setText(alphabet.getUppercase());
        ViewAnimator.springify(next, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                moveToNextLetter(alphabet.getLowerCase());
            }
        });
        ViewAnimator.springify(prev, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                moveToPreviousLetter(alphabet.getLowerCase());
            }
        });
        ViewAnimator.springify(refresh, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ValueAnimator animator = ObjectAnimator.ofFloat(refreshButton, "rotation", 0, 360);
                animator.setDuration(200);
                animator.addListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animator animation) {
                        refreshButton.setRotation(0);
                        letterTraceView.erase();
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
        });

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerView.setLayoutManager(layoutManager);
        List<String> items = Factory.Alphabets.copyAlphabetsUppercase();
        items.add(0, "");
        items.add("");
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
                        viewHolder.itemView.setScaleX(1f);
                        viewHolder.itemView.setScaleY(1f);
                        ViewAnimator.springifyScalable(viewHolder.itemView, new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                moveToLetter(s);
                            }
                        });
                        if (Value.NULL(viewHolder.itemView.getAnimation())) {
                            ViewAnimator.upDownify(viewHolder.itemView, 10, rand.nextInt(500), 800 + rand.nextInt(200));
                        }
                    }
                }).setViewConsumer(new DoubleConsumer<ViewHolder, ViewGroup, Integer>() {
                    @Override
                    public ViewHolder consume(ViewGroup viewGroup, Integer integer) {
                        return ViewHolder.inflateDefault(viewGroup);
                    }
                }).setItems(items);
        recyclerView.setAdapter(adapter);
        slidingLayout.setPanelSlideListener(this);
        int slidingContentHeight = getResources().getDimensionPixelSize(R.dimen.buttonSize) + getResources().getDimensionPixelSize(R.dimen.alpha_learning_list_content_height);
        slidingLayout.setParallaxOffset(slidingContentHeight);

    }

    @Override
    public void onPanelSlide(View panel, float slideOffset) {
        chevron.setRotation(slideOffset * 180f);
    }

    @Override
    public void onPanelCollapsed(View panel) {
        ViewAnimator.popInZero(back, 0, 300);
        ViewAnimator.popInZero(refresh, 0, 500);

    }

    @Override
    public void onPanelExpanded(View panel) {
        ViewAnimator.popOutZero(back, 0, 300);
        ViewAnimator.popOutZero(refresh, 0, 500);
    }

    @Override
    public void onPanelAnchored(View panel) {

    }

    @Override
    public void onPanelHidden(View panel) {

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
            return new ViewHolder(LayoutInflater.from(Activity.getInstance()).inflate(R.layout.trace_item_image_text_h, parent, false));
        }

        public static ViewHolder setup(ViewHolder viewHolder, String string, Integer position, Boolean isLast) {
            if (Value.EMPTY(string)) {
                viewHolder.text.setTag(position);
                viewHolder.itemView.setVisibility(View.INVISIBLE);
                viewHolder.text.setText(string);
                return viewHolder;
            } else {
                viewHolder.text.setText(string.toUpperCase());
                viewHolder.itemView.setClickable(true);
                viewHolder.itemView.setVisibility(View.VISIBLE);
                viewHolder.text.setTag(position);
                return viewHolder;
            }
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

    void moveToNextLetter(String letter){
        int currentPosition = Factory.Alphabets.getPosition(alphabet.getLowerCase());
        final int nextPosition = currentPosition+1;
        Activity.replace(LetterTraceFragment.getInstance(textColor, borderColor, Factory.Alphabets.build(Factory.Alphabets.getAlphabetsLowercase().get(nextPosition)), null));
    }

    void moveToLetter(String letter){
        int currentPosition = Factory.Alphabets.getPosition(letter);
        Activity.replace(LetterTraceFragment.getInstance(textColor, borderColor, Factory.Alphabets.build(Factory.Alphabets.getAlphabetsLowercase().get(currentPosition)), null));
    }

    void moveToPreviousLetter(String letter){
        int currentPosition = Factory.Alphabets.getPosition(alphabet.getLowerCase());
        final int nextPosition = currentPosition-1;
        Activity.replace(LetterTraceFragment.getInstance(textColor, borderColor, Factory.Alphabets.build(Factory.Alphabets.getAlphabetsLowercase().get(nextPosition)), null));

    }
}
