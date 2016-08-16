package com.sygic.aura.route.fragment;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.Toolbar.OnMenuItemClickListener;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import com.sygic.aura.SygicProject;
import com.sygic.aura.fragments.AbstractScreenFragment;
import com.sygic.aura.helper.EventReceivers.RouteEventsReceiver;
import com.sygic.aura.helper.interfaces.RouteComputeErrorListener;
import com.sygic.aura.helper.interfaces.RouteEventsListener;
import com.sygic.aura.route.RouteSummary;
import com.sygic.aura.utils.ProgressTimer;
import com.sygic.aura.views.font_specials.SToolbar;
import com.sygic.aura.views.font_specials.SToolbar.OnInvalidatedMenuListener;

public abstract class RouteComputeProgressFragment extends AbstractScreenFragment implements RouteComputeErrorListener, RouteEventsListener {
    private boolean mCanceled;
    protected boolean mComputing;
    protected ProgressBar mProgressBar;
    private RouteProgressTimer mProgressTimer;

    /* renamed from: com.sygic.aura.route.fragment.RouteComputeProgressFragment.1 */
    class C15261 implements OnMenuItemClickListener {
        C15261() {
        }

        public boolean onMenuItemClick(MenuItem item) {
            return RouteComputeProgressFragment.this.onOptionsItemSelected(item);
        }
    }

    /* renamed from: com.sygic.aura.route.fragment.RouteComputeProgressFragment.2 */
    class C15272 implements OnInvalidatedMenuListener {
        C15272() {
        }

        public void onMenuInvalidated(Menu menu) {
            RouteComputeProgressFragment.this.onPrepareOptionsMenu(menu);
        }
    }

    /* renamed from: com.sygic.aura.route.fragment.RouteComputeProgressFragment.3 */
    class C15283 extends AnimatorListenerAdapter {
        C15283() {
        }

        public void onAnimationEnd(Animator animation) {
            RouteComputeProgressFragment.this.mProgressBar.setAlpha(1.0f);
            RouteComputeProgressFragment.this.mProgressBar.setVisibility(8);
        }
    }

    private class RouteProgressTimer extends ProgressTimer {
        private int mOldProgress;

        public RouteProgressTimer(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval, 100);
            this.mOldProgress = 0;
        }

        public void onTick(long millisUntilFinished) {
            updateProgress();
        }

        private void updateProgress() {
            if (this.mProgress <= this.mMaxProgress) {
                this.mProgress = RouteSummary.nativeGetComputingProgress();
                updateProgress(this.mProgress);
            }
        }

        public void updateProgress(int progress) {
            if (progress > this.mOldProgress) {
                RouteComputeProgressFragment.this.mProgressBar.setProgress(progress);
            }
            this.mOldProgress = progress;
        }

        public void resetProgress() {
            this.mOldProgress = -1;
            this.mProgress = 0;
            updateProgress(0);
        }
    }

    protected abstract int getToolbarMenu();

    protected abstract int getToolbarTitle();

    public RouteComputeProgressFragment() {
        this.mCanceled = true;
        this.mComputing = true;
    }

    protected boolean isCanceled() {
        return this.mCanceled;
    }

    protected void onSetupToolbar(SToolbar toolbar) {
        super.onSetupToolbar(toolbar);
        toolbar.setTitle(getToolbarTitle());
        toolbar.setOnMenuItemClickListener(new C15261());
        toolbar.setOnMenuInvalidateListener(new C15272());
        toolbar.inflateMenu(getToolbarMenu());
        this.mProgressBar = (ProgressBar) toolbar.findViewById(2131624110);
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        RouteEventsReceiver.registerRouteEventsListener(this);
        RouteEventsReceiver.registerRouteComputeErrorListener(this);
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    public void onDestroyView() {
        RouteEventsReceiver.unregisterRouteEventsListener(this);
        RouteEventsReceiver.unregisterRouteComputeErrorListener(this);
        super.onDestroyView();
    }

    public void onStartComputingProgress() {
        this.mComputing = true;
        this.mCanceled = false;
        if (isAdded()) {
            this.mToolbar.invalidateMenu();
            showProgressBar();
            if (this.mProgressTimer == null) {
                this.mProgressTimer = new RouteProgressTimer(10000, SygicProject.IS_PROTOTYPE ? 250 : 150);
            }
            this.mProgressTimer.start();
        }
    }

    public void onFinishComputingProgress() {
        if (!this.mCanceled && isAdded() && this.mProgressTimer != null) {
            this.mProgressTimer.resetProgress();
        }
    }

    public void onRouteComputeFinishedAll() {
        this.mComputing = false;
        FragmentActivity activity = getActivity();
        if (!this.mCanceled && activity != null && isAdded() && this.mProgressTimer != null) {
            this.mProgressTimer.updateProgress(100);
            this.mProgressTimer.cancel();
            this.mProgressTimer = null;
            this.mToolbar.invalidateMenu();
            hideProgressBar();
        }
    }

    public void onRouteComputeError(String msg) {
        this.mComputing = false;
        this.mCanceled = true;
    }

    protected void showProgressBar() {
        if (VERSION.SDK_INT >= 12) {
            this.mProgressBar.setVisibility(0);
            this.mProgressBar.setAlpha(0.0f);
            this.mProgressBar.animate().alpha(1.0f).setDuration(200).setListener(null);
            return;
        }
        this.mProgressBar.setVisibility(0);
    }

    protected void hideProgressBar() {
        if (VERSION.SDK_INT >= 12) {
            this.mProgressBar.animate().alpha(0.0f).setDuration(200).setListener(new C15283());
        } else {
            this.mProgressBar.setVisibility(8);
        }
    }
}
