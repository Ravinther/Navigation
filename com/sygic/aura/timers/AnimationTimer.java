package com.sygic.aura.timers;

import android.os.SystemClock;
import java.lang.Thread.State;

public abstract class AnimationTimer extends Thread {
    protected static long mDelay;
    protected long mEndTime;
    protected boolean mIsFinished;

    protected abstract void hide();

    protected abstract void show();

    public AnimationTimer(long delay) {
        this.mIsFinished = false;
        mDelay = delay;
    }

    public void cancel() {
        cancel(false);
    }

    public void cancel(boolean hide) {
        setFinished(true);
        if (hide) {
            hide();
        }
    }

    public boolean isFinished() {
        return getFinished() || getState().equals(State.TERMINATED);
    }

    public void run() {
        try {
            show();
            setEndTime();
            while (!getFinished()) {
                Thread.sleep(getWakeTime());
                if (!getFinished() && isWakeTime()) {
                    setFinished(true);
                    hide();
                    return;
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void reset() {
        setEndTime();
    }

    protected synchronized boolean getFinished() {
        return this.mIsFinished;
    }

    protected synchronized void setFinished(boolean isFinished) {
        this.mIsFinished = isFinished;
    }

    protected synchronized boolean isWakeTime() {
        return SystemClock.elapsedRealtime() >= this.mEndTime;
    }

    protected synchronized long getWakeTime() {
        return Math.max(this.mEndTime - SystemClock.elapsedRealtime(), 0);
    }

    protected synchronized void setEndTime() {
        this.mEndTime = SystemClock.elapsedRealtime() + mDelay;
    }
}
