package com.sygic.aura.views.font_specials;

import android.content.Context;
import android.content.res.TypedArray;
import android.text.Layout.Alignment;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.View.MeasureSpec;
import android.widget.TextView;
import com.sygic.aura.C1090R;
import com.sygic.aura.helper.InCarConnection;
import com.sygic.aura.resources.ResourceManager;
import com.sygic.aura.resources.Typefaces;
import loquendo.tts.engine.TTSConst;

public class STextView extends TextView {
    protected boolean mDynamicResize;
    protected boolean mDynamicResizeHeight;
    private boolean mInCarScale;
    protected float mMaxTextSize;
    protected float mMinTextSize;
    protected TextPaint mTestPaint;
    protected int mTextId;
    protected int mTextStyle;

    public STextView(Context context) {
        super(context);
        this.mTextStyle = 0;
        this.mDynamicResize = false;
        this.mDynamicResizeHeight = false;
        this.mInCarScale = false;
        init(context, null, 0);
    }

    public STextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mTextStyle = 0;
        this.mDynamicResize = false;
        this.mDynamicResizeHeight = false;
        this.mInCarScale = false;
        init(context, attrs, 0);
    }

    public STextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mTextStyle = 0;
        this.mDynamicResize = false;
        this.mDynamicResizeHeight = false;
        this.mInCarScale = false;
        init(context, attrs, defStyleAttr);
    }

    private void init(Context context, AttributeSet attrs, int defStyleAttr) {
        boolean z = true;
        TypedArray ta = context.obtainStyledAttributes(attrs, C1090R.styleable.STextView, defStyleAttr, 0);
        if (ta != null) {
            boolean inCarConnected;
            boolean z2;
            int iHintId = ta.getResourceId(1, -1);
            int iFontId = ta.getResourceId(0, -1);
            this.mTextId = ta.getResourceId(3, -1);
            this.mTextStyle = ta.getInt(4, 0);
            if (isInEditMode() || !InCarConnection.isInCarConnected()) {
                inCarConnected = false;
            } else {
                inCarConnected = true;
            }
            if (inCarConnected || !ta.getBoolean(5, false)) {
                z2 = false;
            } else {
                z2 = true;
            }
            this.mDynamicResize = z2;
            this.mDynamicResizeHeight = ta.getBoolean(6, false);
            this.mMaxTextSize = (float) ta.getDimensionPixelSize(7, 80);
            this.mMinTextSize = (float) ta.getDimensionPixelSize(8, 5);
            if (!(inCarConnected && ta.getBoolean(2, false))) {
                z = false;
            }
            this.mInCarScale = z;
            ta.recycle();
            if (this.mTextId != -1) {
                setCoreText(this.mTextId);
            }
            if (iHintId != -1) {
                setHint(ResourceManager.getResourceOrCoreString(context, this, iHintId));
            }
            if (iFontId != -1) {
                if (!isInEditMode()) {
                    setTypeface(Typefaces.getFont(getContext(), iFontId));
                }
            } else if (!isInEditMode()) {
                setTypeface(Typefaces.getFont(getContext(), 2131166098));
            }
            if (this.mDynamicResize) {
                this.mTestPaint = new TextPaint(getPaint());
            }
            if (this.mInCarScale) {
                setTextSize(0, getTextSize() * InCarConnection.getPixelsRatio());
            }
        }
    }

    public void setCoreText(int textId) {
        setCoreText(ResourceManager.getResourceOrCoreString(getContext(), this, textId));
    }

    public void setCoreText(String string) {
        switch (this.mTextStyle) {
            case TTSConst.TTSMULTILINE /*1*/:
                setText(string.toLowerCase());
            case TTSConst.TTSPARAGRAPH /*2*/:
                setText(string.toUpperCase());
            default:
                setText(string);
        }
    }

    public void recreateCoreTextFromResId() {
        if (this.mTextId != -1) {
            setCoreText(this.mTextId);
        }
    }

    private void refitText(CharSequence text, int textWidth, int textHeight) {
        if (textWidth > 0 && textHeight > 0 && this.mDynamicResize) {
            int targetWidth = (textWidth - getPaddingLeft()) - getPaddingRight();
            int targetHeight = (textHeight - getPaddingBottom()) - getPaddingTop();
            float hi = this.mMaxTextSize;
            float lo = this.mMinTextSize;
            float size = getTextSize();
            while (hi - lo > 0.5f) {
                this.mTestPaint.setTextSize(size);
                if (this.mTestPaint.measureText(text, 0, text.length()) >= ((float) targetWidth) || (this.mDynamicResizeHeight && getTextHeight(text, this.mTestPaint, targetWidth, size) >= targetHeight)) {
                    hi = size;
                } else {
                    lo = size;
                }
                size = (hi + lo) / 2.0f;
            }
            setTextSize(0, lo);
        }
    }

    private int getTextHeight(CharSequence text, TextPaint paint, int width, float textSize) {
        return new StaticLayout(text, paint, width, Alignment.ALIGN_NORMAL, 1.0f, 0.0f, true).getHeight();
    }

    public int getTextStyle() {
        return this.mTextStyle;
    }

    public void setTextStyle(int textStyle) {
        this.mTextStyle = textStyle;
    }

    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        if (this.mDynamicResize) {
            refitText(getText(), MeasureSpec.getSize(widthMeasureSpec), MeasureSpec.getSize(heightMeasureSpec));
        }
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    protected void onTextChanged(CharSequence text, int start, int before, int after) {
        refitText(text, getWidth(), getHeight());
    }

    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        if (w != oldw || h != oldh) {
            refitText(getText(), w, h);
        }
    }
}
