package com.sygic.sdk;

import android.content.Intent;
import android.os.Bundle;
import android.os.RemoteCallbackList;
import android.os.RemoteException;
import com.sygic.aura.SygicService;
import com.sygic.sdk.aidl.IRemoteServiceCallback;
import com.sygic.sdk.aidl.IRemoteSygicService.Stub;
import com.sygic.sdk.api.Api;
import com.sygic.sdk.api.ApiDialog;
import com.sygic.sdk.api.ApiFavorites;
import com.sygic.sdk.api.ApiGps;
import com.sygic.sdk.api.ApiHistory;
import com.sygic.sdk.api.ApiInfo;
import com.sygic.sdk.api.ApiItinerary;
import com.sygic.sdk.api.ApiLocation;
import com.sygic.sdk.api.ApiMaps;
import com.sygic.sdk.api.ApiMenu;
import com.sygic.sdk.api.ApiNavigation;
import com.sygic.sdk.api.ApiOnline;
import com.sygic.sdk.api.ApiOptions;
import com.sygic.sdk.api.ApiPoi;
import com.sygic.sdk.api.ApiTrip;
import com.sygic.sdk.api.ApiTts;
import com.sygic.sdk.api.exception.GeneralException;
import com.sygic.sdk.api.exception.GpsException;
import com.sygic.sdk.api.exception.InvalidLocationException;
import com.sygic.sdk.api.exception.InvalidNameException;
import com.sygic.sdk.api.exception.NavigationException;
import com.sygic.sdk.api.model.GpsPosition;
import com.sygic.sdk.api.model.HistoryRecord;
import com.sygic.sdk.api.model.NaviVersion;
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
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Iterator;

public class SygicSdkService extends Stub {
    private static final String LOG_TAG;
    private RemoteCallbackList<IRemoteServiceCallback> mCallbacks;
    private SygicService mService;

    static {
        LOG_TAG = SygicSdkService.class.getCanonicalName();
    }

    public SygicSdkService(SygicService service) {
        this.mCallbacks = new RemoteCallbackList();
        this.mService = service;
    }

    private Bundle writeBundle(GeneralException e) {
        if (e == null) {
            return null;
        }
        Bundle b = new Bundle();
        b.putInt("code", e.getCode());
        b.putString("desc", e.getMessage());
        return b;
    }

    public void registerCallback(IRemoteServiceCallback cb) throws RemoteException {
        this.mCallbacks.register(cb);
    }

    public void unregisterCallback(IRemoteServiceCallback cb) throws RemoteException {
        this.mCallbacks.unregister(cb);
    }

    public void startNavi(boolean background) throws RemoteException {
        if (background) {
            try {
                this.mService.getClass().getMethod("runNavi", (Class[]) null).invoke(this.mService, (Object[]) null);
                return;
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
                return;
            } catch (IllegalArgumentException e2) {
                e2.printStackTrace();
                return;
            } catch (IllegalAccessException e3) {
                e3.printStackTrace();
                return;
            } catch (InvocationTargetException e4) {
                e4.printStackTrace();
                return;
            }
        }
        Intent i = this.mService.getPackageManager().getLaunchIntentForPackage(this.mService.getPackageName());
        i.addFlags(268435456);
        this.mService.startActivity(i);
    }

    public boolean isAppRunning() {
        return this.mService.isRunning();
    }

    public Bundle isApplicationRunning(int maxTime) throws RemoteException {
        Bundle b = new Bundle();
        try {
            b.putBoolean("value", Api.isApplicationRunning(maxTime));
        } catch (GeneralException e) {
            b.putBundle("exception", writeBundle(e));
        }
        return b;
    }

    public Bundle endApplication(int maxTime) throws RemoteException {
        Bundle b = new Bundle();
        try {
            Api.endApplication(maxTime);
        } catch (GeneralException e) {
            b.putBundle("exception", writeBundle(e));
        }
        return b;
    }

    public Bundle getUniqueDeviceId(int maxTime) throws RemoteException {
        Bundle b = new Bundle();
        try {
            b.putString("value", Api.getUniqueDeviceId(maxTime));
        } catch (GeneralException e) {
            b.putBundle("exception", writeBundle(e));
        }
        return b;
    }

