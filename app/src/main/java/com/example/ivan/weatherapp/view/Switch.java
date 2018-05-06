package com.example.ivan.weatherapp.view;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Build;
import android.support.annotation.DrawableRes;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Checkable;

import com.example.ivan.weatherapp.R;

/**
 * Created by ivan
 */

public class Switch extends View implements Checkable {
    private boolean isChecked = false;

    private Paint bgRectPaint;
    private Paint trueBgRectPaint;
    private Paint topPaint;
    private Paint bitmapPaint;

    private int bgColor;
    private int trueBgColor;
    private int topColor;
    private float bgHeigth;
    private float bgWidth;
    private float toogleSize;
    private float radius;

    private ValueAnimator valueAnimator;
    private float toogleCenter;
    private float trueBgPaintPercent;

    private GestureDetector gestureDetector;
    private boolean isDraging;
    private float savedDrawingPositionX;

    public Switch(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        readAttrs(context, attrs);

        bgRectPaint = new Paint();
        bgRectPaint.setColor(bgColor);
        bitmapPaint = new Paint(Paint.ANTI_ALIAS_FLAG);

        topPaint = new Paint();
        topPaint.setColor(topColor);

        trueBgRectPaint = new Paint();
        trueBgRectPaint.setColor(trueBgColor);


        toogleCenter = toogleSize / 2;


        setOnClickListener(view -> {
            setChecked(!isChecked());
            onCheckedChanged();
        });

        gestureDetector = new GestureDetector(getContext(), new GestureDetector.OnGestureListener() {
            @Override
            public boolean onDown(MotionEvent motionEvent) {
                if (isChecked)
                    isDraging = motionEvent.getX() >= getWidth() - toogleSize;
                else
                    isDraging = motionEvent.getX() <= toogleSize;
                savedDrawingPositionX = motionEvent.getX();
                return true;
            }

            @Override
            public void onShowPress(MotionEvent motionEvent) {

            }

            @Override
            public boolean onSingleTapUp(MotionEvent motionEvent) {
                setChecked(!isChecked);
                onCheckedChanged();
                return false;
            }

            @Override
            public boolean onScroll(MotionEvent motionEvent, MotionEvent motionEvent1, float v, float v1) {
                if (!isDraging)
                    return true;

                float newTopCenter = toogleCenter - (toogleCenter - savedDrawingPositionX);
                if (newTopCenter > getWidth() / 2)
                    toogleCenter = Math.min(toogleCenter - (toogleCenter - savedDrawingPositionX), getWidth() - toogleSize / 2);
                else
                    toogleCenter = Math.max(toogleCenter - (toogleCenter - savedDrawingPositionX), toogleSize / 2);

                trueBgPaintPercent = (toogleCenter * 100 / (getWidth() - toogleSize / 2)) / 100;

                savedDrawingPositionX = motionEvent1.getX();
                invalidate();
                return false;
            }

            @Override
            public void onLongPress(MotionEvent motionEvent) {

            }

            @Override
            public boolean onFling(MotionEvent motionEvent, MotionEvent motionEvent1, float v, float v1) {
                return false;
            }
        });
    }

    private void readAttrs(Context context, AttributeSet attributeSet) {
        TypedArray typedArray = context.obtainStyledAttributes(attributeSet, R.styleable.Switch, 0 - 1, 0);

        bgColor = typedArray.getColor(R.styleable.Switch_bg_color, ContextCompat.getColor(context, R.color.colorPrimary));
        trueBgColor = typedArray.getColor(R.styleable.Switch_trueBgColor, ContextCompat.getColor(context, R.color.colorAccent));
        topColor = typedArray.getColor(R.styleable.Switch_top_color, ContextCompat.getColor(context, R.color.colorAccent));
        bgHeigth = typedArray.getDimension(R.styleable.Switch_bg_height, 100);
        bgWidth = typedArray.getDimension(R.styleable.Switch_bg_width, 300);
        toogleSize = typedArray.getDimension(R.styleable.Switch_top_size, 150);
        radius = typedArray.getDimension(R.styleable.Switch_radius, 15);

        typedArray.recycle();
    }

    @Override
    protected void onDraw(Canvas canvas) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {

            canvas.drawRoundRect(0, getHeight() / 2 - bgHeigth / 2, getWidth(), getHeight() / 2 + bgHeigth / 2, radius, radius, bgRectPaint);

            float trueBgLeft = getWidth() / 2 - getWidth() / 2 * trueBgPaintPercent;
            float trueBgTop = getHeight() / 2 - bgHeigth * trueBgPaintPercent / 2;
            float trueBgRight = getWidth() * trueBgPaintPercent;
            float trueBgBottom = getHeight() / 2 + bgHeigth * trueBgPaintPercent / 2;

            canvas.drawRoundRect(trueBgLeft, trueBgTop, trueBgRight, trueBgBottom, radius, radius, trueBgRectPaint);
        }
        canvas.drawCircle(toogleCenter, getHeight() / 2, toogleSize / 2, topPaint);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(getMeasurementSize(widthMeasureSpec, getMeasureWidth()), getMeasurementSize(heightMeasureSpec, getMeasureHeigth()));
    }

    private static int getMeasurementSize(int measureSpec, int defaultSize) {
        int mode = MeasureSpec.getMode(measureSpec);
        int size = MeasureSpec.getSize(measureSpec);

        switch (mode) {
            case MeasureSpec.EXACTLY:
                return size;
            case MeasureSpec.AT_MOST:
                return Math.min(defaultSize, size);
            case MeasureSpec.UNSPECIFIED:
            default:
                return defaultSize;
        }
    }

    public void onCheckedChanged() {
        if (valueAnimator != null)
            valueAnimator.cancel();

        float from = isChecked ? toogleSize / 2 : getWidth() - toogleSize / 2;
        float to = isChecked ? getWidth() - toogleSize / 2 : toogleSize / 2;

        animateSwitch(from, to);
    }

    private void onUp() {
        if (valueAnimator != null)
            valueAnimator.cancel();

        float from = toogleCenter;
        float to = isChecked ? getWidth() - toogleSize / 2 : toogleSize / 2;

        animateSwitch(from, to);

    }

    private void animateSwitch(float from, float to) {
        valueAnimator = ValueAnimator.ofFloat(from, to);

        valueAnimator.addUpdateListener(valueAnimator1 -> {
            toogleCenter = ((Float) valueAnimator1.getAnimatedValue());
            trueBgPaintPercent = (isChecked ? ((Float) valueAnimator.getAnimatedValue()) * 100 / to : (((Float) valueAnimator.getAnimatedValue() - to) * 100) / (from - to)) / 100;
            invalidate();
        });

        valueAnimator.setDuration(200);
        valueAnimator.start();
    }

    private void changeIsCheckedByToogleCenter() {
        if (toogleCenter > getWidth() / 2)
            setChecked(true);
        else
            setChecked(false);
    }


    private int getMeasureHeigth() {
        return (int) Math.max(toogleSize, bgHeigth) + 1;
    }

    private int getMeasureWidth() {
        return (int) bgWidth + 1;
    }

    @Override
    public void setChecked(boolean b) {
        isChecked = b;
    }

    @Override
    public boolean isChecked() {
        return isChecked;
    }

    @Override
    public void toggle() {

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_UP && isDraging) {
            changeIsCheckedByToogleCenter();
            onUp();
            return true;
        }
        return gestureDetector.onTouchEvent(event);
    }
}
