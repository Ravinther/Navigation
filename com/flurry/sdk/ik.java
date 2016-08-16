package com.flurry.sdk;

import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class ik {
    private static final String f730b;
    byte[] f731a;

    /* renamed from: com.flurry.sdk.ik.a */
    public static class C0445a implements kk<ik> {

        /* renamed from: com.flurry.sdk.ik.a.1 */
        class C04431 extends DataOutputStream {
            final /* synthetic */ C0445a f728a;

            C04431(C0445a c0445a, OutputStream outputStream) {
                this.f728a = c0445a;
                super(outputStream);
            }

            public void close() {
            }
        }

        /* renamed from: com.flurry.sdk.ik.a.2 */
        class C04442 extends DataInputStream {
            final /* synthetic */ C0445a f729a;

            C04442(C0445a c0445a, InputStream inputStream) {
                this.f729a = c0445a;
                super(inputStream);
            }

            public void close() {
            }
        }

        public /* synthetic */ Object m711b(InputStream inputStream) throws IOException {
            return m708a(inputStream);
        }

        public void m709a(OutputStream outputStream, ik ikVar) throws IOException {
            if (outputStream != null && ikVar != null) {
                DataOutputStream c04431 = new C04431(this, outputStream);
                int i = 0;
                if (ikVar.f731a != null) {
                    i = ikVar.f731a.length;
                }
                c04431.writeShort(i);
                if (i > 0) {
                    c04431.write(ikVar.f731a);
                }
                c04431.flush();
            }
        }

        public ik m708a(InputStream inputStream) throws IOException {
            if (inputStream == null) {
                return null;
            }
            DataInputStream c04442 = new C04442(this, inputStream);
            ik ikVar = new ik();
            int readUnsignedShort = c04442.readUnsignedShort();
            if (readUnsignedShort > 0) {
                byte[] bArr = new byte[readUnsignedShort];
                c04442.readFully(bArr);
                ikVar.f731a = bArr;
            }
            return ikVar;
        }
    }

    static {
        f730b = ik.class.getSimpleName();
    }

    private ik() {
    }

    public ik(byte[] bArr) {
        this.f731a = bArr;
    }

    public ik(il ilVar) throws IOException {
        Throwable e;
        Closeable closeable = null;
        Closeable dataOutputStream;
        try {
            OutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            dataOutputStream = new DataOutputStream(byteArrayOutputStream);
            try {
                int i;
                int i2;
                dataOutputStream.writeShort(5);
                dataOutputStream.writeUTF(ilVar.m713a());
                dataOutputStream.writeLong(ilVar.m723b());
                dataOutputStream.writeLong(ilVar.m729c());
                dataOutputStream.writeLong(ilVar.m733d());
                Map e2 = ilVar.m736e();
                if (e2 == null) {
                    dataOutputStream.writeShort(0);
                } else {
                    dataOutputStream.writeShort(e2.size());
                    for (Entry entry : e2.entrySet()) {
                        dataOutputStream.writeUTF((String) entry.getKey());
                        dataOutputStream.writeUTF((String) entry.getValue());
                        dataOutputStream.writeByte(0);
                    }
                }
                dataOutputStream.writeUTF(ilVar.m737f());
                dataOutputStream.writeUTF(ilVar.m738g());
                dataOutputStream.writeByte(ilVar.m739h());
                dataOutputStream.writeByte(ilVar.m740i());
                dataOutputStream.writeUTF(ilVar.m741j());
                if (ilVar.m742k() == null) {
                    dataOutputStream.writeBoolean(false);
                } else {
                    dataOutputStream.writeBoolean(true);
                    dataOutputStream.writeDouble(lc.m1259a(ilVar.m742k().getLatitude(), 3));
                    dataOutputStream.writeDouble(lc.m1259a(ilVar.m742k().getLongitude(), 3));
                    dataOutputStream.writeFloat(ilVar.m742k().getAccuracy());
                }
                dataOutputStream.writeInt(ilVar.m743l());
                dataOutputStream.writeByte(-1);
                dataOutputStream.writeByte(-1);
                dataOutputStream.writeByte(ilVar.m744m());
                if (ilVar.m745n() == null) {
                    dataOutputStream.writeBoolean(false);
                } else {
                    dataOutputStream.writeBoolean(true);
                    dataOutputStream.writeLong(ilVar.m745n().longValue());
                }
                e2 = ilVar.m746o();
                if (e2 == null) {
                    dataOutputStream.writeShort(0);
                } else {
                    dataOutputStream.writeShort(e2.size());
                    for (Entry entry2 : e2.entrySet()) {
                        dataOutputStream.writeUTF((String) entry2.getKey());
                        dataOutputStream.writeInt(((ig) entry2.getValue()).f701a);
                    }
                }
                List<ih> p = ilVar.m747p();
                if (p == null) {
                    dataOutputStream.writeShort(0);
                } else {
                    dataOutputStream.writeShort(p.size());
                    for (ih e3 : p) {
                        dataOutputStream.write(e3.m677e());
                    }
                }
                dataOutputStream.writeBoolean(ilVar.m748q());
                List s = ilVar.m750s();
                if (s != null) {
                    int i3 = 0;
                    i = 0;
                    for (i2 = 0; i2 < s.size(); i2++) {
                        i3 += ((C0438if) s.get(i2)).m666a();
                        if (i3 > 160000) {
                            jq.m1016a(5, f730b, "Error Log size exceeded. No more event details logged.");
                            i2 = i;
                            break;
                        }
                        i++;
                    }
                    i2 = i;
                } else {
                    i2 = 0;
                }
                dataOutputStream.writeInt(ilVar.m749r());
                dataOutputStream.writeShort(i2);
                for (i = 0; i < i2; i++) {
                    dataOutputStream.write(((C0438if) s.get(i)).m667b());
                }
                dataOutputStream.writeInt(-1);
                dataOutputStream.writeShort(0);
                dataOutputStream.writeShort(0);
                dataOutputStream.writeShort(0);
                this.f731a = byteArrayOutputStream.toByteArray();
                lc.m1265a(dataOutputStream);
            } catch (IOException e4) {
                e = e4;
                closeable = dataOutputStream;
                try {
                    jq.m1017a(6, f730b, "", e);
                    throw e;
                } catch (Throwable th) {
                    e = th;
                    dataOutputStream = closeable;
                    lc.m1265a(dataOutputStream);
                    throw e;
                }
            } catch (Throwable th2) {
                e = th2;
                lc.m1265a(dataOutputStream);
                throw e;
            }
        } catch (IOException e5) {
            e = e5;
            jq.m1017a(6, f730b, "", e);
            throw e;
        } catch (Throwable th3) {
            e = th3;
            dataOutputStream = null;
            lc.m1265a(dataOutputStream);
            throw e;
        }
    }

    public byte[] m712a() {
        return this.f731a;
    }
}
