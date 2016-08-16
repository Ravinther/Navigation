package com.sygic.aura.map.screen;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ProgressBar;
import com.sygic.aura.activity.NaviNativeActivity;
import com.sygic.aura.fragments.interfaces.FragmentManagerInterface;
import com.sygic.aura.helper.SygicHelper;
import com.sygic.aura.map.fragment.MapOverlayFragment;
import com.sygic.aura.map.fragment.MapOverlayFragment.Mode;
import com.sygic.aura.navigate.fragment.NavigateFragment;
import com.sygic.aura.route.RouteManager;
import com.sygic.aura.route.RouteSummary;
import com.sygic.aura.utils.ProgressTimer;
import com.sygic.base.C1799R;

public class RestoreRouteScreen extends OverlayScreen {
    private ProgressBar mProgressBar;
    private CountDownTimer mTimer;

    /* renamed from: com.sygic.aura.map.screen.RestoreRouteScreen.1 */
    class C13511 implements OnClickListener {
        C13511() {
        }

        public void onClick(View v) {
            RouteManager.nativeStopComputingRemoveRoute();
            if (RestoreRouteScreen.this.mTimer != null) {
                RestoreRouteScreen.this.mTimer.cancel();
            }
            RestoreRouteScreen.this.setRestoreFinished();
            MapOverlayFragment.setMode(RestoreRouteScreen.this.mContext, Mode.FREEDRIVE_BROWSE);
        }
    }

    /* renamed from: com.sygic.aura.map.screen.RestoreRouteScreen.2 */
    static class C13532 extends ProgressTimer {
        final /* synthetic */ RestoreRouteScreen val$screen;

        /* renamed from: com.sygic.aura.map.screen.RestoreRouteScreen.2.1 */
        class C13521 implements Runnable {
            C13521() {
            }

            public void run() {
                C13532.this.cancel();
            }
        }

        C13532(long x0, long x1, RestoreRouteScreen restoreRouteScreen) {
            this.val$screen = restoreRouteScreen;
            super(x0, x1);
        }

        public void onTick(long millisUntilFinished) {
            updateProgress(RouteSummary.nativeGetComputingProgress());
        }

        public void updateProgress(int progress) {
            if (progress != this.mProgress) {
                if (progress > 0) {
                    this.val$screen.mProgressBar.setProgress(progress);
                }
                this.mProgress = this.val$screen.mProgressBar.getProgress();
                if (this.mProgress == this.mMaxProgress || progress < 0) {
                    this.mProgress = getMaxProgress();
                    if (RouteManager.nativeExistValidRoute()) {
                        FragmentManagerInterface mngr = SygicHelper.getFragmentActivityWrapper();
                        if (mngr != null) {
                            new Handler().post(new C13521());
                            Bundle bundle = new Bundle();
                            bundle.putBoolean("restore route", true);
                            MapOverlayFragment.setMode(this.val$screen.mContext, Mode.NAVIGATE_INFO_BAR);
                            mngr.replaceFragment(NavigateFragment.class, "fragment_navigate_tag", true, bundle);
                            this.val$screen.setRestoreFinished();
                        }
                    }
                }
            }
        }
    }

    protected void setupChildScreen(View rootView) {
        rootView.findViewById(C1799R.id.btn_cancel).setOnClickListener(new C13511());
        this.mProgressBar = (ProgressBar) rootView.findViewById(2131624110);
    }

    protected void onScreenEntered() {
        fireRestoreRoute();
    }

    protected void onscreenLeft() {
        NavigationInfoBarScreen.setupSlots();
    }

    public static void fireRestoreRoute() {
        RestoreRouteScreen screen = (RestoreRouteScreen) OverlayScreen.getInstance(Mode.RESTORE_ROUTE);
        if (screen != null) {
            screen.mTimer = new C13532(5000, 500, screen);
            screen.mTimer.start();
        }
    }

    private void setRestoreFinished() {
        ((NaviNativeActivity) this.mFragment.getActivity()).setRestoreRoute(false);
    }
}
