package com.google.android.gms.maps;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Parcel;
import android.util.AttributeSet;
import com.google.android.gms.C0568R;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import com.google.android.gms.maps.internal.zza;
import com.google.android.gms.maps.model.CameraPosition;

public final class GoogleMapOptions implements SafeParcelable {
    public static final zza CREATOR;
    private final int mVersionCode;
    private Boolean zzaFI;
    private Boolean zzaFJ;
    private int zzaFK;
    private CameraPosition zzaFL;
    private Boolean zzaFM;
    private Boolean zzaFN;
    private Boolean zzaFO;
    private Boolean zzaFP;
    private Boolean zzaFQ;
    private Boolean zzaFR;
    private Boolean zzaFS;
    private Boolean zzaFT;

    static {
        CREATOR = new zza();
    }

    public GoogleMapOptions() {
        this.zzaFK = -1;
        this.mVersionCode = 1;
    }

    GoogleMapOptions(int versionCode, byte zOrderOnTop, byte useViewLifecycleInFragment, int mapType, CameraPosition camera, byte zoomControlsEnabled, byte compassEnabled, byte scrollGesturesEnabled, byte zoomGesturesEnabled, byte tiltGesturesEnabled, byte rotateGesturesEnabled, byte liteMode, byte mapToolbarEnabled) {
        this.zzaFK = -1;
        this.mVersionCode = versionCode;
        this.zzaFI = zza.zza(zOrderOnTop);
        this.zzaFJ = zza.zza(useViewLifecycleInFragment);
        this.zzaFK = mapType;
        this.zzaFL = camera;
        this.zzaFM = zza.zza(zoomControlsEnabled);
        this.zzaFN = zza.zza(compassEnabled);
        this.zzaFO = zza.zza(scrollGesturesEnabled);
        this.zzaFP = zza.zza(zoomGesturesEnabled);
        this.zzaFQ = zza.zza(tiltGesturesEnabled);
        this.zzaFR = zza.zza(rotateGesturesEnabled);
        this.zzaFS = zza.zza(liteMode);
        this.zzaFT = zza.zza(mapToolbarEnabled);
    }

    public static GoogleMapOptions createFromAttributes(Context context, AttributeSet attrs) {
        if (attrs == null) {
            return null;
        }
        TypedArray obtainAttributes = context.getResources().obtainAttributes(attrs, C0568R.styleable.MapAttrs);
        GoogleMapOptions googleMapOptions = new GoogleMapOptions();
        if (obtainAttributes.hasValue(C0568R.styleable.MapAttrs_mapType)) {
            googleMapOptions.mapType(obtainAttributes.getInt(C0568R.styleable.MapAttrs_mapType, -1));
        }
        if (obtainAttributes.hasValue(C0568R.styleable.MapAttrs_zOrderOnTop)) {
            googleMapOptions.zOrderOnTop(obtainAttributes.getBoolean(C0568R.styleable.MapAttrs_zOrderOnTop, false));
        }
        if (obtainAttributes.hasValue(C0568R.styleable.MapAttrs_useViewLifecycle)) {
            googleMapOptions.useViewLifecycleInFragment(obtainAttributes.getBoolean(C0568R.styleable.MapAttrs_useViewLifecycle, false));
        }
        if (obtainAttributes.hasValue(C0568R.styleable.MapAttrs_uiCompass)) {
            googleMapOptions.compassEnabled(obtainAttributes.getBoolean(C0568R.styleable.MapAttrs_uiCompass, true));
        }
        if (obtainAttributes.hasValue(C0568R.styleable.MapAttrs_uiRotateGestures)) {
            googleMapOptions.rotateGesturesEnabled(obtainAttributes.getBoolean(C0568R.styleable.MapAttrs_uiRotateGestures, true));
        }
        if (obtainAttributes.hasValue(C0568R.styleable.MapAttrs_uiScrollGestures)) {
            googleMapOptions.scrollGesturesEnabled(obtainAttributes.getBoolean(C0568R.styleable.MapAttrs_uiScrollGestures, true));
        }
        if (obtainAttributes.hasValue(C0568R.styleable.MapAttrs_uiTiltGestures)) {
            googleMapOptions.tiltGesturesEnabled(obtainAttributes.getBoolean(C0568R.styleable.MapAttrs_uiTiltGestures, true));
        }
        if (obtainAttributes.hasValue(C0568R.styleable.MapAttrs_uiZoomGestures)) {
            googleMapOptions.zoomGesturesEnabled(obtainAttributes.getBoolean(C0568R.styleable.MapAttrs_uiZoomGestures, true));
        }
        if (obtainAttributes.hasValue(C0568R.styleable.MapAttrs_uiZoomControls)) {
            googleMapOptions.zoomControlsEnabled(obtainAttributes.getBoolean(C0568R.styleable.MapAttrs_uiZoomControls, true));
        }
        if (obtainAttributes.hasValue(C0568R.styleable.MapAttrs_liteMode)) {
            googleMapOptions.liteMode(obtainAttributes.getBoolean(C0568R.styleable.MapAttrs_liteMode, false));
        }
        if (obtainAttributes.hasValue(C0568R.styleable.MapAttrs_uiMapToolbar)) {
            googleMapOptions.mapToolbarEnabled(obtainAttributes.getBoolean(C0568R.styleable.MapAttrs_uiMapToolbar, true));
        }
        googleMapOptions.camera(CameraPosition.createFromAttributes(context, attrs));
        obtainAttributes.recycle();
        return googleMapOptions;
    }

