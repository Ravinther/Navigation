package com.sygic.aura.navigate;

import android.view.View;
import android.view.ViewGroup;
import com.sygic.aura.map.screen.NavigationInfoBarScreen;
import com.sygic.aura.map.view.ModernViewSwitcher;
import com.sygic.aura.route.RouteManager;
import com.sygic.aura.route.RouteManager.RouteComputeMode;

public class SwitchToPedestrianControlHolder extends ActionControlHolder {
    public SwitchToPedestrianControlHolder(ModernViewSwitcher switcher, ViewGroup view) {
        super(switcher, view);
    }

    protected int getTitleForBaseLayout() {
        return 2131165332;
    }

    protected int getIconForBaseLayout() {
        return 2131034124;
    }

    protected void onClickPositive(View v) {
        super.onClickPositive(v);
        RouteManager.nativeRouteRecompute(RouteComputeMode.MODE_PEDESTRIAN);
        NavigationInfoBarScreen.setupSlots();
        hideInternal();
    }
}
