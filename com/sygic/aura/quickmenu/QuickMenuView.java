package com.sygic.aura.quickmenu;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.GestureDetector.OnGestureListener;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.widget.FrameLayout.LayoutParams;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.Toast;
import com.sygic.aura.analytics.AnalyticsInterface.EventType;
import com.sygic.aura.analytics.SygicAnalyticsLogger;
import com.sygic.aura.feature.system.LowSystemFeature.EEventType;
import com.sygic.aura.fragments.interfaces.FragmentResultCallback;
import com.sygic.aura.helper.Fragments;
import com.sygic.aura.helper.interfaces.DemoListener;
import com.sygic.aura.map.MapControlsManager;
import com.sygic.aura.map.MapControlsManager.ENaviType;
import com.sygic.aura.map.PositionInfo;
import com.sygic.aura.map.fragment.MapOverlayFragment;
import com.sygic.aura.map.fragment.MapOverlayFragment.Mode;
import com.sygic.aura.map.screen.NavigationInfoBarScreen;
import com.sygic.aura.map.screen.OverlayScreen;
import com.sygic.aura.map.screen.intefaces.SoundMutedListener;
import com.sygic.aura.poi.fragment.PoiFragment;
import com.sygic.aura.poi.nearbypoi.fragment.NearbyPoiCategoryFragment;
import com.sygic.aura.quickmenu.QuickMenuItems.QuickMenuItemType;
import com.sygic.aura.resources.ResourceManager;
import com.sygic.aura.route.DemoManager;
import com.sygic.aura.route.RouteManager;
import com.sygic.aura.route.RouteManager.RouteComputeMode;
import com.sygic.aura.route.RouteSummary;
import com.sygic.aura.settings.data.SettingsManager;
import com.sygic.aura.views.WndManager;
import com.sygic.aura.views.font_specials.SImageView;
import com.sygic.aura.views.font_specials.STextView;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import loquendo.tts.engine.TTSConst;

public class QuickMenuView extends HorizontalScrollView implements OnGestureListener, OnGlobalLayoutListener, SoundMutedListener {
    private static boolean mLastState;
    private final List<DemoListener> mDemoStartedListeners;
    private final GestureDetector mGestureScanner;
    private final Handler mHandler;
    private LinearLayout mLayout;
    private final List<SoundMutedListener> mMuteChangeListeners;
    private int mPageCount;
    private int mPageWidth;
    private final Runnable mRunnable;
    private QuickMenuTimer mTimerThread;

    /* renamed from: com.sygic.aura.quickmenu.QuickMenuView.1 */
    class C14691 implements Runnable {
        C14691() {
        }

        public void run() {
            boolean isViaPointOnRoute = RouteSummary.nativeIsViaPointOnRoute();
            if (QuickMenuView.mLastState != isViaPointOnRoute) {
                QuickMenuView.this.layoutItems();
                if (QuickMenuView.this.mLayout != null) {
                    QuickMenuView.mLastState = isViaPointOnRoute;
                }
            }
            if (isViaPointOnRoute) {
                QuickMenuView.this.mHandler.postDelayed(this, 3000);
            } else {
                QuickMenuView.this.stopHasWaypointsTimer();
            }
        }
    }

