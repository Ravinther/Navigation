package com.flurry.sdk;

import com.flurry.sdk.kd.C0506a;
import com.flurry.sdk.kf.C0517a;
import java.io.Closeable;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;

public class ke {
    public static final Integer f1027a;
    private static final String f1028d;
    String f1029b;
    LinkedHashMap<String, List<String>> f1030c;

    /* renamed from: com.flurry.sdk.ke.1 */
    class C05071 implements kn<List<kf>> {
        final /* synthetic */ ke f1020a;

        C05071(ke keVar) {
            this.f1020a = keVar;
        }

        public kk<List<kf>> m1126a(int i) {
            return new kj(new C0517a());
        }
    }

    /* renamed from: com.flurry.sdk.ke.2 */
    class C05082 implements kn<kd> {
        final /* synthetic */ ke f1021a;

        C05082(ke keVar) {
            this.f1021a = keVar;
        }

        public kk<kd> m1127a(int i) {
            return new C0506a();
        }
    }

    /* renamed from: com.flurry.sdk.ke.3 */
    class C05093 implements kn<List<kf>> {
        final /* synthetic */ ke f1022a;

        C05093(ke keVar) {
            this.f1022a = keVar;
        }

        public kk<List<kf>> m1128a(int i) {
            return new kj(new C0517a());
        }
    }

    /* renamed from: com.flurry.sdk.ke.4 */
    class C05104 implements kn<List<kf>> {
        final /* synthetic */ ke f1023a;

        C05104(ke keVar) {
            this.f1023a = keVar;
        }

        public kk<List<kf>> m1129a(int i) {
            return new kj(new C0517a());
        }
    }

    /* renamed from: com.flurry.sdk.ke.5 */
    class C05115 implements kn<List<kf>> {
        final /* synthetic */ ke f1024a;

        C05115(ke keVar) {
            this.f1024a = keVar;
        }

        public kk<List<kf>> m1130a(int i) {
            return new kj(new C0517a());
        }
    }

    /* renamed from: com.flurry.sdk.ke.6 */
    class C05126 implements kn<kd> {
        final /* synthetic */ ke f1025a;

        C05126(ke keVar) {
            this.f1025a = keVar;
        }

        public kk<kd> m1131a(int i) {
            return new C0506a();
        }
    }

    /* renamed from: com.flurry.sdk.ke.7 */
    class C05137 implements kn<List<kf>> {
        final /* synthetic */ ke f1026a;

        C05137(ke keVar) {
            this.f1026a = keVar;
        }

        public kk<List<kf>> m1132a(int i) {
            return new kj(new C0517a());
        }
    }

    static {
        f1028d = ke.class.getSimpleName();
        f1027a = Integer.valueOf(50);
    }

    public ke(String str) {
        m1147a(str);
    }

    void m1147a(String str) {
        this.f1029b = str + "Main";
        m1136e(this.f1029b);
    }

    private void m1136e(String str) {
        this.f1030c = new LinkedHashMap();
        List<String> arrayList = new ArrayList();
        if (m1141j(str)) {
            Collection k = m1142k(str);
            if (k != null && k.size() > 0) {
                arrayList.addAll(k);
                for (String f : arrayList) {
                    m1137f(f);
                }
            }
            m1140i(str);
        } else {
            List<kf> list = (List) new jj(jc.m946a().m957c().getFileStreamPath(m1138g(this.f1029b)), str, 1, new C05071(this)).m993a();
            if (list == null) {
                jq.m1028c(f1028d, "New main file also not found. returning..");
                return;
            }
            for (kf a : list) {
                arrayList.add(a.m1159a());
            }
        }
        for (String f2 : arrayList) {
            List h = m1139h(f2);
            if (h != null) {
                this.f1030c.put(f2, h);
            }
        }
    }

    private void m1137f(String str) {
        List<String> k = m1142k(str);
        if (k == null) {
            jq.m1028c(f1028d, "No old file to replace");
            return;
        }
        for (String str2 : k) {
            byte[] m = m1144m(str2);
            if (m == null) {
                jq.m1016a(6, f1028d, "File does not exist");
            } else {
                m1134a(str2, m);
                m1143l(str2);
            }
        }
        if (k != null) {
            m1133a(str, k, ".YFlurrySenderIndex.info.");
            m1140i(str);
        }
    }

    private String m1138g(String str) {
        return ".YFlurrySenderIndex.info." + str;
    }

    private synchronized void m1135c() {
        List linkedList = new LinkedList(this.f1030c.keySet());
        m1149b();
        if (!linkedList.isEmpty()) {
            m1133a(this.f1029b, linkedList, this.f1029b);
        }
    }

