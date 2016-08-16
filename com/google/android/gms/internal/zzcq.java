package com.google.android.gms.internal;

import android.os.IBinder;
import com.google.android.gms.ads.formats.NativeAd.Image;
import com.google.android.gms.ads.formats.NativeContentAd;
import com.google.android.gms.ads.internal.util.client.zzb;
import com.google.android.gms.dynamic.zzd;
import com.google.android.gms.internal.zzcj.zza;
import java.util.ArrayList;
import java.util.List;

@zzgk
public class zzcq extends NativeContentAd {
    private final zzcp zzwA;
    private final zzck zzwB;
    private final List<Image> zzwy;

    public zzcq(zzcp com_google_android_gms_internal_zzcp) {
        zzck com_google_android_gms_internal_zzck;
        this.zzwy = new ArrayList();
        this.zzwA = com_google_android_gms_internal_zzcp;
        try {
            for (Object zzd : this.zzwA.getImages()) {
                zzcj zzd2 = zzd(zzd);
                if (zzd2 != null) {
                    this.zzwy.add(new zzck(zzd2));
                }
            }
        } catch (Throwable e) {
            zzb.zzb("Failed to get image.", e);
        }
        try {
            zzcj zzdw = this.zzwA.zzdw();
            if (zzdw != null) {
                com_google_android_gms_internal_zzck = new zzck(zzdw);
                this.zzwB = com_google_android_gms_internal_zzck;
            }
        } catch (Throwable e2) {
            zzb.zzb("Failed to get icon.", e2);
        }
        com_google_android_gms_internal_zzck = null;
        this.zzwB = com_google_android_gms_internal_zzck;
    }

    public CharSequence getAdvertiser() {
        try {
            return this.zzwA.getAdvertiser();
        } catch (Throwable e) {
            zzb.zzb("Failed to get attribution.", e);
            return null;
        }
    }

    public CharSequence getBody() {
        try {
            return this.zzwA.getBody();
        } catch (Throwable e) {
            zzb.zzb("Failed to get body.", e);
            return null;
        }
    }

    public CharSequence getCallToAction() {
        try {
            return this.zzwA.getCallToAction();
        } catch (Throwable e) {
            zzb.zzb("Failed to get call to action.", e);
            return null;
        }
    }

    public CharSequence getHeadline() {
        try {
            return this.zzwA.getHeadline();
        } catch (Throwable e) {
            zzb.zzb("Failed to get headline.", e);
            return null;
        }
    }

    public List<Image> getImages() {
        return this.zzwy;
    }

    public Image getLogo() {
        return this.zzwB;
    }

    protected /* synthetic */ Object zzaH() {
        return zzdt();
    }

    zzcj zzd(Object obj) {
        return obj instanceof IBinder ? zza.zzt((IBinder) obj) : null;
    }

    protected zzd zzdt() {
        try {
            return this.zzwA.zzdt();
        } catch (Throwable e) {
            zzb.zzb("Failed to retrieve native ad engine.", e);
            return null;
        }
    }
}
