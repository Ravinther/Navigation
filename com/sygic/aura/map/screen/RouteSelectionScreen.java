package com.sygic.aura.map.screen;

import android.os.Build.VERSION;
import android.os.Bundle;
import android.os.Handler;
import android.transition.AutoTransition;
import android.transition.Transition;
import android.transition.Transition.TransitionListener;
import android.transition.TransitionManager;
import android.transition.TransitionSet;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import com.sygic.aura.analytics.AnalyticsInterface.EventType;
import com.sygic.aura.analytics.SygicAnalyticsLogger;
import com.sygic.aura.fragments.interfaces.FragmentManagerInterface;
import com.sygic.aura.helper.FlurryHelper;
import com.sygic.aura.helper.SwitchViewHelper;
import com.sygic.aura.helper.SwitchViewHelper.SwitchViewListener;
import com.sygic.aura.helper.SygicHelper;
import com.sygic.aura.helper.interfaces.ConnectionChangeListener;
import com.sygic.aura.helper.interfaces.SpeedCamsReadyListener;
import com.sygic.aura.helper.interfaces.TrafficChangeListener;
import com.sygic.aura.license.LicenseInfo;
import com.sygic.aura.map.MapControlsManager;
import com.sygic.aura.map.MapControlsManager.EMapView;
import com.sygic.aura.map.bubble.BubbleManager;
import com.sygic.aura.map.fragment.MapOverlayFragment.Mode;
import com.sygic.aura.network.ConnectionManager;
import com.sygic.aura.resources.ResourceManager;
import com.sygic.aura.route.RouteManager;
import com.sygic.aura.route.RouteManager.RouteComputeMode;
import com.sygic.aura.route.data.SpeedCamItem;
import com.sygic.aura.route.data.TrafficItem;
import com.sygic.aura.route.fragment.RouteOverviewFragmentResultCallback;
import com.sygic.aura.route.fragment.RouteSelectionFragment;
import com.sygic.aura.route.overview.RouteOverviewFragment;
import com.sygic.aura.route.overview.RouteOverviewFragment.Filter;
import com.sygic.aura.views.font_specials.SImageButton;
import com.sygic.aura.views.font_specials.SImageView;
import com.sygic.aura.views.font_specials.STextView;
import com.sygic.aura.views.font_specials.SToggleButton;
import java.util.ArrayList;
import java.util.HashMap;

public class RouteSelectionScreen extends OverlayScreen implements ConnectionChangeListener, SpeedCamsReadyListener, TrafficChangeListener, RouteOverviewFragmentResultCallback {
    private SImageButton mBtnRouteOverview;
    private long mDelay;
    private View mDriveWalkContainer;
    private boolean mHasSpeedcamLicense;
    private boolean mHasTrafficLicense;
    private boolean mIsOnline;
    private ViewGroup mRightContainer;
    private View mSpeedcamContainer;
    private int mSpeedcamCount;
    private SImageView mSpeedcamImageView;
    private SImageView mSpeedcamLock;
    private ProgressBar mSpeedcamProgressBar;
    private STextView mSpeedcamValueTextView;
    private SwitchViewHelper<SToggleButton> mSwitchViewHelper;
    private TrafficItem mTraffic;
    private View mTrafficContainer;
    private SImageView mTrafficImageView;
    private SImageView mTrafficLock;
    private ProgressBar mTrafficProgressBar;
    private STextView mTrafficValueTextView;
    private boolean mTransitionRunning;

    /* renamed from: com.sygic.aura.map.screen.RouteSelectionScreen.1 */
    class C13541 implements SwitchViewListener {
        C13541() {
        }

        public void onSelectionChanged(RouteComputeMode mode) {
            RouteManager.nativeRouteRecompute(mode);
            NavigationInfoBarScreen.setupSlots();
        }
    }

    /* renamed from: com.sygic.aura.map.screen.RouteSelectionScreen.2 */
    class C13552 implements OnClickListener {
        C13552() {
        }

