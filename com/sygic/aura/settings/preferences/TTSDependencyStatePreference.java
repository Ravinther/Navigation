package com.sygic.aura.settings.preferences;

import android.content.Context;
import android.util.AttributeSet;
import com.sygic.aura.settings.data.SettingsManager;

public class TTSDependencyStatePreference extends DependencyStatePreference {
    public TTSDependencyStatePreference(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    private void init() {
        setPersistent(false);
        setDefaultValue(Boolean.valueOf(SettingsManager.nativeIsTTSVoiceSelected()));
    }

    protected void onSetInitialValue(boolean restoreValue, Object defaultValue) {
        boolean z = defaultValue == null && restoreValue;
        super.onSetInitialValue(z, defaultValue);
    }
}
