package com.infinario.android.infinariosdk;

import com.sygic.aura.poi.fragment.PoiFragment;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import org.json.JSONObject;

public class Command {
    protected Date createdAt;
    protected String endpoint;

    public Command(String endpoint, Long timestamp) {
        this.endpoint = endpoint;
        this.createdAt = timestamp == null ? new Date() : new Date(timestamp.longValue());
    }

    public Command(String endpoint) {
        this(endpoint, null);
    }

    public Map<String, Object> toMap() {
        Map<String, Object> command = new HashMap();
        command.put("name", this.endpoint);
        command.put(PoiFragment.ARG_DATA, new JSONObject(getData()));
        return command;
    }

    public String toString() {
        return new JSONObject(toMap()).toString();
    }

    protected Map<String, Object> getData() {
        return new HashMap();
    }
}
