package com.google.android.gms.location;

import android.os.Parcel;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public final class GestureRequest implements SafeParcelable {
    public static final zzb CREATOR;
    private static final List<Integer> zzaBN;
    private static final List<Integer> zzaBO;
    private static final List<Integer> zzaBP;
    private static final List<Integer> zzaBQ;
    private final int mVersionCode;
    private final List<Integer> zzaBR;

    static {
        zzaBN = Collections.unmodifiableList(Arrays.asList(new Integer[]{Integer.valueOf(1), Integer.valueOf(2), Integer.valueOf(3), Integer.valueOf(4), Integer.valueOf(5), Integer.valueOf(6), Integer.valueOf(7), Integer.valueOf(8), Integer.valueOf(9), Integer.valueOf(10), Integer.valueOf(11), Integer.valueOf(12), Integer.valueOf(13), Integer.valueOf(14), Integer.valueOf(15), Integer.valueOf(16), Integer.valueOf(17), Integer.valueOf(18), Integer.valueOf(19)}));
        zzaBO = Collections.unmodifiableList(Arrays.asList(new Integer[]{Integer.valueOf(1)}));
        zzaBP = Collections.unmodifiableList(Arrays.asList(new Integer[]{Integer.valueOf(2), Integer.valueOf(4), Integer.valueOf(6), Integer.valueOf(8), Integer.valueOf(10), Integer.valueOf(12), Integer.valueOf(14), Integer.valueOf(16), Integer.valueOf(18), Integer.valueOf(19)}));
        zzaBQ = Collections.unmodifiableList(Arrays.asList(new Integer[]{Integer.valueOf(3), Integer.valueOf(5), Integer.valueOf(7), Integer.valueOf(9), Integer.valueOf(11), Integer.valueOf(13), Integer.valueOf(15), Integer.valueOf(17)}));
        CREATOR = new zzb();
    }

    GestureRequest(int versionCode, List<Integer> gestureTypes) {
        this.mVersionCode = versionCode;
        this.zzaBR = gestureTypes;
    }

    public int describeContents() {
        return 0;
    }

    public int getVersionCode() {
        return this.mVersionCode;
    }

    public void writeToParcel(Parcel out, int flags) {
        zzb.zza(this, out, flags);
    }

    public List<Integer> zzvI() {
        return this.zzaBR;
    }
}
