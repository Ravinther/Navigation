package com.sygic.aura.map.screen;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.ViewAnimator;
import android.widget.ViewSwitcher;
import com.sygic.aura.analytics.AnalyticsInterface.EventType;
import com.sygic.aura.analytics.SygicAnalyticsLogger;
import com.sygic.aura.feature.system.LowSystemFeature.EEventType;
import com.sygic.aura.fragments.interfaces.FragmentManagerInterface;
import com.sygic.aura.helper.Fragments;
import com.sygic.aura.helper.SygicHelper;
import com.sygic.aura.helper.interfaces.ActionControlHolderListener;
import com.sygic.aura.helper.interfaces.DemoListener;
import com.sygic.aura.helper.interfaces.DirectionChangeListener;
import com.sygic.aura.helper.interfaces.JunctionChangeListener;
import com.sygic.aura.helper.interfaces.RouteEventsListener;
import com.sygic.aura.helper.interfaces.RouteWaypointReachedListener;
import com.sygic.aura.helper.interfaces.ScoutComputeListener;
import com.sygic.aura.helper.interfaces.SignpostChangeListener;
import com.sygic.aura.helper.interfaces.SpeedLimitListener;
import com.sygic.aura.license.LicenseInfo;
import com.sygic.aura.map.MapControlsManager;
import com.sygic.aura.map.MapControlsManager.EMapView;
import com.sygic.aura.map.bubble.BubbleManager;
import com.sygic.aura.map.data.SignpostItem;
import com.sygic.aura.map.data.SpeedInfoItem;
import com.sygic.aura.map.data.map_selection.MapSelection;
import com.sygic.aura.map.fragment.MapOverlayFragment.Mode;
import com.sygic.aura.map.notification.NotificationCenterView;
import com.sygic.aura.map.screen.intefaces.InfoBarInterface;
import com.sygic.aura.map.screen.intefaces.SoundMutedListener;
import com.sygic.aura.map.view.MapOverlayAnimator;
import com.sygic.aura.map.view.ModernViewSwitcher;
import com.sygic.aura.navigate.ActionControlFragment;
import com.sygic.aura.navigate.DemoControlsHolder;
import com.sygic.aura.navigate.DirectionsHolder;
import com.sygic.aura.navigate.ParkingActionControlHolder;
import com.sygic.aura.navigate.ScoutActionControlHolder;
import com.sygic.aura.navigate.SignPostHolder;
import com.sygic.aura.navigate.SwitchToPedestrianControlHolder;
import com.sygic.aura.navigate.fragment.NavigateFragment;
import com.sygic.aura.resources.ResourceManager;
import com.sygic.aura.route.DemoManager;
import com.sygic.aura.route.InfoSlotsUpdateTask.SlotHolder;
import com.sygic.aura.route.RouteSummary;
import com.sygic.aura.route.SpeedLimitControl;
import com.sygic.aura.route.data.DirectionStatus;
import com.sygic.aura.route.data.infobar_slots.CurrentSpeedSlot;
import com.sygic.aura.route.data.infobar_slots.DayTimeSlot;
import com.sygic.aura.route.data.infobar_slots.ElevationSlot;
import com.sygic.aura.route.data.infobar_slots.FromStartDistanceSlot;
import com.sygic.aura.route.data.infobar_slots.ProgressBarSlot;
import com.sygic.aura.route.data.infobar_slots.RemainingDistanceSlot;
import com.sygic.aura.route.data.infobar_slots.RemainingTimeSlot;
import com.sygic.aura.route.data.infobar_slots.RouteInfoBarSlot;
import com.sygic.aura.route.data.infobar_slots.Slot;
import com.sygic.aura.route.data.infobar_slots.TimeToEndSlot;
import com.sygic.aura.route.data.infobar_slots.WholeDistanceSlot;
import com.sygic.aura.route.overview.RouteOverviewFragment;
import com.sygic.aura.settings.data.SettingsManager;
import com.sygic.aura.settings.data.SettingsManager.EInfoShowType;
import com.sygic.aura.settings.data.SettingsManager.ESettingsType;
import com.sygic.aura.settings.trial.fragments.PromotionFragment;
import com.sygic.aura.views.WndManager;
import com.sygic.aura.views.font_specials.SImageButton;
import com.sygic.aura.views.font_specials.STextView;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import loquendo.tts.engine.TTSConst;

