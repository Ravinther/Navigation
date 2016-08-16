package com.google.android.gms.maps.model;

import android.os.IBinder;
import android.os.Parcel;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import com.google.android.gms.dynamic.zzd.zza;

public final class GroundOverlayOptions implements SafeParcelable {
    public static final zzc CREATOR;
    private final int mVersionCode;
    private float zzaGS;
    private float zzaGZ;
    private boolean zzaHa;
    private BitmapDescriptor zzaHc;
    private LatLng zzaHd;
    private float zzaHe;
    private float zzaHf;
    private LatLngBounds zzaHg;
    private float zzaHh;
    private float zzaHi;
    private float zzaHj;

    static {
        CREATOR = new zzc();
    }

    public GroundOverlayOptions() {
        this.zzaHa = true;
        this.zzaHh = 0.0f;
        this.zzaHi = 0.5f;
        this.zzaHj = 0.5f;
        this.mVersionCode = 1;
    }

    GroundOverlayOptions(int versionCode, IBinder wrappedImage, LatLng location, float width, float height, LatLngBounds bounds, float bearing, float zIndex, boolean visible, float transparency, float anchorU, float anchorV) {
        this.zzaHa = true;
        this.zzaHh = 0.0f;
        this.zzaHi = 0.5f;
        this.zzaHj = 0.5f;
        this.mVersionCode = versionCode;
        this.zzaHc = new BitmapDescriptor(zza.zzbk(wrappedImage));
        this.zzaHd = location;
        this.zzaHe = width;
        this.zzaHf = height;
        this.zzaHg = bounds;
        this.zzaGS = bearing;
        this.zzaGZ = zIndex;
        this.zzaHa = visible;
        this.zzaHh = transparency;
        this.zzaHi = anchorU;
        this.zzaHj = anchorV;
    }

    public int describeContents() {
        return 0;
    }

    public float getAnchorU() {
        return this.zzaHi;
    }

    public float getAnchorV() {
        return this.zzaHj;
    }

    public float getBearing() {
        return this.zzaGS;
    }

    public LatLngBounds getBounds() {
        return this.zzaHg;
    }

    public float getHeight() {
        return this.zzaHf;
    }

    public LatLng getLocation() {
        return this.zzaHd;
    }

    public float getTransparency() {
        return this.zzaHh;
    }

    int getVersionCode() {
        return this.mVersionCode;
    }

    public float getWidth() {
        return this.zzaHe;
    }

    public float getZIndex() {
        return this.zzaGZ;
    }

    public boolean isVisible() {
        return this.zzaHa;
    }

    public void writeToParcel(Parcel out, int flags) {
        zzc.zza(this, out, flags);
    }

    IBinder zzxc() {
        return this.zzaHc.zzwB().asBinder();
    }
}
