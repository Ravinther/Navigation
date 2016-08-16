package com.infinario.android.infinariosdk;

import java.util.HashMap;
import java.util.Map;
import org.json.JSONObject;

public class Customer extends Command {
    protected String companyId;
    protected Map<String, String> ids;
    protected Map<String, Object> properties;

    public Customer(Map<String, String> ids, String companyId, Map<String, Object> properties) {
        super("crm/customers");
        this.properties = null;
        this.ids = ids;
        this.companyId = companyId;
        this.properties = properties;
    }

    protected Map<String, Object> getData() {
        Map<String, Object> data = new HashMap();
        data.put("ids", new JSONObject(this.ids));
        data.put("company_id", this.companyId);
        if (this.properties != null) {
            data.put("properties", new JSONObject(this.properties));
        }
        return data;
    }
}
