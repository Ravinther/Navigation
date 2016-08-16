package com.google.android.gms.tagmanager;

import android.content.Context;
import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.concurrent.LinkedBlockingQueue;

class zzat extends Thread implements zzas {
    private static zzat zzaQj;
    private volatile boolean mClosed;
    private final Context mContext;
    private volatile boolean zzMT;
    private final LinkedBlockingQueue<Runnable> zzaQi;
    private volatile zzau zzaQk;

    /* renamed from: com.google.android.gms.tagmanager.zzat.1 */
    class C10101 implements Runnable {
        final /* synthetic */ zzas zzaQl;
        final /* synthetic */ long zzaQm;
        final /* synthetic */ zzat zzaQn;
        final /* synthetic */ String zzxv;

        C10101(zzat com_google_android_gms_tagmanager_zzat, zzas com_google_android_gms_tagmanager_zzas, long j, String str) {
            this.zzaQn = com_google_android_gms_tagmanager_zzat;
            this.zzaQl = com_google_android_gms_tagmanager_zzas;
            this.zzaQm = j;
            this.zzxv = str;
        }

        public void run() {
            if (this.zzaQn.zzaQk == null) {
                zzcu zzAP = zzcu.zzAP();
                zzAP.zza(this.zzaQn.mContext, this.zzaQl);
                this.zzaQn.zzaQk = zzAP.zzAS();
            }
            this.zzaQn.zzaQk.zzg(this.zzaQm, this.zzxv);
        }
    }

    private zzat(Context context) {
        super("GAThread");
        this.zzaQi = new LinkedBlockingQueue();
        this.zzMT = false;
        this.mClosed = false;
        if (context != null) {
            this.mContext = context.getApplicationContext();
        } else {
            this.mContext = context;
        }
        start();
    }

    static zzat zzaO(Context context) {
        if (zzaQj == null) {
            zzaQj = new zzat(context);
        }
        return zzaQj;
    }

    private String zzd(Throwable th) {
        OutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        PrintStream printStream = new PrintStream(byteArrayOutputStream);
        th.printStackTrace(printStream);
        printStream.flush();
        return new String(byteArrayOutputStream.toByteArray());
    }

    public void run() {
        while (!this.mClosed) {
            try {
                Runnable runnable = (Runnable) this.zzaQi.take();
                if (!this.zzMT) {
                    runnable.run();
                }
            } catch (InterruptedException e) {
                zzbg.zzaD(e.toString());
            } catch (Throwable th) {
                zzbg.m1447e("Error on Google TagManager Thread: " + zzd(th));
                zzbg.m1447e("Google TagManager is shutting down.");
                this.zzMT = true;
            }
        }
    }

    public void zzeL(String str) {
        zzh(str, System.currentTimeMillis());
    }

    public void zzg(Runnable runnable) {
        this.zzaQi.add(runnable);
    }

    void zzh(String str, long j) {
        zzg(new C10101(this, this, j, str));
    }
}
