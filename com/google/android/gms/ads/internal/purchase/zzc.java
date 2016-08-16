package com.google.android.gms.ads.internal.purchase;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;
import android.os.SystemClock;
import com.google.android.gms.ads.internal.util.client.zzb;
import com.google.android.gms.ads.internal.zzp;
import com.google.android.gms.internal.zzfq;
import com.google.android.gms.internal.zzgk;
import com.google.android.gms.internal.zzhq;
import com.google.android.gms.internal.zzhu;
import java.util.List;

@zzgk
public class zzc extends zzhq implements ServiceConnection {
    private Context mContext;
    private boolean zzBO;
    private zzfq zzBP;
    private zzb zzBQ;
    private zzh zzBR;
    private List<zzf> zzBS;
    private zzk zzBT;
    private final Object zzpc;

    /* renamed from: com.google.android.gms.ads.internal.purchase.zzc.1 */
    class C05861 implements Runnable {
        final /* synthetic */ Intent val$intent;
        final /* synthetic */ zzf zzBU;
        final /* synthetic */ zzc zzBV;

        C05861(zzc com_google_android_gms_ads_internal_purchase_zzc, zzf com_google_android_gms_ads_internal_purchase_zzf, Intent intent) {
            this.zzBV = com_google_android_gms_ads_internal_purchase_zzc;
            this.zzBU = com_google_android_gms_ads_internal_purchase_zzf;
            this.val$intent = intent;
        }

        public void run() {
            try {
                if (this.zzBV.zzBT.zza(this.zzBU.zzCe, -1, this.val$intent)) {
                    this.zzBV.zzBP.zza(new zzg(this.zzBV.mContext, this.zzBU.zzCf, true, -1, this.val$intent, this.zzBU));
                } else {
                    this.zzBV.zzBP.zza(new zzg(this.zzBV.mContext, this.zzBU.zzCf, false, -1, this.val$intent, this.zzBU));
                }
            } catch (RemoteException e) {
                zzb.zzaE("Fail to verify and dispatch pending transaction");
            }
        }
    }

    public zzc(Context context, zzfq com_google_android_gms_internal_zzfq, zzk com_google_android_gms_ads_internal_purchase_zzk) {
        this(context, com_google_android_gms_internal_zzfq, com_google_android_gms_ads_internal_purchase_zzk, new zzb(context), zzh.zzx(context.getApplicationContext()));
    }

    zzc(Context context, zzfq com_google_android_gms_internal_zzfq, zzk com_google_android_gms_ads_internal_purchase_zzk, zzb com_google_android_gms_ads_internal_purchase_zzb, zzh com_google_android_gms_ads_internal_purchase_zzh) {
        this.zzpc = new Object();
        this.zzBO = false;
        this.zzBS = null;
        this.mContext = context;
        this.zzBP = com_google_android_gms_internal_zzfq;
        this.zzBT = com_google_android_gms_ads_internal_purchase_zzk;
        this.zzBQ = com_google_android_gms_ads_internal_purchase_zzb;
        this.zzBR = com_google_android_gms_ads_internal_purchase_zzh;
        this.zzBS = this.zzBR.zzf(10);
    }

    private void zzd(long j) {
        do {
            if (!zze(j)) {
                zzb.m1445v("Timeout waiting for pending transaction to be processed.");
            }
        } while (!this.zzBO);
    }

    private boolean zze(long j) {
        long elapsedRealtime = 60000 - (SystemClock.elapsedRealtime() - j);
        if (elapsedRealtime <= 0) {
            return false;
        }
        try {
            this.zzpc.wait(elapsedRealtime);
        } catch (InterruptedException e) {
            zzb.zzaE("waitWithTimeout_lock interrupted");
        }
        return true;
    }

    public void onServiceConnected(ComponentName name, IBinder service) {
        synchronized (this.zzpc) {
            this.zzBQ.zzM(service);
            zzfg();
            this.zzBO = true;
            this.zzpc.notify();
        }
    }

    public void onServiceDisconnected(ComponentName name) {
        zzb.zzaD("In-app billing service disconnected.");
        this.zzBQ.destroy();
    }

    public void onStop() {
        synchronized (this.zzpc) {
            com.google.android.gms.common.stats.zzb.zzpD().zza(this.mContext, this);
            this.zzBQ.destroy();
        }
    }

