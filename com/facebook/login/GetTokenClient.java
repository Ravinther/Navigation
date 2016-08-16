package com.facebook.login;

import android.content.Context;
import android.os.Bundle;
import com.facebook.internal.PlatformServiceClient;

final class GetTokenClient extends PlatformServiceClient {
    GetTokenClient(Context context, String applicationId) {
        super(context, 65536, 65537, 20121101, applicationId);
    }

    protected void populateRequestBundle(Bundle data) {
    }
}
