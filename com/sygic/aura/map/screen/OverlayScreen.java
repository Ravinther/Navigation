package com.sygic.aura.map.screen;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.drawable.Animatable;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.FragmentManager;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import com.sygic.aura.activity.NaviNativeActivity;
import com.sygic.aura.blackbox.fragment.BlackBoxFragment;
import com.sygic.aura.fragments.interfaces.FragmentManagerInterface;
import com.sygic.aura.helper.SToast;
import com.sygic.aura.helper.SygicHelper;
import com.sygic.aura.incidents.fragment.ReportIncidentFragment;
import com.sygic.aura.incidents.interfaces.ReportIncidentTimeOutCallback;
import com.sygic.aura.map.MapControlsManager;
import com.sygic.aura.map.PositionInfo;
import com.sygic.aura.map.fragment.MapOverlayFragment;
import com.sygic.aura.map.fragment.MapOverlayFragment.Mode;
import com.sygic.aura.map.screen.intefaces.InfoBarInterface;
import com.sygic.aura.map.view.MapOverlayAnimator;
import com.sygic.aura.resources.ResourceManager;
import com.sygic.aura.settings.data.SettingsManager;
import com.sygic.aura.views.font_specials.SImageButton;
import com.sygic.aura.views.font_specials.SToggleButton;
import com.sygic.aura.views.font_specials.SToolbar;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import loquendo.tts.engine.TTSConst;

public abstract class OverlayScreen {
    private static final Map<Mode, OverlayScreen> sInstances;
    private ViewGroup mBlackBoxView;
    protected Context mContext;
    protected MapOverlayFragment mFragment;
    private LinearLayout mLeftBottomControls;
    private SharedPreferences mPreferences;

    /* renamed from: com.sygic.aura.map.screen.OverlayScreen.1 */
    class C13481 implements OnClickListener {

        /* renamed from: com.sygic.aura.map.screen.OverlayScreen.1.1 */
        class C13471 implements ReportIncidentTimeOutCallback {
            final /* synthetic */ View val$view;

            C13471(View view) {
                this.val$view = view;
            }

            public void timeoutFinished() {
                this.val$view.setSelected(false);
            }
        }

        C13481() {
        }

        public void onClick(View view) {
            boolean z = true;
            if (view.isSelected()) {
                FragmentManager manager = SygicHelper.getFragmentManager();
                if (manager != null) {
                    ReportIncidentFragment fragment = (ReportIncidentFragment) manager.findFragmentByTag("fragment_incidents");
                    if (fragment != null) {
                        fragment.dismissFragment();
                    }
                }
            } else if (!PositionInfo.nativeHasValidPosition(false)) {
                SToast.makeText(OverlayScreen.this.mContext, 2131165593, 1).show();
            } else if (PositionInfo.nativeIsVehicleDriving()) {
                FragmentManagerInterface manager2 = SygicHelper.getFragmentActivityWrapper();
                if (manager2 != null) {
                    Bundle bundle = new Bundle();
                    bundle.putParcelable("position", PositionInfo.nativeGetVehiclePosition(false));
                    manager2.addFragment(ReportIncidentFragment.class, "fragment_incidents", true, new C13471(view), bundle);
                }
                if (view.isSelected()) {
                    z = false;
                }
                view.setSelected(z);
            } else {
                SToast.makeText(OverlayScreen.this.mContext, 2131166022, 1).show();
            }
        }
    }

    /* renamed from: com.sygic.aura.map.screen.OverlayScreen.2 */
    class C13492 implements OnClickListener {
        C13492() {
        }

        public void onClick(View v) {
            BlackBoxFragment blackBoxFragment = BlackBoxFragment.getBlackBoxFragment(OverlayScreen.this.mFragment.getFragmentManager());
            if (blackBoxFragment != null) {
                blackBoxFragment.maximize();
            }
        }
    }

