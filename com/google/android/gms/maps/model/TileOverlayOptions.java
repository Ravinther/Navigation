package com.google.android.gms.maps.model;

import android.os.IBinder;
import android.os.Parcel;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import com.google.android.gms.maps.model.internal.zzi;
import com.google.android.gms.maps.model.internal.zzi.zza;

public final class TileOverlayOptions implements SafeParcelable {
    public static final zzo CREATOR;
    private final int mVersionCode;
    private float zzaGZ;
    private zzi zzaHH;
    private TileProvider zzaHI;
    private boolean zzaHJ;
    private boolean zzaHa;

    /* renamed from: com.google.android.gms.maps.model.TileOverlayOptions.1 */
    class C09961 implements TileProvider {
        private final zzi zzaHK;
        final /* synthetic */ TileOverlayOptions zzaHL;

        C09961(TileOverlayOptions tileOverlayOptions) {
            this.zzaHL = tileOverlayOptions;
            this.zzaHK = this.zzaHL.zzaHH;
        }
    }

    static {
        CREATOR = new zzo();
    }

    public TileOverlayOptions() {
        this.zzaHa = true;
        this.zzaHJ = true;
        this.mVersionCode = 1;
    }

    TileOverlayOptions(int versionCode, IBinder delegate, boolean visible, float zIndex, boolean fadeIn) {
        this.zzaHa = true;
        this.zzaHJ = true;
        this.mVersionCode = versionCode;
        this.zzaHH = zza.zzcX(delegate);
        this.zzaHI = this.zzaHH == null ? null : new C09961(this);
        this.zzaHa = visible;
        this.zzaGZ = zIndex;
        this.zzaHJ = fadeIn;
    }

    public int describeContents() {
        return 0;
    }

    public boolean getFadeIn() {
        return this.zzaHJ;
    }

    int getVersionCode() {
        return this.mVersionCode;
    }

    public float getZIndex() {
        return this.zzaGZ;
    }

    public boolean isVisible() {
        return this.zzaHa;
    }

    public void writeToParcel(Parcel out, int flags) {
        zzo.zza(this, out, flags);
    }

    IBinder zzxf() {
        return this.zzaHH.asBinder();
    }
}
