package com.sygic.aura.fcd;

import android.annotation.TargetApi;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager.NameNotFoundException;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.SystemClock;
import android.text.TextUtils;
import android.util.Base64;
import com.google.android.gms.ads.identifier.AdvertisingIdClient;
import com.google.android.gms.ads.identifier.AdvertisingIdClient.Info;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.sygic.aura.ProjectsConsts;
import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.GeometryFactory;
import com.vividsolutions.jts.geom.Point;
import com.vividsolutions.jts.geom.Polygon;
import com.vividsolutions.jts.geom.PrecisionModel;
import com.vividsolutions.jts.index.strtree.STRtree;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;
import java.util.UUID;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class FloatingCarDataService extends Service {
    private static final Object _fcdEntitiesLock;
    private static final Object _locationLock;
    private String _advertisingId;
    private String _appVersion;
    private long _badLocationCounter;
    private long _clientReferenceUptimeMillis;
    private String _currentCountryCode;
    private ExtendedLocation _currentCountryCodeLocation;
    private String _deviceId;
    private String _deviceName;
    private URL _eventHubServiceUrl;
    private String _eventHubSharedAccessSignature;
    private List<FcdEntity> _fcdEntities;
    private GeometryFactory _geometryFactory;
    private long _goodLocationCounter;
    private Handler _handlerCollectData;
    private Handler _handlerUploadData;
    private ExtendedLocation _lastLocation;
    private long _lastUploadTimeStampMillis;
    private PendingIntent _locationIntent;
    LocationManager _locationManager;
    private BroadcastReceiver _networkStateReceiver;
    private int _networkType;
    private String _osVersion;
    private String _platform;
    private ExtendedLocation _previousLocation;
    private Runnable _runnableCollectData;
    private Runnable _runnableUploadData;
    private long _serverReferenceTimeMillis;
    private String _sessionId;
    private STRtree _tree;

    /* renamed from: com.sygic.aura.fcd.FloatingCarDataService.1 */
    class C12081 extends BroadcastReceiver {
        C12081() {
        }

        public void onReceive(Context context, Intent intent) {
            FloatingCarDataService.this.onNetworkStateChanged();
        }
    }

    /* renamed from: com.sygic.aura.fcd.FloatingCarDataService.2 */
    class C12092 implements Runnable {
        C12092() {
        }

        public void run() {
            new CollectDataTask(null).execute(new Void[0]);
            FloatingCarDataService.this._handlerCollectData.postDelayed(this, 5000);
        }
    }

    /* renamed from: com.sygic.aura.fcd.FloatingCarDataService.3 */
    class C12103 implements Runnable {
        C12103() {
        }

        public void run() {
            new UploadDataTask(null).execute(new Void[0]);
            FloatingCarDataService.this._handlerUploadData.postDelayed(this, 60000);
        }
    }

    private class CollectDataTask extends AsyncTask<Void, Void, Void> {
        private CollectDataTask() {
        }

        protected Void doInBackground(Void... args) {
            synchronized (FloatingCarDataService._locationLock) {
                if (FloatingCarDataService.this._previousLocation == null || FloatingCarDataService.this._lastLocation == null) {
                    return null;
                }
                float[] results = new float[2];
                Location.distanceBetween(FloatingCarDataService.this._previousLocation.getLocation().getLatitude(), FloatingCarDataService.this._previousLocation.getLocation().getLongitude(), FloatingCarDataService.this._lastLocation.getLocation().getLatitude(), FloatingCarDataService.this._lastLocation.getLocation().getLongitude(), results);
                float distance = results[0];
                float computedBearing = results[1];
                if (computedBearing < 0.0f) {
                    computedBearing += 360.0f;
                }
                double time = ((double) (FloatingCarDataService.this._lastLocation.getTimestampMillis() - FloatingCarDataService.this._previousLocation.getTimestampMillis())) / 1000.0d;
                ExtendedLocation fcdLocation = new ExtendedLocation(FloatingCarDataService.this._previousLocation.getLocation(), FloatingCarDataService.this._previousLocation.getTimestampMillis(), FloatingCarDataService.this._previousLocation.getDate(), FloatingCarDataService.this._previousLocation.getNetworkType(), time > 0.0d ? (3.6d * ((double) distance)) / time : -1.0d, (double) computedBearing);
                FloatingCarDataService.this._previousLocation = null;
                String countryCode = getCountryCode(fcdLocation.getLocation());
                if (TextUtils.isEmpty(countryCode)) {
                    if (!(TextUtils.isEmpty(FloatingCarDataService.this._currentCountryCode) || FloatingCarDataService.this._currentCountryCodeLocation == null)) {
                        results = new float[1];
                        Location.distanceBetween(FloatingCarDataService.this._currentCountryCodeLocation.getLocation().getLatitude(), FloatingCarDataService.this._currentCountryCodeLocation.getLocation().getLongitude(), fcdLocation.getLocation().getLatitude(), fcdLocation.getLocation().getLongitude(), results);
                        if (((double) results[0]) < 1000.0d) {
                            countryCode = FloatingCarDataService.this._currentCountryCode;
                        }
                    }
                    if (TextUtils.isEmpty(countryCode)) {
                        countryCode = getCountryCodeUsingGeocoder(fcdLocation.getLocation());
                        if (TextUtils.isEmpty(countryCode)) {
                            countryCode = getCountryCodeUsingGoogleApis(fcdLocation.getLocation());
                        }
                        if (!TextUtils.isEmpty(countryCode)) {
                            countryCode = Utils.convertCountryCodeAlpha2ToAlpha3(countryCode);
                        }
                        FloatingCarDataService.this._currentCountryCode = countryCode;
                        FloatingCarDataService.this._currentCountryCodeLocation = fcdLocation;
                    }
                } else {
                    FloatingCarDataService.this._currentCountryCode = countryCode;
                    FloatingCarDataService.this._currentCountryCodeLocation = fcdLocation;
                }
                if (!TextUtils.isEmpty(countryCode)) {
                    long timestampMillis = (FloatingCarDataService.this._serverReferenceTimeMillis <= 0 || FloatingCarDataService.this._clientReferenceUptimeMillis <= 0) ? 0 : (fcdLocation.getTimestampMillis() - FloatingCarDataService.this._clientReferenceUptimeMillis) + FloatingCarDataService.this._serverReferenceTimeMillis;
                    FcdEntity fcdEntity = new FcdEntity(Utils.normalizeUnixTimeMillis(timestampMillis), Utils.normalizeUnixTimeMillis(fcdLocation.getLocation().getTime()), Utils.normalizeUnixTimeMillis(fcdLocation.getDate()), fcdLocation.getLocation().getLatitude(), fcdLocation.getLocation().getLongitude(), fcdLocation.getLocation().hasAltitude() ? fcdLocation.getLocation().getAltitude() : -1.0d, fcdLocation.getLocation().hasAccuracy() ? (double) fcdLocation.getLocation().getAccuracy() : -1.0d, -1.0d, fcdLocation.getLocation().hasBearing() ? (double) fcdLocation.getLocation().getBearing() : -1.0d, fcdLocation.getComputedBearing(), fcdLocation.getLocation().hasSpeed() ? ((double) fcdLocation.getLocation().getSpeed()) * 3.6d : -1.0d, fcdLocation.getComputedSpeed(), countryCode, 0, Utils.convertNetworkType(fcdLocation.getNetworkType()));
                    synchronized (FloatingCarDataService._fcdEntitiesLock) {
                        FloatingCarDataService.this._fcdEntities.add(fcdEntity);
                    }
                }
                return null;
            }
        }

        protected void onPostExecute(Void obj) {
        }

        private String getCountryCode(Location location) {
            try {
                if (FloatingCarDataService.this._tree == null) {
                    return null;
                }
                Point point = FloatingCarDataService.this._geometryFactory.createPoint(new Coordinate(location.getLongitude(), location.getLatitude()));
                List<?> countries = FloatingCarDataService.this._tree.query(point.getEnvelopeInternal());
                if (countries.size() == 0) {
                    return null;
                }
                for (int i = 0; i < countries.size() - 1; i++) {
                    CountryPolygon country = (CountryPolygon) countries.get(i);
                    if (country.getPolygon().contains(point)) {
                        return country.getCountryCode();
                    }
                }
                return ((CountryPolygon) countries.get(countries.size() - 1)).getCountryCode();
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }

        private String getCountryCodeUsingGeocoder(Location location) {
            if (!Geocoder.isPresent()) {
                return null;
            }
            try {
                for (Address address : new Geocoder(FloatingCarDataService.this.getApplicationContext()).getFromLocation(location.getLatitude(), location.getLongitude(), 3)) {
                    String country = address.getCountryCode();
                    if (!TextUtils.isEmpty(country)) {
                        return country;
                    }
                }
                return null;
            } catch (IOException e) {
                return null;
            }
        }

        private String getCountryCodeUsingGoogleApis(Location location) {
            String countryCode = null;
            HttpURLConnection con = null;
            try {
                con = (HttpURLConnection) new URL("http://maps.googleapis.com/maps/api/geocode/json?latlng=" + String.format(Locale.ENGLISH, "%f,%f", new Object[]{Double.valueOf(location.getLatitude()), Double.valueOf(location.getLongitude())})).openConnection();
                con.setConnectTimeout(5000);
                con.setReadTimeout(5000);
                con.setRequestMethod("GET");
                con.setDoInput(true);
                StringBuilder builder = new StringBuilder();
                if (con.getResponseCode() == 200) {
                    char[] buffer = new char[8000];
                    InputStreamReader inputStreamReader = new InputStreamReader(new DataInputStream(con.getInputStream()));
                    while (true) {
                        int charsRead = inputStreamReader.read(buffer, 0, buffer.length);
                        if (charsRead == -1) {
                            break;
                        }
                        builder.append(buffer, 0, charsRead);
                    }
                    inputStreamReader.close();
                }
                JSONArray results = (JSONArray) new JSONObject(builder.toString()).get("results");
                if (results != null) {
                    for (int i = 0; i < results.length(); i++) {
                        JSONArray addressComponents = results.getJSONObject(i).getJSONArray("address_components");
                        if (addressComponents != null) {
                            for (int j = 0; j < addressComponents.length(); j++) {
                                JSONObject addressComponent = addressComponents.getJSONObject(j);
                                JSONArray types = addressComponent.getJSONArray("types");
                                if (types != null) {
                                    for (int k = 0; k < types.length(); k++) {
                                        if (types.getString(k).compareTo("country") == 0) {
                                            String country = addressComponent.getString("short_name");
                                            if (!TextUtils.isEmpty(country)) {
                                                countryCode = country;
                                                break;
                                            }
                                        }
                                    }
                                    if (!TextUtils.isEmpty(countryCode)) {
                                        break;
                                    }
                                }
                            }
                            if (!TextUtils.isEmpty(countryCode)) {
                                break;
                            }
                        }
                    }
                }
                if (con != null) {
                    con.disconnect();
                }
            } catch (JSONException e) {
                e.printStackTrace();
                if (con != null) {
                    con.disconnect();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
                if (con != null) {
                    con.disconnect();
                }
            } catch (Throwable th) {
                if (con != null) {
                    con.disconnect();
                }
            }
            return countryCode;
        }
    }

    private class CountryPolygon {
        private final String _countryCode;
        private final Polygon _polygon;

        public CountryPolygon(String countryCode, Polygon polygon) {
            this._countryCode = countryCode;
            this._polygon = polygon;
        }

        public String getCountryCode() {
            return this._countryCode;
        }

        public Polygon getPolygon() {
            return this._polygon;
        }
    }

    private class ExtendedLocation {
        private double _computedBearing;
        private double _computedSpeed;
        private long _date;
        private Location _location;
        private int _networkType;
        private long _timestampMillis;

        public ExtendedLocation(Location location, long timestampMillis, long date, int networkType) {
            this._location = new Location(location);
            this._timestampMillis = timestampMillis;
            this._date = date;
            this._networkType = networkType;
        }

        public ExtendedLocation(Location location, long timestampMillis, long date, int networkType, double computedSpeed, double computedBearing) {
            this._location = new Location(location);
            this._timestampMillis = timestampMillis;
            this._date = date;
            this._networkType = networkType;
            this._computedSpeed = computedSpeed;
            this._computedBearing = computedBearing;
        }

        public Location getLocation() {
            return this._location;
        }

        public long getTimestampMillis() {
            return this._timestampMillis;
        }

        public long getDate() {
            return this._date;
        }

        public int getNetworkType() {
            return this._networkType;
        }

        public double getComputedSpeed() {
            return this._computedSpeed;
        }

        public double getComputedBearing() {
            return this._computedBearing;
        }
    }

    private class GetAdvertisingIdTask extends AsyncTask<Void, Void, String> {
        private GetAdvertisingIdTask() {
        }

        protected String doInBackground(Void... args) {
            String advertisingId = "";
            try {
                Info advertisingInfo = AdvertisingIdClient.getAdvertisingIdInfo(FloatingCarDataService.this.getApplicationContext());
                if (advertisingInfo != null) {
                    advertisingId = advertisingInfo.getId();
                }
            } catch (GooglePlayServicesRepairableException e) {
                e.printStackTrace();
            } catch (GooglePlayServicesNotAvailableException e2) {
                e2.printStackTrace();
            } catch (Exception e3) {
                e3.printStackTrace();
            }
            return advertisingId;
        }

        protected void onPostExecute(String advertisingId) {
            FloatingCarDataService.this._advertisingId = advertisingId;
        }
    }

    private class GetServerTimeTask extends AsyncTask<Void, Void, String> {
        private GetServerTimeTask() {
        }

        protected String doInBackground(Void... args) {
            String result = null;
            HttpURLConnection con = null;
            try {
                con = (HttpURLConnection) new URL("https://fcd.sygic.com/" + ProjectsConsts.getString(17)).openConnection();
                con.setConnectTimeout(5000);
                con.setReadTimeout(5000);
                con.setRequestMethod("GET");
                con.setDoInput(true);
                StringBuilder builder = new StringBuilder();
                if (con.getResponseCode() == 200) {
                    char[] buffer = new char[8000];
                    InputStreamReader reader = new InputStreamReader(new DataInputStream(con.getInputStream()));
                    while (true) {
                        int charsRead = reader.read(buffer, 0, buffer.length);
                        if (charsRead == -1) {
                            break;
                        }
                        builder.append(buffer, 0, charsRead);
                    }
                    reader.close();
                }
                result = builder.toString();
                if (con != null) {
                    con.disconnect();
                }
            } catch (Exception e) {
                e.printStackTrace();
                return result;
            } finally {
                if (con != null) {
                    con.disconnect();
                }
            }
            return result;
        }

        protected void onPostExecute(String response) {
            long serverReferenceTimeMillis = 0;
            long clientReferenceUptimeMillis = 0;
            if (!TextUtils.isEmpty(response)) {
                for (String line : response.split("\r\n")) {
                    String[] tuple = line.split(":", 2);
                    if (tuple.length >= 2) {
                        String key = tuple[0];
                        String value = tuple[1];
                        if (key.compareTo("servertime") == 0) {
                            try {
                                serverReferenceTimeMillis = Utils.convertSygicTimeSecondsToUnixTimeMillis(Long.parseLong(value));
                                clientReferenceUptimeMillis = SystemClock.elapsedRealtime();
                            } catch (NumberFormatException e) {
                            }
                        }
                    }
                }
            }
            FloatingCarDataService.this._serverReferenceTimeMillis = serverReferenceTimeMillis;
            FloatingCarDataService.this._clientReferenceUptimeMillis = clientReferenceUptimeMillis;
        }
    }

    private class ReadWorldPolygonsTask extends AsyncTask<GeometryFactory, Void, STRtree> {
        private ReadWorldPolygonsTask() {
        }

        protected STRtree doInBackground(GeometryFactory... args) {
            return readWorldPolygons(args[0]);
        }

        protected void onPostExecute(STRtree tree) {
            if (tree == null) {
                FloatingCarDataService.this.stopSelf();
            } else {
                FloatingCarDataService.this._tree = tree;
            }
        }

        private STRtree readWorldPolygons(GeometryFactory geometryFactory) {
            try {
                String str = "fcd.dat";
                InputStream is = FloatingCarDataService.this.getAssets().open(r32);
                byte[] sizeBuf = new byte[4];
                is.read(sizeBuf);
                ByteBuffer sizeConv = ByteBuffer.allocate(4);
                sizeConv.order(ByteOrder.LITTLE_ENDIAN);
                sizeConv.put(sizeBuf);
                ByteBuffer worldBuf = ByteBuffer.allocate(sizeConv.getInt(0));
                worldBuf.order(ByteOrder.LITTLE_ENDIAN);
                byte[] buf = new byte[8192];
                while (true) {
                    int byteCount = is.read(buf);
                    if (byteCount == -1) {
                        break;
                    }
                    worldBuf.put(buf, 0, byteCount);
                }
                is.close();
                STRtree tree = new STRtree();
                worldBuf.position(0);
                int countryCount = worldBuf.getInt();
                for (int i = 0; i < countryCount; i++) {
                    byte[] countryCodeBuf = new byte[3];
                    worldBuf.get(countryCodeBuf);
                    String countryCode = new String(countryCodeBuf);
                    int polygonCount = worldBuf.getInt();
                    for (int j = 0; j < polygonCount; j++) {
                        int coordinateCount = worldBuf.getInt();
                        Coordinate[] coordinates = new Coordinate[coordinateCount];
                        for (int k = 0; k < coordinateCount; k++) {
                            coordinates[k] = new Coordinate(worldBuf.getDouble(), worldBuf.getDouble());
                        }
                        Polygon polygon = geometryFactory.createPolygon(geometryFactory.createLinearRing(coordinates));
                        CountryPolygon countryPolygon = new CountryPolygon(countryCode, polygon);
                        tree.insert(polygon.getEnvelopeInternal(), countryPolygon);
                    }
                }
                tree.build();
                return tree;
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        }
    }

    private class UploadDataTask extends AsyncTask<Void, Void, Boolean> {
        private UploadDataTask() {
        }

        protected Boolean doInBackground(Void... args) {
            List<FcdEntity> fcdEntities = new ArrayList();
            synchronized (FloatingCarDataService._fcdEntitiesLock) {
                if (FloatingCarDataService.this._fcdEntities.size() == 0) {
                    Boolean valueOf = Boolean.valueOf(false);
                    return valueOf;
                }
                fcdEntities.addAll(FloatingCarDataService.this._fcdEntities);
                FloatingCarDataService.this._fcdEntities.clear();
                if (FloatingCarDataService.this._lastUploadTimeStampMillis + 1200000 < SystemClock.elapsedRealtime()) {
                    FloatingCarDataService.this._sessionId = FloatingCarDataService.getSessionId();
                }
                return uploadData(fcdEntities);
            }
        }

        private String quoteString(String stringToQuote) {
            return "\"" + stringToQuote + "\"";
        }

        private Boolean uploadData(List<FcdEntity> fcdEntities) {
            Map<String, String> properties = new HashMap();
            properties.put("#", quoteString("2"));
            properties.put("d", quoteString(FloatingCarDataService.this._deviceId));
            properties.put("a", quoteString(ProjectsConsts.getString(17)));
            properties.put("v", quoteString(FloatingCarDataService.this._appVersion));
            properties.put("p", quoteString(FloatingCarDataService.this._platform));
            properties.put("i", quoteString(""));
            properties.put("m", quoteString(FloatingCarDataService.this._deviceName));
            properties.put("o", quoteString(FloatingCarDataService.this._osVersion));
            properties.put("t", quoteString(FloatingCarDataService.this._advertisingId));
            properties.put("g", quoteString(""));
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            ByteBuffer buf = ByteBuffer.allocate(4);
            buf.order(ByteOrder.LITTLE_ENDIAN);
            for (FcdEntity fcd : fcdEntities) {
                short bearing = (short) ((int) Math.round(fcd.getBearing()));
                if (bearing == (short) 360) {
                    bearing = (short) 0;
                }
                short computedBearing = (short) ((int) Math.round(fcd.getComputedBearing()));
                if (computedBearing == (short) 360) {
                    computedBearing = (short) 0;
                }
                outputStream.write(buf.putInt(0, (int) Utils.convertUnixTimeMillisToSygicTimeSeconds(fcd.getSyncDate())).array(), 0, 4);
                outputStream.write(buf.putInt(0, (int) Utils.convertUnixTimeMillisToSygicTimeSeconds(fcd.getSensorDate())).array(), 0, 4);
                outputStream.write(buf.putInt(0, (int) Utils.convertUnixTimeMillisToSygicTimeSeconds(fcd.getLocalDate())).array(), 0, 4);
                outputStream.write(buf.putInt(0, (int) Math.round(fcd.getLongitude() * 100000.0d)).array(), 0, 4);
                outputStream.write(buf.putInt(0, (int) Math.round(fcd.getLatitude() * 100000.0d)).array(), 0, 4);
                outputStream.write(buf.putShort(0, (short) ((int) Math.round(fcd.getSpeed()))).array(), 0, 2);
                outputStream.write(buf.putShort(0, bearing).array(), 0, 2);
                outputStream.write(fcd.getCountryCode().length() > 0 ? fcd.getCountryCode().charAt(0) : 0);
                outputStream.write(fcd.getCountryCode().length() > 1 ? fcd.getCountryCode().charAt(1) : 0);
                outputStream.write(fcd.getCountryCode().length() > 2 ? fcd.getCountryCode().charAt(2) : 0);
                outputStream.write(0);
                outputStream.write(buf.putShort(0, (short) ((int) Math.round(fcd.getAltitude()))).array(), 0, 2);
                outputStream.write(buf.putShort(0, (short) ((int) Math.round(fcd.getHorizontalAccuracy()))).array(), 0, 2);
                outputStream.write(buf.putShort(0, (short) ((int) Math.round(fcd.getVerticalAccuracy()))).array(), 0, 2);
                outputStream.write(buf.putShort(0, (short) ((int) Math.round(fcd.getComputedSpeed()))).array(), 0, 2);
                outputStream.write(buf.putShort(0, computedBearing).array(), 0, 2);
                outputStream.write(fcd.getForeground() == -1 ? 255 : fcd.getForeground());
                outputStream.write(fcd.getNetworkType());
            }
            try {
                outputStream.flush();
                outputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return sendDataToEventHub(FloatingCarDataService.this._eventHubServiceUrl, FloatingCarDataService.this._eventHubSharedAccessSignature, FloatingCarDataService.this._sessionId, properties, outputStream.toByteArray(), 30000);
        }

        private Boolean sendDataToEventHub(URL serviceUrl, String sharedAccessSignatureToken, String sessionId, Map<String, String> properties, byte[] data, int millisecondsTimeout) {
            boolean result = false;
            int i = 0;
            loop2:
            while (i < 3 && !result) {
                boolean shouldRetry;
                HttpURLConnection con = null;
                int responseCode = 0;
                try {
                    con = (HttpURLConnection) serviceUrl.openConnection();
                    con.setConnectTimeout(30000);
                    con.setReadTimeout(30000);
                    con.setRequestMethod("POST");
                    con.setRequestProperty("Content-Type", "application/octet-stream");
                    con.setRequestProperty("Authorization", sharedAccessSignatureToken);
                    if (!TextUtils.isEmpty(sessionId)) {
                        con.setRequestProperty("BrokerProperties", String.format(Locale.ENGLISH, "{\"PartitionKey\":\"%s\"}", new Object[]{sessionId}));
                    }
                    if (properties != null && properties.size() > 0) {
                        for (Entry<String, String> entry : properties.entrySet()) {
                            con.setRequestProperty((String) entry.getKey(), (String) entry.getValue());
                        }
                    }
                    con.setDoInput(true);
                    con.setDoOutput(true);
                    if (data != null) {
                        DataOutputStream outputStream = new DataOutputStream(con.getOutputStream());
                        outputStream.write(data);
                        outputStream.flush();
                        outputStream.close();
                    }
                    responseCode = con.getResponseCode();
                    if (con != null) {
                        con.disconnect();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    result = responseCode == 201;
                    if (result) {
                    }
                    if (shouldRetry) {
                        break loop2;
                    }
                    try {
                        Thread.sleep(5000);
                    } catch (InterruptedException e2) {
                    }
                    i++;
                } finally {
                    if (con != null) {
                        con.disconnect();
                    }
                }
                if (responseCode == 201) {
                }
                shouldRetry = result && i < 2 && responseCode != 0 && !((responseCode >= 400 && responseCode < 500) || responseCode == 306 || responseCode == 501 || responseCode == 505);
                if (shouldRetry) {
                    break loop2;
                }
                Thread.sleep(5000);
                i++;
            }
            return Boolean.valueOf(result);
        }

        protected void onPostExecute(Boolean result) {
            if (result.booleanValue()) {
                FloatingCarDataService.this._lastUploadTimeStampMillis = SystemClock.elapsedRealtime();
            }
        }
    }

    static {
        _locationLock = new Object();
        _fcdEntitiesLock = new Object();
    }

    public void onCreate() {
        super.onCreate();
        this._geometryFactory = new GeometryFactory(new PrecisionModel(), 4326);
        this._tree = null;
        new ReadWorldPolygonsTask().execute(new GeometryFactory[]{this._geometryFactory});
        try {
            this._eventHubServiceUrl = new URL(String.format(Locale.ENGLISH, "https://%s.servicebus.windows.net/%s/messages?timeout=60&api-version=2015-01", new Object[]{"sygic-fcd", "fcd"}));
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(new Date());
            calendar.add(1, 10);
            this._eventHubSharedAccessSignature = generateEventHubSharedAccessSignatureToken(this._eventHubServiceUrl, "Sender", "Miz2Sv1KAwcKsSON6O7E5YVWodr0ed2DVLEtRvNT98Q=", calendar.getTime());
            this._serverReferenceTimeMillis = 0;
            this._clientReferenceUptimeMillis = 0;
            try {
                this._appVersion = getPackageManager().getPackageInfo(getPackageName(), 0).versionName;
            } catch (NameNotFoundException e) {
                this._appVersion = "";
                e.printStackTrace();
            }
            this._deviceId = Utils.getDeviceId(this);
            if (!TextUtils.isEmpty(this._deviceId)) {
                try {
                    byte[] digest = MessageDigest.getInstance("MD5").digest(this._deviceId.getBytes());
                    StringBuilder builder = new StringBuilder();
                    for (byte b : digest) {
                        builder.append(String.format(Locale.ENGLISH, "%02x", new Object[]{Byte.valueOf(b)}));
                    }
                    this._deviceId = builder.toString();
                } catch (NoSuchAlgorithmException e2) {
                }
            }
            this._platform = "Android";
            this._osVersion = VERSION.RELEASE;
            this._deviceName = Utils.getDeviceName();
            new GetAdvertisingIdTask().execute(new Void[0]);
            this._sessionId = getSessionId();
            this._networkType = -1;
            this._previousLocation = null;
            this._lastLocation = null;
            this._goodLocationCounter = 0;
            this._badLocationCounter = 0;
            this._lastUploadTimeStampMillis = 0;
            this._currentCountryCode = null;
            this._currentCountryCodeLocation = null;
            this._fcdEntities = new ArrayList();
            BroadcastReceiver c12081 = new C12081();
            this._networkStateReceiver = c12081;
            registerReceiver(c12081, new IntentFilter("android.net.conn.CONNECTIVITY_CHANGE"));
            this._locationManager = (LocationManager) getSystemService("location");
            this._locationIntent = PendingIntent.getService(getApplicationContext(), 0, new Intent(this, FloatingCarDataService.class), 268435456);
        } catch (Exception e3) {
            e3.printStackTrace();
            stopSelf();
        }
    }

    public int onStartCommand(Intent intent, int flags, int startId) {
        if (intent != null) {
            Bundle extras = intent.getExtras();
            if (extras != null) {
                Location location = (Location) extras.get("location");
                if (location != null) {
                    onLocationChanged(location);
                }
            }
        }
        return 1;
    }

    public IBinder onBind(Intent intent) {
        return null;
    }

    public void onDestroy() {
        if (this._networkStateReceiver != null) {
            unregisterReceiver(this._networkStateReceiver);
            this._networkStateReceiver = null;
        }
        removeLocationUpdates();
        stopCollectingData();
        stopUploadingData();
        super.onDestroy();
    }

    private void requestLocationUpdates() {
        if (this._locationManager != null) {
            this._locationManager.requestLocationUpdates("passive", 1000, 0.0f, this._locationIntent);
        }
    }

    private void removeLocationUpdates() {
        if (this._locationManager != null) {
            this._locationManager.removeUpdates(this._locationIntent);
        }
    }

    @TargetApi(18)
    private static boolean isMockLocation(Location location) {
        return VERSION.SDK_INT >= 18 && location.isFromMockProvider();
    }

    private void onLocationChanged(Location location) {
        if (!isMockLocation(location) && location.hasAccuracy() && location.getAccuracy() <= 20.0f && !TextUtils.isEmpty(location.getProvider()) && location.getProvider().equals("gps")) {
            long elapsedRealtime = SystemClock.elapsedRealtime();
            long timeNow = System.currentTimeMillis();
            synchronized (_locationLock) {
                if (this._lastLocation == null || this._lastLocation.getTimestampMillis() == 0 || elapsedRealtime - this._lastLocation.getTimestampMillis() < 8000) {
                    this._goodLocationCounter++;
                    this._badLocationCounter = 0;
                } else {
                    this._goodLocationCounter = 0;
                    this._badLocationCounter++;
                }
                if (this._lastLocation != null) {
                    this._previousLocation = this._lastLocation;
                }
                this._lastLocation = new ExtendedLocation(location, elapsedRealtime, timeNow, this._networkType);
            }
            if (this._goodLocationCounter == 10) {
                new GetServerTimeTask().execute(new Void[0]);
                startCollectingData();
                startUploadingData();
            }
            if (this._badLocationCounter == 10) {
                stopCollectingData();
                stopUploadingData();
                synchronized (_fcdEntitiesLock) {
                    this._fcdEntities.clear();
                }
            }
        }
    }

    private void onNetworkStateChanged() {
        boolean isNetworkConnected = false;
        int networkType = -1;
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService("connectivity");
        if (connectivityManager != null) {
            NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
            if (networkInfo != null) {
                isNetworkConnected = networkInfo.isConnected();
                networkType = networkInfo.getType();
            }
        }
        this._networkType = networkType;
        if (isNetworkConnected) {
            requestLocationUpdates();
            return;
        }
        removeLocationUpdates();
        stopCollectingData();
        stopUploadingData();
        synchronized (_fcdEntitiesLock) {
            this._fcdEntities.clear();
        }
    }

    private void startCollectingData() {
        if (this._handlerCollectData == null) {
            this._handlerCollectData = new Handler();
            Handler handler = this._handlerCollectData;
            Runnable c12092 = new C12092();
            this._runnableCollectData = c12092;
            handler.postDelayed(c12092, 1000);
        }
    }

    private void stopCollectingData() {
        if (this._handlerCollectData != null) {
            if (this._runnableCollectData != null) {
                this._handlerCollectData.removeCallbacksAndMessages(null);
            }
            this._handlerCollectData = null;
            synchronized (_locationLock) {
                this._previousLocation = null;
                this._lastLocation = null;
            }
        }
    }

    private void startUploadingData() {
        if (this._handlerUploadData == null) {
            this._handlerUploadData = new Handler();
            Handler handler = this._handlerUploadData;
            Runnable c12103 = new C12103();
            this._runnableUploadData = c12103;
            handler.postDelayed(c12103, 60000);
        }
    }

    private void stopUploadingData() {
        if (this._handlerUploadData != null) {
            if (this._runnableUploadData != null) {
                this._handlerUploadData.removeCallbacksAndMessages(null);
            }
            this._handlerUploadData = null;
        }
    }

    private static String getSessionId() {
        return UUID.randomUUID().toString().replace("-", "");
    }

    private static String generateEventHubSharedAccessSignatureToken(URL serviceUrl, String keyName, String key, Date tokenExpiry) throws UnsupportedEncodingException, NoSuchAlgorithmException, InvalidKeyException {
        long expiryUnixTimestamp = tokenExpiry.getTime() / 1000;
        String escapedUrl = URLEncoder.encode(serviceUrl.getHost(), "UTF-8");
        String stringToSign = String.format(Locale.ENGLISH, "%s\n%d", new Object[]{escapedUrl, Long.valueOf(expiryUnixTimestamp)});
        SecretKeySpec keySpec = new SecretKeySpec(key.getBytes(), "HmacSHA256");
        Mac hmac = Mac.getInstance("HmacSHA256");
        hmac.init(keySpec);
        String signature = Base64.encodeToString(hmac.doFinal(stringToSign.getBytes()), 2);
        return String.format(Locale.ENGLISH, "SharedAccessSignature sr=%s&sig=%s&se=%d&skn=%s", new Object[]{escapedUrl, URLEncoder.encode(signature, "UTF-8"), Long.valueOf(expiryUnixTimestamp), keyName});
    }
}
