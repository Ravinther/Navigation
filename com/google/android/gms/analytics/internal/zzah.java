package com.google.android.gms.analytics.internal;

import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Build.VERSION;
import com.google.android.gms.common.internal.zzx;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Map.Entry;
import java.util.zip.GZIPOutputStream;

class zzah extends zzd {
    private static final byte[] zzOs;
    private final String zzHj;
    private final zzaj zzOr;

    private class zza {
        private int zzOt;
        private ByteArrayOutputStream zzOu;
        final /* synthetic */ zzah zzOv;

        public zza(zzah com_google_android_gms_analytics_internal_zzah) {
            this.zzOv = com_google_android_gms_analytics_internal_zzah;
            this.zzOu = new ByteArrayOutputStream();
        }

        public byte[] getPayload() {
            return this.zzOu.toByteArray();
        }

        public boolean zzj(zzab com_google_android_gms_analytics_internal_zzab) {
            zzx.zzv(com_google_android_gms_analytics_internal_zzab);
            if (this.zzOt + 1 > this.zzOv.zzif().zzjw()) {
                return false;
            }
            String zza = this.zzOv.zza(com_google_android_gms_analytics_internal_zzab, false);
            if (zza == null) {
                this.zzOv.zzie().zza(com_google_android_gms_analytics_internal_zzab, "Error formatting hit");
                return true;
            }
            byte[] bytes = zza.getBytes();
            int length = bytes.length;
            if (length > this.zzOv.zzif().zzjo()) {
                this.zzOv.zzie().zza(com_google_android_gms_analytics_internal_zzab, "Hit size exceeds the maximum size limit");
                return true;
            }
            if (this.zzOu.size() > 0) {
                length++;
            }
            if (length + this.zzOu.size() > this.zzOv.zzif().zzjq()) {
                return false;
            }
            try {
                if (this.zzOu.size() > 0) {
                    this.zzOu.write(zzah.zzOs);
                }
                this.zzOu.write(bytes);
                this.zzOt++;
                return true;
            } catch (IOException e) {
                this.zzOv.zze("Failed to write payload when batching hits", e);
                return true;
            }
        }

        public int zzkx() {
            return this.zzOt;
        }
    }

    static {
        zzOs = "\n".getBytes();
    }

    zzah(zzf com_google_android_gms_analytics_internal_zzf) {
        super(com_google_android_gms_analytics_internal_zzf);
        this.zzHj = zza("GoogleAnalytics", zze.VERSION, VERSION.RELEASE, zzam.zza(Locale.getDefault()), Build.MODEL, Build.ID);
        this.zzOr = new zzaj(com_google_android_gms_analytics_internal_zzf.zzid());
    }

    private int zza(URL url, byte[] bArr) {
        HttpURLConnection zzc;
        int responseCode;
        Object e;
        Throwable th;
        OutputStream outputStream = null;
        zzx.zzv(url);
        zzx.zzv(bArr);
        zzb("POST bytes, url", Integer.valueOf(bArr.length), url);
        if (zzin()) {
            zza("Post payload\n", new String(bArr));
        }
        try {
            zzc = zzc(url);
            try {
                zzc.setDoOutput(true);
                zzc.setFixedLengthStreamingMode(bArr.length);
                zzc.connect();
                outputStream = zzc.getOutputStream();
                outputStream.write(bArr);
                zzb(zzc);
                responseCode = zzc.getResponseCode();
                if (responseCode == 200) {
                    zzhz().zzhZ();
                }
                zzb("POST status", Integer.valueOf(responseCode));
                if (outputStream != null) {
                    try {
                        outputStream.close();
                    } catch (IOException e2) {
                        zze("Error closing http post connection output stream", e2);
                    }
                }
                if (zzc != null) {
                    zzc.disconnect();
                }
            } catch (IOException e3) {
                e = e3;
                try {
                    zzd("Network POST connection error", e);
                    responseCode = 0;
                    if (outputStream != null) {
                        try {
                            outputStream.close();
                        } catch (IOException e22) {
                            zze("Error closing http post connection output stream", e22);
                        }
                    }
                    if (zzc != null) {
                        zzc.disconnect();
                    }
                    return responseCode;
                } catch (Throwable th2) {
                    th = th2;
                    if (outputStream != null) {
                        try {
                            outputStream.close();
                        } catch (IOException e222) {
                            zze("Error closing http post connection output stream", e222);
                        }
                    }
                    if (zzc != null) {
                        zzc.disconnect();
                    }
                    throw th;
                }
            }
        } catch (IOException e4) {
            e = e4;
            zzc = outputStream;
            zzd("Network POST connection error", e);
            responseCode = 0;
            if (outputStream != null) {
                outputStream.close();
            }
            if (zzc != null) {
                zzc.disconnect();
            }
            return responseCode;
        } catch (Throwable th3) {
            th = th3;
            zzc = outputStream;
            if (outputStream != null) {
                outputStream.close();
            }
            if (zzc != null) {
                zzc.disconnect();
            }
            throw th;
        }
        return responseCode;
    }

