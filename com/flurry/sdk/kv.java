package com.flurry.sdk;

import java.util.concurrent.ThreadFactory;

public class kv implements ThreadFactory {
    private final ThreadGroup f1095a;
    private final int f1096b;

    public kv(String str, int i) {
        this.f1095a = new ThreadGroup(str);
        this.f1096b = i;
    }

    public Thread newThread(Runnable runnable) {
        Thread thread = new Thread(this.f1095a, runnable);
        thread.setName(this.f1095a.getName() + ":" + thread.getId());
        thread.setPriority(this.f1096b);
        return thread;
    }
}
