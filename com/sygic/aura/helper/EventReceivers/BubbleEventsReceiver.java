package com.sygic.aura.helper.EventReceivers;

import com.sygic.aura.helper.NativeMethodsHelper;
import com.sygic.aura.helper.interfaces.MapBubbleListener;
import com.sygic.aura.helper.interfaces.ScoutComputeListener;
import com.sygic.aura.helper.interfaces.TrafficBubbleListener;
import com.sygic.aura.map.data.BubbleInfo;
import com.sygic.aura.map.data.TrafficBubbleInfo;
import com.sygic.aura.map.screen.NavigationInfoBarScreen;

public class BubbleEventsReceiver extends NativeMethodsHelper {

    /* renamed from: com.sygic.aura.helper.EventReceivers.BubbleEventsReceiver.1 */
    static class C12651 implements Runnable {
        C12651() {
        }

        public void run() {
            NavigationInfoBarScreen.onInvalidateTrafficDialog();
        }
    }

    public static void registerScoutComputeEventsListener(ScoutComputeListener listener) {
        NativeMethodsHelper.registerListener(ScoutComputeListener.class, listener);
    }

    public static void unregisterScoutComputeEventsListener(ScoutComputeListener listener) {
        NativeMethodsHelper.unregisterListener(ScoutComputeListener.class, listener);
    }

    public static void registerMapBubbleListener(MapBubbleListener listener) {
        NativeMethodsHelper.registerListener(MapBubbleListener.class, listener);
    }

    public static void unregisterMapBubbleListener(MapBubbleListener listener) {
        NativeMethodsHelper.unregisterListener(MapBubbleListener.class, listener);
    }

    public static void registerTrafficBubbleListener(TrafficBubbleListener listener) {
        NativeMethodsHelper.registerListener(TrafficBubbleListener.class, listener);
    }

    public static void unregisterTrafficBubbleListener(TrafficBubbleListener listener) {
        NativeMethodsHelper.unregisterListener(TrafficBubbleListener.class, listener);
    }

    private static void onShowBubble(BubbleInfo bubbleInfo) {
        NativeMethodsHelper.callMethodWhenReady(MapBubbleListener.class, "onShowBubble", bubbleInfo);
    }

    private static void onHideBubble() {
        NativeMethodsHelper.callMethodWhenReady(MapBubbleListener.class, "onHideBubble", new Object[0]);
    }

    private static void onMoveBubble() {
        NativeMethodsHelper.callMethodWhenReady(MapBubbleListener.class, "onMoveBubble", new Object[0]);
    }

    private static void onTrafficBubble(TrafficBubbleInfo bubbleInfo) {
        NativeMethodsHelper.callMethodWhenReady(TrafficBubbleListener.class, "onTrafficBubble", bubbleInfo);
    }

    private static void hideTrafficBubble() {
        NativeMethodsHelper.callMethodWhenReady(TrafficBubbleListener.class, "hideTrafficBubble", new Object[0]);
    }

    public static void invalidateScoutViews() {
        sHandler.post(new C12651());
    }

    private static void onScoutComputeRouteReady(int nTimeDiff, int nDistanceDiff) {
        NativeMethodsHelper.callMethodWhenReady(ScoutComputeListener.class, "onScoutComputeRouteReady", Integer.valueOf(nTimeDiff), Integer.valueOf(nDistanceDiff));
    }
}
