package com.google.android.gms.internal;

import android.content.Context;
import com.google.android.gms.ads.internal.util.client.VersionInfoParcel;
import com.google.android.gms.common.internal.zzx;
import com.sygic.aura.poi.detail.PoiDetailFragment;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

@zzgk
public class zzdv {
    private final Context mContext;
    private final VersionInfoParcel zzpa;
    private final Object zzpc;
    private final String zzxF;
    private zzb<zzbb> zzxG;
    private zzb<zzbb> zzxH;
    private zze zzxI;
    private int zzxJ;

    public interface zzb<T> {
        void zzc(T t);
    }

    /* renamed from: com.google.android.gms.internal.zzdv.1 */
    class C08991 implements Runnable {
        final /* synthetic */ zze zzxK;
        final /* synthetic */ zzdv zzxL;

        /* renamed from: com.google.android.gms.internal.zzdv.1.1 */
        class C08941 implements com.google.android.gms.internal.zzbb.zza {
            final /* synthetic */ zzbb zzxM;
            final /* synthetic */ C08991 zzxN;

            /* renamed from: com.google.android.gms.internal.zzdv.1.1.1 */
            class C08931 extends TimerTask {
                final /* synthetic */ C08941 zzxO;

                /* renamed from: com.google.android.gms.internal.zzdv.1.1.1.1 */
                class C08921 implements Runnable {
                    final /* synthetic */ C08931 zzxP;

                    C08921(C08931 c08931) {
                        this.zzxP = c08931;
                    }

                    public void run() {
                        this.zzxP.zzxO.zzxM.destroy();
                    }
                }

                C08931(C08941 c08941) {
                    this.zzxO = c08941;
                }

                public void run() {
                    synchronized (this.zzxO.zzxN.zzxL.zzpc) {
                        if (this.zzxO.zzxN.zzxK.getStatus() == -1 || this.zzxO.zzxN.zzxK.getStatus() == 1) {
                            return;
                        }
                        this.zzxO.zzxN.zzxK.reject();
                        zzhu.runOnUiThread(new C08921(this));
                        com.google.android.gms.ads.internal.util.client.zzb.m1445v("Could not receive loaded message in a timely manner. Rejecting.");
                    }
                }
            }

            C08941(C08991 c08991, zzbb com_google_android_gms_internal_zzbb) {
                this.zzxN = c08991;
                this.zzxM = com_google_android_gms_internal_zzbb;
            }

            public void zzcj() {
                new Timer().schedule(new C08931(this), (long) zza.zzxU);
            }
        }

        /* renamed from: com.google.android.gms.internal.zzdv.1.2 */
        class C08952 implements zzdg {
            final /* synthetic */ zzbb zzxM;
            final /* synthetic */ C08991 zzxN;

            C08952(C08991 c08991, zzbb com_google_android_gms_internal_zzbb) {
                this.zzxN = c08991;
                this.zzxM = com_google_android_gms_internal_zzbb;
            }

            public void zza(zzip com_google_android_gms_internal_zzip, Map<String, String> map) {
                synchronized (this.zzxN.zzxL.zzpc) {
                    if (this.zzxN.zzxK.getStatus() == -1 || this.zzxN.zzxK.getStatus() == 1) {
                        return;
                    }
                    this.zzxN.zzxL.zzxJ = 0;
                    this.zzxN.zzxL.zzxG.zzc(this.zzxM);
                    this.zzxN.zzxK.zzg(this.zzxM);
                    this.zzxN.zzxL.zzxI = this.zzxN.zzxK;
                    com.google.android.gms.ads.internal.util.client.zzb.m1445v("Successfully loaded JS Engine.");
                }
            }
        }

        /* renamed from: com.google.android.gms.internal.zzdv.1.3 */
        class C08963 implements zzdg {
            final /* synthetic */ zzbb zzxM;
            final /* synthetic */ C08991 zzxN;
            final /* synthetic */ zzic zzxQ;

