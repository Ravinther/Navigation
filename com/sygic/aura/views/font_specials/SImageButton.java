package com.sygic.aura.views.font_specials;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.widget.ImageButton;
import com.sygic.aura.C1090R;
import com.sygic.aura.helper.InCarConnection;
import com.sygic.aura.resources.FontDrawable;

public class SImageButton extends ImageButton {
    private boolean mInCarScale;

    public SImageButton(Context context, AttributeSet attrs) {
        boolean z = true;
        super(context, attrs);
        this.mInCarScale = false;
        TypedArray ta = context.obtainStyledAttributes(attrs, C1090R.styleable.SImageButton);
        if (ta != null) {
            int textSrc = ta.getResourceId(1, -1);
            if (!(!isInEditMode() && InCarConnection.isInCarConnected() && ta.getBoolean(0, false))) {
                z = false;
            }
            this.mInCarScale = z;
            ta.recycle();
            if (textSrc == -1) {
                return;
            }
            if (isInEditMode()) {
                setImageDrawable(getResources().getDrawable(17301533));
            } else {
                setImageDrawable(FontDrawable.inflate(getResources(), textSrc));
            }
        }
    }

    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        if (this.mInCarScale) {
            setMeasuredDimension(InCarConnection.updateViewMeasuredDimension(widthMeasureSpec), InCarConnection.updateViewMeasuredDimension(heightMeasureSpec));
        } else {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        }
    }
}
