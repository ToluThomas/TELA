package com.aun.tela.alphabets.application.gui.custom;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;

import com.aun.tela.alphabets.R;

import io.meengle.util.Value;

public class BarColorView extends View {

    Paint paint; //paint object to draw the bar
    Paint borderPaint; //paint object to draw the border
    int borderWidth = 0; //width of the border to be drawn
    int borderColor = 0xFFFFFFFF; //color of the border to be drawn
    int barColor = 0xFFFFFFFF; //color of the bar to be drawn
    Integer radius; //radius of the view

    public BarColorView(Context context) {
        super(context);
        init();
    }

    public BarColorView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public BarColorView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public BarColorView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(attrs);
    }

    private void init(){
        init(null);
    }

    private void init(AttributeSet attributeSet){
        paint = new Paint();
        borderPaint = new Paint();
        paint.setAntiAlias(true);
        borderPaint.setAntiAlias(true);
        if(!Value.NULL(attributeSet)) {
            TypedArray array = getResources().obtainAttributes(attributeSet, R.styleable.BarColorView);
            borderWidth = array.getDimensionPixelSize(R.styleable.BarColorView_bar_border_width, 0);
            radius  = array.getDimensionPixelSize(R.styleable.BarColorView_bar_radius, -1);
            radius = radius < 0 ? null : radius;
            if (array.getBoolean(R.styleable.BarColorView_bar_shadow, false))
                addShadow();
            if (Value.Same.INTEGER(array.getInteger(R.styleable.BarColorView_bar_border_color, -1), 0))
                setBorderColor(com.aun.tela.alphabets.application.util.Color.random());
            else
                borderColor = array.getColor(R.styleable.BarColorView_bar_border_color, 0xFFEEEEEE);
            if (Value.Same.INTEGER(array.getInteger(R.styleable.BarColorView_bar_color, -1), 0))
                setBarColor(com.aun.tela.alphabets.application.util.Color.random());
            else
                barColor = array.getColor(R.styleable.BarColorView_bar_color, 0xFFFFFFFF);
            array.recycle();
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int canvasHeight = canvas.getHeight(); //height of the canvas
        int canvasWidth = canvas.getWidth(); //width of the canvas

        int borderRadius = Value.NULL(radius) ? canvasHeight / 2 : radius; //if no radius has been set, set
        // it to half the canvas height. To make this view have square corners, set radius to 0
        int barRadius = borderRadius - borderWidth; //set the bar radius to the borders radius - the width of the border

        //initialize drawing properties and style
        borderPaint.setStyle(Paint.Style.STROKE);
        paint.setStyle(Paint.Style.FILL);
        borderPaint.setColor(borderColor);
        borderPaint.setStrokeWidth((float) borderWidth);
        paint.setColor(barColor);

        int left = getLeft();
        int top = getTop();
        int right = getRight();
        int bottom = getBottom();

        //draw bar
        canvas.drawRoundRect(new RectF(left + borderWidth, top + borderWidth, right - borderWidth, bottom - borderWidth), barRadius, barRadius, paint);

        //draw border
        canvas.drawRoundRect(new RectF(left + borderWidth, top + borderWidth, right - borderWidth, bottom - borderWidth), borderRadius, borderRadius, borderPaint);

    }

    /**
     * Add a shadow to this view
     */
    public void addShadow() {
        setLayerType(LAYER_TYPE_SOFTWARE, borderPaint);
        borderPaint.setShadowLayer(8.0f, 0.0f, 4.0f, 0x20000000);
    }

    /**
     * Set the bar color for this view
     * @param barColor the color to set
     */
    public void setBarColor(int barColor){
        this.barColor = barColor;
        this.invalidate();
    }

    /**
     * Set the radius for this view
     * @param radius the radius to set for this view
     */
    public void setRadius(int radius){
        this.radius = radius;
        this.invalidate();
    }

    /**
     * @return the radius of this view
     */
    public Integer getRadius(){
        return this.radius;
    }

    /**
     * @return the radius of this view
     */
    public int getBarColor(){
        return this.barColor;
    }

    /**
     * Set the borderColor of this view
     * @param borderColor the borderColor to set for this view
     */
    public void setBorderColor(int borderColor){
        this.borderColor = borderColor;
        this.invalidate();
    }

    /**
     * Set the borderWidth of this view
     * @param borderWidth the borderWitch to set
     */
    public void setBorderWidth(int borderWidth){
        this.borderWidth = borderWidth;
        this.requestLayout();
        this.invalidate();
    }

    /**
     * @return this views borderWidth
     */
    public int getBorderWidth(){return this.borderWidth;}

    /**
     * @return this views borderColor
     */
    public int getBorderColor(){return this.borderColor;}

<<<<<<< HEAD
    /*
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int height = measureHeight(heightMeasureSpec);
        int width = measureWidth(widthMeasureSpec);
        int size = height > width ? height : width;
        setMeasuredDimension(size, size);
    }
    */

    private int measureWidth(int measureSpec) {
        int result = 0;
        int specMode = MeasureSpec.getMode(measureSpec);
        int specSize = MeasureSpec.getSize(measureSpec);

        if (specMode == MeasureSpec.EXACTLY) {
            // The parent has determined an exact size for the child.
            result = specSize;
        } else if (specMode == MeasureSpec.AT_MOST) {
            // The child can be as large as it wants up to the specified size.
            result = specSize;
        } else {
            // The parent has not imposed any constraint on the child.
            result = canvasSize;
        }

        return result;
    }

    private int measureHeight(int measureSpecHeight) {
        int result = 0;
        int specMode = MeasureSpec.getMode(measureSpecHeight);
        int specSize = MeasureSpec.getSize(measureSpecHeight);

        if (specMode == MeasureSpec.EXACTLY) {
            // We were told how big to be
            result = specSize;
        } else if (specMode == MeasureSpec.AT_MOST) {
            // The child can be as large as it wants up to the specified size.
            result = specSize;
        } else {
            // Measure the text (beware: ascent is a negative number)
            result = canvasSize;
        }

        return (result + 2);
    }
}
=======

}
>>>>>>> 6f985d95ba92fb5c71815fabe8a04fe66a0f7d7a
