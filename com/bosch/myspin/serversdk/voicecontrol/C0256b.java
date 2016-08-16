package com.bosch.myspin.serversdk.voicecontrol;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.os.Messenger;
import com.bosch.myspin.serversdk.MySpinServerSDK.VoiceControlListener;
import com.bosch.myspin.serversdk.utils.C0236e;
import com.bosch.myspin.serversdk.utils.C0236e.C0234a;
import com.bosch.myspin.serversdk.utils.Logger;
import com.bosch.myspin.serversdk.utils.Logger.LogComponent;
import com.bosch.myspin.serversdk.voicecontrol.C0254f.C0260a;
import com.bosch.myspin.serversdk.voicecontrol.bluetooth.C0257a;
import com.sygic.aura.C1090R;
import java.util.HashSet;
import java.util.Set;
import loquendo.tts.engine.TTSConst;

/* renamed from: com.bosch.myspin.serversdk.voicecontrol.b */
public class C0256b extends C0254f implements C0255e {
    private static final LogComponent f485a;
    private static C0256b f486b;
    private Context f487c;
    private C0257a f488d;
    private C0261g f489e;
    private C0250a f490f;
    private boolean f491g;
    private Set<VoiceControlListener> f492h;
    private C0260a f493i;
    private int f494j;
    private int f495k;
    private C0262h f496l;
    private final Messenger f497m;
    private boolean f498n;
    private ServiceConnection f499o;
    private BroadcastReceiver f500p;

