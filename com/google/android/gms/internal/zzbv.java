package com.google.android.gms.internal;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@zzgk
public class zzbv {
    private final Collection<zzbu> zztR;
    private final Collection<zzbu<String>> zztS;
    private final Collection<zzbu<String>> zztT;

    public zzbv() {
        this.zztR = new ArrayList();
        this.zztS = new ArrayList();
        this.zztT = new ArrayList();
    }

    public void zza(zzbu com_google_android_gms_internal_zzbu) {
        this.zztR.add(com_google_android_gms_internal_zzbu);
    }

    public void zzb(zzbu<String> com_google_android_gms_internal_zzbu_java_lang_String) {
        this.zztS.add(com_google_android_gms_internal_zzbu_java_lang_String);
    }

    public void zzc(zzbu<String> com_google_android_gms_internal_zzbu_java_lang_String) {
        this.zztT.add(com_google_android_gms_internal_zzbu_java_lang_String);
    }

    public List<String> zzde() {
        List<String> arrayList = new ArrayList();
        for (zzbu com_google_android_gms_internal_zzbu : this.zztS) {
            String str = (String) com_google_android_gms_internal_zzbu.get();
            if (str != null) {
                arrayList.add(str);
            }
        }
        return arrayList;
    }
}
