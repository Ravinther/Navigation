package com.infinario.android.infinariosdk;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager.NameNotFoundException;
import android.util.Log;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import org.json.JSONException;
import org.json.JSONObject;

public class Preferences {
    private static Preferences instance;
    private static Object lockInstance;
    private Context context;
    private Object lockAccess;

    static {
        instance = null;
        lockInstance = new Object();
    }

    private Preferences(Context context) {
        this.context = context;
        this.lockAccess = new Object();
    }

    public static Preferences get(Context context) {
        if (instance == null) {
            synchronized (lockInstance) {
                if (instance == null) {
                    instance = new Preferences(context);
                }
            }
        }
        return instance;
    }

    private SharedPreferences getPreferences(Context context) {
        SharedPreferences sharedPreferences;
        synchronized (this.lockAccess) {
            sharedPreferences = context.getSharedPreferences("infinario", 0);
        }
        return sharedPreferences;
    }

    public String getSenderId() {
        return getPreferences(this.context).getString("sender_id", null);
    }

    public void setReferrer(String referrer) {
        getPreferences(this.context).edit().putString("referrer", referrer).commit();
    }

    public int getIcon() {
        return getPreferences(this.context).getInt("icon", -1);
    }

    public void setToken(String token) {
        getPreferences(this.context).edit().putString("token", token).commit();
    }

    public void setTarget(String target) {
        getPreferences(this.context).edit().putString("target", target).commit();
    }

    public boolean getAutomaticFlushing() {
        return getPreferences(this.context).getBoolean("auto_flush", true);
    }

    public long getSessionStart() {
        return getPreferences(this.context).getLong("session_start", -1);
    }

    public void setSessionStart(long value) {
        getPreferences(this.context).edit().putLong("session_start", value).commit();
    }

    public long getSessionEnd() {
        return getPreferences(this.context).getLong("session_end", -1);
    }

    public Map<String, Object> getSessionEndProperties() {
        return jsonToMap(getPreferences(this.context).getString("session_end_properties", ""));
    }

    public void setSessionEnd(long value, Map<String, Object> properties) {
        getPreferences(this.context).edit().putLong("session_end", value).commit();
        if (properties != null) {
            getPreferences(this.context).edit().putString("session_end_properties", new JSONObject(properties).toString()).commit();
        } else {
            getPreferences(this.context).edit().putString("session_end_properties", "").commit();
        }
    }

    public boolean getGooglePushNotifications() {
        return getPreferences(this.context).getBoolean("google_push_notifications", false);
    }

    public String getCookieId() {
        return getPreferences(this.context).getString("cookie", "");
    }

    public void setCookieId(String value) {
        getPreferences(this.context).edit().putString("cookie", value).commit();
    }

    public String getRegistredId() {
        return getPreferences(this.context).getString("registered", "");
    }

    public void setRegistredId(String value) {
        getPreferences(this.context).edit().putString("registered", value).commit();
    }

    public String getGoogleAdvertisingId() {
        return getPreferences(this.context).getString("google_advertising_id", "");
    }

    public void setGoogleAdvertisingId(String value) {
        getPreferences(this.context).edit().putString("google_advertising_id", value).commit();
    }

    public String getDeviceType() {
        return getPreferences(this.context).getString("device_type", "");
    }

    public void setDeviceType(String value) {
        getPreferences(this.context).edit().putString("device_type", value).commit();
    }

    public String getAppVersionName() {
        try {
            return this.context.getPackageManager().getPackageInfo(this.context.getPackageName(), 128).versionName;
        } catch (NameNotFoundException e) {
            return null;
        }
    }

    private Map<String, Object> jsonToMap(String json) {
        try {
            if (json.isEmpty()) {
                return null;
            }
            JSONObject jsonObj = new JSONObject(json);
            Iterator it = jsonObj.keys();
            Map<String, Object> map = new HashMap();
            while (it.hasNext()) {
                String key = it.next().toString();
                map.put(key, jsonObj.get(key));
            }
            return map;
        } catch (JSONException e) {
            Log.e("Infinario", e.getMessage().toString());
            return null;
        } catch (NullPointerException e2) {
            Log.e("Infinario", e2.getMessage().toString());
            return null;
        }
    }
}
