package com.example.ivan.weatherapp.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by ivan
 */

public class TagLayout extends ViewGroup {

    public TagLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onLayout(boolean b, int l, int t, int r, int bottom) {
        int count = getChildCount();
        int left = l + getPaddingLeft();
        int top = t + getPaddingTop();
        // keeps track of maximum row height
        int rowHeight = 0;

        View view = getChildAt(0);
        int childWidth = view.getMeasuredWidth();
        int childHeight = view.getMeasuredHeight();

        view.layout(left, bottom - childHeight, left + childWidth, bottom);

        view = getChildAt(1);
        childWidth = view.getMeasuredWidth();
        childHeight = view.getMeasuredHeight();

        view.layout(r / 2 - childWidth / 2, top, r / 2 + childWidth / 2, top + childHeight);



      /*  for (int i = 0; i < count; i++) {
            View child = getChildAt(i);
            int childWidth = child.getMeasuredWidth();
            int childHeight = child.getMeasuredHeight();
// if child fits in this row put it there
            if (left + childWidth < r - getPaddingRight()) {
                child.layout(left, top, left + childWidth, top +
                        childHeight);
                left += childWidth;
            } else {
// otherwise put it on next row
                left = l + getPaddingLeft();
                top += rowHeight;
                rowHeight = 0;
            }
// update maximum row height
            if (childHeight > rowHeight) rowHeight = childHeight;
        }*/
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int count = getChildCount();
        int
                rowHeight = 0;
        int maxWidth = 0;
        int maxHeight = 0;
        int left = 0;
        int top = 0;
        for (int i = 0; i < count; i++) {
            View child = getChildAt(i);
            measureChild(child, widthMeasureSpec, heightMeasureSpec);
            int childWidth = child.getMeasuredWidth();
            int childHeight = child.getMeasuredHeight();
// if child fits in this row put it there
            if (left + childWidth < getWidth()) {
                left += childWidth;
            } else {
// otherwise put it on next row
                if (left > maxWidth) maxWidth = left;
                left = 0;
                top += rowHeight;
                rowHeight = 0;
            }
// update maximum row height
            if (childHeight > rowHeight) rowHeight = childHeight;
        }
        if (left > maxWidth) maxWidth = left;
        maxHeight = top + rowHeight;
        setMeasuredDimension(getMeasure(widthMeasureSpec, 500),
                getMeasure(heightMeasureSpec, 500));
    }

    private int getMeasure(int spec, int desired) {
        switch (MeasureSpec.getMode(spec)) {
            case MeasureSpec.EXACTLY:
                return MeasureSpec.getSize(spec);
            case MeasureSpec.AT_MOST:
                return Math.min(MeasureSpec.getSize(spec), desired);
            case MeasureSpec.UNSPECIFIED:
            default:
                return desired;
        }
    }
}