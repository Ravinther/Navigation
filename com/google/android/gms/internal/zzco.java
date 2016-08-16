package com.google.android.gms.internal;

import android.os.IBinder;
import com.google.android.gms.ads.formats.NativeAd.Image;
import com.google.android.gms.ads.formats.NativeAppInstallAd;
import com.google.android.gms.ads.internal.util.client.zzb;
import com.google.android.gms.dynamic.zzd;
import com.google.android.gms.internal.zzcj.zza;
import java.util.ArrayList;
import java.util.List;

@zzgk
public class zzco extends NativeAppInstallAd {
    private final zzcn zzwx;
    private final List<Image> zzwy;
    private final zzck zzwz;

    public zzco(zzcn com_google_android_gms_internal_zzcn) {
        zzck com_google_android_gms_internal_zzck;
        this.zzwy = new ArrayList();
        this.zzwx = com_google_android_gms_internal_zzcn;
        try {
            for (Object zzd : this.zzwx.getImages()) {
                zzcj zzd2 = zzd(zzd);
                if (zzd2 != null) {
                    this.zzwy.add(new zzck(zzd2));
                }
            }
        } catch (Throwable e) {
            zzb.zzb("Failed to get image.", e);
        }
        try {
            zzcj zzds = this.zzwx.zzds();
            if (zzds != null) {
                com_google_android_gms_internal_zzck = new zzck(zzds);
                this.zzwz = com_google_android_gms_internal_zzck;
            }
        } catch (Throwable e2) {
            zzb.zzb("Failed to get icon.", e2);
        }
        com_google_android_gms_internal_zzck = null;
        this.zzwz = com_google_android_gms_internal_zzck;
    }

    public CharSequence getBody() {
        try {
            return this.zzwx.getBody();
        } catch (Throwable e) {
            zzb.zzb("Failed to get body.", e);
            return null;
        }
    }

    public CharSequence getCallToAction() {
        try {
            return this.zzwx.getCallToAction();
        } catch (Throwable e) {
            zzb.zzb("Failed to get call to action.", e);
            return null;
        }
    }

    public CharSequence getHeadline() {
        try {
            return this.zzwx.getHeadline();
        } catch (Throwable e) {
            zzb.zzb("Failed to get headline.", e);
            return null;
        }
    }

    public Image getIcon() {
        return this.zzwz;
    }

    public List<Image> getImages() {
        return this.zzwy;
    }

    public CharSequence getPrice() {
        try {
            return this.zzwx.getPrice();
        } catch (Throwable e) {
            zzb.zzb("Failed to get price.", e);
            return null;
        }
    }

    public Double getStarRating() {
        Double d = null;
        try {
            double starRating = this.zzwx.getStarRating();
            if (starRating != -1.0d) {
                d = Double.valueOf(starRating);
            }
        } catch (Throwable e) {
            zzb.zzb("Failed to get star rating.", e);
        }
        return d;
    }

    public CharSequence getStore() {
        try {
            return this.zzwx.getStore();
        } catch (Throwable e) {
            zzb.zzb("Failed to get store", e);
            return null;
        }
    }

    protected /* synthetic */ Object zzaH() {
        return zzdt();
    }

    zzcj zzd(Object obj) {
        return obj instanceof IBinder ? zza.zzt((IBinder) obj) : null;
    }

    protected zzd zzdt() {
        try {
            return this.zzwx.zzdt();
        } catch (Throwable e) {
            zzb.zzb("Failed to retrieve native ad engine.", e);
            return null;
        }
    }
}
