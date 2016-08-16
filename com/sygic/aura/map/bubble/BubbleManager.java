package com.sygic.aura.map.bubble;

import com.sygic.aura.helper.CrashlyticsHelper;
import com.sygic.aura.helper.EventReceivers.BubbleEventsReceiver;
import com.sygic.aura.helper.EventReceivers.MapEventsReceiver;
import com.sygic.aura.helper.EventReceivers.RouteEventsReceiver;
import com.sygic.aura.helper.interfaces.AlternativeRouteSelectedListener;
import com.sygic.aura.helper.interfaces.MapBubbleListener;
import com.sygic.aura.helper.interfaces.RouteCancelListener;
import com.sygic.aura.helper.interfaces.RouteEventsListener;
import com.sygic.aura.helper.interfaces.RouteSetListener;
import com.sygic.aura.helper.interfaces.TrafficBubbleListener;
import com.sygic.aura.map.data.BubbleInfo;
import com.sygic.aura.map.data.TrafficBubbleInfo;
import com.sygic.aura.map.data.map_selection.MapSelection;
import com.sygic.aura.route.RouteSummary;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class BubbleManager implements AlternativeRouteSelectedListener, MapBubbleListener, RouteCancelListener, RouteEventsListener, RouteSetListener, TrafficBubbleListener {
    private static BubbleManager sInstance;
    private final Map<Class, BubbleInterface> mBubbleAnimators;
    private ArrayList<OnBubbleShownListener> mBubbleShownListeners;
    protected boolean mBubblesEnabled;
    protected BubbleInterface mCurrentAnimator;
    protected boolean mUseSelectBubble;

    public interface OnBubbleShownListener {
        void onBubbleShown(boolean z);
    }

    static {
        sInstance = null;
    }

    public static BubbleManager getInstance() {
        if (sInstance == null) {
            sInstance = new BubbleManager();
        }
        return sInstance;
    }

    public static void start() {
        getInstance();
    }

    public static void stop() {
        if (sInstance != null) {
            sInstance.unregisterToReceivers();
            sInstance = null;
        }
    }

    protected BubbleManager() {
        this.mBubbleAnimators = new HashMap();
        this.mCurrentAnimator = null;
        this.mUseSelectBubble = false;
        this.mBubblesEnabled = true;
        setCurrentAnimator(MapBubbleController.class);
        registerToReceivers();
    }

    private void registerToReceivers() {
        BubbleEventsReceiver.registerMapBubbleListener(this);
        BubbleEventsReceiver.registerTrafficBubbleListener(this);
        RouteEventsReceiver.registerRouteEventsListener(this);
        RouteEventsReceiver.registerRouteSetListener(this);
        MapEventsReceiver.registerRouteCancelListener(this);
        RouteEventsReceiver.registerAlternativeRouteSelectedListener(this);
    }

    private void unregisterToReceivers() {
        BubbleEventsReceiver.unregisterMapBubbleListener(this);
        BubbleEventsReceiver.unregisterTrafficBubbleListener(this);
        RouteEventsReceiver.unregisterRouteEventsListener(this);
        RouteEventsReceiver.unregisterRouteSetListener(this);
        MapEventsReceiver.unregisterRouteCancelListener(this);
        RouteEventsReceiver.unregisterAlternativeRouteSelectedListener(this);
    }

    public void onShowBubble(BubbleInfo bubbleInfo) {
        if (this.mCurrentAnimator != null && this.mBubblesEnabled) {
            this.mCurrentAnimator.createBubble(bubbleInfo);
            notifyBubbleShownListeners(true);
        }
    }

    public void onHideBubble() {
        getAnimator(SelectBubbleController.class).removeBubble();
        getAnimator(MapBubbleController.class).removeBubble();
        getAnimator(RouteBubbleController.class).removeBubble();
        notifyBubbleShownListeners(false);
    }

    public void onMoveBubble() {
        if (this.mCurrentAnimator != null && this.mBubblesEnabled) {
            this.mCurrentAnimator.moveBubble();
        }
        getAnimator(TrafficBubbleController.class).moveBubble();
    }

    public void onRouteCanceled(Boolean user) {
        setCurrentAnimator(MapBubbleController.class);
        getAnimator(TrafficBubbleController.class).removeBubble();
    }

    public void onStartComputingProgress() {
        setCurrentAnimator(RouteBubbleController.class);
    }

    public void onRouteComputeFinishedAll() {
        if (RouteSummary.nativeGetRouteCount() <= 1) {
            setCurrentAnimator(RouteBubbleController.class);
        }
    }

    public void onCoreSetRoute() {
        setCurrentAnimator(MapBubbleController.class);
    }

    public void onAlternativeRouteSelected() {
        getAnimator(TrafficBubbleController.class).removeBubble();
        ((RouteBubbleController) getAnimator(RouteBubbleController.class)).onAlternativeSelected();
    }

    public void onTrafficBubble(TrafficBubbleInfo bubbleInfo) {
        getAnimator(TrafficBubbleController.class).createBubble(bubbleInfo);
    }

    public void hideTrafficBubble() {
        getAnimator(TrafficBubbleController.class).removeBubble();
    }

    private <T extends BubbleController> void setCurrentAnimator(Class<T> animatorClass) {
        if (this.mCurrentAnimator != null) {
            this.mCurrentAnimator.removeBubble();
        }
        if (this.mUseSelectBubble) {
            boolean routeAnim = animatorClass.equals(RouteBubbleController.class);
            this.mUseSelectBubble = !routeAnim;
            this.mCurrentAnimator = routeAnim ? getAnimator(animatorClass) : getAnimator(SelectBubbleController.class);
            return;
        }
        this.mCurrentAnimator = getAnimator(animatorClass);
    }

    private <T extends BubbleController> BubbleInterface getAnimator(Class<T> animatorClass) {
        BubbleInterface animator = (BubbleInterface) this.mBubbleAnimators.get(animatorClass);
        if (animator != null) {
            return animator;
        }
        try {
            animator = (BubbleInterface) animatorClass.newInstance();
            this.mBubbleAnimators.put(animatorClass, animator);
            return animator;
        } catch (InstantiationException e) {
            CrashlyticsHelper.logError("BUBBLE_MANAGER", e.getMessage());
            return animator;
        } catch (IllegalAccessException e2) {
            CrashlyticsHelper.logError("BUBBLE_MANAGER", e2.getMessage());
            return animator;
        }
    }

    public void setVisible(boolean visible) {
        if (this.mCurrentAnimator != null) {
            this.mCurrentAnimator.setVisible(visible);
        }
    }

    public void setTrafficBubblesVisible(boolean visible) {
        getAnimator(TrafficBubbleController.class).setVisible(visible);
    }

    public boolean isVisible() {
        if (this.mCurrentAnimator != null) {
            return this.mCurrentAnimator.isVisible();
        }
        return false;
    }

    public void checkHighlighting(int value) {
        getAnimator(RouteBubbleController.class).checkHighlighting(value);
    }

    public void useSelectBubble(boolean value) {
        this.mCurrentAnimator.removeBubble();
        this.mUseSelectBubble = value;
        setCurrentAnimator(MapBubbleController.class);
    }

    public void setEnabled(boolean value) {
        this.mBubblesEnabled = value;
    }

    public void onFinishComputingProgress() {
    }

    public MapSelection getSelection() {
        return ((SelectBubbleController) getAnimator(SelectBubbleController.class)).getSelection();
    }

    private void notifyBubbleShownListeners(boolean shown) {
        if (this.mBubbleShownListeners != null && !this.mBubbleShownListeners.isEmpty()) {
            Iterator it = this.mBubbleShownListeners.iterator();
            while (it.hasNext()) {
                ((OnBubbleShownListener) it.next()).onBubbleShown(shown);
            }
        }
    }

    public void unregisterOnBubbleShownListener(OnBubbleShownListener listener) {
        if (this.mBubbleShownListeners != null) {
            this.mBubbleShownListeners.remove(listener);
        }
    }

    public void registerOnBubbleShownListener(OnBubbleShownListener listener) {
        if (this.mBubbleShownListeners == null) {
            this.mBubbleShownListeners = new ArrayList();
        }
        this.mBubbleShownListeners.add(listener);
    }
}
