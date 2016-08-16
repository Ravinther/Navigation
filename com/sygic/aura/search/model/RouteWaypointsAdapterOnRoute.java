package com.sygic.aura.search.model;

import com.sygic.aura.fragments.interfaces.SearchInterface;
import com.sygic.aura.route.data.RouteNavigateData;
import com.sygic.aura.search.view.SearchFrame;

public class RouteWaypointsAdapterOnRoute extends RouteWaypointsAdapter {
    public RouteWaypointsAdapterOnRoute(SearchInterface searchIF, RouteNavigateData routeNaviData) {
        super(searchIF, routeNaviData);
    }

    protected int getWaypointLabel(boolean isStartFromCurrent, int position, int max) {
        return 2130838223;
    }

    public void updateSearchFrame(SearchFrame frame, int position, boolean requestFocus) {
        super.updateSearchFrame(frame, position, requestFocus);
        frame.hideExpandButton();
    }
}
