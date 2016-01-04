package com.aun.tela.alphabets.application.gui.fragments;

import android.animation.Animator;
import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.animation.ValueAnimator;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AnticipateOvershootInterpolator;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.aun.tela.alphabets.R;
import com.aun.tela.alphabets.application.entities.Factory;
import com.aun.tela.alphabets.application.generic.Collector;
import com.aun.tela.alphabets.application.generic.Consumer;
import com.aun.tela.alphabets.application.util.Log;
import com.aun.tela.alphabets.application.util.Speech;
import com.aun.tela.alphabets.application.util.ViewAnimator;

import java.util.HashMap;
import java.util.Map;

import io.meengle.androidutil.gui.fragment.Fragtivity;
import io.meengle.util.Value;

public class AlphaLearning3 extends Fragtivity implements Collector<View> {

    Factory.Alphabets.Alphabet alphabet;
    TextView alphabetUppercaseTop, alphabetLowercaseTop;
    int textColor, borderColor;
    LinearLayout texts;
    ImageView image;
    int itemTextSize = 0;
    int position;
    Map<Integer, Boolean> positions = new HashMap<>();
    Collector<Boolean> finishCollector;
    Map<String, Boolean> states = new HashMap<>();

    public static AlphaLearning3 getInstance(Factory.Alphabets.Alphabet alphabet, int position, int textColor, int borderColor, Collector<Boolean> finishCollector){
        AlphaLearning3 f = new AlphaLearning3().setAlphabet(alphabet).setPosition(position).setTextColor(textColor).setBorderColor(borderColor).setFinishedCollector(finishCollector);
        return f;
    }

    public AlphaLearning3 setPosition(int position){
        this.position = position; return this;
    }

    public AlphaLearning3 setFinishedCollector(Collector<Boolean> finishCollector) {
        this.finishCollector = finishCollector; return this;
    }

    public AlphaLearning3 setAlphabet(Factory.Alphabets.Alphabet alphabet){
        this.alphabet = alphabet; return this;
    }

    public AlphaLearning3 setTextColor(int color){
        this.textColor = color; return this;
    }

    public AlphaLearning3 setBorderColor(int color){
        this.borderColor = color; return this;
    }

    @Override
    public int layoutId() {
        return R.layout.alpha_learning_layout3;
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
        alphabetUppercaseTop = (TextView) findViewById(R.id.alphabetUppercaseTop);
        alphabetLowercaseTop = (TextView) findViewById(R.id.alphabetLowercaseTop);

        texts = (LinearLayout) findViewById(R.id.texts);
        image = (ImageView) findViewById(R.id.image);

    }

