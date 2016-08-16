package com.infinario.android.infinariosdk;

import android.os.Build.VERSION;
import android.util.Log;

public class UserAgent {
    public static String create(Preferences preferences) {
        StringBuilder userAgent = new StringBuilder();
        String deviceType = preferences.getDeviceType();
        userAgent = new StringBuilder();
        userAgent.append("InfinarioAndroidSDK/").append("1.1.4").append(" (Android ").append(VERSION.RELEASE);
        if (!deviceType.isEmpty()) {
            try {
                userAgent.append("; ").append(deviceType.substring(0, 1).toUpperCase() + deviceType.substring(1));
            } catch (StringIndexOutOfBoundsException e) {
                Log.e("Infinario", "Unknow device type");
            }
        }
        userAgent.append(")");
        return userAgent.toString();
    }
}
