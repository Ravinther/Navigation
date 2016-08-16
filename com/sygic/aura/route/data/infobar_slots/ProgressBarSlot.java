package com.sygic.aura.route.data.infobar_slots;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.ProgressBar;
import com.sygic.aura.route.RouteSummary;

public class ProgressBarSlot implements Slot {
    private long mLastRun;
    private ProgressBar mProgressBar;

    public ProgressBarSlot() {
        this.mLastRun = -1 * getUpdateInterval();
    }

    public void setView(ProgressBar progressBar) {
        this.mProgressBar = progressBar;
    }

    public View getView(LayoutInflater inflater) {
        return this.mProgressBar;
    }

    public void execute(long time) {
        if (this.mProgressBar != null && this.mLastRun + getUpdateInterval() <= time) {
            this.mLastRun = time;
            long total = RouteSummary.nativeGetPassedDistance();
            this.mProgressBar.setProgress(total == 0 ? 0 : (int) ((((float) (total - RouteSummary.nativeGetRemainingDistance())) / ((float) total)) * 100.0f));
        }
    }

    public long getUpdateInterval() {
        return 2000;
    }

    public void removeView() {
    }

    public void resetTimer() {
        this.mLastRun = -1 * getUpdateInterval();
    }
}
