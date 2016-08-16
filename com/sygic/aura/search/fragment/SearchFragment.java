package com.sygic.aura.search.fragment;

import android.app.Activity;
import android.content.Context;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.support.v7.widget.Toolbar.OnMenuItemClickListener;
import android.text.TextUtils;
import android.transition.AutoTransition;
import android.transition.TransitionManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Toast;
import com.sygic.aura.activity.NaviNativeActivity;
import com.sygic.aura.analytics.AnalyticsInterface.EventType;
import com.sygic.aura.analytics.InfinarioAnalyticsLogger;
import com.sygic.aura.analytics.SygicAnalyticsLogger;
import com.sygic.aura.favorites.fragment.FavoritesFragmentResultCallback;
import com.sygic.aura.fragments.AbstractFragment;
import com.sygic.aura.fragments.AbstractScreenFragment;
import com.sygic.aura.fragments.WebViewFragment;
import com.sygic.aura.fragments.interfaces.FragmentManagerInterface;
import com.sygic.aura.fragments.interfaces.FragmentResultCallback;
import com.sygic.aura.fragments.interfaces.SearchInterface;
import com.sygic.aura.helper.EventReceivers.ComponentEventsReceiver;
import com.sygic.aura.helper.EventReceivers.WndEventsReceiver;
import com.sygic.aura.helper.Fragments;
import com.sygic.aura.helper.InCarConnection;
import com.sygic.aura.helper.SToast;
import com.sygic.aura.helper.SygicHelper;
import com.sygic.aura.helper.interfaces.AutoCloseListener;
import com.sygic.aura.helper.interfaces.MapsAvailabilityListener;
import com.sygic.aura.keyboard.FocusableKeyboard.OnKeyboardVisibilityListener;
import com.sygic.aura.keyboard.FocusableKeyboardView;
import com.sygic.aura.map.PositionInfo;
import com.sygic.aura.map.data.map_selection.MapSelection;
import com.sygic.aura.map.fragment.MapOverlayFragment;
import com.sygic.aura.map.fragment.MapOverlayFragment.Mode;
import com.sygic.aura.network.ConnectionManager;
import com.sygic.aura.poi.OnlinePoiInfoEntry.EItemType;
import com.sygic.aura.poi.PoiFragmentResultCallback;
import com.sygic.aura.poi.detail.PoiDetailFragment;
import com.sygic.aura.poi.detail.PoiNoConnectivityFragment;
import com.sygic.aura.poi.fragment.PoiFragment;
import com.sygic.aura.poi.nearbypoi.fragment.NearbyPoiCategoryFragment;
import com.sygic.aura.resources.ResourceManager;
import com.sygic.aura.route.fragment.RouteSelectionFragment;
import com.sygic.aura.search.data.HouseNumberEntry;
import com.sygic.aura.search.data.SearchBox;
import com.sygic.aura.search.model.RouteWaypointsAdapter;
import com.sygic.aura.search.model.RouteWaypointsAdapter.ViewHolder;
import com.sygic.aura.search.model.SearchResultsAdapter;
import com.sygic.aura.search.model.data.CityPostalSearchItem;
import com.sygic.aura.search.model.data.FavoritesItem;
import com.sygic.aura.search.model.data.HouseNumberSearchItem;
import com.sygic.aura.search.model.data.ListItem;
import com.sygic.aura.search.model.data.OnlineSearchItem;
import com.sygic.aura.search.model.data.PoiListItem;
import com.sygic.aura.search.model.data.QuickSearchItem;
import com.sygic.aura.search.model.data.SearchItem;
import com.sygic.aura.search.model.data.SearchItem.SearchType;
import com.sygic.aura.search.model.data.SearchLocationData;
import com.sygic.aura.search.model.data.SpecialSearchItem;
import com.sygic.aura.search.model.data.SpecialSearchItem.ItemType;
import com.sygic.aura.search.model.data.StreetSearchItem;
import com.sygic.aura.search.results.SearchResultsIF;
import com.sygic.aura.search.view.PoiHolderView.OnMoreClickListener;
import com.sygic.aura.search.view.SearchBar;
import com.sygic.aura.search.view.SearchResultsListView;
import com.sygic.aura.search.view.WaypointsListView;
import com.sygic.aura.showcase.ShowcaseView;
import com.sygic.aura.showcase.ShowcaseView.OnProceedListener;
import com.sygic.aura.showcase.targets.ToolbarItemTarget;
import com.sygic.aura.showcase.targets.ViewTarget;
import com.sygic.aura.views.animation.VerticalExpandingAnimator;
import com.sygic.aura.views.font_specials.SToolbar;
import com.sygic.aura.views.font_specials.SToolbar.OnInvalidatedMenuListener;
import com.sygic.aura.views.popup.ActionItem;
import com.sygic.aura.views.popup.PopupAction;
import loquendo.tts.engine.TTSConst;

