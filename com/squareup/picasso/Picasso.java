package com.squareup.picasso;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.Process;
import android.widget.ImageView;
import java.lang.ref.ReferenceQueue;
import java.util.List;
import java.util.Map;
import java.util.WeakHashMap;
import java.util.concurrent.ExecutorService;
import loquendo.tts.engine.TTSConst;

public class Picasso {
    static final Handler HANDLER;
    static Picasso singleton;
    final Cache cache;
    private final CleanupThread cleanupThread;
    final Context context;
    final Dispatcher dispatcher;
    boolean indicatorsEnabled;
    private final Listener listener;
    volatile boolean loggingEnabled;
    final ReferenceQueue<Object> referenceQueue;
    private final RequestTransformer requestTransformer;
    boolean shutdown;
    final Stats stats;
    final Map<Object, Action> targetToAction;
    final Map<ImageView, DeferredRequestCreator> targetToDeferredRequestCreator;

    /* renamed from: com.squareup.picasso.Picasso.1 */
    static class C10821 extends Handler {
        C10821(Looper x0) {
            super(x0);
        }

        public void handleMessage(Message msg) {
            switch (msg.what) {
                case TTSConst.TTSUNICODE /*3*/:
                    Action action = msg.obj;
                    action.picasso.cancelExistingRequest(action.getTarget());
                case TTSConst.TTSEVT_TAG /*8*/:
                    List<BitmapHunter> batch = msg.obj;
                    int n = batch.size();
                    for (int i = 0; i < n; i++) {
                        BitmapHunter hunter = (BitmapHunter) batch.get(i);
                        hunter.picasso.complete(hunter);
                    }
                default:
                    throw new AssertionError("Unknown handler message received: " + msg.what);
            }
        }
    }

    public static class Builder {
        private Cache cache;
        private final Context context;
        private Downloader downloader;
        private boolean indicatorsEnabled;
        private Listener listener;
        private boolean loggingEnabled;
        private ExecutorService service;
        private RequestTransformer transformer;

        public Builder(Context context) {
            if (context == null) {
                throw new IllegalArgumentException("Context must not be null.");
            }
            this.context = context.getApplicationContext();
        }

        public Picasso build() {
            Context context = this.context;
            if (this.downloader == null) {
                this.downloader = Utils.createDefaultDownloader(context);
            }
            if (this.cache == null) {
                this.cache = new LruCache(context);
            }
            if (this.service == null) {
                this.service = new PicassoExecutorService();
            }
            if (this.transformer == null) {
                this.transformer = RequestTransformer.IDENTITY;
            }
            Stats stats = new Stats(this.cache);
            Dispatcher dispatcher = new Dispatcher(context, this.service, Picasso.HANDLER, this.downloader, this.cache, stats);
            return new Picasso(context, dispatcher, this.cache, this.listener, this.transformer, stats, this.indicatorsEnabled, this.loggingEnabled);
        }
    }

    private static class CleanupThread extends Thread {
        private final Handler handler;
        private final ReferenceQueue<?> referenceQueue;

        /* renamed from: com.squareup.picasso.Picasso.CleanupThread.1 */
        class C10831 implements Runnable {
            final /* synthetic */ Exception val$e;

            C10831(Exception exception) {
                this.val$e = exception;
            }

            public void run() {
                throw new RuntimeException(this.val$e);
            }
        }

        CleanupThread(ReferenceQueue<?> referenceQueue, Handler handler) {
            this.referenceQueue = referenceQueue;
            this.handler = handler;
            setDaemon(true);
            setName("Picasso-refQueue");
        }

        public void run() {
            Process.setThreadPriority(10);
            while (true) {
                try {
                    this.handler.sendMessage(this.handler.obtainMessage(3, ((RequestWeakReference) this.referenceQueue.remove()).action));
                } catch (InterruptedException e) {
                    return;
                } catch (Exception e2) {
                    this.handler.post(new C10831(e2));
                    return;
                }
            }
        }
    }

    public interface Listener {
        void onImageLoadFailed(Picasso picasso, Uri uri, Exception exception);
    }

    public enum LoadedFrom {
        MEMORY(-16711936),
        DISK(-256),
        NETWORK(-65536);
        
        final int debugColor;

        private LoadedFrom(int debugColor) {
            this.debugColor = debugColor;
        }
    }

    public interface RequestTransformer {
        public static final RequestTransformer IDENTITY;

        /* renamed from: com.squareup.picasso.Picasso.RequestTransformer.1 */
        static class C10841 implements RequestTransformer {
            C10841() {
            }

            public Request transformRequest(Request request) {
                return request;
            }
        }

        Request transformRequest(Request request);

        static {
            IDENTITY = new C10841();
        }
    }

