package com.sygic.aura.analytics;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import com.facebook.appevents.AppEventsLogger;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

public class FacebookAnalyticsLogger implements AnalyticsInterface {
    private static FacebookAnalyticsLogger sInstance;
    private Context mContext;

    static {
        sInstance = null;
    }

    public static FacebookAnalyticsLogger getInstance(Context context) {
        if (sInstance == null) {
            sInstance = new FacebookAnalyticsLogger(context);
        }
        return sInstance;
    }

    public FacebookAnalyticsLogger(Context context) {
        this.mContext = context;
    }

    public void logEvent(Bundle params) {
        String fbParams = Utils.getBundledString(params, "fbParams", "");
        Map<String, String> mapParams = null;
        String[] arrParams = null;
        if (fbParams != null) {
            arrParams = fbParams.split(":");
        }
        if (arrParams != null && arrParams.length > 0) {
            mapParams = new HashMap();
            for (int i = 0; i < arrParams.length / 2; i++) {
                mapParams.put(arrParams[i * 2], arrParams[(i * 2) + 1]);
            }
        }
        if (mapParams != null) {
            if (!TextUtils.isEmpty(Utils.getBundledString(params, "strValue", ""))) {
                try {
                    sendFacebookEvent(Utils.getBundledString(params, "eventName", ""), Double.parseDouble(Utils.getBundledString(params, "strValue", "")), parseFacebookParams(mapParams));
                } catch (NumberFormatException e) {
                    Log.w("Facebook_log", "trying to log FB event with incorrect VALUE_TO_SUM parameter");
                }
            }
            sendFacebookEvent(Utils.getBundledString(params, "eventName", ""), parseFacebookParams(mapParams));
        }
    }

    private Bundle parseFacebookParams(Map<String, String> params) {
        Bundle bundle = new Bundle(params.size());
        for (Entry<String, String> pairs : params.entrySet()) {
            bundle.putString((String) pairs.getKey(), (String) pairs.getValue());
        }
        return bundle;
    }

    private void sendFacebookEvent(String strEvent, Bundle params) {
        String duplicityEvent = duplicateFacebookEvent(strEvent);
        if (duplicityEvent != null) {
            sendFacebookEvent(duplicityEvent, params);
        }
        AppEventsLogger.newLogger(this.mContext.getApplicationContext()).logEvent(strEvent, params);
    }

    private void sendFacebookEvent(String strEvent, double valueToSum, Bundle params) {
        String duplicityEvent = duplicateFacebookEvent(strEvent);
        if (duplicityEvent != null) {
            sendFacebookEvent(duplicityEvent, valueToSum, params);
        }
        AppEventsLogger.newLogger(this.mContext.getApplicationContext()).logEvent(strEvent, valueToSum, params);
    }

    private String duplicateFacebookEvent(String strEvent) {
        if ("fb_mobile_add_to_cart".equals(strEvent)) {
            return "product_priceClicked";
        }
        if ("fb_mobile_add_to_wishlist".equals(strEvent)) {
            return "product_show";
        }
        return null;
    }
}
