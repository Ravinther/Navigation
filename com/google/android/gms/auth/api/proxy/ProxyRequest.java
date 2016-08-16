package com.google.android.gms.auth.api.proxy;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;

public class ProxyRequest implements SafeParcelable {
    public static final Creator<ProxyRequest> CREATOR;
    public static final int HTTP_METHOD_DELETE;
    public static final int HTTP_METHOD_GET;
    public static final int HTTP_METHOD_HEAD;
    public static final int HTTP_METHOD_OPTIONS;
    public static final int HTTP_METHOD_PATCH;
    public static final int HTTP_METHOD_POST;
    public static final int HTTP_METHOD_PUT;
    public static final int HTTP_METHOD_TRACE;
    public static final int LAST_CODE;
    public final byte[] body;
    public final int httpMethod;
    public final long timeoutMillis;
    public final String url;
    final int versionCode;
    Bundle zzRE;

    static {
        CREATOR = new zzb();
        HTTP_METHOD_GET = 0;
        HTTP_METHOD_POST = 1;
        HTTP_METHOD_PUT = 2;
        HTTP_METHOD_DELETE = 3;
        HTTP_METHOD_HEAD = 4;
        HTTP_METHOD_OPTIONS = 5;
        HTTP_METHOD_TRACE = 6;
        HTTP_METHOD_PATCH = 7;
        LAST_CODE = 7;
    }

    ProxyRequest(int version, String googleUrl, int httpMethod, long timeoutMillis, byte[] body, Bundle headers) {
        this.versionCode = version;
        this.url = googleUrl;
        this.httpMethod = httpMethod;
        this.timeoutMillis = timeoutMillis;
        this.body = body;
        this.zzRE = headers;
    }

    public int describeContents() {
        return 0;
    }

    public String toString() {
        return "ProxyRequest[ url: " + this.url + ", method: " + this.httpMethod + " ]";
    }

    public void writeToParcel(Parcel parcel, int flags) {
        zzb.zza(this, parcel, flags);
    }
}