    public Bundle isApplicationInForeground(int maxTime) throws RemoteException {
        Bundle b = new Bundle();
        try {
            b.putBoolean("value", Api.isApplicationInForeground(maxTime));
        } catch (GeneralException e) {
            b.putBundle("exception", writeBundle(e));
        }
        return b;
    }

    public Bundle getMapVersion(String iso, int maxTime) throws RemoteException {
        Bundle b = new Bundle();
        try {
            b.putString("value", Api.getMapVersion(iso, maxTime));
        } catch (GeneralException e) {
            b.putBundle("exception", writeBundle(e));
        }
        return b;
    }

    public Bundle getApplicationVersion(int maxTime) throws RemoteException {
        Bundle b = new Bundle();
        try {
            NaviVersion applicationVersion = Api.getApplicationVersion(maxTime);
            b.putStringArray("value", new String[]{applicationVersion.getBuild(), applicationVersion.getVersion()});
        } catch (GeneralException e) {
            b.putBundle("exception", writeBundle(e));
        }
        return b;
    }

    public Bundle changeApplicationOptions(Bundle changeOption, int maxTime) throws RemoteException {
        Bundle b = new Bundle();
        try {
            b.putBundle("value", Options.writeBundle(ApiOptions.changeApplicationOptions(Options.readBundle(changeOption), maxTime)));
        } catch (GeneralException e) {
            b.putBundle("exception", writeBundle(e));
        }
        return b;
    }

    public Bundle setDefaultValues(int maxTime) throws RemoteException {
        Bundle b = new Bundle();
        try {
            ApiOptions.setDefaultValues(maxTime);
        } catch (GeneralException e) {
            b.putBundle("exception", writeBundle(e));
        }
        return b;
    }

    public Bundle getActualGpsPosition(boolean satInfo, int maxTime) throws RemoteException {
        Bundle b = new Bundle();
        try {
            b.putBundle("value", GpsPosition.writeBundle(ApiNavigation.getActualGpsPosition(satInfo, maxTime)));
        } catch (GpsException e) {
            b.putBundle("exception", writeBundle(e));
        }
        return b;
    }

    public Bundle getRouteInfo(boolean extended, int maxTime) throws RemoteException {
        Bundle b = new Bundle();
        try {
            b.putBundle("value", RouteInfo.writeBundle(ApiNavigation.getRouteInfo(extended, maxTime)));
        } catch (GeneralException e) {
            b.putBundle("exception", writeBundle(e));
        }
        return b;
    }

    public Bundle startNavigation(Bundle location, int flags, boolean searchAddress, int maxTime) throws RemoteException {
        Bundle b = new Bundle();
        try {
            ApiNavigation.startNavigation(WayPoint.readBundle(location), flags, searchAddress, maxTime);
        } catch (NavigationException e) {
            b.putBundle("exception", writeBundle(e));
        }
        return b;
    }

    public Bundle stopNavigation(int maxTime) throws RemoteException {
        Bundle b = new Bundle();
        try {
            ApiNavigation.stopNavigation(maxTime);
        } catch (GeneralException e) {
            b.putBundle("exception", writeBundle(e));
        }
        return b;
    }

    public Bundle navigateToAddress(String address, boolean postal, int flags, int maxTime) throws RemoteException {
        Bundle b = new Bundle();
        try {
            ApiNavigation.navigateToAddress(address, postal, flags, maxTime);
        } catch (NavigationException e) {
            b.putBundle("exception", writeBundle(e));
        }
        return b;
    }

    public Bundle loadComputedRoute(String routePath, int startWpId, int maxTime) throws RemoteException {
        Bundle b = new Bundle();
        try {
            ApiNavigation.loadComputedRoute(routePath, startWpId, maxTime);
        } catch (GeneralException e) {
            b.putBundle("exception", writeBundle(e));
        }
        return b;
    }

    public Bundle saveComputedRoute(String routePath, int maxTime) throws RemoteException {
        Bundle b = new Bundle();
        try {
            ApiNavigation.saveComputedRoute(routePath, maxTime);
        } catch (GeneralException e) {
            b.putBundle("exception", writeBundle(e));
        }
        return b;
    }

