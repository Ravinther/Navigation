package com.flurry.sdk;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class kl implements kk<String> {
    public /* synthetic */ Object m1175b(InputStream inputStream) throws IOException {
        return m1172a(inputStream);
    }

    public void m1174a(OutputStream outputStream, String str) throws IOException {
        if (outputStream != null && str != null) {
            byte[] bytes = str.getBytes("utf-8");
            outputStream.write(bytes, 0, bytes.length);
        }
    }

    public String m1172a(InputStream inputStream) throws IOException {
        if (inputStream == null) {
            return null;
        }
        OutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        lc.m1260a(inputStream, byteArrayOutputStream);
        return byteArrayOutputStream.toString();
    }
}
