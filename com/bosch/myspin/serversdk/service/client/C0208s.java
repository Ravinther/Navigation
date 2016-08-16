package com.bosch.myspin.serversdk.service.client;

import android.view.View;
import android.view.ViewGroup;

/* renamed from: com.bosch.myspin.serversdk.service.client.s */
class C0208s implements Runnable {
    final /* synthetic */ C0194j f275a;

    C0208s(C0194j c0194j) {
        this.f275a = c0194j;
    }

    public void run() {
        this.f275a.f200S.m174a(C0185b.DISCONNECTED);
        if (this.f275a.f232z != null) {
            View rootView = this.f275a.f232z.getWindow().getDecorView().getRootView();
            if (!this.f275a.f190H.m293a() && (rootView instanceof ViewGroup)) {
                this.f275a.f190H.m291a((ViewGroup) rootView);
            }
        }
        this.f275a.m238q();
        this.f275a.f202U.m83a(this.f275a.f232z, false);
        this.f275a.f188F.m103a();
        this.f275a.f184B.m470a();
        this.f275a.f184B.m472a(null);
    }
}
