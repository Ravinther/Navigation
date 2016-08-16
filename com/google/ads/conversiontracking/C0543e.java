package com.google.ads.conversiontracking;

import android.content.Context;
import android.net.http.AndroidHttpClient;
import android.util.Log;
import com.google.ads.conversiontracking.C0552g.C0550c;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;

/* renamed from: com.google.ads.conversiontracking.e */
public class C0543e {
    private final Object f1167a;
    private final List<C0539d> f1168b;
    private Context f1169c;
    private boolean f1170d;
    private boolean f1171e;
    private C0545f f1172f;

    /* renamed from: com.google.ads.conversiontracking.e.1 */
    class C05401 implements Runnable {
        final /* synthetic */ C0539d f1162a;
        final /* synthetic */ C0543e f1163b;

        C05401(C0543e c0543e, C0539d c0539d) {
            this.f1163b = c0543e;
            this.f1162a = c0539d;
        }

        public void run() {
            this.f1163b.m1339a(this.f1162a);
        }
    }

    /* renamed from: com.google.ads.conversiontracking.e.a */
    private class C0541a implements Runnable {
        final /* synthetic */ C0543e f1164a;

        private C0541a(C0543e c0543e) {
            this.f1164a = c0543e;
        }

        public void run() {
            synchronized (this.f1164a.f1167a) {
                if (this.f1164a.f1171e && C0552g.m1406d(this.f1164a.f1169c) && !this.f1164a.f1170d) {
                    this.f1164a.f1168b.addAll(this.f1164a.f1172f.m1345a(100));
                    C0552g.m1405c(this.f1164a.f1169c);
                    this.f1164a.f1170d = true;
                    this.f1164a.f1167a.notify();
                    return;
                }
            }
        }
    }

    /* renamed from: com.google.ads.conversiontracking.e.b */
    public class C0542b implements Runnable {
        protected long f1165a;
        final /* synthetic */ C0543e f1166b;

        public C0542b(C0543e c0543e) {
            this.f1166b = c0543e;
            this.f1165a = 0;
        }

        /* JADX WARNING: inconsistent code. */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public void run() {
            /*
            r6 = this;
            r5 = 0;
            r0 = r6.f1166b;	 Catch:{ InterruptedException -> 0x002d }
            r1 = 1;
            r0.f1171e = r1;	 Catch:{ InterruptedException -> 0x002d }
        L_0x0007:
            r0 = r6.f1166b;	 Catch:{ InterruptedException -> 0x002d }
            r1 = r0.f1167a;	 Catch:{ InterruptedException -> 0x002d }
            monitor-enter(r1);	 Catch:{ InterruptedException -> 0x002d }
        L_0x000e:
            r0 = r6.f1166b;	 Catch:{ all -> 0x002a }
            r0 = r0.f1168b;	 Catch:{ all -> 0x002a }
            r0 = r0.isEmpty();	 Catch:{ all -> 0x002a }
            if (r0 == 0) goto L_0x003d;
        L_0x001a:
            r0 = r6.f1166b;	 Catch:{ all -> 0x002a }
            r2 = 0;
            r0.f1170d = r2;	 Catch:{ all -> 0x002a }
            r0 = r6.f1166b;	 Catch:{ all -> 0x002a }
            r0 = r0.f1167a;	 Catch:{ all -> 0x002a }
            r0.wait();	 Catch:{ all -> 0x002a }
            goto L_0x000e;
        L_0x002a:
            r0 = move-exception;
            monitor-exit(r1);	 Catch:{ all -> 0x002a }
            throw r0;	 Catch:{ InterruptedException -> 0x002d }
        L_0x002d:
            r0 = move-exception;
            r0 = "GoogleConversionReporter";
            r1 = "Dispatch thread is interrupted.";
            android.util.Log.w(r0, r1);
            r0 = r6.f1166b;
            r0.f1171e = r5;
            return;
        L_0x003d:
            r0 = r6.f1166b;	 Catch:{ all -> 0x002a }
            r2 = 1;
            r0.f1170d = r2;	 Catch:{ all -> 0x002a }
            r0 = r6.f1166b;	 Catch:{ all -> 0x002a }
            r0 = r0.f1168b;	 Catch:{ all -> 0x002a }
            r2 = 0;
            r0 = r0.remove(r2);	 Catch:{ all -> 0x002a }
            r0 = (com.google.ads.conversiontracking.C0539d) r0;	 Catch:{ all -> 0x002a }
            monitor-exit(r1);	 Catch:{ all -> 0x002a }
            if (r0 == 0) goto L_0x0007;
        L_0x0053:
            r1 = r6.f1166b;	 Catch:{ InterruptedException -> 0x002d }
            r1 = r1.f1169c;	 Catch:{ InterruptedException -> 0x002d }
            r2 = r0.f1158e;	 Catch:{ InterruptedException -> 0x002d }
            r3 = r0.f1159f;	 Catch:{ InterruptedException -> 0x002d }
            r4 = r0.f1155b;	 Catch:{ InterruptedException -> 0x002d }
            r1 = com.google.ads.conversiontracking.C0552g.m1400a(r1, r2, r3, r4);	 Catch:{ InterruptedException -> 0x002d }
            if (r1 != 0) goto L_0x006f;
        L_0x0065:
            r1 = r6.f1166b;	 Catch:{ InterruptedException -> 0x002d }
            r1 = r1.f1172f;	 Catch:{ InterruptedException -> 0x002d }
            r1.m1346a(r0);	 Catch:{ InterruptedException -> 0x002d }
            goto L_0x0007;
        L_0x006f:
            r1 = r6.f1166b;	 Catch:{ InterruptedException -> 0x002d }
            r1 = r1.m1339a(r0);	 Catch:{ InterruptedException -> 0x002d }
            r2 = 2;
            if (r1 != r2) goto L_0x0086;
        L_0x0078:
            r1 = r6.f1166b;	 Catch:{ InterruptedException -> 0x002d }
            r1 = r1.f1172f;	 Catch:{ InterruptedException -> 0x002d }
            r1.m1346a(r0);	 Catch:{ InterruptedException -> 0x002d }
            r0 = 0;
            r6.f1165a = r0;	 Catch:{ InterruptedException -> 0x002d }
            goto L_0x0007;
        L_0x0086:
            if (r1 != 0) goto L_0x009b;
        L_0x0088:
            r1 = r6.f1166b;	 Catch:{ InterruptedException -> 0x002d }
            r1 = r1.f1172f;	 Catch:{ InterruptedException -> 0x002d }
            r1.m1350c(r0);	 Catch:{ InterruptedException -> 0x002d }
            r6.m1329a();	 Catch:{ InterruptedException -> 0x002d }
            r0 = r6.f1165a;	 Catch:{ InterruptedException -> 0x002d }
            java.lang.Thread.sleep(r0);	 Catch:{ InterruptedException -> 0x002d }
            goto L_0x0007;
        L_0x009b:
            r1 = r6.f1166b;	 Catch:{ InterruptedException -> 0x002d }
            r1 = r1.f1172f;	 Catch:{ InterruptedException -> 0x002d }
            r1.m1350c(r0);	 Catch:{ InterruptedException -> 0x002d }
            r0 = 0;
            r6.f1165a = r0;	 Catch:{ InterruptedException -> 0x002d }
            goto L_0x0007;
            */
            throw new UnsupportedOperationException("Method not decompiled: com.google.ads.conversiontracking.e.b.run():void");
        }

