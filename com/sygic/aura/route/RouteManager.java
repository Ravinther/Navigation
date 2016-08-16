package com.sygic.aura.route;

import android.util.Log;
import com.sygic.aura.SygicProject;
import com.sygic.aura.helper.ObjectHandler;
import com.sygic.aura.helper.ObjectHandler.Callback;
import com.sygic.aura.helper.ObjectHandler.VoidCallback;
import com.sygic.aura.map.data.map_selection.MapSelection;
import com.sygic.aura.route.data.RouteAvoidsData;
import com.sygic.aura.route.data.RouteNavigateData;

public class RouteManager {

    /* renamed from: com.sygic.aura.route.RouteManager.11 */
    static class AnonymousClass11 implements VoidCallback {
        final /* synthetic */ RouteNavigateData val$routeNaviData;

        AnonymousClass11(RouteNavigateData routeNavigateData) {
            this.val$routeNaviData = routeNavigateData;
        }

        public void getMethod() {
            RouteManager.SetCountryRouteAvoids(this.val$routeNaviData);
        }
    }

    /* renamed from: com.sygic.aura.route.RouteManager.12 */
    static class AnonymousClass12 implements VoidCallback {
        final /* synthetic */ MapSelection val$sel;

        AnonymousClass12(MapSelection mapSelection) {
            this.val$sel = mapSelection;
        }

        public void getMethod() {
            RouteManager.PassBy(this.val$sel.getPosition().toNativeLong(), this.val$sel.getNavSelType().getValue(), this.val$sel.getData());
        }
    }

    /* renamed from: com.sygic.aura.route.RouteManager.13 */
    static class AnonymousClass13 implements VoidCallback {
        final /* synthetic */ MapSelection val$mapSel;

        AnonymousClass13(MapSelection mapSelection) {
            this.val$mapSel = mapSelection;
        }

        public void getMethod() {
            RouteManager.TravelVia(this.val$mapSel.getPosition().toNativeLong());
        }
    }

    /* renamed from: com.sygic.aura.route.RouteManager.1 */
    static class C14981 implements Callback<Boolean> {
        C14981() {
        }

        public Boolean getMethod() {
            return Boolean.valueOf(RouteManager.ExistValidRoute());
        }
    }

    /* renamed from: com.sygic.aura.route.RouteManager.2 */
    static class C14992 implements Callback<Boolean> {
        C14992() {
        }

        public Boolean getMethod() {
            return Boolean.valueOf(RouteManager.IsComputing());
        }
    }

    /* renamed from: com.sygic.aura.route.RouteManager.3 */
    static class C15003 implements VoidCallback {
        final /* synthetic */ MapSelection[] val$arrMapSel;
        final /* synthetic */ RouteComputeMode val$computeMode;
        final /* synthetic */ int val$length;
        final /* synthetic */ RouteComputePosition val$positionType;

        C15003(int i, MapSelection[] mapSelectionArr, RouteComputePosition routeComputePosition, RouteComputeMode routeComputeMode) {
            this.val$length = i;
            this.val$arrMapSel = mapSelectionArr;
            this.val$positionType = routeComputePosition;
            this.val$computeMode = routeComputeMode;
        }

        public void getMethod() {
            long[] arrPos = new long[this.val$length];
            int[] arrTypes = new int[this.val$length];
            long[] arrData = new long[this.val$length];
            for (int i = 0; i < this.val$length; i++) {
                MapSelection sel = this.val$arrMapSel[i];
                arrPos[i] = sel.getPosition().toNativeLong();
                arrTypes[i] = sel.getNavSelType().getValue();
                arrData[i] = sel.getData();
            }
            RouteManager.ComputeRoute(arrPos, arrTypes, arrData, this.val$positionType.getValue(), this.val$computeMode.getValue());
        }
    }

    /* renamed from: com.sygic.aura.route.RouteManager.4 */
    static class C15014 implements Callback<MapSelection[]> {
        final /* synthetic */ String val$itinerary;

        C15014(String str) {
            this.val$itinerary = str;
        }

        public MapSelection[] getMethod() {
            return RouteManager.ComputeFromItinerary(this.val$itinerary);
        }
    }

    /* renamed from: com.sygic.aura.route.RouteManager.5 */
    static class C15025 implements VoidCallback {
        final /* synthetic */ RouteComputeMode val$mode;

        C15025(RouteComputeMode routeComputeMode) {
            this.val$mode = routeComputeMode;
        }

        public void getMethod() {
            RouteManager.RecomputeRoute(this.val$mode.getValue());
        }
    }