            C08963(C08991 c08991, zzbb com_google_android_gms_internal_zzbb, zzic com_google_android_gms_internal_zzic) {
                this.zzxN = c08991;
                this.zzxM = com_google_android_gms_internal_zzbb;
                this.zzxQ = com_google_android_gms_internal_zzic;
            }

            public void zza(zzip com_google_android_gms_internal_zzip, Map<String, String> map) {
                synchronized (this.zzxN.zzxL.zzpc) {
                    com.google.android.gms.ads.internal.util.client.zzb.zzaD("JS Engine is requesting an update");
                    if (this.zzxN.zzxL.zzxJ == 0) {
                        com.google.android.gms.ads.internal.util.client.zzb.zzaD("Starting reload.");
                        this.zzxN.zzxL.zzxJ = 2;
                        this.zzxN.zzxL.zzdK();
                    }
                    this.zzxM.zzb("/requestReload", (zzdg) this.zzxQ.get());
                }
            }
        }

        /* renamed from: com.google.android.gms.internal.zzdv.1.4 */
        class C08984 extends TimerTask {
            final /* synthetic */ zzbb zzxM;
            final /* synthetic */ C08991 zzxN;

            /* renamed from: com.google.android.gms.internal.zzdv.1.4.1 */
            class C08971 implements Runnable {
                final /* synthetic */ C08984 zzxR;

                C08971(C08984 c08984) {
                    this.zzxR = c08984;
                }

                public void run() {
                    this.zzxR.zzxM.destroy();
                }
            }

            C08984(C08991 c08991, zzbb com_google_android_gms_internal_zzbb) {
                this.zzxN = c08991;
                this.zzxM = com_google_android_gms_internal_zzbb;
            }

            public void run() {
                synchronized (this.zzxN.zzxL.zzpc) {
                    if (this.zzxN.zzxK.getStatus() == -1 || this.zzxN.zzxK.getStatus() == 1) {
                        return;
                    }
                    this.zzxN.zzxK.reject();
                    zzhu.runOnUiThread(new C08971(this));
                    com.google.android.gms.ads.internal.util.client.zzb.m1445v("Could not receive loaded message in a timely manner. Rejecting.");
                }
            }
        }

        C08991(zzdv com_google_android_gms_internal_zzdv, zze com_google_android_gms_internal_zzdv_zze) {
            this.zzxL = com_google_android_gms_internal_zzdv;
            this.zzxK = com_google_android_gms_internal_zzdv_zze;
        }

        public void run() {
            zzbb zza = this.zzxL.zza(this.zzxL.mContext, this.zzxL.zzpa);
            zza.zza(new C08941(this, zza));
            zza.zza("/jsLoaded", new C08952(this, zza));
            zzic com_google_android_gms_internal_zzic = new zzic();
            zzdg c08963 = new C08963(this, zza, com_google_android_gms_internal_zzic);
            com_google_android_gms_internal_zzic.set(c08963);
            zza.zza("/requestReload", c08963);
            if (this.zzxL.zzxF.endsWith(".js")) {
                zza.zzs(this.zzxL.zzxF);
            } else if (this.zzxL.zzxF.startsWith("<html>")) {
                zza.zzu(this.zzxL.zzxF);
            } else {
                zza.zzt(this.zzxL.zzxF);
            }
            new Timer().schedule(new C08984(this, zza), (long) zza.zzxT);
        }
    }

    /* renamed from: com.google.android.gms.internal.zzdv.2 */
    class C09002 implements com.google.android.gms.internal.zzij.zzc<zzbb> {
        final /* synthetic */ zzdv zzxL;
        final /* synthetic */ zze zzxS;

        C09002(zzdv com_google_android_gms_internal_zzdv, zze com_google_android_gms_internal_zzdv_zze) {
            this.zzxL = com_google_android_gms_internal_zzdv;
            this.zzxS = com_google_android_gms_internal_zzdv_zze;
        }

