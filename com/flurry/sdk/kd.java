package com.flurry.sdk;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.UUID;

public class kd {
    private static final String f1017b;
    String f1018a;
    private byte[] f1019c;

    /* renamed from: com.flurry.sdk.kd.a */
    public static class C0506a implements kk<kd> {

        /* renamed from: com.flurry.sdk.kd.a.1 */
        class C05041 extends DataOutputStream {
            final /* synthetic */ C0506a f1015a;

            C05041(C0506a c0506a, OutputStream outputStream) {
                this.f1015a = c0506a;
                super(outputStream);
            }

            public void close() {
            }
        }

        /* renamed from: com.flurry.sdk.kd.a.2 */
        class C05052 extends DataInputStream {
            final /* synthetic */ C0506a f1016a;

            C05052(C0506a c0506a, InputStream inputStream) {
                this.f1016a = c0506a;
                super(inputStream);
            }

            public void close() {
            }
        }

        public /* synthetic */ Object m1120b(InputStream inputStream) throws IOException {
            return m1117a(inputStream);
        }

        public void m1118a(OutputStream outputStream, kd kdVar) throws IOException {
            if (outputStream != null && kdVar != null) {
                DataOutputStream c05041 = new C05041(this, outputStream);
                c05041.writeShort(kdVar.f1019c.length);
                c05041.write(kdVar.f1019c);
                c05041.writeShort(0);
                c05041.flush();
            }
        }

        public kd m1117a(InputStream inputStream) throws IOException {
            if (inputStream == null) {
                return null;
            }
            DataInputStream c05052 = new C05052(this, inputStream);
            kd kdVar = new kd();
            short readShort = c05052.readShort();
            if (readShort == (short) 0) {
                return null;
            }
            kdVar.f1019c = new byte[readShort];
            c05052.readFully(kdVar.f1019c);
            if (c05052.readUnsignedShort() == 0) {
            }
            return kdVar;
        }
    }

    static {
        f1017b = kd.class.getSimpleName();
    }

    private kd() {
        this.f1018a = null;
        this.f1019c = null;
    }

    public kd(byte[] bArr) {
        this.f1018a = null;
        this.f1019c = null;
        this.f1018a = UUID.randomUUID().toString();
        this.f1019c = bArr;
    }

    public String m1124a() {
        return this.f1018a;
    }

    public byte[] m1125b() {
        return this.f1019c;
    }

    public static String m1121a(String str) {
        return ".yflurrydatasenderblock." + str;
    }
}
