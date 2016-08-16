package com.bosch.myspin.serversdk.service.client;

import android.content.Context;

/* renamed from: com.bosch.myspin.serversdk.service.client.a */
public class C0184a {
    private static C0184a f145a;
    private boolean f146b;
    private Context f147c;

    public C0184a() {
        this.f146b = false;
    }

    static {
        f145a = null;
    }

    public static C0184a m169a() {
        if (f145a == null) {
            f145a = new C0184a();
        }
        return f145a;
    }

    private void m170b() {
        if (this.f147c != null) {
            this.f146b = true;
        } else {
            this.f146b = false;
        }
    }

    public void m171a(Context context) {
        this.f147c = context;
        m170b();
    }
}
