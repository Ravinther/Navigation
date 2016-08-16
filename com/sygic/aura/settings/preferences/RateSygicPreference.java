package com.sygic.aura.settings.preferences;

import android.content.Context;
import android.util.AttributeSet;
import com.sygic.aura.analytics.AnalyticsInterface.EventType;
import com.sygic.aura.analytics.SygicAnalyticsLogger;
import com.sygic.aura.settings.data.SettingsManager;

public class RateSygicPreference extends IconTextPreference {
    public RateSygicPreference(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public RateSygicPreference(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public RateSygicPreference(Context context) {
        super(context);
    }

    protected void onClick() {
        SygicAnalyticsLogger.getAnalyticsEvent(getContext(), EventType.SETTINGS_CATEGORY).setName("Settings category").setValue("Destination", getTitleCoreResource()).logAndRecycle();
        SettingsManager.rateApp(getContext());
    }
}