    /* renamed from: com.sygic.aura.quickmenu.QuickMenuView.3 */
    static /* synthetic */ class C14703 {
        static final /* synthetic */ int[] f1268x2185c976;

        static {
            f1268x2185c976 = new int[QuickMenuItemType.values().length];
            try {
                f1268x2185c976[QuickMenuItemType.EMPTY_ITEM.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                f1268x2185c976[QuickMenuItemType.START_DEMO.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                f1268x2185c976[QuickMenuItemType.POI_SHOW_NEARBY.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
            try {
                f1268x2185c976[QuickMenuItemType.CANCEL_ROUTE.ordinal()] = 4;
            } catch (NoSuchFieldError e4) {
            }
            try {
                f1268x2185c976[QuickMenuItemType.SKIP_WAYPOINT.ordinal()] = 5;
            } catch (NoSuchFieldError e5) {
            }
            try {
                f1268x2185c976[QuickMenuItemType.MUTE_BUTTON.ordinal()] = 6;
            } catch (NoSuchFieldError e6) {
            }
            try {
                f1268x2185c976[QuickMenuItemType.PEDESTRIAN_BUTTON.ordinal()] = 7;
            } catch (NoSuchFieldError e7) {
            }
        }
    }

    static {
        mLastState = false;
    }

    public QuickMenuView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.mPageWidth = 0;
        this.mPageCount = 1;
        this.mDemoStartedListeners = Collections.synchronizedList(new LinkedList());
        this.mMuteChangeListeners = Collections.synchronizedList(new LinkedList());
        this.mHandler = new Handler();
        this.mRunnable = new C14691();
        this.mGestureScanner = new GestureDetector(context, this);
    }

    public QuickMenuView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mPageWidth = 0;
        this.mPageCount = 1;
        this.mDemoStartedListeners = Collections.synchronizedList(new LinkedList());
        this.mMuteChangeListeners = Collections.synchronizedList(new LinkedList());
        this.mHandler = new Handler();
        this.mRunnable = new C14691();
        this.mGestureScanner = new GestureDetector(context, this);
    }

    public QuickMenuView(Context context) {
        this(context, null);
    }

    protected void onFinishInflate() {
        super.onFinishInflate();
        if (!isInEditMode()) {
            ViewTreeObserver viewTreeObserver = getViewTreeObserver();
            if (viewTreeObserver.isAlive()) {
                viewTreeObserver.addOnGlobalLayoutListener(this);
            }
        }
    }

    private int getCurrentPage(float touchDistance) {
        return Math.round((((float) getScrollX()) + touchDistance) / ((float) this.mPageWidth));
    }

    private void scrollToPage(int page) {
        smoothScrollTo(this.mPageWidth * Math.max(0, Math.min(page, this.mPageCount - 1)), 0);
    }

    public boolean onDown(MotionEvent e) {
        if (this.mTimerThread != null) {
            this.mTimerThread.reset();
        }
        return false;
    }

    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        if (e1 == null || e2 == null) {
            return false;
        }
        if (Math.abs(velocityX) > 300.0f) {
            float distance = e2.getX() - e1.getX();
            if (-1.0f * distance > 5.0f) {
                scrollToPage(getCurrentPage(distance) + 1);
                return true;
            } else if (distance > 5.0f) {
                scrollToPage(getCurrentPage(distance) - 1);
                return true;
            }
        }
        return false;
    }

    public void onLongPress(MotionEvent e) {
    }

    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        if (this.mTimerThread != null) {
            this.mTimerThread.reset();
        }
        return false;
    }

    public void onShowPress(MotionEvent e) {
    }

    public boolean onSingleTapUp(MotionEvent e) {
        return false;
    }

    public boolean dispatchTouchEvent(MotionEvent event) {
        return this.mGestureScanner.onTouchEvent(event) || super.dispatchTouchEvent(event);
    }

    public boolean registerDemoStartListener(DemoListener demoStarted) {
        return this.mDemoStartedListeners.add(demoStarted);
    }

    public boolean unregisterDemoStartListener(DemoListener demoFinished) {
        return this.mDemoStartedListeners.remove(demoFinished);
    }

    public void resetItems() {
        layoutItems();
    }

    public void onGlobalLayout() {
        int newWidth = getWidth();
        if (newWidth != 0 && newWidth != this.mPageWidth) {
            this.mPageWidth = newWidth;
            layoutItems();
        }
    }

