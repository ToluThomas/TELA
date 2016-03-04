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

public class AlphaLearningLowcaseAnimation extends Fragtivity implements Collector<View> {

    TextView alphabetCenter, alphabetUppercaseCenter, alphabetLowercaseCenter;
    Factory.Alphabets.Alphabet alphabet;
    int textColor, borderColor;
    Collector<Boolean> finishCollector;
    Map<String, Boolean> states = new HashMap<>();

    public static AlphaLearningLowcaseAnimation getInstance(Factory.Alphabets.Alphabet alphabet, int textColor, int borderColor, Collector<Boolean> finishCollector){
        AlphaLearningLowcaseAnimation f = new AlphaLearningLowcaseAnimation().setAlphabet(alphabet).setTextColor(textColor).setBorderColor(borderColor).setFinishCollector(finishCollector);
        return f;
    }


    public AlphaLearningLowcaseAnimation setAlphabet(Factory.Alphabets.Alphabet alphabet){
        this.alphabet = alphabet; return this;
    }

    public AlphaLearningLowcaseAnimation setTextColor(int color){
        this.textColor = color; return this;
    }

    public AlphaLearningLowcaseAnimation setBorderColor(int color){
        this.borderColor = color; return this;
    }

    public AlphaLearningLowcaseAnimation setFinishCollector(Collector<Boolean> finishCollector){
        this.finishCollector = finishCollector; return this;
    }

    @Override
    public int layoutId() {
        return R.layout.alpha_learning_layout2;
    }

    @Override
    public void destroy() {
        Playback.release();
    }

    @Override
    public void destroyView() {

    }

    @Override
    public void bundle(Bundle bundle) {

    }

    @Override
    public void findViews() {
        alphabetCenter = (TextView) findViewById(R.id.alphabetCenter);
        alphabetUppercaseCenter = (TextView) findViewById(R.id.alphabetUppercaseCenter);
        alphabetLowercaseCenter = (TextView) findViewById(R.id.alphabetLowercaseCenter);
    }

    ViewTreeObserver.OnGlobalLayoutListener layoutListener;

