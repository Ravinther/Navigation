package com.bosch.myspin.serversdk.maps;

import android.annotation.SuppressLint;
import android.content.Context;
import android.location.Location;
import android.util.AttributeSet;
import android.view.DragEvent;
import android.view.View;
import android.view.View.OnDragListener;
import android.webkit.WebView;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import com.bosch.myspin.serversdk.utils.Logger;
import com.bosch.myspin.serversdk.utils.Logger.LogComponent;
import java.util.ArrayList;
import java.util.List;

public class MySpinMapView extends RelativeLayout implements OnDragListener {
    private static final LogComponent f121a;
    protected static List<Object> mMySpinCircleList;
    protected static List<Object> mMySpinCircleOptionsList;
    protected static MySpinJavaScriptHandler mMySpinJavaScriptHandler;
    protected static List<Object> mMySpinMarkerList;
    protected static List<Object> mMySpinMarkerOptionsList;
    protected static List<Object> mMySpinPolygonList;
    protected static List<Object> mMySpinPolygonOptionsList;
    protected static List<Object> mMySpinPolylineList;
    protected static List<Object> mMySpinPolylineOptionsList;
    protected static MySpinMapOptions sMapOptions;
    protected static MySpinMap sMySpinMap;
    protected static MySpinMapView sMySpinMapView;
    protected static WebView sWebView;
    private OnMapLoadedListener f122b;
    private OnMapLeftListener f123c;
    private MySpinLocationManager f124d;
    private float f125e;

    public interface OnMapLeftListener {
        void onMapLeftListener(String str);
    }

    public interface OnMapLoadedListener {
        void onMapLoadedListener();
    }

    static {
        f121a = LogComponent.Maps;
        mMySpinJavaScriptHandler = new MySpinJavaScriptHandler();
        mMySpinCircleList = new ArrayList();
        mMySpinCircleOptionsList = new ArrayList();
        mMySpinMarkerList = new ArrayList();
        mMySpinMarkerOptionsList = new ArrayList();
        mMySpinPolygonList = new ArrayList();
        mMySpinPolygonOptionsList = new ArrayList();
        mMySpinPolylineList = new ArrayList();
        mMySpinPolylineOptionsList = new ArrayList();
    }

    public MySpinMap getMap() {
        return sMySpinMap;
    }

    public void setMapLocationProvider(MySpinMapPositionProvider mySpinMapPositionProvider) {
        this.f124d.setMapLocationProvider(mySpinMapPositionProvider);
    }

    public MySpinMapView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        m125a(context, new MySpinMapOptions());
    }

    @SuppressLint({"SetJavaScriptEnabled"})
    private void m125a(Context context, MySpinMapOptions mySpinMapOptions) {
        Logger.logDebug(f121a, "MySpinMapView/init(" + mySpinMapOptions + ")");
        try {
            sMySpinMapView = this;
            sMapOptions = mySpinMapOptions;
            sWebView = new WebView(context);
            sWebView.getSettings().setJavaScriptEnabled(true);
            setOnDragListener(this);
            sWebView.addJavascriptInterface(mMySpinJavaScriptHandler, "MySpinJavaScriptHandler");
            String replace = MySpinJavaScriptSource.mIndexSource.replace("<script src='http://maps.googleapis.com/maps/api/js?v=3.&key=", "<script src='http://maps.googleapis.com/maps/api/js?v=3.&key=" + context.getPackageManager().getApplicationInfo(context.getPackageName(), 128).metaData.getString("com.bosch.myspin.serversdk.maps.API_KEY")).replace("<script src='myspinmap.js'></script>", "<script>" + MySpinJavaScriptSource.mMapSource + "</script>").replace("<script src='myspincircle.js'></script>", "<script>" + MySpinJavaScriptSource.mMapSource + "</script>").replace("<script src='myspinmarker.js'></script>", "<script>" + MySpinJavaScriptSource.mMarkerSource + "</script>").replace("<script src='myspinpolygon.js'></script>", "<script>" + MySpinJavaScriptSource.mPolygonSource + "</script>").replace("<script src='myspinpolyline.js'></script>", "<script>" + MySpinJavaScriptSource.mPolylineSource + "</script>").replace("<script src='myspinlocation.js'></script>", "<script>" + MySpinJavaScriptSource.mLocationSource + "</script>").replace("<script src='myspinplaces.js'></script>", "<script>" + MySpinJavaScriptSource.mPlacesSource + "</script>").replace("<script src='myspindirections.js'></script>", "<script>" + MySpinJavaScriptSource.mDirectionsSource + "</script>");
            String str = "fake://invalid";
            addView(sWebView, new LayoutParams(-1, -1));
            sWebView.setWebViewClient(new C0171d(this, context));
            mMySpinCircleList = new ArrayList();
            mMySpinCircleOptionsList = new ArrayList();
            mMySpinMarkerList = new ArrayList();
            mMySpinMarkerOptionsList = new ArrayList();
            mMySpinPolygonList = new ArrayList();
            mMySpinPolygonOptionsList = new ArrayList();
            mMySpinPolylineList = new ArrayList();
            mMySpinPolylineOptionsList = new ArrayList();
            sWebView.loadDataWithBaseURL("fake://invalid", replace, "text/html", "UTF-8", null);
            this.f124d = new MySpinLocationManager(this);
        } catch (Throwable e) {
            Logger.logWarning(f121a, "MySpinMapView/getApplicationInfo could not retrieve application information", e);
        }
    }

    public void setOnMapLoadedListener(OnMapLoadedListener onMapLoadedListener) {
        this.f122b = onMapLoadedListener;
    }

    public void setOnMapLeftListener(OnMapLeftListener onMapLeftListener) {
        this.f123c = onMapLeftListener;
    }

    protected void setMyLocationEnabled(boolean z) {
        if (this.f124d != null) {
            this.f124d.setMyLocationEnabled(z);
        }
    }

    protected void onLocationChanged(Location location) {
        if (location != null) {
            float accuracy = location.getAccuracy();
            if (accuracy < 0.0f) {
                location.setAccuracy(0.0f);
            }
            if (accuracy > 10000.0f) {
                location.setAccuracy(10000.0f);
            }
            float bearing = location.getBearing();
            if (location.hasBearing()) {
                this.f125e = bearing;
                MySpinJavaScriptHandler.webViewExecuteCommand("javascript:mySpinOnLocationChanged(" + location.getLatitude() + ", " + location.getLongitude() + ", " + accuracy + ", " + bearing + ")");
            } else {
                MySpinJavaScriptHandler.webViewExecuteCommand("javascript:mySpinOnLocationChanged(" + location.getLatitude() + ", " + location.getLongitude() + ", " + accuracy + ", " + this.f125e + ")");
            }
            sMySpinMap.onLocationChanged(location);
        }
    }

    public boolean onDrag(View view, DragEvent dragEvent) {
        Logger.logDebug(LogComponent.Maps, "MySpinMapView/onDrag: ");
        if (sMySpinMap.mOnMapDragListener != null) {
            if (dragEvent.getAction() == 1 || dragEvent.getAction() == 5) {
                Logger.logDebug(LogComponent.Maps, "MySpinMapView/drag started");
                sMySpinMap.mOnMapDragListener.onMapDragStart();
            } else if (dragEvent.getAction() == 4 || dragEvent.getAction() == 6) {
                Logger.logDebug(LogComponent.Maps, "MySpinMapView/drag ended");
                sMySpinMap.mOnMapDragListener.onMapDragEnd();
            }
        }
        return false;
    }
}