public class SearchFragment extends AbstractScreenFragment implements OnItemClickListener, FavoritesFragmentResultCallback, SearchInterface, MapsAvailabilityListener, PoiFragmentResultCallback, QuickSearchFragmentResultCallback, SearchResultsIF {
    private View mClearFocusView;
    private OnKeyboardVisibilityListener mKeyboardVisibilityListener;
    private SearchResultsListView mResultsList;
    private boolean mRouteQuickTipShown;
    protected boolean mRouteReady;
    private boolean mSearchQuickTipShown;
    private SearchResultsAdapter mSearchResultsAdapter;
    private View mWaitOverlay;
    private RouteWaypointsAdapter mWaypointsAdapter;
    private WaypointsListView mWaypointsList;

    /* renamed from: com.sygic.aura.search.fragment.SearchFragment.1 */
    class C15621 implements OnClickListener {
        C15621() {
        }

        public void onClick(View v) {
            NaviNativeActivity.hideKeyboard(SearchFragment.this.getView().getWindowToken());
            SearchFragment.this.performHomeAction();
        }
    }

    /* renamed from: com.sygic.aura.search.fragment.SearchFragment.2 */
    class C15632 implements OnInvalidatedMenuListener {
        C15632() {
        }

        public void onMenuInvalidated(Menu menu) {
            SearchFragment.this.onPrepareOptionsMenu(menu);
        }
    }

    /* renamed from: com.sygic.aura.search.fragment.SearchFragment.3 */
    class C15643 implements OnMenuItemClickListener {
        C15643() {
        }

        public boolean onMenuItemClick(MenuItem item) {
            return SearchFragment.this.onOptionsItemSelected(item);
        }
    }

    /* renamed from: com.sygic.aura.search.fragment.SearchFragment.4 */
    class C15654 implements OnMoreClickListener {
        C15654() {
        }

        public void onMoreClicked(SearchLocationData locationData) {
            SygicHelper.getFragmentActivityWrapper().addFragment(NearbyPoiCategoryFragment.class, "fragment_nearby_dash", true, SearchFragment.this);
            SygicAnalyticsLogger.getAnalyticsEvent(SearchFragment.this.getActivity(), EventType.SEARCH).setName("search").setValue("click", "more_pois").logAndRecycle();
        }
    }

    /* renamed from: com.sygic.aura.search.fragment.SearchFragment.5 */
    class C15665 implements OnProceedListener {
        final /* synthetic */ Activity val$activity;

        C15665(Activity activity) {
            this.val$activity = activity;
        }

        public void onProceed() {
            new ShowcaseView(this.val$activity).setContentTitle(2131165903).setContentText(2131165902).setTarget(new ToolbarItemTarget(SearchFragment.this.mToolbar, 2131624690)).show();
        }
    }

