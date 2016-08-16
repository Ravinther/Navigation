package com.sygic.aura.utils;

import android.os.CountDownTimer;

public abstract class ProgressTimer extends CountDownTimer {
    protected int mMaxProgress;
    protected int mProgress;

    public abstract void updateProgress(int i);

    public ProgressTimer(long millisInFuture, long countDownInterval) {
        super(millisInFuture, countDownInterval);
        this.mMaxProgress = 100;
    }

    public ProgressTimer(long millisInFuture, long countDownInterval, int maxProgress) {
        super(millisInFuture, countDownInterval);
        this.mMaxProgress = maxProgress;
    }

    public final void onFinish() {
        if (this.mProgress < this.mMaxProgress) {
            start();
        } else {
            updateProgress(this.mMaxProgress);
        }
    }

    public int getMaxProgress() {
        return this.mMaxProgress;
    }
}
