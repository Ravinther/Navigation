package com.sygic.aura.views.font_specials;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.Checkable;
import com.sygic.aura.C1090R;
import com.sygic.aura.helper.InCarConnection;
import com.sygic.aura.resources.FontDrawable;
import com.sygic.base.C1799R;

public class SToggleButton extends SImageButton implements Checkable {
    private boolean mChecked;
    private int mColorOff;
    private int mColorOn;
    private Drawable mDrawableOff;
    private Drawable mDrawableOn;
    private boolean mInCarScale;
    private OnCheckedChangeListener mOnCheckedChangeListener;
    private boolean mUsesColorAsToggle;

    public interface OnCheckedChangeListener {
        void onCheckedChanged(SImageButton sImageButton, boolean z);
    }

    public SToggleButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mInCarScale = false;
        TypedArray ta = context.obtainStyledAttributes(attrs, C1090R.styleable.SToggleButton);
        if (ta != null) {
            boolean z;
            int textSrcOn = ta.getResourceId(3, -1);
            int textSrcOff = ta.getResourceId(4, -1);
            int colorOn = ta.getColor(5, getResources().getColor(C1799R.color.azure_radiance));
            int colorOff = ta.getColor(6, getResources().getColor(2131558503));
            if (!isInEditMode() && InCarConnection.isInCarConnected() && ta.getBoolean(0, false)) {
                z = true;
            } else {
                z = false;
            }
            this.mInCarScale = z;
            if (textSrcOn == -1 || textSrcOff == -1) {
                this.mUsesColorAsToggle = true;
                this.mColorOn = colorOn;
                this.mColorOff = colorOff;
            } else {
                this.mUsesColorAsToggle = false;
                if (isInEditMode()) {
                    this.mDrawableOn = getResources().getDrawable(17301533);
                    this.mDrawableOff = getResources().getDrawable(17301533);
                } else {
                    this.mDrawableOn = FontDrawable.inflate(getResources(), textSrcOn);
                    this.mDrawableOff = FontDrawable.inflate(getResources(), textSrcOff);
                }
            }
            syncDrawableState();
            ta.recycle();
        }
    }

    private void syncDrawableState() {
        if (this.mUsesColorAsToggle) {
            Drawable drawable = getDrawable();
            if (drawable != null && (drawable instanceof FontDrawable)) {
                ((FontDrawable) drawable).setColor(this.mChecked ? this.mColorOn : this.mColorOff);
            }
        } else if (this.mDrawableOn != null && this.mDrawableOff != null) {
            setImageDrawable(this.mChecked ? this.mDrawableOn : this.mDrawableOff);
        }
    }

    public void setChecked(boolean checked) {
        if (this.mChecked != checked) {
            this.mChecked = checked;
            syncDrawableState();
            if (this.mOnCheckedChangeListener != null) {
                this.mOnCheckedChangeListener.onCheckedChanged(this, this.mChecked);
            }
        }
    }

    public boolean isChecked() {
        return this.mChecked;
    }

    public void toggle() {
        setChecked(!this.mChecked);
    }

    public void setOnCheckedChangeListener(OnCheckedChangeListener listener) {
        this.mOnCheckedChangeListener = listener;
    }

    public boolean performClick() {
        toggle();
        return super.performClick();
    }

    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        if (this.mInCarScale) {
            setMeasuredDimension(InCarConnection.updateViewMeasuredDimension(widthMeasureSpec), InCarConnection.updateViewMeasuredDimension(heightMeasureSpec));
        } else {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        }
    }
}