        public void zza(zzbb com_google_android_gms_internal_zzbb) {
            synchronized (this.zzxL.zzpc) {
                this.zzxL.zzxJ = 0;
                if (!(this.zzxL.zzxI == null || this.zzxS == this.zzxL.zzxI)) {
                    com.google.android.gms.ads.internal.util.client.zzb.m1445v("New JS engine is loaded, marking previous one as destroyable.");
                    this.zzxL.zzxI.zzdO();
                }
                this.zzxL.zzxI = this.zzxS;
            }
        }

        public /* synthetic */ void zzc(Object obj) {
            zza((zzbb) obj);
        }
    }

    /* renamed from: com.google.android.gms.internal.zzdv.3 */
    class C09013 implements com.google.android.gms.internal.zzij.zza {
        final /* synthetic */ zzdv zzxL;
        final /* synthetic */ zze zzxS;

        C09013(zzdv com_google_android_gms_internal_zzdv, zze com_google_android_gms_internal_zzdv_zze) {
            this.zzxL = com_google_android_gms_internal_zzdv;
            this.zzxS = com_google_android_gms_internal_zzdv_zze;
        }

        public void run() {
            synchronized (this.zzxL.zzpc) {
                this.zzxL.zzxJ = 1;
                com.google.android.gms.ads.internal.util.client.zzb.m1445v("Failed loading new engine. Marking new engine destroyable.");
                this.zzxS.zzdO();
            }
        }
    }

    static class zza {
        static int zzxT;
        static int zzxU;

        static {
            zzxT = 60000;
            zzxU = PoiDetailFragment.MIN_STREET_VIEW_BYTES;
        }
    }

    public static class zzc<T> implements zzb<T> {
        public void zzc(T t) {
        }
    }

    public static class zzd extends zzik<zzbe> {
        private final Object zzpc;
        private final zze zzxV;
        private boolean zzxW;

        /* renamed from: com.google.android.gms.internal.zzdv.zzd.1 */
        class C09021 implements com.google.android.gms.internal.zzij.zzc<zzbe> {
            final /* synthetic */ zzd zzxX;

            C09021(zzd com_google_android_gms_internal_zzdv_zzd) {
                this.zzxX = com_google_android_gms_internal_zzdv_zzd;
            }

            public void zzb(zzbe com_google_android_gms_internal_zzbe) {
                com.google.android.gms.ads.internal.util.client.zzb.m1445v("Ending javascript session.");
                ((zzbf) com_google_android_gms_internal_zzbe).zzck();
            }

            public /* synthetic */ void zzc(Object obj) {
                zzb((zzbe) obj);
            }
        }

        /* renamed from: com.google.android.gms.internal.zzdv.zzd.2 */
        class C09032 implements com.google.android.gms.internal.zzij.zzc<zzbe> {
            final /* synthetic */ zzd zzxX;

            C09032(zzd com_google_android_gms_internal_zzdv_zzd) {
                this.zzxX = com_google_android_gms_internal_zzdv_zzd;
            }

            public void zzb(zzbe com_google_android_gms_internal_zzbe) {
                com.google.android.gms.ads.internal.util.client.zzb.m1445v("Releasing engine reference.");
                this.zzxX.zzxV.zzdN();
            }

            public /* synthetic */ void zzc(Object obj) {
                zzb((zzbe) obj);
            }
        }

        /* renamed from: com.google.android.gms.internal.zzdv.zzd.3 */
        class C09043 implements com.google.android.gms.internal.zzij.zza {
            final /* synthetic */ zzd zzxX;

            C09043(zzd com_google_android_gms_internal_zzdv_zzd) {
                this.zzxX = com_google_android_gms_internal_zzdv_zzd;
            }

            public void run() {
                this.zzxX.zzxV.zzdN();
            }
        }

        public zzd(zze com_google_android_gms_internal_zzdv_zze) {
            this.zzpc = new Object();
            this.zzxV = com_google_android_gms_internal_zzdv_zze;
        }

