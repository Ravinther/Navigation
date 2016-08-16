package com.sygic.aura.helper.EventReceivers;

import com.sygic.aura.helper.NativeMethodsHelper;
import com.sygic.aura.helper.interfaces.AlternativeRouteSelectedListener;
import com.sygic.aura.helper.interfaces.DirectionChangeListener;
import com.sygic.aura.helper.interfaces.JunctionChangeListener;
import com.sygic.aura.helper.interfaces.RouteAvoidUnableListener;
import com.sygic.aura.helper.interfaces.RouteCommandListener;
import com.sygic.aura.helper.interfaces.RouteComputeErrorListener;
import com.sygic.aura.helper.interfaces.RouteDataChangeListener;
import com.sygic.aura.helper.interfaces.RouteEventsListener;
import com.sygic.aura.helper.interfaces.RoutePartChangeListener;
import com.sygic.aura.helper.interfaces.RouteSetListener;
import com.sygic.aura.helper.interfaces.RouteWaypointReachedListener;
import com.sygic.aura.helper.interfaces.SpeedCamsReadyListener;
import com.sygic.aura.map.data.map_selection.MapSelection;
import com.sygic.aura.route.RouteManager.AvoidType;
import com.sygic.aura.route.RouteManager.RouteComputeMode;
import com.sygic.aura.route.data.DirectionStatus;
import com.sygic.aura.route.data.SpeedCamItem;
import com.sygic.aura.search.model.data.ListItem;
import java.util.ArrayList;
import java.util.Collections;

public class RouteEventsReceiver extends NativeMethodsHelper {
    public static void registerRouteEventsListener(RouteEventsListener listener) {
        NativeMethodsHelper.registerListener(RouteEventsListener.class, listener);
    }

    public static void unregisterRouteEventsListener(RouteEventsListener listener) {
        NativeMethodsHelper.unregisterListener(RouteEventsListener.class, listener);
    }

    public static void registerRouteSetListener(RouteSetListener listener) {
        NativeMethodsHelper.registerListener(RouteSetListener.class, listener);
    }

    public static void unregisterRouteSetListener(RouteSetListener listener) {
        NativeMethodsHelper.unregisterListener(RouteSetListener.class, listener);
    }

    public static void registerDirectionChangeListener(DirectionChangeListener listener) {
        NativeMethodsHelper.registerListener(DirectionChangeListener.class, listener);
    }

    public static void unregisterDirectionChangeListener(DirectionChangeListener listener) {
        NativeMethodsHelper.unregisterListener(DirectionChangeListener.class, listener);
    }

    public static void registerJunctionChangeListener(JunctionChangeListener listener) {
        NativeMethodsHelper.registerListener(JunctionChangeListener.class, listener);
    }

    public static void unregisterJunctionChangeListener(JunctionChangeListener listener) {
        NativeMethodsHelper.unregisterListener(JunctionChangeListener.class, listener);
    }

    public static void registerRoutePartChangeListener(RoutePartChangeListener listener) {
        NativeMethodsHelper.registerListener(RoutePartChangeListener.class, listener);
    }

    public static void unregisterRoutePartChangeListener(RoutePartChangeListener listener) {
        NativeMethodsHelper.unregisterListener(RoutePartChangeListener.class, listener);
    }

    public static void registerRouteDataChangeListener(RouteDataChangeListener listener) {
        NativeMethodsHelper.registerListener(RouteDataChangeListener.class, listener);
    }

    public static void unregisterRouteDataChangeListener(RouteDataChangeListener listener) {
        NativeMethodsHelper.unregisterListener(RouteDataChangeListener.class, listener);
    }

    public static void registerRouteCommandListener(RouteCommandListener listener) {
        NativeMethodsHelper.registerListener(RouteCommandListener.class, listener);
    }

    public static void unregisterRouteCommandListener(RouteCommandListener listener) {
        NativeMethodsHelper.unregisterListener(RouteCommandListener.class, listener);
    }

    public static void registerRouteAvoidUnableListener(RouteAvoidUnableListener listener) {
        NativeMethodsHelper.registerListener(RouteAvoidUnableListener.class, listener);
    }

    public static void unregisterRouteAvoidUnableListener(RouteAvoidUnableListener listener) {
        NativeMethodsHelper.unregisterListener(RouteAvoidUnableListener.class, listener);
    }

    public static void registerRouteComputeErrorListener(RouteComputeErrorListener listener) {
        NativeMethodsHelper.registerListener(RouteComputeErrorListener.class, listener);
    }

    public static void unregisterRouteComputeErrorListener(RouteComputeErrorListener listener) {
        NativeMethodsHelper.unregisterListener(RouteComputeErrorListener.class, listener);
    }

    public static void registerAlternativeRouteSelectedListener(AlternativeRouteSelectedListener listener) {
        NativeMethodsHelper.registerListener(AlternativeRouteSelectedListener.class, listener);
    }

    public static void unregisterAlternativeRouteSelectedListener(AlternativeRouteSelectedListener listener) {
        NativeMethodsHelper.unregisterListener(AlternativeRouteSelectedListener.class, listener);
    }

