package com.sygic.aura.clazz;

import android.location.GpsSatellite;

public class SatelliteInfo {
    public float fAzimuth;
    public float fElevation;
    public float fStrength;
    public int nInUse;
    public int nPrn;

    public SatelliteInfo() {
        this.nPrn = 0;
        this.fAzimuth = 0.0f;
        this.fElevation = 0.0f;
        this.fStrength = 0.0f;
        this.nInUse = 0;
    }

    public SatelliteInfo(GpsSatellite gpsSatellite) {
        this.nPrn = gpsSatellite.getPrn();
        this.fAzimuth = gpsSatellite.getAzimuth();
        this.fElevation = gpsSatellite.getElevation();
        this.fStrength = gpsSatellite.getSnr();
        this.nInUse = gpsSatellite.usedInFix() ? 1 : 0;
    }
}
