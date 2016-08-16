package com.sygic.aura.feature.store.v3;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender.SendIntentException;
import android.content.ServiceConnection;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.RemoteException;
import android.text.TextUtils;
import android.util.Log;
import com.android.vending.billing.IInAppBillingService;
import com.android.vending.billing.IInAppBillingService.Stub;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.json.JSONException;

public class IabHelper {
    boolean mAsyncInProgress;
    String mAsyncOperation;
    Context mContext;
    boolean mDebugLog;
    String mDebugTag;
    OnIabPurchaseFinishedListener mPurchaseListener;
    String mPurchasingItemType;
    int mRequestCode;
    IInAppBillingService mService;
    ServiceConnection mServiceConn;
    boolean mSetupDone;
    String mSignatureBase64;
    boolean mSubscriptionsSupported;

    public interface OnIabSetupFinishedListener {
        void onIabSetupFinished(IabResult iabResult);
    }

    public interface QueryInventoryFinishedListener {
        void onQueryInventoryFinished(IabResult iabResult, Inventory inventory);
    }

    public interface OnIabPurchaseFinishedListener {
        void onIabPurchaseFinished(IabResult iabResult, Purchase purchase);
    }

    /* renamed from: com.sygic.aura.feature.store.v3.IabHelper.1 */
    class C12401 implements ServiceConnection {
        final /* synthetic */ OnIabSetupFinishedListener val$listener;

        C12401(OnIabSetupFinishedListener onIabSetupFinishedListener) {
            this.val$listener = onIabSetupFinishedListener;
        }

        public void onServiceDisconnected(ComponentName name) {
            IabHelper.this.logDebug("Billing service disconnected.");
            IabHelper.this.mService = null;
        }

        public void onServiceConnected(ComponentName name, IBinder service) {
            IabHelper.this.logDebug("Billing service connected.");
            IabHelper.this.mService = Stub.asInterface(service);
            String packageName = IabHelper.this.mContext.getPackageName();
            try {
                IabHelper.this.logDebug("Checking for in-app billing 3 support.");
                int response = IabHelper.this.mService.isBillingSupported(3, packageName, "inapp");
                if (response != 0) {
                    if (this.val$listener != null) {
                        this.val$listener.onIabSetupFinished(new IabResult(response, "Error checking for billing v3 support."));
                    }
                    IabHelper.this.mSubscriptionsSupported = false;
                    return;
                }
                IabHelper.this.logDebug("In-app billing version 3 supported for " + packageName);
                response = IabHelper.this.mService.isBillingSupported(3, packageName, "subs");
                if (response == 0) {
                    IabHelper.this.logDebug("Subscriptions AVAILABLE.");
                    IabHelper.this.mSubscriptionsSupported = true;
                } else {
                    IabHelper.this.logDebug("Subscriptions NOT AVAILABLE. Response: " + response);
                }
                IabHelper.this.mSetupDone = true;
                if (this.val$listener != null) {
                    this.val$listener.onIabSetupFinished(new IabResult(0, "Setup successful."));
                }
            } catch (RemoteException e) {
                if (this.val$listener != null) {
                    this.val$listener.onIabSetupFinished(new IabResult(-1001, "RemoteException while setting up in-app billing."));
                }
                e.printStackTrace();
            }
        }
    }

    /* renamed from: com.sygic.aura.feature.store.v3.IabHelper.2 */
    class C12422 implements Runnable {
        final /* synthetic */ Handler val$handler;
        final /* synthetic */ QueryInventoryFinishedListener val$listener;
        final /* synthetic */ List val$moreSkus;
        final /* synthetic */ boolean val$querySkuDetails;

        /* renamed from: com.sygic.aura.feature.store.v3.IabHelper.2.1 */
        class C12411 implements Runnable {
            final /* synthetic */ Inventory val$inv_f;
            final /* synthetic */ IabResult val$result_f;