    public Bundle[] getFavoritesList(boolean searchAddress, int maxTime) throws RemoteException {
        Bundle[] b = new Bundle[]{new Bundle()};
        ArrayList<Poi> poiArr = null;
        try {
            poiArr = ApiFavorites.getFavoritesList(searchAddress, maxTime);
        } catch (GeneralException e) {
            b[0].putBundle("exception", writeBundle(e));
        }
        if (poiArr == null) {
            return b;
        }
        Bundle[] bundleArr = new Bundle[poiArr.size()];
        Iterator<Poi> iter = poiArr.iterator();
        int i = 0;
        while (iter.hasNext()) {
            bundleArr[i] = Poi.writeBundle((Poi) iter.next());
            i++;
        }
        return bundleArr;
    }

    public Bundle addStopOffPointsToFavorites(Bundle poi, int maxTime) throws RemoteException {
        Bundle b = new Bundle();
        try {
            b.putString("value", ApiFavorites.addStopOffPointsToFavorites(Poi.readBundle(poi), maxTime));
        } catch (InvalidLocationException e) {
            b.putBundle("exception", writeBundle(e));
        }
        return b;
    }

    public Bundle removeFavoriteFromList(Bundle poi, int maxTime) throws RemoteException {
        Bundle b = new Bundle();
        try {
            b.putString("value", ApiFavorites.removeFavoriteFromList(Poi.readBundle(poi), maxTime));
        } catch (InvalidNameException e) {
            b.putBundle("exception", writeBundle(e));
        }
        return b;
    }

    public Bundle addStopOffPointToHistory(Bundle historyRecord, int maxTime) throws RemoteException {
        Bundle b = new Bundle();
        try {
            ApiFavorites.addStopOffPointsToFavorites(Poi.readBundle(historyRecord), maxTime);
        } catch (InvalidLocationException e) {
            b.putBundle("exception", writeBundle(e));
        }
        return b;
    }

    public Bundle[] getHistoryList(boolean searchAddress, int maxTime) throws RemoteException {
        Bundle[] b = new Bundle[]{new Bundle()};
        ArrayList<HistoryRecord> arr = null;
        try {
            arr = ApiHistory.getHistoryList(searchAddress, maxTime);
        } catch (GeneralException e) {
            b[0].putBundle("exception", writeBundle(e));
        }
        if (arr == null) {
            return b;
        }
        Bundle[] bundleArr = new Bundle[arr.size()];
        Iterator<HistoryRecord> iter = arr.iterator();
        int i = 0;
        while (iter.hasNext()) {
            bundleArr[i] = HistoryRecord.writeBundle((HistoryRecord) iter.next());
            i++;
        }
        return bundleArr;
    }

    public Bundle addPoi(Bundle poi, int maxTime) throws RemoteException {
        Bundle b = new Bundle();
        try {
            b.putString("value", ApiPoi.addPoi(Poi.readBundle(poi), maxTime));
        } catch (InvalidLocationException e) {
            b.putBundle("exception", writeBundle(e));
        }
        return b;
    }

    public Bundle addPoiCategory(String category, String bitmapPath, String isoCode, int maxTime) throws RemoteException {
        Bundle b = new Bundle();
        try {
            ApiPoi.addPoiCategory(category, bitmapPath, isoCode, maxTime);
        } catch (GeneralException e) {
            b.putBundle("exception", writeBundle(e));
        }
        return b;
    }

    public Bundle deletePoiCategory(String category, String isoCode, int maxTime) throws RemoteException {
        Bundle b = new Bundle();
        try {
            ApiPoi.deletePoiCategory(category, isoCode, maxTime);
        } catch (GeneralException e) {
            b.putBundle("exception", writeBundle(e));
        }
        return b;
    }

    public Bundle deletePoi(Bundle poi, int maxTime) throws RemoteException {
        Bundle b = new Bundle();
        try {
            b.putString("value", ApiPoi.deletePoi(Poi.readBundle(poi), maxTime));
        } catch (InvalidLocationException e) {
            b.putBundle("exception", writeBundle(e));
        }
        return b;
    }

    public Bundle makeUserPoiVisible(int categoryId, String category, boolean show, int maxTime) throws RemoteException {
        Bundle b = new Bundle();
        try {
            ApiPoi.makeUserPoiVisible(categoryId, category, show, maxTime);
        } catch (GeneralException e) {
            b.putBundle("exception", writeBundle(e));
        }
        return b;
    }

