package com.sygic.aura.map;

import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import com.sygic.aura.SygicProject;
import com.sygic.aura.data.LongPosition;
import com.sygic.aura.helper.ObjectHandler;
import com.sygic.aura.helper.ObjectHandler.Callback;
import com.sygic.aura.helper.ObjectHandler.VoidCallback;
import com.sygic.aura.helper.SygicHelper;
import com.sygic.aura.incidents.IncidentItemsHelper.IncidentItemType;
import com.sygic.aura.map.data.map_selection.MapSelection;
import com.sygic.aura.map.view.ThreeStateButton;
import com.sygic.aura.quickmenu.QuickMenuTimer;
import com.sygic.aura.views.font_specials.SImageButton;
import com.sygic.aura.views.font_specials.SToggleButton;
import com.sygic.aura.views.font_specials.SToggleButton.OnCheckedChangeListener;

public class MapControlsManager {

    /* renamed from: com.sygic.aura.map.MapControlsManager.15 */
    static class AnonymousClass15 implements VoidCallback {
        final /* synthetic */ LongPosition val$position;
        final /* synthetic */ IncidentItemType val$type;

        AnonymousClass15(IncidentItemType incidentItemType, LongPosition longPosition) {
            this.val$type = incidentItemType;
            this.val$position = longPosition;
        }

        public void getMethod() {
            MapControlsManager.ReportIncident(this.val$type.getValue(), this.val$position.toNativeLong());
        }
    }

    /* renamed from: com.sygic.aura.map.MapControlsManager.18 */
    static class AnonymousClass18 implements VoidCallback {
        final /* synthetic */ float val$fRotation;

        AnonymousClass18(float f) {
            this.val$fRotation = f;
        }

        public void getMethod() {
            MapControlsManager.SetViewRotation(this.val$fRotation);
        }
    }

    /* renamed from: com.sygic.aura.map.MapControlsManager.19 */
    static class AnonymousClass19 implements VoidCallback {
        final /* synthetic */ float val$fRotation;

        AnonymousClass19(float f) {
            this.val$fRotation = f;
        }

        public void getMethod() {
            MapControlsManager.SetWantedRotation(this.val$fRotation);
        }
    }

    /* renamed from: com.sygic.aura.map.MapControlsManager.1 */
    static class C12951 implements VoidCallback {
        C12951() {
        }

        public void getMethod() {
            MapControlsManager.ZoomOut();
        }
    }

    /* renamed from: com.sygic.aura.map.MapControlsManager.23 */
    static class AnonymousClass23 implements VoidCallback {
        final /* synthetic */ float val$fTilt;

        AnonymousClass23(float f) {
            this.val$fTilt = f;
        }

        public void getMethod() {
            MapControlsManager.SetWantedUserTilt(this.val$fTilt);
        }
    }

    /* renamed from: com.sygic.aura.map.MapControlsManager.24 */
    static class AnonymousClass24 implements Callback<Long> {
        final /* synthetic */ LongPosition val$longPosition;

        AnonymousClass24(LongPosition longPosition) {
            this.val$longPosition = longPosition;
        }

        public Long getMethod() {
            return Long.valueOf(MapControlsManager.GetDistanceFromVehicle(this.val$longPosition.toNativeLong()));
        }
    }

    /* renamed from: com.sygic.aura.map.MapControlsManager.27 */
    static class AnonymousClass27 implements VoidCallback {
        final /* synthetic */ EMapView val$eMode;

        AnonymousClass27(EMapView eMapView) {
            this.val$eMode = eMapView;
        }

        public void getMethod() {
            MapControlsManager.SetMapViewMode(this.val$eMode.getValue());
        }
    }

    /* renamed from: com.sygic.aura.map.MapControlsManager.28 */
    static class AnonymousClass28 implements VoidCallback {
        final /* synthetic */ ENaviType val$eType;

        AnonymousClass28(ENaviType eNaviType) {
            this.val$eType = eNaviType;
        }

        public void getMethod() {
            MapControlsManager.SetNaviType(this.val$eType.getValue());
        }
    }

