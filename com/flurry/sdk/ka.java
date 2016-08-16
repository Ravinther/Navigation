package com.flurry.sdk;

public abstract class ka {
    private long f672a;
    private boolean f673b;
    private int f674c;
    private String f675d;
    private String f676e;
    private int f677f;

    protected ka() {
    }

    public long m629f() {
        return this.f672a;
    }

    public boolean m630g() {
        return this.f673b;
    }

    public int m631h() {
        return this.f674c;
    }

    public String m632i() {
        return this.f675d;
    }

    public int m628d() {
        return this.f677f;
    }

    public String m633j() {
        return this.f676e;
    }

    public void m623a(long j) {
        this.f672a = j;
    }

    public void m625a(boolean z) {
        this.f673b = z;
    }

    public void m622a(int i) {
        this.f674c = i;
    }

    public void m634k() {
        this.f674c++;
    }

    public void m624a(String str) {
        this.f675d = str;
        this.f676e = str;
    }

    public void m626b(String str) {
        this.f675d = str;
    }

    public void m627c(String str) {
        this.f676e = str;
    }
}
