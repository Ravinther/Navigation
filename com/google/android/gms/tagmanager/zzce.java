package com.google.android.gms.tagmanager;

import com.google.android.gms.internal.zzad;
import com.google.android.gms.internal.zzae;
import com.google.android.gms.internal.zzag.zza;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

class zzce extends zzak {
    private static final String ID;
    private static final String zzaRi;
    private static final String zzaRj;
    private static final String zzaRk;
    private static final String zzaRl;

    static {
        ID = zzad.REGEX_GROUP.toString();
        zzaRi = zzae.ARG0.toString();
        zzaRj = zzae.ARG1.toString();
        zzaRk = zzae.IGNORE_CASE.toString();
        zzaRl = zzae.GROUP.toString();
    }

    public zzce() {
        super(ID, zzaRi, zzaRj);
    }

    public zza zzG(Map<String, zza> map) {
        zza com_google_android_gms_internal_zzag_zza = (zza) map.get(zzaRi);
        zza com_google_android_gms_internal_zzag_zza2 = (zza) map.get(zzaRj);
        if (com_google_android_gms_internal_zzag_zza == null || com_google_android_gms_internal_zzag_zza == zzdf.zzBg() || com_google_android_gms_internal_zzag_zza2 == null || com_google_android_gms_internal_zzag_zza2 == zzdf.zzBg()) {
            return zzdf.zzBg();
        }
        int i = 64;
        if (zzdf.zzk((zza) map.get(zzaRk)).booleanValue()) {
            i = 66;
        }
        zza com_google_android_gms_internal_zzag_zza3 = (zza) map.get(zzaRl);
        int intValue;
        if (com_google_android_gms_internal_zzag_zza3 != null) {
            Long zzi = zzdf.zzi(com_google_android_gms_internal_zzag_zza3);
            if (zzi == zzdf.zzBb()) {
                return zzdf.zzBg();
            }
            intValue = zzi.intValue();
            if (intValue < 0) {
                return zzdf.zzBg();
            }
        }
        intValue = 1;
        try {
            CharSequence zzg = zzdf.zzg(com_google_android_gms_internal_zzag_zza);
            Object obj = null;
            Matcher matcher = Pattern.compile(zzdf.zzg(com_google_android_gms_internal_zzag_zza2), i).matcher(zzg);
            if (matcher.find() && matcher.groupCount() >= intValue) {
                obj = matcher.group(intValue);
            }
            return obj == null ? zzdf.zzBg() : zzdf.zzK(obj);
        } catch (PatternSyntaxException e) {
            return zzdf.zzBg();
        }
    }

    public boolean zzzx() {
        return true;
    }
}
