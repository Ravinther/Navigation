package com.flurry.sdk;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class id extends ka {
    protected long f678a;

    /* renamed from: com.flurry.sdk.id.a */
    public static class C0431a implements kk<id> {

        /* renamed from: com.flurry.sdk.id.a.1 */
        class C04291 extends DataOutputStream {
            final /* synthetic */ C0431a f669a;

            C04291(C0431a c0431a, OutputStream outputStream) {
                this.f669a = c0431a;
                super(outputStream);
            }

            public void close() {
            }
        }

        /* renamed from: com.flurry.sdk.id.a.2 */
        class C04302 extends DataInputStream {
            final /* synthetic */ C0431a f670a;

            C04302(C0431a c0431a, InputStream inputStream) {
                this.f670a = c0431a;
                super(inputStream);
            }

            public void close() {
            }
        }

        public /* synthetic */ Object m617b(InputStream inputStream) throws IOException {
            return m614a(inputStream);
        }

        public void m615a(OutputStream outputStream, id idVar) throws IOException {
            if (outputStream != null && idVar != null) {
                DataOutputStream c04291 = new C04291(this, outputStream);
                c04291.writeLong(idVar.m629f());
                c04291.writeBoolean(idVar.m630g());
                c04291.writeInt(idVar.m631h());
                c04291.writeUTF(idVar.m632i());
                c04291.writeUTF(idVar.m633j());
                c04291.writeLong(idVar.f678a);
                c04291.flush();
            }
        }

        public id m614a(InputStream inputStream) throws IOException {
            if (inputStream == null) {
                return null;
            }
            DataInputStream c04302 = new C04302(this, inputStream);
            id idVar = new id();
            idVar.m623a(c04302.readLong());
            idVar.m625a(c04302.readBoolean());
            idVar.m622a(c04302.readInt());
            idVar.m626b(c04302.readUTF());
            idVar.m627c(c04302.readUTF());
            idVar.f678a = c04302.readLong();
            return idVar;
        }
    }

    /* renamed from: com.flurry.sdk.id.b */
    public static class C0433b implements kk<id> {

        /* renamed from: com.flurry.sdk.id.b.1 */
        class C04321 extends DataInputStream {
            final /* synthetic */ C0433b f671a;

            C04321(C0433b c0433b, InputStream inputStream) {
                this.f671a = c0433b;
                super(inputStream);
            }

            public void close() {
            }
        }

        public /* synthetic */ Object m621b(InputStream inputStream) throws IOException {
            return m618a(inputStream);
        }

        public void m619a(OutputStream outputStream, id idVar) throws IOException {
            throw new UnsupportedOperationException("Serialization not supported");
        }

        public id m618a(InputStream inputStream) throws IOException {
            if (inputStream == null) {
                return null;
            }
            DataInputStream c04321 = new C04321(this, inputStream);
            id idVar = new id();
            idVar.m623a(c04321.readLong());
            idVar.m625a(c04321.readBoolean());
            idVar.m622a(c04321.readInt());
            idVar.f678a = c04321.readLong();
            idVar.m624a(c04321.readUTF());
            return idVar;
        }
    }

    private id() {
    }

    public id(long j, String str, String str2, long j2) {
        m624a(str2);
        m623a(j2);
        this.f678a = j;
    }

    public long m635a() {
        return this.f678a;
    }
}
