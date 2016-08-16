package com.sygic.widget;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.util.ArrayMap;
import android.util.SparseArray;
import com.sygic.aura.helper.CrashlyticsHelper;
import com.sygic.aura.map.data.MemoItem.EMemoType;
import com.sygic.widget.places.PlacesFakeAdapter;
import com.sygic.widget.places.data.PlaceEntry;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import loquendo.tts.engine.TTSConst;

public class WidgetDataProvider extends ContentProvider {
    private static Uri sContentUri;
    private ArrayList<PlaceDataPoint> mDataPoints;
    private PlacesFakeAdapter mPlacesAdapter;
    private SparseArray<EMemoType> mWidgetStates;

    public class PlaceDataPoint {
        public int delaySeconds;
        public int freeSeconds;
        public float lat;
        public float lon;
        public int memoType;
        public String placeName;

        PlaceDataPoint(String name, int delay, int free, float lat, float lon, int memoType) {
            this.placeName = name;
            this.delaySeconds = delay;
            this.freeSeconds = free;
            this.lat = lat;
            this.lon = lon;
            this.memoType = memoType;
        }

        public boolean satisfySelection(String selection, String[] selectionArgs) {
            Map<String, String> map = split(selection, selectionArgs);
            for (String key : map.keySet()) {
                Object obj = -1;
                switch (key.hashCode()) {
                    case -1067310595:
                        if (key.equals("traffic")) {
                            int i = 1;
                            break;
                        }
                        break;
                    case 101147:
                        if (key.equals("fav")) {
                            obj = null;
                            break;
                        }
                        break;
                    case 106911:
                        if (key.equals("lat")) {
                            obj = 3;
                            break;
                        }
                        break;
                    case 107339:
                        if (key.equals("lon")) {
                            obj = 4;
                            break;
                        }
                        break;
                    case 3560141:
                        if (key.equals("time")) {
                            obj = 2;
                            break;
                        }
                        break;
                    case 1708943135:
                        if (key.equals("memo_type")) {
                            obj = 5;
                            break;
                        }
                        break;
                }
                switch (obj) {
                    case TTSConst.TTSEVT_AUDIOSTART /*0*/:
                        if (this.placeName.compareTo((String) map.get(key)) == 0) {
                            break;
                        }
                        return false;
                    case TTSConst.TTSMULTILINE /*1*/:
                        if (compareInt(this.delaySeconds, (String) map.get(key))) {
                            break;
                        }
                        return false;
                    case TTSConst.TTSPARAGRAPH /*2*/:
                        if (compareInt(this.freeSeconds, (String) map.get(key))) {
                            break;
                        }
                        return false;
                    case TTSConst.TTSUNICODE /*3*/:
                        if (compareFloat(this.lat, (String) map.get(key))) {
                            break;
                        }
                        return false;
                    case TTSConst.TTSXML /*4*/:
                        if (compareFloat(this.lon, (String) map.get(key))) {
                            break;
                        }
                        return false;
                    case TTSConst.TTSEVT_TEXT /*5*/:
                        if (compareInt(this.memoType, (String) map.get(key))) {
                            break;
                        }
                        return false;
                    default:
                        break;
                }
            }
            return true;
        }

        private boolean compareInt(int value, String stringValue) {
            try {
                if (value == Integer.parseInt(stringValue)) {
                    return true;
                }
            } catch (NumberFormatException e) {
                CrashlyticsHelper.logException("WIDGET DATA", e.getMessage(), e);
            }
            return false;
        }

        private boolean compareFloat(float value, String stringValue) {
            try {
                if (value == Float.parseFloat(stringValue)) {
                    return true;
                }
            } catch (NumberFormatException e) {
                CrashlyticsHelper.logException("WIDGET DATA", e.getMessage(), e);
            }
            return false;
        }

