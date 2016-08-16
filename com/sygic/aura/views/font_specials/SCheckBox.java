package com.sygic.aura.views.font_specials;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.widget.CheckBox;
import com.sygic.aura.C1090R;
import com.sygic.aura.resources.ResourceManager;
import com.sygic.aura.resources.Typefaces;
import loquendo.tts.engine.TTSConst;

public class SCheckBox extends CheckBox {
    private int mTextStyle;

    public SCheckBox(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray ta = context.obtainStyledAttributes(attrs, C1090R.styleable.SCheckBox);
        if (ta != null) {
            int iTextId = ta.getResourceId(1, -1);
            int iFontId = ta.getResourceId(0, -1);
            int iThemeStyle = ta.getInt(3, 0);
            this.mTextStyle = ta.getInt(2, 0);
            ta.recycle();
            if (iTextId != -1) {
                setCoreText(iTextId);
            }
            if (iFontId != -1) {
                if (!isInEditMode()) {
                    setTypeface(Typefaces.getFont(getContext(), iFontId));
                }
            } else if (!isInEditMode()) {
                setTypeface(Typefaces.getFont(getContext(), 2131166098));
            }
            if (iThemeStyle > 0) {
                int resId = getResources().getIdentifier(iThemeStyle == 1 ? "btn_check_holo_light" : "btn_check_holo_dark", "drawable", "android");
                if (resId != 0) {
                    setButtonDrawable(resId);
                }
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
}
