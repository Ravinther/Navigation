package com.bosch.myspin.serversdk.service.client;

/* renamed from: com.bosch.myspin.serversdk.service.client.o */
class C0199o implements Runnable {
    final /* synthetic */ int f242a;
    final /* synthetic */ C0195k f243b;

    C0199o(C0195k c0195k, int i) {
        this.f243b = c0195k;
        this.f242a = i;
    }

    public void run() {
        this.f243b.f233a.m205a(this.f242a);
    }
}
