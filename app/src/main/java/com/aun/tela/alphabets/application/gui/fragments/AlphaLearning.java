package com.aun.tela.alphabets.application.gui.fragments;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.animation.ValueAnimator;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.BounceInterpolator;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.TextView;

import com.aun.tela.alphabets.R;
import com.aun.tela.alphabets.application.entities.Factory;
import com.aun.tela.alphabets.application.generic.Collector;
import com.aun.tela.alphabets.application.generic.DoubleRetriever;
import com.aun.tela.alphabets.application.generic.QuatroConsumer;
import com.aun.tela.alphabets.application.gui.activity.Activity;
import com.aun.tela.alphabets.application.gui.adapter.GenericRecyclerViewItemAdapter;
import com.aun.tela.alphabets.application.gui.custom.CircularColorView;
import com.aun.tela.alphabets.application.util.Color;
import com.aun.tela.alphabets.application.util.Log;
import com.aun.tela.alphabets.application.util.ViewAnimator;
import com.sothree.slidinguppanel.SlidingUpPanelLayout;

import java.util.List;
import java.util.Random;

import io.meengle.androidutil.gui.fragment.Fragtivity;
import io.meengle.util.Value;

public class AlphaLearning extends Fragtivity implements SlidingUpPanelLayout.PanelSlideListener {

    public static AlphaLearning getInstance(Factory.Alphabets.Alphabet alphabet, int textColor, int borderColor){
        AlphaLearning f = new AlphaLearning().setAlphabet(alphabet).setTextColor(textColor).setBorderColor(borderColor);
        return f;
    }

    public AlphaLearning setAlphabet(Factory.Alphabets.Alphabet alphabet){
        this.alphabet = alphabet; return this;
    }

    public AlphaLearning setTextColor(int color){
        this.textColor = color; return this;
    }

    public AlphaLearning setBorderColor(int color){
        this.borderColor = color; return this;
    }

    View slidingView, slidingContent, dragView;
    RecyclerView recyclerView;
    SlidingUpPanelLayout slidingLayout;
    ImageButton chevron;
    CircularColorView backCircularColorView, leftCircularColorView, rightCircularColorView;
    Factory.Alphabets.Alphabet alphabet;
    int textColor, borderColor;
    View back, left, right;

    State state = State.PENDING;

    GenericRecyclerViewItemAdapter<String, ViewHolder> adapter;

    @Override
    public int layoutId() {
        return R.layout.fragment_learning_alpha;
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
        slidingView = findViewById(R.id.slidingView);
        slidingContent = findViewById(R.id.slidingContent);
        dragView = findViewById(R.id.dragView);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        slidingLayout = (SlidingUpPanelLayout) findViewById(R.id.slidingLayout);
        chevron = (ImageButton) findViewById(R.id.chevron);
        back = findViewById(R.id.back);
        backCircularColorView = (CircularColorView) findViewById(R.id.backCircularView);
        left = findViewById(R.id.left);
        right = findViewById(R.id.right);
        leftCircularColorView = (CircularColorView) findViewById(R.id.leftCircularView);
        rightCircularColorView = (CircularColorView) findViewById(R.id.rightCircularView);
    }

    ViewTreeObserver.OnGlobalLayoutListener layoutListener;

    @Override
    public void setupViews() {
        /*layoutListener = new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    getRootView().getViewTreeObserver().removeOnGlobalLayoutListener(layoutListener);
                }else{
                    getRootView().getViewTreeObserver().removeGlobalOnLayoutListener(layoutListener);
                }
                setup();
            }
        };
        getRootView().getViewTreeObserver().addOnGlobalLayoutListener(layoutListener);
        */
        setup();
    }

