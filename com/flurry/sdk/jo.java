package com.flurry.sdk;

import java.lang.ref.WeakReference;
import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;

public class jo<V> extends FutureTask<V> {
    private final WeakReference<Callable<V>> f934a;
    private final WeakReference<Runnable> f935b;

    public jo(Runnable runnable, V v) {
        super(runnable, v);
        this.f934a = new WeakReference(null);
        this.f935b = new WeakReference(runnable);
    }

    public Runnable m1009a() {
        return (Runnable) this.f935b.get();
    }
}
