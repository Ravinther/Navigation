package com.sygic.aura.feature.store;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import com.sygic.aura.SygicMain;
import com.sygic.aura.analytics.AnalyticsInterface.EventType;
import com.sygic.aura.analytics.InfinarioAnalyticsLogger;
import com.sygic.aura.analytics.SygicAnalyticsLogger;
import com.sygic.aura.feature.store.InAppPurchase.EPaymentResult;
import com.sygic.aura.feature.store.InAppPurchase.EResponseType;
import com.sygic.aura.feature.store.v3.IabException;
import com.sygic.aura.feature.store.v3.IabHelper;
import com.sygic.aura.feature.store.v3.IabHelper.OnIabPurchaseFinishedListener;
import com.sygic.aura.feature.store.v3.IabHelper.OnIabSetupFinishedListener;
import com.sygic.aura.feature.store.v3.IabHelper.QueryInventoryFinishedListener;
import com.sygic.aura.feature.store.v3.IabResult;
import com.sygic.aura.feature.store.v3.Inventory;
import com.sygic.aura.feature.store.v3.Purchase;
import com.sygic.aura.feature.store.v3.SkuDetails;
import com.sygic.aura.utils.AdWordsConversionUtils;
import java.util.List;
import java.util.UUID;

public class GoogleInAppPurchase extends InAppPurchase {
    private static volatile boolean mCheckingRefunds;
    private static volatile boolean mRestoringPurchase;
    private transient SygicConsumeFinishedListener mConsumeFinishedListener;
    private transient IabHelper mHelper;
    private boolean mLocked;
    private transient SygicPurchaseFinishedListener mPurchaseFinishedListener;
    private transient SygicQueryInventoryFinishedListener mQueryInventoryFinishedListener;
    private transient boolean mServiceStarted;

    /* renamed from: com.sygic.aura.feature.store.GoogleInAppPurchase.1 */
    class C12361 implements OnIabSetupFinishedListener {
        C12361() {
        }

        public void onIabSetupFinished(IabResult result) {
            GoogleInAppPurchase.logDebug("startService() - onIabSetupFinished - " + result);
            if (result.isSuccess()) {
                synchronized (GoogleInAppPurchase.this) {
                    GoogleInAppPurchase.this.mServiceStarted = true;
                    GoogleInAppPurchase.this.mLocked = false;
                    GoogleInAppPurchase.this.notifyAll();
                }
                return;
            }
            Log.w("SygicNav.GInAppPurchase", "IabHelper Setup failed!!!");
        }
    }

    /* renamed from: com.sygic.aura.feature.store.GoogleInAppPurchase.2 */
    class C12372 implements Runnable {
        final /* synthetic */ String val$strProductID;

        C12372(String str) {
            this.val$strProductID = str;
        }

        public void run() {
            GoogleInAppPurchase.this.mHelper.launchPurchaseFlow((Activity) GoogleInAppPurchase.this.mContext, this.val$strProductID, "inapp", 227, GoogleInAppPurchase.this.mPurchaseFinishedListener, "");
        }
    }

    /* renamed from: com.sygic.aura.feature.store.GoogleInAppPurchase.3 */
    class C12383 implements Runnable {
        C12383() {
        }

        public void run() {
            GoogleInAppPurchase.this.checkRefunds();
            GoogleInAppPurchase.this.checkQueuedTrans();
        }
    }

    static class SygicConsumeFinishedListener {
        SygicConsumeFinishedListener() {
        }
    }

    class SygicPurchaseFinishedListener implements OnIabPurchaseFinishedListener {
        private final Context mContext;

        /* renamed from: com.sygic.aura.feature.store.GoogleInAppPurchase.SygicPurchaseFinishedListener.1 */
        class C12391 implements QueryInventoryFinishedListener {
            final /* synthetic */ Purchase val$info;

            C12391(Purchase purchase) {
                this.val$info = purchase;
            }

