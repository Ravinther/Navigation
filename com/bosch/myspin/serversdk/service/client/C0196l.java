package com.bosch.myspin.serversdk.service.client;

/* renamed from: com.bosch.myspin.serversdk.service.client.l */
class C0196l implements Runnable {
    final /* synthetic */ int[] f234a;
    final /* synthetic */ int[] f235b;
    final /* synthetic */ int[] f236c;
    final /* synthetic */ int[] f237d;
    final /* synthetic */ int f238e;
    final /* synthetic */ C0195k f239f;

    C0196l(C0195k c0195k, int[] iArr, int[] iArr2, int[] iArr3, int[] iArr4, int i) {
        this.f239f = c0195k;
        this.f234a = iArr;
        this.f235b = iArr2;
        this.f236c = iArr3;
        this.f237d = iArr4;
        this.f238e = i;
    }

    public void run() {
        this.f239f.f233a.f189G.m318a(this.f234a, this.f235b, this.f236c, this.f237d, this.f238e);
    }
}
