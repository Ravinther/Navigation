package com.flurry.sdk;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class hy {
    private long f626a;
    private boolean f627b;
    private byte[] f628c;

    /* renamed from: com.flurry.sdk.hy.a */
    public static class C0415a implements kk<hy> {

        /* renamed from: com.flurry.sdk.hy.a.1 */
        class C04131 extends DataOutputStream {
            final /* synthetic */ C0415a f624a;

            C04131(C0415a c0415a, OutputStream outputStream) {
                this.f624a = c0415a;
                super(outputStream);
            }

            public void close() {
            }
        }

        /* renamed from: com.flurry.sdk.hy.a.2 */
        class C04142 extends DataInputStream {
            final /* synthetic */ C0415a f625a;

            C04142(C0415a c0415a, InputStream inputStream) {
                this.f625a = c0415a;
                super(inputStream);
            }

            public void close() {
            }
        }

        public /* synthetic */ Object m536b(InputStream inputStream) throws IOException {
            return m533a(inputStream);
        }

        public void m534a(OutputStream outputStream, hy hyVar) throws IOException {
            if (outputStream != null && hyVar != null) {
                DataOutputStream c04131 = new C04131(this, outputStream);
                c04131.writeLong(hyVar.f626a);
                c04131.writeBoolean(hyVar.f627b);
                c04131.writeInt(hyVar.f628c.length);
                c04131.write(hyVar.f628c);
                c04131.flush();
            }
        }

        public hy m533a(InputStream inputStream) throws IOException {
            if (inputStream == null) {
                return null;
            }
            DataInputStream c04142 = new C04142(this, inputStream);
            hy hyVar = new hy();
            hyVar.f626a = c04142.readLong();
            hyVar.f627b = c04142.readBoolean();
            hyVar.f628c = new byte[c04142.readInt()];
            c04142.readFully(hyVar.f628c);
            return hyVar;
        }
    }

    public long m543a() {
        return this.f626a;
    }

    public boolean m547b() {
        return this.f627b;
    }

    public byte[] m548c() {
        return this.f628c;
    }

    public void m544a(long j) {
        this.f626a = j;
    }

    public void m545a(boolean z) {
        this.f627b = z;
    }

    public void m546a(byte[] bArr) {
        this.f628c = bArr;
    }
}
