package com.sygic.aura.map.fragment;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Handler;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import android.preference.PreferenceManager;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager.BackStackEntry;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.sygic.aura.activity.NaviNativeActivity;
import com.sygic.aura.feature.automotive.InCarConnectionListener;
import com.sygic.aura.fragments.AbstractScreenFragment;
import com.sygic.aura.fragments.interfaces.FragmentManagerInterface;
import com.sygic.aura.fragments.interfaces.FragmentResultCallback;
import com.sygic.aura.helper.AsyncTaskHelper;
import com.sygic.aura.helper.CustomDialogFragment;
import com.sygic.aura.helper.EventReceivers.LicenseEventsReceiver;
import com.sygic.aura.helper.EventReceivers.MapEventsReceiver;
import com.sygic.aura.helper.EventReceivers.RouteEventsReceiver;
import com.sygic.aura.helper.InCarConnection;
import com.sygic.aura.helper.RepeatingThread;
import com.sygic.aura.helper.RepeatingThread.ExecutionOrder;
import com.sygic.aura.helper.SygicHelper;
import com.sygic.aura.helper.interfaces.BackPressedListener;
import com.sygic.aura.helper.interfaces.ColorSchemeChangeListener;
import com.sygic.aura.helper.interfaces.LicenseListener;
import com.sygic.aura.helper.interfaces.MapClickListener;
import com.sygic.aura.helper.interfaces.RecomputeListener;
import com.sygic.aura.helper.interfaces.RouteCancelListener;
import com.sygic.aura.helper.interfaces.RouteComputeErrorListener;
import com.sygic.aura.helper.interfaces.RouteEventsListener;
import com.sygic.aura.helper.interfaces.RoutePartChangeListener;
import com.sygic.aura.helper.interfaces.TrafficReceivedListener;
import com.sygic.aura.helper.interfaces.UnlockNaviListener;
import com.sygic.aura.helper.interfaces.VehicleClickListener;
import com.sygic.aura.map.MapControlsManager;
import com.sygic.aura.map.MapControlsManager.EMapView;
import com.sygic.aura.map.MapControlsManager.ENaviType;
import com.sygic.aura.map.PositionInfo;
import com.sygic.aura.map.bubble.BubbleManager;
import com.sygic.aura.map.data.map_selection.MapSelection;
import com.sygic.aura.map.screen.FreeDriveBrowseScreen;
import com.sygic.aura.map.screen.FreeDriveInfoBarScreen;
import com.sygic.aura.map.screen.NavigationBrowseScreen;
import com.sygic.aura.map.screen.NavigationInfoBarScreen;
import com.sygic.aura.map.screen.NavigationQuickMenuScreen;
import com.sygic.aura.map.screen.OverlayScreen;
import com.sygic.aura.map.screen.RouteSelectionScreen;
import com.sygic.aura.map.screen.SelectStartFromMapScreen;
import com.sygic.aura.map.view.MapOverlayAnimator;
import com.sygic.aura.map.view.MapOverlayAnimator.OnChildChangeListener;
import com.sygic.aura.network.ComponentManager;
import com.sygic.aura.poi.detail.PoiDetailFragment;
import com.sygic.aura.quickmenu.QuickMenuTimer;
import com.sygic.aura.route.InfoSlotsUpdateTask;
import com.sygic.aura.route.InfoSlotsUpdateTask.SlotHolder;
import com.sygic.aura.route.RouteSummary;
import com.sygic.aura.settings.data.SettingsManager;
import com.sygic.aura.settings.data.SettingsManager.EColorScheme;
import com.sygic.aura.settings.data.SettingsManager.EPoiOnRoute;
import com.sygic.aura.settings.data.SettingsManager.ESettingsType;
import com.sygic.aura.settings.data.SettingsManager.OnCoreSettingsChangeListener;
import com.sygic.aura.settings.data.SettingsManager.OnDebugChangeListener;
import com.sygic.aura.settings.data.SettingsManager.OnLanguageChangeListener;
import loquendo.tts.engine.TTSConst;