        private void m1329a() {
            if (this.f1165a == 0) {
                this.f1165a = 1000;
            } else {
                this.f1165a = Math.min(this.f1165a * 2, 60000);
            }
        }
    }

    public C0543e(Context context) {
        this.f1167a = new Object();
        this.f1170d = true;
        this.f1171e = false;
        this.f1169c = context;
        this.f1172f = new C0545f(context);
        this.f1168b = new LinkedList();
        new Thread(new C0542b(this)).start();
        ScheduledThreadPoolExecutor scheduledThreadPoolExecutor = new ScheduledThreadPoolExecutor(1);
        long b = (C0552g.m1401b(context) + 300000) - C0552g.m1381a();
        Runnable c0541a = new C0541a();
        if (b <= 0) {
            b = 0;
        }
        scheduledThreadPoolExecutor.scheduleAtFixedRate(c0541a, b, 300000, TimeUnit.MILLISECONDS);
    }

    public void m1341a(String str, C0550c c0550c, boolean z, boolean z2, boolean z3) {
        C0539d c0539d = new C0539d(str, c0550c, z, z2);
        synchronized (this.f1167a) {
            if (z3) {
                this.f1172f.m1348b(c0539d);
                if (this.f1171e && C0552g.m1406d(this.f1169c)) {
                    this.f1168b.add(c0539d);
                    this.f1170d = true;
                    this.f1167a.notify();
                }
                return;
            }
            m1340a(new C05401(this, c0539d));
        }
    }

    protected void m1340a(Runnable runnable) {
        new Thread(runnable).start();
    }

    protected int m1339a(C0539d c0539d) {
        Throwable e;
        HttpUriRequest httpUriRequest = null;
        AndroidHttpClient newInstance = AndroidHttpClient.newInstance(System.getProperty("http.agent"), this.f1169c);
        try {
            String str = "GoogleConversionReporter";
            String str2 = "Pinging: ";
            String valueOf = String.valueOf(c0539d.f1160g);
            if (valueOf.length() != 0) {
                valueOf = str2.concat(valueOf);
            } else {
                valueOf = new String(str2);
            }
            Log.i(str, valueOf);
            HttpUriRequest httpGet = new HttpGet(c0539d.f1160g);
            try {
                HttpResponse execute = newInstance.execute(httpGet);
                int statusCode = execute.getStatusLine().getStatusCode();
                Log.i("GoogleConversionReporter", "Receive response code " + statusCode);
                HttpEntity entity = execute.getEntity();
                if (entity != null) {
                    entity.consumeContent();
                }
                int i = statusCode == 200 ? 2 : 1;
                if (i == 2) {
                    m1332b(c0539d);
                }
                newInstance.close();
                return i;
            } catch (IOException e2) {
                e = e2;
                httpUriRequest = httpGet;
                try {
                    Log.e("GoogleConversionReporter", "Error sending ping", e);
                    if (httpUriRequest != null) {
                        try {
                            httpUriRequest.abort();
                        } catch (UnsupportedOperationException e3) {
                        }
                    }
                    newInstance.close();
                    return 0;
                } catch (Throwable th) {
                    newInstance.close();
                }
            }
        } catch (IOException e4) {
            e = e4;
            Log.e("GoogleConversionReporter", "Error sending ping", e);
            if (httpUriRequest != null) {
                httpUriRequest.abort();
            }
            newInstance.close();
            return 0;
        }
    }

    private void m1332b(C0539d c0539d) {
        if (!c0539d.f1155b && c0539d.f1154a) {
            C0552g.m1394a(this.f1169c, c0539d.f1158e, c0539d.f1159f);
        }
    }
}
