package com.google.android.gms.dynamic;

import android.os.Bundle;
import java.util.Iterator;
import java.util.LinkedList;

public abstract class zza<T extends LifecycleDelegate> {
    private T zzamN;
    private Bundle zzamO;
    private LinkedList<zza> zzamP;
    private final zzf<T> zzamQ;

    /* renamed from: com.google.android.gms.dynamic.zza.1 */
    class C08421 implements zzf<T> {
        final /* synthetic */ zza zzamR;

        C08421(zza com_google_android_gms_dynamic_zza) {
            this.zzamR = com_google_android_gms_dynamic_zza;
        }

        public void zza(T t) {
            this.zzamR.zzamN = t;
            Iterator it = this.zzamR.zzamP.iterator();
            while (it.hasNext()) {
                ((zza) it.next()).zzb(this.zzamR.zzamN);
            }
            this.zzamR.zzamP.clear();
            this.zzamR.zzamO = null;
        }
    }

    private interface zza {
        void zzb(LifecycleDelegate lifecycleDelegate);
    }

    public zza() {
        this.zzamQ = new C08421(this);
    }

    public T zzrn() {
        return this.zzamN;
    }
}
