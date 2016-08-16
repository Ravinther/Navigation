package com.sygic.aura.map;

import android.content.Context;
import android.text.TextUtils;
import com.sygic.aura.SygicProject;
import com.sygic.aura.data.LongPosition;
import com.sygic.aura.data.ScreenPoint;
import com.sygic.aura.helper.ObjectHandler;
import com.sygic.aura.helper.ObjectHandler.Callback;
import com.sygic.aura.helper.ObjectHandler.VoidCallback;
import com.sygic.aura.map.data.map_selection.MapSelection;
import com.sygic.aura.resources.ResourceManager;
import com.sygic.aura.search.model.data.ListItem;
import com.sygic.aura.search.model.data.SearchItem;

public class PositionInfo {

    /* renamed from: com.sygic.aura.map.PositionInfo.10 */
    static class AnonymousClass10 implements Callback<ListItem[]> {
        final /* synthetic */ long val$data;
        final /* synthetic */ LongPosition val$lPos;
        final /* synthetic */ int val$navSelType;

        AnonymousClass10(LongPosition longPosition, int i, long j) {
            this.val$lPos = longPosition;
            this.val$navSelType = i;
            this.val$data = j;
        }

        public ListItem[] getMethod() {
            return PositionInfo.GetPositionSearchEntries(this.val$lPos.toNativeLong(), this.val$navSelType, this.val$data);
        }
    }

    /* renamed from: com.sygic.aura.map.PositionInfo.11 */
    static class AnonymousClass11 implements Callback<int[]> {
        final /* synthetic */ long val$cityTreeEntry;
        final /* synthetic */ long val$streetTreeEntry;

        AnonymousClass11(long j, long j2) {
            this.val$cityTreeEntry = j;
            this.val$streetTreeEntry = j2;
        }

        public int[] getMethod() {
            return PositionInfo.GetStreetMinMaxHouseNum(this.val$cityTreeEntry, this.val$streetTreeEntry);
        }
    }

    /* renamed from: com.sygic.aura.map.PositionInfo.18 */
    static class AnonymousClass18 implements Callback<ScreenPoint> {
        final /* synthetic */ LongPosition val$longPos;

        AnonymousClass18(LongPosition longPosition) {
            this.val$longPos = longPosition;
        }

        public ScreenPoint getMethod() {
            return new ScreenPoint(PositionInfo.GeoToScreen(this.val$longPos.getX(), this.val$longPos.getY()));
        }
    }

    /* renamed from: com.sygic.aura.map.PositionInfo.1 */
    static class C13131 implements Callback<Boolean> {
        C13131() {
        }

        public Boolean getMethod() {
            return Boolean.valueOf(PositionInfo.IsVehicleOnMap());
        }
    }

    /* renamed from: com.sygic.aura.map.PositionInfo.20 */
    static class AnonymousClass20 implements Callback<Boolean> {
        final /* synthetic */ MapSelection val$selection;

        AnonymousClass20(MapSelection mapSelection) {
            this.val$selection = mapSelection;
        }

        public Boolean getMethod() {
            return Boolean.valueOf(PositionInfo.IsCityCenter(this.val$selection.getPosition().toNativeLong(), this.val$selection.getNavSelType().getValue(), this.val$selection.getData()));
        }
    }

    /* renamed from: com.sygic.aura.map.PositionInfo.21 */
    static class AnonymousClass21 implements Callback<Boolean> {
        final /* synthetic */ SearchItem val$item;

        AnonymousClass21(SearchItem searchItem) {
            this.val$item = searchItem;
        }

        public Boolean getMethod() {
            boolean z = this.val$item.getIso() != null && PositionInfo.IsUSACountry(this.val$item.getIso());
            return Boolean.valueOf(z);
        }
    }

    /* renamed from: com.sygic.aura.map.PositionInfo.22 */
    static class AnonymousClass22 implements Callback<Integer> {
        final /* synthetic */ MapSelection val$selection;

