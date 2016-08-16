package com.sygic.aura.map.view;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import com.sygic.aura.resources.FontDrawable;
import com.sygic.aura.views.font_specials.SImageButton;
import loquendo.tts.engine.TTSConst;

public class ThreeStateButton extends SImageButton {
    private final Drawable mDrawableLocked;
    private final Drawable mDrawableLockedCompass;
    private final Drawable mDrawableUnlocked;
    private int mState;

    public ThreeStateButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mState = 0;
        if (isInEditMode()) {
            this.mDrawableUnlocked = getResources().getDrawable(17301533);
            this.mDrawableLocked = getResources().getDrawable(17301533);
            this.mDrawableLockedCompass = getResources().getDrawable(17301533);
        } else {
            this.mDrawableUnlocked = FontDrawable.inflate(getResources(), 2131034317);
            this.mDrawableLocked = FontDrawable.inflate(getResources(), 2131034315);
            this.mDrawableLockedCompass = FontDrawable.inflate(getResources(), 2131034316);
        }
        setButtonDrawable();
    }

    public boolean performClick() {
        nextState();
        return super.performClick();
    }

    public void setState(int state) {
        if (state != this.mState) {
            this.mState = state;
            setButtonDrawable();
        }
    }

    public int getState() {
        return this.mState;
    }

    public void nextState() {
        if (this.mState + 1 > 2) {
            setState(0);
        } else {
            setState(this.mState + 1);
        }
    }

    private void setButtonDrawable() {
        switch (this.mState) {
            case TTSConst.TTSEVT_AUDIOSTART /*0*/:
                setImageDrawable(this.mDrawableUnlocked);
            case TTSConst.TTSMULTILINE /*1*/:
                setImageDrawable(this.mDrawableLockedCompass);
            case TTSConst.TTSPARAGRAPH /*2*/:
                setImageDrawable(this.mDrawableLocked);
            default:
        }
    }
}
