package com.bosch.myspin.serversdk.voicecontrol;

import android.os.Handler;
import android.os.Message;
import com.bosch.myspin.serversdk.utils.Logger;
import com.bosch.myspin.serversdk.utils.Logger.LogComponent;
import com.sygic.aura.C1090R;
import java.util.ArrayList;
import java.util.List;

/* renamed from: com.bosch.myspin.serversdk.voicecontrol.h */
public class C0262h extends Handler {
    private static final LogComponent f516a;
    private List<C0255e> f517b;
    private int f518c;
    private int f519d;

    static {
        f516a = LogComponent.VoiceControl;
    }

    public C0262h() {
        this.f518c = 0;
        this.f519d = C1090R.styleable.Theme_checkedTextViewStyle;
        this.f517b = new ArrayList();
    }

    public void handleMessage(Message message) {
        if (message == null) {
            Logger.logWarning(f516a, "VoiceControlStateMessageHandler/Message is null and is not being handled!!");
        } else if (message.what == 66) {
            Logger.logDebug(f516a, "VoiceControlStateMessageHandler/Message KEY_VOICE_CONTROL_SESSION_STATUS received, notifying " + this.f517b.size() + " listeners with state [" + message.arg1 + "] and code [" + message.arg2 + "].");
            this.f518c = message.arg1;
            this.f519d = message.arg2;
            for (C0255e a : this.f517b) {
                a.m449a(this.f518c, this.f519d);
            }
        }
    }

    public void m468a(C0255e c0255e) {
        if (!this.f517b.contains(c0255e)) {
            this.f517b.add(c0255e);
            c0255e.m449a(this.f518c, this.f519d);
        }
    }

    public void m469b(C0255e c0255e) {
        this.f517b.remove(c0255e);
    }
}
