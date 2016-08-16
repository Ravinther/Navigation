package com.sygic.aura.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Outline;
import android.os.Build.VERSION;
import android.support.design.C0001R;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewOutlineProvider;
import android.widget.FrameLayout;
import android.widget.FrameLayout.LayoutParams;
import com.sygic.aura.C1090R;
import com.sygic.aura.views.font_specials.SImageView;

public class FloatingActionButton extends FrameLayout {
    private SImageView mImageView;
    private int mSize;

    /* renamed from: com.sygic.aura.views.FloatingActionButton.1 */
    class C17801 extends ViewOutlineProvider {
        C17801() {
        }

        public void getOutline(View view, Outline outline) {
            outline.setOval(0, 0, FloatingActionButton.this.mSize, FloatingActionButton.this.mSize);
        }
    }

    public FloatingActionButton(Context context) {
        super(context);
        init(context, null);
    }

    public FloatingActionButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public FloatingActionButton(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        this.mImageView = new SImageView(context);
        this.mImageView.setLayoutParams(new LayoutParams(-2, -2, 17));
        addView(this.mImageView);
        this.mSize = (int) getResources().getDimension(2131230891);
        TypedArray ta = context.obtainStyledAttributes(attrs, C1090R.styleable.FloatingActionButton);
        if (ta != null) {
            int bgRes = ta.getResourceId(7, 2130837695);
            if (VERSION.SDK_INT >= 16) {
                setBackground(getResources().getDrawable(bgRes));
            } else {
                setBackgroundDrawable(getResources().getDrawable(bgRes));
            }
            if (VERSION.SDK_INT >= 21) {
                setClipToOutline(true);
                setOutlineProvider(new C17801());
                setElevation(getResources().getDimension(C0001R.dimen.fab_elevation));
            }
            int iconRes = ta.getResourceId(6, 0);
            if (iconRes != 0) {
                this.mImageView.setFontDrawableResource(iconRes);
            }
            ta.recycle();
        }
    }

    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(this.mSize, this.mSize);
    }

    public void setFontDrawableResource(int resId) {
        if (this.mImageView != null) {
            this.mImageView.setFontDrawableResource(resId);
        }
    }
}