            public void onQueryInventoryFinished(IabResult result, Inventory inv) {
                SkuDetails details = inv.getSkuDetails(this.val$info.getSku());
                String params = (("productId:" + details.getSku()) + ":title:" + details.getTitle()) + ":fb_currency:" + details.getCurrency();
                String priceAmountMicros = details.getPriceAmountMicros();
                if (priceAmountMicros != null) {
                    int length = priceAmountMicros.length();
                    if (length > 6) {
                        priceAmountMicros = priceAmountMicros.substring(0, length - 6) + "." + priceAmountMicros.substring(length - 6);
                    } else {
                        String prefix = "0.";
                        for (int offset = length - 6; offset < 0; offset++) {
                            prefix = prefix + "0";
                        }
                        priceAmountMicros = prefix + priceAmountMicros;
                    }
                }
                Bundle logParams = new Bundle();
                logParams.putString("eventName", "purchased");
                logParams.putString("category", "buy_google_iap");
                logParams.putString("strValue", priceAmountMicros);
                logParams.putString("fbParams", params);
                SygicAnalyticsLogger.logEvent(SygicPurchaseFinishedListener.this.mContext, EventType.IN_APP_PURCHASE, logParams);
                InfinarioAnalyticsLogger.getInstance(SygicPurchaseFinishedListener.this.mContext).trackPurchaseIab(details.getSku(), priceAmountMicros, details.getCurrency());
                Bundle productParams = new Bundle();
                productParams.putString("transaction_id", UUID.randomUUID().toString());
                productParams.putString("affiliation", "Google Play");
                productParams.putString("name", details.getTitle());
                productParams.putString("sku", details.getSku());
                productParams.putFloat("price", Float.parseFloat(priceAmountMicros));
                productParams.putString("currency", details.getCurrency());
                SygicAnalyticsLogger.logEvent(SygicPurchaseFinishedListener.this.mContext, EventType.IN_APP_PURCHASE, productParams);
                AdWordsConversionUtils.reportConversion(SygicPurchaseFinishedListener.this.mContext, priceAmountMicros);
            }
        }

        public SygicPurchaseFinishedListener(Context context) {
            this.mContext = context;
        }

        public void onIabPurchaseFinished(IabResult result, Purchase info) {
            GoogleInAppPurchase.logDebug("SygicPurchaseFinishedListener.onIabPurchaseFinished(): " + result + ", " + info);
            if (result != null) {
                if (!result.isFailure() && info != null) {
                    int nLength = 0;
                    int nType = 0;
                    int nResult = 0;
                    int nState = 0;
                    String strData = info.getSku() + "|" + info.getOriginalJson().trim() + " " + info.getSignature();
                    if (strData != null) {
                        nLength = strData.length();
                    }
                    int iPurchaseState = info.getPurchaseState();
                    if (iPurchaseState == 0) {
                        nType = EResponseType.RtPayment.getValue();
                        nResult = EPaymentResult.PaymentCompleted.getValue();
                        nState = SygicMain.getInstance().ProcessStoreResponse(strData, nLength, nType, nResult);
                        GoogleInAppPurchase.this.mHelper.queryInventoryAsync(true, new C12391(info));
                    } else if (iPurchaseState == 1) {
                        nType = EResponseType.RtRefund.getValue();
                        nResult = EPaymentResult.PaymentCancelled.getValue();
                        nState = SygicMain.getInstance().ProcessStoreResponse(strData, nLength, nType, nResult);
                    } else if (iPurchaseState == 2) {
                        nType = EResponseType.RtRefund.getValue();
                        nResult = EPaymentResult.PaymentNone.getValue();
                        nState = SygicMain.getInstance().ProcessStoreResponse(strData, nLength, nType, nResult);
                    } else {
                        SygicMain.getInstance().ProcessStoreResponse(null, 0, EResponseType.RtPayment.getValue(), EPaymentResult.PaymentNone.getValue());
                    }
                    if (nState == 1 && strData != null) {
                        GoogleInAppPurchase.this.saveQueuedTransaction(this.mContext, strData, nType, nResult);
                    }
                } else if (result.getResponse() == 1) {
                    SygicMain.getInstance().ProcessStoreResponse(null, 0, EResponseType.RtPayment.getValue(), EPaymentResult.PaymentCancelled.getValue());
                } else {
                    if (result.getResponse() == -1005) {
                        Bundle logParams = new Bundle();
                        logParams.putString("eventName", "exited");
                        logParams.putString("category", "buy_google_iap");
                        SygicAnalyticsLogger.logEvent(this.mContext, EventType.IN_APP_PURCHASE, logParams);
                    }
                    SygicMain.getInstance().ProcessStoreResponse(null, 0, EResponseType.RtPayment.getValue(), EPaymentResult.PaymentFailed.getValue());
                }
            }
        }
    }

