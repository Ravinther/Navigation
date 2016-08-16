package com.crashlytics.android.answers;

import android.app.Activity;
import java.util.Collections;
import java.util.Map;

final class SessionEvent {
    public final Map<String, Object> customAttributes;
    public final String customType;
    public final Map<String, String> details;
    public final Map<String, Object> predefinedAttributes;
    public final String predefinedType;
    public final SessionEventMetadata sessionEventMetadata;
    private String stringRepresentation;
    public final long timestamp;
    public final Type type;

    static class Builder {
        Map<String, Object> customAttributes;
        String customType;
        Map<String, String> details;
        Map<String, Object> predefinedAttributes;
        String predefinedType;
        final long timestamp;
        final Type type;

        public Builder(Type type) {
            this.type = type;
            this.timestamp = System.currentTimeMillis();
            this.details = Collections.emptyMap();
            this.customType = null;
            this.customAttributes = Collections.emptyMap();
            this.predefinedType = null;
            this.predefinedAttributes = Collections.emptyMap();
        }

        public Builder details(Map<String, String> details) {
            this.details = details;
            return this;
        }

        public SessionEvent build(SessionEventMetadata sessionEventMetadata) {
            return new SessionEvent(this.timestamp, this.type, this.details, this.customType, this.customAttributes, this.predefinedType, this.predefinedAttributes, null);
        }
    }

    enum Type {
        CREATE,
        START,
        RESUME,
        SAVE_INSTANCE_STATE,
        PAUSE,
        STOP,
        DESTROY,
        ERROR,
        CRASH,
        INSTALL,
        CUSTOM,
        PREDEFINED
    }

    public static Builder lifecycleEventBuilder(Type type, Activity activity) {
        return new Builder(type).details(Collections.singletonMap("activity", activity.getClass().getName()));
    }

    public static Builder installEventBuilder() {
        return new Builder(Type.INSTALL);
    }

    public static Builder errorEventBuilder(String sessionId) {
        return new Builder(Type.ERROR).details(Collections.singletonMap("sessionId", sessionId));
    }

    public static Builder crashEventBuilder(String sessionId) {
        return new Builder(Type.CRASH).details(Collections.singletonMap("sessionId", sessionId));
    }

    private SessionEvent(SessionEventMetadata sessionEventMetadata, long timestamp, Type type, Map<String, String> details, String customType, Map<String, Object> customAttributes, String predefinedType, Map<String, Object> predefinedAttributes) {
        this.sessionEventMetadata = sessionEventMetadata;
        this.timestamp = timestamp;
        this.type = type;
        this.details = details;
        this.customType = customType;
        this.customAttributes = customAttributes;
        this.predefinedType = predefinedType;
        this.predefinedAttributes = predefinedAttributes;
    }

    public String toString() {
        if (this.stringRepresentation == null) {
            this.stringRepresentation = "[" + getClass().getSimpleName() + ": " + "timestamp=" + this.timestamp + ", type=" + this.type + ", details=" + this.details.toString() + ", customType=" + this.customType + ", customAttributes=" + this.customAttributes.toString() + ", predefinedType=" + this.predefinedType + ", predefinedAttributes=" + this.predefinedAttributes.toString() + ", metadata=[" + this.sessionEventMetadata + "]]";
        }
        return this.stringRepresentation;
    }
}
