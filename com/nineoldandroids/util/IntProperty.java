package com.nineoldandroids.util;

public abstract class IntProperty<T> extends Property<T, Integer> {
    public IntProperty(String name) {
        super(Integer.class, name);
    }

    public final void set(T object, Integer value) {
        set((Object) object, Integer.valueOf(value.intValue()));
    }
}
