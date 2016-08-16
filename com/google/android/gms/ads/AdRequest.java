package com.google.android.gms.ads;

import android.location.Location;
import android.os.Bundle;
import com.google.ads.mediation.admob.AdMobAdapter;
import com.google.android.gms.ads.internal.client.zzx;
import com.google.android.gms.ads.internal.client.zzx.zza;
import com.google.android.gms.ads.mediation.MediationAdapter;
import java.util.Date;

public final class AdRequest {
    public static final String DEVICE_ID_EMULATOR;
    private final zzx zznN;

    public static final class Builder {
        private final zza zznO;

        public Builder() {
            this.zznO = new zza();
            this.zznO.zzG(AdRequest.DEVICE_ID_EMULATOR);
        }

        public Builder addKeyword(String keyword) {
            this.zznO.zzF(keyword);
            return this;
        }

        public Builder addNetworkExtrasBundle(Class<? extends MediationAdapter> adapterClass, Bundle networkExtras) {
            this.zznO.zza(adapterClass, networkExtras);
            if (adapterClass.equals(AdMobAdapter.class) && networkExtras.getBoolean("_emulatorLiveAds")) {
                this.zznO.zzH(AdRequest.DEVICE_ID_EMULATOR);
            }
            return this;
        }

        public Builder addTestDevice(String deviceId) {
            this.zznO.zzG(deviceId);
            return this;
        }

        public AdRequest build() {
            return new AdRequest();
        }

        public Builder setBirthday(Date birthday) {
            this.zznO.zza(birthday);
            return this;
        }

        public Builder setGender(int gender) {
            this.zznO.zzm(gender);
            return this;
        }

        public Builder setLocation(Location location) {
            this.zznO.zza(location);
            return this;
        }

        public Builder tagForChildDirectedTreatment(boolean tagForChildDirectedTreatment) {
            this.zznO.zzj(tagForChildDirectedTreatment);
            return this;
        }
    }

    static {
        DEVICE_ID_EMULATOR = zzx.DEVICE_ID_EMULATOR;
    }

    private AdRequest(Builder builder) {
        this.zznN = new zzx(builder.zznO);
    }

    public zzx zzaF() {
        return this.zznN;
    }
}
