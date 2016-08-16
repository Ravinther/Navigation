package com.google.android.gms.internal;

import android.content.Context;
import android.text.TextUtils;
import com.google.android.gms.ads.internal.util.client.zzb;
import com.google.android.gms.ads.internal.zzp;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

@zzgk
public final class zzia extends zzhq {
    private final Context mContext;
    private final String zzF;
    private String zzHj;
    private final String zzqK;

    public zzia(Context context, String str, String str2) {
        this.zzHj = null;
        this.mContext = context;
        this.zzqK = str;
        this.zzF = str2;
    }

    public zzia(Context context, String str, String str2, String str3) {
        this.zzHj = null;
        this.mContext = context;
        this.zzqK = str;
        this.zzF = str2;
        this.zzHj = str3;
    }

    public void onStop() {
    }

    public void zzdG() {
        HttpURLConnection httpURLConnection;
        try {
            zzb.m1445v("Pinging URL: " + this.zzF);
            httpURLConnection = (HttpURLConnection) new URL(this.zzF).openConnection();
            if (TextUtils.isEmpty(this.zzHj)) {
                zzp.zzbx().zza(this.mContext, this.zzqK, true, httpURLConnection);
            } else {
                zzp.zzbx().zza(this.mContext, this.zzqK, true, httpURLConnection, this.zzHj);
            }
            int responseCode = httpURLConnection.getResponseCode();
            if (responseCode < 200 || responseCode >= 300) {
                zzb.zzaE("Received non-success response code " + responseCode + " from pinging URL: " + this.zzF);
            }
            httpURLConnection.disconnect();
        } catch (IndexOutOfBoundsException e) {
            zzb.zzaE("Error while parsing ping URL: " + this.zzF + ". " + e.getMessage());
        } catch (IOException e2) {
            zzb.zzaE("Error while pinging URL: " + this.zzF + ". " + e2.getMessage());
        } catch (RuntimeException e3) {
            zzb.zzaE("Error while pinging URL: " + this.zzF + ". " + e3.getMessage());
        } catch (Throwable th) {
            httpURLConnection.disconnect();
        }
    }
}
