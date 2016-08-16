package com.facebook.internal;

import android.net.Uri;
import com.facebook.internal.FileLruCache.Limits;
import java.io.IOException;
import java.io.OutputStream;

class UrlRedirectCache {
    private static final String REDIRECT_CONTENT_TAG;
    static final String TAG;
    private static volatile FileLruCache urlRedirectCache;

    UrlRedirectCache() {
    }

    static {
        TAG = UrlRedirectCache.class.getSimpleName();
        REDIRECT_CONTENT_TAG = TAG + "_Redirect";
    }

    static synchronized FileLruCache getCache() throws IOException {
        FileLruCache fileLruCache;
        synchronized (UrlRedirectCache.class) {
            if (urlRedirectCache == null) {
                urlRedirectCache = new FileLruCache(TAG, new Limits());
            }
            fileLruCache = urlRedirectCache;
        }
        return fileLruCache;
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    static android.net.Uri getRedirectedUri(android.net.Uri r12) {
        /*
        r9 = 0;
        if (r12 != 0) goto L_0x0004;
    L_0x0003:
        return r9;
    L_0x0004:
        r7 = r12.toString();
        r3 = 0;
        r2 = getCache();	 Catch:{ IOException -> 0x0033, all -> 0x0050 }
        r5 = 0;
        r4 = r3;
    L_0x000f:
        r10 = REDIRECT_CONTENT_TAG;	 Catch:{ IOException -> 0x0058, all -> 0x0055 }
        r6 = r2.get(r7, r10);	 Catch:{ IOException -> 0x0058, all -> 0x0055 }
        if (r6 == 0) goto L_0x0041;
    L_0x0017:
        r5 = 1;
        r3 = new java.io.InputStreamReader;	 Catch:{ IOException -> 0x0058, all -> 0x0055 }
        r3.<init>(r6);	 Catch:{ IOException -> 0x0058, all -> 0x0055 }
        r10 = 128; // 0x80 float:1.794E-43 double:6.32E-322;
        r0 = new char[r10];	 Catch:{ IOException -> 0x0033, all -> 0x0050 }
        r8 = new java.lang.StringBuilder;	 Catch:{ IOException -> 0x0033, all -> 0x0050 }
        r8.<init>();	 Catch:{ IOException -> 0x0033, all -> 0x0050 }
    L_0x0026:
        r10 = 0;
        r11 = r0.length;	 Catch:{ IOException -> 0x0033, all -> 0x0050 }
        r1 = r3.read(r0, r10, r11);	 Catch:{ IOException -> 0x0033, all -> 0x0050 }
        if (r1 <= 0) goto L_0x0038;
    L_0x002e:
        r10 = 0;
        r8.append(r0, r10, r1);	 Catch:{ IOException -> 0x0033, all -> 0x0050 }
        goto L_0x0026;
    L_0x0033:
        r10 = move-exception;
    L_0x0034:
        com.facebook.internal.Utility.closeQuietly(r3);
        goto L_0x0003;
    L_0x0038:
        com.facebook.internal.Utility.closeQuietly(r3);	 Catch:{ IOException -> 0x0033, all -> 0x0050 }
        r7 = r8.toString();	 Catch:{ IOException -> 0x0033, all -> 0x0050 }
        r4 = r3;
        goto L_0x000f;
    L_0x0041:
        if (r5 == 0) goto L_0x004b;
    L_0x0043:
        r9 = android.net.Uri.parse(r7);	 Catch:{ IOException -> 0x0058, all -> 0x0055 }
        com.facebook.internal.Utility.closeQuietly(r4);
        goto L_0x0003;
    L_0x004b:
        com.facebook.internal.Utility.closeQuietly(r4);
        r3 = r4;
        goto L_0x0003;
    L_0x0050:
        r9 = move-exception;
    L_0x0051:
        com.facebook.internal.Utility.closeQuietly(r3);
        throw r9;
    L_0x0055:
        r9 = move-exception;
        r3 = r4;
        goto L_0x0051;
    L_0x0058:
        r10 = move-exception;
        r3 = r4;
        goto L_0x0034;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.facebook.internal.UrlRedirectCache.getRedirectedUri(android.net.Uri):android.net.Uri");
    }

    static void cacheUriRedirect(Uri fromUri, Uri toUri) {
        if (fromUri != null && toUri != null) {
            OutputStream redirectStream = null;
            try {
                redirectStream = getCache().openPutStream(fromUri.toString(), REDIRECT_CONTENT_TAG);
                redirectStream.write(toUri.toString().getBytes());
            } catch (IOException e) {
            } finally {
                Utility.closeQuietly(redirectStream);
            }
        }
    }
}
