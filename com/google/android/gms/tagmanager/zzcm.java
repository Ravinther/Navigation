package com.google.android.gms.tagmanager;

import android.content.Context;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;

class zzcm implements zze {
    private boolean mClosed;
    private final Context mContext;
    private final String zzaOS;
    private String zzaPp;
    private zzs zzaRp;
    private final ScheduledExecutorService zzaRr;
    private final zza zzaRs;
    private ScheduledFuture<?> zzaRt;

    interface zzb {
        ScheduledExecutorService zzAC();
    }

    /* renamed from: com.google.android.gms.tagmanager.zzcm.1 */
    class C10131 implements zzb {
        final /* synthetic */ zzcm zzaRu;

        C10131(zzcm com_google_android_gms_tagmanager_zzcm) {
            this.zzaRu = com_google_android_gms_tagmanager_zzcm;
        }

        public ScheduledExecutorService zzAC() {
            return Executors.newSingleThreadScheduledExecutor();
        }
    }

    interface zza {
    }

    /* renamed from: com.google.android.gms.tagmanager.zzcm.2 */
    class C10142 implements zza {
        final /* synthetic */ zzcm zzaRu;

        C10142(zzcm com_google_android_gms_tagmanager_zzcm) {
            this.zzaRu = com_google_android_gms_tagmanager_zzcm;
        }
    }

    public zzcm(Context context, String str, zzs com_google_android_gms_tagmanager_zzs) {
        this(context, str, com_google_android_gms_tagmanager_zzs, null, null);
    }

    zzcm(Context context, String str, zzs com_google_android_gms_tagmanager_zzs, zzb com_google_android_gms_tagmanager_zzcm_zzb, zza com_google_android_gms_tagmanager_zzcm_zza) {
        this.zzaRp = com_google_android_gms_tagmanager_zzs;
        this.mContext = context;
        this.zzaOS = str;
        if (com_google_android_gms_tagmanager_zzcm_zzb == null) {
            com_google_android_gms_tagmanager_zzcm_zzb = new C10131(this);
        }
        this.zzaRr = com_google_android_gms_tagmanager_zzcm_zzb.zzAC();
        if (com_google_android_gms_tagmanager_zzcm_zza == null) {
            this.zzaRs = new C10142(this);
        } else {
            this.zzaRs = com_google_android_gms_tagmanager_zzcm_zza;
        }
    }

    private synchronized void zzAB() {
        if (this.mClosed) {
            throw new IllegalStateException("called method after closed");
        }
    }

    public synchronized void release() {
        zzAB();
        if (this.zzaRt != null) {
            this.zzaRt.cancel(false);
        }
        this.zzaRr.shutdown();
        this.mClosed = true;
    }

    public synchronized void zzeB(String str) {
        zzAB();
        this.zzaPp = str;
    }
}
