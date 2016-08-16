package com.sygic.aura.settings.data;

import com.sygic.aura.SygicProject;
import com.sygic.aura.helper.ObjectHandler;
import com.sygic.aura.helper.ObjectHandler.Callback;
import com.sygic.aura.helper.ObjectHandler.VoidCallback;

public class PoiSettingsManager {

    /* renamed from: com.sygic.aura.settings.data.PoiSettingsManager.10 */
    static class AnonymousClass10 implements Callback<Boolean> {
        final /* synthetic */ int val$nId;

        AnonymousClass10(int i) {
            this.val$nId = i;
        }

        public Boolean getMethod() {
            return Boolean.valueOf(PoiSettingsManager.GetShowWarnStatusGroup(this.val$nId));
        }
    }

    /* renamed from: com.sygic.aura.settings.data.PoiSettingsManager.1 */
    static class C16091 implements Callback<PoiCategoryEntry[]> {
        final /* synthetic */ int val$nGroupID;
        final /* synthetic */ EPoiType val$poiType;

        C16091(int i, EPoiType ePoiType) {
            this.val$nGroupID = i;
            this.val$poiType = ePoiType;
        }

        public PoiCategoryEntry[] getMethod() {
            return PoiSettingsManager.GetPoiCategoriesByGroup(this.val$nGroupID, this.val$poiType.getValue());
        }
    }

    /* renamed from: com.sygic.aura.settings.data.PoiSettingsManager.2 */
    static class C16102 implements Callback<PoiGroupEntry[]> {
        final /* synthetic */ EPoiType val$poiType;

        C16102(EPoiType ePoiType) {
            this.val$poiType = ePoiType;
        }

        public PoiGroupEntry[] getMethod() {
            return PoiSettingsManager.GetPoiGroups(this.val$poiType.getValue());
        }
    }

    /* renamed from: com.sygic.aura.settings.data.PoiSettingsManager.3 */
    static class C16113 implements VoidCallback {
        final /* synthetic */ boolean val$bIsCustom;
        final /* synthetic */ boolean val$bIsEnabled;
        final /* synthetic */ int val$nId;

        C16113(int i, boolean z, boolean z2) {
            this.val$nId = i;
            this.val$bIsEnabled = z;
            this.val$bIsCustom = z2;
        }

        public void getMethod() {
            PoiSettingsManager.SetShowStatus(this.val$nId, this.val$bIsEnabled, this.val$bIsCustom);
        }
    }

    /* renamed from: com.sygic.aura.settings.data.PoiSettingsManager.4 */
    static class C16124 implements VoidCallback {
        final /* synthetic */ boolean val$bIsCustom;
        final /* synthetic */ boolean val$bIsEnabled;
        final /* synthetic */ int val$nId;

        C16124(int i, boolean z, boolean z2) {
            this.val$nId = i;
            this.val$bIsEnabled = z;
            this.val$bIsCustom = z2;
        }

        public void getMethod() {
            PoiSettingsManager.SetShowWarnStatus(this.val$nId, this.val$bIsEnabled, this.val$bIsCustom);
        }
    }

    /* renamed from: com.sygic.aura.settings.data.PoiSettingsManager.5 */
    static class C16135 implements VoidCallback {
        final /* synthetic */ boolean val$bIsEnabled;

        C16135(boolean z) {
            this.val$bIsEnabled = z;
        }

        public void getMethod() {
            PoiSettingsManager.SetShowStatusAll(this.val$bIsEnabled);
        }
    }

    /* renamed from: com.sygic.aura.settings.data.PoiSettingsManager.6 */
    static class C16146 implements VoidCallback {
        final /* synthetic */ boolean val$bIsEnabled;
        final /* synthetic */ int val$nId;

        C16146(int i, boolean z) {
            this.val$nId = i;
            this.val$bIsEnabled = z;
        }

        public void getMethod() {
            PoiSettingsManager.SetShowStatusGroup(this.val$nId, this.val$bIsEnabled);
        }
    }

    /* renamed from: com.sygic.aura.settings.data.PoiSettingsManager.7 */
    static class C16157 implements VoidCallback {
        final /* synthetic */ boolean val$bIsEnabled;
        final /* synthetic */ int val$nId;

        C16157(int i, boolean z) {
            this.val$nId = i;
            this.val$bIsEnabled = z;
        }

        public void getMethod() {
            PoiSettingsManager.SetShowWarnStatusGroup(this.val$nId, this.val$bIsEnabled);
        }
    }

    /* renamed from: com.sygic.aura.settings.data.PoiSettingsManager.8 */
    static class C16168 implements Callback<Boolean> {
        final /* synthetic */ int val$nId;

        C16168(int i) {
            this.val$nId = i;
        }

        public Boolean getMethod() {
            return Boolean.valueOf(PoiSettingsManager.GetShowStatusGroup(this.val$nId));
        }
    }

    /* renamed from: com.sygic.aura.settings.data.PoiSettingsManager.9 */
    static class C16179 implements Callback<Boolean> {
        C16179() {
        }

        public Boolean getMethod() {
            return Boolean.valueOf(PoiSettingsManager.GetShowStatusAll());
        }
    }

