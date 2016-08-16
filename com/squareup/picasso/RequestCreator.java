package com.squareup.picasso;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.widget.ImageView;
import com.squareup.picasso.Picasso.LoadedFrom;
import com.squareup.picasso.Request.Builder;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;

public class RequestCreator {
    private static int nextId;
    private final Builder data;
    private boolean deferred;
    private Drawable errorDrawable;
    private int errorResId;
    private boolean noFade;
    private final Picasso picasso;
    private Drawable placeholderDrawable;
    private int placeholderResId;
    private boolean skipMemoryCache;

    /* renamed from: com.squareup.picasso.RequestCreator.1 */
    static class C10861 implements Runnable {
        final /* synthetic */ AtomicInteger val$id;
        final /* synthetic */ CountDownLatch val$latch;

        C10861(AtomicInteger atomicInteger, CountDownLatch countDownLatch) {
            this.val$id = atomicInteger;
            this.val$latch = countDownLatch;
        }

        public void run() {
            this.val$id.set(RequestCreator.getRequestId());
            this.val$latch.countDown();
        }
    }

    /* renamed from: com.squareup.picasso.RequestCreator.2 */
    static class C10872 implements Runnable {
        final /* synthetic */ InterruptedException val$e;

        C10872(InterruptedException interruptedException) {
            this.val$e = interruptedException;
        }

        public void run() {
            throw new RuntimeException(this.val$e);
        }
    }

    static {
        nextId = 0;
    }

    private static int getRequestId() {
        if (Utils.isMain()) {
            int i = nextId;
            nextId = i + 1;
            return i;
        }
        CountDownLatch latch = new CountDownLatch(1);
        AtomicInteger id = new AtomicInteger();
        Picasso.HANDLER.post(new C10861(id, latch));
        try {
            latch.await();
        } catch (InterruptedException e) {
            Picasso.HANDLER.post(new C10872(e));
        }
        return id.get();
    }

    RequestCreator(Picasso picasso, Uri uri, int resourceId) {
        if (picasso.shutdown) {
            throw new IllegalStateException("Picasso instance already shut down. Cannot submit new requests.");
        }
        this.picasso = picasso;
        this.data = new Builder(uri, resourceId);
    }

    RequestCreator() {
        this.picasso = null;
        this.data = new Builder(null, 0);
    }

    public RequestCreator placeholder(int placeholderResId) {
        if (placeholderResId == 0) {
            throw new IllegalArgumentException("Placeholder image resource invalid.");
        } else if (this.placeholderDrawable != null) {
            throw new IllegalStateException("Placeholder image already set.");
        } else {
            this.placeholderResId = placeholderResId;
            return this;
        }
    }

    public RequestCreator error(int errorResId) {
        if (errorResId == 0) {
            throw new IllegalArgumentException("Error image resource invalid.");
        } else if (this.errorDrawable != null) {
            throw new IllegalStateException("Error image already set.");
        } else {
            this.errorResId = errorResId;
            return this;
        }
    }

    public RequestCreator error(Drawable errorDrawable) {
        if (errorDrawable == null) {
            throw new IllegalArgumentException("Error image may not be null.");
        } else if (this.errorResId != 0) {
            throw new IllegalStateException("Error image already set.");
        } else {
            this.errorDrawable = errorDrawable;
            return this;
        }
    }

    public RequestCreator fit() {
        this.deferred = true;
        return this;
    }

    RequestCreator unfit() {
        this.deferred = false;
        return this;
    }

    public RequestCreator resizeDimen(int targetWidthResId, int targetHeightResId) {
        Resources resources = this.picasso.context.getResources();
        return resize(resources.getDimensionPixelSize(targetWidthResId), resources.getDimensionPixelSize(targetHeightResId));
    }

    public RequestCreator resize(int targetWidth, int targetHeight) {
        this.data.resize(targetWidth, targetHeight);
        return this;
    }

    public RequestCreator centerCrop() {
        this.data.centerCrop();
        return this;
    }

    public RequestCreator transform(Transformation transformation) {
        this.data.transform(transformation);
        return this;
    }

    public void into(ImageView target) {
        into(target, null);
    }

    public void into(ImageView target, Callback callback) {
        long started = System.nanoTime();
        Utils.checkMain();
        if (target == null) {
            throw new IllegalArgumentException("Target must not be null.");
        } else if (this.data.hasImage()) {
            if (this.deferred) {
                if (this.data.hasSize()) {
                    throw new IllegalStateException("Fit cannot be used with resize.");
                }
                int width = target.getWidth();
                int height = target.getHeight();
                if (width == 0 || height == 0) {
                    PicassoDrawable.setPlaceholder(target, this.placeholderResId, this.placeholderDrawable);
                    this.picasso.defer(target, new DeferredRequestCreator(this, target, callback));
                    return;
                }
                this.data.resize(width, height);
            }
            Request request = createRequest(started);
            String requestKey = Utils.createKey(request);
            if (!this.skipMemoryCache) {
                Bitmap bitmap = this.picasso.quickMemoryCacheCheck(requestKey);
                if (bitmap != null) {
                    this.picasso.cancelRequest(target);
                    PicassoDrawable.setBitmap(target, this.picasso.context, bitmap, LoadedFrom.MEMORY, this.noFade, this.picasso.indicatorsEnabled);
                    if (this.picasso.loggingEnabled) {
                        Utils.log("Main", "completed", request.plainId(), "from " + LoadedFrom.MEMORY);
                    }
                    if (callback != null) {
                        callback.onSuccess();
                        return;
                    }
                    return;
                }
            }
            PicassoDrawable.setPlaceholder(target, this.placeholderResId, this.placeholderDrawable);
            this.picasso.enqueueAndSubmit(new ImageViewAction(this.picasso, target, request, this.skipMemoryCache, this.noFade, this.errorResId, this.errorDrawable, requestKey, callback));
        } else {
            this.picasso.cancelRequest(target);
            PicassoDrawable.setPlaceholder(target, this.placeholderResId, this.placeholderDrawable);
        }
    }

    private Request createRequest(long started) {
        int id = getRequestId();
        Request request = this.data.build();
        request.id = id;
        request.started = started;
        boolean loggingEnabled = this.picasso.loggingEnabled;
        if (loggingEnabled) {
            Utils.log("Main", "created", request.plainId(), request.toString());
        }
        Request transformed = this.picasso.transformRequest(request);
        if (transformed != request) {
            transformed.id = id;
            transformed.started = started;
            if (loggingEnabled) {
                Utils.log("Main", "changed", transformed.logId(), "into " + transformed);
            }
        }
        return transformed;
    }
}
