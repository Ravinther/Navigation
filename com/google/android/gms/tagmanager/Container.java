package com.google.android.gms.tagmanager;

import android.content.Context;
import com.google.android.gms.internal.zzqp.zzc;
import java.util.HashMap;
import java.util.Map;

public class Container {
    private final Context mContext;
    private final String zzaOS;
    private final DataLayer zzaOT;
    private zzcp zzaOU;
    private Map<String, FunctionCallMacroCallback> zzaOV;
    private Map<String, FunctionCallTagCallback> zzaOW;
    private volatile long zzaOX;
    private volatile String zzaOY;

    public interface FunctionCallMacroCallback {
        Object getValue(String str, Map<String, Object> map);
    }

    public interface FunctionCallTagCallback {
        void execute(String str, Map<String, Object> map);
    }

    private class zza implements com.google.android.gms.tagmanager.zzt.zza {
        final /* synthetic */ Container zzaOZ;

        private zza(Container container) {
            this.zzaOZ = container;
        }

        public Object zzd(String str, Map<String, Object> map) {
            FunctionCallMacroCallback zzeu = this.zzaOZ.zzeu(str);
            return zzeu == null ? null : zzeu.getValue(str, map);
        }
    }

    private class zzb implements com.google.android.gms.tagmanager.zzt.zza {
        final /* synthetic */ Container zzaOZ;

        private zzb(Container container) {
            this.zzaOZ = container;
        }

        public Object zzd(String str, Map<String, Object> map) {
            FunctionCallTagCallback zzev = this.zzaOZ.zzev(str);
            if (zzev != null) {
                zzev.execute(str, map);
            }
            return zzdf.zzBf();
        }
    }

    Container(Context context, DataLayer dataLayer, String containerId, long lastRefreshTime, zzc resource) {
        this.zzaOV = new HashMap();
        this.zzaOW = new HashMap();
        this.zzaOY = "";
        this.mContext = context;
        this.zzaOT = dataLayer;
        this.zzaOS = containerId;
        this.zzaOX = lastRefreshTime;
        zza(resource);
    }

    private void zza(zzc com_google_android_gms_internal_zzqp_zzc) {
        this.zzaOY = com_google_android_gms_internal_zzqp_zzc.getVersion();
        zzc com_google_android_gms_internal_zzqp_zzc2 = com_google_android_gms_internal_zzqp_zzc;
        zza(new zzcp(this.mContext, com_google_android_gms_internal_zzqp_zzc2, this.zzaOT, new zza(), new zzb(), zzex(this.zzaOY)));
        if (getBoolean("_gtm.loadEventEnabled")) {
            this.zzaOT.pushEvent("gtm.load", DataLayer.mapOf("gtm.id", this.zzaOS));
        }
    }

    private synchronized void zza(zzcp com_google_android_gms_tagmanager_zzcp) {
        this.zzaOU = com_google_android_gms_tagmanager_zzcp;
    }

    private synchronized zzcp zzzD() {
        return this.zzaOU;
    }

    public boolean getBoolean(String key) {
        zzcp zzzD = zzzD();
        if (zzzD == null) {
            zzbg.m1447e("getBoolean called for closed container.");
            return zzdf.zzBd().booleanValue();
        }
        try {
            return zzdf.zzk((com.google.android.gms.internal.zzag.zza) zzzD.zzeS(key).getObject()).booleanValue();
        } catch (Exception e) {
            zzbg.m1447e("Calling getBoolean() threw an exception: " + e.getMessage() + " Returning default value.");
            return zzdf.zzBd().booleanValue();
        }
    }

    public long getLastRefreshTime() {
        return this.zzaOX;
    }

    public String getString(String key) {
        zzcp zzzD = zzzD();
        if (zzzD == null) {
            zzbg.m1447e("getString called for closed container.");
            return zzdf.zzBf();
        }
        try {
            return zzdf.zzg((com.google.android.gms.internal.zzag.zza) zzzD.zzeS(key).getObject());
        } catch (Exception e) {
            zzbg.m1447e("Calling getString() threw an exception: " + e.getMessage() + " Returning default value.");
            return zzdf.zzBf();
        }
    }

    public boolean isDefault() {
        return getLastRefreshTime() == 0;
    }

    void release() {
        this.zzaOU = null;
    }

    FunctionCallMacroCallback zzeu(String str) {
        FunctionCallMacroCallback functionCallMacroCallback;
        synchronized (this.zzaOV) {
            functionCallMacroCallback = (FunctionCallMacroCallback) this.zzaOV.get(str);
        }
        return functionCallMacroCallback;
    }

    FunctionCallTagCallback zzev(String str) {
        FunctionCallTagCallback functionCallTagCallback;
        synchronized (this.zzaOW) {
            functionCallTagCallback = (FunctionCallTagCallback) this.zzaOW.get(str);
        }
        return functionCallTagCallback;
    }

    void zzew(String str) {
        zzzD().zzew(str);
    }

    zzah zzex(String str) {
        if (zzcb.zzAv().zzAw().equals(zza.CONTAINER_DEBUG)) {
        }
        return new zzbo();
    }
}
