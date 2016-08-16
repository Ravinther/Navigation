package com.flurry.sdk;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class in {
    private static final String f806a;
    private boolean f807b;
    private long f808c;
    private final List<ik> f809d;

    /* renamed from: com.flurry.sdk.in.a */
    public static class C0458a implements kk<in> {

        /* renamed from: com.flurry.sdk.in.a.1 */
        class C04571 extends DataInputStream {
            final /* synthetic */ C0458a f805a;

            C04571(C0458a c0458a, InputStream inputStream) {
                this.f805a = c0458a;
                super(inputStream);
            }

            public void close() {
            }
        }

        public /* synthetic */ Object m809b(InputStream inputStream) throws IOException {
            return m806a(inputStream);
        }

        public void m807a(OutputStream outputStream, in inVar) throws IOException {
            throw new UnsupportedOperationException("Serialization not supported");
        }

        public in m806a(InputStream inputStream) throws IOException {
            if (inputStream == null) {
                return null;
            }
            DataInputStream c04571 = new C04571(this, inputStream);
            in inVar = new in();
            c04571.readUTF();
            c04571.readUTF();
            inVar.f807b = c04571.readBoolean();
            inVar.f808c = c04571.readLong();
            while (true) {
                int readUnsignedShort = c04571.readUnsignedShort();
                if (readUnsignedShort == 0) {
                    return inVar;
                }
                byte[] bArr = new byte[readUnsignedShort];
                c04571.readFully(bArr);
                inVar.f809d.add(0, new ik(bArr));
            }
        }
    }

    static {
        f806a = in.class.getSimpleName();
    }

    public in() {
        this.f809d = new ArrayList();
    }

    public boolean m813a() {
        return this.f807b;
    }

    public long m814b() {
        return this.f808c;
    }

    public List<ik> m815c() {
        return Collections.unmodifiableList(this.f809d);
    }
}
