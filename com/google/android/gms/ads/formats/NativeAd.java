package com.google.android.gms.ads.formats;

import android.graphics.drawable.Drawable;
import android.net.Uri;

public abstract class NativeAd {

    public interface Image {
        Drawable getDrawable();

        Uri getUri();
    }

    protected abstract Object zzaH();
}
