package com.sygic.aura.search.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import com.sygic.aura.activity.NaviNativeActivity;
import com.sygic.aura.fragments.interfaces.FragmentManagerInterface;
import com.sygic.aura.fragments.interfaces.SearchInterface;
import com.sygic.aura.helper.SygicHelper;
import com.sygic.aura.map.PositionInfo;
import com.sygic.aura.map.data.map_selection.MapSelection;
import com.sygic.aura.navigate.fragment.NavigateFragment;
import com.sygic.aura.route.RouteManager;
import com.sygic.aura.route.data.RouteNavigateData;
import com.sygic.aura.route.fragment.RouteSelectionFragment;
import com.sygic.aura.search.model.RouteWaypointsAdapter;
import com.sygic.aura.search.model.RouteWaypointsAdapterOnRoute;
import com.sygic.aura.search.model.data.ListItem;
import com.sygic.aura.search.model.data.SearchLocationData;
import com.sygic.aura.showcase.ShowcaseView;
import com.sygic.aura.showcase.ShowcaseView.OnProceedListener;
import com.sygic.aura.showcase.targets.ToolbarItemTarget;

public class SearchOnRouteFragment extends SearchFragment {
    private SearchLocationData mLocationData;
    private RouteNavigateData mTmpRouteData;

    /* renamed from: com.sygic.aura.search.fragment.SearchOnRouteFragment.1 */
    class C15681 implements Runnable {
        final /* synthetic */ MapSelection val$mapSel;

        C15681(MapSelection mapSelection) {
            this.val$mapSel = mapSelection;
        }

        public void run() {
            RouteManager.nativePassBy(this.val$mapSel);
        }
    }

    /* renamed from: com.sygic.aura.search.fragment.SearchOnRouteFragment.2 */
    class C15692 implements Runnable {
        final /* synthetic */ MapSelection val$mapSel;

        C15692(MapSelection mapSelection) {
            this.val$mapSel = mapSelection;
        }

        public void run() {
            RouteManager.nativeTravelVia(this.val$mapSel);
        }
    }

    /* renamed from: com.sygic.aura.search.fragment.SearchOnRouteFragment.3 */
    class C15703 implements OnProceedListener {
        final /* synthetic */ Activity val$activity;

        C15703(Activity activity) {
            this.val$activity = activity;
        }

        public void onProceed() {
            new ShowcaseView(this.val$activity).setContentTitle(2131165909).setContentText(2131165908).setTarget(new ToolbarItemTarget(SearchOnRouteFragment.this.mToolbar, 2131624694)).setScale(0.5f).show();
        }
    }

    protected int getMenuResId() {
        return 2131755029;
    }

    public void onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
        MenuItem item = menu.findItem(2131624693);
        if (item != null) {
            item.setEnabled(this.mRouteReady);
        }
        item = menu.findItem(2131624694);
        if (item != null) {
            item.setEnabled(this.mRouteReady);
        }
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        View view;
        if (id == 2131624693) {
            view = getView();
            FragmentManagerInterface mngr = SygicHelper.getFragmentActivityWrapper();
            if (view == null || mngr == null || mngr.clearBackStackRunning()) {
                return true;
            }
            NavigateFragment.nativeCancelRoute();
            this.mRouteNavigateData.updateWaypointsArray(this.mTmpRouteData.getWaypointsList());
            NaviNativeActivity.hideKeyboard(view.getWindowToken());
            Bundle bundle = new Bundle(1);
            bundle.putBoolean("can_show_tooltip", false);
            FragmentManager fragmentManager = getFragmentManager();
            mngr.clearBackStack(fragmentManager.findFragmentByTag("fragment_route_selection_tag") == null ? null : "fragment_route_selection_tag", true, 1);
            if (fragmentManager.getBackStackEntryCount() == 0) {
                mngr.addFragment(RouteSelectionFragment.class, "fragment_route_selection_tag", true, null, bundle);
                return true;
            }
            mngr.replaceFragment(RouteSelectionFragment.class, "fragment_route_selection_tag", true, bundle);
            return true;
        }
        if (id == 2131624694) {
            view = getView();
            ListItem currentItem = this.mLocationData.getCurrentItem();
            if (!(view == null || currentItem == null)) {
                NaviNativeActivity.hideKeyboard(view.getWindowToken());
                performHomeAction();
                MapSelection mapSel = currentItem.getMapSel();
                if (PositionInfo.nativeIsCityCenter(mapSel)) {
                    view.postDelayed(new C15681(mapSel), 200);
                    return true;
                }
                view.postDelayed(new C15692(mapSel), 200);
                return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }

    protected ShowcaseView getShowcaseView() {
        Activity activity = getActivity();
        return new ShowcaseView(activity, getShowcaseId()).setContentTitle(2131165907).setContentText(2131165906).setTarget(new ToolbarItemTarget(this.mToolbar, 2131624693)).setScale(0.5f).setOnProceedListener(new C15703(activity));
    }

    protected String getShowcaseId() {
        return getString(2131166719);
    }

    protected void initRouteReady() {
        int i = 0;
        this.mTmpRouteData = new RouteNavigateData();
        MapSelection mapSelection = (MapSelection) getArguments().getParcelable("map_selection");
        if (mapSelection != null) {
            ListItem[] addressSegments = PositionInfo.nativeGetPositionSearchEntries(mapSelection);
            this.mLocationData = this.mTmpRouteData.getWaypoint(this.mTmpRouteData.insertEmptyWaypoint(0));
            this.mLocationData.clearAllLocationData();
            this.mLocationData.setAsTerminalBubble(true);
            int length = addressSegments.length;
            while (i < length) {
                this.mLocationData.setSelectedItem(addressSegments[i]);
                this.mLocationData.shiftToNextSubtype();
                i++;
            }
        } else {
            this.mLocationData = this.mTmpRouteData.getWaypoint(this.mTmpRouteData.insertNewWaypoint());
        }
        this.mTmpRouteData.registerObserver((SearchInterface) this);
        this.mTmpRouteData.computeRouteReady();
    }

    protected RouteWaypointsAdapter getWaypointsAdapter() {
        if (this.mTmpRouteData == null) {
            initRouteReady();
        }
        return new RouteWaypointsAdapterOnRoute(this, this.mTmpRouteData);
    }

    public void onDestroy() {
        super.onDestroy();
        this.mTmpRouteData.destroy();
    }
}
