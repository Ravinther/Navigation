package com.crashlytics.android.ndk;

class ProcMapEntry {
    public final long address;
    public final String path;
    public final long size;

    public ProcMapEntry(long address, long size, String path) {
        this.address = address;
        this.size = size;
        this.path = path;
    }
}
