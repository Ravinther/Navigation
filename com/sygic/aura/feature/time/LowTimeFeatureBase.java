package com.sygic.aura.feature.time;

import android.os.SystemClock;
import com.sygic.aura.clazz.TimeInfo;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;

/* compiled from: LowTimeFeature */
class LowTimeFeatureBase extends LowTimeFeature {
    protected LowTimeFeatureBase() {
    }

    public long getTickCount() {
        return SystemClock.uptimeMillis();
    }

    public long getCurrentTime() {
        return (System.currentTimeMillis() - ms_2001) / 1000;
    }

    public Object getTime(long lTime) {
        GregorianCalendar c = new GregorianCalendar();
        c.setTime(new Date((1000 * lTime) + ms_2001));
        TimeInfo time = new TimeInfo();
        time.nYear = c.get(1);
        time.nMonth = c.get(2) + 1;
        time.nDay = c.get(5);
        time.nHour = c.get(11);
        time.nMinute = c.get(12);
        time.nSecond = c.get(13);
        time.nDayInWeek = c.get(7);
        return time;
    }

    public long convertTime(int nYear, byte cbMonth, byte cbDay, byte cbHour, byte cbMin, byte cbSec) {
        return (new GregorianCalendar(nYear, cbMonth - 1, cbDay, cbHour, cbMin, cbSec).getTimeInMillis() / 1000) - (ms_2001 / 1000);
    }

    public int getTimeZone() {
        return (TimeZone.getDefault().getOffset(System.currentTimeMillis()) / 1000) / 60;
    }

    public int getDaylightSaving() {
        if (TimeZone.getDefault().inDaylightTime(new Date(System.currentTimeMillis()))) {
            return (TimeZone.getDefault().getDSTSavings() / 1000) / 60;
        }
        return 0;
    }
}
