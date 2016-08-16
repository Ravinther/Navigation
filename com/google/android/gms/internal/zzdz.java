package com.google.android.gms.internal;

import com.google.android.gms.ads.internal.zzp;
import com.sygic.aura.poi.fragment.PoiFragment;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

@zzgk
public final class zzdz {
    public final String zzyk;
    public final String zzyl;
    public final List<String> zzym;
    public final String zzyn;
    public final String zzyo;
    public final List<String> zzyp;
    public final List<String> zzyq;
    public final String zzyr;
    public final List<String> zzys;
    public final List<String> zzyt;

    public zzdz(JSONObject jSONObject) throws JSONException {
        String str = null;
        this.zzyl = jSONObject.getString("id");
        JSONArray jSONArray = jSONObject.getJSONArray("adapters");
        List arrayList = new ArrayList(jSONArray.length());
        for (int i = 0; i < jSONArray.length(); i++) {
            arrayList.add(jSONArray.getString(i));
        }
        this.zzym = Collections.unmodifiableList(arrayList);
        this.zzyn = jSONObject.optString("allocation_id", null);
        this.zzyp = zzp.zzbJ().zza(jSONObject, "clickurl");
        this.zzyq = zzp.zzbJ().zza(jSONObject, "imp_urls");
        this.zzys = zzp.zzbJ().zza(jSONObject, "video_start_urls");
        this.zzyt = zzp.zzbJ().zza(jSONObject, "video_complete_urls");
        JSONObject optJSONObject = jSONObject.optJSONObject("ad");
        this.zzyk = optJSONObject != null ? optJSONObject.toString() : null;
        JSONObject optJSONObject2 = jSONObject.optJSONObject(PoiFragment.ARG_DATA);
        this.zzyr = optJSONObject2 != null ? optJSONObject2.toString() : null;
        if (optJSONObject2 != null) {
            str = optJSONObject2.optString("class_name");
        }
        this.zzyo = str;
    }
}
