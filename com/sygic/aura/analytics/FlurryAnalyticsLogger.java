package com.sygic.aura.analytics;

import android.content.Context;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import com.flurry.android.FlurryAgent;
import com.sygic.aura.ProjectsConsts;
import java.util.HashMap;
import java.util.Map;

public class FlurryAnalyticsLogger implements AnalyticsInterface {
    private static FlurryAnalyticsLogger sInstance;

    static {
        sInstance = null;
    }

    public static FlurryAnalyticsLogger getInstance(Context context) {
        if (sInstance == null) {
            sInstance = new FlurryAnalyticsLogger(context);
        }
        return sInstance;
    }

    public FlurryAnalyticsLogger(Context context) {
        String apiKey = ProjectsConsts.getString(15);
        if (apiKey == null) {
            try {
                apiKey = context.getPackageManager().getApplicationInfo(context.getPackageName(), 128).metaData.getString("flurry.ApiKey");
            } catch (NameNotFoundException e) {
                Log.e("FlurryAnalytics", "Failed to load meta-data, NameNotFound: " + e.getMessage());
            } catch (NullPointerException e2) {
                Log.e("FlurryAnalytics", "Failed to load meta-data, NullPointer: " + e2.getMessage());
            }
        }
        FlurryAgent.init(context, apiKey);
        FlurryAgent.onStartSession(context);
    }

    public void logEvent(Bundle params) {
        String eventName = params.getString("eventName");
        Map flurryParams = new HashMap();
        if (!TextUtils.isEmpty(eventName)) {
            HashMap<String, String> attributes = (HashMap) params.getSerializable("attributes");
            for (String key : attributes.keySet()) {
                flurryParams.put(key, attributes.get(key));
            }
            FlurryAgent.logEvent(eventName, flurryParams);
        }
    }

    public void logEvent(AnalyticsEvent event) {
        FlurryAgent.logEvent(event.getName(), event.getParams());
    }
}
