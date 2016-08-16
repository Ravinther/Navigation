package com.google.android.vending.licensing;

import android.text.TextUtils;
import android.util.Log;
import com.google.android.vending.licensing.util.Base64;
import com.google.android.vending.licensing.util.Base64DecoderException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.Signature;
import java.security.SignatureException;
import loquendo.tts.engine.TTSConst;

class LicenseValidator {
    private final LicenseCheckerCallback mCallback;
    private final DeviceLimiter mDeviceLimiter;
    private final int mNonce;
    private final String mPackageName;
    private final Policy mPolicy;
    private final String mVersionCode;

    LicenseValidator(Policy policy, DeviceLimiter deviceLimiter, LicenseCheckerCallback callback, int nonce, String packageName, String versionCode) {
        this.mPolicy = policy;
        this.mDeviceLimiter = deviceLimiter;
        this.mCallback = callback;
        this.mNonce = nonce;
        this.mPackageName = packageName;
        this.mVersionCode = versionCode;
    }

    public LicenseCheckerCallback getCallback() {
        return this.mCallback;
    }

    public int getNonce() {
        return this.mNonce;
    }

    public String getPackageName() {
        return this.mPackageName;
    }

    public void verify(PublicKey publicKey, int responseCode, String signedData, String signature) {
        String userId = null;
        ResponseData data = null;
        if (responseCode == 0 || responseCode == 1 || responseCode == 2) {
            if (signedData == null) {
                handleInvalidResponse();
                return;
            }
            try {
                Signature sig = Signature.getInstance("SHA1withRSA");
                sig.initVerify(publicKey);
                sig.update(signedData.getBytes());
                if (sig.verify(Base64.decode(signature))) {
                    try {
                        data = ResponseData.parse(signedData);
                        if (data.responseCode != responseCode) {
                            Log.e("LicenseValidator", "Response codes don't match.");
                            handleInvalidResponse();
                            return;
                        } else if (data.nonce != this.mNonce) {
                            Log.e("LicenseValidator", "Nonce doesn't match.");
                            handleInvalidResponse();
                            return;
                        } else if (!data.packageName.equals(this.mPackageName)) {
                            Log.e("LicenseValidator", "Package name doesn't match.");
                            handleInvalidResponse();
                            return;
                        } else if (data.versionCode.equals(this.mVersionCode)) {
                            userId = data.userId;
                            if (TextUtils.isEmpty(userId)) {
                                Log.e("LicenseValidator", "User identifier is empty.");
                                handleInvalidResponse();
                                return;
                            }
                        } else {
                            Log.e("LicenseValidator", "Version codes don't match.");
                            handleInvalidResponse();
                            return;
                        }
                    } catch (IllegalArgumentException e) {
                        Log.e("LicenseValidator", "Could not parse response.");
                        handleInvalidResponse();
                        return;
                    }
                }
                Log.e("LicenseValidator", "Signature verification failed.");
                handleInvalidResponse();
                return;
            } catch (NoSuchAlgorithmException e2) {
                throw new RuntimeException(e2);
            } catch (InvalidKeyException e3) {
                handleApplicationError(5);
                return;
            } catch (SignatureException e4) {
                throw new RuntimeException(e4);
            } catch (Base64DecoderException e5) {
                Log.e("LicenseValidator", "Could not Base64-decode signature.");
                handleInvalidResponse();
                return;
            }
        }
        switch (responseCode) {
            case TTSConst.TTSEVT_AUDIOSTART /*0*/:
            case TTSConst.TTSPARAGRAPH /*2*/:
                handleResponse(this.mDeviceLimiter.isDeviceAllowed(userId), data);
            case TTSConst.TTSMULTILINE /*1*/:
                handleResponse(561, data);
            case TTSConst.TTSUNICODE /*3*/:
                handleApplicationError(3);
            case TTSConst.TTSXML /*4*/:
                Log.w("LicenseValidator", "An error has occurred on the licensing server.");
                handleResponse(291, data);
            case TTSConst.TTSEVT_TEXT /*5*/:
                Log.w("LicenseValidator", "Licensing server is refusing to talk to this device, over quota.");
                handleResponse(291, data);
            case 257:
                Log.w("LicenseValidator", "Error contacting licensing server.");
                handleResponse(291, data);
            case 258:
                handleApplicationError(1);
            case 259:
                handleApplicationError(2);
            default:
                Log.e("LicenseValidator", "Unknown response code for license check.");
                handleInvalidResponse();
        }
    }

    private void handleResponse(int response, ResponseData rawData) {
        this.mPolicy.processServerResponse(response, rawData);
        if (this.mPolicy.allowAccess()) {
            this.mCallback.allow(response);
        } else {
            this.mCallback.dontAllow(response);
        }
    }

    private void handleApplicationError(int code) {
        this.mCallback.applicationError(code);
    }

    private void handleInvalidResponse() {
        this.mCallback.dontAllow(561);
    }
}
