package com.sygic.aura.store;

import com.sygic.aura.SygicProject;
import com.sygic.aura.helper.ObjectHandler;
import com.sygic.aura.helper.ObjectHandler.Callback;
import com.sygic.aura.helper.ObjectHandler.VoidCallback;

public class MarketPlaceManager {

    /* renamed from: com.sygic.aura.store.MarketPlaceManager.10 */
    static class AnonymousClass10 implements Callback<Boolean> {
        final /* synthetic */ String val$strCode;

        AnonymousClass10(String str) {
            this.val$strCode = str;
        }

        public Boolean getMethod() {
            return Boolean.valueOf(MarketPlaceManager.EnterProductCode(this.val$strCode));
        }
    }

    /* renamed from: com.sygic.aura.store.MarketPlaceManager.12 */
    static class AnonymousClass12 implements Callback<String> {
        final /* synthetic */ int val$nSampleID;
        final /* synthetic */ String val$strID;
        final /* synthetic */ String val$strURL;

        AnonymousClass12(String str, String str2, int i) {
            this.val$strURL = str;
            this.val$strID = str2;
            this.val$nSampleID = i;
        }

        public String getMethod() {
            return MarketPlaceManager.DownloadSample(this.val$strURL, this.val$strID, this.val$nSampleID);
        }
    }

    /* renamed from: com.sygic.aura.store.MarketPlaceManager.1 */
    static class C16861 implements Callback<Boolean> {
        final /* synthetic */ String val$strCurrencyId;
        final /* synthetic */ String val$strRegionId;

        C16861(String str, String str2) {
            this.val$strRegionId = str;
            this.val$strCurrencyId = str2;
        }

        public Boolean getMethod() {
            return Boolean.valueOf(MarketPlaceManager.InvokeSygicStore(this.val$strRegionId, this.val$strCurrencyId));
        }
    }

    /* renamed from: com.sygic.aura.store.MarketPlaceManager.3 */
    static class C16873 implements Callback<Boolean> {
        final /* synthetic */ String val$strCurrencyId;
        final /* synthetic */ String val$strListId;
        final /* synthetic */ String val$strRegionId;

        C16873(String str, String str2, String str3) {
            this.val$strListId = str;
            this.val$strRegionId = str2;
            this.val$strCurrencyId = str3;
        }

        public Boolean getMethod() {
            return Boolean.valueOf(MarketPlaceManager.RequestList(this.val$strListId, this.val$strRegionId, this.val$strCurrencyId));
        }
    }

    /* renamed from: com.sygic.aura.store.MarketPlaceManager.4 */
    static class C16884 implements Callback<Boolean> {
        final /* synthetic */ int val$nPage;
        final /* synthetic */ String val$strListId;
        final /* synthetic */ String val$strQuery;

        C16884(String str, String str2, int i) {
            this.val$strListId = str;
            this.val$strQuery = str2;
            this.val$nPage = i;
        }

        public Boolean getMethod() {
            return Boolean.valueOf(MarketPlaceManager.RequestSearch(this.val$strListId, this.val$strQuery, this.val$nPage));
        }
    }

    /* renamed from: com.sygic.aura.store.MarketPlaceManager.5 */
    static class C16895 implements Callback<Boolean> {
        final /* synthetic */ String val$strProductId;

        C16895(String str) {
            this.val$strProductId = str;
        }

        public Boolean getMethod() {
            return Boolean.valueOf(MarketPlaceManager.RequestProductDetail(this.val$strProductId));
        }
    }

    /* renamed from: com.sygic.aura.store.MarketPlaceManager.6 */
    static class C16906 implements Callback<Boolean> {
        final /* synthetic */ String val$strProductId;

        C16906(String str) {
            this.val$strProductId = str;
        }

        public Boolean getMethod() {
            return Boolean.valueOf(MarketPlaceManager.RequestGet(this.val$strProductId));
        }
    }

    /* renamed from: com.sygic.aura.store.MarketPlaceManager.7 */
    static class C16917 implements Callback<Boolean> {
        final /* synthetic */ boolean val$bTry;
        final /* synthetic */ String val$strCode;
        final /* synthetic */ String val$strProductId;

        C16917(String str, String str2, boolean z) {
            this.val$strProductId = str;
            this.val$strCode = str2;
            this.val$bTry = z;
        }

        public Boolean getMethod() {
            return Boolean.valueOf(MarketPlaceManager.RequestBuyPrepare(this.val$strProductId, this.val$strCode, this.val$bTry));
        }
    }

    /* renamed from: com.sygic.aura.store.MarketPlaceManager.8 */
    static class C16928 implements Callback<Boolean> {
        final /* synthetic */ String val$strBuyMethod;
        final /* synthetic */ String val$strId;
        final /* synthetic */ String val$strStoreId;
        final /* synthetic */ String val$strTitle;

