package com.bosch.myspin.serversdk.utils;

/* renamed from: com.bosch.myspin.serversdk.utils.c */
class C0232c implements Runnable {
    final /* synthetic */ C0231b f428a;

    C0232c(C0231b c0231b) {
        this.f428a = c0231b;
    }

    public void run() {
        this.f428a.m365b();
        this.f428a.f426j.postDelayed(this, 10000);
    }
}