    @Override
    public void setupViews() {
        alphabetLowercaseTop.setTextColor(textColor);
        alphabetUppercaseTop.setTextColor(textColor);
        alphabetUppercaseTop.setText(alphabet.getUppercase());
        alphabetLowercaseTop.setText(alphabet.getLowerCase());

        build();
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

    void build(){
        final String animate = "build";
        final String sound = "playBuild";
        states.put(animate, false);
        states.put(sound, false);
        final Consumer<Boolean> finishedConsumer = new Consumer<Boolean>() {
            @Override
            public Boolean consume() {
                return states.get(animate) && states.get(sound);
            }
        };
        Factory.Alphabets.Alphabet.AlphabetInfo alphabetInfo = alphabet.getAlphabetInfoMap().get(position);
        playBuild(alphabetInfo, new Speech.VoiceCallback() {
            @Override
            public void onStart(String id) {

            }

            @Override
            public void onDone(String id) {
                states.put(sound, true);
                if(finishedConsumer.consume())
                    getRootView().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            dramatize();
                        }
                    }, 500);

            }

            @Override
            public void onError(String id, Integer errorCode) {

            }
        });
        char[] text = alphabetInfo.getTitle().toCharArray();
        int count = text.length;
        int res = count <=4 ? R.integer.text_size_display4 : count <= 8 ? R.integer.text_size_display3 : count <= 12?  R.integer.text_size_display2 : count <= 16? R.integer.text_size_display1 : R.integer.text_size_title;
        itemTextSize = getResources().getInteger(res);
        for(int i = 0; i < count; i++){
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            params.gravity = Gravity.BOTTOM;
            TextView textView = new TextView(getContext());
            textView.setTextColor(borderColor);
            textView.setTextSize(itemTextSize);
            textView.setTypeface(null, Typeface.BOLD);
            textView.setGravity(Gravity.BOTTOM);
            textView.setLayoutParams(params);
            texts.addView(textView, params);
            textView.setAlpha(0);
            String t = String.valueOf(text[i]);
            textView.setText(i < 1 ? t.toUpperCase() : t);

            if(Value.Same.STRING(t.toLowerCase(), alphabet.getLowerCase())) {
                textView.setTextColor(textColor);
                positions.put(i, i < 1);
            }
        }
        image.setAlpha(0f);
        if(!Value.Same.INTEGER(0, alphabetInfo.getImageRes()))
            image.setImageResource(alphabetInfo.getImageRes());

        int animatedCount = 0;
        for(int i = 0; i < count; i++){
            if(!positions.containsKey(i)){
                ViewAnimator.popIn(texts.getChildAt(i), animatedCount * 50l, 200);
                animatedCount++;
            }
        }
        ViewAnimator.popIn(image, count - positions.size() * 50l, 200).addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                states.put(animate, true);
                if(finishedConsumer.consume())
                    if(finishedConsumer.consume())
                        getRootView().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                dramatize();
                            }
                        }, 500);
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });

    }

    void playBuild(Factory.Alphabets.Alphabet.AlphabetInfo info, Speech.VoiceCallback callback){
        int res = 0;
        switch (position){
            case 0:
                res = alphabet.word1Info;
                break;
            case 1:
                res = alphabet.word2Info;
                break;
            case 2:
                res = alphabet.word3Info;
                break;
        }
        Speech.play(res, null, callback);
    }

    void dramatize(){
        long delay = 200;
        int done = 0;
        Animator.AnimatorListener listener = new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                getRootView().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        vocalize();
                    }
                }, 500);
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        };
        try {
            for (Map.Entry<Integer, Boolean> entry : positions.entrySet()) {
                done++;
                if (entry.getValue()) {
                    animateUppercaseToPosition(entry.getKey(), delay, done == positions.size() ? listener : null);
                } else {
                    animateLowercasetoPosition(entry.getKey(), delay, done == positions.size() ? listener : null);
                }
                delay += 50;
            }
        }catch (Exception e){
            Log.d(e.toString());
        }
    }

    void animateUppercaseToPosition(final int position, final long delay, final Animator.AnimatorListener listener){
        Log.d("Animating upper to position");
        final float size = getResources().getInteger(R.integer.text_size_display3);
        final TextView textView = new TextView(getContext());
        textView.setTextColor(textColor);
        textView.setText(alphabetUppercaseTop.getText());
        textView.setTextSize(size);
        textView.setTypeface(null, Typeface.BOLD);
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.gravity = Gravity.TOP | Gravity.LEFT;
        textView.setLayoutParams(params);
        textView.setVisibility(View.INVISIBLE);
        ((FrameLayout)getRootView()).addView(textView, params);
        textView.setX(alphabetUppercaseTop.getX());
        textView.setY(alphabetUppercaseTop.getY() + getResources().getDimensionPixelSize(R.dimen.activity_horizontal_margin));
        textView.setVisibility(View.VISIBLE);

        PropertyValuesHolder tx = PropertyValuesHolder.ofFloat("X", textView.getX(), texts.getChildAt(position).getX());
        PropertyValuesHolder ts = PropertyValuesHolder.ofFloat("textSize", size, itemTextSize);
        ValueAnimator animator = ObjectAnimator.ofPropertyValuesHolder(textView, tx, ts);
        ValueAnimator y = ObjectAnimator.ofFloat(textView, "Y", textView.getY(), texts.getY());
        animator.setStartDelay(delay);
        y.setStartDelay(delay + 550);
        y.setDuration(800);
        y.setInterpolator(new AnticipateOvershootInterpolator());
        animator.setInterpolator(new AccelerateDecelerateInterpolator());
        animator.setDuration(300);
        y.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                ((TextView)texts.getChildAt(position)).setTextColor(textView.getCurrentTextColor());
                texts.getChildAt(position).setAlpha(1);
                ((FrameLayout) getRootView()).removeView(textView);
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        if(!Value.NULL(listener))
            y.addListener(listener);
        animator.start();
        y.start();
    }

    void animateLowercasetoPosition(final int position, final long delay, final Animator.AnimatorListener listener){
        Log.d("Animating lowercase to position");
        final float size = getResources().getInteger(R.integer.text_size_display3);
        final TextView textView = new TextView(getContext());
        textView.setTextColor(textColor);
        textView.setText(alphabetLowercaseTop.getText());
        textView.setTextSize(size);
        textView.setTypeface(null, Typeface.BOLD);
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.gravity = Gravity.TOP | Gravity.LEFT;
        textView.setLayoutParams(params);
        textView.setVisibility(View.INVISIBLE);
        ((FrameLayout)getRootView()).addView(textView, params);
        textView.setX(getRootView().getWidth() / 2);
        textView.setY(alphabetLowercaseTop.getY() + getResources().getDimensionPixelSize(R.dimen.activity_horizontal_margin));
        textView.setVisibility(View.VISIBLE);

        PropertyValuesHolder tx = PropertyValuesHolder.ofFloat("X", textView.getX(), texts.getChildAt(position).getX());
        PropertyValuesHolder ts = PropertyValuesHolder.ofFloat("textSize", size, itemTextSize);
        ValueAnimator animator = ObjectAnimator.ofPropertyValuesHolder(textView, tx, ts);
        ValueAnimator y = ObjectAnimator.ofFloat(textView, "Y", textView.getY(), texts.getY());
        animator.setStartDelay(delay);
        y.setStartDelay(delay + 550);
        y.setDuration(800);
        y.setInterpolator(new AnticipateOvershootInterpolator());
        animator.setInterpolator(new AccelerateDecelerateInterpolator());
        animator.setDuration(300);
        y.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                ((TextView)texts.getChildAt(position)).setTextColor(textView.getCurrentTextColor());
                texts.getChildAt(position).setAlpha(1);
                ((FrameLayout) getRootView()).removeView(textView);
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        if(!Value.NULL(listener))
            y.addListener(listener);
        animator.start();
        y.start();
    }

    void vocalize(){
        final String animate = "vocalize";
        final String sound = "playVocalize";
        states.put(animate, false);
        states.put(sound, false);
        final Consumer<Boolean> finishConsumer = new Consumer<Boolean>() {
            @Override
            public Boolean consume() {
                return states.get(animate) && states.get(sound);
            }
        };
        for(int i=0; i<texts.getChildCount(); i++){
            TextView t = (TextView) texts.getChildAt(i);
            int currentTextColor = t.getCurrentTextColor();
            ValueAnimator a = ObjectAnimator.ofInt(t, "textColor", currentTextColor, textColor, currentTextColor);
            a.setDuration(800);
            a.setStartDelay(i * 25l);
            a.setEvaluator(new ArgbEvaluator());
            if(i == texts.getChildCount()-1)
                a.addListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animation) {
                        playVocalize(new Speech.VoiceCallback() {
                            @Override
                            public void onStart(String id) {

                            }

                            @Override
                            public void onDone(String id) {
                                states.put(sound, true);
                                if(finishConsumer.consume())
                                    end();
                            }

                            @Override
                            public void onError(String id, Integer errorCode) {

                            }
                        });
                    }

                    @Override
                    public void onAnimationEnd(Animator animation) {
                        states.put(animate, true);
                        if(finishConsumer.consume())
                            end();
                    }

                    @Override
                    public void onAnimationCancel(Animator animation) {

                    }

                    @Override
                    public void onAnimationRepeat(Animator animation) {

                    }
                });
            a.start();
        }
    }

    void playVocalize(Speech.VoiceCallback voiceCallback){
        int res = 0;
        switch (position){
            case 0:
                res = alphabet.word1;
                break;
            case 1:
                res = alphabet.word2;
                break;
            case 2:
                res = alphabet.word3;
                break;
        }
        Speech.play(res, null, voiceCallback);
    }

    void popOut(){
        for(int i=0; i<texts.getChildCount(); i++){
            TextView t = (TextView) texts.getChildAt(i);
            ViewAnimator.popOut(t, i * 25l, 200);
        }
        ViewAnimator.popOut(image, texts.getChildCount() * 25l, 200).addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                ((AlphaLearning) getParentFragment()).nextStateAndBuild();
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
    }

    void end(){
        finishCollector.collect(true);
    }


    @Override
    public void collect(final View view) {
        view.post(new Runnable() {
            @Override
            public void run() {
                ViewAnimator.popOutZero(view, 0, 200);
            }
        });
        popOut();
    }
}