        AnonymousClass22(MapSelection mapSelection) {
            this.val$selection = mapSelection;
        }

        public Integer getMethod() {
            return Integer.valueOf(PositionInfo.GetStreetViewHeading(this.val$selection.getPosition().toNativeLong(), this.val$selection.getNavSelType().getValue(), this.val$selection.getData()));
        }
    }

    /* renamed from: com.sygic.aura.map.PositionInfo.2 */
    static class C13142 implements Callback<Boolean> {
        final /* synthetic */ LongPosition val$lPos;

        C13142(LongPosition longPosition) {
            this.val$lPos = longPosition;
        }

        public Boolean getMethod() {
            return Boolean.valueOf(PositionInfo.HasNavSel(this.val$lPos.toNativeLong()));
        }
    }

    /* renamed from: com.sygic.aura.map.PositionInfo.3 */
    static class C13153 implements Callback<Boolean> {
        final /* synthetic */ boolean val$bAcceptDemostrate;

        C13153(boolean z) {
            this.val$bAcceptDemostrate = z;
        }

        public Boolean getMethod() {
            return Boolean.valueOf(PositionInfo.HasValidPosition(this.val$bAcceptDemostrate));
        }
    }

    /* renamed from: com.sygic.aura.map.PositionInfo.4 */
    static class C13164 implements Callback<Boolean> {
        final /* synthetic */ boolean val$bAcceptDemostrate;

        C13164(boolean z) {
            this.val$bAcceptDemostrate = z;
        }

        public Boolean getMethod() {
            return Boolean.valueOf(PositionInfo.HasActualPosition(this.val$bAcceptDemostrate));
        }
    }

    /* renamed from: com.sygic.aura.map.PositionInfo.5 */
    static class C13175 implements Callback<Boolean> {
        C13175() {
        }

        public Boolean getMethod() {
            return Boolean.valueOf(PositionInfo.HasLastValidPosition());
        }
    }

    /* renamed from: com.sygic.aura.map.PositionInfo.6 */
    static class C13186 implements Callback<LongPosition> {
        C13186() {
        }

        public LongPosition getMethod() {
            return new LongPosition(PositionInfo.GetLastValidPosition());
        }
    }

    /* renamed from: com.sygic.aura.map.PositionInfo.7 */
    static class C13197 implements Callback<String> {
        final /* synthetic */ Context val$context;
        final /* synthetic */ LongPosition val$longPosition;

        C13197(LongPosition longPosition, Context context) {
            this.val$longPosition = longPosition;
            this.val$context = context;
        }

        public String getMethod() {
            String positionString = PositionInfo.GetPositionString(this.val$longPosition.toNativeLong());
            if (TextUtils.isEmpty(positionString)) {
                return ResourceManager.getCoreString(this.val$context, 2131165942);
            }
            return positionString;
        }
    }

    /* renamed from: com.sygic.aura.map.PositionInfo.8 */
    static class C13208 implements Callback<LongPosition> {
        final /* synthetic */ boolean val$bAcceptDemostrate;

        C13208(boolean z) {
            this.val$bAcceptDemostrate = z;
        }

        public LongPosition getMethod() {
            return new LongPosition(PositionInfo.GetVehiclePosition(this.val$bAcceptDemostrate));
        }
    }

    /* renamed from: com.sygic.aura.map.PositionInfo.9 */
    static class C13219 implements Callback<ListItem[]> {
        C13219() {
        }

        public ListItem[] getMethod() {
            return PositionInfo.GetActualPositionSearchEntries();
        }
    }

    private static native void DisableRenderUpdate(boolean z);

    private static native long GeoToScreen(int i, int i2);

    private static native ListItem[] GetActualPositionSearchEntries();

    private static native double GetCurrentVehicleSpeed();

    private static native long GetLastValidPosition();

    private static native ListItem[] GetPositionSearchEntries(long j, int i, long j2);