            C12411(IabResult iabResult, Inventory inventory) {
                this.val$result_f = iabResult;
                this.val$inv_f = inventory;
            }

            public void run() {
                C12422.this.val$listener.onQueryInventoryFinished(this.val$result_f, this.val$inv_f);
            }
        }

        C12422(boolean z, List list, Handler handler, QueryInventoryFinishedListener queryInventoryFinishedListener) {
            this.val$querySkuDetails = z;
            this.val$moreSkus = list;
            this.val$handler = handler;
            this.val$listener = queryInventoryFinishedListener;
        }

        public void run() {
            IabResult result = new IabResult(0, "Inventory refresh successful.");
            Inventory inv = null;
            try {
                inv = IabHelper.this.queryInventory(this.val$querySkuDetails, this.val$moreSkus);
            } catch (IabException ex) {
                result = ex.getResult();
            }
            IabHelper.this.flagEndAsync();
            this.val$handler.post(new C12411(result, inv));
        }
    }

    public IabHelper(Context ctx, String base64PublicKey) {
        this.mDebugLog = false;
        this.mDebugTag = "IabHelper";
        this.mSetupDone = false;
        this.mSubscriptionsSupported = false;
        this.mAsyncInProgress = false;
        this.mAsyncOperation = "";
        this.mSignatureBase64 = null;
        this.mContext = ctx.getApplicationContext();
        this.mSignatureBase64 = base64PublicKey;
        logDebug("IAB helper created.");
    }

    public void enableDebugLogging(boolean enable) {
        this.mDebugLog = enable;
    }

    public void startSetup(OnIabSetupFinishedListener listener) {
        if (this.mSetupDone) {
            throw new IllegalStateException("IAB helper is already set up.");
        }
        logDebug("Starting in-app billing setup.");
        this.mServiceConn = new C12401(listener);
        Intent serviceIntent = new Intent("com.android.vending.billing.InAppBillingService.BIND");
        serviceIntent.setPackage("com.android.vending");
        List<ResolveInfo> resolveInfos = this.mContext.getPackageManager().queryIntentServices(serviceIntent, 0);
        if (resolveInfos != null && !resolveInfos.isEmpty()) {
            this.mContext.bindService(serviceIntent, this.mServiceConn, 1);
        } else if (listener != null) {
            listener.onIabSetupFinished(new IabResult(3, "Billing service unavailable on device."));
        }
    }

    public void dispose() {
        logDebug("Disposing.");
        this.mSetupDone = false;
        if (this.mServiceConn != null) {
            logDebug("Unbinding from service.");
            try {
                if (this.mContext != null) {
                    this.mContext.unbindService(this.mServiceConn);
                }
            } catch (IllegalArgumentException e) {
                logDebug("Unbinding from service failed.");
            }
            this.mServiceConn = null;
            this.mService = null;
            this.mPurchaseListener = null;
        }
    }

