package com.aun.tela.alphabets.application.gui.fragments;

import android.animation.ValueAnimator;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.AbsListView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.aun.tela.alphabets.R;
import com.aun.tela.alphabets.application.generic.DoubleRetriever;
import com.aun.tela.alphabets.application.generic.QuatroConsumer;
import com.aun.tela.alphabets.application.gui.activity.Activity;
import com.aun.tela.alphabets.application.gui.adapter.GenericItemAdapter;
import com.aun.tela.alphabets.application.gui.custom.BarColorView;
import com.aun.tela.alphabets.application.gui.custom.CircularColorView;
import com.aun.tela.alphabets.application.util.Color;
import com.aun.tela.alphabets.application.util.ViewAnimator;
import com.sothree.slidinguppanel.SlidingUpPanelLayout;

import java.util.List;
import java.util.Random;

import io.meengle.androidutil.gui.fragment.Fragtivity;
import io.meengle.util.Value;

public class MainFragment extends Fragtivity implements SlidingUpPanelLayout.PanelSlideListener{

    View back, scrollDown, scrollUp;
    CircularColorView backCircularColorView, scrollDownCircularColorView, scrollUpCircularColorView;
    ImageButton chevron;
    SlidingUpPanelLayout slidingUpPanelLayout;
    BarColorView titleBar;
    TextView title;
    ListView list;
    GenericItemAdapter<AdapterItem, ViewHolder> adapter;

    @Override
    public int layoutId() {
        return R.layout.fragment_main;
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
        scrollDown = findViewById(R.id.scrollDown);
        scrollUp = findViewById(R.id.scrollUp);
        chevron = (ImageButton) findViewById(R.id.chevron);
        slidingUpPanelLayout = (SlidingUpPanelLayout) findViewById(R.id.slidingLayout);
        titleBar = (BarColorView) findViewById(R.id.titleBar);
        title = (TextView) findViewById(R.id.title);
        list = (ListView) findViewById(R.id.list);
        scrollDownCircularColorView = (CircularColorView) findViewById(R.id.scrollDownCircularView);
        scrollUpCircularColorView = (CircularColorView) findViewById(R.id.scrollUpCircularView);    }

    ViewTreeObserver.OnGlobalLayoutListener listener;