    /* renamed from: com.sygic.aura.route.RouteManager.6 */
    static class C15036 implements VoidCallback {
        C15036() {
        }

        public void getMethod() {
            RouteManager.StartNavigate();
        }
    }

    /* renamed from: com.sygic.aura.route.RouteManager.7 */
    static class C15047 implements VoidCallback {
        C15047() {
        }

        public void getMethod() {
            RouteManager.ResumeNavigation();
        }
    }

    /* renamed from: com.sygic.aura.route.RouteManager.8 */
    static class C15058 implements Callback<Integer> {
        final /* synthetic */ String val$strCountryISO;

        C15058(String str) {
            this.val$strCountryISO = str;
        }

        public Integer getMethod() {
            return Integer.valueOf(RouteManager.GetCountryRouteAvoids(this.val$strCountryISO));
        }
    }

    /* renamed from: com.sygic.aura.route.RouteManager.9 */
    static class C15069 implements Callback<String> {
        C15069() {
        }

        public String getMethod() {
            return RouteManager.GetRouteCountries();
        }
    }

    public enum AvoidType {
        TYPE_TOLL_ROADS_ASK(0),
        TYPE_TOLL_ROADS_UNABLE(1),
        TYPE_MOTORWAYS_ASK(2),
        TYPE_MOTORWAYS_UNABLE(3),
        TYPE_UNPAVED_ASK(4),
        TYPE_UNPAVED_UNABLE(5),
        TYPE_FERRIES_ROADS_ASK(6),
        TYPE_FERRIES_UNABLE(7);
        
        final int nValue;

        private AvoidType(int value) {
            this.nValue = value;
        }
    }

    public enum RouteComputeMode {
        MODE_NONE(0),
        MODE_PEDESTRIAN(1),
        MODE_CAR(2),
        MODE_PUBLIC_TRANSPORT(3);
        
        final int nValue;

        private RouteComputeMode(int value) {
            this.nValue = value;
        }

        public int getValue() {
            return this.nValue;
        }
    }

    public enum RouteComputePosition {
        POSITION_LAST_VALID(0),
        POSITION_USER_DEFINED(1),
        POSITION_ACTUAL(2);
        
        final int nValue;

        private RouteComputePosition(int value) {
            this.nValue = value;
        }

        public int getValue() {
            return this.nValue;
        }
    }

    private static native MapSelection[] ComputeFromItinerary(String str);

    private static native void ComputeRoute(long[] jArr, int[] iArr, long[] jArr2, int i, int i2);

    private static native void ComputeRouteCameras();

    private static native boolean ExistValidRoute();

    private static native int GetCountryRouteAvoids(String str);

    private static native String GetRouteCountries();

    private static native String GetRouteCountriesISO();

    private static native boolean IsComputing();

    private static native void PassBy(long j, int i, long j2);

    private static native void RecomputeRoute(int i);

    private static native void ResumeNavigation();

    private static native void SaveItinerar();

    private static native void SetCountryRouteAvoids(RouteNavigateData routeNavigateData);

    private static native void StartNavigate();

    private static native void StopComputingRemoveRoute();

    private static native void SwitchScoutRoute();

    private static native void TravelVia(long j);

    private static native void UpdateDirection();

    private static native void UpdateSpeedWarning();

    private static native boolean WillRouteRestore();

    public static boolean nativeExistValidRoute() {
        if (SygicProject.IS_PROTOTYPE) {
            return false;
        }
        return ((Boolean) new ObjectHandler(new C14981()).execute().get(Boolean.valueOf(false))).booleanValue();
    }

    public static boolean nativeIsComputing() {
        if (SygicProject.IS_PROTOTYPE) {
            return false;
        }
        return ((Boolean) new ObjectHandler(new C14992()).execute().get(Boolean.valueOf(false))).booleanValue();
    }

    public static void nativeComputeRoute(MapSelection[] arrMapSel, RouteComputePosition positionType, RouteComputeMode computeMode) {
        if (!SygicProject.IS_PROTOTYPE) {
            int length = arrMapSel.length;
            if (length == 0 || arrMapSel[length - 1] == null) {
                logError("route computing: [arrNavSel[destination] == 0] (" + positionType.name() + ")");
            } else {
                ObjectHandler.postAndWait(new C15003(length, arrMapSel, positionType, computeMode));
            }
        }
    }

    public static MapSelection[] nativeComputeFromItinerary(String itinerary) {
        if (SygicProject.IS_PROTOTYPE) {
            return null;
        }
        return (MapSelection[]) new ObjectHandler(new C15014(itinerary)).execute().get(null);
    }

