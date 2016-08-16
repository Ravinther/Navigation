package com.sygic.aura.feature.automotive;

import android.content.Context;
import android.content.Intent;
import com.sygic.aura.SygicMain;
import com.sygic.aura.feature.ActivityListener;

public class PanasonicSmartApp implements ActivityListener, Runnable {
    private static final String LOG_TAG;
    private Context mContext;
    private boolean mStarted;

    static {
        LOG_TAG = PanasonicSmartApp.class.getSimpleName();
    }

    public PanasonicSmartApp(Context context) {
        this.mContext = context;
    }

    public void start() {
        this.mStarted = true;
        run();
    }

    public void stop() {
        SygicMain.getHandler().removeCallbacks(this);
        sentIntent(false);
        this.mStarted = false;
    }

    public void run() {
        sentIntent(true);
        SygicMain.getHandler().postDelayed(this, 1000);
    }

    private void sentIntent(boolean connected) {
        Intent sendIntent = new Intent();
        sendIntent.setAction("com.panasonic.pasap.dad.la.smartappcar.action.ACTION_NOTIFY_REGULATION");
        sendIntent.putExtra("com.panasonic.pasap.dad.la.smartappcar.extra.EXTRA_RELEASE_REGULATION", connected);
        sendIntent.setFlags(268435456);
        this.mContext.sendBroadcast(sendIntent);
    }

    public void onResume() {
        if (this.mStarted) {
            run();
        }
    }

    public void onPause() {
        if (this.mStarted) {
            SygicMain.getHandler().removeCallbacks(this);
            sentIntent(false);
        }
    }

    public void onCreate() {
    }

    public void onStart() {
    }

    public void onStop() {
    }

    public void onDestroy() {
    }
}