        C16928(String str, String str2, String str3, String str4) {
            this.val$strTitle = str;
            this.val$strId = str2;
            this.val$strStoreId = str3;
            this.val$strBuyMethod = str4;
        }

        public Boolean getMethod() {
            return Boolean.valueOf(MarketPlaceManager.BuyProduct(this.val$strTitle, this.val$strId, this.val$strStoreId, this.val$strBuyMethod));
        }
    }

    /* renamed from: com.sygic.aura.store.MarketPlaceManager.9 */
    static class C16939 implements VoidCallback {
        C16939() {
        }

        public void getMethod() {
            MarketPlaceManager.ContinueProcess();
        }
    }

    private static native boolean BuyProduct(String str, String str2, String str3, String str4);

    private static native void ContinueProcess();

    private static native String DownloadSample(String str, String str2, int i);

    private static native boolean EnterProductCode(String str);

    private static native boolean InvokeSygicStore(String str, String str2);

    private static native boolean RequestBuyPrepare(String str, String str2, boolean z);

    private static native boolean RequestGet(String str);

    private static native boolean RequestList(String str, String str2, String str3);

    private static native boolean RequestProductDetail(String str);

    private static native boolean RequestSearch(String str, String str2, int i);

    private static native boolean RestoreProducts();

    public static boolean nativeInvokeSygicStore(String strRegionId, String strCurrencyId) {
        if (SygicProject.IS_PROTOTYPE) {
            return true;
        }
        return ((Boolean) new ObjectHandler(new C16861(strRegionId, strCurrencyId)).execute().get(Boolean.valueOf(false))).booleanValue();
    }

    public static boolean nativeRequestList(String strListId) {
        return nativeRequestList(strListId, null, null);
    }

    public static boolean nativeRequestList(String strListId, String strRegionId, String strCurrencyId) {
        if (SygicProject.IS_PROTOTYPE) {
            return true;
        }
        return ((Boolean) new ObjectHandler(new C16873(strListId, strRegionId, strCurrencyId)).execute().get(Boolean.valueOf(false))).booleanValue();
    }

    public static boolean nativeRequestSearch(String strListId, String strQuery, int nPage) {
        if (SygicProject.IS_PROTOTYPE) {
            return true;
        }
        return ((Boolean) new ObjectHandler(new C16884(strListId, strQuery, nPage)).execute().get(Boolean.valueOf(false))).booleanValue();
    }

    public static boolean nativeRequestProductDetail(String strProductId) {
        if (SygicProject.IS_PROTOTYPE) {
            return true;
        }
        return ((Boolean) new ObjectHandler(new C16895(strProductId)).execute().get(Boolean.valueOf(false))).booleanValue();
    }

    public static boolean nativeRequestGet(String strProductId) {
        if (SygicProject.IS_PROTOTYPE) {
            return true;
        }
        return ((Boolean) new ObjectHandler(new C16906(strProductId)).execute().get(Boolean.valueOf(false))).booleanValue();
    }

    public static boolean nativeRequestBuyPrepare(String strProductId, String strCode, boolean bTry) {
        if (SygicProject.IS_PROTOTYPE) {
            return true;
        }
        return ((Boolean) new ObjectHandler(new C16917(strProductId, strCode, bTry)).execute().get(Boolean.valueOf(false))).booleanValue();
    }

    public static boolean nativeBuyProduct(String strTitle, String strId, String strStoreId, String strBuyMethod) {
        if (SygicProject.IS_PROTOTYPE) {
            return true;
        }
        return ((Boolean) new ObjectHandler(new C16928(strTitle, strId, strStoreId, strBuyMethod)).execute().get(Boolean.valueOf(false))).booleanValue();
    }

    public static void nativeContinueProcess() {
        if (!SygicProject.IS_PROTOTYPE) {
            ObjectHandler.postAndWait(new C16939());
        }
    }

    public static boolean nativeEnterProductCode(String strCode) {
        if (SygicProject.IS_PROTOTYPE) {
            return true;
        }
        return ((Boolean) new ObjectHandler(new AnonymousClass10(strCode)).execute().get(Boolean.valueOf(false))).booleanValue();
    }

    public static boolean nativeRestoreProducts() {
        if (SygicProject.IS_PROTOTYPE) {
            return true;
        }
        return ((Boolean) new ObjectHandler(new Callback<Boolean>() {
            public Boolean getMethod() {
                return Boolean.valueOf(MarketPlaceManager.RestoreProducts());
            }
        }).execute().get(Boolean.valueOf(false))).booleanValue();
    }

    public static String nativeDownloadSample(String strURL, String strID, int nSampleID) {
        if (SygicProject.IS_PROTOTYPE) {
            return "";
        }
        return (String) new ObjectHandler(new AnonymousClass12(strURL, strID, nSampleID)).execute().get(null);
    }
}
