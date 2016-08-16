package com.sygic.aura.settings.preferences;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import com.sygic.aura.C1090R;
import com.sygic.aura.resources.FontDrawable;

public abstract class IconTextPreference extends CoreTitlePreference {
    private int mIconStringRes;
    private boolean mUseIconTextLayout;

    public IconTextPreference(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context, attrs);
    }

    public IconTextPreference(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public IconTextPreference(Context context) {
        super(context);
        init(context, null);
    }

    private void init(Context context, AttributeSet attrs) {
        if (attrs != null) {
            TypedArray a = context.obtainStyledAttributes(attrs, C1090R.styleable.IconTextPreference, 0, 0);
            this.mUseIconTextLayout = a.getBoolean(0, false);
            this.mIconStringRes = a.getResourceId(1, 2130838125);
            a.recycle();
        }
        if (this.mUseIconTextLayout) {
            Resources res = getContext().getResources();
            setIcon(FontDrawable.inflate(res, 2131034290, res.getString(this.mIconStringRes)));
        }
    }
}
