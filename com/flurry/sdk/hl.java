package com.flurry.sdk;

import com.flurry.sdk.in.C0458a;
import java.io.Closeable;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;

public final class hl {
    private static final String f570a;

    static {
        f570a = hl.class.getSimpleName();
    }

    public static in m530a(File file) {
        Closeable fileInputStream;
        Closeable dataInputStream;
        Throwable e;
        in inVar;
        if (file == null || !file.exists()) {
            return null;
        }
        kk c0458a = new C0458a();
        try {
            fileInputStream = new FileInputStream(file);
            try {
                dataInputStream = new DataInputStream(fileInputStream);
            } catch (Exception e2) {
                e = e2;
                dataInputStream = null;
                try {
                    jq.m1017a(3, f570a, "Error loading legacy agent data.", e);
                    lc.m1265a(dataInputStream);
                    lc.m1265a(fileInputStream);
                    inVar = null;
                    return inVar;
                } catch (Throwable th) {
                    e = th;
                    lc.m1265a(dataInputStream);
                    lc.m1265a(fileInputStream);
                    throw e;
                }
            } catch (Throwable th2) {
                e = th2;
                dataInputStream = null;
                lc.m1265a(dataInputStream);
                lc.m1265a(fileInputStream);
                throw e;
            }
            try {
                if (dataInputStream.readUnsignedShort() != 46586) {
                    jq.m1016a(3, f570a, "Unexpected file type");
                    lc.m1265a(dataInputStream);
                    lc.m1265a(fileInputStream);
                    return null;
                }
                int readUnsignedShort = dataInputStream.readUnsignedShort();
                if (readUnsignedShort != 2) {
                    jq.m1016a(6, f570a, "Unknown agent file version: " + readUnsignedShort);
                    lc.m1265a(dataInputStream);
                    lc.m1265a(fileInputStream);
                    return null;
                }
                inVar = (in) c0458a.m532b(dataInputStream);
                lc.m1265a(dataInputStream);
                lc.m1265a(fileInputStream);
                return inVar;
            } catch (Exception e3) {
                e = e3;
                jq.m1017a(3, f570a, "Error loading legacy agent data.", e);
                lc.m1265a(dataInputStream);
                lc.m1265a(fileInputStream);
                inVar = null;
                return inVar;
            }
        } catch (Exception e4) {
            e = e4;
            dataInputStream = null;
            fileInputStream = null;
            jq.m1017a(3, f570a, "Error loading legacy agent data.", e);
            lc.m1265a(dataInputStream);
            lc.m1265a(fileInputStream);
            inVar = null;
            return inVar;
        } catch (Throwable th3) {
            e = th3;
            dataInputStream = null;
            fileInputStream = null;
            lc.m1265a(dataInputStream);
            lc.m1265a(fileInputStream);
            throw e;
        }
    }
}