    public Bundle[] getPoiList(String category, boolean searchAddress, int maxTime) throws RemoteException {
        Bundle[] b = new Bundle[]{new Bundle()};
        ArrayList<Poi> arr = null;
        try {
            arr = ApiPoi.getPoiList(category, searchAddress, maxTime);
        } catch (GeneralException e) {
            b[0].putBundle("exception", writeBundle(e));
        }
        if (arr == null) {
            return b;
        }
        Bundle[] bundleArr = new Bundle[arr.size()];
        Iterator<Poi> iter = arr.iterator();
        int i = 0;
        while (iter.hasNext()) {
            bundleArr[i] = Poi.writeBundle((Poi) iter.next());
            i++;
        }
        return bundleArr;
    }

    public Bundle findNearbyPoi(int categoryId, String category, int x, int y, int maxTime) throws RemoteException {
        Bundle b = new Bundle();
        try {
            b.putBundle("value", Poi.writeBundle(ApiPoi.findNearbyPoi(categoryId, category, x, y, maxTime)));
        } catch (GeneralException e) {
            b.putBundle("exception", writeBundle(e));
        }
        return b;
    }

    public Bundle[] findNearbyPoiList(int categoryId, String category, int x, int y, int maxCount, int maxTime) throws RemoteException {
        Bundle[] b = new Bundle[]{new Bundle()};
        ArrayList<Poi> arr = null;
        try {
            arr = ApiPoi.findNearbyPoiList(categoryId, category, x, y, maxCount, maxTime);
        } catch (GeneralException e) {
            b[0].putBundle("exception", writeBundle(e));
        }
        if (arr == null) {
            return b;
        }
        Bundle[] bundleArr = new Bundle[arr.size()];
        Iterator<Poi> iter = arr.iterator();
        int i = 0;
        while (iter.hasNext()) {
            bundleArr[i] = Poi.writeBundle((Poi) iter.next());
            i++;
        }
        return bundleArr;
    }

    public Bundle[] getPoiCategoryList(int maxTime) throws RemoteException {
        Bundle[] b = new Bundle[]{new Bundle()};
        ArrayList<PoiCategory> arr = null;
        try {
            arr = ApiPoi.getPoiCategoryList(maxTime);
        } catch (GeneralException e) {
            b[0].putBundle("exception", writeBundle(e));
        }
        if (arr == null) {
            return b;
        }
        Bundle[] bundleArr = new Bundle[arr.size()];
        Iterator<PoiCategory> iter = arr.iterator();
        int i = 0;
        while (iter.hasNext()) {
            bundleArr[i] = PoiCategory.writeBundle((PoiCategory) iter.next());
            i++;
        }
        return bundleArr;
    }

    public Bundle setPoiWarning(Bundle poiCategory, int maxTime) throws RemoteException {
        Bundle b = new Bundle();
        try {
            b.putBundle("value", PoiCategory.writeBundle(ApiPoi.setPoiWarning(PoiCategory.readBundle(poiCategory), maxTime)));
        } catch (GeneralException e) {
            b.putBundle("exception", writeBundle(e));
        }
        return b;
    }

    public Bundle highlightPoi(String category, String name, String sound, int[] pos, int maxTime) throws RemoteException {
        Bundle b = new Bundle();
        try {
            ApiPoi.highlightPoi(category, name, sound, new Position(pos[0], pos[1]), maxTime);
        } catch (GeneralException e) {
            b.putBundle("exception", writeBundle(e));
        }
        return b;
    }

    public Bundle getLocationAddressInfo(int[] location, int maxTime) throws RemoteException {
        Bundle b = new Bundle();
        try {
            b.putString("value", ApiLocation.getLocationAddressInfo(new Position(location[0], location[1]), maxTime));
        } catch (InvalidLocationException e) {
            b.putBundle("exception", writeBundle(e));
        }
        return b;
    }

    public Bundle getLocationRoadInfo(int[] location, int maxTime) throws RemoteException {
        Bundle b = new Bundle();
        try {
            b.putBundle("value", RoadInfo.writeBundle(ApiLocation.getLocationRoadInfo(new Position(location[0], location[1]), maxTime)));
        } catch (InvalidLocationException e) {
            b.putBundle("exception", writeBundle(e));
        }
        return b;
    }