        public void onClick(View v) {
            Bundle logParams = new Bundle();
            logParams.putString("eventName", "Route selection to overview");
            logParams.putSerializable("attributes", RouteSelectionScreen.this.prepareLogEventForCategory("all"));
            SygicAnalyticsLogger.logEvent(RouteSelectionScreen.this.mFragment.getActivity(), EventType.LIVE_SERVICES, logParams);
            RouteSelectionScreen.this.goToRouteOverview(Filter.ALL);
        }
    }

    /* renamed from: com.sygic.aura.map.screen.RouteSelectionScreen.3 */
    class C13563 implements OnClickListener {
        C13563() {
        }

        public void onClick(View v) {
            Bundle logParams = new Bundle();
            logParams.putString("eventName", "Route selection to overview");
            logParams.putSerializable("attributes", RouteSelectionScreen.this.prepareLogEventForCategory("traffic"));
            SygicAnalyticsLogger.logEvent(RouteSelectionScreen.this.mFragment.getActivity(), EventType.LIVE_SERVICES, logParams);
            RouteSelectionScreen.this.goToRouteOverview(Filter.TRAFFIC);
        }
    }

    /* renamed from: com.sygic.aura.map.screen.RouteSelectionScreen.4 */
    class C13574 implements OnClickListener {
        C13574() {
        }

        public void onClick(View v) {
            Bundle logParams = new Bundle();
            logParams.putString("eventName", "Route selection to overview");
            logParams.putSerializable("attributes", RouteSelectionScreen.this.prepareLogEventForCategory("speedcams"));
            SygicAnalyticsLogger.logEvent(RouteSelectionScreen.this.mFragment.getActivity(), EventType.LIVE_SERVICES, logParams);
            RouteSelectionScreen.this.goToRouteOverview(Filter.SPEEDCAMS);
        }
    }

    /* renamed from: com.sygic.aura.map.screen.RouteSelectionScreen.5 */
    class C13585 implements Runnable {
        C13585() {
        }

        public void run() {
            RouteSelectionScreen.this.updateRightContainer();
        }
    }

    /* renamed from: com.sygic.aura.map.screen.RouteSelectionScreen.6 */
    class C13596 implements TransitionListener {
        C13596() {
        }

        public void onTransitionStart(Transition transition) {
            RouteSelectionScreen.this.mTransitionRunning = true;
        }

        public void onTransitionEnd(Transition transition) {
            RouteSelectionScreen.this.mTransitionRunning = false;
        }

        public void onTransitionCancel(Transition transition) {
        }

        public void onTransitionPause(Transition transition) {
        }

        public void onTransitionResume(Transition transition) {
        }
    }

