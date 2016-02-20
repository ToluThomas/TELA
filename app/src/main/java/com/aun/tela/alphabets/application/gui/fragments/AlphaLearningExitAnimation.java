package com.aun.tela.alphabets.application.gui.fragments;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.animation.ValueAnimator;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.animation.AnticipateOvershootInterpolator;
import android.widget.TextView;

import com.aun.tela.alphabets.R;
import com.aun.tela.alphabets.application.entities.Factory;
import com.aun.tela.alphabets.application.generic.Collector;
import com.aun.tela.alphabets.application.generic.Retriever;
import com.aun.tela.alphabets.application.util.Log;
import com.aun.tela.alphabets.application.util.Playback;
import com.aun.tela.alphabets.application.util.ViewAnimator;

import java.util.HashMap;
import java.util.Map;

import io.meengle.androidutil.gui.fragment.Fragtivity;

/**
 * This class handles the last alphabet animation based on the script. The alphabet animates from the top
 * of the view to the center of the view, during which it's textSize is also changed and increased
 */
public class AlphaLearningExitAnimation extends Fragtivity implements Collector<View> {


    TextView alphabetUppercaseTop, alphabetLowercaseTop, alphabetUppercaseCenter, alphabetLowercaseCenter;
    Collector<Boolean> finishCollector;
    Factory.Alphabets.Alphabet alphabet;
    int textColor, borderColor;
    Map<String, Boolean> states = new HashMap<>();

    public static AlphaLearningExitAnimation getInstance(Factory.Alphabets.Alphabet alphabet, int textColor, int borderColor, Collector<Boolean> finishCollector){
        AlphaLearningExitAnimation f = new AlphaLearningExitAnimation().setAlphabet(alphabet).setTextColor(textColor).setBorderColor(borderColor).setFinishListener(finishCollector);
        return f;
    }

    public AlphaLearningExitAnimation setAlphabet(Factory.Alphabets.Alphabet alphabet){
        this.alphabet = alphabet; return this;
    }

    public AlphaLearningExitAnimation setFinishListener(Collector<Boolean> finishCollector){
        this.finishCollector = finishCollector; return this;
    }

    public AlphaLearningExitAnimation setTextColor(int color){
        this.textColor = color; return this;
    }

    public AlphaLearningExitAnimation setBorderColor(int color){
        this.borderColor = color; return this;
    }

    @Override
    public int layoutId() {
        return R.layout.alpha_learning_layout6;
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

        alphabetUppercaseCenter = (TextView) findViewById(R.id.alphabetUppercaseCenter);
        alphabetLowercaseCenter = (TextView) findViewById(R.id.alphabetLowercaseCenter);
    }

    ViewTreeObserver.OnGlobalLayoutListener layoutListener;

    @Override
    public void setupViews() {
        alphabetUppercaseTop.setTextColor(textColor);
        alphabetLowercaseTop.setTextColor(textColor);
        alphabetUppercaseCenter.setTextColor(textColor);
        alphabetLowercaseCenter.setTextColor(textColor);

        alphabetUppercaseTop.setText(alphabet.getUppercase());
        alphabetLowercaseTop.setText(alphabet.getLowerCase());
        alphabetUppercaseCenter.setText(alphabet.getUppercase());
        alphabetLowercaseCenter.setText(alphabet.getLowerCase());
        layoutListener = new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    getRootView().getViewTreeObserver().removeOnGlobalLayoutListener(layoutListener);
                }else{
                    getRootView().getViewTreeObserver().removeGlobalOnLayoutListener(layoutListener);
                }
                animateCenter();
            }
        };
        getRootView().getViewTreeObserver().addOnGlobalLayoutListener(layoutListener);
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


    /**
     * Animate textView to center
     */
    void animateCenter(){
        final String animate = "animateCenter";
        final String sound = "playAnimateCenter";
        states.put(animate, false);
        states.put(sound, false);

        //consumer which checks to see if both animation and sound have finished
        final Retriever<Boolean> finishedConsumer = new Retriever<Boolean>() {
            @Override
            public Boolean retrieve() {
                return states.get(animate) && states.get(sound);
            }
        };
        int size = getResources().getInteger(R.integer.text_size_display4);
        int from = getResources().getInteger(R.integer.text_size_display3);
        PropertyValuesHolder uts = PropertyValuesHolder.ofFloat("textSize", from, size); //animator the textSize of uppercase textview
        PropertyValuesHolder lts = PropertyValuesHolder.ofFloat("textSize", from, size); //animate the textSize of the lowercase textView
        int rem = getResources().getDimensionPixelSize(R.dimen.activity_horizontal_margin);
        PropertyValuesHolder uty = PropertyValuesHolder.ofFloat("Y", alphabetUppercaseTop.getY(), alphabetUppercaseCenter.getY() - rem);
        PropertyValuesHolder lty = PropertyValuesHolder.ofFloat("Y", alphabetLowercaseTop.getY(), alphabetLowercaseCenter.getY() - rem);
        final ValueAnimator cap = ObjectAnimator.ofPropertyValuesHolder(alphabetUppercaseTop, uts, uty);
        final ValueAnimator low = ObjectAnimator.ofPropertyValuesHolder(alphabetLowercaseTop, lts, lty);
        low.setInterpolator(new AnticipateOvershootInterpolator());
        cap.setInterpolator(new AnticipateOvershootInterpolator());
        low.setStartDelay(25);
        cap.setDuration(1000);
        low.setDuration(1000);
        cap.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                playAnimateCenter(new Playback.PlaybackListener() {
                    @Override
                    public void onStart(String id) {

                    }

                    @Override
                    public void onDone(String id) {
                        states.put(sound, true);
                        if (finishedConsumer.retrieve()) //if animation complete and sound complete, call end method
                            end();
                    }

                    @Override
                    public void onError(String id, Integer errorCode) {

                    }
                });
            }

            @Override
            public void onAnimationEnd(Animator animation) {
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        low.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                Playback.uncommit(cap, low);
                states.put(animate, true);
                if (finishedConsumer.retrieve())
                    end();
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        Log.d("Exit Animation point reached");
        Log.d("Playback state is "+ (Playback.paused() ? "paused" : "playing"));
        Playback.play(cap, low);
        Log.d("Playback state is " + (Playback.paused() ? "paused" : "playing"));
    }

    //play the sound for animating the view to the center
    void playAnimateCenter(Playback.PlaybackListener playbackListener){
        Playback.play(alphabet.audio_res_exit, null, playbackListener);
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
        ((AlphaLearningFragment)getParentFragment()).nextStateAndBuild();
    }
}