    public static void nativeRouteRecompute(RouteComputeMode mode) {
        if (!SygicProject.IS_PROTOTYPE) {
            ObjectHandler.postAndWait(new C15025(mode));
        }
    }

    public static void nativeStartNavigate() {
        if (!SygicProject.IS_PROTOTYPE) {
            ObjectHandler.postAndWait(new C15036());
        }
    }

    public static void nativeResumeNavigation() {
        if (!SygicProject.IS_PROTOTYPE) {
            ObjectHandler.postAndWait(new C15047());
        }
    }

    public static int nativeGetCountryRouteAvoids(String strCountryISO) {
        if (SygicProject.IS_PROTOTYPE) {
            return 2;
        }
        return ((Integer) new ObjectHandler(new C15058(strCountryISO)).execute().get(Integer.valueOf(0))).intValue();
    }

    public static RouteAvoidsData[] nativeGetRouteAvoids() {
        if (SygicProject.IS_PROTOTYPE) {
            return RouteAvoidsData.newArrayInstance("Slovakia|Hungary|Rudov Raj", "SVK|sHUN|RUD");
        }
        return RouteAvoidsData.newArrayInstance(nativeGetRouteCountries(), nativeGetRouteCountriesISO());
    }

    public static String nativeGetRouteCountries() {
        if (SygicProject.IS_PROTOTYPE) {
            return "Slovakia";
        }
        return (String) new ObjectHandler(new C15069()).execute().get(null);
    }

    public static String nativeGetRouteCountriesISO() {
        if (SygicProject.IS_PROTOTYPE) {
            return "SVK";
        }
        return (String) new ObjectHandler(new Callback<String>() {
            public String getMethod() {
                return RouteManager.GetRouteCountriesISO();
            }
        }).execute().get(null);
    }

    public static void nativeSetCountryRouteAvoids(RouteNavigateData routeNaviData) {
        if (!SygicProject.IS_PROTOTYPE) {
            ObjectHandler.postAndWait(new AnonymousClass11(routeNaviData));
        }
    }

    public static void nativePassBy(MapSelection sel) {
        if (!SygicProject.IS_PROTOTYPE) {
            ObjectHandler.postAndWait(new AnonymousClass12(sel));
        }
    }

    public static void nativeTravelVia(MapSelection mapSel) {
        if (!SygicProject.IS_PROTOTYPE) {
            ObjectHandler.postAndWait(new AnonymousClass13(mapSel));
        }
    }

    public static void nativeStopComputingRemoveRoute() {
        if (!SygicProject.IS_PROTOTYPE) {
            ObjectHandler.postAndWait(new VoidCallback() {
                public void getMethod() {
                    RouteManager.StopComputingRemoveRoute();
                }
            });
        }
    }

    public static void nativeUpdateDirection() {
        if (!SygicProject.IS_PROTOTYPE) {
            ObjectHandler.postAndWait(new VoidCallback() {
                public void getMethod() {
                    RouteManager.UpdateDirection();
                }
            });
        }
    }

    public static void nativeUpdateSpeedWarning() {
        if (!SygicProject.IS_PROTOTYPE) {
            ObjectHandler.postAndWait(new VoidCallback() {
                public void getMethod() {
                    RouteManager.UpdateSpeedWarning();
                }
            });
        }
    }

    public static void nativeSaveItinerar() {
        if (!SygicProject.IS_PROTOTYPE) {
            ObjectHandler.postAndWait(new VoidCallback() {
                public void getMethod() {
                    RouteManager.SaveItinerar();
                }
            });
        }
    }

    public static void nativeSwitchScoutRoute() {
        if (!SygicProject.IS_PROTOTYPE) {
            ObjectHandler.postAndWait(new VoidCallback() {
                public void getMethod() {
                    RouteManager.SwitchScoutRoute();
                }
            });
        }
    }

    public static void nativeComputeRouteCameras() {
        if (!SygicProject.IS_PROTOTYPE) {
            ObjectHandler.postAndWait(new VoidCallback() {
                public void getMethod() {
                    RouteManager.ComputeRouteCameras();
                }
            });
        }
    }

    public static boolean nativeWillRouteRestore() {
        if (SygicProject.IS_PROTOTYPE) {
            return false;
        }
        return ((Boolean) new ObjectHandler(new Callback<Boolean>() {
            public Boolean getMethod() {
                return Boolean.valueOf(RouteManager.WillRouteRestore());
            }
        }).execute().get(Boolean.valueOf(false))).booleanValue();
    }

    private static void logError(String message) {
        Log.e("RouteManager", message);
    }
}
