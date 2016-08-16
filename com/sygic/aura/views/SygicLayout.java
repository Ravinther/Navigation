package com.sygic.aura.views;

import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.app.ActionBarActivity;
import android.util.AttributeSet;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import com.sygic.aura.SygicMain;
import com.sygic.aura.SygicProject;
import com.sygic.aura.analytics.InfinarioAnalyticsLogger;
import com.sygic.aura.events.core.InitCoreListener;
import com.sygic.aura.helper.SygicHelper;
import com.sygic.aura.map.PositionInfo;
import com.sygic.aura.settings.data.SettingsManager;
import com.sygic.base.C1799R;

public class SygicLayout extends FrameLayout implements InitCoreListener {

    /* renamed from: com.sygic.aura.views.SygicLayout.1 */
    class C17881 implements Runnable {
        C17881() {
        }

        public void run() {
            if (!SygicProject.IS_TEST) {
                SygicMain.getInstance().SetIsRunning();
            }
        }
    }

    /* renamed from: com.sygic.aura.views.SygicLayout.2 */
    class C17892 implements Runnable {
        C17892() {
        }

        public void run() {
            SettingsManager.initFromCore(SygicLayout.this.getContext());
            View mapSurface = SygicLayout.this.findViewById(C1799R.id.surface);
            ((ViewGroup) mapSurface.getParent()).removeView(mapSurface);
            Activity activity = (Activity) SygicLayout.this.getContext();
            activity.setContentView(2130903229);
            SygicHelper.setSurface((SurfaceView) activity.findViewById(C1799R.id.surface));
            ((ActionBarActivity) activity).supportInvalidateOptionsMenu();
            InfinarioAnalyticsLogger.getInstance(SygicLayout.this.getContext()).init();
        }
    }

    public SygicLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        if (!isInEditMode()) {
            SygicHelper.registerInitCoreListener(this);
            if (SygicProject.IS_PROTOTYPE) {
                new Handler().postDelayed(new C17881(), 500);
            }
        }
    }

    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        SygicHelper.unregisterInitCoreListener(this);
    }

    public void onInitCoreDone() {
        PositionInfo.nativeDisableMapView();
        new Handler(Looper.getMainLooper()).post(new C17892());
    }
}
