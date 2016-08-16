package com.google.android.gms.ads.internal;

import android.content.Context;
import android.view.MotionEvent;
import com.google.android.gms.ads.internal.client.zzk;
import com.google.android.gms.ads.internal.util.client.zzb;
import com.google.android.gms.internal.zzaj;
import com.google.android.gms.internal.zzam;
import com.google.android.gms.internal.zzby;
import com.google.android.gms.internal.zzgk;
import com.google.android.gms.internal.zzht;
import java.util.List;
import java.util.Vector;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicReference;

@zzgk
class zzh implements zzaj, Runnable {
    private final List<Object[]> zzoP;
    private final AtomicReference<zzaj> zzoQ;
    CountDownLatch zzoR;
    private zzq zzos;

    public zzh(zzq com_google_android_gms_ads_internal_zzq) {
        this.zzoP = new Vector();
        this.zzoQ = new AtomicReference();
        this.zzoR = new CountDownLatch(1);
        this.zzos = com_google_android_gms_ads_internal_zzq;
        if (zzk.zzcE().zzgI()) {
            zzht.zza((Runnable) this);
        } else {
            run();
        }
    }

    private void zzbh() {
        if (!this.zzoP.isEmpty()) {
            for (Object[] objArr : this.zzoP) {
                if (objArr.length == 1) {
                    ((zzaj) this.zzoQ.get()).zza((MotionEvent) objArr[0]);
                } else if (objArr.length == 3) {
                    ((zzaj) this.zzoQ.get()).zza(((Integer) objArr[0]).intValue(), ((Integer) objArr[1]).intValue(), ((Integer) objArr[2]).intValue());
                }
            }
            this.zzoP.clear();
        }
    }

    private Context zzp(Context context) {
        if (!((Boolean) zzby.zzuh.get()).booleanValue()) {
            return context;
        }
        Context applicationContext = context.getApplicationContext();
        return applicationContext != null ? applicationContext : context;
    }

    public void run() {
        try {
            boolean z = !((Boolean) zzby.zzut.get()).booleanValue() || this.zzos.zzqb.zzIC;
            zza(zzb(this.zzos.zzqb.zzIz, zzp(this.zzos.context), z));
        } finally {
            this.zzoR.countDown();
            this.zzos = null;
        }
    }

    public void zza(int i, int i2, int i3) {
        zzaj com_google_android_gms_internal_zzaj = (zzaj) this.zzoQ.get();
        if (com_google_android_gms_internal_zzaj != null) {
            zzbh();
            com_google_android_gms_internal_zzaj.zza(i, i2, i3);
            return;
        }
        this.zzoP.add(new Object[]{Integer.valueOf(i), Integer.valueOf(i2), Integer.valueOf(i3)});
    }

    public void zza(MotionEvent motionEvent) {
        zzaj com_google_android_gms_internal_zzaj = (zzaj) this.zzoQ.get();
        if (com_google_android_gms_internal_zzaj != null) {
            zzbh();
            com_google_android_gms_internal_zzaj.zza(motionEvent);
            return;
        }
        this.zzoP.add(new Object[]{motionEvent});
    }

    protected void zza(zzaj com_google_android_gms_internal_zzaj) {
        this.zzoQ.set(com_google_android_gms_internal_zzaj);
    }

    protected zzaj zzb(String str, Context context, boolean z) {
        return zzam.zza(str, context, z);
    }

    public String zzb(Context context) {
        if (zzbg()) {
            zzaj com_google_android_gms_internal_zzaj = (zzaj) this.zzoQ.get();
            if (com_google_android_gms_internal_zzaj != null) {
                zzbh();
                return com_google_android_gms_internal_zzaj.zzb(zzp(context));
            }
        }
        return "";
    }

    public String zzb(Context context, String str) {
        if (zzbg()) {
            zzaj com_google_android_gms_internal_zzaj = (zzaj) this.zzoQ.get();
            if (com_google_android_gms_internal_zzaj != null) {
                zzbh();
                return com_google_android_gms_internal_zzaj.zzb(zzp(context), str);
            }
        }
        return "";
    }

    protected boolean zzbg() {
        try {
            this.zzoR.await();
            return true;
        } catch (Throwable e) {
            zzb.zzd("Interrupted during GADSignals creation.", e);
            return false;
        }
    }
}
