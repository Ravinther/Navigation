package com.sygic.sdk.api;

import com.sygic.sdk.api.exception.GeneralException;
import com.sygic.sdk.api.exception.GpsException;
import com.sygic.sdk.api.exception.InvalidLocationException;
import com.sygic.sdk.api.exception.InvalidNameException;
import com.sygic.sdk.api.exception.NavigationException;
import com.sygic.sdk.api.model.GpsPosition;
import com.sygic.sdk.api.model.HistoryRecord;
import com.sygic.sdk.api.model.NaviVersion;
import com.sygic.sdk.api.model.OnlineServicesSettings;
import com.sygic.sdk.api.model.Options;
import com.sygic.sdk.api.model.Poi;
import com.sygic.sdk.api.model.PoiCategory;
import com.sygic.sdk.api.model.Position;
import com.sygic.sdk.api.model.Rectangle;
import com.sygic.sdk.api.model.RoadInfo;
import com.sygic.sdk.api.model.RouteInfo;
import com.sygic.sdk.api.model.RouteInstruction;
import com.sygic.sdk.api.model.StopOffPoint;
import com.sygic.sdk.api.model.TmcEvent;
import com.sygic.sdk.api.model.WayPoint;

public class Api {
    static native int nAddBitmapToMap(String str, int i, int i2, int i3) throws GeneralException;

    static native void nAddEntryToItinerary(String str, StopOffPoint stopOffPoint, int i, int i2) throws GeneralException;

    static native void nAddItinerary(StopOffPoint[] stopOffPointArr, int i, String str, int i2) throws GeneralException;

    static native String nAddPoi(Poi poi, int i) throws InvalidLocationException;

    static native void nAddPoiCategory(String str, String str2, String str3, int i) throws GeneralException;

    static native String nAddStopOffPointsToFavorites(Poi poi, int i) throws InvalidLocationException;

    static native TmcEvent nAddTMCEvent(TmcEvent tmcEvent, int i) throws GeneralException;

    static native Options nChangeApplicationOptions(Options options, int i) throws GeneralException;

    static native void nClearTMCTable(int i) throws GeneralException;

    static native int nCloseApi();

    static native void nCloseDialogs(int i) throws GeneralException;

    static native void nDeleteEntryInItinerary(String str, int i, int i2) throws GeneralException;

    static native void nDeleteItinerary(String str, int i) throws GeneralException;

    static native String nDeletePoi(Poi poi, int i) throws InvalidLocationException;

    static native void nDeletePoiCategory(String str, String str2, int i) throws GeneralException;

    static native void nDisableExternalGpsInput(int i) throws GeneralException;

    static native void nEnableExternalGpsInput(int i) throws GeneralException;

    static native void nEndApplication(int i) throws GeneralException;

    static native int nErrorReport(int i) throws GeneralException;

    static native Poi nFindNearbyPoi(int i, String str, int i2, int i3, int i4) throws GeneralException;

    static native Poi[] nFindNearbyPoi2(int i, String str, int i2, int i3, int i4, int i5) throws GeneralException;

    static native void nFlashMessage(String str, boolean z, int i) throws GeneralException;

    static native GpsPosition nGetActualGpsPosition(boolean z, int i) throws GpsException;

    static native WayPoint[] nGetAddressList(String str, boolean z, int i, int i2) throws GeneralException;

    static native NaviVersion nGetApplicationVersion(int i) throws GeneralException;

    static native Position nGetCoordinatesFromOffset(String str, long j, int i) throws GeneralException;

    static native int nGetCurrentSpeedLimit(int i) throws GeneralException;

    static native Poi[] nGetFavoritesList(boolean z, int i) throws GeneralException;

    static native HistoryRecord[] nGetHistoryList(boolean z, int i) throws GeneralException;

    static native StopOffPoint[] nGetItineraryList(String str, int i) throws GeneralException;

    static native String nGetLocationInfo(Position position, int i) throws InvalidLocationException;

    static native RoadInfo nGetLocationInfo2(Position position, int i) throws InvalidLocationException;

    static native String nGetMapVersion(String str, int i) throws GeneralException;

    static native RouteInstruction nGetNextInstruction(int i) throws GeneralException;

    static native PoiCategory[] nGetPoiCategoryList(int i) throws GeneralException;

    static native Poi[] nGetPoiList(String str, boolean z, int i) throws GeneralException;

    static native RouteInfo nGetRouteInfo(boolean z, int i) throws GeneralException;

    static native String nGetUniqueDeviceId(int i) throws GeneralException;

