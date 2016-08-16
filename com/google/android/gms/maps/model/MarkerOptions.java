package com.google.android.gms.maps.model;

import android.os.IBinder;
import android.os.Parcel;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import com.google.android.gms.dynamic.zzd.zza;

public final class MarkerOptions implements SafeParcelable {
    public static final zzf CREATOR;
    private float mAlpha;
    private final int mVersionCode;
    private LatLng zzaGx;
    private boolean zzaHa;
    private float zzaHi;
    private float zzaHj;
    private String zzaHr;
    private BitmapDescriptor zzaHs;
    private boolean zzaHt;
    private boolean zzaHu;
    private float zzaHv;
    private float zzaHw;
    private float zzaHx;
    private String zzagU;

    static {
        CREATOR = new zzf();
    }

    public MarkerOptions() {
        this.zzaHi = 0.5f;
        this.zzaHj = 1.0f;
        this.zzaHa = true;
        this.zzaHu = false;
        this.zzaHv = 0.0f;
        this.zzaHw = 0.5f;
        this.zzaHx = 0.0f;
        this.mAlpha = 1.0f;
        this.mVersionCode = 1;
    }

    MarkerOptions(int versionCode, LatLng position, String title, String snippet, IBinder wrappedIcon, float anchorU, float anchorV, boolean draggable, boolean visible, boolean flat, float rotation, float infoWindowAnchorU, float infoWindowAnchorV, float alpha) {
        this.zzaHi = 0.5f;
        this.zzaHj = 1.0f;
        this.zzaHa = true;
        this.zzaHu = false;
        this.zzaHv = 0.0f;
        this.zzaHw = 0.5f;
        this.zzaHx = 0.0f;
        this.mAlpha = 1.0f;
        this.mVersionCode = versionCode;
        this.zzaGx = position;
        this.zzagU = title;
        this.zzaHr = snippet;
        this.zzaHs = wrappedIcon == null ? null : new BitmapDescriptor(zza.zzbk(wrappedIcon));
        this.zzaHi = anchorU;
        this.zzaHj = anchorV;
        this.zzaHt = draggable;
        this.zzaHa = visible;
        this.zzaHu = flat;
        this.zzaHv = rotation;
        this.zzaHw = infoWindowAnchorU;
        this.zzaHx = infoWindowAnchorV;
        this.mAlpha = alpha;
    }

    public int describeContents() {
        return 0;
    }

    public float getAlpha() {
        return this.mAlpha;
    }

    public float getAnchorU() {
        return this.zzaHi;
    }

    public float getAnchorV() {
        return this.zzaHj;
    }

    public float getInfoWindowAnchorU() {
        return this.zzaHw;
    }

    public float getInfoWindowAnchorV() {
        return this.zzaHx;
    }

    public LatLng getPosition() {
        return this.zzaGx;
    }

    public float getRotation() {
        return this.zzaHv;
    }

    public String getSnippet() {
        return this.zzaHr;
    }

    public String getTitle() {
        return this.zzagU;
    }

    int getVersionCode() {
        return this.mVersionCode;
    }

    public boolean isDraggable() {
        return this.zzaHt;
    }

    public boolean isFlat() {
        return this.zzaHu;
    }

    public boolean isVisible() {
        return this.zzaHa;
    }

    public void writeToParcel(Parcel out, int flags) {
        zzf.zza(this, out, flags);
    }

    IBinder zzxd() {
        return this.zzaHs == null ? null : this.zzaHs.zzwB().asBinder();
    }
}