    public synchronized void m1146a(kd kdVar, String str) {
        Object obj = null;
        synchronized (this) {
            List linkedList;
            jq.m1016a(4, f1028d, "addBlockInfo" + str);
            String a = kdVar.m1124a();
            List list = (List) this.f1030c.get(str);
            if (list == null) {
                jq.m1016a(4, f1028d, "New Data Key");
                linkedList = new LinkedList();
                obj = 1;
            } else {
                linkedList = list;
            }
            linkedList.add(a);
            if (linkedList.size() > f1027a.intValue()) {
                m1150b((String) linkedList.get(0));
                linkedList.remove(0);
            }
            this.f1030c.put(str, linkedList);
            m1133a(str, linkedList, ".YFlurrySenderIndex.info.");
            if (obj != null) {
                m1135c();
            }
        }
    }

    boolean m1150b(String str) {
        return new jj(jc.m946a().m957c().getFileStreamPath(kd.m1121a(str)), ".yflurrydatasenderblock.", 1, new C05082(this)).m995b();
    }

    public boolean m1148a(String str, String str2) {
        List list = (List) this.f1030c.get(str2);
        boolean z = false;
        if (list != null) {
            m1150b(str);
            z = list.remove(str);
        }
        if (list == null || list.isEmpty()) {
            m1152d(str2);
        } else {
            this.f1030c.put(str2, list);
            m1133a(str2, list, ".YFlurrySenderIndex.info.");
        }
        return z;
    }

    public List<String> m1145a() {
        return new ArrayList(this.f1030c.keySet());
    }

    public List<String> m1151c(String str) {
        return (List) this.f1030c.get(str);
    }

    public synchronized boolean m1152d(String str) {
        boolean b;
        lc.m1272b();
        jj jjVar = new jj(jc.m946a().m957c().getFileStreamPath(m1138g(str)), ".YFlurrySenderIndex.info.", 1, new C05093(this));
        List<String> c = m1151c(str);
        if (c != null) {
            jq.m1016a(4, f1028d, "discardOutdatedBlocksForDataKey: notSentBlocks = " + c.size());
            for (String str2 : c) {
                m1150b(str2);
                jq.m1016a(4, f1028d, "discardOutdatedBlocksForDataKey: removed block = " + str2);
            }
        }
        this.f1030c.remove(str);
        b = jjVar.m995b();
        m1135c();
        return b;
    }

    void m1149b() {
        new jj(jc.m946a().m957c().getFileStreamPath(m1138g(this.f1029b)), ".YFlurrySenderIndex.info.", 1, new C05104(this)).m995b();
    }

    private synchronized List<String> m1139h(String str) {
        List<String> arrayList;
        lc.m1272b();
        jq.m1016a(5, f1028d, "Reading Index File for " + str + " file name:" + jc.m946a().m957c().getFileStreamPath(m1138g(str)));
        List<kf> list = (List) new jj(jc.m946a().m957c().getFileStreamPath(m1138g(str)), ".YFlurrySenderIndex.info.", 1, new C05115(this)).m993a();
        arrayList = new ArrayList();
        for (kf a : list) {
            arrayList.add(a.m1159a());
        }
        return arrayList;
    }

    private synchronized void m1134a(String str, byte[] bArr) {
        lc.m1272b();
        jq.m1016a(5, f1028d, "Saving Block File for " + str + " file name:" + jc.m946a().m957c().getFileStreamPath(kd.m1121a(str)));
        new jj(jc.m946a().m957c().getFileStreamPath(kd.m1121a(str)), ".yflurrydatasenderblock.", 1, new C05126(this)).m994a(new kd(bArr));
    }

    private void m1140i(String str) {
        lc.m1272b();
        jq.m1016a(5, f1028d, "Deleting Index File for " + str + " file name:" + jc.m946a().m957c().getFileStreamPath(".FlurrySenderIndex.info." + str));
        File fileStreamPath = jc.m946a().m957c().getFileStreamPath(".FlurrySenderIndex.info." + str);
        if (fileStreamPath.exists()) {
            jq.m1016a(5, f1028d, "Found file for " + str + ". Deleted - " + fileStreamPath.delete());
        }
    }

    private synchronized void m1133a(String str, List<String> list, String str2) {
        lc.m1272b();
        jq.m1016a(5, f1028d, "Saving Index File for " + str + " file name:" + jc.m946a().m957c().getFileStreamPath(m1138g(str)));
        jj jjVar = new jj(jc.m946a().m957c().getFileStreamPath(m1138g(str)), str2, 1, new C05137(this));
        List arrayList = new ArrayList();
        for (String kfVar : list) {
            arrayList.add(new kf(kfVar));
        }
        jjVar.m994a(arrayList);
    }

    private synchronized boolean m1141j(String str) {
        File fileStreamPath;
        fileStreamPath = jc.m946a().m957c().getFileStreamPath(".FlurrySenderIndex.info." + str);
        jq.m1016a(5, f1028d, "isOldIndexFilePresent: for " + str + fileStreamPath.exists());
        return fileStreamPath.exists();
    }

