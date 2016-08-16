package com.sygic.aura.route.data.infobar_slots;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public abstract class RouteInfoBarSlot implements Slot {
    protected long mLastRun;
    private View mView;

    protected abstract void executeImpl();

    protected abstract int getLayoutId();

    protected RouteInfoBarSlot() {
        this.mLastRun = -1 * getUpdateInterval();
    }

    public View getView(LayoutInflater inflater) {
        if (this.mView == null) {
            this.mView = inflater.inflate(getLayoutId(), null);
            findSubViews(this.mView);
        }
        return this.mView;
    }

    public void removeView() {
        if (this.mView != null) {
            ViewGroup parent = (ViewGroup) this.mView.getParent();
            if (parent != null) {
                parent.removeView(this.mView);
            }
        }
    }

    protected int getColor(int colorResId) {
        return this.mView.getResources().getColor(colorResId);
    }

    protected Context getContext() {
        if (this.mView != null) {
            return this.mView.getContext();
        }
        return null;
    }

    public final void execute(long time) {
        if (this.mLastRun + getUpdateInterval() <= time) {
            this.mLastRun = time;
            executeImpl();
        }
    }

    public void resetTimer() {
        this.mLastRun = -1 * getUpdateInterval();
    }

    protected void findSubViews(View rootView) {
    }
}
