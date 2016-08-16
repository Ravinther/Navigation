package com.google.android.gms.internal;

import com.google.android.gms.ads.internal.util.client.zzb;
import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@zzgk
public class zzdu extends zzdr {
    private static final Set<String> zzxB;
    private static final DecimalFormat zzxC;
    private File zzxD;
    private boolean zzxE;

    static {
        zzxB = Collections.synchronizedSet(new HashSet());
        zzxC = new DecimalFormat("#,###");
    }

    public zzdu(zzip com_google_android_gms_internal_zzip) {
        super(com_google_android_gms_internal_zzip);
        File cacheDir = com_google_android_gms_internal_zzip.getContext().getCacheDir();
        if (cacheDir == null) {
            zzb.zzaE("Context.getCacheDir() returned null");
            return;
        }
        this.zzxD = new File(cacheDir, "admobVideoStreams");
        if (!this.zzxD.isDirectory() && !this.zzxD.mkdirs()) {
            zzb.zzaE("Could not create preload cache directory at " + this.zzxD.getAbsolutePath());
            this.zzxD = null;
        } else if (!this.zzxD.setReadable(true, false) || !this.zzxD.setExecutable(true, false)) {
            zzb.zzaE("Could not set cache file permissions at " + this.zzxD.getAbsolutePath());
            this.zzxD = null;
        }
    }

    private File zza(File file) {
        return new File(this.zzxD, file.getName() + ".done");
    }

    private static void zzb(File file) {
        if (file.isFile()) {
            file.setLastModified(System.currentTimeMillis());
            return;
        }
        try {
            file.createNewFile();
        } catch (IOException e) {
        }
    }

