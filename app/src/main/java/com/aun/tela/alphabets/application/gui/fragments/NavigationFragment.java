package com.aun.tela.alphabets.application.gui.fragments;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.aun.tela.alphabets.R;
import com.aun.tela.alphabets.application.generic.Collector;
import com.aun.tela.alphabets.application.gui.activity.Activity;
import com.aun.tela.alphabets.application.gui.custom.BarColorView;
import com.aun.tela.alphabets.application.gui.custom.CircularColorView;
import com.aun.tela.alphabets.application.util.Color;
import com.aun.tela.alphabets.application.util.ViewAnimator;

import java.util.Random;

import io.meengle.androidutil.gui.fragment.Fragtivity;

/**
 * Created by Joey on 07/02/16 at 10:00 PM.
 * Project : TELA.
 * Copyright (c) 2016 Meengle. All rights reserved.
 */
public class NavigationFragment extends Fragtivity {

    BarColorView alphabets, numbers;
    CircularColorView back;
    int contextColor = Color.random();
    int borderColor = Color.random();
    ImageView grass1, grass2, grass3, grass4;

    @Override
    public int layoutId() {
        return R.layout.fragment_navigation;
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
        alphabets = (BarColorView) findViewById(R.id.alphabets);
        numbers = (BarColorView) findViewById(R.id.numbers);
        back = (CircularColorView) findViewById(R.id.back);

        grass1 = (ImageView) findViewById(R.id.grass1);
        grass2 = (ImageView) findViewById(R.id.grass2);
        grass3 = (ImageView) findViewById(R.id.grass3);
        grass4 = (ImageView) findViewById(R.id.grass4);
    }

    @Override
    public void setupViews() {
        back.setCircularColor(contextColor);
        Random rand = new Random();
        Activity.setColor(contextColor);
        ViewAnimator.upDownify(alphabets, 4, rand.nextInt(500), 1500);
        ViewAnimator.upDownify(numbers, 4, rand.nextInt(500), 1500);
        ViewAnimator.springify(alphabets, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Activity.replace(LetterNavigationFragment.getInstance(alphabets.getBarColor(), alphabets.getBorderColor(), new Collector() {
                    @Override
                    public void collect(Object o) {
                        Activity.replace(new NavigationFragment());
                    }
                }));
            }
        });
        ViewAnimator.springify(numbers, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //
            }
        });
        ViewAnimator.upDownify(back, 8, 300, 1500);
        ViewAnimator.springify(back, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                closeApp();
            }
        });

        ViewAnimator.popInZero(back, 300, 300);

        ViewAnimator.leftRightify(grass1, 4, 1000, 4000);
        ViewAnimator.leftRightify(grass2, 4, 450, 2500);
        ViewAnimator.leftRightify(grass3, 4, 200, 3000);
        ViewAnimator.leftRightify(grass4, 4, 800, 3500);

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


    void closeApp(){

    }
}
