package com.bosch.myspin.serversdk.uielements;

/* renamed from: com.bosch.myspin.serversdk.uielements.d */
class C0229d implements Runnable {
    final /* synthetic */ MySpinKeyboardBaseView f402a;

    C0229d(MySpinKeyboardBaseView mySpinKeyboardBaseView) {
        this.f402a = mySpinKeyboardBaseView;
    }

    public void run() {
        this.f402a.removeFlyin();
        this.f402a.invalidate();
    }
}
