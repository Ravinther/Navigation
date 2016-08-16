package com.flurry.sdk;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class km<T> implements kk<T> {
    private final String f1040a;
    private final int f1041b;
    private final kn<T> f1042c;

    /* renamed from: com.flurry.sdk.km.1 */
    class C05201 extends DataOutputStream {
        final /* synthetic */ km f1038a;

        C05201(km kmVar, OutputStream outputStream) {
            this.f1038a = kmVar;
            super(outputStream);
        }

        public void close() {
        }
    }

    /* renamed from: com.flurry.sdk.km.2 */
    class C05212 extends DataInputStream {
        final /* synthetic */ km f1039a;

        C05212(km kmVar, InputStream inputStream) {
            this.f1039a = kmVar;
            super(inputStream);
        }

        public void close() {
        }
    }

    public km(String str, int i, kn<T> knVar) {
        this.f1040a = str;
        this.f1041b = i;
        this.f1042c = knVar;
    }

    public void m1176a(OutputStream outputStream, T t) throws IOException {
        if (outputStream != null && this.f1042c != null) {
            OutputStream c05201 = new C05201(this, outputStream);
            c05201.writeUTF(this.f1040a);
            c05201.writeInt(this.f1041b);
            kk a = this.f1042c.m553a(this.f1041b);
            if (a == null) {
                throw new IOException("No serializer for version: " + this.f1041b);
            }
            a.m531a(c05201, t);
            c05201.flush();
        }
    }

    public T m1177b(InputStream inputStream) throws IOException {
        if (inputStream == null || this.f1042c == null) {
            return null;
        }
        InputStream c05212 = new C05212(this, inputStream);
        String readUTF = c05212.readUTF();
        if (this.f1040a.equals(readUTF)) {
            int readInt = c05212.readInt();
            kk a = this.f1042c.m553a(readInt);
            if (a != null) {
                return a.m532b(c05212);
            }
            throw new IOException("No serializer for version: " + readInt);
        }
        throw new IOException("Signature: " + readUTF + " is invalid");
    }
}
