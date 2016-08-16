package com.sygic.aura.route.fragment;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.appcompat.C0101R;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import com.sygic.aura.analytics.InfinarioAnalyticsLogger;
import com.sygic.aura.fragments.interfaces.FragmentManagerInterface;
import com.sygic.aura.helper.CustomDialogFragment.Builder;
import com.sygic.aura.helper.EventReceivers.MapEventsReceiver;
import com.sygic.aura.helper.EventReceivers.NetworkEventsReceiver;
import com.sygic.aura.helper.EventReceivers.RouteEventsReceiver;
import com.sygic.aura.helper.EventReceivers.WndEventsReceiver;
import com.sygic.aura.helper.SToast;
import com.sygic.aura.helper.SygicHelper;
import com.sygic.aura.helper.interfaces.AlternativeRouteSelectedListener;
import com.sygic.aura.helper.interfaces.AutoCloseListener;
import com.sygic.aura.helper.interfaces.RouteCancelListener;
import com.sygic.aura.map.MapControlsManager;
import com.sygic.aura.map.PositionInfo;
import com.sygic.aura.map.data.map_selection.MapSelection;
import com.sygic.aura.map.fragment.MapOverlayFragment;
import com.sygic.aura.map.fragment.MapOverlayFragment.Mode;
import com.sygic.aura.map.screen.OverlayScreen;
import com.sygic.aura.map.screen.RouteSelectionScreen;
import com.sygic.aura.navigate.fragment.NavigateFragment;
import com.sygic.aura.resources.ResourceManager;
import com.sygic.aura.route.NoGpsSignalDialogFragment;
import com.sygic.aura.route.NoGpsSignalDialogFragment.NoGpsDialogListener;
import com.sygic.aura.route.NoGpsSignalDialogFragment.NoGpsDialogOutput;
import com.sygic.aura.route.RouteManager;
import com.sygic.aura.route.RouteManager.RouteComputeMode;
import com.sygic.aura.route.RouteManager.RouteComputePosition;
import com.sygic.aura.route.RouteSummary;
import com.sygic.aura.search.fragment.SearchFragment;
import com.sygic.aura.showcase.ShowcaseView;
import com.sygic.aura.showcase.ShowcaseView.OnProceedListener;
import com.sygic.aura.showcase.targets.PointTarget;
import com.sygic.aura.showcase.targets.ToolbarItemTarget;
import com.sygic.aura.utils.StartDestInfoImpl;
import com.sygic.aura.views.WndManager;
import loquendo.tts.engine.TTSConst;

public class RouteSelectionFragment extends RouteComputeProgressFragment implements AlternativeRouteSelectedListener, AutoCloseListener, RouteCancelListener {
    Handler gpsTimerHandler;
    Runnable gpsTimerRunnable;
    private NoGpsSignalDialogFragment mNoGpsSignalFragment;
    private RouteComputeMode mRouteComputeMode;
    private RouteSelectionScreen mRouteSelectionScreen;
    private ShowcaseView mShowcase;
    private MenuItem mStartActionButton;
    private String mStartTitle;
    private boolean mWasAlternativeSelected;
    private boolean mWasMapIn3D;

    /* renamed from: com.sygic.aura.route.fragment.RouteSelectionFragment.1 */
    class C15291 implements Runnable {
        C15291() {
        }

        public void run() {
            if (!RouteSelectionFragment.this.isDialogVisible()) {
                return;
            }
            if (PositionInfo.nativeHasValidPosition(false)) {
                RouteManager.nativeComputeRoute(RouteSelectionFragment.this.mRouteNavigateData.getWaypointsNavSel(), RouteComputePosition.POSITION_ACTUAL, RouteSelectionFragment.this.mRouteComputeMode);
                RouteSelectionFragment.this.hideNoGpsFragment();
                return;
            }
            RouteSelectionFragment.this.gpsTimerHandler.postDelayed(this, 1000);
        }
    }

    /* renamed from: com.sygic.aura.route.fragment.RouteSelectionFragment.2 */
    class C15302 implements Runnable {
        C15302() {
        }

        public void run() {
            if (RouteSelectionFragment.this.getActivity() != null) {
                Resources res = RouteSelectionFragment.this.getResources();
                MapControlsManager.nativeShowRouteWithMargin(0, res.getDimensionPixelSize(C0101R.dimen.abc_action_bar_default_height_material) + res.getDimensionPixelSize(2131230995), 0, 0);
            }
        }
    }

    /* renamed from: com.sygic.aura.route.fragment.RouteSelectionFragment.3 */
    class C15313 implements NoGpsDialogListener {
        C15313() {
        }

