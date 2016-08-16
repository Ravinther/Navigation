package com.flurry.sdk;

import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

public final class ih {
    private final Map<String, String> f702a;
    private int f703b;
    private String f704c;
    private long f705d;
    private boolean f706e;
    private boolean f707f;
    private long f708g;

    public ih(int i, String str, Map<String, String> map, long j, boolean z) {
        this.f702a = new HashMap();
        this.f703b = i;
        this.f704c = str;
        if (map != null) {
            this.f702a.putAll(map);
        }
        this.f705d = j;
        this.f706e = z;
        if (this.f706e) {
            this.f707f = false;
        } else {
            this.f707f = true;
        }
    }

    public boolean m671a() {
        return this.f706e;
    }

    public boolean m674b() {
        return this.f707f;
    }

    public boolean m672a(String str) {
        return this.f706e && this.f708g == 0 && this.f704c.equals(str);
    }

    public void m669a(long j) {
        this.f707f = true;
        this.f708g = j - this.f705d;
        jq.m1016a(3, "FlurryAgent", "Ended event '" + this.f704c + "' (" + this.f705d + ") after " + this.f708g + "ms");
    }

    public synchronized void m670a(Map<String, String> map) {
        if (map != null) {
            this.f702a.putAll(map);
        }
    }

    public synchronized Map<String, String> m675c() {
        return new HashMap(this.f702a);
    }

    public synchronized void m673b(Map<String, String> map) {
        this.f702a.clear();
        if (map != null) {
            this.f702a.putAll(map);
        }
    }

    public int m676d() {
        return m677e().length;
    }

    public synchronized byte[] m677e() {
        byte[] toByteArray;
        Closeable closeable;
        Throwable th;
        Closeable dataOutputStream;
        try {
            OutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            dataOutputStream = new DataOutputStream(byteArrayOutputStream);
            try {
                dataOutputStream.writeShort(this.f703b);
                dataOutputStream.writeUTF(this.f704c);
                dataOutputStream.writeShort(this.f702a.size());
                for (Entry entry : this.f702a.entrySet()) {
                    dataOutputStream.writeUTF(lc.m1270b((String) entry.getKey()));
                    dataOutputStream.writeUTF(lc.m1270b((String) entry.getValue()));
                }
                dataOutputStream.writeLong(this.f705d);
                dataOutputStream.writeLong(this.f708g);
                dataOutputStream.flush();
                toByteArray = byteArrayOutputStream.toByteArray();
                lc.m1265a(dataOutputStream);
            } catch (IOException e) {
                closeable = dataOutputStream;
                try {
                    toByteArray = new byte[0];
                    lc.m1265a(closeable);
                    return toByteArray;
                } catch (Throwable th2) {
                    th = th2;
                    dataOutputStream = closeable;
                    lc.m1265a(dataOutputStream);
                    throw th;
                }
            } catch (Throwable th3) {
                th = th3;
                lc.m1265a(dataOutputStream);
                throw th;
            }
        } catch (IOException e2) {
            closeable = null;
            toByteArray = new byte[0];
            lc.m1265a(closeable);
            return toByteArray;
        } catch (Throwable th4) {
            dataOutputStream = null;
            th = th4;
            lc.m1265a(dataOutputStream);
            throw th;
        }
        return toByteArray;
    }
}
