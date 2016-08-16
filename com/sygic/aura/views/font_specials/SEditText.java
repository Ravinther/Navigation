package com.sygic.aura.views.font_specials;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.widget.EditText;
import com.sygic.aura.C1090R;
import com.sygic.aura.resources.ResourceManager;
import com.sygic.aura.resources.Typefaces;
import loquendo.tts.engine.TTSConst;

public class SEditText extends EditText {
    public SEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray ta = context.obtainStyledAttributes(attrs, C1090R.styleable.SEditText);
        if (ta != null) {
            int iTextId = ta.getResourceId(2, -1);
            int iHintId = ta.getResourceId(1, -1);
            int iFontId = ta.getResourceId(0, -1);
            int iTextStyle = ta.getInt(3, -1);
            ta.recycle();
            if (iTextId != -1) {
                String strText = ResourceManager.getResourceOrCoreString(context, this, iTextId);
                switch (iTextStyle) {
                    case TTSConst.TTSMULTILINE /*1*/:
                        setText(strText.toLowerCase());
                        break;
                    case TTSConst.TTSPARAGRAPH /*2*/:
                        setText(strText.toUpperCase());
                        break;
                    default:
                        setText(strText);
                        break;
                }
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
        }
    }
}
