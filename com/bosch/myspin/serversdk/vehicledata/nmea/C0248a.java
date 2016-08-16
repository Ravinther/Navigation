package com.bosch.myspin.serversdk.vehicledata.nmea;

/* renamed from: com.bosch.myspin.serversdk.vehicledata.nmea.a */
public class C0248a extends C0247b {
    private static volatile C0248a f481d;

    private C0248a() {
    }

    public static C0248a m434a() {
        if (f481d == null) {
            f481d = new C0248a();
        }
        return f481d;
    }

    public C0247b m435a(String str) {
        this.a = false;
        this.b = false;
        this.c = false;
        String[] split = str.split("\\*")[0].split(",", -1);
        m408c(split[1]);
        String str2 = split[2];
        m412d(split[3]);
        if (!(split[2].equals("") || split[3].equals(""))) {
            m405b(m400a(str2, m419g()));
        }
        str2 = split[4];
        m415e(split[5]);
        if (!(split[4].equals("") || split[5].equals(""))) {
            m407c(m404b(str2, m422h()));
        }
        if (!split[6].equals("")) {
            m402a(Integer.parseInt(split[6]));
        }
        if (!split[7].equals("")) {
            m406b(Integer.parseInt(split[7]));
        }
        if (!split[8].equals("")) {
            m411d(Double.parseDouble(split[8]));
        }
        if (!(split[9].equals("") || split[10].equals(""))) {
            m401a(Double.parseDouble(split[9]));
            m418f(split[10]);
        }
        if (!(split[11].equals("") || split[12].equals(""))) {
            m426i(Double.parseDouble(split[11]));
            m431k(split[12]);
        }
        if (!split[13].equals("")) {
            m423h(Double.parseDouble(split[13]));
        }
        m428j(split[14]);
        return this;
    }

    public boolean m436b(String str) {
        String[] split = str.split("\\*")[0].split(",", -1);
        if (!split[6].equals("0") && split[2].split("\\.")[0].length() == 4 && split[4].split("\\.")[0].length() == 5) {
            return true;
        }
        return false;
    }
}