        public void onButtonClick(NoGpsDialogOutput output) {
            FragmentManagerInterface mngr;
            switch (C15346.f1271x9f8aeac7[output.ordinal()]) {
                case TTSConst.TTSMULTILINE /*1*/:
                    RouteManager.nativeComputeRoute(RouteSelectionFragment.this.mRouteNavigateData.getWaypointsNavSel(), RouteComputePosition.POSITION_LAST_VALID, RouteSelectionFragment.this.mRouteComputeMode);
                    break;
                case TTSConst.TTSPARAGRAPH /*2*/:
                    boolean fragmentFound;
                    String str;
                    RouteSelectionFragment.this.mRouteNavigateData.changeStart();
                    mngr = SygicHelper.getFragmentActivityWrapper();
                    if (RouteSelectionFragment.this.getFragmentManager().findFragmentByTag("fragment_search_tag") != null) {
                        fragmentFound = true;
                    } else {
                        fragmentFound = false;
                    }
                    if (fragmentFound) {
                        str = "fragment_search_tag";
                    } else {
                        str = null;
                    }
                    mngr.clearBackStack(str, false);
                    if (!fragmentFound) {
                        mngr.addFragment(SearchFragment.class, "fragment_search_tag", true);
                        break;
                    }
                    break;
                case TTSConst.TTSUNICODE /*3*/:
                    RouteSelectionFragment.this.performHomeAction();
                    break;
                case TTSConst.TTSXML /*4*/:
                    SygicHelper.getFragmentActivityWrapper().clearBackStack(false);
                    mngr = SygicHelper.getFragmentActivityWrapper();
                    if (!(mngr == null || RouteSelectionFragment.this.getActivity() == null)) {
                        mngr.addFragment(SelectFromMapFragment.class, "fragment_select_from_map_tag", true);
                        break;
                    }
            }
            RouteSelectionFragment.this.mNoGpsSignalFragment = null;
        }
    }

    /* renamed from: com.sygic.aura.route.fragment.RouteSelectionFragment.4 */
    class C15324 implements OnProceedListener {
        final /* synthetic */ Activity val$activity;

        C15324(Activity activity) {
            this.val$activity = activity;
        }

        public void onProceed() {
            RouteSelectionFragment.this.showStartShowcase(this.val$activity);
        }
    }

    /* renamed from: com.sygic.aura.route.fragment.RouteSelectionFragment.5 */
    class C15335 implements OnClickListener {
        C15335() {
        }

        public void onClick(DialogInterface dialog, int which) {
            RouteSelectionFragment.this.performHomeAction();
        }
    }

