package com.sygic.aura.resources;

public class ValueUnitPair<V, U> {
    private final transient int hash;
    private final U unit;
    private final V value;

    public ValueUnitPair(V v, U u) {
        int i = 0;
        this.value = v;
        this.unit = u;
        int hashCode = this.value == null ? 0 : this.value.hashCode() << 31;
        if (this.unit != null) {
            i = this.unit.hashCode();
        }
        this.hash = hashCode + i;
    }

    public V getValue() {
        return this.value;
    }

    public U getUnit() {
        return this.unit;
    }

    public int hashCode() {
        return this.hash;
    }
}