    private static native String GetPositionString(long j);

    private static native int[] GetStreetMinMaxHouseNum(long j, long j2);

    private static native int GetStreetViewHeading(long j, int i, long j2);

    private static native float GetVehicleAltitude();

    private static native long GetVehiclePosition(boolean z);

    private static native boolean HasActualPosition(boolean z);

    private static native boolean HasLastValidPosition();

    private static native boolean HasNavSel(long j);

    private static native boolean HasValidPosition(boolean z);

    private static native boolean IsCityCenter(long j, int i, long j2);

    private static native boolean IsUSACountry(String str);

    private static native boolean IsVehicleDriving();

    private static native boolean IsVehicleOnMap();

    private static native boolean IsVehicleSpeeding();

    public static boolean nativeIsVehicleOnMap() {
        if (SygicProject.IS_PROTOTYPE) {
            return false;
        }
        return ((Boolean) new ObjectHandler(new C13131()).execute().get(Boolean.valueOf(false))).booleanValue();
    }

    public static boolean nativeHasNavSel(LongPosition lPos) {
        if (SygicProject.IS_PROTOTYPE) {
            return false;
        }
        return ((Boolean) new ObjectHandler(new C13142(lPos)).execute().get(Boolean.valueOf(false))).booleanValue();
    }

    public static boolean nativeHasValidPosition(boolean bAcceptDemostrate) {
        if (SygicProject.IS_PROTOTYPE) {
            return false;
        }
        return ((Boolean) new ObjectHandler(new C13153(bAcceptDemostrate)).execute().get(Boolean.valueOf(false))).booleanValue();
    }

    public static boolean nativeHasActualPosition(boolean bAcceptDemostrate) {
        if (SygicProject.IS_PROTOTYPE) {
            return false;
        }
        return ((Boolean) new ObjectHandler(new C13164(bAcceptDemostrate)).execute().get(Boolean.valueOf(false))).booleanValue();
    }

    public static boolean nativeHasLastValidPosition() {
        if (SygicProject.IS_PROTOTYPE) {
            return false;
        }
        return ((Boolean) new ObjectHandler(new C13175()).execute().get(Boolean.valueOf(false))).booleanValue();
    }

    public static LongPosition nativeGetLastValidPosition() {
        if (SygicProject.IS_PROTOTYPE) {
            return LongPosition.Invalid;
        }
        return (LongPosition) new ObjectHandler(new C13186()).execute().get(LongPosition.Invalid);
    }

    public static String nativeGetPositionString(Context context, LongPosition longPosition) {
        if (SygicProject.IS_PROTOTYPE) {
            return "Karadzicova 14";
        }
        return (String) new ObjectHandler(new C13197(longPosition, context)).execute().get(null);
    }

    public static LongPosition nativeGetVehiclePosition(boolean bAcceptDemostrate) {
        if (SygicProject.IS_PROTOTYPE) {
            return LongPosition.Invalid;
        }
        return (LongPosition) new ObjectHandler(new C13208(bAcceptDemostrate)).execute().get(LongPosition.Invalid);
    }

    public static ListItem[] nativeGetActualPositionSearchEntries() {
        if (SygicProject.IS_PROTOTYPE) {
            return null;
        }
        return (ListItem[]) new ObjectHandler(new C13219()).execute().get(new ListItem[0]);
    }

    public static ListItem[] nativeGetPositionSearchEntries(MapSelection mapSel) {
        return nativeGetPositionSearchEntries(mapSel.getPosition(), mapSel.getNavSelType().getValue(), mapSel.getData());
    }

    public static ListItem[] nativeGetPositionSearchEntries(LongPosition lPos, int navSelType, long data) {
        if (SygicProject.IS_PROTOTYPE) {
            return new ListItem[]{ListItem.getDummyItem("Test search entry")};
        } else if (lPos == null) {
            return null;
        } else {
            return (ListItem[]) new ObjectHandler(new AnonymousClass10(lPos, navSelType, data)).execute().get(new ListItem[0]);
        }
    }

