package com.sygic.aura.helper;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.os.AsyncTask;
import android.os.Build.VERSION;
import java.util.concurrent.Executor;

public class AsyncTaskHelper {
    public static <P, T extends AsyncTask<P, ?, ?>> AsyncTask<P, ?, ?> execute(T task) {
        return execute((AsyncTask) task, (Object[]) null);
    }

    @SuppressLint({"NewApi"})
    public static <P, T extends AsyncTask<P, ?, ?>> AsyncTask<P, ?, ?> execute(T task, P... params) {
        if (VERSION.SDK_INT >= 11) {
            return execute(AsyncTask.THREAD_POOL_EXECUTOR, task, params);
        }
        return task.execute(params);
    }

    @TargetApi(11)
    public static <P, T extends AsyncTask<P, ?, ?>> AsyncTask<P, ?, ?> execute(Executor executor, T task) {
        return execute(executor, task, (Object[]) null);
    }

    @TargetApi(11)
    public static <P, T extends AsyncTask<P, ?, ?>> AsyncTask<P, ?, ?> execute(Executor executor, T task, P... params) {
        return task.executeOnExecutor(executor, params);
    }
}
