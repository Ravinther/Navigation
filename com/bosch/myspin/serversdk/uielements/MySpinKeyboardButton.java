package com.bosch.myspin.serversdk.uielements;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;

public class MySpinKeyboardButton extends Drawable {
    private Rect f340a;
    private Drawable f341b;
    private Paint f342c;
    private Drawable f343d;
    private Bitmap f344e;
    private float f345f;
    private String f346g;
    private int f347h;
    private int f348i;
    private Paint f349j;
    private boolean f350k;
    private boolean f351l;
    private boolean f352m;

    public MySpinKeyboardButton(Typeface typeface) {
        this.f340a = new Rect();
        this.f349j = new Paint();
        m348a(typeface);
    }

    private void m348a(Typeface typeface) {
        this.f349j.setColor(-1);
        this.f349j.setTextAlign(Align.CENTER);
        this.f349j.setFlags(1);
        if (typeface != null) {
            this.f349j.setTypeface(typeface);
        }
    }

    public void draw(Canvas canvas) {
        if (this.f341b != null) {
            if (!this.f352m || this.f343d == null) {
                this.f341b.draw(canvas);
            } else {
                if (!this.f350k) {
                    Rect rect = new Rect(this.f340a);
                    rect.top -= this.f340a.height();
                    this.f343d.setBounds(rect);
                }
                this.f343d.draw(canvas);
            }
        } else if (this.f342c != null) {
            canvas.drawRect(this.f340a, this.f342c);
        }
        if (this.f344e != null) {
            canvas.drawBitmap(this.f344e, (float) (this.f340a.centerX() - (this.f344e.getWidth() / 2)), (float) (this.f340a.centerY() - (this.f344e.getHeight() / 2)), null);
        }
        if (this.f346g != null && this.f344e == null) {
            if (this.f346g.contains("\n")) {
                this.f349j.setTextSize(this.f345f - 2.0f);
                canvas.drawText(this.f346g.split("\n")[0], (float) this.f340a.centerX(), (float) this.f340a.centerY(), this.f349j);
                canvas.drawText(this.f346g.split("\n")[1], (float) this.f340a.centerX(), (float) (this.f340a.bottom - 8), this.f349j);
                this.f349j.setTextSize(this.f345f + 2.0f);
            } else if (this.f350k) {
                canvas.drawText(this.f346g, (float) this.f340a.centerX(), ((float) this.f340a.centerY()) + (this.f345f / 4.0f), this.f349j);
            } else {
                if (this.f352m) {
                    canvas.drawText(this.f346g.substring(0, 1), (float) this.f340a.centerX(), (((float) this.f340a.centerY()) + (this.f345f / 4.0f)) - ((float) this.f340a.height()), this.f349j);
                }
                canvas.drawText(this.f346g.substring(0, 1), (float) this.f340a.centerX(), ((float) this.f340a.centerY()) + (this.f345f / 4.0f), this.f349j);
            }
        }
    }

    public void setAlpha(int i) {
    }

    public void setColorFilter(ColorFilter colorFilter) {
    }

    public int getOpacity() {
        return 0;
    }

    public boolean isPressed(int i, int i2) {
        return this.f340a.contains(i, i2);
    }

    public void setBackground(Drawable drawable) {
        this.f342c = null;
        this.f341b = drawable;
    }

    public void setIcon(Bitmap bitmap) {
        this.f344e = bitmap;
    }

    public void setBackgroundPressed(Drawable drawable) {
        this.f343d = drawable;
    }

    public void setTextSize(float f) {
        this.f345f = f;
        if (this.f349j != null) {
            this.f349j.setTextSize(f);
        }
    }

    public void setPosition(int i, int i2, int i3, int i4, boolean z) {
        if (z) {
            this.f340a.set(i, i2 - i3, i + i4, i2);
        } else {
            this.f340a.set(i, i3, i4, i2);
        }
        m347a();
    }

    public Rect getPosition() {
        return this.f340a;
    }

    private void m347a() {
        if (this.f340a != null) {
            Rect rect = new Rect(this.f340a.left, this.f340a.top + this.f347h, this.f340a.right - this.f348i, this.f340a.bottom - this.f347h);
            if (this.f341b != null) {
                this.f341b.setBounds(rect);
            }
            if (this.f343d != null) {
                this.f343d.setBounds(rect);
            }
        }
    }

    public void setText(String str) {
        this.f346g = str;
    }

    public void fitLabel() {
        if (this.f340a.width() > 0 && this.f346g != null && !this.f346g.startsWith("*")) {
            String str = "";
            for (String str2 : this.f346g.split("\n")) {
                if (this.f349j.measureText(str) <= this.f349j.measureText(str2)) {
                    str = str2;
                }
            }
            while (this.f349j.measureText(str) > ((float) (this.f340a.width() - this.f348i)) && this.f345f > 25.0f) {
                setTextSize(this.f345f - 1.0f);
            }
        }
    }

    public void setRightPadding(int i) {
        this.f348i = i;
        m347a();
    }

    public void setTopBottomPadding(int i) {
        this.f347h = i;
        m347a();
    }

    public String getText() {
        return this.f346g;
    }

    public void setSpecialKey(boolean z) {
        this.f350k = z;
    }

    public boolean isSpecialKey() {
        return this.f350k;
    }

    public void setFlyinButton(boolean z) {
        this.f351l = z;
    }

    public void setButtonPressed(boolean z) {
        this.f352m = z;
    }

    public boolean isFlyinButton() {
        return this.f351l;
    }
}
