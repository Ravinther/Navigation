package com.flurry.sdk;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;
import java.util.concurrent.RunnableFuture;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.ThreadPoolExecutor.DiscardPolicy;
import java.util.concurrent.TimeUnit;

public class jp<T extends lf> {
    private static final String f877a;
    private final jh<Object, T> f878b;
    private final HashMap<T, Object> f879c;
    private final HashMap<T, Future<?>> f880d;
    private final ThreadPoolExecutor f881e;

    /* renamed from: com.flurry.sdk.jp.1 */
    class C04761 extends ThreadPoolExecutor {
        final /* synthetic */ jp f940a;

        /* renamed from: com.flurry.sdk.jp.1.1 */
        class C04741 extends le {
            final /* synthetic */ lf f936a;
            final /* synthetic */ C04761 f937b;

            C04741(C04761 c04761, lf lfVar) {
                this.f937b = c04761;
                this.f936a = lfVar;
            }

            public void m1010a() {
                this.f936a.m1058k();
            }
        }

        /* renamed from: com.flurry.sdk.jp.1.2 */
        class C04752 extends le {
            final /* synthetic */ lf f938a;
            final /* synthetic */ C04761 f939b;

            C04752(C04761 c04761, lf lfVar) {
                this.f939b = c04761;
                this.f938a = lfVar;
            }

            public void m1011a() {
                this.f938a.m1059l();
            }
        }

        C04761(jp jpVar, int i, int i2, long j, TimeUnit timeUnit, BlockingQueue blockingQueue) {
            this.f940a = jpVar;
            super(i, i2, j, timeUnit, blockingQueue);
        }

        protected void beforeExecute(Thread thread, Runnable runnable) {
            super.beforeExecute(thread, runnable);
            lf a = this.f940a.m917a(runnable);
            if (a != null) {
                new C04741(this, a).run();
            }
        }

        protected void afterExecute(Runnable runnable, Throwable th) {
            super.afterExecute(runnable, th);
            lf a = this.f940a.m917a(runnable);
            if (a != null) {
                synchronized (this.f940a.f880d) {
                    this.f940a.f880d.remove(a);
                }
                this.f940a.m920b(a);
                new C04752(this, a).run();
            }
        }

        protected <V> RunnableFuture<V> newTaskFor(Callable<V> callable) {
            throw new UnsupportedOperationException("Callable not supported");
        }

        protected <V> RunnableFuture<V> newTaskFor(Runnable runnable, V v) {
            RunnableFuture joVar = new jo(runnable, v);
            synchronized (this.f940a.f880d) {
                this.f940a.f880d.put((lf) runnable, joVar);
            }
            return joVar;
        }
    }

    /* renamed from: com.flurry.sdk.jp.2 */
    class C04782 extends DiscardPolicy {
        final /* synthetic */ jp f943a;

        /* renamed from: com.flurry.sdk.jp.2.1 */
        class C04771 extends le {
            final /* synthetic */ lf f941a;
            final /* synthetic */ C04782 f942b;

            C04771(C04782 c04782, lf lfVar) {
                this.f942b = c04782;
                this.f941a = lfVar;
            }

            public void m1012a() {
                this.f941a.m1060m();
            }
        }

        C04782(jp jpVar) {
            this.f943a = jpVar;
        }

        public void rejectedExecution(Runnable runnable, ThreadPoolExecutor threadPoolExecutor) {
            super.rejectedExecution(runnable, threadPoolExecutor);
            lf a = this.f943a.m917a(runnable);
            if (a != null) {
                synchronized (this.f943a.f880d) {
                    this.f943a.f880d.remove(a);
                }
                this.f943a.m920b(a);
                new C04771(this, a).run();
            }
        }
    }

    /* renamed from: com.flurry.sdk.jp.3 */
    class C04793 extends le {
        final /* synthetic */ lf f944a;
        final /* synthetic */ jp f945b;

        C04793(jp jpVar, lf lfVar) {
            this.f945b = jpVar;
            this.f944a = lfVar;
        }

        public void m1013a() {
            this.f944a.m1056i();
        }
    }

    static {
        f877a = jp.class.getSimpleName();
    }

    public jp(String str, int i, int i2, long j, TimeUnit timeUnit, BlockingQueue<Runnable> blockingQueue) {
        this.f878b = new jh();
        this.f879c = new HashMap();
        this.f880d = new HashMap();
        this.f881e = new C04761(this, i, i2, j, timeUnit, blockingQueue);
        this.f881e.setRejectedExecutionHandler(new C04782(this));
        this.f881e.setThreadFactory(new kv(str, 1));
    }

    public synchronized void m925a(Object obj, T t) {
        if (!(obj == null || t == null)) {
            m921b(obj, t);
            this.f881e.submit(t);
        }
    }

    public synchronized void m924a(Object obj) {
        if (obj != null) {
            Collection<lf> hashSet = new HashSet();
            hashSet.addAll(this.f878b.m981a(obj));
            for (lf a : hashSet) {
                m923a(a);
            }
        }
    }

    public synchronized void m923a(T t) {
        if (t != null) {
            Future future;
            synchronized (this.f880d) {
                future = (Future) this.f880d.remove(t);
            }
            m920b((lf) t);
            if (future != null) {
                future.cancel(true);
            }
            new C04793(this, t).run();
        }
    }

    public synchronized void m927c() {
        Set<Object> hashSet = new HashSet();
        hashSet.addAll(this.f878b.m988c());
        for (Object a : hashSet) {
            m924a(a);
        }
    }

    public synchronized long m926b(Object obj) {
        long j;
        if (obj == null) {
            j = 0;
        } else {
            j = (long) this.f878b.m981a(obj).size();
        }
        return j;
    }

    private synchronized void m921b(Object obj, T t) {
        this.f878b.m984a(obj, (Object) t);
        this.f879c.put(t, obj);
    }

    private synchronized void m920b(T t) {
        m922c(this.f879c.get(t), t);
    }

    private synchronized void m922c(Object obj, T t) {
        this.f878b.m987b(obj, t);
        this.f879c.remove(t);
    }

    private T m917a(Runnable runnable) {
        if (runnable instanceof jo) {
            return (lf) ((jo) runnable).m1009a();
        }
        if (runnable instanceof lf) {
            return (lf) runnable;
        }
        jq.m1016a(6, f877a, "Unknown runnable class: " + runnable.getClass().getName());
        return null;
    }
}
