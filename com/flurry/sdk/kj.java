package com.flurry.sdk;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

public class kj<T> implements kk<List<T>> {
    kk<T> f1037a;

    /* renamed from: com.flurry.sdk.kj.1 */
    class C05181 extends DataOutputStream {
        final /* synthetic */ kj f1035a;

        C05181(kj kjVar, OutputStream outputStream) {
            this.f1035a = kjVar;
            super(outputStream);
        }

        public void close() {
        }
    }

    /* renamed from: com.flurry.sdk.kj.2 */
    class C05192 extends DataInputStream {
        final /* synthetic */ kj f1036a;

        C05192(kj kjVar, InputStream inputStream) {
            this.f1036a = kjVar;
            super(inputStream);
        }

        public void close() {
        }
    }

    public /* synthetic */ Object m1171b(InputStream inputStream) throws IOException {
        return m1168a(inputStream);
    }

    public kj(kk<T> kkVar) {
        if (kkVar == null) {
            throw new IllegalArgumentException("recordSerializer cannot be null");
        }
        this.f1037a = kkVar;
    }

    public void m1170a(OutputStream outputStream, List<T> list) throws IOException {
        int i = 0;
        if (outputStream != null) {
            int size;
            DataOutputStream c05181 = new C05181(this, outputStream);
            if (list != null) {
                size = list.size();
            } else {
                size = 0;
            }
            c05181.writeInt(size);
            while (i < size) {
                this.f1037a.m531a(outputStream, list.get(i));
                i++;
            }
            c05181.flush();
        }
    }

    public List<T> m1168a(InputStream inputStream) throws IOException {
        if (inputStream == null) {
            return null;
        }
        int readInt = new C05192(this, inputStream).readInt();
        List<T> arrayList = new ArrayList(readInt);
        for (int i = 0; i < readInt; i++) {
            Object b = this.f1037a.m532b(inputStream);
            if (b == null) {
                throw new IOException("Missing record.");
            }
            arrayList.add(b);
        }
        return arrayList;
    }
}