public class NavigationInfoBarScreen extends OverlayScreen implements ActionControlHolderListener, DemoListener, DirectionChangeListener, JunctionChangeListener, RouteEventsListener, RouteWaypointReachedListener, ScoutComputeListener, SignpostChangeListener, SpeedLimitListener, InfoBarInterface, SoundMutedListener, SlotHolder {
    private ViewAnimator mBottomLeftControls;
    private ModernViewSwitcher mBottomPart;
    private STextView mCurrStreet;
    private DemoControlsHolder mDemoControlsHolder;
    private DirectionsHolder mDirectionsHolder;
    private View mInfoBar;
    private ViewGroup mInfoBarSlots;
    private Map<SlotPosition, Slot> mInfoSlots;
    private boolean mIsTrialBannerInitialized;
    private SImageButton mMutedView;
    private NotificationCenterView mNotificationCenter;
    private ParkingActionControlHolder mParkingActionControlHolder;
    private ProgressBar mProgress;
    private ScoutActionControlHolder mScoutActionControlHolder;
    private SignPostHolder mSignPostHolder;
    private SpeedLimitControl mSpeedLimitHolder;
    private String mStrRecentStreetName;
    private SwitchToPedestrianControlHolder mSwitchToPedeActionControlHolder;
    private View mTrialBarExpired;
    private STextView mTrialBarLeft;
    private View mTrialBarLeftWrapper;
    private STextView mTxtInaccurateSignal;
    private int mnLastPartIndex;

    /* renamed from: com.sygic.aura.map.screen.NavigationInfoBarScreen.1 */
    class C13411 implements OnClickListener {
        C13411() {
        }

        public void onClick(View v) {
            FragmentManagerInterface manager = SygicHelper.getFragmentActivityWrapper();
            if (manager != null) {
                Bundle bundle = new Bundle();
                bundle.putString("class_name", NavigateFragment.class.getName());
                manager.addFragment(RouteOverviewFragment.class, "fragment_route_overview_tag", true, null, bundle);
            }
        }
    }

    /* renamed from: com.sygic.aura.map.screen.NavigationInfoBarScreen.2 */
    class C13422 implements OnClickListener {
        C13422() {
        }

        public void onClick(View v) {
            NavigationInfoBarScreen.this.mTrialBarExpired.setVisibility(8);
            NavigationInfoBarScreen.this.mTrialBarLeftWrapper.setVisibility(0);
            Bundle logParams = new Bundle();
            logParams.putString("eventName", "click");
            logParams.putString("category", "anui_navi_trial_banner_close");
            SygicAnalyticsLogger.logEvent(NavigationInfoBarScreen.this.mFragment.getActivity(), EventType.CLICK, logParams);
        }
    }

    /* renamed from: com.sygic.aura.map.screen.NavigationInfoBarScreen.3 */
    class C13433 implements OnClickListener {
        C13433() {
        }

        public void onClick(View v) {
            NavigationInfoBarScreen.this.showPromotion("nav_go_premium");
            Bundle logParams = new Bundle();
            logParams.putString("eventName", "click");
            logParams.putString("category", "anui_navi_trial_banner_unlock");
            SygicAnalyticsLogger.logEvent(NavigationInfoBarScreen.this.mFragment.getActivity(), EventType.CLICK, logParams);
        }
    }

    /* renamed from: com.sygic.aura.map.screen.NavigationInfoBarScreen.4 */
    class C13444 implements OnClickListener {
        C13444() {
        }

        public void onClick(View v) {
            String category = LicenseInfo.nativeIsTrialExpired() ? "anui_navi_trial_expired" : "anui_navi_trial_left";
            NavigationInfoBarScreen.this.showPromotion(category);
            Bundle logParams = new Bundle();
            logParams.putString("eventName", "click");
            logParams.putString("category", category);
            SygicAnalyticsLogger.logEvent(NavigationInfoBarScreen.this.mFragment.getActivity(), EventType.CLICK, logParams);
        }
    }

