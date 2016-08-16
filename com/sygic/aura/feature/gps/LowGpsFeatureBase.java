package com.sygic.aura.feature.gps;

import android.content.Context;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.GoogleApiClient.Builder;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.sygic.aura.SygicConsts;
import com.sygic.aura.SygicMain;
import java.util.List;

/* compiled from: LowGpsFeature */
class LowGpsFeatureBase extends LowGpsFeature {
    private PlayServiceLocationManager mPlayserviceManager;

    /* renamed from: com.sygic.aura.feature.gps.LowGpsFeatureBase.1 */
    class LowGpsFeature implements Runnable {
        LowGpsFeature() {
        }

        public void run() {
            if (!LowGpsFeatureBase.this.m_bStarted) {
                List<String> lstProviders = LowGpsFeatureBase.this.mLocationManager.getAllProviders();
                if (lstProviders.contains("gps")) {
                    LowGpsFeatureBase.this.mLocationManager.requestLocationUpdates("gps", 0, 0.0f, LowGpsFeatureBase.this.mLocationService);
                }
                if (lstProviders.contains("network")) {
                    LowGpsFeatureBase.this.mLocationManager.requestLocationUpdates("network", 0, 0.0f, LowGpsFeatureBase.this.mLocationService);
                }
                LowGpsFeatureBase.this.mLocationManager.addGpsStatusListener(LowGpsFeatureBase.this.mLocationService);
                if (LowGpsFeatureBase.this.mSensorManager != null) {
                    LowGpsFeatureBase.this.mSensorManager.registerListener(LowGpsFeatureBase.this.mLocationService, LowGpsFeatureBase.this.mSensorManager.getDefaultSensor(3), 3);
                    LowGpsFeatureBase.this.mSensorManager.registerListener(LowGpsFeatureBase.this.mLocationService, LowGpsFeatureBase.this.mSensorManager.getDefaultSensor(1), 3);
                }
                LowGpsFeatureBase.this.m_bStarted = true;
            }
        }
    }

    /* renamed from: com.sygic.aura.feature.gps.LowGpsFeatureBase.2 */
    class LowGpsFeature implements Runnable {
        LowGpsFeature() {
        }

        public void run() {
            LowGpsFeatureBase.this.mLocationManager.removeUpdates(LowGpsFeatureBase.this.mLocationService);
            LowGpsFeatureBase.this.mLocationManager.removeGpsStatusListener(LowGpsFeatureBase.this.mLocationService);
        }
    }

    /* renamed from: com.sygic.aura.feature.gps.LowGpsFeatureBase.3 */
    class LowGpsFeature implements Runnable {
        LowGpsFeature() {
        }

        public void run() {
            if (LowGpsFeatureBase.this.mLocationManager.getAllProviders().contains("gps")) {
                LowGpsFeatureBase.this.mLocationManager.requestLocationUpdates("gps", 0, 0.0f, LowGpsFeatureBase.this.mLocationService);
            }
            LowGpsFeatureBase.this.mLocationManager.addGpsStatusListener(LowGpsFeatureBase.this.mLocationService);
        }
    }

    /* compiled from: LowGpsFeature */
    private class PlayServiceLocationManager implements ConnectionCallbacks, OnConnectionFailedListener, LocationListener {
        private Context mContext;
        private GoogleApiClient mLocationClient;
        private LocationService mLocationService;

        public PlayServiceLocationManager(Context context, LocationService locationService) {
            this.mContext = context;
            this.mLocationService = locationService;
            if (GooglePlayServicesUtil.isGooglePlayServicesAvailable(this.mContext) == 0) {
                this.mLocationClient = new Builder(this.mContext).addConnectionCallbacks(this).addOnConnectionFailedListener(this).addApi(LocationServices.API).build();
            }
        }

        private LocationRequest getLocationRequest() {
            LocationRequest locationRequest = new LocationRequest();
            locationRequest.setInterval(1000);
            locationRequest.setPriority(100);
            return locationRequest;
        }

        public void connect() {
            if (this.mLocationClient != null && !this.mLocationClient.isConnected() && !this.mLocationClient.isConnecting()) {
                this.mLocationClient.connect();
            }
        }

        public void disconnect() {
            if (this.mLocationClient != null) {
                this.mLocationClient.disconnect();
            }
        }

        public void onConnected(Bundle bundle) {
            LocationServices.FusedLocationApi.requestLocationUpdates(this.mLocationClient, getLocationRequest(), this);
            Location lastLocation = LocationServices.FusedLocationApi.getLastLocation(this.mLocationClient);
            if (lastLocation != null) {
                this.mLocationService.onLocationChanged(lastLocation);
            }
        }

        public void onConnectionSuspended(int i) {
        }

        public void onConnectionFailed(ConnectionResult connectionResult) {
        }

        public void onLocationChanged(Location location) {
            this.mLocationService.onLocationChanged(location);
            if (this.mLocationClient.isConnected()) {
                LocationServices.FusedLocationApi.removeLocationUpdates(this.mLocationClient, this);
            }
        }
    }

    protected LowGpsFeatureBase() {
    }

    protected LowGpsFeatureBase(Context context, Handler handler) {
        super(context, handler);
        this.mPlayserviceManager = new PlayServiceLocationManager(context, this.mLocationService);
    }

    public boolean isEnabled() {
        if (this.mLocationManager != null) {
            return this.mLocationManager.isProviderEnabled("gps");
        }
        return false;
    }

    public boolean open() {
        this.mPlayserviceManager.connect();
        registerListeners();
        try {
            if (isEnabled()) {
                return true;
            }
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public void close() {
        if (this.m_bStarted) {
            if (this.mLocationManager != null) {
                this.mLocationManager.removeUpdates(this.mLocationService);
                this.mLocationManager.removeGpsStatusListener(this.mLocationService);
            }
            if (this.mSensorManager != null) {
                this.mSensorManager.unregisterListener(this.mLocationService);
            }
            this.m_bStarted = false;
        }
        this.mPlayserviceManager.disconnect();
    }

    public boolean GpsClearCache() {
        return LocationService.clearAGPSCache(SygicMain.getActivity());
    }

    public boolean GpsUpdateCache() {
        return LocationService.updateAGPSData(SygicMain.getActivity());
    }

    private void registerListeners() {
        if (this.mHandler != null) {
            this.mHandler.post(new LowGpsFeature());
        }
    }

    public void onCreate() {
    }

    public void onStart() {
    }

    public void onResume() {
        if (!SygicConsts.IS_PROTOTYPE) {
            if (isEnabled() && !this.m_bStarted) {
                SygicMain.getInstance().OnGpsStatus(true);
            }
            registerListeners();
        }
    }

    public void onPause() {
    }

    public void onStop() {
    }

    public void onDestroy() {
    }

    public void onExternalGpsConnected() {
        if (this.m_bStarted && this.mLocationManager != null) {
            this.mHandler.post(new LowGpsFeature());
        }
        if (this.mLocationService != null) {
            this.mLocationService.onExternalGpsConnected();
        }
    }

    public void onExternalGpsDisconnected() {
        this.mHandler.post(new LowGpsFeature());
        if (this.mLocationService != null) {
            this.mLocationService.onExternalGpsDisconnected();
        }
    }
}
