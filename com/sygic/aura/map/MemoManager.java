package com.sygic.aura.map;

import android.content.ContentResolver;
import android.content.Context;
import android.os.Bundle;
import com.sygic.aura.SygicProject;
import com.sygic.aura.dashboard.WidgetItem;
import com.sygic.aura.data.LongPosition;
import com.sygic.aura.helper.CrashlyticsHelper;
import com.sygic.aura.helper.EventReceivers.MapEventsReceiver;
import com.sygic.aura.helper.ObjectHandler;
import com.sygic.aura.helper.ObjectHandler.Callback;
import com.sygic.aura.helper.ObjectHandler.VoidCallback;
import com.sygic.aura.map.data.MemoItem;
import com.sygic.aura.map.data.MemoItem.EMemoType;
import com.sygic.aura.map.data.MemoItem.ESelType;
import com.sygic.aura.map.data.map_selection.MapSelection;
import com.sygic.aura.settings.data.SettingsManager;
import com.sygic.widget.TrafficWidgetProvider;
import com.sygic.widget.WidgetDataProvider;
import com.sygic.widget.places.data.PlaceEntry;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import loquendo.tts.engine.TTSConst;

public class MemoManager {
    private static final String LOG_TAG;

    /* renamed from: com.sygic.aura.map.MemoManager.10 */
    static class AnonymousClass10 implements Callback<Boolean> {
        final /* synthetic */ LongPosition val$longPosition;

        AnonymousClass10(LongPosition longPosition) {
            this.val$longPosition = longPosition;
        }

        public Boolean getMethod() {
            return Boolean.valueOf(MemoManager.IsItemFavorite(this.val$longPosition.toNativeLong()));
        }
    }

    /* renamed from: com.sygic.aura.map.MemoManager.12 */
    static class AnonymousClass12 implements Callback<Boolean> {
        final /* synthetic */ LongPosition val$longPosition;

        AnonymousClass12(LongPosition longPosition) {
            this.val$longPosition = longPosition;
        }

        public Boolean getMethod() {
            return Boolean.valueOf(MemoManager.RemoveMemoFavorite(this.val$longPosition.toNativeLong()));
        }
    }

    /* renamed from: com.sygic.aura.map.MemoManager.13 */
    static class AnonymousClass13 implements VoidCallback {
        final /* synthetic */ String val$routeName;

        AnonymousClass13(String str) {
            this.val$routeName = str;
        }

        public void getMethod() {
            MemoManager.AddRoute(this.val$routeName);
        }
    }

    /* renamed from: com.sygic.aura.map.MemoManager.14 */
    static class AnonymousClass14 implements VoidCallback {
        final /* synthetic */ String val$routeName;

        AnonymousClass14(String str) {
            this.val$routeName = str;
        }

        public void getMethod() {
            MemoManager.RemoveRoute(this.val$routeName);
        }
    }

    /* renamed from: com.sygic.aura.map.MemoManager.15 */
    static class AnonymousClass15 implements Callback<MapSelection> {
        final /* synthetic */ long val$memoId;

        AnonymousClass15(long j) {
            this.val$memoId = j;
        }

        public MapSelection getMethod() {
            return MemoManager.GetMapSelFromMemoID(this.val$memoId);
        }
    }

    /* renamed from: com.sygic.aura.map.MemoManager.16 */
    static class AnonymousClass16 implements Callback<Integer> {
        final /* synthetic */ int val$memoId;

        AnonymousClass16(int i) {
            this.val$memoId = i;
        }

        public Integer getMethod() {
            return Integer.valueOf(MemoManager.GetWidgetIDFromMemoID(this.val$memoId));
        }
    }

