package com.google.android.gms.appindexing;

import android.net.Uri;
import android.net.Uri.Builder;
import com.google.android.gms.common.internal.zzw;

public final class AndroidAppUri {
    private final Uri mUri;

    private AndroidAppUri(Uri uri) {
        this.mUri = uri;
    }

    public static AndroidAppUri newAndroidAppUri(String packageName, Uri deepLink) {
        Builder authority = new Builder().scheme("android-app").authority(packageName);
        if (deepLink != null) {
            authority.appendPath(deepLink.getScheme());
            if (deepLink.getAuthority() != null) {
                authority.appendPath(deepLink.getAuthority());
            }
            for (String appendPath : deepLink.getPathSegments()) {
                authority.appendPath(appendPath);
            }
            authority.encodedQuery(deepLink.getEncodedQuery()).encodedFragment(deepLink.getEncodedFragment());
        }
        return new AndroidAppUri(authority.build());
    }

    public boolean equals(Object object) {
        return object instanceof AndroidAppUri ? this.mUri.equals(((AndroidAppUri) object).mUri) : false;
    }

    public int hashCode() {
        return zzw.hashCode(this.mUri);
    }

    public String toString() {
        return this.mUri.toString();
    }
}
