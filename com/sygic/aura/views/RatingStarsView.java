package com.sygic.aura.views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;

public class RatingStarsView extends LinearLayout implements OnClickListener {
    private static final String TAG;
    private int mCount;
    private int mDefaultImageRes;
    private int mFilledCount;
    private int mFilledImageRes;

    static {
        TAG = RatingStarsView.class.toString();
    }

    public RatingStarsView(Context context) {
        super(context);
        this.mCount = 5;
        this.mFilledCount = 3;
        this.mDefaultImageRes = 2130838181;
        this.mFilledImageRes = 2130838180;
    }

    public RatingStarsView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mCount = 5;
        this.mFilledCount = 3;
        this.mDefaultImageRes = 2130838181;
        this.mFilledImageRes = 2130838180;
    }

    private void init() {
        setOrientation(0);
        LayoutParams params = new LayoutParams(0, -2, 1.0f);
        int i = 0;
        while (i < this.mCount) {
            ImageButton imageButton = new ImageButton(getContext());
            imageButton.setLayoutParams(params);
            imageButton.setId(i);
            imageButton.setBackgroundResource(0);
            imageButton.setImageResource(i < this.mFilledCount ? this.mFilledImageRes : this.mDefaultImageRes);
            imageButton.setOnClickListener(this);
            addView(imageButton);
            i++;
        }
    }

    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        init();
    }

    public void onClick(View view) {
        if (isEnabled()) {
            this.mFilledCount = view.getId() + 1;
            update(this.mFilledCount);
        }
    }

    private void update(int filledCount) {
        int i = 0;
        while (i < getChildCount()) {
            ((ImageButton) getChildAt(i)).setImageResource(i < filledCount ? this.mFilledImageRes : this.mDefaultImageRes);
            i++;
        }
    }

    public void setFilledStarRes(int filledImageRes) {
        this.mFilledImageRes = filledImageRes;
    }

    public void setDefaultStarRes(int defaultImageRes) {
        this.mDefaultImageRes = defaultImageRes;
    }

    public void setFilledCount(int filledCount) {
        this.mFilledCount = filledCount;
    }

    public int getFilledCount() {
        return this.mFilledCount;
    }

    public void setCount(int count) {
        this.mCount = count;
    }

    public int getCount() {
        return this.mCount;
    }

    public int getDefaultImageRes() {
        return this.mDefaultImageRes;
    }

    public void setDefaultImageRes(int defaultImageRes) {
        this.mDefaultImageRes = defaultImageRes;
    }

    public int getFilledImageRes() {
        return this.mFilledImageRes;
    }

    public void setFilledImageRes(int filledImageRes) {
        this.mFilledImageRes = filledImageRes;
    }
}
