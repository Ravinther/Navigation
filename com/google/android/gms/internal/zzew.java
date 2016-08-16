package com.google.android.gms.internal;

import android.app.Activity;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import com.google.android.gms.ads.internal.client.AdSizeParcel;
import com.google.android.gms.ads.internal.client.zzk;
import com.google.android.gms.ads.internal.util.client.zzb;
import com.google.android.gms.ads.internal.zzp;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import loquendo.tts.engine.TTSConst;

@zzgk
public class zzew extends zzfb {
    static final Set<String> zzzo;
    private int zznP;
    private int zznQ;
    private final zzip zzoL;
    private final Object zzpc;
    private AdSizeParcel zzyK;
    private RelativeLayout zzzA;
    private ViewGroup zzzB;
    private String zzzp;
    private boolean zzzq;
    private int zzzr;
    private int zzzs;
    private int zzzt;
    private int zzzu;
    private final Activity zzzv;
    private ImageView zzzw;
    private LinearLayout zzzx;
    private zzfc zzzy;
    private PopupWindow zzzz;

    /* renamed from: com.google.android.gms.internal.zzew.1 */
    class C09141 implements OnClickListener {
        final /* synthetic */ zzew zzzC;

        C09141(zzew com_google_android_gms_internal_zzew) {
            this.zzzC = com_google_android_gms_internal_zzew;
        }

        public void onClick(View view) {
            this.zzzC.zzn(true);
        }
    }

    static {
        zzzo = new HashSet(Arrays.asList(new String[]{"top-left", "top-right", "top-center", "center", "bottom-left", "bottom-right", "bottom-center"}));
    }

    public zzew(zzip com_google_android_gms_internal_zzip, zzfc com_google_android_gms_internal_zzfc) {
        super(com_google_android_gms_internal_zzip, "resize");
        this.zzzp = "top-right";
        this.zzzq = true;
        this.zzzr = 0;
        this.zzzs = 0;
        this.zznQ = -1;
        this.zzzt = 0;
        this.zzzu = 0;
        this.zznP = -1;
        this.zzpc = new Object();
        this.zzoL = com_google_android_gms_internal_zzip;
        this.zzzv = com_google_android_gms_internal_zzip.zzgN();
        this.zzzy = com_google_android_gms_internal_zzfc;
    }

    private int[] zzdX() {
        if (!zzdZ()) {
            return null;
        }
        if (this.zzzq) {
            return new int[]{this.zzzr + this.zzzt, this.zzzs + this.zzzu};
        }
        int[] zzh = zzp.zzbx().zzh(this.zzzv);
        int[] zzj = zzp.zzbx().zzj(this.zzzv);
        int i = zzh[0];
        int i2 = this.zzzr + this.zzzt;
        int i3 = this.zzzs + this.zzzu;
        if (i2 < 0) {
            i2 = 0;
        } else if (this.zznP + i2 > i) {
            i2 = i - this.zznP;
        }
        if (i3 < zzj[0]) {
            i3 = zzj[0];
        } else if (this.zznQ + i3 > zzj[1]) {
            i3 = zzj[1] - this.zznQ;
        }
        return new int[]{i2, i3};
    }

    private void zzf(Map<String, String> map) {
        if (!TextUtils.isEmpty((CharSequence) map.get("width"))) {
            this.zznP = zzp.zzbx().zzax((String) map.get("width"));
        }
        if (!TextUtils.isEmpty((CharSequence) map.get("height"))) {
            this.zznQ = zzp.zzbx().zzax((String) map.get("height"));
        }
        if (!TextUtils.isEmpty((CharSequence) map.get("offsetX"))) {
            this.zzzt = zzp.zzbx().zzax((String) map.get("offsetX"));
        }
        if (!TextUtils.isEmpty((CharSequence) map.get("offsetY"))) {
            this.zzzu = zzp.zzbx().zzax((String) map.get("offsetY"));
        }
        if (!TextUtils.isEmpty((CharSequence) map.get("allowOffscreen"))) {
            this.zzzq = Boolean.parseBoolean((String) map.get("allowOffscreen"));
        }
        String str = (String) map.get("customClosePosition");
        if (!TextUtils.isEmpty(str)) {
            this.zzzp = str;
        }
    }

