package com.sygic.aura.views;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.SurfaceView;

public class WhiteSurfaceView extends SurfaceView {
    private Drawable mBackground;
    private boolean mInitialised;

    public WhiteSurfaceView(Context context) {
        super(context);
        this.mInitialised = false;
        init();
    }

    public WhiteSurfaceView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mInitialised = false;
        init();
    }

    public WhiteSurfaceView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.mInitialised = false;
        init();
    }

    @TargetApi(21)
    public WhiteSurfaceView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        this.mInitialised = false;
        init();
    }

    private void init() {
        this.mBackground = new ColorDrawable(-1);
    }

    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        if (this.mBackground != null) {
            this.mBackground.setBounds(left, top, right, bottom);
        }
    }

    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (!this.mInitialised) {
            this.mBackground.draw(canvas);
        }
    }

    public void setInitialised() {
        this.mInitialised = true;
        invalidate();
    }
}
