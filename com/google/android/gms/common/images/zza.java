package com.google.android.gms.common.images;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import com.google.android.gms.common.images.ImageManager.OnImageLoadedListener;
import com.google.android.gms.common.internal.zzb;
import com.google.android.gms.common.internal.zzw;
import com.google.android.gms.internal.zzks;
import com.google.android.gms.internal.zzku;
import java.lang.ref.WeakReference;

public abstract class zza {
    final zza zzabQ;
    protected int zzabS;
    protected OnImageLoadedListener zzabU;
    protected int zzabY;

    static final class zza {
        public final Uri uri;

        public zza(Uri uri) {
            this.uri = uri;
        }

        public boolean equals(Object obj) {
            if (obj instanceof zza) {
                return this == obj ? true : zzw.equal(((zza) obj).uri, this.uri);
            } else {
                return false;
            }
        }

        public int hashCode() {
            return zzw.hashCode(this.uri);
        }
    }

    public static final class zzc extends zza {
        private WeakReference<OnImageLoadedListener> zzaca;

        public boolean equals(Object obj) {
            if (!(obj instanceof zzc)) {
                return false;
            }
            if (this == obj) {
                return true;
            }
            zzc com_google_android_gms_common_images_zza_zzc = (zzc) obj;
            OnImageLoadedListener onImageLoadedListener = (OnImageLoadedListener) this.zzaca.get();
            OnImageLoadedListener onImageLoadedListener2 = (OnImageLoadedListener) com_google_android_gms_common_images_zza_zzc.zzaca.get();
            boolean z = onImageLoadedListener2 != null && onImageLoadedListener != null && zzw.equal(onImageLoadedListener2, onImageLoadedListener) && zzw.equal(com_google_android_gms_common_images_zza_zzc.zzabQ, this.zzabQ);
            return z;
        }

        public int hashCode() {
            return zzw.hashCode(this.zzabQ);
        }

        protected void zza(Drawable drawable, boolean z, boolean z2, boolean z3) {
            if (!z2) {
                OnImageLoadedListener onImageLoadedListener = (OnImageLoadedListener) this.zzaca.get();
                if (onImageLoadedListener != null) {
                    onImageLoadedListener.onImageLoaded(this.zzabQ.uri, drawable, z3);
                }
            }
        }
    }

    private Drawable zza(Context context, zzku com_google_android_gms_internal_zzku, int i) {
        Resources resources = context.getResources();
        if (this.zzabY <= 0) {
            return resources.getDrawable(i);
        }
        com.google.android.gms.internal.zzku.zza com_google_android_gms_internal_zzku_zza = new com.google.android.gms.internal.zzku.zza(i, this.zzabY);
        Drawable drawable = (Drawable) com_google_android_gms_internal_zzku.get(com_google_android_gms_internal_zzku_zza);
        if (drawable != null) {
            return drawable;
        }
        drawable = resources.getDrawable(i);
        if ((this.zzabY & 1) != 0) {
            drawable = zza(resources, drawable);
        }
        com_google_android_gms_internal_zzku.put(com_google_android_gms_internal_zzku_zza, drawable);
        return drawable;
    }

    protected Drawable zza(Resources resources, Drawable drawable) {
        return zzks.zza(resources, drawable);
    }

    void zza(Context context, Bitmap bitmap, boolean z) {
        zzb.zzr(bitmap);
        if ((this.zzabY & 1) != 0) {
            bitmap = zzks.zza(bitmap);
        }
        Drawable bitmapDrawable = new BitmapDrawable(context.getResources(), bitmap);
        if (this.zzabU != null) {
            this.zzabU.onImageLoaded(this.zzabQ.uri, bitmapDrawable, true);
        }
        zza(bitmapDrawable, z, false, true);
    }

    void zza(Context context, zzku com_google_android_gms_internal_zzku, boolean z) {
        Drawable drawable = null;
        if (this.zzabS != 0) {
            drawable = zza(context, com_google_android_gms_internal_zzku, this.zzabS);
        }
        if (this.zzabU != null) {
            this.zzabU.onImageLoaded(this.zzabQ.uri, drawable, false);
        }
        zza(drawable, z, false, false);
    }

    protected abstract void zza(Drawable drawable, boolean z, boolean z2, boolean z3);
}
