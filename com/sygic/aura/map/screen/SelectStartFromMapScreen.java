package com.sygic.aura.map.screen;

import android.view.View;
import com.sygic.aura.map.MapControlsManager;
import com.sygic.aura.map.MapControlsManager.EMapView;
import com.sygic.aura.map.fragment.MapOverlayFragment.Mode;
import com.sygic.aura.resources.ResourceManager;
import com.sygic.aura.views.font_specials.SImageButton;
import com.sygic.aura.views.font_specials.SToggleButton;
import com.sygic.aura.views.font_specials.SToggleButton.OnCheckedChangeListener;

public class SelectStartFromMapScreen extends OverlayScreen {
    private SToggleButton mBtn2D3DBrowse;
    private SToggleButton mBtnTraffic;
    protected OnCheckedChangeListener mTrafficOnCheckedChangeListener;

    /* renamed from: com.sygic.aura.map.screen.SelectStartFromMapScreen.1 */
    class C13601 implements OnCheckedChangeListener {
        public boolean bIsMapView3D;

        C13601() {
        }

        public void onCheckedChanged(SImageButton buttonView, boolean isChecked) {
            if (isChecked) {
                this.bIsMapView3D = SelectStartFromMapScreen.this.mBtn2D3DBrowse.isChecked();
            }
            OverlayScreen.performTrafficChange(SelectStartFromMapScreen.this.mBtn2D3DBrowse, isChecked, this.bIsMapView3D);
        }
    }

    public SelectStartFromMapScreen() {
        this.mTrafficOnCheckedChangeListener = new C13601();
    }

    protected void setupChildScreen(View rootView) {
        if (rootView != null) {
            this.mBtn2D3DBrowse = (SToggleButton) rootView.findViewById(2131624414);
            this.mBtn2D3DBrowse.setChecked(!MapControlsManager.nativeIsMapView2D());
            this.mBtn2D3DBrowse.setOnCheckedChangeListener(MapControlsManager.getOnClickListener2D3D(this.mBtn2D3DBrowse));
            this.mBtnTraffic = (SToggleButton) rootView.findViewById(2131624413);
            this.mBtnTraffic.setChecked(false);
            this.mBtnTraffic.setOnCheckedChangeListener(this.mTrafficOnCheckedChangeListener);
            this.mFragment.getAnimator().registerViewForTranslateAnimationByY((View) this.mBtn2D3DBrowse.getParent(), Mode.SELECT_START_FROM_MAP);
        }
    }

    protected void onScreenEntered() {
        MapControlsManager.nativeSetMapViewMode(EMapView.MVDefault);
    }

    protected void onscreenLeft() {
    }

    public static void onTrafficReceived() {
        SelectStartFromMapScreen screen = (SelectStartFromMapScreen) OverlayScreen.getInstance(Mode.SELECT_START_FROM_MAP);
        if (screen != null && screen.mBtnTraffic != null) {
            ResourceManager.makeControlVisible(screen.mBtnTraffic, true);
        }
    }
}
