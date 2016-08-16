package com.bosch.myspin.serversdk.window;

import android.util.SparseArray;
import android.view.Window;
import android.view.WindowManager.LayoutParams;
import com.bosch.myspin.serversdk.utils.Logger;
import com.bosch.myspin.serversdk.utils.Logger.LogComponent;
import java.lang.ref.WeakReference;

/* renamed from: com.bosch.myspin.serversdk.window.a */
public class C0265a {
    private static final LogComponent f523a;
    private C0155a f524b;
    private SparseArray<C0264b> f525c;

    /* renamed from: com.bosch.myspin.serversdk.window.a.a */
    public interface C0155a {
        void m68a(Window window, LayoutParams layoutParams);

        void m69b(Window window, LayoutParams layoutParams);
    }

    /* renamed from: com.bosch.myspin.serversdk.window.a.b */
    class C0264b {
        WeakReference<Window> f520a;
        LayoutParams f521b;
        final /* synthetic */ C0265a f522c;

        private C0264b(C0265a c0265a, Window window, LayoutParams layoutParams) {
            this.f522c = c0265a;
            this.f520a = null;
            this.f521b = null;
            this.f520a = new WeakReference(window);
            this.f521b = layoutParams;
        }
    }

    static {
        f523a = LogComponent.UI;
    }

    public C0265a() {
        this.f524b = null;
        this.f525c = new SparseArray();
    }

    public void m472a(C0155a c0155a) {
        if (this.f524b != null && (!this.f524b.equals(c0155a) || c0155a == null)) {
            m470a();
        }
        this.f524b = c0155a;
    }

    public void m471a(Window window, int i) {
        if (window == null) {
            Logger.logWarning(f523a, "WindowTransformer/transformWindow(window is null)");
        } else if (this.f524b == null) {
            Logger.logWarning(f523a, "WindowTransformer/transformWindow(mWindowTransformation is null). No transformation available");
        } else if (this.f525c.get(i) == null) {
            LayoutParams layoutParams = new LayoutParams();
            this.f524b.m68a(window, layoutParams);
            this.f525c.put(i, new C0264b(window, layoutParams, null));
            Logger.logDebug(f523a, "WindowTransformer/-- transformWindow: transformation done and stored");
        }
    }

    public void m473b(Window window, int i) {
        if (window == null) {
            Logger.logWarning(f523a, "WindowTransformer/-- transformWindow: Size of stored params(" + this.f525c.size() + ")");
        } else if (this.f524b == null) {
            Logger.logWarning(f523a, "WindowTransformer/restoreWindow(mWindowTransformation is null). No transformation available");
        } else {
            C0264b c0264b = (C0264b) this.f525c.get(i);
            if (c0264b != null) {
                LayoutParams layoutParams = c0264b.f521b;
                if (layoutParams != null) {
                    Logger.logDebug(f523a, "WindowTransformer/-- restoreWindow: Backup available");
                    this.f524b.m69b(window, layoutParams);
                    this.f525c.remove(i);
                }
            }
        }
    }

    public void m470a() {
        if (this.f524b == null) {
            Logger.logWarning(f523a, "WindowTransformer/restoreAllWindows(mWindowTransformation is null). No transformation available");
            return;
        }
        for (int i = 0; i < this.f525c.size(); i++) {
            C0264b c0264b = (C0264b) this.f525c.valueAt(i);
            if (!(c0264b.f520a.get() == null || c0264b.f521b == null)) {
                this.f524b.m69b((Window) c0264b.f520a.get(), c0264b.f521b);
            }
        }
        this.f525c.clear();
    }

    public void m474c(Window window, int i) {
        this.f525c.remove(i);
    }
}
