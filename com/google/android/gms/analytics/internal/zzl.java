package com.google.android.gms.analytics.internal;

import android.content.Context;
import android.database.sqlite.SQLiteException;
import android.text.TextUtils;
import android.util.Pair;
import com.google.android.gms.analytics.AnalyticsReceiver;
import com.google.android.gms.analytics.AnalyticsService;
import com.google.android.gms.analytics.CampaignTrackingReceiver;
import com.google.android.gms.analytics.CampaignTrackingService;
import com.google.android.gms.analytics.zza;
import com.google.android.gms.common.internal.zzx;
import com.google.android.gms.internal.zzja;
import com.google.android.gms.internal.zzjb;
import com.google.android.gms.internal.zzob;
import com.google.android.gms.internal.zzof;
import com.google.android.gms.internal.zzok;
import com.google.android.gms.internal.zzol;
import com.sygic.aura.poi.fragment.PoiFragment;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

class zzl extends zzd {
    private boolean mStarted;
    private final zzj zzMn;
    private final zzah zzMo;
    private final zzag zzMp;
    private final zzi zzMq;
    private long zzMr;
    private final zzt zzMs;
    private final zzt zzMt;
    private final zzaj zzMu;
    private long zzMv;
    private boolean zzMw;

    /* renamed from: com.google.android.gms.analytics.internal.zzl.1 */
    class C06251 extends zzt {
        final /* synthetic */ zzl zzMx;

        C06251(zzl com_google_android_gms_analytics_internal_zzl, zzf com_google_android_gms_analytics_internal_zzf) {
            this.zzMx = com_google_android_gms_analytics_internal_zzl;
            super(com_google_android_gms_analytics_internal_zzf);
        }

        public void run() {
            this.zzMx.zziO();
        }
    }

    /* renamed from: com.google.android.gms.analytics.internal.zzl.2 */
    class C06262 extends zzt {
        final /* synthetic */ zzl zzMx;

        C06262(zzl com_google_android_gms_analytics_internal_zzl, zzf com_google_android_gms_analytics_internal_zzf) {
            this.zzMx = com_google_android_gms_analytics_internal_zzl;
            super(com_google_android_gms_analytics_internal_zzf);
        }

        public void run() {
            this.zzMx.zziP();
        }
    }

    /* renamed from: com.google.android.gms.analytics.internal.zzl.3 */
    class C06273 implements Runnable {
        final /* synthetic */ zzl zzMx;

        C06273(zzl com_google_android_gms_analytics_internal_zzl) {
            this.zzMx = com_google_android_gms_analytics_internal_zzl;
        }

        public void run() {
            this.zzMx.zziN();
        }
    }

    /* renamed from: com.google.android.gms.analytics.internal.zzl.4 */
    class C06284 implements zzw {
        final /* synthetic */ zzl zzMx;

        C06284(zzl com_google_android_gms_analytics_internal_zzl) {
            this.zzMx = com_google_android_gms_analytics_internal_zzl;
        }

        public void zzc(Throwable th) {
            this.zzMx.zziU();
        }
    }

    /* renamed from: com.google.android.gms.analytics.internal.zzl.5 */
    class C06295 implements Runnable {
        final /* synthetic */ zzw zzLx;
        final /* synthetic */ zzl zzMx;
        final /* synthetic */ long zzMy;

        C06295(zzl com_google_android_gms_analytics_internal_zzl, zzw com_google_android_gms_analytics_internal_zzw, long j) {
            this.zzMx = com_google_android_gms_analytics_internal_zzl;
            this.zzLx = com_google_android_gms_analytics_internal_zzw;
            this.zzMy = j;
        }

        public void run() {
            this.zzMx.zza(this.zzLx, this.zzMy);
        }
    }

    protected zzl(zzf com_google_android_gms_analytics_internal_zzf, zzg com_google_android_gms_analytics_internal_zzg) {
        super(com_google_android_gms_analytics_internal_zzf);
        zzx.zzv(com_google_android_gms_analytics_internal_zzg);
        this.zzMr = Long.MIN_VALUE;
        this.zzMp = com_google_android_gms_analytics_internal_zzg.zzk(com_google_android_gms_analytics_internal_zzf);
        this.zzMn = com_google_android_gms_analytics_internal_zzg.zzm(com_google_android_gms_analytics_internal_zzf);
        this.zzMo = com_google_android_gms_analytics_internal_zzg.zzn(com_google_android_gms_analytics_internal_zzf);
        this.zzMq = com_google_android_gms_analytics_internal_zzg.zzo(com_google_android_gms_analytics_internal_zzf);
        this.zzMu = new zzaj(zzid());
        this.zzMs = new C06251(this, com_google_android_gms_analytics_internal_zzf);
        this.zzMt = new C06262(this, com_google_android_gms_analytics_internal_zzf);
    }

