package com.bosch.myspin.serversdk.utils;

import android.annotation.SuppressLint;
import java.util.Properties;

@SuppressLint({"DefaultLocale"})
/* renamed from: com.bosch.myspin.serversdk.utils.a */
public class C0230a extends Properties {
    public Object put(Object obj, Object obj2) {
        return super.put(((String) obj).toLowerCase(), obj2);
    }

    public String getProperty(String str) {
        return super.getProperty(str.toLowerCase());
    }

    public String getProperty(String str, String str2) {
        return super.getProperty(str.toLowerCase(), str2);
    }
}
