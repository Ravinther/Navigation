package com.bosch.myspin.serversdk.vehicledata.nmea;

/* renamed from: com.bosch.myspin.serversdk.vehicledata.nmea.b */
public class C0247b {
    protected boolean f458a;
    protected boolean f459b;
    protected boolean f460c;
    private String f461d;
    private double f462e;
    private double f463f;
    private String f464g;
    private String f465h;
    private int f466i;
    private int f467j;
    private double f468k;
    private double f469l;
    private String f470m;
    private String f471n;
    private double f472o;
    private double f473p;
    private String f474q;
    private double f475r;
    private String f476s;
    private double f477t;
    private String f478u;
    private double f479v;
    private String f480w;

    public C0247b() {
        this.f461d = "";
        this.f464g = "";
        this.f465h = "";
        this.f466i = -1;
        this.f467j = -1;
        this.f470m = "";
        this.f471n = "";
        this.f474q = "";
        this.f476s = "";
        this.f478u = "";
        this.f480w = "";
    }

    public double m403b() {
        return this.f469l;
    }

    public boolean m409c() {
        return this.f458a;
    }

    public void m401a(double d) {
        this.f458a = true;
        this.f469l = d;
    }

    public String m410d() {
        return this.f461d;
    }

    public void m408c(String str) {
        this.f461d = str;
    }

    public double m413e() {
        return this.f462e;
    }

    public void m405b(double d) {
        this.f462e = d;
    }

    public double m416f() {
        return this.f463f;
    }

    public void m407c(double d) {
        this.f463f = d;
    }

    public String m419g() {
        return this.f464g;
    }

    public void m412d(String str) {
        this.f464g = str;
    }

    public String m422h() {
        return this.f465h;
    }

    public void m415e(String str) {
        this.f465h = str;
    }

    public void m402a(int i) {
        this.f466i = i;
    }

    public void m406b(int i) {
        this.f467j = i;
    }

    public void m411d(double d) {
        this.f468k = d;
    }

    public void m418f(String str) {
        this.f470m = str;
    }

    public void m421g(String str) {
        this.f471n = str;
    }

    public double m425i() {
        return this.f472o;
    }

    public boolean m429j() {
        return this.f459b;
    }

    public void m414e(double d) {
        this.f459b = true;
        this.f472o = d;
    }

    public double m430k() {
        return this.f473p;
    }

    public boolean m432l() {
        return this.f460c;
    }

    public void m417f(double d) {
        this.f460c = true;
        this.f473p = d;
    }

    public String m433m() {
        return this.f474q;
    }

    public void m424h(String str) {
        this.f474q = str;
    }

    public void m420g(double d) {
        this.f475r = d;
    }

    public void m427i(String str) {
        this.f476s = str;
    }

    public void m423h(double d) {
        this.f477t = d;
    }

    public void m428j(String str) {
        this.f478u = str;
    }

    public void m426i(double d) {
        this.f479v = d;
    }

    public void m431k(String str) {
        this.f480w = str;
    }

    public double m400a(String str, String str2) {
        double parseDouble = Double.parseDouble(str.substring(0, 2)) + (Double.parseDouble("0." + str.substring(2, 4) + str.substring(5)) / 0.6d);
        return str2.equals("N") ? parseDouble : parseDouble * -1.0d;
    }

    public double m404b(String str, String str2) {
        String substring = str.substring(0, 3);
        String substring2 = str.substring(3, 5);
        float parseFloat = Float.parseFloat(substring) + (Float.parseFloat("0." + substring2 + str.substring(6)) / 0.6f);
        if (str2.equals("E")) {
            return (double) parseFloat;
        }
        return (double) (parseFloat * -1.0f);
    }
}
