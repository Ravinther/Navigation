package com.sygic.aura.helper;

import android.os.Handler;

public class ObjectHandler<T> {
    private final Callback<T> mCallback;
    private boolean mCondition;
    private T mValue;

    public interface Callback<K> {
        K getMethod();
    }

    public interface VoidCallback {
        void getMethod();
    }

    /* renamed from: com.sygic.aura.helper.ObjectHandler.1 */
    class C12761 implements Runnable {
        C12761() {
        }

        public void run() {
            synchronized (ObjectHandler.this) {
                ObjectHandler.this.mValue = ObjectHandler.this.mCallback.getMethod();
                ObjectHandler.this.mCondition = true;
                ObjectHandler.this.notify();
            }
        }
    }

    /* renamed from: com.sygic.aura.helper.ObjectHandler.2 */
    class C12772 implements Runnable {
        final /* synthetic */ VoidCallback val$c;

        C12772(VoidCallback voidCallback) {
            this.val$c = voidCallback;
        }

        public void run() {
            synchronized (ObjectHandler.this) {
                this.val$c.getMethod();
                ObjectHandler.this.mCondition = true;
                ObjectHandler.this.notify();
            }
        }
    }

    public ObjectHandler(Callback<T> c) {
        this.mCondition = false;
        this.mCallback = c;
    }

    public ObjectHandler<T> execute() {
        if (this.mCallback != null) {
            Runnable r = new C12761();
            Handler coreHandler = SygicHelper.getCoreHandler();
            if (coreHandler != null) {
                if (Thread.currentThread() != coreHandler.getLooper().getThread()) {
                    coreHandler.post(r);
                } else {
                    r.run();
                }
            }
        }
        return this;
    }

    public T get(T defaultValue) {
        if (this.mCallback == null || SygicHelper.getCoreHandler() == null) {
            return defaultValue;
        }
        if (Thread.currentThread() != SygicHelper.getCoreHandler().getLooper().getThread()) {
            waitForExecution();
        }
        return this.mValue;
    }

    public static void postAndWait(VoidCallback c) {
        new ObjectHandler(null).execute(c);
    }

    private void execute(VoidCallback c) {
        if (c != null) {
            Runnable r = new C12772(c);
            Handler coreHandler = SygicHelper.getCoreHandler();
            if (coreHandler == null) {
                return;
            }
            if (Thread.currentThread() != coreHandler.getLooper().getThread()) {
                coreHandler.post(r);
                waitForExecution();
                return;
            }
            r.run();
        }
    }

    private void waitForExecution() {
        synchronized (this) {
            while (!this.mCondition) {
                try {
                    wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
