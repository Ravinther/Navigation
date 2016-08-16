package com.google.android.gms.common.images;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import com.google.android.gms.common.internal.zzw;

public final class WebImage implements SafeParcelable {
    public static final Creator<WebImage> CREATOR;
    private final int mVersionCode;
    private final Uri zzacb;
    private final int zznP;
    private final int zznQ;

    static {
        CREATOR = new zzb();
    }

    WebImage(int versionCode, Uri url, int width, int height) {
        this.mVersionCode = versionCode;
        this.zzacb = url;
        this.zznP = width;
        this.zznQ = height;
    }

    public int describeContents() {
        return 0;
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (other == null || !(other instanceof WebImage)) {
            return false;
        }
        WebImage webImage = (WebImage) other;
        return zzw.equal(this.zzacb, webImage.zzacb) && this.zznP == webImage.zznP && this.zznQ == webImage.zznQ;
    }

    public int getHeight() {
        return this.zznQ;
    }

    public Uri getUrl() {
        return this.zzacb;
    }

    int getVersionCode() {
        return this.mVersionCode;
    }

    public int getWidth() {
        return this.zznP;
    }

    public int hashCode() {
        return zzw.hashCode(this.zzacb, Integer.valueOf(this.zznP), Integer.valueOf(this.zznQ));
    }

    public String toString() {
        return String.format("Image %dx%d %s", new Object[]{Integer.valueOf(this.zznP), Integer.valueOf(this.zznQ), this.zzacb.toString()});
    }

    public void writeToParcel(Parcel out, int flags) {
        zzb.zza(this, out, flags);
    }
}
