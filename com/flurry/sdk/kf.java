package com.flurry.sdk;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class kf {
    private String f1033a;

    /* renamed from: com.flurry.sdk.kf.a */
    public static class C0517a implements kk<kf> {

        /* renamed from: com.flurry.sdk.kf.a.1 */
        class C05151 extends DataOutputStream {
            final /* synthetic */ C0517a f1031a;

            C05151(C0517a c0517a, OutputStream outputStream) {
                this.f1031a = c0517a;
                super(outputStream);
            }

            public void close() {
            }
        }

        /* renamed from: com.flurry.sdk.kf.a.2 */
        class C05162 extends DataInputStream {
            final /* synthetic */ C0517a f1032a;

            C05162(C0517a c0517a, InputStream inputStream) {
                this.f1032a = c0517a;
                super(inputStream);
            }

            public void close() {
            }
        }

        public /* synthetic */ Object m1156b(InputStream inputStream) throws IOException {
            return m1153a(inputStream);
        }

        public void m1154a(OutputStream outputStream, kf kfVar) throws IOException {
            if (outputStream != null && kfVar != null) {
                DataOutputStream c05151 = new C05151(this, outputStream);
                c05151.writeUTF(kfVar.f1033a);
                c05151.flush();
            }
        }

        public kf m1153a(InputStream inputStream) throws IOException {
            if (inputStream == null) {
                return null;
            }
            DataInputStream c05162 = new C05162(this, inputStream);
            kf kfVar = new kf();
            kfVar.f1033a = c05162.readUTF();
            return kfVar;
        }
    }

    private kf() {
    }

    public kf(String str) {
        this.f1033a = str;
    }

    public String m1159a() {
        return this.f1033a;
    }
}