    class SygicQueryInventoryFinishedListener implements QueryInventoryFinishedListener {
        private final Context mContext;

        public SygicQueryInventoryFinishedListener(Context context) {
            this.mContext = context;
        }

        public void onQueryInventoryFinished(IabResult result, Inventory inv) {
            GoogleInAppPurchase.logDebug("SygicQueryInventoryFinishedListener.onQueryInventoryFinished(): " + result + ", " + inv);
            if (result == null || result.isFailure()) {
                Log.e("SygicNav.GInAppPurchase", "onQueryInventoryFinished failed!!!");
                SygicMain.getInstance().ProcessStoreResponse(null, 0, EResponseType.RtRestore.getValue(), EPaymentResult.PaymentNone.getValue());
            } else if (inv == null) {
                Log.w("SygicNav.GInAppPurchase", "onQueryInventoryFinished - empty inventory");
                SygicMain.getInstance().ProcessStoreResponse(null, 0, EResponseType.RtRestore.getValue(), EPaymentResult.PaymentNone.getValue());
            } else {
                GoogleInAppPurchase.this.handleSkus(inv);
            }
        }
    }

    static {
        mRestoringPurchase = false;
        mCheckingRefunds = false;
    }

    protected GoogleInAppPurchase(Context context, Handler handler) {
        super(context, handler);
        this.mServiceStarted = false;
        this.mLocked = false;
    }

    protected boolean startService() {
        this.mHelper = new IabHelper(this.mContext, null);
        if (this.mHelper == null) {
            return false;
        }
        this.mHelper.enableDebugLogging(false);
        this.mQueryInventoryFinishedListener = new SygicQueryInventoryFinishedListener(this.mContext);
        this.mPurchaseFinishedListener = new SygicPurchaseFinishedListener(this.mContext);
        this.mConsumeFinishedListener = new SygicConsumeFinishedListener();
        this.mLocked = true;
        this.mHelper.startSetup(new C12361());
        return true;
    }

