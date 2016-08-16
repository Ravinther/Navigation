package com.google.android.gms.tagmanager;

import android.content.Context;
import android.os.Handler;
import android.os.Handler.Callback;
import android.os.Message;

class zzcu extends zzct {
    private static final Object zzaRX;
    private static zzcu zzaSi;
    private boolean connected;
    private Handler handler;
    private Context zzaRY;
    private zzau zzaRZ;
    private volatile zzas zzaSa;
    private int zzaSb;
    private boolean zzaSc;
    private boolean zzaSd;
    private boolean zzaSe;
    private zzav zzaSf;
    private zzbl zzaSg;
    private boolean zzaSh;

    /* renamed from: com.google.android.gms.tagmanager.zzcu.1 */
    class C10191 implements zzav {
        final /* synthetic */ zzcu zzaSj;

        C10191(zzcu com_google_android_gms_tagmanager_zzcu) {
            this.zzaSj = com_google_android_gms_tagmanager_zzcu;
        }

        public void zzas(boolean z) {
            this.zzaSj.zzd(z, this.zzaSj.connected);
        }
    }

    /* renamed from: com.google.android.gms.tagmanager.zzcu.2 */
    class C10202 implements Callback {
        final /* synthetic */ zzcu zzaSj;

        C10202(zzcu com_google_android_gms_tagmanager_zzcu) {
            this.zzaSj = com_google_android_gms_tagmanager_zzcu;
        }

        public boolean handleMessage(Message msg) {
            if (1 == msg.what && zzcu.zzaRX.equals(msg.obj)) {
                this.zzaSj.dispatch();
                if (this.zzaSj.zzaSb > 0 && !this.zzaSj.zzaSh) {
                    this.zzaSj.handler.sendMessageDelayed(this.zzaSj.handler.obtainMessage(1, zzcu.zzaRX), (long) this.zzaSj.zzaSb);
                }
            }
            return true;
        }
    }

    /* renamed from: com.google.android.gms.tagmanager.zzcu.3 */
    class C10213 implements Runnable {
        final /* synthetic */ zzcu zzaSj;

        C10213(zzcu com_google_android_gms_tagmanager_zzcu) {
            this.zzaSj = com_google_android_gms_tagmanager_zzcu;
        }

        public void run() {
            this.zzaSj.zzaRZ.dispatch();
        }
    }

    static {
        zzaRX = new Object();
    }

    private zzcu() {
        this.zzaSb = 1800000;
        this.zzaSc = true;
        this.zzaSd = false;
        this.connected = true;
        this.zzaSe = true;
        this.zzaSf = new C10191(this);
        this.zzaSh = false;
    }

    public static zzcu zzAP() {
        if (zzaSi == null) {
            zzaSi = new zzcu();
        }
        return zzaSi;
    }

    private void zzAQ() {
        this.zzaSg = new zzbl(this);
        this.zzaSg.zzaP(this.zzaRY);
    }

    private void zzAR() {
        this.handler = new Handler(this.zzaRY.getMainLooper(), new C10202(this));
        if (this.zzaSb > 0) {
            this.handler.sendMessageDelayed(this.handler.obtainMessage(1, zzaRX), (long) this.zzaSb);
        }
    }

    public synchronized void dispatch() {
        if (this.zzaSd) {
            this.zzaSa.zzg(new C10213(this));
        } else {
            zzbg.m1448v("Dispatch call queued. Dispatch will run once initialization is complete.");
            this.zzaSc = true;
        }
    }

    synchronized zzau zzAS() {
        if (this.zzaRZ == null) {
            if (this.zzaRY == null) {
                throw new IllegalStateException("Cant get a store unless we have a context");
            }
            this.zzaRZ = new zzby(this.zzaSf, this.zzaRY);
        }
        if (this.handler == null) {
            zzAR();
        }
        this.zzaSd = true;
        if (this.zzaSc) {
            dispatch();
            this.zzaSc = false;
        }
        if (this.zzaSg == null && this.zzaSe) {
            zzAQ();
        }
        return this.zzaRZ;
    }

    synchronized void zza(Context context, zzas com_google_android_gms_tagmanager_zzas) {
        if (this.zzaRY == null) {
            this.zzaRY = context.getApplicationContext();
            if (this.zzaSa == null) {
                this.zzaSa = com_google_android_gms_tagmanager_zzas;
            }
        }
    }

    synchronized void zzat(boolean z) {
        zzd(this.zzaSh, z);
    }

    synchronized void zzd(boolean z, boolean z2) {
        if (!(this.zzaSh == z && this.connected == z2)) {
            if (z || !z2) {
                if (this.zzaSb > 0) {
                    this.handler.removeMessages(1, zzaRX);
                }
            }
            if (!z && z2 && this.zzaSb > 0) {
                this.handler.sendMessageDelayed(this.handler.obtainMessage(1, zzaRX), (long) this.zzaSb);
            }
            StringBuilder append = new StringBuilder().append("PowerSaveMode ");
            String str = (z || !z2) ? "initiated." : "terminated.";
            zzbg.m1448v(append.append(str).toString());
            this.zzaSh = z;
            this.connected = z2;
        }
    }

    synchronized void zzhY() {
        if (!this.zzaSh && this.connected && this.zzaSb > 0) {
            this.handler.removeMessages(1, zzaRX);
            this.handler.sendMessage(this.handler.obtainMessage(1, zzaRX));
        }
    }
}