    public void launchPurchaseFlow(Activity act, String sku, String itemType, int requestCode, OnIabPurchaseFinishedListener listener, String extraData) {
        try {
            checkSetupDoneIab("launchPurchaseFlow");
            flagStartAsync("launchPurchaseFlow");
            if (!itemType.equals("subs") || this.mSubscriptionsSupported) {
                IabResult result;
                try {
                    logDebug("Constructing buy intent for " + sku + ", item type: " + itemType);
                    Bundle buyIntentBundle = this.mService.getBuyIntent(3, this.mContext.getPackageName(), sku, itemType, extraData);
                    int response = getResponseCodeFromBundle(buyIntentBundle);
                    if (response != 0) {
                        logError("Unable to buy item, Error response: " + getResponseDesc(response));
                        flagEndAsync();
                        result = new IabResult(response, "Unable to buy item");
                        if (listener != null) {
                            listener.onIabPurchaseFinished(result, null);
                            return;
                        }
                        return;
                    }
                    PendingIntent pendingIntent = (PendingIntent) buyIntentBundle.getParcelable("BUY_INTENT");
                    logDebug("Launching buy intent for " + sku + ". Request code: " + requestCode);
                    this.mRequestCode = requestCode;
                    this.mPurchaseListener = listener;
                    this.mPurchasingItemType = itemType;
                    act.startIntentSenderForResult(pendingIntent.getIntentSender(), requestCode, new Intent(), Integer.valueOf(0).intValue(), Integer.valueOf(0).intValue(), Integer.valueOf(0).intValue());
                    return;
                } catch (SendIntentException e) {
                    logError("SendIntentException while launching purchase flow for sku " + sku);
                    e.printStackTrace();
                    result = new IabResult(-1004, "Failed to send intent.");
                    if (listener != null) {
                        listener.onIabPurchaseFinished(result, null);
                        return;
                    }
                    return;
                } catch (RemoteException e2) {
                    logError("RemoteException while launching purchase flow for sku " + sku);
                    e2.printStackTrace();
                    result = new IabResult(-1001, "Remote exception while starting purchase flow");
                    if (listener != null) {
                        listener.onIabPurchaseFinished(result, null);
                        return;
                    }
                    return;
                }
            }
            IabResult r = new IabResult(-1009, "Subscriptions are not available.");
            if (listener != null) {
                listener.onIabPurchaseFinished(r, null);
            }
        } catch (IabException iabex) {
            if (listener != null) {
                listener.onIabPurchaseFinished(iabex.getResult(), null);
            }
        }
    }

    public boolean handleActivityResult(int requestCode, int resultCode, Intent data) {
        JSONException e;
        if (requestCode != this.mRequestCode) {
            return false;
        }
        try {
            checkSetupDoneIab("handleActivityResult");
            flagEndAsync();
            IabResult result;
            if (data == null) {
                logError("Null data in IAB activity result.");
                result = new IabResult(-1002, "Null data in IAB result");
                if (this.mPurchaseListener != null) {
                    this.mPurchaseListener.onIabPurchaseFinished(result, null);
                }
                return true;
            }
            int responseCode = getResponseCodeFromIntent(data);
            String purchaseData = data.getStringExtra("INAPP_PURCHASE_DATA");
            String dataSignature = data.getStringExtra("INAPP_DATA_SIGNATURE");
            if (resultCode == -1 && responseCode == 0) {
                logDebug("Successful resultcode from purchase activity.");
                logDebug("Purchase data: " + purchaseData);
                logDebug("Data signature: " + dataSignature);
                logDebug("Extras: " + data.getExtras());
                logDebug("Expected item type: " + this.mPurchasingItemType);
                if (purchaseData == null || dataSignature == null) {
                    logError("BUG: either purchaseData or dataSignature is null.");
                    logDebug("Extras: " + data.getExtras().toString());
                    result = new IabResult(-1008, "IAB returned null purchaseData or dataSignature");
                    if (this.mPurchaseListener != null) {
                        this.mPurchaseListener.onIabPurchaseFinished(result, null);
                    }
                    return true;
                }
                try {
                    Purchase purchase = new Purchase(this.mPurchasingItemType, purchaseData, dataSignature);
                    try {
                        String sku = purchase.getSku();
                        if (Security.verifyPurchase(this.mSignatureBase64, purchaseData, dataSignature)) {
                            logDebug("Purchase signature successfully verified.");
                            if (this.mPurchaseListener != null) {
                                this.mPurchaseListener.onIabPurchaseFinished(new IabResult(0, "Success"), purchase);
                            }
                        } else {
                            logError("Purchase signature verification FAILED for sku " + sku);
                            result = new IabResult(-1003, "Signature verification failed for sku " + sku);
                            if (this.mPurchaseListener != null) {
                                this.mPurchaseListener.onIabPurchaseFinished(result, purchase);
                            }
                            return true;
                        }
                    } catch (JSONException e2) {
                        e = e2;
                        Purchase purchase2 = purchase;
                        logError("Failed to parse purchase data.");
                        e.printStackTrace();
                        result = new IabResult(-1002, "Failed to parse purchase data.");
                        if (this.mPurchaseListener != null) {
                            this.mPurchaseListener.onIabPurchaseFinished(result, null);
                        }
                        return true;
                    }
                } catch (JSONException e3) {
                    e = e3;
                    logError("Failed to parse purchase data.");
                    e.printStackTrace();
                    result = new IabResult(-1002, "Failed to parse purchase data.");
                    if (this.mPurchaseListener != null) {
                        this.mPurchaseListener.onIabPurchaseFinished(result, null);
                    }
                    return true;
                }
            } else if (resultCode == -1) {
                logDebug("Result code was OK but in-app billing response was not OK: " + getResponseDesc(responseCode));
                if (this.mPurchaseListener != null) {
                    this.mPurchaseListener.onIabPurchaseFinished(new IabResult(responseCode, "Problem purchashing item."), null);
                }
            } else if (resultCode == 0) {
                logDebug("Purchase canceled - Response: " + getResponseDesc(responseCode));
                result = new IabResult(-1005, "User canceled.");
                if (this.mPurchaseListener != null) {
                    this.mPurchaseListener.onIabPurchaseFinished(result, null);
                }
            } else {
                logError("Purchase failed. Result code: " + Integer.toString(resultCode) + ". Response: " + getResponseDesc(responseCode));
                result = new IabResult(-1006, "Unknown purchase response.");
                if (this.mPurchaseListener != null) {
                    this.mPurchaseListener.onIabPurchaseFinished(result, null);
                }
            }
            return true;
        } catch (IabException aibex) {
            flagEndAsync();
            if (this.mPurchaseListener != null) {
                this.mPurchaseListener.onIabPurchaseFinished(aibex.getResult(), null);
            }
            return true;
        }
    }