    public void abort() {
        this.zzxE = true;
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public boolean zzZ(java.lang.String r25) {
        /*
        r24 = this;
        r0 = r24;
        r2 = r0.zzxD;
        if (r2 != 0) goto L_0x0010;
    L_0x0006:
        r2 = 0;
        r0 = r24;
        r1 = r25;
        r0.zzf(r1, r2);
        r2 = 0;
    L_0x000f:
        return r2;
    L_0x0010:
        r3 = r24.zzdH();
        r2 = com.google.android.gms.internal.zzby.zzuj;
        r2 = r2.get();
        r2 = (java.lang.Integer) r2;
        r2 = r2.intValue();
        if (r3 <= r2) goto L_0x0038;
    L_0x0022:
        r2 = r24.zzdI();
        if (r2 != 0) goto L_0x0010;
    L_0x0028:
        r2 = "Unable to expire stream cache";
        com.google.android.gms.ads.internal.util.client.zzb.zzaE(r2);
        r2 = 0;
        r0 = r24;
        r1 = r25;
        r0.zzf(r1, r2);
        r2 = 0;
        goto L_0x000f;
    L_0x0038:
        r2 = r24.zzaa(r25);
        r9 = new java.io.File;
        r0 = r24;
        r3 = r0.zzxD;
        r9.<init>(r3, r2);
        r0 = r24;
        r10 = r0.zza(r9);
        r2 = r9.isFile();
        if (r2 == 0) goto L_0x0082;
    L_0x0051:
        r2 = r10.isFile();
        if (r2 == 0) goto L_0x0082;
    L_0x0057:
        r2 = r9.length();
        r2 = (int) r2;
        r3 = new java.lang.StringBuilder;
        r3.<init>();
        r4 = "Stream cache hit at ";
        r3 = r3.append(r4);
        r0 = r25;
        r3 = r3.append(r0);
        r3 = r3.toString();
        com.google.android.gms.ads.internal.util.client.zzb.zzaC(r3);
        r3 = r9.getAbsolutePath();
        r0 = r24;
        r1 = r25;
        r0.zza(r1, r3, r2);
        r2 = 1;
        goto L_0x000f;
    L_0x0082:
        r2 = new java.lang.StringBuilder;
        r2.<init>();
        r0 = r24;
        r3 = r0.zzxD;
        r3 = r3.getAbsolutePath();
        r2 = r2.append(r3);
        r0 = r25;
        r2 = r2.append(r0);
        r11 = r2.toString();
        r3 = zzxB;
        monitor-enter(r3);
        r2 = zzxB;	 Catch:{ all -> 0x00d0 }
        r2 = r2.contains(r11);	 Catch:{ all -> 0x00d0 }
        if (r2 == 0) goto L_0x00d3;
    L_0x00a8:
        r2 = new java.lang.StringBuilder;	 Catch:{ all -> 0x00d0 }
        r2.<init>();	 Catch:{ all -> 0x00d0 }
        r4 = "Stream cache already in progress at ";
        r2 = r2.append(r4);	 Catch:{ all -> 0x00d0 }
        r0 = r25;
        r2 = r2.append(r0);	 Catch:{ all -> 0x00d0 }
        r2 = r2.toString();	 Catch:{ all -> 0x00d0 }
        com.google.android.gms.ads.internal.util.client.zzb.zzaE(r2);	 Catch:{ all -> 0x00d0 }
        r2 = r9.getAbsolutePath();	 Catch:{ all -> 0x00d0 }
        r0 = r24;
        r1 = r25;
        r0.zzf(r1, r2);	 Catch:{ all -> 0x00d0 }
        r2 = 0;
        monitor-exit(r3);	 Catch:{ all -> 0x00d0 }
        goto L_0x000f;
    L_0x00d0:
        r2 = move-exception;
        monitor-exit(r3);	 Catch:{ all -> 0x00d0 }
        throw r2;
    L_0x00d3:
        r2 = zzxB;	 Catch:{ all -> 0x00d0 }
        r2.add(r11);	 Catch:{ all -> 0x00d0 }
        monitor-exit(r3);	 Catch:{ all -> 0x00d0 }
        r4 = 0;
        r2 = new java.net.URL;	 Catch:{ IOException -> 0x012e }
        r0 = r25;
        r2.<init>(r0);	 Catch:{ IOException -> 0x012e }
        r3 = r2.openConnection();	 Catch:{ IOException -> 0x012e }
        r2 = com.google.android.gms.internal.zzby.zzuo;	 Catch:{ IOException -> 0x012e }
        r2 = r2.get();	 Catch:{ IOException -> 0x012e }
        r2 = (java.lang.Integer) r2;	 Catch:{ IOException -> 0x012e }
        r2 = r2.intValue();	 Catch:{ IOException -> 0x012e }
        r3.setConnectTimeout(r2);	 Catch:{ IOException -> 0x012e }
        r3.setReadTimeout(r2);	 Catch:{ IOException -> 0x012e }
        r2 = r3 instanceof java.net.HttpURLConnection;	 Catch:{ IOException -> 0x012e }
        if (r2 == 0) goto L_0x0193;
    L_0x00fb:
        r0 = r3;
        r0 = (java.net.HttpURLConnection) r0;	 Catch:{ IOException -> 0x012e }
        r2 = r0;
        r2 = r2.getResponseCode();	 Catch:{ IOException -> 0x012e }
        r5 = 400; // 0x190 float:5.6E-43 double:1.976E-321;
        if (r2 < r5) goto L_0x0193;
    L_0x0107:
        r3 = new java.io.IOException;	 Catch:{ IOException -> 0x012e }
        r5 = new java.lang.StringBuilder;	 Catch:{ IOException -> 0x012e }
        r5.<init>();	 Catch:{ IOException -> 0x012e }
        r6 = "HTTP status code ";
        r5 = r5.append(r6);	 Catch:{ IOException -> 0x012e }
        r2 = r5.append(r2);	 Catch:{ IOException -> 0x012e }
        r5 = " at ";
        r2 = r2.append(r5);	 Catch:{ IOException -> 0x012e }
        r0 = r25;
        r2 = r2.append(r0);	 Catch:{ IOException -> 0x012e }
        r2 = r2.toString();	 Catch:{ IOException -> 0x012e }
        r3.<init>(r2);	 Catch:{ IOException -> 0x012e }
        throw r3;	 Catch:{ IOException -> 0x012e }
    L_0x012e:
        r2 = move-exception;
        r3 = r4;
    L_0x0130:
        r3.close();	 Catch:{ IOException -> 0x0341, NullPointerException -> 0x0344 }
    L_0x0133:
        r0 = r24;
        r3 = r0.zzxE;
        if (r3 == 0) goto L_0x031f;
    L_0x0139:
        r2 = new java.lang.StringBuilder;
        r2.<init>();
        r3 = "Preload aborted for URL \"";
        r2 = r2.append(r3);
        r0 = r25;
        r2 = r2.append(r0);
        r3 = "\"";
        r2 = r2.append(r3);
        r2 = r2.toString();
        com.google.android.gms.ads.internal.util.client.zzb.zzaD(r2);
    L_0x0159:
        r2 = r9.exists();
        if (r2 == 0) goto L_0x0180;
    L_0x015f:
        r2 = r9.delete();
        if (r2 != 0) goto L_0x0180;
    L_0x0165:
        r2 = new java.lang.StringBuilder;
        r2.<init>();
        r3 = "Could not delete partial cache file at ";
        r2 = r2.append(r3);
        r3 = r9.getAbsolutePath();
        r2 = r2.append(r3);
        r2 = r2.toString();
        com.google.android.gms.ads.internal.util.client.zzb.zzaE(r2);
    L_0x0180:
        r2 = r9.getAbsolutePath();
        r0 = r24;
        r1 = r25;
        r0.zzf(r1, r2);
        r2 = zzxB;
        r2.remove(r11);
        r2 = 0;
        goto L_0x000f;
    L_0x0193:
        r6 = r3.getContentLength();	 Catch:{ IOException -> 0x012e }
        if (r6 >= 0) goto L_0x01c5;
    L_0x0199:
        r2 = new java.lang.StringBuilder;	 Catch:{ IOException -> 0x012e }
        r2.<init>();	 Catch:{ IOException -> 0x012e }
        r3 = "Stream cache aborted, missing content-length header at ";
        r2 = r2.append(r3);	 Catch:{ IOException -> 0x012e }
        r0 = r25;
        r2 = r2.append(r0);	 Catch:{ IOException -> 0x012e }
        r2 = r2.toString();	 Catch:{ IOException -> 0x012e }
        com.google.android.gms.ads.internal.util.client.zzb.zzaE(r2);	 Catch:{ IOException -> 0x012e }
        r2 = r9.getAbsolutePath();	 Catch:{ IOException -> 0x012e }
        r0 = r24;
        r1 = r25;
        r0.zzf(r1, r2);	 Catch:{ IOException -> 0x012e }
        r2 = zzxB;	 Catch:{ IOException -> 0x012e }
        r2.remove(r11);	 Catch:{ IOException -> 0x012e }
        r2 = 0;
        goto L_0x000f;
    L_0x01c5:
        r2 = zzxC;	 Catch:{ IOException -> 0x012e }
        r12 = (long) r6;	 Catch:{ IOException -> 0x012e }
        r5 = r2.format(r12);	 Catch:{ IOException -> 0x012e }
        r2 = com.google.android.gms.internal.zzby.zzuk;	 Catch:{ IOException -> 0x012e }
        r2 = r2.get();	 Catch:{ IOException -> 0x012e }
        r2 = (java.lang.Integer) r2;	 Catch:{ IOException -> 0x012e }
        r12 = r2.intValue();	 Catch:{ IOException -> 0x012e }
        if (r6 <= r12) goto L_0x0211;
    L_0x01da:
        r2 = new java.lang.StringBuilder;	 Catch:{ IOException -> 0x012e }
        r2.<init>();	 Catch:{ IOException -> 0x012e }
        r3 = "Content length ";
        r2 = r2.append(r3);	 Catch:{ IOException -> 0x012e }
        r2 = r2.append(r5);	 Catch:{ IOException -> 0x012e }
        r3 = " exceeds limit at ";
        r2 = r2.append(r3);	 Catch:{ IOException -> 0x012e }
        r0 = r25;
        r2 = r2.append(r0);	 Catch:{ IOException -> 0x012e }
        r2 = r2.toString();	 Catch:{ IOException -> 0x012e }
        com.google.android.gms.ads.internal.util.client.zzb.zzaE(r2);	 Catch:{ IOException -> 0x012e }
        r2 = r9.getAbsolutePath();	 Catch:{ IOException -> 0x012e }
        r0 = r24;
        r1 = r25;
        r0.zzf(r1, r2);	 Catch:{ IOException -> 0x012e }
        r2 = zzxB;	 Catch:{ IOException -> 0x012e }
        r2.remove(r11);	 Catch:{ IOException -> 0x012e }
        r2 = 0;
        goto L_0x000f;
    L_0x0211:
        r2 = new java.lang.StringBuilder;	 Catch:{ IOException -> 0x012e }
        r2.<init>();	 Catch:{ IOException -> 0x012e }
        r7 = "Caching ";
        r2 = r2.append(r7);	 Catch:{ IOException -> 0x012e }
        r2 = r2.append(r5);	 Catch:{ IOException -> 0x012e }
        r5 = " bytes from ";
        r2 = r2.append(r5);	 Catch:{ IOException -> 0x012e }
        r0 = r25;
        r2 = r2.append(r0);	 Catch:{ IOException -> 0x012e }
        r2 = r2.toString();	 Catch:{ IOException -> 0x012e }
        com.google.android.gms.ads.internal.util.client.zzb.zzaC(r2);	 Catch:{ IOException -> 0x012e }
        r2 = r3.getInputStream();	 Catch:{ IOException -> 0x012e }
        r13 = java.nio.channels.Channels.newChannel(r2);	 Catch:{ IOException -> 0x012e }
        r8 = new java.io.FileOutputStream;	 Catch:{ IOException -> 0x012e }
        r8.<init>(r9);	 Catch:{ IOException -> 0x012e }
        r14 = r8.getChannel();	 Catch:{ IOException -> 0x0286 }
        r2 = 1048576; // 0x100000 float:1.469368E-39 double:5.180654E-318;
        r15 = java.nio.ByteBuffer.allocate(r2);	 Catch:{ IOException -> 0x0286 }
        r16 = com.google.android.gms.ads.internal.zzp.zzbB();	 Catch:{ IOException -> 0x0286 }
        r5 = 0;
        r18 = r16.currentTimeMillis();	 Catch:{ IOException -> 0x0286 }
        r2 = com.google.android.gms.internal.zzby.zzun;	 Catch:{ IOException -> 0x0286 }
        r2 = r2.get();	 Catch:{ IOException -> 0x0286 }
        r2 = (java.lang.Long) r2;	 Catch:{ IOException -> 0x0286 }
        r2 = r2.longValue();	 Catch:{ IOException -> 0x0286 }
        r17 = new com.google.android.gms.internal.zzib;	 Catch:{ IOException -> 0x0286 }
        r0 = r17;
        r0.<init>(r2);	 Catch:{ IOException -> 0x0286 }
        r2 = com.google.android.gms.internal.zzby.zzum;	 Catch:{ IOException -> 0x0286 }
        r2 = r2.get();	 Catch:{ IOException -> 0x0286 }
        r2 = (java.lang.Long) r2;	 Catch:{ IOException -> 0x0286 }
        r20 = r2.longValue();	 Catch:{ IOException -> 0x0286 }
    L_0x0274:
        r2 = r13.read(r15);	 Catch:{ IOException -> 0x0286 }
        if (r2 < 0) goto L_0x02cf;
    L_0x027a:
        r5 = r5 + r2;
        if (r5 <= r12) goto L_0x028a;
    L_0x027d:
        r2 = new java.io.IOException;	 Catch:{ IOException -> 0x0286 }
        r3 = "stream cache file size limit exceeded";
        r2.<init>(r3);	 Catch:{ IOException -> 0x0286 }
        throw r2;	 Catch:{ IOException -> 0x0286 }
    L_0x0286:
        r2 = move-exception;
        r3 = r8;
        goto L_0x0130;
    L_0x028a:
        r15.flip();	 Catch:{ IOException -> 0x0286 }
    L_0x028d:
        r2 = r14.write(r15);	 Catch:{ IOException -> 0x0286 }
        if (r2 > 0) goto L_0x028d;
    L_0x0293:
        r15.clear();	 Catch:{ IOException -> 0x0286 }
        r2 = r16.currentTimeMillis();	 Catch:{ IOException -> 0x0286 }
        r2 = r2 - r18;
        r22 = 1000; // 0x3e8 float:1.401E-42 double:4.94E-321;
        r22 = r22 * r20;
        r2 = (r2 > r22 ? 1 : (r2 == r22 ? 0 : -1));
        if (r2 <= 0) goto L_0x02ad;
    L_0x02a4:
        r2 = new java.io.IOException;	 Catch:{ IOException -> 0x0286 }
        r3 = "stream cache time limit exceeded";
        r2.<init>(r3);	 Catch:{ IOException -> 0x0286 }
        throw r2;	 Catch:{ IOException -> 0x0286 }
    L_0x02ad:
        r0 = r24;
        r2 = r0.zzxE;	 Catch:{ IOException -> 0x0286 }
        if (r2 == 0) goto L_0x02bc;
    L_0x02b3:
        r2 = new java.io.IOException;	 Catch:{ IOException -> 0x0286 }
        r3 = "abort requested";
        r2.<init>(r3);	 Catch:{ IOException -> 0x0286 }
        throw r2;	 Catch:{ IOException -> 0x0286 }
    L_0x02bc:
        r2 = r17.tryAcquire();	 Catch:{ IOException -> 0x0286 }
        if (r2 == 0) goto L_0x0274;
    L_0x02c2:
        r4 = r9.getAbsolutePath();	 Catch:{ IOException -> 0x0286 }
        r7 = 0;
        r2 = r24;
        r3 = r25;
        r2.zza(r3, r4, r5, r6, r7);	 Catch:{ IOException -> 0x0286 }
        goto L_0x0274;
    L_0x02cf:
        r8.close();	 Catch:{ IOException -> 0x0286 }
        r2 = 3;
        r2 = com.google.android.gms.ads.internal.util.client.zzb.zzM(r2);	 Catch:{ IOException -> 0x0286 }
        if (r2 == 0) goto L_0x0304;
    L_0x02d9:
        r2 = zzxC;	 Catch:{ IOException -> 0x0286 }
        r6 = (long) r5;	 Catch:{ IOException -> 0x0286 }
        r2 = r2.format(r6);	 Catch:{ IOException -> 0x0286 }
        r3 = new java.lang.StringBuilder;	 Catch:{ IOException -> 0x0286 }
        r3.<init>();	 Catch:{ IOException -> 0x0286 }
        r4 = "Preloaded ";
        r3 = r3.append(r4);	 Catch:{ IOException -> 0x0286 }
        r2 = r3.append(r2);	 Catch:{ IOException -> 0x0286 }
        r3 = " bytes from ";
        r2 = r2.append(r3);	 Catch:{ IOException -> 0x0286 }
        r0 = r25;
        r2 = r2.append(r0);	 Catch:{ IOException -> 0x0286 }
        r2 = r2.toString();	 Catch:{ IOException -> 0x0286 }
        com.google.android.gms.ads.internal.util.client.zzb.zzaC(r2);	 Catch:{ IOException -> 0x0286 }
    L_0x0304:
        r2 = 1;
        r3 = 0;
        r9.setReadable(r2, r3);	 Catch:{ IOException -> 0x0286 }
        zzb(r10);	 Catch:{ IOException -> 0x0286 }
        r2 = r9.getAbsolutePath();	 Catch:{ IOException -> 0x0286 }
        r0 = r24;
        r1 = r25;
        r0.zza(r1, r2, r5);	 Catch:{ IOException -> 0x0286 }
        r2 = zzxB;	 Catch:{ IOException -> 0x0286 }
        r2.remove(r11);	 Catch:{ IOException -> 0x0286 }
        r2 = 1;
        goto L_0x000f;
    L_0x031f:
        r3 = new java.lang.StringBuilder;
        r3.<init>();
        r4 = "Preload failed for URL \"";
        r3 = r3.append(r4);
        r0 = r25;
        r3 = r3.append(r0);
        r4 = "\"";
        r3 = r3.append(r4);
        r3 = r3.toString();
        com.google.android.gms.ads.internal.util.client.zzb.zzd(r3, r2);
        goto L_0x0159;
    L_0x0341:
        r3 = move-exception;
        goto L_0x0133;
    L_0x0344:
        r3 = move-exception;
        goto L_0x0133;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.zzdu.zzZ(java.lang.String):boolean");
    }

    public int zzdH() {
        int i = 0;
        if (this.zzxD != null) {
            for (File name : this.zzxD.listFiles()) {
                if (!name.getName().endsWith(".done")) {
                    i++;
                }
            }
        }
        return i;
    }

    public boolean zzdI() {
        if (this.zzxD == null) {
            return false;
        }
        boolean delete;
        File file = null;
        long j = Long.MAX_VALUE;
        File[] listFiles = this.zzxD.listFiles();
        int length = listFiles.length;
        int i = 0;
        while (i < length) {
            long lastModified;
            File file2;
            File file3 = listFiles[i];
            if (!file3.getName().endsWith(".done")) {
                lastModified = file3.lastModified();
                if (lastModified < j) {
                    file2 = file3;
                    i++;
                    file = file2;
                    j = lastModified;
                }
            }
            lastModified = j;
            file2 = file;
            i++;
            file = file2;
            j = lastModified;
        }
        if (file != null) {
            delete = file.delete();
            File zza = zza(file);
            if (zza.isFile()) {
                delete &= zza.delete();
            }
        } else {
            delete = false;
        }
        return delete;
    }
}