    protected void setupChildScreen(View rootView) {
        if (rootView != null) {
            setupRotationLock(rootView);
            SToggleButton btnDrive = (SToggleButton) rootView.findViewById(2131624449);
            SToggleButton btnWalk = (SToggleButton) rootView.findViewById(2131624448);
            this.mRightContainer = (ViewGroup) rootView.findViewById(2131624450);
            this.mDriveWalkContainer = rootView.findViewById(2131624447);
            this.mSwitchViewHelper = new SwitchViewHelper(new C13541());
            this.mSwitchViewHelper.add(RouteComputeMode.MODE_CAR, btnDrive);
            this.mSwitchViewHelper.add(RouteComputeMode.MODE_PEDESTRIAN, btnWalk);
            this.mSwitchViewHelper.setSelected(RouteComputeMode.MODE_CAR);
            this.mBtnRouteOverview = (SImageButton) rootView.findViewById(2131624439);
            this.mBtnRouteOverview.setOnClickListener(new C13552());
            this.mTrafficContainer = rootView.findViewById(2131624451);
            this.mTrafficContainer.setOnClickListener(new C13563());
            this.mTrafficValueTextView = (STextView) rootView.findViewById(2131624453);
            this.mTrafficImageView = (SImageView) rootView.findViewById(2131624452);
            this.mTrafficLock = (SImageView) rootView.findViewById(2131624454);
            this.mTrafficProgressBar = (ProgressBar) rootView.findViewById(2131624455);
            this.mSpeedcamContainer = rootView.findViewById(2131624456);
            this.mSpeedcamContainer.setOnClickListener(new C13574());
            this.mSpeedcamValueTextView = (STextView) rootView.findViewById(2131624458);
            this.mSpeedcamImageView = (SImageView) rootView.findViewById(2131624457);
            this.mSpeedcamLock = (SImageView) rootView.findViewById(2131624459);
            this.mSpeedcamProgressBar = (ProgressBar) rootView.findViewById(2131624460);
            this.mTraffic = null;
            this.mSpeedcamCount = -1;
            this.mHasTrafficLicense = LicenseInfo.nativeHasTrafficLicense();
            this.mHasSpeedcamLicense = LicenseInfo.nativeHasSpeedcamLicense();
            this.mIsOnline = ConnectionManager.nativeIsConnected();
            updateRightContainer();
        }
    }

    private HashMap<String, Object> prepareLogEventForCategory(String category) {
        HashMap<String, Object> attributes = new HashMap();
        attributes.put("category", category);
        attributes.put("all", getLicenceStatus());
        FlurryHelper.addDefaultParams(attributes);
        return attributes;
    }

    protected void onScreenEntered() {
        BubbleManager.getInstance().setTrafficBubblesVisible(false);
        MapControlsManager.nativeSetMapViewMode(EMapView.MVRouteSelection);
    }

    protected void onscreenLeft() {
        BubbleManager.getInstance().setTrafficBubblesVisible(true);
    }

    private String getLicenceStatus() {
        if (LicenseInfo.nativeIsTrial() && !LicenseInfo.nativeIsTrialExpired()) {
            return "trial_valid";
        }
        if (LicenseInfo.nativeIsTrialExpired()) {
            return "trial_expired";
        }
        return "paid";
    }

    private void goToRouteOverview(Filter filter) {
        FragmentManagerInterface fmi = SygicHelper.getFragmentActivityWrapper();
        if (fmi != null) {
            Bundle args = new Bundle();
            args.putString("class_name", RouteSelectionFragment.class.getName());
            args.putSerializable("filter", filter);
            args.putParcelable("traffic_delay", this.mTraffic);
            fmi.addFragment(RouteOverviewFragment.class, "fragment_route_overview_tag", true, this, args);
        }
    }

    public static void setVehicleMode(RouteComputeMode routeComputeMode) {
        RouteSelectionScreen screen = (RouteSelectionScreen) OverlayScreen.getInstance(Mode.ROUTE_SELECTION);
        if (screen != null && screen.mSwitchViewHelper != null) {
            screen.mSwitchViewHelper.setSelected(routeComputeMode);
        }
    }

    public static void onStartComputingProgress() {
        RouteSelectionScreen screen = (RouteSelectionScreen) OverlayScreen.getInstance(Mode.ROUTE_SELECTION);
        if (screen != null && screen.mSwitchViewHelper != null && screen.mBtnRouteOverview != null) {
            screen.mDriveWalkContainer.setEnabled(false);
            screen.mSwitchViewHelper.setEnabled(false);
            screen.mBtnRouteOverview.setEnabled(false);
            screen.mRightContainer.setEnabled(false);
        }
    }

    private void updateRightContainer() {
        if (this.mRightContainer != null) {
            if (VERSION.SDK_INT >= 19) {
                if (this.mTransitionRunning) {
                    new Handler().postDelayed(new C13585(), this.mDelay);
                    return;
                }
                TransitionSet transition = new AutoTransition();
                this.mDelay = transition.getDuration();
                transition.addListener(new C13596());
                TransitionManager.beginDelayedTransition(this.mRightContainer, transition);
            }
            updateTrafficContainer();
            updateSpeedcamContainer();
        }
    }

