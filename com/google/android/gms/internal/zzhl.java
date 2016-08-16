package com.google.android.gms.internal;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import com.google.android.gms.ads.internal.util.client.VersionInfoParcel;
import com.google.android.gms.ads.internal.zzp;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.internal.zzhs.zzb;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.concurrent.Future;

@zzgk
public class zzhl implements zzb {
    private Context mContext;
    private boolean zzFr;
    private boolean zzFs;
    private final String zzGY;
    private final zzhm zzGZ;
    private BigInteger zzHa;
    private final HashSet<zzhk> zzHb;
    private final HashMap<String, zzho> zzHc;
    private boolean zzHd;
    private int zzHe;
    private zzca zzHf;
    private zzbk zzHg;
    private final LinkedList<Thread> zzHh;
    private Boolean zzHi;
    private String zzHj;
    private boolean zzHk;
    private zzay zzou;
    private VersionInfoParcel zzpa;
    private final Object zzpc;
    private boolean zzpr;
    private zzbj zzrP;
    private zzbi zzrQ;
    private final zzgj zzrR;

    public zzhl(zzhu com_google_android_gms_internal_zzhu) {
        this.zzpc = new Object();
        this.zzHa = BigInteger.ONE;
        this.zzHb = new HashSet();
        this.zzHc = new HashMap();
        this.zzHd = false;
        this.zzFr = true;
        this.zzHe = 0;
        this.zzpr = false;
        this.zzHf = null;
        this.zzFs = true;
        this.zzrP = null;
        this.zzHg = null;
        this.zzrQ = null;
        this.zzHh = new LinkedList();
        this.zzrR = null;
        this.zzHi = null;
        this.zzHk = false;
        this.zzGY = com_google_android_gms_internal_zzhu.zzgs();
        this.zzGZ = new zzhm(this.zzGY);
    }

    public String getSessionId() {
        return this.zzGY;
    }

    public void zzA(boolean z) {
        synchronized (this.zzpc) {
            this.zzFs = z;
        }
    }

    public void zzB(boolean z) {
        synchronized (this.zzpc) {
            this.zzHk = z;
        }
    }

    public zzbk zzF(Context context) {
        if (!((Boolean) zzby.zzuE.get()).booleanValue() || !zzlv.zzpR() || zzfZ()) {
            return null;
        }
        synchronized (this.zzpc) {
            if (this.zzrP == null) {
                if (context instanceof Activity) {
                    this.zzrP = new zzbj((Application) context.getApplicationContext(), (Activity) context);
                } else {
                    return null;
                }
            }
            if (this.zzrQ == null) {
                this.zzrQ = new zzbi();
            }
            if (this.zzHg == null) {
                this.zzHg = new zzbk(this.zzrP, this.zzrQ, new zzgj(this.mContext, this.zzpa, null, null));
            }
            this.zzHg.zzct();
            zzbk com_google_android_gms_internal_zzbk = this.zzHg;
            return com_google_android_gms_internal_zzbk;
        }
    }

    public Bundle zza(Context context, zzhn com_google_android_gms_internal_zzhn, String str) {
        Bundle bundle;
        synchronized (this.zzpc) {
            bundle = new Bundle();
            bundle.putBundle("app", this.zzGZ.zze(context, str));
            Bundle bundle2 = new Bundle();
            for (String str2 : this.zzHc.keySet()) {
                bundle2.putBundle(str2, ((zzho) this.zzHc.get(str2)).toBundle());
            }
            bundle.putBundle("slots", bundle2);
            ArrayList arrayList = new ArrayList();
            Iterator it = this.zzHb.iterator();
            while (it.hasNext()) {
                arrayList.add(((zzhk) it.next()).toBundle());
            }
            bundle.putParcelableArrayList("ads", arrayList);
            com_google_android_gms_internal_zzhn.zza(this.zzHb);
            this.zzHb.clear();
        }
        return bundle;
    }

    public Future zza(Context context, boolean z) {
        Future zza;
        synchronized (this.zzpc) {
            if (z != this.zzFr) {
                this.zzFr = z;
                zza = zzhs.zza(context, z);
            } else {
                zza = null;
            }
        }
        return zza;
    }

    public void zza(zzhk com_google_android_gms_internal_zzhk) {
        synchronized (this.zzpc) {
            this.zzHb.add(com_google_android_gms_internal_zzhk);
        }
    }

