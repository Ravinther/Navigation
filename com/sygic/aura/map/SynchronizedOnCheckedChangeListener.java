package com.sygic.aura.map;

import com.sygic.aura.views.font_specials.SImageButton;
import com.sygic.aura.views.font_specials.SToggleButton;
import com.sygic.aura.views.font_specials.SToggleButton.OnCheckedChangeListener;
import java.util.HashSet;
import java.util.Set;

public class SynchronizedOnCheckedChangeListener implements OnCheckedChangeListener {
    private static SynchronizedOnCheckedChangeListener sInstance;
    private final Set<SToggleButton> mButtons;

    static {
        sInstance = null;
    }

    public static SynchronizedOnCheckedChangeListener getInstance(SToggleButton button) {
        if (sInstance == null) {
            sInstance = new SynchronizedOnCheckedChangeListener();
        }
        sInstance.mButtons.add(button);
        return sInstance;
    }

    private SynchronizedOnCheckedChangeListener() {
        this.mButtons = new HashSet();
    }

    public void onCheckedChanged(SImageButton buttonView, boolean isChecked) {
        if (isChecked) {
            MapControlsManager.nativeSetMapView3D();
        } else {
            MapControlsManager.nativeSetMapView2D();
        }
        for (SToggleButton button : this.mButtons) {
            if (!buttonView.equals(button)) {
                button.setChecked(isChecked);
            }
        }
    }
}