    public GoogleMapOptions camera(CameraPosition camera) {
        this.zzaFL = camera;
        return this;
    }

    public GoogleMapOptions compassEnabled(boolean enabled) {
        this.zzaFN = Boolean.valueOf(enabled);
        return this;
    }

    public int describeContents() {
        return 0;
    }

    public CameraPosition getCamera() {
        return this.zzaFL;
    }

    public int getMapType() {
        return this.zzaFK;
    }

    int getVersionCode() {
        return this.mVersionCode;
    }

    public GoogleMapOptions liteMode(boolean enabled) {
        this.zzaFS = Boolean.valueOf(enabled);
        return this;
    }

    public GoogleMapOptions mapToolbarEnabled(boolean enabled) {
        this.zzaFT = Boolean.valueOf(enabled);
        return this;
    }

    public GoogleMapOptions mapType(int mapType) {
        this.zzaFK = mapType;
        return this;
    }

    public GoogleMapOptions rotateGesturesEnabled(boolean enabled) {
        this.zzaFR = Boolean.valueOf(enabled);
        return this;
    }

    public GoogleMapOptions scrollGesturesEnabled(boolean enabled) {
        this.zzaFO = Boolean.valueOf(enabled);
        return this;
    }

    public GoogleMapOptions tiltGesturesEnabled(boolean enabled) {
        this.zzaFQ = Boolean.valueOf(enabled);
        return this;
    }

    public GoogleMapOptions useViewLifecycleInFragment(boolean useViewLifecycleInFragment) {
        this.zzaFJ = Boolean.valueOf(useViewLifecycleInFragment);
        return this;
    }

    public void writeToParcel(Parcel out, int flags) {
        zza.zza(this, out, flags);
    }

    public GoogleMapOptions zOrderOnTop(boolean zOrderOnTop) {
        this.zzaFI = Boolean.valueOf(zOrderOnTop);
        return this;
    }

    public GoogleMapOptions zoomControlsEnabled(boolean enabled) {
        this.zzaFM = Boolean.valueOf(enabled);
        return this;
    }

    public GoogleMapOptions zoomGesturesEnabled(boolean enabled) {
        this.zzaFP = Boolean.valueOf(enabled);
        return this;
    }

    byte zzwE() {
        return zza.zze(this.zzaFI);
    }

    byte zzwF() {
        return zza.zze(this.zzaFJ);
    }

    byte zzwG() {
        return zza.zze(this.zzaFM);
    }

    byte zzwH() {
        return zza.zze(this.zzaFN);
    }

    byte zzwI() {
        return zza.zze(this.zzaFO);
    }

    byte zzwJ() {
        return zza.zze(this.zzaFP);
    }

    byte zzwK() {
        return zza.zze(this.zzaFQ);
    }

    byte zzwL() {
        return zza.zze(this.zzaFR);
    }

    byte zzwM() {
        return zza.zze(this.zzaFS);
    }

    byte zzwN() {
        return zza.zze(this.zzaFT);
    }
}