    /* renamed from: com.sygic.aura.map.MapControlsManager.2 */
    static class C12962 implements VoidCallback {
        C12962() {
        }

        public void getMethod() {
            MapControlsManager.ZoomIn();
        }
    }

    /* renamed from: com.sygic.aura.map.MapControlsManager.30 */
    static class AnonymousClass30 implements VoidCallback {
        final /* synthetic */ MapSelection val$sel;

        AnonymousClass30(MapSelection mapSelection) {
            this.val$sel = mapSelection;
        }

        public void getMethod() {
            MapControlsManager.ShowPin(this.val$sel.getPosition().toNativeLong(), this.val$sel.getNavSelType().getValue(), this.val$sel.getData());
        }
    }

    /* renamed from: com.sygic.aura.map.MapControlsManager.33 */
    static class AnonymousClass33 implements VoidCallback {
        final /* synthetic */ LongPosition val$longPosition;

        AnonymousClass33(LongPosition longPosition) {
            this.val$longPosition = longPosition;
        }

        public void getMethod() {
            if (this.val$longPosition.isValid()) {
                MapControlsManager.SetViewPosition(this.val$longPosition.toNativeLong());
            }
        }
    }

    /* renamed from: com.sygic.aura.map.MapControlsManager.34 */
    static class AnonymousClass34 implements VoidCallback {
        final /* synthetic */ LongPosition val$longPosition;

        AnonymousClass34(LongPosition longPosition) {
            this.val$longPosition = longPosition;
        }

        public void getMethod() {
            if (this.val$longPosition.isValid()) {
                MapControlsManager.SetWantedPosition(this.val$longPosition.toNativeLong());
            }
        }
    }

    /* renamed from: com.sygic.aura.map.MapControlsManager.35 */
    static class AnonymousClass35 implements VoidCallback {
        final /* synthetic */ int val$bottom;
        final /* synthetic */ int val$left;
        final /* synthetic */ int val$right;
        final /* synthetic */ int val$top;

        AnonymousClass35(int i, int i2, int i3, int i4) {
            this.val$left = i;
            this.val$top = i2;
            this.val$right = i3;
            this.val$bottom = i4;
        }

        public void getMethod() {
            MapControlsManager.ShowRouteWithMargin(this.val$left, this.val$top, this.val$right, this.val$bottom);
        }
    }

    /* renamed from: com.sygic.aura.map.MapControlsManager.36 */
    static class AnonymousClass36 implements VoidCallback {
        final /* synthetic */ float val$fDistance;

        AnonymousClass36(float f) {
            this.val$fDistance = f;
        }

        public void getMethod() {
            MapControlsManager.SetViewDistance(this.val$fDistance);
        }
    }

    /* renamed from: com.sygic.aura.map.MapControlsManager.3 */
    static class C12973 implements VoidCallback {
        final /* synthetic */ float val$distance;

        C12973(float f) {
            this.val$distance = f;
        }

        public void getMethod() {
            MapControlsManager.SetZoomDistance(this.val$distance);
        }
    }

    /* renamed from: com.sygic.aura.map.MapControlsManager.42 */
    static class AnonymousClass42 implements VoidCallback {
        final /* synthetic */ int val$nRouteIndex;

        AnonymousClass42(int i) {
            this.val$nRouteIndex = i;
        }

        public void getMethod() {
            MapControlsManager.OnRouteBubbleClick(this.val$nRouteIndex);
        }
    }

    /* renamed from: com.sygic.aura.map.MapControlsManager.43 */
    static class AnonymousClass43 implements Callback<Boolean> {
        final /* synthetic */ String val$strFileName;

        AnonymousClass43(String str) {
            this.val$strFileName = str;
        }

        public Boolean getMethod() {
            return Boolean.valueOf(MapControlsManager.EmulatorStart(this.val$strFileName));
        }
    }

    /* renamed from: com.sygic.aura.map.MapControlsManager.45 */
    static class AnonymousClass45 implements VoidCallback {
        final /* synthetic */ String val$strFileName;