public class MapOverlayFragment extends AbstractScreenFragment implements InCarConnectionListener, BackPressedListener, ColorSchemeChangeListener, LicenseListener, MapClickListener, RecomputeListener, RouteCancelListener, RouteComputeErrorListener, RouteEventsListener, RoutePartChangeListener, TrafficReceivedListener, UnlockNaviListener, VehicleClickListener, OnCoreSettingsChangeListener, OnDebugChangeListener, OnLanguageChangeListener {
    private Context mContext;
    private RepeatingThread mInaccurateSignalThread;
    private long mLastSignalTime;
    private int mOrientation;
    private QuickMenuTimer mQuickMenuTimer;
    private final MapOverlayReceiver mReceiver;
    private boolean mShouldIgnoreUnlock;
    private MapOverlayAnimator mViewAnimator;

    /* renamed from: com.sygic.aura.map.fragment.MapOverlayFragment.1 */
    static /* synthetic */ class C13261 {
        static final /* synthetic */ int[] $SwitchMap$com$sygic$aura$map$fragment$MapOverlayFragment$Mode;
        static final /* synthetic */ int[] f1261xb7e7eb1d;
        static final /* synthetic */ int[] f1262x44c2e7da;

        static {
            $SwitchMap$com$sygic$aura$map$fragment$MapOverlayFragment$Mode = new int[Mode.values().length];
            try {
                $SwitchMap$com$sygic$aura$map$fragment$MapOverlayFragment$Mode[Mode.NAVIGATE_INFO_BAR.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                $SwitchMap$com$sygic$aura$map$fragment$MapOverlayFragment$Mode[Mode.FREEDRIVE_INFO_BAR.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            f1261xb7e7eb1d = new int[EPoiOnRoute.values().length];
            try {
                f1261xb7e7eb1d[EPoiOnRoute.Hidden.ordinal()] = 1;
            } catch (NoSuchFieldError e3) {
            }
            try {
                f1261xb7e7eb1d[EPoiOnRoute.Auto.ordinal()] = 2;
            } catch (NoSuchFieldError e4) {
            }
            try {
                f1261xb7e7eb1d[EPoiOnRoute.Visible.ordinal()] = 3;
            } catch (NoSuchFieldError e5) {
            }
            f1262x44c2e7da = new int[ESettingsType.values().length];
            try {
                f1262x44c2e7da[ESettingsType.ePoiOnRouteOnOff.ordinal()] = 1;
            } catch (NoSuchFieldError e6) {
            }
            try {
                f1262x44c2e7da[ESettingsType.eRotationLock.ordinal()] = 2;
            } catch (NoSuchFieldError e7) {
            }
            try {
                f1262x44c2e7da[ESettingsType.eZoomControls.ordinal()] = 3;
            } catch (NoSuchFieldError e8) {
            }
            try {
                f1262x44c2e7da[ESettingsType.eCarSlot1.ordinal()] = 4;
            } catch (NoSuchFieldError e9) {
            }
            try {
                f1262x44c2e7da[ESettingsType.ePedSlot1.ordinal()] = 5;
            } catch (NoSuchFieldError e10) {
            }
            try {
                f1262x44c2e7da[ESettingsType.eCarSlot2.ordinal()] = 6;
            } catch (NoSuchFieldError e11) {
            }
            try {
                f1262x44c2e7da[ESettingsType.ePedSlot2.ordinal()] = 7;
            } catch (NoSuchFieldError e12) {
            }
            try {
                f1262x44c2e7da[ESettingsType.eCarSlot3.ordinal()] = 8;
            } catch (NoSuchFieldError e13) {
            }
            try {
                f1262x44c2e7da[ESettingsType.ePedSlot3.ordinal()] = 9;
            } catch (NoSuchFieldError e14) {
            }
        }
    }

    private class MapOverlayReceiver extends BroadcastReceiver implements OnChildChangeListener {
        private InfoSlotsUpdateTask mRefreshTask;

        /* renamed from: com.sygic.aura.map.fragment.MapOverlayFragment.MapOverlayReceiver.1 */
        class C13271 implements Runnable {
            final /* synthetic */ Mode val$actual;

            C13271(Mode mode) {
                this.val$actual = mode;
            }

            public void run() {
                if (MapOverlayFragment.this.mViewAnimator.getDisplayedMode().equals(this.val$actual)) {
                    MapOverlayReceiver.this.startInaccurateSignalTask(this.val$actual);
                }
            }
        }

        /* renamed from: com.sygic.aura.map.fragment.MapOverlayFragment.MapOverlayReceiver.2 */
        class C13282 implements Runnable {
            final /* synthetic */ Mode val$actual;

            C13282(Mode mode) {
                this.val$actual = mode;
            }

            public void run() {
                if (MapOverlayFragment.this.mViewAnimator.getDisplayedMode().equals(this.val$actual)) {
                    MapOverlayReceiver.this.startInaccurateSignalTask(this.val$actual);
                }
            }
        }

        /* renamed from: com.sygic.aura.map.fragment.MapOverlayFragment.MapOverlayReceiver.3 */
        class C13313 implements ExecutionOrder {
            private Boolean mLastSignalState;
            final /* synthetic */ Mode val$mode;

            /* renamed from: com.sygic.aura.map.fragment.MapOverlayFragment.MapOverlayReceiver.3.1 */
            class C13291 implements Runnable {
                C13291() {
                }

                public void run() {
                    OverlayScreen.setInfoBarInaccurateSignal(C13313.this.val$mode, true);
                }
            }

            /* renamed from: com.sygic.aura.map.fragment.MapOverlayFragment.MapOverlayReceiver.3.2 */
            class C13302 implements Runnable {
                C13302() {
                }

                public void run() {
                    OverlayScreen.setInfoBarInaccurateSignal(C13313.this.val$mode, false);
                }
            }

            C13313(Mode mode) {
                this.val$mode = mode;
                this.mLastSignalState = null;
            }

            public boolean runningCondition() {
                boolean hasPosition = PositionInfo.nativeHasActualPosition(true);
                if (hasPosition) {
                    MapOverlayFragment.this.mLastSignalTime = System.currentTimeMillis();
                    return hasPosition;
                } else if (System.currentTimeMillis() - MapOverlayFragment.this.mLastSignalTime < 4000) {
                    return true;
                } else {
                    return hasPosition;
                }
            }

            public boolean onPositive() {
                if (this.mLastSignalState == null || !this.mLastSignalState.booleanValue()) {
                    this.mLastSignalState = Boolean.valueOf(true);
                    try {
                        MapOverlayFragment.this.getActivity().runOnUiThread(new C13291());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                return true;
            }

            public boolean onNegative() {
                if (this.mLastSignalState == null || this.mLastSignalState.booleanValue()) {
                    this.mLastSignalState = Boolean.valueOf(false);
                    try {
                        MapOverlayFragment.this.getActivity().runOnUiThread(new C13302());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                return true;
            }
        }

        private MapOverlayReceiver() {
        }

        public void onReceive(Context context, Intent intent) {
            if (intent.getAction() != null && intent.getAction().equals("com.sygic.aura.MapOverlayFragment.SetModeAction")) {
                Mode mode = (Mode) intent.getParcelableExtra("mode");
                if (mode != null && !MapOverlayFragment.this.mViewAnimator.getDisplayedMode().equals(mode)) {
                    MapOverlayFragment.this.mViewAnimator.setDisplayedMode(mode);
                }
            }
        }

        public void onChildSwitched(Mode previous, Mode actual) {
            OverlayScreen.onScreenLeft(previous);
            switch (C13261.$SwitchMap$com$sygic$aura$map$fragment$MapOverlayFragment$Mode[previous.ordinal()]) {
                case TTSConst.TTSMULTILINE /*1*/:
                case TTSConst.TTSPARAGRAPH /*2*/:
                    if (this.mRefreshTask != null) {
                        this.mRefreshTask.finishTask();
                    }
                    if (MapOverlayFragment.this.mInaccurateSignalThread != null) {
                        MapOverlayFragment.this.mInaccurateSignalThread.setFinished(true);
                        break;
                    }
                    break;
            }
            OverlayScreen.onScreenEntered(actual);
            switch (C13261.$SwitchMap$com$sygic$aura$map$fragment$MapOverlayFragment$Mode[actual.ordinal()]) {
                case TTSConst.TTSMULTILINE /*1*/:
                    startNavigationInfoBarTimer(NavigationInfoBarScreen.getInstance());
                    new Handler().postDelayed(new C13282(actual), 1000);
                case TTSConst.TTSPARAGRAPH /*2*/:
                    startNavigationInfoBarTimer(FreeDriveInfoBarScreen.getInstance());
                    new Handler().postDelayed(new C13271(actual), 1000);
                default:
            }
        }

        private void startNavigationInfoBarTimer(SlotHolder holder) {
            if (this.mRefreshTask == null || this.mRefreshTask.isFinished()) {
                this.mRefreshTask = new InfoSlotsUpdateTask();
            }
            AsyncTaskHelper.execute(this.mRefreshTask, holder);
        }

        private void startInaccurateSignalTask(Mode mode) {
            if (MapOverlayFragment.this.mInaccurateSignalThread == null || MapOverlayFragment.this.mInaccurateSignalThread.isFinished()) {
                MapOverlayFragment.this.mInaccurateSignalThread = new RepeatingThread(new C13313(mode), 200);
                MapOverlayFragment.this.mInaccurateSignalThread.start();
            }
        }
    }

    public enum Mode implements Parcelable {
        FREEDRIVE_BROWSE,
        FREEDRIVE_INFO_BAR,
        FREEDRIVE_QUICK_MENU,
        SELECT_START_FROM_MAP,
        ROUTE_SELECTION,
        NAVIGATE_INFO_BAR,
        NAVIGATE_QUICK_MENU,
        NAVIGATE_BROWSE,
        NAVIGATE_JUNCTION,
        RESTORE_ROUTE,
        NO_MAPS,
        TRIPLOG_SHOW_ON_MAP,
        ACTION_CONTROL;
        
        public static final Creator<Mode> CREATOR;

        /* renamed from: com.sygic.aura.map.fragment.MapOverlayFragment.Mode.1 */
        static class C13321 implements Creator<Mode> {
            C13321() {
            }

            public Mode createFromParcel(Parcel source) {
                return Mode.values()[source.readInt()];
            }

            public Mode[] newArray(int size) {
                return new Mode[size];
            }
        }

        static {
            CREATOR = new C13321();
        }

        public int describeContents() {
            return 0;
        }

        public void writeToParcel(Parcel dest, int flags) {
            dest.writeInt(ordinal());
        }
    }

    public MapOverlayFragment() {
        this.mOrientation = -1;
        this.mReceiver = new MapOverlayReceiver();
        this.mInaccurateSignalThread = null;
        this.mLastSignalTime = System.currentTimeMillis();
        setWantsNavigationData(false);
    }

    public static void setMode(Context context, Mode mode) {
        if (context != null) {
            context.sendBroadcast(new Intent("com.sygic.aura.MapOverlayFragment.SetModeAction").setPackage(context.getPackageName()).putExtra("mode", mode));
        }
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.mOrientation = getActivity().getResources().getConfiguration().orientation;
        ((NaviNativeActivity) getActivity()).setDayNightMode(SettingsManager.nativeIsNightModeOn());
        RouteEventsReceiver.registerRouteEventsListener(this);
        RouteEventsReceiver.registerRoutePartChangeListener(this);
        RouteEventsReceiver.registerRouteComputeErrorListener(this);
        MapEventsReceiver.registerUnlockNaviListener(this);
        MapEventsReceiver.registerRouteCancelListener(this);
        MapEventsReceiver.registerTrafficReceivedListener(this);
        MapEventsReceiver.registerMapClickListener(this);
        MapEventsReceiver.registerVehicleClickListener(this);
        MapEventsReceiver.registerRecomputeListener(this);
        MapEventsReceiver.registerColorSchemeChangeListener(this);
        ((NaviNativeActivity) getActivity()).registerBackPressedListener(this);
        SettingsManager.registerOnLanguageChangeListener(this);
        SettingsManager.registerOnDebugChangeListener(this);
        SettingsManager.registerOnCoreSettingsChangeListener(this);
        LicenseEventsReceiver.registerLicenseListener(this);
        InCarConnection.registerOnConnectionListener(this);
    }

    public void onDestroy() {
        super.onDestroy();
        BubbleManager.stop();
        RouteEventsReceiver.unregisterRouteEventsListener(this);
        RouteEventsReceiver.unregisterRoutePartChangeListener(this);
        RouteEventsReceiver.unregisterRouteComputeErrorListener(this);
        MapEventsReceiver.unregisterUnlockNaviListener(this);
        MapEventsReceiver.unregisterRouteCancelListener(this);
        MapEventsReceiver.unregisterTrafficReceivedListener(this);
        MapEventsReceiver.unregisterMapClickListener(this);
        MapEventsReceiver.unregisterVehicleClickListener(this);
        MapEventsReceiver.unregisterRecomputeListener(this);
        MapEventsReceiver.unregisterColorSchemeChangeListener(this);
        ((NaviNativeActivity) getActivity()).unregisterBackPressedListener(this);
        SettingsManager.unregisterOnCoreSettingsChangeListener(this);
        SettingsManager.unregisterOnLanguageChangeListener(this);
        SettingsManager.unregisterOnDebugChangeListener(this);
        LicenseEventsReceiver.unregisterLicenseListener(this);
        InCarConnection.unregisterOnConnectionListener(this);
        if (this.mInaccurateSignalThread != null) {
            this.mInaccurateSignalThread.setFinished(true);
        }
    }

    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.mContext = activity;
        this.mContext.registerReceiver(this.mReceiver, new IntentFilter("com.sygic.aura.MapOverlayFragment.SetModeAction"));
    }

    public void onDetach() {
        super.onDetach();
        this.mContext.unregisterReceiver(this.mReceiver);
    }

    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        this.mOrientation = newConfig.orientation;
        NavigationInfoBarScreen.onConfigurationChanged(newConfig.orientation);
        FreeDriveInfoBarScreen.onConfigurationChanged(newConfig.orientation);
        NavigationBrowseScreen.onConfigurationChanged(getActivity(), newConfig.orientation);
        FreeDriveBrowseScreen.onConfigurationChanged(getActivity(), newConfig.orientation);
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        this.mViewAnimator = (MapOverlayAnimator) inflater.inflate(2130903112, container, false);
        this.mViewAnimator.setOnChildChangeListener(this.mReceiver);
        OverlayScreen.setupScreen(Mode.FREEDRIVE_BROWSE, this);
        OverlayScreen.setupScreen(Mode.FREEDRIVE_INFO_BAR, this);
        OverlayScreen.setupScreen(Mode.FREEDRIVE_QUICK_MENU, this);
        OverlayScreen.setupScreen(Mode.ROUTE_SELECTION, this);
        OverlayScreen.setupScreen(Mode.NAVIGATE_INFO_BAR, this);
        OverlayScreen.setupScreen(Mode.NAVIGATE_BROWSE, this);
        OverlayScreen.setupScreen(Mode.NAVIGATE_QUICK_MENU, this);
        OverlayScreen.setupScreen(Mode.SELECT_START_FROM_MAP, this);
        setPoiOnRouteMode();
        OverlayScreen.setupScreen(Mode.RESTORE_ROUTE, this);
        OverlayScreen.setupScreen(Mode.NO_MAPS, this);
        if (ComponentManager.nativeGetInstalledMapCount() <= 0) {
            this.mViewAnimator.setDisplayedMode(Mode.NO_MAPS);
        }
        OverlayScreen.setupScreen(Mode.TRIPLOG_SHOW_ON_MAP, this);
        OverlayScreen.setupScreen(Mode.ACTION_CONTROL, this);
        return this.mViewAnimator;
    }

    public void onDestroyView() {
        super.onDestroyView();
        OverlayScreen.destroyReferences();
    }

    public MapOverlayAnimator getAnimator() {
        return this.mViewAnimator;
    }

    public int getOrientation() {
        return this.mOrientation;
    }

    public void onCoreSettingsChanged(ESettingsType type, int newValue) {
        switch (C13261.f1262x44c2e7da[type.ordinal()]) {
            case TTSConst.TTSMULTILINE /*1*/:
                setPoiOnRouteMode();
            case TTSConst.TTSPARAGRAPH /*2*/:
                setupRotationLock();
            case TTSConst.TTSUNICODE /*3*/:
                setupZoomControls();
            case TTSConst.TTSXML /*4*/:
            case TTSConst.TTSEVT_TEXT /*5*/:
            case TTSConst.TTSEVT_SENTENCE /*6*/:
            case TTSConst.TTSEVT_BOOKMARK /*7*/:
            case TTSConst.TTSEVT_TAG /*8*/:
            case TTSConst.TTSEVT_PAUSE /*9*/:
                NavigationInfoBarScreen.changeSlot(type, newValue);
            default:
        }
    }

    public void onColorSchemeChanged(EColorScheme colorScheme) {
        boolean isNightModeOn = SettingsManager.nativeIsNightModeOn(colorScheme);
        ((NaviNativeActivity) getActivity()).setDayNightMode(isNightModeOn);
        NavigationInfoBarScreen.onDayNightModeChange(isNightModeOn);
    }

    public void onLanguageChanged(String newLang) {
        NavigationInfoBarScreen.onLanguageChanged(newLang);
        NavigationQuickMenuScreen.onLanguageChanged(newLang);
        FreeDriveInfoBarScreen.onLanguageChanged(newLang);
    }

    public void onDebugChanged() {
        NavigationQuickMenuScreen.onDebugChanged();
    }

    public void onLicenseUpdated() {
        NavigationInfoBarScreen.onLicenseUpdated();
    }

    private void setPoiOnRouteMode() {
        switch (C13261.f1261xb7e7eb1d[EPoiOnRoute.values()[SettingsManager.nativeGetSettings(ESettingsType.ePoiOnRouteOnOff)].ordinal()]) {
            case TTSConst.TTSMULTILINE /*1*/:
                NavigationInfoBarScreen.showPoiOnRouteNotifications(false);
                NavigationQuickMenuScreen.showPoiOnRouteNotifications(false);
            case TTSConst.TTSPARAGRAPH /*2*/:
                NavigationInfoBarScreen.showPoiOnRouteNotifications(false);
                NavigationQuickMenuScreen.showPoiOnRouteNotifications(true);
            case TTSConst.TTSUNICODE /*3*/:
                NavigationInfoBarScreen.showPoiOnRouteNotifications(true);
                NavigationQuickMenuScreen.showPoiOnRouteNotifications(false);
            default:
        }
    }

    private void setupRotationLock() {
        OverlayScreen.setupRotationLock(Mode.FREEDRIVE_BROWSE, this.mViewAnimator);
        OverlayScreen.setupRotationLock(Mode.NAVIGATE_BROWSE, this.mViewAnimator);
        OverlayScreen.setupRotationLock(Mode.ROUTE_SELECTION, this.mViewAnimator);
        OverlayScreen.setupRotationLock(Mode.FREEDRIVE_QUICK_MENU, this.mViewAnimator);
        OverlayScreen.setupRotationLock(Mode.NAVIGATE_QUICK_MENU, this.mViewAnimator);
    }

    private void setupZoomControls() {
        OverlayScreen.setupZoomControls(Mode.FREEDRIVE_BROWSE, this.mViewAnimator);
        OverlayScreen.setupZoomControls(Mode.NAVIGATE_BROWSE, this.mViewAnimator);
    }

    public void setBlackBoxMaximizeVisible(boolean visible) {
        OverlayScreen.setBlackboxVisibility(Mode.FREEDRIVE_INFO_BAR, visible);
        OverlayScreen.setBlackboxVisibility(Mode.NAVIGATE_INFO_BAR, visible);
    }

    public void onStartComputingProgress() {
        FreeDriveBrowseScreen.onStartComputingProgress();
        RouteSelectionScreen.onStartComputingProgress();
        NavigationQuickMenuScreen.onStartComputingProgress();
        NavigationBrowseScreen.onStartComputingProgress();
    }

    public void onFinishComputingProgress() {
    }

    public void onRouteComputeFinishedAll() {
        RouteSelectionScreen.onRouteComputeFinishedAll();
        NavigationQuickMenuScreen.onRouteComputeFinishedAll();
        NavigationBrowseScreen.onRouteComputeFinishedAll();
        FragmentActivity activity = getActivity();
        if (activity != null) {
            PreferenceManager.getDefaultSharedPreferences(activity).edit().putString(getString(2131166238), RouteSummary.nativeGetStartPointText()).apply();
        }
    }

    public void onRouteComputeError(String msg) {
        NavigationQuickMenuScreen.onRouteComputeError();
        MapControlsManager.nativeSetNaviType(ENaviType.NtCar);
        NavigationInfoBarScreen.setupSlots();
    }

    public void onRoutePartChange(Integer index) {
        NavigationInfoBarScreen.onRoutePartChange(index.intValue());
        FreeDriveInfoBarScreen.onRoutePartChange(index.intValue());
    }

    public void onLockNavi() {
        FreeDriveBrowseScreen.onLockNavi();
    }

    public void onUnlockNavi() {
        if (!(this.mQuickMenuTimer == null || this.mQuickMenuTimer.isFinished())) {
            this.mQuickMenuTimer.cancel();
        }
        FreeDriveBrowseScreen.onUnlockNavi();
        if (getFragmentManager().getBackStackEntryCount() == 0 && ComponentManager.nativeGetInstalledMapCount() > 0 && !this.mShouldIgnoreUnlock) {
            setMode(getActivity(), Mode.FREEDRIVE_BROWSE);
        }
        this.mShouldIgnoreUnlock = false;
    }

    public boolean onBackPressed() {
        int entryCount = getFragmentManager().getBackStackEntryCount();
        if (entryCount > 0) {
            BackStackEntry backEntry = getFragmentManager().getBackStackEntryAt(entryCount - 1);
            if (backEntry != null && backEntry.getName().equals("fragment_navigate_tag")) {
                if (this.mViewAnimator.getDisplayedMode().equals(Mode.NAVIGATE_INFO_BAR)) {
                    CustomDialogFragment.showExitDialog(getActivity());
                    return true;
                } else if (this.mViewAnimator.getDisplayedMode().equals(Mode.NAVIGATE_BROWSE)) {
                    NavigationBrowseScreen.resumeNavigation();
                    return true;
                }
            }
            return false;
        } else if (!MapControlsManager.nativeIsTrafficView()) {
            return false;
        } else {
            MapControlsManager.nativeLeaveTrafficView();
            return true;
        }
    }

    public void onRouteCanceled(Boolean user) {
        MapControlsManager.nativeSetNaviType(ENaviType.NtCar);
        NavigationInfoBarScreen.onRouteCanceled();
        NavigationBrowseScreen.onRouteCanceled();
        NavigationQuickMenuScreen.onRouteCanceled();
    }

    public void onTrafficReceived() {
        FreeDriveBrowseScreen.onTrafficReceived();
        NavigationBrowseScreen.onTrafficReceived();
        SelectStartFromMapScreen.onTrafficReceived();
    }

    public void onMapClick() {
        Mode mode = this.mViewAnimator.getDisplayedMode();
        if ((mode.equals(Mode.FREEDRIVE_INFO_BAR) || mode.equals(Mode.FREEDRIVE_QUICK_MENU)) && !MapControlsManager.nativeIsTrafficView() && MapControlsManager.nativeGetMapViewMode() != EMapView.MVCustomPois) {
            if (this.mQuickMenuTimer == null || this.mQuickMenuTimer.isFinished()) {
                this.mQuickMenuTimer = new QuickMenuTimer(getActivity(), true);
                this.mQuickMenuTimer.start();
                SygicHelper.getToolbar().setTag(this.mQuickMenuTimer);
                return;
            }
            this.mQuickMenuTimer.cancel(true);
        }
    }

    public void onVehicleClick(MapSelection mapSelection, String poiDescription) {
        Bundle args = new Bundle();
        args.putParcelable(PoiDetailFragment.POI_SEL, mapSelection);
        args.putString(PoiDetailFragment.POI_TITLE, poiDescription);
        FragmentManagerInterface manager = SygicHelper.getFragmentActivityWrapper();
        if (manager != null) {
            manager.addFragment(PoiDetailFragment.class, "fragment_poi_detail_tag", true, (FragmentResultCallback) manager, args);
        }
    }

    public void onRecomputeStarted() {
        NavigationInfoBarScreen.onInvalidateTrafficDialog();
    }

    public void onRecomputeFinished() {
    }

    public void onConnectionChanged(boolean connected) {
        FragmentManagerInterface manager = SygicHelper.getFragmentActivityWrapper();
        if (manager != null) {
            getFragmentManager().beginTransaction().remove(this).commitAllowingStateLoss();
            manager.addFragment(MapOverlayFragment.class, "fragment_browse_controls_tag", false, null, getArguments());
        }
    }

    public void setShouldIgnoreUnlock(boolean shouldIgnoreUnlock) {
        this.mShouldIgnoreUnlock = shouldIgnoreUnlock;
    }
}
