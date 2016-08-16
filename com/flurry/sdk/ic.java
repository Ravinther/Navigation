package com.flurry.sdk;

import android.text.TextUtils;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONObject;

public class ic implements kk<hq> {
    private static final String f668a;

    public /* synthetic */ Object m613b(InputStream inputStream) throws IOException {
        return m610a(inputStream);
    }

    static {
        f668a = ic.class.getSimpleName();
    }

    public void m611a(OutputStream outputStream, hq hqVar) throws IOException {
        throw new IOException("Serialize not supported for response");
    }

    public hq m610a(InputStream inputStream) throws IOException {
        if (inputStream == null) {
            return null;
        }
        String str = new String(lc.m1269a(inputStream));
        jq.m1016a(5, f668a, "Proton response string: " + str);
        hq hqVar = new hq();
        try {
            JSONObject jSONObject = new JSONObject(str);
            hqVar.f597a = jSONObject.getLong("issued_at");
            hqVar.f598b = jSONObject.getLong("refresh_ttl");
            hqVar.f599c = jSONObject.getLong("expiration_ttl");
            JSONObject jSONObject2 = (JSONObject) jSONObject.get("global_settings");
            if (jSONObject2 != null) {
                hv hvVar = new hv();
                hvVar.f609a = m609b(jSONObject2.getString("log_level"));
                hvVar.f610b = jSONObject2.getBoolean("analytics_enabled");
                hqVar.f600d = hvVar;
            }
            jSONObject2 = (JSONObject) jSONObject.get("callbacks");
            if (jSONObject2 != null) {
                ho hoVar = new ho();
                hoVar.f585a = jSONObject2.getInt("max_callbacks");
                JSONArray jSONArray = jSONObject2.getJSONArray("templates");
                if (jSONArray != null) {
                    List arrayList = new ArrayList();
                    for (int i = 0; i < jSONArray.length(); i++) {
                        jSONObject2 = (JSONObject) jSONArray.get(i);
                        if (jSONObject2 != null) {
                            hn hnVar = new hn();
                            hnVar.f577a = jSONObject2.getString("partner");
                            JSONArray jSONArray2 = jSONObject2.getJSONArray("events");
                            if (jSONArray2 != null) {
                                hnVar.f578b = ld.m1288b(jSONArray2);
                            }
                            hnVar.f579c = m608a(jSONObject2.getString("method"));
                            hnVar.f580d = jSONObject2.getString("uri_template");
                            hnVar.f581e = jSONObject2.getString("body_template");
                            hnVar.f582f = jSONObject2.getInt("max_redirects");
                            hnVar.f583g = jSONObject2.getInt("connect_timeout");
                            hnVar.f584h = jSONObject2.getInt("request_timeout");
                            arrayList.add(hnVar);
                        }
                    }
                    hoVar.f586b = arrayList;
                }
                hqVar.f601e = hoVar;
            }
            return hqVar;
        } catch (Throwable e) {
            throw new IOException("Exception while deserialize:", e);
        }
    }

    private hx m608a(String str) {
        hx hxVar = hx.GET;
        try {
            if (TextUtils.isEmpty(str)) {
                return hxVar;
            }
            return (hx) Enum.valueOf(hx.class, str);
        } catch (Exception e) {
            return hxVar;
        }
    }

    private hw m609b(String str) {
        hw hwVar = hw.OFF;
        try {
            if (TextUtils.isEmpty(str)) {
                return hwVar;
            }
            return (hw) Enum.valueOf(hw.class, str);
        } catch (Exception e) {
            return hwVar;
        }
    }
}
