package com.aun.tela.alphabets.application.gui.fragments;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.aun.tela.alphabets.R;
import com.aun.tela.alphabets.application.gui.activity.Activity;
import com.aun.tela.alphabets.application.util.ViewAnimator;

import io.meengle.androidutil.gui.fragment.Fragtivity;

public class AlphaLearning1 extends Fragtivity {

    TextView alphabetText;
    String alphabet;
    int color;
    View back;

    public static AlphaLearning1 getInstance(String alphabet, TextView textView){
        AlphaLearning1 f = new AlphaLearning1().setAlphabet(alphabet).setColor(textView.getCurrentTextColor());
        return f;
    }

    public AlphaLearning1 setAlphabet(String alphabet){
        this.alphabet = alphabet; return this;
    }

    public AlphaLearning1 setColor(int color){
        this.color = color; return this;
    }

    @Override
    public int layoutId() {
        return R.layout.alpha_learning_layout1;
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
        alphabetText = (TextView) findViewById(R.id.alphabet);
        back = findViewById(R.id.back);
    }

    @Override
    public void setupViews() {
        String text = alphabet.toUpperCase() + alphabet.toLowerCase();
        alphabetText.setText(text);
        alphabetText.setTextColor(color);

        ViewAnimator.springify(back);
        ViewAnimator.upDownify(back, 20, 300, 700);
        back.setAlpha(0f);
        back.setVisibility(View.VISIBLE);
        ViewAnimator.popInZero(back, 200, 300);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Activity.getInstance().onBackPressed();
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


}
