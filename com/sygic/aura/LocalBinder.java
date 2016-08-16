package com.sygic.aura;

import android.os.Binder;
import java.lang.ref.WeakReference;

public class LocalBinder<S> extends Binder {
    private WeakReference<S> mService;

    public LocalBinder(S service) {
        this.mService = new WeakReference(service);
    }

    public S getService() {
        return this.mService.get();
    }
}
