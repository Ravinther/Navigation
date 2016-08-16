package com.google.android.gms.common;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.IntentSender.SendIntentException;
import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import com.google.android.gms.common.internal.zzw;
import loquendo.tts.engine.TTSConst;

public final class ConnectionResult implements SafeParcelable {
    public static final Creator<ConnectionResult> CREATOR;
    public static final ConnectionResult zzYi;
    private final PendingIntent mPendingIntent;
    final int mVersionCode;
    private final int zzWu;

    static {
        zzYi = new ConnectionResult(0, null);
        CREATOR = new zzb();
    }

    ConnectionResult(int versionCode, int statusCode, PendingIntent pendingIntent) {
        this.mVersionCode = versionCode;
        this.zzWu = statusCode;
        this.mPendingIntent = pendingIntent;
    }

    public ConnectionResult(int statusCode, PendingIntent pendingIntent) {
        this(1, statusCode, pendingIntent);
    }

    static String getStatusString(int statusCode) {
        switch (statusCode) {
            case TTSConst.TTSEVT_AUDIOSTART /*0*/:
                return "SUCCESS";
            case TTSConst.TTSMULTILINE /*1*/:
                return "SERVICE_MISSING";
            case TTSConst.TTSPARAGRAPH /*2*/:
                return "SERVICE_VERSION_UPDATE_REQUIRED";
            case TTSConst.TTSUNICODE /*3*/:
                return "SERVICE_DISABLED";
            case TTSConst.TTSXML /*4*/:
                return "SIGN_IN_REQUIRED";
            case TTSConst.TTSEVT_TEXT /*5*/:
                return "INVALID_ACCOUNT";
            case TTSConst.TTSEVT_SENTENCE /*6*/:
                return "RESOLUTION_REQUIRED";
            case TTSConst.TTSEVT_BOOKMARK /*7*/:
                return "NETWORK_ERROR";
            case TTSConst.TTSEVT_TAG /*8*/:
                return "INTERNAL_ERROR";
            case TTSConst.TTSEVT_PAUSE /*9*/:
                return "SERVICE_INVALID";
            case TTSConst.TTSEVT_RESUME /*10*/:
                return "DEVELOPER_ERROR";
            case TTSConst.TTSEVT_FREESPACE /*11*/:
                return "LICENSE_CHECK_FAILED";
            case TTSConst.TTSEVT_AUDIO /*13*/:
                return "CANCELED";
            case TTSConst.TTSEVT_VOICECHANGE /*14*/:
                return "TIMEOUT";
            case TTSConst.TTSEVT_LANGUAGECHANGE /*15*/:
                return "INTERRUPTED";
            case TTSConst.TTSEVT_ERROR /*16*/:
                return "API_UNAVAILABLE";
            case TTSConst.TTSEVT_JUMP /*17*/:
                return "SIGN_IN_FAILED";
            case TTSConst.TTSEVT_PARAGRAPH /*18*/:
                return "SERVICE_UPDATING";
            default:
                return "UNKNOWN_ERROR_CODE(" + statusCode + ")";
        }
    }

    public int describeContents() {
        return 0;
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof ConnectionResult)) {
            return false;
        }
        ConnectionResult connectionResult = (ConnectionResult) o;
        return this.zzWu == connectionResult.zzWu && zzw.equal(this.mPendingIntent, connectionResult.mPendingIntent);
    }

    public int getErrorCode() {
        return this.zzWu;
    }

    public PendingIntent getResolution() {
        return this.mPendingIntent;
    }

    public boolean hasResolution() {
        return (this.zzWu == 0 || this.mPendingIntent == null) ? false : true;
    }

    public int hashCode() {
        return zzw.hashCode(Integer.valueOf(this.zzWu), this.mPendingIntent);
    }

    public boolean isSuccess() {
        return this.zzWu == 0;
    }

    public void startResolutionForResult(Activity activity, int requestCode) throws SendIntentException {
        if (hasResolution()) {
            activity.startIntentSenderForResult(this.mPendingIntent.getIntentSender(), requestCode, null, 0, 0, 0);
        }
    }

    public String toString() {
        return zzw.zzu(this).zzg("statusCode", getStatusString(this.zzWu)).zzg("resolution", this.mPendingIntent).toString();
    }

    public void writeToParcel(Parcel out, int flags) {
        zzb.zza(this, out, flags);
    }
}
