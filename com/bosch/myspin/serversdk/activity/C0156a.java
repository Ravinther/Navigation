package com.bosch.myspin.serversdk.activity;

import android.view.Window;
import android.view.WindowManager.LayoutParams;
import com.bosch.myspin.serversdk.utils.Logger;
import com.bosch.myspin.serversdk.utils.Logger.LogComponent;
import com.bosch.myspin.serversdk.window.C0265a.C0155a;

/* renamed from: com.bosch.myspin.serversdk.activity.a */
public class C0156a implements C0155a {
    private static final LogComponent f62a;
    private int f63b;
    private int f64c;

    static {
        f62a = LogComponent.SDKMain;
    }

    public C0156a(int i, int i2) {
        m70a(i, i2);
    }

    public void m70a(int i, int i2) {
        Logger.logDebug(f62a, "ActivityWindowTransformation/setPreferedWindowSize(" + i + ", " + i2 + ")");
        this.f63b = i;
        this.f64c = i2;
    }

    public void m71a(Window window, LayoutParams layoutParams) {
        if (window == null || layoutParams == null) {
            Logger.logWarning(f62a, "ActivityWindowTransformation/onTransformWindow(Window: " + window + "LayoutParams: " + layoutParams + ")");
            return;
        }
        layoutParams.copyFrom(window.getAttributes());
        window.setLayout(this.f63b, this.f64c);
        window.setFlags(1024, 1024);
        window.setWindowAnimations(0);
        window.setFlags(128, 128);
    }

    public void m72b(Window window, LayoutParams layoutParams) {
        if (window == null || layoutParams == null) {
            Logger.logWarning(f62a, "onRestoreWindow(Window: " + window + "LayoutParams: " + layoutParams + ")");
            return;
        }
        window.setLayout(layoutParams.width, layoutParams.height);
        window.setFlags(layoutParams.flags & 1024, 1024);
        window.setFlags(layoutParams.flags & 128, 128);
        window.setWindowAnimations(layoutParams.windowAnimations);
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof C0156a)) {
            return false;
        }
        C0156a c0156a = (C0156a) obj;
        if (this.f64c == c0156a.f64c && this.f63b == c0156a.f63b) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        return (((this.f63b ^ (this.f63b >>> 32)) + 155) * 31) + this.f64c;
    }
}
