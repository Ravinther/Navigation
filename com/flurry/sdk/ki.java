package com.flurry.sdk;

import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

public class ki<ObjectType> extends kh<ObjectType> {
    public ki(kk<ObjectType> kkVar) {
        super(kkVar);
    }

    public void m1166a(OutputStream outputStream, ObjectType objectType) throws IOException {
        Closeable gZIPOutputStream;
        Throwable th;
        if (outputStream != null) {
            try {
                gZIPOutputStream = new GZIPOutputStream(outputStream);
                try {
                    super.m1164a(gZIPOutputStream, objectType);
                    lc.m1265a(gZIPOutputStream);
                } catch (Throwable th2) {
                    th = th2;
                    lc.m1265a(gZIPOutputStream);
                    throw th;
                }
            } catch (Throwable th3) {
                th = th3;
                gZIPOutputStream = null;
                lc.m1265a(gZIPOutputStream);
                throw th;
            }
        }
    }

    public ObjectType m1167b(InputStream inputStream) throws IOException {
        Throwable th;
        ObjectType objectType = null;
        if (inputStream != null) {
            Closeable gZIPInputStream;
            try {
                gZIPInputStream = new GZIPInputStream(inputStream);
                try {
                    objectType = super.m1165b(gZIPInputStream);
                    lc.m1265a(gZIPInputStream);
                } catch (Throwable th2) {
                    th = th2;
                    lc.m1265a(gZIPInputStream);
                    throw th;
                }
            } catch (Throwable th3) {
                Throwable th4 = th3;
                gZIPInputStream = null;
                th = th4;
                lc.m1265a(gZIPInputStream);
                throw th;
            }
        }
        return objectType;
    }
}
