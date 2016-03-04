package com.aun.tela.alphabets.application.gui.fragments;

import android.animation.Animator;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ImageView;

import com.aun.tela.alphabets.R;
import com.aun.tela.alphabets.application.generic.DoubleConsumer;
import com.aun.tela.alphabets.application.generic.QuatroCollector;
import com.aun.tela.alphabets.application.gui.activity.Activity;
import com.aun.tela.alphabets.application.gui.adapter.GenericItemAdapter;
import com.aun.tela.alphabets.application.gui.custom.CircularColorView;
import com.aun.tela.alphabets.application.gui.custom.HeaderFooterGridView;
import com.aun.tela.alphabets.application.util.ViewAnimator;

import java.util.ArrayList;
import java.util.List;

import io.meengle.androidutil.gui.fragment.Fragtivity;

/**
 * Created by Joey on 28/02/16 at 12:09 AM.
 * Project : TELA.
 * Copydown (c) 2016 Meengle. All downs reserved.
 */
public class ReadingNavigationFragment extends Fragtivity {

    HeaderFooterGridView grid;
    CircularColorView back, up, down;
    GenericItemAdapter<BookItem, ViewHolder> adapter;
    int textColor, borderColor;

    public static ReadingNavigationFragment getInstance(int textColor, int borderColor){
        return new ReadingNavigationFragment().setInstanceStuff(textColor, borderColor);
    }

    public ReadingNavigationFragment setInstanceStuff(int textColor, int borderColor){
        this.textColor = textColor; this.borderColor = borderColor; return this;
    }

    @Override
    public int layoutId() {
        return R.layout.fragment_navigation_reading;
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
        grid = (HeaderFooterGridView) findViewById(R.id.grid);
        back = (CircularColorView) findViewById(R.id.back);
        up = (CircularColorView) findViewById(R.id.up);
        down = (CircularColorView) findViewById(R.id.down);
    }