    /* renamed from: com.sygic.aura.map.screen.NavigationInfoBarScreen.5 */
    static /* synthetic */ class C13455 {
        static final /* synthetic */ int[] f1263x9d44c129;
        static final /* synthetic */ int[] f1264xd5a24482;
        static final /* synthetic */ int[] f1265x44c2e7da;

        static {
            f1264xd5a24482 = new int[EInfoShowType.values().length];
            try {
                f1264xd5a24482[EInfoShowType.eInfoShowDistToEnd.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                f1264xd5a24482[EInfoShowType.eInfoShowCurrentSpeed.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                f1264xd5a24482[EInfoShowType.eInfoShowTimeOfArrival.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
            try {
                f1264xd5a24482[EInfoShowType.eInfoShowElevation.ordinal()] = 4;
            } catch (NoSuchFieldError e4) {
            }
            try {
                f1264xd5a24482[EInfoShowType.eInfoShowTimeOfDay.ordinal()] = 5;
            } catch (NoSuchFieldError e5) {
            }
            try {
                f1264xd5a24482[EInfoShowType.eInfoShowDistAll.ordinal()] = 6;
            } catch (NoSuchFieldError e6) {
            }
            try {
                f1264xd5a24482[EInfoShowType.eInfoShowDistFromStart.ordinal()] = 7;
            } catch (NoSuchFieldError e7) {
            }
            try {
                f1264xd5a24482[EInfoShowType.eInfoShowTimeToEnd.ordinal()] = 8;
            } catch (NoSuchFieldError e8) {
            }
            try {
                f1264xd5a24482[EInfoShowType.eInfoShowNone.ordinal()] = 9;
            } catch (NoSuchFieldError e9) {
            }
            f1263x9d44c129 = new int[SlotPosition.values().length];
            try {
                f1263x9d44c129[SlotPosition.FIRST.ordinal()] = 1;
            } catch (NoSuchFieldError e10) {
            }
            try {
                f1263x9d44c129[SlotPosition.SECOND.ordinal()] = 2;
            } catch (NoSuchFieldError e11) {
            }
            try {
                f1263x9d44c129[SlotPosition.THIRD.ordinal()] = 3;
            } catch (NoSuchFieldError e12) {
            }
            f1265x44c2e7da = new int[ESettingsType.values().length];
            try {
                f1265x44c2e7da[ESettingsType.ePedSlot1.ordinal()] = 1;
            } catch (NoSuchFieldError e13) {
            }
            try {
                f1265x44c2e7da[ESettingsType.eCarSlot1.ordinal()] = 2;
            } catch (NoSuchFieldError e14) {
            }
            try {
                f1265x44c2e7da[ESettingsType.ePedSlot2.ordinal()] = 3;
            } catch (NoSuchFieldError e15) {
            }
            try {
                f1265x44c2e7da[ESettingsType.eCarSlot2.ordinal()] = 4;
            } catch (NoSuchFieldError e16) {
            }
            try {
                f1265x44c2e7da[ESettingsType.ePedSlot3.ordinal()] = 5;
            } catch (NoSuchFieldError e17) {
            }
            try {
                f1265x44c2e7da[ESettingsType.eCarSlot3.ordinal()] = 6;
            } catch (NoSuchFieldError e18) {
            }
        }
    }

    public enum SlotPosition {
        PROGRESS,
        FIRST,
        SECOND,
        THIRD;

        public String getId() {
            return "routeInfoBarSlot" + ordinal();
        }
    }

    public NavigationInfoBarScreen() {
        this.mIsTrialBannerInitialized = false;
    }

    public static SlotHolder getInstance() {
        return (SlotHolder) OverlayScreen.getInstance(Mode.NAVIGATE_INFO_BAR);
    }

    protected void setupChildScreen(View rootView) {
        if (rootView != null) {
            int i;
            ((SImageButton) rootView.findViewById(2131624439)).setOnClickListener(new C13411());
            View mTopRow = rootView.findViewById(2131624433);
            this.mBottomPart = (ModernViewSwitcher) rootView.findViewById(2131624434);
            this.mBottomLeftControls = (ViewAnimator) rootView.findViewById(2131624440);
            initSignpostLayout(mTopRow);
            initDirectionsLayout(mTopRow);
            initSpeedLimitControl((ViewSwitcher) rootView.findViewById(2131624599));
            initTrafficDialogLayout();
            this.mCurrStreet = (STextView) rootView.findViewById(2131624418);
            STextView sTextView = this.mCurrStreet;
            if (this.mFragment.getOrientation() == 1) {
                i = 2;
            } else {
                i = 1;
            }
            sTextView.setMaxLines(i);
            initDemoControlsLayout(rootView);
            this.mInfoBar = rootView.findViewById(2131624419);
            this.mInfoBarSlots = (ViewGroup) this.mInfoBar.findViewById(2131624423);
            this.mTxtInaccurateSignal = (STextView) this.mInfoBar.findViewById(2131624422);
            this.mTxtInaccurateSignal.setText(ResourceManager.getCoreString(this.mContext, 2131165490));
            this.mInfoSlots = new HashMap(3);
            ProgressBarSlot progressSlot = new ProgressBarSlot();
            this.mProgress = (ProgressBar) rootView.findViewById(2131624435);
            progressSlot.setView(this.mProgress);
            this.mInfoSlots.put(SlotPosition.PROGRESS, progressSlot);
            setupSlotsInternal();
            initTrialBanner(rootView);
            MapOverlayAnimator animator = this.mFragment.getAnimator();
            animator.registerViewForTranslateAnimationByY(this.mInfoBar, Mode.NAVIGATE_INFO_BAR);
            animator.registerViewForTranslateAnimationByY(mTopRow, Mode.NAVIGATE_INFO_BAR, true);
            animator.registerViewForAnimation(this.mCurrStreet, Mode.NAVIGATE_INFO_BAR, "alpha", 0.0f, 1.0f);
            animator.registerViewForAnimation(this.mSpeedLimitHolder.getView(), Mode.NAVIGATE_INFO_BAR, "alpha", 0.0f, 1.0f);
            View[] views = new View[1];
            setupBlackboxMaximize(rootView, views);
            animator.registerViewsForAnimation(views, Mode.NAVIGATE_INFO_BAR, "alpha", 0.0f, 1.0f);
            setupMutedControl(rootView, views);
            animator.registerViewForAnimation(views[0], Mode.NAVIGATE_INFO_BAR, "alpha", 0.0f, 1.0f);
            setupReportIncident(rootView);
            setupBottomLeftControls(rootView);
            this.mMutedView = (SImageButton) rootView.findViewById(2131624386);
            this.mNotificationCenter = (NotificationCenterView) rootView.findViewById(2131624420);
            animator.registerViewForAnimation(this.mNotificationCenter, Mode.NAVIGATE_INFO_BAR, "alpha", 0.0f, 1.0f);
        }
    }

    protected void onScreenEntered() {
        getToolbar().hide();
        MapControlsManager.nativeSetMapViewMode(EMapView.MVNavScreen);
        this.mNotificationCenter.setActive(true);
    }

    protected void onscreenLeft() {
        getToolbar().show();
        this.mNotificationCenter.setActive(false);
    }

    public static void onConfigurationChanged(int orientation) {
        int i = 1;
        NavigationInfoBarScreen screen = (NavigationInfoBarScreen) OverlayScreen.getInstance(Mode.NAVIGATE_INFO_BAR);
        if (screen != null) {
            STextView sTextView = screen.mCurrStreet;
            if (orientation == 1) {
                i = 2;
            }
            sTextView.setMaxLines(i);
            screen.setCurrentStreet(screen.mStrRecentStreetName);
            screen.mDemoControlsHolder.onConfigurationChanged(orientation);
            screen.mSignPostHolder.onConfigurationChanged(orientation);
            screen.mDirectionsHolder.onConfigurationChanged(orientation);
            screen.mScoutActionControlHolder.onConfigurationChanged();
            screen.mParkingActionControlHolder.onConfigurationChanged();
            screen.mSwitchToPedeActionControlHolder.onConfigurationChanged();
            screen.setupSlotsInternal();
        }
    }

    public static void onLanguageChanged(String newLang) {
        NavigationInfoBarScreen screen = (NavigationInfoBarScreen) OverlayScreen.getInstance(Mode.NAVIGATE_INFO_BAR);
        if (screen != null && screen.mTxtInaccurateSignal != null) {
            screen.mTxtInaccurateSignal.setText(ResourceManager.getCoreString(screen.mContext, 2131165490));
            screen.mScoutActionControlHolder.onLanguageChanged();
            screen.mParkingActionControlHolder.onLanguageChanged();
            screen.mSwitchToPedeActionControlHolder.onLanguageChanged();
        }
    }

    public static void onLicenseUpdated() {
        NavigationInfoBarScreen screen = (NavigationInfoBarScreen) OverlayScreen.getInstance(Mode.NAVIGATE_INFO_BAR);
        if (screen != null) {
            if (LicenseInfo.nativeIsTrial()) {
                screen.initTrialBanner(screen.getInfoBar().getRootView());
            } else {
                screen.hideTrialBanner();
            }
        }
    }

    private void initSpeedLimitControl(ViewSwitcher view) {
        this.mSpeedLimitHolder = new SpeedLimitControl(view);
    }

    private void initSignpostLayout(View view) {
        ViewGroup signpost = (ViewGroup) view.findViewById(2131624481);
        if (this.mSignPostHolder == null) {
            this.mSignPostHolder = new SignPostHolder(signpost, this.mContext);
        }
    }

    private void initDirectionsLayout(View view) {
        ViewGroup direction = (ViewGroup) view.findViewById(2131624388);
        if (this.mDirectionsHolder == null) {
            this.mDirectionsHolder = new DirectionsHolder(direction, this.mContext);
        }
    }

    private void initTrafficDialogLayout() {
        ViewGroup trafficView = (ViewGroup) this.mBottomPart.findViewById(2131624441);
        if (this.mScoutActionControlHolder == null) {
            this.mScoutActionControlHolder = new ScoutActionControlHolder(this.mBottomPart, trafficView);
        }
        ViewGroup parkingView = (ViewGroup) this.mBottomPart.findViewById(2131624442);
        if (this.mParkingActionControlHolder == null) {
            this.mParkingActionControlHolder = new ParkingActionControlHolder(this.mBottomPart, parkingView);
        }
        ViewGroup switchToPedestrianView = (ViewGroup) this.mBottomPart.findViewById(2131624443);
        if (switchToPedestrianView != null) {
            this.mSwitchToPedeActionControlHolder = new SwitchToPedestrianControlHolder(this.mBottomPart, switchToPedestrianView);
        }
    }

    public static void setupSlots() {
        NavigationInfoBarScreen screen = (NavigationInfoBarScreen) OverlayScreen.getInstance(Mode.NAVIGATE_INFO_BAR);
        if (screen != null) {
            screen.setupSlotsInternal();
        }
    }

    public static void onRoutePartChange(int index) {
        boolean isStreetValid = false;
        NavigationInfoBarScreen screen = (NavigationInfoBarScreen) OverlayScreen.getInstance(Mode.NAVIGATE_INFO_BAR);
        if (screen != null && screen.mnLastPartIndex != index) {
            screen.mnLastPartIndex = index;
            if (SettingsManager.nativeGetSettings(ESettingsType.eStreets) == 0) {
                ResourceManager.makeControlVisible(screen.mCurrStreet, false, true);
                return;
            }
            String strStreetName = MapControlsManager.nativeGetCurrentStreetName();
            if (!TextUtils.isEmpty(strStreetName)) {
                isStreetValid = true;
            }
            ResourceManager.makeControlVisible(screen.mCurrStreet, isStreetValid, true);
            if (isStreetValid && !strStreetName.equals(screen.mStrRecentStreetName)) {
                screen.mStrRecentStreetName = strStreetName;
                screen.setCurrentStreet(strStreetName);
            }
        }
    }

    public static void onDayNightModeChange(boolean isNightModeOn) {
        NavigationInfoBarScreen screen = (NavigationInfoBarScreen) OverlayScreen.getInstance(Mode.NAVIGATE_INFO_BAR);
        if (screen != null) {
            screen.mDirectionsHolder.onDayNightModeChanged(isNightModeOn);
        }
    }

    private void setCurrentStreet(String strStreetName) {
        configureCurrentStreet(this.mCurrStreet, strStreetName, this.mFragment.getOrientation());
    }

    static void configureCurrentStreet(TextView view, String text, int orientation) {
        if (text != null) {
            if (orientation == 1) {
                String[] splitText = text.split("\\|");
                if (splitText.length > 1) {
                    view.setText(splitText[0].concat("\n").concat(splitText[1]));
                    return;
                } else {
                    view.setText(splitText[0]);
                    return;
                }
            }
            view.setText(text.replace("|", ", "));
        }
    }

    private void setupSlotsInternal() {
        if (this.mInfoBarSlots != null) {
            LayoutInflater inflater = LayoutInflater.from(this.mContext);
            boolean isPedestrian = RouteSummary.nativeIsPedestrian();
            putSlotAtPosition(this.mInfoBarSlots, inflater, getSlotFromSettings(SlotPosition.FIRST, isPedestrian), SlotPosition.FIRST);
            putSlotAtPosition(this.mInfoBarSlots, inflater, getSlotFromSettings(SlotPosition.SECOND, isPedestrian), SlotPosition.SECOND);
            putSlotAtPosition(this.mInfoBarSlots, inflater, getSlotFromSettings(SlotPosition.THIRD, isPedestrian), SlotPosition.THIRD);
        }
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static void changeSlot(com.sygic.aura.settings.data.SettingsManager.ESettingsType r7, int r8) {
        /*
        r5 = com.sygic.aura.map.fragment.MapOverlayFragment.Mode.NAVIGATE_INFO_BAR;
        r4 = com.sygic.aura.map.screen.OverlayScreen.getInstance(r5);
        r4 = (com.sygic.aura.map.screen.NavigationInfoBarScreen) r4;
        if (r4 == 0) goto L_0x001f;
    L_0x000a:
        r1 = 0;
        r5 = com.sygic.aura.map.screen.NavigationInfoBarScreen.C13455.f1265x44c2e7da;
        r6 = r7.ordinal();
        r5 = r5[r6];
        switch(r5) {
            case 1: goto L_0x0020;
            case 2: goto L_0x0021;
            case 3: goto L_0x0039;
            case 4: goto L_0x003a;
            case 5: goto L_0x003d;
            case 6: goto L_0x003e;
            default: goto L_0x0016;
        };
    L_0x0016:
        r5 = "NavigationInfoBarScreen";
        r6 = "Wrong slot position";
        android.util.Log.w(r5, r6);
    L_0x001f:
        return;
    L_0x0020:
        r1 = 1;
    L_0x0021:
        r3 = com.sygic.aura.map.screen.NavigationInfoBarScreen.SlotPosition.FIRST;
    L_0x0023:
        r2 = com.sygic.aura.route.RouteSummary.nativeIsPedestrian();
        if (r1 != r2) goto L_0x001f;
    L_0x0029:
        r5 = r4.mContext;
        r0 = android.view.LayoutInflater.from(r5);
        r5 = r4.mInfoBarSlots;
        r6 = r4.getSlot(r8);
        r4.putSlotAtPosition(r5, r0, r6, r3);
        goto L_0x001f;
    L_0x0039:
        r1 = 1;
    L_0x003a:
        r3 = com.sygic.aura.map.screen.NavigationInfoBarScreen.SlotPosition.SECOND;
        goto L_0x0023;
    L_0x003d:
        r1 = 1;
    L_0x003e:
        r3 = com.sygic.aura.map.screen.NavigationInfoBarScreen.SlotPosition.THIRD;
        goto L_0x0023;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.sygic.aura.map.screen.NavigationInfoBarScreen.changeSlot(com.sygic.aura.settings.data.SettingsManager$ESettingsType, int):void");
    }

    private synchronized void putSlotAtPosition(View parent, LayoutInflater inflater, Slot slot, SlotPosition position) {
        if (this.mInfoSlots.get(position) != null) {
            ((Slot) this.mInfoSlots.get(position)).removeView();
        }
        if (slot != null) {
            this.mInfoSlots.put(position, slot);
            addViewToSlot(parent, slot.getView(inflater), position.getId());
        }
    }

    private RouteInfoBarSlot getSlotFromSettings(SlotPosition position, boolean forPedestrian) {
        int slotType;
        switch (C13455.f1263x9d44c129[position.ordinal()]) {
            case TTSConst.TTSMULTILINE /*1*/:
                ESettingsType eSettingsType;
                if (forPedestrian) {
                    eSettingsType = ESettingsType.ePedSlot1;
                } else {
                    eSettingsType = ESettingsType.eCarSlot1;
                }
                slotType = SettingsManager.nativeGetSettings(eSettingsType);
                break;
            case TTSConst.TTSPARAGRAPH /*2*/:
                slotType = SettingsManager.nativeGetSettings(forPedestrian ? ESettingsType.ePedSlot2 : ESettingsType.eCarSlot2);
                break;
            case TTSConst.TTSUNICODE /*3*/:
                slotType = SettingsManager.nativeGetSettings(forPedestrian ? ESettingsType.ePedSlot3 : ESettingsType.eCarSlot3);
                break;
            default:
                return null;
        }
        return getSlot(slotType);
    }

    private RouteInfoBarSlot getSlot(int slotType) {
        switch (C13455.f1264xd5a24482[EInfoShowType.fromInt(slotType).ordinal()]) {
            case TTSConst.TTSMULTILINE /*1*/:
                return new RemainingDistanceSlot();
            case TTSConst.TTSPARAGRAPH /*2*/:
                return new CurrentSpeedSlot();
            case TTSConst.TTSUNICODE /*3*/:
                return new RemainingTimeSlot();
            case TTSConst.TTSXML /*4*/:
                return new ElevationSlot();
            case TTSConst.TTSEVT_TEXT /*5*/:
                return new DayTimeSlot();
            case TTSConst.TTSEVT_SENTENCE /*6*/:
                return new WholeDistanceSlot();
            case TTSConst.TTSEVT_BOOKMARK /*7*/:
                return new FromStartDistanceSlot();
            case TTSConst.TTSEVT_TAG /*8*/:
                return new TimeToEndSlot();
            default:
                return null;
        }
    }

    protected static void addViewToSlot(View parent, View view, String slotPositionId) {
        int slotResId = getSlotId(parent.getContext(), slotPositionId);
        if (slotResId != 0) {
            ((ViewGroup) parent.findViewById(slotResId)).addView(view, -1, -1);
        }
    }

    private static int getSlotId(Context context, String slot) {
        return context.getResources().getIdentifier(slot, "id", context.getPackageName());
    }

    private void hideTrialBanner() {
        if (this.mTrialBarExpired != null) {
            this.mTrialBarExpired.setVisibility(8);
        }
        if (this.mTrialBarLeftWrapper != null) {
            this.mTrialBarLeftWrapper.setVisibility(8);
        }
    }

    private void initTrialBanner(View view) {
        if (LicenseInfo.nativeIsTrial() && !this.mIsTrialBannerInitialized) {
            this.mIsTrialBannerInitialized = true;
            this.mTrialBarExpired = view.findViewById(2131624498);
            this.mTrialBarLeftWrapper = view.findViewById(2131624431);
            this.mTrialBarLeft = (STextView) this.mTrialBarLeftWrapper.findViewById(2131624432);
            if (LicenseInfo.nativeIsTrialExpired()) {
                this.mTrialBarLeft.setText(ResourceManager.getCoreString(this.mContext, 2131165981));
                this.mTrialBarExpired.setVisibility(0);
            } else {
                this.mTrialBarLeftWrapper.setVisibility(0);
                this.mTrialBarLeft.setBackgroundResource(2131558453);
                this.mTrialBarLeft.setText(((String) this.mTrialBarLeft.getText()).replace("%days%", Integer.toString(LicenseInfo.nativeGetTrialDays())));
            }
            this.mTrialBarExpired.findViewById(2131624499).setOnClickListener(new C13422());
            this.mTrialBarExpired.findViewById(2131624501).setOnClickListener(new C13433());
            this.mTrialBarLeftWrapper.setOnClickListener(new C13444());
        }
    }

    private void showPromotion(String source) {
        Bundle params = new Bundle(1);
        params.putString("source", source);
        Fragments.add(this.mFragment.getActivity(), PromotionFragment.class, "fragment_product_speedcam", params);
    }

    private void initDemoControlsLayout(View view) {
        ViewGroup demoControls = (ViewGroup) view.findViewById(2131624174);
        if (this.mDemoControlsHolder == null) {
            this.mDemoControlsHolder = new DemoControlsHolder(demoControls, view.getContext());
        }
    }

    private void setDemoControlsVisible(boolean visible) {
        this.mDemoControlsHolder.setDemoControlVisible(visible);
    }

    public void cancelDemo() {
        this.mDemoControlsHolder.cancelDemo();
    }

    public void onDemoStarted() {
        this.mBottomLeftControls.setDisplayedChild(1);
        this.mDemoControlsHolder.setStartPauseButton(true);
    }

    public void onDemoStoped() {
        this.mBottomLeftControls.setDisplayedChild(0);
        this.mDemoControlsHolder.setStartPauseButton(false);
    }

    public synchronized Collection<Slot> getSlots() {
        return this.mInfoSlots.values();
    }

    public static void onRouteCanceled() {
        NavigationInfoBarScreen screen = (NavigationInfoBarScreen) OverlayScreen.getInstance(Mode.NAVIGATE_INFO_BAR);
        if (screen != null) {
            screen.setDemoControlsVisible(false);
            screen.mSignPostHolder.setSignpostPlateVisible(false);
            screen.mDirectionsHolder.setDirectionsControlsVisible(false);
            screen.setupSlotsInternal();
        }
    }

    public View getInfoBar() {
        return this.mInfoBar;
    }

    public View getProgress() {
        return this.mProgress;
    }

    public View getInfoBarSlots() {
        return this.mInfoBarSlots;
    }

    public View getSignalView() {
        return this.mTxtInaccurateSignal;
    }

    public void onSignpostChange(ArrayList<SignpostItem> signpostItems) {
        if (signpostItems != null && this.mSignPostHolder != null) {
            this.mSignPostHolder.updateSignPostItems((SignpostItem[]) signpostItems.toArray(new SignpostItem[signpostItems.size()]));
        }
    }

    public void onSpeedLimitChanged(SpeedInfoItem item) {
        this.mSpeedLimitHolder.updateSpeedLimit(item);
    }

    public void onDirectionChange(DirectionStatus direction) {
        if (direction != null && this.mDirectionsHolder != null) {
            this.mDirectionsHolder.updateDirections(direction);
        }
    }

    public void onJunctionViewChanged(Boolean shown) {
        boolean z;
        boolean z2 = true;
        BubbleManager instance = BubbleManager.getInstance();
        if (shown.booleanValue()) {
            z = false;
        } else {
            z = true;
        }
        instance.setTrafficBubblesVisible(z);
        View view = this.mBottomPart;
        if (shown.booleanValue()) {
            z2 = false;
        }
        ResourceManager.makeControlVisible(view, z2, false);
    }

    public void onStartComputingProgress() {
    }

    public void onFinishComputingProgress() {
    }

    public void onRouteComputeFinishedAll() {
        setDemoControlsVisible(DemoManager.nativeIsDemoPaused());
    }

    public void onScoutComputeRouteReady(Integer nTimeDiff, Integer nDistanceDiff) {
        showAlternativeTraffic(nTimeDiff.intValue(), nDistanceDiff.intValue());
        String params = (("timeDiff:" + nTimeDiff) + ":distanceDiff:" + nDistanceDiff) + ":" + WndManager.nativeGetLogParams();
        Bundle logParams = new Bundle();
        logParams.putString("eventName", "scout_compute_new_alternative");
        logParams.putString("coreParams", params);
        logParams.putSerializable("eventType", EEventType.ETNone);
        SygicAnalyticsLogger.logEvent(this.mFragment.getActivity(), EventType.CORE, logParams);
    }

    private void showAlternativeTraffic(int time, int distance) {
        if (this.mBottomPart.getCurrentIndex() == 0) {
            this.mScoutActionControlHolder.show(time, distance);
        }
    }

    public static void onInvalidateTrafficDialog() {
        NavigationInfoBarScreen screen = (NavigationInfoBarScreen) OverlayScreen.getInstance(Mode.NAVIGATE_INFO_BAR);
        if (screen != null) {
            screen.mScoutActionControlHolder.invalidateTrafficDialog();
        }
    }

    public void onMuteChanged(boolean isMuted) {
        this.mMutedView.setVisibility(!isMuted ? 8 : 0);
    }

    public static void showPoiOnRouteNotifications(boolean show) {
        NavigationInfoBarScreen screen = (NavigationInfoBarScreen) OverlayScreen.getInstance(Mode.NAVIGATE_INFO_BAR);
        if (screen == null) {
            return;
        }
        if (show) {
            screen.mNotificationCenter.addAcceptance(16);
        } else {
            screen.mNotificationCenter.removeAcceptance(16);
        }
    }

    public void onWaypointReached() {
        if (RouteSummary.nativeIsLastWaypointParking()) {
            this.mSwitchToPedeActionControlHolder.show();
        }
    }

    public void onLastMileParkingAvailable(MapSelection mapSel, String title, String icon) {
        this.mParkingActionControlHolder.show();
    }

    public void onShowParkingPlaces(MapSelection mapSel, String title, String icon) {
        FragmentManagerInterface manager = SygicHelper.getFragmentActivityWrapper();
        if (manager != null) {
            Bundle args = new Bundle();
            args.putParcelable("mapsel", mapSel);
            args.putString("icon_char", icon);
            args.putParcelable("mode", ActionControlFragment.Mode.PARKING);
            manager.replaceFragment(ActionControlFragment.class, "action_control", true, args);
        }
    }
}
