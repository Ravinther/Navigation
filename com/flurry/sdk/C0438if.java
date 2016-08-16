package com.flurry.sdk;

import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;

/* renamed from: com.flurry.sdk.if */
public final class C0438if {
    private int f695a;
    private long f696b;
    private String f697c;
    private String f698d;
    private String f699e;
    private Throwable f700f;

    public C0438if(int i, long j, String str, String str2, String str3, Throwable th) {
        this.f695a = i;
        this.f696b = j;
        this.f697c = str;
        this.f698d = str2;
        this.f699e = str3;
        this.f700f = th;
    }

    public int m666a() {
        return m667b().length;
    }

    public byte[] m667b() {
        byte[] bytes;
        Throwable th;
        Closeable dataOutputStream;
        try {
            OutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            dataOutputStream = new DataOutputStream(byteArrayOutputStream);
            try {
                dataOutputStream.writeShort(this.f695a);
                dataOutputStream.writeLong(this.f696b);
                dataOutputStream.writeUTF(this.f697c);
                dataOutputStream.writeUTF(this.f698d);
                dataOutputStream.writeUTF(this.f699e);
                if (this.f700f != null) {
                    if (this.f697c == "uncaught") {
                        dataOutputStream.writeByte(3);
                    } else {
                        dataOutputStream.writeByte(2);
                    }
                    dataOutputStream.writeByte(2);
                    StringBuilder stringBuilder = new StringBuilder("");
                    String property = System.getProperty("line.separator");
                    for (Object append : this.f700f.getStackTrace()) {
                        stringBuilder.append(append);
                        stringBuilder.append(property);
                    }
                    if (this.f700f.getCause() != null) {
                        stringBuilder.append(property);
                        stringBuilder.append("Caused by: ");
                        for (Object append2 : this.f700f.getCause().getStackTrace()) {
                            stringBuilder.append(append2);
                            stringBuilder.append(property);
                        }
                    }
                    bytes = stringBuilder.toString().getBytes();
                    dataOutputStream.writeInt(bytes.length);
                    dataOutputStream.write(bytes);
                } else {
                    dataOutputStream.writeByte(1);
                    dataOutputStream.writeByte(0);
                }
                dataOutputStream.flush();
                bytes = byteArrayOutputStream.toByteArray();
                lc.m1265a(dataOutputStream);
            } catch (IOException e) {
                try {
                    bytes = new byte[0];
                    lc.m1265a(dataOutputStream);
                    return bytes;
                } catch (Throwable th2) {
                    th = th2;
                    lc.m1265a(dataOutputStream);
                    throw th;
                }
            }
        } catch (IOException e2) {
            dataOutputStream = null;
            bytes = new byte[0];
            lc.m1265a(dataOutputStream);
            return bytes;
        } catch (Throwable th3) {
            Throwable th4 = th3;
            dataOutputStream = null;
            th = th4;
            lc.m1265a(dataOutputStream);
            throw th;
        }
        return bytes;
    }

    public String m668c() {
        return this.f697c;
    }
}
