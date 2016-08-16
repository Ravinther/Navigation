package com.sygic.aura.search.model;

public interface RoutePointChangeObserver {
    void onRoutePointAdded(int i);

    void onRoutePointCleared();

    void onRoutePointRemoved(int i);
}
