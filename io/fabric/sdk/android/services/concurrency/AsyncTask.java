package io.fabric.sdk.android.services.concurrency;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.Process;
import android.util.Log;
import java.util.LinkedList;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.FutureTask;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import loquendo.tts.engine.TTSConst;

public abstract class AsyncTask<Params, Progress, Result> {
    private static final int CORE_POOL_SIZE;
    private static final int CPU_COUNT;
    private static final int MAXIMUM_POOL_SIZE;
    public static final Executor SERIAL_EXECUTOR;
    public static final Executor THREAD_POOL_EXECUTOR;
    private static volatile Executor defaultExecutor;
    private static final InternalHandler handler;
    private static final BlockingQueue<Runnable> poolWorkQueue;
    private static final ThreadFactory threadFactory;
    private final AtomicBoolean cancelled;
    private final FutureTask<Result> future;
    private volatile Status status;
    private final AtomicBoolean taskInvoked;
    private final WorkerRunnable<Params, Result> worker;

    /* renamed from: io.fabric.sdk.android.services.concurrency.AsyncTask.1 */
    static class C18201 implements ThreadFactory {
        private final AtomicInteger count;

        C18201() {
            this.count = new AtomicInteger(1);
        }

        public Thread newThread(Runnable r) {
            return new Thread(r, "AsyncTask #" + this.count.getAndIncrement());
        }
    }

    private static abstract class WorkerRunnable<Params, Result> implements Callable<Result> {
        Params[] params;

        private WorkerRunnable() {
        }
    }

    /* renamed from: io.fabric.sdk.android.services.concurrency.AsyncTask.2 */
    class C18212 extends WorkerRunnable<Params, Result> {
        C18212() {
            super();
        }

        public Result call() throws Exception {
            AsyncTask.this.taskInvoked.set(true);
            Process.setThreadPriority(10);
            return AsyncTask.this.postResult(AsyncTask.this.doInBackground(this.params));
        }
    }

    /* renamed from: io.fabric.sdk.android.services.concurrency.AsyncTask.3 */
    class C18223 extends FutureTask<Result> {
        C18223(Callable x0) {
            super(x0);
        }

        protected void done() {
            try {
                AsyncTask.this.postResultIfNotInvoked(get());
            } catch (InterruptedException e) {
                Log.w("AsyncTask", e);
            } catch (ExecutionException e2) {
                throw new RuntimeException("An error occured while executing doInBackground()", e2.getCause());
            } catch (CancellationException e3) {
                AsyncTask.this.postResultIfNotInvoked(null);
            }
        }
    }

    /* renamed from: io.fabric.sdk.android.services.concurrency.AsyncTask.4 */
    static /* synthetic */ class C18234 {
        static final /* synthetic */ int[] f1300x37f1d2cc;

        static {
            f1300x37f1d2cc = new int[Status.values().length];
            try {
                f1300x37f1d2cc[Status.RUNNING.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                f1300x37f1d2cc[Status.FINISHED.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
        }
    }

    private static class AsyncTaskResult<Data> {
        final Data[] data;
        final AsyncTask task;

        AsyncTaskResult(AsyncTask task, Data... data) {
            this.task = task;
            this.data = data;
        }
    }

    private static class InternalHandler extends Handler {
        public InternalHandler() {
            super(Looper.getMainLooper());
        }

        public void handleMessage(Message msg) {
            AsyncTaskResult result = msg.obj;
            switch (msg.what) {
                case TTSConst.TTSMULTILINE /*1*/:
                    result.task.finish(result.data[0]);
                case TTSConst.TTSPARAGRAPH /*2*/:
                    result.task.onProgressUpdate(result.data);
                default:
            }
        }
    }

    private static class SerialExecutor implements Executor {
        Runnable active;
        final LinkedList<Runnable> tasks;

        /* renamed from: io.fabric.sdk.android.services.concurrency.AsyncTask.SerialExecutor.1 */
        class C18241 implements Runnable {
            final /* synthetic */ Runnable val$r;

            C18241(Runnable runnable) {
                this.val$r = runnable;
            }

            public void run() {
                try {
                    this.val$r.run();
                } finally {
                    SerialExecutor.this.scheduleNext();
                }
            }
        }

        private SerialExecutor() {
            this.tasks = new LinkedList();
        }

        public synchronized void execute(Runnable r) {
            this.tasks.offer(new C18241(r));
            if (this.active == null) {
                scheduleNext();
            }
        }

        protected synchronized void scheduleNext() {
            Runnable runnable = (Runnable) this.tasks.poll();
            this.active = runnable;
            if (runnable != null) {
                AsyncTask.THREAD_POOL_EXECUTOR.execute(this.active);
            }
        }
    }

    public enum Status {
        PENDING,
        RUNNING,
        FINISHED
    }

    protected abstract Result doInBackground(Params... paramsArr);

    static {
        CPU_COUNT = Runtime.getRuntime().availableProcessors();
        CORE_POOL_SIZE = CPU_COUNT + 1;
        MAXIMUM_POOL_SIZE = (CPU_COUNT * 2) + 1;
        threadFactory = new C18201();
        poolWorkQueue = new LinkedBlockingQueue(128);
        THREAD_POOL_EXECUTOR = new ThreadPoolExecutor(CORE_POOL_SIZE, MAXIMUM_POOL_SIZE, 1, TimeUnit.SECONDS, poolWorkQueue, threadFactory);
        SERIAL_EXECUTOR = new SerialExecutor();
        handler = new InternalHandler();
        defaultExecutor = SERIAL_EXECUTOR;
    }

    public AsyncTask() {
        this.status = Status.PENDING;
        this.cancelled = new AtomicBoolean();
        this.taskInvoked = new AtomicBoolean();
        this.worker = new C18212();
        this.future = new C18223(this.worker);
    }

    private void postResultIfNotInvoked(Result result) {
        if (!this.taskInvoked.get()) {
            postResult(result);
        }
    }

    private Result postResult(Result result) {
        handler.obtainMessage(1, new AsyncTaskResult(this, result)).sendToTarget();
        return result;
    }

    public final Status getStatus() {
        return this.status;
    }

    protected void onPreExecute() {
    }

    protected void onPostExecute(Result result) {
    }

    protected void onProgressUpdate(Progress... progressArr) {
    }

    protected void onCancelled(Result result) {
        onCancelled();
    }

    protected void onCancelled() {
    }

    public final boolean isCancelled() {
        return this.cancelled.get();
    }

    public final boolean cancel(boolean mayInterruptIfRunning) {
        this.cancelled.set(true);
        return this.future.cancel(mayInterruptIfRunning);
    }

    public final AsyncTask<Params, Progress, Result> executeOnExecutor(Executor exec, Params... params) {
        if (this.status != Status.PENDING) {
            switch (C18234.f1300x37f1d2cc[this.status.ordinal()]) {
                case TTSConst.TTSMULTILINE /*1*/:
                    throw new IllegalStateException("Cannot execute task: the task is already running.");
                case TTSConst.TTSPARAGRAPH /*2*/:
                    throw new IllegalStateException("Cannot execute task: the task has already been executed (a task can be executed only once)");
            }
        }
        this.status = Status.RUNNING;
        onPreExecute();
        this.worker.params = params;
        exec.execute(this.future);
        return this;
    }

    private void finish(Result result) {
        if (isCancelled()) {
            onCancelled(result);
        } else {
            onPostExecute(result);
        }
        this.status = Status.FINISHED;
    }
}
