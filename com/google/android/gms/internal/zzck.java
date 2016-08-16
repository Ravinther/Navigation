package com.google.android.gms.internal;

import android.graphics.drawable.Drawable;
import android.net.Uri;
import com.google.android.gms.ads.formats.NativeAd.Image;
import com.google.android.gms.ads.internal.util.client.zzb;
import com.google.android.gms.dynamic.zzd;
import com.google.android.gms.dynamic.zze;

@zzgk
public class zzck implements Image {
    private final Drawable mDrawable;
    private final Uri mUri;
    private final zzcj zzww;

    public zzck(zzcj com_google_android_gms_internal_zzcj) {
        Drawable drawable;
        Uri uri = null;
        this.zzww = com_google_android_gms_internal_zzcj;
        try {
            zzd zzdr = this.zzww.zzdr();
            if (zzdr != null) {
                drawable = (Drawable) zze.zzp(zzdr);
                this.mDrawable = drawable;
                uri = this.zzww.getUri();
                this.mUri = uri;
            }
        } catch (Throwable e) {
            zzb.zzb("Failed to get drawable.", e);
        }
        Object obj = uri;
        this.mDrawable = drawable;
        try {
            uri = this.zzww.getUri();
        } catch (Throwable e2) {
            zzb.zzb("Failed to get uri.", e2);
        }
        this.mUri = uri;
    }

    public Drawable getDrawable() {
        return this.mDrawable;
    }

    public Uri getUri() {
        return this.mUri;
    }
}