        AnonymousClass45(String str) {
            this.val$strFileName = str;
        }

        public void getMethod() {
            MapControlsManager.EmulatorRecord(this.val$strFileName);
        }
    }

    /* renamed from: com.sygic.aura.map.MapControlsManager.47 */
    static class AnonymousClass47 implements VoidCallback {
        final /* synthetic */ LongPosition val$longPosition;

        AnonymousClass47(LongPosition longPosition) {
            this.val$longPosition = longPosition;
        }

        public void getMethod() {
            MapControlsManager.SaveMapState(this.val$longPosition != null ? this.val$longPosition.toNativeLong() : LongPosition.InvalidNativeLong);
        }
    }

    /* renamed from: com.sygic.aura.map.MapControlsManager.4 */
    static class C12984 implements VoidCallback {
        C12984() {
        }

        public void getMethod() {
            MapControlsManager.ZoomStop();
        }
    }

    /* renamed from: com.sygic.aura.map.MapControlsManager.5 */
    static class C12995 implements VoidCallback {
        final /* synthetic */ boolean val$animate;

        C12995(boolean z) {
            this.val$animate = z;
        }

        public void getMethod() {
            MapControlsManager.SetMapView2D(this.val$animate);
        }
    }

    /* renamed from: com.sygic.aura.map.MapControlsManager.6 */
    static class C13006 implements Callback<Boolean> {
        C13006() {
        }

        public Boolean getMethod() {
            return Boolean.valueOf(MapControlsManager.IsMapView2D());
        }
    }

    /* renamed from: com.sygic.aura.map.MapControlsManager.7 */
    static class C13017 implements VoidCallback {
        C13017() {
        }

        public void getMethod() {
            MapControlsManager.SetMapView3D();
        }
    }

    /* renamed from: com.sygic.aura.map.MapControlsManager.8 */
    static class C13028 implements Callback<Boolean> {
        C13028() {
        }

        public Boolean getMethod() {
            return Boolean.valueOf(MapControlsManager.IsTrafficView());
        }
    }

    /* renamed from: com.sygic.aura.map.MapControlsManager.9 */
    static class C13039 implements VoidCallback {
        C13039() {
        }

        public void getMethod() {
            MapControlsManager.EnterTrafficView();
        }
    }

    public enum EMapView {
        MVDefault(0),
        MVPoiDetail(1),
        MVRouteOverview(2),
        MVRouteSelection(3),
        MVNavScreen(4),
        MVCustomPois(5);
        
        final int nValue;

        private EMapView(int val) {
            this.nValue = val;
        }

        public int getValue() {
            return this.nValue;
        }
    }

    public enum ENaviType {
        NtNone(0),
        NtPedestrian(1),
        NtCar(2),
        NtPublicTransport(3);
        
        final int nValue;

        private ENaviType(int val) {
            this.nValue = val;
        }

        public int getValue() {
            return this.nValue;
        }
    }

    private static native void EmulatorRecord(String str);

    private static native boolean EmulatorStart(String str);

    private static native void EmulatorStop();

    private static native void EnterTrafficView();

    private static native String GetActualPositionCountryIso();

    private static native String GetCurrentStreetName();

    private static native long GetDistanceFromVehicle(long j);

    private static native int GetMapViewMode();

    private static native MapSelection GetPinPosition();

    private static native float GetViewRotation();

    private static native float GetViewUserTilt();

    private static native boolean IsMapView2D();

    private static native boolean IsTrafficView();

    private static native void LeaveTrafficView();

    private static native void LockVehicleCompass();

    private static native void LockVehicleNorthUp();

    private static native void OnRouteBubbleClick(int i);

    private static native void PlayLastInstruction();

    private static native void ReportIncident(int i, long j);

    private static native void RestoreMapState();

    private static native void SaveMapState(long j);

    private static native void SetMapView2D(boolean z);

    private static native void SetMapView3D();

    private static native void SetMapViewMode(int i);

