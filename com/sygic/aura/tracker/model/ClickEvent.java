package com.sygic.aura.tracker.model;

import org.json.JSONException;
import org.json.JSONObject;

public class ClickEvent extends Event {
    private final String mCategory;
    private final String mName;
    private final String mValue;

    public ClickEvent(String category, String name, String value) {
        this.mCategory = category;
        this.mName = name;
        this.mValue = value;
    }

    protected String getJsonTypeName() {
        return "click";
    }

    protected JSONObject jsonifyPayload() throws JSONException {
        JSONObject json = new JSONObject();
        json.put("category", this.mCategory);
        json.put("name", this.mName);
        json.put("value", this.mValue);
        return json;
    }
}
