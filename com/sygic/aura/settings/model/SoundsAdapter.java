package com.sygic.aura.settings.model;

import android.content.Context;
import com.sygic.aura.settings.data.SettingsManager;
import com.sygic.aura.settings.data.SoundEntry;

public class SoundsAdapter extends SettingsAdapter<SoundEntry> {
    public SoundsAdapter(Context context) {
        super(context);
    }

    public SoundEntry[] loadItems() {
        return SettingsManager.nativeGetSounds();
    }

    public CharSequence getItemName(SoundEntry item) {
        return item.getName();
    }
}
