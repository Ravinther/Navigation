package com.sygic.aura.navigate.fragment;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.res.Configuration;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentManager.BackStackEntry;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnSystemUiVisibilityChangeListener;
import android.view.ViewGroup;
import com.sygic.aura.activity.NaviNativeActivity;
import com.sygic.aura.favorites.fragment.FavoritesFragmentResultCallback;
import com.sygic.aura.fragments.AbstractScreenFragment;
import com.sygic.aura.fragments.interfaces.FragmentManagerInterface;
import com.sygic.aura.helper.CustomDialogFragment.Builder;
import com.sygic.aura.helper.EventReceivers.BubbleEventsReceiver;
import com.sygic.aura.helper.EventReceivers.MapEventsReceiver;
import com.sygic.aura.helper.EventReceivers.RouteEventsReceiver;
import com.sygic.aura.helper.SToast;
import com.sygic.aura.helper.SygicHelper;
import com.sygic.aura.helper.interfaces.ActionControlHolderListener;
import com.sygic.aura.helper.interfaces.BackPressedListener;
import com.sygic.aura.helper.interfaces.DirectionChangeListener;
import com.sygic.aura.helper.interfaces.JunctionChangeListener;
import com.sygic.aura.helper.interfaces.MapClickListener;
import com.sygic.aura.helper.interfaces.PoiDetailListener;
import com.sygic.aura.helper.interfaces.RouteCancelListener;
import com.sygic.aura.helper.interfaces.RouteComputeErrorListener;
import com.sygic.aura.helper.interfaces.RouteEventsListener;
import com.sygic.aura.helper.interfaces.RouteWaypointReachedListener;
import com.sygic.aura.helper.interfaces.ScoutComputeListener;
import com.sygic.aura.helper.interfaces.SignpostChangeListener;
import com.sygic.aura.helper.interfaces.SpeedLimitListener;
import com.sygic.aura.helper.interfaces.UnlockNaviListener;
import com.sygic.aura.map.MapControlsManager;
import com.sygic.aura.map.MapControlsManager.EMapView;
import com.sygic.aura.map.PositionInfo;
import com.sygic.aura.map.data.map_selection.MapSelection;
import com.sygic.aura.map.fragment.MapOverlayFragment;
import com.sygic.aura.map.fragment.MapOverlayFragment.Mode;
import com.sygic.aura.map.screen.NavigationBrowseScreen;
import com.sygic.aura.map.screen.OverlayScreen;
import com.sygic.aura.quickmenu.QuickMenuTimer;
import com.sygic.aura.route.DemoManager;
import com.sygic.aura.route.RouteManager;
import com.sygic.aura.route.RouteSummary;
import com.sygic.aura.route.fragment.RouteSelectionFragment;
import com.sygic.aura.search.data.SearchBox;
import com.sygic.aura.search.fragment.SearchOnRouteFragment;
import com.sygic.aura.search.model.data.FavoritesItem;

public class NavigateFragment extends AbstractScreenFragment implements FavoritesFragmentResultCallback, BackPressedListener, MapClickListener, PoiDetailListener, RouteCancelListener, RouteComputeErrorListener, RouteEventsListener, UnlockNaviListener {
    private String mClearUpToFragmentTag;
    private boolean mIsDemoPaused;
    private OverlayScreen mNavigateOverlayScreen;
    private QuickMenuTimer mQuickMenuTimer;

    /* renamed from: com.sygic.aura.navigate.fragment.NavigateFragment.1 */
    class C13881 implements OnSystemUiVisibilityChangeListener {

        /* renamed from: com.sygic.aura.navigate.fragment.NavigateFragment.1.1 */
        class C13871 implements Runnable {
            C13871() {
            }

            public void run() {
                NaviNativeActivity.hideNavigationBar(NavigateFragment.this.getActivity());
            }
        }

        C13881() {
        }

        public void onSystemUiVisibilityChange(int visibility) {
            if ((visibility & 2) == 0 && NavigateFragment.this.isOnTop()) {
                new Handler().postDelayed(new C13871(), 3000);
            }
        }
    }

    /* renamed from: com.sygic.aura.navigate.fragment.NavigateFragment.2 */
    static class C13892 implements Runnable {
        C13892() {
        }

        public void run() {
            RouteSummary.nativeCancelRoute();
        }
    }

    /* renamed from: com.sygic.aura.navigate.fragment.NavigateFragment.3 */
    class C13903 implements OnClickListener {
        C13903() {
        }

        public void onClick(DialogInterface dialog, int which) {
            if (NavigateFragment.this.getActivity() != null) {
                try {
                    SygicHelper.getFragmentActivityWrapper().clearBackStack(NavigateFragment.this.getFragmentManager().findFragmentByTag("fragment_search_tag") == null ? null : "fragment_search_tag", false);
                } catch (Exception e) {
                    Log.w("NavigateFragment", "Route compute error dialog failed: " + e.getMessage());
                }
            }
        }
    }

    public NavigateFragment() {
        this.mClearUpToFragmentTag = null;
        this.mIsDemoPaused = true;
    }

