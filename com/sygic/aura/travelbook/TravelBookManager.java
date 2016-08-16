package com.sygic.aura.travelbook;

import com.sygic.aura.SygicProject;
import com.sygic.aura.helper.ObjectHandler;
import com.sygic.aura.helper.ObjectHandler.Callback;
import com.sygic.aura.helper.ObjectHandler.VoidCallback;
import com.sygic.aura.travelbook.data.TravelbookGraphData;
import com.sygic.aura.travelbook.data.TravelbookTrackLogItem;
import com.sygic.aura.travelbook.data.TravelbookTrackLogItem.ETravelLogDataType;

public class TravelBookManager {

    /* renamed from: com.sygic.aura.travelbook.TravelBookManager.10 */
    static class AnonymousClass10 implements Callback<Integer> {
        final /* synthetic */ int val$nGraphType;
        final /* synthetic */ int val$nLogIndex;
        final /* synthetic */ int[] val$rect;

        AnonymousClass10(int i, int i2, int[] iArr) {
            this.val$nLogIndex = i;
            this.val$nGraphType = i2;
            this.val$rect = iArr;
        }

        public Integer getMethod() {
            return Integer.valueOf(TravelBookManager.FillGraphData(this.val$nLogIndex, this.val$nGraphType, this.val$rect));
        }
    }

    /* renamed from: com.sygic.aura.travelbook.TravelBookManager.1 */
    static class C17531 implements VoidCallback {
        final /* synthetic */ int val$logId;

        C17531(int i) {
            this.val$logId = i;
        }

        public void getMethod() {
            TravelBookManager.ShowOnMap(this.val$logId);
        }
    }

    /* renamed from: com.sygic.aura.travelbook.TravelBookManager.2 */
    static class C17542 implements VoidCallback {
        C17542() {
        }

        public void getMethod() {
            TravelBookManager.DisableShowOnMap();
        }
    }

    /* renamed from: com.sygic.aura.travelbook.TravelBookManager.3 */
    static class C17553 implements Callback<Boolean> {
        final /* synthetic */ int val$nIndex;

        C17553(int i) {
            this.val$nIndex = i;
        }

        public Boolean getMethod() {
            return Boolean.valueOf(TravelBookManager.DeleteTrackLog(this.val$nIndex));
        }
    }

    /* renamed from: com.sygic.aura.travelbook.TravelBookManager.4 */
    static class C17564 implements Callback<String> {
        final /* synthetic */ ETravelLogDataType val$type;
        final /* synthetic */ int val$value;

        C17564(ETravelLogDataType eTravelLogDataType, int i) {
            this.val$type = eTravelLogDataType;
            this.val$value = i;
        }

        public String getMethod() {
            return TravelBookManager.FormatValue(this.val$type.ordinal(), (float) this.val$value);
        }
    }

    /* renamed from: com.sygic.aura.travelbook.TravelBookManager.5 */
    static class C17575 implements Callback<String> {
        final /* synthetic */ ETravelLogDataType val$type;
        final /* synthetic */ int val$valueX;

        C17575(ETravelLogDataType eTravelLogDataType, int i) {
            this.val$type = eTravelLogDataType;
            this.val$valueX = i;
        }

        public String getMethod() {
            return TravelBookManager.FormatValueX(this.val$type.ordinal(), (float) this.val$valueX);
        }
    }

    /* renamed from: com.sygic.aura.travelbook.TravelBookManager.6 */
    static class C17586 implements Callback<String[]> {
        final /* synthetic */ int val$nLogIndex;
        final /* synthetic */ ETravelLogDataType val$type;

        C17586(int i, ETravelLogDataType eTravelLogDataType) {
            this.val$nLogIndex = i;
            this.val$type = eTravelLogDataType;
        }

        public String[] getMethod() {
            return TravelBookManager.GetMinMaxAvg(this.val$nLogIndex, this.val$type.ordinal());
        }
    }

    /* renamed from: com.sygic.aura.travelbook.TravelBookManager.7 */
    static class C17597 implements VoidCallback {
        final /* synthetic */ boolean val$isFavourite;
        final /* synthetic */ int val$logIndex;

        C17597(int i, boolean z) {
            this.val$logIndex = i;
            this.val$isFavourite = z;
        }

        public void getMethod() {
            TravelBookManager.SetTrackLogFavourite(this.val$logIndex, this.val$isFavourite);
        }
    }

    /* renamed from: com.sygic.aura.travelbook.TravelBookManager.8 */
    static class C17608 implements Callback<TravelbookTrackLogItem[]> {
        C17608() {
        }

        public TravelbookTrackLogItem[] getMethod() {
            return TravelBookManager.GetTracksLogs();
        }
    }

    /* renamed from: com.sygic.aura.travelbook.TravelBookManager.9 */
    static class C17619 implements Callback<TravelbookGraphData[]> {
        final /* synthetic */ int val$nFromIndex;

