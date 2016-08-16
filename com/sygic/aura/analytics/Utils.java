package com.sygic.aura.analytics;

import android.os.Bundle;

public class Utils {
    public static String getBundledString(Bundle bundle, String key, String defaultValue) {
        String result = bundle.getString(key);
        return result == null ? defaultValue : result;
    }
}