    protected void zza(zzf com_google_android_gms_ads_internal_purchase_zzf, String str, String str2) {
        Intent intent = new Intent();
        zzp.zzbH();
        intent.putExtra("RESPONSE_CODE", 0);
        zzp.zzbH();
        intent.putExtra("INAPP_PURCHASE_DATA", str);
        zzp.zzbH();
        intent.putExtra("INAPP_DATA_SIGNATURE", str2);
        zzhu.zzHK.post(new C05861(this, com_google_android_gms_ads_internal_purchase_zzf, intent));
    }

    public void zzdG() {
        synchronized (this.zzpc) {
            Intent intent = new Intent("com.android.vending.billing.InAppBillingService.BIND");
            intent.setPackage("com.android.vending");
            com.google.android.gms.common.stats.zzb.zzpD().zza(this.mContext, intent, (ServiceConnection) this, 1);
            zzd(SystemClock.elapsedRealtime());
            com.google.android.gms.common.stats.zzb.zzpD().zza(this.mContext, this);
            this.zzBQ.destroy();
        }
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    protected void zzfg() {
        /*
        r12 = this;
        r0 = r12.zzBS;
        r0 = r0.isEmpty();
        if (r0 == 0) goto L_0x0009;
    L_0x0008:
        return;
    L_0x0009:
        r6 = new java.util.HashMap;
        r6.<init>();
        r0 = r12.zzBS;
        r1 = r0.iterator();
    L_0x0014:
        r0 = r1.hasNext();
        if (r0 == 0) goto L_0x0026;
    L_0x001a:
        r0 = r1.next();
        r0 = (com.google.android.gms.ads.internal.purchase.zzf) r0;
        r2 = r0.zzCf;
        r6.put(r2, r0);
        goto L_0x0014;
    L_0x0026:
        r0 = 0;
    L_0x0027:
        r1 = r12.zzBQ;
        r2 = r12.mContext;
        r2 = r2.getPackageName();
        r0 = r1.zzj(r2, r0);
        if (r0 != 0) goto L_0x0055;
    L_0x0035:
        r0 = r6.keySet();
        r1 = r0.iterator();
    L_0x003d:
        r0 = r1.hasNext();
        if (r0 == 0) goto L_0x0008;
    L_0x0043:
        r0 = r1.next();
        r0 = (java.lang.String) r0;
        r2 = r12.zzBR;
        r0 = r6.get(r0);
        r0 = (com.google.android.gms.ads.internal.purchase.zzf) r0;
        r2.zza(r0);
        goto L_0x003d;
    L_0x0055:
        r1 = com.google.android.gms.ads.internal.zzp.zzbH();
        r1 = r1.zzc(r0);
        if (r1 != 0) goto L_0x0035;
    L_0x005f:
        r1 = "INAPP_PURCHASE_ITEM_LIST";
        r7 = r0.getStringArrayList(r1);
        r1 = "INAPP_PURCHASE_DATA_LIST";
        r8 = r0.getStringArrayList(r1);
        r1 = "INAPP_DATA_SIGNATURE_LIST";
        r9 = r0.getStringArrayList(r1);
        r1 = "INAPP_CONTINUATION_TOKEN";
        r5 = r0.getString(r1);
        r0 = 0;
        r4 = r0;
    L_0x007d:
        r0 = r7.size();
        if (r4 >= r0) goto L_0x00bf;
    L_0x0083:
        r0 = r7.get(r4);
        r0 = r6.containsKey(r0);
        if (r0 == 0) goto L_0x00bb;
    L_0x008d:
        r0 = r7.get(r4);
        r0 = (java.lang.String) r0;
        r1 = r8.get(r4);
        r1 = (java.lang.String) r1;
        r2 = r9.get(r4);
        r2 = (java.lang.String) r2;
        r3 = r6.get(r0);
        r3 = (com.google.android.gms.ads.internal.purchase.zzf) r3;
        r10 = com.google.android.gms.ads.internal.zzp.zzbH();
        r10 = r10.zzal(r1);
        r11 = r3.zzCe;
        r10 = r11.equals(r10);
        if (r10 == 0) goto L_0x00bb;
    L_0x00b5:
        r12.zza(r3, r1, r2);
        r6.remove(r0);
    L_0x00bb:
        r0 = r4 + 1;
        r4 = r0;
        goto L_0x007d;
    L_0x00bf:
        if (r5 == 0) goto L_0x0035;
    L_0x00c1:
        r0 = r6.isEmpty();
        if (r0 != 0) goto L_0x0035;
    L_0x00c7:
        r0 = r5;
        goto L_0x0027;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.ads.internal.purchase.zzc.zzfg():void");
    }
}
