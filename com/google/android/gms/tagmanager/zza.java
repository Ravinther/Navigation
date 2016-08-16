package com.google.android.gms.tagmanager;

import android.content.Context;
import android.os.Process;
import com.google.android.gms.ads.identifier.AdvertisingIdClient;
import com.google.android.gms.ads.identifier.AdvertisingIdClient.Info;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.internal.zzlm;
import com.google.android.gms.internal.zzlo;
import java.io.IOException;

public class zza {
    private static Object zzaOF;
    private static zza zzaOG;
    private volatile boolean mClosed;
    private final Context mContext;
    private final Thread zzHt;
    private volatile Info zzLl;
    private volatile long zzaOB;
    private volatile long zzaOC;
    private volatile long zzaOD;
    private zza zzaOE;
    private final zzlm zzpO;

    public interface zza {
        Info zzzw();
    }

    /* renamed from: com.google.android.gms.tagmanager.zza.1 */
    class C10081 implements zza {
        final /* synthetic */ zza zzaOH;

        C10081(zza com_google_android_gms_tagmanager_zza) {
            this.zzaOH = com_google_android_gms_tagmanager_zza;
        }

        public Info zzzw() {
            Info info = null;
            try {
                info = AdvertisingIdClient.getAdvertisingIdInfo(this.zzaOH.mContext);
            } catch (IllegalStateException e) {
                zzbg.zzaE("IllegalStateException getting Advertising Id Info");
            } catch (GooglePlayServicesRepairableException e2) {
                zzbg.zzaE("GooglePlayServicesRepairableException getting Advertising Id Info");
            } catch (IOException e3) {
                zzbg.zzaE("IOException getting Ad Id Info");
            } catch (GooglePlayServicesNotAvailableException e4) {
                zzbg.zzaE("GooglePlayServicesNotAvailableException getting Advertising Id Info");
            } catch (Exception e5) {
                zzbg.zzaE("Unknown exception. Could not get the Advertising Id Info.");
            }
            return info;
        }
    }

    /* renamed from: com.google.android.gms.tagmanager.zza.2 */
    class C10092 implements Runnable {
        final /* synthetic */ zza zzaOH;

        C10092(zza com_google_android_gms_tagmanager_zza) {
            this.zzaOH = com_google_android_gms_tagmanager_zza;
        }

        public void run() {
            this.zzaOH.zzzu();
        }
    }

    static {
        zzaOF = new Object();
    }

    private zza(Context context) {
        this(context, null, zzlo.zzpN());
    }

    public zza(Context context, zza com_google_android_gms_tagmanager_zza_zza, zzlm com_google_android_gms_internal_zzlm) {
        this.zzaOB = 900000;
        this.zzaOC = 30000;
        this.mClosed = false;
        this.zzaOE = new C10081(this);
        this.zzpO = com_google_android_gms_internal_zzlm;
        if (context != null) {
            this.mContext = context.getApplicationContext();
        } else {
            this.mContext = context;
        }
        if (com_google_android_gms_tagmanager_zza_zza != null) {
            this.zzaOE = com_google_android_gms_tagmanager_zza_zza;
        }
        this.zzHt = new Thread(new C10092(this));
    }

    public static zza zzaL(Context context) {
        if (zzaOG == null) {
            synchronized (zzaOF) {
                if (zzaOG == null) {
                    zzaOG = new zza(context);
                    zzaOG.start();
                }
            }
        }
        return zzaOG;
    }

    private void zzzu() {
        Process.setThreadPriority(10);
        while (!this.mClosed) {
            try {
                this.zzLl = this.zzaOE.zzzw();
                Thread.sleep(this.zzaOB);
            } catch (InterruptedException e) {
                zzbg.zzaD("sleep interrupted in AdvertiserDataPoller thread; continuing");
            }
        }
    }

    private void zzzv() {
        if (this.zzpO.currentTimeMillis() - this.zzaOD >= this.zzaOC) {
            interrupt();
            this.zzaOD = this.zzpO.currentTimeMillis();
        }
    }

    public void interrupt() {
        this.zzHt.interrupt();
    }

    public boolean isLimitAdTrackingEnabled() {
        zzzv();
        return this.zzLl == null ? true : this.zzLl.isLimitAdTrackingEnabled();
    }

    public void start() {
        this.zzHt.start();
    }

    public String zzzt() {
        zzzv();
        return this.zzLl == null ? null : this.zzLl.getId();
    }
}
