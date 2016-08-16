package com.flurry.sdk;

import com.sygic.aura.poi.detail.PoiDetailFragment;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.Closeable;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.Map.Entry;
import loquendo.tts.engine.TTSConst;

public class jy extends lf {
    private static final String f962a;
    private String f963b;
    private C0484a f964c;
    private int f965d;
    private int f966e;
    private boolean f967f;
    private final jh<String, String> f968g;
    private C0480c f969j;
    private HttpURLConnection f970k;
    private boolean f971l;
    private boolean f972m;
    private Exception f973n;
    private int f974o;
    private final jh<String, String> f975p;
    private final Object f976q;

    /* renamed from: com.flurry.sdk.jy.c */
    public interface C0480c {
        void m1049a(jy jyVar);

        void m1050a(jy jyVar, InputStream inputStream) throws Exception;

        void m1051a(jy jyVar, OutputStream outputStream) throws Exception;
    }

    /* renamed from: com.flurry.sdk.jy.1 */
    class C04821 extends Thread {
        final /* synthetic */ jy f982a;

        C04821(jy jyVar) {
            this.f982a = jyVar;
        }

        public void run() {
            try {
                if (this.f982a.f970k != null) {
                    this.f982a.f970k.disconnect();
                }
            } catch (Throwable th) {
            }
        }
    }

