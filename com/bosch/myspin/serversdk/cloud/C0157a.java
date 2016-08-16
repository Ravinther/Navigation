package com.bosch.myspin.serversdk.cloud;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import com.bosch.myspin.serversdk.utils.C0236e;
import com.bosch.myspin.serversdk.utils.C0236e.C0234a;
import com.bosch.myspin.serversdk.utils.Logger;
import com.bosch.myspin.serversdk.utils.Logger.LogComponent;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/* renamed from: com.bosch.myspin.serversdk.cloud.a */
public class C0157a {
    private static final LogComponent f65a;
    private boolean f66b;
    private final Context f67c;
    private C0159c f68d;
    private Set<Map<Integer, String>> f69e;
    private ServiceConnection f70f;

    static {
        f65a = LogComponent.Config;
    }

    public C0157a(Context context) {
        this.f69e = new HashSet();
        this.f70f = new C0158b(this);
        if (context == null) {
            throw new IllegalArgumentException("CloudServiceClient: Context can't be null");
        }
        this.f67c = context;
        m80c();
    }

    public void m82a() {
        if (this.f68d == null || !this.f66b) {
            m80c();
        }
    }

    private void m80c() {
        try {
            if (this.f67c.bindService(C0236e.m368a(this.f67c, new Intent("com.bosch.myspin.ACTION_BIND_CLOUD_SERVICE_INTERFACE")), this.f70f, 1)) {
                this.f66b = true;
            } else {
                this.f66b = false;
            }
        } catch (Throwable e) {
            this.f66b = false;
            Logger.logError(f65a, "CloudServiceClient/Cant bind cloud service, make sure that only one launcher app is installed", e);
        } catch (C0234a e2) {
            Logger.logError(f65a, "CloudServiceClient/Cant bind mySPIN service, make sure that a launcher app is installed.");
            this.f66b = false;
        }
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private synchronized void m77a(java.util.Map<java.lang.Integer, java.lang.String> r4) {
        /*
        r3 = this;
        monitor-enter(r3);
        if (r4 == 0) goto L_0x000d;
    L_0x0003:
        r0 = r4.keySet();	 Catch:{ all -> 0x0038 }
        r0 = r0.size();	 Catch:{ all -> 0x0038 }
        if (r0 != 0) goto L_0x0015;
    L_0x000d:
        r0 = f65a;	 Catch:{ all -> 0x0038 }
        r1 = "CloudServiceClient/Event is null or empty, can't handle it!";
        com.bosch.myspin.serversdk.utils.Logger.logError(r0, r1);	 Catch:{ all -> 0x0038 }
    L_0x0015:
        r0 = r3.f68d;	 Catch:{ RemoteException -> 0x002e }
        if (r0 != 0) goto L_0x0028;
    L_0x0019:
        r0 = f65a;	 Catch:{ RemoteException -> 0x002e }
        r1 = "CloudServiceClient/onAnalyticsEvent not connected to the service, caching.";
        com.bosch.myspin.serversdk.utils.Logger.logDebug(r0, r1);	 Catch:{ RemoteException -> 0x002e }
        r0 = r3.f69e;	 Catch:{ RemoteException -> 0x002e }
        r0.add(r4);	 Catch:{ RemoteException -> 0x002e }
    L_0x0026:
        monitor-exit(r3);
        return;
    L_0x0028:
        r0 = r3.f68d;	 Catch:{ RemoteException -> 0x002e }
        r0.m84a(r4);	 Catch:{ RemoteException -> 0x002e }
        goto L_0x0026;
    L_0x002e:
        r0 = move-exception;
        r1 = f65a;	 Catch:{ all -> 0x0038 }
        r2 = "CloudServiceClient/onAnalyticsEventCalling remote method not possible as there is no service connection yet!";
        com.bosch.myspin.serversdk.utils.Logger.logError(r1, r2, r0);	 Catch:{ all -> 0x0038 }
        goto L_0x0026;
    L_0x0038:
        r0 = move-exception;
        monitor-exit(r3);
        throw r0;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.bosch.myspin.serversdk.cloud.a.a(java.util.Map):void");
    }

    @SuppressLint({"UseSparseArrays"})
    private Map<Integer, String> m74a(Activity activity) {
        Map<Integer, String> hashMap = new HashMap();
        String str = "1.3.0";
        try {
            String packageName = this.f67c.getPackageName();
            String valueOf = String.valueOf(this.f67c.getPackageManager().getPackageInfo(this.f67c.getPackageName(), 0).versionCode);
            hashMap.put(Integer.valueOf(1), packageName);
            hashMap.put(Integer.valueOf(2), valueOf);
            hashMap.put(Integer.valueOf(3), str);
            if (activity != null) {
                hashMap.put(Integer.valueOf(7), activity.getComponentName().getClassName());
            }
        } catch (Throwable e) {
            Logger.logError(f65a, "CloudServiceClient//onAnalyticsEventCan't create an event!", e);
        }
        return hashMap;
    }

    public synchronized void m83a(Activity activity, boolean z) {
        if (activity != null) {
            Logger.logDebug(f65a, "CloudServiceClient/onActivityVisibilityEvent: " + activity.getPackageName() + " " + z);
        }
        if (z) {
            m79b(activity);
        } else {
            m81c(activity);
        }
    }

    private synchronized void m79b(Activity activity) {
        Map a = m74a(activity);
        a.put(Integer.valueOf(4), String.valueOf(true));
        m77a(a);
    }

    private synchronized void m81c(Activity activity) {
        Map a = m74a(activity);
        a.put(Integer.valueOf(5), String.valueOf(true));
        m77a(a);
    }
}
