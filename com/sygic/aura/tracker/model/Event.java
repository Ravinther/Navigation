package com.sygic.aura.tracker.model;

import com.sygic.aura.tracker.TrackerUtils;
import java.util.HashMap;
import org.json.JSONException;
import org.json.JSONObject;

public abstract class Event {
    protected String mId;
    protected HashMap<String, Object> mMetrics;
    protected long mTimestamp;

    protected abstract String getJsonTypeName();

    protected abstract JSONObject jsonifyPayload() throws JSONException;

    public Event() {
        this.mId = TrackerUtils.getRandomId();
        this.mTimestamp = TrackerUtils.getCurrentTime();
    }

    public Event attachMetrics(HashMap<String, Object> metricsMap) {
        this.mMetrics = metricsMap;
        return this;
    }

    public void toJson(JSONObject json) throws JSONException {
        jsonifyHead(json);
        JSONObject jsonPayload = jsonifyPayload();
        if (jsonPayload != null) {
            json.put("payload", jsonPayload);
        }
        JSONObject jsonMetrics = jsonifyMetrics();
        if (jsonMetrics != null) {
            json.put("metrics", jsonMetrics);
        }
    }

    private void jsonifyHead(JSONObject json) throws JSONException {
        json.put("id", this.mId);
        json.put("timestamp", this.mTimestamp);
        json.put("type", getJsonTypeName());
    }

    private JSONObject jsonifyMetrics() throws JSONException {
        if (this.mMetrics == null || this.mMetrics.isEmpty()) {
            return null;
        }
        JSONObject jSONObject = new JSONObject();
        for (String key : this.mMetrics.keySet()) {
            try {
                jSONObject.put(key, this.mMetrics.get(key));
            } catch (JSONException e) {
            }
        }
        return jSONObject;
    }
}
