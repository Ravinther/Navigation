package com.sygic.aura.helper;

import android.os.Handler;
import android.os.Looper;
import android.util.SparseArray;
import com.sygic.aura.activity.NaviNativeActivity;
import com.sygic.aura.helper.RepeatingThread.ExecutionOrder;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Queue;
import java.util.Set;

public abstract class NativeMethodsHelper {
    protected static Handler sHandler;
    private static final SparseArray<Set<CoreEventListener>> sListeners;
    private static final Queue<MethodData> sRequestQueue;
    private static RepeatingThread sRequestThread;

    public interface CoreEventListener {
    }

    /* renamed from: com.sygic.aura.helper.NativeMethodsHelper.1 */
    static class C12741 implements Runnable {
        final /* synthetic */ Object[] val$args;
        final /* synthetic */ Class val$cls;
        final /* synthetic */ CoreEventListener val$listener;
        final /* synthetic */ String val$methodName;

        C12741(Class cls, String str, Object[] objArr, CoreEventListener coreEventListener) {
            this.val$cls = cls;
            this.val$methodName = str;
            this.val$args = objArr;
            this.val$listener = coreEventListener;
        }

        public void run() {
            NativeMethodsHelper.invokeMethod(this.val$listener, NativeMethodsHelper.getMethod(this.val$cls, this.val$methodName, this.val$args), this.val$args);
        }
    }

    /* renamed from: com.sygic.aura.helper.NativeMethodsHelper.2 */
    static class C12752 implements ExecutionOrder {
        C12752() {
        }

        public boolean runningCondition() {
            return NaviNativeActivity.isInitialized();
        }

        /* JADX WARNING: inconsistent code. */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public boolean onPositive() {
            /*
            r5 = this;
            r2 = com.sygic.aura.helper.NativeMethodsHelper.sRequestQueue;
            monitor-enter(r2);
        L_0x0005:
            r1 = com.sygic.aura.helper.NativeMethodsHelper.sRequestQueue;	 Catch:{ all -> 0x0029 }
            r1 = r1.isEmpty();	 Catch:{ all -> 0x0029 }
            if (r1 != 0) goto L_0x002c;
        L_0x000f:
            r1 = com.sygic.aura.helper.NativeMethodsHelper.sRequestQueue;	 Catch:{ all -> 0x0029 }
            r0 = r1.poll();	 Catch:{ all -> 0x0029 }
            r0 = (com.sygic.aura.helper.NativeMethodsHelper.MethodData) r0;	 Catch:{ all -> 0x0029 }
            r1 = r0.cls;	 Catch:{ all -> 0x0029 }
            r3 = r0.name;	 Catch:{ all -> 0x0029 }
            r4 = r0.args;	 Catch:{ all -> 0x0029 }
            com.sygic.aura.helper.NativeMethodsHelper.callMethod(r1, r3, r4);	 Catch:{ all -> 0x0029 }
            goto L_0x0005;
        L_0x0029:
            r1 = move-exception;
            monitor-exit(r2);	 Catch:{ all -> 0x0029 }
            throw r1;
        L_0x002c:
            r1 = com.sygic.aura.helper.NativeMethodsHelper.sRequestThread;	 Catch:{ all -> 0x0029 }
            r3 = 1;
            r1.setFinished(r3);	 Catch:{ all -> 0x0029 }
            monitor-exit(r2);	 Catch:{ all -> 0x0029 }
            r1 = 0;
            return r1;
            */
            throw new UnsupportedOperationException("Method not decompiled: com.sygic.aura.helper.NativeMethodsHelper.2.onPositive():boolean");
        }

        public boolean onNegative() {
            return true;
        }
    }

    private static class MethodData {
        private final Object[] args;
        private final Class<? extends CoreEventListener> cls;
        private final String name;

