package com.sygic.aura.utils;

public class Pair<T, U> {
    private final T first;
    private final transient int hash;
    private final U second;

    public Pair(T f, U s) {
        int i = 0;
        this.first = f;
        this.second = s;
        int hashCode = this.first == null ? 0 : this.first.hashCode() * 31;
        if (this.second != null) {
            i = this.second.hashCode();
        }
        this.hash = hashCode + i;
    }

    public T getFirst() {
        return this.first;
    }

    public U getSecond() {
        return this.second;
    }

    public int hashCode() {
        return this.hash;
    }
}
