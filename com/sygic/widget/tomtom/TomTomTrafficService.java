package com.sygic.widget.tomtom;

import android.app.IntentService;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build.VERSION;
import com.sygic.aura.analytics.AnalyticsInterface.EventType;
import com.sygic.aura.analytics.SygicAnalyticsLogger;
import com.sygic.aura.map.data.MemoItem.EMemoType;
import com.sygic.widget.TrafficWidgetProvider;
import com.sygic.widget.WidgetDataProvider;
import com.sygic.widget.location.ComparableLocation;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import javax.net.ssl.HttpsURLConnection;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class TomTomTrafficService extends IntentService {
    private static Map<Location, Location> sLocationCache;
    private LocationManager mLocationManager;

    static {
        sLocationCache = new HashMap();
    }

    public TomTomTrafficService() {
        super("TomTomTraffic");
    }

    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        this.mLocationManager = (LocationManager) base.getSystemService("location");
    }

    protected void onHandleIntent(Intent intent) {
        if (intent != null && "com.sygic.aura.trafficService.ACTION_GET_TRAFFIC".equals(intent.getAction())) {
            float latitude = intent.getFloatExtra("latitude", TrafficWidgetProvider.LOCATION_INVALID);
            float longitude = intent.getFloatExtra("longitude", TrafficWidgetProvider.LOCATION_INVALID);
            EMemoType placeType = (EMemoType) intent.getParcelableExtra("place_type");
            Location startLocation = this.mLocationManager.getLastKnownLocation("passive");
            ComparableLocation endLocation = new ComparableLocation("Sygic");
            endLocation.setLatitude((double) latitude);
            endLocation.setLongitude((double) longitude);
            Location cachedStartLocation = (Location) sLocationCache.get(endLocation);
            if (cachedStartLocation != null) {
                long cachedTimeInMinutes;
                long actualTimeInMinutes;
                if (VERSION.SDK_INT >= 17) {
                    long tmpTime = cachedStartLocation.getElapsedRealtimeNanos();
                    cachedTimeInMinutes = TimeUnit.NANOSECONDS.toMinutes(tmpTime);
                    tmpTime = startLocation.getElapsedRealtimeNanos();
                    actualTimeInMinutes = TimeUnit.NANOSECONDS.toMinutes(tmpTime);
                } else {
                    cachedTimeInMinutes = TimeUnit.MILLISECONDS.toMinutes(cachedStartLocation.getTime());
                    actualTimeInMinutes = TimeUnit.MILLISECONDS.toMinutes(startLocation.getTime());
                }
                if (actualTimeInMinutes - cachedTimeInMinutes < 15 && cachedStartLocation.distanceTo(startLocation) < 500.0f) {
                    return;
                }
            }
            HttpsURLConnection urlConnection = null;
            try {
                URL url = buildUrl(startLocation, endLocation, placeType);
                if (url != null) {
                    urlConnection = (HttpsURLConnection) url.openConnection();
                    String str = "UTF-8";
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new BufferedInputStream(urlConnection.getInputStream()), r33));
                    StringBuilder responseStrBuilder = new StringBuilder();
                    while (true) {
                        String inputStr = bufferedReader.readLine();
                        if (inputStr == null) {
                            break;
                        }
                        responseStrBuilder.append(inputStr);
                    }
                    JSONArray jsnArray = new JSONObject(responseStrBuilder.toString()).getJSONArray("routes");
                    if (jsnArray.length() > 0) {
                        JSONObject jsnSummary = jsnArray.getJSONObject(0).getJSONObject("summary");
                        int totalTime = jsnSummary.getInt("travelTimeInSeconds");
                        int delay = jsnSummary.getInt("trafficDelayInSeconds");
                        ContentValues values = new ContentValues();
                        values.put("time", Integer.valueOf(totalTime - delay));
                        values.put("traffic", Integer.valueOf(delay));
                        Context context = getBaseContext();
                        String str2 = "lat=? AND lon=?";
                        context.getContentResolver().update(WidgetDataProvider.getContentUri(context), values, r34, new String[]{Float.toString(latitude), Float.toString(longitude)});
                        sLocationCache.put(endLocation, startLocation);
                        String str3 = "TomTom";
                        SygicAnalyticsLogger.getAnalyticsEvent(context, EventType.WIDGET_TRAFFIC_REQUEST).setName(r32).logAndRecycle();
                    }
                    if (urlConnection != null) {
                        urlConnection.disconnect();
                    }
                } else if (urlConnection != null) {
                    urlConnection.disconnect();
                }
            } catch (IOException e) {
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
            } catch (JSONException e2) {
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
            } catch (Throwable th) {
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
            }
        }
    }

    private URL buildUrl(Location startLocation, Location endLocation, EMemoType placeType) throws MalformedURLException {
        if (startLocation == null || endLocation.getLatitude() == ((double) TrafficWidgetProvider.LOCATION_INVALID) || endLocation.getLongitude() == ((double) TrafficWidgetProvider.LOCATION_INVALID)) {
            return null;
        }
        StringBuilder urlBuilder = new StringBuilder("https://api.tomtom.com/");
        urlBuilder.append("routing/");
        urlBuilder.append("1/");
        urlBuilder.append("calculateRoute/");
        urlBuilder.append(PlacePoint.getStringPositionOfPoint(startLocation.getLatitude(), startLocation.getLongitude()));
        urlBuilder.append(":");
        urlBuilder.append(PlacePoint.getStringPositionOfPoint(endLocation.getLatitude(), endLocation.getLongitude()));
        urlBuilder.append("/");
        urlBuilder.append("json");
        urlBuilder.append("?");
        urlBuilder.append("traffic=true");
        urlBuilder.append("&");
        urlBuilder.append(EMemoType.memoParking.equals(placeType) ? "travelMode=pedestrian" : "travelMode=car");
        urlBuilder.append("&");
        urlBuilder.append("key=d4g4k2t7k9mnrrdbb2hn3xtc");
        return new URL(urlBuilder.toString());
    }
}
