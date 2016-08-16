package com.google.android.gms.analytics.internal;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Bundle;

public class zzan extends zzd {
    protected boolean zzKx;
    protected int zzMQ;
    protected String zzNT;
    protected String zzNU;
    protected int zzNW;
    protected boolean zzOL;
    protected boolean zzOM;
    protected boolean zzON;

    public zzan(zzf com_google_android_gms_analytics_internal_zzf) {
        super(com_google_android_gms_analytics_internal_zzf);
    }

    private static int zzbt(String str) {
        String toLowerCase = str.toLowerCase();
        return "verbose".equals(toLowerCase) ? 0 : "info".equals(toLowerCase) ? 1 : "warning".equals(toLowerCase) ? 2 : "error".equals(toLowerCase) ? 3 : -1;
    }

    public int getLogLevel() {
        zzio();
        return this.zzMQ;
    }

    void zza(zzaa com_google_android_gms_analytics_internal_zzaa) {
        int zzbt;
        zzaY("Loading global XML config values");
        if (com_google_android_gms_analytics_internal_zzaa.zzjY()) {
            String zzjZ = com_google_android_gms_analytics_internal_zzaa.zzjZ();
            this.zzNT = zzjZ;
            zzb("XML config - app name", zzjZ);
        }
        if (com_google_android_gms_analytics_internal_zzaa.zzka()) {
            zzjZ = com_google_android_gms_analytics_internal_zzaa.zzkb();
            this.zzNU = zzjZ;
            zzb("XML config - app version", zzjZ);
        }
        if (com_google_android_gms_analytics_internal_zzaa.zzkc()) {
            zzbt = zzbt(com_google_android_gms_analytics_internal_zzaa.zzkd());
            if (zzbt >= 0) {
                this.zzMQ = zzbt;
                zza("XML config - log level", Integer.valueOf(zzbt));
            }
        }
        if (com_google_android_gms_analytics_internal_zzaa.zzke()) {
            zzbt = com_google_android_gms_analytics_internal_zzaa.zzkf();
            this.zzNW = zzbt;
            this.zzOM = true;
            zzb("XML config - dispatch period (sec)", Integer.valueOf(zzbt));
        }
        if (com_google_android_gms_analytics_internal_zzaa.zzkg()) {
            boolean zzkh = com_google_android_gms_analytics_internal_zzaa.zzkh();
            this.zzKx = zzkh;
            this.zzON = true;
            zzb("XML config - dry run", Boolean.valueOf(zzkh));
        }
    }

    protected void zzhB() {
        zzkW();
    }

    public String zzjZ() {
        zzio();
        return this.zzNT;
    }

    public int zzkV() {
        zzio();
        return this.zzNW;
    }

    protected void zzkW() {
        ApplicationInfo applicationInfo;
        Context context = getContext();
        try {
            applicationInfo = context.getPackageManager().getApplicationInfo(context.getPackageName(), 129);
        } catch (NameNotFoundException e) {
            zzd("PackageManager doesn't know about the app package", e);
            applicationInfo = null;
        }
        if (applicationInfo == null) {
            zzbb("Couldn't get ApplicationInfo to load global config");
            return;
        }
        Bundle bundle = applicationInfo.metaData;
        if (bundle != null) {
            int i = bundle.getInt("com.google.android.gms.analytics.globalConfigResource");
            if (i > 0) {
                zzaa com_google_android_gms_analytics_internal_zzaa = (zzaa) new zzz(zzia()).zzac(i);
                if (com_google_android_gms_analytics_internal_zzaa != null) {
                    zza(com_google_android_gms_analytics_internal_zzaa);
                }
            }
        }
    }

    public String zzkb() {
        zzio();
        return this.zzNU;
    }

    public boolean zzkc() {
        zzio();
        return this.zzOL;
    }

    public boolean zzke() {
        zzio();
        return this.zzOM;
    }

    public boolean zzkg() {
        zzio();
        return this.zzON;
    }

    public boolean zzkh() {
        zzio();
        return this.zzKx;
    }
}