    public Bundle locationFromAddress(String address, boolean postal, boolean valueMatch, int maxTime) throws RemoteException {
        Bundle b = new Bundle();
        try {
            b.putIntArray("value", ApiLocation.locationFromAddress(address, postal, valueMatch, maxTime).toArray());
        } catch (GeneralException e) {
            b.putBundle("exception", writeBundle(e));
        }
        return b;
    }

    public Bundle[] locationFromAddressEx(String address, boolean postal, int maxTime) throws RemoteException {
        Bundle[] b = new Bundle[]{new Bundle()};
        ArrayList<WayPoint> arr = null;
        try {
            arr = ApiLocation.locationFromAddressEx(address, postal, maxTime);
        } catch (GeneralException e) {
            b[0].putBundle("exception", writeBundle(e));
        }
        if (arr == null) {
            return b;
        }
        Bundle[] bundleArr = new Bundle[arr.size()];
        Iterator<WayPoint> iter = arr.iterator();
        int i = 0;
        while (iter.hasNext()) {
            bundleArr[i] = WayPoint.writeBundle((WayPoint) iter.next());
            i++;
        }
        return bundleArr;
    }

    public Bundle[] locationFromAddressEx2(String address, boolean postal, boolean fuzzySearch, int maxTime) throws RemoteException {
        Bundle[] b = new Bundle[]{new Bundle()};
        ArrayList<WayPoint> arr = null;
        try {
            arr = ApiLocation.locationFromAddressEx(address, postal, fuzzySearch, maxTime);
        } catch (GeneralException e) {
            b[0].putBundle("exception", writeBundle(e));
        }
        if (arr == null) {
            return b;
        }
        Bundle[] bundleArr = new Bundle[arr.size()];
        Iterator<WayPoint> iter = arr.iterator();
        int i = 0;
        while (iter.hasNext()) {
            bundleArr[i] = WayPoint.writeBundle((WayPoint) iter.next());
            i++;
        }
        return bundleArr;
    }

    public Bundle showHierarchyDialog(String iso, String city, String street, boolean postal, int maxTime) throws RemoteException {
        Bundle b = new Bundle();
        try {
            b.putBundle("value", WayPoint.writeBundle(ApiLocation.showHierarchyDialog(iso, city, street, postal, maxTime)));
        } catch (GeneralException e) {
            b.putBundle("exception", writeBundle(e));
        }
        return b;
    }

    public Bundle[] getAddressList(String address, boolean postal, int maxCount, int maxTime) throws RemoteException {
        Bundle[] b = new Bundle[]{new Bundle()};
        ArrayList<WayPoint> arr = null;
        try {
            arr = ApiLocation.getAddressList(address, postal, maxCount, maxTime);
        } catch (GeneralException e) {
            b[0].putBundle("exception", writeBundle(e));
        }
        if (arr == null) {
            return b;
        }
        Bundle[] bundleArr = new Bundle[arr.size()];
        Iterator<WayPoint> iter = arr.iterator();
        int i = 0;
        while (iter.hasNext()) {
            bundleArr[i] = WayPoint.writeBundle((WayPoint) iter.next());
            i++;
        }
        return bundleArr;
    }

    public Bundle getCoordinatesFromOffset(String iso, long offset, int maxTime) throws RemoteException {
        Bundle b = new Bundle();
        try {
            b.putIntArray("value", ApiLocation.getCoordinatesFromOffset(iso, offset, maxTime).toArray());
        } catch (GeneralException e) {
            b.putBundle("exception", writeBundle(e));
        }
        return b;
    }

    public Bundle switchMap(String loadPath, int maxTime) throws RemoteException {
        Bundle b = new Bundle();
        try {
            ApiMaps.switchMap(loadPath, maxTime);
        } catch (GeneralException e) {
            b.putBundle("exception", writeBundle(e));
        }
        return b;
    }

    public Bundle showCoordinatesOnMap(int[] location, int zoom, int maxTime) throws RemoteException {
        Bundle b = new Bundle();
        try {
            ApiMaps.showCoordinatesOnMap(new Position(location[0], location[1]), zoom, maxTime);
        } catch (GeneralException e) {
            b.putBundle("exception", writeBundle(e));
        }
        return b;
    }

