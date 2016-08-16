package com.sygic.aura.feature.store;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.text.TextUtils;
import com.amazon.device.iap.PurchasingListener;
import com.amazon.device.iap.PurchasingService;
import com.amazon.device.iap.model.ProductDataResponse;
import com.amazon.device.iap.model.PurchaseResponse;
import com.amazon.device.iap.model.PurchaseUpdatesResponse;
import com.amazon.device.iap.model.Receipt;
import com.amazon.device.iap.model.UserDataResponse;
import com.amazon.device.iap.model.UserDataResponse.RequestStatus;
import com.sygic.aura.SygicMain;
import com.sygic.aura.feature.store.InAppPurchase.EPaymentResult;
import com.sygic.aura.feature.store.InAppPurchase.EResponseType;
import java.util.List;
import loquendo.tts.engine.TTSConst;

public class AmazonInAppPurchase extends InAppPurchase {
    private boolean mLocked;
    private String mMarketplace;

    /* renamed from: com.sygic.aura.feature.store.AmazonInAppPurchase.1 */
    class C12341 implements Runnable {
        C12341() {
        }

        public void run() {
            AmazonInAppPurchase.this.checkQueuedTrans();
        }
    }

    /* renamed from: com.sygic.aura.feature.store.AmazonInAppPurchase.2 */
    static /* synthetic */ class C12352 {
        static final /* synthetic */ int[] f1255x4eb80c9f;
        static final /* synthetic */ int[] f1256xc71ab397;
        static final /* synthetic */ int[] f1257xe10c5bef;
        static final /* synthetic */ int[] f1258x29a859ab;

        static {
            f1258x29a859ab = new int[RequestStatus.values().length];
            try {
                f1258x29a859ab[RequestStatus.SUCCESSFUL.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                f1258x29a859ab[RequestStatus.FAILED.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                f1258x29a859ab[RequestStatus.NOT_SUPPORTED.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
            f1257xe10c5bef = new int[PurchaseUpdatesResponse.RequestStatus.values().length];
            try {
                f1257xe10c5bef[PurchaseUpdatesResponse.RequestStatus.SUCCESSFUL.ordinal()] = 1;
            } catch (NoSuchFieldError e4) {
            }
            try {
                f1257xe10c5bef[PurchaseUpdatesResponse.RequestStatus.FAILED.ordinal()] = 2;
            } catch (NoSuchFieldError e5) {
            }
            try {
                f1257xe10c5bef[PurchaseUpdatesResponse.RequestStatus.NOT_SUPPORTED.ordinal()] = 3;
            } catch (NoSuchFieldError e6) {
            }
            f1256xc71ab397 = new int[PurchaseResponse.RequestStatus.values().length];
            try {
                f1256xc71ab397[PurchaseResponse.RequestStatus.SUCCESSFUL.ordinal()] = 1;
            } catch (NoSuchFieldError e7) {
            }
            try {
                f1256xc71ab397[PurchaseResponse.RequestStatus.ALREADY_PURCHASED.ordinal()] = 2;
            } catch (NoSuchFieldError e8) {
            }
            try {
                f1256xc71ab397[PurchaseResponse.RequestStatus.INVALID_SKU.ordinal()] = 3;
            } catch (NoSuchFieldError e9) {
            }
            try {
                f1256xc71ab397[PurchaseResponse.RequestStatus.FAILED.ordinal()] = 4;
            } catch (NoSuchFieldError e10) {
            }
            try {
                f1256xc71ab397[PurchaseResponse.RequestStatus.NOT_SUPPORTED.ordinal()] = 5;
            } catch (NoSuchFieldError e11) {
            }
            f1255x4eb80c9f = new int[ProductDataResponse.RequestStatus.values().length];
            try {
                f1255x4eb80c9f[ProductDataResponse.RequestStatus.SUCCESSFUL.ordinal()] = 1;
            } catch (NoSuchFieldError e12) {
            }
            try {
                f1255x4eb80c9f[ProductDataResponse.RequestStatus.FAILED.ordinal()] = 2;
            } catch (NoSuchFieldError e13) {
            }
            try {
                f1255x4eb80c9f[ProductDataResponse.RequestStatus.NOT_SUPPORTED.ordinal()] = 3;
            } catch (NoSuchFieldError e14) {
            }
        }
    }

    class AmazonPurchasingListener implements PurchasingListener {
        AmazonPurchasingListener() {
        }

        public void onProductDataResponse(ProductDataResponse response) {
            ProductDataResponse.RequestStatus eStatus = response.getRequestStatus();
            AmazonInAppPurchase.logDebug("onProductDataResponse: " + eStatus + " - " + response.toString());
            switch (C12352.f1255x4eb80c9f[eStatus.ordinal()]) {
            }
        }

        public void onPurchaseResponse(PurchaseResponse response) {
            if (response == null) {
                AmazonInAppPurchase.logDebug("onPurchaseResponse == null");
                return;
            }
            PurchaseResponse.RequestStatus eStatus = response.getRequestStatus();
            AmazonInAppPurchase.logDebug("onPurchaseResponse: " + eStatus + " - " + response.toString());
            int nLength = 0;
            int nType = EResponseType.RtPayment.getValue();
            int nResult = EPaymentResult.PaymentNone.getValue();
            String strData = null;
            switch (C12352.f1256xc71ab397[eStatus.ordinal()]) {
                case TTSConst.TTSMULTILINE /*1*/:
                    Receipt receipt = response.getReceipt();
                    if (receipt != null) {
                        strData = receipt.getSku() + "|" + response.getUserData().getUserId() + ":" + receipt.getReceiptId();
                        nLength = strData.length();
                        nType = EResponseType.RtPayment.getValue();
                        nResult = EPaymentResult.PaymentCompleted.getValue();
                        break;
                    }
                    break;
                case TTSConst.TTSPARAGRAPH /*2*/:
                case TTSConst.TTSUNICODE /*3*/:
                case TTSConst.TTSXML /*4*/:
                case TTSConst.TTSEVT_TEXT /*5*/:
                    nType = EResponseType.RtPayment.getValue();
                    nResult = EPaymentResult.PaymentNone.getValue();
                    break;
                default:
                    nType = EResponseType.RtPayment.getValue();
                    nResult = EPaymentResult.PaymentNone.getValue();
                    break;
            }
            AmazonInAppPurchase.logDebug("onPurchaseResponse - process: " + nType + ", " + nResult + " - " + strData);
            if (SygicMain.getInstance().ProcessStoreResponse(strData, nLength, nType, nResult) == 1 && strData != null) {
                AmazonInAppPurchase.this.saveQueuedTransaction(AmazonInAppPurchase.this.mContext, strData, nType, nResult);
            }
        }

        public void onPurchaseUpdatesResponse(PurchaseUpdatesResponse response) {
            if (response == null) {
                AmazonInAppPurchase.logDebug("onPurchaseUpdatesResponse == null");
                return;
            }
            PurchaseUpdatesResponse.RequestStatus eStatus = response.getRequestStatus();
            AmazonInAppPurchase.logDebug("onPurchaseUpdatesResponse: " + eStatus + " - " + response.toString());
            List<Receipt> receipts = response.getReceipts();
            if (receipts != null) {
                switch (C12352.f1257xe10c5bef[eStatus.ordinal()]) {
                    case TTSConst.TTSMULTILINE /*1*/:
                        String strData = "";
                        for (Receipt receipt : receipts) {
                            if (!TextUtils.isEmpty(strData)) {
                                strData = strData + "|";
                            }
                            strData = strData + response.getUserData().getUserId() + ":" + receipt.getReceiptId();
                        }
                        int nLength = strData.length();
                        int nType = EResponseType.RtRestore.getValue();
                        int nResult = EPaymentResult.PaymentNone.getValue();
                        AmazonInAppPurchase.logDebug("onPurchaseUpdatesResponse - process: " + nType + ", " + nResult + " - " + strData);
                        if (SygicMain.getInstance().ProcessStoreResponse(strData, nLength, nType, nResult) == 1 && strData != null) {
                            AmazonInAppPurchase.this.saveQueuedTransaction(AmazonInAppPurchase.this.mContext, strData, nType, nResult);
                        }
                        if (response.hasMore()) {
                            AmazonInAppPurchase.logDebug("onPurchaseUpdatesResponse - get more");
                            PurchasingService.getPurchaseUpdates(false);
                        }
                    case TTSConst.TTSPARAGRAPH /*2*/:
                    case TTSConst.TTSUNICODE /*3*/:
                        SygicMain.getInstance().ProcessStoreResponse(null, 0, EResponseType.RtRestore.getValue(), EPaymentResult.PaymentNone.getValue());
                    default:
                }
            }
        }

        public void onUserDataResponse(UserDataResponse response) {
            RequestStatus eStatus = response.getRequestStatus();
            AmazonInAppPurchase.logDebug("onUserDataResponse: " + eStatus + " - " + response.toString());
            switch (C12352.f1258x29a859ab[eStatus.ordinal()]) {
                case TTSConst.TTSMULTILINE /*1*/:
                    AmazonInAppPurchase.this.mMarketplace = response.getUserData().getMarketplace();
                    synchronized (AmazonInAppPurchase.this) {
                        AmazonInAppPurchase.this.mLocked = false;
                        AmazonInAppPurchase.this.notifyAll();
                        break;
                    }
                default:
            }
        }
    }

    protected AmazonInAppPurchase(Context context, Handler handler) {
        super(context, handler);
        this.mLocked = false;
    }

    protected boolean startService() {
        logDebug("Starting InApp service. Sandbox mode is: " + PurchasingService.IS_SANDBOX_MODE);
        PurchasingService.registerListener(this.mContext, new AmazonPurchasingListener());
        this.mLocked = true;
        PurchasingService.getUserData();
        return true;
    }

    public void stopService() {
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public boolean isBillingSupported() {
        /*
        r4 = this;
        r1 = 0;
        r2 = "isBillingSupported()";
        logDebug(r2);
        monitor-enter(r4);
    L_0x0008:
        r2 = r4.mLocked;	 Catch:{ all -> 0x0013 }
        if (r2 == 0) goto L_0x0023;
    L_0x000c:
        r4.wait();	 Catch:{ InterruptedException -> 0x0016 }
        r2 = 0;
        r4.mLocked = r2;	 Catch:{ all -> 0x0013 }
        goto L_0x0008;
    L_0x0013:
        r1 = move-exception;
        monitor-exit(r4);	 Catch:{ all -> 0x0013 }
        throw r1;
    L_0x0016:
        r0 = move-exception;
        r0.printStackTrace();	 Catch:{ all -> 0x001e }
        r2 = 0;
        r4.mLocked = r2;	 Catch:{ all -> 0x0013 }
        goto L_0x0008;
    L_0x001e:
        r1 = move-exception;
        r2 = 0;
        r4.mLocked = r2;	 Catch:{ all -> 0x0013 }
        throw r1;	 Catch:{ all -> 0x0013 }
    L_0x0023:
        monitor-exit(r4);	 Catch:{ all -> 0x0013 }
        r2 = new java.lang.StringBuilder;
        r2.<init>();
        r3 = "isBillingSupported() markeptlace: ";
        r2 = r2.append(r3);
        r3 = r4.mMarketplace;
        r2 = r2.append(r3);
        r2 = r2.toString();
        logDebug(r2);
        r2 = r4.mMarketplace;
        r2 = android.text.TextUtils.isEmpty(r2);
        if (r2 != 0) goto L_0x0046;
    L_0x0045:
        r1 = 1;
    L_0x0046:
        return r1;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.sygic.aura.feature.store.AmazonInAppPurchase.isBillingSupported():boolean");
    }

    public boolean buyProduct(String strProductID) {
        logDebug("buyProduct: " + strProductID);
        logDebug("buyProduct; requestId: " + PurchasingService.purchase(strProductID).toString());
        return true;
    }

    public boolean restorePurchase() {
        logDebug("restorePurchase() request id: " + PurchasingService.getPurchaseUpdates(true));
        return true;
    }

    public void checkQueuedTransactions() {
        logDebug("checkQueuedTransactions()");
        this.mHandler.post(new C12341());
    }

    public boolean handleActivityResult(int requestCode, int resultCode, Intent dataIntent) {
        return false;
    }

    public void onPause() {
    }

    public void onResume() {
    }

    private static void logDebug(String msg) {
    }
}
