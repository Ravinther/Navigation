package com.sygic.aura.views.font_specials;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.ImageView;
import com.sygic.aura.C1090R;
import com.sygic.aura.helper.InCarConnection;
import com.sygic.aura.resources.FontDrawable;

public class SImageView extends ImageView {
    private boolean mInCarScale;
    private int mTextSrcId;

    public SImageView(Context context) {
        super(context);
    }

    public SImageView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SImageView(Context context, AttributeSet attrs, int defStyle) {
        boolean z = false;
        super(context, attrs, defStyle);
        TypedArray ta = context.obtainStyledAttributes(attrs, C1090R.styleable.SImageView);
        if (ta != null) {
            this.mTextSrcId = ta.getResourceId(0, -1);
            if (!isInEditMode() && InCarConnection.isInCarConnected() && ta.getBoolean(0, false)) {
                z = true;
            }
            this.mInCarScale = z;
            ta.recycle();
            if (this.mTextSrcId != -1) {
                if (isInEditMode()) {
                    setImageDrawable(getResources().getDrawable(17301533));
                } else {
                    setImageDrawable(FontDrawable.inflate(getResources(), this.mTextSrcId));
                }
            }
            if (this.mInCarScale) {
                Drawable drawable = getDrawable();
                if (drawable != null && (drawable instanceof FontDrawable)) {
                    FontDrawable fontDrawable = (FontDrawable) drawable;
                    fontDrawable.setTextSize(fontDrawable.getTextSize() * InCarConnection.getPixelsRatio());
                }
            }
        }
    }

    public void setFontDrawableResource(int resId) {
        setImageDrawable(FontDrawable.inflate(getResources(), resId));
    }

    public void setFontDrawableColor(int color) {
        Drawable drawable = getDrawable();
        if (drawable != null && (drawable instanceof FontDrawable)) {
            ((FontDrawable) drawable).setColor(color);
        }
    }

    public void setFontDrawableChar(String character) {
        Drawable drawable = getDrawable();
        if (drawable != null && (drawable instanceof FontDrawable)) {
            setImageDrawable(new FontDrawable((FontDrawable) drawable, character));
        }
    }

    public void resetImageDrawable() {
        setImageDrawable(FontDrawable.inflate(getResources(), this.mTextSrcId));
    }
}