    static {
        HANDLER = new C10821(Looper.getMainLooper());
        singleton = null;
    }

    Picasso(Context context, Dispatcher dispatcher, Cache cache, Listener listener, RequestTransformer requestTransformer, Stats stats, boolean indicatorsEnabled, boolean loggingEnabled) {
        this.context = context;
        this.dispatcher = dispatcher;
        this.cache = cache;
        this.listener = listener;
        this.requestTransformer = requestTransformer;
        this.stats = stats;
        this.targetToAction = new WeakHashMap();
        this.targetToDeferredRequestCreator = new WeakHashMap();
        this.indicatorsEnabled = indicatorsEnabled;
        this.loggingEnabled = loggingEnabled;
        this.referenceQueue = new ReferenceQueue();
        this.cleanupThread = new CleanupThread(this.referenceQueue, HANDLER);
        this.cleanupThread.start();
    }

    public void cancelRequest(ImageView view) {
        cancelExistingRequest(view);
    }

    public RequestCreator load(Uri uri) {
        return new RequestCreator(this, uri, 0);
    }

    public RequestCreator load(String path) {
        if (path == null) {
            return new RequestCreator(this, null, 0);
        }
        if (path.trim().length() != 0) {
            return load(Uri.parse(path));
        }
        throw new IllegalArgumentException("Path must not be empty.");
    }

    Request transformRequest(Request request) {
        Request transformed = this.requestTransformer.transformRequest(request);
        if (transformed != null) {
            return transformed;
        }
        throw new IllegalStateException("Request transformer " + this.requestTransformer.getClass().getCanonicalName() + " returned null for " + request);
    }

    void defer(ImageView view, DeferredRequestCreator request) {
        this.targetToDeferredRequestCreator.put(view, request);
    }

    void enqueueAndSubmit(Action action) {
        Object target = action.getTarget();
        if (target != null) {
            cancelExistingRequest(target);
            this.targetToAction.put(target, action);
        }
        submit(action);
    }

    void submit(Action action) {
        this.dispatcher.dispatchSubmit(action);
    }

    Bitmap quickMemoryCacheCheck(String key) {
        Bitmap cached = this.cache.get(key);
        if (cached != null) {
            this.stats.dispatchCacheHit();
        } else {
            this.stats.dispatchCacheMiss();
        }
        return cached;
    }

    void complete(BitmapHunter hunter) {
        boolean hasMultiple;
        boolean shouldDeliver = false;
        Action single = hunter.getAction();
        List<Action> joined = hunter.getActions();
        if (joined == null || joined.isEmpty()) {
            hasMultiple = false;
        } else {
            hasMultiple = true;
        }
        if (single != null || hasMultiple) {
            shouldDeliver = true;
        }
        if (shouldDeliver) {
            Uri uri = hunter.getData().uri;
            Exception exception = hunter.getException();
            Bitmap result = hunter.getResult();
            LoadedFrom from = hunter.getLoadedFrom();
            if (single != null) {
                deliverAction(result, from, single);
            }
            if (hasMultiple) {
                int n = joined.size();
                for (int i = 0; i < n; i++) {
                    deliverAction(result, from, (Action) joined.get(i));
                }
            }
            if (this.listener != null && exception != null) {
                this.listener.onImageLoadFailed(this, uri, exception);
            }
        }
    }

    private void deliverAction(Bitmap result, LoadedFrom from, Action action) {
        if (!action.isCancelled()) {
            if (!action.willReplay()) {
                this.targetToAction.remove(action.getTarget());
            }
            if (result == null) {
                action.error();
                if (this.loggingEnabled) {
                    Utils.log("Main", "errored", action.request.logId());
                }
            } else if (from == null) {
                throw new AssertionError("LoadedFrom cannot be null.");
            } else {
                action.complete(result, from);
                if (this.loggingEnabled) {
                    Utils.log("Main", "completed", action.request.logId(), "from " + from);
                }
            }
        }
    }

    private void cancelExistingRequest(Object target) {
        Utils.checkMain();
        Action action = (Action) this.targetToAction.remove(target);
        if (action != null) {
            action.cancel();
            this.dispatcher.dispatchCancel(action);
        }
        if (target instanceof ImageView) {
            DeferredRequestCreator deferredRequestCreator = (DeferredRequestCreator) this.targetToDeferredRequestCreator.remove((ImageView) target);
            if (deferredRequestCreator != null) {
                deferredRequestCreator.cancel();
            }
        }
    }

    public static Picasso with(Context context) {
        if (singleton == null) {
            synchronized (Picasso.class) {
                if (singleton == null) {
                    singleton = new Builder(context).build();
                }
            }
        }
        return singleton;
    }
}
