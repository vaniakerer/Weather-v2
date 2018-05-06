package com.example.ivan.weatherapp.view;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;

/**
 * Created by ivan
 */

public class PagerIndicator extends View {

    private int pointsCount = 5;
    private int pointsRadius = 25;
    private int pointsMargin = 20;

    private int selectedPosition = 1;
    private int previousSelectePosition = 0;

    private int selectedPointX = 50;
    private int previousSelectedPointX;

    private Paint unselectedPint;
    private Paint selectedPaint;

    private ValueAnimator selectedValueXAnimator;
    private ValueAnimator previousSelectedValueAnimator;

    public PagerIndicator(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        unselectedPint = new Paint();
        unselectedPint.setColor(Color.BLUE);
        unselectedPint.setStyle(Paint.Style.STROKE);
        unselectedPint.setStrokeWidth(5);

        selectedPaint = new Paint();
        selectedPaint.setColor(Color.BLUE);
        selectedPaint.setStyle(Paint.Style.FILL);

        setOnClickListener(view -> plusSelected());
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        for (int i = 1; i <= pointsCount; i++) {
            if (i == selectedPosition)
                canvas.drawCircle(selectedPointX, getHeight() / 2, pointsRadius, selectedPaint);
            else if (i == previousSelectePosition) {
                canvas.drawCircle(previousSelectedPointX, getHeight() / 2/* - pointsRadius*/, pointsRadius, unselectedPint);
            } else
                canvas.drawCircle(i * (pointsRadius * 2 + pointsMargin) - pointsMargin, getHeight() / 2, pointsRadius, unselectedPint);
            Log.d("ASFasf", i + " : " + (i * (pointsRadius * 2 + pointsMargin) - pointsMargin));
        }
    }

    public void plusSelected() {
        if (selectedValueXAnimator != null)
            selectedValueXAnimator.cancel();

        previousSelectePosition = selectedPosition;

        if (selectedPosition < pointsCount)
            selectedPosition += 1;
        else
            selectedPosition = 1;

        int fromX = (previousSelectePosition) * (pointsRadius * 2 + pointsMargin) - pointsMargin;
        int toX = (selectedPosition) * (pointsRadius * 2 + pointsMargin) - pointsMargin;

        selectedValueXAnimator = ValueAnimator.ofFloat(fromX, toX);
        selectedValueXAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
        selectedValueXAnimator.addUpdateListener(valueAnimator1 -> {
            selectedPointX = ((Float) valueAnimator1.getAnimatedValue()).intValue();
            invalidate();
        });

        previousSelectedValueAnimator = ValueAnimator.ofFloat(toX, fromX);
        previousSelectedValueAnimator.addUpdateListener(valueAnimator -> previousSelectedPointX = ((Float) valueAnimator.getAnimatedValue()).intValue());

        previousSelectedValueAnimator.start();
        selectedValueXAnimator.start();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(getMeasurementSize(widthMeasureSpec, measureWidth()), getMeasurementSize(heightMeasureSpec, getMEasureHeigth()));
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

    private int measureWidth() {
        int measureWidth;
        measureWidth = pointsCount * (pointsRadius * 2 + pointsMargin) + pointsMargin + getPaddingLeft() + getPaddingRight();
        return measureWidth;
    }

    private int getMEasureHeigth() {
        int measureHeigth;
        measureHeigth = pointsRadius * 2 + getPaddingTop() + getPaddingBottom() + 20;
        return measureHeigth;
    }
}
