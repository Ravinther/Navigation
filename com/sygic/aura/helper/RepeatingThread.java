package com.sygic.aura.helper;

public class RepeatingThread extends Thread {
    private boolean mFinished;
    private final ExecutionOrder mOrder;
    private final long mSleepTime;

    public interface ExecutionOrder {
        boolean onNegative();

        boolean onPositive();

        boolean runningCondition();
    }

    public RepeatingThread(ExecutionOrder order, long sleepTime) {
        this.mFinished = false;
        this.mOrder = order;
        this.mSleepTime = sleepTime;
    }

    public void run() {
        boolean running = true;
        while (!this.mFinished && running) {
            if (this.mOrder.runningCondition()) {
                running = this.mOrder.onPositive();
            } else {
                running = this.mOrder.onNegative();
            }
            if (running) {
                try {
                    Thread.sleep(this.mSleepTime);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
        this.mFinished = true;
    }

    public void setFinished(boolean finished) {
        this.mFinished = finished;
    }

    public boolean isFinished() {
        return this.mFinished;
    }
}