    public static void registerSpeedCamsReadyListener(SpeedCamsReadyListener listener) {
        NativeMethodsHelper.registerListener(SpeedCamsReadyListener.class, listener);
    }

    public static void unregisterSpeedCamsReadyListener(SpeedCamsReadyListener listener) {
        NativeMethodsHelper.unregisterListener(SpeedCamsReadyListener.class, listener);
    }

    public static void registerWaypointReachedListener(RouteWaypointReachedListener listener) {
        NativeMethodsHelper.registerListener(RouteWaypointReachedListener.class, listener);
    }

    public static void unregisterWaypointReachedListener(RouteWaypointReachedListener listener) {
        NativeMethodsHelper.unregisterListener(RouteWaypointReachedListener.class, listener);
    }

    private static void onRoutePartChange(int index) {
        NativeMethodsHelper.callMethodWhenReady(RoutePartChangeListener.class, "onRoutePartChange", Integer.valueOf(index));
    }

    private static void onDirectionChange(Object obj) {
        NativeMethodsHelper.callMethodWhenReady(DirectionChangeListener.class, "onDirectionChange", (DirectionStatus) obj);
    }

    private static void onJunctionViewChanged(boolean shown) {
        NativeMethodsHelper.callMethodWhenReady(JunctionChangeListener.class, "onJunctionViewChanged", Boolean.valueOf(shown));
    }

    private static void onStartComputingProgress() {
        NativeMethodsHelper.callMethodWhenReady(RouteEventsListener.class, "onStartComputingProgress", new Object[0]);
    }

    private static void onFinishComputingProgress() {
        NativeMethodsHelper.callMethodWhenReady(RouteEventsListener.class, "onFinishComputingProgress", new Object[0]);
    }

    private static void onAlternativeRouteSelected() {
        NativeMethodsHelper.callMethodWhenReady(AlternativeRouteSelectedListener.class, "onAlternativeRouteSelected", new Object[0]);
    }

    private static void onRouteComputeFinishedAll() {
        NativeMethodsHelper.callMethodWhenReady(RouteEventsListener.class, "onRouteComputeFinishedAll", new Object[0]);
    }

    private static void onRouteComputeError(String msg) {
        NativeMethodsHelper.callMethodWhenReady(RouteComputeErrorListener.class, "onRouteComputeError", msg);
    }

    private static void onAvoidUnable(int avoidType) {
        if (AvoidType.values()[avoidType].ordinal() != avoidType) {
            throw new IllegalStateException("Enum value mismatch");
        }
        NativeMethodsHelper.callMethodWhenReady(RouteAvoidUnableListener.class, "onAvoidUnable", AvoidType.values()[avoidType]);
    }

    private static void onWaypointInserted(int position, ListItem[] items) {
        ArrayList<ListItem> arr = new ArrayList();
        for (ListItem item : items) {
            arr.add(item);
        }
        NativeMethodsHelper.callMethodWhenReady(RouteDataChangeListener.class, "onWaypointInserted", Integer.valueOf(position), arr);
    }

    private static void onWaypointRemoved(int position) {
        NativeMethodsHelper.callMethodWhenReady(RouteDataChangeListener.class, "onWaypointRemoved", Integer.valueOf(position));
    }

    private static void onFinishChanged(ListItem[] items) {
        ArrayList<ListItem> arr = new ArrayList();
        for (ListItem item : items) {
            arr.add(item);
        }
        NativeMethodsHelper.callMethodWhenReady(RouteDataChangeListener.class, "onFinishChanged", arr);
    }

    private static void onComputeAfterStart() {
        NativeMethodsHelper.callMethodWhenReady(RouteCommandListener.class, "onComputeAfterStart", new Object[0]);
    }

    private static void onNavigateThere(MapSelection[] mapSelections, int type) {
        ArrayList<MapSelection> arr = new ArrayList();
        for (MapSelection mapSel : mapSelections) {
            arr.add(mapSel);
        }
        if (RouteComputeMode.values()[type].ordinal() != type) {
            throw new IllegalStateException("Enum value mismatch");
        }
        NativeMethodsHelper.callMethodWhenReady(RouteCommandListener.class, "onNavigateThere", arr, RouteComputeMode.values()[type]);
    }

    private static void onComputeSetRoute() {
        NativeMethodsHelper.callMethodWhenReady(RouteCommandListener.class, "onComputeSetRoute", new Object[0]);
    }

    private static void onCoreSetRoute() {
        NativeMethodsHelper.callMethodWhenReady(RouteSetListener.class, "onCoreSetRoute", new Object[0]);
    }

    private static void onRouteCamsReady(SpeedCamItem[] items) {
        Collections.addAll(new ArrayList(), items);
        NativeMethodsHelper.callMethodWhenReady(SpeedCamsReadyListener.class, "onSpeedCamsReady", arr);
    }

    private static void onWaypointReached() {
        NativeMethodsHelper.callMethodWhenReady(RouteWaypointReachedListener.class, "onWaypointReached", new Object[0]);
    }
}
