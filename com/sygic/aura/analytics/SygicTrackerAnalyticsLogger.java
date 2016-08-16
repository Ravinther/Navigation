package com.sygic.aura.analytics;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import com.sygic.aura.tracker.SygicTracker;
import com.sygic.aura.tracker.model.ClickEvent;
import com.sygic.aura.tracker.model.Event;
import com.sygic.aura.tracker.model.ScreenViewEvent;
import java.util.HashMap;

public class SygicTrackerAnalyticsLogger implements AnalyticsInterface {
    private static SygicTrackerAnalyticsLogger sInstance;
    private final Context mContext;

    public static SygicTrackerAnalyticsLogger getInstance(Context context) {
        if (sInstance == null) {
            sInstance = new SygicTrackerAnalyticsLogger(context);
        }
        return sInstance;
    }

    private SygicTrackerAnalyticsLogger(Context context) {
        this.mContext = context;
    }

    public void logEvent(Bundle params) {
        Event event;
        String screenName = params.getString("screenName");
        if (TextUtils.isEmpty(screenName)) {
            event = new ClickEvent(params.getString("category"), params.getString("eventName"), params.getString("strValue"));
        } else {
            event = new ScreenViewEvent(screenName);
        }
        if (params.containsKey("metrics")) {
            event.attachMetrics((HashMap) params.getSerializable("metrics"));
        }
        SygicTracker.get(this.mContext).sendEvent(event);
    }
}