        C17619(int i) {
            this.val$nFromIndex = i;
        }

        public TravelbookGraphData[] getMethod() {
            return TravelBookManager.GetGraphData(this.val$nFromIndex);
        }
    }

    private static native boolean DeleteTrackLog(int i);

    private static native void DisableShowOnMap();

    private static native int FillGraphData(int i, int i2, int[] iArr);

    private static native String FormatValue(int i, float f);

    private static native String FormatValueX(int i, float f);

    private static native TravelbookGraphData[] GetGraphData(int i);

    private static native String[] GetMinMaxAvg(int i, int i2);

    private static native TravelbookTrackLogItem[] GetTracksLogs();

    private static native void SetTrackLogFavourite(int i, boolean z);

    private static native void ShowOnMap(int i);

    public static void nativeShowOnMap(int logId) {
        if (!SygicProject.IS_PROTOTYPE) {
            ObjectHandler.postAndWait(new C17531(logId));
        }
    }

    public static void nativeDisableShowOnMap() {
        if (!SygicProject.IS_PROTOTYPE) {
            ObjectHandler.postAndWait(new C17542());
        }
    }

    public static boolean nativeDeleteTrackLog(int nIndex) {
        if (SygicProject.IS_PROTOTYPE) {
            return false;
        }
        return ((Boolean) new ObjectHandler(new C17553(nIndex)).execute().get(Boolean.valueOf(false))).booleanValue();
    }

    public static String nativeFormatValue(ETravelLogDataType type, int value) {
        if (SygicProject.IS_PROTOTYPE) {
            return String.valueOf(value);
        }
        return (String) new ObjectHandler(new C17564(type, value)).execute().get(null);
    }

    public static String nativeFormatValueX(ETravelLogDataType type, int valueX) {
        if (SygicProject.IS_PROTOTYPE) {
            return String.valueOf(valueX);
        }
        return (String) new ObjectHandler(new C17575(type, valueX)).execute().get(null);
    }

    public static String[] nativeGetMinMaxAvg(int nLogIndex, ETravelLogDataType type) {
        if (!SygicProject.IS_PROTOTYPE) {
            return (String[]) new ObjectHandler(new C17586(nLogIndex, type)).execute().get(new String[0]);
        }
        return new String[]{"100", "200", "150"};
    }

    public static void nativeSetTrackLogFavourite(int logIndex, boolean isFavourite) {
        if (!SygicProject.IS_PROTOTYPE) {
            ObjectHandler.postAndWait(new C17597(logIndex, isFavourite));
        }
    }

    public static TravelbookTrackLogItem[] nativeGetTracksLogs() {
        if (!SygicProject.IS_PROTOTYPE) {
            return (TravelbookTrackLogItem[]) new ObjectHandler(new C17608()).execute().get(new TravelbookTrackLogItem[0]);
        }
        return new TravelbookTrackLogItem[]{new TravelbookTrackLogItem(1, 123456, "Foo address", "Bar address", "10:00", "12:00", "1.1.2015", "4min 22s", "458m", "20km/h", "3min/km", "258m", "220m", true, ETravelLogDataType.TYPE_SPEED_DISTANCE.ordinal())};
    }

    public static TravelbookGraphData[] nativeGetGraphData(int nFromIndex) {
        if (!SygicProject.IS_PROTOTYPE) {
            return (TravelbookGraphData[]) new ObjectHandler(new C17619(nFromIndex)).execute().get(new TravelbookGraphData[0]);
        }
        return new TravelbookGraphData[]{new TravelbookGraphData(0, 0, 100, 100, true), new TravelbookGraphData(50, 50, 200, 200, false), new TravelbookGraphData(50, 200, 200, 500, true)};
    }

    public static int nativeFillGraphData(int nLogIndex, int nGraphType, int[] rect) {
        if (SygicProject.IS_PROTOTYPE) {
            return 3;
        }
        return ((Integer) new ObjectHandler(new AnonymousClass10(nLogIndex, nGraphType, rect)).execute().get(Integer.valueOf(0))).intValue();
    }

    public static TravelbookGraphData[] getAllGraphData(int logIndex, ETravelLogDataType type, int[] window) {
        int totalPointsCount = nativeFillGraphData(logIndex, type.getValue(), window);
        TravelbookGraphData[] totalPoints = new TravelbookGraphData[totalPointsCount];
        int totalCopyCount = 0;
        while (totalCopyCount < totalPointsCount) {
            TravelbookGraphData[] points = nativeGetGraphData(totalCopyCount);
            int copyCount = Math.min(points.length, totalPoints.length - totalCopyCount);
            System.arraycopy(points, 0, totalPoints, totalCopyCount, copyCount);
            totalCopyCount += copyCount;
        }
        return totalPoints;
    }
}
