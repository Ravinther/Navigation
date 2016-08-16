package com.infinario.android.infinariosdk;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.ResolveInfo;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;
import java.util.List;

public class IabHelper {
    Context mContext;
    boolean mDisposed;
    IabProxy mService;
    ServiceConnection mServiceConn;
    boolean mSetupDone;
    boolean mSubscriptionsSupported;

    /* renamed from: com.infinario.android.infinariosdk.IabHelper.1 */
    class C10471 implements ServiceConnection {
        final /* synthetic */ OnIabSetupFinishedListener val$listener;

        C10471(OnIabSetupFinishedListener onIabSetupFinishedListener) {
            this.val$listener = onIabSetupFinishedListener;
        }

        public void onServiceDisconnected(ComponentName name) {
            Log.d("Infinario", "Billing service disconnected.");
            IabHelper.this.mService = null;
        }

        public void onServiceConnected(ComponentName name, IBinder service) {
            if (!IabHelper.this.mDisposed) {
                Log.d("Infinario", "Billing service connected.");
                IabHelper.this.mService = new IabProxy(service);
                if (IabHelper.this.mService.isLoaded()) {
                    String packageName = IabHelper.this.mContext.getPackageName();
                    try {
                        Log.d("Infinario", "Checking for in-app billing 3 support.");
                        int response = IabHelper.this.mService.isBillingSupported(3, packageName, "inapp");
                        if (response != 0) {
                            if (this.val$listener != null) {
                                this.val$listener.onIabSetupFinished(new IabResult(response, "Error checking for billing v3 support."));
                            }
                            IabHelper.this.mSubscriptionsSupported = false;
                            return;
                        }
                        Log.d("Infinario", "In-app billing version 3 supported for " + packageName);
                        response = IabHelper.this.mService.isBillingSupported(3, packageName, "subs");
                        if (response == 0) {
                            Log.d("Infinario", "Subscriptions AVAILABLE.");
                            IabHelper.this.mSubscriptionsSupported = true;
                        } else {
                            Log.d("Infinario", "Subscriptions NOT AVAILABLE. Response: " + response);
                        }
                        IabHelper.this.mSetupDone = true;
                        if (this.val$listener != null) {
                            this.val$listener.onIabSetupFinished(new IabResult(0, "Setup successful."));
                            return;
                        }
                        return;
                    } catch (RemoteException e) {
                        if (this.val$listener != null) {
                            this.val$listener.onIabSetupFinished(new IabResult(-1001, "RemoteException while setting up in-app billing."));
                        }
                        e.printStackTrace();
                        return;
                    }
                }
                Log.i("Infinario", "InAppBillingService.aidl not included, automatic payment tracking is unavailable");
            }
        }
    }

    public interface OnIabSetupFinishedListener {
        void onIabSetupFinished(IabResult iabResult);
    }

    public IabHelper(Context ctx) {
        this.mSetupDone = false;
        this.mDisposed = false;
        this.mSubscriptionsSupported = false;
        this.mContext = ctx.getApplicationContext();
    }

    public void startSetup(OnIabSetupFinishedListener listener) {
        if (!this.mSetupDone) {
            Log.d("Infinario", "Starting in-app billing setup.");
            this.mServiceConn = new C10471(listener);
            Intent serviceIntent = new Intent("com.android.vending.billing.InAppBillingService.BIND");
            serviceIntent.setPackage("com.android.vending");
            List<ResolveInfo> services = this.mContext.getPackageManager().queryIntentServices(serviceIntent, 0);
            if (services != null && !services.isEmpty()) {
                this.mContext.bindService(serviceIntent, this.mServiceConn, 1);
            } else if (listener != null) {
                listener.onIabSetupFinished(new IabResult(3, "Billing service unavailable on device."));
            }
        }
    }
}
