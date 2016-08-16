package com.sygic.aura.analytics;

import android.os.Bundle;
import com.sygic.aura.SygicMain;
import com.sygic.aura.feature.system.LowSystemFeature.EEventType;

public class CoreAnalyticsLogger implements AnalyticsInterface {
    private static CoreAnalyticsLogger sInstance;

    static {
        sInstance = null;
    }

    public static CoreAnalyticsLogger getInstance() {
        if (sInstance == null) {
            sInstance = new CoreAnalyticsLogger();
        }
        return sInstance;
    }

    public void logEvent(Bundle params) {
        SygicMain.getInstance().LogEvent(Utils.getBundledString(params, "eventName", ""), Utils.getBundledString(params, "coreParams", ""), ((EEventType) params.getSerializable("eventType")).getValue(), Utils.getBundledString(params, "strValue", ""), params.getBoolean("attachCoreParams", false));
    }
}
