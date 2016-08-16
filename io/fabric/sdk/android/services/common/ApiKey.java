package io.fabric.sdk.android.services.common;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import io.fabric.sdk.android.Fabric;

public class ApiKey {
    public String getValue(Context context) {
        String apiKey = getApiKeyFromManifest(context);
        if (TextUtils.isEmpty(apiKey)) {
            apiKey = getApiKeyFromStrings(context);
        }
        if (TextUtils.isEmpty(apiKey)) {
            logErrorOrThrowException(context);
        }
        return apiKey;
    }

    protected String getApiKeyFromManifest(Context context) {
        String apiKey = null;
        try {
            Bundle bundle = context.getPackageManager().getApplicationInfo(context.getPackageName(), 128).metaData;
            if (bundle != null) {
                apiKey = bundle.getString("io.fabric.ApiKey");
                if (apiKey == null) {
                    Fabric.getLogger().m1453d("Fabric", "Falling back to Crashlytics key lookup from Manifest");
                    apiKey = bundle.getString("com.crashlytics.ApiKey");
                }
            }
        } catch (Exception e) {
            Fabric.getLogger().m1453d("Fabric", "Caught non-fatal exception while retrieving apiKey: " + e);
        }
        return apiKey;
    }

    protected String getApiKeyFromStrings(Context context) {
        int id = CommonUtils.getResourcesIdentifier(context, "io.fabric.ApiKey", "string");
        if (id == 0) {
            Fabric.getLogger().m1453d("Fabric", "Falling back to Crashlytics key lookup from Strings");
            id = CommonUtils.getResourcesIdentifier(context, "com.crashlytics.ApiKey", "string");
        }
        if (id != 0) {
            return context.getResources().getString(id);
        }
        return null;
    }

    protected void logErrorOrThrowException(Context context) {
        if (Fabric.isDebuggable() || CommonUtils.isAppDebuggable(context)) {
            throw new IllegalArgumentException(buildApiKeyInstructions());
        }
        Fabric.getLogger().m1455e("Fabric", buildApiKeyInstructions());
    }

    protected String buildApiKeyInstructions() {
        return "Fabric could not be initialized, API key missing from AndroidManifest.xml. Add the following tag to your Application element \n\t<meta-data android:name=\"io.fabric.ApiKey\" android:value=\"YOUR_API_KEY\"/>";
    }
}
