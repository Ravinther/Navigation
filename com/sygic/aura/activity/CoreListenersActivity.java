package com.sygic.aura.activity;

import android.os.Bundle;
import android.os.Handler;
import com.sygic.aura.analytics.InfinarioAnalyticsLogger;
import com.sygic.aura.events.ActivityUserInteractionListener;
import com.sygic.aura.favorites.fragment.FavoritesFragmentResultCallback;
import com.sygic.aura.fragments.AbstractFragment;
import com.sygic.aura.fragments.WebViewFragment;
import com.sygic.aura.fragments.interfaces.FragmentManagerInterface;
import com.sygic.aura.fragments.interfaces.FragmentResultCallback;
import com.sygic.aura.helper.Fragments;
import com.sygic.aura.helper.SToast;
import com.sygic.aura.helper.SygicHelper;
import com.sygic.aura.helper.interfaces.InvokeCommandListener;
import com.sygic.aura.helper.interfaces.MapsAvailabilityListener;
import com.sygic.aura.helper.interfaces.PoiDetailListener;
import com.sygic.aura.helper.interfaces.RouteCancelListener;
import com.sygic.aura.helper.interfaces.RouteCommandListener;
import com.sygic.aura.helper.interfaces.RouteFinishListener;
import com.sygic.aura.map.PositionInfo;
import com.sygic.aura.map.data.map_selection.MapSelection;
import com.sygic.aura.map.fragment.MapOverlayFragment;
import com.sygic.aura.map.fragment.MapOverlayFragment.Mode;
import com.sygic.aura.poi.OnlinePoiInfoEntry.EItemType;
import com.sygic.aura.poi.PoiFragmentResultCallback;
import com.sygic.aura.poi.detail.PoiDetailActions;
import com.sygic.aura.poi.detail.PoiDetailFragment;
import com.sygic.aura.poi.detail.PoiDetailFragmentResultCallback;
import com.sygic.aura.poi.fragment.PoiFragment;
import com.sygic.aura.poi.nearbypoi.fragment.NearbyPoiCategoryFragment;
import com.sygic.aura.resources.ResourceManager;
import com.sygic.aura.route.RouteManager;
import com.sygic.aura.route.RouteManager.RouteComputeMode;
import com.sygic.aura.route.RouteSummary;
import com.sygic.aura.route.fragment.RouteSelectionFragment;
import com.sygic.aura.search.data.SearchBox;
import com.sygic.aura.search.fragment.SearchFragment;
import com.sygic.aura.search.model.data.FavoritesItem;
import com.sygic.aura.search.model.data.ListItem;
import com.sygic.aura.search.model.data.OnlinePoiListItem;
import com.sygic.aura.search.model.data.PoiListItem;
import com.sygic.aura.search.model.data.RouteListItem;
import com.sygic.aura.search.model.data.SearchLocationData;
import com.sygic.aura.store.fragment.ComponentsFragment;
import com.sygic.aura.store.fragment.MarketPlaceFragment;
import com.sygic.aura.utils.StartDestInfoImpl;
import com.sygic.aura.views.WndManager;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import loquendo.tts.engine.TTSConst;

public class CoreListenersActivity extends FragmentMethodsActivity implements ActivityUserInteractionListener, FavoritesFragmentResultCallback, InvokeCommandListener, MapsAvailabilityListener, RouteCancelListener, RouteCommandListener, RouteFinishListener, PoiFragmentResultCallback, PoiDetailFragmentResultCallback {
    protected boolean mMapsAvailable;
    private final List<PoiDetailListener> mPoiDetailListeners;
    protected boolean mRestoringRoute;
    protected boolean mRouteFinished;
    private OnRouteRestoredListener mRouteRestoredListener;
    private boolean mSoftEndTracked;

    /* renamed from: com.sygic.aura.activity.CoreListenersActivity.1 */
    class C11251 implements Runnable {
        final /* synthetic */ Integer val$command;
        final /* synthetic */ String val$id;

        C11251(Integer num, String str) {
            this.val$command = num;
            this.val$id = str;
        }

