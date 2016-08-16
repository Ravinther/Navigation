package com.flurry.sdk;

import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class jj<T> {
    private static final String f923a;
    private final File f924b;
    private final kk<T> f925c;

    static {
        f923a = jj.class.getSimpleName();
    }

    public jj(File file, String str, int i, kn<T> knVar) {
        this.f924b = file;
        this.f925c = new ki(new km(str, i, knVar));
    }

    public T m993a() {
        Throwable e;
        Throwable th;
        T t = null;
        if (this.f924b != null) {
            if (this.f924b.exists()) {
                Object obj = null;
                Closeable fileInputStream;
                try {
                    fileInputStream = new FileInputStream(this.f924b);
                    try {
                        t = this.f925c.m532b(fileInputStream);
                        lc.m1265a(fileInputStream);
                    } catch (Exception e2) {
                        e = e2;
                        try {
                            jq.m1017a(3, f923a, "Error reading data file:" + this.f924b.getName(), e);
                            obj = 1;
                            lc.m1265a(fileInputStream);
                            if (obj != null) {
                                jq.m1016a(3, f923a, "Deleting data file:" + this.f924b.getName());
                                this.f924b.delete();
                            }
                            return t;
                        } catch (Throwable th2) {
                            th = th2;
                            lc.m1265a(fileInputStream);
                            throw th;
                        }
                    }
                } catch (Exception e3) {
                    e = e3;
                    fileInputStream = t;
                    jq.m1017a(3, f923a, "Error reading data file:" + this.f924b.getName(), e);
                    obj = 1;
                    lc.m1265a(fileInputStream);
                    if (obj != null) {
                        jq.m1016a(3, f923a, "Deleting data file:" + this.f924b.getName());
                        this.f924b.delete();
                    }
                    return t;
                } catch (Throwable e4) {
                    fileInputStream = t;
                    th = e4;
                    lc.m1265a(fileInputStream);
                    throw th;
                }
                if (obj != null) {
                    jq.m1016a(3, f923a, "Deleting data file:" + this.f924b.getName());
                    this.f924b.delete();
                }
            } else {
                jq.m1016a(5, f923a, "No data to read for file:" + this.f924b.getName());
            }
        }
        return t;
    }

    public void m994a(T t) {
        Throwable e;
        int i;
        Object obj = null;
        Closeable closeable = null;
        if (t == null) {
            jq.m1016a(3, f923a, "No data to write for file:" + this.f924b.getName());
            obj = 1;
        } else {
            try {
                if (lb.m1255a(this.f924b)) {
                    Closeable fileOutputStream = new FileOutputStream(this.f924b);
                    try {
                        this.f925c.m531a(fileOutputStream, t);
                        lc.m1265a(fileOutputStream);
                    } catch (Exception e2) {
                        e = e2;
                        closeable = fileOutputStream;
                        try {
                            jq.m1017a(3, f923a, "Error writing data file:" + this.f924b.getName(), e);
                            lc.m1265a(closeable);
                            i = 1;
                            if (obj == null) {
                                jq.m1016a(3, f923a, "Deleting data file:" + this.f924b.getName());
                                this.f924b.delete();
                            }
                        } catch (Throwable th) {
                            e = th;
                            lc.m1265a(closeable);
                            throw e;
                        }
                    } catch (Throwable th2) {
                        e = th2;
                        closeable = fileOutputStream;
                        lc.m1265a(closeable);
                        throw e;
                    }
                }
                throw new IOException("Cannot create parent directory!");
            } catch (Exception e3) {
                e = e3;
                jq.m1017a(3, f923a, "Error writing data file:" + this.f924b.getName(), e);
                lc.m1265a(closeable);
                i = 1;
                if (obj == null) {
                    jq.m1016a(3, f923a, "Deleting data file:" + this.f924b.getName());
                    this.f924b.delete();
                }
            }
        }
        if (obj == null) {
            jq.m1016a(3, f923a, "Deleting data file:" + this.f924b.getName());
            this.f924b.delete();
        }
    }

    public boolean m995b() {
        if (this.f924b == null) {
            return false;
        }
        return this.f924b.delete();
    }
}
