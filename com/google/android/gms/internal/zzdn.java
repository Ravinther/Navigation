package com.google.android.gms.internal;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningAppProcessInfo;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.text.TextUtils;
import com.google.android.gms.ads.internal.overlay.AdLauncherIntentInfoParcel;
import com.google.android.gms.ads.internal.zze;
import com.google.android.gms.ads.internal.zzp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

@zzgk
public final class zzdn implements zzdg {
    private final zze zzxj;
    private final zzew zzxk;
    private final zzdi zzxm;

    public static class zza extends zzhq {
        private final String zzF;
        private final zzip zzoL;
        private final String zzxn;
        private final String zzxo;
        private final int zzxp;

        public zza(zzip com_google_android_gms_internal_zzip, String str) {
            this.zzxn = "play.google.com";
            this.zzxo = "market";
            this.zzxp = 10;
            this.zzoL = com_google_android_gms_internal_zzip;
            this.zzF = str;
        }

        public void onStop() {
        }

        public Intent zzY(String str) {
            Uri parse = Uri.parse(str);
            Intent intent = new Intent("android.intent.action.VIEW");
            intent.addFlags(268435456);
            intent.setData(parse);
            return intent;
        }

        /* JADX WARNING: inconsistent code. */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public void zzdG() {
            /*
            r8 = this;
            r0 = 0;
            r2 = r8.zzF;
        L_0x0003:
            r1 = 10;
            if (r0 >= r1) goto L_0x0120;
        L_0x0007:
            r4 = r0 + 1;
            r0 = new java.net.URL;	 Catch:{ IndexOutOfBoundsException -> 0x00ba, IOException -> 0x00d6, RuntimeException -> 0x00f2 }
            r0.<init>(r2);	 Catch:{ IndexOutOfBoundsException -> 0x00ba, IOException -> 0x00d6, RuntimeException -> 0x00f2 }
            r1 = "play.google.com";
            r3 = r0.getHost();	 Catch:{ IndexOutOfBoundsException -> 0x00ba, IOException -> 0x00d6, RuntimeException -> 0x00f2 }
            r1 = r1.equalsIgnoreCase(r3);	 Catch:{ IndexOutOfBoundsException -> 0x00ba, IOException -> 0x00d6, RuntimeException -> 0x00f2 }
            if (r1 == 0) goto L_0x002a;
        L_0x001b:
            r0 = r2;
        L_0x001c:
            r0 = r8.zzY(r0);
            r1 = r8.zzoL;
            r1 = r1.getContext();
            r1.startActivity(r0);
            return;
        L_0x002a:
            r1 = "market";
            r3 = r0.getProtocol();	 Catch:{ IndexOutOfBoundsException -> 0x00ba, IOException -> 0x00d6, RuntimeException -> 0x00f2 }
            r1 = r1.equalsIgnoreCase(r3);	 Catch:{ IndexOutOfBoundsException -> 0x00ba, IOException -> 0x00d6, RuntimeException -> 0x00f2 }
            if (r1 == 0) goto L_0x0039;
        L_0x0037:
            r0 = r2;
            goto L_0x001c;
        L_0x0039:
            r0 = r0.openConnection();	 Catch:{ IndexOutOfBoundsException -> 0x00ba, IOException -> 0x00d6, RuntimeException -> 0x00f2 }
            r0 = (java.net.HttpURLConnection) r0;	 Catch:{ IndexOutOfBoundsException -> 0x00ba, IOException -> 0x00d6, RuntimeException -> 0x00f2 }
            r1 = com.google.android.gms.ads.internal.zzp.zzbx();	 Catch:{ all -> 0x00b5 }
            r3 = r8.zzoL;	 Catch:{ all -> 0x00b5 }
            r3 = r3.getContext();	 Catch:{ all -> 0x00b5 }
            r5 = r8.zzoL;	 Catch:{ all -> 0x00b5 }
            r5 = r5.zzgV();	 Catch:{ all -> 0x00b5 }
            r5 = r5.zzIz;	 Catch:{ all -> 0x00b5 }
            r6 = 0;
            r1.zza(r3, r5, r6, r0);	 Catch:{ all -> 0x00b5 }
            r1 = r0.getResponseCode();	 Catch:{ all -> 0x00b5 }
            r5 = r0.getHeaderFields();	 Catch:{ all -> 0x00b5 }
            r3 = "";
            r6 = 300; // 0x12c float:4.2E-43 double:1.48E-321;
            if (r1 < r6) goto L_0x011d;
        L_0x0064:
            r6 = 399; // 0x18f float:5.59E-43 double:1.97E-321;
            if (r1 > r6) goto L_0x011d;
        L_0x0068:
            r1 = 0;
            r6 = "Location";
            r6 = r5.containsKey(r6);	 Catch:{ all -> 0x00b5 }
            if (r6 == 0) goto L_0x009b;
        L_0x0072:
            r1 = "Location";
            r1 = r5.get(r1);	 Catch:{ all -> 0x00b5 }
            r1 = (java.util.List) r1;	 Catch:{ all -> 0x00b5 }
        L_0x007b:
            if (r1 == 0) goto L_0x011d;
        L_0x007d:
            r5 = r1.size();	 Catch:{ all -> 0x00b5 }
            if (r5 <= 0) goto L_0x011d;
        L_0x0083:
            r3 = 0;
            r1 = r1.get(r3);	 Catch:{ all -> 0x00b5 }
            r1 = (java.lang.String) r1;	 Catch:{ all -> 0x00b5 }
        L_0x008a:
            r3 = android.text.TextUtils.isEmpty(r1);	 Catch:{ all -> 0x00b5 }
            if (r3 == 0) goto L_0x00ae;
        L_0x0090:
            r1 = "Arrived at landing page, this ideally should not happen. Will open it in browser.";
            com.google.android.gms.ads.internal.util.client.zzb.zzaE(r1);	 Catch:{ all -> 0x00b5 }
            r0.disconnect();	 Catch:{ IndexOutOfBoundsException -> 0x00ba, IOException -> 0x00d6, RuntimeException -> 0x00f2 }
            r0 = r2;
            goto L_0x001c;
        L_0x009b:
            r6 = "location";
            r6 = r5.containsKey(r6);	 Catch:{ all -> 0x00b5 }
            if (r6 == 0) goto L_0x007b;
        L_0x00a4:
            r1 = "location";
            r1 = r5.get(r1);	 Catch:{ all -> 0x00b5 }
            r1 = (java.util.List) r1;	 Catch:{ all -> 0x00b5 }
            goto L_0x007b;
        L_0x00ae:
            r0.disconnect();	 Catch:{ IndexOutOfBoundsException -> 0x0118, IOException -> 0x0113, RuntimeException -> 0x010e }
            r0 = r4;
            r2 = r1;
            goto L_0x0003;
        L_0x00b5:
            r1 = move-exception;
            r0.disconnect();	 Catch:{ IndexOutOfBoundsException -> 0x00ba, IOException -> 0x00d6, RuntimeException -> 0x00f2 }
            throw r1;	 Catch:{ IndexOutOfBoundsException -> 0x00ba, IOException -> 0x00d6, RuntimeException -> 0x00f2 }
        L_0x00ba:
            r0 = move-exception;
            r1 = r0;
            r0 = r2;
        L_0x00bd:
            r2 = new java.lang.StringBuilder;
            r2.<init>();
            r3 = "Error while parsing ping URL: ";
            r2 = r2.append(r3);
            r2 = r2.append(r0);
            r2 = r2.toString();
            com.google.android.gms.ads.internal.util.client.zzb.zzd(r2, r1);
            goto L_0x001c;
        L_0x00d6:
            r0 = move-exception;
            r1 = r0;
            r0 = r2;
        L_0x00d9:
            r2 = new java.lang.StringBuilder;
            r2.<init>();
            r3 = "Error while pinging URL: ";
            r2 = r2.append(r3);
            r2 = r2.append(r0);
            r2 = r2.toString();
            com.google.android.gms.ads.internal.util.client.zzb.zzd(r2, r1);
            goto L_0x001c;
        L_0x00f2:
            r0 = move-exception;
            r1 = r0;
            r0 = r2;
        L_0x00f5:
            r2 = new java.lang.StringBuilder;
            r2.<init>();
            r3 = "Error while pinging URL: ";
            r2 = r2.append(r3);
            r2 = r2.append(r0);
            r2 = r2.toString();
            com.google.android.gms.ads.internal.util.client.zzb.zzd(r2, r1);
            goto L_0x001c;
        L_0x010e:
            r0 = move-exception;
            r7 = r0;
            r0 = r1;
            r1 = r7;
            goto L_0x00f5;
        L_0x0113:
            r0 = move-exception;
            r7 = r0;
            r0 = r1;
            r1 = r7;
            goto L_0x00d9;
        L_0x0118:
            r0 = move-exception;
            r7 = r0;
            r0 = r1;
            r1 = r7;
            goto L_0x00bd;
        L_0x011d:
            r1 = r3;
            goto L_0x008a;
        L_0x0120:
            r0 = r2;
            goto L_0x001c;
            */
            throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.zzdn.zza.zzdG():void");
        }
    }

