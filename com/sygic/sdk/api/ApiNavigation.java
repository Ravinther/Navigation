package com.sygic.sdk.api;

import com.sygic.sdk.api.exception.GeneralException;
import com.sygic.sdk.api.exception.GpsException;
import com.sygic.sdk.api.exception.NavigationException;
import com.sygic.sdk.api.model.GpsPosition;
import com.sygic.sdk.api.model.RouteInfo;
import com.sygic.sdk.api.model.WayPoint;

public class ApiNavigation {
    public static GpsPosition getActualGpsPosition(boolean satInfo, int maxTime) throws GpsException {
        return Api.nGetActualGpsPosition(satInfo, maxTime);
    }

    public static RouteInfo getRouteInfo(boolean extended, int maxTime) throws GeneralException {
        return Api.nGetRouteInfo(extended, maxTime);
    }

    public static void startNavigation(WayPoint location, int flags, boolean searchAddress, int maxTime) throws NavigationException {
        Api.nStartNavigation(location, flags, true, searchAddress, maxTime);
    }

    public static void stopNavigation(int maxTime) throws GeneralException {
        Api.nStopNavigation(maxTime);
    }

    public static void navigateToAddress(String strAddress, boolean postal, int flags, int maxTime) throws NavigationException {
        Api.nNavigateToAddress(strAddress, postal, flags, true, maxTime);
    }

    public static void loadComputedRoute(String routePath, int startWaypointId, int maxTime) throws GeneralException {
        Api.nLoadComputedRoute(routePath, startWaypointId, maxTime);
    }

    public static void saveComputedRoute(String routePath, int maxTime) throws GeneralException {
        Api.nSaveComputedRoute(routePath, maxTime);
    }
}
