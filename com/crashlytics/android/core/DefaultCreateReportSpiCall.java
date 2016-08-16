package com.crashlytics.android.core;

import io.fabric.sdk.android.Fabric;
import io.fabric.sdk.android.Kit;
import io.fabric.sdk.android.services.common.AbstractSpiCall;
import io.fabric.sdk.android.services.common.ResponseParser;
import io.fabric.sdk.android.services.network.HttpMethod;
import io.fabric.sdk.android.services.network.HttpRequest;
import io.fabric.sdk.android.services.network.HttpRequestFactory;
import java.util.Map.Entry;

class DefaultCreateReportSpiCall extends AbstractSpiCall implements CreateReportSpiCall {
    public DefaultCreateReportSpiCall(Kit kit, String protocolAndHostOverride, String url, HttpRequestFactory requestFactory) {
        super(kit, protocolAndHostOverride, url, requestFactory, HttpMethod.POST);
    }

    public boolean invoke(CreateReportRequest requestData) {
        HttpRequest httpRequest = applyMultipartDataTo(applyHeadersTo(getHttpRequest(), requestData), requestData);
        Fabric.getLogger().m1453d("Fabric", "Sending report to: " + getUrl());
        int statusCode = httpRequest.code();
        Fabric.getLogger().m1453d("Fabric", "Create report request ID: " + httpRequest.header("X-REQUEST-ID"));
        Fabric.getLogger().m1453d("Fabric", "Result was: " + statusCode);
        return ResponseParser.parse(statusCode) == 0;
    }

    private HttpRequest applyHeadersTo(HttpRequest request, CreateReportRequest requestData) {
        request = request.header("X-CRASHLYTICS-API-KEY", requestData.apiKey).header("X-CRASHLYTICS-API-CLIENT-TYPE", "android").header("X-CRASHLYTICS-API-CLIENT-VERSION", CrashlyticsCore.getInstance().getVersion());
        for (Entry entry : requestData.report.getCustomHeaders().entrySet()) {
            request = request.header(entry);
        }
        return request;
    }

    private HttpRequest applyMultipartDataTo(HttpRequest request, CreateReportRequest requestData) {
        Report report = requestData.report;
        return request.part("report[file]", report.getFileName(), "application/octet-stream", report.getFile()).part("report[identifier]", report.getIdentifier());
    }
}