    @Override
    public void setupViews() {
        listener = new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    getRootView().getViewTreeObserver().removeOnGlobalLayoutListener(listener);
                }else{
                    getRootView().getViewTreeObserver().removeGlobalOnLayoutListener(listener);
                }
                setup();
            }
        };
        getRootView().getViewTreeObserver().addOnGlobalLayoutListener(listener);
    }

    void setup(){
        int a = Color.random();
        int b = Color.random();
        Activity.setColor(b);

        scrollDownCircularColorView.setBorderColor(b);
        scrollDownCircularColorView.setCircularColor(a);
        scrollUpCircularColorView.setCircularColor(a);
        scrollUpCircularColorView.setBorderColor(b);

        ViewAnimator.springify(scrollDown, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                scrollDown();
            }
        });
        ViewAnimator.springify(scrollUp, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                scrollUp();
            }
        });
        scrollUp.setAlpha(0);
        ViewAnimator.upDownify(scrollDown, -10, 500, 700);
        ViewAnimator.upDownify(scrollUp, 10, 500, 700);

        list.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                ValueAnimator a = Value.Same.INTEGER(firstVisibleItem, 0) && scrollUp.getAlpha() < 1 ? ViewAnimator.popOut(scrollUp, 0, 300) : ViewAnimator.popIn(scrollUp, 0, 300);
                ValueAnimator b = Value.Same.INTEGER(firstVisibleItem + visibleItemCount, totalItemCount) && scrollDown.getAlpha() < 1 ? ViewAnimator.popOut(scrollDown, 0, 300) : ViewAnimator.popIn(scrollDown, 0, 300);
            }
        });

        slidingUpPanelLayout.setParallaxOffset(getRootView().getHeight());


        slidingUpPanelLayout.setEnabled(false);
       // ViewAnimator.popInZero(chevron, 500, 300);
       // ViewAnimator.upDownify(chevron, 10, 400, 700);
        slidingUpPanelLayout.setPanelSlideListener(this);
        ViewAnimator.upDownify(findViewById(R.id.titleBarLayout), 10, 500, 1000);
        title.setTextColor(a);
        titleBar.setBarColor(0xFFFFFFFF);
        titleBar.setBorderColor(b);
        chevron.setClickable(false);
        slidingUpPanelLayout.setDragView(chevron);
        List<AdapterItem> items = Value.As.<AdapterItem>LIST(new AdapterItem(0, getString(R.string.alphabets), a, b)/*, new AdapterItem(1, getString(R.string.vowels), a, b), new AdapterItem(2, getString(R.string.numbers), a, b)*/);
        final Random rand = new Random();
        adapter = GenericItemAdapter.<AdapterItem, ViewHolder>getInstance()
                .setItems(items)
                .setIdRetriever(new DoubleRetriever<Long, AdapterItem, Integer>() {
                    @Override
                    public Long retrieve(AdapterItem adapterItem, Integer integer) {
                        return integer.longValue();
                    }
                })
                .setViewConsumer(new QuatroConsumer<ViewHolder, AdapterItem, Integer, Boolean>() {
                    @Override
                    public void consume(ViewHolder viewHolder, final AdapterItem adapterItem, Integer integer, Boolean aBoolean) {
                        ViewHolder.setup(viewHolder,adapterItem, integer, aBoolean );
                        viewHolder.itemView.setScaleX(1f);
                        viewHolder.itemView.setScaleY(1f);
                        viewHolder.itemView.setOnTouchListener(new View.OnTouchListener() {
                            @Override
                            public boolean onTouch(View v, MotionEvent event) {
                                BarColorView titleBar = (BarColorView)v.findViewById(R.id.titleBar);
                                switch (event.getAction()){
                                    case MotionEvent.ACTION_DOWN:
                                        titleBar.setBarColor(titleBar.getBorderColor());
                                        break;
                                    case MotionEvent.ACTION_UP:
                                        titleBar.setBarColor(0xFFFFFFFF);
                                        break;
                                }
                                return false;
                            }
                        });
                        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                exit(adapterItem, v);
                            }
                        });
                        if(Value.NULL(viewHolder.itemView.getAnimation())) {
                            ViewAnimator.upDownify(viewHolder.itemView, 4, rand.nextInt(500), 800 + rand.nextInt(200));
                        }
                    }
                })
                .setViewRetriever(new DoubleRetriever<ViewHolder, ViewGroup, Integer>() {
                    @Override
                    public ViewHolder retrieve(ViewGroup viewGroup, Integer integer) {
                        return ViewHolder.inflateDefault(viewGroup);
                    }
                });
        list.setAdapter(adapter);
    }

    class AdapterItem {
        public int id;
        public String title;
        public int borderColor;
        public int textColor;

        public AdapterItem(int id, String title, int textColor, int borderColor){
            this.id = id; this.title = title; this.borderColor = borderColor; this.textColor = textColor;
        }
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        public static ViewHolder inflateDefault(ViewGroup viewGroup){
            return new ViewHolder(LayoutInflater.from(Activity.getInstance()).inflate(R.layout.item_list_navigation, viewGroup, false));
        }

        TextView title;
        BarColorView titleBar;

        public static ViewHolder setup(ViewHolder viewHolder, AdapterItem adapterItem, Integer position, Boolean isLast){
                viewHolder.titleBar.setBorderColor(Color.random());
                viewHolder.titleBar.setBarColor(0xFFFFFFFF);
                viewHolder.title.setText(adapterItem.title.toUpperCase());
                viewHolder.title.setTextColor(Color.random());
                viewHolder.itemView.setClickable(true);
                viewHolder.title.setTag(position);
                viewHolder.titleBar.setTag(position);
                return viewHolder;
        }

        public ViewHolder(View itemView) {
            super(itemView);
            title = (TextView) findViewById(R.id.title);
            titleBar = (BarColorView) findViewById(R.id.titleBar);
        }

        View findViewById(int resId){
            return itemView.findViewById(resId);
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

    @Override
    public void onPanelSlide(View panel, float slideOffset) {
        chevron.setRotation(slideOffset * 180f);
    }

    @Override
    public void onPanelCollapsed(View panel) {

    }

    @Override
    public void onPanelExpanded(View panel) {

    }

    @Override
    public void onPanelAnchored(View panel) {

    }

    @Override
    public void onPanelHidden(View panel) {

    }

    void exit(final AdapterItem adapterItem, View view){
        final BarColorView bar = (BarColorView) view.findViewById(R.id.titleBar);
        TextView title = (TextView) view.findViewById(R.id.title);
        final int textColor = title.getCurrentTextColor();
        final int borderColor = bar.getBorderColor();

        /*
        Rect viewRect = new Rect();
        view.getGlobalVisibleRect(viewRect);
        int top = getResources().getDimensionPixelSize(R.dimen.activity_horizontal_margin) * 2;
        int to = getResources().getInteger(R.integer.text_size_display3);
        int from = getResources().getInteger(R.integer.text_size_display2);
        TextView textView = new TextView(getContext());

        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.gravity = Gravity.TOP | Gravity.LEFT;
        textView.setText(title.getText());
        textView.setTextSize(from);
        textView.setTypeface(null, Typeface.BOLD);
        textView.setTextColor(title.getCurrentTextColor());

        ((FrameLayout)getRootView()).addView(textView, ((FrameLayout) getRootView()).getChildCount(), params);
        textView.setTranslationX(title.getX());
        textView.setTranslationY(title.getY());
        title = textView;
        ValueAnimator animator = ObjectAnimator.ofFloat(title, "translationY", title.getY(), top);
        Log.d("Y "+view.getY());
        Log.d("translation Y: "+view.getTranslationY());
        Log.d("title Y"+title.getY());
        Log.d("title translation Y"+title.getTranslationY());
        Log.d("bar Y" + bar.getY());
        Log.d("bar translation Y" + bar.getTranslationY());
        ValueAnimator textSizeAnimator  = ObjectAnimator.ofFloat(title, "textSize", from, to);
        textSizeAnimator.setDuration(800);
        animator.setInterpolator(new AnticipateOvershootInterpolator());
        animator.setDuration(800);
        animator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                getRootView().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        switch (adapterItem.id) {
                            case 0:
                                Activity.replace(AlphaChoiceFragment.getInstance(textColor, borderColor));
                                break;
                        }
                    }
                }, 200);
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        textSizeAnimator.start();
        animator.start();
        */
        switch (adapterItem.id) {
            case 0:
                Activity.replace(AlphaChoiceFragment.getInstance(textColor, borderColor));
                break;
        }
    }

    void scrollDown(){
        list.smoothScrollToPosition(list.getLastVisiblePosition() + 1);
    }

    void scrollUp(){
        list.smoothScrollToPosition(list.getFirstVisiblePosition() - 1);
    }
}