        public void release() {
            synchronized (this.zzpc) {
                if (this.zzxW) {
                    return;
                }
                this.zzxW = true;
                zza(new C09021(this), new com.google.android.gms.internal.zzij.zzb());
                zza(new C09032(this), new C09043(this));
            }
        }
    }

    public static class zze extends zzik<zzbb> {
        private final Object zzpc;
        private zzb<zzbb> zzxH;
        private boolean zzxY;
        private int zzxZ;

        /* renamed from: com.google.android.gms.internal.zzdv.zze.1 */
        class C09051 implements com.google.android.gms.internal.zzij.zzc<zzbb> {
            final /* synthetic */ zzd zzya;
            final /* synthetic */ zze zzyb;

            C09051(zze com_google_android_gms_internal_zzdv_zze, zzd com_google_android_gms_internal_zzdv_zzd) {
                this.zzyb = com_google_android_gms_internal_zzdv_zze;
                this.zzya = com_google_android_gms_internal_zzdv_zzd;
            }

            public void zza(zzbb com_google_android_gms_internal_zzbb) {
                com.google.android.gms.ads.internal.util.client.zzb.m1445v("Getting a new session for JS Engine.");
                this.zzya.zzg(com_google_android_gms_internal_zzbb.zzci());
            }

            public /* synthetic */ void zzc(Object obj) {
                zza((zzbb) obj);
            }
        }

        /* renamed from: com.google.android.gms.internal.zzdv.zze.2 */
        class C09062 implements com.google.android.gms.internal.zzij.zza {
            final /* synthetic */ zzd zzya;
            final /* synthetic */ zze zzyb;

            C09062(zze com_google_android_gms_internal_zzdv_zze, zzd com_google_android_gms_internal_zzdv_zzd) {
                this.zzyb = com_google_android_gms_internal_zzdv_zze;
                this.zzya = com_google_android_gms_internal_zzdv_zzd;
            }

            public void run() {
                com.google.android.gms.ads.internal.util.client.zzb.m1445v("Rejecting reference for JS Engine.");
                this.zzya.reject();
            }
        }

        /* renamed from: com.google.android.gms.internal.zzdv.zze.3 */
        class C09083 implements com.google.android.gms.internal.zzij.zzc<zzbb> {
            final /* synthetic */ zze zzyb;

            /* renamed from: com.google.android.gms.internal.zzdv.zze.3.1 */
            class C09071 implements Runnable {
                final /* synthetic */ zzbb zzrt;
                final /* synthetic */ C09083 zzyc;

                C09071(C09083 c09083, zzbb com_google_android_gms_internal_zzbb) {
                    this.zzyc = c09083;
                    this.zzrt = com_google_android_gms_internal_zzbb;
                }

                public void run() {
                    this.zzyc.zzyb.zzxH.zzc(this.zzrt);
                    this.zzrt.destroy();
                }
            }

            C09083(zze com_google_android_gms_internal_zzdv_zze) {
                this.zzyb = com_google_android_gms_internal_zzdv_zze;
            }

            public void zza(zzbb com_google_android_gms_internal_zzbb) {
                zzhu.runOnUiThread(new C09071(this, com_google_android_gms_internal_zzbb));
            }

            public /* synthetic */ void zzc(Object obj) {
                zza((zzbb) obj);
            }
        }

        public zze(zzb<zzbb> com_google_android_gms_internal_zzdv_zzb_com_google_android_gms_internal_zzbb) {
            this.zzpc = new Object();
            this.zzxH = com_google_android_gms_internal_zzdv_zzb_com_google_android_gms_internal_zzbb;
            this.zzxY = false;
            this.zzxZ = 0;
        }

