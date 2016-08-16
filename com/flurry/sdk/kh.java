package com.flurry.sdk;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class kh<ObjectType> implements kk<ObjectType> {
    protected final kk<ObjectType> f1034a;

    public kh(kk<ObjectType> kkVar) {
        this.f1034a = kkVar;
    }

    public void m1164a(OutputStream outputStream, ObjectType objectType) throws IOException {
        if (this.f1034a != null && outputStream != null && objectType != null) {
            this.f1034a.m531a(outputStream, objectType);
        }
    }

    public ObjectType m1165b(InputStream inputStream) throws IOException {
        if (this.f1034a == null || inputStream == null) {
            return null;
        }
        return this.f1034a.m532b(inputStream);
    }
}
