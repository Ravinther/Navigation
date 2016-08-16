package com.sygic.aura.views;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

public class ManagedViewPager extends ViewPager {
    private boolean isSwipeEnabled;

    public ManagedViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.isSwipeEnabled = true;
    }

    public boolean onTouchEvent(MotionEvent event) {
        return this.isSwipeEnabled && super.onTouchEvent(event);
    }

    public boolean onInterceptTouchEvent(MotionEvent event) {
        return this.isSwipeEnabled && super.onInterceptTouchEvent(event);
    }

    public void setPagingEnabled(boolean isSwipeEnabled) {
        this.isSwipeEnabled = isSwipeEnabled;
    }
}
