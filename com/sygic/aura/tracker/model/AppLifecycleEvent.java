package com.sygic.aura.tracker.model;

import org.json.JSONException;
import org.json.JSONObject;

public abstract class AppLifecycleEvent extends Event {
    protected JSONObject jsonifyPayload() throws JSONException {
        return null;
    }
}
