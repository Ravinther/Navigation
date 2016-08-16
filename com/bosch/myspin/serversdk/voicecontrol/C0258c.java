package com.bosch.myspin.serversdk.voicecontrol;

import android.content.ComponentName;
import android.content.ServiceConnection;
import android.os.IBinder;
import com.bosch.myspin.serversdk.utils.Logger;
import com.bosch.myspin.serversdk.voicecontrol.C0250a.C0252a;

/* renamed from: com.bosch.myspin.serversdk.voicecontrol.c */
class C0258c implements ServiceConnection {
    final /* synthetic */ C0256b f505a;

    C0258c(C0256b c0256b) {
        this.f505a = c0256b;
    }

    public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
        Logger.logDebug(C0256b.f485a, "MySpinVoiceControlManager/VoiceControl service is connected");
        this.f505a.f490f = C0252a.m448b(iBinder);
        if (this.f505a.f490f != null) {
            try {
                this.f505a.f490f.m441a(this.f505a.f497m.getBinder());
                return;
            } catch (Throwable e) {
                Logger.logError(C0256b.f485a, "MySpinVoiceControlManager/Could not set VoiceControl messenger! ", e);
                return;
            }
        }
        Logger.logError(C0256b.f485a, "MySpinVoiceControlManager/No VoiceControl service!");
    }

    public void onServiceDisconnected(ComponentName componentName) {
        Logger.logDebug(C0256b.f485a, "MySpinVoiceControlManager/VoiceControl service is disconnected");
        this.f505a.f490f = null;
    }
}