    public static int[] nativeGetStreetMinMaxHouseNum(long cityTreeEntry, long streetTreeEntry) {
        return SygicProject.IS_PROTOTYPE ? new int[]{1, 4} : (int[]) new ObjectHandler(new AnonymousClass11(cityTreeEntry, streetTreeEntry)).execute().get(new int[0]);
    }

    public static float nativeGetActualVehicleAltitude() {
        if (SygicProject.IS_PROTOTYPE) {
            return 875.14f;
        }
        return ((Float) new ObjectHandler(new Callback<Float>() {
            public Float getMethod() {
                return Float.valueOf(PositionInfo.GetVehicleAltitude());
            }
        }).execute().get(Float.valueOf(0.0f))).floatValue();
    }

    public static double nativeGetCurrentVehicleSpeed() {
        if (SygicProject.IS_PROTOTYPE) {
            return 125.14d;
        }
        return ((Double) new ObjectHandler(new Callback<Double>() {
            public Double getMethod() {
                return Double.valueOf(PositionInfo.GetCurrentVehicleSpeed());
            }
        }).execute().get(Double.valueOf(0.0d))).doubleValue();
    }

    public static boolean nativeIsVehicleSpeeding() {
        if (SygicProject.IS_PROTOTYPE) {
            return true;
        }
        return ((Boolean) new ObjectHandler(new Callback<Boolean>() {
            public Boolean getMethod() {
                return Boolean.valueOf(PositionInfo.IsVehicleSpeeding());
            }
        }).execute().get(Boolean.valueOf(false))).booleanValue();
    }

    public static boolean nativeIsVehicleDriving() {
        if (SygicProject.IS_PROTOTYPE) {
            return true;
        }
        return ((Boolean) new ObjectHandler(new Callback<Boolean>() {
            public Boolean getMethod() {
                return Boolean.valueOf(PositionInfo.IsVehicleDriving());
            }
        }).execute().get(Boolean.valueOf(false))).booleanValue();
    }

    public static void nativeEnableMapView() {
        if (!SygicProject.IS_PROTOTYPE) {
            ObjectHandler.postAndWait(new VoidCallback() {
                public void getMethod() {
                    PositionInfo.DisableRenderUpdate(false);
                }
            });
        }
    }

    public static void nativeDisableMapView() {
        if (!SygicProject.IS_PROTOTYPE) {
            ObjectHandler.postAndWait(new VoidCallback() {
                public void getMethod() {
                    PositionInfo.DisableRenderUpdate(true);
                }
            });
        }
    }

    public static ScreenPoint nativeGeoToScreen(LongPosition longPos) {
        if (SygicProject.IS_PROTOTYPE) {
            return null;
        }
        return (ScreenPoint) new ObjectHandler(new AnonymousClass18(longPos)).execute().get(ScreenPoint.Invalid);
    }

    public static boolean nativeIsCityCenter(MapSelection selection) {
        if (SygicProject.IS_PROTOTYPE) {
            return false;
        }
        return ((Boolean) new ObjectHandler(new AnonymousClass20(selection)).execute().get(Boolean.valueOf(false))).booleanValue();
    }

    public static boolean nativeIsUsaCountry(SearchItem item) {
        if (SygicProject.IS_PROTOTYPE || item == null) {
            return false;
        }
        return ((Boolean) new ObjectHandler(new AnonymousClass21(item)).execute().get(Boolean.valueOf(false))).booleanValue();
    }

    public static int nativeGetStreetViewHeading(MapSelection selection) {
        if (SygicProject.IS_PROTOTYPE) {
            return 60;
        }
        return ((Integer) new ObjectHandler(new AnonymousClass22(selection)).execute().get(Integer.valueOf(0))).intValue();
    }
}
