package io.fabric.sdk.android.services.settings;

import io.fabric.sdk.android.Fabric;
import io.fabric.sdk.android.Kit;
import io.fabric.sdk.android.services.common.AbstractSpiCall;
import io.fabric.sdk.android.services.common.CommonUtils;
import io.fabric.sdk.android.services.network.HttpMethod;
import io.fabric.sdk.android.services.network.HttpRequest;
import io.fabric.sdk.android.services.network.HttpRequestFactory;
import java.util.HashMap;
import java.util.Map;
import org.json.JSONObject;

class DefaultSettingsSpiCall extends AbstractSpiCall implements SettingsSpiCall {
    public DefaultSettingsSpiCall(Kit kit, String protocolAndHostOverride, String url, HttpRequestFactory requestFactory) {
        this(kit, protocolAndHostOverride, url, requestFactory, HttpMethod.GET);
    }

    DefaultSettingsSpiCall(Kit kit, String protocolAndHostOverride, String url, HttpRequestFactory requestFactory, HttpMethod method) {
        super(kit, protocolAndHostOverride, url, requestFactory, method);
    }

    public JSONObject invoke(SettingsRequest requestData) {
        JSONObject toReturn = null;
        HttpRequest httpRequest = null;
        try {
            Map<String, String> queryParams = getQueryParamsFor(requestData);
            httpRequest = applyHeadersTo(getHttpRequest(queryParams), requestData);
            Fabric.getLogger().m1453d("Fabric", "Requesting settings from " + getUrl());
            Fabric.getLogger().m1453d("Fabric", "Settings query params were: " + queryParams);
            toReturn = handleResponse(httpRequest);
            return toReturn;
        } finally {
            if (httpRequest != null) {
                Fabric.getLogger().m1453d("Fabric", "Settings request ID: " + httpRequest.header("X-REQUEST-ID"));
            }
        }
    }

    JSONObject handleResponse(HttpRequest httpRequest) {
        int statusCode = httpRequest.code();
        Fabric.getLogger().m1453d("Fabric", "Settings result was: " + statusCode);
        if (requestWasSuccessful(statusCode)) {
            return getJsonObjectFrom(httpRequest.body());
        }
        Fabric.getLogger().m1455e("Fabric", "Failed to retrieve settings from " + getUrl());
        return null;
    }

    boolean requestWasSuccessful(int httpStatusCode) {
        return httpStatusCode == 200 || httpStatusCode == 201 || httpStatusCode == 202 || httpStatusCode == 203;
    }

    private JSONObject getJsonObjectFrom(String httpRequestBody) {
        try {
            return new JSONObject(httpRequestBody);
        } catch (Exception e) {
            Fabric.getLogger().m1454d("Fabric", "Failed to parse settings JSON from " + getUrl(), e);
            Fabric.getLogger().m1453d("Fabric", "Settings response " + httpRequestBody);
            return null;
        }
    }

    private Map<String, String> getQueryParamsFor(SettingsRequest requestData) {
        Map<String, String> queryParams = new HashMap();
        queryParams.put("build_version", requestData.buildVersion);
        queryParams.put("display_version", requestData.displayVersion);
        queryParams.put("source", Integer.toString(requestData.source));
        if (requestData.iconHash != null) {
            queryParams.put("icon_hash", requestData.iconHash);
        }
        String instanceId = requestData.instanceId;
        if (!CommonUtils.isNullOrEmpty(instanceId)) {
            queryParams.put("instance", instanceId);
        }
        return queryParams;
    }

    private HttpRequest applyHeadersTo(HttpRequest request, SettingsRequest requestData) {
        return request.header("X-CRASHLYTICS-API-KEY", requestData.apiKey).header("X-CRASHLYTICS-API-CLIENT-TYPE", "android").header("X-CRASHLYTICS-D", requestData.deviceId).header("X-CRASHLYTICS-API-CLIENT-VERSION", this.kit.getVersion()).header("Accept", "application/json");
    }
}
