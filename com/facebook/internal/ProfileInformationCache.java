package com.facebook.internal;

import java.util.concurrent.ConcurrentHashMap;
import org.json.JSONObject;

class ProfileInformationCache {
    private static final ConcurrentHashMap<String, JSONObject> infoCache;

    static {
        infoCache = new ConcurrentHashMap();
    }

    public static JSONObject getProfileInformation(String accessToken) {
        return (JSONObject) infoCache.get(accessToken);
    }

    public static void putProfileInformation(String key, JSONObject value) {
        infoCache.put(key, value);
    }
}
