package com.sygic.aura.settings.preferences;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import com.sygic.aura.map.MapControlsManager;

public class GpsLogStopPref extends CoreTitlePreference {
    public GpsLogStopPref(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public GpsLogStopPref(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public GpsLogStopPref(Context context) {
        super(context);
    }

    protected void onClick() {
        MapControlsManager.nativeEmulatorStop();
        ((Activity) getContext()).onBackPressed();
    }
}