    private static native void SetNaviType(int i);

    private static native void SetViewDistance(float f);

    private static native void SetViewPosition(long j);

    private static native void SetViewRotation(float f);

    private static native void SetWantedPosition(long j);

    private static native void SetWantedRotation(float f);

    private static native void SetWantedUserTilt(float f);

    private static native void SetZoomDistance(float f);

    private static native void ShowPin(long j, int i, long j2);

    private static native void ShowRouteWithMargin(int i, int i2, int i3, int i4);

    private static native void UnlockVehicle();

    private static native void ZoomIn();

    private static native void ZoomOut();

    private static native void ZoomStop();

    public static void nativeZoomOut() {
        if (!SygicProject.IS_PROTOTYPE) {
            ObjectHandler.postAndWait(new C12951());
        }
    }

    public static void nativeZoomIn() {
        if (!SygicProject.IS_PROTOTYPE) {
            ObjectHandler.postAndWait(new C12962());
        }
    }

    public static void nativeSetZoomDistance(float distance) {
        if (!SygicProject.IS_PROTOTYPE) {
            ObjectHandler.postAndWait(new C12973(distance));
        }
    }

    public static void nativeZoomStop() {
        if (!SygicProject.IS_PROTOTYPE) {
            ObjectHandler.postAndWait(new C12984());
        }
    }

    public static void nativeSetMapView2D() {
        nativeSetMapView2D(true);
    }

    public static void nativeSetMapView2D(boolean animate) {
        if (!SygicProject.IS_PROTOTYPE) {
            ObjectHandler.postAndWait(new C12995(animate));
        }
    }

    public static boolean nativeIsMapView2D() {
        if (SygicProject.IS_PROTOTYPE) {
            return true;
        }
        return ((Boolean) new ObjectHandler(new C13006()).execute().get(Boolean.valueOf(false))).booleanValue();
    }

    public static void nativeSetMapView3D() {
        if (!SygicProject.IS_PROTOTYPE) {
            ObjectHandler.postAndWait(new C13017());
        }
    }

    public static boolean nativeIsTrafficView() {
        if (SygicProject.IS_PROTOTYPE) {
            return false;
        }
        return ((Boolean) new ObjectHandler(new C13028()).execute().get(Boolean.valueOf(false))).booleanValue();
    }

    public static void nativeEnterTrafficView() {
        if (!SygicProject.IS_PROTOTYPE) {
            ObjectHandler.postAndWait(new C13039());
        }
    }

    public static void nativeLeaveTrafficView() {
        if (!SygicProject.IS_PROTOTYPE) {
            ObjectHandler.postAndWait(new VoidCallback() {
                public void getMethod() {
                    MapControlsManager.LeaveTrafficView();
                }
            });
        }
    }

    public static void nativeLockVehicleCompass() {
        if (!SygicProject.IS_PROTOTYPE) {
            ObjectHandler.postAndWait(new VoidCallback() {
                public void getMethod() {
                    MapControlsManager.LockVehicleCompass();
                }
            });
        }
    }

    public static void nativeLockVehicleNorthUp() {
        if (!SygicProject.IS_PROTOTYPE) {
            ObjectHandler.postAndWait(new VoidCallback() {
                public void getMethod() {
                    MapControlsManager.LockVehicleNorthUp();
                }
            });
        }
    }

    public static void nativeUnlockVehicle() {
        if (!SygicProject.IS_PROTOTYPE) {
            ObjectHandler.postAndWait(new VoidCallback() {
                public void getMethod() {
                    MapControlsManager.UnlockVehicle();
                }
            });
        }
    }

    public static void nativeReportIncident(IncidentItemType type, LongPosition position) {
        if (!SygicProject.IS_PROTOTYPE) {
            ObjectHandler.postAndWait(new AnonymousClass15(type, position));
        }
    }

    public static String nativeGetActualPositionCountryIso() {
        if (SygicProject.IS_PROTOTYPE) {
            return "SVK";
        }
        return (String) new ObjectHandler(new Callback<String>() {
            public String getMethod() {
                return MapControlsManager.GetActualPositionCountryIso();
            }
        }).execute().get(null);
    }

