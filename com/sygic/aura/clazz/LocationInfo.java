package com.sygic.aura.clazz;

import android.location.Location;
import com.sygic.aura.feature.time.LowTimeFeature;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;

public class LocationInfo {
    public double dAccur;
    public double dAlt;
    public double dCourse;
    public double dHdop;
    public double dHorizAccur;
    public double dLat;
    public double dLng;
    public double dPdop;
    public double dSpd;
    public double dVdop;
    public double dVertAccur;
    public long lTime;
    public int nDay;
    public int nFix;
    public int nHour;
    public int nMinute;
    public int nMonth;
    public int nSatNum;
    public int nSecond;
    public int nYear;

    public LocationInfo(Location loc) {
        this.dLng = loc.getLongitude();
        this.dLat = loc.getLatitude();
        this.dSpd = (double) loc.getSpeed();
        this.dCourse = loc.hasBearing() ? (double) loc.getBearing() : -1.0d;
        this.dAlt = loc.getAltitude();
        this.dAccur = (double) loc.getAccuracy();
        this.dHorizAccur = this.dAccur / 6.6d;
        this.dHdop = this.dAccur / 6.6d;
        this.lTime = (loc.getTime() - LowTimeFeature.ms_2001) / 1000;
        setTime(loc.getTime());
    }

    private void setTime(long lTime) {
        GregorianCalendar calendar = new GregorianCalendar();
        calendar.setTime(new Date(lTime));
        calendar.setTimeZone(TimeZone.getTimeZone("UTC"));
        this.nYear = calendar.get(1);
        this.nMonth = calendar.get(2) + 1;
        this.nDay = calendar.get(5);
        this.nHour = calendar.get(11);
        this.nMinute = calendar.get(12);
        this.nSecond = calendar.get(13);
    }
}