    public void onLockNavi() {
        if (!this.mIsDemoPaused) {
            DemoManager.nativeSwapDemoPaused();
        }
        MapOverlayFragment.setMode(getActivity(), Mode.NAVIGATE_INFO_BAR);
    }

    public void onUnlockNavi() {
        this.mIsDemoPaused = DemoManager.nativeIsDemoPaused();
        if (!this.mIsDemoPaused) {
            DemoManager.nativeSwapDemoPaused();
        }
        MapOverlayFragment.setMode(getActivity(), Mode.NAVIGATE_BROWSE);
        if (this.mQuickMenuTimer != null && !this.mQuickMenuTimer.isFinished()) {
            this.mQuickMenuTimer.cancel();
        }
    }

    public void onMapClick() {
        if (!MapControlsManager.nativeIsTrafficView() && MapControlsManager.nativeGetMapViewMode() != EMapView.MVCustomPois) {
            if (this.mQuickMenuTimer == null || this.mQuickMenuTimer.isFinished()) {
                this.mQuickMenuTimer = new QuickMenuTimer(getActivity(), false);
                this.mQuickMenuTimer.start();
                ((NaviNativeActivity) getActivity()).getToolbar().setTag(this.mQuickMenuTimer);
                return;
            }
            this.mQuickMenuTimer.cancel(true);
        }
    }

    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loadNavigationData();
        if (getArguments().getBoolean("restore route", false)) {
            this.mRouteNavigateData.setCountryAvoidsArray(RouteManager.nativeGetRouteAvoids());
        }
        MapEventsReceiver.registerMapClickListener(this);
        RouteEventsReceiver.registerRouteEventsListener(this);
        RouteEventsReceiver.registerRouteComputeErrorListener(this);
        MapEventsReceiver.registerRouteCancelListener(this);
        ((NaviNativeActivity) getActivity()).registerBackPressedListener(this);
        this.mNavigateOverlayScreen = OverlayScreen.getInstance(Mode.NAVIGATE_INFO_BAR);
        MapEventsReceiver.registerSignpostChangeListener((SignpostChangeListener) this.mNavigateOverlayScreen);
        RouteEventsReceiver.registerDirectionChangeListener((DirectionChangeListener) this.mNavigateOverlayScreen);
        RouteEventsReceiver.registerJunctionChangeListener((JunctionChangeListener) this.mNavigateOverlayScreen);
        RouteEventsReceiver.registerWaypointReachedListener((RouteWaypointReachedListener) this.mNavigateOverlayScreen);
        MapEventsReceiver.registerSpeedLimitListener((SpeedLimitListener) this.mNavigateOverlayScreen);
        RouteEventsReceiver.registerRouteEventsListener((RouteEventsListener) this.mNavigateOverlayScreen);
        BubbleEventsReceiver.registerScoutComputeEventsListener((ScoutComputeListener) this.mNavigateOverlayScreen);
        MapEventsReceiver.registerActionControlHolderListener((ActionControlHolderListener) this.mNavigateOverlayScreen);
        ((NaviNativeActivity) getActivity()).registerPoiDetailListener(this);
    }

    public void onDestroy() {
        super.onDestroy();
        MapEventsReceiver.unregisterMapClickListener(this);
        RouteEventsReceiver.unregisterRouteEventsListener(this);
        RouteEventsReceiver.unregisterRouteComputeErrorListener(this);
        MapEventsReceiver.unregisterRouteCancelListener(this);
        ((NaviNativeActivity) getActivity()).unregisterBackPressedListener(this);
        MapEventsReceiver.unregisterSpeedLimitListener((SpeedLimitListener) this.mNavigateOverlayScreen);
        RouteEventsReceiver.unregisterDirectionChangeListener((DirectionChangeListener) this.mNavigateOverlayScreen);
        RouteEventsReceiver.unregisterJunctionChangeListener((JunctionChangeListener) this.mNavigateOverlayScreen);
        RouteEventsReceiver.unregisterWaypointReachedListener((RouteWaypointReachedListener) this.mNavigateOverlayScreen);
        MapEventsReceiver.unregisterSignpostChangeListener((SignpostChangeListener) this.mNavigateOverlayScreen);
        RouteEventsReceiver.unregisterRouteEventsListener((RouteEventsListener) this.mNavigateOverlayScreen);
        BubbleEventsReceiver.unregisterScoutComputeEventsListener((ScoutComputeListener) this.mNavigateOverlayScreen);
        MapEventsReceiver.unregisterActionControlHolderListener((ActionControlHolderListener) this.mNavigateOverlayScreen);
        ((NaviNativeActivity) getActivity()).unregisterPoiDetailListener(this);
    }

    public void onDestroyView() {
        super.onDestroyView();
        cleanTimers(false);
        MapEventsReceiver.unregisterUnlockNaviListener(this);
    }

    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (VERSION.SDK_INT >= 19) {
            activity.getWindow().getDecorView().setOnSystemUiVisibilityChangeListener(new C13881());
            NaviNativeActivity.hideNavigationBar(activity);
        }
    }

    public void onDetach() {
        super.onDetach();
        if (VERSION.SDK_INT >= 19) {
            getActivity().getWindow().getDecorView().setOnSystemUiVisibilityChangeListener(null);
            NaviNativeActivity.showNavigationBar(getActivity(), false);
        }
    }

    public void onResume() {
        super.onResume();
        if (isOnTop()) {
            NaviNativeActivity.hideNavigationBar(getActivity());
        }
    }

    private boolean isOnTop() {
        FragmentManager fragmentManager = getFragmentManager();
        int entryCount = fragmentManager.getBackStackEntryCount();
        if (entryCount <= 0) {
            return false;
        }
        BackStackEntry backEntry = fragmentManager.getBackStackEntryAt(entryCount - 1);
        if (backEntry == null || !backEntry.getName().equals("fragment_navigate_tag")) {
            return false;
        }
        return true;
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(2130903113, container, false);
        MapOverlayFragment.setMode(inflater.getContext(), Mode.NAVIGATE_INFO_BAR);
        MapEventsReceiver.registerUnlockNaviListener(this);
        RouteManager.nativeStartNavigate();
        return view;
    }

    public void onRouteCanceled(Boolean user) {
        FragmentManagerInterface mngr = SygicHelper.getFragmentActivityWrapper();
        if (mngr != null && !mngr.clearBackStackRunning()) {
            boolean isFragmentFound = getFragmentManager().findFragmentByTag(this.mClearUpToFragmentTag) != null;
            mngr.clearBackStack(isFragmentFound ? this.mClearUpToFragmentTag : null, false);
            if (TextUtils.isEmpty(this.mClearUpToFragmentTag)) {
                this.mRouteNavigateData.clearRouteWaypoints();
            } else if (!isFragmentFound && this.mClearUpToFragmentTag.compareTo("fragment_route_selection_tag") == 0) {
                mngr.addFragment(RouteSelectionFragment.class, "fragment_route_selection_tag", true);
            }
        }
    }

    private boolean cleanTimers(boolean bValue) {
        if (this.mQuickMenuTimer == null || this.mQuickMenuTimer.isFinished()) {
            return false;
        }
        this.mQuickMenuTimer.cancel(bValue);
        return true;
    }

    public boolean onBackPressed() {
        return cleanTimers(true);
    }

    public static void nativeCancelRoute() {
        new Handler().post(new C13892());
    }

    public void onStartComputingProgress() {
    }

    public void onFinishComputingProgress() {
    }

    public void onRouteComputeFinishedAll() {
        int entryCount = getFragmentManager().getBackStackEntryCount();
        if (entryCount > 0 && RouteManager.nativeExistValidRoute()) {
            BackStackEntry backEntry = getFragmentManager().getBackStackEntryAt(entryCount - 1);
            if (backEntry != null && !backEntry.getName().equals("fragment_route_overview_tag")) {
                if (RouteSummary.nativeGetRouteCount() > 1) {
                    MapControlsManager.nativeSetMapViewMode(EMapView.MVRouteSelection);
                } else {
                    RouteManager.nativeStartNavigate();
                }
            }
        }
    }

    public void onRouteComputeError(String msg) {
        new Builder(getActivity()).title(2131165394).body(msg).negativeButton(2131165355, new C13903()).build().showAllowingStateLoss("routecompute_error_dialog");
        MapControlsManager.nativeUnlockVehicle();
    }

    public void onGetDirections(MapSelection mapSelection) {
        this.mClearUpToFragmentTag = "fragment_route_selection_tag";
        nativeCancelRoute();
    }

    public void onTravelVia(MapSelection mapSelection) {
        FragmentManagerInterface mngr = SygicHelper.getFragmentActivityWrapper();
        if (!(mngr == null || mngr.clearBackStackRunning())) {
            mngr.clearBackStack("fragment_navigate_tag", false);
        }
        RouteManager.nativeTravelVia(mapSelection);
    }

    public void onPassBy(MapSelection mapSelection) {
        RouteManager.nativePassBy(mapSelection);
    }

    public void onFavoritesFragmentResult(FavoritesItem result) {
        if (result == null) {
            NaviNativeActivity.hideNavigationBar(getActivity());
            NavigationBrowseScreen.resumeNavigation();
        } else if (PositionInfo.nativeHasNavSel(result.getLongPosition())) {
            FragmentManagerInterface manager = SygicHelper.getFragmentActivityWrapper();
            Bundle bundle = new Bundle();
            bundle.putBoolean("can_show_tooltip", false);
            bundle.putParcelable("map_selection", result.getMapSel());
            if (manager != null) {
                SearchBox.nativeShowOnMap(result.getMapSel());
                manager.replaceFragment(SearchOnRouteFragment.class, "fragment_search_on_route_tag", true, bundle);
            }
        } else {
            NaviNativeActivity.hideNavigationBar(getActivity());
            NavigationBrowseScreen.resumeNavigation();
            SToast.makeText(getActivity(), 2131165396, 1).show();
        }
    }
}
