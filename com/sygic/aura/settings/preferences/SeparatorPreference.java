package com.sygic.aura.settings.preferences;

import android.content.Context;
import android.preference.PreferenceCategory;
import android.util.AttributeSet;

public class SeparatorPreference extends PreferenceCategory {
    public SeparatorPreference(Context context, AttributeSet attrs) {
        super(context, attrs);
        setTitle(" ");
    }
}
