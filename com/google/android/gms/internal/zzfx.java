package com.google.android.gms.internal;

import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.view.View.MeasureSpec;
import android.webkit.WebView;
import com.google.android.gms.ads.internal.request.AdResponseParcel;
import com.google.android.gms.ads.internal.util.client.zzb;
import com.google.android.gms.ads.internal.zzp;

@zzgk
public class zzfx implements Runnable {
    private final Handler zzCs;
    private final long zzCt;
    private long zzCu;
    private com.google.android.gms.internal.zziq.zza zzCv;
    protected boolean zzCw;
    protected boolean zzCx;
    private final int zznP;
    private final int zznQ;
    protected final zzip zzoL;

    protected final class zza extends AsyncTask<Void, Void, Boolean> {
        final /* synthetic */ zzfx zzCA;
        private final WebView zzCy;
        private Bitmap zzCz;

        public zza(zzfx com_google_android_gms_internal_zzfx, WebView webView) {
            this.zzCA = com_google_android_gms_internal_zzfx;
            this.zzCy = webView;
        }

        protected /* synthetic */ Object doInBackground(Object[] x0) {
            return zza((Void[]) x0);
        }

        protected /* synthetic */ void onPostExecute(Object x0) {
            zza((Boolean) x0);
        }

        protected synchronized void onPreExecute() {
            this.zzCz = Bitmap.createBitmap(this.zzCA.zznP, this.zzCA.zznQ, Config.ARGB_8888);
            this.zzCy.setVisibility(0);
            this.zzCy.measure(MeasureSpec.makeMeasureSpec(this.zzCA.zznP, 0), MeasureSpec.makeMeasureSpec(this.zzCA.zznQ, 0));
            this.zzCy.layout(0, 0, this.zzCA.zznP, this.zzCA.zznQ);
            this.zzCy.draw(new Canvas(this.zzCz));
            this.zzCy.invalidate();
        }

        protected synchronized Boolean zza(Void... voidArr) {
            Boolean valueOf;
            int width = this.zzCz.getWidth();
            int height = this.zzCz.getHeight();
            if (width == 0 || height == 0) {
                valueOf = Boolean.valueOf(false);
            } else {
                int i = 0;
                for (int i2 = 0; i2 < width; i2 += 10) {
                    for (int i3 = 0; i3 < height; i3 += 10) {
                        if (this.zzCz.getPixel(i2, i3) != 0) {
                            i++;
                        }
                    }
                }
                valueOf = Boolean.valueOf(((double) i) / (((double) (width * height)) / 100.0d) > 0.1d);
            }
            return valueOf;
        }

        protected void zza(Boolean bool) {
            zzfx.zzc(this.zzCA);
            if (bool.booleanValue() || this.zzCA.zzfn() || this.zzCA.zzCu <= 0) {
                this.zzCA.zzCx = bool.booleanValue();
                this.zzCA.zzCv.zza(this.zzCA.zzoL, true);
            } else if (this.zzCA.zzCu > 0) {
                if (zzb.zzM(2)) {
                    zzb.zzaC("Ad not detected, scheduling another run.");
                }
                this.zzCA.zzCs.postDelayed(this.zzCA, this.zzCA.zzCt);
            }
        }
    }

    public zzfx(com.google.android.gms.internal.zziq.zza com_google_android_gms_internal_zziq_zza, zzip com_google_android_gms_internal_zzip, int i, int i2) {
        this(com_google_android_gms_internal_zziq_zza, com_google_android_gms_internal_zzip, i, i2, 200, 50);
    }

    public zzfx(com.google.android.gms.internal.zziq.zza com_google_android_gms_internal_zziq_zza, zzip com_google_android_gms_internal_zzip, int i, int i2, long j, long j2) {
        this.zzCt = j;
        this.zzCu = j2;
        this.zzCs = new Handler(Looper.getMainLooper());
        this.zzoL = com_google_android_gms_internal_zzip;
        this.zzCv = com_google_android_gms_internal_zziq_zza;
        this.zzCw = false;
        this.zzCx = false;
        this.zznQ = i2;
        this.zznP = i;
    }

    static /* synthetic */ long zzc(zzfx com_google_android_gms_internal_zzfx) {
        long j = com_google_android_gms_internal_zzfx.zzCu - 1;
        com_google_android_gms_internal_zzfx.zzCu = j;
        return j;
    }

    public void run() {
        if (this.zzoL == null || zzfn()) {
            this.zzCv.zza(this.zzoL, true);
        } else {
            new zza(this, this.zzoL.getWebView()).execute(new Void[0]);
        }
    }

    public void zza(AdResponseParcel adResponseParcel) {
        zza(adResponseParcel, new zzix(this, this.zzoL, adResponseParcel.zzEe));
    }

    public void zza(AdResponseParcel adResponseParcel, zzix com_google_android_gms_internal_zzix) {
        this.zzoL.setWebViewClient(com_google_android_gms_internal_zzix);
        this.zzoL.loadDataWithBaseURL(TextUtils.isEmpty(adResponseParcel.zzAT) ? null : zzp.zzbx().zzaw(adResponseParcel.zzAT), adResponseParcel.body, "text/html", "UTF-8", null);
    }

    public void zzfl() {
        this.zzCs.postDelayed(this, this.zzCt);
    }

    public synchronized void zzfm() {
        this.zzCw = true;
    }

    public synchronized boolean zzfn() {
        return this.zzCw;
    }

    public boolean zzfo() {
        return this.zzCx;
    }
}
