package com.sygic.aura.views.font_specials;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.widget.Button;
import com.sygic.aura.C1090R;
import com.sygic.aura.resources.ResourceManager;
import com.sygic.aura.resources.Typefaces;
import loquendo.tts.engine.TTSConst;

public class SButton extends Button {
    public SButton(Context context) {
        this(context, null);
    }

    public SButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context, attrs);
    }

    public SButton(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initView(context, attrs);
    }

    private void initView(Context context, AttributeSet attrs) {
        TypedArray ta = context.obtainStyledAttributes(attrs, C1090R.styleable.SButton);
        if (ta != null) {
            int iTextId = ta.getResourceId(1, -1);
            int iFontId = ta.getResourceId(0, -1);
            int iTextStyle = ta.getInt(2, -1);
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
