package com.flurry.sdk;

import android.os.Build.VERSION;

public final class jr {
    private final Class<? extends jt> f950a;
    private final int f951b;

    public jr(Class<? extends jt> cls, int i) {
        this.f950a = cls;
        this.f951b = i;
    }

    public Class<? extends jt> m1033a() {
        return this.f950a;
    }

    public boolean m1034b() {
        return this.f950a != null && VERSION.SDK_INT >= this.f951b;
    }
}
