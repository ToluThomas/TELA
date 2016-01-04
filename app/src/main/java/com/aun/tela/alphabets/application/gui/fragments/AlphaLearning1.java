package com.aun.tela.alphabets.application.gui.fragments;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.TextView;

import com.aun.tela.alphabets.R;
import com.aun.tela.alphabets.application.entities.Factory;
import com.aun.tela.alphabets.application.generic.Collector;
import com.aun.tela.alphabets.application.generic.Consumer;
import com.aun.tela.alphabets.application.gui.activity.Activity;
import com.aun.tela.alphabets.application.util.Log;
import com.aun.tela.alphabets.application.util.Speech;
import com.aun.tela.alphabets.application.util.ViewAnimator;

import java.util.HashMap;
import java.util.Map;

import io.meengle.androidutil.gui.fragment.Fragtivity;

public class AlphaLearning1 extends Fragtivity implements Collector<View>{

    TextView alphabetText;
    Factory.Alphabets.Alphabet alphabet;
    int textColor, borderColor;
    Map<String, Boolean> states = new HashMap<>();
    Collector<Boolean> finishCollector;

    public static AlphaLearning1 getInstance(Factory.Alphabets.Alphabet alphabet, int textColor, int borderColor, Collector<Boolean> finishCollector){
        AlphaLearning1 f = new AlphaLearning1().setAlphabet(alphabet).setTextColor(textColor).setBorderColor(borderColor).setFinishCollector(finishCollector);
        return f;
    }

    public AlphaLearning1 setFinishCollector(Collector<Boolean> finishCollector){
        this.finishCollector = finishCollector; return this;
    }

    public AlphaLearning1 setAlphabet(Factory.Alphabets.Alphabet alphabet){
        this.alphabet = alphabet; return this;
    }

    public AlphaLearning1 setTextColor(int color){
        this.textColor = color; return this;
    }

    public AlphaLearning1 setBorderColor(int color){
        this.borderColor = color; return this;
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
    }

    @Override
    public void setupViews() {
        alphabetText.setText(alphabet.getUppercase());
        alphabetText.setTextColor(textColor);
        appearAndAnimate();
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

    void appearAndAnimate(){
        final String animate = "appearAndAnimate";
        final String sound = "playAppearAndAnimate";
        states.put(animate, false);
        states.put(sound, false);
        DisplayMetrics metrics = new DisplayMetrics();
        Activity.getInstance().getWindow().getWindowManager().getDefaultDisplay().getMetrics(metrics);
        float from = getResources().getInteger(R.integer.text_size_display3);
        float to = getResources().getInteger(R.integer.text_size_display4);
        ValueAnimator animator = ObjectAnimator.ofFloat(alphabetText, "textSize", from, to);
        animator.setDuration(1000);
        animator.setStartDelay(200);
        final Consumer<Boolean> finishConsumer = new Consumer<Boolean>() {
            @Override
            public Boolean consume() {
                Log.d("Checking finish");
                return states.get(animate) && states.get(sound);
            }
        };
        animator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                playAppearAndAnimate(new Speech.VoiceCallback() {
                    @Override
                    public void onStart(String id) {

                    }

                    @Override
                    public void onDone(String id) {
                        states.put(sound, true);
                        if(finishConsumer.consume()){
                            getRootView().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    popAnimate();
                                }
                            }, 500);
                        }
                    }

                    @Override
                    public void onError(String id, Integer errorCode) {

                    }
                });
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                states.put(animate, true);
                if(finishConsumer.consume()){
                    getRootView().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            popAnimate();
                        }
                    }, 500);

                }
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

    void playAppearAndAnimate(Speech.VoiceCallback voiceCallback){
        Speech.play(alphabet.intro, null, voiceCallback);
    }

    void popAnimate(){
        Log.d("pop animating");
        final String animate = "popAndAnimate";
        final String sound = "playPopAndAnimate";
        states.put(animate, false);
        states.put(sound, false);
        final Consumer<Boolean> finishConsumer = new Consumer<Boolean>() {
            @Override
            public Boolean consume() {
                return states.get(animate) && states.get(sound);
            }
        };
        ViewAnimator.pop(alphabetText, new Collector<View>() {
            @Override
            public void collect(View view) {
                states.put(animate, true);
                if(finishConsumer.consume()){
                    end();
                }
            }
        });
        playPopAnimate(new Speech.VoiceCallback() {
            @Override
            public void onStart(String id) {

            }

            @Override
            public void onDone(String id) {
                Log.d("Played Pop Animate");
                states.put(sound, true);
                if(finishConsumer.consume()){
                    end();
                }
            }

            @Override
            public void onError(String id, Integer errorCode) {

            }
        });
    }

    void playPopAnimate(Speech.VoiceCallback voiceCallback){
        Log.d("Playing pop animate");
        Speech.play(alphabet.introInfo, null, voiceCallback);
    }

    void end(){
        Log.d("Ending");
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
        ((AlphaLearning)getParentFragment()).nextStateAndBuild();
    }
}