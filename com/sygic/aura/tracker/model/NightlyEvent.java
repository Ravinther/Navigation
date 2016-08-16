package com.sygic.aura.tracker.model;

import java.util.HashMap;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class NightlyEvent extends Event {
    private final HashMap<String, Integer> mAllConnDistributions;
    private final String mDeviceId;
    private final HashMap<String, Integer> mForegroundConnDistributions;
    private final long mFreeDiskSpace;
    private final String[] mInstalledMaps;
    private final String[] mInstalledPackageNames;
    private final int mKmWithRoute;
    private final int mKmWithoutRoute;
    private final String mPushId;

    public NightlyEvent(String deviceId, String pushId, long freeDiskSpace, String[] installedPackageNames, HashMap<String, Integer> allConnDistributions, HashMap<String, Integer> foregroundConnDistributions, String[] installedMaps, int kmWithRoute, int kmWithoutRoute) {
        this.mDeviceId = deviceId;
        this.mPushId = pushId;
        this.mFreeDiskSpace = freeDiskSpace;
        this.mInstalledPackageNames = installedPackageNames;
        this.mAllConnDistributions = allConnDistributions;
        this.mForegroundConnDistributions = foregroundConnDistributions;
        this.mInstalledMaps = installedMaps;
        this.mKmWithRoute = kmWithRoute;
        this.mKmWithoutRoute = kmWithoutRoute;
    }

    protected JSONObject jsonifyPayload() throws JSONException {
        int i = 0;
        JSONObject json = new JSONObject();
        json.put("device_id", this.mDeviceId);
        json.put("push_id", this.mPushId);
        json.put("disk_space_MB", this.mFreeDiskSpace);
        json.put("km_driven", new JSONObject().put("trip_with_route_KM", this.mKmWithRoute).put("trip_without_route_KM", this.mKmWithoutRoute));
        if (this.mInstalledPackageNames != null && this.mInstalledPackageNames.length > 0) {
            JSONArray jsonApps = new JSONArray();
            for (String packageName : this.mInstalledPackageNames) {
                jsonApps.put(packageName);
            }
            json.put("installed_apps", jsonApps);
        }
        if (this.mAllConnDistributions != null && this.mAllConnDistributions.size() > 0) {
            JSONObject jsonAllConnDistribution = new JSONObject();
            for (String connKey : this.mAllConnDistributions.keySet()) {
                jsonAllConnDistribution.put(connKey, this.mAllConnDistributions.get(connKey));
            }
            json.put("connectivity_distribution_%", jsonAllConnDistribution);
        }
        if (this.mForegroundConnDistributions != null && this.mForegroundConnDistributions.size() > 0) {
            JSONObject jsonFgConnDistribution = new JSONObject();
            for (String connKey2 : this.mForegroundConnDistributions.keySet()) {
                jsonFgConnDistribution.put(connKey2, this.mForegroundConnDistributions.get(connKey2));
            }
            json.put("foreground_connectivity_distribution_%", jsonFgConnDistribution);
        }
        if (this.mInstalledMaps != null && this.mInstalledMaps.length > 0) {
            JSONArray jsonMaps = new JSONArray();
            String[] strArr = this.mInstalledMaps;
            int length = strArr.length;
            while (i < length) {
                jsonMaps.put(strArr[i]);
                i++;
            }
            json.put("installed_maps", jsonMaps);
        }
        return json;
    }

    protected String getJsonTypeName() {
        return "nightly";
    }
}
