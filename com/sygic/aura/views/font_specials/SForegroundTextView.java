package com.sygic.aura.views.font_specials;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.os.Build.VERSION;
import android.util.AttributeSet;
import com.sygic.aura.C1090R;

public class SForegroundTextView extends STextView {
    private Drawable mForeground;

    public SForegroundTextView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SForegroundTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        TypedArray a = context.obtainStyledAttributes(attrs, C1090R.styleable.SForegroundImageView, defStyle, 0);
        Drawable d = a.getDrawable(0);
        if (d != null) {
            setForeground(d);
        }
        a.recycle();
    }

    protected boolean verifyDrawable(Drawable who) {
        return super.verifyDrawable(who) || who == this.mForeground;
    }

    public void jumpDrawablesToCurrentState() {
        super.jumpDrawablesToCurrentState();
        if (this.mForeground != null) {
            this.mForeground.jumpToCurrentState();
        }
    }

    protected void drawableStateChanged() {
        super.drawableStateChanged();
        if (this.mForeground != null && this.mForeground.isStateful()) {
            this.mForeground.setState(getDrawableState());
        }
    }

    public void setForeground(Drawable drawable) {
        if (this.mForeground != drawable) {
            if (this.mForeground != null) {
                this.mForeground.setCallback(null);
                unscheduleDrawable(this.mForeground);
            }
            this.mForeground = drawable;
            if (drawable != null) {
                setWillNotDraw(false);
                drawable.setCallback(this);
                if (drawable.isStateful()) {
                    drawable.setState(getDrawableState());
                }
            } else {
                setWillNotDraw(true);
            }
            requestLayout();
            invalidate();
        }
    }

    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        if (this.mForeground != null) {
            this.mForeground.setBounds(0, 0, getMeasuredWidth(), getMeasuredHeight());
            invalidate();
        }
    }

    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        if (this.mForeground != null) {
            this.mForeground.setBounds(0, 0, getMeasuredWidth(), getMeasuredHeight());
            invalidate();
        }
    }

    @TargetApi(21)
    public void drawableHotspotChanged(float x, float y) {
        super.drawableHotspotChanged(x, y);
        if (VERSION.SDK_INT >= 21 && this.mForeground != null) {
            this.mForeground.setHotspot(x, y);
        }
    }

    public void draw(Canvas canvas) {
        super.draw(canvas);
        if (this.mForeground != null) {
            this.mForeground.draw(canvas);
        }
    }
}
