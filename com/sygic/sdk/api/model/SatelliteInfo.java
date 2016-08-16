package com.sygic.sdk.api.model;

import android.os.Bundle;

public class SatelliteInfo {
    private int Azimuth;
    private int Elevation;
    private int Quality;
    private int SatelliteId;
    private boolean usedForFix;

    public static Bundle writeBundle(SatelliteInfo info) {
        if (info == null) {
            return null;
        }
        Bundle b = new Bundle();
        b.putInt("elevation", info.getElevation());
        b.putInt("azimuth", info.getAzimuth());
        b.putInt("quality", info.getQuality());
        b.putInt("satelliteId", info.getSatelliteId());
        b.putBoolean("usedForFix", info.isUsedForFix());
        return b;
    }

    public String toString() {
        return "SatelliteInfo [Elevation=" + this.Elevation + ", Azimuth=" + this.Azimuth + ", Quality=" + this.Quality + ", SatelliteId=" + this.SatelliteId + ", UsedForFix=" + this.usedForFix + "]";
    }

    public int getElevation() {
        return this.Elevation;
    }

    public int getAzimuth() {
        return this.Azimuth;
    }

    public int getQuality() {
        return this.Quality;
    }

    public int getSatelliteId() {
        return this.SatelliteId;
    }

    public boolean isUsedForFix() {
        return this.usedForFix;
    }
}
