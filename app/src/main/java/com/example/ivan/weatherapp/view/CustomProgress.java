package com.example.ivan.weatherapp.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LightingColorFilter;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by ivan
 */

public class CustomProgress extends View {

    private Paint linePaint;
    private Paint circlePaint;
    private Paint innerCirclePaint;

    private GestureDetector gestureDetector;

    private int selectedX = 0;
    private int selectedY = 0;
    private boolean isPressed = false;

    private int innerCircleWidth = 0;

    public CustomProgress(Context context) {
        super(context);
    }

    public CustomProgress(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        linePaint = new Paint();
        linePaint.setStyle(Paint.Style.FILL);
        linePaint.setColor(Color.BLUE);

        circlePaint = new Paint();
        circlePaint.setColor(Color.GREEN);
        circlePaint.setStyle(Paint.Style.FILL);
        circlePaint.setStrokeWidth(5);

        innerCirclePaint = new Paint();
        innerCirclePaint.setColor(Color.GREEN);
        innerCirclePaint.setStyle(Paint.Style.STROKE);

        gestureDetector = new GestureDetector(getContext(), new GestureDetector.OnGestureListener() {
            @Override
            public boolean onDown(MotionEvent motionEvent) {
                Log.d("test_gest", "onShowPress()" + motionEvent.getAction());
                selectedX = (int) motionEvent.getX();
                selectedY = (int) motionEvent.getY();
                invalidate();
                return true;
            }

            @Override
            public void onShowPress(MotionEvent motionEvent) {
                Log.d("test_gest", "onShowPress()" + motionEvent.getAction());
            }

            @Override
            public boolean onSingleTapUp(MotionEvent motionEvent) {
                Log.d("test_gest", "onSingleTapUp()" + motionEvent.getAction());
                return false;
            }

            @Override
            public boolean onScroll(MotionEvent motionEvent, MotionEvent motionEvent1, float v, float v1) {

                Log.d("test_gest", "onScroll()" + motionEvent.getAction() + " " + motionEvent1.getAction());
                selectedX = (int) motionEvent1.getX();
                selectedY = (int) motionEvent1.getY();
                invalidate();
                return false;
            }

            @Override
            public void onLongPress(MotionEvent motionEvent) {
                Log.d("test_gest", "onScroll()" + motionEvent.getAction());
            }

            @Override
            public boolean onFling(MotionEvent motionEvent, MotionEvent motionEvent1, float v, float v1) {
                Log.d("test_gest", "onScroll()" + motionEvent.getAction() + " " + motionEvent1.getAction());
                isPressed = true;
                selectedX = (int) motionEvent1.getX();
                selectedY = (int) motionEvent1.getY();
                invalidate();
                return true;
            }
        });
    }

    @SuppressLint("DrawAllocation")
    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawColor(Color.RED);

        canvas.drawRect(new RectF(0, getHeight() - 50, selectedX, getHeight() / 2 - 50), linePaint);
        canvas.drawCircle(selectedX, getHeight() / 4, getHeight() / 4, circlePaint);

        if (isPressed) {
            innerCircleWidth += 1;
            canvas.drawCircle(selectedX, getHeight() / 2, innerCircleWidth, innerCirclePaint);
            invalidate();
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        boolean gestureDetectorResult = gestureDetector.onTouchEvent(event);

        if (!gestureDetectorResult && event.getAction() == MotionEvent.ACTION_UP) {
            isPressed = false;
            innerCircleWidth = 0;
            invalidate();
            return false;
        }
        return gestureDetectorResult;
    }
}
