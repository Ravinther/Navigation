package com.bosch.myspin.serversdk.vehicledata.nmea;

import android.location.Location;
import com.bosch.myspin.serversdk.utils.Logger;
import com.bosch.myspin.serversdk.utils.Logger.LogComponent;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;
import loquendo.tts.engine.TTSConst;

public final class MySpinLocationFactory {
    private static final LogComponent f457a;

    /* renamed from: com.bosch.myspin.serversdk.vehicledata.nmea.MySpinLocationFactory.1 */
    static /* synthetic */ class C02461 {
        static final /* synthetic */ int[] f454a;

        static {
            f454a = new int[SentenceId.values().length];
            try {
                f454a[SentenceId.GGA.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                f454a[SentenceId.RMC.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
        }
    }

    public enum SentenceId {
        GGA("$GPGGA"),
        RMC("$GPRMC"),
        GLL("$GPGLL"),
        GSA("$GPGSA"),
        GSV("$GPGSV"),
        VTG("$GPVTG"),
        UNKNOWN("UNKNOWN");
        
        private String f456a;

        private SentenceId(String str) {
            this.f456a = str;
        }

        public String toString() {
            return this.f456a;
        }
    }

    static {
        f457a = LogComponent.EventListener;
    }

    public static Location parseNmea(String str) throws ParseException {
        switch (C02461.f454a[m399a(str).ordinal()]) {
            case TTSConst.TTSMULTILINE /*1*/:
                if (C0248a.m434a().m436b(str)) {
                    return m398a(C0248a.m434a().m435a(str));
                }
                Logger.logWarning(f457a, "No valid NMEA string! " + str);
                return null;
            case TTSConst.TTSPARAGRAPH /*2*/:
                if (C0249c.m437a().m439b(str)) {
                    return m398a(C0249c.m437a().m438a(str));
                }
                Logger.logWarning(f457a, "No valid NMEA string! " + str);
                return null;
            default:
                return null;
        }
    }

    private static SentenceId m399a(String str) {
        try {
            return SentenceId.valueOf(str.substring(3, 6));
        } catch (Exception e) {
            return SentenceId.UNKNOWN;
        }
    }

    private static Location m398a(C0247b c0247b) throws ParseException {
        Location location = new Location("MYSPIN_CAR_GPS");
        long j = 0;
        if (!(c0247b.m433m().equals("") || c0247b.m410d().equals(""))) {
            j = new SimpleDateFormat("ddMMyyHHmmss", Locale.getDefault()).parse(c0247b.m433m() + c0247b.m410d().split("\\.")[0]).getTime();
        }
        location.setLatitude(c0247b.m413e());
        location.setLongitude(c0247b.m416f());
        if (c0247b.m432l()) {
            location.setBearing((float) c0247b.m430k());
        }
        if (c0247b.m429j()) {
            location.setSpeed((float) c0247b.m425i());
        }
        if (c0247b.m409c()) {
            location.setAltitude(c0247b.m403b());
        }
        location.setTime(j);
        return location;
    }
}
