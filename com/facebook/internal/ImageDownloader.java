package com.facebook.internal;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Handler;
import android.os.Looper;
import com.facebook.internal.ImageRequest.Callback;
import com.facebook.internal.WorkQueue.WorkItem;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public class ImageDownloader {
    private static WorkQueue cacheReadQueue;
    private static WorkQueue downloadQueue;
    private static Handler handler;
    private static final Map<RequestKey, DownloaderContext> pendingRequests;

    /* renamed from: com.facebook.internal.ImageDownloader.1 */
    static class C03511 implements Runnable {
        final /* synthetic */ Bitmap val$bitmap;
        final /* synthetic */ Callback val$callback;
        final /* synthetic */ Exception val$error;
        final /* synthetic */ boolean val$isCachedRedirect;
        final /* synthetic */ ImageRequest val$request;

        C03511(ImageRequest imageRequest, Exception exception, boolean z, Bitmap bitmap, Callback callback) {
            this.val$request = imageRequest;
            this.val$error = exception;
            this.val$isCachedRedirect = z;
            this.val$bitmap = bitmap;
            this.val$callback = callback;
        }

        public void run() {
            this.val$callback.onCompleted(new ImageResponse(this.val$request, this.val$error, this.val$isCachedRedirect, this.val$bitmap));
        }
    }

    private static class CacheReadWorkItem implements Runnable {
        private boolean allowCachedRedirects;
        private Context context;
        private RequestKey key;

        CacheReadWorkItem(Context context, RequestKey key, boolean allowCachedRedirects) {
            this.context = context;
            this.key = key;
            this.allowCachedRedirects = allowCachedRedirects;
        }

        public void run() {
            ImageDownloader.readFromCache(this.key, this.context, this.allowCachedRedirects);
        }
    }

    private static class DownloadImageWorkItem implements Runnable {
        private Context context;
        private RequestKey key;

        DownloadImageWorkItem(Context context, RequestKey key) {
            this.context = context;
            this.key = key;
        }

        public void run() {
            ImageDownloader.download(this.key, this.context);
        }
    }

    private static class DownloaderContext {
        boolean isCancelled;
        ImageRequest request;
        WorkItem workItem;

        private DownloaderContext() {
        }
    }

    private static class RequestKey {
        Object tag;
        Uri uri;

        RequestKey(Uri url, Object tag) {
            this.uri = url;
            this.tag = tag;
        }

        public int hashCode() {
            return ((this.uri.hashCode() + 1073) * 37) + this.tag.hashCode();
        }

        public boolean equals(Object o) {
            if (o == null || !(o instanceof RequestKey)) {
                return false;
            }
            RequestKey compareTo = (RequestKey) o;
            return compareTo.uri == this.uri && compareTo.tag == this.tag;
        }
    }

    static {
        downloadQueue = new WorkQueue(8);
        cacheReadQueue = new WorkQueue(2);
        pendingRequests = new HashMap();
    }

    public static void downloadAsync(ImageRequest request) {
        if (request != null) {
            RequestKey key = new RequestKey(request.getImageUri(), request.getCallerTag());
            synchronized (pendingRequests) {
                DownloaderContext downloaderContext = (DownloaderContext) pendingRequests.get(key);
                if (downloaderContext != null) {
                    downloaderContext.request = request;
                    downloaderContext.isCancelled = false;
                    downloaderContext.workItem.moveToFront();
                } else {
                    enqueueCacheRead(request, key, request.isCachedRedirectAllowed());
                }
            }
        }
    }

    public static boolean cancelRequest(ImageRequest request) {
        boolean cancelled = false;
        RequestKey key = new RequestKey(request.getImageUri(), request.getCallerTag());
        synchronized (pendingRequests) {
            DownloaderContext downloaderContext = (DownloaderContext) pendingRequests.get(key);
            if (downloaderContext != null) {
                cancelled = true;
                if (downloaderContext.workItem.cancel()) {
                    pendingRequests.remove(key);
                } else {
                    downloaderContext.isCancelled = true;
                }
            }
        }
        return cancelled;
    }

    private static void enqueueCacheRead(ImageRequest request, RequestKey key, boolean allowCachedRedirects) {
        enqueueRequest(request, key, cacheReadQueue, new CacheReadWorkItem(request.getContext(), key, allowCachedRedirects));
    }

    private static void enqueueDownload(ImageRequest request, RequestKey key) {
        enqueueRequest(request, key, downloadQueue, new DownloadImageWorkItem(request.getContext(), key));
    }

    private static void enqueueRequest(ImageRequest request, RequestKey key, WorkQueue workQueue, Runnable workItem) {
        synchronized (pendingRequests) {
            DownloaderContext downloaderContext = new DownloaderContext();
            downloaderContext.request = request;
            pendingRequests.put(key, downloaderContext);
            downloaderContext.workItem = workQueue.addActiveWorkItem(workItem);
        }
    }

    private static void issueResponse(RequestKey key, Exception error, Bitmap bitmap, boolean isCachedRedirect) {
        DownloaderContext completedRequestContext = removePendingRequest(key);
        if (completedRequestContext != null && !completedRequestContext.isCancelled) {
            ImageRequest request = completedRequestContext.request;
            Callback callback = request.getCallback();
            if (callback != null) {
                getHandler().post(new C03511(request, error, isCachedRedirect, bitmap, callback));
            }
        }
    }

    private static void readFromCache(RequestKey key, Context context, boolean allowCachedRedirects) {
        InputStream cachedStream = null;
        boolean isCachedRedirect = false;
        if (allowCachedRedirects) {
            Uri redirectUri = UrlRedirectCache.getRedirectedUri(key.uri);
            if (redirectUri != null) {
                cachedStream = ImageResponseCache.getCachedImageStream(redirectUri, context);
                isCachedRedirect = cachedStream != null;
            }
        }
        if (!isCachedRedirect) {
            cachedStream = ImageResponseCache.getCachedImageStream(key.uri, context);
        }
        if (cachedStream != null) {
            Bitmap bitmap = BitmapFactory.decodeStream(cachedStream);
            Utility.closeQuietly(cachedStream);
            issueResponse(key, null, bitmap, isCachedRedirect);
            return;
        }
        DownloaderContext downloaderContext = removePendingRequest(key);
        if (downloaderContext != null && !downloaderContext.isCancelled) {
            enqueueDownload(downloaderContext.request, key);
        }
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static void download(com.facebook.internal.ImageDownloader.RequestKey r19, android.content.Context r20) {
        /*
        r5 = 0;
        r14 = 0;
        r8 = 0;
        r2 = 0;
        r10 = 1;
        r15 = new java.net.URL;	 Catch:{ IOException -> 0x0058, all -> 0x00e0 }
        r0 = r19;
        r0 = r0.uri;	 Catch:{ IOException -> 0x0058, all -> 0x00e0 }
        r16 = r0;
        r16 = r16.toString();	 Catch:{ IOException -> 0x0058, all -> 0x00e0 }
        r15.<init>(r16);	 Catch:{ IOException -> 0x0058, all -> 0x00e0 }
        r16 = r15.openConnection();	 Catch:{ IOException -> 0x0058, all -> 0x00e0 }
        r0 = r16;
        r0 = (java.net.HttpURLConnection) r0;	 Catch:{ IOException -> 0x0058, all -> 0x00e0 }
        r5 = r0;
        r16 = 0;
        r0 = r16;
        r5.setInstanceFollowRedirects(r0);	 Catch:{ IOException -> 0x0058, all -> 0x00e0 }
        r16 = r5.getResponseCode();	 Catch:{ IOException -> 0x0058, all -> 0x00e0 }
        switch(r16) {
            case 200: goto L_0x00b6;
            case 301: goto L_0x006c;
            case 302: goto L_0x006c;
            default: goto L_0x002b;
        };	 Catch:{ IOException -> 0x0058, all -> 0x00e0 }
    L_0x002b:
        r14 = r5.getErrorStream();	 Catch:{ IOException -> 0x0058, all -> 0x00e0 }
        r9 = new java.lang.StringBuilder;	 Catch:{ IOException -> 0x0058, all -> 0x00e0 }
        r9.<init>();	 Catch:{ IOException -> 0x0058, all -> 0x00e0 }
        if (r14 == 0) goto L_0x00d0;
    L_0x0036:
        r11 = new java.io.InputStreamReader;	 Catch:{ IOException -> 0x0058, all -> 0x00e0 }
        r11.<init>(r14);	 Catch:{ IOException -> 0x0058, all -> 0x00e0 }
        r16 = 128; // 0x80 float:1.794E-43 double:6.32E-322;
        r0 = r16;
        r3 = new char[r0];	 Catch:{ IOException -> 0x0058, all -> 0x00e0 }
    L_0x0041:
        r16 = 0;
        r0 = r3.length;	 Catch:{ IOException -> 0x0058, all -> 0x00e0 }
        r17 = r0;
        r0 = r16;
        r1 = r17;
        r4 = r11.read(r3, r0, r1);	 Catch:{ IOException -> 0x0058, all -> 0x00e0 }
        if (r4 <= 0) goto L_0x00c1;
    L_0x0050:
        r16 = 0;
        r0 = r16;
        r9.append(r3, r0, r4);	 Catch:{ IOException -> 0x0058, all -> 0x00e0 }
        goto L_0x0041;
    L_0x0058:
        r7 = move-exception;
        r8 = r7;
        com.facebook.internal.Utility.closeQuietly(r14);
        com.facebook.internal.Utility.disconnectQuietly(r5);
    L_0x0060:
        if (r10 == 0) goto L_0x006b;
    L_0x0062:
        r16 = 0;
        r0 = r19;
        r1 = r16;
        issueResponse(r0, r8, r2, r1);
    L_0x006b:
        return;
    L_0x006c:
        r10 = 0;
        r16 = "location";
        r0 = r16;
        r12 = r5.getHeaderField(r0);	 Catch:{ IOException -> 0x0058, all -> 0x00e0 }
        r16 = com.facebook.internal.Utility.isNullOrEmpty(r12);	 Catch:{ IOException -> 0x0058, all -> 0x00e0 }
        if (r16 != 0) goto L_0x00af;
    L_0x007c:
        r13 = android.net.Uri.parse(r12);	 Catch:{ IOException -> 0x0058, all -> 0x00e0 }
        r0 = r19;
        r0 = r0.uri;	 Catch:{ IOException -> 0x0058, all -> 0x00e0 }
        r16 = r0;
        r0 = r16;
        com.facebook.internal.UrlRedirectCache.cacheUriRedirect(r0, r13);	 Catch:{ IOException -> 0x0058, all -> 0x00e0 }
        r6 = removePendingRequest(r19);	 Catch:{ IOException -> 0x0058, all -> 0x00e0 }
        if (r6 == 0) goto L_0x00af;
    L_0x0091:
        r0 = r6.isCancelled;	 Catch:{ IOException -> 0x0058, all -> 0x00e0 }
        r16 = r0;
        if (r16 != 0) goto L_0x00af;
    L_0x0097:
        r0 = r6.request;	 Catch:{ IOException -> 0x0058, all -> 0x00e0 }
        r16 = r0;
        r17 = new com.facebook.internal.ImageDownloader$RequestKey;	 Catch:{ IOException -> 0x0058, all -> 0x00e0 }
        r0 = r19;
        r0 = r0.tag;	 Catch:{ IOException -> 0x0058, all -> 0x00e0 }
        r18 = r0;
        r0 = r17;
        r1 = r18;
        r0.<init>(r13, r1);	 Catch:{ IOException -> 0x0058, all -> 0x00e0 }
        r18 = 0;
        enqueueCacheRead(r16, r17, r18);	 Catch:{ IOException -> 0x0058, all -> 0x00e0 }
    L_0x00af:
        com.facebook.internal.Utility.closeQuietly(r14);
        com.facebook.internal.Utility.disconnectQuietly(r5);
        goto L_0x0060;
    L_0x00b6:
        r0 = r20;
        r14 = com.facebook.internal.ImageResponseCache.interceptAndCacheImageStream(r0, r5);	 Catch:{ IOException -> 0x0058, all -> 0x00e0 }
        r2 = android.graphics.BitmapFactory.decodeStream(r14);	 Catch:{ IOException -> 0x0058, all -> 0x00e0 }
        goto L_0x00af;
    L_0x00c1:
        com.facebook.internal.Utility.closeQuietly(r11);	 Catch:{ IOException -> 0x0058, all -> 0x00e0 }
    L_0x00c4:
        r8 = new com.facebook.FacebookException;	 Catch:{ IOException -> 0x0058, all -> 0x00e0 }
        r16 = r9.toString();	 Catch:{ IOException -> 0x0058, all -> 0x00e0 }
        r0 = r16;
        r8.<init>(r0);	 Catch:{ IOException -> 0x0058, all -> 0x00e0 }
        goto L_0x00af;
    L_0x00d0:
        r16 = com.facebook.C0322R.string.com_facebook_image_download_unknown_error;	 Catch:{ IOException -> 0x0058, all -> 0x00e0 }
        r0 = r20;
        r1 = r16;
        r16 = r0.getString(r1);	 Catch:{ IOException -> 0x0058, all -> 0x00e0 }
        r0 = r16;
        r9.append(r0);	 Catch:{ IOException -> 0x0058, all -> 0x00e0 }
        goto L_0x00c4;
    L_0x00e0:
        r16 = move-exception;
        com.facebook.internal.Utility.closeQuietly(r14);
        com.facebook.internal.Utility.disconnectQuietly(r5);
        throw r16;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.facebook.internal.ImageDownloader.download(com.facebook.internal.ImageDownloader$RequestKey, android.content.Context):void");
    }

    private static synchronized Handler getHandler() {
        Handler handler;
        synchronized (ImageDownloader.class) {
            if (handler == null) {
                handler = new Handler(Looper.getMainLooper());
            }
            handler = handler;
        }
        return handler;
    }

    private static DownloaderContext removePendingRequest(RequestKey key) {
        DownloaderContext downloaderContext;
        synchronized (pendingRequests) {
            downloaderContext = (DownloaderContext) pendingRequests.remove(key);
        }
        return downloaderContext;
    }
}
