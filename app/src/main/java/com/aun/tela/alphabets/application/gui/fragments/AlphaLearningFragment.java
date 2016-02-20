package com.aun.tela.alphabets.application.gui.fragments;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.animation.ValueAnimator;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AnticipateOvershootInterpolator;
import android.widget.FrameLayout;
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
import com.aun.tela.alphabets.application.gui.custom.ArialTextView;
import com.aun.tela.alphabets.application.gui.custom.BarColorView;
import com.aun.tela.alphabets.application.gui.custom.CircularColorView;
import com.aun.tela.alphabets.application.util.Color;
import com.aun.tela.alphabets.application.util.Log;
import com.aun.tela.alphabets.application.util.Playback;
import com.aun.tela.alphabets.application.util.ViewAnimator;
import com.sothree.slidinguppanel.SlidingUpPanelLayout;

import java.util.List;
import java.util.Random;

import io.meengle.androidutil.gui.fragment.Fragtivity;
import io.meengle.util.Value;

/**
 * Created by Joseph Dalughut on 31/12/15 at 9:12 AM.
 * Project name : TELA.
 * Copyright (c) 2015 Meengle. All rights reserved.
 */

/**
 * This is the main fragment where the animations for learning alphabets take place, even though these animations
 * happen in other fragments, those fragments are held and switched by this fragment
 */
public class AlphaLearningFragment extends Fragtivity implements SlidingUpPanelLayout.PanelSlideListener {

    public static AlphaLearningFragment getInstance(Factory.Alphabets.Alphabet alphabet, int textColor, int borderColor){
        AlphaLearningFragment f = new AlphaLearningFragment().setAlphabet(alphabet).setTextColor(textColor).setBorderColor(borderColor);
        return f;
    }

    public AlphaLearningFragment setAlphabet(Factory.Alphabets.Alphabet alphabet){
        this.alphabet = alphabet; return this;
    }

    public AlphaLearningFragment setTextColor(int color){
        this.textColor = color; return this;
    }

    public AlphaLearningFragment setBorderColor(int color){
        this.borderColor = color; return this;
    }

    View slidingView, slidingContent, overlay;
    RecyclerView recyclerView;
    SlidingUpPanelLayout slidingLayout;
    ImageButton chevron, pauseButton, replayButton;
    CircularColorView back, next, pause, dragView;
    boolean pausedByPanel = false;
    Factory.Alphabets.Alphabet alphabet;
    ImageView grass4, grass3, grass2, grass1;
    int textColor, borderColor;
    BarColorView panel;

    State state = State.PENDING;

    GenericRecyclerViewItemAdapter<String, ViewHolder> adapter;

    @Override
    public int layoutId() {
        return R.layout.fragment_learning_alpha;
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
        slidingView = findViewById(R.id.slidingView);
        slidingContent = findViewById(R.id.slidingContent);
        dragView = (CircularColorView) findViewById(R.id.dragView);
        overlay = findViewById(R.id.overlay);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        slidingLayout = (SlidingUpPanelLayout) findViewById(R.id.slidingLayout);
        chevron = (ImageButton) findViewById(R.id.chevron);
        back = (CircularColorView) findViewById(R.id.back);
        back = (CircularColorView) findViewById(R.id.back);
        next = (CircularColorView) findViewById(R.id.next);
        pause = (CircularColorView) findViewById(R.id.pause);
        pauseButton = (ImageButton) findViewById(R.id.pauseButton);
        replayButton = (ImageButton) findViewById(R.id.replayButton);
        grass1 = (ImageView) findViewById(R.id.grass1);
        grass2 = (ImageView) findViewById(R.id.grass2);
        grass3 = (ImageView) findViewById(R.id.grass3);
        grass4 = (ImageView) findViewById(R.id.grass4);
        panel = (BarColorView) findViewById(R.id.panel);
    }
    @Override
    public void setupViews() {
        setup();
    }

