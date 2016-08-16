package com.sygic.aura.settings.fragments;

import android.content.SharedPreferences;

public class RegionalSettingsFragment extends SettingsFragment {
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        if (key.equals(getString(2131166722))) {
            languageChanged(sharedPreferences, key);
        }
    }
}
