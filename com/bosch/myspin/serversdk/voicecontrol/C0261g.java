package com.bosch.myspin.serversdk.voicecontrol;

import android.content.Context;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import com.bosch.myspin.serversdk.utils.Logger;
import com.bosch.myspin.serversdk.utils.Logger.LogComponent;

/* renamed from: com.bosch.myspin.serversdk.voicecontrol.g */
public class C0261g extends PhoneStateListener {
    private static final LogComponent f514a;
    private Context f515b;

    static {
        f514a = LogComponent.VoiceControl;
    }

    public C0261g(Context context) {
        if (context == null) {
            throw new IllegalArgumentException("Context is null!");
        }
        this.f515b = context;
    }

    void m467b(Context context) {
        TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService("phone");
        if (telephonyManager != null) {
            telephonyManager.listen(this, 0);
        }
    }

    public void onCallStateChanged(int i, String str) {
        if (i == 1) {
            Logger.logDebug(f514a, "VoiceControlPhoneCallReceiver/CALL_STATE_RINGING");
            C0256b.m452a(this.f515b).m463b(4);
        }
    }
}