    private void zza(zzh com_google_android_gms_analytics_internal_zzh, zzol com_google_android_gms_internal_zzol) {
        zzx.zzv(com_google_android_gms_analytics_internal_zzh);
        zzx.zzv(com_google_android_gms_internal_zzol);
        zza com_google_android_gms_analytics_zza = new zza(zzia());
        com_google_android_gms_analytics_zza.zzaN(com_google_android_gms_analytics_internal_zzh.zzix());
        com_google_android_gms_analytics_zza.enableAdvertisingIdCollection(com_google_android_gms_analytics_internal_zzh.zziy());
        zzob zzhq = com_google_android_gms_analytics_zza.zzhq();
        zzjb com_google_android_gms_internal_zzjb = (zzjb) zzhq.zze(zzjb.class);
        com_google_android_gms_internal_zzjb.zzaS(PoiFragment.ARG_DATA);
        com_google_android_gms_internal_zzjb.zzH(true);
        zzhq.zzb(com_google_android_gms_internal_zzol);
        zzja com_google_android_gms_internal_zzja = (zzja) zzhq.zze(zzja.class);
        zzok com_google_android_gms_internal_zzok = (zzok) zzhq.zze(zzok.class);
        for (Entry entry : com_google_android_gms_analytics_internal_zzh.zzn().entrySet()) {
            String str = (String) entry.getKey();
            String str2 = (String) entry.getValue();
            if ("an".equals(str)) {
                com_google_android_gms_internal_zzok.setAppName(str2);
            } else if ("av".equals(str)) {
                com_google_android_gms_internal_zzok.setAppVersion(str2);
            } else if ("aid".equals(str)) {
                com_google_android_gms_internal_zzok.setAppId(str2);
            } else if ("aiid".equals(str)) {
                com_google_android_gms_internal_zzok.setAppInstallerId(str2);
            } else if ("uid".equals(str)) {
                com_google_android_gms_internal_zzjb.setUserId(str2);
            } else {
                com_google_android_gms_internal_zzja.set(str, str2);
            }
        }
        zzb("Sending installation campaign to", com_google_android_gms_analytics_internal_zzh.zzix(), com_google_android_gms_internal_zzol);
        zzhq.zzL(zzii().zzky());
        zzhq.zzxl();
    }

    private boolean zzbf(String str) {
        return getContext().checkCallingOrSelfPermission(str) == 0;
    }

    private void zziM() {
        Context context = zzia().getContext();
        if (!AnalyticsReceiver.zzV(context)) {
            zzbb("AnalyticsReceiver is not registered or is disabled. Register the receiver for reliable dispatching on non-Google Play devices. See http://goo.gl/8Rd3yj for instructions.");
        } else if (!AnalyticsService.zzW(context)) {
            zzbc("AnalyticsService is not registered or is disabled. Analytics service at risk of not starting. See http://goo.gl/8Rd3yj for instructions.");
        }
        if (!CampaignTrackingReceiver.zzV(context)) {
            zzbb("CampaignTrackingReceiver is not registered, not exported or is disabled. Installation campaign tracking is not possible. See http://goo.gl/8Rd3yj for instructions.");
        } else if (!CampaignTrackingService.zzW(context)) {
            zzbb("CampaignTrackingService is not registered or is disabled. Installation campaign tracking is not possible. See http://goo.gl/8Rd3yj for instructions.");
        }
    }

    private void zziO() {
        zzb(new C06284(this));
    }

    private void zziP() {
        try {
            this.zzMn.zziG();
            zziU();
        } catch (SQLiteException e) {
            zzd("Failed to delete stale hits", e);
        }
        this.zzMt.zzt(zzif().zzjM());
    }

    private boolean zziV() {
        return this.zzMw ? false : (!zzif().zzjk() || zzif().zzjl()) && zzjb() > 0;
    }

    private void zziW() {
        zzv zzih = zzih();
        if (zzih.zzjU() && !zzih.zzbr()) {
            long zziH = zziH();
            if (zziH != 0 && Math.abs(zzid().currentTimeMillis() - zziH) <= zzif().zzju()) {
                zza("Dispatch alarm scheduled (ms)", Long.valueOf(zzif().zzjt()));
                zzih.zzjV();
            }
        }
    }

    private void zziX() {
        zziW();
        long zzjb = zzjb();
        long zzkA = zzii().zzkA();
        if (zzkA != 0) {
            zzkA = zzjb - Math.abs(zzid().currentTimeMillis() - zzkA);
            if (zzkA <= 0) {
                zzkA = Math.min(zzif().zzjr(), zzjb);
            }
        } else {
            zzkA = Math.min(zzif().zzjr(), zzjb);
        }
        zza("Dispatch scheduled (ms)", Long.valueOf(zzkA));
        if (this.zzMs.zzbr()) {
            this.zzMs.zzu(Math.max(1, zzkA + this.zzMs.zzjR()));
            return;
        }
        this.zzMs.zzt(zzkA);
    }

