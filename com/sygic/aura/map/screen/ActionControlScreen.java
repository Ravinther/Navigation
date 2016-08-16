package com.sygic.aura.map.screen;

import android.view.View;

public class ActionControlScreen extends OverlayScreen {
    protected void setupChildScreen(View rootView) {
    }

    protected void onScreenEntered() {
        getToolbar().hide();
    }

    protected void onscreenLeft() {
        getToolbar().show();
    }
}
