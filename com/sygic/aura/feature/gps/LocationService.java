package com.sygic.aura.feature.gps;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.location.GpsSatellite;
import android.location.GpsStatus;
import android.location.GpsStatus.Listener;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import com.sygic.aura.SygicMain;
import com.sygic.aura.clazz.LocationInfo;
import com.sygic.aura.clazz.SatelliteInfo;
import com.sygic.aura.clazz.SatelliteInfoArray;
import com.sygic.aura.helper.RepeatingThread;
import com.sygic.aura.helper.RepeatingThread.ExecutionOrder;
import loquendo.tts.engine.TTSConst;

public class LocationService implements SensorEventListener, Listener, LocationListener, LocationManagerInterface {
    private boolean mIsExternalGpsConnected;
    private LocationManager mLocationManager;
    private SygicMain mSygicMain;
    private RepeatingThread mWifiThread;
    private boolean m_bHasGps;
    private long m_lLastNetTime;
    private long m_lLastTimeSat;
    private Location m_locLastPos;

    /* renamed from: com.sygic.aura.feature.gps.LocationService.1 */
    class C12291 implements ExecutionOrder {
        C12291() {
        }

        public boolean runningCondition() {
            return LocationService.this.m_bHasGps;
        }

        public boolean onPositive() {
            return false;
        }

        public boolean onNegative() {
            try {
                if (System.currentTimeMillis() - LocationService.this.m_lLastNetTime < 60000) {
                    LocationService.this.mSygicMain.GpsSetData(new LocationInfo(LocationService.this.m_locLastPos));
                    if (System.currentTimeMillis() - LocationService.this.m_lLastTimeSat > 5000) {
                        LocationService.this.mSygicMain.GpsSetSatellites(new SatelliteInfoArray(new SatelliteInfo()));
                    }
                }
                LocationService.this.m_locLastPos.setTime(LocationService.this.m_locLastPos.getTime() + 1000);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return true;
        }
    }

    private LocationService() {
        this.mSygicMain = null;
        this.mLocationManager = null;
        this.m_bHasGps = false;
        this.m_locLastPos = null;
        this.mIsExternalGpsConnected = false;
        this.mWifiThread = null;
    }

    public LocationService(Context context, LocationManager locManager) {
        this.mSygicMain = null;
        this.mLocationManager = null;
        this.m_bHasGps = false;
        this.m_locLastPos = null;
        this.mIsExternalGpsConnected = false;
        this.mWifiThread = null;
        this.mLocationManager = locManager;
        this.mSygicMain = SygicMain.getInstance();
    }

    public void onLocationChanged(Location location) {
        if (!this.mIsExternalGpsConnected) {
            locationChanged(location, false);
        }
    }

    private void setGpsData(Location location) {
        try {
            this.mSygicMain.GpsSetData(new LocationInfo(location));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void locationChanged(Location location, boolean isExternal) {
        if (this.mSygicMain != null && location != null) {
            if (location.getProvider().compareToIgnoreCase("gps") == 0 || isExternal) {
                if (this.mWifiThread != null) {
                    this.mWifiThread.setFinished(true);
                }
                this.m_bHasGps = true;
                setGpsData(location);
            } else if (location.getProvider().compareToIgnoreCase("fused") == 0) {
                setGpsData(location);
            } else if (location.getProvider().compareToIgnoreCase("network") == 0) {
                this.m_locLastPos = location;
                this.m_lLastNetTime = System.currentTimeMillis();
                if (!this.m_bHasGps && this.mWifiThread == null) {
                    this.mWifiThread = new RepeatingThread(new C12291(), 1000);
                    this.mWifiThread.start();
                }
            }
        }
    }

    public void onProviderDisabled(String provider) {
        if (this.mSygicMain != null) {
            this.mSygicMain.GpsSetStatus(0);
            if ("gps".equals(provider)) {
                this.mSygicMain.OnGpsStatus(false);
            }
        }
    }

    public void onProviderEnabled(String provider) {
        if (this.mSygicMain != null && !this.mIsExternalGpsConnected) {
            this.mSygicMain.GpsSetStatus(1);
            if ("gps".equals(provider)) {
                this.mSygicMain.OnGpsStatus(true);
            }
        }
    }

    public void onStatusChanged(String provider, int status, Bundle extras) {
        if (this.mSygicMain != null && provider != null && !this.mIsExternalGpsConnected) {
            try {
                if (provider.compareToIgnoreCase("gps") == 0) {
                    switch (status) {
                        case TTSConst.TTSEVT_AUDIOSTART /*0*/:
                            this.mSygicMain.GpsSetStatus(0);
                            return;
                        case TTSConst.TTSMULTILINE /*1*/:
                            this.mSygicMain.GpsSetStatus(1);
                            return;
                        case TTSConst.TTSPARAGRAPH /*2*/:
                            this.mSygicMain.GpsSetStatus(2);
                            this.m_bHasGps = true;
                            return;
                        default:
                            return;
                    }
                    e.printStackTrace();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void onGpsStatusChanged(int event) {
        if (this.mSygicMain != null && !this.mIsExternalGpsConnected) {
            if (event == 4) {
                GpsStatus gpsStatus = this.mLocationManager.getGpsStatus(null);
                if (gpsStatus != null) {
                    Iterable<GpsSatellite> gpsSatellites = gpsStatus.getSatellites();
                    SatelliteInfoArray arrSatInfo = new SatelliteInfoArray();
                    for (GpsSatellite gpsSatellite : gpsSatellites) {
                        this.m_lLastTimeSat = System.currentTimeMillis();
                        arrSatInfo.add(new SatelliteInfo(gpsSatellite));
                    }
                    this.mSygicMain.GpsSetSatellites(arrSatInfo);
                }
            }
            if (event == 1) {
                this.mSygicMain.GpsSetStatus(1);
            }
        }
    }

    public void onAccuracyChanged(Sensor sensor, int i) {
    }

    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == 3) {
            SygicMain.getInstance().GpsSetCompassHeading(event.values[0], (float) (45 - (event.accuracy * 15)));
        }
        if (event.sensor.getType() == 1) {
            this.mSygicMain.AccSetData((-event.values[0]) / 9.81f, (-event.values[1]) / 9.81f, (-event.values[2]) / 9.81f);
        }
    }

    public static boolean clearAGPSCache(Context context) {
        LocationManager locationManager = (LocationManager) context.getSystemService("location");
        if (locationManager == null || !locationManager.isProviderEnabled("gps")) {
            return false;
        }
        return locationManager.sendExtraCommand("gps", "delete_aiding_data", null);
    }

    public static boolean updateAGPSData(Context context) {
        LocationManager locationManager = (LocationManager) context.getSystemService("location");
        if (locationManager == null || !locationManager.isProviderEnabled("gps")) {
            return false;
        }
        boolean bRet1 = locationManager.sendExtraCommand("gps", "force_xtra_injection", null);
        boolean bRet2 = locationManager.sendExtraCommand("gps", "force_time_injection", null);
        if (bRet1 && bRet2) {
            return true;
        }
        return false;
    }

    public void onExternalGpsConnected() {
        this.mIsExternalGpsConnected = true;
    }

    public void onExternalGpsDisconnected() {
        this.mIsExternalGpsConnected = false;
    }
}