    public static class zzb {
        public Intent zza(Intent intent, ResolveInfo resolveInfo) {
            Intent intent2 = new Intent(intent);
            intent2.setClassName(resolveInfo.activityInfo.packageName, resolveInfo.activityInfo.name);
            return intent2;
        }

        public ResolveInfo zza(Context context, Intent intent) {
            return zza(context, intent, new ArrayList());
        }

        public ResolveInfo zza(Context context, Intent intent, ArrayList<ResolveInfo> arrayList) {
            PackageManager packageManager = context.getPackageManager();
            if (packageManager == null) {
                return null;
            }
            ResolveInfo resolveInfo;
            Collection queryIntentActivities = packageManager.queryIntentActivities(intent, 65536);
            ResolveInfo resolveActivity = packageManager.resolveActivity(intent, 65536);
            if (!(queryIntentActivities == null || resolveActivity == null)) {
                for (int i = 0; i < queryIntentActivities.size(); i++) {
                    resolveInfo = (ResolveInfo) queryIntentActivities.get(i);
                    if (resolveActivity != null && resolveActivity.activityInfo.name.equals(resolveInfo.activityInfo.name)) {
                        resolveInfo = resolveActivity;
                        break;
                    }
                }
            }
            resolveInfo = null;
            arrayList.addAll(queryIntentActivities);
            return resolveInfo;
        }

