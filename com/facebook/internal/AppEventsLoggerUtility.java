package com.facebook.internal;

import android.content.Context;
import com.facebook.LoggingBehavior;
import java.util.HashMap;
import java.util.Map;
import org.json.JSONException;
import org.json.JSONObject;

public class AppEventsLoggerUtility {
    private static final Map<GraphAPIActivityType, String> API_ACTIVITY_TYPE_TO_STRING;

    /* renamed from: com.facebook.internal.AppEventsLoggerUtility.1 */
    static class C03321 extends HashMap<GraphAPIActivityType, String> {
        C03321() {
            put(GraphAPIActivityType.MOBILE_INSTALL_EVENT, "MOBILE_APP_INSTALL");
            put(GraphAPIActivityType.CUSTOM_APP_EVENTS, "CUSTOM_APP_EVENTS");
        }
    }

    public enum GraphAPIActivityType {
        MOBILE_INSTALL_EVENT,
        CUSTOM_APP_EVENTS
    }

    static {
        API_ACTIVITY_TYPE_TO_STRING = new C03321();
    }

    public static JSONObject getJSONObjectForGraphAPICall(GraphAPIActivityType activityType, AttributionIdentifiers attributionIdentifiers, String anonymousAppDeviceGUID, boolean limitEventUsage, Context context) throws JSONException {
        JSONObject publishParams = new JSONObject();
        publishParams.put("event", API_ACTIVITY_TYPE_TO_STRING.get(activityType));
        Utility.setAppEventAttributionParameters(publishParams, attributionIdentifiers, anonymousAppDeviceGUID, limitEventUsage);
        try {
            Utility.setAppEventExtendedDeviceInfoParameters(publishParams, context);
        } catch (Exception e) {
            Logger.log(LoggingBehavior.APP_EVENTS, "AppEvents", "Fetching extended device info parameters failed: '%s'", e.toString());
        }
        publishParams.put("application_package_name", context.getPackageName());
        return publishParams;
    }
}