    public void stopService() {
        if (this.mHelper != null) {
            this.mHelper.dispose();
            this.mHelper = null;
        }
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public boolean isBillingSupported() {
        /*
        r3 = this;
        r1 = "isBillingSupported() ";
        logDebug(r1);
        monitor-enter(r3);
    L_0x0007:
        r1 = r3.mLocked;	 Catch:{ all -> 0x0012 }
        if (r1 == 0) goto L_0x0022;
    L_0x000b:
        r3.wait();	 Catch:{ InterruptedException -> 0x0015 }
        r1 = 0;
        r3.mLocked = r1;	 Catch:{ all -> 0x0012 }
        goto L_0x0007;
    L_0x0012:
        r1 = move-exception;
        monitor-exit(r3);	 Catch:{ all -> 0x0012 }
        throw r1;
    L_0x0015:
        r0 = move-exception;
        r0.printStackTrace();	 Catch:{ all -> 0x001d }
        r1 = 0;
        r3.mLocked = r1;	 Catch:{ all -> 0x0012 }
        goto L_0x0007;
    L_0x001d:
        r1 = move-exception;
        r2 = 0;
        r3.mLocked = r2;	 Catch:{ all -> 0x0012 }
        throw r1;	 Catch:{ all -> 0x0012 }
    L_0x0022:
        monitor-exit(r3);	 Catch:{ all -> 0x0012 }
        r1 = new java.lang.StringBuilder;
        r1.<init>();
        r2 = "isBillingSupported() - ";
        r1 = r1.append(r2);
        r2 = r3.mServiceStarted;
        r1 = r1.append(r2);
        r1 = r1.toString();
        logDebug(r1);
        r1 = r3.mServiceStarted;
        return r1;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.sygic.aura.feature.store.GoogleInAppPurchase.isBillingSupported():boolean");
    }

    public boolean buyProduct(String strProductID) {
        logDebug("buyProduct(" + strProductID + ")");
        if (this.mHelper == null || strProductID == null) {
            return false;
        }
        Bundle logParams = new Bundle();
        logParams.putString("eventName", "entered");
        logParams.putString("category", "buy_google_iap");
        SygicAnalyticsLogger.logEvent(this.mContext, EventType.IN_APP_PURCHASE, logParams);
        this.mHandler.post(new C12372(strProductID));
        return true;
    }

    public boolean restorePurchase() {
        logDebug("restorePurchase()");
        if (this.mHelper == null) {
            Log.w("SygicNav.GInAppPurchase", "restorePurchase() - (mHelper == null)");
            return false;
        }
        mRestoringPurchase = true;
        try {
            return handleSkus(this.mHelper.queryInventory(false, null));
        } catch (IabException e) {
            e.printStackTrace();
            return false;
        }
    }

    public void checkQueuedTransactions() {
        logDebug("checkQueuedTransactions()");
        this.mHandler.post(new C12383());
    }

    public void onResume() {
        this.m_bResumed = true;
    }

    public void onPause() {
        this.m_bResumed = false;
    }

    public boolean handleActivityResult(int requestCode, int resultCode, Intent dataIntent) {
        return this.mHelper.handleActivityResult(requestCode, resultCode, dataIntent);
    }

    private void checkRefunds() {
        mCheckingRefunds = true;
        this.mHelper.queryInventoryAsync(this.mQueryInventoryFinishedListener);
    }

    private static void logDebug(String msg) {
    }

    private boolean handleSkus(Inventory inv) {
        List<String> skusList = inv.getAllOwnedSkus();
        if (skusList == null || skusList.isEmpty()) {
            SygicMain.getInstance().ProcessStoreResponse(null, 0, EResponseType.RtRestore.getValue(), EPaymentResult.PaymentNone.getValue());
            mRestoringPurchase = false;
            mCheckingRefunds = false;
            return false;
        }
        boolean error = true;
        for (String strSku : skusList) {
            Purchase purchase = inv.getPurchase(strSku);
            if (purchase.getItemType().equals("inapp")) {
                int i;
                int nLength = 0;
                int nType = 0;
                int nResult = 0;
                int nState = 0;
                String strData = purchase.getOriginalJson().trim() + " " + purchase.getSignature();
                if (strData != null) {
                    nLength = strData.length();
                }
                int iPurchaseState = purchase.getPurchaseState();
                if (mCheckingRefunds) {
                    if (iPurchaseState == 2) {
                        nType = EResponseType.RtRefund.getValue();
                        nResult = EPaymentResult.PaymentNone.getValue();
                        nState = SygicMain.getInstance().ProcessStoreResponse(strData, nLength, nType, nResult);
                    }
                } else if (iPurchaseState == 0) {
                    nType = EResponseType.RtPayment.getValue();
                    nResult = EPaymentResult.PaymentCompleted.getValue();
                    if (mRestoringPurchase) {
                        mRestoringPurchase = false;
                        nType = EResponseType.RtRestore.getValue();
                        nResult = EPaymentResult.PaymentNone.getValue();
                    }
                    nState = SygicMain.getInstance().ProcessStoreResponse(strData, nLength, nType, nResult);
                } else {
                    Log.e("SygicNav.GInAppPurchase", "Unknown purchae state(" + iPurchaseState + ") for: " + strSku);
                    SygicMain.getInstance().ProcessStoreResponse(null, 0, EResponseType.RtRestore.getValue(), EPaymentResult.PaymentNone.getValue());
                }
                if (nState == 1 && strData != null) {
                    saveQueuedTransaction(this.mContext, purchase.getSku() + "|" + strData, nType, nResult);
                }
                if (nState != 0) {
                    i = 1;
                } else {
                    i = 0;
                }
                error &= i;
            }
        }
        mRestoringPurchase = false;
        mCheckingRefunds = false;
        return !error;
    }
}
