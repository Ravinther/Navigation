package com.sygic.aura.settings.data;

import android.content.SharedPreferences;
import android.content.res.Resources;
import com.sygic.aura.settings.data.SettingsManager.EInfoShowType;
import com.sygic.aura.settings.data.SettingsManager.ESettingsType;
import com.sygic.aura.settings.fragments.SettingsFragment;
import com.sygic.aura.utils.Pair;
import java.util.ArrayList;
import java.util.Iterator;

public class InfobarSlotsHack {
    private static final String KEY_WAS_FIXED = "key_was_fixed";

    public static void fixInfobarSettings(SharedPreferences prefs, Resources res) {
        if (!prefs.getBoolean(KEY_WAS_FIXED, false)) {
            ArrayList<Pair<String, ESettingsType>> settings = new ArrayList();
            settings.add(new Pair(res.getString(2131166290), ESettingsType.eCarSlot1));
            settings.add(new Pair(res.getString(2131166291), ESettingsType.eCarSlot2));
            settings.add(new Pair(res.getString(2131166292), ESettingsType.eCarSlot3));
            settings.add(new Pair(res.getString(2131166293), ESettingsType.ePedSlot1));
            settings.add(new Pair(res.getString(2131166294), ESettingsType.ePedSlot2));
            settings.add(new Pair(res.getString(2131166295), ESettingsType.ePedSlot3));
            Iterator it = settings.iterator();
            while (it.hasNext()) {
                Pair<String, ESettingsType> pair = (Pair) it.next();
                repairSetting(prefs, (String) pair.getFirst(), (ESettingsType) pair.getSecond());
            }
            prefs.edit().putBoolean(KEY_WAS_FIXED, true).apply();
        }
    }

    private static void repairSetting(SharedPreferences prefs, String string, ESettingsType eCarSlot) {
        int value = SettingsFragment.strSettingsToInt(prefs, string);
        if (value != -1) {
            SettingsManager.nativeSetSettings(eCarSlot, EInfoShowType.infobarToCore(value));
        }
    }
}