    public Inventory queryInventory(boolean querySkuDetails, List<String> moreSkus) throws IabException {
        return queryInventory(querySkuDetails, moreSkus, null);
    }

    public Inventory queryInventory(boolean querySkuDetails, List<String> moreItemSkus, List<String> list) throws IabException {
        checkSetupDoneIab("queryInventory");
        try {
            Inventory inv = new Inventory();
            int r = queryPurchases(inv, "inapp");
            if (r != 0) {
                throw new IabException(r, "Error refreshing inventory (querying owned items).");
            }
            if (querySkuDetails) {
                r = querySkuDetails("inapp", inv, moreItemSkus);
                if (r != 0) {
                    throw new IabException(r, "Error refreshing inventory (querying prices of items).");
                }
            }
            if (this.mSubscriptionsSupported) {
                r = queryPurchases(inv, "subs");
                if (r != 0) {
                    throw new IabException(r, "Error refreshing inventory (querying owned subscriptions).");
                } else if (querySkuDetails) {
                    r = querySkuDetails("subs", inv, moreItemSkus);
                    if (r != 0) {
                        throw new IabException(r, "Error refreshing inventory (querying prices of subscriptions).");
                    }
                }
            }
            return inv;
        } catch (RemoteException e) {
            throw new IabException(-1001, "Remote exception while refreshing inventory.", e);
        } catch (JSONException e2) {
            throw new IabException(-1002, "Error parsing JSON response while refreshing inventory.", e2);
        }
    }

    public void queryInventoryAsync(boolean querySkuDetails, List<String> moreSkus, QueryInventoryFinishedListener listener) {
        Handler handler = new Handler();
        try {
            checkSetupDoneIab("queryInventory");
        } catch (IabException e) {
        }
        flagStartAsync("refresh inventory");
        new Thread(new C12422(querySkuDetails, moreSkus, handler, listener)).start();
    }