    private synchronized List<String> m1142k(String str) {
        Closeable dataInputStream;
        List<String> arrayList;
        Throwable th;
        Throwable th2;
        Throwable th3;
        List<String> list = null;
        synchronized (this) {
            lc.m1272b();
            jq.m1016a(5, f1028d, "Reading Index File for " + str + " file name:" + jc.m946a().m957c().getFileStreamPath(".FlurrySenderIndex.info." + str));
            File fileStreamPath = jc.m946a().m957c().getFileStreamPath(".FlurrySenderIndex.info." + str);
            if (fileStreamPath.exists()) {
                jq.m1016a(5, f1028d, "Reading Index File for " + str + " Found file.");
                try {
                    dataInputStream = new DataInputStream(new FileInputStream(fileStreamPath));
                    try {
                        int readUnsignedShort = dataInputStream.readUnsignedShort();
                        if (readUnsignedShort == 0) {
                            lc.m1265a(dataInputStream);
                        } else {
                            arrayList = new ArrayList(readUnsignedShort);
                            int i = 0;
                            while (i < readUnsignedShort) {
                                try {
                                    int readUnsignedShort2 = dataInputStream.readUnsignedShort();
                                    jq.m1016a(4, f1028d, "read iter " + i + " dataLength = " + readUnsignedShort2);
                                    byte[] bArr = new byte[readUnsignedShort2];
                                    dataInputStream.readFully(bArr);
                                    arrayList.add(new String(bArr));
                                    i++;
                                } catch (Throwable th4) {
                                    th = th4;
                                }
                            }
                            if (dataInputStream.readUnsignedShort() == 0) {
                                lc.m1265a(dataInputStream);
                                list = arrayList;
                            } else {
                                lc.m1265a(dataInputStream);
                                list = arrayList;
                            }
                        }
                    } catch (Throwable th32) {
                        th2 = th32;
                        arrayList = null;
                        th = th2;
                        try {
                            jq.m1017a(6, f1028d, "Error when loading persistent file", th);
                            lc.m1265a(dataInputStream);
                            list = arrayList;
                            return list;
                        } catch (Throwable th5) {
                            th32 = th5;
                            lc.m1265a(dataInputStream);
                            throw th32;
                        }
                    }
                } catch (Throwable th6) {
                    th32 = th6;
                    dataInputStream = null;
                    lc.m1265a(dataInputStream);
                    throw th32;
                }
            } else {
                jq.m1016a(5, f1028d, "Agent cache file doesn't exist.");
                arrayList = null;
                list = arrayList;
            }
        }
        return list;
    }

    private void m1143l(String str) {
        lc.m1272b();
        jq.m1016a(5, f1028d, "Deleting  block File for " + str + " file name:" + jc.m946a().m957c().getFileStreamPath(".flurrydatasenderblock." + str));
        File fileStreamPath = jc.m946a().m957c().getFileStreamPath(".flurrydatasenderblock." + str);
        if (fileStreamPath.exists()) {
            jq.m1016a(5, f1028d, "Found file for " + str + ". Deleted - " + fileStreamPath.delete());
        }
    }

    private byte[] m1144m(String str) {
        Closeable dataInputStream;
        Throwable th;
        Throwable th2;
        byte[] bArr = null;
        lc.m1272b();
        jq.m1016a(5, f1028d, "Reading block File for " + str + " file name:" + jc.m946a().m957c().getFileStreamPath(".flurrydatasenderblock." + str));
        File fileStreamPath = jc.m946a().m957c().getFileStreamPath(".flurrydatasenderblock." + str);
        if (fileStreamPath.exists()) {
            jq.m1016a(5, f1028d, "Reading Index File for " + str + " Found file.");
            try {
                dataInputStream = new DataInputStream(new FileInputStream(fileStreamPath));
                try {
                    int readUnsignedShort = dataInputStream.readUnsignedShort();
                    if (readUnsignedShort == 0) {
                        lc.m1265a(dataInputStream);
                    } else {
                        bArr = new byte[readUnsignedShort];
                        dataInputStream.readFully(bArr);
                        if (dataInputStream.readUnsignedShort() == 0) {
                            lc.m1265a(dataInputStream);
                        } else {
                            lc.m1265a(dataInputStream);
                        }
                    }
                } catch (Throwable th3) {
                    th = th3;
                    try {
                        jq.m1017a(6, f1028d, "Error when loading persistent file", th);
                        lc.m1265a(dataInputStream);
                        return bArr;
                    } catch (Throwable th4) {
                        th2 = th4;
                        lc.m1265a(dataInputStream);
                        throw th2;
                    }
                }
            } catch (Throwable th5) {
                dataInputStream = null;
                th2 = th5;
                lc.m1265a(dataInputStream);
                throw th2;
            }
        }
        jq.m1016a(4, f1028d, "Agent cache file doesn't exist.");
        return bArr;
    }
}