    static native void nGoOnline(boolean z, int i) throws GeneralException;

    static native void nGpsSwitchOn(String str, int i, boolean z, int i2) throws GeneralException;

    static native void nHighlightPoi(String str, String str2, String str3, Position position, int i) throws GeneralException;

    static native void nInitJavaObjects(ApiCallback apiCallback);

    static native boolean nIsApplicationInForeground(int i) throws GeneralException;

    static native boolean nIsApplicationRunning(int i) throws GeneralException;

    static native void nLoadComputedRoute(String str, int i, int i2) throws GeneralException;

    static native void nLoadExternalFile(String str, int i, int i2) throws GeneralException;

    static native void nLoadGFFile(String str, int i) throws GeneralException;

    static native Position nLocationFromAddress(String str, boolean z, boolean z2, int i) throws GeneralException;

    static native WayPoint[] nLocationFromAddressEx(String str, boolean z, int i) throws GeneralException;

    static native WayPoint[] nLocationFromAddressEx2(String str, boolean z, boolean z2, int i) throws GeneralException;

    static native void nMakeUserPoiVisible(int i, String str, boolean z, int i2) throws GeneralException;

    static native void nMoveBitmap(int i, int i2, int i3, int i4) throws GeneralException;

    static native void nNavigateToAddress(String str, boolean z, int i, boolean z2, int i2) throws NavigationException;

    static native void nOnMenuCommand(int i, int i2, boolean z, int i3) throws GeneralException;

    static native void nOnlineServicesLogin(String str, String str2, int i) throws GeneralException;

    static native OnlineServicesSettings nOnlineServicesSettings(int i) throws GeneralException;

    static native void nPlaySoundTTS(String str, int i) throws GeneralException;

    static native void nReloadExternalFiles(int i) throws GeneralException;

    static native void nRemoveBitmap(int i, int i2) throws GeneralException;

    static native String nRemoveFavoriteFromList(Poi poi, int i) throws InvalidNameException;

    static native void nRemoveTMCEvent(short s, int i) throws GeneralException;

    static native void nSaveComputedRoute(String str, int i) throws GeneralException;

    static native int nSendGpsData(String str, int i);

    static native void nSetDefaultValues(int i) throws GeneralException;

    static native PoiCategory nSetPoiWarning(PoiCategory poiCategory, int i) throws GeneralException;

    static native void nSetRoute(String str, int i, boolean z, int i2) throws NavigationException;

    static native void nShowBitmap(int i, boolean z, int i2) throws GeneralException;

    static native void nShowCoordinatesOnMap(Position position, int i, boolean z, int i2) throws GeneralException;

    static native WayPoint nShowHierarchyDialog(String str, String str2, String str3, boolean z, int i) throws GeneralException;

    static native int nShowMessage(String str, int i, boolean z, boolean z2, int i2) throws GeneralException;

    static native void nShowRectangleOnMap(Rectangle rectangle, boolean z, int i) throws GeneralException;

    static native void nSkipNextWaypoint(int i) throws GeneralException;

    static native void nStartNavigation(WayPoint wayPoint, int i, boolean z, boolean z2, int i2) throws NavigationException;

    static native void nStopNavigation(int i) throws GeneralException;

    static native void nSwitchMap(String str, int i) throws GeneralException;

    static native void nTripAddUserEvent(String str, int i, int i2) throws GeneralException;

    static native void nTripEnd(int i) throws GeneralException;

    static native void nTripStart(String str, int i, int i2) throws GeneralException;

    static native void nUnloadExternalFile(String str, int i, int i2) throws GeneralException;

    static native void nUnloadGFFile(String str, int i) throws GeneralException;

    private Api() {
    }

    public static boolean isApplicationRunning(int maxTime) throws GeneralException {
        return nIsApplicationRunning(maxTime);
    }

    public static void endApplication(int maxTime) throws GeneralException {
        nEndApplication(maxTime);
    }

    public static String getUniqueDeviceId(int maxTime) throws GeneralException {
        return nGetUniqueDeviceId(maxTime);
    }

    public static boolean isApplicationInForeground(int maxTime) throws GeneralException {
        return nIsApplicationInForeground(maxTime);
    }

    public static String getMapVersion(String iso, int maxTime) throws GeneralException {
        return nGetMapVersion(iso, maxTime);
    }

    public static NaviVersion getApplicationVersion(int maxTime) throws GeneralException {
        return nGetApplicationVersion(maxTime);
    }

    static {
        try {
            System.loadLibrary("ApplicationAPI");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