        public void run() {
            String fragmentTag = "fragment_sygic_store_tag";
            if (CoreListenersActivity.this.findFragmentByTag("fragment_sygic_store_tag") == null) {
                Bundle bundle = new Bundle();
                bundle.putString(AbstractFragment.ARG_TITLE, ResourceManager.getCoreString(CoreListenersActivity.this, 2131165377));
                bundle.putInt("invoke_command", this.val$command.intValue());
                bundle.putString("product_id", this.val$id);
                bundle.putString("source", "CoreListenersActivity");
                CoreListenersActivity.this.addFragment(MarketPlaceFragment.class, "fragment_sygic_store_tag", true, null, bundle);
            }
        }
    }

    /* renamed from: com.sygic.aura.activity.CoreListenersActivity.2 */
    class C11262 implements Runnable {
        C11262() {
        }

        public void run() {
            Bundle bundle = new Bundle();
            String title = ResourceManager.getCoreString(CoreListenersActivity.this, 2131165799);
            bundle.putString(AbstractFragment.ARG_TITLE, title);
            Fragments.add(CoreListenersActivity.this, ComponentsFragment.class, title, bundle);
        }
    }

    /* renamed from: com.sygic.aura.activity.CoreListenersActivity.3 */
    class C11273 implements Runnable {
        C11273() {
        }

        public void run() {
            CoreListenersActivity.this.setRestoreRoute(true);
            MapOverlayFragment.setMode(CoreListenersActivity.this, Mode.RESTORE_ROUTE);
        }
    }

