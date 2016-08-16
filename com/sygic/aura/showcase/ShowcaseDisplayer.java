package com.sygic.aura.showcase;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;

class ShowcaseDisplayer {
    private Bitmap mBitmap;
    private int mInnerRadius;
    private Paint mPaint;
    private int mRadius;

    public ShowcaseDisplayer(Resources resources, int backgroundColor) {
        this.mRadius = resources.getDimensionPixelSize(2131231026);
        this.mInnerRadius = resources.getDimensionPixelSize(2131231025);
        this.mPaint = new Paint();
        this.mPaint.setAntiAlias(true);
        this.mPaint.setStyle(Style.FILL);
        this.mPaint.setColor(backgroundColor);
    }

    public void prepare() {
        prepareBitmap(this.mPaint.getColor());
    }

    private void prepareBitmap(int backgroundColor) {
        release();
        this.mBitmap = Bitmap.createBitmap(this.mRadius * 2, this.mRadius * 2, Config.ARGB_8888);
        Paint eraserPaint = new Paint();
        eraserPaint.setAntiAlias(true);
        eraserPaint.setColor(-1);
        Canvas bufferCanvas = new Canvas(this.mBitmap);
        bufferCanvas.drawColor(backgroundColor);
        eraserPaint.setXfermode(new PorterDuffXfermode(Mode.CLEAR));
        bufferCanvas.drawCircle((float) this.mRadius, (float) this.mRadius, (float) this.mInnerRadius, eraserPaint);
        eraserPaint.setXfermode(new PorterDuffXfermode(Mode.MULTIPLY));
        eraserPaint.setAlpha(153);
        bufferCanvas.drawCircle((float) this.mRadius, (float) this.mRadius, (float) this.mRadius, eraserPaint);
    }

    public void release() {
        if (this.mBitmap != null && !this.mBitmap.isRecycled()) {
            this.mBitmap.recycle();
            this.mBitmap = null;
        }
    }

    public void setScale(float scale) {
        this.mRadius = (int) (((float) this.mRadius) * scale);
        this.mInnerRadius = (int) (((float) this.mInnerRadius) * scale);
    }

    public int getShowcaseSize() {
        return this.mRadius * 2;
    }

    public void draw(Canvas canvas, int cx, int cy) {
        if (this.mBitmap == null || cx < 0 || cy < 0) {
            canvas.drawColor(this.mPaint.getColor());
            return;
        }
        int width = canvas.getWidth();
        int height = canvas.getHeight();
        canvas.drawBitmap(this.mBitmap, (float) (cx - this.mRadius), (float) (cy - this.mRadius), null);
        canvas.drawRect(0.0f, (float) (cy - this.mRadius), (float) (cx - this.mRadius), (float) height, this.mPaint);
        canvas.drawRect(0.0f, 0.0f, (float) width, (float) (cy - this.mRadius), this.mPaint);
        canvas.drawRect((float) (this.mRadius + cx), (float) (cy - this.mRadius), (float) width, (float) height, this.mPaint);
        canvas.drawRect((float) (cx - this.mRadius), (float) (this.mRadius + cy), (float) (this.mRadius + cx), (float) height, this.mPaint);
    }
}
