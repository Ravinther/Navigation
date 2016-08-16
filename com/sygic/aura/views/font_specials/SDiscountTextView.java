package com.sygic.aura.views.font_specials;

import android.content.Context;
import android.util.AttributeSet;

public class SDiscountTextView extends STextView {
    public SDiscountTextView(Context context) {
        this(context, null);
    }

    public SDiscountTextView(Context context, AttributeSet attrs) {
        this(context, attrs, 2130772068);
    }

    public SDiscountTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        if (w != oldw || h != oldh) {
            setTranslationX((float) ((-w) / 2));
            setTranslationY((float) ((-h) / 2));
        }
    }
}
