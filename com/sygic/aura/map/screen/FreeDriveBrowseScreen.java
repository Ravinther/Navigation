package com.sygic.aura.map.screen;

import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.RelativeLayout.LayoutParams;
import com.sygic.aura.map.MapControlsManager;
import com.sygic.aura.map.MapControlsManager.EMapView;
import com.sygic.aura.map.fragment.MapOverlayFragment;
import com.sygic.aura.map.fragment.MapOverlayFragment.Mode;
import com.sygic.aura.map.view.CompassView;
import com.sygic.aura.map.view.MapOverlayAnimator;
import com.sygic.aura.map.view.ThreeStateButton;
import com.sygic.aura.resources.ResourceManager;
import com.sygic.aura.views.font_specials.SImageButton;
import com.sygic.aura.views.font_specials.STextView;
import com.sygic.aura.views.font_specials.SToggleButton;
import com.sygic.aura.views.font_specials.SToggleButton.OnCheckedChangeListener;

public class FreeDriveBrowseScreen extends AutoCloseableOverlayScreen {
    private SToggleButton mBtn2D3DBrowse;
    private SToggleButton mBtnTraffic;
    private ThreeStateButton mBtnVehicleLock;
    private CompassView mCompassView;
    private STextView mCountdownTextView;
    private ViewGroup mLockContainer;
    protected OnCheckedChangeListener mTrafficOnCheckedChangeListener;

    /* renamed from: com.sygic.aura.map.screen.FreeDriveBrowseScreen.1 */
    class C13371 implements OnCheckedChangeListener {
        public boolean bIsMapView3D;

        C13371() {
        }

        public void onCheckedChanged(SImageButton buttonView, boolean isChecked) {
            if (isChecked) {
                this.bIsMapView3D = FreeDriveBrowseScreen.this.mBtn2D3DBrowse.isChecked();
            }
            OverlayScreen.performTrafficChange(FreeDriveBrowseScreen.this.mBtn2D3DBrowse, isChecked, this.bIsMapView3D);
        }
    }

    /* renamed from: com.sygic.aura.map.screen.FreeDriveBrowseScreen.2 */
    class C13382 implements OnClickListener {
        C13382() {
        }

        public void onClick(View v) {
            FreeDriveBrowseScreen.this.mBtnVehicleLock.performClick();
        }
    }

    public FreeDriveBrowseScreen() {
        this.mTrafficOnCheckedChangeListener = new C13371();
    }

    protected void setupChildScreen(View rootView) {
        if (rootView != null) {
            this.mBtn2D3DBrowse = (SToggleButton) rootView.findViewById(2131624414);
            this.mBtn2D3DBrowse.setChecked(!MapControlsManager.nativeIsMapView2D());
            this.mBtn2D3DBrowse.setOnCheckedChangeListener(MapControlsManager.getOnClickListener2D3D(this.mBtn2D3DBrowse));
            this.mCompassView = (CompassView) rootView.findViewById(2131624387);
            this.mLockContainer = (ViewGroup) rootView.findViewById(2131624410);
            this.mLockContainer.setOnClickListener(new C13382());
            this.mCountdownTextView = (STextView) this.mLockContainer.findViewById(2131624411);
            this.mBtnVehicleLock = (ThreeStateButton) this.mLockContainer.findViewById(2131624412);
            this.mBtnVehicleLock.setOnClickListener(MapControlsManager.getOnClickListenerVehicleLock(this.mBtnVehicleLock));
            this.mBtnTraffic = (SToggleButton) rootView.findViewById(2131624413);
            this.mBtnTraffic.setChecked(false);
            this.mBtnTraffic.setOnCheckedChangeListener(this.mTrafficOnCheckedChangeListener);
            MapOverlayAnimator animator = this.mFragment.getAnimator();
            View[] views = new View[2];
            setupZoomControls(rootView, views);
            animator.registerViewsForAnimation(views, Mode.FREEDRIVE_BROWSE, "alpha", 0.0f, 1.0f);
            setupRotationLock(rootView, views);
            animator.registerViewForAnimation(views[0], Mode.FREEDRIVE_BROWSE, "alpha", 0.0f, 1.0f);
            animator.registerViewForTranslateAnimationByY((View) this.mBtn2D3DBrowse.getParent(), Mode.FREEDRIVE_BROWSE);
        }
    }

    protected boolean isZoomControlVisibilityFromSettings() {
        return true;
    }

    protected void onScreenEntered() {
        super.onScreenEntered();
        MapControlsManager.nativeSetMapViewMode(EMapView.MVDefault);
    }

    protected void onscreenLeft() {
        super.onscreenLeft();
    }

    public static void onLockNavi() {
        FreeDriveBrowseScreen screen = (FreeDriveBrowseScreen) OverlayScreen.getInstance(Mode.FREEDRIVE_BROWSE);
        if (screen != null && screen.mBtnVehicleLock != null && screen.mBtnTraffic != null) {
            screen.mBtnVehicleLock.setState(1);
            screen.mBtnTraffic.setChecked(false);
        }
    }

    public static void onUnlockNavi() {
        FreeDriveBrowseScreen screen = (FreeDriveBrowseScreen) OverlayScreen.getInstance(Mode.FREEDRIVE_BROWSE);
        if (screen != null && screen.mBtnVehicleLock != null) {
            screen.mBtnVehicleLock.setState(0);
        }
    }

    public static void onTrafficReceived() {
        FreeDriveBrowseScreen screen = (FreeDriveBrowseScreen) OverlayScreen.getInstance(Mode.FREEDRIVE_BROWSE);
        if (screen != null && screen.mBtnTraffic != null) {
            ResourceManager.makeControlVisible(screen.mBtnTraffic, true);
        }
    }

    public static void onStartComputingProgress() {
        FreeDriveBrowseScreen screen = (FreeDriveBrowseScreen) OverlayScreen.getInstance(Mode.FREEDRIVE_BROWSE);
        if (screen != null && screen.mBtnTraffic != null) {
            screen.mBtnTraffic.setChecked(false);
        }
    }

    protected ViewGroup getCountdownContainer() {
        return this.mLockContainer;
    }

    public STextView getCountdownTextView() {
        return this.mCountdownTextView;
    }

    protected boolean isMovementRequired() {
        return true;
    }

    protected void onAutoClosed() {
        MapOverlayFragment.setMode(this.mFragment.getActivity(), Mode.FREEDRIVE_INFO_BAR);
        MapControlsManager.nativeLockVehicleCompass();
    }

    public static void onConfigurationChanged(Context context, int orientation) {
        FreeDriveBrowseScreen screen = (FreeDriveBrowseScreen) OverlayScreen.getInstance(Mode.FREEDRIVE_BROWSE);
        if (screen != null) {
            ((LayoutParams) screen.mCompassView.getLayoutParams()).setMargins(0, context.getResources().getDimensionPixelSize(2131230729), 0, 0);
        }
    }
}
