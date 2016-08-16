package com.sygic.aura.route.data;

import com.sygic.aura.search.model.data.SearchLocationData;

public interface RouteNavigateDataCallback {
    void computeRouteReady();

    void resetRouteReady(SearchLocationData searchLocationData);
}
