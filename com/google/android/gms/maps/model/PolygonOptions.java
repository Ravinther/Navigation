package com.google.android.gms.maps.model;

import android.os.Parcel;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import java.util.ArrayList;
import java.util.List;

public final class PolygonOptions implements SafeParcelable {
    public static final zzh CREATOR;
    private final int mVersionCode;
    private float zzaGW;
    private int zzaGX;
    private int zzaGY;
    private float zzaGZ;
    private final List<LatLng> zzaHB;
    private final List<List<LatLng>> zzaHC;
    private boolean zzaHD;
    private boolean zzaHa;

    static {
        CREATOR = new zzh();
    }

    public PolygonOptions() {
        this.zzaGW = 10.0f;
        this.zzaGX = -16777216;
        this.zzaGY = 0;
        this.zzaGZ = 0.0f;
        this.zzaHa = true;
        this.zzaHD = false;
        this.mVersionCode = 1;
        this.zzaHB = new ArrayList();
        this.zzaHC = new ArrayList();
    }

    PolygonOptions(int versionCode, List<LatLng> points, List holes, float strokeWidth, int strokeColor, int fillColor, float zIndex, boolean visible, boolean geodesic) {
        this.zzaGW = 10.0f;
        this.zzaGX = -16777216;
        this.zzaGY = 0;
        this.zzaGZ = 0.0f;
        this.zzaHa = true;
        this.zzaHD = false;
        this.mVersionCode = versionCode;
        this.zzaHB = points;
        this.zzaHC = holes;
        this.zzaGW = strokeWidth;
        this.zzaGX = strokeColor;
        this.zzaGY = fillColor;
        this.zzaGZ = zIndex;
        this.zzaHa = visible;
        this.zzaHD = geodesic;
    }

    public int describeContents() {
        return 0;
    }

    public int getFillColor() {
        return this.zzaGY;
    }

    public List<LatLng> getPoints() {
        return this.zzaHB;
    }

    public int getStrokeColor() {
        return this.zzaGX;
    }

    public float getStrokeWidth() {
        return this.zzaGW;
    }

    int getVersionCode() {
        return this.mVersionCode;
    }

    public float getZIndex() {
        return this.zzaGZ;
    }

    public boolean isGeodesic() {
        return this.zzaHD;
    }

    public boolean isVisible() {
        return this.zzaHa;
    }

    public void writeToParcel(Parcel out, int flags) {
        zzh.zza(this, out, flags);
    }

    List zzxe() {
        return this.zzaHC;
    }
}