    public void zza(String str, zzho com_google_android_gms_internal_zzho) {
        synchronized (this.zzpc) {
            this.zzHc.put(str, com_google_android_gms_internal_zzho);
        }
    }

    public void zza(Thread thread) {
        zzgj.zza(this.mContext, thread, this.zzpa);
    }

    public void zzb(Context context, VersionInfoParcel versionInfoParcel) {
        synchronized (this.zzpc) {
            if (!this.zzpr) {
                this.mContext = context.getApplicationContext();
                this.zzpa = versionInfoParcel;
                zzhs.zza(context, (zzb) this);
                zzhs.zzb(context, this);
                zza(Thread.currentThread());
                this.zzHj = zzp.zzbx().zzf(context, versionInfoParcel.zzIz);
                this.zzou = new zzay(context.getApplicationContext(), this.zzpa, new zzdv(context.getApplicationContext(), this.zzpa, (String) zzby.zztW.get()));
                zzgk();
                zzp.zzbH().zzy(this.mContext);
                this.zzpr = true;
            }
        }
    }

    public void zzb(Boolean bool) {
        synchronized (this.zzpc) {
            this.zzHi = bool;
        }
    }

    public void zzb(HashSet<zzhk> hashSet) {
        synchronized (this.zzpc) {
            this.zzHb.addAll(hashSet);
        }
    }

    public String zzc(int i, String str) {
        Resources resources = this.zzpa.zzIC ? this.mContext.getResources() : GooglePlayServicesUtil.getRemoteResource(this.mContext);
        return resources == null ? str : resources.getString(i);
    }

    public void zzc(Throwable th, boolean z) {
        new zzgj(this.mContext, this.zzpa, null, null).zza(th, z);
    }

    public void zzd(Bundle bundle) {
        synchronized (this.zzpc) {
            this.zzFr = bundle.containsKey("use_https") ? bundle.getBoolean("use_https") : this.zzFr;
            this.zzHe = bundle.containsKey("webview_cache_version") ? bundle.getInt("webview_cache_version") : this.zzHe;
        }
    }

    public boolean zzfZ() {
        boolean z;
        synchronized (this.zzpc) {
            z = this.zzFs;
        }
        return z;
    }

    public String zzga() {
        String bigInteger;
        synchronized (this.zzpc) {
            bigInteger = this.zzHa.toString();
            this.zzHa = this.zzHa.add(BigInteger.ONE);
        }
        return bigInteger;
    }

    public zzhm zzgb() {
        zzhm com_google_android_gms_internal_zzhm;
        synchronized (this.zzpc) {
            com_google_android_gms_internal_zzhm = this.zzGZ;
        }
        return com_google_android_gms_internal_zzhm;
    }

    public zzca zzgc() {
        zzca com_google_android_gms_internal_zzca;
        synchronized (this.zzpc) {
            com_google_android_gms_internal_zzca = this.zzHf;
        }
        return com_google_android_gms_internal_zzca;
    }

    public boolean zzgd() {
        boolean z;
        synchronized (this.zzpc) {
            z = this.zzHd;
            this.zzHd = true;
        }
        return z;
    }

    public boolean zzge() {
        boolean z;
        synchronized (this.zzpc) {
            z = this.zzFr;
        }
        return z;
    }

    public String zzgf() {
        String str;
        synchronized (this.zzpc) {
            str = this.zzHj;
        }
        return str;
    }

    public Boolean zzgg() {
        Boolean bool;
        synchronized (this.zzpc) {
            bool = this.zzHi;
        }
        return bool;
    }

    public zzay zzgh() {
        return this.zzou;
    }

    public boolean zzgi() {
        boolean z;
        synchronized (this.zzpc) {
            if (this.zzHe < ((Integer) zzby.zzuS.get()).intValue()) {
                this.zzHe = ((Integer) zzby.zzuS.get()).intValue();
                zzhs.zza(this.mContext, this.zzHe);
                z = true;
            } else {
                z = false;
            }
        }
        return z;
    }

    public boolean zzgj() {
        boolean z;
        synchronized (this.zzpc) {
            z = this.zzHk;
        }
        return z;
    }

    void zzgk() {
        try {
            this.zzHf = zzp.zzbC().zza(new zzbz(this.mContext, this.zzpa.zzIz));
        } catch (Throwable e) {
            com.google.android.gms.ads.internal.util.client.zzb.zzd("Cannot initialize CSI reporter.", e);
        }
    }
}
