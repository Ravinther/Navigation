package com.google.android.gms.appdatasearch;

import java.util.HashMap;
import java.util.Map;

public class zzh {
    private static final String[] zzPy;
    private static final Map<String, Integer> zzPz;

    static {
        int i = 0;
        zzPy = new String[]{"text1", "text2", "icon", "intent_action", "intent_data", "intent_data_id", "intent_extra_data", "suggest_large_icon", "intent_activity"};
        zzPz = new HashMap(zzPy.length);
        while (i < zzPy.length) {
            zzPz.put(zzPy[i], Integer.valueOf(i));
            i++;
        }
    }

    public static String zzaj(int i) {
        return (i < 0 || i >= zzPy.length) ? null : zzPy[i];
    }
}