    public void queryInventoryAsync(QueryInventoryFinishedListener listener) {
        queryInventoryAsync(true, null, listener);
    }

    public void queryInventoryAsync(boolean querySkuDetails, QueryInventoryFinishedListener listener) {
        queryInventoryAsync(querySkuDetails, null, listener);
    }

    public static String getResponseDesc(int code) {
        String[] iab_msgs = "0:OK/1:User Canceled/2:Unknown/3:Billing Unavailable/4:Item unavailable/5:Developer Error/6:Error/7:Item Already Owned/8:Item not owned".split("/");
        String[] iabhelper_msgs = "0:OK/-1001:Remote exception during initialization/-1002:Bad response received/-1003:Purchase signature verification failed/-1004:Send intent failed/-1005:User cancelled/-1006:Unknown purchase response/-1007:Missing token/-1008:Unknown error/-1009:Subscriptions not available/-1010:Invalid consumption attempt".split("/");
        if (code <= -1000) {
            int index = -1000 - code;
            if (index < 0 || index >= iabhelper_msgs.length) {
                return String.valueOf(code) + ":Unknown IAB Helper Error";
            }
            return iabhelper_msgs[index];
        } else if (code < 0 || code >= iab_msgs.length) {
            return String.valueOf(code) + ":Unknown";
        } else {
            return iab_msgs[code];
        }
    }

    void checkSetupDoneIab(String operation) throws IabException {
        if (!this.mSetupDone) {
            logError("IabException for operation (" + operation + "): IAB helper is not set up.");
            throw new IabException(6, "IAB helper is not set up. Can't perform operation: " + operation);
        }
    }

    int getResponseCodeFromBundle(Bundle b) {
        Object o = b.get("RESPONSE_CODE");
        if (o == null) {
            logDebug("Bundle with null response code, assuming OK (known issue)");
            return 0;
        } else if (o instanceof Integer) {
            return ((Integer) o).intValue();
        } else {
            if (o instanceof Long) {
                return (int) ((Long) o).longValue();
            }
            logError("Unexpected type for bundle response code.");
            logError(o.getClass().getName());
            throw new RuntimeException("Unexpected type for bundle response code: " + o.getClass().getName());
        }
    }

    int getResponseCodeFromIntent(Intent i) {
        Object o = i.getExtras().get("RESPONSE_CODE");
        if (o == null) {
            logError("Intent with no response code, assuming OK (known issue)");
            return 0;
        } else if (o instanceof Integer) {
            return ((Integer) o).intValue();
        } else {
            if (o instanceof Long) {
                return (int) ((Long) o).longValue();
            }
            logError("Unexpected type for intent response code.");
            logError(o.getClass().getName());
            throw new RuntimeException("Unexpected type for intent response code: " + o.getClass().getName());
        }
    }

    void flagStartAsync(String operation) {
        if (this.mAsyncInProgress) {
            throw new IllegalStateException("Can't start async operation (" + operation + ") because another async operation(" + this.mAsyncOperation + ") is in progress.");
        }
        this.mAsyncOperation = operation;
        this.mAsyncInProgress = true;
        logDebug("Starting async operation: " + operation);
    }

    void flagEndAsync() {
        logDebug("Ending async operation: " + this.mAsyncOperation);
        this.mAsyncOperation = "";
        this.mAsyncInProgress = false;
    }