    public Bundle showRectangleOnMap(int[] rect, int maxTime) throws RemoteException {
        Bundle b = new Bundle();
        try {
            ApiMaps.showRectangleOnMap(new Rectangle(rect[0], rect[1], rect[2], rect[3]), maxTime);
        } catch (GeneralException e) {
            b.putBundle("exception", writeBundle(e));
        }
        return b;
    }

    public Bundle addBitmapToMap(String bitmapPath, int x, int y, int maxTime) throws RemoteException {
        Bundle b = new Bundle();
        try {
            b.putInt("value", ApiMaps.addBitmapToMap(bitmapPath, x, y, maxTime));
        } catch (GeneralException e) {
            b.putBundle("exception", writeBundle(e));
        }
        return b;
    }

    public Bundle removeBitmap(int bitmapId, int maxTime) throws RemoteException {
        Bundle b = new Bundle();
        try {
            ApiMaps.removeBitmap(bitmapId, maxTime);
        } catch (GeneralException e) {
            b.putBundle("exception", writeBundle(e));
        }
        return b;
    }

    public Bundle showBitmap(int bitmapId, boolean show, int maxTime) throws RemoteException {
        Bundle b = new Bundle();
        try {
            ApiMaps.showBitmap(bitmapId, show, maxTime);
        } catch (GeneralException e) {
            b.putBundle("exception", writeBundle(e));
        }
        return b;
    }

    public Bundle moveBitmap(int bitmapId, int x, int y, int maxTime) throws RemoteException {
        Bundle b = new Bundle();
        try {
            ApiMaps.moveBitmap(bitmapId, x, y, maxTime);
        } catch (GeneralException e) {
            b.putBundle("exception", writeBundle(e));
        }
        return b;
    }

    public Bundle loadGFFile(String path, int maxTime) throws RemoteException {
        Bundle b = new Bundle();
        try {
            ApiMaps.loadGFFile(path, maxTime);
        } catch (GeneralException e) {
            b.putBundle("exception", writeBundle(e));
        }
        return b;
    }

    public Bundle unloadGFFile(String path, int maxTime) throws RemoteException {
        Bundle b = new Bundle();
        try {
            ApiMaps.unloadGFFile(path, maxTime);
        } catch (GeneralException e) {
            b.putBundle("exception", writeBundle(e));
        }
        return b;
    }

    public Bundle onMenuCommand(int menuId, int subMenuId, int maxTime) throws RemoteException {
        Bundle b = new Bundle();
        try {
            ApiMenu.onMenuCommand(menuId, subMenuId, maxTime);
        } catch (GeneralException e) {
            b.putBundle("exception", writeBundle(e));
        }
        return b;
    }

    public Bundle showMessage(String message, int buttons, boolean waitForFeedback, int maxTime) throws RemoteException {
        Bundle b = new Bundle();
        try {
            b.putInt("value", ApiDialog.showMessage(message, buttons, waitForFeedback, maxTime));
        } catch (GeneralException e) {
            b.putBundle("exception", writeBundle(e));
        }
        return b;
    }

    public Bundle flashMessage(String message, int maxTime) throws RemoteException {
        Bundle b = new Bundle();
        try {
            ApiDialog.flashMessage(message, maxTime);
        } catch (GeneralException e) {
            b.putBundle("exception", writeBundle(e));
        }
        return b;
    }

    public Bundle errorReport(int maxTime) throws RemoteException {
        Bundle b = new Bundle();
        try {
            b.putInt("value", ApiDialog.errorReport(maxTime));
        } catch (GeneralException e) {
            b.putBundle("exception", writeBundle(e));
        }
        return b;
    }

    public Bundle closeDialogs(int maxTime) throws RemoteException {
        Bundle b = new Bundle();
        try {
            ApiDialog.closeDialogs(maxTime);
        } catch (GeneralException e) {
            b.putBundle("exception", writeBundle(e));
        }
        return b;
    }

    public Bundle addItinerary(Bundle[] pointArr, String name, int maxTime) throws RemoteException {
        Bundle b = new Bundle();
        ArrayList<StopOffPoint> arr = new ArrayList(pointArr.length);
        for (Bundle aPointArr : pointArr) {
            arr.add(StopOffPoint.readBundle(aPointArr));
        }
        try {
            ApiItinerary.addItinerary(arr, name, maxTime);
        } catch (GeneralException e) {
            b.putBundle("exception", writeBundle(e));
        }
        return b;
    }