    private static String zza(String str, String str2, String str3, String str4, String str5, String str6) {
        return String.format("%s/%s (Linux; U; Android %s; %s; %s Build/%s)", new Object[]{str, str2, str3, str4, str5, str6});
    }

    private void zza(StringBuilder stringBuilder, String str, String str2) throws UnsupportedEncodingException {
        if (stringBuilder.length() != 0) {
            stringBuilder.append('&');
        }
        stringBuilder.append(URLEncoder.encode(str, "UTF-8"));
        stringBuilder.append('=');
        stringBuilder.append(URLEncoder.encode(str2, "UTF-8"));
    }

    private int zzb(URL url) {
        int responseCode;
        zzx.zzv(url);
        zzb("GET request", url);
        HttpURLConnection httpURLConnection = null;
        try {
            httpURLConnection = zzc(url);
            httpURLConnection.connect();
            zzb(httpURLConnection);
            responseCode = httpURLConnection.getResponseCode();
            if (responseCode == 200) {
                zzhz().zzhZ();
            }
            zzb("GET status", Integer.valueOf(responseCode));
            if (httpURLConnection != null) {
                httpURLConnection.disconnect();
            }
        } catch (IOException e) {
            zzd("Network GET connection error", e);
            responseCode = 0;
            if (httpURLConnection != null) {
                httpURLConnection.disconnect();
            }
        } catch (Throwable th) {
            if (httpURLConnection != null) {
                httpURLConnection.disconnect();
            }
        }
        return responseCode;
    }

