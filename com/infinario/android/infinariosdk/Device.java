package com.infinario.android.infinariosdk;

import android.os.Build;
import android.os.Build.VERSION;
import java.util.HashMap;
import java.util.Map;

public class Device {
    public static Map<String, Object> deviceProperties(Preferences preferences) {
        Map<String, Object> properties = new HashMap();
        properties.put("sdk", "AndroidSDK");
        properties.put("sdk_version", "1.1.4");
        properties.put("device_model", Build.MODEL);
        properties.put("device_type", preferences.getDeviceType());
        properties.put("os_version", VERSION.RELEASE);
        properties.put("os_name", "Android");
        return properties;
    }
}
