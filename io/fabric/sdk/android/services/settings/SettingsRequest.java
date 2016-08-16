package io.fabric.sdk.android.services.settings;

public class SettingsRequest {
    public final String apiKey;
    public final String buildVersion;
    public final String deviceId;
    public final String displayVersion;
    public final String iconHash;
    public final String instanceId;
    public final int source;

    public SettingsRequest(String apiKey, String deviceId, String instanceId, String displayVersion, String buildVersion, int source, String iconHash) {
        this.apiKey = apiKey;
        this.deviceId = deviceId;
        this.instanceId = instanceId;
        this.displayVersion = displayVersion;
        this.buildVersion = buildVersion;
        this.source = source;
        this.iconHash = iconHash;
    }
}
