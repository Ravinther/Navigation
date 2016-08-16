package com.google.android.gms.internal;

import android.content.ComponentName;
import android.content.Context;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Bundle;
import com.google.android.gms.ads.internal.client.AdRequestParcel;
import com.google.android.gms.ads.internal.util.client.zzb;

@zzgk
public class zzhm {
    final String zzGY;
    long zzHl;
    long zzHm;
    int zzHn;
    int zzHo;
    int zzHp;
    private final Object zzpc;

    public zzhm(String str) {
        this.zzHl = -1;
        this.zzHm = -1;
        this.zzHn = -1;
        this.zzpc = new Object();
        this.zzHo = 0;
        this.zzHp = 0;
        this.zzGY = str;
    }

    public static boolean zzG(Context context) {
        int identifier = context.getResources().getIdentifier("Theme.Translucent", "style", "android");
        if (identifier == 0) {
            zzb.zzaD("Please set theme of AdActivity to @android:style/Theme.Translucent to enable transparent background interstitial ad.");
            return false;
        }
        try {
            if (identifier == context.getPackageManager().getActivityInfo(new ComponentName(context.getPackageName(), "com.google.android.gms.ads.AdActivity"), 0).theme) {
                return true;
            }
            zzb.zzaD("Please set theme of AdActivity to @android:style/Theme.Translucent to enable transparent background interstitial ad.");
            return false;
        } catch (NameNotFoundException e) {
            zzb.zzaE("Fail to fetch AdActivity theme");
            zzb.zzaD("Please set theme of AdActivity to @android:style/Theme.Translucent to enable transparent background interstitial ad.");
            return false;
        }
    }

    public void zzb(AdRequestParcel adRequestParcel, long j) {
        synchronized (this.zzpc) {
            if (this.zzHm == -1) {
                this.zzHm = j;
                this.zzHl = this.zzHm;
            } else {
                this.zzHl = j;
            }
            if (adRequestParcel.extras == null || adRequestParcel.extras.getInt("gw", 2) != 1) {
                this.zzHn++;
                return;
            }
        }
    }

    public Bundle zze(Context context, String str) {
        Bundle bundle;
        synchronized (this.zzpc) {
            bundle = new Bundle();
            bundle.putString("session_id", this.zzGY);
            bundle.putLong("basets", this.zzHm);
            bundle.putLong("currts", this.zzHl);
            bundle.putString("seq_num", str);
            bundle.putInt("preqs", this.zzHn);
            bundle.putInt("pclick", this.zzHo);
            bundle.putInt("pimp", this.zzHp);
            bundle.putBoolean("support_transparent_background", zzG(context));
        }
        return bundle;
    }

    public void zzfT() {
        synchronized (this.zzpc) {
            this.zzHp++;
        }
    }

    public void zzfU() {
        synchronized (this.zzpc) {
            this.zzHo++;
        }
    }

    public long zzgl() {
        return this.zzHm;
    }
}
