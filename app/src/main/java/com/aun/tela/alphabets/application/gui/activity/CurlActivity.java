package com.aun.tela.alphabets.application.gui.activity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.aun.tela.alphabets.R;
import com.aun.tela.alphabets.application.Application;
import com.aun.tela.alphabets.application.util.Log;

import fi.harism.curl.CurlPage;
import fi.harism.curl.CurlView;

/**
 * Created by Joey on 03/03/16 at 3:19 PM.
 * Project : TELA.
 * Copyright (c) 2016 Meengle. All rights reserved.
 */
public class CurlActivity extends AppCompatActivity {

    private CurlView mCurlView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(fi.harism.curl.R.layout.main);

        int index = 0;
        if (getLastNonConfigurationInstance() != null) {
            index = (Integer) getLastNonConfigurationInstance();
        }
        mCurlView = (CurlView) findViewById(fi.harism.curl.R.id.curl);
        mCurlView.setPageProvider(new PageProvider());
        mCurlView.setSizeChangedObserver(new SizeChangedObserver());
        mCurlView.setCurrentIndex(index);
        mCurlView.setBackgroundColor(0xFF000000);

        // This is something somewhat experimental. Before uncommenting next
        // line, please see method comments in CurlView.
        // mCurlView.setEnableTouchPressure(true);
    }

    @Override
    public void onPause() {
        super.onPause();
        mCurlView.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
        mCurlView.onResume();
    }

    /*@Override
    public Object onRetainNonConfigurationInstance() {
        return mCurlView.getCurrentIndex();
    }
    */

    /**
     * Bitmap provider.
     */
    private class PageProvider implements CurlView.PageProvider {

        // Bitmap resources.
        private int[] mBitmapIds = {
                R.drawable.goat_wants_coat_1,
                R.drawable.goat_wants_coat_2,
                R.drawable.goat_wants_coat_3,
                R.drawable.goat_wants_coat_4,
                R.drawable.goat_wants_coat_5,
                R.drawable.goat_wants_coat_6,
                R.drawable.goat_wants_coat_7,
                R.drawable.goat_wants_coat_8,
                R.drawable.goat_wants_coat_9,
                R.drawable.goat_wants_coat_10,
                R.drawable.goat_wants_coat_11,
                R.drawable.goat_wants_coat_12,
                R.drawable.goat_wants_coat_13,
                R.drawable.goat_wants_coat_14
        };

        private int getItemCount(){
            return mBitmapIds.length;
        }

        @Override
        public int getPageCount() {
            boolean even = mBitmapIds.length %2 == 0;
            return even ? mBitmapIds.length /2 : (mBitmapIds.length/2)+1;
        }

        private Bitmap loadBitmap(int width, int height, int index) {
            //Log.d("Width is "+width + ", height is "+height);
            /*Bitmap b = Bitmap.createBitmap(width, height,
                    Bitmap.Config.ARGB_8888);
            b.eraseColor(0xFFFFFFFF);
            Canvas c = new Canvas(b);
            Drawable d = getResources().getDrawable(mBitmapIds[index]);
            //c.scale(1f, -1f);
            //c.scale(index%2!=0 ? -1 : 1, 1);

            int margin = 0;
            int border = 0;
            Rect r = new Rect(margin, margin, width - margin, height - margin);


            int imageWidth = r.width() - (border * 2);
            int imageHeight = imageWidth * d.getIntrinsicHeight()
                    / d.getIntrinsicWidth();
            if (imageHeight > r.height() - (border * 2)) {
                imageHeight = r.height() - (border * 2);
                imageWidth = imageHeight * d.getIntrinsicWidth()
                        / d.getIntrinsicHeight();
            }

            r.left += ((r.width() - imageWidth) / 2) - border;
            r.right = r.left + imageWidth + border + border;
            r.top += ((r.height() - imageHeight) / 2) - border;
            r.bottom = r.top + imageHeight + border + border;

            Paint p = new Paint();
            p.setColor(0xFFC0C0C0);
            c.drawRect(r, p);
            r.left += border;
            r.right -= border;
            r.top += border;
            r.bottom -= border;

            d.setBounds(r);
            d.draw(c);

            return b;
            */
            Bitmap resBit = BitmapFactory.decodeResource(Application.getInstance().getResources(), mBitmapIds[index]);
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
            Log.d("Page number called : "+index);
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
            //switch (index) {
                // Second case is image on back side, solid colored front.
                /*case 1: {
                    Bitmap back = loadBitmap(width, height, 2);
                    page.setTexture(back, CurlPage.SIDE_BACK);
                    page.setColor(Color.rgb(127, 140, 180), CurlPage.SIDE_FRONT);
                    break;
                }
                // Third case is images on both sides.
                case 2: {
                    Bitmap front = loadBitmap(width, height, 1);
                    Bitmap back = loadBitmap(width, height, 3);
                    page.setTexture(front, CurlPage.SIDE_FRONT);
                    page.setTexture(back, CurlPage.SIDE_BACK);
                    break;
                }
                // Fourth case is images on both sides - plus they are blend against
                // separate colors.
                case 3: {
                    Bitmap front = loadBitmap(width, height, 2);
                    Bitmap back = loadBitmap(width, height, 1);
                    page.setTexture(front, CurlPage.SIDE_FRONT);
                    page.setTexture(back, CurlPage.SIDE_BACK);
                    page.setColor(Color.argb(127, 170, 130, 255),
                            CurlPage.SIDE_FRONT);
                    page.setColor(Color.rgb(255, 190, 150), CurlPage.SIDE_BACK);
                    break;
                }
                // Fifth case is same image is assigned to front and back. In this
                // scenario only one texture is used and shared for both sides.
                case 4:
                    Bitmap front = loadBitmap(width, height, 0);
                    page.setTexture(front, CurlPage.SIDE_BOTH);
                    page.setColor(Color.argb(127, 255, 255, 255),
                            CurlPage.SIDE_BACK);
                    break;
                    */
            //}
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
                mCurlView.setMargins(0f, .05f, 0f, .0f);
            } else {
                mCurlView.setViewMode(CurlView.SHOW_ONE_PAGE);
                mCurlView.setMargins(.1f, .1f, .1f, .1f);
            }
        }
    }

}
