package com.google.android.gms.internal;

import android.content.Context;
import android.net.Uri;
import android.net.Uri.Builder;
import android.os.Environment;
import android.text.TextUtils;
import com.google.android.gms.ads.internal.util.client.zzb;
import com.google.android.gms.ads.internal.zzp;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicBoolean;

public class zzca {
    final Context mContext;
    final String zzqK;
    String zzvg;
    BlockingQueue<zzcd> zzvi;
    ExecutorService zzvj;
    LinkedHashMap<String, String> zzvk;
    private AtomicBoolean zzvl;
    private File zzvm;

    /* renamed from: com.google.android.gms.internal.zzca.1 */
    class C08761 implements Runnable {
        final /* synthetic */ zzca zzvn;

        C08761(zzca com_google_android_gms_internal_zzca) {
            this.zzvn = com_google_android_gms_internal_zzca;
        }

        public void run() {
            this.zzvn.zzdi();
        }
    }

    public zzca(Context context, String str, String str2, Map<String, String> map) {
        this.zzvk = new LinkedHashMap();
        this.mContext = context;
        this.zzqK = str;
        this.zzvg = str2;
        this.zzvl = new AtomicBoolean(false);
        this.zzvl.set(((Boolean) zzby.zzuD.get()).booleanValue());
        if (this.zzvl.get()) {
            File externalStorageDirectory = Environment.getExternalStorageDirectory();
            if (externalStorageDirectory != null) {
                this.zzvm = new File(externalStorageDirectory, "sdk_csi_data.txt");
            }
        }
        for (Entry entry : map.entrySet()) {
            this.zzvk.put(entry.getKey(), entry.getValue());
        }
        this.zzvi = new ArrayBlockingQueue(30);
        this.zzvj = Executors.newSingleThreadExecutor();
        this.zzvj.execute(new C08761(this));
    }

    private void zza(File file, String str) {
        FileOutputStream fileOutputStream;
        Throwable e;
        if (file != null) {
            try {
                fileOutputStream = new FileOutputStream(file, true);
                try {
                    fileOutputStream.write(str.getBytes());
                    fileOutputStream.write(10);
                    if (fileOutputStream != null) {
                        try {
                            fileOutputStream.close();
                            return;
                        } catch (Throwable e2) {
                            zzb.zzd("CsiReporter: Cannot close file: sdk_csi_data.txt.", e2);
                            return;
                        }
                    }
                    return;
                } catch (IOException e3) {
                    e2 = e3;
                    try {
                        zzb.zzd("CsiReporter: Cannot write to file: sdk_csi_data.txt.", e2);
                        if (fileOutputStream != null) {
                            try {
                                fileOutputStream.close();
                                return;
                            } catch (Throwable e22) {
                                zzb.zzd("CsiReporter: Cannot close file: sdk_csi_data.txt.", e22);
                                return;
                            }
                        }
                        return;
                    } catch (Throwable th) {
                        e22 = th;
                        if (fileOutputStream != null) {
                            try {
                                fileOutputStream.close();
                            } catch (Throwable e4) {
                                zzb.zzd("CsiReporter: Cannot close file: sdk_csi_data.txt.", e4);
                            }
                        }
                        throw e22;
                    }
                }
            } catch (IOException e5) {
                e22 = e5;
                fileOutputStream = null;
                zzb.zzd("CsiReporter: Cannot write to file: sdk_csi_data.txt.", e22);
                if (fileOutputStream != null) {
                    fileOutputStream.close();
                    return;
                }
                return;
            } catch (Throwable th2) {
                e22 = th2;
                fileOutputStream = null;
                if (fileOutputStream != null) {
                    fileOutputStream.close();
                }
                throw e22;
            }
        }
        zzb.zzaE("CsiReporter: File doesn't exists. Cannot write CSI data to file.");
    }

    private void zzc(Map<String, String> map, String str) {
        String zza = zza(this.zzvg, map, str);
        if (this.zzvl.get()) {
            zza(this.zzvm, zza);
        } else {
            zzp.zzbx().zzc(this.mContext, this.zzqK, zza);
        }
    }

    private void zzdi() {
        while (true) {
            try {
                zzcd com_google_android_gms_internal_zzcd = (zzcd) this.zzvi.take();
                Object zzdn = com_google_android_gms_internal_zzcd.zzdn();
                if (!TextUtils.isEmpty(zzdn)) {
                    this.zzvk.putAll(com_google_android_gms_internal_zzcd.zzn());
                    zzc(this.zzvk, zzdn);
                }
            } catch (Throwable e) {
                zzb.zzd("CsiReporter:reporter interrupted", e);
                return;
            }
        }
    }

    String zza(String str, Map<String, String> map, String str2) {
        Builder buildUpon = Uri.parse(str).buildUpon();
        for (Entry entry : map.entrySet()) {
            buildUpon.appendQueryParameter((String) entry.getKey(), (String) entry.getValue());
        }
        StringBuilder stringBuilder = new StringBuilder(buildUpon.build().toString());
        stringBuilder.append("&").append("it").append("=").append(str2);
        return stringBuilder.toString();
    }

    public boolean zza(zzcd com_google_android_gms_internal_zzcd) {
        return this.zzvi.offer(com_google_android_gms_internal_zzcd);
    }

    public void zzb(List<String> list) {
        if (list != null && !list.isEmpty()) {
            this.zzvk.put("e", TextUtils.join(",", list));
        }
    }
}