    private int zzb(URL url, byte[] bArr) {
        HttpURLConnection zzc;
        int responseCode;
        Object e;
        HttpURLConnection httpURLConnection;
        Throwable th;
        OutputStream outputStream = null;
        zzx.zzv(url);
        zzx.zzv(bArr);
        try {
            byte[] zzg = zzg(bArr);
            zza("POST compressed size, ratio %, url", Integer.valueOf(zzg.length), Long.valueOf((100 * ((long) zzg.length)) / ((long) bArr.length)), url);
            if (zzg.length > bArr.length) {
                zzc("Compressed payload is larger then uncompressed. compressed, uncompressed", Integer.valueOf(zzg.length), Integer.valueOf(bArr.length));
            }
            if (zzin()) {
                zza("Post payload", "\n" + new String(bArr));
            }
            zzc = zzc(url);
            try {
                zzc.setDoOutput(true);
                zzc.addRequestProperty("Content-Encoding", "gzip");
                zzc.setFixedLengthStreamingMode(zzg.length);
                zzc.connect();
                OutputStream outputStream2 = zzc.getOutputStream();
                try {
                    outputStream2.write(zzg);
                    outputStream2.close();
                    outputStream2 = null;
                    zzb(zzc);
                    responseCode = zzc.getResponseCode();
                    if (responseCode == 200) {
                        zzhz().zzhZ();
                    }
                    zzb("POST status", Integer.valueOf(responseCode));
                    if (null != null) {
                        try {
                            outputStream2.close();
                        } catch (IOException e2) {
                            zze("Error closing http compressed post connection output stream", e2);
                        }
                    }
                    if (zzc != null) {
                        zzc.disconnect();
                    }
                } catch (IOException e3) {
                    e = e3;
                    outputStream = outputStream2;
                    httpURLConnection = zzc;
                    try {
                        zzd("Network compressed POST connection error", e);
                        responseCode = 0;
                        if (outputStream != null) {
                            try {
                                outputStream.close();
                            } catch (IOException e22) {
                                zze("Error closing http compressed post connection output stream", e22);
                            }
                        }
                        if (httpURLConnection != null) {
                            httpURLConnection.disconnect();
                        }
                        return responseCode;
                    } catch (Throwable th2) {
                        th = th2;
                        zzc = httpURLConnection;
                        if (outputStream != null) {
                            try {
                                outputStream.close();
                            } catch (IOException e222) {
                                zze("Error closing http compressed post connection output stream", e222);
                            }
                        }
                        if (zzc != null) {
                            zzc.disconnect();
                        }
                        throw th;
                    }
                } catch (Throwable th3) {
                    th = th3;
                    outputStream = outputStream2;
                    if (outputStream != null) {
                        outputStream.close();
                    }
                    if (zzc != null) {
                        zzc.disconnect();
                    }
                    throw th;
                }
            } catch (IOException e4) {
                e = e4;
                httpURLConnection = zzc;
                zzd("Network compressed POST connection error", e);
                responseCode = 0;
                if (outputStream != null) {
                    outputStream.close();
                }
                if (httpURLConnection != null) {
                    httpURLConnection.disconnect();
                }
                return responseCode;
            } catch (Throwable th4) {
                th = th4;
                if (outputStream != null) {
                    outputStream.close();
                }
                if (zzc != null) {
                    zzc.disconnect();
                }
                throw th;
            }
        } catch (IOException e5) {
            e = e5;
            httpURLConnection = null;
            zzd("Network compressed POST connection error", e);
            responseCode = 0;
            if (outputStream != null) {
                outputStream.close();
            }
            if (httpURLConnection != null) {
                httpURLConnection.disconnect();
            }
            return responseCode;
        } catch (Throwable th5) {
            th = th5;
            zzc = null;
            if (outputStream != null) {
                outputStream.close();
            }
            if (zzc != null) {
                zzc.disconnect();
            }
            throw th;
        }
        return responseCode;
    }

    private URL zzb(zzab com_google_android_gms_analytics_internal_zzab, String str) {
        try {
            return new URL(com_google_android_gms_analytics_internal_zzab.zzkm() ? zzif().zzjy() + zzif().zzjA() + "?" + str : zzif().zzjz() + zzif().zzjA() + "?" + str);
        } catch (MalformedURLException e) {
            zze("Error trying to parse the hardcoded host url", e);
            return null;
        }
    }

