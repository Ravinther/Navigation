package com.flurry.sdk;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class kg implements kk<byte[]> {
    public /* synthetic */ Object m1163b(InputStream inputStream) throws IOException {
        return m1162a(inputStream);
    }

    public void m1161a(OutputStream outputStream, byte[] bArr) throws IOException {
        if (outputStream != null && bArr != null) {
            outputStream.write(bArr, 0, bArr.length);
        }
    }

    public byte[] m1162a(InputStream inputStream) throws IOException {
        if (inputStream == null) {
            return null;
        }
        OutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        lc.m1260a(inputStream, byteArrayOutputStream);
        return byteArrayOutputStream.toByteArray();
    }
}
