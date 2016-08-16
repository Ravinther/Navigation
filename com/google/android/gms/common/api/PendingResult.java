package com.google.android.gms.common.api;

import java.util.concurrent.TimeUnit;

public interface PendingResult<R extends Result> {

    public interface BatchCallback {
        void onComplete(Status status);
    }

    R await(long j, TimeUnit timeUnit);

    void setResultCallback(ResultCallback<R> resultCallback);
}
