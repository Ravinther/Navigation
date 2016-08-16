package com.sygic.aura.map.screen;

import android.view.View;
import com.sygic.aura.helper.SygicHelper;
import com.sygic.aura.helper.interfaces.DemoListener;
import com.sygic.aura.map.MapControlsManager;
import com.sygic.aura.map.fragment.MapOverlayFragment.Mode;
import com.sygic.aura.map.notification.NotificationCenterView;
import com.sygic.aura.map.screen.intefaces.SoundMutedListener;
import com.sygic.aura.map.view.MapOverlayAnimator;
import com.sygic.aura.quickmenu.QuickMenuTimer;
import com.sygic.aura.quickmenu.QuickMenuView;
import com.sygic.aura.route.DemoManager;
import com.sygic.aura.route.RouteSummary;
import com.sygic.aura.views.font_specials.SToggleButton;

public class NavigationQuickMenuScreen extends OverlayScreen implements SoundMutedListener {
    private NotificationCenterView mNotificationCenter;
    protected QuickMenuView mQuickMenuView;
    private View mRotationBt;
    private View mZoomInBt;
    private View mZoomOutBt;

    protected void setupChildScreen(View rootView) {
        if (rootView != null) {
            boolean z;
            MapOverlayAnimator animator = this.mFragment.getAnimator();
            SToggleButton btn2D3DBrowse = (SToggleButton) rootView.findViewById(2131624414);
            if (MapControlsManager.nativeIsMapView2D()) {
                z = false;
            } else {
                z = true;
            }
            btn2D3DBrowse.setChecked(z);
            btn2D3DBrowse.setOnCheckedChangeListener(MapControlsManager.getOnClickListener2D3D(btn2D3DBrowse));
            animator.registerViewForAnimation(btn2D3DBrowse, Mode.NAVIGATE_QUICK_MENU, "alpha", 0.0f, 1.0f);
            View[] views = new View[2];
            setupZoomControls(rootView, views);
            animator.registerViewsForAnimation(views, Mode.NAVIGATE_QUICK_MENU, "alpha", 0.0f, 1.0f);
            setupRotationLock(rootView, views);
            animator.registerViewForAnimation(views[0], Mode.NAVIGATE_QUICK_MENU, "alpha", 0.0f, 1.0f);
            this.mRotationBt = rootView.findViewById(2131624417);
            this.mZoomInBt = rootView.findViewById(2131624416);
            this.mZoomOutBt = rootView.findViewById(2131624415);
            this.mQuickMenuView = (QuickMenuView) rootView.findViewById(2131624427);
            animator.registerViewForTranslateAnimationByY(this.mQuickMenuView, Mode.NAVIGATE_QUICK_MENU);
            OverlayScreen instance = OverlayScreen.getInstance(Mode.NAVIGATE_INFO_BAR);
            if (instance != null) {
                this.mQuickMenuView.registerDemoStartListener((DemoListener) instance);
                this.mQuickMenuView.registerMuteListener(QuickMenuView.getDefaultMuteChangeListeners());
            }
            this.mNotificationCenter = (NotificationCenterView) rootView.findViewById(2131624420);
            animator.registerViewForAnimation(this.mNotificationCenter, Mode.NAVIGATE_QUICK_MENU, "alpha", 0.0f, 1.0f);
        }
    }

    protected void onScreenEntered() {
        if (this.mQuickMenuView != null) {
            QuickMenuTimer quickMenuTimer = (QuickMenuTimer) SygicHelper.getToolbar().getTag();
            this.mQuickMenuView.setTimer(quickMenuTimer);
            this.mRotationBt.setTag(2131624427, quickMenuTimer);
            this.mZoomInBt.setTag(2131624427, quickMenuTimer);
            this.mZoomOutBt.setTag(2131624427, quickMenuTimer);
            this.mNotificationCenter.setTag(2131624427, quickMenuTimer);
        }
        this.mNotificationCenter.setActive(true);
    }

    protected void onscreenLeft() {
        this.mNotificationCenter.setActive(false);
    }

    protected void onDestroy() {
        super.onDestroy();
        OverlayScreen instance = OverlayScreen.getInstance(Mode.NAVIGATE_INFO_BAR);
        if (instance != null) {
            this.mQuickMenuView.unregisterDemoStartListener((DemoListener) instance);
            this.mQuickMenuView.unregisterMuteListener(QuickMenuView.getDefaultMuteChangeListeners());
        }
    }

    private static void resetQuickMenu() {
        NavigationQuickMenuScreen screen = (NavigationQuickMenuScreen) OverlayScreen.getInstance(Mode.NAVIGATE_QUICK_MENU);
        if (screen != null && screen.mQuickMenuView != null) {
            screen.mQuickMenuView.resetItems();
        }
    }

    public static void onLanguageChanged(String newLang) {
        resetQuickMenu();
    }

    public static void onDebugChanged() {
        resetQuickMenu();
    }

    public static void onRouteCanceled() {
        NavigationQuickMenuScreen screen = (NavigationQuickMenuScreen) OverlayScreen.getInstance(Mode.NAVIGATE_QUICK_MENU);
        if (screen != null && screen.mQuickMenuView != null) {
            screen.mQuickMenuView.stopHasWaypointsTimer();
            screen.mQuickMenuView.updatePedestrianButton(false);
            screen.mQuickMenuView.setPedestrianButtonEnabled(true);
        }
    }

    public static void onRouteComputeError() {
        NavigationQuickMenuScreen screen = (NavigationQuickMenuScreen) OverlayScreen.getInstance(Mode.NAVIGATE_QUICK_MENU);
        if (screen != null && screen.mQuickMenuView != null) {
            screen.mQuickMenuView.updatePedestrianButton(false);
            screen.mQuickMenuView.setPedestrianButtonEnabled(true);
        }
    }

    public static void onRouteComputeFinishedAll() {
        NavigationQuickMenuScreen screen = (NavigationQuickMenuScreen) OverlayScreen.getInstance(Mode.NAVIGATE_QUICK_MENU);
        if (screen != null && screen.mQuickMenuView != null) {
            screen.mQuickMenuView.startHasViapointsTimer();
            screen.mQuickMenuView.updatePedestrianButton(RouteSummary.nativeIsPedestrian());
            screen.mQuickMenuView.setPedestrianButtonEnabled(true);
            screen.mQuickMenuView.layoutItems();
            if (!DemoManager.isStopped()) {
                screen.mQuickMenuView.cancelDemo();
            }
        }
    }

    public static void onStartComputingProgress() {
        NavigationQuickMenuScreen screen = (NavigationQuickMenuScreen) OverlayScreen.getInstance(Mode.NAVIGATE_QUICK_MENU);
        if (screen != null && screen.mQuickMenuView != null) {
            screen.mQuickMenuView.setPedestrianButtonEnabled(false);
        }
    }

    public void onMuteChanged(boolean isMuted) {
        this.mQuickMenuView.onMuteChanged(isMuted);
    }

    public static void showPoiOnRouteNotifications(boolean show) {
        NavigationQuickMenuScreen screen = (NavigationQuickMenuScreen) OverlayScreen.getInstance(Mode.NAVIGATE_QUICK_MENU);
        if (screen == null) {
            return;
        }
        if (show) {
            screen.mNotificationCenter.addAcceptance(16);
        } else {
            screen.mNotificationCenter.removeAcceptance(16);
        }
    }
}
