package com.sygic.aura.tracker.model;

import org.json.JSONException;
import org.json.JSONObject;

public class ScreenViewEvent extends Event {
    private final String mScreenName;

    public ScreenViewEvent(String screenName) {
        this.mScreenName = screenName;
    }

    protected String getJsonTypeName() {
        return "screen_view";
    }

    protected JSONObject jsonifyPayload() throws JSONException {
        JSONObject json = new JSONObject();
        json.put("name", this.mScreenName);
        return json;
    }
}