        private Map<String, String> split(String selectionText, String[] selectionArgs) {
            Map<String, String> resultMap = new ArrayMap(selectionArgs.length);
            String[] selections = selectionText.split(" ");
            int j = 0;
            for (int i = 0; i < selections.length; i++) {
                if (selections[i].contains("?") && selectionArgs.length > j) {
                    int j2 = j + 1;
                    selections[i] = selections[i].replace("?", selectionArgs[j]);
                    j = j2;
                }
                String[] keyValue = selections[i].split("=");
                if (keyValue.length == 2) {
                    resultMap.put(keyValue[0], keyValue[1]);
                }
            }
            return resultMap;
        }

        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }
            PlaceDataPoint that = (PlaceDataPoint) o;
            if (Float.compare(that.lat, this.lat) != 0) {
                return false;
            }
            if (Float.compare(that.lon, this.lon) != 0) {
                return false;
            }
            return true;
        }

        public int hashCode() {
            int result;
            int i = 0;
            if (this.lat != 0.0f) {
                result = Float.floatToIntBits(this.lat);
            } else {
                result = 0;
            }
            int i2 = result * 31;
            if (this.lon != 0.0f) {
                i = Float.floatToIntBits(this.lon);
            }
            return i2 + i;
        }
    }

    public WidgetDataProvider() {
        this.mDataPoints = new ArrayList();
        this.mWidgetStates = new SparseArray(5);
    }

    public boolean onCreate() {
        this.mPlacesAdapter = new PlacesFakeAdapter(getContext());
        updatePlacesData();
        return true;
    }

    public synchronized Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        MatrixCursor cursor;
        boolean useAll;
        if (projection == null) {
            useAll = true;
            projection = new String[]{"fav", "time", "traffic", "lat", "lon", "memo_type"};
        } else {
            useAll = false;
        }
        cursor = new MatrixCursor(projection);
        List<Object> dataList = new ArrayList(projection.length);
        List<String> projectionList = Arrays.asList(projection);
        Iterator it = this.mDataPoints.iterator();
        while (it.hasNext()) {
            PlaceDataPoint data = (PlaceDataPoint) it.next();
            if (data.satisfySelection(selection, selectionArgs)) {
                if (useAll) {
                    cursor.addRow(new Object[]{data.placeName, Integer.valueOf(data.freeSeconds), Integer.valueOf(data.delaySeconds), Float.valueOf(data.lat), Float.valueOf(data.lon), Integer.valueOf(data.memoType)});
                } else {
                    if (projectionList.contains("fav")) {
                        dataList.add(data.placeName);
                    } else if (projectionList.contains("time")) {
                        dataList.add(Integer.valueOf(data.freeSeconds));
                    } else if (projectionList.contains("traffic")) {
                        dataList.add(Integer.valueOf(data.delaySeconds));
                    } else if (projectionList.contains("lat")) {
                        dataList.add(Float.valueOf(data.lat));
                    } else if (projectionList.contains("lon")) {
                        dataList.add(Float.valueOf(data.lon));
                    } else if (projectionList.contains("memo_type")) {
                        dataList.add(Integer.valueOf(data.memoType));
                    }
                    cursor.addRow(dataList.toArray(new Object[dataList.size()]));
                }
            }
        }
        return cursor;
    }

    public String getType(Uri uri) {
        return "vnd.android.cursor.dir/vnd.trafficwidget.favtime";
    }

    public Uri insert(Uri uri, ContentValues values) {
        return null;
    }

    public int delete(Uri uri, String selection, String[] selectionArgs) {
        return 0;
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public synchronized int update(android.net.Uri r16, android.content.ContentValues r17, java.lang.String r18, java.lang.String[] r19) {
        /*
        r15 = this;
        monitor-enter(r15);
        r10 = 0;
        r5 = 0;
        r7 = com.sygic.widget.TrafficWidgetProvider.LOCATION_INVALID;	 Catch:{ all -> 0x0116 }
        r8 = com.sygic.widget.TrafficWidgetProvider.LOCATION_INVALID;	 Catch:{ all -> 0x0116 }
        r12 = " ";
        r0 = r18;
        r11 = r0.split(r12);	 Catch:{ all -> 0x0116 }
        r4 = 0;
        r6 = r5;
    L_0x0012:
        r12 = r11.length;	 Catch:{ all -> 0x0119 }
        if (r4 >= r12) goto L_0x00b0;
    L_0x0015:
        r12 = r11[r4];	 Catch:{ all -> 0x0119 }
        r13 = "=";
        r9 = r12.split(r13);	 Catch:{ all -> 0x0119 }
        r12 = r9.length;	 Catch:{ all -> 0x0119 }
        r13 = 2;
        if (r12 == r13) goto L_0x0027;
    L_0x0022:
        r5 = r6;
    L_0x0023:
        r4 = r4 + 1;
        r6 = r5;
        goto L_0x0012;
    L_0x0027:
        r12 = 0;
        r13 = r9[r12];	 Catch:{ all -> 0x0119 }
        r12 = -1;
        r14 = r13.hashCode();	 Catch:{ all -> 0x0119 }
        switch(r14) {
            case 106911: goto L_0x0066;
            case 107339: goto L_0x005b;
            default: goto L_0x0032;
        };	 Catch:{ all -> 0x0119 }
    L_0x0032:
        switch(r12) {
            case 0: goto L_0x0071;
            case 1: goto L_0x0090;
            default: goto L_0x0035;
        };	 Catch:{ all -> 0x0119 }
    L_0x0035:
        r12 = "WIDGET DATA";
        r13 = new java.lang.StringBuilder;	 Catch:{ all -> 0x0119 }
        r13.<init>();	 Catch:{ all -> 0x0119 }
        r14 = "updating of ";
        r13 = r13.append(r14);	 Catch:{ all -> 0x0119 }
        r14 = 0;
        r14 = r9[r14];	 Catch:{ all -> 0x0119 }
        r13 = r13.append(r14);	 Catch:{ all -> 0x0119 }
        r14 = " is not supported!";
        r13 = r13.append(r14);	 Catch:{ all -> 0x0119 }
        r13 = r13.toString();	 Catch:{ all -> 0x0119 }
        com.sygic.aura.helper.CrashlyticsHelper.logWarning(r12, r13);	 Catch:{ all -> 0x0119 }
        r5 = r6;
        goto L_0x0023;
    L_0x005b:
        r14 = "lon";
        r13 = r13.equals(r14);	 Catch:{ all -> 0x0119 }
        if (r13 == 0) goto L_0x0032;
    L_0x0064:
        r12 = 0;
        goto L_0x0032;
    L_0x0066:
        r14 = "lat";
        r13 = r13.equals(r14);	 Catch:{ all -> 0x0119 }
        if (r13 == 0) goto L_0x0032;
    L_0x006f:
        r12 = 1;
        goto L_0x0032;
    L_0x0071:
        r0 = r19;
        r12 = r0.length;	 Catch:{ all -> 0x0119 }
        if (r12 <= r6) goto L_0x011c;
    L_0x0076:
        r12 = 1;
        r12 = r9[r12];	 Catch:{ all -> 0x0119 }
        r13 = 0;
        r12 = r12.charAt(r13);	 Catch:{ all -> 0x0119 }
        r13 = 63;
        if (r12 != r13) goto L_0x008b;
    L_0x0082:
        r5 = r6 + 1;
        r12 = r19[r6];	 Catch:{ all -> 0x0116 }
    L_0x0086:
        r8 = java.lang.Float.parseFloat(r12);	 Catch:{ all -> 0x0116 }
        goto L_0x0023;
    L_0x008b:
        r12 = 1;
        r12 = r9[r12];	 Catch:{ all -> 0x0119 }
        r5 = r6;
        goto L_0x0086;
    L_0x0090:
        r0 = r19;
        r12 = r0.length;	 Catch:{ all -> 0x0119 }
        if (r12 <= r6) goto L_0x011c;
    L_0x0095:
        r12 = 1;
        r12 = r9[r12];	 Catch:{ all -> 0x0119 }
        r13 = 0;
        r12 = r12.charAt(r13);	 Catch:{ all -> 0x0119 }
        r13 = 63;
        if (r12 != r13) goto L_0x00ab;
    L_0x00a1:
        r5 = r6 + 1;
        r12 = r19[r6];	 Catch:{ all -> 0x0116 }
    L_0x00a5:
        r7 = java.lang.Float.parseFloat(r12);	 Catch:{ all -> 0x0116 }
        goto L_0x0023;
    L_0x00ab:
        r12 = 1;
        r12 = r9[r12];	 Catch:{ all -> 0x0119 }
        r5 = r6;
        goto L_0x00a5;
    L_0x00b0:
        r12 = r15.mDataPoints;	 Catch:{ all -> 0x0119 }
        r12 = r12.iterator();	 Catch:{ all -> 0x0119 }
    L_0x00b6:
        r13 = r12.hasNext();	 Catch:{ all -> 0x0119 }
        if (r13 == 0) goto L_0x00ef;
    L_0x00bc:
        r3 = r12.next();	 Catch:{ all -> 0x0119 }
        r3 = (com.sygic.widget.WidgetDataProvider.PlaceDataPoint) r3;	 Catch:{ all -> 0x0119 }
        r13 = r3.lat;	 Catch:{ all -> 0x0119 }
        r13 = (r13 > r7 ? 1 : (r13 == r7 ? 0 : -1));
        if (r13 != 0) goto L_0x00b6;
    L_0x00c8:
        r13 = r3.lon;	 Catch:{ all -> 0x0119 }
        r13 = (r13 > r8 ? 1 : (r13 == r8 ? 0 : -1));
        if (r13 != 0) goto L_0x00b6;
    L_0x00ce:
        r13 = "time";
        r0 = r17;
        r13 = r0.getAsInteger(r13);	 Catch:{ all -> 0x0119 }
        r13 = r13.intValue();	 Catch:{ all -> 0x0119 }
        r3.freeSeconds = r13;	 Catch:{ all -> 0x0119 }
        r13 = "traffic";
        r0 = r17;
        r13 = r0.getAsInteger(r13);	 Catch:{ all -> 0x0119 }
        r13 = r13.intValue();	 Catch:{ all -> 0x0119 }
        r3.delaySeconds = r13;	 Catch:{ all -> 0x0119 }
        r10 = r10 + 1;
        goto L_0x00b6;
    L_0x00ef:
        if (r10 <= 0) goto L_0x0114;
    L_0x00f1:
        r2 = r15.getContext();	 Catch:{ all -> 0x0119 }
        r12 = r2.getContentResolver();	 Catch:{ all -> 0x0119 }
        r13 = 0;
        r0 = r16;
        r12.notifyChange(r0, r13);	 Catch:{ all -> 0x0119 }
        r1 = android.appwidget.AppWidgetManager.getInstance(r2);	 Catch:{ all -> 0x0119 }
        r12 = new android.content.ComponentName;	 Catch:{ all -> 0x0119 }
        r13 = com.sygic.widget.TrafficWidgetProvider.class;
        r12.<init>(r2, r13);	 Catch:{ all -> 0x0119 }
        r12 = r1.getAppWidgetIds(r12);	 Catch:{ all -> 0x0119 }
        r13 = 2131624663; // 0x7f0e02d7 float:1.8876512E38 double:1.053162516E-314;
        r1.notifyAppWidgetViewDataChanged(r12, r13);	 Catch:{ all -> 0x0119 }
    L_0x0114:
        monitor-exit(r15);
        return r10;
    L_0x0116:
        r12 = move-exception;
    L_0x0117:
        monitor-exit(r15);
        throw r12;
    L_0x0119:
        r12 = move-exception;
        r5 = r6;
        goto L_0x0117;
    L_0x011c:
        r5 = r6;
        goto L_0x0023;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.sygic.widget.WidgetDataProvider.update(android.net.Uri, android.content.ContentValues, java.lang.String, java.lang.String[]):int");
    }

    public void updatePlacesData() {
        List<PlaceDataPoint> copy = new ArrayList(this.mDataPoints);
        this.mDataPoints.clear();
        this.mPlacesAdapter.updatePlaces(getContext());
        List<PlaceEntry> placeList = this.mPlacesAdapter.getPlaces();
        for (int i = 0; i != placeList.size(); i++) {
            PlaceEntry p = (PlaceEntry) placeList.get(i);
            PlaceDataPoint data = new PlaceDataPoint(p.getName(), -1, -1, p.getLat(), p.getLon(), p.getType().getValue());
            int index = copy.indexOf(data);
            if (index != -1) {
                data.delaySeconds = ((PlaceDataPoint) copy.get(index)).delaySeconds;
                data.freeSeconds = ((PlaceDataPoint) copy.get(index)).freeSeconds;
            }
            this.mDataPoints.add(data);
        }
        copy.clear();
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public android.os.Bundle call(java.lang.String r7, java.lang.String r8, android.os.Bundle r9) {
        /*
        r6 = this;
        r5 = 1;
        r2 = 0;
        r4 = -1;
        r3 = r7.hashCode();
        switch(r3) {
            case -2072925648: goto L_0x0030;
            case -1532619841: goto L_0x000f;
            case -1271220480: goto L_0x003b;
            case -827484715: goto L_0x005c;
            case -378038840: goto L_0x0046;
            case 710241090: goto L_0x001a;
            case 976167977: goto L_0x0025;
            case 1636039753: goto L_0x0051;
            default: goto L_0x000a;
        };
    L_0x000a:
        r3 = r4;
    L_0x000b:
        switch(r3) {
            case 0: goto L_0x0067;
            case 1: goto L_0x006b;
            case 2: goto L_0x0070;
            case 3: goto L_0x0095;
            case 4: goto L_0x00b6;
            case 5: goto L_0x00d0;
            case 6: goto L_0x00de;
            case 7: goto L_0x00fd;
            default: goto L_0x000e;
        };
    L_0x000e:
        return r2;
    L_0x000f:
        r3 = "updatePlacesData";
        r3 = r7.equals(r3);
        if (r3 == 0) goto L_0x000a;
    L_0x0018:
        r3 = 0;
        goto L_0x000b;
    L_0x001a:
        r3 = "getPlaces";
        r3 = r7.equals(r3);
        if (r3 == 0) goto L_0x000a;
    L_0x0023:
        r3 = r5;
        goto L_0x000b;
    L_0x0025:
        r3 = "savePlaces";
        r3 = r7.equals(r3);
        if (r3 == 0) goto L_0x000a;
    L_0x002e:
        r3 = 2;
        goto L_0x000b;
    L_0x0030:
        r3 = "saveItem";
        r3 = r7.equals(r3);
        if (r3 == 0) goto L_0x000a;
    L_0x0039:
        r3 = 3;
        goto L_0x000b;
    L_0x003b:
        r3 = "clearItem";
        r3 = r7.equals(r3);
        if (r3 == 0) goto L_0x000a;
    L_0x0044:
        r3 = 4;
        goto L_0x000b;
    L_0x0046:
        r3 = "setShowPlace";
        r3 = r7.equals(r3);
        if (r3 == 0) goto L_0x000a;
    L_0x004f:
        r3 = 5;
        goto L_0x000b;
    L_0x0051:
        r3 = "setWidgetTabType";
        r3 = r7.equals(r3);
        if (r3 == 0) goto L_0x000a;
    L_0x005a:
        r3 = 6;
        goto L_0x000b;
    L_0x005c:
        r3 = "getWidgetTabType";
        r3 = r7.equals(r3);
        if (r3 == 0) goto L_0x000a;
    L_0x0065:
        r3 = 7;
        goto L_0x000b;
    L_0x0067:
        r6.updatePlacesData();
        goto L_0x000e;
    L_0x006b:
        r2 = r6.getPlaces();
        goto L_0x000e;
    L_0x0070:
        r3 = r6.mPlacesAdapter;
        r4 = new java.util.HashSet;
        r5 = com.sygic.widget.TrafficWidgetProvider.PREFERENCE_PLACES_KEY;
        r5 = r9.getStringArrayList(r5);
        r4.<init>(r5);
        r3.savePlaces(r4);
        r3 = r6.getContext();
        r3 = r3.getContentResolver();
        r4 = r6.getContext();
        r4 = getContentUri(r4);
        r3.notifyChange(r4, r2);
        goto L_0x000e;
    L_0x0095:
        r3 = r6.mPlacesAdapter;
        r4 = "item";
        r4 = r9.getString(r4);
        r3.saveItem(r8, r4);
        r3 = r6.getContext();
        r3 = r3.getContentResolver();
        r4 = r6.getContext();
        r4 = getContentUri(r4);
        r3.notifyChange(r4, r2);
        goto L_0x000e;
    L_0x00b6:
        r3 = r6.mPlacesAdapter;
        r3.clearItem(r8);
        r3 = r6.getContext();
        r3 = r3.getContentResolver();
        r4 = r6.getContext();
        r4 = getContentUri(r4);
        r3.notifyChange(r4, r2);
        goto L_0x000e;
    L_0x00d0:
        r3 = r6.mPlacesAdapter;
        r4 = "show";
        r4 = r9.getBoolean(r4);
        r3.showPlace(r8, r4);
        goto L_0x000e;
    L_0x00de:
        if (r9 == 0) goto L_0x000e;
    L_0x00e0:
        r3 = "appWidgetId";
        r0 = r9.getInt(r3, r4);
        if (r0 == r4) goto L_0x000e;
    L_0x00e9:
        r3 = "tab";
        r1 = r9.getParcelable(r3);
        r1 = (com.sygic.aura.map.data.MemoItem.EMemoType) r1;
        r3 = r6.mWidgetStates;
        if (r1 != 0) goto L_0x00f8;
    L_0x00f6:
        r1 = com.sygic.aura.map.data.MemoItem.EMemoType.memoHome;
    L_0x00f8:
        r3.put(r0, r1);
        goto L_0x000e;
    L_0x00fd:
        r2 = new android.os.Bundle;
        r2.<init>(r5);
        if (r9 == 0) goto L_0x000e;
    L_0x0104:
        r3 = "appWidgetId";
        r0 = r9.getInt(r3, r4);
        if (r0 == r4) goto L_0x000e;
    L_0x010d:
        r3 = r6.mWidgetStates;
        r1 = r3.get(r0);
        r1 = (com.sygic.aura.map.data.MemoItem.EMemoType) r1;
        r3 = "tab";
        if (r1 != 0) goto L_0x011c;
    L_0x011a:
        r1 = com.sygic.aura.map.data.MemoItem.EMemoType.memoHome;
    L_0x011c:
        r2.putParcelable(r3, r1);
        goto L_0x000e;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.sygic.widget.WidgetDataProvider.call(java.lang.String, java.lang.String, android.os.Bundle):android.os.Bundle");
    }

    private Bundle getPlaces() {
        Bundle result = new Bundle(1);
        result.putStringArrayList(TrafficWidgetProvider.PREFERENCE_PLACES_KEY, new ArrayList(this.mPlacesAdapter.getPlacesStringSet()));
        return result;
    }

    public static Uri getContentUri(Context context) {
        if (sContentUri == null) {
            sContentUri = Uri.parse("content://" + context.getPackageName() + ".widget.traffic.provider");
        }
        return sContentUri;
    }
}
