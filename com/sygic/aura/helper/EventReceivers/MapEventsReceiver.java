package com.sygic.aura.helper.EventReceivers;

import com.sygic.aura.data.LongPosition;
import com.sygic.aura.helper.CrashlyticsHelper;
import com.sygic.aura.helper.NativeMethodsHelper;
import com.sygic.aura.helper.interfaces.ActionControlFragmentListener;
import com.sygic.aura.helper.interfaces.ActionControlHolderListener;
import com.sygic.aura.helper.interfaces.ColorSchemeChangeListener;
import com.sygic.aura.helper.interfaces.InvokeCommandListener;
import com.sygic.aura.helper.interfaces.MapClickListener;
import com.sygic.aura.helper.interfaces.MapUpdateListener;
import com.sygic.aura.helper.interfaces.MemoListener;
import com.sygic.aura.helper.interfaces.NotifyCenterListener;
import com.sygic.aura.helper.interfaces.PoiDetailInfoListener;
import com.sygic.aura.helper.interfaces.RecomputeListener;
import com.sygic.aura.helper.interfaces.RouteCancelListener;
import com.sygic.aura.helper.interfaces.RouteFinishListener;
import com.sygic.aura.helper.interfaces.SignpostChangeListener;
import com.sygic.aura.helper.interfaces.SpeedLimitListener;
import com.sygic.aura.helper.interfaces.TrafficChangeListener;
import com.sygic.aura.helper.interfaces.TrafficProgressSegmentsListener;
import com.sygic.aura.helper.interfaces.TrafficReceivedListener;
import com.sygic.aura.helper.interfaces.UnlockNaviListener;
import com.sygic.aura.helper.interfaces.VehicleClickListener;
import com.sygic.aura.helper.interfaces.WarningChangeListener;
import com.sygic.aura.map.data.MemoItem.EMemoType;
import com.sygic.aura.map.data.SignpostItem;
import com.sygic.aura.map.data.SpeedInfoItem;
import com.sygic.aura.map.data.map_selection.MapSelection;
import com.sygic.aura.poi.OnlinePoiInfoEntry;
import com.sygic.aura.route.data.TrafficItem;
import com.sygic.aura.route.data.WarningItem;
import com.sygic.aura.settings.data.SettingsManager.EColorScheme;
import java.util.ArrayList;
import loquendo.tts.engine.TTSConst;

public class MapEventsReceiver extends NativeMethodsHelper {
    public static final String TAG = "MapEventsReceiver";

