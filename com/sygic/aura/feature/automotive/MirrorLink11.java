package com.sygic.aura.feature.automotive;

import android.app.Activity;
import android.app.UiModeManager;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.ResolveInfo;
import android.content.pm.ServiceInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;
import android.view.WindowManager;
import com.flurry.android.FlurryAgent;
import com.mirrorlink.android.commonapi.ICommonAPIService;
import com.mirrorlink.android.commonapi.IConnectionListener;
import com.mirrorlink.android.commonapi.IConnectionListener.Stub;
import com.mirrorlink.android.commonapi.IConnectionManager;
import com.mirrorlink.android.commonapi.IContextListener;
import com.mirrorlink.android.commonapi.IContextManager;
import com.mirrorlink.android.commonapi.IDeviceInfoListener;
import com.mirrorlink.android.commonapi.IDeviceInfoManager;
import com.mirrorlink.android.commonapi.IDeviceStatusListener;
import com.mirrorlink.android.commonapi.IDeviceStatusManager;
import com.mirrorlink.android.commonapi.IDisplayListener;
import com.mirrorlink.android.commonapi.IDisplayManager;
import com.sygic.aura.SygicMain;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MirrorLink11 extends AbstractMirrorLink implements ServiceConnection {
    private final Runnable mAudioReleaseRunnable;
    private ICommonAPIService mCommonApiService;
    private final IConnectionListener mConnectionListener;
    private IConnectionManager mConnectionManager;
    private final IContextListener mContextListener;
    private IContextManager mContextManager;
    private final IDeviceInfoListener mDeviceInfoListener;
    private IDeviceInfoManager mDeviceInfoManager;
    private final IDeviceStatusListener mDeviceStatusListener;
    private IDeviceStatusManager mDeviceStatusManager;
    private final IDisplayListener mDisplayListener;
    private IDisplayManager mDisplayManager;
    private volatile boolean mIsAudioContextSet;
    private volatile boolean mIsMirrorLinkSessionEstablished;
    private Boolean mPendingConnection;

    /* renamed from: com.sygic.aura.feature.automotive.MirrorLink11.1 */
    class C12161 implements Runnable {
        C12161() {
        }

        public void run() {
            MirrorLink11.this.setAudioContext(false);
        }
    }

    /* renamed from: com.sygic.aura.feature.automotive.MirrorLink11.2 */
    class C12172 extends Stub {
        C12172() {
        }

        public void onRemoteDisplayConnectionChanged(int remoteDisplayConnection) throws RemoteException {
        }

        public void onMirrorLinkSessionChanged(boolean mirrolinkSessionIsEstablished) throws RemoteException {
            MirrorLink11.this.onSessionChanged(mirrolinkSessionIsEstablished);
        }

        public void onAudioConnectionsChanged(Bundle audioConnections) throws RemoteException {
        }
    }

    /* renamed from: com.sygic.aura.feature.automotive.MirrorLink11.3 */
    class C12183 extends IContextListener.Stub {
        C12183() {
        }

        public void onFramebufferUnblocked() throws RemoteException {
        }

        public void onFramebufferBlocked(int reason, Bundle framebufferArea) throws RemoteException {
        }

        public void onAudioUnblocked() throws RemoteException {
            SygicMain.nativePostCommand(16898, 1);
        }

        public void onAudioBlocked(int reason) throws RemoteException {
            SygicMain.nativePostCommand(16898, 0);
            SygicMain.getInstance().getFeature().getTtsFeature().stop();
            SygicMain.getInstance().getFeature().getSoundFeature().stop(true);
        }
    }

    /* renamed from: com.sygic.aura.feature.automotive.MirrorLink11.4 */
    class C12194 extends IDeviceStatusListener.Stub {
        C12194() {
        }

        public void onNightModeChanged(boolean nightMode) throws RemoteException {
        }

        public void onMicrophoneStatusChanged(boolean micInput) throws RemoteException {
        }

        public void onDriveModeChange(boolean driveMode) throws RemoteException {
            MirrorLink11.this.postDriveModeChanged(driveMode);
        }
    }

    /* renamed from: com.sygic.aura.feature.automotive.MirrorLink11.5 */
    class C12205 extends IDisplayListener.Stub {
        C12205() {
        }

        public void onPixelFormatChanged(Bundle pixelFormat) throws RemoteException {
        }

        public void onDisplayConfigurationChanged(Bundle displayConfiguration) throws RemoteException {
            MirrorLink11.this.postDisplayConfigurationChanged(displayConfiguration);
        }
    }

    /* renamed from: com.sygic.aura.feature.automotive.MirrorLink11.6 */
    class C12216 extends IDeviceInfoListener.Stub {
        C12216() {
        }

        public void onDeviceInfoChanged(Bundle clientInformation) throws RemoteException {
        }
    }

    /* renamed from: com.sygic.aura.feature.automotive.MirrorLink11.7 */
    class C12227 implements Runnable {
        C12227() {
        }

        public void run() {
            MirrorLink11.this.setAudioContext(true);
        }
    }

    /* renamed from: com.sygic.aura.feature.automotive.MirrorLink11.8 */
    class C12238 implements Runnable {
        C12238() {
        }

        public void run() {
            MirrorLink11.this.setAudioContext(false);
        }
    }

    public MirrorLink11() {
        this.mIsMirrorLinkSessionEstablished = false;
        this.mIsAudioContextSet = false;
        this.mCommonApiService = null;
        this.mConnectionManager = null;
        this.mDeviceStatusManager = null;
        this.mDeviceInfoManager = null;
        this.mDisplayManager = null;
        this.mContextManager = null;
        this.mPendingConnection = null;
        this.mAudioReleaseRunnable = new C12161();
        this.mConnectionListener = new C12172();
        this.mContextListener = new C12183();
        this.mDeviceStatusListener = new C12194();
        this.mDisplayListener = new C12205();
        this.mDeviceInfoListener = new C12216();
    }

    private void postDriveModeChanged(boolean driveMode) {
        int i;
        if (driveMode) {
            i = 1;
        } else {
            i = 0;
        }
        SygicMain.nativePostCommand(16897, (short) i, (short) 0);
    }

    private void postDisplayConfigurationChanged(Bundle displayConfiguration) {
        if (displayConfiguration != null) {
            int wPixels = displayConfiguration.getInt("CLIENT_PIXEL_WIDTH");
            int hPixels = displayConfiguration.getInt("CLIENT_PIXEL_HEIGHT");
            int wPhysical = displayConfiguration.getInt("MM_WIDTH");
            int hPhysical = displayConfiguration.getInt("MM_HEIGHT");
            StringBuilder builder = new StringBuilder("postDisplayConfigurationChanged");
            builder.append(", PIXEL=").append(wPixels).append('x').append(hPixels).append(", MM=").append(wPhysical).append('x').append(hPhysical);
            Log.i(MirrorLink11.class.getSimpleName(), builder.toString());
            SygicMain.nativePostCommand(16900, (short) wPhysical, (short) hPhysical);
            if (wPhysical > 0 && wPixels > 0) {
                sPhone2Headup = 5.2493f / ((0.1f * ((float) wPhysical)) / 2.54f);
            }
        }
    }

    private void onSessionChanged(boolean mirrorlinkSessionIsEstablished) {
        log("Mirrorlink session is established: " + mirrorlinkSessionIsEstablished);
        if (this.mIsMirrorLinkSessionEstablished == mirrorlinkSessionIsEstablished) {
            this.mPendingConnection = null;
        } else if (SygicMain.getInstance().getFeature().getGlFeature().getInBackground()) {
            this.mPendingConnection = Boolean.valueOf(mirrorlinkSessionIsEstablished);
        } else {
            int i;
            requestOrientation(mirrorlinkSessionIsEstablished);
            if (mirrorlinkSessionIsEstablished) {
                i = 1;
            } else {
                i = 0;
            }
            SygicMain.nativePostCommand(16896, (short) i, (short) 5);
            this.mIsMirrorLinkSessionEstablished = mirrorlinkSessionIsEstablished;
            if (mirrorlinkSessionIsEstablished) {
                Map clientInfo = new HashMap();
                if (this.mDeviceInfoManager != null) {
                    try {
                        Bundle info = this.mDeviceInfoManager.getMirrorLinkClientInformation();
                        if (info != null) {
                            clientInfo.put("friendly_name", info.getString("CLIENT_FRIENDLY_NAME"));
                            clientInfo.put("identifier", info.getString("CLIENT_IDENTIFIER"));
                            clientInfo.put("manufacturer", info.getString("CLIENT_MANUFACTURER"));
                            clientInfo.put("model_name", info.getString("CLIENT_MODEL_NAME"));
                            clientInfo.put("model_number", info.getString("CLIENT_MODEL_NUMBER"));
                            clientInfo.put("version_major", info.getString("VERSION_MAJOR"));
                            clientInfo.put("version_minor", info.getString("VERSION_MINOR"));
                        }
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                }
                if (clientInfo.isEmpty()) {
                    FlurryAgent.logEvent("incar->connect->mirrorlink");
                } else {
                    FlurryAgent.logEvent("incar->connect->mirrorlink", clientInfo);
                }
            }
            UiModeManager uiMode = (UiModeManager) SygicMain.getActivity().getSystemService("uimode");
            if (mirrorlinkSessionIsEstablished) {
                uiMode.enableCarMode(0);
            } else {
                uiMode.disableCarMode(0);
            }
        }
    }

    private void requestOrientation(boolean isEstablished) {
        Activity activity = SygicMain.getInstance().getFeature().getActivity();
        if (activity == null) {
            return;
        }
        if (!isEstablished) {
            activity.setRequestedOrientation(-1);
        } else if (((WindowManager) activity.getSystemService("window")).getDefaultDisplay().getRotation() != 1) {
            activity.setRequestedOrientation(0);
        }
    }

    public void start() {
        if (this.mCommonApiService == null) {
            log("Binding to service");
            Intent bindIntent = new Intent("com.mirrorlink.android.service.BIND");
            Activity activity = SygicMain.getInstance().getFeature().getActivity();
            List<ResolveInfo> resolveInfos = activity.getPackageManager().queryIntentServices(bindIntent, 0);
            if (resolveInfos != null && resolveInfos.size() > 0) {
                if (resolveInfos.size() > 1) {
                    Log.e(MirrorLink11.class.getSimpleName(), "Multiple CommonAPI services are available!!!@");
                }
                ServiceInfo serviceInfo = ((ResolveInfo) resolveInfos.get(0)).serviceInfo;
                bindIntent.setComponent(new ComponentName(serviceInfo.packageName, serviceInfo.name));
                activity.bindService(bindIntent, this, 1);
            }
        }
        startSamsungApi();
    }

    public void stop() {
        stopSamsungApi();
        try {
            if (this.mContextManager != null) {
                this.mContextManager.unregister();
                this.mContextManager = null;
            }
            if (this.mConnectionManager != null) {
                this.mConnectionManager.unregister();
                this.mConnectionManager = null;
            }
            if (this.mDeviceStatusManager != null) {
                this.mDeviceStatusManager.unregister();
                this.mDeviceStatusManager = null;
            }
            if (this.mDeviceInfoManager != null) {
                this.mDeviceInfoManager.unregister();
                this.mDeviceInfoManager = null;
            }
            if (this.mDisplayManager != null) {
                this.mDisplayManager.unregister();
                this.mDisplayManager = null;
            }
            if (this.mCommonApiService != null) {
                log("Unbinding from service");
                Activity activity = SygicMain.getInstance().getFeature().getActivity();
                this.mCommonApiService.applicationStopping(activity.getPackageName());
                this.mCommonApiService = null;
                activity.unbindService(this);
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public void onServiceConnected(ComponentName name, IBinder service) {
        log("Service connected");
        this.mCommonApiService = ICommonAPIService.Stub.asInterface(service);
        if (this.mCommonApiService != null) {
            try {
                String packageName = SygicMain.getInstance().getFeature().getActivity().getPackageName();
                this.mCommonApiService.applicationStarted(packageName, 1);
                this.mConnectionManager = this.mCommonApiService.getConnectionManager(packageName, this.mConnectionListener);
                this.mDeviceStatusManager = this.mCommonApiService.getDeviceStatusManager(packageName, this.mDeviceStatusListener);
                this.mDeviceInfoManager = this.mCommonApiService.getDeviceInfoManager(packageName, this.mDeviceInfoListener);
                this.mDisplayManager = this.mCommonApiService.getDisplayManager(packageName, this.mDisplayListener);
                this.mContextManager = this.mCommonApiService.getContextManager(packageName, this.mContextListener);
                if (this.mConnectionManager != null) {
                    onSessionChanged(this.mConnectionManager.isMirrorLinkSessionEstablished());
                }
                if (this.mDeviceStatusManager != null) {
                    postDriveModeChanged(this.mDeviceStatusManager.isInDriveMode());
                }
                if (this.mDisplayManager != null) {
                    postDisplayConfigurationChanged(this.mDisplayManager.getDisplayConfiguration());
                }
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }

    public void onServiceDisconnected(ComponentName arg0) {
        this.mCommonApiService = null;
        this.mConnectionManager = null;
        this.mDeviceStatusManager = null;
        this.mDeviceInfoManager = null;
        this.mDisplayManager = null;
        this.mContextManager = null;
    }

    private void setAudioContext(boolean audioContext) {
        if (this.mIsAudioContextSet != audioContext) {
            try {
                if (this.mContextManager != null && this.mConnectionManager != null && this.mConnectionManager.isMirrorLinkSessionEstablished()) {
                    this.mContextManager.setAudioContextInformation(audioContext, new int[]{327680}, false);
                    this.mIsAudioContextSet = audioContext;
                }
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }

    public void onAudioPlay() {
        Handler handler = SygicMain.getHandler();
        handler.post(new C12227());
        handler.removeCallbacks(this.mAudioReleaseRunnable);
        samsungOnAudio(true);
        try {
            Thread.sleep(800);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void onAudioStop() {
        Handler handler = SygicMain.getHandler();
        handler.removeCallbacks(this.mAudioReleaseRunnable);
        handler.postDelayed(this.mAudioReleaseRunnable, 1000);
        handler.post(new C12238());
        samsungOnAudio(false);
    }

    public void onResume() {
        if (this.mPendingConnection != null) {
            onSessionChanged(this.mPendingConnection.booleanValue());
            this.mPendingConnection = null;
        }
    }

    public boolean isConnected() {
        if (this.mPendingConnection != null) {
            return this.mPendingConnection.booleanValue();
        }
        return this.mIsMirrorLinkSessionEstablished;
    }

    private void log(String text) {
        Log.d(MirrorLink11.class.getName(), text);
    }
}
