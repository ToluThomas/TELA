package com.aun.tela.alphabets.application.gui.fragments;

import android.animation.Animator;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Matrix;
import android.os.Bundle;
import android.view.View;

import com.aun.tela.alphabets.R;
import com.aun.tela.alphabets.application.Application;
import com.aun.tela.alphabets.application.entities.Factory;
import com.aun.tela.alphabets.application.gui.activity.Activity;
import com.aun.tela.alphabets.application.gui.custom.CircularColorView;
import com.aun.tela.alphabets.application.util.Log;
import com.aun.tela.alphabets.application.util.ViewAnimator;

import fi.harism.curl.CurlPage;
import fi.harism.curl.CurlView;
import io.meengle.androidutil.gui.fragment.Fragtivity;

/**
 * Created by Joey on 04/03/16 at 3:14 PM.
 * Project : TELA.
 * Copyright (c) 2016 Meengle. All rights reserved.
 */
public class RealBookFragment extends Fragtivity {

    public static RealBookFragment getInstance(Factory.Book book, int textColor, int borderColor){
        return new RealBookFragment().setBook(book).setColors(textColor, borderColor);
    }

    private CurlView mCurlView;
    private Factory.Book book;
    private int textColor, borderColor;
    CircularColorView back;

    public RealBookFragment setBook(Factory.Book book){
        this.book = book; return this;
    }

    public RealBookFragment setColors(int textColor, int borderColor){
        this.textColor = textColor; this.borderColor = borderColor; return this;
    }

    @Override
    public int layoutId() {
        return R.layout.fragment_realbook;
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
        int index = 0;
        if (Activity.getInstance().getLastNonConfigurationInstance() != null) {
            index = (Integer) Activity.getInstance().getLastNonConfigurationInstance();
        }
        mCurlView = (CurlView) findViewById(fi.harism.curl.R.id.curl);
        mCurlView.setPageProvider(new PageProvider());
        mCurlView.setSizeChangedObserver(new SizeChangedObserver());
        mCurlView.setCurrentIndex(index);
        mCurlView.setBackgroundColor(Color.TRANSPARENT);
        back  = (CircularColorView) findViewById(R.id.back);
        ViewAnimator.popInZero(back, 100, 300);
        ViewAnimator.upDownify(back, 10, 0, 1000);
        ViewAnimator.springify(back, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                exit();
            }
        });

        ViewAnimator.fadeIn(findViewById(R.id.ui), 0, 300);
        back.setCircularColor(textColor);
    }

    @Override
    public void setupViews() {

    }

    @Override
    public void pause() {
        mCurlView.onPause();
    }

    @Override
    public void resume() {
        mCurlView.onResume();
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

    private class PageProvider implements CurlView.PageProvider {

        // Bitmap resources.
        private int getItemCount(){
            return book.getPageCount();
        }

        @Override
        public int getPageCount() {
            boolean even = book.getPageCount() %2 == 0;
            return even ? book.getPageCount() /2 : (book.getPageCount()/2)+1;
        }

        private Bitmap loadBitmap(int width, int height, int index) {
            Bitmap resBit = BitmapFactory.decodeResource(Application.getInstance().getResources(), book.getPage(index));
            Matrix matrix = new Matrix();
            matrix.preScale(index%2==0 ? 1f : -1f, 1f);
            int w = resBit.getWidth();
            int h = resBit.getHeight();
            Bitmap bitmap = Bitmap.createBitmap(resBit, 0, 0, w, h, matrix, false);
            if(!bitmap.equals(resBit))
                resBit.recycle();
            return bitmap;
        }

        @Override
        public void updatePage(CurlPage page, int width, int height, int index) {
            Log.d("Page number called : " + index);
            final int i = index * 2;
            final int j = i+1;

            try {

                if (index == 0) {
                    Bitmap back = loadBitmap(width, height, i);
                    page.setTexture(back, CurlPage.SIDE_FRONT);
                    if (index + i != getItemCount()) {
                        Bitmap front = loadBitmap(width, height, j);
                        page.setTexture(front, CurlPage.SIDE_BACK);
                    }
                } else {
                    Bitmap back = loadBitmap(width, height, i);
                    page.setTexture(back, CurlPage.SIDE_FRONT);
                    if (index + i != getItemCount()) {
                        Bitmap front = loadBitmap(width, height, j);
                        page.setTexture(front, CurlPage.SIDE_BACK);
                    }
                }
            }
            catch (Exception e){
                Log.d("Exception eis "+e.getMessage());
            }
        }

    }

    /**
     * CurlView size changed observer.
     */
    private class SizeChangedObserver implements CurlView.SizeChangedObserver {
        @Override
        public void onSizeChanged(int w, int h) {
            if (w > h) {
                mCurlView.setViewMode(CurlView.SHOW_TWO_PAGES);
                mCurlView.setMargins(0f, .0f, 0f, .0f);
            } else {
                mCurlView.setViewMode(CurlView.SHOW_ONE_PAGE);
                mCurlView.setMargins(.1f, .1f, .1f, .1f);
            }
        }
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
                Activity.replace(ReadingNavigationFragment.getInstance(textColor, borderColor));
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
    }
}