    public Bundle setRoute(String name, int flags, int maxTime) throws RemoteException {
        Bundle b = new Bundle();
        try {
            ApiItinerary.setRoute(name, flags, maxTime);
        } catch (NavigationException e) {
            b.putBundle("exception", writeBundle(e));
        }
        return b;
    }

    public Bundle addEntryToItinerary(String name, Bundle stopOffPoint, int index, int maxTime) throws RemoteException {
        Bundle b = new Bundle();
        try {
            ApiItinerary.addEntryToItinerary(name, StopOffPoint.readBundle(stopOffPoint), index, maxTime);
        } catch (GeneralException e) {
            b.putBundle("exception", writeBundle(e));
        }
        return b;
    }

    public Bundle deleteEntryInItinerary(String name, int index, int maxTime) throws RemoteException {
        Bundle b = new Bundle();
        try {
            ApiItinerary.deleteEntryInItinerary(name, index, maxTime);
        } catch (GeneralException e) {
            b.putBundle("exception", writeBundle(e));
        }
        return b;
    }

    public Bundle deleteItinerary(String name, int maxTime) throws RemoteException {
        Bundle b = new Bundle();
        try {
            ApiItinerary.deleteItinerary(name, maxTime);
        } catch (GeneralException e) {
            b.putBundle("exception", writeBundle(e));
        }
        return b;
    }

    public Bundle[] getItineraryList(String name, int maxTime) throws RemoteException {
        Bundle[] b = new Bundle[]{new Bundle()};
        ArrayList<StopOffPoint> arr = null;
        try {
            arr = ApiItinerary.getItineraryList(name, maxTime);
        } catch (GeneralException e) {
            b[0].putBundle("exception", writeBundle(e));
        }
        if (arr == null) {
            return b;
        }
        Bundle[] bundleArr = new Bundle[arr.size()];
        Iterator<StopOffPoint> iter = arr.iterator();
        int i = 0;
        while (iter.hasNext()) {
            bundleArr[i] = StopOffPoint.writeBundle((StopOffPoint) iter.next());
            i++;
        }
        return bundleArr;
    }

    public Bundle skipNextWaypoint(int maxTime) throws RemoteException {
        Bundle b = new Bundle();
        try {
            ApiItinerary.skipNextWaypoint(maxTime);
        } catch (GeneralException e) {
            b.putBundle("exception", writeBundle(e));
        }
        return b;
    }

    public Bundle enableExternalGpsInput(int maxTime) throws RemoteException {
        Bundle b = new Bundle();
        try {
            ApiGps.enableExternalGpsInput(maxTime);
        } catch (GeneralException e) {
            b.putBundle("exception", writeBundle(e));
        }
        return b;
    }

    public Bundle disableExternalGpsInput(int maxTime) throws RemoteException {
        Bundle b = new Bundle();
        try {
            ApiGps.disableExternalGpsInput(maxTime);
        } catch (GeneralException e) {
            b.putBundle("exception", writeBundle(e));
        }
        return b;
    }

    public int sendGpsData(String data) throws RemoteException {
        return ApiGps.sendGpsData(data);
    }

    public Bundle gpsSwitchOn(boolean switchOn, int maxTime) throws RemoteException {
        Bundle b = new Bundle();
        try {
            ApiGps.gpsSwitchOn(switchOn, maxTime);
        } catch (GeneralException e) {
            b.putBundle("exception", writeBundle(e));
        }
        return b;
    }

    public Bundle getCurrentSpeedLimit(int maxTime) throws RemoteException {
        Bundle b = new Bundle();
        try {
            b.putInt("value", ApiInfo.getCurrentSpeedLimit(maxTime));
        } catch (GeneralException e) {
            b.putBundle("exception", writeBundle(e));
        }
        return b;
    }

    public Bundle getNextInstruction(int maxTime) throws RemoteException {
        Bundle b = new Bundle();
        try {
            b.putBundle("value", RouteInstruction.writeBundle(ApiInfo.getNextInstruction(maxTime)));
        } catch (GeneralException e) {
            b.putBundle("exception", writeBundle(e));
        }
        return b;
    }

