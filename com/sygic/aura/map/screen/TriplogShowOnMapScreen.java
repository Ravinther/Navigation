package com.sygic.aura.map.screen;

import android.view.View;
import com.sygic.aura.map.MapControlsManager;
import com.sygic.aura.views.font_specials.SToggleButton;

public class TriplogShowOnMapScreen extends OverlayScreen {
    protected void setupChildScreen(View rootView) {
        if (rootView != null) {
            SToggleButton button2d3Dtoggle = (SToggleButton) rootView.findViewById(2131624414);
            button2d3Dtoggle.setChecked(!MapControlsManager.nativeIsMapView2D());
            button2d3Dtoggle.setOnCheckedChangeListener(MapControlsManager.getOnClickListener2D3D(button2d3Dtoggle));
            setupRotationLock(rootView, null);
        }
    }

    protected void onScreenEntered() {
    }

    protected void onscreenLeft() {
    }
}