    void setup(){
        color();
        ViewAnimator.springify(back, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Activity.replace(LetterWatchFragment.getInstance(textColor, borderColor, new Collector() {
                    @Override
                    public void collect(Object o) {
                        Activity.replace(new NavigationFragment());
                    }
                }));
            }
        });
        ViewAnimator.springify(next, new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        ViewAnimator.upDownify(back, 20, 300, 700);
        ViewAnimator.upDownify(pause, 20, 400, 700);
        ViewAnimator.upDownify(next, 20, 600, 700);
        //ViewAnimator.upDownify(dragView, 20, 600, 700);
        pauseButton.setImageResource(R.mipmap.ic_action_media_pause);

        //panel.setBarColor(android.graphics.Color.WHITE);
        panel.setBorderColor(borderColor);
        //panel.setLayerType(View.LAYER_TYPE_HARDWARE, null);
        PropertyValuesHolder x = PropertyValuesHolder.ofFloat("scaleX", panel.getScaleX(), 1f);
        PropertyValuesHolder y = PropertyValuesHolder.ofFloat("scaleY", panel.getScaleY(), 1f);
        ValueAnimator animator = ObjectAnimator.ofPropertyValuesHolder(panel, x, y);
        animator.setDuration(500);
        animator.setInterpolator(new AnticipateOvershootInterpolator());
        animator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                //panel.setLayerType(View.LAYER_TYPE_NONE, null);
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        animator.start();

        ViewAnimator.springify(dragView, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                slidingLayout.setPanelState(slidingLayout.getPanelState().equals(SlidingUpPanelLayout.PanelState.COLLAPSED) ? SlidingUpPanelLayout.PanelState.EXPANDED : SlidingUpPanelLayout.PanelState.COLLAPSED);
            }
        });

        ViewAnimator.springify(pause, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (pauseButton.getVisibility()){
                    case View.VISIBLE:
                        boolean paused = Playback.paused();
                        if(!paused) {
                            Playback.pause();
                            pauseButton.setImageResource(R.drawable.media_play);
                        }else{
                            Playback.resume();
                            pauseButton.setImageResource(R.drawable.media_pause);
                        }
                        break;
                    default:
                        Playback.release();
                        ValueAnimator animator = ObjectAnimator.ofFloat(replayButton, "rotation", 0, 360);
                        animator.setDuration(200);
                        animator.addListener(new Animator.AnimatorListener() {
                            @Override
                            public void onAnimationStart(Animator animation) {

                            }

                            @Override
                            public void onAnimationEnd(Animator animation) {
                                replayButton.setRotation(0);
                                replayButton.setVisibility(View.INVISIBLE);
                                pauseButton.setVisibility(View.VISIBLE);
                            }

                            @Override
                            public void onAnimationCancel(Animator animation) {

                            }

                            @Override
                            public void onAnimationRepeat(Animator animation) {

                            }
                        });
                        if(next.getAlpha() > 0.5f){
                            ViewAnimator.popOutZero(next, 0, 200);
                        }
                        animator.start();
                        build();
                        break;
                }
            }
        });

        ViewAnimator.popInZero(back, 0, 300);
        //ViewAnimator.popInZero(pause, 200, 300);
        //ViewAnimator.popInZero(left, 100, 300);
        //ViewAnimator.popInZero(right, 200, 300);

        ViewAnimator.popInZero(chevron, 500, 300);
        //ViewAnimator.upDownify(chevron, 10, 400, 700);

        ViewAnimator.leftRightify(grass1, 4, 1000, 4000);
        ViewAnimator.leftRightify(grass2, 4, 450, 2500);
        ViewAnimator.leftRightify(grass3, 4, 200, 3000);
        ViewAnimator.leftRightify(grass4, 4, 800, 3500);
        /* Might not be needed
        int slidingContentHeight = getResources().getDimensionPixelSize(R.dimen.actionBarSize) + getResources().getDimensionPixelSize(R.dimen.hundred);
        slidingLayout.setAnchorPoint(getRootView().getWidth() /  slidingContentHeight);
        */

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
                                animateToLetter(s, v);
                            }
                        });
                        if (Value.NULL(viewHolder.itemView.getAnimation())) {
                            Log.d("Animation null, starting for view : " + viewHolder.itemView.toString());
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
        build();
    }

    //change the color of our buttons, but by animation
    void color(){
        Activity.setColor(borderColor);
        ViewAnimator.color(back, "circularColor", 0, 500, back.getCircularColor(), textColor);
        ViewAnimator.color(pause, "circularColor", 0, 500, pause.getCircularColor(), textColor);
        ViewAnimator.color(next, "circularColor", 200, 500, next.getCircularColor(), textColor);
        ViewAnimator.color(dragView, "borderColor", 200, 500, dragView.getBorderColor(), textColor);
        //ViewAnimator.color(back, "borderColor", 0, 500, back.getCircularColor(), borderColor);
        //ViewAnimator.color(pause, "borderColor", 0, 500, pause.getCircularColor(), borderColor);
        //ViewAnimator.color(next, "borderColor", 200, 500, next.getCircularColor(), borderColor);
    }

    @Override
    public void pause() {
        Playback.pause();
        if(!Value.NULL(overlay))
            overlay.setVisibility(View.VISIBLE);
    }

    @Override
    public void resume() {
        Playback.resume();
        if(!Value.NULL(overlay))
            overlay.setVisibility(View.INVISIBLE);
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


    public void add(Fragment fragment){
        getChildFragmentManager().beginTransaction().add(R.id.container, fragment).addToBackStack(null).commitAllowingStateLoss();
    }

    public void replace(Fragment fragment){
        getChildFragmentManager().beginTransaction().replace(R.id.container, fragment).commitAllowingStateLoss();
    }


    @Override
    public void onPanelSlide(View panel, float slideOffset) {
        chevron.setRotation(slideOffset * 180f);
    }

    @Override
    public void onPanelCollapsed(View panel) {
        ViewAnimator.popInZero(back, 0, 300);
        ViewAnimator.popInZero(pause, 0, 500);
        if(Playback.paused() && pausedByPanel) {
            Playback.resume();
        }
        pausedByPanel = false;
    }

    @Override
    public void onPanelExpanded(View panel) {
        ViewAnimator.popOutZero(back, 0, 300);
        ViewAnimator.popOutZero(pause, 0, 500);
        if(!Playback.paused()) {
            Playback.pause();
            pausedByPanel= true;
        }
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
            return new ViewHolder(LayoutInflater.from(Activity.getInstance()).inflate(R.layout.grid_item_image_text_h, parent, false));
        }

        public static ViewHolder setup(ViewHolder viewHolder, String string, Integer position, Boolean isLast) {
            if (Value.EMPTY(string)) {
                viewHolder.text.setTag(position);
                viewHolder.itemView.setVisibility(View.INVISIBLE);
                viewHolder.text.setText(string);
                return viewHolder;
            } else {
                viewHolder.text.setText(string.toUpperCase());
                viewHolder.text.setTextColor(Color.random());
                viewHolder.itemView.setClickable(true);
                viewHolder.itemView.setVisibility(View.VISIBLE);
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
    }

    /**
     * Create an animation for this alphabet {@param string} entering the screen from view {@param view}
     * It's the same process as from {@link LetterWatchFragment}. Take {@param view}'s position on screen, duplicate
     * it, pick the textview inside and then animate the clone to the center of screen while it's background scales
     * up to the size of the screen
     * @param string
     * @param view
     */
    private void animateToLetter(final String string, View view){
        TextView textView = (TextView) view.findViewById(R.id.text);

        int x = (getRootView().getWidth() / 2);
        int y = (getRootView().getHeight()/ 2);

        float midX = view.getX() + (view.getWidth() / 2);
        float midY = getRootView().getHeight() - view.getY() + (view.getHeight() / 2);

        //Log.d("MidX: "+midX);
        //Log.d("MidY: " + midY);

        TextView newText = new ArialTextView(getContext());
        //((FrameLayout) view).removeView(textView);
        //recyclerView.removeViewInLayout(view);
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.gravity = Gravity.TOP | Gravity.START | Gravity.LEFT;
        newText.setTextColor(textView.getCurrentTextColor());
        newText.setText(textView.getText());
        //newText.setTypeface(null, Typeface.BOLD);
        newText.setTextSize(TypedValue.COMPLEX_UNIT_PX, textView.getResources().getDimension(R.dimen.text_size_display3));
        textView = newText;
        final int textColor = textView.getCurrentTextColor();
        textView.setLayoutParams(params);
        FrameLayout.LayoutParams paramsNew = new FrameLayout.LayoutParams(view.getWidth(), view.getHeight());
        paramsNew.height = view.getHeight();
        paramsNew.width = view.getWidth();
        paramsNew.gravity = Gravity.TOP | Gravity.START | Gravity.LEFT;
        FrameLayout rootView = (FrameLayout) getRootView();
        recyclerView.removeViewInLayout(view);
        rootView.addView(view, rootView.getChildCount(), paramsNew);
        rootView.addView(textView, rootView.getChildCount(), params);


        view.setTranslationX(midX - (paramsNew.width / 2));
        view.setTranslationY(midY - (paramsNew.height / 2));

        textView.setTranslationX(midX - (textView.getWidth() / 2));
        textView.setTranslationY(midY - (textView.getWidth() / 2));

        float tvx = textView.getTranslationX();
        float tvy = textView.getTranslationY();
        //float w = Math.abs(x - tvx);
        //float h = Math.abs(y - tvy);
        //Float hypotenuse =((Double)Math.sqrt( ((Float)((w * w) + (h * h))).doubleValue())).floatValue();
        long durationByDistance = /*10 * (hypotenuse.longValue() < 1 ? 1 : hypotenuse.longValue());*/ 800;
        textView.measure(View.MeasureSpec.makeMeasureSpec(getRootView().getWidth(), View.MeasureSpec.AT_MOST), View.MeasureSpec.makeMeasureSpec(getRootView().getHeight(), View.MeasureSpec.AT_MOST));
        PropertyValuesHolder tx = PropertyValuesHolder.ofFloat("translationX", tvx, x  - textView.getMeasuredWidth() / 2);
        PropertyValuesHolder ty = PropertyValuesHolder.ofFloat("translationY", tvy, y  - textView.getMeasuredHeight() / 2);
        PropertyValuesHolder vtx = PropertyValuesHolder.ofFloat("translationX", view.getTranslationX(), x  - view.getWidth() / 2);
        PropertyValuesHolder vty = PropertyValuesHolder.ofFloat("translationY", view.getTranslationY(), y  - view.getHeight() / 2);
        PropertyValuesHolder vsx = PropertyValuesHolder.ofFloat("scaleX", view.getScaleX(), getRootView().getWidth());
        PropertyValuesHolder vsy = PropertyValuesHolder.ofFloat("scaleY", view.getScaleY(), getRootView().getHeight());
        int targetSX = getResources().getDimensionPixelSize(R.dimen.text_size_display4) / params.width;
        int targetSy = getResources().getDimensionPixelSize(R.dimen.text_size_display4) / params.height;
        PropertyValuesHolder sx = PropertyValuesHolder.ofFloat("scaleX", textView.getScaleX(), targetSX);
        PropertyValuesHolder sy = PropertyValuesHolder.ofFloat("scaleX", textView.getScaleY(), targetSy);
        ValueAnimator centerTextAnimator = ObjectAnimator.ofPropertyValuesHolder(textView, tx, ty);
        ValueAnimator centerViewAnimator = ObjectAnimator.ofPropertyValuesHolder(view, vtx, vty, vsx, vsy);
        centerViewAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
        centerViewAnimator.setDuration(1500);
        centerTextAnimator.setInterpolator(new AnticipateOvershootInterpolator());
        centerTextAnimator.setDuration(durationByDistance);
        Activity.setColor(textColor);
        centerViewAnimator.start();
        final TextView finalTextView = textView;
        final View finalView = view;
        centerTextAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                //Activity.replace(AlphaLearning.getInstance(exitString, textView.getCurrentTextColor()));
                alphabet = Factory.Alphabets.build(string);
                Activity.replace(AlphaLearningFragment.getInstance(alphabet, textColor, textColor));
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        slidingLayout.post(new Runnable() {
            @Override
            public void run() {
                slidingLayout.setPanelState(SlidingUpPanelLayout.PanelState.COLLAPSED);
            }
        });
        centerTextAnimator.start();
    }

    static enum State {
        PENDING, AlphaLearningUpcaseAnimation, AlphaLearningLowcaseAnimation, AlphaLearningWordAnimation1,
        AlphaLearningWordAnimation2, AlphaLearningWordAnimation3, AlphaLearningExitAnimation;
    }

    public void setStateAndBuild(State state){
        this.state = state;
        build();
    }

    public void nextStateAndBuild(){
        this.state = State.values()[this.state.ordinal()+1];
        build();
    }

    //.
    Fragtivity f;

    /**
     * This method switches to the next animation fragment based on the currently animating fragment, this is
     * based on the currently assigned state (type {@link com.aun.tela.alphabets.application.gui.fragments.AlphaLearningFragment.State})
     *
     */
    void build(){
        switch (state){
            case PENDING:
                pauseButton.setVisibility(View.VISIBLE);
                replayButton.setVisibility(View.INVISIBLE);
                f = AlphaLearningUpcaseAnimation.getInstance(alphabet, textColor, borderColor, new Collector<Boolean>() {
                    @Override
                    public void collect(Boolean aBoolean) {
                        showNext();
                    }
                });
                replace(f);
                if(pause.getAlpha() < 0.5)
                    ViewAnimator.popInZero(pause, 0, 200);
                ViewAnimator.springify(next, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        pauseButton.setVisibility(View.VISIBLE);
                        replayButton.setVisibility(View.INVISIBLE);
                        ((Collector<View>) f).collect(next);
                    }
                });
                break;
            case AlphaLearningUpcaseAnimation:
                f = AlphaLearningLowcaseAnimation.getInstance(alphabet, textColor, borderColor, new Collector<Boolean>() {
                    @Override
                    public void collect(Boolean aBoolean) {
                        showNext();
                    }
                });
                replace(f);
                ViewAnimator.springify(next, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        pauseButton.setVisibility(View.VISIBLE);
                        replayButton.setVisibility(View.INVISIBLE);
                        ((Collector<View>) f).collect(next);
                    }
                });
                break;
            case AlphaLearningLowcaseAnimation:
                f = AlphaLearningWordAnimation.getInstance(alphabet, 0, textColor, borderColor, new Collector<Boolean>() {
                    @Override
                    public void collect(Boolean aBoolean) {
                        showNext();
                    }
                });
                replace(f);
                ViewAnimator.springify(next, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        pauseButton.setVisibility(View.VISIBLE);
                        replayButton.setVisibility(View.INVISIBLE);
                        ((Collector<View>) f).collect(next);
                    }
                });
                break;
            case AlphaLearningWordAnimation1:
                f = AlphaLearningWordAnimation.getInstance(alphabet, 1, textColor, borderColor, new Collector<Boolean>() {
                    @Override
                    public void collect(Boolean aBoolean) {
                        showNext();
                    }
                });
                replace(f);
                ViewAnimator.springify(next, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        pauseButton.setVisibility(View.VISIBLE);
                        replayButton.setVisibility(View.INVISIBLE);
                        ((Collector<View>) f).collect(next);
                    }
                });
                break;
            case AlphaLearningWordAnimation2:
                f = AlphaLearningWordAnimation.getInstance(alphabet, 2, textColor, borderColor, new Collector<Boolean>() {
                    @Override
                    public void collect(Boolean aBoolean) {
                        showNext();
                    }
                });
                replace(f);
                ViewAnimator.springify(next, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        pauseButton.setVisibility(View.VISIBLE);
                        replayButton.setVisibility(View.INVISIBLE);
                        ((Collector<View>) f).collect(next);
                    }
                });
                break;
            case AlphaLearningWordAnimation3:
                f = AlphaLearningExitAnimation.getInstance(alphabet, textColor, borderColor, new Collector<Boolean>() {
                    @Override
                    public void collect(Boolean aBoolean) {
                        showNext();
                    }
                });
                replace(f);
                ViewAnimator.springify(next, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        pauseButton.setVisibility(View.VISIBLE);
                        replayButton.setVisibility(View.INVISIBLE);
                        ((Collector<View>) f).collect(next);
                    }
                });
                break;
            case AlphaLearningExitAnimation:
                if(alphabet.getLowerCase().equals("z")){
                    Activity.replace(LetterWatchFragment.getInstance(textColor, borderColor, new Collector() {
                        @Override
                        public void collect(Object o) {
                            Activity.replace(new NavigationFragment());
                        }
                    }));
                }else{
                    pickNextLetter();
                }
                break;
        }
    }


    /**
     * Show the next button
     */
    void showNext(){
        Log.d("Nexting");
        next.post(new Runnable() {
            @Override
            public void run() {
                pauseButton.setVisibility(View.INVISIBLE);
                replayButton.setVisibility(View.VISIBLE);
                ViewAnimator.popInZero(next, 0, 200);
            }
        });
    }



    void pickNextLetter(){
        int currentPosition = Factory.Alphabets.getPosition(alphabet.getLowerCase());
        final int nextPosition = currentPosition+1;
        getRootView().postDelayed(new Runnable() {
            @Override
            public void run() {
                slidingLayout.setPanelState(SlidingUpPanelLayout.PanelState.EXPANDED);
                getRootView().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        final int nextViewPosition = nextPosition+1;
                        recyclerView.smoothScrollToPosition(nextViewPosition);
                        getRootView().postDelayed(new Runnable() {
                            @Override
                            public void run() {

                                Log.d("NextViewPosition" + nextViewPosition);
                                RecyclerView.ViewHolder v = recyclerView.findViewHolderForAdapterPosition(nextViewPosition);
                                Log.d("V is : " + (Value.NULL(v) ? "Null" : v.toString()));
                                View view = v.itemView;

                                animateToLetter(Factory.Alphabets.getAlphabetsUppercase().get(nextPosition), view);
                            }
                        }, 500);
                    }
                }, 500);
            }
        }, 500);
    }

}
