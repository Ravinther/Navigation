package com.sygic.aura.tracker;

import android.app.IntentService;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import com.sygic.aura.analytics.InfinarioAnalyticsLogger;
import com.sygic.aura.c2dm.C2DMessaging;
import com.sygic.aura.connectivity.ConnectivityChangesManager;
import com.sygic.aura.connectivity.ConnectivityChangesManager.ConnType;
import com.sygic.aura.tracker.model.Batch;
import com.sygic.aura.tracker.model.Event;
import com.sygic.aura.tracker.model.NightlyEvent;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import javax.net.ssl.HttpsURLConnection;
import loquendo.tts.engine.TTSConst;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class TrackerService extends IntentService {

    /* renamed from: com.sygic.aura.tracker.TrackerService.1 */
    static /* synthetic */ class C17521 {
        static final /* synthetic */ int[] f1293x6345db1;

        static {
            f1293x6345db1 = new int[ConnType.values().length];
            try {
                f1293x6345db1[ConnType.OFFLINE.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                f1293x6345db1[ConnType.WIFI.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                f1293x6345db1[ConnType.MOBILE_2G.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
            try {
                f1293x6345db1[ConnType.MOBILE_3G.ordinal()] = 4;
            } catch (NoSuchFieldError e4) {
            }
            try {
                f1293x6345db1[ConnType.MOBILE_4G.ordinal()] = 5;
            } catch (NoSuchFieldError e5) {
            }
            try {
                f1293x6345db1[ConnType.MOBILE_UNKNOWN.ordinal()] = 6;
            } catch (NoSuchFieldError e6) {
            }
            try {
                f1293x6345db1[ConnType.UNKNOWN.ordinal()] = 7;
            } catch (NoSuchFieldError e7) {
            }
        }
    }

    private class Interval {
        private long mEnd;
        private long mStart;

        private Interval() {
        }

        public void setStart(long start) {
            this.mStart = start;
        }

        public void setEnd(long end) {
            this.mEnd = end;
        }

        public long getStart() {
            return this.mStart;
        }

        public long getEnd() {
            return this.mEnd;
        }

        public String toString() {
            return "interval (" + this.mStart + "," + this.mEnd + "    d=" + (this.mEnd - this.mStart) + ")";
        }
    }

    public TrackerService() {
        super(TrackerService.class.getName());
    }

    protected void onHandleIntent(Intent intent) {
        if (intent == null) {
            return;
        }
        if ("com.sygic.aura.ACTION_DISPATCH".equals(intent.getAction())) {
            dispatchAnalytics(intent.getBooleanExtra("forced", false));
        } else if ("com.sygic.aura.ACTION_NIGHTLY".equals(intent.getAction())) {
            collectNightlyStatistics();
        }
    }

    private void collectNightlyStatistics() {
        logi("--- Collect nightly statistics");
        String deviceId = TrackerUtils.getDeviceId(this);
        String pushId = C2DMessaging.getRegistrationId(this);
        long freeMegabytes = TrackerUtils.getFreeDiskSpaceInMegabytes(this);
        String[] installedPackageNames = null;
        List<ApplicationInfo> installedApps = getPackageManager().getInstalledApplications(128);
        if (installedApps != null && installedApps.size() > 0) {
            installedPackageNames = new String[installedApps.size()];
            for (int i = 0; i < installedApps.size(); i++) {
                installedPackageNames[i] = ((ApplicationInfo) installedApps.get(i)).packageName;
            }
        }
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        String flattenedMaps = prefs.getString("installed_maps", null);
        String[] installedMaps = null;
        if (!TextUtils.isEmpty(flattenedMaps)) {
            installedMaps = flattenedMaps.split(";");
        }
        int kmWithRoute = prefs.getInt("trip_with_route", -1);
        int kmWithoutRoute = prefs.getInt("trip_without_route", -1);
        long dayStartTimestamp = initDayStartTimestamp();
        long dayEndTimestamp = initDayEndTimestamp();
        HashMap<String, Long> allDurations = new HashMap();
        calculateConnectivityDurations(allDurations, dayStartTimestamp, dayEndTimestamp);
        HashMap<String, Integer> allConnectivity = new HashMap();
        calculatePercentageFromDurations(allDurations, allConnectivity);
        HashMap<String, Long> fgDurations = new HashMap();
        calculateForegroundConnectivityDurations(fgDurations, dayStartTimestamp, dayEndTimestamp);
        HashMap<String, Integer> fgConnectivity = new HashMap();
        calculatePercentageFromDurations(fgDurations, fgConnectivity);
        InfinarioAnalyticsLogger.getInstance(this).trackNightly(kmWithRoute, kmWithoutRoute, installedMaps, installedPackageNames);
        SygicTracker.get(this).sendEvent(new NightlyEvent(deviceId, pushId, freeMegabytes, installedPackageNames, allConnectivity, fgConnectivity, installedMaps, kmWithRoute, kmWithoutRoute));
        SygicTracker.get(this).forceDispatch();
    }

    private void calculatePercentageFromDurations(HashMap<String, Long> durations, HashMap<String, Integer> percentages) {
        long sum = 0;
        for (String key : durations.keySet()) {
            sum += ((Long) durations.get(key)).longValue();
        }
        for (String key2 : durations.keySet()) {
            percentages.put(key2, Integer.valueOf((int) ((((double) ((Long) durations.get(key2)).longValue()) / ((double) sum)) * 100.0d)));
        }
    }

    private void calculateForegroundConnectivityDurations(HashMap<String, Long> foregroundDurations, long dayStartTimestamp, long dayEndTimestamp) {
        ArrayList<Interval> foregroundIntervals = new ArrayList();
        TrackerService trackerService = this;
        Interval interval = new Interval();
        Cursor cursor = ConnectivityChangesManager.get(this).getForegroundChangesIn(dayStartTimestamp, dayEndTimestamp);
        if (cursor != null) {
            while (cursor.moveToNext()) {
                boolean inForeground = cursor.getInt(cursor.getColumnIndex("app_in_foreground")) == 1;
                long timestamp = cursor.getLong(cursor.getColumnIndex("timestamp"));
                if (inForeground) {
                    trackerService = this;
                    interval = new Interval();
                    interval.setStart(timestamp);
                    if (cursor.getPosition() == cursor.getCount() - 1) {
                        interval.setEnd(dayEndTimestamp);
                        foregroundIntervals.add(interval);
                    }
                } else {
                    if (cursor.getPosition() == 0) {
                        interval.setStart(dayStartTimestamp);
                    }
                    interval.setEnd(timestamp);
                    foregroundIntervals.add(interval);
                }
            }
            cursor.close();
        }
        Iterator it = foregroundIntervals.iterator();
        while (it.hasNext()) {
            Interval fgInterval = (Interval) it.next();
            calculateConnectivityDurations(foregroundDurations, fgInterval.getStart(), fgInterval.getEnd());
        }
    }

    private void calculateConnectivityDurations(HashMap<String, Long> durations, long intervalStartTimestamp, long intervalEndTimestamp) {
        Cursor cursor = ConnectivityChangesManager.get(this).getChangesIn(intervalStartTimestamp, intervalEndTimestamp);
        ConnType prevType = ConnType.UNKNOWN;
        Cursor cursorPrev = ConnectivityChangesManager.get(this).getChangesBefore(intervalStartTimestamp);
        if (cursorPrev != null) {
            if (cursorPrev.moveToFirst()) {
                prevType = ConnType.values()[cursorPrev.getInt(cursorPrev.getColumnIndex("type"))];
            }
            cursorPrev.close();
        }
        long prevTimestamp = intervalStartTimestamp;
        if (cursor != null) {
            while (cursor.moveToNext()) {
                ConnType type = ConnType.values()[cursor.getInt(cursor.getColumnIndex("type"))];
                long timestamp = cursor.getLong(cursor.getColumnIndex("timestamp"));
                cumulateDuration(durations, prevType, timestamp - prevTimestamp);
                prevTimestamp = timestamp;
                prevType = type;
            }
            cursor.close();
        }
        cumulateDuration(durations, prevType, intervalEndTimestamp - prevTimestamp);
    }

    private void cumulateDuration(HashMap<String, Long> durations, ConnType type, long duration) {
        switch (C17521.f1293x6345db1[type.ordinal()]) {
            case TTSConst.TTSMULTILINE /*1*/:
                cumulate(durations, "offline", duration);
            case TTSConst.TTSPARAGRAPH /*2*/:
                cumulate(durations, "wifi", duration);
            case TTSConst.TTSUNICODE /*3*/:
                cumulate(durations, "mobile_2g", duration);
            case TTSConst.TTSXML /*4*/:
                cumulate(durations, "mobile_3g", duration);
            case TTSConst.TTSEVT_TEXT /*5*/:
                cumulate(durations, "mobile_4g", duration);
            case TTSConst.TTSEVT_SENTENCE /*6*/:
                cumulate(durations, "mobile_unknown", duration);
            default:
                cumulate(durations, "unknown", duration);
        }
    }

    private void cumulate(HashMap<String, Long> durations, String key, long duration) {
        durations.put(key, Long.valueOf(((Long) TrackerUtils.get(durations, key, Long.valueOf(0))).longValue() + duration));
    }

    private long initDayStartTimestamp() {
        Calendar calendarStart = Calendar.getInstance();
        calendarStart.add(5, -1);
        calendarStart.set(11, 0);
        calendarStart.set(12, 0);
        calendarStart.set(13, 0);
        calendarStart.set(14, 1);
        return calendarStart.getTimeInMillis();
    }

    private long initDayEndTimestamp() {
        Calendar calendarEnd = Calendar.getInstance();
        calendarEnd.set(11, 0);
        calendarEnd.set(12, 0);
        calendarEnd.set(13, 0);
        calendarEnd.set(14, 1);
        return calendarEnd.getTimeInMillis();
    }

    private void dispatchAnalytics(boolean forced) {
        List<Batch> batches;
        logi("--- Begin dispatching analytics # forced=" + forced);
        SygicTracker tracker = SygicTracker.get(this);
        HashMap<String, String> metadata = tracker.getMetadata();
        EventsCache cache = tracker.getEventsCache();
        if (forced) {
            batches = cache.getAllBatchesCopy();
        } else {
            batches = cache.getUndispatchedBatchesCopy();
        }
        if (!batches.isEmpty()) {
            for (Batch batch : batches) {
                if (dispatchBatch(batch, metadata)) {
                    batch.markDispatched();
                    logd("Dispatch of " + batch.getId() + " successful, marking dispatched");
                } else {
                    logd("Giving up on " + batch.getId());
                }
            }
            cache.removeDispatchedBatches();
        }
        logi("--- End dispatching analytics");
    }

    private boolean dispatchBatch(Batch batch, HashMap<String, String> metadata) {
        if (isNetworkAvailable()) {
            String body = generateJsonBody(batch, metadata);
            logd("JSON=" + body);
            if (!TextUtils.isEmpty(body)) {
                return dispatchWithRetry(body);
            }
            loge("Batch " + batch.getId() + " is empty");
            return false;
        }
        loge("Network unavailable");
        return false;
    }

    private String generateJsonBody(Batch batch, HashMap<String, String> metadata) {
        JSONObject json = new JSONObject();
        try {
            json.put("id", batch.getId());
            json.put("timestamp", TrackerUtils.getCurrentTime());
        } catch (JSONException e) {
        }
        if (metadata == null || metadata.isEmpty()) {
            return null;
        }
        JSONObject jsonMetadata = new JSONObject();
        for (String key : metadata.keySet()) {
            try {
                jsonMetadata.put(key, metadata.get(key));
            } catch (JSONException e2) {
            }
        }
        try {
            json.put("metadata", jsonMetadata);
        } catch (JSONException e3) {
        }
        List<Event> events = batch.getEvents();
        if (events == null || events.isEmpty()) {
            return null;
        }
        try {
            JSONArray jsonEvents = new JSONArray();
            synchronized (events) {
                for (Event event : events) {
                    JSONObject jsonEvent = new JSONObject();
                    event.toJson(jsonEvent);
                    jsonEvents.put(jsonEvent);
                }
            }
            JSONObject jsonBatch = new JSONObject();
            jsonBatch.put("events", jsonEvents);
            json.put("batch", jsonBatch);
        } catch (JSONException e4) {
        }
        return json.toString();
    }

    private boolean dispatchWithRetry(String body) {
        for (int i = 0; i < 3; i++) {
            if (doDispatchBatch(body)) {
                return true;
            }
            logd("Retry:" + i + " ...");
            try {
                Thread.sleep((long) (i * 1000));
            } catch (InterruptedException e) {
            }
        }
        return false;
    }

    private boolean doDispatchBatch(String payload) {
        HttpsURLConnection conn = null;
        boolean success = false;
        try {
            conn = (HttpsURLConnection) new URL("https://ts.sygic.com/api/v1/app-events").openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestProperty("Content-Length", Integer.toString(payload.length()));
            conn.setDoOutput(true);
            OutputStream os = conn.getOutputStream();
            os.write(payload.getBytes());
            os.close();
            if (conn.getResponseCode() == 200) {
                success = true;
            }
            if (conn != null) {
                conn.disconnect();
            }
        } catch (IOException e) {
            success = false;
            if (conn != null) {
                conn.disconnect();
            }
        } catch (Throwable th) {
            if (conn != null) {
                conn.disconnect();
            }
        }
        return success;
    }

    private boolean isNetworkAvailable() {
        NetworkInfo networkInfo = ((ConnectivityManager) getSystemService("connectivity")).getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isConnected();
    }

    private void logd(String msg) {
    }

    private void logi(String msg) {
    }

    private void loge(String msg) {
    }
}
