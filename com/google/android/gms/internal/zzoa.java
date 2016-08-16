package com.google.android.gms.internal;

import android.net.Uri;
import android.net.Uri.Builder;
import android.text.TextUtils;
import android.util.LogPrinter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public final class zzoa implements zzoh {
    private static final Uri zzaHN;
    private final LogPrinter zzaHO;

    /* renamed from: com.google.android.gms.internal.zzoa.1 */
    class C09751 implements Comparator<zzod> {
        final /* synthetic */ zzoa zzaHP;

        C09751(zzoa com_google_android_gms_internal_zzoa) {
            this.zzaHP = com_google_android_gms_internal_zzoa;
        }

        public /* synthetic */ int compare(Object x0, Object x1) {
            return zza((zzod) x0, (zzod) x1);
        }

        public int zza(zzod com_google_android_gms_internal_zzod, zzod com_google_android_gms_internal_zzod2) {
            return com_google_android_gms_internal_zzod.getClass().getCanonicalName().compareTo(com_google_android_gms_internal_zzod2.getClass().getCanonicalName());
        }
    }

    static {
        Builder builder = new Builder();
        builder.scheme("uri");
        builder.authority("local");
        zzaHN = builder.build();
    }

    public zzoa() {
        this.zzaHO = new LogPrinter(4, "GA/LogCatTransport");
    }

    public void zzb(zzob com_google_android_gms_internal_zzob) {
        List<zzod> arrayList = new ArrayList(com_google_android_gms_internal_zzob.zzxi());
        Collections.sort(arrayList, new C09751(this));
        StringBuilder stringBuilder = new StringBuilder();
        for (zzod obj : arrayList) {
            Object obj2 = obj.toString();
            if (!TextUtils.isEmpty(obj2)) {
                if (stringBuilder.length() != 0) {
                    stringBuilder.append(", ");
                }
                stringBuilder.append(obj2);
            }
        }
        this.zzaHO.println(stringBuilder.toString());
    }

    public Uri zzhs() {
        return zzaHN;
    }
}
