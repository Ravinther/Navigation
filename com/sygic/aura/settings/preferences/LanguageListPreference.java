package com.sygic.aura.settings.preferences;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import com.sygic.aura.C1090R;

public class LanguageListPreference extends FragmentPreference {
    private String mKeySecondary;

    public LanguageListPreference(Context context) {
        super(context);
    }

    public LanguageListPreference(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray a = context.obtainStyledAttributes(attrs, C1090R.styleable.LanguageListPreference, 0, 0);
        this.mKeySecondary = a.getString(0);
        a.recycle();
    }

    public void setSummary(CharSequence summary) {
        super.setSummary(getSharedPreferences().getString(this.mKeySecondary, null));
    }
}