    private void updateSpeedcamContainer() {
        if (this.mIsOnline) {
            this.mSpeedcamImageView.setFontDrawableColor(this.mContext.getResources().getColor(2131558632));
            if (this.mHasSpeedcamLicense) {
                this.mSpeedcamLock.setVisibility(8);
                if (this.mSpeedcamCount == -1) {
                    this.mSpeedcamProgressBar.setVisibility(0);
                    this.mSpeedcamValueTextView.setVisibility(8);
                    return;
                }
                this.mSpeedcamProgressBar.setVisibility(8);
                this.mSpeedcamValueTextView.setVisibility(0);
                this.mSpeedcamValueTextView.setText(this.mSpeedcamCount + "x");
                return;
            }
            this.mSpeedcamLock.setVisibility(0);
            this.mSpeedcamProgressBar.setVisibility(8);
            this.mSpeedcamValueTextView.setVisibility(8);
            return;
        }
        this.mSpeedcamImageView.setFontDrawableColor(this.mContext.getResources().getColor(2131558495));
        this.mSpeedcamLock.setVisibility(8);
        this.mSpeedcamProgressBar.setVisibility(8);
        this.mSpeedcamValueTextView.setVisibility(8);
    }

    private void updateTrafficContainer() {
        if (this.mIsOnline) {
            this.mTrafficImageView.setFontDrawableColor(this.mContext.getResources().getColor(2131558632));
            if (this.mHasTrafficLicense) {
                this.mTrafficLock.setVisibility(8);
                if (this.mTraffic == null) {
                    this.mTrafficProgressBar.setVisibility(0);
                    this.mTrafficValueTextView.setVisibility(8);
                    return;
                }
                this.mTrafficProgressBar.setVisibility(8);
                this.mTrafficValueTextView.setVisibility(0);
                int delay = this.mTraffic.getDelay();
                this.mTrafficValueTextView.setText((delay > 0 ? "+" : "") + ResourceManager.nativeFormatTimeSpanToShortWords((long) delay));
                return;
            }
            this.mTrafficLock.setVisibility(0);
            this.mTrafficProgressBar.setVisibility(8);
            this.mTrafficValueTextView.setVisibility(8);
            return;
        }
        this.mTrafficImageView.setFontDrawableColor(this.mContext.getResources().getColor(2131558495));
        this.mTrafficLock.setVisibility(8);
        this.mTrafficProgressBar.setVisibility(8);
        this.mTrafficValueTextView.setVisibility(8);
    }

    public static void onRouteComputeFinishedAll() {
        RouteSelectionScreen screen = (RouteSelectionScreen) OverlayScreen.getInstance(Mode.ROUTE_SELECTION);
        if (!(screen == null || screen.mSwitchViewHelper == null || screen.mBtnRouteOverview == null)) {
            screen.mDriveWalkContainer.setEnabled(true);
            screen.mSwitchViewHelper.setEnabled(true);
            screen.mBtnRouteOverview.setEnabled(true);
            screen.mRightContainer.setEnabled(true);
        }
        RouteManager.nativeComputeRouteCameras();
    }

    public void onConnectionChanged(Boolean bConnection) {
        this.mIsOnline = bConnection.booleanValue();
        updateRightContainer();
    }

    public void onTrafficChange(TrafficItem trafficItem) {
        if (trafficItem != null) {
            this.mTraffic = trafficItem;
            updateRightContainer();
        }
    }

    public void onSpeedCamsReady(ArrayList<SpeedCamItem> items) {
        this.mSpeedcamCount = items.size();
        updateRightContainer();
    }

    public void onDetach() {
        this.mTraffic = null;
        this.mSpeedcamCount = -1;
        updateRightContainer();
    }
}
