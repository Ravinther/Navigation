package com.sygic.aura.feature.net;

/* compiled from: NetworkThreadFactory */
abstract class NetworkThread<Param, Ret> extends Thread {
    protected volatile Param mParam;
    protected volatile Ret mRetValue;

    NetworkThread() {
    }

    public synchronized NetworkThread<Param, Ret> execute(Param param) {
        this.mParam = param;
        start();
        return this;
    }

    public synchronized Ret get() throws InterruptedException {
        join();
        return this.mRetValue;
    }
}
