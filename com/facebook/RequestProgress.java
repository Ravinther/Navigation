package com.facebook;

import android.os.Handler;
import com.facebook.GraphRequest.Callback;
import com.facebook.GraphRequest.OnProgressCallback;

class RequestProgress {
    private final Handler callbackHandler;
    private long lastReportedProgress;
    private long maxProgress;
    private long progress;
    private final GraphRequest request;
    private final long threshold;

    /* renamed from: com.facebook.RequestProgress.1 */
    class C03231 implements Runnable {
        final /* synthetic */ OnProgressCallback val$callbackCopy;
        final /* synthetic */ long val$currentCopy;
        final /* synthetic */ long val$maxProgressCopy;

        C03231(OnProgressCallback onProgressCallback, long j, long j2) {
            this.val$callbackCopy = onProgressCallback;
            this.val$currentCopy = j;
            this.val$maxProgressCopy = j2;
        }

        public void run() {
            this.val$callbackCopy.onProgress(this.val$currentCopy, this.val$maxProgressCopy);
        }
    }

    RequestProgress(Handler callbackHandler, GraphRequest request) {
        this.request = request;
        this.callbackHandler = callbackHandler;
        this.threshold = FacebookSdk.getOnProgressThreshold();
    }

    void addProgress(long size) {
        this.progress += size;
        if (this.progress >= this.lastReportedProgress + this.threshold || this.progress >= this.maxProgress) {
            reportProgress();
        }
    }

    void addToMax(long size) {
        this.maxProgress += size;
    }

    void reportProgress() {
        if (this.progress > this.lastReportedProgress) {
            Callback callback = this.request.getCallback();
            if (this.maxProgress > 0 && (callback instanceof OnProgressCallback)) {
                long currentCopy = this.progress;
                long maxProgressCopy = this.maxProgress;
                OnProgressCallback callbackCopy = (OnProgressCallback) callback;
                if (this.callbackHandler == null) {
                    callbackCopy.onProgress(currentCopy, maxProgressCopy);
                } else {
                    this.callbackHandler.post(new C03231(callbackCopy, currentCopy, maxProgressCopy));
                }
                this.lastReportedProgress = this.progress;
            }
        }
    }
}