    private void zziY() {
        zziZ();
        zzja();
    }

    private void zziZ() {
        if (this.zzMs.zzbr()) {
            zzaY("All hits dispatched or no network/service. Going to power save mode");
        }
        this.zzMs.cancel();
    }

    private void zzja() {
        zzv zzih = zzih();
        if (zzih.zzbr()) {
            zzih.cancel();
        }
    }

    protected void onServiceConnected() {
        zzic();
        if (!zzif().zzjk()) {
            zziR();
        }
    }

    void start() {
        zzio();
        zzx.zza(!this.mStarted, "Analytics backend already started");
        this.mStarted = true;
        if (!zzif().zzjk()) {
            zziM();
        }
        zzig().zzf(new C06273(this));
    }

    public void zzI(boolean z) {
        zziU();
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public long zza(com.google.android.gms.analytics.internal.zzh r6, boolean r7) {
        /*
        r5 = this;
        com.google.android.gms.common.internal.zzx.zzv(r6);
        r5.zzio();
        r5.zzic();
        r0 = r5.zzMn;	 Catch:{ SQLiteException -> 0x0049 }
        r0.beginTransaction();	 Catch:{ SQLiteException -> 0x0049 }
        r0 = r5.zzMn;	 Catch:{ SQLiteException -> 0x0049 }
        r2 = r6.zziw();	 Catch:{ SQLiteException -> 0x0049 }
        r1 = r6.getClientId();	 Catch:{ SQLiteException -> 0x0049 }
        r0.zza(r2, r1);	 Catch:{ SQLiteException -> 0x0049 }
        r0 = r5.zzMn;	 Catch:{ SQLiteException -> 0x0049 }
        r2 = r6.zziw();	 Catch:{ SQLiteException -> 0x0049 }
        r1 = r6.getClientId();	 Catch:{ SQLiteException -> 0x0049 }
        r4 = r6.zzix();	 Catch:{ SQLiteException -> 0x0049 }
        r0 = r0.zza(r2, r1, r4);	 Catch:{ SQLiteException -> 0x0049 }
        if (r7 != 0) goto L_0x0042;
    L_0x002f:
        r6.zzn(r0);	 Catch:{ SQLiteException -> 0x0049 }
    L_0x0032:
        r2 = r5.zzMn;	 Catch:{ SQLiteException -> 0x0049 }
        r2.zzb(r6);	 Catch:{ SQLiteException -> 0x0049 }
        r2 = r5.zzMn;	 Catch:{ SQLiteException -> 0x0049 }
        r2.setTransactionSuccessful();	 Catch:{ SQLiteException -> 0x0049 }
        r2 = r5.zzMn;	 Catch:{ SQLiteException -> 0x0060 }
        r2.endTransaction();	 Catch:{ SQLiteException -> 0x0060 }
    L_0x0041:
        return r0;
    L_0x0042:
        r2 = 1;
        r2 = r2 + r0;
        r6.zzn(r2);	 Catch:{ SQLiteException -> 0x0049 }
        goto L_0x0032;
    L_0x0049:
        r0 = move-exception;
        r1 = "Failed to update Analytics property";
        r5.zze(r1, r0);	 Catch:{ all -> 0x0068 }
        r0 = -1;
        r2 = r5.zzMn;	 Catch:{ SQLiteException -> 0x0058 }
        r2.endTransaction();	 Catch:{ SQLiteException -> 0x0058 }
        goto L_0x0041;
    L_0x0058:
        r2 = move-exception;
        r3 = "Failed to end transaction";
        r5.zze(r3, r2);
        goto L_0x0041;
    L_0x0060:
        r2 = move-exception;
        r3 = "Failed to end transaction";
        r5.zze(r3, r2);
        goto L_0x0041;
    L_0x0068:
        r0 = move-exception;
        r1 = r5.zzMn;	 Catch:{ SQLiteException -> 0x006f }
        r1.endTransaction();	 Catch:{ SQLiteException -> 0x006f }
    L_0x006e:
        throw r0;
    L_0x006f:
        r1 = move-exception;
        r2 = "Failed to end transaction";
        r5.zze(r2, r1);
        goto L_0x006e;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.analytics.internal.zzl.zza(com.google.android.gms.analytics.internal.zzh, boolean):long");
    }

    public void zza(zzab com_google_android_gms_analytics_internal_zzab) {
        zzx.zzv(com_google_android_gms_analytics_internal_zzab);
        zzof.zzic();
        zzio();
        if (this.zzMw) {
            zzaZ("Hit delivery not possible. Missing network permissions. See http://goo.gl/8Rd3yj for instructions");
        } else {
            zza("Delivering hit", com_google_android_gms_analytics_internal_zzab);
        }
        zzab zzf = zzf(com_google_android_gms_analytics_internal_zzab);
        zziQ();
        if (this.zzMq.zzb(zzf)) {
            zzaZ("Hit sent to the device AnalyticsService for delivery");
        } else if (zzif().zzjk()) {
            zzie().zza(zzf, "Service unavailable on package side");
        } else {
            try {
                this.zzMn.zzc(zzf);
                zziU();
            } catch (SQLiteException e) {
                zze("Delivery failed to save hit to a database", e);
                zzie().zza(zzf, "deliver: failed to insert hit to database");
            }
        }
    }

    public void zza(zzw com_google_android_gms_analytics_internal_zzw, long j) {
        zzof.zzic();
        zzio();
        long j2 = -1;
        long zzkA = zzii().zzkA();
        if (zzkA != 0) {
            j2 = Math.abs(zzid().currentTimeMillis() - zzkA);
        }
        zzb("Dispatching local hits. Elapsed time since last dispatch (ms)", Long.valueOf(j2));
        if (!zzif().zzjk()) {
            zziQ();
        }
        try {
            if (zziS()) {
                zzig().zzf(new C06295(this, com_google_android_gms_analytics_internal_zzw, j));
                return;
            }
            zzii().zzkB();
            zziU();
            if (com_google_android_gms_analytics_internal_zzw != null) {
                com_google_android_gms_analytics_internal_zzw.zzc(null);
            }
            if (this.zzMv != j) {
                this.zzMp.zzkt();
            }
        } catch (Throwable th) {
            zze("Local dispatch failed", th);
            zzii().zzkB();
            zziU();
            if (com_google_android_gms_analytics_internal_zzw != null) {
                com_google_android_gms_analytics_internal_zzw.zzc(th);
            }
        }
    }

    public void zzb(zzw com_google_android_gms_analytics_internal_zzw) {
        zza(com_google_android_gms_analytics_internal_zzw, this.zzMv);
    }

    public void zzbg(String str) {
        zzx.zzcs(str);
        zzic();
        zzib();
        zzol zza = zzam.zza(zzie(), str);
        if (zza == null) {
            zzd("Parsing failed. Ignoring invalid campaign data", str);
            return;
        }
        CharSequence zzkC = zzii().zzkC();
        if (str.equals(zzkC)) {
            zzbb("Ignoring duplicate install campaign");
        } else if (TextUtils.isEmpty(zzkC)) {
            zzii().zzbk(str);
            if (zzii().zzkz().zzv(zzif().zzjP())) {
                zzd("Campaign received too late, ignoring", zza);
                return;
            }
            zzb("Received installation campaign", zza);
            for (zzh zza2 : this.zzMn.zzr(0)) {
                zza(zza2, zza);
            }
        } else {
            zzd("Ignoring multiple install campaigns. original, new", zzkC, str);
        }
    }

    protected void zzc(zzh com_google_android_gms_analytics_internal_zzh) {
        zzic();
        zzb("Sending first hit to property", com_google_android_gms_analytics_internal_zzh.zzix());
        if (!zzii().zzkz().zzv(zzif().zzjP())) {
            String zzkC = zzii().zzkC();
            if (!TextUtils.isEmpty(zzkC)) {
                zzol zza = zzam.zza(zzie(), zzkC);
                zzb("Found relevant installation campaign", zza);
                zza(com_google_android_gms_analytics_internal_zzh, zza);
            }
        }
    }

    zzab zzf(zzab com_google_android_gms_analytics_internal_zzab) {
        if (!TextUtils.isEmpty(com_google_android_gms_analytics_internal_zzab.zzko())) {
            return com_google_android_gms_analytics_internal_zzab;
        }
        Pair zzkG = zzii().zzkD().zzkG();
        if (zzkG == null) {
            return com_google_android_gms_analytics_internal_zzab;
        }
        Long l = (Long) zzkG.second;
        String str = l + ":" + ((String) zzkG.first);
        Map hashMap = new HashMap(com_google_android_gms_analytics_internal_zzab.zzn());
        hashMap.put("_m", str);
        return zzab.zza(this, com_google_android_gms_analytics_internal_zzab, hashMap);
    }

    protected void zzhB() {
        this.zzMn.zza();
        this.zzMo.zza();
        this.zzMq.zza();
    }

    public void zzhX() {
        zzof.zzic();
        zzio();
        zzaY("Service disconnected");
    }

    void zzhZ() {
        zzic();
        this.zzMv = zzid().currentTimeMillis();
    }

    public long zziH() {
        zzof.zzic();
        zzio();
        try {
            return this.zzMn.zziH();
        } catch (SQLiteException e) {
            zze("Failed to get min/max hit times from local store", e);
            return 0;
        }
    }

    protected void zziN() {
        zzio();
        zzii().zzky();
        if (!zzbf("android.permission.ACCESS_NETWORK_STATE")) {
            zzbc("Missing required android.permission.ACCESS_NETWORK_STATE. Google Analytics disabled. See http://goo.gl/8Rd3yj for instructions");
            zzjc();
        }
        if (!zzbf("android.permission.INTERNET")) {
            zzbc("Missing required android.permission.INTERNET. Google Analytics disabled. See http://goo.gl/8Rd3yj for instructions");
            zzjc();
        }
        if (AnalyticsService.zzW(getContext())) {
            zzaY("AnalyticsService registered in the app manifest and enabled");
        } else if (zzif().zzjk()) {
            zzbc("Device AnalyticsService not registered! Hits will not be delivered reliably.");
        } else {
            zzbb("AnalyticsService not registered in the app manifest. Hits might not be delivered reliably. See http://goo.gl/8Rd3yj for instructions.");
        }
        if (!(this.zzMw || zzif().zzjk() || this.zzMn.isEmpty())) {
            zziQ();
        }
        zziU();
    }

    protected void zziQ() {
        if (!this.zzMw && zzif().zzjm() && !this.zzMq.isConnected()) {
            if (this.zzMu.zzv(zzif().zzjH())) {
                this.zzMu.start();
                zzaY("Connecting to service");
                if (this.zzMq.connect()) {
                    zzaY("Connected to service");
                    this.zzMu.clear();
                    onServiceConnected();
                }
            }
        }
    }

    public void zziR() {
        zzof.zzic();
        zzio();
        zzib();
        if (!zzif().zzjm()) {
            zzbb("Service client disabled. Can't dispatch local hits to device AnalyticsService");
        }
        if (!this.zzMq.isConnected()) {
            zzaY("Service not connected");
        } else if (!this.zzMn.isEmpty()) {
            zzaY("Dispatching local hits to device AnalyticsService");
            while (true) {
                try {
                    break;
                    List zzp = this.zzMn.zzp((long) zzif().zzjv());
                    if (zzp.isEmpty()) {
                        zzab com_google_android_gms_analytics_internal_zzab;
                        while (true) {
                            if (!zzp.isEmpty()) {
                                com_google_android_gms_analytics_internal_zzab = (zzab) zzp.get(0);
                                if (this.zzMq.zzb(com_google_android_gms_analytics_internal_zzab)) {
                                    zziU();
                                    return;
                                }
                                zzp.remove(com_google_android_gms_analytics_internal_zzab);
                                try {
                                    this.zzMn.zzq(com_google_android_gms_analytics_internal_zzab.zzkj());
                                } catch (SQLiteException e) {
                                    zze("Failed to remove hit that was send for delivery", e);
                                    zziY();
                                    return;
                                }
                            }
                        }
                        List zzp2 = this.zzMn.zzp((long) zzif().zzjv());
                        if (zzp2.isEmpty()) {
                            while (true) {
                                if (zzp2.isEmpty()) {
                                    com_google_android_gms_analytics_internal_zzab = (zzab) zzp2.get(0);
                                    if (this.zzMq.zzb(com_google_android_gms_analytics_internal_zzab)) {
                                        zzp2.remove(com_google_android_gms_analytics_internal_zzab);
                                        this.zzMn.zzq(com_google_android_gms_analytics_internal_zzab.zzkj());
                                    } else {
                                        zziU();
                                        return;
                                    }
                                }
                            }
                        }
                        zziU();
                        return;
                    }
                    zziU();
                    return;
                } catch (SQLiteException e2) {
                    zze("Failed to read hits from store", e2);
                    zziY();
                    return;
                }
            }
        }
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    protected boolean zziS() {
        /*
        r12 = this;
        r1 = 1;
        r2 = 0;
        com.google.android.gms.internal.zzof.zzic();
        r12.zzio();
        r0 = "Dispatching a batch of local hits";
        r12.zzaY(r0);
        r0 = r12.zzMq;
        r0 = r0.isConnected();
        if (r0 != 0) goto L_0x0034;
    L_0x0016:
        r0 = r12.zzif();
        r0 = r0.zzjk();
        if (r0 != 0) goto L_0x0034;
    L_0x0020:
        r0 = r1;
    L_0x0021:
        r3 = r12.zzMo;
        r3 = r3.zzku();
        if (r3 != 0) goto L_0x0036;
    L_0x0029:
        if (r0 == 0) goto L_0x0038;
    L_0x002b:
        if (r1 == 0) goto L_0x0038;
    L_0x002d:
        r0 = "No network or service available. Will retry later";
        r12.zzaY(r0);
    L_0x0033:
        return r2;
    L_0x0034:
        r0 = r2;
        goto L_0x0021;
    L_0x0036:
        r1 = r2;
        goto L_0x0029;
    L_0x0038:
        r0 = r12.zzif();
        r0 = r0.zzjv();
        r1 = r12.zzif();
        r1 = r1.zzjw();
        r0 = java.lang.Math.max(r0, r1);
        r6 = (long) r0;
        r3 = new java.util.ArrayList;
        r3.<init>();
        r4 = 0;
    L_0x0054:
        r0 = r12.zzMn;	 Catch:{ all -> 0x01ff }
        r0.beginTransaction();	 Catch:{ all -> 0x01ff }
        r3.clear();	 Catch:{ all -> 0x01ff }
        r0 = r12.zzMn;	 Catch:{ SQLiteException -> 0x00da }
        r8 = r0.zzp(r6);	 Catch:{ SQLiteException -> 0x00da }
        r0 = r8.isEmpty();	 Catch:{ SQLiteException -> 0x00da }
        if (r0 == 0) goto L_0x0087;
    L_0x0068:
        r0 = "Store is empty, nothing to dispatch";
        r12.zzaY(r0);	 Catch:{ SQLiteException -> 0x00da }
        r12.zziY();	 Catch:{ SQLiteException -> 0x00da }
        r0 = r12.zzMn;	 Catch:{ SQLiteException -> 0x007c }
        r0.setTransactionSuccessful();	 Catch:{ SQLiteException -> 0x007c }
        r0 = r12.zzMn;	 Catch:{ SQLiteException -> 0x007c }
        r0.endTransaction();	 Catch:{ SQLiteException -> 0x007c }
        goto L_0x0033;
    L_0x007c:
        r0 = move-exception;
        r1 = "Failed to commit local dispatch transaction";
        r12.zze(r1, r0);
        r12.zziY();
        goto L_0x0033;
    L_0x0087:
        r0 = "Hits loaded from store. count";
        r1 = r8.size();	 Catch:{ SQLiteException -> 0x00da }
        r1 = java.lang.Integer.valueOf(r1);	 Catch:{ SQLiteException -> 0x00da }
        r12.zza(r0, r1);	 Catch:{ SQLiteException -> 0x00da }
        r1 = r8.iterator();	 Catch:{ all -> 0x01ff }
    L_0x0099:
        r0 = r1.hasNext();	 Catch:{ all -> 0x01ff }
        if (r0 == 0) goto L_0x00fc;
    L_0x009f:
        r0 = r1.next();	 Catch:{ all -> 0x01ff }
        r0 = (com.google.android.gms.analytics.internal.zzab) r0;	 Catch:{ all -> 0x01ff }
        r10 = r0.zzkj();	 Catch:{ all -> 0x01ff }
        r0 = (r10 > r4 ? 1 : (r10 == r4 ? 0 : -1));
        if (r0 != 0) goto L_0x0099;
    L_0x00ad:
        r0 = "Database contains successfully uploaded hit";
        r1 = java.lang.Long.valueOf(r4);	 Catch:{ all -> 0x01ff }
        r3 = r8.size();	 Catch:{ all -> 0x01ff }
        r3 = java.lang.Integer.valueOf(r3);	 Catch:{ all -> 0x01ff }
        r12.zzd(r0, r1, r3);	 Catch:{ all -> 0x01ff }
        r12.zziY();	 Catch:{ all -> 0x01ff }
        r0 = r12.zzMn;	 Catch:{ SQLiteException -> 0x00ce }
        r0.setTransactionSuccessful();	 Catch:{ SQLiteException -> 0x00ce }
        r0 = r12.zzMn;	 Catch:{ SQLiteException -> 0x00ce }
        r0.endTransaction();	 Catch:{ SQLiteException -> 0x00ce }
        goto L_0x0033;
    L_0x00ce:
        r0 = move-exception;
        r1 = "Failed to commit local dispatch transaction";
        r12.zze(r1, r0);
        r12.zziY();
        goto L_0x0033;
    L_0x00da:
        r0 = move-exception;
        r1 = "Failed to read hits from persisted store";
        r12.zzd(r1, r0);	 Catch:{ all -> 0x01ff }
        r12.zziY();	 Catch:{ all -> 0x01ff }
        r0 = r12.zzMn;	 Catch:{ SQLiteException -> 0x00f0 }
        r0.setTransactionSuccessful();	 Catch:{ SQLiteException -> 0x00f0 }
        r0 = r12.zzMn;	 Catch:{ SQLiteException -> 0x00f0 }
        r0.endTransaction();	 Catch:{ SQLiteException -> 0x00f0 }
        goto L_0x0033;
    L_0x00f0:
        r0 = move-exception;
        r1 = "Failed to commit local dispatch transaction";
        r12.zze(r1, r0);
        r12.zziY();
        goto L_0x0033;
    L_0x00fc:
        r0 = r12.zzMq;	 Catch:{ all -> 0x01ff }
        r0 = r0.isConnected();	 Catch:{ all -> 0x01ff }
        if (r0 == 0) goto L_0x0217;
    L_0x0104:
        r0 = r12.zzif();	 Catch:{ all -> 0x01ff }
        r0 = r0.zzjk();	 Catch:{ all -> 0x01ff }
        if (r0 != 0) goto L_0x0217;
    L_0x010e:
        r0 = "Service connected, sending hits to the service";
        r12.zzaY(r0);	 Catch:{ all -> 0x01ff }
    L_0x0114:
        r0 = r8.isEmpty();	 Catch:{ all -> 0x01ff }
        if (r0 != 0) goto L_0x0217;
    L_0x011a:
        r0 = 0;
        r0 = r8.get(r0);	 Catch:{ all -> 0x01ff }
        r0 = (com.google.android.gms.analytics.internal.zzab) r0;	 Catch:{ all -> 0x01ff }
        r1 = r12.zzMq;	 Catch:{ all -> 0x01ff }
        r1 = r1.zzb(r0);	 Catch:{ all -> 0x01ff }
        if (r1 != 0) goto L_0x0152;
    L_0x0129:
        r0 = r4;
    L_0x012a:
        r4 = r12.zzMo;	 Catch:{ all -> 0x01ff }
        r4 = r4.zzku();	 Catch:{ all -> 0x01ff }
        if (r4 == 0) goto L_0x01a6;
    L_0x0132:
        r4 = r12.zzMo;	 Catch:{ all -> 0x01ff }
        r9 = r4.zzf(r8);	 Catch:{ all -> 0x01ff }
        r10 = r9.iterator();	 Catch:{ all -> 0x01ff }
        r4 = r0;
    L_0x013d:
        r0 = r10.hasNext();	 Catch:{ all -> 0x01ff }
        if (r0 == 0) goto L_0x019a;
    L_0x0143:
        r0 = r10.next();	 Catch:{ all -> 0x01ff }
        r0 = (java.lang.Long) r0;	 Catch:{ all -> 0x01ff }
        r0 = r0.longValue();	 Catch:{ all -> 0x01ff }
        r4 = java.lang.Math.max(r4, r0);	 Catch:{ all -> 0x01ff }
        goto L_0x013d;
    L_0x0152:
        r10 = r0.zzkj();	 Catch:{ all -> 0x01ff }
        r4 = java.lang.Math.max(r4, r10);	 Catch:{ all -> 0x01ff }
        r8.remove(r0);	 Catch:{ all -> 0x01ff }
        r1 = "Hit sent do device AnalyticsService for delivery";
        r12.zzb(r1, r0);	 Catch:{ all -> 0x01ff }
        r1 = r12.zzMn;	 Catch:{ SQLiteException -> 0x0178 }
        r10 = r0.zzkj();	 Catch:{ SQLiteException -> 0x0178 }
        r1.zzq(r10);	 Catch:{ SQLiteException -> 0x0178 }
        r0 = r0.zzkj();	 Catch:{ SQLiteException -> 0x0178 }
        r0 = java.lang.Long.valueOf(r0);	 Catch:{ SQLiteException -> 0x0178 }
        r3.add(r0);	 Catch:{ SQLiteException -> 0x0178 }
        goto L_0x0114;
    L_0x0178:
        r0 = move-exception;
        r1 = "Failed to remove hit that was send for delivery";
        r12.zze(r1, r0);	 Catch:{ all -> 0x01ff }
        r12.zziY();	 Catch:{ all -> 0x01ff }
        r0 = r12.zzMn;	 Catch:{ SQLiteException -> 0x018e }
        r0.setTransactionSuccessful();	 Catch:{ SQLiteException -> 0x018e }
        r0 = r12.zzMn;	 Catch:{ SQLiteException -> 0x018e }
        r0.endTransaction();	 Catch:{ SQLiteException -> 0x018e }
        goto L_0x0033;
    L_0x018e:
        r0 = move-exception;
        r1 = "Failed to commit local dispatch transaction";
        r12.zze(r1, r0);
        r12.zziY();
        goto L_0x0033;
    L_0x019a:
        r8.removeAll(r9);	 Catch:{ all -> 0x01ff }
        r0 = r12.zzMn;	 Catch:{ SQLiteException -> 0x01c4 }
        r0.zzd(r9);	 Catch:{ SQLiteException -> 0x01c4 }
        r3.addAll(r9);	 Catch:{ SQLiteException -> 0x01c4 }
        r0 = r4;
    L_0x01a6:
        r4 = r3.isEmpty();	 Catch:{ all -> 0x01ff }
        if (r4 == 0) goto L_0x01e6;
    L_0x01ac:
        r0 = r12.zzMn;	 Catch:{ SQLiteException -> 0x01b8 }
        r0.setTransactionSuccessful();	 Catch:{ SQLiteException -> 0x01b8 }
        r0 = r12.zzMn;	 Catch:{ SQLiteException -> 0x01b8 }
        r0.endTransaction();	 Catch:{ SQLiteException -> 0x01b8 }
        goto L_0x0033;
    L_0x01b8:
        r0 = move-exception;
        r1 = "Failed to commit local dispatch transaction";
        r12.zze(r1, r0);
        r12.zziY();
        goto L_0x0033;
    L_0x01c4:
        r0 = move-exception;
        r1 = "Failed to remove successfully uploaded hits";
        r12.zze(r1, r0);	 Catch:{ all -> 0x01ff }
        r12.zziY();	 Catch:{ all -> 0x01ff }
        r0 = r12.zzMn;	 Catch:{ SQLiteException -> 0x01da }
        r0.setTransactionSuccessful();	 Catch:{ SQLiteException -> 0x01da }
        r0 = r12.zzMn;	 Catch:{ SQLiteException -> 0x01da }
        r0.endTransaction();	 Catch:{ SQLiteException -> 0x01da }
        goto L_0x0033;
    L_0x01da:
        r0 = move-exception;
        r1 = "Failed to commit local dispatch transaction";
        r12.zze(r1, r0);
        r12.zziY();
        goto L_0x0033;
    L_0x01e6:
        r4 = r12.zzMn;	 Catch:{ SQLiteException -> 0x01f3 }
        r4.setTransactionSuccessful();	 Catch:{ SQLiteException -> 0x01f3 }
        r4 = r12.zzMn;	 Catch:{ SQLiteException -> 0x01f3 }
        r4.endTransaction();	 Catch:{ SQLiteException -> 0x01f3 }
        r4 = r0;
        goto L_0x0054;
    L_0x01f3:
        r0 = move-exception;
        r1 = "Failed to commit local dispatch transaction";
        r12.zze(r1, r0);
        r12.zziY();
        goto L_0x0033;
    L_0x01ff:
        r0 = move-exception;
        r1 = r12.zzMn;	 Catch:{ SQLiteException -> 0x020b }
        r1.setTransactionSuccessful();	 Catch:{ SQLiteException -> 0x020b }
        r1 = r12.zzMn;	 Catch:{ SQLiteException -> 0x020b }
        r1.endTransaction();	 Catch:{ SQLiteException -> 0x020b }
        throw r0;
    L_0x020b:
        r0 = move-exception;
        r1 = "Failed to commit local dispatch transaction";
        r12.zze(r1, r0);
        r12.zziY();
        goto L_0x0033;
    L_0x0217:
        r0 = r4;
        goto L_0x012a;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.analytics.internal.zzl.zziS():boolean");
    }

    public void zziT() {
        zzof.zzic();
        zzio();
        zzaZ("Sync dispatching local hits");
        long j = this.zzMv;
        if (!zzif().zzjk()) {
            zziQ();
        }
        do {
            try {
            } catch (Throwable th) {
                zze("Sync local dispatch failed", th);
                zziU();
                return;
            }
        } while (zziS());
        zzii().zzkB();
        zziU();
        if (this.zzMv != j) {
            this.zzMp.zzkt();
        }
    }

    public void zziU() {
        zzia().zzic();
        zzio();
        if (!zziV()) {
            this.zzMp.unregister();
            zziY();
        } else if (this.zzMn.isEmpty()) {
            this.zzMp.unregister();
            zziY();
        } else {
            boolean z;
            if (((Boolean) zzy.zzNH.get()).booleanValue()) {
                z = true;
            } else {
                this.zzMp.zzkr();
                z = this.zzMp.isConnected();
            }
            if (z) {
                zziX();
                return;
            }
            zziY();
            zziW();
        }
    }

    public long zzjb() {
        if (this.zzMr != Long.MIN_VALUE) {
            return this.zzMr;
        }
        return zzhA().zzke() ? ((long) zzhA().zzkV()) * 1000 : zzif().zzjs();
    }

    public void zzjc() {
        zzio();
        zzic();
        this.zzMw = true;
        this.zzMq.disconnect();
        zziU();
    }
}
