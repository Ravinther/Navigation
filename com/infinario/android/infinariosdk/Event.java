package com.infinario.android.infinariosdk;

import java.util.HashMap;
import java.util.Map;
import org.json.JSONObject;

public class Event extends Command {
    protected String companyId;
    protected Map<String, String> customerIds;
    protected Map<String, Object> properties;
    protected String type;

    public Event(Map<String, String> customerIds, String companyId, String type, Map<String, Object> properties, Long timestamp) {
        super("crm/events", timestamp);
        this.properties = null;
        this.customerIds = customerIds;
        this.companyId = companyId;
        this.properties = properties;
        this.type = type;
    }

    protected Map<String, Object> getData() {
        Map<String, Object> data = new HashMap();
        data.put("customer_ids", new JSONObject(this.customerIds));
        data.put("company_id", this.companyId);
        data.put("type", this.type);
        data.put("age", Long.valueOf(this.createdAt.getTime()));
        if (this.properties != null) {
            data.put("properties", new JSONObject(this.properties));
        }
        return data;
    }
}
