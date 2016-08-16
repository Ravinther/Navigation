package com.flurry.sdk;

import java.lang.ref.Reference;
import java.lang.ref.WeakReference;

public class jz<T> extends WeakReference<T> {
    public jz(T t) {
        super(t);
    }

    public boolean equals(Object obj) {
        Object obj2 = get();
        if (obj instanceof Reference) {
            obj = ((Reference) obj).get();
        }
        if (obj2 != null) {
            return obj2.equals(obj);
        }
        if (obj != null) {
            return false;
        }
        return true;
    }

    public int hashCode() {
        Object obj = get();
        if (obj == null) {
            return super.hashCode();
        }
        return obj.hashCode();
    }
}
