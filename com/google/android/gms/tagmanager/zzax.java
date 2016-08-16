package com.google.android.gms.tagmanager;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import java.util.HashMap;
import java.util.Map;

public class zzax {
    private static String zzaQo;
    static Map<String, String> zzaQp;

    static {
        zzaQp = new HashMap();
    }

    public static String zzF(String str, String str2) {
        return str2 == null ? str.length() > 0 ? str : null : Uri.parse("http://hostname/?" + str).getQueryParameter(str2);
    }

    public static String zzg(Context context, String str, String str2) {
        String str3 = (String) zzaQp.get(str);
        if (str3 == null) {
            SharedPreferences sharedPreferences = context.getSharedPreferences("gtm_click_referrers", 0);
            str3 = sharedPreferences != null ? sharedPreferences.getString(str, "") : "";
            zzaQp.put(str, str3);
        }
        return zzF(str3, str2);
    }

    public static String zzk(Context context, String str) {
        if (zzaQo == null) {
            synchronized (zzax.class) {
                if (zzaQo == null) {
                    SharedPreferences sharedPreferences = context.getSharedPreferences("gtm_install_referrer", 0);
                    if (sharedPreferences != null) {
                        zzaQo = sharedPreferences.getString("referrer", "");
                    } else {
                        zzaQo = "";
                    }
                }
            }
        }
        return zzF(zzaQo, str);
    }

    public static void zzl(Context context, String str) {
        String zzF = zzF(str, "conv");
        if (zzF != null && zzF.length() > 0) {
            zzaQp.put(zzF, str);
            zzcv.zzb(context, "gtm_click_referrers", zzF, str);
        }
    }
}