    public void layoutItems() {
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService("layout_inflater");
        if (this.mLayout == null) {
            this.mLayout = (LinearLayout) findViewById(2131624428);
        }
        this.mLayout.removeAllViews();
        int[] items = getAllItems(getContext());
        List<Integer> itemsList = new ArrayList();
        for (int valueOf : items) {
            itemsList.add(Integer.valueOf(valueOf));
        }
        if (!RouteSummary.nativeIsViaPointOnRoute()) {
            itemsList.remove(Integer.valueOf(QuickMenuItemType.SKIP_WAYPOINT.ordinal()));
        }
        int itemsPerPage = getItemsPerPage(getContext());
        this.mPageCount = ((itemsList.size() + itemsPerPage) - 1) / itemsPerPage;
        LayoutParams layoutparams = new LayoutParams(this.mPageWidth / itemsPerPage, -1);
        int i = 0;
        while (i < this.mPageCount * itemsPerPage) {
            View itemView = QuickMenuItems.createView(this, inflater, i < itemsList.size() ? QuickMenuItems.getType(((Integer) itemsList.get(i)).intValue()) : QuickMenuItemType.EMPTY_ITEM);
            itemView.setLayoutParams(layoutparams);
            this.mLayout.addView(itemView);
            i++;
        }
    }

    public void cancelDemo() {
        NavigationInfoBarScreen screen = (NavigationInfoBarScreen) OverlayScreen.getInstance(Mode.NAVIGATE_INFO_BAR);
        DemoManager.nativeDemonstrateStop();
        DemoManager.nativeSetDemoPaused(false);
        View menuItem = QuickMenuItems.findViewByType(this.mLayout, QuickMenuItemType.START_DEMO);
        if (menuItem != null) {
            notifyDemoListeners(true);
            QuickMenuItems.updateView(menuItem, QuickMenuItemType.START_DEMO);
            screen.cancelDemo();
        }
    }

    private void notifyDemoListeners(boolean demoStopped) {
        if (!this.mDemoStartedListeners.isEmpty()) {
            synchronized (this.mDemoStartedListeners) {
                for (DemoListener listener : this.mDemoStartedListeners) {
                    if (demoStopped) {
                        listener.onDemoStoped();
                    } else {
                        listener.onDemoStarted();
                    }
                }
            }
        }
    }

