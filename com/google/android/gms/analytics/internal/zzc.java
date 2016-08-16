package com.google.android.gms.analytics.internal;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.common.internal.zzx;
import com.google.android.gms.internal.zzlm;
import com.google.android.gms.internal.zzof;

public class zzc {
    private final zzf zzLy;

    protected zzc(zzf com_google_android_gms_analytics_internal_zzf) {
        zzx.zzv(com_google_android_gms_analytics_internal_zzf);
        this.zzLy = com_google_android_gms_analytics_internal_zzf;
    }

    private void zza(int i, String str, Object obj, Object obj2, Object obj3) {
        zzaf com_google_android_gms_analytics_internal_zzaf = null;
        if (this.zzLy != null) {
            com_google_android_gms_analytics_internal_zzaf = this.zzLy.zzir();
        }
        if (com_google_android_gms_analytics_internal_zzaf != null) {
            com_google_android_gms_analytics_internal_zzaf.zza(i, str, obj, obj2, obj3);
            return;
        }
        String str2 = (String) zzy.zzNa.get();
        if (Log.isLoggable(str2, i)) {
            Log.println(i, str2, zzc(str, obj, obj2, obj3));
        }
    }

    protected static String zzc(String str, Object obj, Object obj2, Object obj3) {
        if (str == null) {
            Object obj4 = "";
        }
        Object zzi = zzi(obj);
        Object zzi2 = zzi(obj2);
        Object zzi3 = zzi(obj3);
        StringBuilder stringBuilder = new StringBuilder();
        String str2 = "";
        if (!TextUtils.isEmpty(obj4)) {
            stringBuilder.append(obj4);
            str2 = ": ";
        }
        if (!TextUtils.isEmpty(zzi)) {
            stringBuilder.append(str2);
            stringBuilder.append(zzi);
            str2 = ", ";
        }
        if (!TextUtils.isEmpty(zzi2)) {
            stringBuilder.append(str2);
            stringBuilder.append(zzi2);
            str2 = ", ";
        }
        if (!TextUtils.isEmpty(zzi3)) {
            stringBuilder.append(str2);
            stringBuilder.append(zzi3);
            str2 = ", ";
        }
        return stringBuilder.toString();
    }

    private static String zzi(Object obj) {
        if (obj == null) {
            return "";
        }
        if (obj instanceof String) {
            return (String) obj;
        }
        if (!(obj instanceof Boolean)) {
            return obj instanceof Throwable ? ((Throwable) obj).toString() : obj.toString();
        } else {
            return obj == Boolean.TRUE ? "true" : "false";
        }
    }

    protected Context getContext() {
        return this.zzLy.getContext();
    }

    public void zza(String str, Object obj) {
        zza(2, str, obj, null, null);
    }

    public void zza(String str, Object obj, Object obj2) {
        zza(2, str, obj, obj2, null);
    }

    public void zza(String str, Object obj, Object obj2, Object obj3) {
        zza(3, str, obj, obj2, obj3);
    }

    public void zzaY(String str) {
        zza(2, str, null, null, null);
    }

    public void zzaZ(String str) {
        zza(3, str, null, null, null);
    }

    public void zzb(String str, Object obj) {
        zza(3, str, obj, null, null);
    }

    public void zzb(String str, Object obj, Object obj2) {
        zza(3, str, obj, obj2, null);
    }

    public void zzb(String str, Object obj, Object obj2, Object obj3) {
        zza(5, str, obj, obj2, obj3);
    }

    public void zzba(String str) {
        zza(4, str, null, null, null);
    }

    public void zzbb(String str) {
        zza(5, str, null, null, null);
    }

    public void zzbc(String str) {
        zza(6, str, null, null, null);
    }

    public void zzc(String str, Object obj) {
        zza(4, str, obj, null, null);
    }

    public void zzc(String str, Object obj, Object obj2) {
        zza(5, str, obj, obj2, null);
    }

    public void zzd(String str, Object obj) {
        zza(5, str, obj, null, null);
    }

    public void zzd(String str, Object obj, Object obj2) {
        zza(6, str, obj, obj2, null);
    }

    public void zze(String str, Object obj) {
        zza(6, str, obj, null, null);
    }

    protected zzan zzhA() {
        return this.zzLy.zzhA();
    }

    public GoogleAnalytics zzhu() {
        return this.zzLy.zzis();
    }

    protected zzb zzhz() {
        return this.zzLy.zzhz();
    }

    public zzf zzia() {
        return this.zzLy;
    }

    protected void zzib() {
        if (zzif().zzjk()) {
            throw new IllegalStateException("Call only supported on the client side");
        }
    }

    protected void zzic() {
        this.zzLy.zzic();
    }

    protected zzlm zzid() {
        return this.zzLy.zzid();
    }

    protected zzaf zzie() {
        return this.zzLy.zzie();
    }

    protected zzr zzif() {
        return this.zzLy.zzif();
    }

    protected zzof zzig() {
        return this.zzLy.zzig();
    }

    protected zzv zzih() {
        return this.zzLy.zzih();
    }

    protected zzai zzii() {
        return this.zzLy.zzii();
    }

    protected zzn zzij() {
        return this.zzLy.zziv();
    }

    protected zza zzik() {
        return this.zzLy.zziu();
    }

    protected zzk zzil() {
        return this.zzLy.zzil();
    }

    protected zzu zzim() {
        return this.zzLy.zzim();
    }

    public boolean zzin() {
        return Log.isLoggable((String) zzy.zzNa.get(), 2);
    }
}
