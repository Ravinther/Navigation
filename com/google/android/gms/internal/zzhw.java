package com.google.android.gms.internal;

import android.app.Activity;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;
import android.view.MotionEvent;
import com.google.android.gms.ads.internal.util.client.zzb;
import com.google.android.gms.ads.internal.zzp;
import java.util.Map;

@zzgk
public class zzhw {
    private final Context mContext;
    private int mState;
    private String zzHO;
    private float zzHP;
    private float zzHQ;
    private float zzHR;
    private final float zzzP;

    /* renamed from: com.google.android.gms.internal.zzhw.1 */
    class C09511 implements OnClickListener {
        final /* synthetic */ String zzHS;
        final /* synthetic */ zzhw zzHT;

        C09511(zzhw com_google_android_gms_internal_zzhw, String str) {
            this.zzHT = com_google_android_gms_internal_zzhw;
            this.zzHS = str;
        }

        public void onClick(DialogInterface dialog, int which) {
            this.zzHT.mContext.startActivity(Intent.createChooser(new Intent("android.intent.action.SEND").setType("text/plain").putExtra("android.intent.extra.TEXT", this.zzHS), "Share via"));
        }
    }

    /* renamed from: com.google.android.gms.internal.zzhw.2 */
    class C09522 implements OnClickListener {
        final /* synthetic */ zzhw zzHT;

        C09522(zzhw com_google_android_gms_internal_zzhw) {
            this.zzHT = com_google_android_gms_internal_zzhw;
        }

        public void onClick(DialogInterface dialog, int which) {
        }
    }

    public zzhw(Context context) {
        this.mState = 0;
        this.mContext = context;
        this.zzzP = context.getResources().getDisplayMetrics().density;
    }

    public zzhw(Context context, String str) {
        this(context);
        this.zzHO = str;
    }

    private void showDialog() {
        if (this.mContext instanceof Activity) {
            Object zzaA = zzaA(this.zzHO);
            Builder builder = new Builder(this.mContext);
            builder.setMessage(zzaA);
            builder.setTitle("Ad Information");
            builder.setPositiveButton("Share", new C09511(this, zzaA));
            builder.setNegativeButton("Close", new C09522(this));
            builder.create().show();
            return;
        }
        zzb.zzaD("Can not create dialog without Activity Context");
    }

    static String zzaA(String str) {
        if (TextUtils.isEmpty(str)) {
            return "No debug information";
        }
        Uri build = new Uri.Builder().encodedQuery(str.replaceAll("\\+", "%20")).build();
        StringBuilder stringBuilder = new StringBuilder();
        Map zze = zzp.zzbx().zze(build);
        for (String str2 : zze.keySet()) {
            stringBuilder.append(str2).append(" = ").append((String) zze.get(str2)).append("\n\n");
        }
        Object trim = stringBuilder.toString().trim();
        return TextUtils.isEmpty(trim) ? "No debug information" : trim;
    }

    void zza(int i, float f, float f2) {
        if (i == 0) {
            this.mState = 0;
            this.zzHP = f;
            this.zzHQ = f2;
            this.zzHR = f2;
        } else if (this.mState == -1) {
        } else {
            if (i == 2) {
                if (f2 > this.zzHQ) {
                    this.zzHQ = f2;
                } else if (f2 < this.zzHR) {
                    this.zzHR = f2;
                }
                if (this.zzHQ - this.zzHR > 30.0f * this.zzzP) {
                    this.mState = -1;
                    return;
                }
                if (this.mState == 0 || this.mState == 2) {
                    if (f - this.zzHP >= 50.0f * this.zzzP) {
                        this.zzHP = f;
                        this.mState++;
                    }
                } else if ((this.mState == 1 || this.mState == 3) && f - this.zzHP <= -50.0f * this.zzzP) {
                    this.zzHP = f;
                    this.mState++;
                }
                if (this.mState == 1 || this.mState == 3) {
                    if (f > this.zzHP) {
                        this.zzHP = f;
                    }
                } else if (this.mState == 2 && f < this.zzHP) {
                    this.zzHP = f;
                }
            } else if (i == 1 && this.mState == 4) {
                showDialog();
            }
        }
    }

    public void zzaz(String str) {
        this.zzHO = str;
    }

    public void zze(MotionEvent motionEvent) {
        int historySize = motionEvent.getHistorySize();
        for (int i = 0; i < historySize; i++) {
            zza(motionEvent.getActionMasked(), motionEvent.getHistoricalX(0, i), motionEvent.getHistoricalY(0, i));
        }
        zza(motionEvent.getActionMasked(), motionEvent.getX(), motionEvent.getY());
    }
}