    /* renamed from: com.bosch.myspin.serversdk.voicecontrol.b.1 */
    static /* synthetic */ class C02531 {
        static final /* synthetic */ int[] f484a;

        static {
            f484a = new int[C0260a.values().length];
            try {
                f484a[C0260a.STATE_UNAVAILABLE.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                f484a[C0260a.STATE_IDLE.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                f484a[C0260a.STATE_REQUEST.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
            try {
                f484a[C0260a.STATE_ACTIVE.ordinal()] = 4;
            } catch (NoSuchFieldError e4) {
            }
            try {
                f484a[C0260a.STATE_SCO_CONNECTED.ordinal()] = 5;
            } catch (NoSuchFieldError e5) {
            }
            try {
                f484a[C0260a.STATE_RESIGN.ordinal()] = 6;
            } catch (NoSuchFieldError e6) {
            }
        }
    }

    static {
        f485a = LogComponent.VoiceControl;
    }

    private C0256b(Context context) {
        this.f492h = new HashSet();
        this.f493i = C0260a.STATE_UNAVAILABLE;
        this.f494j = 0;
        this.f495k = C1090R.styleable.Theme_checkedTextViewStyle;
        this.f496l = new C0262h();
        this.f497m = new Messenger(this.f496l);
        this.f499o = new C0258c(this);
        this.f500p = new C0259d(this);
        if (context == null) {
            throw new IllegalArgumentException("MySpinVoiceControlManager: Context must not be null");
        }
        this.f487c = context;
        this.f488d = new C0257a(this.f487c);
        this.f489e = new C0261g(this.f487c);
    }

    public void m460a() {
        Logger.logDebug(f485a, "MySpinVoiceControlManager/Initializing VoiceControl service [!mIsInitialized=" + (!this.f498n) + "] " + hashCode());
        if (!this.f498n) {
            try {
                Logger.logDebug(f485a, "MySpinVoiceControlManager/Binding VoiceControl service successful: " + this.f487c.bindService(C0236e.m368a(this.f487c, new Intent("com.bosch.myspin.ACTION_BIND_VOICECONTROL_INTERFACE")), this.f499o, 1));
            } catch (Throwable e) {
                Logger.logError(f485a, "MySpinVoiceControlManager/Can't bind VoiceControl, make sure that only one LauncherApp installed!", e);
            } catch (C0234a e2) {
                Logger.logWarning(f485a, "MySpinVoiceControlManager/Can't bind VoiceControl service, make sure that a LauncherApp supporting VoiceControl is installed!");
            }
            this.f496l.m468a(this);
            this.f498n = true;
        }
    }

    public void m462b() {
        Logger.logDebug(f485a, "MySpinVoiceControlManager/Deinitializing VoiceControl service [mIsInitialized=" + this.f498n + "] " + hashCode());
        if (this.f498n) {
            this.f496l.m469b(this);
            if (this.f488d.m466c()) {
                m463b(3);
            }
            this.f489e.m467b(this.f487c);
            if (this.f490f != null) {
                this.f487c.unbindService(this.f499o);
                this.f490f = null;
            }
            this.f498n = false;
        }
    }

    public static C0256b m452a(Context context) {
        if (f486b == null) {
            synchronized (C0256b.class) {
                if (f486b == null) {
                    f486b = new C0256b(context);
                }
            }
        }
        return f486b;
    }

    public void m463b(int i) {
        Logger.logDebug(f485a, "MySpinVoiceControlManager/resignType: " + i);
        m454a(C0260a.STATE_RESIGN);
        this.f488d.m465b();
        if (this.f490f == null || this.f494j == 0) {
            Logger.logWarning(f485a, "MySpinVoiceControlManager/No voice control service!");
            return;
        }
        try {
            this.f490f.m443b(i);
        } catch (Throwable e) {
            Logger.logError(f485a, "MySpinVoiceControlManager/Could not resign voice control!", e);
        }
    }

    public void m461a(int i, int i2) {
        this.f495k = 0;
        Logger.logDebug(f485a, "MySpinVoiceControlManager/ voiceControlSessionState: " + i + " voiceControlSessionConstraint: " + i2);
        switch (i2) {
            case TTSConst.TTSEVT_AUDIOSTART /*0*/:
                break;
            case TTSConst.TTSMULTILINE /*1*/:
                this.f495k = 1;
                break;
            case TTSConst.TTSPARAGRAPH /*2*/:
                this.f495k = 2;
                break;
            case TTSConst.TTSUNICODE /*3*/:
                this.f495k = 3;
                break;
            default:
                Logger.logWarning(f485a, "MySpinVoiceControlManager/[UNKNOWN CODE]");
                break;
        }
        switch (i) {
            case TTSConst.TTSEVT_AUDIOSTART /*0*/:
                m454a(C0260a.STATE_UNAVAILABLE);
            case TTSConst.TTSMULTILINE /*1*/:
                m454a(C0260a.STATE_IDLE);
            case TTSConst.TTSPARAGRAPH /*2*/:
                m454a(C0260a.STATE_REQUEST);
            case TTSConst.TTSUNICODE /*3*/:
                m454a(C0260a.STATE_ACTIVE);
            case TTSConst.TTSXML /*4*/:
                m454a(C0260a.STATE_RESIGN);
            default:
                Logger.logWarning(f485a, "MySpinVoiceControlManager/[UNKNOWN]");
        }
    }

    private void m458c(int i) {
        this.f494j = i;
        for (VoiceControlListener onVoiceControlStateChanged : this.f492h) {
            onVoiceControlStateChanged.onVoiceControlStateChanged(i, this.f495k);
        }
    }

    private void m454a(C0260a c0260a) {
        Logger.logDebug(f485a, "MySpinVoiceControlManager/STATE_CHANGE [" + m456b(this.f493i) + "] => [" + m456b(c0260a) + "]");
        this.f493i = c0260a;
        switch (C02531.f484a[this.f493i.ordinal()]) {
            case TTSConst.TTSMULTILINE /*1*/:
                m458c(0);
            case TTSConst.TTSPARAGRAPH /*2*/:
                if (this.f488d.m466c()) {
                    this.f495k = 4;
                    m463b(3);
                    m458c(4);
                }
                m458c(1);
            case TTSConst.TTSUNICODE /*3*/:
                m458c(2);
                this.f487c.registerReceiver(this.f500p, new IntentFilter("android.media.ACTION_SCO_AUDIO_STATE_UPDATED"));
                this.f488d.m464a();
                this.f491g = true;
            case TTSConst.TTSXML /*4*/:
                if (!this.f491g) {
                    m458c(2);
                    this.f487c.registerReceiver(this.f500p, new IntentFilter("android.media.ACTION_SCO_AUDIO_STATE_UPDATED"));
                    this.f488d.m464a();
                }
            case TTSConst.TTSEVT_TEXT /*5*/:
                m458c(3);
            case TTSConst.TTSEVT_SENTENCE /*6*/:
                m458c(4);
                this.f488d.m465b();
                try {
                    this.f487c.unregisterReceiver(this.f500p);
                } catch (IllegalArgumentException e) {
                    Logger.logWarning(f485a, "MySpinVoiceControlManager/BroadcastReceiver was not registered yet!");
                }
            default:
                Logger.logWarning(f485a, "MySpinVoiceControlManager/Unknown status: " + c0260a);
        }
    }

    private String m456b(C0260a c0260a) {
        switch (C02531.f484a[c0260a.ordinal()]) {
            case TTSConst.TTSMULTILINE /*1*/:
                return "STATE_UNAVAILABLE";
            case TTSConst.TTSPARAGRAPH /*2*/:
                return "STATE_IDLE";
            case TTSConst.TTSUNICODE /*3*/:
                return "STATE_REQUEST";
            case TTSConst.TTSXML /*4*/:
                return "STATE_ACTIVE";
            case TTSConst.TTSEVT_TEXT /*5*/:
                return "STATE_SCO_CONNECTED";
            case TTSConst.TTSEVT_SENTENCE /*6*/:
                return "STATE_RESIGN";
            default:
                return "!unknown state!";
        }
    }
}