        public Intent zzb(Context context, Map<String, String> map) {
            ActivityManager activityManager = (ActivityManager) context.getSystemService("activity");
            String str = (String) map.get("u");
            if (TextUtils.isEmpty(str)) {
                return null;
            }
            Uri parse = Uri.parse(str);
            boolean parseBoolean = Boolean.parseBoolean((String) map.get("use_first_package"));
            boolean parseBoolean2 = Boolean.parseBoolean((String) map.get("use_running_process"));
            Uri build = "http".equalsIgnoreCase(parse.getScheme()) ? parse.buildUpon().scheme("https").build() : "https".equalsIgnoreCase(parse.getScheme()) ? parse.buildUpon().scheme("http").build() : null;
            ArrayList arrayList = new ArrayList();
            Intent zzd = zzd(parse);
            Intent zzd2 = zzd(build);
            ResolveInfo zza = zza(context, zzd, arrayList);
            if (zza != null) {
                return zza(zzd, zza);
            }
            if (zzd2 != null) {
                ResolveInfo zza2 = zza(context, zzd2);
                if (zza2 != null) {
                    Intent zza3 = zza(zzd, zza2);
                    if (zza(context, zza3) != null) {
                        return zza3;
                    }
                }
            }
            if (arrayList.size() == 0) {
                return zzd;
            }
            if (parseBoolean2 && activityManager != null) {
                List<RunningAppProcessInfo> runningAppProcesses = activityManager.getRunningAppProcesses();
                if (runningAppProcesses != null) {
                    Iterator it = arrayList.iterator();
                    while (it.hasNext()) {
                        ResolveInfo resolveInfo = (ResolveInfo) it.next();
                        for (RunningAppProcessInfo runningAppProcessInfo : runningAppProcesses) {
                            if (runningAppProcessInfo.processName.equals(resolveInfo.activityInfo.packageName)) {
                                return zza(zzd, resolveInfo);
                            }
                        }
                    }
                }
            }
            return parseBoolean ? zza(zzd, (ResolveInfo) arrayList.get(0)) : zzd;
        }

        public Intent zzd(Uri uri) {
            if (uri == null) {
                return null;
            }
            Intent intent = new Intent("android.intent.action.VIEW");
            intent.addFlags(268435456);
            intent.setData(uri);
            intent.setAction("android.intent.action.VIEW");
            return intent;
        }
    }

    public zzdn(zzdi com_google_android_gms_internal_zzdi, zze com_google_android_gms_ads_internal_zze, zzew com_google_android_gms_internal_zzew) {
        this.zzxm = com_google_android_gms_internal_zzdi;
        this.zzxj = com_google_android_gms_ads_internal_zze;
        this.zzxk = com_google_android_gms_internal_zzew;
    }

