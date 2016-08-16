package com.sygic.aura.helper;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.ActivityManager.MemoryInfo;
import android.content.Context;
import android.graphics.Point;
import android.os.Build.VERSION;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.view.WindowManager;
import com.sygic.aura.c2dm.C2DMessaging;
import com.sygic.aura.network.ComponentManager;
import com.sygic.aura.resources.ResourceManager;
import com.sygic.aura.settings.data.SettingsManager;
import com.sygic.aura.tracker.SygicTracker;
import com.sygic.aura.tracker.TrackerUtils;
import com.sygic.aura.tracker.model.AppPausedEvent;
import com.sygic.aura.tracker.model.AppResumedEvent;
import com.sygic.aura.tracker.model.AppStartedEvent;
import java.util.HashMap;

public class SygicTrackerHelper {
    public static void logOnCreate(Context context) {
        HashMap<String, Object> metrics = new HashMap();
        TrackerUtils.putConnectivityType(context, metrics);
        metrics.put("disk_space_MB", Long.valueOf(TrackerUtils.getFreeDiskSpaceInMegabytes(context)));
        metrics.put("initialized_opengl", ResourceManager.nativeGetInitializedOpenGlVersion());
        if (VERSION.SDK_INT >= 16) {
            ActivityManager am = (ActivityManager) context.getSystemService("activity");
            MemoryInfo mi = new MemoryInfo();
            am.getMemoryInfo(mi);
            long j = mi.availMem;
            metrics.put("memory_usage_%", Long.valueOf(100 - ((r0 * 100) / mi.totalMem)));
        }
        WindowManager wm = (WindowManager) context.getSystemService("window");
        Point size = new Point();
        wm.getDefaultDisplay().getSize(size);
        metrics.put("display_resolution_PX", size.x + "x" + size.y);
        metrics.put("push_id", C2DMessaging.getRegistrationId(context));
        SygicTracker.get(context).sendEvent(new AppStartedEvent().attachMetrics(metrics));
    }

    public static void logOnResume(Context context) {
        SygicTracker.get(context).sendEvent(new AppResumedEvent());
    }

    public static void logOnPause(Activity activity) {
        if (SygicHelper.getCoreHandler() != null) {
            String[] maps = ComponentManager.nativeGetInstalledMaps();
            int[] tripKilometers = SettingsManager.nativeGetTripKilometers();
            HashMap<String, Object> metrics = new HashMap();
            metrics.put("trip_with_route_KM", Integer.valueOf(tripKilometers[0]));
            metrics.put("trip_without_route_KM", Integer.valueOf(tripKilometers[1]));
            saveValuesForTrackerService(activity, tripKilometers, maps);
            SygicTracker.get(activity).sendEvent(new AppPausedEvent().attachMetrics(metrics));
            SygicTracker.get(activity).forceDispatch();
        }
    }

    public static void saveValuesForTrackerService(Context context, int[] tripKilometers, String[] maps) {
        StringBuilder sb = new StringBuilder();
        for (String map : maps) {
            if (!TextUtils.isEmpty(map)) {
                sb.append(map).append(";");
            }
        }
        PreferenceManager.getDefaultSharedPreferences(context).edit().putInt("trip_with_route", tripKilometers[0]).putInt("trip_without_route", tripKilometers[1]).putString("installed_maps", sb.toString()).apply();
    }

    public static void logOnDestroy(Context context) {
    }
}
