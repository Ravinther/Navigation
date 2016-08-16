package com.sygic.aura.map.screen;

import android.view.View;
import com.sygic.aura.helper.SygicHelper;
import com.sygic.aura.map.MapControlsManager;
import com.sygic.aura.map.fragment.MapOverlayFragment.Mode;
import com.sygic.aura.map.screen.intefaces.SoundMutedListener;
import com.sygic.aura.map.view.MapOverlayAnimator;
import com.sygic.aura.quickmenu.QuickMenuTimer;
import com.sygic.aura.quickmenu.QuickMenuView;
import com.sygic.aura.views.font_specials.SToggleButton;

public class FreeDriveQuickMenuScreen extends OverlayScreen implements SoundMutedListener {
    protected QuickMenuView mQuickMenuViewFreeDrive;
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
            animator.registerViewForAnimation(btn2D3DBrowse, Mode.FREEDRIVE_QUICK_MENU, "alpha", 0.0f, 1.0f);
            View[] views = new View[2];
            setupZoomControls(rootView, views);
            animator.registerViewsForAnimation(views, Mode.FREEDRIVE_QUICK_MENU, "alpha", 0.0f, 1.0f);
            setupRotationLock(rootView, views);
            animator.registerViewForAnimation(views[0], Mode.FREEDRIVE_QUICK_MENU, "alpha", 0.0f, 1.0f);
            this.mRotationBt = rootView.findViewById(2131624417);
            this.mZoomInBt = rootView.findViewById(2131624416);
            this.mZoomOutBt = rootView.findViewById(2131624415);
            this.mQuickMenuViewFreeDrive = (QuickMenuView) rootView.findViewById(2131624427);
            animator.registerViewForTranslateAnimationByY(this.mQuickMenuViewFreeDrive, Mode.FREEDRIVE_QUICK_MENU);
            if (OverlayScreen.getInstance(Mode.FREEDRIVE_INFO_BAR) != null) {
                this.mQuickMenuViewFreeDrive.registerMuteListener(QuickMenuView.getDefaultMuteChangeListeners());
            }
        }
    }

    protected void onScreenEntered() {
        if (this.mQuickMenuViewFreeDrive != null) {
            QuickMenuTimer quickMenuTimer = (QuickMenuTimer) SygicHelper.getToolbar().getTag();
            this.mQuickMenuViewFreeDrive.setTimer(quickMenuTimer);
            this.mRotationBt.setTag(2131624427, quickMenuTimer);
            this.mZoomInBt.setTag(2131624427, quickMenuTimer);
            this.mZoomOutBt.setTag(2131624427, quickMenuTimer);
        }
    }

    protected void onscreenLeft() {
    }

    protected void onDestroy() {
        super.onDestroy();
        if (OverlayScreen.getInstance(Mode.FREEDRIVE_INFO_BAR) != null) {
            this.mQuickMenuViewFreeDrive.unregisterMuteListener(QuickMenuView.getDefaultMuteChangeListeners());
        }
    }

    public void onMuteChanged(boolean isMuted) {
        this.mQuickMenuViewFreeDrive.onMuteChanged(isMuted);
    }
}