    public Bundle goOnline(boolean online, int maxTime) throws RemoteException {
        Bundle b = new Bundle();
        try {
            ApiOnline.goOnline(online, maxTime);
        } catch (GeneralException e) {
            b.putBundle("exception", writeBundle(e));
        }
        return b;
    }

    public Bundle onlineServicesLogin(String userName, String password, int maxTime) throws RemoteException {
        Bundle b = new Bundle();
        try {
            ApiOnline.onlineServicesLogin(userName, password, maxTime);
        } catch (GeneralException e) {
            b.putBundle("exception", writeBundle(e));
        }
        return b;
    }

    public Bundle addTMCEvent(Bundle event, int maxTime) throws RemoteException {
        Bundle b = new Bundle();
        try {
            b.putBundle("value", TmcEvent.writeBundle(ApiOnline.addTMCEvent(TmcEvent.readBundle(event), maxTime)));
        } catch (GeneralException e) {
            b.putBundle("exception", writeBundle(e));
        }
        return b;
    }

    public Bundle removeTMCEvent(int eventId, int maxTime) throws RemoteException {
        Bundle b = new Bundle();
        try {
            ApiOnline.removeTMCEvent((short) eventId, maxTime);
        } catch (GeneralException e) {
            b.putBundle("exception", writeBundle(e));
        }
        return b;
    }

    public Bundle clearTMCTable(int maxTime) throws RemoteException {
        Bundle b = new Bundle();
        try {
            ApiOnline.clearTMCTable(maxTime);
        } catch (GeneralException e) {
            b.putBundle("exception", writeBundle(e));
        }
        return b;
    }

    public Bundle onlineServicesSettings(int maxTime) throws RemoteException {
        Bundle b = new Bundle();
        try {
            b.putBooleanArray("value", ApiOnline.onlineServicesSettings(maxTime).toArray());
        } catch (GeneralException e) {
            b.putBundle("exception", writeBundle(e));
        }
        return b;
    }

    public Bundle playSoundTTS(String text, int maxTime) throws RemoteException {
        Bundle b = new Bundle();
        try {
            ApiTts.playSound(text, maxTime);
        } catch (GeneralException e) {
            b.putBundle("exception", writeBundle(e));
        }
        return b;
    }

    public Bundle tripStart(String name, int mode, int maxTime) throws RemoteException {
        Bundle b = new Bundle();
        try {
            ApiTrip.tripStart(name, mode, maxTime);
        } catch (GeneralException e) {
            b.putBundle("exception", writeBundle(e));
        }
        return b;
    }

    public Bundle tripAddUserEvent(String name, int id, int maxTime) throws RemoteException {
        Bundle b = new Bundle();
        try {
            ApiTrip.tripAddUserEvent(name, id, maxTime);
        } catch (GeneralException e) {
            b.putBundle("exception", writeBundle(e));
        }
        return b;
    }

    public Bundle tripEnd(int maxTime) throws RemoteException {
        Bundle b = new Bundle();
        try {
            ApiTrip.tripEnd(maxTime);
        } catch (GeneralException e) {
            b.putBundle("exception", writeBundle(e));
        }
        return b;
    }

    public boolean isRunning() throws RemoteException {
        return this.mService.isRunning();
    }

    public Bundle loadExternalFile(String path, int type, int maxTime) throws RemoteException {
        Bundle b = new Bundle();
        try {
            ApiMaps.loadExternalFile(path, type, maxTime);
        } catch (GeneralException e) {
            b.putBundle("exception", writeBundle(e));
        }
        return b;
    }

    public Bundle unloadExternalFile(String path, int type, int maxTime) throws RemoteException {
        Bundle b = new Bundle();
        try {
            ApiMaps.unloadExternalFile(path, type, maxTime);
        } catch (GeneralException e) {
            b.putBundle("exception", writeBundle(e));
        }
        return b;
    }

    public Bundle reloadExternalFiles(int maxTime) throws RemoteException {
        Bundle b = new Bundle();
        try {
            ApiMaps.reloadExternalFiles(maxTime);
        } catch (GeneralException e) {
            b.putBundle("exception", writeBundle(e));
        }
        return b;
    }
}
