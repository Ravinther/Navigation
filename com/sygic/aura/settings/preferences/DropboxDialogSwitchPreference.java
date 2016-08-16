package com.sygic.aura.settings.preferences;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.DialogInterface.OnClickListener;
import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.preference.PreferenceManager;
import android.util.AttributeSet;
import com.sygic.aura.settings.data.SettingsManager;

@TargetApi(14)
public class DropboxDialogSwitchPreference extends DialogSwitchPreference implements OnCancelListener, OnClickListener, OnSharedPreferenceChangeListener {
    private boolean mProcessing;

    public DropboxDialogSwitchPreference(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public DropboxDialogSwitchPreference(Context context, AttributeSet attrs) {
        super(context, attrs);
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this.mContext);
        if (preferences != null) {
            preferences.registerOnSharedPreferenceChangeListener(this);
        }
        setOnClickListener(this);
        setOnCancelListener(this);
    }

    public DropboxDialogSwitchPreference(Context context) {
        super(context);
    }

    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        if (!key.equals(this.mContext.getString(2131166264))) {
            return;
        }
        if (this.mProcessing) {
            this.mProcessing = false;
        } else if (!sharedPreferences.getBoolean(key, false)) {
            SettingsManager.nativeSwitchDropbox(false);
        } else if (SettingsManager.nativeHasDropboxSession()) {
            SettingsManager.nativeSwitchDropbox(true);
        } else {
            showDialog();
        }
    }

    public void onClick(DialogInterface dialog, int which) {
        if (which == -1) {
            SettingsManager.nativeSwitchDropbox(true);
        } else if (which == -2) {
            revertValue();
        }
    }

    public void onCancel(DialogInterface dialog) {
        revertValue();
    }

    private void revertValue() {
        boolean newValue = true;
        if (PreferenceManager.getDefaultSharedPreferences(this.mContext) != null) {
            this.mProcessing = true;
            if (isChecked()) {
                newValue = false;
            }
            setChecked(newValue);
        }
    }
}
