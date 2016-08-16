package com.sygic.aura.tracker;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Build;
import android.os.Build.VERSION;
import com.sygic.aura.tracker.EventsCache.DispatchListener;
import com.sygic.aura.tracker.model.Event;
import java.util.HashMap;

public class SygicTracker implements DispatchListener {
    private static SygicTracker sInstance;
    private final EventsCache mCache;
    private final Context mContext;
    private final HashMap<String, String> mMetadata;

    public static SygicTracker get(Context context) {
        if (sInstance == null) {
            sInstance = new SygicTracker(context.getApplicationContext());
        }
        return sInstance;
    }

    private SygicTracker(Context context) {
        this.mContext = context;
        this.mCache = new EventsCache(this);
        this.mMetadata = new HashMap();
        initMetadata();
    }

    private void initMetadata() {
        this.mMetadata.put("os", "android");
        this.mMetadata.put("os_version", VERSION.RELEASE);
        try {
            PackageInfo packageInfo = this.mContext.getPackageManager().getPackageInfo(this.mContext.getPackageName(), 0);
            this.mMetadata.put("app_id", packageInfo.packageName);
            this.mMetadata.put("app_version", packageInfo.versionName);
        } catch (NameNotFoundException e) {
        }
        this.mMetadata.put("app_instance", TrackerUtils.getRandomId());
        this.mMetadata.put("device_manufacturer", Build.MANUFACTURER);
        this.mMetadata.put("device_model", Build.MODEL);
        this.mMetadata.put("device_id", TrackerUtils.getDeviceId(this.mContext));
    }

    public SygicTracker sendEvent(Event event) {
        this.mCache.add(event);
        return this;
    }

    public void forceDispatch() {
        dispatch(true);
    }

    public HashMap<String, String> getMetadata() {
        return this.mMetadata;
    }

    public EventsCache getEventsCache() {
        return this.mCache;
    }

    private void dispatch(boolean forced) {
        this.mContext.startService(new Intent(this.mContext, TrackerService.class).setAction("com.sygic.aura.ACTION_DISPATCH").putExtra("forced", forced));
    }

    public void onTriggerDispatch() {
        dispatch(false);
    }
}
