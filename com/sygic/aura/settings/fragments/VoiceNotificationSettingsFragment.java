package com.sygic.aura.settings.fragments;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.TwoStatePreference;
import com.sygic.aura.settings.data.SettingsManager;
import com.sygic.aura.settings.data.VoiceEntry;
import com.sygic.aura.settings.preferences.road_numbers.RoadNumbersSwitchPreference;

public class VoiceNotificationSettingsFragment extends SettingsFragment {
    private TwoStatePreference mRoadNumberPrefrence;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.mRoadNumberPrefrence = (TwoStatePreference) findPreferenceByKey(2131166770);
        if (this.mRoadNumberPrefrence != null) {
            this.mRoadNumberPrefrence.setDependency(getString(2131166772));
        }
    }

    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        boolean z = true;
        if (key.equals(getString(2131166730))) {
            boolean isTTS = sharedPreferences.getBoolean(key, true);
            if (this.mRoadNumberPrefrence != null && (this.mRoadNumberPrefrence instanceof RoadNumbersSwitchPreference)) {
                ((RoadNumbersSwitchPreference) this.mRoadNumberPrefrence).langChanged(isTTS);
                return;
            }
            return;
        }
        if (key.equals(getString(2131166772))) {
            VoiceEntry voice = SettingsManager.nativeGetSelectedVoice();
            boolean voiceInstructions = sharedPreferences.getBoolean(key, true);
            TwoStatePreference twoStatePreference = this.mRoadNumberPrefrence;
            if (!(voiceInstructions && voice.isTTS())) {
                z = false;
            }
            twoStatePreference.setChecked(z);
        }
        super.onSharedPreferenceChanged(sharedPreferences, key);
    }
}