    private static void zza(Context context, Map<String, String> map) {
        if (TextUtils.isEmpty((String) map.get("u"))) {
            com.google.android.gms.ads.internal.util.client.zzb.zzaE("Destination url cannot be empty.");
            return;
        }
        try {
            context.startActivity(new zzb().zzb(context, map));
        } catch (ActivityNotFoundException e) {
            com.google.android.gms.ads.internal.util.client.zzb.zzaE(e.getMessage());
        }
    }

    private static void zzb(zzip com_google_android_gms_internal_zzip, Map<String, String> map) {
        String str = (String) map.get("u");
        if (TextUtils.isEmpty(str)) {
            com.google.android.gms.ads.internal.util.client.zzb.zzaE("Destination url cannot be empty.");
        } else {
            new zza(com_google_android_gms_internal_zzip, str).zzgn();
        }
    }

    private static boolean zzc(Map<String, String> map) {
        return "1".equals(map.get("custom_close"));
    }

    private static int zzd(Map<String, String> map) {
        String str = (String) map.get("o");
        if (str != null) {
            if ("p".equalsIgnoreCase(str)) {
                return zzp.zzbz().zzgw();
            }
            if ("l".equalsIgnoreCase(str)) {
                return zzp.zzbz().zzgv();
            }
            if ("c".equalsIgnoreCase(str)) {
                return zzp.zzbz().zzgx();
            }
        }
        return -1;
    }

    private void zzm(boolean z) {
        if (this.zzxk != null) {
            this.zzxk.zzn(z);
        }
    }

    public void zza(zzip com_google_android_gms_internal_zzip, Map<String, String> map) {
        String str = (String) map.get("a");
        if (str == null) {
            com.google.android.gms.ads.internal.util.client.zzb.zzaE("Action missing from an open GMSG.");
        } else if (this.zzxj == null || this.zzxj.zzbe()) {
            zziq zzgS = com_google_android_gms_internal_zzip.zzgS();
            if ("expand".equalsIgnoreCase(str)) {
                if (com_google_android_gms_internal_zzip.zzgW()) {
                    com.google.android.gms.ads.internal.util.client.zzb.zzaE("Cannot expand WebView that is already expanded.");
                    return;
                }
                zzm(false);
                zzgS.zza(zzc(map), zzd(map));
            } else if ("webapp".equalsIgnoreCase(str)) {
                str = (String) map.get("u");
                zzm(false);
                if (str != null) {
                    zzgS.zza(zzc(map), zzd(map), str);
                } else {
                    zzgS.zza(zzc(map), zzd(map), (String) map.get("html"), (String) map.get("baseurl"));
                }
            } else if ("in_app_purchase".equalsIgnoreCase(str)) {
                str = (String) map.get("product_id");
                String str2 = (String) map.get("report_urls");
                if (this.zzxm == null) {
                    return;
                }
                if (str2 == null || str2.isEmpty()) {
                    this.zzxm.zza(str, new ArrayList());
                    return;
                }
                this.zzxm.zza(str, new ArrayList(Arrays.asList(str2.split(" "))));
            } else if ("app".equalsIgnoreCase(str) && "true".equalsIgnoreCase((String) map.get("play_store"))) {
                zzb(com_google_android_gms_internal_zzip, map);
            } else if ("app".equalsIgnoreCase(str) && "true".equalsIgnoreCase((String) map.get("system_browser"))) {
                zza(com_google_android_gms_internal_zzip.getContext(), (Map) map);
            } else {
                String str3;
                zzm(true);
                zzan zzgU = com_google_android_gms_internal_zzip.zzgU();
                str = (String) map.get("u");
                if (TextUtils.isEmpty(str)) {
                    str3 = str;
                } else {
                    if (zzgU != null && zzgU.zzb(Uri.parse(str))) {
                        str = zzp.zzbx().zzd(com_google_android_gms_internal_zzip.getContext(), str, com_google_android_gms_internal_zzip.zzha());
                    }
                    str3 = zzp.zzbx().zza(com_google_android_gms_internal_zzip, str);
                }
                zzgS.zza(new AdLauncherIntentInfoParcel((String) map.get("i"), str3, (String) map.get("m"), (String) map.get("p"), (String) map.get("c"), (String) map.get("f"), (String) map.get("e")));
            }
        } else {
            this.zzxj.zzp((String) map.get("u"));
        }
    }
}
