package com.bosch.myspin.serversdk.voicecontrol.bluetooth;

import android.content.Context;
import android.media.AudioManager;
import com.bosch.myspin.serversdk.utils.Logger;
import com.bosch.myspin.serversdk.utils.Logger.LogComponent;

/* renamed from: com.bosch.myspin.serversdk.voicecontrol.bluetooth.a */
public class C0257a {
    private static final LogComponent f501a;
    private Context f502b;
    private AudioManager f503c;
    private boolean f504d;

    static {
        f501a = LogComponent.VoiceControl;
    }

    public C0257a(Context context) {
        this.f502b = context;
        this.f503c = (AudioManager) this.f502b.getSystemService("audio");
    }

    public void m464a() {
        Logger.logDebug(f501a, "BluetoothScoManager/startScoSession");
        if (this.f503c == null) {
            Logger.logError(f501a, "BluetoothScoManager/Could not get AudioManager Service! AudioManager == null");
            return;
        }
        if (this.f502b.getApplicationInfo().targetSdkVersion >= 18) {
            Logger.logInfo(f501a, "BluetoothScoManager/Using SCO_MODE_RAW with API " + this.f502b.getApplicationInfo().targetSdkVersion);
        } else {
            Logger.logInfo(f501a, "BluetoothScoManager/Using SCO_MODE_VIRTUAL with API " + this.f502b.getApplicationInfo().targetSdkVersion);
        }
        if (!m466c()) {
            this.f503c.setStreamSolo(0, true);
            this.f503c.setStreamVolume(0, this.f503c.getStreamMaxVolume(0), 0);
            this.f503c.startBluetoothSco();
            this.f503c.setBluetoothScoOn(true);
            this.f504d = true;
        }
    }

    public void m465b() {
        if (this.f503c == null) {
            Logger.logError(f501a, "BluetoothScoManager/Could not get AudioManager Service! AudioManager == null");
            return;
        }
        Logger.logDebug(f501a, "BluetoothScoManager/stopScoSession");
        if (this.f504d) {
            this.f503c.setStreamSolo(0, false);
            this.f503c.stopBluetoothSco();
            this.f504d = false;
        }
    }

    public boolean m466c() {
        if (this.f503c == null) {
            Logger.logError(f501a, "BluetoothScoManager/Could not get AudioManager Service! AudioManager == null");
            return false;
        } else if (this.f504d && this.f503c.isBluetoothScoOn()) {
            return true;
        } else {
            return false;
        }
    }
}
