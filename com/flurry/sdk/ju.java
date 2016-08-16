package com.flurry.sdk;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class ju<ObjectType> {
    private static final String f955a;
    private static final byte[] f956b;
    private String f957c;
    private kk<ObjectType> f958d;

    static {
        f955a = ju.class.getSimpleName();
        f956b = new byte[]{(byte) 113, (byte) -92, (byte) -8, (byte) 125, (byte) 121, (byte) 107, (byte) -65, (byte) -61, (byte) -74, (byte) -114, (byte) -32, (byte) 0, (byte) -57, (byte) -87, (byte) -35, (byte) -56, (byte) -6, (byte) -52, (byte) 51, (byte) 126, (byte) -104, (byte) 49, (byte) 79, (byte) -52, (byte) 118, (byte) -84, (byte) 99, (byte) -52, (byte) -14, (byte) -126, (byte) -27, (byte) -64};
    }

    public static void m1040a(byte[] bArr) {
        if (bArr != null) {
            int length = bArr.length;
            int length2 = f956b.length;
            for (int i = 0; i < length; i++) {
                bArr[i] = (byte) ((bArr[i] ^ f956b[i % length2]) ^ ((i * 31) % 251));
            }
        }
    }

    public static void m1041b(byte[] bArr) {
        m1040a(bArr);
    }

    public static int m1042c(byte[] bArr) {
        if (bArr == null) {
            return 0;
        }
        ji jiVar = new ji();
        jiVar.update(bArr);
        return jiVar.m992b();
    }

    public ju(String str, kk<ObjectType> kkVar) {
        this.f957c = str;
        this.f958d = kkVar;
    }

    public byte[] m1043a(ObjectType objectType) throws IOException {
        if (objectType == null) {
            throw new IOException("Encoding: " + this.f957c + ": Nothing to encode");
        }
        OutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        this.f958d.m531a(byteArrayOutputStream, objectType);
        Object toByteArray = byteArrayOutputStream.toByteArray();
        jq.m1016a(3, f955a, "Encoding " + this.f957c + ": " + new String(toByteArray));
        kk kiVar = new ki(new kg());
        OutputStream byteArrayOutputStream2 = new ByteArrayOutputStream();
        kiVar.m531a(byteArrayOutputStream2, toByteArray);
        byte[] toByteArray2 = byteArrayOutputStream2.toByteArray();
        m1040a(toByteArray2);
        return toByteArray2;
    }

    public ObjectType m1044d(byte[] bArr) throws IOException {
        if (bArr == null) {
            throw new IOException("Decoding: " + this.f957c + ": Nothing to decode");
        }
        m1041b(bArr);
        byte[] bArr2 = (byte[]) new ki(new kg()).m532b(new ByteArrayInputStream(bArr));
        jq.m1016a(3, f955a, "Decoding: " + this.f957c + ": " + new String(bArr2));
        return this.f958d.m532b(new ByteArrayInputStream(bArr2));
    }
}
