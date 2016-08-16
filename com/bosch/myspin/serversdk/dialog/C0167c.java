package com.bosch.myspin.serversdk.dialog;

import android.view.Window;
import android.view.WindowManager.LayoutParams;
import com.bosch.myspin.serversdk.utils.Logger;
import com.bosch.myspin.serversdk.utils.Logger.LogComponent;
import com.bosch.myspin.serversdk.window.C0265a.C0155a;

/* renamed from: com.bosch.myspin.serversdk.dialog.c */
class C0167c implements C0155a {
    private static final LogComponent f91a;
    private int f92b;
    private int f93c;

    static {
        f91a = LogComponent.UI;
    }

    public C0167c(int i, int i2) {
        this.f92b = i;
        this.f93c = i2;
    }

    public void m109a(Window window, LayoutParams layoutParams) {
        if (window == null || layoutParams == null) {
            Logger.logWarning(f91a, "DialogWindowTransformation/Parameter is null Window: " + window + "LayoutParams: " + layoutParams + ")");
            return;
        }
        layoutParams.copyFrom(window.getAttributes());
        window.setFlags(512, 512);
        LayoutParams layoutParams2 = new LayoutParams();
        layoutParams2.copyFrom(window.getAttributes());
        layoutParams2.gravity = 8388611;
        layoutParams2.x = -this.f92b;
        layoutParams2.width = this.f92b;
        layoutParams2.height = this.f93c;
        layoutParams2.dimAmount = 0.0f;
        window.setAttributes(layoutParams2);
    }

    public void m111b(Window window, LayoutParams layoutParams) {
        if (window == null || layoutParams == null) {
            Logger.logWarning(f91a, "DialogWindowTransformation/Parameter is null Window: " + window + "LayoutParams: " + layoutParams + ")");
        } else {
            window.setAttributes(layoutParams);
        }
    }

    public void m108a(int i) {
        this.f92b = i;
    }

    public void m110b(int i) {
        this.f93c = i;
    }
}
