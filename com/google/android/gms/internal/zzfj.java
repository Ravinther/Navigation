package com.google.android.gms.internal;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import com.sygic.aura.poi.fragment.PoiFragment;
import org.json.JSONObject;

@zzgk
public class zzfj extends Handler {
    private final zzfi zzBD;

    public zzfj(Context context) {
        this(new zzfk(context));
    }

    public zzfj(zzfi com_google_android_gms_internal_zzfi) {
        this.zzBD = com_google_android_gms_internal_zzfi;
    }

    private void zzc(JSONObject jSONObject) {
        try {
            this.zzBD.zza(jSONObject.getString("request_id"), jSONObject.getString("base_url"), jSONObject.getString("html"));
        } catch (Exception e) {
        }
    }

    public void handleMessage(Message msg) {
        try {
            Bundle data = msg.getData();
            if (data != null) {
                JSONObject jSONObject = new JSONObject(data.getString(PoiFragment.ARG_DATA));
                if ("fetch_html".equals(jSONObject.getString("message_name"))) {
                    zzc(jSONObject);
                }
            }
        } catch (Exception e) {
        }
    }
}
