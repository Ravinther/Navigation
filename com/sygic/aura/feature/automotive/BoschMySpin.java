package com.sygic.aura.feature.automotive;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.location.Location;
import android.os.Handler;
import android.util.Log;
import com.bosch.myspin.serversdk.MySpinException;
import com.bosch.myspin.serversdk.MySpinServerSDK;
import com.bosch.myspin.serversdk.MySpinServerSDK.ConnectionStateListener;
import com.bosch.myspin.serversdk.OnCarDataChangeListener;
import com.bosch.myspin.serversdk.service.client.opengl.MySpinSurfaceViewHandle;
import com.flurry.android.FlurryAgent;
import com.sygic.aura.SygicMain;
import com.sygic.aura.feature.gl.LowGlFeature.EglCallback;
import com.sygic.aura.feature.gps.LocationService;

public enum BoschMySpin implements EglCallback {
    INSTANCE;
    
    private static final String LOG_TAG;
    private final ConnectionStateListener mConnectionStateListener;
    OnCarDataChangeListener mDataChangeListener;
    private LocationService mLocService;
    public boolean mMirroringEnabled;
    BroadcastReceiver mReceiver;
    private transient MySpinSurfaceViewHandle mSurfaceViewHandle;

    /* renamed from: com.sygic.aura.feature.automotive.BoschMySpin.1 */
    class C12121 implements ConnectionStateListener {
        C12121() {
        }

        public void onConnectionStateChanged(boolean connected) {
            SygicMain.nativePostCommand(16896, (short) (connected ? 1 : 0), (short) 1);
            if (BoschMySpin.this.mLocService == null) {
                BoschMySpin.this.mLocService = new LocationService(null, null);
            }
            if (!connected) {
                BoschMySpin.this.mSurfaceViewHandle = null;
                MySpinServerSDK.sharedInstance().unregisterSurfaceView(SygicMain.getSurface());
                SygicMain.getInstance().getFeature().getGpsFeature().onExternalGpsDisconnected();
            } else if (BoschMySpin.this.mSurfaceViewHandle == null) {
                BoschMySpin.this.mSurfaceViewHandle = MySpinServerSDK.sharedInstance().registerSurfaceView(SygicMain.getSurface());
                SygicMain.getInstance().getFeature().getGlFeature().registerEglCallback(BoschMySpin.this);
                SygicMain.getInstance().getFeature().getGpsFeature().onExternalGpsConnected();
                FlurryAgent.logEvent("incar->connect->boschmyspin");
            }
        }
    }

    /* renamed from: com.sygic.aura.feature.automotive.BoschMySpin.2 */
    class C12132 extends BroadcastReceiver {
        private boolean mIsKeybVisible;

        C12132() {
            this.mIsKeybVisible = false;
        }

        public void onReceive(Context context, Intent intent) {
            if ("com.bosch.myspin.intent.event.KEYBOARD_VISIBILITY_CHANGED".equals(intent.getAction())) {
                boolean isVisble = intent.getBooleanExtra("com.bosch.myspin.EXTRA_KEYBOARD_VISIBILITY", false);
                if (isVisble || this.mIsKeybVisible) {
                    this.mIsKeybVisible = isVisble;
                } else {
                    this.mIsKeybVisible = isVisble;
                }
            }
        }
    }

    /* renamed from: com.sygic.aura.feature.automotive.BoschMySpin.3 */
    class C12143 implements OnCarDataChangeListener {
        C12143() {
        }

        public void onLocationUpdate(Location location) {
            if (BoschMySpin.this.mLocService != null && location != null) {
                location.setSpeed(location.getSpeed() / 1.7f);
                BoschMySpin.this.mLocService.locationChanged(location, "MYSPIN_CAR_GPS".equals(location.getProvider()));
            }
        }

        public void onDayNightModeChanged(boolean arg0) {
        }

        public void onCarStationaryStatusChanged(boolean arg0) {
        }
    }

    /* renamed from: com.sygic.aura.feature.automotive.BoschMySpin.4 */
    class C12154 implements Runnable {
        C12154() {
        }

        public void run() {
            if (MySpinServerSDK.sharedInstance().isConnected()) {
                BoschMySpin.this.mSurfaceViewHandle = MySpinServerSDK.sharedInstance().registerSurfaceView(SygicMain.getSurface());
                SygicMain.getInstance().getFeature().getGlFeature().registerEglCallback(BoschMySpin.this);
            }
        }
    }

    static {
        LOG_TAG = BoschMySpin.class.getSimpleName();
    }

    public void start(Activity activity) {
        try {
            Handler handler = new Handler();
            if (activity != null && handler != null) {
                MySpinServerSDK.sharedInstance().registerApplication(activity.getApplication(), this.mConnectionStateListener);
                handler.post(new C12154());
                MySpinServerSDK.sharedInstance().registerCarDataChangedListener(this.mDataChangeListener);
                activity.registerReceiver(this.mReceiver, new IntentFilter("com.bosch.myspin.intent.event.KEYBOARD_VISIBILITY_CHANGED"));
            }
        } catch (MySpinException e) {
            e.printStackTrace();
        }
    }

    public void stop() {
        if (MySpinServerSDK.sharedInstance().isConnected()) {
            SygicMain.getInstance().getFeature().getGlFeature().registerEglCallback(null);
            MySpinServerSDK.sharedInstance().unregisterSurfaceView(SygicMain.getSurface());
        }
        try {
            MySpinServerSDK.sharedInstance().unregisterCarDataChangedListener(this.mDataChangeListener);
        } catch (MySpinException e) {
            e.printStackTrace();
        }
        try {
            SygicMain.getActivity().unregisterReceiver(this.mReceiver);
        } catch (IllegalArgumentException e2) {
            Log.w(LOG_TAG, "mReceiver already unregistered");
        }
    }

    public void eglSwapBuffers() {
        if (this.mSurfaceViewHandle != null && this.mMirroringEnabled) {
            this.mSurfaceViewHandle.captureOpenGl();
        }
    }

    public boolean isConnected() {
        return this.mSurfaceViewHandle != null;
    }
}