    /* renamed from: com.sygic.aura.map.screen.OverlayScreen.3 */
    static /* synthetic */ class C13503 {
        static final /* synthetic */ int[] $SwitchMap$com$sygic$aura$map$fragment$MapOverlayFragment$Mode;

        static {
            $SwitchMap$com$sygic$aura$map$fragment$MapOverlayFragment$Mode = new int[Mode.values().length];
            try {
                $SwitchMap$com$sygic$aura$map$fragment$MapOverlayFragment$Mode[Mode.FREEDRIVE_BROWSE.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                $SwitchMap$com$sygic$aura$map$fragment$MapOverlayFragment$Mode[Mode.FREEDRIVE_INFO_BAR.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                $SwitchMap$com$sygic$aura$map$fragment$MapOverlayFragment$Mode[Mode.FREEDRIVE_QUICK_MENU.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
            try {
                $SwitchMap$com$sygic$aura$map$fragment$MapOverlayFragment$Mode[Mode.ROUTE_SELECTION.ordinal()] = 4;
            } catch (NoSuchFieldError e4) {
            }
            try {
                $SwitchMap$com$sygic$aura$map$fragment$MapOverlayFragment$Mode[Mode.NAVIGATE_INFO_BAR.ordinal()] = 5;
            } catch (NoSuchFieldError e5) {
            }
            try {
                $SwitchMap$com$sygic$aura$map$fragment$MapOverlayFragment$Mode[Mode.NAVIGATE_QUICK_MENU.ordinal()] = 6;
            } catch (NoSuchFieldError e6) {
            }
            try {
                $SwitchMap$com$sygic$aura$map$fragment$MapOverlayFragment$Mode[Mode.NAVIGATE_BROWSE.ordinal()] = 7;
            } catch (NoSuchFieldError e7) {
            }
            try {
                $SwitchMap$com$sygic$aura$map$fragment$MapOverlayFragment$Mode[Mode.NAVIGATE_JUNCTION.ordinal()] = 8;
            } catch (NoSuchFieldError e8) {
            }
            try {
                $SwitchMap$com$sygic$aura$map$fragment$MapOverlayFragment$Mode[Mode.RESTORE_ROUTE.ordinal()] = 9;
            } catch (NoSuchFieldError e9) {
            }
            try {
                $SwitchMap$com$sygic$aura$map$fragment$MapOverlayFragment$Mode[Mode.SELECT_START_FROM_MAP.ordinal()] = 10;
            } catch (NoSuchFieldError e10) {
            }
            try {
                $SwitchMap$com$sygic$aura$map$fragment$MapOverlayFragment$Mode[Mode.NO_MAPS.ordinal()] = 11;
            } catch (NoSuchFieldError e11) {
            }
            try {
                $SwitchMap$com$sygic$aura$map$fragment$MapOverlayFragment$Mode[Mode.TRIPLOG_SHOW_ON_MAP.ordinal()] = 12;
            } catch (NoSuchFieldError e12) {
            }
            try {
                $SwitchMap$com$sygic$aura$map$fragment$MapOverlayFragment$Mode[Mode.ACTION_CONTROL.ordinal()] = 13;
            } catch (NoSuchFieldError e13) {
            }
        }
    }

    protected abstract void onScreenEntered();

    protected abstract void onscreenLeft();

    protected abstract void setupChildScreen(View view);

    static {
        sInstances = new HashMap();
    }

    protected void onDestroy() {
    }

    public static void destroyReferences() {
        for (Entry<Mode, OverlayScreen> entry : sInstances.entrySet()) {
            ((OverlayScreen) entry.getValue()).onDestroy();
        }
        sInstances.clear();
    }

    public static void setupScreen(Mode mode, MapOverlayFragment fragment) {
        OverlayScreen screen = getInstance(mode);
        if (screen != null) {
            screen.init(fragment);
            screen.setupChildScreen(fragment.getAnimator().getChildForMode(mode));
        }
    }

    public static void onScreenEntered(Mode mode) {
        OverlayScreen screen = getInstance(mode);
        if (screen != null) {
            screen.onScreenEntered();
        }
    }

    public static void onScreenLeft(Mode mode) {
        OverlayScreen screen = getInstance(mode);
        if (screen != null) {
            screen.onscreenLeft();
        }
    }

    public static void setupZoomControls(Mode mode, MapOverlayAnimator animator) {
        OverlayScreen screen = getInstance(mode);
        if (screen != null) {
            screen.setupZoomControls(animator.getChildForMode(mode));
        }
    }

    public static void setupRotationLock(Mode mode, MapOverlayAnimator animator) {
        OverlayScreen screen = getInstance(mode);
        if (screen != null) {
            screen.setupRotationLock(animator.getChildForMode(mode));
        }
    }

    public static void setBlackboxVisibility(Mode mode, boolean visible) {
        OverlayScreen screen = getInstance(mode);
        if (screen != null) {
            screen.setBlackboxVisibility(visible);
        }
    }

    protected static void performTrafficChange(SToggleButton button, boolean isChecked, boolean originalState) {
        if (isChecked) {
            button.setChecked(false);
            MapControlsManager.nativeEnterTrafficView();
            return;
        }
        MapControlsManager.nativeLeaveTrafficView();
        if (button.isChecked() != originalState) {
            button.setChecked(originalState);
        } else if (originalState) {
            MapControlsManager.nativeSetMapView3D();
        } else {
            MapControlsManager.nativeSetMapView2D();
        }
    }

    public static OverlayScreen getInstance(Mode mode) {
        OverlayScreen screen = (OverlayScreen) sInstances.get(mode);
        if (screen == null) {
            try {
                switch (C13503.$SwitchMap$com$sygic$aura$map$fragment$MapOverlayFragment$Mode[mode.ordinal()]) {
                    case TTSConst.TTSMULTILINE /*1*/:
                        screen = (OverlayScreen) FreeDriveBrowseScreen.class.newInstance();
                        break;
                    case TTSConst.TTSPARAGRAPH /*2*/:
                        screen = (OverlayScreen) FreeDriveInfoBarScreen.class.newInstance();
                        break;
                    case TTSConst.TTSUNICODE /*3*/:
                        screen = (OverlayScreen) FreeDriveQuickMenuScreen.class.newInstance();
                        break;
                    case TTSConst.TTSXML /*4*/:
                        screen = (OverlayScreen) RouteSelectionScreen.class.newInstance();
                        break;
                    case TTSConst.TTSEVT_TEXT /*5*/:
                        screen = (OverlayScreen) NavigationInfoBarScreen.class.newInstance();
                        break;
                    case TTSConst.TTSEVT_SENTENCE /*6*/:
                        screen = (OverlayScreen) NavigationQuickMenuScreen.class.newInstance();
                        break;
                    case TTSConst.TTSEVT_BOOKMARK /*7*/:
                        screen = (OverlayScreen) NavigationBrowseScreen.class.newInstance();
                        break;
                    case TTSConst.TTSEVT_PAUSE /*9*/:
                        screen = (OverlayScreen) RestoreRouteScreen.class.newInstance();
                        break;
                    case TTSConst.TTSEVT_RESUME /*10*/:
                        screen = (OverlayScreen) SelectStartFromMapScreen.class.newInstance();
                        break;
                    case TTSConst.TTSEVT_FREESPACE /*11*/:
                        screen = (OverlayScreen) NoMapsScreen.class.newInstance();
                        break;
                    case TTSConst.TTSEVT_NOTSENT /*12*/:
                        screen = (OverlayScreen) TriplogShowOnMapScreen.class.newInstance();
                        break;
                    case TTSConst.TTSEVT_AUDIO /*13*/:
                        screen = (OverlayScreen) ActionControlScreen.class.newInstance();
                        break;
                }
                sInstances.put(mode, screen);
            } catch (IllegalAccessException e) {
                return null;
            } catch (InstantiationException e2) {
                return null;
            }
        }
        return screen;
    }

    protected OverlayScreen() {
    }

    private void init(MapOverlayFragment fragment) {
        this.mContext = fragment.getActivity();
        this.mPreferences = PreferenceManager.getDefaultSharedPreferences(this.mContext);
        this.mFragment = fragment;
    }

    protected boolean isZoomControlVisibilityFromSettings() {
        return false;
    }

    protected void setupZoomControls(View rootView) {
        setupZoomControls(rootView, null);
    }

    protected void setupZoomControls(View rootView, View[] views) {
        boolean zoomVisible;
        int i;
        int i2 = 8;
        if (!isZoomControlVisibilityFromSettings() || this.mPreferences.getBoolean(this.mContext.getString(2131166709), false)) {
            zoomVisible = true;
        } else {
            zoomVisible = false;
        }
        SImageButton btnZoomOut = (SImageButton) rootView.findViewById(2131624415);
        if (zoomVisible) {
            i = 0;
        } else {
            i = 8;
        }
        btnZoomOut.setVisibility(i);
        SImageButton btnZoomIn = (SImageButton) rootView.findViewById(2131624416);
        if (zoomVisible) {
            i2 = 0;
        }
        btnZoomIn.setVisibility(i2);
        if (zoomVisible) {
            if (btnZoomOut != null) {
                btnZoomOut.setOnTouchListener(MapControlsManager.getZoomOutListener());
            }
            if (btnZoomIn != null) {
                btnZoomIn.setOnTouchListener(MapControlsManager.getZoomInListener());
            }
        }
        if (views != null && views.length >= 2) {
            views[0] = btnZoomIn;
            views[1] = btnZoomOut;
        }
    }

    protected void setupRotationLock(View rootView) {
        setupRotationLock(rootView, null);
    }

    protected void setupRotationLock(View rootView, View[] views) {
        SToggleButton btnRotateLock = (SToggleButton) rootView.findViewById(2131624417);
        btnRotateLock.setVisibility(this.mPreferences.getBoolean(this.mContext.getString(2131166299), true) ? 0 : 8);
        btnRotateLock.setOnCheckedChangeListener(MapControlsManager.getRotateLockListener());
        if (views != null && views.length >= 1) {
            views[0] = btnRotateLock;
        }
    }

    protected void setupReportIncident(View rootView) {
        SImageButton btnSpeedcam = (SImageButton) rootView.findViewById(2131624421);
        btnSpeedcam.setSelected(false);
        btnSpeedcam.setOnClickListener(new C13481());
    }

    protected void setupMutedControl(View rootView, View[] views) {
        View btnMuted = rootView.findViewById(2131624386);
        btnMuted.setVisibility(SettingsManager.nativeIsMuted() ? 0 : 8);
        btnMuted.setEnabled(false);
        if (views != null && views.length >= 1) {
            views[0] = btnMuted;
        }
    }

    protected void setupBottomLeftControls(View rootView) {
        this.mLeftBottomControls = (LinearLayout) rootView.findViewById(2131624426);
    }

    public void updateBottomLeftControls(int orientation) {
        this.mLeftBottomControls.setOrientation(orientation);
    }

    protected void setupBlackboxMaximize(View rootView, View[] views) {
        this.mBlackBoxView = (ViewGroup) rootView.findViewById(2131624383);
        ((SImageButton) this.mBlackBoxView.findViewById(2131624384)).setOnClickListener(new C13492());
        if (views != null && views.length >= 1) {
            views[0] = this.mBlackBoxView;
        }
    }

    protected void setBlackboxVisibility(boolean visible) {
        this.mBlackBoxView.setVisibility(visible ? 0 : 8);
        Animatable animation = (Animatable) ((ImageView) this.mBlackBoxView.findViewById(2131624385)).getDrawable();
        if (visible) {
            animation.start();
        } else {
            animation.stop();
        }
    }

    public static void setInfoBarInaccurateSignal(Mode mode, boolean hasSignal) {
        InfoBarInterface infoBarScreen;
        switch (C13503.$SwitchMap$com$sygic$aura$map$fragment$MapOverlayFragment$Mode[mode.ordinal()]) {
            case TTSConst.TTSPARAGRAPH /*2*/:
                infoBarScreen = (InfoBarInterface) getInstance(Mode.FREEDRIVE_INFO_BAR);
                break;
            case TTSConst.TTSEVT_TEXT /*5*/:
                infoBarScreen = (InfoBarInterface) getInstance(Mode.NAVIGATE_INFO_BAR);
                break;
            default:
                infoBarScreen = null;
                break;
        }
        if (infoBarScreen == null) {
            return;
        }
        if (hasSignal) {
            try {
                infoBarScreen.getInfoBar().setBackgroundColor(((OverlayScreen) infoBarScreen).mContext.getResources().getColor(2131558457));
                ResourceManager.makeControlVisible(infoBarScreen.getProgress(), true);
                ResourceManager.makeControlVisible(infoBarScreen.getInfoBarSlots(), true);
                ResourceManager.makeControlVisible(infoBarScreen.getSignalView(), false, true);
                return;
            } catch (Exception e) {
                e.printStackTrace();
                return;
            }
        }
        infoBarScreen.getInfoBar().setBackgroundColor(((OverlayScreen) infoBarScreen).mContext.getResources().getColor(2131558458));
        ResourceManager.makeControlVisible(infoBarScreen.getProgress(), false);
        ResourceManager.makeControlVisible(infoBarScreen.getInfoBarSlots(), false, true);
        ResourceManager.makeControlVisible(infoBarScreen.getSignalView(), true);
    }

    protected SToolbar getToolbar() {
        return ((NaviNativeActivity) this.mFragment.getActivity()).getToolbar();
    }
}
