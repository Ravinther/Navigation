package com.google.android.gms.gcm;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.Messenger;
import android.util.Log;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

public class GoogleCloudMessaging {
    public static int zzazR;
    public static int zzazS;
    public static int zzazT;
    static GoogleCloudMessaging zzazU;
    private static final AtomicInteger zzazX;
    private Context context;
    private Map<String, Handler> zzazW;
    private final BlockingQueue<Intent> zzazY;
    final Messenger zzazZ;

    /* renamed from: com.google.android.gms.gcm.GoogleCloudMessaging.1 */
    class C08431 extends Handler {
        final /* synthetic */ GoogleCloudMessaging zzaAa;

        C08431(GoogleCloudMessaging googleCloudMessaging, Looper looper) {
            this.zzaAa = googleCloudMessaging;
            super(looper);
        }

        public void handleMessage(Message msg) {
            if (msg == null || !(msg.obj instanceof Intent)) {
                Log.w("GCM", "Dropping invalid message");
            }
            Intent intent = (Intent) msg.obj;
            if ("com.google.android.c2dm.intent.REGISTRATION".equals(intent.getAction())) {
                this.zzaAa.zzazY.add(intent);
            } else if (!this.zzaAa.zzl(intent)) {
                intent.setPackage(this.zzaAa.context.getPackageName());
                this.zzaAa.context.sendBroadcast(intent);
            }
        }
    }

    static {
        zzazR = 5000000;
        zzazS = 6500000;
        zzazT = 7000000;
        zzazX = new AtomicInteger(1);
    }

    public GoogleCloudMessaging() {
        this.zzazY = new LinkedBlockingQueue();
        this.zzazW = Collections.synchronizedMap(new HashMap());
        this.zzazZ = new Messenger(new C08431(this, Looper.getMainLooper()));
    }

    public static synchronized GoogleCloudMessaging getInstance(Context context) {
        GoogleCloudMessaging googleCloudMessaging;
        synchronized (GoogleCloudMessaging.class) {
            if (zzazU == null) {
                zzazU = new GoogleCloudMessaging();
                zzazU.context = context.getApplicationContext();
            }
            googleCloudMessaging = zzazU;
        }
        return googleCloudMessaging;
    }

    private boolean zzl(Intent intent) {
        Object stringExtra = intent.getStringExtra("In-Reply-To");
        if (stringExtra == null && intent.hasExtra("error")) {
            stringExtra = intent.getStringExtra("google.message_id");
        }
        if (stringExtra != null) {
            Handler handler = (Handler) this.zzazW.remove(stringExtra);
            if (handler != null) {
                Message obtain = Message.obtain();
                obtain.obj = intent;
                return handler.sendMessage(obtain);
            }
        }
        return false;
    }

    public String getMessageType(Intent intent) {
        if (!"com.google.android.c2dm.intent.RECEIVE".equals(intent.getAction())) {
            return null;
        }
        String stringExtra = intent.getStringExtra("message_type");
        return stringExtra == null ? "gcm" : stringExtra;
    }
}
