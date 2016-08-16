package com.sygic.aura.dashboard.plugins;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import com.sygic.aura.dashboard.DashboardPlugin;
import com.sygic.aura.dashboard.WidgetItem;
import com.sygic.aura.dashboard.WidgetItem.EWidgetSize;
import com.sygic.aura.dashboard.WidgetItem.EWidgetType;
import com.sygic.aura.dashboard.fragment.DashboardFragment;
import com.sygic.aura.fragments.interfaces.FragmentManagerInterface;
import com.sygic.aura.helper.SygicHelper;
import com.sygic.aura.route.RouteSummary;
import com.sygic.aura.route.fragment.RouteSelectionFragment;
import com.sygic.aura.search.model.data.ListItem;
import com.sygic.aura.search.model.data.RouteListItem;

public class RouteDashPlugin extends MemoDashPlugin {
    public static DashboardPlugin newInstance(WidgetItem widgetItem) {
        if (widgetItem == null) {
            widgetItem = new WidgetItem(EWidgetType.widgetTypeFavoriteRoute, EWidgetSize.widgetSizeHalfRow);
        }
        return new RouteDashPlugin(widgetItem);
    }

    private RouteDashPlugin(WidgetItem widgetItem) {
        super(widgetItem);
    }

    public int getPluginImage() {
        return 2131034164;
    }

    protected void updateAddressSegments() {
        this.mAddressSegments.clear();
        this.mAddressSegments.add(new RouteListItem(this.mWidgetItem.getName(), this.mWidgetItem.getExtName(), "", null, this.mWidgetItem.getMemoId(), 0));
    }

    protected void setAddressSegments(ListItem selectedItem) {
        this.mAddressSegments.add(selectedItem);
    }

    protected void navigate(DashboardFragment dashboardFragment) {
        FragmentManagerInterface manager = SygicHelper.getFragmentActivityWrapper();
        FragmentManager fragmentManager = SygicHelper.getFragmentManager();
        if (manager != null && fragmentManager != null) {
            RouteSummary.nativeCancelRoute();
            dashboardFragment.getRouteNavigateData().clearRouteWaypoints();
            dashboardFragment.getNavigationDrawer().toggleDrawer();
            Bundle bundle = new Bundle();
            bundle.putString("itinerary", this.mWidgetItem.getExtName());
            manager.clearBackStack(fragmentManager.findFragmentByTag("fragment_route_selection_tag") == null ? null : "fragment_route_selection_tag", true, 1);
            if (fragmentManager.getBackStackEntryCount() == 0) {
                manager.addFragment(RouteSelectionFragment.class, "fragment_route_selection_tag", true, null, bundle);
            } else {
                manager.replaceFragment(RouteSelectionFragment.class, "fragment_route_selection_tag", true, bundle);
            }
        }
    }
}