    /* renamed from: com.flurry.sdk.jy.2 */
    static /* synthetic */ class C04832 {
        static final /* synthetic */ int[] f983a;

        static {
            f983a = new int[C0484a.values().length];
            try {
                f983a[C0484a.kPost.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                f983a[C0484a.kPut.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                f983a[C0484a.kDelete.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
            try {
                f983a[C0484a.kHead.ordinal()] = 4;
            } catch (NoSuchFieldError e4) {
            }
            try {
                f983a[C0484a.kGet.ordinal()] = 5;
            } catch (NoSuchFieldError e5) {
            }
        }
    }

    /* renamed from: com.flurry.sdk.jy.a */
    public enum C0484a {
        kUnknown,
        kGet,
        kPost,
        kPut,
        kDelete,
        kHead;

        public String toString() {
            switch (C04832.f983a[ordinal()]) {
                case TTSConst.TTSMULTILINE /*1*/:
                    return "POST";
                case TTSConst.TTSPARAGRAPH /*2*/:
                    return "PUT";
                case TTSConst.TTSUNICODE /*3*/:
                    return "DELETE";
                case TTSConst.TTSXML /*4*/:
                    return "HEAD";
                case TTSConst.TTSEVT_TEXT /*5*/:
                    return "GET";
                default:
                    return null;
            }
        }
    }

    /* renamed from: com.flurry.sdk.jy.b */
    public static class C0485b implements C0480c {
        public void m1097a(jy jyVar, OutputStream outputStream) throws Exception {
        }

        public void m1096a(jy jyVar, InputStream inputStream) throws Exception {
        }

        public void m1095a(jy jyVar) {
        }
    }

    static {
        f962a = jy.class.getSimpleName();
    }

    public jy() {
        this.f965d = PoiDetailFragment.MIN_STREET_VIEW_BYTES;
        this.f966e = 15000;
        this.f967f = true;
        this.f968g = new jh();
        this.f974o = -1;
        this.f975p = new jh();
        this.f976q = new Object();
    }

    public void m1071a(String str) {
        this.f963b = str;
    }

    public String m1074b() {
        return this.f963b;
    }

    public void m1069a(C0484a c0484a) {
        this.f964c = c0484a;
    }

    public void m1073a(boolean z) {
        this.f967f = z;
    }

    public void m1072a(String str, String str2) {
        this.f968g.m984a((Object) str, (Object) str2);
    }

    public void m1070a(C0480c c0480c) {
        this.f969j = c0480c;
    }

    public boolean m1076c() {
        boolean z;
        synchronized (this.f976q) {
            z = this.f972m;
        }
        return z;
    }

    public boolean m1077d() {
        return !m1080g() && m1078e();
    }

    public boolean m1078e() {
        return this.f974o >= 200 && this.f974o < 400;
    }

    public int m1079f() {
        return this.f974o;
    }

    public boolean m1080g() {
        return this.f973n != null;
    }

    public List<String> m1075b(String str) {
        if (str == null) {
            return null;
        }
        return this.f975p.m981a((Object) str);
    }

    public void m1081h() {
        synchronized (this.f976q) {
            this.f972m = true;
        }
        m1067q();
    }

    public void m1068a() {
        try {
            if (this.f963b != null) {
                if (iw.m897a().m903c()) {
                    if (this.f964c == null || C0484a.kUnknown.equals(this.f964c)) {
                        this.f964c = C0484a.kGet;
                    }
                    m1064n();
                    jq.m1016a(4, f962a, "HTTP status: " + this.f974o + " for url: " + this.f963b);
                    m1065o();
                    return;
                }
                jq.m1016a(3, f962a, "Network not available, aborting http request: " + this.f963b);
                m1065o();
            }
        } catch (Throwable e) {
            jq.m1016a(4, f962a, "HTTP status: " + this.f974o + " for url: " + this.f963b);
            jq.m1017a(3, f962a, "Exception during http request: " + this.f963b, e);
            this.f973n = e;
        } finally {
            m1065o();
        }
    }

    public void m1082i() {
        m1081h();
    }

    private void m1064n() throws Exception {
        Closeable outputStream;
        Closeable bufferedOutputStream;
        Throwable th;
        Closeable closeable = null;
        if (!this.f972m) {
            this.f963b = lc.m1261a(this.f963b);
            this.f970k = (HttpURLConnection) new URL(this.f963b).openConnection();
            this.f970k.setConnectTimeout(this.f965d);
            this.f970k.setReadTimeout(this.f966e);
            this.f970k.setRequestMethod(this.f964c.toString());
            this.f970k.setInstanceFollowRedirects(this.f967f);
            this.f970k.setDoOutput(C0484a.kPost.equals(this.f964c));
            this.f970k.setDoInput(true);
            for (Entry entry : this.f968g.m985b()) {
                this.f970k.addRequestProperty((String) entry.getKey(), (String) entry.getValue());
            }
            if (!(C0484a.kGet.equals(this.f964c) || C0484a.kPost.equals(this.f964c))) {
                this.f970k.setRequestProperty("Accept-Encoding", "");
            }
            if (this.f972m) {
                m1066p();
                return;
            }
            if (C0484a.kPost.equals(this.f964c)) {
                try {
                    outputStream = this.f970k.getOutputStream();
                    try {
                        bufferedOutputStream = new BufferedOutputStream(outputStream);
                    } catch (Throwable th2) {
                        th = th2;
                        bufferedOutputStream = null;
                        closeable = outputStream;
                        lc.m1265a(bufferedOutputStream);
                        lc.m1265a(closeable);
                        throw th;
                    }
                    try {
                        m1063a((OutputStream) bufferedOutputStream);
                        lc.m1265a(bufferedOutputStream);
                        lc.m1265a(outputStream);
                    } catch (Throwable th3) {
                        th = th3;
                        closeable = outputStream;
                        lc.m1265a(bufferedOutputStream);
                        lc.m1265a(closeable);
                        throw th;
                    }
                } catch (Throwable th4) {
                    m1066p();
                }
            }
            this.f974o = this.f970k.getResponseCode();
            for (Entry entry2 : this.f970k.getHeaderFields().entrySet()) {
                for (Object a : (List) entry2.getValue()) {
                    this.f975p.m984a(entry2.getKey(), a);
                }
            }
            if (!C0484a.kGet.equals(this.f964c) && !C0484a.kPost.equals(this.f964c)) {
                m1066p();
            } else if (this.f972m) {
                m1066p();
            } else {
                try {
                    outputStream = this.f970k.getInputStream();
                    try {
                        bufferedOutputStream = new BufferedInputStream(outputStream);
                    } catch (Throwable th5) {
                        th = th5;
                        bufferedOutputStream = outputStream;
                        lc.m1265a(closeable);
                        lc.m1265a(bufferedOutputStream);
                        throw th;
                    }
                    try {
                        m1062a((InputStream) bufferedOutputStream);
                        lc.m1265a(bufferedOutputStream);
                        lc.m1265a(outputStream);
                        m1066p();
                    } catch (Throwable th6) {
                        th = th6;
                        closeable = bufferedOutputStream;
                        bufferedOutputStream = outputStream;
                        lc.m1265a(closeable);
                        lc.m1265a(bufferedOutputStream);
                        throw th;
                    }
                } catch (Throwable th7) {
                    th = th7;
                    bufferedOutputStream = null;
                    lc.m1265a(closeable);
                    lc.m1265a(bufferedOutputStream);
                    throw th;
                }
            }
        }
    }

    private void m1063a(OutputStream outputStream) throws Exception {
        if (this.f969j != null && !m1076c() && outputStream != null) {
            this.f969j.m1051a(this, outputStream);
        }
    }

    private void m1062a(InputStream inputStream) throws Exception {
        if (this.f969j != null && !m1076c() && inputStream != null) {
            this.f969j.m1050a(this, inputStream);
        }
    }

    private void m1065o() {
        if (this.f969j != null && !m1076c()) {
            this.f969j.m1049a(this);
        }
    }

    private void m1066p() {
        if (!this.f971l) {
            this.f971l = true;
            if (this.f970k != null) {
                this.f970k.disconnect();
            }
        }
    }

    private void m1067q() {
        if (!this.f971l) {
            this.f971l = true;
            if (this.f970k != null) {
                new C04821(this).start();
            }
        }
    }
}
