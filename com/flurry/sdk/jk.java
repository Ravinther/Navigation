package com.flurry.sdk;

import android.text.TextUtils;

public abstract class jk {
    protected String f834g;

    public jk(String str) {
        this.f834g = "com.flurry.android.sdk.ReplaceMeWithAProperEventName";
        if (TextUtils.isEmpty(str)) {
            throw new IllegalArgumentException("Event must have a name!");
        }
        this.f834g = str;
    }

    public String m867a() {
        return this.f834g;
    }

    public void m868b() {
        jm.m997a().m999a(this);
    }
}