    /* renamed from: com.sygic.aura.search.fragment.SearchFragment.6 */
    static /* synthetic */ class C15676 {
        static final /* synthetic */ int[] f1274x44b36741;
        static final /* synthetic */ int[] f1275x69b2bbd3;
        static final /* synthetic */ int[] f1276xfef9d2f5;

        static {
            f1276xfef9d2f5 = new int[ItemType.values().length];
            try {
                f1276xfef9d2f5[ItemType.ITEM_NEARBY_POI_STREET.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                f1276xfef9d2f5[ItemType.ITEM_NEARBY_POI_CROSSING.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                f1276xfef9d2f5[ItemType.ITEM_QUICK_NOTHING.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
            try {
                f1276xfef9d2f5[ItemType.ITEM_GPS_COORDS.ordinal()] = 4;
            } catch (NoSuchFieldError e4) {
            }
            try {
                f1276xfef9d2f5[ItemType.ITEM_HOUSE_NUMBER.ordinal()] = 5;
            } catch (NoSuchFieldError e5) {
            }
            f1275x69b2bbd3 = new int[SearchType.values().length];
            try {
                f1275x69b2bbd3[SearchType.CITY.ordinal()] = 1;
            } catch (NoSuchFieldError e6) {
            }
            try {
                f1275x69b2bbd3[SearchType.STREET.ordinal()] = 2;
            } catch (NoSuchFieldError e7) {
            }
            try {
                f1275x69b2bbd3[SearchType.SPECIAL.ordinal()] = 3;
            } catch (NoSuchFieldError e8) {
            }
            try {
                f1275x69b2bbd3[SearchType.QUICK.ordinal()] = 4;
            } catch (NoSuchFieldError e9) {
            }
            f1274x44b36741 = new int[QuickSearchItem.ItemType.values().length];
            try {
                f1274x44b36741[QuickSearchItem.ItemType.ITEM_NEARBY_POI.ordinal()] = 1;
            } catch (NoSuchFieldError e10) {
            }
            try {
                f1274x44b36741[QuickSearchItem.ItemType.ITEM_4SQUARE_SEARCH.ordinal()] = 2;
            } catch (NoSuchFieldError e11) {
            }
            try {
                f1274x44b36741[QuickSearchItem.ItemType.ITEM_YELP_SEARCH.ordinal()] = 3;
            } catch (NoSuchFieldError e12) {
            }
            try {
                f1274x44b36741[QuickSearchItem.ItemType.ITEM_VIATOR_SEARCH.ordinal()] = 4;
            } catch (NoSuchFieldError e13) {
            }
        }
    }

    public SearchFragment() {
        this.mRouteReady = false;
        this.mSearchQuickTipShown = false;
        this.mRouteQuickTipShown = false;
    }

    protected int getMenuResId() {
        return 2131755028;
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loadNavigationData();
        initRouteReady();
        InfinarioAnalyticsLogger.getInstance(getActivity()).trackAppActionOpenSearch();
        SygicAnalyticsLogger.getAnalyticsEvent(getActivity(), EventType.SEARCH).setName("search").setValue("click", "search_entered").logAndRecycle();
    }

    protected void initRouteReady() {
        this.mRouteNavigateData.registerObserver((SearchInterface) this);
        this.mRouteNavigateData.computeRouteReady();
    }

    public void onDestroy() {
        this.mRouteNavigateData.unregisterObserver((SearchInterface) this);
        super.onDestroy();
    }

    public void onDestroyView() {
        super.onDestroyView();
        this.mSearchResultsAdapter.destroySearchRef();
        ComponentEventsReceiver.unregisterMapsAvailabilityListener(this);
        WndEventsReceiver.unregisterAutoCloseListener(AutoCloseListener.DUMMY_LISTENER);
        InCarConnection.unhandleKeyboardChanges(this.mKeyboardVisibilityListener);
    }

    protected void onSetupToolbar(SToolbar toolbar) {
        super.onSetupToolbar(toolbar);
        toolbar.setTitle(2131165323);
        toolbar.setNavigationOnClickListener(new C15621());
        toolbar.setOnMenuInvalidateListener(new C15632());
        toolbar.inflateMenu(getMenuResId());
        toolbar.setOnMenuItemClickListener(new C15643());
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(2130903129, container, false);
        this.mWaypointsList = (WaypointsListView) view.findViewById(2131624292);
        this.mResultsList = (SearchResultsListView) view.findViewById(2131624291);
        this.mWaitOverlay = this.mResultsList.findViewById(2131624471);
        this.mClearFocusView = view.findViewById(2131624293);
        this.mWaypointsAdapter = getWaypointsAdapter();
        this.mWaypointsAdapter.setShouldShowKeyboardOnSearchFrameAdded(!shouldShowShowcaseView());
        this.mWaypointsList.setAdapter(this.mWaypointsAdapter);
        this.mSearchResultsAdapter = new SearchResultsAdapter(getContext(), this.mLocationQuery, this);
        this.mResultsList.setAdapter(this.mSearchResultsAdapter);
        this.mResultsList.setOnItemClickListener(this);
        this.mResultsList.setScrollView(this.mWaypointsList);
        this.mResultsList.initHistory(this.mLocationQuery, this);
        this.mResultsList.initPoiShortcuts(this.mWaypointsAdapter, this, new C15654());
        this.mResultsList.initButtonScroll(view, 2131624136, 2131624138);
        MapOverlayFragment.setMode(inflater.getContext(), Mode.FREEDRIVE_BROWSE);
        ComponentEventsReceiver.registerMapsAvailabilityListener(this);
        WndEventsReceiver.registerAutoCloseListener(AutoCloseListener.DUMMY_LISTENER);
        InCarConnection.registerKeyboard((FocusableKeyboardView) view.findViewById(2131624371), (SearchBar) view.findViewById(2131624643));
        this.mKeyboardVisibilityListener = InCarConnection.handleKeyboardChanges(this.mResultsList);
        return view;
    }

    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initCoreSearch("", false);
        this.mWaypointsList.requestFocus();
        showShowcase();
    }

    protected ShowcaseView getShowcaseView() {
        Activity activity = getActivity();
        return new ShowcaseView(activity, getShowcaseId()).setContentTitle(2131165905).setContentText(2131165904).setTarget(new ViewTarget(this.mWaypointsList)).setOnProceedListener(new C15665(activity));
    }

    protected String getShowcaseId() {
        return getString(2131166718);
    }

    private boolean shouldShowShowcaseView() {
        return ShowcaseView.shouldShow(getActivity(), getShowcaseId());
    }

    private void showShowcase() {
        if (shouldShowShowcaseView()) {
            ShowcaseView showcaseView = getShowcaseView();
            if (showcaseView != null) {
                showcaseView.show();
            }
        }
    }

    protected RouteWaypointsAdapter getWaypointsAdapter() {
        return new RouteWaypointsAdapter(this, this.mRouteNavigateData);
    }

    public void setRouteReady(boolean isRouteReady) {
        this.mRouteReady = isRouteReady;
        if (this.mToolbar != null) {
            this.mToolbar.invalidateMenu();
        }
    }

    public void onPrepareOptionsMenu(Menu menu) {
        boolean enableMenu;
        boolean z = true;
        if (this.mWaypointsAdapter == null || !this.mWaypointsAdapter.areOptionsVisible()) {
            enableMenu = true;
        } else {
            enableMenu = false;
        }
        menu.setGroupEnabled(2131624691, enableMenu);
        MenuItem item = menu.findItem(2131624690);
        if (item != null) {
            if (!(enableMenu && this.mRouteReady)) {
                z = false;
            }
            item.setEnabled(z);
        }
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        SygicAnalyticsLogger.getAnalyticsEvent(getActivity(), EventType.SEARCH).setName("search").setValue("context_menu", getResources().getResourceEntryName(item.getItemId())).logAndRecycle();
        if (id == 2131624692) {
            hideResultsList();
            this.mSearchResultsAdapter.cancelCoreSearchLoading();
            if (VERSION.SDK_INT >= 21) {
                TransitionManager.beginDelayedTransition((ViewGroup) getView(), new AutoTransition().addListener(this.mWaypointsList.getTransitionListener()));
            }
            this.mWaypointsAdapter.clearSearch();
            return true;
        } else if (id != 2131624690) {
            return super.onOptionsItemSelected(item);
        } else {
            boolean originalShouldShowKeyboard = this.mWaypointsAdapter.shouldShowKeyboardOnSearchFrameAdded();
            this.mWaypointsAdapter.setShouldShowKeyboardOnSearchFrameAdded(false);
            this.mWaypointsAdapter.expand();
            this.mWaypointsAdapter.rotateExpandButton();
            this.mWaypointsAdapter.setShouldShowKeyboardOnSearchFrameAdded(originalShouldShowKeyboard);
            if (this.mRouteReady) {
                int waypointsCount = this.mRouteNavigateData.getWaypointsCount();
                MapSelection startMapSel = waypointsCount >= 1 ? this.mRouteNavigateData.getWaypointMapSel(0) : null;
                if (waypointsCount == 2 && startMapSel != null && startMapSel.equals(this.mRouteNavigateData.getWaypointMapSel(1))) {
                    NaviNativeActivity.hideKeyboard(getView().getWindowToken());
                    getFragmentManager().popBackStack();
                } else {
                    NaviNativeActivity.hideKeyboard(getView().getWindowToken());
                    ViewHolder holder = getCurrentWaypointHolder();
                    if (holder != null) {
                        resolveHouseNumber(holder.mSearchFrame.getSearchText(), false);
                    }
                    FragmentManagerInterface manager = SygicHelper.getFragmentActivityWrapper();
                    if (manager != null) {
                        manager.replaceFragment(RouteSelectionFragment.class, "fragment_route_selection_tag", true);
                    }
                }
                return true;
            }
            int i = 0;
            while (i < this.mRouteNavigateData.getWaypointsCount()) {
                SearchLocationData waypoint = this.mRouteNavigateData.getWaypoint(i);
                boolean fromCurrent = i == 0 && waypoint.isEmpty(0);
                if (!fromCurrent && !this.mRouteNavigateData.isWaypointReady(waypoint)) {
                    this.mWaypointsAdapter.setCurrentItemPosition(i);
                    reinitializeCurrentInput();
                    break;
                }
                i++;
            }
            SToast.makeText(getActivity(), 2131165676, 1).show();
            return true;
        }
    }

    private void hideResultsList() {
        showResultsList(false);
    }

    public void showResultsList(boolean toShow) {
        if (!toShow || !this.mWaypointsAdapter.areOptionsVisible()) {
            ResourceManager.makeControlVisible(this.mResultsList, toShow, true);
        }
    }

    public Context getContext() {
        return getActivity();
    }

    public void reinitializeCurrentInput() {
        ViewHolder row = getCurrentWaypointHolder();
        row.mSearchFrame.initCoreSearch();
        row.mSearchFrame.requestFocus();
    }

    public int[] getStreetHouseNumbersMinMax() {
        return this.mLocationQuery.getStreetHouseNumbersMinMax();
    }

    public void initCoreSearch(String searchQuery, boolean isPrevious) {
        SearchLocationData waypointData = this.mWaypointsAdapter.getCurrentItem();
        if ((waypointData == null) || (waypointData.isSearchFinished() && !isPrevious)) {
            this.mSearchResultsAdapter.cancelCoreSearchLoading();
            hideResultsList();
            return;
        }
        initCoreSearch(waypointData, searchQuery, isPrevious);
    }

    private void initCoreSearch(SearchLocationData locationData, String searchStr, boolean isPrevious) {
        int bubbleIndex = isPrevious ? locationData.getSearchSubtype() - 1 : locationData.getSearchSubtype();
        if (bubbleIndex < 0) {
            hideResultsList();
        } else if (this.mSearchResultsAdapter != null) {
            if (this.mLocationQuery.isCoreInitialised(10)) {
                this.mSearchResultsAdapter.clear();
            }
            if (bubbleIndex <= 1) {
                this.mSearchResultsAdapter.initCoreSearch(bubbleIndex, locationData.getPreviousSearchItem(bubbleIndex), null);
            } else if (!locationData.isHouseFirst() || isPrevious) {
                this.mSearchResultsAdapter.initCoreSearch(bubbleIndex, locationData.getSearchItem(1), locationData.getSearchItem(2));
            } else {
                VerticalExpandingAnimator.animateView(this.mWaitOverlay, true);
                this.mSearchResultsAdapter.initAmericanCoreSearch(bubbleIndex, locationData.getSearchItem(1), (HouseNumberSearchItem) locationData.getItem(2));
            }
            if (bubbleIndex < 5) {
                coreSearchForQuery(searchStr);
            }
        }
    }

    public void coreSearchForQuery(String searchQuery) {
        boolean z = true;
        SearchLocationData currentItem = this.mWaypointsAdapter.getCurrentItem();
        SearchResultsListView searchResultsListView = this.mResultsList;
        if (!TextUtils.isEmpty(searchQuery) || currentItem == null || currentItem.getSearchSubtype() > 1) {
            z = false;
        }
        searchResultsListView.enableHistory(z);
        this.mResultsList.resetScroll();
        this.mSearchResultsAdapter.queryCoreSearch(searchQuery);
    }

    public void onStartSearch() {
        this.mResultsList.setSelectionAfterHeaderView();
    }

    public void onTickFinished(int loaded) {
        showResultsList(loaded > 0);
    }

    public void onLoadingFinished(boolean hasResults) {
        VerticalExpandingAnimator.animateView(this.mWaitOverlay, false);
        if (this.mSearchResultsAdapter.isAmericaSearchActive()) {
            SearchLocationData locationData;
            if (hasResults) {
                locationData = this.mWaypointsAdapter.getCurrentItem();
                if (locationData != null) {
                    locationData.notifyObservers(3);
                }
            } else {
                String text = null;
                locationData = this.mWaypointsAdapter.getCurrentItem();
                ListItem houseNoItem = locationData.getItem(2);
                initQuickSearch(locationData.getSearchItem(1));
                if (houseNoItem != null) {
                    text = houseNoItem.getDisplayName();
                    locationData.removeItem(2, true, true);
                }
                if (TextUtils.isEmpty(text)) {
                    coreSearchForQuery(text);
                    return;
                }
                return;
            }
        }
        showResultsList(hasResults);
    }

    public void zoomOnAddress(MapSelection mapSel) {
        SearchBox.nativeShowOnMap(mapSel);
        this.mClearFocusView.requestFocus();
        this.mWaypointsAdapter.setCurrentItemPosition(-1);
        InfinarioAnalyticsLogger.getInstance(getActivity()).trackAppActionSearchedAddress();
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void onDelimiterPressed(int r11, java.lang.String r12) {
        /*
        r10 = this;
        r1 = 2;
        r2 = 0;
        r0 = r10.mSearchResultsAdapter;
        r7 = r0.getCount();
        r3 = 0;
        r0 = android.text.TextUtils.isEmpty(r12);
        if (r0 == 0) goto L_0x0059;
    L_0x000f:
        r0 = r10.mWaypointsAdapter;
        r9 = r0.getCurrentItem();
        if (r9 == 0) goto L_0x0037;
    L_0x0017:
        r0 = r9.isHouseFirst();
        if (r0 == 0) goto L_0x0037;
    L_0x001d:
        r8 = r9.getItem(r1);
        if (r8 == 0) goto L_0x0037;
    L_0x0023:
        r9.removeItem(r1, r2);
        r9.shiftToPreviousSubtype();
        r0 = r8.getDisplayName();
        r1 = r11 + -1;
        r1 = java.lang.Math.max(r2, r1);
        r10.startHouseNumberMatching(r0, r1);
    L_0x0036:
        return;
    L_0x0037:
        r10.showResultsList(r2);
        r0 = r10.getView();
        r0 = r0.getWindowToken();
        com.sygic.aura.activity.NaviNativeActivity.hideKeyboard(r0);
        if (r9 == 0) goto L_0x0036;
    L_0x0047:
        r6 = r9.getPreviousSrchSubtype();
        r0 = r9.isNonEmpty(r6);
        if (r0 == 0) goto L_0x0036;
    L_0x0051:
        r0 = r9.getItemMapSel(r6);
        r10.zoomOnAddress(r0);
        goto L_0x0036;
    L_0x0059:
        if (r7 <= r3) goto L_0x0072;
    L_0x005b:
        r0 = r10.mSearchResultsAdapter;
        r8 = r0.getItem(r3);
        r0 = com.sygic.aura.search.model.data.SpecialSearchItem.isSpecial(r8);
        if (r0 == 0) goto L_0x0072;
    L_0x0067:
        r0 = com.sygic.aura.search.model.data.SpecialSearchItem.ItemType.ITEM_HOUSE_NUMBER;
        r0 = com.sygic.aura.search.model.data.SpecialSearchItem.isSpecialType(r8, r0);
        if (r0 != 0) goto L_0x0072;
    L_0x006f:
        r3 = r3 + 1;
        goto L_0x0059;
    L_0x0072:
        if (r7 <= r3) goto L_0x009e;
    L_0x0074:
        r0 = r10.mSearchResultsAdapter;
        r8 = r0.getItem(r3);
        r0 = r8.getDisplayName();
        r0 = com.sygic.aura.resources.ResourceManager.nativeFindMatch(r0, r12);
        if (r0 == 0) goto L_0x009a;
    L_0x0084:
        r0 = r10.mResultsList;
        r0 = r0.getHeaderViewsCount();
        r3 = r3 + r0;
        r1 = r10.mResultsList;
        r0 = r10.mResultsList;
        r2 = r0.getChildAt(r3);
        r4 = 0;
        r0 = r10;
        r0.onItemClick(r1, r2, r3, r4);
        goto L_0x0036;
    L_0x009a:
        r10.startHouseNumberMatching(r12, r11);
        goto L_0x0036;
    L_0x009e:
        r10.startHouseNumberMatching(r12, r11);
        goto L_0x0036;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.sygic.aura.search.fragment.SearchFragment.onDelimiterPressed(int, java.lang.String):void");
    }

    private void startHouseNumberMatching(String text, int searchSubType) {
        if (!resolveHouseNumber(text) && !tryAmericaSearch(text, searchSubType)) {
            startQuickSearch(text);
        }
    }

    private boolean resolveHouseNumber(String text) {
        return resolveHouseNumber(text, true);
    }

    private boolean resolveHouseNumber(String text, boolean showOutOfRangeHint) {
        return resolveHouseNumber(null, text, showOutOfRangeHint);
    }

    private boolean resolveHouseNumber(SearchItem streetItem, String text, boolean showOutOfRangeHint) {
        int i = 0;
        while (i < text.length() && Character.isDigit(text.charAt(i))) {
            i++;
        }
        if (i > 0) {
            try {
                int houseNumber = Integer.parseInt(text.substring(0, i));
                boolean hasStreet = streetItem != null;
                if (hasStreet) {
                    long treeEntry = streetItem.getTreeEntry();
                    if (treeEntry != 0) {
                        this.mLocationQuery.setStreetEntry(treeEntry);
                    }
                }
                HouseNumberEntry result = this.mLocationQuery.getHouseNumberNavSel(houseNumber);
                if (result != null && result.isValid()) {
                    String bubbleText = text;
                    ViewHolder row = getCurrentWaypointHolder();
                    if (houseNumber != result.mHouseNumber && showOutOfRangeHint) {
                        bubbleText = Integer.toString(result.mHouseNumber);
                        String message = ResourceManager.getCoreString(getContext(), 2131165675).replace("%house_number%", bubbleText);
                        PopupAction quick = new PopupAction(getContext(), 0);
                        quick.setAnimStyle(4);
                        quick.addActionItem(new ActionItem(1, message, null));
                        quick.setDismissTimer(4000);
                        quick.show(row.mSearchFrame);
                    }
                    if (hasStreet) {
                        SearchLocationData currentItem = this.mWaypointsAdapter.getCurrentItem();
                        currentItem.setSelectedItem(streetItem, currentItem.getPreviousSrchSubtype());
                        currentItem.shiftToNextSubtype();
                    }
                    this.mWaypointsAdapter.addItemToCurrentFrame(new HouseNumberSearchItem(bubbleText, result.mMapSel), true);
                    row.mSearchFrame.setSearchFinished(true);
                    return true;
                }
            } catch (NumberFormatException e) {
                e.printStackTrace();
                return false;
            }
        }
        return false;
    }

    public void onSearchCanceled(boolean wasFinished) {
        if (!wasFinished) {
            VerticalExpandingAnimator.animateView(this.mWaitOverlay, false);
        }
    }

    private boolean tryAmericaSearch(String text, int searchSubType) {
        SearchLocationData locationData = this.mWaypointsAdapter.getCurrentItem();
        if (searchSubType != 2 || HouseNumberSearchItem.parseNumber(text) == -1 || !PositionInfo.nativeIsUsaCountry(locationData.getSearchItem(0))) {
            return false;
        }
        this.mWaypointsAdapter.addItemToCurrentFrame(new HouseNumberSearchItem(text, locationData.getCurrentItem().getMapSel()), false, true);
        return true;
    }

    private void initQuickSearch() {
        SearchLocationData locationData = this.mWaypointsAdapter.getCurrentItem();
        if (locationData != null) {
            initQuickSearch(locationData.getCurrentSearchItem());
        }
    }

    private void initQuickSearch(SearchItem locationItem) {
        this.mSearchResultsAdapter.initCoreSearch(10, locationItem, null);
        VerticalExpandingAnimator.animateView(this.mWaitOverlay, true);
        this.mSearchResultsAdapter.clear();
        showResultsList(true);
    }

    private void startQuickSearch(String text) {
        initQuickSearch();
        coreSearchForQuery(text);
    }

    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        this.mSearchResultsAdapter.cancelCoreSearchLoading();
        SearchItem item = (SearchItem) parent.getItemAtPosition(position);
        if (item != null && item.checkMapSelection(this.mLocationQuery)) {
            SearchType eSearchType = item.getSearchType();
            SygicAnalyticsLogger.getAnalyticsEvent(getActivity(), EventType.SEARCH).setName("search").setValue("click", "list_item_clicked").logAndKeep().clearParams().setValue("list_item_clicked", eSearchType.name()).logAndRecycle();
            SygicAnalyticsLogger.getAnalyticsEvent(getContext(), EventType.SEARCH).setName("search").setValue("click", "list_item_clicked").logAndKeep().clearParams().setValue("list_item_clicked", eSearchType.name()).logAndRecycle();
            switch (C15676.f1275x69b2bbd3[eSearchType.ordinal()]) {
                case TTSConst.TTSMULTILINE /*1*/:
                    if ((item instanceof CityPostalSearchItem) && ((CityPostalSearchItem) item).isPostalPositionAddress()) {
                        this.mWaypointsAdapter.addItemToCurrentFrame(item, true);
                        setSearchFinished();
                        return;
                    }
                    this.mWaypointsAdapter.addItemToCurrentFrame(item, false);
                case TTSConst.TTSPARAGRAPH /*2*/:
                    if ((item instanceof StreetSearchItem) && ((StreetSearchItem) item).isCityCenter()) {
                        this.mWaypointsAdapter.addItemToCurrentFrame(item, true);
                        setSearchFinished();
                        return;
                    }
                    ListItem no = this.mWaypointsAdapter.getCurrentItem().getItem(2);
                    if (no == null || !(no instanceof HouseNumberSearchItem)) {
                        this.mWaypointsAdapter.addItemToCurrentFrame(item, false);
                    } else {
                        resolveHouseNumber(item, no.getDisplayName(), true);
                    }
                case TTSConst.TTSUNICODE /*3*/:
                    performSpecialTypeAction((SpecialSearchItem) item, view);
                case TTSConst.TTSXML /*4*/:
                    QuickSearchItem quickSearchItem = (QuickSearchItem) item;
                    if (quickSearchItem.isSubmenuItem()) {
                        this.mSearchResultsAdapter.cancelCoreSearchLoading();
                        FragmentManagerInterface manager = SygicHelper.getFragmentActivityWrapper();
                        if (manager != null) {
                            NaviNativeActivity.hideKeyboard(view.getWindowToken());
                            if (ConnectionManager.nativeIsConnected() || !quickSearchItem.isConnectivityRequired()) {
                                Bundle bundle = new Bundle();
                                bundle.putString(AbstractFragment.ARG_TITLE, item.getDisplayName());
                                bundle.putSerializable("item_type", quickSearchItem.getQuickItemType());
                                bundle.putLong("item_object", item.getTreeEntry());
                                manager.addFragment(QuickSearchFragment.class, "quick_search", true, this, bundle);
                                return;
                            }
                            manager.addFragment(PoiNoConnectivityFragment.class, "poi_no_connectivity", true);
                            return;
                        }
                        return;
                    }
                    switch (C15676.f1274x44b36741[quickSearchItem.getQuickItemType().ordinal()]) {
                        case TTSConst.TTSMULTILINE /*1*/:
                            showPoiDetail(quickSearchItem);
                        default:
                            addAddressSegments(item.getMapSel());
                    }
                default:
                    this.mWaypointsAdapter.addItemToCurrentFrame(item);
            }
        }
    }

    private void performSpecialTypeAction(SpecialSearchItem item, View view) {
        switch (C15676.f1276xfef9d2f5[item.getSpecialItemType().ordinal()]) {
            case TTSConst.TTSMULTILINE /*1*/:
            case TTSConst.TTSPARAGRAPH /*2*/:
                FragmentManagerInterface manager = SygicHelper.getFragmentActivityWrapper();
                if (manager != null) {
                    Bundle bundle = new Bundle();
                    bundle.putParcelable(PoiFragment.ARG_DATA, this.mWaypointsAdapter.getCurrentItem());
                    manager.addFragment(NearbyPoiCategoryFragment.class, "fragment_nearby_dash", true, this, bundle);
                }
                NaviNativeActivity.hideKeyboard(view.getWindowToken());
            case TTSConst.TTSUNICODE /*3*/:
                startQuickSearch(this.mSearchResultsAdapter.getLastSearchText());
            case TTSConst.TTSXML /*4*/:
                FragmentManagerInterface mngr = SygicHelper.getFragmentActivityWrapper();
                if (mngr != null) {
                    mngr.addFragment(GpsCoordinatesFragment.class, "fragment_gps_search_tag", true, this);
                }
                NaviNativeActivity.hideKeyboard(view.getWindowToken());
            case TTSConst.TTSEVT_TEXT /*5*/:
                String text = item.getDisplayName();
                if (!resolveHouseNumber(text) && !tryAmericaSearch(text, 2)) {
                    Log.d("SearchFragment", "Unhandled special subtype");
                }
            default:
                Log.d("SearchFragment", "Unhandled special subtype");
        }
    }

    public boolean dismissOnFinish() {
        return false;
    }

    public void onPoiFragmentResult(PoiListItem item) {
        NaviNativeActivity activity = (NaviNativeActivity) getActivity();
        if (item != null) {
            activity.onPoiFragmentResult(item);
        } else if (!activity.clearBackStackRunning()) {
            reinitializeCurrentInput();
        }
    }

    public void onQuickSearchFragmentResult(QuickSearchItem item) {
        if (item != null) {
            switch (C15676.f1274x44b36741[item.getQuickItemType().ordinal()]) {
                case TTSConst.TTSMULTILINE /*1*/:
                    showPoiDetail(item);
                case TTSConst.TTSPARAGRAPH /*2*/:
                case TTSConst.TTSUNICODE /*3*/:
                    this.mWaypointsAdapter.addItemToCurrentFrame(item, true);
                    setSearchFinished();
                case TTSConst.TTSXML /*4*/:
                    Bundle bundle = new Bundle();
                    bundle.putString(AbstractFragment.ARG_TITLE, item.getDisplayName());
                    bundle.putString("uri", ((OnlineSearchItem) item).getLink());
                    Fragments.add(getActivity(), WebViewFragment.class, "fragment_webview", bundle);
                default:
                    addAddressSegments(item.getMapSel());
            }
        }
    }

    private void showPoiDetail(QuickSearchItem item) {
        showPoiDetail(item.getMapSel().getData(), item.getDisplayName(), item.getMapSel(), EItemType.None);
    }

    private void showPoiDetail(long poiId, String name, MapSelection mapsel, EItemType type) {
        Bundle poiData = new Bundle();
        poiData.putLong(PoiDetailFragment.POI_ID, poiId);
        poiData.putString(PoiDetailFragment.POI_TITLE, name);
        poiData.putParcelable(PoiDetailFragment.POI_SEL, mapsel);
        poiData.putSerializable(PoiDetailFragment.POI_ONLINE_TYPE, type);
        FragmentManagerInterface manager = SygicHelper.getFragmentActivityWrapper();
        if (manager != null) {
            manager.addFragment(PoiDetailFragment.class, "fragment_poi_detail_tag", true, (FragmentResultCallback) manager, poiData);
        }
        InfinarioAnalyticsLogger.getInstance(getActivity()).trackAppActionSearchedPoi();
    }

    public void onQuickSearchFragmentResult(MapSelection mapSelection) {
        if (mapSelection != null) {
            addAddressSegments(mapSelection);
        }
    }

    public void onFavoritesFragmentResult(FavoritesItem result) {
        if (result == null) {
            reinitializeCurrentInput();
        } else if (PositionInfo.nativeHasNavSel(result.getLongPosition())) {
            addAddressSegments(result.getMapSel());
        } else {
            SToast.makeText(getContext(), 2131165396, 1).show();
        }
    }

    public void onMapsAvailabilityChanged(Boolean mapsAvailable) {
        SToast.makeText(getActivity(), 2131165677, 1).show();
        NaviNativeActivity.hideKeyboard(this.mWaypointsList.getWindowToken());
        SygicHelper.getFragmentActivityWrapper().clearBackStack(false);
    }

    private ViewHolder getCurrentWaypointHolder() {
        View child = this.mWaypointsList.getViewAt(this.mWaypointsAdapter.getCurrentItemPosition());
        return child != null ? (ViewHolder) child.getTag() : null;
    }

    private void setSearchFinished() {
        ViewHolder row = getCurrentWaypointHolder();
        if (row != null) {
            row.mSearchFrame.setSearchFinished(true);
        }
    }

    private void addAddressSegments(MapSelection mapSelection) {
        SearchLocationData locationData = this.mWaypointsAdapter.getCurrentItem();
        if (locationData != null) {
            locationData.clearAllLocationData();
            locationData.setAsTerminalBubble(true);
            ListItem[] addressSegments = PositionInfo.nativeGetPositionSearchEntries(mapSelection);
            if (addressSegments == null) {
                return;
            }
            if (addressSegments.length == 1 && addressSegments[0].getMapSel() == null) {
                Toast.makeText(getContext(), ResourceManager.getCoreString(getContext(), 2131165669), 1).show();
                return;
            }
            for (ListItem item : addressSegments) {
                locationData.addItem(item);
            }
            setSearchFinished();
            this.mResultsList.smoothScrollToPosition(0);
            NaviNativeActivity.hideKeyboard(this.mWaypointsList.getWindowToken());
        }
    }
}
