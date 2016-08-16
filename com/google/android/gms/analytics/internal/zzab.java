package com.google.android.gms.analytics.internal;

import android.text.TextUtils;
import com.google.android.gms.common.internal.zzx;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class zzab {
    private final List<Command> zzNY;
    private final long zzNZ;
    private final long zzOa;
    private final int zzOb;
    private final boolean zzOc;
    private final String zzOd;
    private final Map<String, String> zzvs;

    public zzab(zzc com_google_android_gms_analytics_internal_zzc, Map<String, String> map, long j, boolean z) {
        this(com_google_android_gms_analytics_internal_zzc, map, j, z, 0, 0, null);
    }

    public zzab(zzc com_google_android_gms_analytics_internal_zzc, Map<String, String> map, long j, boolean z, long j2, int i) {
        this(com_google_android_gms_analytics_internal_zzc, map, j, z, j2, i, null);
    }

    public zzab(zzc com_google_android_gms_analytics_internal_zzc, Map<String, String> map, long j, boolean z, long j2, int i, List<Command> list) {
        zzx.zzv(com_google_android_gms_analytics_internal_zzc);
        zzx.zzv(map);
        this.zzOa = j;
        this.zzOc = z;
        this.zzNZ = j2;
        this.zzOb = i;
        this.zzNY = list != null ? list : Collections.EMPTY_LIST;
        this.zzOd = zze(list);
        Map hashMap = new HashMap();
        for (Entry entry : map.entrySet()) {
            String zza;
            if (zzj(entry.getKey())) {
                zza = zza(com_google_android_gms_analytics_internal_zzc, entry.getKey());
                if (zza != null) {
                    hashMap.put(zza, zzb(com_google_android_gms_analytics_internal_zzc, entry.getValue()));
                }
            }
        }
        for (Entry entry2 : map.entrySet()) {
            if (!zzj(entry2.getKey())) {
                zza = zza(com_google_android_gms_analytics_internal_zzc, entry2.getKey());
                if (zza != null) {
                    hashMap.put(zza, zzb(com_google_android_gms_analytics_internal_zzc, entry2.getValue()));
                }
            }
        }
        if (!TextUtils.isEmpty(this.zzOd)) {
            zzam.zzb(hashMap, "_v", this.zzOd);
            if (this.zzOd.equals("ma4.0.0") || this.zzOd.equals("ma4.0.1")) {
                hashMap.remove("adid");
            }
        }
        this.zzvs = Collections.unmodifiableMap(hashMap);
    }

    public static zzab zza(zzc com_google_android_gms_analytics_internal_zzc, zzab com_google_android_gms_analytics_internal_zzab, Map<String, String> map) {
        return new zzab(com_google_android_gms_analytics_internal_zzc, map, com_google_android_gms_analytics_internal_zzab.zzkk(), com_google_android_gms_analytics_internal_zzab.zzkm(), com_google_android_gms_analytics_internal_zzab.zzkj(), com_google_android_gms_analytics_internal_zzab.zzki(), com_google_android_gms_analytics_internal_zzab.zzkl());
    }

    private static String zza(zzc com_google_android_gms_analytics_internal_zzc, Object obj) {
        if (obj == null) {
            return null;
        }
        Object obj2 = obj.toString();
        if (obj2.startsWith("&")) {
            obj2 = obj2.substring(1);
        }
        int length = obj2.length();
        if (length > 256) {
            obj2 = obj2.substring(0, 256);
            com_google_android_gms_analytics_internal_zzc.zzc("Hit param name is too long and will be trimmed", Integer.valueOf(length), obj2);
        }
        return TextUtils.isEmpty(obj2) ? null : obj2;
    }

    private static String zzb(zzc com_google_android_gms_analytics_internal_zzc, Object obj) {
        String obj2 = obj == null ? "" : obj.toString();
        int length = obj2.length();
        if (length <= 8192) {
            return obj2;
        }
        obj2 = obj2.substring(0, 8192);
        com_google_android_gms_analytics_internal_zzc.zzc("Hit param value is too long and will be trimmed", Integer.valueOf(length), obj2);
        return obj2;
    }

    private static String zze(List<Command> list) {
        CharSequence value;
        if (list != null) {
            for (Command command : list) {
                if ("appendVersion".equals(command.getId())) {
                    value = command.getValue();
                    break;
                }
            }
        }
        value = null;
        return TextUtils.isEmpty(value) ? null : value;
    }

    private static boolean zzj(Object obj) {
        return obj == null ? false : obj.toString().startsWith("&");
    }

    private String zzo(String str, String str2) {
        zzx.zzcs(str);
        zzx.zzb(!str.startsWith("&"), (Object) "Short param name required");
        String str3 = (String) this.zzvs.get(str);
        return str3 != null ? str3 : str2;
    }

    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("ht=").append(this.zzOa);
        if (this.zzNZ != 0) {
            stringBuffer.append(", dbId=").append(this.zzNZ);
        }
        if (((long) this.zzOb) != 0) {
            stringBuffer.append(", appUID=").append(this.zzOb);
        }
        List<String> arrayList = new ArrayList(this.zzvs.keySet());
        Collections.sort(arrayList);
        for (String str : arrayList) {
            stringBuffer.append(", ");
            stringBuffer.append(str);
            stringBuffer.append("=");
            stringBuffer.append((String) this.zzvs.get(str));
        }
        return stringBuffer.toString();
    }

    public int zzki() {
        return this.zzOb;
    }

    public long zzkj() {
        return this.zzNZ;
    }

    public long zzkk() {
        return this.zzOa;
    }

    public List<Command> zzkl() {
        return this.zzNY;
    }

    public boolean zzkm() {
        return this.zzOc;
    }

    public long zzkn() {
        return zzam.zzbo(zzo("_s", "0"));
    }

    public String zzko() {
        return zzo("_m", "");
    }

    public Map<String, String> zzn() {
        return this.zzvs;
    }
}
