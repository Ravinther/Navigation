package com.sygic.aura.poi;

import com.sygic.aura.SygicProject;
import com.sygic.aura.data.LongPosition;
import com.sygic.aura.helper.ObjectHandler;
import com.sygic.aura.helper.ObjectHandler.Callback;
import com.sygic.aura.helper.ObjectHandler.VoidCallback;
import com.sygic.aura.map.data.map_selection.MapSelection;
import com.sygic.aura.poi.nearbypoi.model.NearbyPoiGroup;
import com.sygic.aura.search.model.data.ListItem;

public class PoiManager {

    /* renamed from: com.sygic.aura.poi.PoiManager.1 */
    static class C14281 implements Callback<String> {
        final /* synthetic */ LongPosition val$lPos;

        C14281(LongPosition longPosition) {
            this.val$lPos = longPosition;
        }

        public String getMethod() {
            return PoiManager.GetPoiDescription(this.val$lPos.toNativeLong());
        }
    }

    /* renamed from: com.sygic.aura.poi.PoiManager.2 */
    static class C14292 implements Callback<MapSelection> {
        final /* synthetic */ LongPosition val$position;

        C14292(LongPosition longPosition) {
            this.val$position = longPosition;
        }

        public MapSelection getMethod() {
            return PoiManager.GetOnlineSelectionAt(this.val$position.toNativeLong());
        }
    }

    /* renamed from: com.sygic.aura.poi.PoiManager.3 */
    static class C14303 implements VoidCallback {
        final /* synthetic */ long[] val$data;
        final /* synthetic */ boolean val$isOnline;

        C14303(boolean z, long[] jArr) {
            this.val$isOnline = z;
            this.val$data = jArr;
        }

        public void getMethod() {
            PoiManager.ShowPoisOnMap(this.val$isOnline, this.val$data);
        }
    }

    /* renamed from: com.sygic.aura.poi.PoiManager.4 */
    static class C14314 implements Callback<MapSelection> {
        final /* synthetic */ int val$notifItemId;

        C14314(int i) {
            this.val$notifItemId = i;
        }

        public MapSelection getMethod() {
            return PoiManager.GetNavselFromPorNotifItem((long) this.val$notifItemId);
        }
    }

    /* renamed from: com.sygic.aura.poi.PoiManager.5 */
    static class C14325 implements Callback<int[]> {
        final /* synthetic */ int val$group;
        final /* synthetic */ LongPosition val$longPosition;

        C14325(int i, LongPosition longPosition) {
            this.val$group = i;
            this.val$longPosition = longPosition;
        }

        public int[] getMethod() {
            return PoiManager.GetProvidersForGroup(this.val$group, this.val$longPosition != null ? this.val$longPosition.toNativeLong() : LongPosition.InvalidNativeLong);
        }
    }

    /* renamed from: com.sygic.aura.poi.PoiManager.6 */
    static class C14336 implements Callback<Integer> {
        final /* synthetic */ int val$group;
        final /* synthetic */ LongPosition val$longPosition;

        C14336(int i, LongPosition longPosition) {
            this.val$group = i;
            this.val$longPosition = longPosition;
        }

        public Integer getMethod() {
            return Integer.valueOf(PoiManager.GetDefaultProviderForGroup(this.val$group, this.val$longPosition != null ? this.val$longPosition.toNativeLong() : LongPosition.InvalidNativeLong));
        }
    }

    /* renamed from: com.sygic.aura.poi.PoiManager.7 */
    static class C14347 implements Callback<String> {
        C14347() {
        }

        public String getMethod() {
            return PoiManager.GetSelectedPoiTitle();
        }
    }

    private static native int GetDefaultProviderForGroup(int i, long j);

    private static native MapSelection GetNavselFromPorNotifItem(long j);

    private static native MapSelection GetOnlineSelectionAt(long j);

    private static native ListItem[] GetPoiCategoriesByIds(int[] iArr);

    private static native String GetPoiDescription(long j);

    private static native NearbyPoiGroup[] GetPoiGroupsByIds(int[] iArr);

    private static native int[] GetProvidersForGroup(int i, long j);

    private static native String GetSelectedPoiTitle();

    private static native void ShowPoisOnMap(boolean z, long[] jArr);

    public static NearbyPoiGroup[] nativeGetPoiGroupsByIds(int[] ids) {
        return GetPoiGroupsByIds(ids);
    }

    public static ListItem[] nativeGetPoiCategoriesByIds(int[] ids) {
        return GetPoiCategoriesByIds(ids);
    }

    public static String nativeGetPoiDescription(LongPosition lPos) {
        if (SygicProject.IS_PROTOTYPE) {
            return "POI description";
        }
        return (String) new ObjectHandler(new C14281(lPos)).execute().get(null);
    }

    public static MapSelection nativeGetOnlineSelectionAt(LongPosition position) {
        return (MapSelection) new ObjectHandler(new C14292(position)).execute().get(null);
    }

    public static void nativeShowPoisOnMap(boolean isOnline, long[] data) {
        if (!SygicProject.IS_PROTOTYPE) {
            ObjectHandler.postAndWait(new C14303(isOnline, data));
        }
    }

    public static MapSelection nativeGetNavselFromPorNotifItem(int notifItemId) {
        return (MapSelection) new ObjectHandler(new C14314(notifItemId)).execute().get(null);
    }

    public static int[] nativeGetProvidersForGroup(int group, LongPosition longPosition) {
        if (SygicProject.IS_PROTOTYPE) {
            return null;
        }
        return (int[]) new ObjectHandler(new C14325(group, longPosition)).execute().get(null);
    }

    public static int nativeGetDefaultProviderForGroup(int group, LongPosition longPosition) {
        if (SygicProject.IS_PROTOTYPE) {
            return 0;
        }
        return ((Integer) new ObjectHandler(new C14336(group, longPosition)).execute().get(null)).intValue();
    }

    public static String nativeGetSelectedPoiTitle() {
        if (SygicProject.IS_PROTOTYPE) {
            return null;
        }
        return (String) new ObjectHandler(new C14347()).execute().get(null);
    }
}
