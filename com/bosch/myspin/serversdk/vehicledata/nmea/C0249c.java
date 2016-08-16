package com.bosch.myspin.serversdk.vehicledata.nmea;

/* renamed from: com.bosch.myspin.serversdk.vehicledata.nmea.c */
public class C0249c extends C0247b {
    private static volatile C0249c f482d;

    private C0249c() {
    }

    public static C0249c m437a() {
        if (f482d == null) {
            f482d = new C0249c();
        }
        return f482d;
    }

    public C0247b m438a(String str) {
        this.a = false;
        this.b = false;
        this.c = false;
        String[] split = str.split("\\*")[0].split(",", -1);
        m408c(split[1]);
        m421g(split[2]);
        String str2 = split[3];
        m412d(split[4]);
        if (!(split[3].equals("") || split[4].equals(""))) {
            m405b(m400a(str2, m419g()));
        }
        str2 = split[5];
        m415e(split[6]);
        if (!(split[5].equals("") || split[6].equals(""))) {
            m407c(m404b(str2, m422h()));
        }
        if (!split[7].equals("")) {
            m414e(Double.parseDouble(split[7]));
        }
        if (!split[8].equals("")) {
            m417f(Double.parseDouble(split[8]));
        }
        m424h(split[9]);
        if (!(split[10].equals("") || split[11].equals(""))) {
            m420g(Double.parseDouble(split[10]));
            m427i(split[11]);
        }
        return this;
    }

    public boolean m439b(String str) {
        String[] split = str.split("\\*")[0].split(",", -1);
        if (split[2].equalsIgnoreCase("A") && split[3].split("\\.")[0].length() == 4 && split[5].split("\\.")[0].length() == 5) {
            return true;
        }
        return false;
    }
}
