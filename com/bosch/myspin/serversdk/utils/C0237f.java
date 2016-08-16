package com.bosch.myspin.serversdk.utils;

import android.view.View;
import android.view.View.OnFocusChangeListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup.OnHierarchyChangeListener;
import java.util.WeakHashMap;

/* renamed from: com.bosch.myspin.serversdk.utils.f */
public class C0237f {
    private static C0237f f429d;
    private WeakHashMap<View, OnTouchListener> f430a;
    private WeakHashMap<View, OnFocusChangeListener> f431b;
    private WeakHashMap<View, OnHierarchyChangeListener> f432c;

    private C0237f() {
        this.f430a = new WeakHashMap();
        this.f431b = new WeakHashMap();
        this.f432c = new WeakHashMap();
    }

    public static C0237f m369a() {
        if (f429d == null) {
            f429d = new C0237f();
        }
        return f429d;
    }

    public OnTouchListener m370a(View view) {
        return (OnTouchListener) this.f430a.get(view);
    }

    public OnFocusChangeListener m371b(View view) {
        return (OnFocusChangeListener) this.f431b.get(view);
    }

    public OnHierarchyChangeListener m372c(View view) {
        return (OnHierarchyChangeListener) this.f432c.get(view);
    }
}