    /* renamed from: com.sygic.aura.map.MemoManager.18 */
    static /* synthetic */ class AnonymousClass18 {
        static final /* synthetic */ int[] $SwitchMap$com$sygic$aura$map$data$MemoItem$EMemoType;

        static {
            $SwitchMap$com$sygic$aura$map$data$MemoItem$EMemoType = new int[EMemoType.values().length];
            try {
                $SwitchMap$com$sygic$aura$map$data$MemoItem$EMemoType[EMemoType.memoWork.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                $SwitchMap$com$sygic$aura$map$data$MemoItem$EMemoType[EMemoType.memoHome.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                $SwitchMap$com$sygic$aura$map$data$MemoItem$EMemoType[EMemoType.memoParking.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
        }
    }

    /* renamed from: com.sygic.aura.map.MemoManager.1 */
    static class C13041 implements Callback<Boolean> {
        final /* synthetic */ long val$memoId;

        C13041(long j) {
            this.val$memoId = j;
        }

        public Boolean getMethod() {
            return Boolean.valueOf(MemoManager.RemoveItem(this.val$memoId));
        }
    }

    /* renamed from: com.sygic.aura.map.MemoManager.2 */
    static class C13052 implements Callback<Boolean> {
        final /* synthetic */ long val$memoId;
        final /* synthetic */ String val$newName;

        C13052(long j, String str) {
            this.val$memoId = j;
            this.val$newName = str;
        }

        public Boolean getMethod() {
            return Boolean.valueOf(MemoManager.RenameItem(this.val$memoId, this.val$newName));
        }
    }

    /* renamed from: com.sygic.aura.map.MemoManager.3 */
    static class C13063 implements VoidCallback {
        final /* synthetic */ long val$memoIdFrom;
        final /* synthetic */ long val$memoIdTo;

        C13063(long j, long j2) {
            this.val$memoIdFrom = j;
            this.val$memoIdTo = j2;
        }

        public void getMethod() {
            MemoManager.ReorderItem(this.val$memoIdFrom, this.val$memoIdTo);
        }
    }

    /* renamed from: com.sygic.aura.map.MemoManager.4 */
    static class C13074 implements Callback<Long> {
        final /* synthetic */ LongPosition val$longPosition;
        final /* synthetic */ int val$poiCategory;
        final /* synthetic */ String val$strName;

        C13074(LongPosition longPosition, String str, int i) {
            this.val$longPosition = longPosition;
            this.val$strName = str;
            this.val$poiCategory = i;
        }

        public Long getMethod() {
            return Long.valueOf(MemoManager.AddMemo(this.val$longPosition.toNativeLong(), this.val$strName, this.val$poiCategory, EMemoType.memoFavorites.getValue()));
        }
    }

    /* renamed from: com.sygic.aura.map.MemoManager.5 */
    static class C13085 implements Callback<Long> {
        final /* synthetic */ LongPosition val$longPosition;
        final /* synthetic */ String val$strName;
        final /* synthetic */ EMemoType val$type;

        C13085(LongPosition longPosition, String str, EMemoType eMemoType) {
            this.val$longPosition = longPosition;
            this.val$strName = str;
            this.val$type = eMemoType;
        }

        public Long getMethod() {
            return Long.valueOf(MemoManager.AddMemo(this.val$longPosition.toNativeLong(), this.val$strName, 0, this.val$type.getValue()));
        }
    }

    /* renamed from: com.sygic.aura.map.MemoManager.6 */
    static class C13096 implements Callback<MemoItem> {
        C13096() {
        }

        public MemoItem getMethod() {
            return MemoManager.GetMemoByType(EMemoType.memoHome.getValue());
        }
    }

    /* renamed from: com.sygic.aura.map.MemoManager.7 */
    static class C13107 implements Callback<MemoItem> {
        C13107() {
        }

        public MemoItem getMethod() {
            return MemoManager.GetMemoByType(EMemoType.memoParking.getValue());
        }
    }

    /* renamed from: com.sygic.aura.map.MemoManager.8 */
    static class C13118 implements Callback<Boolean> {
        final /* synthetic */ LongPosition val$longPosition;
        final /* synthetic */ EMemoType val$type;

        C13118(LongPosition longPosition, EMemoType eMemoType) {
            this.val$longPosition = longPosition;
            this.val$type = eMemoType;
        }

        public Boolean getMethod() {
            return Boolean.valueOf(MemoManager.HasItemType(this.val$longPosition.toNativeLong(), this.val$type.getValue()));
        }
    }

    /* renamed from: com.sygic.aura.map.MemoManager.9 */
    static class C13129 implements VoidCallback {
        final /* synthetic */ EMemoType val$type;

        C13129(EMemoType eMemoType) {
            this.val$type = eMemoType;
        }

        public void getMethod() {
            MemoManager.RemoveAllMemoByType(this.val$type.getValue());
        }
    }

    private static native long AddMemo(long j, String str, int i, int i2);

    private static native void AddRoute(String str);

    private static native MapSelection GetMapSelFromMemoID(long j);

    private static native MemoItem GetMemoByType(int i);

    private static native int GetWidgetIDFromMemoID(int i);

    private static native boolean HasItemType(long j, int i);

    private static native boolean IsItemFavorite(long j);

    private static native void RemoveAllMemoByType(int i);

    private static native boolean RemoveItem(long j);

    private static native boolean RemoveMemoFavorite(long j);

    private static native void RemoveRoute(String str);

    private static native boolean RenameItem(long j, String str);

    private static native void ReorderItem(long j, long j2);

    static {
        LOG_TAG = MemoManager.class.getSimpleName();
    }

    public static boolean nativeRemoveItem(Context context, long memoId) {
        if (SygicProject.IS_PROTOTYPE) {
            return true;
        }
        boolean result = ((Boolean) new ObjectHandler(new C13041(memoId)).execute().get(Boolean.valueOf(false))).booleanValue();
        if (!result) {
            return result;
        }
        syncWidgetFavorites(context);
        return result;
    }

    public static boolean nativeRenameItem(Context context, long memoId, String newName) {
        if (SygicProject.IS_PROTOTYPE) {
            return true;
        }
        boolean result = ((Boolean) new ObjectHandler(new C13052(memoId, newName)).execute().get(Boolean.valueOf(false))).booleanValue();
        if (!result) {
            return result;
        }
        syncWidgetFavorites(context);
        return result;
    }

    public static void nativeReorderItem(long memoIdFrom, long memoIdTo) {
        if (!SygicProject.IS_PROTOTYPE) {
            ObjectHandler.postAndWait(new C13063(memoIdFrom, memoIdTo));
        }
    }

    public static long nativeAddFavorite(Context context, LongPosition longPosition, String strName) {
        return nativeAddFavorite(context, longPosition, strName, 0);
    }

    public static long nativeAddFavorite(Context context, LongPosition longPosition, String strName, int poiCategory) {
        if (SygicProject.IS_PROTOTYPE) {
            return 0;
        }
        long result = ((Long) new ObjectHandler(new C13074(longPosition, strName, poiCategory)).execute().get(Long.valueOf(0))).longValue();
        if (result == 0) {
            return result;
        }
        syncWidgetFavorites(context);
        return result;
    }

    public static long nativeAddPlugin(Context context, LongPosition longPosition, String strName, EMemoType type) {
        if (SygicProject.IS_PROTOTYPE) {
            return 0;
        }
        long result = ((Long) new ObjectHandler(new C13085(longPosition, strName, type)).execute().get(Long.valueOf(0))).longValue();
        if (result <= 0) {
            return result;
        }
        syncWidgetItem(context, new PlaceEntry(strName, longPosition.toNativeLong(), type));
        MapEventsReceiver.onMemoAdded(longPosition, strName, type);
        return result;
    }

    public static MemoItem nativeGetHome() {
        if (SygicProject.IS_PROTOTYPE) {
            return new MemoItem(1, "Test Home", "Orig test home", new LongPosition(1712644, 4815009), EMemoType.memoHome, ESelType.selHome, 0);
        }
        return (MemoItem) new ObjectHandler(new C13096()).execute().get(null);
    }

    public static MemoItem nativeGetParking() {
        if (SygicProject.IS_PROTOTYPE) {
            return new MemoItem(1, "Test Parking", "Karadzicova 14, Bratislava", new LongPosition(1712644, 4815009), EMemoType.memoParking, ESelType.selParking, 0);
        }
        return (MemoItem) new ObjectHandler(new C13107()).execute().get(null);
    }

    public static boolean nativeHasItemType(LongPosition longPosition, EMemoType type) {
        if (SygicProject.IS_PROTOTYPE) {
            return false;
        }
        return ((Boolean) new ObjectHandler(new C13118(longPosition, type)).execute().get(Boolean.valueOf(false))).booleanValue();
    }

    public static void nativeRemoveAllMemoByType(Context context, EMemoType type) {
        if (!SygicProject.IS_PROTOTYPE) {
            ObjectHandler.postAndWait(new C13129(type));
        }
        syncWidgetItem(context, PlaceEntry.fromJSON("\"type\":\"" + type.getValue() + "\""));
    }

    public static boolean nativeIsItemFavorite(LongPosition longPosition) {
        if (SygicProject.IS_PROTOTYPE) {
            return false;
        }
        return ((Boolean) new ObjectHandler(new AnonymousClass10(longPosition)).execute().get(Boolean.valueOf(false))).booleanValue();
    }

    public static boolean nativeRemoveMemoFavorite(Context context, LongPosition longPosition) {
        boolean z = false;
        if (!SygicProject.IS_PROTOTYPE) {
            z = ((Boolean) new ObjectHandler(new AnonymousClass12(longPosition)).execute().get(Boolean.valueOf(false))).booleanValue();
            if (z) {
                syncWidgetFavorites(context);
            }
        }
        return z;
    }

    public static void nativeAddRoute(String routeName) {
        if (!SygicProject.IS_PROTOTYPE) {
            ObjectHandler.postAndWait(new AnonymousClass13(routeName));
        }
    }

    public static void nativeRemoveRoute(String routeName) {
        if (!SygicProject.IS_PROTOTYPE) {
            ObjectHandler.postAndWait(new AnonymousClass14(routeName));
        }
    }

    public static MapSelection nativeGetMapSel(WidgetItem widgetItem) {
        return nativeGetMapSel(widgetItem.getMemoId());
    }

    public static MapSelection nativeGetMapSel(long memoId) {
        if (SygicProject.IS_PROTOTYPE) {
            return MapSelection.Empty;
        }
        return (MapSelection) new ObjectHandler(new AnonymousClass15(memoId)).execute().get(MapSelection.Empty);
    }

    public static int nativeGetWidgetIDFromMemoID(int memoId) {
        if (SygicProject.IS_PROTOTYPE) {
            return 2;
        }
        return ((Integer) new ObjectHandler(new AnonymousClass16(memoId)).execute().get(Integer.valueOf(0))).intValue();
    }

    public static MemoItem nativeGetWork() {
        if (SygicProject.IS_PROTOTYPE) {
            return null;
        }
        return (MemoItem) new ObjectHandler(new Callback<MemoItem>() {
            public MemoItem getMethod() {
                return MemoManager.GetMemoByType(EMemoType.memoWork.getValue());
            }
        }).execute().get(null);
    }

    private static void syncWidgetFavorites(Context context) {
        PlaceEntry[] entries = SettingsManager.nativeGetWidgetPlaces();
        if (entries != null) {
            Set<String> mSavedSet = new HashSet(entries.length);
            for (PlaceEntry entry : entries) {
                mSavedSet.add(entry.toJSON());
            }
            ContentResolver resolver = context.getContentResolver();
            Bundle bundle = new Bundle(1);
            bundle.putStringArrayList(TrafficWidgetProvider.PREFERENCE_PLACES_KEY, new ArrayList(mSavedSet));
            resolver.call(WidgetDataProvider.getContentUri(context), "savePlaces", null, bundle);
        }
    }

    private static void syncWidgetItem(Context context, PlaceEntry item) {
        if (item != null) {
            String strKey;
            switch (AnonymousClass18.$SwitchMap$com$sygic$aura$map$data$MemoItem$EMemoType[item.getType().ordinal()]) {
                case TTSConst.TTSMULTILINE /*1*/:
                    strKey = TrafficWidgetProvider.PREFERENCE_WORK_KEY;
                    break;
                case TTSConst.TTSPARAGRAPH /*2*/:
                    strKey = TrafficWidgetProvider.PREFERENCE_HOME_KEY;
                    break;
                case TTSConst.TTSUNICODE /*3*/:
                    strKey = TrafficWidgetProvider.PREFERENCE_PARKING_KEY;
                    break;
                default:
                    CrashlyticsHelper.logWarning(LOG_TAG, "Unsupported widget type");
                    return;
            }
            ContentResolver resolver = context.getContentResolver();
            Bundle bundle = new Bundle(1);
            bundle.putString("item", item.toJSON());
            resolver.call(WidgetDataProvider.getContentUri(context), "saveItem", strKey, bundle);
        }
    }
}
