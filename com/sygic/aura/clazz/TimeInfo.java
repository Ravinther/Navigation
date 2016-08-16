package com.sygic.aura.clazz;

public class TimeInfo {
    public int nDay;
    public int nDayInWeek;
    public int nHour;
    public int nMinute;
    public int nMonth;
    public int nSecond;
    public int nYear;

    public TimeInfo() {
        this.nYear = 0;
        this.nMonth = 0;
        this.nDay = 0;
        this.nHour = 0;
        this.nMinute = 0;
        this.nSecond = 0;
        this.nDayInWeek = 0;
    }

    public String toString() {
        return this.nDay + "." + this.nMonth + "." + this.nYear + " " + this.nHour + ":" + this.nMinute + ":" + this.nSecond + " - " + this.nDayInWeek;
    }

    public String getMDY() {
        return this.nMonth + "/" + this.nDay + "/" + this.nYear;
    }

    public String getDMY() {
        return this.nDay + "/" + this.nMonth + "/" + this.nYear;
    }

    public String getYMD() {
        return this.nYear + "/" + this.nMonth + "/" + this.nDay;
    }
}