    public static float nativeGetViewRotation() {
        if (SygicProject.IS_PROTOTYPE) {
            return 0.1f;
        }
        return ((Float) new ObjectHandler(new Callback<Float>() {
            public Float getMethod() {
                return Float.valueOf(MapControlsManager.GetViewRotation());
            }
        }).execute().get(Float.valueOf(0.0f))).floatValue();
    }

    public static void nativeSetViewRotation(float fRotation) {
        if (!SygicProject.IS_PROTOTYPE) {
            ObjectHandler.postAndWait(new AnonymousClass18(fRotation));
        }
    }

    public static void nativeSetWantedRotation(float fRotation) {
        if (!SygicProject.IS_PROTOTYPE) {
            ObjectHandler.postAndWait(new AnonymousClass19(fRotation));
        }
    }

    public static float nativeGetViewTilt() {
        if (SygicProject.IS_PROTOTYPE) {
            return 0.1f;
        }
        return ((Float) new ObjectHandler(new Callback<Float>() {
            public Float getMethod() {
                return Float.valueOf(MapControlsManager.GetViewUserTilt());
            }
        }).execute().get(Float.valueOf(0.0f))).floatValue();
    }

    public static void nativeSetWantedTilt(float fTilt) {
        if (!SygicProject.IS_PROTOTYPE) {
            ObjectHandler.postAndWait(new AnonymousClass23(fTilt));
        }
    }

    public static long nativeGetPointsDistance(LongPosition longPosition) {
        if (SygicProject.IS_PROTOTYPE) {
            return 0;
        }
        return ((Long) new ObjectHandler(new AnonymousClass24(longPosition)).execute().get(Long.valueOf(0))).longValue();
    }

    public static String nativeGetCurrentStreetName() {
        if (SygicProject.IS_PROTOTYPE) {
            return "street";
        }
        return (String) new ObjectHandler(new Callback<String>() {
            public String getMethod() {
                return MapControlsManager.GetCurrentStreetName();
            }
        }).execute().get(null);
    }

    public static void nativeSetMapViewMode(EMapView eMode) {
        if (!SygicProject.IS_PROTOTYPE) {
            ObjectHandler.postAndWait(new AnonymousClass27(eMode));
        }
    }

    public static void nativeSetNaviType(ENaviType eType) {
        if (!SygicProject.IS_PROTOTYPE) {
            ObjectHandler.postAndWait(new AnonymousClass28(eType));
        }
    }

    public static EMapView nativeGetMapViewMode() {
        if (SygicProject.IS_PROTOTYPE) {
            return EMapView.MVDefault;
        }
        return (EMapView) new ObjectHandler(new Callback<EMapView>() {
            public EMapView getMethod() {
                return EMapView.values()[MapControlsManager.GetMapViewMode()];
            }
        }).execute().get(EMapView.MVDefault);
    }

    public static void nativeShowPin(MapSelection mapSel) {
        if (!SygicProject.IS_PROTOTYPE) {
            MapSelection sel;
            if (mapSel == null) {
                sel = MapSelection.Empty;
            } else {
                sel = mapSel;
            }
            ObjectHandler.postAndWait(new AnonymousClass30(sel));
        }
    }

    public static MapSelection nativeGetPinPosition() {
        if (SygicProject.IS_PROTOTYPE) {
            return MapSelection.Empty;
        }
        return (MapSelection) new ObjectHandler(new Callback<MapSelection>() {
            public MapSelection getMethod() {
                return MapControlsManager.GetPinPosition();
            }
        }).execute().get(MapSelection.Empty);
    }

    public static void nativeSetViewPosition(LongPosition longPosition) {
        if (!SygicProject.IS_PROTOTYPE) {
            ObjectHandler.postAndWait(new AnonymousClass33(longPosition));
        }
    }

