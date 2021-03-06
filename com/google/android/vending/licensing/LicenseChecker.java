package com.google.android.vending.licensing;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;
import com.google.android.vending.licensing.ILicenseResultListener.Stub;
import com.google.android.vending.licensing.util.Base64;
import com.google.android.vending.licensing.util.Base64DecoderException;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;

public class LicenseChecker implements ServiceConnection {
    private static final SecureRandom RANDOM;
    private final Set<LicenseValidator> mChecksInProgress;
    private final Context mContext;
    private Handler mHandler;
    private final String mPackageName;
    private final Queue<LicenseValidator> mPendingChecks;
    private final Policy mPolicy;
    private PublicKey mPublicKey;
    private ILicensingService mService;
    private final String mVersionCode;

    private class ResultListener extends Stub {
        private Runnable mOnTimeout;
        private final LicenseValidator mValidator;

        /* renamed from: com.google.android.vending.licensing.LicenseChecker.ResultListener.1 */
        class C10381 implements Runnable {
            final /* synthetic */ LicenseChecker val$this$0;

            C10381(LicenseChecker licenseChecker) {
                this.val$this$0 = licenseChecker;
            }

            public void run() {
                Log.i("LicenseChecker", "Check timed out.");
                LicenseChecker.this.handleServiceConnectionError(ResultListener.this.mValidator);
                LicenseChecker.this.finishCheck(ResultListener.this.mValidator);
            }
        }

        /* renamed from: com.google.android.vending.licensing.LicenseChecker.ResultListener.2 */
        class C10392 implements Runnable {
            final /* synthetic */ int val$responseCode;
            final /* synthetic */ String val$signature;
            final /* synthetic */ String val$signedData;

            C10392(int i, String str, String str2) {
                this.val$responseCode = i;
                this.val$signedData = str;
                this.val$signature = str2;
            }

            public void run() {
                Log.i("LicenseChecker", "Received response.");
                if (LicenseChecker.this.mChecksInProgress.contains(ResultListener.this.mValidator)) {
                    ResultListener.this.clearTimeout();
                    ResultListener.this.mValidator.verify(LicenseChecker.this.mPublicKey, this.val$responseCode, this.val$signedData, this.val$signature);
                    LicenseChecker.this.finishCheck(ResultListener.this.mValidator);
                }
            }
        }

        public ResultListener(LicenseValidator validator) {
            this.mValidator = validator;
            this.mOnTimeout = new C10381(LicenseChecker.this);
            startTimeout();
        }

        public void verifyLicense(int responseCode, String signedData, String signature) {
            LicenseChecker.this.mHandler.post(new C10392(responseCode, signedData, signature));
        }

        private void startTimeout() {
            Log.i("LicenseChecker", "Start monitoring timeout.");
            LicenseChecker.this.mHandler.postDelayed(this.mOnTimeout, 10000);
        }

        private void clearTimeout() {
            Log.i("LicenseChecker", "Clearing timeout.");
            LicenseChecker.this.mHandler.removeCallbacks(this.mOnTimeout);
        }
    }

    static {
        RANDOM = new SecureRandom();
    }

    public LicenseChecker(Context context, Policy policy, String encodedPublicKey) {
        this.mChecksInProgress = new HashSet();
        this.mPendingChecks = new LinkedList();
        this.mContext = context;
        this.mPolicy = policy;
        this.mPublicKey = generatePublicKey(encodedPublicKey);
        this.mPackageName = this.mContext.getPackageName();
        this.mVersionCode = getVersionCode(context, this.mPackageName);
        HandlerThread handlerThread = new HandlerThread("background thread");
        handlerThread.start();
        this.mHandler = new Handler(handlerThread.getLooper());
    }

    private static PublicKey generatePublicKey(String encodedPublicKey) {
        try {
            return KeyFactory.getInstance("RSA").generatePublic(new X509EncodedKeySpec(Base64.decode(encodedPublicKey)));
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        } catch (Base64DecoderException e2) {
            Log.e("LicenseChecker", "Could not decode from Base64.");
            throw new IllegalArgumentException(e2);
        } catch (InvalidKeySpecException e3) {
            Log.e("LicenseChecker", "Invalid key specification.");
            throw new IllegalArgumentException(e3);
        }
    }

    public synchronized void checkAccess(LicenseCheckerCallback callback) {
        if (this.mPolicy.allowAccess()) {
            Log.i("LicenseChecker", "Using cached license response");
            callback.allow(256);
        } else {
            LicenseValidator validator = new LicenseValidator(this.mPolicy, new NullDeviceLimiter(), callback, generateNonce(), this.mPackageName, this.mVersionCode);
            if (this.mService == null) {
                Log.i("LicenseChecker", "Binding to licensing service.");
                try {
                    if (this.mContext.bindService(new Intent(new String(Base64.decode("Y29tLmFuZHJvaWQudmVuZGluZy5saWNlbnNpbmcuSUxpY2Vuc2luZ1NlcnZpY2U="))).setPackage("com.android.vending"), this, 1)) {
                        this.mPendingChecks.offer(validator);
                    } else {
                        Log.e("LicenseChecker", "Could not bind to service.");
                        handleServiceConnectionError(validator);
                    }
                } catch (SecurityException e) {
                    callback.applicationError(6);
                } catch (Base64DecoderException e2) {
                    e2.printStackTrace();
                }
            } else {
                this.mPendingChecks.offer(validator);
                runChecks();
            }
        }
    }

    private void runChecks() {
        while (true) {
            LicenseValidator validator = (LicenseValidator) this.mPendingChecks.poll();
            if (validator != null) {
                try {
                    Log.i("LicenseChecker", "Calling checkLicense on service for " + validator.getPackageName());
                    this.mService.checkLicense((long) validator.getNonce(), validator.getPackageName(), new ResultListener(validator));
                    this.mChecksInProgress.add(validator);
                } catch (RemoteException e) {
                    Log.w("LicenseChecker", "RemoteException in checkLicense call.", e);
                    handleServiceConnectionError(validator);
                }
            } else {
                return;
            }
        }
    }

    private synchronized void finishCheck(LicenseValidator validator) {
        this.mChecksInProgress.remove(validator);
        if (this.mChecksInProgress.isEmpty()) {
            cleanupService();
        }
    }

    public synchronized void onServiceConnected(ComponentName name, IBinder service) {
        this.mService = ILicensingService.Stub.asInterface(service);
        runChecks();
    }

    public synchronized void onServiceDisconnected(ComponentName name) {
        Log.w("LicenseChecker", "Service unexpectedly disconnected.");
        this.mService = null;
    }

    private synchronized void handleServiceConnectionError(LicenseValidator validator) {
        this.mPolicy.processServerResponse(291, null);
        if (this.mPolicy.allowAccess()) {
            validator.getCallback().allow(291);
        } else {
            validator.getCallback().dontAllow(291);
        }
    }

    private void cleanupService() {
        if (this.mService != null) {
            try {
                this.mContext.unbindService(this);
            } catch (IllegalArgumentException e) {
                Log.e("LicenseChecker", "Unable to unbind from licensing service (already unbound)");
            }
            this.mService = null;
        }
    }

    private int generateNonce() {
        return RANDOM.nextInt();
    }

    private static String getVersionCode(Context context, String packageName) {
        try {
            return String.valueOf(context.getPackageManager().getPackageInfo(packageName, 0).versionCode);
        } catch (NameNotFoundException e) {
            Log.e("LicenseChecker", "Package not found. could not get version code.");
            return "";
        }
    }
}
