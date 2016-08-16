package com.sygic.aura.settings.preferences;

import android.content.Context;
import android.content.DialogInterface;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;
import com.sygic.aura.settings.StyleablePreference;
import com.sygic.aura.settings.data.SettingsManager;

public class StyleableDialogPreference extends CoreDialogPreference implements StyleablePreference {
    public StyleableDialogPreference(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public StyleableDialogPreference(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void style(View view) {
        ((TextView) view.findViewById(16908310)).setTextColor(getContext().getResources().getColorStateList(2131558824));
    }

    public void onClick(DialogInterface dialog, int which) {
        String key = getKey();
        if (which == -1 && key.equals(getContext().getString(2131166759))) {
            SettingsManager.nativeUninstallSpeedCams();
        }
        super.onClick(dialog, which);
    }
}
