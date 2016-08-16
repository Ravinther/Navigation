package com.sygic.aura.search.model.data;

import android.text.TextUtils;
import com.sygic.aura.dashboard.DashboardPlugin;
import com.sygic.aura.dashboard.WidgetManager;
import com.sygic.aura.dashboard.plugins.RouteDashPlugin;
import com.sygic.aura.map.data.map_selection.MapSelection;

public class RouteListItem extends BookmarksListItem {
    protected String mStrItinerary;

    public RouteListItem(String strName, String strExtName, String strItineraryPath, MapSelection mapSel, long memoId, int iconType) {
        super(strName, strExtName, mapSel, memoId, iconType, 0);
        this.mStrItinerary = strItineraryPath;
    }

    public String getItinerarPath() {
        return this.mStrItinerary;
    }

    public String toString() {
        return getDisplayName();
    }

    public String getExtName() {
        if (TextUtils.isEmpty(super.getExtName())) {
            return WidgetManager.nativeGetRouteItineraryPath(this.mMemoId);
        }
        return super.getExtName();
    }

    protected int getIconResId() {
        return 2131034186;
    }

    public DashboardPlugin createDashPlugin() {
        return RouteDashPlugin.newInstance(null);
    }
}
