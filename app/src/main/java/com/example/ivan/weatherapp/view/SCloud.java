package com.example.ivan.weatherapp.view;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Created by ivan
 */

public class SCloud extends View {

    private Paint rectPaint;
    private Paint borderedPaint;
    private Random random;
    private GestureDetector gestureDetector;
    private int minRectWidth = 8;
    private float maxRectWidth = 8.1f;
    private float currentRectWidth = minRectWidth;

    private int margin = 2;
    private int rectsCount = 200;

    private float scrolledX;
    private float lastX;

    private ValueAnimator rectsSizeAnimator;

    private float savedPreviousScrolle;

    private float[] coords = new float[rectsCount];

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public SCloud(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        rectPaint = new Paint();
        rectPaint.setColor(Color.WHITE);
        rectPaint.setStyle(Paint.Style.FILL);

        borderedPaint = new Paint();
        borderedPaint.setColor(Color.YELLOW);
        borderedPaint.setStyle(Paint.Style.STROKE);

        random = new Random();

        gestureDetector = new GestureDetector(getContext(), new GestureDetector.OnGestureListener() {
            @Override
            public boolean onDown(MotionEvent motionEvent) {
                lastX = motionEvent.getX();
                animBig();
                return true;
            }

            @Override
            public void onShowPress(MotionEvent motionEvent) {

            }

            @Override
            public boolean onSingleTapUp(MotionEvent motionEvent) {
                return true;
            }

            @Override
            public boolean onScroll(MotionEvent motionEvent, MotionEvent motionEvent1, float v, float v1) {
                scrolledX = motionEvent1.getX() - lastX;
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

        for (int i = 0; i < rectsCount; i++) {
            coords[i] = ThreadLocalRandom.current().nextInt(100, 200);
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        for (int i = 1; i < rectsCount; i++) {
            float x = i * (currentRectWidth + margin) + scrolledX + savedPreviousScrolle;
            if (x > getWidth() / 2)
                canvas.drawRect(x, getHeight() - coords[i], i * (currentRectWidth + margin) + currentRectWidth + scrolledX + savedPreviousScrolle, getHeight(), rectPaint);
            else
                canvas.drawRect(x, getHeight() - coords[i], i * (currentRectWidth + margin) + currentRectWidth + scrolledX + savedPreviousScrolle, getHeight(), borderedPaint);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_UP) {
            savedPreviousScrolle += scrolledX;
            animSmall();
        }

        return gestureDetector.onTouchEvent(event);
    }

    private void animBig() {
      /*  rectsSizeAnimator = ValueAnimator.ofFloat(currentRectWidth, maxRectWidth);
        rectsSizeAnimator.setDuration(100);
        rectsSizeAnimator.addUpdateListener(valueAnimator -> {
            currentRectWidth = ((Float) valueAnimator.getAnimatedValue());
            invalidate();
        });
        rectsSizeAnimator.start();*/
    }

    private void animSmall(){
       /* rectsSizeAnimator = ValueAnimator.ofFloat(currentRectWidth, minRectWidth);
        rectsSizeAnimator.setDuration(100);
        rectsSizeAnimator.addUpdateListener(valueAnimator -> {
            currentRectWidth = ((Float) valueAnimator.getAnimatedValue());
            invalidate();
        });

        rectsSizeAnimator.start();*/
    }
}