    public void onItemClick(View viewClicked, QuickMenuItemType type) {
        int resetView = 3;
        if (this.mTimerThread != null) {
            this.mTimerThread.cancel();
        }
        boolean hasValidRoute = RouteManager.nativeExistValidRoute();
        String strEventCategory = hasValidRoute ? "anui_navi_quickmenu_click" : "anui_freedrive_quickmenu_click";
        Bundle params = new Bundle();
        Context context = getContext();
        switch (C14703.f1268x2185c976[type.ordinal()]) {
            case TTSConst.TTSPARAGRAPH /*2*/:
                params.putString("eventName", "quickmenu_click_START_DEMONSTRATION");
                params.putString("category", strEventCategory);
                SygicAnalyticsLogger.logEvent(context, EventType.QUICK_MENU, params);
                params.clear();
                params.putString("eventName", hasValidRoute ? "navi_quickmenu_click" : "freedrive_quickmenu_click");
                params.putString("coreParams", "START_DEMONSTRATION:click:" + WndManager.nativeGetLogParams());
                params.putSerializable("eventType", EEventType.ETNone);
                SygicAnalyticsLogger.logEvent(context, EventType.QUICK_MENU_FLURRY, params);
                if (DemoManager.isStopped()) {
                    DemoManager.nativeDemonstrateStart(0);
                } else {
                    cancelDemo();
                }
                notifyDemoListeners(DemoManager.isStopped());
                QuickMenuItems.updateView(viewClicked, type);
                break;
            case TTSConst.TTSUNICODE /*3*/:
                params.putString("eventName", "quickmenu_click_SHOW_NEARBY");
                params.putString("category", strEventCategory);
                SygicAnalyticsLogger.logEvent(context, EventType.QUICK_MENU, params);
                params.clear();
                params.putString("eventName", hasValidRoute ? "navi_quickmenu_click" : "freedrive_quickmenu_click");
                params.putString("coreParams", "SHOW_NEARBY:click:" + WndManager.nativeGetLogParams());
                params.putSerializable("eventType", EEventType.ETNone);
                SygicAnalyticsLogger.logEvent(context, EventType.QUICK_MENU_FLURRY, params);
                resetView = 3 ^ 1;
                Bundle bundle = new Bundle();
                bundle.putParcelable(PoiFragment.ARG_DATA, PositionInfo.nativeGetLastValidPosition());
                Fragments.add((Activity) context, NearbyPoiCategoryFragment.class, "fragment_nearby_dash", bundle, true, (FragmentResultCallback) context);
                break;
            case TTSConst.TTSXML /*4*/:
                params.putString("eventName", "quickmenu_click_CANCEL_ROUTE");
                params.putString("category", strEventCategory);
                SygicAnalyticsLogger.logEvent(context, EventType.QUICK_MENU, params);
                params.clear();
                params.putString("eventName", hasValidRoute ? "navi_quickmenu_click" : "freedrive_quickmenu_click");
                params.putString("coreParams", "CANCEL_ROUTE:click:" + WndManager.nativeGetLogParams());
                params.putSerializable("eventType", EEventType.ETNone);
                SygicAnalyticsLogger.logEvent(context, EventType.QUICK_MENU_FLURRY, params);
                resetView = 3 ^ 3;
                RouteSummary.nativeCancelRoute();
                cancelDemo();
                break;
            case TTSConst.TTSEVT_TEXT /*5*/:
                params.putString("eventName", "quickmenu_click_SKIP_WAYPOINT");
                params.putString("category", strEventCategory);
                params.putString("coreParams", "SKIP_WAYPOINT:click:" + WndManager.nativeGetLogParams());
                params.putSerializable("eventType", EEventType.ETNone);
                SygicAnalyticsLogger.logEvent(context, EventType.QUICK_MENU, params);
                RouteSummary.nativeSkipViaPoint();
                break;
            case TTSConst.TTSEVT_SENTENCE /*6*/:
                boolean isMuted = SettingsManager.nativeIsMuted();
                String strMuteState = isMuted ? "_UNMUTE" : "_MUTE";
                String strIsMuted = isMuted ? "yes" : "no";
                params.putString("eventName", "quickmenu_click" + strMuteState);
                params.putString("category", strEventCategory);
                SygicAnalyticsLogger.logEvent(context, EventType.QUICK_MENU, params);
                params.clear();
                params.putString("eventName", hasValidRoute ? "navi_quickmenu_click" : "freedrive_quickmenu_click");
                params.putString("coreParams", "MUTE:" + strIsMuted + ":" + WndManager.nativeGetLogParams());
                params.putSerializable("eventType", EEventType.ETNone);
                SygicAnalyticsLogger.logEvent(context, EventType.QUICK_MENU_FLURRY, params);
                SettingsManager.nativeChangeMute();
                synchronized (this.mMuteChangeListeners) {
                    for (SoundMutedListener listener : this.mMuteChangeListeners) {
                        listener.onMuteChanged(!isMuted);
                    }
                    break;
                }
                break;
            case TTSConst.TTSEVT_BOOKMARK /*7*/:
                boolean isPedestrian = RouteSummary.nativeIsPedestrian();
                updatePedestrianButton(!isPedestrian);
                String strMode = isPedestrian ? "_VEHICLE" : "_PEDESTRIAN";
                String strIsPedestrian = isPedestrian ? "yes" : "no";
                params.putString("eventName", "quickmenu_click" + strMode);
                params.putString("category", strEventCategory);
                SygicAnalyticsLogger.logEvent(context, EventType.QUICK_MENU, params);
                params.clear();
                params.putString("eventName", hasValidRoute ? "navi_quickmenu_click" : "freedrive_quickmenu_click");
                params.putString("coreParams", "PEDESTRIAN:" + strIsPedestrian + ":" + WndManager.nativeGetLogParams());
                params.putSerializable("eventType", EEventType.ETNone);
                SygicAnalyticsLogger.logEvent(context, EventType.QUICK_MENU_FLURRY, params);
                if (!hasValidRoute) {
                    MapControlsManager.nativeSetNaviType(isPedestrian ? ENaviType.NtCar : ENaviType.NtPedestrian);
                    break;
                }
                RouteManager.nativeRouteRecompute(isPedestrian ? RouteComputeMode.MODE_CAR : RouteComputeMode.MODE_PEDESTRIAN);
                NavigationInfoBarScreen.setupSlots();
                break;
        }
        if ((resetView & 2) != 2) {
            return;
        }
        if (hasValidRoute) {
            MapOverlayFragment.setMode(context, Mode.NAVIGATE_INFO_BAR);
        } else {
            MapOverlayFragment.setMode(context, Mode.FREEDRIVE_INFO_BAR);
        }
    }

