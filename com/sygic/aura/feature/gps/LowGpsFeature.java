package com.sygic.aura.feature.gps;

import android.content.Context;
import android.hardware.SensorManager;
import android.location.LocationManager;
import android.os.Handler;
import com.sygic.aura.feature.ActivityListener;

public abstract class LowGpsFeature implements ActivityListener, LocationManagerInterface {
    protected Handler mHandler;
    protected LocationManager mLocationManager;
    protected LocationService mLocationService;
    protected SensorManager mSensorManager;
    protected volatile boolean m_bStarted;

    public abstract boolean GpsClearCache();

    public abstract boolean GpsUpdateCache();

    public abstract void close();

    public abstract boolean isEnabled();

    public abstract boolean open();

    protected LowGpsFeature() {
        this.m_bStarted = false;
    }

    protected LowGpsFeature(Context context, Handler handler) {
        this.m_bStarted = false;
        this.mLocationManager = (LocationManager) context.getSystemService("location");
        this.mSensorManager = (SensorManager) context.getSystemService("sensor");
        this.mLocationService = new LocationService(context, this.mLocationManager);
        this.mHandler = handler;
    }

    public static LowGpsFeature createInstance(Context context, Handler handler) {
        return new LowGpsFeatureBase(context, handler);
    }
}