    /* renamed from: com.sygic.aura.route.fragment.RouteSelectionFragment.6 */
    static /* synthetic */ class C15346 {
        static final /* synthetic */ int[] f1271x9f8aeac7;

        static {
            f1271x9f8aeac7 = new int[NoGpsDialogOutput.values().length];
            try {
                f1271x9f8aeac7[NoGpsDialogOutput.BUTTON_LAST_VALID.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                f1271x9f8aeac7[NoGpsDialogOutput.BUTTON_CHANGE_START.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                f1271x9f8aeac7[NoGpsDialogOutput.BUTTON_CANCEL.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
            try {
                f1271x9f8aeac7[NoGpsDialogOutput.BUTTON_FROM_MAP.ordinal()] = 4;
            } catch (NoSuchFieldError e4) {
            }
        }
    }

    public RouteSelectionFragment() {
        this.gpsTimerHandler = new Handler();
        this.gpsTimerRunnable = new C15291();
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loadNavigationData();
        this.mWasMapIn3D = !MapControlsManager.nativeIsMapView2D();
        if (this.mWasMapIn3D) {
            MapControlsManager.nativeSetMapView2D();
        }
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        RouteEventsReceiver.registerAlternativeRouteSelectedListener(this);
        MapEventsReceiver.registerRouteCancelListener(this);
        MapOverlayFragment.setMode(getActivity(), Mode.ROUTE_SELECTION);
        this.mRouteSelectionScreen = (RouteSelectionScreen) OverlayScreen.getInstance(Mode.ROUTE_SELECTION);
        MapEventsReceiver.registerTrafficChangeListener(this.mRouteSelectionScreen);
        RouteEventsReceiver.registerSpeedCamsReadyListener(this.mRouteSelectionScreen);
        NetworkEventsReceiver.registerConnectionChangeListener(this.mRouteSelectionScreen);
        Bundle arguments = getArguments();
        this.mRouteComputeMode = (RouteComputeMode) arguments.getSerializable("compute_mode");
        if (this.mRouteComputeMode == null) {
            this.mRouteComputeMode = this.mRouteNavigateData.isDestinationParking() ? RouteComputeMode.MODE_PEDESTRIAN : RouteComputeMode.MODE_CAR;
        }
        String itinerary = arguments.getString("itinerary");
        if (itinerary == null) {
            RouteSelectionScreen.setVehicleMode(this.mRouteComputeMode);
            startComputing(this.mRouteComputeMode);
        } else {
            RouteSelectionScreen.setVehicleMode(RouteComputeMode.MODE_CAR);
            MapSelection[] waypoints = RouteManager.nativeComputeFromItinerary(itinerary);
            if (waypoints == null) {
                SToast.makeText(getActivity(), 2131165396, 1).show();
                performHomeAction();
            } else {
                this.mRouteNavigateData.changeStart(waypoints[0]);
                for (int i = 1; i < waypoints.length; i++) {
                    this.mRouteNavigateData.insertNewWaypoint(i, waypoints[i]);
                }
            }
        }
        return inflater.inflate(2130903128, container, false);
    }

    protected int getToolbarTitle() {
        return 2131165322;
    }

    protected int getToolbarMenu() {
        return 2131755026;
    }

    public void onDestroyView() {
        super.onDestroyView();
        RouteEventsReceiver.unregisterAlternativeRouteSelectedListener(this);
        MapEventsReceiver.unregisterRouteCancelListener(this);
        MapEventsReceiver.unregisterTrafficChangeListener(this.mRouteSelectionScreen);
        RouteEventsReceiver.unregisterSpeedCamsReadyListener(this.mRouteSelectionScreen);
        NetworkEventsReceiver.unregisterConnectionChangeListener(this.mRouteSelectionScreen);
        this.mRouteSelectionScreen.onDetach();
        WndEventsReceiver.unregisterAutoCloseListener(this);
    }

    public void onDestroy() {
        super.onDestroy();
        NavigateFragment.nativeCancelRoute();
        if (this.mWasMapIn3D) {
            MapControlsManager.nativeSetMapView3D();
        }
    }

    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        recreateNoGpsDialog();
        postPlaceMap();
    }

    public void postPlaceMap() {
        new Handler().postDelayed(new C15302(), 500);
    }

    private void startComputing(RouteComputeMode computeMode) {
        boolean startFromCurrent = this.mRouteNavigateData.isStartFromCurrentLocation();
        if (!startFromCurrent && this.mRouteNavigateData.getWaypoint(0).isEmpty(0)) {
            startFromCurrent = true;
        }
        if (!startFromCurrent) {
            RouteManager.nativeComputeRoute(this.mRouteNavigateData.getWaypointsNavSel(), RouteComputePosition.POSITION_USER_DEFINED, computeMode);
        } else if (PositionInfo.nativeHasValidPosition(false)) {
            RouteManager.nativeComputeRoute(this.mRouteNavigateData.getWaypointsNavSel(), RouteComputePosition.POSITION_ACTUAL, computeMode);
        } else {
            showNoGpsFragment();
            InfinarioAnalyticsLogger.getInstance(getActivity()).trackJourneyNoGpsModal(StartDestInfoImpl.from(null, this.mRouteNavigateData.getWaypoint(this.mRouteNavigateData.getWaypointsCount() - 1).getSearchItems()));
        }
    }

    public void onPrepareOptionsMenu(Menu menu) {
        MenuItem menuItem = menu.findItem(2131624688);
        if (menuItem != null) {
            menuItem.setEnabled(!this.mComputing);
        }
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() != 2131624688) {
            return super.onOptionsItemSelected(item);
        }
        runNavigation();
        return true;
    }

    private void runNavigation() {
        if (this.mWasMapIn3D) {
            MapControlsManager.nativeSetMapView3D();
        }
        RouteManager.nativeSaveItinerar();
        FragmentManagerInterface mngr = SygicHelper.getFragmentActivityWrapper();
        if (mngr != null) {
            mngr.replaceFragment(NavigateFragment.class, "fragment_navigate_tag", true);
        }
        InfinarioAnalyticsLogger.getInstance(getActivity()).trackJourneyStarted(StartDestInfoImpl.from(RouteSummary.nativeGetStartSearchEntries(), RouteSummary.nativeGetEndSearchEntries()), RouteSummary.nativeGetTotalDistance(), RouteSummary.nativeGetTotalTime(), this.mWasAlternativeSelected);
    }

    public NoGpsDialogListener getNoGpsDialogListener() {
        return new C15313();
    }

    private void showNoGpsFragment() {
        if (this.mNoGpsSignalFragment == null) {
            this.mNoGpsSignalFragment = NoGpsSignalDialogFragment.getInstance(getNoGpsDialogListener());
            showDialogFragment(this.mNoGpsSignalFragment, "NoGpsSignal");
            this.gpsTimerHandler.postDelayed(this.gpsTimerRunnable, 1000);
        }
    }

    private void recreateNoGpsDialog() {
        if (this.mNoGpsSignalFragment != null && this.mNoGpsSignalFragment.getDialog() != null && this.mNoGpsSignalFragment.getDialog().isShowing()) {
            this.mNoGpsSignalFragment.dismissAllowingStateLoss();
            this.mNoGpsSignalFragment = null;
            showNoGpsFragment();
        }
    }

    private void hideNoGpsFragment() {
        if (isDialogVisible()) {
            this.mNoGpsSignalFragment.dismissAllowingStateLoss();
        }
    }

    private void showDialogFragment(DialogFragment dialogFragment, String dialogTag) {
        if (dialogFragment == null) {
            return;
        }
        if (!dialogFragment.isAdded() || dialogFragment.isHidden()) {
            dialogFragment.show(getFragmentManager(), dialogTag);
            return;
        }
        Dialog dialog = dialogFragment.getDialog();
        if (dialog != null) {
            dialog.show();
        }
    }

    private boolean isDialogVisible() {
        return (this.mNoGpsSignalFragment == null || !this.mNoGpsSignalFragment.isAdded() || this.mNoGpsSignalFragment.isHidden() || this.mNoGpsSignalFragment.getDialog() == null || !this.mNoGpsSignalFragment.getDialog().isShowing()) ? false : true;
    }

    public void onAutoClose(int tick, boolean isMoveable) {
        ensureStartActionButton();
        ensureStartTitle();
        if (WndManager.shouldShowCountdown(tick)) {
            this.mStartActionButton.setTitle("(" + tick + ") " + this.mStartTitle);
            this.mToolbar.invalidateMenuStrings();
        } else if (!this.mStartActionButton.getTitle().equals(this.mStartTitle)) {
            this.mStartActionButton.setTitle(this.mStartTitle);
            this.mToolbar.invalidateMenuStrings();
        }
        if (tick <= 0) {
            runNavigation();
            ShowcaseView.dismiss(this.mShowcase);
        }
    }

    private void ensureStartTitle() {
        if (this.mStartTitle == null) {
            this.mStartTitle = ResourceManager.getCoreString(getResources(), 2131165505);
        }
    }

    private void ensureStartActionButton() {
        if (this.mStartActionButton == null && this.mToolbar != null) {
            this.mStartActionButton = this.mToolbar.getMenu().findItem(2131624688);
        }
    }

    public void onStartComputingProgress() {
        WndEventsReceiver.unregisterAutoCloseListener(this);
        super.onStartComputingProgress();
    }

    public void onAlternativeRouteSelected() {
        this.mWasAlternativeSelected = true;
    }

    public void onRouteComputeFinishedAll() {
        super.onRouteComputeFinishedAll();
        FragmentActivity activity = getActivity();
        if (!isCanceled() && activity != null) {
            WndManager.nativeResetAutoCloseTimer();
            WndEventsReceiver.registerAutoCloseListener(this);
            MapControlsManager.nativeShowRouteWithMargin(0, getResources().getDimensionPixelSize(C0101R.dimen.abc_action_bar_default_height_material), 0, 0);
            this.mRouteNavigateData.setCountryAvoidsArray(RouteManager.nativeGetRouteAvoids());
            showShowcase();
            InfinarioAnalyticsLogger.getInstance(activity).trackJourneyPlanned(StartDestInfoImpl.from(RouteSummary.nativeGetStartSearchEntries(), RouteSummary.nativeGetEndSearchEntries()));
        }
    }

    private void showShowcase() {
        Activity activity = getActivity();
        if (RouteSummary.nativeGetRouteCount() > 1) {
            showAlternativesAndStartShowcase(activity);
        } else {
            showStartShowcase(activity);
        }
    }

    private void showAlternativesAndStartShowcase(Activity activity) {
        String alternativesShowcaseId = getString(2131166716);
        if (ShowcaseView.shouldShow(activity, alternativesShowcaseId)) {
            DisplayMetrics display = getResources().getDisplayMetrics();
            this.mShowcase = new ShowcaseView(activity, alternativesShowcaseId).setContentTitle(2131165899).setContentText(2131165898).setTarget(new PointTarget((int) (((float) display.widthPixels) / 2.0f), (int) (3.0f * (((float) display.heightPixels) / 5.0f)))).setScale(1.5f).setOnProceedListener(new C15324(activity)).show();
        }
    }

    private void showStartShowcase(Activity activity) {
        String startShowcaseId = getString(2131166717);
        if (ShowcaseView.shouldShow(activity, startShowcaseId)) {
            this.mShowcase = new ShowcaseView(activity, startShowcaseId).setContentTitle(2131165901).setContentText(2131165900).setTarget(new ToolbarItemTarget(this.mToolbar, 2131624688)).show();
        }
    }

    public void onRouteComputeError(String msg) {
        super.onRouteComputeError(msg);
        new Builder(getActivity()).title(2131165394).body(msg).negativeButton(2131165355, new C15335()).build().showAllowingStateLoss("routecompute_error_dialog");
    }

    public void onRouteCanceled(Boolean user) {
        performHomeAction();
    }
}
