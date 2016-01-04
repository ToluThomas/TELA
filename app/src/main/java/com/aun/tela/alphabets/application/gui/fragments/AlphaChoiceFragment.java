package com.aun.tela.alphabets.application.gui.fragments;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.animation.ValueAnimator;
import android.graphics.Rect;
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
import com.aun.tela.alphabets.application.generic.DoubleRetriever;
import com.aun.tela.alphabets.application.generic.QuatroConsumer;
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

public class AlphaChoiceFragment extends Fragtivity {

    View back, scrollDown, scrollUp;
    String exitString = null;
    CircularColorView backCircularColorView, scrollDownCircularColorView, scrollUpCircularColorView;
    Integer exitPosition = null;
    HeaderFooterGridView alphaGrid;
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
        back = findViewById(R.id.back);
        scrollDown = findViewById(R.id.scrollDown);
        scrollUp = findViewById(R.id.scrollUp);
        alphaGrid = (HeaderFooterGridView) findViewById(R.id.alphaGrid);
        backCircularColorView = (CircularColorView) findViewById(R.id.backCircularView);
        scrollDownCircularColorView = (CircularColorView) findViewById(R.id.scrollDownCircularView);
        scrollUpCircularColorView = (CircularColorView) findViewById(R.id.scrollUpCircularView);
    }

    @Override
    public void setupViews() {
        int a = textColor;
        int b = borderColor;
        Activity.setColor(b);

        backCircularColorView.setBorderColor(b);
        backCircularColorView.setCircularColor(a);

        scrollDownCircularColorView.setBorderColor(b);
        scrollDownCircularColorView.setCircularColor(a);
        scrollUpCircularColorView.setCircularColor(a);
        scrollUpCircularColorView.setBorderColor(b);
        ViewAnimator.springify(back);
        ViewAnimator.springify(scrollDown);
        ViewAnimator.springify(scrollUp);
        scrollUp.setAlpha(0);
        ViewAnimator.upDownify(back, 20, 300, 700);
        ViewAnimator.upDownify(scrollDown, -20, 500, 700);
        ViewAnimator.upDownify(scrollUp, 20, 500, 700);
        List<String> items = Factory.Alphabets.copyAlphabetsUppercase();
        final Random rand = new Random();
        adapter = GenericItemAdapter.<String, ViewHolder>getInstance()
                .setItems(items)
                .setIdRetriever(new DoubleRetriever<Long, String, Integer>() {
                    @Override
                    public Long retrieve(String s, Integer integer) {
                        return Value.LONG(integer);
                    }
                }).setViewRetriever(new DoubleRetriever<ViewHolder, ViewGroup, Integer>() {
                    @Override
                    public ViewHolder retrieve(ViewGroup viewGroup, Integer integer) {
                        return ViewHolder.inflateDefault(viewGroup);
                    }
                }).setViewConsumer(new QuatroConsumer<ViewHolder, String, Integer, Boolean>() {
                    @Override
                    public void consume(ViewHolder viewHolder, final String s, final Integer integer, Boolean aBoolean) {
                        ViewHolder.setup(viewHolder, s, integer, aBoolean);
                        viewHolder.itemView.setScaleX(1f);
                        viewHolder.itemView.setScaleY(1f);
                        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                exit(s, integer, v);
                            }
                        });
                        if(exiting()){
                            viewHolder.itemView.setClickable(false);
                        }
                        if(Value.NULL(viewHolder.itemView.getAnimation())) {
                            ViewAnimator.upDownify(viewHolder.itemView, 10, rand.nextInt(500), 800 + rand.nextInt(200));
                        }
                    }
                });
        addHeader(a, b);
        alphaGrid.setAdapter(adapter);
        scrollDown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                scrollDown();
            }
        });
        scrollUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                scrollUp();
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Activity.replace(new MainFragment());
            }
        });
        alphaGrid.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                ValueAnimator a = Value.Same.INTEGER(firstVisibleItem, 0) && scrollUp.getAlpha() < 1 ? ViewAnimator.popOut(scrollUp, 0, 300) : ViewAnimator.popIn(scrollUp, 0, 300);
                ValueAnimator b = Value.Same.INTEGER(firstVisibleItem + visibleItemCount, totalItemCount) && scrollDown.getAlpha() < 1 ? ViewAnimator.popOut(scrollDown, 0, 300) : ViewAnimator.popIn(scrollDown, 0, 300);
            }
        });
    }

    void addHeader(int color, int borderColor){
        View v = LayoutInflater.from(Activity.getInstance()).inflate(R.layout.alpha_choice_header, null, false);
        TextView textView = (TextView) v.findViewById(R.id.text);
        textView.setTextColor(color);
        BarColorView barColorView = (BarColorView) v.findViewById(R.id.barColorView);
        barColorView.setBorderColor(borderColor);
        barColorView.setBarColor(0xFFFFFFFF);
        alphaGrid.addHeaderView(v);
        ViewAnimator.upDownify(v, 10, 500, 1000);
        View u = LayoutInflater.from(Activity.getInstance()).inflate(R.layout.alpha_choice_header, null, false);
        TextView t = (TextView) u.findViewById(R.id.text);
        u.findViewById(R.id.barColorView).setVisibility(View.INVISIBLE);
        t.setTextColor(getColor(R.color.transparent));
        alphaGrid.addFooterView(u);
    }

    void scrollDown(){
        alphaGrid.smoothScrollToPosition(alphaGrid.getLastVisiblePosition() + 3);
    }

    void scrollUp(){
        alphaGrid.smoothScrollToPosition(alphaGrid.getFirstVisiblePosition() - 3);
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
            ViewAnimator.springify(viewHolder.itemView);
            viewHolder.itemView.setClickable(true);
            viewHolder.text.setTag(position);
            viewHolder.circularColorView.setTag(position);
            return viewHolder;
        }

    }

    boolean exiting(){
        return !Value.NULL(exitString);
    }

    private void exit(String string, int position, View view){
        exitString = string;
        exitPosition = position;
        adapter.notifyDataSetChanged();
        alphaGrid.setEnabled(false);
        final CircularColorView circularColorView = (CircularColorView) view.findViewById(R.id.circularView);
        final TextView textView = (TextView) view.findViewById(R.id.text);
        scrollUp.setClickable(false);
        scrollDown.setClickable(false);
        back.setClickable(false);
        ViewAnimator.popOut(scrollUp, 0, 200);
        ViewAnimator.popOut(scrollDown, 0, 200);
        ViewAnimator.popOut(back, 0, 200);

        int x = (getRootView().getWidth() / 2);
        int y = (getRootView().getHeight()/ 2);
        float textX = textView.getX();
        float textY = textView.getY();

        float midX = view.getX() + (view.getWidth() / 2) - getResources().getDimensionPixelSize(R.dimen.activity_horizontal_margin) + getResources().getDimensionPixelSize(R.dimen.actionBarSize);
        float midY = view.getY() + (view.getHeight() / 2);

        ((FrameLayout) view).removeView(textView);
        alphaGrid.removeViewInLayout(view);
        Rect viewRect = new Rect();
        view.getGlobalVisibleRect(viewRect);
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
                Activity.replace(AlphaLearning.getInstance(Factory.Alphabets.build(exitString), textView.getCurrentTextColor(), circularColorView.getBorderColor()));
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