    @Override
    public void setupViews() {
        back.setCircularColor(textColor);
        up.setCircularColor(textColor);
        down.setCircularColor(textColor);
        ViewAnimator.popInZero(back, 100, 300);
        ViewAnimator.popInZero(up, 300, 300);
        ViewAnimator.popInZero(down, 500, 300);
        ViewAnimator.upDownify(back, 10, 100, 1000);
        ViewAnimator.upDownify(up, 10, 300, 1000);
        ViewAnimator.upDownify(down, 10, 500, 1000);
        ViewAnimator.fadeIn(findViewById(R.id.ui), 0, 300);

        ViewAnimator.springify(back, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                exit();
            }
        });

        ViewAnimator.springify(up, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                scrollUp();
            }
        });
        ViewAnimator.springify(down, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                scrollDown();
            }
        });

        List<BookItem> bookItems = new ArrayList<>();
        bookItems.add(new BookItem(0, R.drawable.ic_decodable_stories));
        bookItems.add(new BookItem(1, R.drawable.ic_reading_1));
        bookItems.add(new BookItem(2, R.drawable.ic_reading_2));
        bookItems.add(new BookItem(3, R.drawable.ic_reading_3));
        bookItems.add(new BookItem(4, R.drawable.ic_reading_4));
        bookItems.add(new BookItem(5, R.drawable.ic_reading_5));

        adapter = GenericItemAdapter.<BookItem, ViewHolder>getInstance()
                .setIdConsumer(new DoubleConsumer<Long, BookItem, Integer>() {
                    @Override
                    public Long consume(BookItem bookItem, Integer integer) {
                        return integer.longValue();
                    }
                })
                .setItems(bookItems)
                .setViewCollector(new QuatroCollector<ViewHolder, BookItem, Integer, Boolean>() {
                    @Override
                    public void collect(ViewHolder viewHolder, final BookItem bookItem, Integer integer, Boolean aBoolean) {
                        viewHolder.image.setImageResource(bookItem.iconRes);
                        viewHolder.itemView.setClickable(true);
                        ViewAnimator.springify(viewHolder.itemView, new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                select(bookItem);
                            }
                        });
                    }
                })
                .setViewConsumer(new DoubleConsumer<ViewHolder, ViewGroup, Integer>() {
                    @Override
                    public ViewHolder consume(ViewGroup viewGroup, Integer integer) {
                        return new ViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.grid_item_image, viewGroup, false));
                    }
                });

        addHeader();
        grid.setAdapter(adapter);

        grid.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                if (grid.getChildAt(0) != null && grid.getChildAt(0).getY() < 0) {
                    if (up.getAlpha() < 1) {
                        ViewAnimator.popInZero(up, 0, 300);
                    }
                } else {
                    if (up.getAlpha() > 0) {
                        ViewAnimator.popOutZero(up, 0, 300);
                    }
                }
                if (view.getLastVisiblePosition() + 1 != totalItemCount) {
                    if (down.getAlpha() < 1) {
                        ViewAnimator.popInZero(down, 0, 300);
                    }
                } else {
                    if (down.getAlpha() > 0) {
                        ViewAnimator.popOutZero(down, 0, 300);
                    }
                }
            }
        });

    }

    private void addHeader(){
        View v = LayoutInflater.from(Activity.getInstance()).inflate(R.layout.image_header, null, false); //inflate a layout from resources

        //the inflated layout contains a textview and a barColorView which we use here
        ImageView i =  (ImageView) v.findViewById(R.id.image);
        i.setImageResource(R.drawable.owl_books);
        grid.addHeaderView(v);
        ViewAnimator.springify(i, null);
        View u = LayoutInflater.from(Activity.getInstance()).inflate(R.layout.image_header, null, false);
        ImageView j =  (ImageView) u.findViewById(R.id.image);
        j.setImageResource(R.drawable.owl_calc);
        grid.addFooterView(u);
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

    private class BookItem {
        public int id;
        public int iconRes;
        public BookItem(int id, int iconRes){
            this.id = id; this.iconRes = iconRes;
        }
    }

    private static class ViewHolder extends RecyclerView.ViewHolder{

        ImageView image;

        public ViewHolder(View itemView) {
            super(itemView);
            image = (ImageView) findViewById(R.id.image);
        }

        private View findViewById(int resId){
            return itemView.findViewById(resId);
        }
    }

    void select(BookItem bookItem){
        final Book book;
        switch (bookItem.id){
            case 0:
                book = new Book(R.drawable.decodable_stories_1, R.drawable.decodable_stories_2,
                        R.drawable.decodable_stories_3, R.drawable.decodable_stories_4, R.drawable.decodable_stories_5,
                        R.drawable.decodable_stories_6);
                break;
            case 1:
                book = new Book(R.drawable.reading1_1, R.drawable.reading1_2, R.drawable.reading1_3);
                break;
            case 2:
                book = new Book(R.drawable.reading2_1, R.drawable.reading2_2, R.drawable.reading2_3);
                break;
            case 3:
                book = new Book(R.drawable.reading3_1, R.drawable.reading3_2);
                break;
            case 4:
                book = new Book(R.drawable.reading4_1, R.drawable.reading4_2, R.drawable.reading4_3, R.drawable.reading4_4);
                break;
            case 5:
                book = new Book(R.drawable.reading5_1, R.drawable.reading5_2, R.drawable.reading5_3, R.drawable.reading5_4);
                break;
            default:
                book = null;
                break;
        }
        View hud = findViewById(R.id.hud);
        View ui = findViewById(R.id.ui);
        ViewAnimator.fadeOut(hud, 0, 500);
        ViewAnimator.fadeOut(ui, 200, 500).addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                Activity.replace(BookReadingFragment.getInstance(textColor, borderColor, book));
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
    }

    void exit(){
        View hud = findViewById(R.id.hud);
        View ui = findViewById(R.id.ui);
        ViewAnimator.fadeOut(hud, 0, 500);
        ViewAnimator.fadeOut(ui, 200, 500).addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                Activity.replace(LetterNavigationFragment.getInstance(textColor, borderColor, null));
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
    }

    void scrollDown(){
        grid.smoothScrollBy(grid.getChildAt(0).getHeight(), 300);
        //grid.smoothScrollToPosition(grid.getLastVisiblePosition() + 3);
    }

    //scroll up the grid by moving to a position that offsets the current position by -3, since we have three columns for our grid
    void scrollUp(){
        grid.smoothScrollToPosition(grid.getFirstVisiblePosition() - 3);
    }

    public static class Book {
        public List<Integer> pagesResId;

        public Book(int... pageResIds){
            List<Integer> pages = new ArrayList<>(pageResIds.length);
            for(int i : pageResIds){
                pages.add(i);
            }
            this.pagesResId = pages;
        }
    }

}