    /* renamed from: com.sygic.aura.activity.CoreListenersActivity.4 */
    static /* synthetic */ class C11284 {
        static final /* synthetic */ int[] $SwitchMap$com$sygic$aura$poi$detail$PoiDetailActions;

        static {
            $SwitchMap$com$sygic$aura$poi$detail$PoiDetailActions = new int[PoiDetailActions.values().length];
            try {
                $SwitchMap$com$sygic$aura$poi$detail$PoiDetailActions[PoiDetailActions.ACTION_DRIVE_TO.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                $SwitchMap$com$sygic$aura$poi$detail$PoiDetailActions[PoiDetailActions.ACTION_TRAVEL_VIA.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                $SwitchMap$com$sygic$aura$poi$detail$PoiDetailActions[PoiDetailActions.ACTION_EXPLORE_NEARBY.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
            try {
                $SwitchMap$com$sygic$aura$poi$detail$PoiDetailActions[PoiDetailActions.ACTION_EXPLORE_NEARBY_SELECT.ordinal()] = 4;
            } catch (NoSuchFieldError e4) {
            }
            try {
                $SwitchMap$com$sygic$aura$poi$detail$PoiDetailActions[PoiDetailActions.ACTION_PASS_BY.ordinal()] = 5;
            } catch (NoSuchFieldError e5) {
            }
        }
    }

    public interface OnRouteRestoredListener {
        void onRouteRestored();
    }

    public CoreListenersActivity() {
        this.mPoiDetailListeners = Collections.synchronizedList(new LinkedList());
    }

    public boolean registerPoiDetailListener(PoiDetailListener poiDetailFinished) {
        return this.mPoiDetailListeners.add(poiDetailFinished);
    }

    public boolean unregisterPoiDetailListener(PoiDetailListener poiDetailFinished) {
        return this.mPoiDetailListeners.remove(poiDetailFinished);
    }

    public void registerRouteRestoredListener(OnRouteRestoredListener listener) {
        this.mRouteRestoredListener = listener;
    }

    public void unregisterRouteRestoredListener() {
        this.mRouteRestoredListener = null;
    }

    public void onMapsAvailabilityChanged(Boolean mapsAvailable) {
        this.mRouteData.clearRouteWaypoints();
        if (this.mMapsAvailable != mapsAvailable.booleanValue()) {
            this.mMapsAvailable = mapsAvailable.booleanValue();
            MapOverlayFragment.setMode(this, this.mMapsAvailable ? Mode.FREEDRIVE_BROWSE : Mode.NO_MAPS);
        }
    }

    public void onStoreInvoked(Integer command, String id) {
        new Handler().postDelayed(new C11251(command, id), 1000);
    }

    public void onDownloadMap(String strIso) {
        new Handler().postDelayed(new C11262(), 500);
    }

    public void onRouteFinished() {
        if (!clearBackStackRunning()) {
            this.mRouteFinished = true;
            clearBackStack(false);
            this.mRouteData.clearRouteWaypoints();
            InfinarioAnalyticsLogger.getInstance(this).trackJourneyHardEnd(StartDestInfoImpl.from(RouteSummary.nativeGetStartSearchEntries(), RouteSummary.nativeGetEndSearchEntries()), RouteSummary.nativeGetTotalDistance(), RouteSummary.nativeGetTotalTime());
            this.mSoftEndTracked = false;
        }
    }

    public void onRouteFinishedSoft() {
        if (!this.mSoftEndTracked) {
            InfinarioAnalyticsLogger.getInstance(this).trackJourneySoftEnd(StartDestInfoImpl.from(RouteSummary.nativeGetStartSearchEntries(), RouteSummary.nativeGetEndSearchEntries()), RouteSummary.nativeGetTotalDistance(), RouteSummary.nativeGetTotalTime());
            this.mSoftEndTracked = true;
        }
    }

    public void onRouteCanceled(Boolean user) {
        this.mSoftEndTracked = false;
    }

    public void onComputeAfterStart() {
        new Handler().postDelayed(new C11273(), 100);
    }

    public void onNavigateThere(ArrayList<MapSelection> selectionsArray, RouteComputeMode mode) {
        FragmentManagerInterface manager = SygicHelper.getFragmentActivityWrapper();
        MapSelection[] selections = (MapSelection[]) selectionsArray.toArray(new MapSelection[selectionsArray.size()]);
        if (manager != null && selections.length != 0) {
            RouteSummary.nativeCancelRoute();
            this.mRouteData.clearRouteWaypoints();
            this.mRouteData.updateRouteNaviData(selections);
            Bundle bundle = new Bundle();
            bundle.putSerializable("compute_mode", mode);
            manager.addFragment(RouteSelectionFragment.class, "fragment_route_selection_tag", true, null, bundle);
        }
    }

    public void onCloseFragments() {
        closeFragments();
    }

    public void onComputeSetRoute() {
        FragmentManagerInterface manager = SygicHelper.getFragmentActivityWrapper();
        if (manager != null) {
            Bundle bundle = new Bundle();
            bundle.putSerializable("compute_mode", RouteComputeMode.MODE_CAR);
            manager.addFragment(RouteSelectionFragment.class, "fragment_route_selection_tag", true, null, bundle);
        }
    }

    public void onPoiDetailFragmentResult(PoiDetailActions actionId, MapSelection mapSelection) {
        if (mapSelection == null) {
            return;
        }
        if (this.mPoiDetailListeners.isEmpty()) {
            switch (C11284.$SwitchMap$com$sygic$aura$poi$detail$PoiDetailActions[actionId.ordinal()]) {
                case TTSConst.TTSMULTILINE /*1*/:
                    onGetDirectionsAction(mapSelection);
                    return;
                case TTSConst.TTSPARAGRAPH /*2*/:
                    onTravelVia(mapSelection);
                    return;
                case TTSConst.TTSUNICODE /*3*/:
                    onShowNearbyPoisAction(mapSelection, false);
                    return;
                case TTSConst.TTSXML /*4*/:
                    onShowNearbyPoisAction(mapSelection, true);
                    return;
                default:
                    return;
            }
        }
        synchronized (this.mPoiDetailListeners) {
            for (PoiDetailListener listener : this.mPoiDetailListeners) {
                switch (C11284.$SwitchMap$com$sygic$aura$poi$detail$PoiDetailActions[actionId.ordinal()]) {
                    case TTSConst.TTSMULTILINE /*1*/:
                        this.mRouteData.updateRouteNaviData(new MapSelection[]{mapSelection});
                        listener.onGetDirections(mapSelection);
                        continue;
                    case TTSConst.TTSPARAGRAPH /*2*/:
                        listener.onTravelVia(mapSelection);
                        continue;
                    case TTSConst.TTSUNICODE /*3*/:
                        onShowNearbyPoisAction(mapSelection, false);
                        continue;
                    case TTSConst.TTSXML /*4*/:
                        onShowNearbyPoisAction(mapSelection, true);
                        continue;
                    case TTSConst.TTSEVT_TEXT /*5*/:
                        listener.onPassBy(mapSelection);
                        continue;
                    default:
                        continue;
                }
            }
        }
    }

    public boolean dismissOnFinish() {
        return false;
    }

    public void onPoiFragmentResult(PoiListItem result) {
        if (result == null) {
            return;
        }
        if (result.getProviderType() == EItemType.Viator) {
            Bundle bundle = new Bundle();
            bundle.putString("uri", ((OnlinePoiListItem) result).getDetailUrl());
            addFragment(WebViewFragment.class, "fragment_webview", true, null, bundle);
            return;
        }
        Bundle poiData = new Bundle();
        poiData.putLong(PoiDetailFragment.POI_ID, result.getPoiId());
        poiData.putString(PoiDetailFragment.POI_TITLE, result.getDisplayName());
        poiData.putParcelable(PoiDetailFragment.POI_SEL, result.getMapSel());
        poiData.putSerializable(PoiDetailFragment.POI_ONLINE_TYPE, result.getProviderType());
        addFragment(PoiDetailFragment.class, "fragment_poi_detail_tag", true, (FragmentResultCallback) this, poiData);
    }

    public void onFavoritesFragmentResult(FavoritesItem result) {
        int i = 0;
        if (result == null) {
            return;
        }
        if (result instanceof RouteListItem) {
            this.mRouteData.clearRouteWaypoints();
            Bundle bundle = new Bundle();
            bundle.putString("itinerary", ((RouteListItem) result).getItinerarPath());
            addFragment(RouteSelectionFragment.class, "fragment_route_selection_tag", true, null, bundle);
        } else if (PositionInfo.nativeHasNavSel(result.getLongPosition())) {
            this.mRouteData.clearRouteWaypoints();
            SearchLocationData locationData = this.mRouteData.getWaypoint(this.mRouteData.insertEmptyWaypoint(0));
            locationData.clearAllLocationData();
            locationData.setAsTerminalBubble(true);
            ListItem[] addressSegments = PositionInfo.nativeGetPositionSearchEntries(result.getMapSel());
            if (addressSegments != null) {
                int length = addressSegments.length;
                while (i < length) {
                    locationData.setSelectedItem(addressSegments[i]);
                    locationData.shiftToNextSubtype();
                    i++;
                }
            }
            SearchBox.nativeShowOnMap(result.getMapSel());
            addFragment(SearchFragment.class, "fragment_search_tag", true);
        } else {
            SToast.makeText(this, 2131165396, 1).show();
        }
    }

    public void onUserInteraction() {
        WndManager.nativeResetAutoCloseTimer();
    }

    public void setRestoreRoute(boolean restoring) {
        this.mRestoringRoute = restoring;
        if (!restoring && this.mRouteRestoredListener != null) {
            this.mRouteRestoredListener.onRouteRestored();
        }
    }

    private void closeFragments() {
        if (this.mNavigationDrawer != null && this.mNavigationDrawer.isDrawerOpen()) {
            this.mNavigationDrawer.toggleDrawer();
        }
        if (!clearBackStackRunning()) {
            clearBackStack(true);
        }
    }

    private void onGetDirectionsAction(MapSelection selection) {
        if (PositionInfo.nativeHasNavSel(selection.getPosition())) {
            RouteSummary.nativeCancelRoute();
            this.mRouteData.updateRouteNaviData(new MapSelection[]{selection});
            clearBackStack(findFragmentByTag("fragment_route_selection_tag") == null ? null : "fragment_route_selection_tag", true);
            if (getSupportFragmentManager().getBackStackEntryCount() == 0) {
                addFragment(RouteSelectionFragment.class, "fragment_route_selection_tag", true);
                return;
            } else {
                replaceFragment(RouteSelectionFragment.class, "fragment_route_selection_tag", true);
                return;
            }
        }
        SToast.makeText(this, 2131165396, 1).show();
    }

    public void onTravelVia(MapSelection selection) {
        RouteManager.nativeTravelVia(selection);
    }

    private void onShowNearbyPoisAction(MapSelection selection, boolean bSelectMode) {
        if (!clearBackStackRunning()) {
            boolean isFragmentFound;
            Bundle bundle = new Bundle();
            bundle.putParcelable(PoiFragment.ARG_DATA, selection.getPosition());
            bundle.putBoolean(PoiFragment.ARG_SELECT_MODE, bSelectMode);
            if (findFragmentByTag("fragment_nearby_dash") != null) {
                isFragmentFound = true;
            } else {
                isFragmentFound = false;
            }
            clearBackStack(isFragmentFound ? "fragment_nearby_dash" : null, false, 1);
            addFragment(NearbyPoiCategoryFragment.class, "fragment_nearby_dash", true, (FragmentResultCallback) this, bundle);
        }
    }
}
