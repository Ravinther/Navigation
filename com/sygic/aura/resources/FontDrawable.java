package com.sygic.aura.resources;

import android.content.res.Resources;
import android.content.res.Resources.Theme;
import android.content.res.TypedArray;
import android.content.res.XmlResourceParser;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint.Align;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.StateListDrawable;
import android.os.Build.VERSION;
import android.text.TextPaint;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.util.Xml;
import android.view.InflateException;
import com.sygic.aura.C1090R;
import java.io.IOException;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

public class FontDrawable extends Drawable {
    private float mOffsetX;
    private float mOffsetY;
    private final TextPaint mPaint;
    private final Rect mRect;
    private String mText;

    public static Drawable inflate(Resources resources, int xmlId) {
        return inflate(resources, xmlId, null);
    }

    public static Drawable inflate(Resources resources, int xmlId, String text) {
        XmlResourceParser parser = resources.getXml(xmlId);
        if (parser == null) {
            throw new InflateException();
        }
        Drawable drawable;
        int type;
        do {
            try {
                type = parser.next();
                if (type != 1) {
                    String name = parser.getName();
                } else {
                    throw new InflateException();
                }
            } catch (XmlPullParserException ex) {
                throw new InflateException(ex);
            } catch (IOException ex2) {
                throw new InflateException(ex2);
            }
        } while (type != 2);
        if ("selector".equals(name)) {
            drawable = new StateListDrawable();
        } else if ("font-icon".equals(name)) {
            drawable = new FontDrawable(text);
        } else {
            throw new InflateException(name);
        }
        drawable.inflate(resources, parser, Xml.asAttributeSet(parser));
        return drawable;
    }

    public void setBounds(Rect bounds) {
        super.setBounds(bounds);
        calculateOffset(this.mRect, bounds);
    }

    private void calculateOffset(Rect rect1, Rect rect2) {
        if (!isSameDimen(rect1, rect2)) {
            this.mOffsetX = (((float) rect1.width()) - ((float) rect2.width())) / 2.0f;
            this.mOffsetY = (((float) rect1.height()) - ((float) rect2.height())) / 2.0f;
        }
    }

    public FontDrawable() {
        this(null);
    }

    protected FontDrawable(String text) {
        this.mOffsetX = 0.0f;
        this.mOffsetY = 0.0f;
        if (text == null) {
            text = "";
        }
        this.mText = text;
        this.mPaint = new TextPaint();
        this.mRect = new Rect();
        this.mPaint.setAntiAlias(true);
        this.mPaint.setTextAlign(Align.LEFT);
    }

    public FontDrawable(FontDrawable fromDrawable, String newIconChar) {
        this.mOffsetX = 0.0f;
        this.mOffsetY = 0.0f;
        if (newIconChar == null) {
            newIconChar = "";
        }
        this.mText = newIconChar;
        this.mRect = new Rect();
        this.mPaint = fromDrawable.getPaint();
        this.mPaint.getTextBounds(this.mText, 0, this.mText.length(), this.mRect);
    }

    public void inflate(Resources r, XmlPullParser parser, AttributeSet attrs) throws XmlPullParserException, IOException {
        if (VERSION.SDK_INT < 21) {
            super.inflate(r, parser, attrs);
        }
        TypedArray a = r.obtainAttributes(attrs, C1090R.styleable.FontDrawable);
        if (a == null) {
            Log.w(FontDrawable.class.getPackage().getName(), "inflate failed: r.obtainAttributes() returns null");
            return;
        }
        int iFontId = -1;
        try {
            if (TextUtils.isEmpty(this.mText)) {
                this.mText = a.getString(2);
            }
            this.mPaint.setColor(a.getColor(1, -16777216));
            this.mPaint.setTextSize(a.getDimension(0, 9.0f));
            iFontId = a.getResourceId(3, -1);
            if (iFontId != -1) {
                this.mPaint.setTypeface(Typefaces.getFont(r, iFontId));
            }
            if (this.mText == null) {
                this.mText = "";
            }
            this.mPaint.getTextBounds(this.mText, 0, this.mText.length(), this.mRect);
            setBounds(this.mRect);
        } finally {
            a.recycle();
        }
    }

    public void setText(String text) {
        if (text == null) {
            text = "";
        }
        this.mText = text;
        this.mPaint.getTextBounds(this.mText, 0, this.mText.length(), this.mRect);
        calculateOffset(this.mRect, getBounds());
        super.setBounds(this.mRect);
        invalidateSelf();
    }

    public void setTextSize(float textSize) {
        this.mPaint.setTextSize(textSize);
    }

    public float getTextSize() {
        return this.mPaint.getTextSize();
    }

    public void inflate(Resources r, XmlPullParser parser, AttributeSet attrs, Theme theme) throws XmlPullParserException, IOException {
        super.inflate(r, parser, attrs, theme);
        inflate(r, parser, attrs);
    }

    public void setAlpha(int alpha) {
        if (this.mPaint.getAlpha() != alpha) {
            this.mPaint.setAlpha(alpha);
            invalidateSelf();
        }
    }

    public void setColorFilter(ColorFilter cf) {
        if (this.mPaint.getColorFilter() != cf) {
            this.mPaint.setColorFilter(cf);
            invalidateSelf();
        }
    }

    public void setColor(int color) {
        if (this.mPaint.getColor() != color) {
            this.mPaint.setColor(color);
            invalidateSelf();
        }
    }

    private TextPaint getPaint() {
        return this.mPaint;
    }

    public int getOpacity() {
        return -3;
    }

    public boolean getPadding(Rect padding) {
        return super.getPadding(padding);
    }

    public int getIntrinsicWidth() {
        return this.mRect.width();
    }

    public int getIntrinsicHeight() {
        return this.mRect.height();
    }

    public void draw(Canvas canvas) {
        canvas.drawText(this.mText, ((float) (-this.mRect.left)) - this.mOffsetX, ((float) (-this.mRect.top)) - this.mOffsetY, this.mPaint);
    }

    private boolean isSameDimen(Rect r1, Rect r2) {
        return ((float) r1.width()) - (this.mOffsetX * 2.0f) == ((float) r2.width()) && ((float) r1.height()) - (this.mOffsetY * 2.0f) == ((float) r2.height());
    }
}
