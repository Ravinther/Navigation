package com.squareup.picasso;

import android.graphics.Bitmap;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import loquendo.tts.engine.TTSConst;

class Stats {
    long averageDownloadSize;
    long averageOriginalBitmapSize;
    long averageTransformedBitmapSize;
    final Cache cache;
    long cacheHits;
    long cacheMisses;
    int downloadCount;
    final Handler handler;
    int originalBitmapCount;
    final HandlerThread statsThread;
    long totalDownloadSize;
    long totalOriginalBitmapSize;
    long totalTransformedBitmapSize;
    int transformedBitmapCount;

    private static class StatsHandler extends Handler {
        private final Stats stats;

        /* renamed from: com.squareup.picasso.Stats.StatsHandler.1 */
        class C10881 implements Runnable {
            final /* synthetic */ Message val$msg;

            C10881(Message message) {
                this.val$msg = message;
            }

            public void run() {
                throw new AssertionError("Unhandled stats message." + this.val$msg.what);
            }
        }

        public StatsHandler(Looper looper, Stats stats) {
            super(looper);
            this.stats = stats;
        }

        public void handleMessage(Message msg) {
            switch (msg.what) {
                case TTSConst.TTSEVT_AUDIOSTART /*0*/:
                    this.stats.performCacheHit();
                case TTSConst.TTSMULTILINE /*1*/:
                    this.stats.performCacheMiss();
                case TTSConst.TTSPARAGRAPH /*2*/:
                    this.stats.performBitmapDecoded((long) msg.arg1);
                case TTSConst.TTSUNICODE /*3*/:
                    this.stats.performBitmapTransformed((long) msg.arg1);
                case TTSConst.TTSXML /*4*/:
                    this.stats.performDownloadFinished((Long) msg.obj);
                default:
                    Picasso.HANDLER.post(new C10881(msg));
            }
        }
    }

    Stats(Cache cache) {
        this.cache = cache;
        this.statsThread = new HandlerThread("Picasso-Stats", 10);
        this.statsThread.start();
        this.handler = new StatsHandler(this.statsThread.getLooper(), this);
    }

    void dispatchBitmapDecoded(Bitmap bitmap) {
        processBitmap(bitmap, 2);
    }

    void dispatchBitmapTransformed(Bitmap bitmap) {
        processBitmap(bitmap, 3);
    }

    void dispatchDownloadFinished(long size) {
        this.handler.sendMessage(this.handler.obtainMessage(4, Long.valueOf(size)));
    }

    void dispatchCacheHit() {
        this.handler.sendEmptyMessage(0);
    }

    void dispatchCacheMiss() {
        this.handler.sendEmptyMessage(1);
    }

    void performCacheHit() {
        this.cacheHits++;
    }

    void performCacheMiss() {
        this.cacheMisses++;
    }

    void performDownloadFinished(Long size) {
        this.downloadCount++;
        this.totalDownloadSize += size.longValue();
        this.averageDownloadSize = getAverage(this.downloadCount, this.totalDownloadSize);
    }

    void performBitmapDecoded(long size) {
        this.originalBitmapCount++;
        this.totalOriginalBitmapSize += size;
        this.averageOriginalBitmapSize = getAverage(this.originalBitmapCount, this.totalOriginalBitmapSize);
    }

    void performBitmapTransformed(long size) {
        this.transformedBitmapCount++;
        this.totalTransformedBitmapSize += size;
        this.averageTransformedBitmapSize = getAverage(this.originalBitmapCount, this.totalTransformedBitmapSize);
    }

    StatsSnapshot createSnapshot() {
        return new StatsSnapshot(this.cache.maxSize(), this.cache.size(), this.cacheHits, this.cacheMisses, this.totalDownloadSize, this.totalOriginalBitmapSize, this.totalTransformedBitmapSize, this.averageDownloadSize, this.averageOriginalBitmapSize, this.averageTransformedBitmapSize, this.downloadCount, this.originalBitmapCount, this.transformedBitmapCount, System.currentTimeMillis());
    }

    private void processBitmap(Bitmap bitmap, int what) {
        this.handler.sendMessage(this.handler.obtainMessage(what, Utils.getBitmapBytes(bitmap), 0));
    }

    private static long getAverage(int count, long totalSize) {
        return totalSize / ((long) count);
    }
}