    public static void nativeSetWantedPosition(LongPosition longPosition) {
        if (!SygicProject.IS_PROTOTYPE) {
            ObjectHandler.postAndWait(new AnonymousClass34(longPosition));
        }
    }

    public static void nativeShowRouteWithMargin(int left, int top, int right, int bottom) {
        if (!SygicProject.IS_PROTOTYPE) {
            ObjectHandler.postAndWait(new AnonymousClass35(left, top, right, bottom));
        }
    }

    public static void nativeSetViewDistance(float fDistance) {
        if (!SygicProject.IS_PROTOTYPE) {
            ObjectHandler.postAndWait(new AnonymousClass36(fDistance));
        }
    }

    public static OnClickListener getOnClickListenerVehicleLock(ThreeStateButton button) {
        return SynchronizedThreeStateChangeListener.getInstance(button);
    }

    public static OnCheckedChangeListener getOnClickListener2D3D(SToggleButton button) {
        return SynchronizedOnCheckedChangeListener.getInstance(button);
    }

    public static OnTouchListener getZoomOutListener() {
        return new OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {
                if (v.getTag(2131624427) != null) {
                    ((QuickMenuTimer) v.getTag(2131624427)).reset();
                }
                if (event.getAction() == 0) {
                    MapControlsManager.nativeZoomOut();
                } else if (event.getAction() == 1 || event.getAction() == 3) {
                    MapControlsManager.nativeZoomStop();
                }
                return false;
            }
        };
    }

    public static OnTouchListener getZoomInListener() {
        return new OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {
                if (v.getTag(2131624427) != null) {
                    ((QuickMenuTimer) v.getTag(2131624427)).reset();
                }
                if (event.getAction() == 0) {
                    MapControlsManager.nativeZoomIn();
                } else if (event.getAction() == 1 || event.getAction() == 3) {
                    MapControlsManager.nativeZoomStop();
                }
                return false;
            }
        };
    }

    public static OnCheckedChangeListener getRotateLockListener() {
        return new OnCheckedChangeListener() {
            public void onCheckedChanged(SImageButton button, boolean isChecked) {
                if (button.getTag(2131624427) != null) {
                    ((QuickMenuTimer) button.getTag(2131624427)).reset();
                }
                SygicHelper.setRotationLock(isChecked);
            }
        };
    }

    public static void nativeOnRouteBubbleClick(int nRouteIndex) {
        if (!SygicProject.IS_PROTOTYPE) {
            ObjectHandler.postAndWait(new AnonymousClass42(nRouteIndex));
        }
    }

    public static boolean nativeEmulatorStart(String strFileName) {
        if (SygicProject.IS_PROTOTYPE) {
            return false;
        }
        return ((Boolean) new ObjectHandler(new AnonymousClass43(strFileName)).execute().get(Boolean.valueOf(false))).booleanValue();
    }

    public static void nativeEmulatorStop() {
        if (!SygicProject.IS_PROTOTYPE) {
            ObjectHandler.postAndWait(new VoidCallback() {
                public void getMethod() {
                    MapControlsManager.EmulatorStop();
                }
            });
        }
    }

    public static void nativeEmulatorRecord(String strFileName) {
        if (!SygicProject.IS_PROTOTYPE) {
            ObjectHandler.postAndWait(new AnonymousClass45(strFileName));
        }
    }

    public static void nativePlayLastInstruction() {
        if (!SygicProject.IS_PROTOTYPE) {
            ObjectHandler.postAndWait(new VoidCallback() {
                public void getMethod() {
                    MapControlsManager.PlayLastInstruction();
                }
            });
        }
    }

    public static void nativeSaveMapState(LongPosition longPosition) {
        if (!SygicProject.IS_PROTOTYPE) {
            ObjectHandler.postAndWait(new AnonymousClass47(longPosition));
        }
    }

    public static void nativeRestoreMapState() {
        if (!SygicProject.IS_PROTOTYPE) {
            ObjectHandler.postAndWait(new VoidCallback() {
                public void getMethod() {
                    MapControlsManager.RestoreMapState();
                }
            });
        }
    }
}
