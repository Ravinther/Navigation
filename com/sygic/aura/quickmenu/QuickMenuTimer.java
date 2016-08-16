package com.sygic.aura.quickmenu;

import android.content.Context;
import com.sygic.aura.map.fragment.MapOverlayFragment;
import com.sygic.aura.map.fragment.MapOverlayFragment.Mode;
import com.sygic.aura.timers.AnimationTimer;

public class QuickMenuTimer extends AnimationTimer {
    private final Context mContext;
    private final boolean mbIsFreeDrive;

    public QuickMenuTimer(Context context, boolean isFreeDrive) {
        super(4000);
        this.mContext = context;
        this.mbIsFreeDrive = isFreeDrive;
    }

    protected void show() {
        if (this.mbIsFreeDrive) {
            MapOverlayFragment.setMode(this.mContext, Mode.FREEDRIVE_QUICK_MENU);
        } else {
            MapOverlayFragment.setMode(this.mContext, Mode.NAVIGATE_QUICK_MENU);
        }
    }

    protected void hide() {
        if (this.mbIsFreeDrive) {
            MapOverlayFragment.setMode(this.mContext, Mode.FREEDRIVE_INFO_BAR);
        } else {
            MapOverlayFragment.setMode(this.mContext, Mode.NAVIGATE_INFO_BAR);
        }
    }
}
