package com.sygic.sdk.api.model;

import android.os.Bundle;
import java.util.Arrays;

public class GpsPosition {
    private int Altitude;
    private double Course;
    private int Date;
    private double HDoP;
    private int Latitude;
    private int Longitude;
    private String MapIso;
    private double RealCourse;
    private int RoadOffset;
    private short Satellites;
    private double Speed;
    private double Time;
    private SatelliteInfo[] satellitesInfo;

    public static Bundle writeBundle(GpsPosition pos) {
        if (pos == null) {
            return null;
        }
        Bundle b = new Bundle();
        b.putShort("satellites", pos.getSatellites());
        b.putInt("latitude", pos.getLatitude());
        b.putInt("longtitude", pos.getLongitude());
        b.putInt("altitude", pos.getAltitude());
        b.putDouble("course", pos.getCourse());
        b.putDouble("realCourse", pos.getRealCourse());
        b.putDouble("speed", pos.getSpeed());
        b.putDouble("hdop", pos.getHdop());
        b.putDouble("time", pos.getTime());
        b.putInt("date", pos.getDate());
        b.putInt("roadOffset", pos.getRoadOffset());
        b.putString("mapIso", pos.getMapIso());
        b.putInt("satelliteInfoSize", pos.getSatellitesInfo().length);
        int i = 0;
        for (SatelliteInfo sat : pos.getSatellitesInfo()) {
            b.putBundle("satelliteInfo" + i, SatelliteInfo.writeBundle(sat));
            i++;
        }
        return b;
    }

    public int getDate() {
        return this.Date;
    }

    public short getSatellites() {
        return this.Satellites;
    }

    public int getLatitude() {
        return this.Latitude;
    }

    public int getLongitude() {
        return this.Longitude;
    }

    public int getAltitude() {
        return this.Altitude;
    }

    public double getCourse() {
        return this.Course;
    }

    public double getRealCourse() {
        return this.RealCourse;
    }

    public double getSpeed() {
        return this.Speed;
    }

    public double getHdop() {
        return this.HDoP;
    }

    public double getTime() {
        return this.Time;
    }

    public int getRoadOffset() {
        return this.RoadOffset;
    }

    public SatelliteInfo[] getSatellitesInfo() {
        return this.satellitesInfo;
    }

    public String getMapIso() {
        return this.MapIso;
    }

    public String toString() {
        return "GpsPosition [Satellites=" + this.Satellites + ", Latitude=" + this.Latitude + ", Longitude=" + this.Longitude + ", Altitude=" + this.Altitude + ", Course=" + this.Course + ", RealCourse=" + this.RealCourse + ", Speed=" + this.Speed + ", HDoP=" + this.HDoP + ", Time=" + this.Time + ", Date=" + this.Date + ", RoadOffset=" + this.RoadOffset + ", MapIso=" + this.MapIso + ", SatellitesInfo=" + Arrays.toString(this.satellitesInfo) + "]";
    }
}