    void setup(){
        color();
        build();
        ViewAnimator.springify(back, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Activity.replace(AlphaChoiceFragment.getInstance(textColor, borderColor));
            }
        });
        ViewAnimator.springify(left, new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        ViewAnimator.springify(right, new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        left.setClickable(true);
        right.setClickable(true);
        ViewAnimator.upDownify(back, 20, 300, 700);
        //ViewAnimator.upDownify(left, 20, 100, 700);
        ViewAnimator.upDownify(right, 20, 600, 700);
        back.setAlpha(0f);
        left.setAlpha(0f);
        right.setAlpha(0f);
        left.setVisibility(View.INVISIBLE);
        right.setVisibility(View.VISIBLE);
        back.setVisibility(View.VISIBLE);
        ViewAnimator.popInZero(back, 0, 300);
        //ViewAnimator.popInZero(left, 100, 300);
        //ViewAnimator.popInZero(right, 200, 300);

        ViewAnimator.popInZero(chevron, 500, 300);
        ViewAnimator.upDownify(chevron, 10, 400, 700);

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
        adapter = GenericRecyclerViewItemAdapter.<String, ViewHolder>getInstance()
                .setIdRetriever(new DoubleRetriever<Long, String, Integer>() {
                    @Override
                    public Long retrieve(String s, Integer integer) {
                        return integer.longValue();
                    }
                }).setViewConsumer(new QuatroConsumer<ViewHolder, String, Integer, Boolean>() {
                    @Override
                    public void consume(ViewHolder viewHolder, final String s, final Integer integer, Boolean aBoolean) {
                        ViewHolder.setup(viewHolder, s, integer, aBoolean);
                        viewHolder.itemView.setScaleX(1f);
                        viewHolder.itemView.setScaleY(1f);
                        ViewAnimator.springify(viewHolder.itemView, new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                exit(s, integer, v);
                            }
                        });
                        if(Value.NULL(viewHolder.itemView.getAnimation())) {
                            Log.d("Animation null, starting for view : "+viewHolder.itemView.toString());
                            ViewAnimator.upDownify(viewHolder.itemView, 10, rand.nextInt(500), 800 + rand.nextInt(200));
                        }
                    }
                }).setViewRetriever(new DoubleRetriever<ViewHolder, ViewGroup, Integer>() {
                    @Override
                    public ViewHolder retrieve(ViewGroup viewGroup, Integer integer) {
                        return ViewHolder.inflateDefault(viewGroup);
                    }
                }).setItems(items);
        recyclerView.setAdapter(adapter);
        slidingLayout.setPanelSlideListener(this);
        int slidingContentHeight = getResources().getDimensionPixelSize(R.dimen.buttonSize) + getResources().getDimensionPixelSize(R.dimen.alpha_learning_list_content_height);
        slidingLayout.setParallaxOffset(slidingContentHeight);
    }

    void color(){
        Activity.setColor(borderColor);
        ViewAnimator.color(backCircularColorView, "circularColor", 0, 500, backCircularColorView.getCircularColor(), textColor);
        ViewAnimator.color(leftCircularColorView, "circularColor", 100, 500, leftCircularColorView.getCircularColor(), textColor);
        ViewAnimator.color(rightCircularColorView, "circularColor", 200, 500, rightCircularColorView.getCircularColor(), textColor);
        ViewAnimator.color(backCircularColorView, "borderColor", 0, 500, backCircularColorView.getCircularColor(), borderColor);
        ViewAnimator.color(leftCircularColorView, "borderColor", 100, 500, leftCircularColorView.getCircularColor(), borderColor);
        ViewAnimator.color(rightCircularColorView, "borderColor", 200, 500, rightCircularColorView.getCircularColor(), borderColor);
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
        ViewAnimator.popIn(back, 0, 300);
    }

    @Override
    public void onPanelExpanded(View panel) {
        ViewAnimator.popOut(back, 0, 300);
    }

    @Override
    public void onPanelAnchored(View panel) {

    }

    @Override
    public void onPanelHidden(View panel) {

    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        TextView text;
        CircularColorView circularColorView;

        public ViewHolder(View itemView) {
            super(itemView);
            text = (TextView) findViewById(R.id.text);
            circularColorView = (CircularColorView) findViewById(R.id.circularView);
        }

        View findViewById(int resId) {
            return this.itemView.findViewById(resId);
        }

        public static ViewHolder inflateDefault(ViewGroup parent) {
            return new ViewHolder(LayoutInflater.from(Activity.getInstance()).inflate(R.layout.item_alpha_h, parent, false));
        }

        public static ViewHolder setup(ViewHolder viewHolder, String string, Integer position, Boolean isLast) {
            if (Value.EMPTY(string)) {
                viewHolder.circularColorView.setBorderColor(viewHolder.circularColorView.getResources().getColor(R.color.transparent));
                viewHolder.circularColorView.setCircularColor(viewHolder.circularColorView.getResources().getColor(R.color.transparent));
                viewHolder.text.setTag(position);
                viewHolder.circularColorView.setTag(position);
                viewHolder.circularColorView.removeShadow();
                viewHolder.text.setText(string);
                return viewHolder;
            } else {
                viewHolder.circularColorView.setBorderColor(Color.random());
                viewHolder.circularColorView.setCircularColor(0xFFFFFFFF);
                viewHolder.text.setText(string.toUpperCase());
                viewHolder.text.setTextColor(Color.random());
                viewHolder.itemView.setClickable(true);
                viewHolder.text.setTag(position);
                viewHolder.circularColorView.addShadow();
                viewHolder.circularColorView.setTag(position);
                return viewHolder;
            }
        }
    }

    private void exit(final String string, int position, View view){
        final CircularColorView circularColorView = (CircularColorView) view.findViewById(R.id.circularView);
        TextView textView = (TextView) view.findViewById(R.id.text);

        int x = (getRootView().getWidth() / 2);
        int y = (getRootView().getHeight()/ 2);

        float midX = view.getX() + (view.getWidth() / 2);
        float midY = getRootView().getHeight() - view.getY() + (view.getHeight() / 2);

        //Log.d("MidX: "+midX);
        //Log.d("MidY: " + midY);

        TextView newText = new TextView(getContext());
        //((FrameLayout) view).removeView(textView);
        //recyclerView.removeViewInLayout(view);
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.gravity = Gravity.TOP | Gravity.START | Gravity.LEFT;
        newText.setTextColor(textView.getCurrentTextColor());
        newText.setText(textView.getText());
        newText.setTypeface(null, Typeface.BOLD);
        newText.setTextSize(TypedValue.COMPLEX_UNIT_PX, textView.getResources().getDimension(R.dimen.text_size_display3));
        textView = newText;
        final int textColor = textView.getCurrentTextColor();
        textView.setLayoutParams(params);
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

        //view.setVisibility(View.GONE);
        view = newView;
        FrameLayout rootView = (FrameLayout) getRootView().findViewById(R.id.container);
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
        PropertyValuesHolder vsx = PropertyValuesHolder.ofFloat("scaleX", view.getScaleX(), getRootView().getWidth() + paramsNew.width / paramsNew.width);
        PropertyValuesHolder vsy = PropertyValuesHolder.ofFloat("scaleY", view.getScaleY(), getRootView().getHeight()+ paramsNew.height / paramsNew.height);
        int targetSX = getResources().getDimensionPixelSize(R.dimen.text_size_display4) / params.width;
        int targetSy = getResources().getDimensionPixelSize(R.dimen.text_size_display4) / params.height;
        PropertyValuesHolder sx = PropertyValuesHolder.ofFloat("scaleX", textView.getScaleX(), targetSX);
        PropertyValuesHolder sy = PropertyValuesHolder.ofFloat("scaleX", textView.getScaleY(), targetSy);
        ValueAnimator centerTextAnimator = ObjectAnimator.ofPropertyValuesHolder(textView, tx, ty);
        ValueAnimator centerViewAnimator = ObjectAnimator.ofPropertyValuesHolder(view, vtx, vty, vsx, vsy);
        centerViewAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
        centerViewAnimator.setDuration(1500);
        centerTextAnimator.setInterpolator(new BounceInterpolator());
        centerTextAnimator.setDuration(durationByDistance);
        Activity.setColor(circularColorView.getBorderColor());
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
                state = State.PENDING;
                ((FrameLayout)getRootView()).removeView(finalView);
                ((FrameLayout)getRootView()).removeView(finalTextView);
                AlphaLearning.this.textColor = textColor;
                AlphaLearning.this.borderColor = circularColorView.getBorderColor();
                color();
                build();
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
        PENDING, UPPERCASE_ENTRANCE, LOWERCASE_ENTRANCE, PICTURE_ENTRANCE_1, PICTURE_ENTRANCE_2, PICTURE_ENTRANCE_3, EXIT;
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
    void build(){
        switch (state){
            case PENDING:
                f = AlphaLearning1.getInstance(alphabet, textColor, borderColor, new Collector<Boolean>() {
                    @Override
                    public void collect(Boolean aBoolean) {
                        showNext();
                    }
                });
                replace(f);
                right.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ((Collector<View>) f).collect(right);
                    }
                });
                break;
            case UPPERCASE_ENTRANCE:
                f = AlphaLearning2.getInstance(alphabet, textColor, borderColor, new Collector<Boolean>() {
                    @Override
                    public void collect(Boolean aBoolean) {
                        showNext();
                    }
                });
                replace(f);
                right.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ((Collector<View>) f).collect(right);
                    }
                });
                break;
            case LOWERCASE_ENTRANCE:
                f = AlphaLearning3.getInstance(alphabet, 0,textColor, borderColor, new Collector<Boolean>() {
                    @Override
                    public void collect(Boolean aBoolean) {
                        showNext();
                    }
                });
                replace(f);
                right.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ((Collector<View>) f).collect(right);
                    }
                });
                break;
            case PICTURE_ENTRANCE_1:
                f = AlphaLearning3.getInstance(alphabet, 1,textColor, borderColor, new Collector<Boolean>() {
                    @Override
                    public void collect(Boolean aBoolean) {
                        showNext();
                    }
                });
                replace(f);
                right.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ((Collector<View>) f).collect(right);
                    }
                });
                break;
            case PICTURE_ENTRANCE_2:
                f = AlphaLearning3.getInstance(alphabet, 2,textColor, borderColor, new Collector<Boolean>() {
                    @Override
                    public void collect(Boolean aBoolean) {
                        showNext();
                    }
                });
                replace(f);
                right.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ((Collector<View>) f).collect(right);
                    }
                });
                break;
            case PICTURE_ENTRANCE_3:
                f = AlphaLearning6.getInstance(alphabet, textColor, borderColor, new Collector<Boolean>() {
                    @Override
                    public void collect(Boolean aBoolean) {
                        showNext();
                    }
                });
                replace(f);
                right.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ((Collector<View>) f).collect(right);
                    }
                });
                break;
            case EXIT:
                if(alphabet.getLowerCase().equals("z")){
                    Activity.replace(AlphaChoiceFragment.getInstance(textColor, borderColor));
                }else{
                    pickNextLetter();
                }
                break;
        }
    }


    void showNext(){
        Log.d("Nexting");
        right.post(new Runnable() {
            @Override
            public void run() {
                ViewAnimator.popInZero(right, 0, 200);
            }
        });
    }

    void hideNext(){
        Log.d("Nexting");
        right.post(new Runnable() {
            @Override
            public void run() {
                ViewAnimator.popOutZero(right, 0, 200);
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

                                exit(Factory.Alphabets.getAlphabetsUppercase().get(nextPosition), nextPosition, view);
                            }
                        }, 500);
                    }
                }, 500);
            }
        }, 500);
    }

}
