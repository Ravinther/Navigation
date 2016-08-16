package com.google.android.gms.ads.internal.client;

import android.content.Context;
import android.location.Location;
import android.os.Bundle;
import com.google.android.gms.ads.mediation.MediationAdapter;
import com.google.android.gms.ads.search.SearchAdRequest;
import com.google.android.gms.internal.zzgk;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@zzgk
public final class zzx {
    public static final String DEVICE_ID_EMULATOR;
    private final Date zzaT;
    private final Set<String> zzaV;
    private final Location zzaX;
    private final boolean zzoM;
    private final String zzsV;
    private final int zzsW;
    private final Bundle zzsX;
    private final Map<Class<? extends Object>, Object> zzsY;
    private final String zzsZ;
    private final String zzta;
    private final SearchAdRequest zztb;
    private final int zztc;
    private final Set<String> zztd;
    private final Bundle zzte;
    private final Set<String> zztf;

    public static final class zza {
        private Date zzaT;
        private Location zzaX;
        private boolean zzoM;
        private String zzsV;
        private int zzsW;
        private final Bundle zzsX;
        private String zzsZ;
        private String zzta;
        private int zztc;
        private final Bundle zzte;
        private final HashSet<String> zztg;
        private final HashMap<Class<? extends Object>, Object> zzth;
        private final HashSet<String> zzti;
        private final HashSet<String> zztj;

        public zza() {
            this.zztg = new HashSet();
            this.zzsX = new Bundle();
            this.zzth = new HashMap();
            this.zzti = new HashSet();
            this.zzte = new Bundle();
            this.zztj = new HashSet();
            this.zzsW = -1;
            this.zzoM = false;
            this.zztc = -1;
        }

        public void zzF(String str) {
            this.zztg.add(str);
        }

        public void zzG(String str) {
            this.zzti.add(str);
        }

        public void zzH(String str) {
            this.zzti.remove(str);
        }

        public void zza(Location location) {
            this.zzaX = location;
        }

        public void zza(Class<? extends MediationAdapter> cls, Bundle bundle) {
            this.zzsX.putBundle(cls.getName(), bundle);
        }

        public void zza(Date date) {
            this.zzaT = date;
        }

        public void zzj(boolean z) {
            this.zztc = z ? 1 : 0;
        }

        public void zzm(int i) {
            this.zzsW = i;
        }
    }

    static {
        DEVICE_ID_EMULATOR = zzk.zzcE().zzaB("emulator");
    }

    public zzx(zza com_google_android_gms_ads_internal_client_zzx_zza) {
        this(com_google_android_gms_ads_internal_client_zzx_zza, null);
    }

    public zzx(zza com_google_android_gms_ads_internal_client_zzx_zza, SearchAdRequest searchAdRequest) {
        this.zzaT = com_google_android_gms_ads_internal_client_zzx_zza.zzaT;
        this.zzsV = com_google_android_gms_ads_internal_client_zzx_zza.zzsV;
        this.zzsW = com_google_android_gms_ads_internal_client_zzx_zza.zzsW;
        this.zzaV = Collections.unmodifiableSet(com_google_android_gms_ads_internal_client_zzx_zza.zztg);
        this.zzaX = com_google_android_gms_ads_internal_client_zzx_zza.zzaX;
        this.zzoM = com_google_android_gms_ads_internal_client_zzx_zza.zzoM;
        this.zzsX = com_google_android_gms_ads_internal_client_zzx_zza.zzsX;
        this.zzsY = Collections.unmodifiableMap(com_google_android_gms_ads_internal_client_zzx_zza.zzth);
        this.zzsZ = com_google_android_gms_ads_internal_client_zzx_zza.zzsZ;
        this.zzta = com_google_android_gms_ads_internal_client_zzx_zza.zzta;
        this.zztb = searchAdRequest;
        this.zztc = com_google_android_gms_ads_internal_client_zzx_zza.zztc;
        this.zztd = Collections.unmodifiableSet(com_google_android_gms_ads_internal_client_zzx_zza.zzti);
        this.zzte = com_google_android_gms_ads_internal_client_zzx_zza.zzte;
        this.zztf = Collections.unmodifiableSet(com_google_android_gms_ads_internal_client_zzx_zza.zztj);
    }

    public Date getBirthday() {
        return this.zzaT;
    }

    public String getContentUrl() {
        return this.zzsV;
    }

    public Bundle getCustomTargeting() {
        return this.zzte;
    }

    public int getGender() {
        return this.zzsW;
    }

    public Set<String> getKeywords() {
        return this.zzaV;
    }

    public Location getLocation() {
        return this.zzaX;
    }

    public boolean getManualImpressionsEnabled() {
        return this.zzoM;
    }

    public Bundle getNetworkExtrasBundle(Class<? extends MediationAdapter> adapterClass) {
        return this.zzsX.getBundle(adapterClass.getName());
    }

    public String getPublisherProvidedId() {
        return this.zzsZ;
    }

    public boolean isTestDevice(Context context) {
        return this.zztd.contains(zzk.zzcE().zzQ(context));
    }

    public String zzcL() {
        return this.zzta;
    }

    public SearchAdRequest zzcM() {
        return this.zztb;
    }

    public Map<Class<? extends Object>, Object> zzcN() {
        return this.zzsY;
    }

    public Bundle zzcO() {
        return this.zzsX;
    }

    public int zzcP() {
        return this.zztc;
    }

    public Set<String> zzcQ() {
        return this.zztf;
    }
}
