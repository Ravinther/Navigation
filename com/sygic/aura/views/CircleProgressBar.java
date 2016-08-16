package com.sygic.aura.views;

import android.animation.Animator.AnimatorListener;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.LinearInterpolator;
import com.sygic.aura.C1090R;

public class CircleProgressBar extends View {
    private Paint backgroundPaint;
    private int color;
    private Paint foregroundPaint;
    private ObjectAnimator mObjectAnimator;
    private int max;
    private int min;
    private float progress;
    private RectF rectF;
    private int startAngle;
    private float strokeWidth;

    public float getStrokeWidth() {
        return this.strokeWidth;
    }

    public void setStrokeWidth(float strokeWidth) {
        this.strokeWidth = strokeWidth;
        this.backgroundPaint.setStrokeWidth(strokeWidth);
        this.foregroundPaint.setStrokeWidth(strokeWidth);
        invalidate();
        requestLayout();
    }

    public float getProgress() {
        return this.progress;
    }

    public void setCircleProgress(float progress) {
        cancelRunningAnimation();
        setProgress(progress);
    }

    private void setProgress(float progress) {
        this.progress = progress;
        invalidate();
    }

    public int getMin() {
        return this.min;
    }

    public void setMin(int min) {
        this.min = min;
        invalidate();
    }

    public int getMax() {
        return this.max;
    }

    public void setMax(int max) {
        this.max = max;
        invalidate();
    }

    public int getColor() {
        return this.color;
    }

    public void setColor(int color) {
        this.color = color;
        this.backgroundPaint.setColor(adjustAlpha(color, 0.3f));
        this.foregroundPaint.setColor(color);
        invalidate();
        requestLayout();
    }

    public CircleProgressBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.strokeWidth = 4.0f;
        this.progress = 0.0f;
        this.min = 0;
        this.max = 100;
        this.startAngle = -90;
        this.color = -12303292;
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        this.rectF = new RectF();
        TypedArray typedArray = context.getTheme().obtainStyledAttributes(attrs, C1090R.styleable.CircleProgressBar, 0, 0);
        try {
            this.strokeWidth = typedArray.getDimension(4, this.strokeWidth);
            this.progress = typedArray.getFloat(2, this.progress);
            this.color = typedArray.getInt(3, this.color);
            this.min = typedArray.getInt(1, this.min);
            this.max = typedArray.getInt(0, this.max);
            this.backgroundPaint = new Paint(1);
            this.backgroundPaint.setColor(adjustAlpha(this.color, 0.1f));
            this.backgroundPaint.setStyle(Style.STROKE);
            this.backgroundPaint.setStrokeWidth(this.strokeWidth);
            this.foregroundPaint = new Paint(1);
            this.foregroundPaint.setColor(this.color);
            this.foregroundPaint.setStyle(Style.STROKE);
            this.foregroundPaint.setStrokeWidth(this.strokeWidth);
        } finally {
            typedArray.recycle();
        }
    }

    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawOval(this.rectF, this.backgroundPaint);
        Canvas canvas2 = canvas;
        canvas2.drawArc(this.rectF, (float) this.startAngle, (360.0f * this.progress) / ((float) this.max), false, this.foregroundPaint);
    }

    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int min = Math.min(getDefaultSize(getSuggestedMinimumWidth(), widthMeasureSpec), getDefaultSize(getSuggestedMinimumHeight(), heightMeasureSpec));
        setMeasuredDimension(min, min);
        this.rectF.set((this.strokeWidth / 2.0f) + 0.0f, (this.strokeWidth / 2.0f) + 0.0f, ((float) min) - (this.strokeWidth / 2.0f), ((float) min) - (this.strokeWidth / 2.0f));
    }

    public int adjustAlpha(int color, float factor) {
        return Color.argb(Math.round(((float) Color.alpha(color)) * factor), Color.red(color), Color.green(color), Color.blue(color));
    }

    public void setProgressWithAnimation(float progress) {
        setProgressWithAnimation(progress, 1500);
    }

    public void setProgressWithAnimation(float progress, int animationDuration) {
        setProgressWithAnimation(progress, animationDuration, null);
    }

    public void setProgressWithAnimation(float progress, int animationDuration, AnimatorListener listener) {
        this.mObjectAnimator = ObjectAnimator.ofFloat(this, "progress", new float[]{progress});
        this.mObjectAnimator.setDuration((long) animationDuration);
        this.mObjectAnimator.addListener(listener);
        this.mObjectAnimator.setInterpolator(new LinearInterpolator());
        this.mObjectAnimator.start();
    }

    public void cancelRunningAnimation() {
        if (this.mObjectAnimator != null) {
            this.mObjectAnimator.cancel();
            this.progress = 0.0f;
        }
    }
}