    private static int[] getAllItems(Context context) {
        if (!RouteManager.nativeExistValidRoute()) {
            return context.getResources().getIntArray(2131492883);
        }
        if (SettingsManager.nativeIsDebugEnabled()) {
            return context.getResources().getIntArray(2131492882);
        }
        return context.getResources().getIntArray(2131492881);
    }

    private static int getItemsPerPage(Context context) {
        return RouteManager.nativeExistValidRoute() ? context.getResources().getInteger(2131361792) : context.getResources().getInteger(2131361793);
    }

    private void updateMuteButton(boolean isMuted) {
        if (this.mLayout != null) {
            View itemView = QuickMenuItems.findViewByType(this.mLayout, QuickMenuItemType.MUTE_BUTTON);
            if (itemView != null) {
                SImageView imageView = (SImageView) itemView.findViewById(2131624565);
                STextView textView = (STextView) itemView.findViewById(2131624566);
                int iconId = isMuted ? 2131034261 : 2131034256;
                int textId = isMuted ? 2131165590 : 2131165583;
                Toast.makeText(getContext(), ResourceManager.getCoreString(getContext(), isMuted ? 2131165591 : 2131165592), 1).show();
                imageView.setFontDrawableResource(iconId);
                textView.setCoreText(textId);
            }
        }
    }

    public boolean registerMuteListener(List<SoundMutedListener> listeners) {
        return this.mMuteChangeListeners.addAll(listeners);
    }

    public boolean unregisterMuteListener(List<SoundMutedListener> listeners) {
        return this.mMuteChangeListeners.removeAll(listeners);
    }

    public void updatePedestrianButton(boolean isPedestrian) {
        if (this.mLayout != null) {
            View itemView = QuickMenuItems.findViewByType(this.mLayout, QuickMenuItemType.PEDESTRIAN_BUTTON);
            if (itemView != null) {
                SImageView imageView = (SImageView) itemView.findViewById(2131624565);
                STextView textView = (STextView) itemView.findViewById(2131624566);
                int iconId = isPedestrian ? 2131034255 : 2131034258;
                int textId = isPedestrian ? 2131165582 : 2131165585;
                imageView.setFontDrawableResource(iconId);
                textView.setCoreText(textId);
            }
        }
    }

    public void startHasViapointsTimer() {
        if (RouteSummary.nativeGetRouteWayPointsCount() > 0) {
            this.mHandler.postDelayed(this.mRunnable, 1000);
        }
    }

    public void stopHasWaypointsTimer() {
        mLastState = false;
        this.mHandler.removeCallbacks(this.mRunnable);
    }

    public void setPedestrianButtonEnabled(boolean enabled) {
        if (this.mLayout != null) {
            View itemView = QuickMenuItems.findViewByType(this.mLayout, QuickMenuItemType.PEDESTRIAN_BUTTON);
            if (itemView != null) {
                itemView.setEnabled(enabled);
            }
        }
    }

    public void onMuteChanged(boolean isMuted) {
        updateMuteButton(isMuted);
    }

    public static List<SoundMutedListener> getDefaultMuteChangeListeners() {
        List listeners = new ArrayList();
        listeners.add(OverlayScreen.getInstance(Mode.NAVIGATE_INFO_BAR));
        listeners.add(OverlayScreen.getInstance(Mode.FREEDRIVE_INFO_BAR));
        listeners.add(OverlayScreen.getInstance(Mode.FREEDRIVE_QUICK_MENU));
        listeners.add(OverlayScreen.getInstance(Mode.NAVIGATE_QUICK_MENU));
        return listeners;
    }

    public void setTimer(QuickMenuTimer timer) {
        this.mTimerThread = timer;
    }
}
