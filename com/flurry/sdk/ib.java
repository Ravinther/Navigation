package com.flurry.sdk;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ib implements kk<hp> {
    private static final String f667a;

    /* renamed from: com.flurry.sdk.ib.1 */
    class C04271 extends DataOutputStream {
        final /* synthetic */ ib f666a;

        C04271(ib ibVar, OutputStream outputStream) {
            this.f666a = ibVar;
            super(outputStream);
        }

        public void close() {
        }
    }

    public /* synthetic */ Object m607b(InputStream inputStream) throws IOException {
        return m604a(inputStream);
    }

    static {
        f667a = ib.class.getSimpleName();
    }

    public void m605a(OutputStream outputStream, hp hpVar) throws IOException {
        if (outputStream != null && hpVar != null) {
            DataOutputStream c04271 = new C04271(this, outputStream);
            JSONObject jSONObject = new JSONObject();
            try {
                Object obj;
                m603a(jSONObject, "project_key", hpVar.f587a);
                m603a(jSONObject, "bundle_id", hpVar.f588b);
                m603a(jSONObject, "app_version", hpVar.f589c);
                jSONObject.put("sdk_version", hpVar.f590d);
                jSONObject.put("platform", hpVar.f591e);
                m603a(jSONObject, "platform_version", hpVar.f592f);
                jSONObject.put("limit_ad_tracking", hpVar.f593g);
                if (hpVar.f594h == null || hpVar.f594h.f604a == null) {
                    obj = null;
                } else {
                    obj = new JSONObject();
                    JSONObject jSONObject2 = new JSONObject();
                    m603a(jSONObject2, "model", hpVar.f594h.f604a.f571a);
                    m603a(jSONObject2, "brand", hpVar.f594h.f604a.f572b);
                    m603a(jSONObject2, "id", hpVar.f594h.f604a.f573c);
                    m603a(jSONObject2, "device", hpVar.f594h.f604a.f574d);
                    m603a(jSONObject2, "product", hpVar.f594h.f604a.f575e);
                    m603a(jSONObject2, "version_release", hpVar.f594h.f604a.f576f);
                    obj.put("com.flurry.proton.generated.avro.AndroidTags", jSONObject2);
                }
                if (obj != null) {
                    jSONObject.put("device_tags", obj);
                } else {
                    jSONObject.put("device_tags", JSONObject.NULL);
                }
                JSONArray jSONArray = new JSONArray();
                for (hr hrVar : hpVar.f595i) {
                    JSONObject jSONObject3 = new JSONObject();
                    jSONObject3.put("type", hrVar.f602a);
                    m603a(jSONObject3, "id", hrVar.f603b);
                    jSONArray.put(jSONObject3);
                }
                jSONObject.put("device_ids", jSONArray);
                if (hpVar.f596j == null || hpVar.f596j.f608a == null) {
                    obj = null;
                } else {
                    obj = new JSONObject();
                    JSONObject jSONObject4 = new JSONObject();
                    jSONObject4.put("latitude", hpVar.f596j.f608a.f605a);
                    jSONObject4.put("longitude", hpVar.f596j.f608a.f606b);
                    jSONObject4.put("accuracy", (double) hpVar.f596j.f608a.f607c);
                    obj.put("com.flurry.proton.generated.avro.Geolocation", jSONObject4);
                }
                if (obj != null) {
                    jSONObject.put("geo", obj);
                } else {
                    jSONObject.put("geo", JSONObject.NULL);
                }
                jq.m1016a(5, f667a, "Proton Request String: " + jSONObject.toString());
                c04271.write(jSONObject.toString().getBytes());
                c04271.flush();
                c04271.close();
            } catch (Throwable e) {
                throw new IOException("Invalid Json", e);
            } catch (Throwable th) {
                c04271.close();
            }
        }
    }

    private void m603a(JSONObject jSONObject, String str, String str2) throws IOException, JSONException {
        if (jSONObject == null) {
            throw new IOException("Null Json object");
        } else if (str2 != null) {
            jSONObject.put(str, str2);
        } else {
            jSONObject.put(str, JSONObject.NULL);
        }
    }

    public hp m604a(InputStream inputStream) throws IOException {
        throw new IOException("Deserialize not supported for request");
    }
}