    public enum EPoiType {
        ePoiDisplay(0),
        ePoiWarn(1);
        
        int nValue;

        private EPoiType(int val) {
            this.nValue = val;
        }

        public int getValue() {
            return this.nValue;
        }
    }

    private static native PoiCategoryEntry[] GetPoiCategoriesByGroup(int i, int i2);

    private static native PoiGroupEntry[] GetPoiGroups(int i);

    private static native boolean GetShowStatusAll();

    private static native boolean GetShowStatusGroup(int i);

    private static native boolean GetShowWarnStatusAll();

    private static native boolean GetShowWarnStatusGroup(int i);

    private static native void ImportCustomPoi();

    private static native void SetShowStatus(int i, boolean z, boolean z2);

    private static native void SetShowStatusAll(boolean z);

    private static native void SetShowStatusGroup(int i, boolean z);

    private static native void SetShowWarnStatus(int i, boolean z, boolean z2);

    private static native void SetShowWarnStatusGroup(int i, boolean z);

    public static PoiCategoryEntry[] nativeGetPoiCategoriesByGroup(int nGroupID, EPoiType poiType) {
        if (SygicProject.IS_PROTOTYPE) {
            return null;
        }
        return (PoiCategoryEntry[]) new ObjectHandler(new C16091(nGroupID, poiType)).execute().get(new PoiCategoryEntry[0]);
    }

    public static PoiGroupEntry[] nativeGetPoiGroups(EPoiType poiType) {
        if (SygicProject.IS_PROTOTYPE) {
            return null;
        }
        return (PoiGroupEntry[]) new ObjectHandler(new C16102(poiType)).execute().get(new PoiGroupEntry[0]);
    }

    public static void nativeSetShowStatus(int nId, EPoiType poiType, boolean bIsEnabled, boolean bIsCustom) {
        if (poiType == EPoiType.ePoiDisplay) {
            nativeSetShowStatus(nId, bIsEnabled, bIsCustom);
        } else {
            nativeSetShowWarnStatus(nId, bIsEnabled, bIsCustom);
        }
    }

    protected static void nativeSetShowStatus(int nId, boolean bIsEnabled, boolean bIsCustom) {
        if (!SygicProject.IS_PROTOTYPE) {
            ObjectHandler.postAndWait(new C16113(nId, bIsEnabled, bIsCustom));
        }
    }

    protected static void nativeSetShowWarnStatus(int nId, boolean bIsEnabled, boolean bIsCustom) {
        if (!SygicProject.IS_PROTOTYPE) {
            ObjectHandler.postAndWait(new C16124(nId, bIsEnabled, bIsCustom));
        }
    }

    public static void nativeSetShowStatusAll(boolean bIsEnabled) {
        if (!SygicProject.IS_PROTOTYPE) {
            ObjectHandler.postAndWait(new C16135(bIsEnabled));
        }
    }

    public static void nativeSetShowStatusGroup(int nId, EPoiType poiType, boolean bIsEnabled) {
        if (poiType == EPoiType.ePoiDisplay) {
            nativeSetShowStatusGroup(nId, bIsEnabled);
        } else {
            nativeSetShowWarnStatusGroup(nId, bIsEnabled);
        }
    }

    public static void nativeSetShowStatusGroup(int nId, boolean bIsEnabled) {
        if (!SygicProject.IS_PROTOTYPE) {
            ObjectHandler.postAndWait(new C16146(nId, bIsEnabled));
        }
    }

    public static void nativeSetShowWarnStatusGroup(int nId, boolean bIsEnabled) {
        if (!SygicProject.IS_PROTOTYPE) {
            ObjectHandler.postAndWait(new C16157(nId, bIsEnabled));
        }
    }

    public static boolean nativeGetShowStatusGroup(int nId) {
        if (SygicProject.IS_PROTOTYPE) {
            return true;
        }
        return ((Boolean) new ObjectHandler(new C16168(nId)).execute().get(Boolean.valueOf(false))).booleanValue();
    }

    public static boolean nativeGetShowStatusAll() {
        if (SygicProject.IS_PROTOTYPE) {
            return true;
        }
        return ((Boolean) new ObjectHandler(new C16179()).execute().get(Boolean.valueOf(false))).booleanValue();
    }

    public static boolean nativeGetShowWarnStatusGroup(int nId) {
        if (SygicProject.IS_PROTOTYPE) {
            return true;
        }
        return ((Boolean) new ObjectHandler(new AnonymousClass10(nId)).execute().get(Boolean.valueOf(false))).booleanValue();
    }

    public static boolean nativeGetShowWarnStatusAll() {
        if (SygicProject.IS_PROTOTYPE) {
            return true;
        }
        return ((Boolean) new ObjectHandler(new Callback<Boolean>() {
            public Boolean getMethod() {
                return Boolean.valueOf(PoiSettingsManager.GetShowWarnStatusAll());
            }
        }).execute().get(Boolean.valueOf(false))).booleanValue();
    }

    public static void nativeImportCustomPoi() {
        if (!SygicProject.IS_PROTOTYPE) {
            ObjectHandler.postAndWait(new VoidCallback() {
                public void getMethod() {
                    PoiSettingsManager.ImportCustomPoi();
                }
            });
        }
    }
}
