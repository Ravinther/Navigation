package com.sygic.aura.settings.data;

import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.res.Resources;
import com.sygic.aura.settings.data.SettingsManager.ESettingsType;

public class AvoidRouteHack {
    private static final String KEY_WAS_FIXED = "avoid_route_settings_was_fixed";

    public static void fixIt(SharedPreferences prefs, Resources res) {
        boolean z = true;
        if (!prefs.contains(KEY_WAS_FIXED)) {
            boolean z2;
            Editor editor = prefs.edit();
            editor.putBoolean(KEY_WAS_FIXED, true);
            if (SettingsManager.nativeGetSettings(ESettingsType.eTollRoads) == 1) {
                z2 = true;
            } else {
                z2 = false;
            }
            Boolean bValue = Boolean.valueOf(z2);
            String string = res.getString(2131166735);
            if (bValue.booleanValue()) {
                z2 = false;
            } else {
                z2 = true;
            }
            editor.putBoolean(string, z2);
            if (SettingsManager.nativeGetSettings(ESettingsType.eUnpavedRoads) == 1) {
                z2 = true;
            } else {
                z2 = false;
            }
            bValue = Boolean.valueOf(z2);
            string = res.getString(2131166736);
            if (bValue.booleanValue()) {
                z2 = false;
            } else {
                z2 = true;
            }
            editor.putBoolean(string, z2);
            if (SettingsManager.nativeGetSettings(ESettingsType.eMotorway) == 1) {
                z2 = true;
            } else {
                z2 = false;
            }
            bValue = Boolean.valueOf(z2);
            string = res.getString(2131166734);
            if (bValue.booleanValue()) {
                z2 = false;
            } else {
                z2 = true;
            }
            editor.putBoolean(string, z2);
            if (SettingsManager.nativeGetSettings(ESettingsType.eFerries) == 1) {
                z2 = true;
            } else {
                z2 = false;
            }
            bValue = Boolean.valueOf(z2);
            String string2 = res.getString(2131166733);
            if (bValue.booleanValue()) {
                z = false;
            }
            editor.putBoolean(string2, z);
            editor.apply();
        }
    }
}
