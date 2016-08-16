package com.flurry.sdk;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import loquendo.tts.engine.TTSConst;

public class iw extends BroadcastReceiver {
    private static iw f861c;
    boolean f862a;
    boolean f863b;
    private boolean f864d;

    /* renamed from: com.flurry.sdk.iw.a */
    public enum C0467a {
        NONE_OR_UNKNOWN(0),
        NETWORK_AVAILABLE(1),
        WIFI(2),
        CELL(3);
        
        private int f860e;

        private C0467a(int i) {
            this.f860e = i;
        }

        public int m896a() {
            return this.f860e;
        }
    }

    public static synchronized iw m897a() {
        iw iwVar;
        synchronized (iw.class) {
            if (f861c == null) {
                f861c = new iw();
            }
            iwVar = f861c;
        }
        return iwVar;
    }

    public static synchronized void m899b() {
        synchronized (iw.class) {
            if (f861c != null) {
                f861c.m901f();
            }
            f861c = null;
        }
    }

    private iw() {
        boolean z = false;
        this.f864d = false;
        Context c = jc.m946a().m957c();
        if (c.checkCallingOrSelfPermission("android.permission.ACCESS_NETWORK_STATE") == 0) {
            z = true;
        }
        this.f864d = z;
        this.f863b = m898a(c);
        if (this.f864d) {
            m900e();
        }
    }

    public boolean m903c() {
        return this.f863b;
    }

    private synchronized void m900e() {
        if (!this.f862a) {
            Context c = jc.m946a().m957c();
            this.f863b = m898a(c);
            c.registerReceiver(this, new IntentFilter("android.net.conn.CONNECTIVITY_CHANGE"));
            this.f862a = true;
        }
    }

    private synchronized void m901f() {
        if (this.f862a) {
            jc.m946a().m957c().unregisterReceiver(this);
            this.f862a = false;
        }
    }

    public void onReceive(Context context, Intent intent) {
        boolean a = m898a(context);
        if (this.f863b != a) {
            this.f863b = a;
            iv ivVar = new iv();
            ivVar.f853a = a;
            ivVar.f854b = m904d();
            ivVar.m868b();
        }
    }

    private boolean m898a(Context context) {
        if (!this.f864d || context == null) {
            return true;
        }
        NetworkInfo activeNetworkInfo = m902g().getActiveNetworkInfo();
        if (activeNetworkInfo == null || !activeNetworkInfo.isConnected()) {
            return false;
        }
        return true;
    }

    public C0467a m904d() {
        if (!this.f864d) {
            return C0467a.NONE_OR_UNKNOWN;
        }
        NetworkInfo activeNetworkInfo = m902g().getActiveNetworkInfo();
        if (activeNetworkInfo == null || !activeNetworkInfo.isConnected()) {
            return C0467a.NONE_OR_UNKNOWN;
        }
        switch (activeNetworkInfo.getType()) {
            case TTSConst.TTSEVT_AUDIOSTART /*0*/:
            case TTSConst.TTSPARAGRAPH /*2*/:
            case TTSConst.TTSUNICODE /*3*/:
            case TTSConst.TTSXML /*4*/:
            case TTSConst.TTSEVT_TEXT /*5*/:
                return C0467a.CELL;
            case TTSConst.TTSMULTILINE /*1*/:
                return C0467a.WIFI;
            case TTSConst.TTSEVT_TAG /*8*/:
                return C0467a.NONE_OR_UNKNOWN;
            default:
                if (activeNetworkInfo.isConnected()) {
                    return C0467a.NETWORK_AVAILABLE;
                }
                return C0467a.NONE_OR_UNKNOWN;
        }
    }

    private ConnectivityManager m902g() {
        return (ConnectivityManager) jc.m946a().m957c().getSystemService("connectivity");
    }
}
