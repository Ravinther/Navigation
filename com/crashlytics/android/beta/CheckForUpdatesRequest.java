package com.crashlytics.android.beta;

import io.fabric.sdk.android.Fabric;
import io.fabric.sdk.android.Kit;
import io.fabric.sdk.android.services.common.AbstractSpiCall;
import io.fabric.sdk.android.services.network.HttpMethod;
import io.fabric.sdk.android.services.network.HttpRequest;
import io.fabric.sdk.android.services.network.HttpRequestFactory;
import java.util.HashMap;
import java.util.Map;
import org.json.JSONObject;

class CheckForUpdatesRequest extends AbstractSpiCall {
    private final CheckForUpdatesResponseTransform responseTransform;

    public CheckForUpdatesRequest(Kit kit, String protocolAndHostOverride, String url, HttpRequestFactory requestFactory, CheckForUpdatesResponseTransform responseTransform) {
        super(kit, protocolAndHostOverride, url, requestFactory, HttpMethod.GET);
        this.responseTransform = responseTransform;
    }

    public CheckForUpdatesResponse invoke(String apiKey, String idHeaderValue, BuildProperties buildProps) {
        HttpRequest httpRequest = null;
        try {
            Map<String, String> queryParams = getQueryParamsFor(buildProps);
            httpRequest = applyHeadersTo(getHttpRequest(queryParams), apiKey, idHeaderValue);
            Fabric.getLogger().m1453d("Beta", "Checking for updates from " + getUrl());
            Fabric.getLogger().m1453d("Beta", "Checking for updates query params are: " + queryParams);
            if (httpRequest.ok()) {
                Fabric.getLogger().m1453d("Beta", "Checking for updates was successful");
                CheckForUpdatesResponse fromJson = this.responseTransform.fromJson(new JSONObject(httpRequest.body()));
                if (httpRequest == null) {
                    return fromJson;
                }
                Fabric.getLogger().m1453d("Fabric", "Checking for updates request ID: " + httpRequest.header("X-REQUEST-ID"));
                return fromJson;
            }
            Fabric.getLogger().m1455e("Beta", "Checking for updates failed. Response code: " + httpRequest.code());
            if (httpRequest != null) {
                Fabric.getLogger().m1453d("Fabric", "Checking for updates request ID: " + httpRequest.header("X-REQUEST-ID"));
            }
            return null;
        } catch (Exception e) {
            Fabric.getLogger().m1456e("Beta", "Error while checking for updates from " + getUrl(), e);
            if (httpRequest != null) {
                Fabric.getLogger().m1453d("Fabric", "Checking for updates request ID: " + httpRequest.header("X-REQUEST-ID"));
            }
        } catch (Throwable th) {
            if (httpRequest != null) {
                Fabric.getLogger().m1453d("Fabric", "Checking for updates request ID: " + httpRequest.header("X-REQUEST-ID"));
            }
        }
    }

    private HttpRequest applyHeadersTo(HttpRequest request, String apiKey, String idHeaderValue) {
        return request.header("Accept", "application/json").header("User-Agent", "Crashlytics Android SDK/" + this.kit.getVersion()).header("X-CRASHLYTICS-DEVELOPER-TOKEN", "470fa2b4ae81cd56ecbcda9735803434cec591fa").header("X-CRASHLYTICS-API-CLIENT-TYPE", "android").header("X-CRASHLYTICS-API-CLIENT-VERSION", this.kit.getVersion()).header("X-CRASHLYTICS-API-KEY", apiKey).header("X-CRASHLYTICS-D", idHeaderValue);
    }

    private Map<String, String> getQueryParamsFor(BuildProperties buildProps) {
        Map<String, String> queryParams = new HashMap();
        queryParams.put("build_version", buildProps.versionCode);
        queryParams.put("display_version", buildProps.versionName);
        queryParams.put("instance", buildProps.buildId);
        queryParams.put("source", "3");
        return queryParams;
    }
}
