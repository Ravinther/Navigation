package com.sygic.aura;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import java.util.HashMap;
import java.util.Map;

public class SygicPreferences {
    public static final String[] PREFS_REFERRALS_EXPECTED_PARAMETERS;

    static {
        PREFS_REFERRALS_EXPECTED_PARAMETERS = new String[]{"utm_source", "utm_medium", "utm_term", "utm_content", "utm_campaign"};
    }

    public static SharedPreferences getPreferences(Context context) {
        return context.getSharedPreferences("sygic_settings", 0);
    }

    public static String getSygicDirPath(Context context) {
        return getPreferences(context).getString("strSygicDir", null);
    }

    public static void setSygicDirPath(Context context, String strPath) {
        try {
            Editor editor = getPreferences(context).edit();
            editor.putString("strSygicDir", strPath);
            editor.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static boolean getFullscreen(Context context) {
        return getPreferences(context).getBoolean("bFullscreen", true);
    }

    public static void setFullscreen(Context context, boolean bFullscreen) {
        try {
            Editor editor = getPreferences(context).edit();
            editor.putBoolean("bFullscreen", bFullscreen);
            editor.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static float getGlCapabilities(Context context) {
        return getPreferences(context).getFloat("fGlCapabilities", 0.0f);
    }

    public static void setGlCapabilities(Context context, float fCapabilities) {
        try {
            Editor editor = context.getSharedPreferences("sygic_settings", 0).edit();
            editor.putFloat("fGlCapabilities", fCapabilities);
            editor.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static boolean getResetPushToken(Context context) {
        return getPreferences(context).getBoolean("bResetPushToken", false);
    }

    public static void setResetPushToken(Context context, boolean bReset) {
        try {
            Editor editor = context.getSharedPreferences("sygic_settings", 0).edit();
            editor.putBoolean("bResetPushToken", bReset);
            editor.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Map<String, String> retrieveReferralParams(Context context) {
        HashMap<String, String> params = new HashMap();
        for (String key : PREFS_REFERRALS_EXPECTED_PARAMETERS) {
            String value = getPreferences(context).getString(key, null);
            if (value != null) {
                params.put(key, value);
            }
        }
        return params;
    }

    public static boolean hasReferrer(Context context) {
        return getPreferences(context).getBoolean("hasReferrer", false);
    }

    public static void setLicensed(Context context, boolean bIsLicensed) {
        try {
            Editor editor = context.getSharedPreferences("sygic_settings", 0).edit();
            editor.putBoolean("isLicensed", bIsLicensed);
            editor.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static boolean isLicensed(Context context) {
        return getPreferences(context).getBoolean("isLicensed", false);
    }
}
