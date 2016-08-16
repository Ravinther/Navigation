package com.google.android.gms.location.places;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable.Creator;
import android.text.TextUtils;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import com.google.android.gms.common.internal.zzw;
import com.google.android.gms.common.internal.zzx;
import com.google.android.gms.maps.model.LatLng;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class AddPlaceRequest implements SafeParcelable {
    public static final Creator<AddPlaceRequest> CREATOR;
    private final String mName;
    final int mVersionCode;
    private final LatLng zzaDh;
    private final List<Integer> zzaDi;
    private final String zzaDj;
    private final Uri zzaDk;
    private final String zzanu;

    static {
        CREATOR = new zzb();
    }

    AddPlaceRequest(int versionCode, String name, LatLng latLng, String address, List<Integer> placeTypes, String phoneNumber, Uri websiteUri) {
        boolean z = false;
        this.mVersionCode = versionCode;
        this.mName = zzx.zzcs(name);
        this.zzaDh = (LatLng) zzx.zzv(latLng);
        this.zzanu = zzx.zzcs(address);
        this.zzaDi = new ArrayList((Collection) zzx.zzv(placeTypes));
        zzx.zzb(!this.zzaDi.isEmpty(), (Object) "At least one place type should be provided.");
        if (!(TextUtils.isEmpty(phoneNumber) && websiteUri == null)) {
            z = true;
        }
        zzx.zzb(z, (Object) "One of phone number or URI should be provided.");
        this.zzaDj = phoneNumber;
        this.zzaDk = websiteUri;
    }

    public int describeContents() {
        return 0;
    }

    public String getAddress() {
        return this.zzanu;
    }

    public LatLng getLatLng() {
        return this.zzaDh;
    }

    public String getName() {
        return this.mName;
    }

    public String getPhoneNumber() {
        return this.zzaDj;
    }

    public List<Integer> getPlaceTypes() {
        return this.zzaDi;
    }

    public Uri getWebsiteUri() {
        return this.zzaDk;
    }

    public String toString() {
        return zzw.zzu(this).zzg("name", this.mName).zzg("latLng", this.zzaDh).zzg("address", this.zzanu).zzg("placeTypes", this.zzaDi).zzg("phoneNumer", this.zzaDj).zzg("websiteUri", this.zzaDk).toString();
    }

    public void writeToParcel(Parcel parcel, int flags) {
        zzb.zza(this, parcel, flags);
    }
}