    @Override
    public void setupViews() {
        alphabetCenter.setTextColor(borderColor);
        alphabetUppercaseCenter.setTextColor(borderColor);
        alphabetLowercaseCenter.setTextColor(textColor);
        alphabetUppercaseCenter.setVisibility(View.INVISIBLE);
        alphabetLowercaseCenter.setVisibility(View.INVISIBLE);
        alphabetCenter.setText(alphabet.getUppercase());
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
                shiftAnimate();
            }
        };
        getRootView().getViewTreeObserver().addOnGlobalLayoutListener(layoutListener);

    }

    @Override
    public void pause() {
        Playback.pause();
    }

    @Override
    public void resume() {
        Playback.resume();
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
     * Method to shift the Uppercase Alphabet from the center of the view to a suitable position on its
     * left, to make space for the lowercase alphabet which is then popped into visibility. This method is
     * tied to the playShiftAnimate method and only when they both finish is the next method called
     */
    void shiftAnimate(){
        final String animate = "shiftAnimate";
        final String sound = "playShiftAnimate";
        final Retriever<Boolean> finishListener = new Retriever<Boolean>() {
            @Override
            public Boolean retrieve() {
                return states.get(animate) && states.get(sound);
            }
        };
        states.put(animate, false);
        states.put(sound, false);
        alphabetCenter.measure(View.MeasureSpec.makeMeasureSpec(getRootView().getMeasuredWidth(), View.MeasureSpec.AT_MOST), View.MeasureSpec.makeMeasureSpec(getRootView().getMeasuredHeight(), View.MeasureSpec.AT_MOST));
        alphabetUppercaseCenter.measure(View.MeasureSpec.makeMeasureSpec(getRootView().getMeasuredWidth(), View.MeasureSpec.AT_MOST), View.MeasureSpec.makeMeasureSpec(getRootView().getMeasuredHeight(), View.MeasureSpec.AT_MOST));
        alphabetLowercaseCenter.measure(View.MeasureSpec.makeMeasureSpec(getRootView().getMeasuredWidth(), View.MeasureSpec.AT_MOST), View.MeasureSpec.makeMeasureSpec(getRootView().getMeasuredHeight(), View.MeasureSpec.AT_MOST));
        float x = alphabetCenter.getTranslationX();
        Log.d("x is: "+x);
        x = x - alphabetCenter.getMeasuredWidth() / 2f;
        Log.d("x going is: " + x);
        final ValueAnimator animator = ObjectAnimator.ofFloat(alphabetCenter, "translationX", alphabetCenter.getTranslationX(), x);
        animator.setDuration(700);
        animator.setInterpolator(new AnticipateOvershootInterpolator());
        animator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                playShiftAnimate(new Playback.PlaybackListener() {
                    @Override
                    public void onStart(String id) {

                    }

                    @Override
                    public void onDone(String id) {
                        states.put(sound, true);
                        if (finishListener.retrieve()) //if sound and animation finished, move to next method
                            getRootView().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    animateInfo();
                                }
                            }, 500);

                    }

                    @Override
                    public void onError(String id, Integer errorCode) {

                    }
                });
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                Playback.uncommit(animator);
                alphabetUppercaseCenter.setVisibility(View.VISIBLE);
                alphabetCenter.setVisibility(View.GONE);
                final ValueAnimator popLowercase = popLowercase();
                popLowercase.addListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animator animation) {
                        Playback.uncommit(popLowercase);
                        states.put(animate, true);
                        if (finishListener.retrieve())
                            getRootView().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    animateInfo();
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
                Playback.play(popLowercase);
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        Playback.play(animator);
    }

    /**
     * Play sound for the shifting animation
     * @param playbackListener
     */
    void playShiftAnimate(Playback.PlaybackListener playbackListener){
        Playback.play(alphabet.audio_res_low_ident, null, playbackListener);
    }

    /**
     * Show the lowercase letter by popping it into visiblity
     * @param
     */
    ValueAnimator popLowercase(){
        alphabetLowercaseCenter.setAlpha(0f);
        alphabetLowercaseCenter.setVisibility(View.VISIBLE);
        ValueAnimator animator = ViewAnimator.popInZero(alphabetLowercaseCenter, 0, 300);
        return animator;
    }

    /**
     * Do a simple animation of color while information about the alphabet is being said.
     * This method is tied to the playAnimateInfo method and only when they both finish
     * is the next method called
     */
    void animateInfo(){
        final String animate = "animateInfo";
        final String sound = "playAnimateInfo";
        final Retriever<Boolean> finishListener = new Retriever<Boolean>() {
            @Override
            public Boolean retrieve() {
                return states.get(animate) && states.get(sound);
            }
        };
        states.put(animate, false);
        states.put(sound, false);

        float cx = alphabetCenter.getTranslationX();

        PropertyValuesHolder capX = PropertyValuesHolder.ofFloat("scaleX", 1f, 2f);
        PropertyValuesHolder capY = PropertyValuesHolder.ofFloat("scaleY", 1f, 2f);
        //PropertyValuesHolder capCol = PropertyValuesHolder.ofInt("textColor", borderColor, textColor);
        PropertyValuesHolder capTX = PropertyValuesHolder.ofFloat("translationX", cx + (alphabetUppercaseCenter.getWidth() / 2), cx - (alphabetUppercaseCenter.getWidth() / 2));
        //capCol.setEvaluator(new ArgbEvaluator());

        float lx = alphabetLowercaseCenter.getTranslationX();
        PropertyValuesHolder lowX = PropertyValuesHolder.ofFloat("scaleX", 1f, 2f);
        PropertyValuesHolder lowY = PropertyValuesHolder.ofFloat("scaleY", 1f, 2f);
        //PropertyValuesHolder lowCol = PropertyValuesHolder.ofInt("textColor", textColor, borderColor);
        PropertyValuesHolder lowTX = PropertyValuesHolder.ofFloat("translationX", lx, lx + (alphabetLowercaseCenter.getWidth() / 2));
        //lowCol.setEvaluator(new ArgbEvaluator());

        final ValueAnimator cap = ObjectAnimator.ofPropertyValuesHolder(alphabetUppercaseCenter, capX, capY, capTX);
        cap.setRepeatCount(1);
        cap.setRepeatMode(ValueAnimator.REVERSE);
        final ValueAnimator low = ObjectAnimator.ofPropertyValuesHolder(alphabetLowercaseCenter, lowX, lowY, lowTX);
        low.setRepeatCount(1);
        low.setRepeatMode(ValueAnimator.REVERSE);
        cap.setDuration(1500);
        low.setDuration(1500);
        low.setStartDelay(3000);
        cap.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                playInfo(new Playback.PlaybackListener() {
                    @Override
                    public void onStart(String id) {

                    }

                    @Override
                    public void onDone(String id) {
                        states.put(sound, true);
                        if (finishListener.retrieve())//if sound and animation finished, move to next method
                            getRootView().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    end();
                                }
                            }, 500);

                    }

                    @Override
                    public void onError(String id, Integer errorCode) {

                    }
                });
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                Playback.uncommit(cap);
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
                Playback.uncommit(low);
                states.put(animate, true);
                if (finishListener.retrieve())
                    getRootView().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            end();
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
        Playback.play(cap, low);
    }

    /**
     * Play sound for info animation
     * @param playbackListener
     */
    void playInfo(Playback.PlaybackListener playbackListener){
        Playback.play(alphabet.audio_res_cap_low_desc, null, playbackListener);
    }

    /**
     * Move the alphabets (uppercase and lowercase) to the top of the view.
     */
    void animateUp(){
        float top = getResources().getDimensionPixelSize(R.dimen.activity_horizontal_margin);
        float size = getResources().getInteger(R.integer.text_size_display3);
        float from = getResources().getInteger(R.integer.text_size_display4);
        PropertyValuesHolder uts = PropertyValuesHolder.ofFloat("textSize", from, size);
        PropertyValuesHolder lts = PropertyValuesHolder.ofFloat("textSize", from, size);
        Log.d("Translationy y: "+alphabetUppercaseCenter.getY());
        PropertyValuesHolder uty = PropertyValuesHolder.ofFloat("Y", alphabetUppercaseCenter.getY(), top);
        PropertyValuesHolder lty = PropertyValuesHolder.ofFloat("Y", alphabetLowercaseCenter.getY(), top);
        //PropertyValuesHolder clCp = PropertyValuesHolder.ofInt("textColor", alphabetUppercaseCenter.getCurrentTextColor(), alphabetLowercaseCenter.getCurrentTextColor());
        //clCp.setEvaluator(new ArgbEvaluator());
        alphabetUppercaseCenter.setTextColor(alphabetLowercaseCenter.getCurrentTextColor());
        ValueAnimator cap = ObjectAnimator.ofPropertyValuesHolder(alphabetUppercaseCenter, uts, uty);
        ValueAnimator low = ObjectAnimator.ofPropertyValuesHolder(alphabetLowercaseCenter, lts, lty);
        low.setInterpolator(new AnticipateOvershootInterpolator());
        cap.setInterpolator(new AnticipateOvershootInterpolator());
        low.setStartDelay(20);
        cap.setDuration(1000);
        low.setDuration(1000);
        low.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                finish();
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        cap.start();
        low.start();
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
        animateUp();
    }

    private void finish(){
        ((AlphaLearningFragment)getParentFragment()).nextStateAndBuild();
    }

}