    public void zza(int i, int i2, boolean z) {
        synchronized (this.zzpc) {
            this.zzzr = i;
            this.zzzs = i2;
            if (this.zzzz != null && z) {
                int[] zzdX = zzdX();
                if (zzdX != null) {
                    this.zzzz.update(zzk.zzcE().zzb(this.zzzv, zzdX[0]), zzk.zzcE().zzb(this.zzzv, zzdX[1]), this.zzzz.getWidth(), this.zzzz.getHeight());
                    zzc(zzdX[0], zzdX[1]);
                } else {
                    zzn(true);
                }
            }
        }
    }

    void zzb(int i, int i2) {
        if (this.zzzy != null) {
            this.zzzy.zza(i, i2, this.zznP, this.zznQ);
        }
    }

    void zzc(int i, int i2) {
        zzb(i, i2 - zzp.zzbx().zzj(this.zzzv)[0], this.zznP, this.zznQ);
    }

    public void zzd(int i, int i2) {
        this.zzzr = i;
        this.zzzs = i2;
    }

    boolean zzdW() {
        return this.zznP > -1 && this.zznQ > -1;
    }

    public boolean zzdY() {
        boolean z;
        synchronized (this.zzpc) {
            z = this.zzzz != null;
        }
        return z;
    }

    boolean zzdZ() {
        int[] zzh = zzp.zzbx().zzh(this.zzzv);
        int[] zzj = zzp.zzbx().zzj(this.zzzv);
        int i = zzh[0];
        int i2 = zzh[1];
        if (this.zznP < 50 || this.zznP > i) {
            zzb.zzaE("Width is too small or too large.");
            return false;
        } else if (this.zznQ < 50 || this.zznQ > i2) {
            zzb.zzaE("Height is too small or too large.");
            return false;
        } else if (this.zznQ == i2 && this.zznP == i) {
            zzb.zzaE("Cannot resize to a full-screen ad.");
            return false;
        } else {
            if (this.zzzq) {
                int i3;
                String str = this.zzzp;
                boolean z = true;
                switch (str.hashCode()) {
                    case -1364013995:
                        if (str.equals("center")) {
                            z = true;
                            break;
                        }
                        break;
                    case -1012429441:
                        if (str.equals("top-left")) {
                            z = false;
                            break;
                        }
                        break;
                    case -655373719:
                        if (str.equals("bottom-left")) {
                            z = true;
                            break;
                        }
                        break;
                    case 1163912186:
                        if (str.equals("bottom-right")) {
                            z = true;
                            break;
                        }
                        break;
                    case 1288627767:
                        if (str.equals("bottom-center")) {
                            z = true;
                            break;
                        }
                        break;
                    case 1755462605:
                        if (str.equals("top-center")) {
                            z = true;
                            break;
                        }
                        break;
                }
                switch (z) {
                    case TTSConst.TTSEVT_AUDIOSTART /*0*/:
                        i3 = this.zzzt + this.zzzr;
                        i2 = this.zzzs + this.zzzu;
                        break;
                    case TTSConst.TTSMULTILINE /*1*/:
                        i3 = ((this.zzzr + this.zzzt) + (this.zznP / 2)) - 25;
                        i2 = this.zzzs + this.zzzu;
                        break;
                    case TTSConst.TTSPARAGRAPH /*2*/:
                        i3 = ((this.zzzr + this.zzzt) + (this.zznP / 2)) - 25;
                        i2 = ((this.zzzs + this.zzzu) + (this.zznQ / 2)) - 25;
                        break;
                    case TTSConst.TTSUNICODE /*3*/:
                        i3 = this.zzzt + this.zzzr;
                        i2 = ((this.zzzs + this.zzzu) + this.zznQ) - 50;
                        break;
                    case TTSConst.TTSXML /*4*/:
                        i3 = ((this.zzzr + this.zzzt) + (this.zznP / 2)) - 25;
                        i2 = ((this.zzzs + this.zzzu) + this.zznQ) - 50;
                        break;
                    case TTSConst.TTSEVT_TEXT /*5*/:
                        i3 = ((this.zzzr + this.zzzt) + this.zznP) - 50;
                        i2 = ((this.zzzs + this.zzzu) + this.zznQ) - 50;
                        break;
                    default:
                        i3 = ((this.zzzr + this.zzzt) + this.zznP) - 50;
                        i2 = this.zzzs + this.zzzu;
                        break;
                }
                if (i3 < 0 || i3 + 50 > i || r2 < zzj[0] || r2 + 50 > zzj[1]) {
                    return false;
                }
            }
            return true;
        }
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void zzg(java.util.Map<java.lang.String, java.lang.String> r13) {
        /*
        r12 = this;
        r4 = -1;
        r5 = 1;
        r3 = 0;
        r6 = r12.zzpc;
        monitor-enter(r6);
        r1 = r12.zzzv;	 Catch:{ all -> 0x0022 }
        if (r1 != 0) goto L_0x0012;
    L_0x000a:
        r1 = "Not an activity context. Cannot resize.";
        r12.zzah(r1);	 Catch:{ all -> 0x0022 }
        monitor-exit(r6);	 Catch:{ all -> 0x0022 }
    L_0x0011:
        return;
    L_0x0012:
        r1 = r12.zzoL;	 Catch:{ all -> 0x0022 }
        r1 = r1.zzaN();	 Catch:{ all -> 0x0022 }
        if (r1 != 0) goto L_0x0025;
    L_0x001a:
        r1 = "Webview is not yet available, size is not set.";
        r12.zzah(r1);	 Catch:{ all -> 0x0022 }
        monitor-exit(r6);	 Catch:{ all -> 0x0022 }
        goto L_0x0011;
    L_0x0022:
        r1 = move-exception;
        monitor-exit(r6);	 Catch:{ all -> 0x0022 }
        throw r1;
    L_0x0025:
        r1 = r12.zzoL;	 Catch:{ all -> 0x0022 }
        r1 = r1.zzaN();	 Catch:{ all -> 0x0022 }
        r1 = r1.zzsH;	 Catch:{ all -> 0x0022 }
        if (r1 == 0) goto L_0x0037;
    L_0x002f:
        r1 = "Is interstitial. Cannot resize an interstitial.";
        r12.zzah(r1);	 Catch:{ all -> 0x0022 }
        monitor-exit(r6);	 Catch:{ all -> 0x0022 }
        goto L_0x0011;
    L_0x0037:
        r1 = r12.zzoL;	 Catch:{ all -> 0x0022 }
        r1 = r1.zzgW();	 Catch:{ all -> 0x0022 }
        if (r1 == 0) goto L_0x0047;
    L_0x003f:
        r1 = "Cannot resize an expanded banner.";
        r12.zzah(r1);	 Catch:{ all -> 0x0022 }
        monitor-exit(r6);	 Catch:{ all -> 0x0022 }
        goto L_0x0011;
    L_0x0047:
        r12.zzf(r13);	 Catch:{ all -> 0x0022 }
        r1 = r12.zzdW();	 Catch:{ all -> 0x0022 }
        if (r1 != 0) goto L_0x0058;
    L_0x0050:
        r1 = "Invalid width and height options. Cannot resize.";
        r12.zzah(r1);	 Catch:{ all -> 0x0022 }
        monitor-exit(r6);	 Catch:{ all -> 0x0022 }
        goto L_0x0011;
    L_0x0058:
        r1 = r12.zzzv;	 Catch:{ all -> 0x0022 }
        r7 = r1.getWindow();	 Catch:{ all -> 0x0022 }
        if (r7 == 0) goto L_0x0066;
    L_0x0060:
        r1 = r7.getDecorView();	 Catch:{ all -> 0x0022 }
        if (r1 != 0) goto L_0x006e;
    L_0x0066:
        r1 = "Activity context is not ready, cannot get window or decor view.";
        r12.zzah(r1);	 Catch:{ all -> 0x0022 }
        monitor-exit(r6);	 Catch:{ all -> 0x0022 }
        goto L_0x0011;
    L_0x006e:
        r8 = r12.zzdX();	 Catch:{ all -> 0x0022 }
        if (r8 != 0) goto L_0x007c;
    L_0x0074:
        r1 = "Resize location out of screen or close button is not visible.";
        r12.zzah(r1);	 Catch:{ all -> 0x0022 }
        monitor-exit(r6);	 Catch:{ all -> 0x0022 }
        goto L_0x0011;
    L_0x007c:
        r1 = com.google.android.gms.ads.internal.client.zzk.zzcE();	 Catch:{ all -> 0x0022 }
        r2 = r12.zzzv;	 Catch:{ all -> 0x0022 }
        r9 = r12.zznP;	 Catch:{ all -> 0x0022 }
        r9 = r1.zzb(r2, r9);	 Catch:{ all -> 0x0022 }
        r1 = com.google.android.gms.ads.internal.client.zzk.zzcE();	 Catch:{ all -> 0x0022 }
        r2 = r12.zzzv;	 Catch:{ all -> 0x0022 }
        r10 = r12.zznQ;	 Catch:{ all -> 0x0022 }
        r10 = r1.zzb(r2, r10);	 Catch:{ all -> 0x0022 }
        r1 = r12.zzoL;	 Catch:{ all -> 0x0022 }
        r1 = r1.getWebView();	 Catch:{ all -> 0x0022 }
        r2 = r1.getParent();	 Catch:{ all -> 0x0022 }
        if (r2 == 0) goto L_0x01de;
    L_0x00a0:
        r1 = r2 instanceof android.view.ViewGroup;	 Catch:{ all -> 0x0022 }
        if (r1 == 0) goto L_0x01de;
    L_0x00a4:
        r0 = r2;
        r0 = (android.view.ViewGroup) r0;	 Catch:{ all -> 0x0022 }
        r1 = r0;
        r11 = r12.zzoL;	 Catch:{ all -> 0x0022 }
        r11 = r11.getWebView();	 Catch:{ all -> 0x0022 }
        r1.removeView(r11);	 Catch:{ all -> 0x0022 }
        r1 = r12.zzzz;	 Catch:{ all -> 0x0022 }
        if (r1 != 0) goto L_0x01d7;
    L_0x00b5:
        r2 = (android.view.ViewGroup) r2;	 Catch:{ all -> 0x0022 }
        r12.zzzB = r2;	 Catch:{ all -> 0x0022 }
        r1 = com.google.android.gms.ads.internal.zzp.zzbx();	 Catch:{ all -> 0x0022 }
        r2 = r12.zzoL;	 Catch:{ all -> 0x0022 }
        r2 = r2.getWebView();	 Catch:{ all -> 0x0022 }
        r1 = r1.zzj(r2);	 Catch:{ all -> 0x0022 }
        r2 = new android.widget.ImageView;	 Catch:{ all -> 0x0022 }
        r11 = r12.zzzv;	 Catch:{ all -> 0x0022 }
        r2.<init>(r11);	 Catch:{ all -> 0x0022 }
        r12.zzzw = r2;	 Catch:{ all -> 0x0022 }
        r2 = r12.zzzw;	 Catch:{ all -> 0x0022 }
        r2.setImageBitmap(r1);	 Catch:{ all -> 0x0022 }
        r1 = r12.zzoL;	 Catch:{ all -> 0x0022 }
        r1 = r1.zzaN();	 Catch:{ all -> 0x0022 }
        r12.zzyK = r1;	 Catch:{ all -> 0x0022 }
        r1 = r12.zzzB;	 Catch:{ all -> 0x0022 }
        r2 = r12.zzzw;	 Catch:{ all -> 0x0022 }
        r1.addView(r2);	 Catch:{ all -> 0x0022 }
    L_0x00e4:
        r1 = new android.widget.RelativeLayout;	 Catch:{ all -> 0x0022 }
        r2 = r12.zzzv;	 Catch:{ all -> 0x0022 }
        r1.<init>(r2);	 Catch:{ all -> 0x0022 }
        r12.zzzA = r1;	 Catch:{ all -> 0x0022 }
        r1 = r12.zzzA;	 Catch:{ all -> 0x0022 }
        r2 = 0;
        r1.setBackgroundColor(r2);	 Catch:{ all -> 0x0022 }
        r1 = r12.zzzA;	 Catch:{ all -> 0x0022 }
        r2 = new android.view.ViewGroup$LayoutParams;	 Catch:{ all -> 0x0022 }
        r2.<init>(r9, r10);	 Catch:{ all -> 0x0022 }
        r1.setLayoutParams(r2);	 Catch:{ all -> 0x0022 }
        r1 = com.google.android.gms.ads.internal.zzp.zzbx();	 Catch:{ all -> 0x0022 }
        r2 = r12.zzzA;	 Catch:{ all -> 0x0022 }
        r11 = 0;
        r1 = r1.zza(r2, r9, r10, r11);	 Catch:{ all -> 0x0022 }
        r12.zzzz = r1;	 Catch:{ all -> 0x0022 }
        r1 = r12.zzzz;	 Catch:{ all -> 0x0022 }
        r2 = 1;
        r1.setOutsideTouchable(r2);	 Catch:{ all -> 0x0022 }
        r1 = r12.zzzz;	 Catch:{ all -> 0x0022 }
        r2 = 1;
        r1.setTouchable(r2);	 Catch:{ all -> 0x0022 }
        r2 = r12.zzzz;	 Catch:{ all -> 0x0022 }
        r1 = r12.zzzq;	 Catch:{ all -> 0x0022 }
        if (r1 != 0) goto L_0x01e7;
    L_0x011c:
        r1 = r5;
    L_0x011d:
        r2.setClippingEnabled(r1);	 Catch:{ all -> 0x0022 }
        r1 = r12.zzzA;	 Catch:{ all -> 0x0022 }
        r2 = r12.zzoL;	 Catch:{ all -> 0x0022 }
        r2 = r2.getWebView();	 Catch:{ all -> 0x0022 }
        r9 = -1;
        r10 = -1;
        r1.addView(r2, r9, r10);	 Catch:{ all -> 0x0022 }
        r1 = new android.widget.LinearLayout;	 Catch:{ all -> 0x0022 }
        r2 = r12.zzzv;	 Catch:{ all -> 0x0022 }
        r1.<init>(r2);	 Catch:{ all -> 0x0022 }
        r12.zzzx = r1;	 Catch:{ all -> 0x0022 }
        r2 = new android.widget.RelativeLayout$LayoutParams;	 Catch:{ all -> 0x0022 }
        r1 = com.google.android.gms.ads.internal.client.zzk.zzcE();	 Catch:{ all -> 0x0022 }
        r9 = r12.zzzv;	 Catch:{ all -> 0x0022 }
        r10 = 50;
        r1 = r1.zzb(r9, r10);	 Catch:{ all -> 0x0022 }
        r9 = com.google.android.gms.ads.internal.client.zzk.zzcE();	 Catch:{ all -> 0x0022 }
        r10 = r12.zzzv;	 Catch:{ all -> 0x0022 }
        r11 = 50;
        r9 = r9.zzb(r10, r11);	 Catch:{ all -> 0x0022 }
        r2.<init>(r1, r9);	 Catch:{ all -> 0x0022 }
        r1 = r12.zzzp;	 Catch:{ all -> 0x0022 }
        r9 = r1.hashCode();	 Catch:{ all -> 0x0022 }
        switch(r9) {
            case -1364013995: goto L_0x0202;
            case -1012429441: goto L_0x01ea;
            case -655373719: goto L_0x020e;
            case 1163912186: goto L_0x0226;
            case 1288627767: goto L_0x021a;
            case 1755462605: goto L_0x01f6;
            default: goto L_0x015c;
        };	 Catch:{ all -> 0x0022 }
    L_0x015c:
        r1 = r4;
    L_0x015d:
        switch(r1) {
            case 0: goto L_0x0232;
            case 1: goto L_0x023e;
            case 2: goto L_0x024a;
            case 3: goto L_0x0251;
            case 4: goto L_0x025d;
            case 5: goto L_0x0269;
            default: goto L_0x0160;
        };	 Catch:{ all -> 0x0022 }
    L_0x0160:
        r1 = 10;
        r2.addRule(r1);	 Catch:{ all -> 0x0022 }
        r1 = 11;
        r2.addRule(r1);	 Catch:{ all -> 0x0022 }
    L_0x016a:
        r1 = r12.zzzx;	 Catch:{ all -> 0x0022 }
        r3 = new com.google.android.gms.internal.zzew$1;	 Catch:{ all -> 0x0022 }
        r3.<init>(r12);	 Catch:{ all -> 0x0022 }
        r1.setOnClickListener(r3);	 Catch:{ all -> 0x0022 }
        r1 = r12.zzzx;	 Catch:{ all -> 0x0022 }
        r3 = "Close button";
        r1.setContentDescription(r3);	 Catch:{ all -> 0x0022 }
        r1 = r12.zzzA;	 Catch:{ all -> 0x0022 }
        r3 = r12.zzzx;	 Catch:{ all -> 0x0022 }
        r1.addView(r3, r2);	 Catch:{ all -> 0x0022 }
        r1 = r12.zzzz;	 Catch:{ RuntimeException -> 0x0275 }
        r2 = r7.getDecorView();	 Catch:{ RuntimeException -> 0x0275 }
        r3 = 0;
        r4 = com.google.android.gms.ads.internal.client.zzk.zzcE();	 Catch:{ RuntimeException -> 0x0275 }
        r5 = r12.zzzv;	 Catch:{ RuntimeException -> 0x0275 }
        r7 = 0;
        r7 = r8[r7];	 Catch:{ RuntimeException -> 0x0275 }
        r4 = r4.zzb(r5, r7);	 Catch:{ RuntimeException -> 0x0275 }
        r5 = com.google.android.gms.ads.internal.client.zzk.zzcE();	 Catch:{ RuntimeException -> 0x0275 }
        r7 = r12.zzzv;	 Catch:{ RuntimeException -> 0x0275 }
        r9 = 1;
        r9 = r8[r9];	 Catch:{ RuntimeException -> 0x0275 }
        r5 = r5.zzb(r7, r9);	 Catch:{ RuntimeException -> 0x0275 }
        r1.showAtLocation(r2, r3, r4, r5);	 Catch:{ RuntimeException -> 0x0275 }
        r1 = 0;
        r1 = r8[r1];	 Catch:{ all -> 0x0022 }
        r2 = 1;
        r2 = r8[r2];	 Catch:{ all -> 0x0022 }
        r12.zzb(r1, r2);	 Catch:{ all -> 0x0022 }
        r1 = r12.zzoL;	 Catch:{ all -> 0x0022 }
        r2 = new com.google.android.gms.ads.internal.client.AdSizeParcel;	 Catch:{ all -> 0x0022 }
        r3 = r12.zzzv;	 Catch:{ all -> 0x0022 }
        r4 = new com.google.android.gms.ads.AdSize;	 Catch:{ all -> 0x0022 }
        r5 = r12.zznP;	 Catch:{ all -> 0x0022 }
        r7 = r12.zznQ;	 Catch:{ all -> 0x0022 }
        r4.<init>(r5, r7);	 Catch:{ all -> 0x0022 }
        r2.<init>(r3, r4);	 Catch:{ all -> 0x0022 }
        r1.zza(r2);	 Catch:{ all -> 0x0022 }
        r1 = 0;
        r1 = r8[r1];	 Catch:{ all -> 0x0022 }
        r2 = 1;
        r2 = r8[r2];	 Catch:{ all -> 0x0022 }
        r12.zzc(r1, r2);	 Catch:{ all -> 0x0022 }
        r1 = "resized";
        r12.zzaj(r1);	 Catch:{ all -> 0x0022 }
        monitor-exit(r6);	 Catch:{ all -> 0x0022 }
        goto L_0x0011;
    L_0x01d7:
        r1 = r12.zzzz;	 Catch:{ all -> 0x0022 }
        r1.dismiss();	 Catch:{ all -> 0x0022 }
        goto L_0x00e4;
    L_0x01de:
        r1 = "Webview is detached, probably in the middle of a resize or expand.";
        r12.zzah(r1);	 Catch:{ all -> 0x0022 }
        monitor-exit(r6);	 Catch:{ all -> 0x0022 }
        goto L_0x0011;
    L_0x01e7:
        r1 = r3;
        goto L_0x011d;
    L_0x01ea:
        r5 = "top-left";
        r1 = r1.equals(r5);	 Catch:{ all -> 0x0022 }
        if (r1 == 0) goto L_0x015c;
    L_0x01f3:
        r1 = r3;
        goto L_0x015d;
    L_0x01f6:
        r3 = "top-center";
        r1 = r1.equals(r3);	 Catch:{ all -> 0x0022 }
        if (r1 == 0) goto L_0x015c;
    L_0x01ff:
        r1 = r5;
        goto L_0x015d;
    L_0x0202:
        r3 = "center";
        r1 = r1.equals(r3);	 Catch:{ all -> 0x0022 }
        if (r1 == 0) goto L_0x015c;
    L_0x020b:
        r1 = 2;
        goto L_0x015d;
    L_0x020e:
        r3 = "bottom-left";
        r1 = r1.equals(r3);	 Catch:{ all -> 0x0022 }
        if (r1 == 0) goto L_0x015c;
    L_0x0217:
        r1 = 3;
        goto L_0x015d;
    L_0x021a:
        r3 = "bottom-center";
        r1 = r1.equals(r3);	 Catch:{ all -> 0x0022 }
        if (r1 == 0) goto L_0x015c;
    L_0x0223:
        r1 = 4;
        goto L_0x015d;
    L_0x0226:
        r3 = "bottom-right";
        r1 = r1.equals(r3);	 Catch:{ all -> 0x0022 }
        if (r1 == 0) goto L_0x015c;
    L_0x022f:
        r1 = 5;
        goto L_0x015d;
    L_0x0232:
        r1 = 10;
        r2.addRule(r1);	 Catch:{ all -> 0x0022 }
        r1 = 9;
        r2.addRule(r1);	 Catch:{ all -> 0x0022 }
        goto L_0x016a;
    L_0x023e:
        r1 = 10;
        r2.addRule(r1);	 Catch:{ all -> 0x0022 }
        r1 = 14;
        r2.addRule(r1);	 Catch:{ all -> 0x0022 }
        goto L_0x016a;
    L_0x024a:
        r1 = 13;
        r2.addRule(r1);	 Catch:{ all -> 0x0022 }
        goto L_0x016a;
    L_0x0251:
        r1 = 12;
        r2.addRule(r1);	 Catch:{ all -> 0x0022 }
        r1 = 9;
        r2.addRule(r1);	 Catch:{ all -> 0x0022 }
        goto L_0x016a;
    L_0x025d:
        r1 = 12;
        r2.addRule(r1);	 Catch:{ all -> 0x0022 }
        r1 = 14;
        r2.addRule(r1);	 Catch:{ all -> 0x0022 }
        goto L_0x016a;
    L_0x0269:
        r1 = 12;
        r2.addRule(r1);	 Catch:{ all -> 0x0022 }
        r1 = 11;
        r2.addRule(r1);	 Catch:{ all -> 0x0022 }
        goto L_0x016a;
    L_0x0275:
        r1 = move-exception;
        r2 = new java.lang.StringBuilder;	 Catch:{ all -> 0x0022 }
        r2.<init>();	 Catch:{ all -> 0x0022 }
        r3 = "Cannot show popup window: ";
        r2 = r2.append(r3);	 Catch:{ all -> 0x0022 }
        r1 = r1.getMessage();	 Catch:{ all -> 0x0022 }
        r1 = r2.append(r1);	 Catch:{ all -> 0x0022 }
        r1 = r1.toString();	 Catch:{ all -> 0x0022 }
        r12.zzah(r1);	 Catch:{ all -> 0x0022 }
        r1 = r12.zzzA;	 Catch:{ all -> 0x0022 }
        r2 = r12.zzoL;	 Catch:{ all -> 0x0022 }
        r2 = r2.getWebView();	 Catch:{ all -> 0x0022 }
        r1.removeView(r2);	 Catch:{ all -> 0x0022 }
        r1 = r12.zzzB;	 Catch:{ all -> 0x0022 }
        if (r1 == 0) goto L_0x02b9;
    L_0x02a0:
        r1 = r12.zzzB;	 Catch:{ all -> 0x0022 }
        r2 = r12.zzzw;	 Catch:{ all -> 0x0022 }
        r1.removeView(r2);	 Catch:{ all -> 0x0022 }
        r1 = r12.zzzB;	 Catch:{ all -> 0x0022 }
        r2 = r12.zzoL;	 Catch:{ all -> 0x0022 }
        r2 = r2.getWebView();	 Catch:{ all -> 0x0022 }
        r1.addView(r2);	 Catch:{ all -> 0x0022 }
        r1 = r12.zzoL;	 Catch:{ all -> 0x0022 }
        r2 = r12.zzyK;	 Catch:{ all -> 0x0022 }
        r1.zza(r2);	 Catch:{ all -> 0x0022 }
    L_0x02b9:
        monitor-exit(r6);	 Catch:{ all -> 0x0022 }
        goto L_0x0011;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.zzew.zzg(java.util.Map):void");
    }

    public void zzn(boolean z) {
        synchronized (this.zzpc) {
            if (this.zzzz != null) {
                this.zzzz.dismiss();
                this.zzzA.removeView(this.zzoL.getWebView());
                if (this.zzzB != null) {
                    this.zzzB.removeView(this.zzzw);
                    this.zzzB.addView(this.zzoL.getWebView());
                    this.zzoL.zza(this.zzyK);
                }
                if (z) {
                    zzaj("default");
                    if (this.zzzy != null) {
                        this.zzzy.zzbc();
                    }
                }
                this.zzzz = null;
                this.zzzA = null;
                this.zzzB = null;
                this.zzzx = null;
            }
        }
    }
}
