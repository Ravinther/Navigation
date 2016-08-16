package com.sygic.aura.dashboard.navigationDrawer;

import android.content.Context;
import android.content.res.Configuration;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.DrawerLayout.DrawerListener;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.WindowManager;
import com.sygic.aura.dashboard.fragment.DashboardFragment;
import com.sygic.aura.helper.InCarConnection;
import com.sygic.aura.helper.SygicHelper;
import com.sygic.aura.helper.interfaces.NavigationDrawerListener;
import com.sygic.aura.map.PositionInfo;
import com.sygic.aura.route.data.RouteNavigateData;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class NavigationDrawer extends DrawerLayout implements DrawerListener {
    private Runnable mCloseDrawerRunnable;
    private int mDefaultWidth;
    private final List<NavigationDrawerListener> mNavigationDrawerListeners;
    private int mScreenWidth;

    /* renamed from: com.sygic.aura.dashboard.navigationDrawer.NavigationDrawer.1 */
    class C11701 implements Runnable {
        C11701() {
        }

        public void run() {
            SygicHelper.enableGlMapTouchListener(true);
        }
    }

    /* renamed from: com.sygic.aura.dashboard.navigationDrawer.NavigationDrawer.2 */
    class C11712 implements Runnable {
        C11712() {
        }

        public void run() {
            NavigationDrawer.this.closeDrawer();
        }
    }

    public NavigationDrawer(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.mNavigationDrawerListeners = Collections.synchronizedList(new LinkedList());
        this.mCloseDrawerRunnable = new C11712();
        initInternal();
    }

    public NavigationDrawer(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mNavigationDrawerListeners = Collections.synchronizedList(new LinkedList());
        this.mCloseDrawerRunnable = new C11712();
        initInternal();
    }

    public NavigationDrawer(Context context) {
        super(context);
        this.mNavigationDrawerListeners = Collections.synchronizedList(new LinkedList());
        this.mCloseDrawerRunnable = new C11712();
        initInternal();
    }

    private void initInternal() {
        this.mScreenWidth = getScreenWidth();
        this.mDefaultWidth = (int) getResources().getDimension(2131230886);
    }

    public void init(FragmentManager fragmentManager, RouteNavigateData navigateData, int launchMode) {
        setDrawerListener(this);
        setFocusableInTouchMode(false);
        post(new C11701());
        addDashboardFragment(fragmentManager, navigateData, launchMode);
    }

    public boolean onInterceptTouchEvent(MotionEvent ev) {
        if (InCarConnection.isInCarConnected()) {
            return false;
        }
        return super.onInterceptTouchEvent(ev);
    }

    protected void onFinishInflate() {
        super.onFinishInflate();
        if (!isInEditMode()) {
            LayoutParams lp = ((ViewGroup) findViewById(2131624521)).getLayoutParams();
            int reqWidth = getDrawerRequestedWidth();
            if (lp.width != reqWidth) {
                lp.width = reqWidth;
            }
        }
    }

    private int getDrawerRequestedWidth() {
        return InCarConnection.isInCarConnected() ? this.mScreenWidth : this.mDefaultWidth;
    }

    private int getScreenWidth() {
        WindowManager wm = (WindowManager) getContext().getSystemService("window");
        DisplayMetrics metrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(metrics);
        return metrics.widthPixels;
    }

    private void addDashboardFragment(FragmentManager manager, RouteNavigateData navigateData, int launchMode) {
        manager.beginTransaction().add(2131624521, DashboardFragment.newInstance(navigateData, launchMode), "fragment_dashboard_tag").commitAllowingStateLoss();
    }

    protected void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        this.mScreenWidth = getScreenWidth();
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void notifyListeners(boolean r5) {
        /*
        r4 = this;
        r1 = r4.mNavigationDrawerListeners;
        r1 = r1.isEmpty();
        if (r1 != 0) goto L_0x0025;
    L_0x0008:
        r2 = r4.mNavigationDrawerListeners;
        monitor-enter(r2);
        r1 = r4.mNavigationDrawerListeners;	 Catch:{ all -> 0x0021 }
        r1 = r1.iterator();	 Catch:{ all -> 0x0021 }
    L_0x0011:
        r3 = r1.hasNext();	 Catch:{ all -> 0x0021 }
        if (r3 == 0) goto L_0x0024;
    L_0x0017:
        r0 = r1.next();	 Catch:{ all -> 0x0021 }
        r0 = (com.sygic.aura.helper.interfaces.NavigationDrawerListener) r0;	 Catch:{ all -> 0x0021 }
        r0.onDrawerFinished(r5);	 Catch:{ all -> 0x0021 }
        goto L_0x0011;
    L_0x0021:
        r1 = move-exception;
        monitor-exit(r2);	 Catch:{ all -> 0x0021 }
        throw r1;
    L_0x0024:
        monitor-exit(r2);	 Catch:{ all -> 0x0021 }
    L_0x0025:
        return;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.sygic.aura.dashboard.navigationDrawer.NavigationDrawer.notifyListeners(boolean):void");
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void onDrawerStateChanged(int r5) {
        /*
        r4 = this;
        if (r5 != 0) goto L_0x0027;
    L_0x0002:
        r1 = r4.mNavigationDrawerListeners;
        r1 = r1.isEmpty();
        if (r1 != 0) goto L_0x0027;
    L_0x000a:
        r2 = r4.mNavigationDrawerListeners;
        monitor-enter(r2);
        r1 = r4.mNavigationDrawerListeners;	 Catch:{ all -> 0x0023 }
        r1 = r1.iterator();	 Catch:{ all -> 0x0023 }
    L_0x0013:
        r3 = r1.hasNext();	 Catch:{ all -> 0x0023 }
        if (r3 == 0) goto L_0x0026;
    L_0x0019:
        r0 = r1.next();	 Catch:{ all -> 0x0023 }
        r0 = (com.sygic.aura.helper.interfaces.NavigationDrawerListener) r0;	 Catch:{ all -> 0x0023 }
        r0.onDrawerStateIdle();	 Catch:{ all -> 0x0023 }
        goto L_0x0013;
    L_0x0023:
        r1 = move-exception;
        monitor-exit(r2);	 Catch:{ all -> 0x0023 }
        throw r1;
    L_0x0026:
        monitor-exit(r2);	 Catch:{ all -> 0x0023 }
    L_0x0027:
        return;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.sygic.aura.dashboard.navigationDrawer.NavigationDrawer.onDrawerStateChanged(int):void");
    }

    public void onDrawerClosed(View view) {
        PositionInfo.nativeEnableMapView();
        SygicHelper.enableGlMapTouchListener(true);
        notifyListeners(true);
    }

    public void onDrawerOpened(View drawerView) {
        SygicHelper.enableGlMapTouchListener(false);
        notifyListeners(false);
    }

    public void onDrawerSlide(View drawerView, float slideOffset) {
    }

    public boolean registerAnimationDrawerListener(NavigationDrawerListener navigationDrawerFinished) {
        return this.mNavigationDrawerListeners.add(navigationDrawerFinished);
    }

    public boolean unregisterAnimationDrawerListener(NavigationDrawerListener navigationDrawerFinished) {
        return this.mNavigationDrawerListeners.remove(navigationDrawerFinished);
    }

    public void toggleDrawer() {
        if (isDrawerOpen()) {
            closeDrawer();
        } else {
            openDrawer();
        }
    }

    public boolean isDrawerOpen() {
        return isDrawerOpen(8388611);
    }

    public void closeDrawerDelayed() {
        postDelayed(this.mCloseDrawerRunnable, 50);
    }

    public void closeDrawer() {
        closeDrawer(8388611);
    }

    public void openDrawer() {
        openDrawer(8388611);
    }
}
