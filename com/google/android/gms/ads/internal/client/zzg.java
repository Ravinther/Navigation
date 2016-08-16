package com.google.android.gms.ads.internal.client;

import android.content.Context;
import android.location.Location;
import android.os.Bundle;
import com.google.ads.mediation.admob.AdMobAdapter;
import com.google.android.gms.ads.internal.zzp;
import com.google.android.gms.ads.search.SearchAdRequest;
import com.google.android.gms.internal.zzgk;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.List;

@zzgk
public class zzg {
    public static final zzg zzsF;

    static {
        zzsF = new zzg();
    }

    protected zzg() {
    }

    public static zzg zzcA() {
        return zzsF;
    }

    public AdRequestParcel zza(Context context, zzx com_google_android_gms_ads_internal_client_zzx) {
        Date birthday = com_google_android_gms_ads_internal_client_zzx.getBirthday();
        long time = birthday != null ? birthday.getTime() : -1;
        String contentUrl = com_google_android_gms_ads_internal_client_zzx.getContentUrl();
        int gender = com_google_android_gms_ads_internal_client_zzx.getGender();
        Collection keywords = com_google_android_gms_ads_internal_client_zzx.getKeywords();
        List unmodifiableList = !keywords.isEmpty() ? Collections.unmodifiableList(new ArrayList(keywords)) : null;
        boolean isTestDevice = com_google_android_gms_ads_internal_client_zzx.isTestDevice(context);
        int zzcP = com_google_android_gms_ads_internal_client_zzx.zzcP();
        Location location = com_google_android_gms_ads_internal_client_zzx.getLocation();
        Bundle networkExtrasBundle = com_google_android_gms_ads_internal_client_zzx.getNetworkExtrasBundle(AdMobAdapter.class);
        boolean manualImpressionsEnabled = com_google_android_gms_ads_internal_client_zzx.getManualImpressionsEnabled();
        String publisherProvidedId = com_google_android_gms_ads_internal_client_zzx.getPublisherProvidedId();
        SearchAdRequest zzcM = com_google_android_gms_ads_internal_client_zzx.zzcM();
        SearchAdRequestParcel searchAdRequestParcel = zzcM != null ? new SearchAdRequestParcel(zzcM) : null;
        String str = null;
        Context applicationContext = context.getApplicationContext();
        if (applicationContext != null) {
            str = zzp.zzbx().zza(Thread.currentThread().getStackTrace(), applicationContext.getPackageName());
        }
        return new AdRequestParcel(6, time, networkExtrasBundle, gender, unmodifiableList, isTestDevice, zzcP, manualImpressionsEnabled, publisherProvidedId, searchAdRequestParcel, location, contentUrl, com_google_android_gms_ads_internal_client_zzx.zzcO(), com_google_android_gms_ads_internal_client_zzx.getCustomTargeting(), Collections.unmodifiableList(new ArrayList(com_google_android_gms_ads_internal_client_zzx.zzcQ())), com_google_android_gms_ads_internal_client_zzx.zzcL(), str);
    }
}
