package com.google.android.gms.location.places.personalized;

import android.os.Parcel;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import com.google.android.gms.common.internal.zzw;
import com.sygic.aura.fragments.AbstractFragment;
import com.sygic.aura.poi.fragment.PoiFragment;
import java.util.List;

public class HereContent implements SafeParcelable {
    public static final zzb CREATOR;
    final int mVersionCode;
    private final String zzaFe;
    private final List<Action> zzaFf;

    public static final class Action implements SafeParcelable {
        public static final zza CREATOR;
        final int mVersionCode;
        private final String zzPb;
        private final String zzagU;

        static {
            CREATOR = new zza();
        }

        Action(int versionCode, String title, String uri) {
            this.mVersionCode = versionCode;
            this.zzagU = title;
            this.zzPb = uri;
        }

        public int describeContents() {
            zza com_google_android_gms_location_places_personalized_zza = CREATOR;
            return 0;
        }

        public boolean equals(Object object) {
            if (this == object) {
                return true;
            }
            if (!(object instanceof Action)) {
                return false;
            }
            Action action = (Action) object;
            return zzw.equal(this.zzagU, action.zzagU) && zzw.equal(this.zzPb, action.zzPb);
        }

        public String getTitle() {
            return this.zzagU;
        }

        public String getUri() {
            return this.zzPb;
        }

        public int hashCode() {
            return zzw.hashCode(this.zzagU, this.zzPb);
        }

        public String toString() {
            return zzw.zzu(this).zzg(AbstractFragment.ARG_TITLE, this.zzagU).zzg("uri", this.zzPb).toString();
        }

        public void writeToParcel(Parcel parcel, int flags) {
            zza com_google_android_gms_location_places_personalized_zza = CREATOR;
            zza.zza(this, parcel, flags);
        }
    }

    static {
        CREATOR = new zzb();
    }

    HereContent(int versionCode, String data, List<Action> actions) {
        this.mVersionCode = versionCode;
        this.zzaFe = data;
        this.zzaFf = actions;
    }

    public int describeContents() {
        zzb com_google_android_gms_location_places_personalized_zzb = CREATOR;
        return 0;
    }

    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (!(object instanceof HereContent)) {
            return false;
        }
        HereContent hereContent = (HereContent) object;
        return zzw.equal(this.zzaFe, hereContent.zzaFe) && zzw.equal(this.zzaFf, hereContent.zzaFf);
    }

    public List<Action> getActions() {
        return this.zzaFf;
    }

    public String getData() {
        return this.zzaFe;
    }

    public int hashCode() {
        return zzw.hashCode(this.zzaFe, this.zzaFf);
    }

    public String toString() {
        return zzw.zzu(this).zzg(PoiFragment.ARG_DATA, this.zzaFe).zzg("actions", this.zzaFf).toString();
    }

    public void writeToParcel(Parcel parcel, int flags) {
        zzb com_google_android_gms_location_places_personalized_zzb = CREATOR;
        zzb.zza(this, parcel, flags);
    }
}
