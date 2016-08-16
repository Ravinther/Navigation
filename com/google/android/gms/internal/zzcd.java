package com.google.android.gms.internal;

import android.text.TextUtils;
import com.google.android.gms.ads.internal.zzp;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class zzcd {
    private final Object zzpc;
    boolean zzvf;
    private final List<zzcc> zzvr;
    private final Map<String, String> zzvs;
    private String zzvt;
    private zzcc zzvu;

    public zzcd(boolean z, String str, String str2) {
        this.zzvr = new LinkedList();
        this.zzvs = new LinkedHashMap();
        this.zzpc = new Object();
        this.zzvf = z;
        this.zzvs.put("action", str);
        this.zzvs.put("ad_format", str2);
    }

    private boolean zza(zzcc com_google_android_gms_internal_zzcc, long j, String... strArr) {
        synchronized (this.zzpc) {
            for (String com_google_android_gms_internal_zzcc2 : strArr) {
                this.zzvr.add(new zzcc(j, com_google_android_gms_internal_zzcc2, com_google_android_gms_internal_zzcc));
            }
        }
        return true;
    }

    public void zzR(String str) {
        if (this.zzvf) {
            synchronized (this.zzpc) {
                this.zzvt = str;
            }
        }
    }

    public boolean zza(zzcc com_google_android_gms_internal_zzcc, String... strArr) {
        return (!this.zzvf || com_google_android_gms_internal_zzcc == null) ? false : zza(com_google_android_gms_internal_zzcc, zzp.zzbB().elapsedRealtime(), strArr);
    }

    public zzcc zzb(long j) {
        return !this.zzvf ? null : new zzcc(j, null, null);
    }

    public void zzd(String str, String str2) {
        if (this.zzvf) {
            synchronized (this.zzpc) {
                this.zzvs.put(str, str2);
            }
        }
    }

    public zzcc zzdl() {
        return zzb(zzp.zzbB().elapsedRealtime());
    }

    public void zzdm() {
        synchronized (this.zzpc) {
            this.zzvu = zzdl();
        }
    }

    public String zzdn() {
        String stringBuilder;
        StringBuilder stringBuilder2 = new StringBuilder();
        synchronized (this.zzpc) {
            for (zzcc com_google_android_gms_internal_zzcc : this.zzvr) {
                long time = com_google_android_gms_internal_zzcc.getTime();
                String zzdj = com_google_android_gms_internal_zzcc.zzdj();
                zzcc com_google_android_gms_internal_zzcc2 = com_google_android_gms_internal_zzcc2.zzdk();
                if (com_google_android_gms_internal_zzcc2 != null && time > 0) {
                    stringBuilder2.append(zzdj).append('.').append(time - com_google_android_gms_internal_zzcc2.getTime()).append(',');
                }
            }
            this.zzvr.clear();
            if (!TextUtils.isEmpty(this.zzvt)) {
                stringBuilder2.append(this.zzvt);
            } else if (stringBuilder2.length() > 0) {
                stringBuilder2.setLength(stringBuilder2.length() - 1);
            }
            stringBuilder = stringBuilder2.toString();
        }
        return stringBuilder;
    }

    public zzcc zzdo() {
        zzcc com_google_android_gms_internal_zzcc;
        synchronized (this.zzpc) {
            com_google_android_gms_internal_zzcc = this.zzvu;
        }
        return com_google_android_gms_internal_zzcc;
    }

    Map<String, String> zzn() {
        Map<String, String> map;
        synchronized (this.zzpc) {
            map = this.zzvs;
        }
        return map;
    }
}