        public MethodData(Class<? extends CoreEventListener> cls, String methodName, Object... args) {
            this.cls = cls;
            this.name = methodName;
            this.args = args;
        }

        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }
            MethodData that = (MethodData) o;
            if (!this.cls.equals(that.cls)) {
                return false;
            }
            if (this.name.compareTo(that.name) != 0) {
                return false;
            }
            return true;
        }

        public int hashCode() {
            return (this.cls.hashCode() * 31) + this.name.hashCode();
        }
    }

    static {
        sRequestQueue = new ArrayDeque();
        sHandler = new Handler(Looper.getMainLooper());
        sListeners = new SparseArray();
    }

    private static int getClassId(Class<? extends CoreEventListener> clazz) {
        return clazz.getName().hashCode();
    }

    protected static Set<CoreEventListener> getListeners(Class<? extends CoreEventListener> clazz) {
        int key = getClassId(clazz);
        Set<CoreEventListener> listeners = (Set) sListeners.get(key);
        if (listeners != null) {
            return listeners;
        }
        listeners = Collections.synchronizedSet(new HashSet());
        sListeners.put(key, listeners);
        return listeners;
    }

    protected static void registerListener(Class<? extends CoreEventListener> clazz, CoreEventListener listenerObject) {
        getListeners(clazz).add(listenerObject);
    }

    protected static void unregisterListener(Class<? extends CoreEventListener> clazz, CoreEventListener listenerObject) {
        Set<CoreEventListener> listeners = getListeners(clazz);
        if (listeners != null) {
            listeners.remove(listenerObject);
        }
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static void callMethod(java.lang.Class<? extends com.sygic.aura.helper.NativeMethodsHelper.CoreEventListener> r5, java.lang.String r6, java.lang.Object... r7) {
        /*
        r1 = getListeners(r5);
        if (r1 == 0) goto L_0x0026;
    L_0x0006:
        monitor-enter(r1);
        r2 = r1.iterator();	 Catch:{ all -> 0x0022 }
    L_0x000b:
        r3 = r2.hasNext();	 Catch:{ all -> 0x0022 }
        if (r3 == 0) goto L_0x0025;
    L_0x0011:
        r0 = r2.next();	 Catch:{ all -> 0x0022 }
        r0 = (com.sygic.aura.helper.NativeMethodsHelper.CoreEventListener) r0;	 Catch:{ all -> 0x0022 }
        r3 = sHandler;	 Catch:{ all -> 0x0022 }
        r4 = new com.sygic.aura.helper.NativeMethodsHelper$1;	 Catch:{ all -> 0x0022 }
        r4.<init>(r5, r6, r7, r0);	 Catch:{ all -> 0x0022 }
        r3.post(r4);	 Catch:{ all -> 0x0022 }
        goto L_0x000b;
    L_0x0022:
        r2 = move-exception;
        monitor-exit(r1);	 Catch:{ all -> 0x0022 }
        throw r2;
    L_0x0025:
        monitor-exit(r1);	 Catch:{ all -> 0x0022 }
    L_0x0026:
        return;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.sygic.aura.helper.NativeMethodsHelper.callMethod(java.lang.Class, java.lang.String, java.lang.Object[]):void");
    }

    protected static void callMethodWhenReady(Class<? extends CoreEventListener> cls, String methodName, Object... args) {
        if (!(NaviNativeActivity.isInitialized() && (sRequestThread == null || sRequestThread.isFinished()))) {
            if (sRequestThread == null) {
                sRequestThread = new RepeatingThread(new C12752(), 500);
                sRequestThread.start();
            }
            synchronized (sRequestQueue) {
                if (sRequestThread.isFinished()) {
                } else {
                    MethodData methodData = new MethodData(cls, methodName, args);
                    sRequestQueue.remove(methodData);
                    sRequestQueue.add(methodData);
                    return;
                }
            }
        }
        if (sRequestThread != null) {
            sRequestThread = null;
        }
        callMethod(cls, methodName, args);
    }

    private static Method getMethod(Class<?> clazz, String methodName, Object... args) {
        Class<?>[] parameterTypes = null;
        if (args != null) {
            try {
                parameterTypes = new Class[args.length];
                for (int i = 0; i < args.length; i++) {
                    parameterTypes[i] = args[i].getClass();
                }
            } catch (NoSuchMethodException e) {
                CrashlyticsHelper.logException("NativeMethodsHelper", "No such method method '" + methodName + "' in class '" + clazz.getName() + "' with params " + Arrays.toString(args), e, true);
                return null;
            } catch (NullPointerException e2) {
                e2.printStackTrace();
                CrashlyticsHelper.logException("NativeMethodsHelper", "Failed to get method '" + methodName + "' in class '" + clazz.getName() + "' with params " + Arrays.toString(args), e2, true);
                throw e2;
            }
        }
        return clazz.getMethod(methodName, parameterTypes);
    }

    private static Object invokeMethod(Object receiver, Method method, Object... args) {
        Object obj = null;
        try {
            obj = method.invoke(receiver, args);
        } catch (IllegalAccessException e) {
            CrashlyticsHelper.logError("NativeMethodsHelper", "IllegalAccessException in invokeMethod - " + method);
            e.printStackTrace();
        } catch (InvocationTargetException e2) {
            CrashlyticsHelper.logError("NativeMethodsHelper", "InvocationTargetException in invokeMethod - " + method);
            e2.printStackTrace();
        }
        return obj;
    }
}
