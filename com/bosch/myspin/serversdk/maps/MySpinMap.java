package com.bosch.myspin.serversdk.maps;

import android.location.Location;
import android.webkit.WebView;
import com.bosch.myspin.serversdk.utils.Logger;
import com.bosch.myspin.serversdk.utils.Logger.LogComponent;
import java.util.Iterator;

public class MySpinMap {
    private static final LogComponent f109a;
    private MySpinLatLng f110b;
    private MySpinMapView f111c;
    private int f112e;
    private MySpinUiSettings f113g;
    private Location f114h;
    protected MySpinPlaces mMySpinPlaces;
    protected OnLocationChangedListener mOnLocationChangedListener;
    protected OnMapDragListener mOnMapDragListener;
    protected WebView mWebView;

    public interface OnLocationChangedListener {
        void onLocationChanged(Location location);
    }

    public interface OnMapDragListener {
        void onMapDragEnd();

        void onMapDragStart();
    }

    static {
        f109a = LogComponent.Maps;
    }

    protected MySpinMap(MySpinMapView mySpinMapView, WebView webView, MySpinMapOptions mySpinMapOptions) {
        if (mySpinMapView == null) {
            throw new IllegalArgumentException("mapView must not be null!");
        }
        this.f111c = mySpinMapView;
        this.mWebView = webView;
        MySpinJavaScriptHandler.webViewExecuteCommand("javascript:mySpinMapOptionsInit()");
        MySpinJavaScriptHandler.webViewExecuteCommand("javascript:mySpinMapOptionsMaxZoom(" + mySpinMapOptions.getMaxZoom() + ")");
        MySpinJavaScriptHandler.webViewExecuteCommand("javascript:mySpinMapOptionsMinZoom(" + mySpinMapOptions.getMinZoom() + ")");
        MySpinJavaScriptHandler.webViewExecuteCommand("javascript:mySpinMapOptionsZoomControl(" + mySpinMapOptions.getZoomControlsEnabled() + ")");
        MySpinJavaScriptHandler.webViewExecuteCommand("javascript:mySpinMapInit()");
        this.f112e = mySpinMapOptions.getMapType();
        this.f113g = new MySpinUiSettings(mySpinMapOptions.getZoomControlsEnabled());
        this.mMySpinPlaces = new MySpinPlaces();
    }

    public void addRoute(MySpinLatLng mySpinLatLng, MySpinLatLng mySpinLatLng2) {
        addRoute(mySpinLatLng, mySpinLatLng2, null);
    }

    public void addRoute(MySpinLatLng mySpinLatLng, MySpinLatLng mySpinLatLng2, MySpinDirectionsOptions mySpinDirectionsOptions) {
        if (mySpinLatLng != null && mySpinLatLng2 != null) {
            Logger.logDebug(f109a, "MySpinMap/addRoute origin: " + mySpinLatLng + " destination: " + mySpinLatLng2);
            String str = "new Array( ";
            if (!(mySpinDirectionsOptions == null || mySpinDirectionsOptions.getStopovers() == null)) {
                Iterator it = mySpinDirectionsOptions.getStopovers().iterator();
                String str2 = str;
                while (it.hasNext()) {
                    MySpinLatLng mySpinLatLng3 = (MySpinLatLng) it.next();
                    str2 = str2 + "\"" + mySpinLatLng3.getLatitude() + "," + mySpinLatLng3.getLongitude() + "\",";
                }
                str = str2;
            }
            str = str.substring(0, str.length() - 1) + ")";
            if (mySpinDirectionsOptions == null || mySpinDirectionsOptions.getIcon() == null) {
                MySpinJavaScriptHandler.webViewExecuteCommand("javascript:mySpinAddRoute(" + mySpinLatLng.getLatitude() + ", " + mySpinLatLng.getLongitude() + ", " + mySpinLatLng2.getLatitude() + ", " + mySpinLatLng2.getLongitude() + ", " + str + ", \"" + "" + "\")");
            } else {
                MySpinJavaScriptHandler.webViewExecuteCommand("javascript:mySpinAddRoute(" + mySpinLatLng.getLatitude() + ", " + mySpinLatLng.getLongitude() + ", " + mySpinLatLng2.getLatitude() + ", " + mySpinLatLng2.getLongitude() + ", " + str + ", \"" + mySpinDirectionsOptions.getIcon().getPath() + "\")");
            }
        } else if (mySpinLatLng != null || mySpinLatLng2 == null) {
            Logger.logWarning(f109a, "Error adding route, origin: " + mySpinLatLng + " destination: null");
        } else {
            this.f110b = mySpinLatLng2;
        }
    }

    protected final void onLocationChanged(Location location) {
        this.f114h = location;
        if (this.mOnLocationChangedListener != null) {
            this.mOnLocationChangedListener.onLocationChanged(location);
        }
        if (this.f110b != null && this.f114h != null) {
            Logger.logWarning(f109a, "MySpinMap/onLocationChanged adding route after location update.");
            addRoute(new MySpinLatLng(this.f114h), this.f110b);
            this.f110b = null;
        }
    }
}