    int queryPurchases(Inventory inv, String itemType) throws JSONException, RemoteException {
        logDebug("Querying owned items, item type: " + itemType);
        logDebug("Package name: " + this.mContext.getPackageName());
        boolean verificationFailed = false;
        String continueToken = null;
        if (this.mService == null) {
            logDebug("Error, mService == null");
            return -1008;
        }
        do {
            logDebug("Calling getPurchases with continuation token: " + continueToken);
            Bundle ownedItems = this.mService.getPurchases(3, this.mContext.getPackageName(), itemType, continueToken);
            int response = getResponseCodeFromBundle(ownedItems);
            logDebug("Owned items response: " + String.valueOf(response));
            if (response != 0) {
                logDebug("getPurchases() failed: " + getResponseDesc(response));
                return response;
            } else if (ownedItems.containsKey("INAPP_PURCHASE_ITEM_LIST") && ownedItems.containsKey("INAPP_PURCHASE_DATA_LIST") && ownedItems.containsKey("INAPP_DATA_SIGNATURE_LIST")) {
                ArrayList<String> ownedSkus = ownedItems.getStringArrayList("INAPP_PURCHASE_ITEM_LIST");
                ArrayList<String> purchaseDataList = ownedItems.getStringArrayList("INAPP_PURCHASE_DATA_LIST");
                ArrayList<String> signatureList = ownedItems.getStringArrayList("INAPP_DATA_SIGNATURE_LIST");
                for (int i = 0; i < purchaseDataList.size(); i++) {
                    String purchaseData = (String) purchaseDataList.get(i);
                    String signature = (String) signatureList.get(i);
                    String sku = (String) ownedSkus.get(i);
                    if (Security.verifyPurchase(this.mSignatureBase64, purchaseData, signature)) {
                        logDebug("Sku is owned: " + sku);
                        Purchase purchase = new Purchase(itemType, purchaseData, signature);
                        if (TextUtils.isEmpty(purchase.getToken())) {
                            logWarn("BUG: empty/null token!");
                            logDebug("Purchase data: " + purchaseData);
                        }
                        inv.addPurchase(purchase);
                    } else {
                        logWarn("Purchase signature verification **FAILED**. Not adding item.");
                        logDebug("   Purchase data: " + purchaseData);
                        logDebug("   Signature: " + signature);
                        verificationFailed = true;
                    }
                }
                continueToken = ownedItems.getString("INAPP_CONTINUATION_TOKEN");
                logDebug("Continuation token: " + continueToken);
            } else {
                logError("Bundle returned from getPurchases() doesn't contain required fields.");
                return -1002;
            }
        } while (!TextUtils.isEmpty(continueToken));
        return verificationFailed ? -1003 : 0;
    }

    int querySkuDetails(String itemType, Inventory inv, List<String> moreSkus) throws RemoteException, JSONException {
        logDebug("Querying SKU details.");
        ArrayList<String> skuList = new ArrayList();
        skuList.addAll(inv.getAllOwnedSkus(itemType));
        if (moreSkus != null) {
            skuList.addAll(moreSkus);
        }
        if (skuList.size() == 0) {
            logDebug("queryPrices: nothing to do because there are no SKUs.");
            return 0;
        }
        Bundle querySkus = new Bundle();
        querySkus.putStringArrayList("ITEM_ID_LIST", skuList);
        Bundle skuDetails = this.mService.getSkuDetails(3, this.mContext.getPackageName(), itemType, querySkus);
        if (skuDetails.containsKey("DETAILS_LIST")) {
            Iterator it = skuDetails.getStringArrayList("DETAILS_LIST").iterator();
            while (it.hasNext()) {
                SkuDetails d = new SkuDetails(itemType, (String) it.next());
                logDebug("Got sku details: " + d);
                inv.addSkuDetails(d);
            }
            return 0;
        }
        int response = getResponseCodeFromBundle(skuDetails);
        if (response != 0) {
            logDebug("getSkuDetails() failed: " + getResponseDesc(response));
            return response;
        }
        logError("getSkuDetails() returned a bundle with neither an error nor a detail list.");
        return -1002;
    }

    void logDebug(String msg) {
        if (this.mDebugLog) {
            Log.d(this.mDebugTag, msg);
        }
    }

    void logError(String msg) {
        Log.e(this.mDebugTag, "In-app billing error: " + msg);
    }

    void logWarn(String msg) {
        Log.w(this.mDebugTag, "In-app billing warning: " + msg);
    }
}
