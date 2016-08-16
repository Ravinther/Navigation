package com.google.android.gms.internal;

import com.google.android.gms.ads.internal.util.client.zzb;
import org.json.JSONObject;

@zzgk
public class zzfb {
    private final zzip zzoL;
    private final String zzzX;

    public zzfb(zzip com_google_android_gms_internal_zzip) {
        this(com_google_android_gms_internal_zzip, "");
    }

    public zzfb(zzip com_google_android_gms_internal_zzip, String str) {
        this.zzoL = com_google_android_gms_internal_zzip;
        this.zzzX = str;
    }

    public void zza(int i, int i2, int i3, int i4, float f, int i5) {
        try {
            this.zzoL.zzb("onScreenInfoChanged", new JSONObject().put("width", i).put("height", i2).put("maxSizeWidth", i3).put("maxSizeHeight", i4).put("density", (double) f).put("rotation", i5));
        } catch (Throwable e) {
            zzb.zzb("Error occured while obtaining screen information.", e);
        }
    }

    public void zzah(String str) {
        try {
            this.zzoL.zzb("onError", new JSONObject().put("message", str).put("action", this.zzzX));
        } catch (Throwable e) {
            zzb.zzb("Error occurred while dispatching error event.", e);
        }
    }

    public void zzai(String str) {
        try {
            this.zzoL.zzb("onReadyEventReceived", new JSONObject().put("js", str));
        } catch (Throwable e) {
            zzb.zzb("Error occured while dispatching ready Event.", e);
        }
    }

    public void zzaj(String str) {
        try {
            this.zzoL.zzb("onStateChanged", new JSONObject().put("state", str));
        } catch (Throwable e) {
            zzb.zzb("Error occured while dispatching state change.", e);
        }
    }

    public void zzb(int i, int i2, int i3, int i4) {
        try {
            this.zzoL.zzb("onSizeChanged", new JSONObject().put("x", i).put("y", i2).put("width", i3).put("height", i4));
        } catch (Throwable e) {
            zzb.zzb("Error occured while dispatching size change.", e);
        }
    }

    public void zzc(int i, int i2, int i3, int i4) {
        try {
            this.zzoL.zzb("onDefaultPositionReceived", new JSONObject().put("x", i).put("y", i2).put("width", i3).put("height", i4));
        } catch (Throwable e) {
            zzb.zzb("Error occured while dispatching default position.", e);
        }
    }
}
