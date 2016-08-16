package com.sygic.aura.dashboard.plugins;

import android.content.Context;
import com.sygic.aura.dashboard.DashboardPlugin;
import com.sygic.aura.dashboard.WidgetItem;
import com.sygic.aura.dashboard.fragment.DashboardFragment;
import com.sygic.aura.fragments.interfaces.FragmentManagerInterface;
import com.sygic.aura.helper.SToast;
import com.sygic.aura.helper.SygicHelper;
import com.sygic.aura.map.PositionInfo;
import com.sygic.aura.route.RouteSummary;
import com.sygic.aura.route.data.RouteNavigateData;
import com.sygic.aura.route.fragment.RouteSelectionFragment;
import com.sygic.aura.search.model.data.ListItem;
import com.sygic.aura.search.model.data.SearchLocationData;

public abstract class NavigableDashPlugin extends DashboardPlugin {
    protected NavigableDashPlugin(WidgetItem widgetItem) {
        super(widgetItem);
    }

    public void performAction(DashboardFragment dashboardFragment) {
        navigate(dashboardFragment);
    }

    protected void navigate(DashboardFragment dashboardFragment) {
        FragmentManagerInterface manager = SygicHelper.getFragmentActivityWrapper();
        if (manager != null) {
            manager.clearBackStack(true);
            RouteSummary.nativeCancelRoute();
            if (setSearchBarLocationData(dashboardFragment)) {
                manager.addFragment(RouteSelectionFragment.class, "fragment_route_selection_tag", true, null, null, 17432576, 17432577);
            }
        }
    }

    private boolean setSearchBarLocationData(DashboardFragment dashboardFragment) {
        RouteNavigateData routeNavigateData = dashboardFragment.getRouteNavigateData();
        routeNavigateData.clearRouteWaypoints();
        return fillNavigateData(dashboardFragment.getActivity(), routeNavigateData);
    }

    protected boolean fillNavigateData(Context context, RouteNavigateData routeNavigateData) {
        if (PositionInfo.nativeHasNavSel(getLongPosition())) {
            SearchLocationData locationData = routeNavigateData.getWaypoint(routeNavigateData.insertEmptyWaypoint(0));
            for (ListItem item : getAddressSegments()) {
                locationData.addItem(item);
            }
            locationData.setAsTerminalBubble(true);
            return true;
        }
        SToast.makeText(context, 2131165396, 1).show();
        return false;
    }
}
