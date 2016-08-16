package com.google.ads.mediation;

import android.location.Location;
import com.google.ads.AdRequest.Gender;
import java.util.Date;
import java.util.Set;

@Deprecated
public class MediationAdRequest {
    private final Date zzaT;
    private final Gender zzaU;
    private final Set<String> zzaV;
    private final boolean zzaW;
    private final Location zzaX;

    public MediationAdRequest(Date birthday, Gender gender, Set<String> keywords, boolean isTesting, Location location) {
        this.zzaT = birthday;
        this.zzaU = gender;
        this.zzaV = keywords;
        this.zzaW = isTesting;
        this.zzaX = location;
    }
}