        public zzd zzdM() {
            zzd com_google_android_gms_internal_zzdv_zzd = new zzd(this);
            synchronized (this.zzpc) {
                zza(new C09051(this, com_google_android_gms_internal_zzdv_zzd), new C09062(this, com_google_android_gms_internal_zzdv_zzd));
                zzx.zzY(this.zzxZ >= 0);
                this.zzxZ++;
            }
            return com_google_android_gms_internal_zzdv_zzd;
        }

        protected void zzdN() {
            boolean z = true;
            synchronized (this.zzpc) {
                if (this.zzxZ < 1) {
                    z = false;
                }
                zzx.zzY(z);
                com.google.android.gms.ads.internal.util.client.zzb.m1445v("Releasing 1 reference for JS Engine");
                this.zzxZ--;
                zzdP();
            }
        }

        public void zzdO() {
            boolean z = true;
            synchronized (this.zzpc) {
                if (this.zzxZ < 0) {
                    z = false;
                }
                zzx.zzY(z);
                com.google.android.gms.ads.internal.util.client.zzb.m1445v("Releasing root reference. JS Engine will be destroyed once other references are released.");
                this.zzxY = true;
                zzdP();
            }
        }

        protected void zzdP() {
            synchronized (this.zzpc) {
                zzx.zzY(this.zzxZ >= 0);
                if (this.zzxY && this.zzxZ == 0) {
                    com.google.android.gms.ads.internal.util.client.zzb.m1445v("No reference is left (including root). Cleaning up engine.");
                    zza(new C09083(this), new com.google.android.gms.internal.zzij.zzb());
                } else {
                    com.google.android.gms.ads.internal.util.client.zzb.m1445v("There are still references to the engine. Not destroying.");
                }
            }
        }
    }

    public zzdv(Context context, VersionInfoParcel versionInfoParcel, String str) {
        this.zzpc = new Object();
        this.zzxJ = 1;
        this.zzxF = str;
        this.mContext = context.getApplicationContext();
        this.zzpa = versionInfoParcel;
        this.zzxG = new zzc();
        this.zzxH = new zzc();
    }

    public zzdv(Context context, VersionInfoParcel versionInfoParcel, String str, zzb<zzbb> com_google_android_gms_internal_zzdv_zzb_com_google_android_gms_internal_zzbb, zzb<zzbb> com_google_android_gms_internal_zzdv_zzb_com_google_android_gms_internal_zzbb2) {
        this(context, versionInfoParcel, str);
        this.zzxG = com_google_android_gms_internal_zzdv_zzb_com_google_android_gms_internal_zzbb;
        this.zzxH = com_google_android_gms_internal_zzdv_zzb_com_google_android_gms_internal_zzbb2;
    }

    private zze zzdJ() {
        zze com_google_android_gms_internal_zzdv_zze = new zze(this.zzxH);
        zzhu.runOnUiThread(new C08991(this, com_google_android_gms_internal_zzdv_zze));
        return com_google_android_gms_internal_zzdv_zze;
    }

    protected zzbb zza(Context context, VersionInfoParcel versionInfoParcel) {
        return new zzbd(context, versionInfoParcel, null);
    }

    protected zze zzdK() {
        zze zzdJ = zzdJ();
        zzdJ.zza(new C09002(this, zzdJ), new C09013(this, zzdJ));
        return zzdJ;
    }

    public zzd zzdL() {
        zzd zzdM;
        synchronized (this.zzpc) {
            if (this.zzxI == null || this.zzxI.getStatus() == -1) {
                this.zzxJ = 2;
                this.zzxI = zzdK();
                zzdM = this.zzxI.zzdM();
            } else if (this.zzxJ == 0) {
                zzdM = this.zzxI.zzdM();
            } else if (this.zzxJ == 1) {
                this.zzxJ = 2;
                zzdK();
                zzdM = this.zzxI.zzdM();
            } else if (this.zzxJ == 2) {
                zzdM = this.zzxI.zzdM();
            } else {
                zzdM = this.zzxI.zzdM();
            }
        }
        return zzdM;
    }
}