    private void zzb(HttpURLConnection httpURLConnection) throws IOException {
        InputStream inputStream = null;
        try {
            inputStream = httpURLConnection.getInputStream();
            do {
            } while (inputStream.read(new byte[1024]) > 0);
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    zze("Error closing http connection input stream", e);
                }
            }
        } catch (Throwable th) {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e2) {
                    zze("Error closing http connection input stream", e2);
                }
            }
        }
    }

    private boolean zzg(zzab com_google_android_gms_analytics_internal_zzab) {
        zzx.zzv(com_google_android_gms_analytics_internal_zzab);
        String zza = zza(com_google_android_gms_analytics_internal_zzab, !com_google_android_gms_analytics_internal_zzab.zzkm());
        if (zza == null) {
            zzie().zza(com_google_android_gms_analytics_internal_zzab, "Error formatting hit for upload");
            return true;
        } else if (zza.length() <= zzif().zzjn()) {
            URL zzb = zzb(com_google_android_gms_analytics_internal_zzab, zza);
            if (zzb != null) {
                return zzb(zzb) == 200;
            } else {
                zzbc("Failed to build collect GET endpoint url");
                return false;
            }
        } else {
            zza = zza(com_google_android_gms_analytics_internal_zzab, false);
            if (zza == null) {
                zzie().zza(com_google_android_gms_analytics_internal_zzab, "Error formatting hit for POST upload");
                return true;
            }
            byte[] bytes = zza.getBytes();
            if (bytes.length > zzif().zzjp()) {
                zzie().zza(com_google_android_gms_analytics_internal_zzab, "Hit payload exceeds size limit");
                return true;
            }
            URL zzh = zzh(com_google_android_gms_analytics_internal_zzab);
            if (zzh != null) {
                return zza(zzh, bytes) == 200;
            } else {
                zzbc("Failed to build collect POST endpoint url");
                return false;
            }
        }
    }

    private static byte[] zzg(byte[] bArr) throws IOException {
        OutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        GZIPOutputStream gZIPOutputStream = new GZIPOutputStream(byteArrayOutputStream);
        gZIPOutputStream.write(bArr);
        gZIPOutputStream.close();
        byteArrayOutputStream.close();
        return byteArrayOutputStream.toByteArray();
    }

    private URL zzh(zzab com_google_android_gms_analytics_internal_zzab) {
        try {
            return new URL(com_google_android_gms_analytics_internal_zzab.zzkm() ? zzif().zzjy() + zzif().zzjA() : zzif().zzjz() + zzif().zzjA());
        } catch (MalformedURLException e) {
            zze("Error trying to parse the hardcoded host url", e);
            return null;
        }
    }

    private String zzi(zzab com_google_android_gms_analytics_internal_zzab) {
        return String.valueOf(com_google_android_gms_analytics_internal_zzab.zzkj());
    }

    private URL zzkv() {
        try {
            return new URL(zzif().zzjy() + zzif().zzjB());
        } catch (MalformedURLException e) {
            zze("Error trying to parse the hardcoded host url", e);
            return null;
        }
    }

    String zza(zzab com_google_android_gms_analytics_internal_zzab, boolean z) {
        zzx.zzv(com_google_android_gms_analytics_internal_zzab);
        StringBuilder stringBuilder = new StringBuilder();
        try {
            for (Entry entry : com_google_android_gms_analytics_internal_zzab.zzn().entrySet()) {
                String str = (String) entry.getKey();
                if (!("ht".equals(str) || "qt".equals(str) || "AppUID".equals(str) || "z".equals(str) || "_gmsv".equals(str))) {
                    zza(stringBuilder, str, (String) entry.getValue());
                }
            }
            zza(stringBuilder, "ht", String.valueOf(com_google_android_gms_analytics_internal_zzab.zzkk()));
            zza(stringBuilder, "qt", String.valueOf(zzid().currentTimeMillis() - com_google_android_gms_analytics_internal_zzab.zzkk()));
            if (zzif().zzjk()) {
                zza(stringBuilder, "_gmsv", zze.VERSION);
            }
            if (z) {
                long zzkn = com_google_android_gms_analytics_internal_zzab.zzkn();
                zza(stringBuilder, "z", zzkn != 0 ? String.valueOf(zzkn) : zzi(com_google_android_gms_analytics_internal_zzab));
            }
            return stringBuilder.toString();
        } catch (UnsupportedEncodingException e) {
            zze("Failed to encode name or value", e);
            return null;
        }
    }

    List<Long> zza(List<zzab> list, boolean z) {
        zzx.zzZ(!list.isEmpty());
        zza("Uploading batched hits. compression, count", Boolean.valueOf(z), Integer.valueOf(list.size()));
        zza com_google_android_gms_analytics_internal_zzah_zza = new zza(this);
        List<Long> arrayList = new ArrayList();
        for (zzab com_google_android_gms_analytics_internal_zzab : list) {
            if (!com_google_android_gms_analytics_internal_zzah_zza.zzj(com_google_android_gms_analytics_internal_zzab)) {
                break;
            }
            arrayList.add(Long.valueOf(com_google_android_gms_analytics_internal_zzab.zzkj()));
        }
        if (com_google_android_gms_analytics_internal_zzah_zza.zzkx() == 0) {
            return arrayList;
        }
        URL zzkv = zzkv();
        if (zzkv == null) {
            zzbc("Failed to build batching endpoint url");
            return Collections.emptyList();
        }
        int zzb = z ? zzb(zzkv, com_google_android_gms_analytics_internal_zzah_zza.getPayload()) : zza(zzkv, com_google_android_gms_analytics_internal_zzah_zza.getPayload());
        if (200 == zzb) {
            zza("Batched upload completed. Hits batched", Integer.valueOf(com_google_android_gms_analytics_internal_zzah_zza.zzkx()));
            return arrayList;
        }
        zza("Network error uploading hits. status code", Integer.valueOf(zzb));
        if (zzif().zzjE().contains(Integer.valueOf(zzb))) {
            zzbb("Server instructed the client to stop batching");
            this.zzOr.start();
        }
        return Collections.emptyList();
    }

    HttpURLConnection zzc(URL url) throws IOException {
        URLConnection openConnection = url.openConnection();
        if (openConnection instanceof HttpURLConnection) {
            HttpURLConnection httpURLConnection = (HttpURLConnection) openConnection;
            httpURLConnection.setDefaultUseCaches(false);
            httpURLConnection.setConnectTimeout(zzif().zzjN());
            httpURLConnection.setReadTimeout(zzif().zzjO());
            httpURLConnection.setInstanceFollowRedirects(false);
            httpURLConnection.setRequestProperty("User-Agent", this.zzHj);
            httpURLConnection.setDoInput(true);
            return httpURLConnection;
        }
        throw new IOException("Failed to obtain http connection");
    }

    public List<Long> zzf(List<zzab> list) {
        boolean z;
        boolean z2 = true;
        zzic();
        zzio();
        zzx.zzv(list);
        if (zzif().zzjE().isEmpty() || !this.zzOr.zzv(zzif().zzjx() * 1000)) {
            z2 = false;
            z = false;
        } else {
            z = zzif().zzjC() != zzm.NONE;
            if (zzif().zzjD() != zzo.GZIP) {
                z2 = false;
            }
        }
        return z ? zza((List) list, z2) : zzg((List) list);
    }

    List<Long> zzg(List<zzab> list) {
        List<Long> arrayList = new ArrayList(list.size());
        for (zzab com_google_android_gms_analytics_internal_zzab : list) {
            if (!zzg(com_google_android_gms_analytics_internal_zzab)) {
                break;
            }
            arrayList.add(Long.valueOf(com_google_android_gms_analytics_internal_zzab.zzkj()));
            if (arrayList.size() >= zzif().zzjv()) {
                break;
            }
        }
        return arrayList;
    }

    protected void zzhB() {
        zza("Network initialized. User agent", this.zzHj);
    }

    public boolean zzku() {
        NetworkInfo activeNetworkInfo;
        zzic();
        zzio();
        try {
            activeNetworkInfo = ((ConnectivityManager) getContext().getSystemService("connectivity")).getActiveNetworkInfo();
        } catch (SecurityException e) {
            activeNetworkInfo = null;
        }
        if (activeNetworkInfo != null && activeNetworkInfo.isConnected()) {
            return true;
        }
        zzaY("No network connectivity");
        return false;
    }
}
