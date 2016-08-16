package com.sygic.aura.showcase;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.text.DynamicLayout;
import android.text.Layout.Alignment;
import android.text.SpannableString;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.style.TextAppearanceSpan;
import com.sygic.aura.resources.Typefaces;
import loquendo.tts.engine.TTSConst;

class TextDisplayer {
    private final float mActionBarOffset;
    private float[] mBestTextPosition;
    private final ShowcaseAreaCalculator mCalculator;
    private final Context mContext;
    private TextAppearanceSpan mDetailSpan;
    private CharSequence mDetails;
    private DynamicLayout mDynamicDetailLayout;
    private DynamicLayout mDynamicTitleLayout;
    private boolean mHasRecalculated;
    private final float mPadding;
    private final TextPaint mTextPaint;
    private CharSequence mTitle;
    private final TextPaint mTitlePaint;
    private TextAppearanceSpan mTitleSpan;

    public TextDisplayer(Resources resources, ShowcaseAreaCalculator calculator, Context context) {
        this.mBestTextPosition = new float[3];
        this.mPadding = resources.getDimension(2131231055);
        this.mActionBarOffset = resources.getDimension(2131230816);
        this.mCalculator = calculator;
        this.mContext = context;
        this.mTitlePaint = new TextPaint();
        this.mTitlePaint.setAntiAlias(true);
        this.mTitlePaint.setTypeface(Typefaces.getFont(resources, 2131166099));
        this.mTextPaint = new TextPaint();
        this.mTextPaint.setAntiAlias(true);
        this.mTextPaint.setTypeface(Typefaces.getFont(resources, 2131166098));
    }

    public void draw(Canvas canvas) {
        if (shouldDrawText()) {
            float[] textPosition = getBestTextPosition();
            if (!TextUtils.isEmpty(this.mTitle)) {
                canvas.save();
                if (this.mHasRecalculated) {
                    this.mDynamicTitleLayout = new DynamicLayout(this.mTitle, this.mTitlePaint, (int) textPosition[2], Alignment.ALIGN_NORMAL, 1.0f, 1.0f, true);
                }
                if (this.mDynamicTitleLayout != null) {
                    canvas.translate(textPosition[0], textPosition[1]);
                    this.mDynamicTitleLayout.draw(canvas);
                    canvas.restore();
                }
            }
            if (!TextUtils.isEmpty(this.mDetails)) {
                canvas.save();
                if (this.mHasRecalculated) {
                    this.mDynamicDetailLayout = new DynamicLayout(this.mDetails, this.mTextPaint, (int) textPosition[2], Alignment.ALIGN_NORMAL, 1.0f, 1.0f, true);
                }
                float detailOffset = this.mDynamicTitleLayout != null ? ((float) this.mDynamicTitleLayout.getHeight()) * 1.25f : 0.0f;
                if (this.mDynamicDetailLayout != null) {
                    canvas.translate(textPosition[0], textPosition[1] + detailOffset);
                    this.mDynamicDetailLayout.draw(canvas);
                    canvas.restore();
                }
            }
        }
        this.mHasRecalculated = false;
    }

    public void setContentText(CharSequence details) {
        if (details != null) {
            SpannableString ssbDetail = new SpannableString(details);
            ssbDetail.setSpan(this.mDetailSpan, 0, ssbDetail.length(), 0);
            this.mDetails = ssbDetail;
        }
    }

    public void setContentTitle(CharSequence title) {
        if (title != null) {
            SpannableString ssbTitle = new SpannableString(title);
            ssbTitle.setSpan(this.mTitleSpan, 0, ssbTitle.length(), 0);
            this.mTitle = ssbTitle;
        }
    }

    public void calculateTextPosition(int canvasW, int canvasH, ShowcaseView showcaseView) {
        Rect showcase;
        if (showcaseView.hasTarget()) {
            showcase = this.mCalculator.getShowcaseRect();
        } else {
            showcase = new Rect();
        }
        int[] areas = new int[]{showcase.left * canvasH, showcase.top * canvasW, (canvasW - showcase.right) * canvasH, (canvasH - showcase.bottom) * canvasW};
        int largest = 0;
        for (int i = 1; i < areas.length; i++) {
            if (areas[i] > areas[largest]) {
                largest = i;
            }
        }
        switch (largest) {
            case TTSConst.TTSEVT_AUDIOSTART /*0*/:
                this.mBestTextPosition[0] = this.mPadding;
                this.mBestTextPosition[1] = this.mPadding;
                this.mBestTextPosition[2] = ((float) showcase.left) - (this.mPadding * 2.0f);
                break;
            case TTSConst.TTSMULTILINE /*1*/:
                this.mBestTextPosition[0] = this.mPadding;
                this.mBestTextPosition[1] = this.mPadding + this.mActionBarOffset;
                this.mBestTextPosition[2] = ((float) canvasW) - (this.mPadding * 2.0f);
                break;
            case TTSConst.TTSPARAGRAPH /*2*/:
                this.mBestTextPosition[0] = ((float) showcase.right) + this.mPadding;
                this.mBestTextPosition[1] = this.mPadding;
                this.mBestTextPosition[2] = ((float) (canvasW - showcase.right)) - (this.mPadding * 2.0f);
                break;
            case TTSConst.TTSUNICODE /*3*/:
                this.mBestTextPosition[0] = this.mPadding;
                this.mBestTextPosition[1] = ((float) showcase.bottom) + this.mPadding;
                this.mBestTextPosition[2] = ((float) canvasW) - (this.mPadding * 2.0f);
                break;
        }
        switch (largest) {
            case TTSConst.TTSEVT_AUDIOSTART /*0*/:
            case TTSConst.TTSPARAGRAPH /*2*/:
                float[] fArr = this.mBestTextPosition;
                fArr[1] = fArr[1] + this.mActionBarOffset;
                break;
        }
        this.mHasRecalculated = true;
    }

    public void setTitleStyling(int styleId) {
        this.mTitleSpan = new TextAppearanceSpan(this.mContext, styleId);
        setContentTitle(this.mTitle);
    }

    public void setDetailStyling(int styleId) {
        this.mDetailSpan = new TextAppearanceSpan(this.mContext, styleId);
        setContentText(this.mDetails);
    }

    public float[] getBestTextPosition() {
        return this.mBestTextPosition;
    }

    public boolean shouldDrawText() {
        return (TextUtils.isEmpty(this.mTitle) && TextUtils.isEmpty(this.mDetails)) ? false : true;
    }
}
