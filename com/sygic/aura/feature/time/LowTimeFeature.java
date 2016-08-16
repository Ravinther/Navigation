package com.sygic.aura.feature.time;

import java.util.GregorianCalendar;

public abstract class LowTimeFeature {
    public static final long ms_2001;

    public abstract long convertTime(int i, byte b, byte b2, byte b3, byte b4, byte b5);

    public abstract long getCurrentTime();

    public abstract int getDaylightSaving();

    public abstract long getTickCount();

    public abstract Object getTime(long j);

    public abstract int getTimeZone();

    static {
        ms_2001 = new GregorianCalendar(2001, 0, 1).getTimeInMillis();
    }

    protected LowTimeFeature() {
    }

    public static LowTimeFeature createInstance() {
        return new LowTimeFeatureBase();
    }
}