    /* renamed from: com.sygic.aura.helper.EventReceivers.MapEventsReceiver.1 */
    static /* synthetic */ class C12661 {
        static final /* synthetic */ int[] $SwitchMap$com$sygic$aura$map$data$MemoItem$EMemoType;

        static {
            $SwitchMap$com$sygic$aura$map$data$MemoItem$EMemoType = new int[EMemoType.values().length];
            try {
                $SwitchMap$com$sygic$aura$map$data$MemoItem$EMemoType[EMemoType.memoHome.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                $SwitchMap$com$sygic$aura$map$data$MemoItem$EMemoType[EMemoType.memoWork.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                $SwitchMap$com$sygic$aura$map$data$MemoItem$EMemoType[EMemoType.memoParking.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
        }
    }

    public static void registerSignpostChangeListener(SignpostChangeListener listener) {
        NativeMethodsHelper.registerListener(SignpostChangeListener.class, listener);
    }

    public static void unregisterSignpostChangeListener(SignpostChangeListener listener) {
        NativeMethodsHelper.unregisterListener(SignpostChangeListener.class, listener);
    }

    private static void onSignpostChange(Object obj) {
        ArrayList<SignpostItem> arr = new ArrayList();
        if (obj != null) {
            for (SignpostItem item : (SignpostItem[]) obj) {
                arr.add(item);
            }
        }
        NativeMethodsHelper.callMethodWhenReady(SignpostChangeListener.class, "onSignpostChange", arr);
    }

    public static void registerUnlockNaviListener(UnlockNaviListener listener) {
        NativeMethodsHelper.registerListener(UnlockNaviListener.class, listener);
    }

    public static void unregisterUnlockNaviListener(UnlockNaviListener listener) {
        NativeMethodsHelper.unregisterListener(UnlockNaviListener.class, listener);
    }

    private static void onLockNavi() {
        NativeMethodsHelper.callMethodWhenReady(UnlockNaviListener.class, "onLockNavi", new Object[0]);
    }

    private static void onUnlockNavi() {
        NativeMethodsHelper.callMethodWhenReady(UnlockNaviListener.class, "onUnlockNavi", new Object[0]);
    }

    public static void registerMapClickListener(MapClickListener listener) {
        NativeMethodsHelper.registerListener(MapClickListener.class, listener);
    }

    public static void unregisterMapClickListener(MapClickListener listener) {
        NativeMethodsHelper.unregisterListener(MapClickListener.class, listener);
    }

    private static void onMapClick() {
        NativeMethodsHelper.callMethodWhenReady(MapClickListener.class, "onMapClick", new Object[0]);
    }

    public static void registerVehicleClickListener(VehicleClickListener listener) {
        NativeMethodsHelper.registerListener(VehicleClickListener.class, listener);
    }

    public static void unregisterVehicleClickListener(VehicleClickListener listener) {
        NativeMethodsHelper.unregisterListener(VehicleClickListener.class, listener);
    }

    private static void onVehicleClick(MapSelection mapSelection, String poiDescription) {
        NativeMethodsHelper.callMethodWhenReady(VehicleClickListener.class, "onVehicleClick", mapSelection, poiDescription);
    }

    public static void registerMemoListener(MemoListener listener) {
        NativeMethodsHelper.registerListener(MemoListener.class, listener);
    }

    public static void unregisterMemoListener(MemoListener listener) {
        NativeMethodsHelper.unregisterListener(MemoListener.class, listener);
    }

    public static void onMemoRemoved(int widgetItemId) {
        NativeMethodsHelper.callMethodWhenReady(MemoListener.class, "onMemoRemoved", Integer.valueOf(widgetItemId));
    }

    public static void onMemoAdded(LongPosition longPosition, String strName, EMemoType type) {
        String methodName;
        switch (C12661.$SwitchMap$com$sygic$aura$map$data$MemoItem$EMemoType[type.ordinal()]) {
            case TTSConst.TTSMULTILINE /*1*/:
                methodName = "onAddHome";
                break;
            case TTSConst.TTSPARAGRAPH /*2*/:
                methodName = "onAddWork";
                break;
            case TTSConst.TTSUNICODE /*3*/:
                methodName = "onAddParkingLocation";
                break;
            default:
                CrashlyticsHelper.logWarning(TAG, "Unsupported callback");
                return;
        }
        NativeMethodsHelper.callMethodWhenReady(MemoListener.class, methodName, longPosition, strName);
    }

    public static void registerNotificationCenterListener(NotifyCenterListener listener) {
        NativeMethodsHelper.registerListener(NotifyCenterListener.class, listener);
    }

    public static void unregisterNotificationCenterListener(NotifyCenterListener listener) {
        NativeMethodsHelper.unregisterListener(NotifyCenterListener.class, listener);
    }

    private static void onNotifyCenterChange(int mask) {
        NativeMethodsHelper.callMethodWhenReady(NotifyCenterListener.class, "onNotifyCenterChange", Integer.valueOf(mask));
    }

    public static void registerTrafficChangeListener(TrafficChangeListener listener) {
        NativeMethodsHelper.registerListener(TrafficChangeListener.class, listener);
    }

    public static void unregisterTrafficChangeListener(TrafficChangeListener listener) {
        NativeMethodsHelper.unregisterListener(TrafficChangeListener.class, listener);
    }

    private static void onTrafficChange(TrafficItem trafficItem) {
        NativeMethodsHelper.callMethodWhenReady(TrafficChangeListener.class, "onTrafficChange", trafficItem);
    }

    public static void registerTrafficReceivedListener(TrafficReceivedListener listener) {
        NativeMethodsHelper.registerListener(TrafficReceivedListener.class, listener);
    }

    public static void unregisterTrafficReceivedListener(TrafficReceivedListener listener) {
        NativeMethodsHelper.unregisterListener(TrafficReceivedListener.class, listener);
    }

    private static void onTrafficReceived() {
        NativeMethodsHelper.callMethodWhenReady(TrafficReceivedListener.class, "onTrafficReceived", new Object[0]);
    }

    public static void registerTrafficSegmentsListener(TrafficProgressSegmentsListener listener) {
        NativeMethodsHelper.registerListener(TrafficProgressSegmentsListener.class, listener);
    }

    public static void unregisterTrafficSegmentsListener(TrafficProgressSegmentsListener listener) {
        NativeMethodsHelper.unregisterListener(TrafficProgressSegmentsListener.class, listener);
    }

    private static void onTrafficSegmentsComputed(float[] segments, int[] colors) {
        ArrayList<Float> arrSegments = new ArrayList();
        for (float valueOf : segments) {
            arrSegments.add(Float.valueOf(valueOf));
        }
        ArrayList<Integer> arrColors = new ArrayList();
        for (int valueOf2 : colors) {
            arrColors.add(Integer.valueOf(valueOf2));
        }
        NativeMethodsHelper.callMethodWhenReady(TrafficProgressSegmentsListener.class, "onTrafficSegmentsComputed", arrSegments, arrColors);
    }

    public static void registerWarningChangeListener(WarningChangeListener listener) {
        NativeMethodsHelper.registerListener(WarningChangeListener.class, listener);
    }

    public static void unregisterWarningChangeListener(WarningChangeListener listener) {
        NativeMethodsHelper.unregisterListener(WarningChangeListener.class, listener);
    }

    private static void onWarningChange(WarningItem warningItem) {
        if (warningItem == null) {
            NativeMethodsHelper.callMethodWhenReady(WarningChangeListener.class, "onWarningChange", new Object[0]);
            return;
        }
        NativeMethodsHelper.callMethodWhenReady(WarningChangeListener.class, "onWarningChange", warningItem);
    }

    public static void registerRouteCancelListener(RouteCancelListener listener) {
        NativeMethodsHelper.registerListener(RouteCancelListener.class, listener);
    }

    public static void unregisterRouteCancelListener(RouteCancelListener listener) {
        NativeMethodsHelper.unregisterListener(RouteCancelListener.class, listener);
    }

    private static void onRouteCanceled(boolean user) {
        NativeMethodsHelper.callMethodWhenReady(RouteCancelListener.class, "onRouteCanceled", Boolean.valueOf(user));
    }

    public static void registerRouteFinishListener(RouteFinishListener listener) {
        NativeMethodsHelper.registerListener(RouteFinishListener.class, listener);
    }

    public static void unregisterRouteFinishListener(RouteFinishListener listener) {
        NativeMethodsHelper.unregisterListener(RouteFinishListener.class, listener);
    }

    private static void onRouteFinished() {
        NativeMethodsHelper.callMethodWhenReady(RouteFinishListener.class, "onRouteFinished", new Object[0]);
    }

    private static void onRouteFinishedSoft() {
        NativeMethodsHelper.callMethodWhenReady(RouteFinishListener.class, "onRouteFinishedSoft", new Object[0]);
    }

    public static void registerRecomputeListener(RecomputeListener listener) {
        NativeMethodsHelper.registerListener(RecomputeListener.class, listener);
    }

    public static void unregisterRecomputeListener(RecomputeListener listener) {
        NativeMethodsHelper.unregisterListener(RecomputeListener.class, listener);
    }

    private static void onRecomputeStarted() {
        NativeMethodsHelper.callMethodWhenReady(RecomputeListener.class, "onRecomputeStarted", new Object[0]);
    }

    private static void onRecomputeFinished() {
        NativeMethodsHelper.callMethodWhenReady(RecomputeListener.class, "onRecomputeFinished", new Object[0]);
    }

    public static void registerSpeedLimitListener(SpeedLimitListener listener) {
        NativeMethodsHelper.registerListener(SpeedLimitListener.class, listener);
    }

    public static void unregisterSpeedLimitListener(SpeedLimitListener listener) {
        NativeMethodsHelper.unregisterListener(SpeedLimitListener.class, listener);
    }

    private static void onSpeedLimitChanged(SpeedInfoItem items) {
        NativeMethodsHelper.callMethodWhenReady(SpeedLimitListener.class, "onSpeedLimitChanged", items);
    }

    public static void registerInvokeCommandListener(InvokeCommandListener listener) {
        NativeMethodsHelper.registerListener(InvokeCommandListener.class, listener);
    }

    public static void unregisterInvokeCommandListener(InvokeCommandListener listener) {
        NativeMethodsHelper.unregisterListener(InvokeCommandListener.class, listener);
    }

    private static void onStoreInvoked(int command, String id) {
        NativeMethodsHelper.callMethodWhenReady(InvokeCommandListener.class, "onStoreInvoked", Integer.valueOf(command), id);
    }

    private static void onCloseFragments() {
        NativeMethodsHelper.callMethodWhenReady(InvokeCommandListener.class, "onCloseFragments", new Object[0]);
    }

    private static void onDownloadMap(String strIso) {
        NativeMethodsHelper.callMethodWhenReady(InvokeCommandListener.class, "onDownloadMap", strIso);
    }

    public static void registerPoiDetailInfoListener(PoiDetailInfoListener listener) {
        NativeMethodsHelper.registerListener(PoiDetailInfoListener.class, listener);
    }

    public static void unregisterPoiDetailInfoListener(PoiDetailInfoListener listener) {
        NativeMethodsHelper.unregisterListener(PoiDetailInfoListener.class, listener);
    }

    private static void onUpdateOnlineInfo(OnlinePoiInfoEntry entry) {
        NativeMethodsHelper.callMethodWhenReady(PoiDetailInfoListener.class, "onUpdateOnlineInfo", entry);
    }

    public static void registerActionControlHolderListener(ActionControlHolderListener listener) {
        NativeMethodsHelper.registerListener(ActionControlHolderListener.class, listener);
    }

    public static void unregisterActionControlHolderListener(ActionControlHolderListener listener) {
        NativeMethodsHelper.unregisterListener(ActionControlHolderListener.class, listener);
    }

    private static void onLastMileParkingAvailable(MapSelection mapSel, String title, String icon) {
        NativeMethodsHelper.callMethodWhenReady(ActionControlHolderListener.class, "onLastMileParkingAvailable", mapSel, title, icon);
    }

    private static void onShowParkingPlaces(MapSelection mapSel, String title, String icon) {
        NativeMethodsHelper.callMethodWhenReady(ActionControlHolderListener.class, "onShowParkingPlaces", mapSel, title, icon);
    }

    public static void registerActionControlFragmentListener(ActionControlFragmentListener listener) {
        NativeMethodsHelper.registerListener(ActionControlFragmentListener.class, listener);
    }

    public static void unregisterActionControlFragmentListener(ActionControlFragmentListener listener) {
        NativeMethodsHelper.unregisterListener(ActionControlFragmentListener.class, listener);
    }

    private static void onPoiOnRouteSelected(MapSelection mapSel, String title, String icon) {
        NativeMethodsHelper.callMethodWhenReady(ActionControlFragmentListener.class, "onPoiSelectionChanged", mapSel, title, icon);
    }

    public static void registerColorSchemeChangeListener(ColorSchemeChangeListener listener) {
        NativeMethodsHelper.registerListener(ColorSchemeChangeListener.class, listener);
    }

    public static void unregisterColorSchemeChangeListener(ColorSchemeChangeListener listener) {
        NativeMethodsHelper.unregisterListener(ColorSchemeChangeListener.class, listener);
    }

    private static void onColorSchemeChanged(int colorScheme) {
        EColorScheme scheme = EColorScheme.values()[colorScheme];
        NativeMethodsHelper.callMethodWhenReady(ColorSchemeChangeListener.class, "onColorSchemeChanged", scheme);
    }

    public static void registerOnMapUpdateListener(MapUpdateListener listener) {
        NativeMethodsHelper.registerListener(MapUpdateListener.class, listener);
    }

    public static void unregisterOnMapUpdateListener(MapUpdateListener listener) {
        NativeMethodsHelper.unregisterListener(MapUpdateListener.class, listener);
    }

    private static void onMapUpdateCountAvailable(int count) {
        NativeMethodsHelper.callMethodWhenReady(MapUpdateListener.class, "onMapUpdateCount", Integer.valueOf(count));
    }
}
