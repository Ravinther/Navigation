package com.sygic.aura.settings.preferences;

import android.content.Context;
import android.preference.PreferenceCategory;
import android.util.AttributeSet;
import com.sygic.aura.resources.ResourceManager;

public class SPreferenceCategory extends PreferenceCategory {
    public SPreferenceCategory(Context context, AttributeSet attrs) {
        super(context, attrs);
        setTitle(ResourceManager.getCoreString(getTitle().toString()).toUpperCase());
    }
}
