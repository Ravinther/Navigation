package com.google.android.gms.internal;

import com.google.android.gms.internal.zzag.zza;
import com.google.android.gms.internal.zzqp.zzb;
import com.google.android.gms.internal.zzqp.zzc;
import com.google.android.gms.internal.zzqp.zzd;
import com.google.android.gms.internal.zzqp.zze;
import com.google.android.gms.internal.zzqp.zzf;
import com.google.android.gms.internal.zzqp.zzg;
import com.google.android.gms.tagmanager.zzbg;
import com.google.android.gms.tagmanager.zzdf;
import com.sygic.aura.settings.fragments.SettingsFragment;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

final class zzqj {
    static zza zza(int i, JSONArray jSONArray, zza[] com_google_android_gms_internal_zzag_zzaArr, Set<Integer> set) throws zzg, JSONException {
        int i2 = 0;
        if (set.contains(Integer.valueOf(i))) {
            zzfi("Value cycle detected. Current value reference: " + i + "." + "  Previous value references: " + set + ".");
        }
        Object zza = zza(jSONArray, i, "values");
        if (com_google_android_gms_internal_zzag_zzaArr[i] != null) {
            return com_google_android_gms_internal_zzag_zzaArr[i];
        }
        set.add(Integer.valueOf(i));
        zza com_google_android_gms_internal_zzag_zza = new zza();
        JSONArray jSONArray2;
        if (zza instanceof JSONArray) {
            jSONArray2 = (JSONArray) zza;
            com_google_android_gms_internal_zzag_zza.type = 2;
            com_google_android_gms_internal_zzag_zza.zzje = true;
            com_google_android_gms_internal_zzag_zza.zziV = new zza[jSONArray2.length()];
            while (i2 < com_google_android_gms_internal_zzag_zza.zziV.length) {
                com_google_android_gms_internal_zzag_zza.zziV[i2] = zza(jSONArray2.getInt(i2), jSONArray, com_google_android_gms_internal_zzag_zzaArr, (Set) set);
                i2++;
            }
        } else if (zza instanceof JSONObject) {
            int i3;
            JSONObject jSONObject = (JSONObject) zza;
            JSONArray optJSONArray = jSONObject.optJSONArray("escaping");
            if (optJSONArray != null) {
                com_google_android_gms_internal_zzag_zza.zzjd = new int[optJSONArray.length()];
                for (i3 = 0; i3 < com_google_android_gms_internal_zzag_zza.zzjd.length; i3++) {
                    com_google_android_gms_internal_zzag_zza.zzjd[i3] = optJSONArray.getInt(i3);
                }
            }
            if (jSONObject.has("function_id")) {
                com_google_android_gms_internal_zzag_zza.type = 5;
                com_google_android_gms_internal_zzag_zza.zziZ = jSONObject.getString("function_id");
            } else if (jSONObject.has("macro_reference")) {
                com_google_android_gms_internal_zzag_zza.type = 4;
                com_google_android_gms_internal_zzag_zza.zzje = true;
                com_google_android_gms_internal_zzag_zza.zziY = zzdf.zzg(zza(jSONObject.getInt("macro_reference"), jSONArray, com_google_android_gms_internal_zzag_zzaArr, (Set) set));
            } else if (jSONObject.has("template_token")) {
                com_google_android_gms_internal_zzag_zza.type = 7;
                com_google_android_gms_internal_zzag_zza.zzje = true;
                jSONArray2 = jSONObject.getJSONArray("template_token");
                com_google_android_gms_internal_zzag_zza.zzjc = new zza[jSONArray2.length()];
                while (i2 < com_google_android_gms_internal_zzag_zza.zzjc.length) {
                    com_google_android_gms_internal_zzag_zza.zzjc[i2] = zza(jSONArray2.getInt(i2), jSONArray, com_google_android_gms_internal_zzag_zzaArr, (Set) set);
                    i2++;
                }
            } else {
                com_google_android_gms_internal_zzag_zza.type = 3;
                com_google_android_gms_internal_zzag_zza.zzje = true;
                i3 = jSONObject.length();
                com_google_android_gms_internal_zzag_zza.zziW = new zza[i3];
                com_google_android_gms_internal_zzag_zza.zziX = new zza[i3];
                Iterator keys = jSONObject.keys();
                i3 = 0;
                while (keys.hasNext()) {
                    String str = (String) keys.next();
                    com_google_android_gms_internal_zzag_zza.zziW[i3] = zza(new Integer(str).intValue(), jSONArray, com_google_android_gms_internal_zzag_zzaArr, (Set) set);
                    com_google_android_gms_internal_zzag_zza.zziX[i3] = zza(jSONObject.getInt(str), jSONArray, com_google_android_gms_internal_zzag_zzaArr, (Set) set);
                    i3++;
                }
            }
        } else if (zza instanceof String) {
            com_google_android_gms_internal_zzag_zza.zziU = (String) zza;
            com_google_android_gms_internal_zzag_zza.type = 1;
        } else if (zza instanceof Boolean) {
            com_google_android_gms_internal_zzag_zza.zzjb = ((Boolean) zza).booleanValue();
            com_google_android_gms_internal_zzag_zza.type = 8;
        } else if (zza instanceof Integer) {
            com_google_android_gms_internal_zzag_zza.zzja = (long) ((Integer) zza).intValue();
            com_google_android_gms_internal_zzag_zza.type = 6;
        } else {
            zzfi("Invalid value type: " + zza);
        }
        com_google_android_gms_internal_zzag_zzaArr[i] = com_google_android_gms_internal_zzag_zza;
        set.remove(Integer.valueOf(i));
        return com_google_android_gms_internal_zzag_zza;
    }

    static zzqp.zza zza(JSONObject jSONObject, JSONArray jSONArray, JSONArray jSONArray2, zza[] com_google_android_gms_internal_zzag_zzaArr, int i) throws zzg, JSONException {
        zzb zzBC = zzqp.zza.zzBC();
        JSONArray jSONArray3 = jSONObject.getJSONArray("property");
        for (int i2 = 0; i2 < jSONArray3.length(); i2++) {
            JSONObject jSONObject2 = (JSONObject) zza(jSONArray, jSONArray3.getInt(i2), "properties");
            String str = (String) zza(jSONArray2, jSONObject2.getInt(SettingsFragment.ARG_KEY), SettingsFragment.ARG_KEY);
            zza com_google_android_gms_internal_zzag_zza = (zza) zza((Object[]) com_google_android_gms_internal_zzag_zzaArr, jSONObject2.getInt("value"), "value");
            if (zzae.PUSH_AFTER_EVALUATE.toString().equals(str)) {
                zzBC.zzq(com_google_android_gms_internal_zzag_zza);
            } else {
                zzBC.zzb(str, com_google_android_gms_internal_zzag_zza);
            }
        }
        return zzBC.zzBE();
    }

    static zze zza(JSONObject jSONObject, List<zzqp.zza> list, List<zzqp.zza> list2, List<zzqp.zza> list3, zza[] com_google_android_gms_internal_zzag_zzaArr) throws JSONException {
        int i;
        int i2;
        zzf zzBJ = zze.zzBJ();
        JSONArray optJSONArray = jSONObject.optJSONArray("positive_predicate");
        JSONArray optJSONArray2 = jSONObject.optJSONArray("negative_predicate");
        JSONArray optJSONArray3 = jSONObject.optJSONArray("add_tag");
        JSONArray optJSONArray4 = jSONObject.optJSONArray("remove_tag");
        JSONArray optJSONArray5 = jSONObject.optJSONArray("add_tag_rule_name");
        JSONArray optJSONArray6 = jSONObject.optJSONArray("remove_tag_rule_name");
        JSONArray optJSONArray7 = jSONObject.optJSONArray("add_macro");
        JSONArray optJSONArray8 = jSONObject.optJSONArray("remove_macro");
        JSONArray optJSONArray9 = jSONObject.optJSONArray("add_macro_rule_name");
        JSONArray optJSONArray10 = jSONObject.optJSONArray("remove_macro_rule_name");
        if (optJSONArray != null) {
            for (i = 0; i < optJSONArray.length(); i++) {
                zzBJ.zzd((zzqp.zza) list3.get(optJSONArray.getInt(i)));
            }
        }
        if (optJSONArray2 != null) {
            for (i = 0; i < optJSONArray2.length(); i++) {
                zzBJ.zze((zzqp.zza) list3.get(optJSONArray2.getInt(i)));
            }
        }
        if (optJSONArray3 != null) {
            for (i = 0; i < optJSONArray3.length(); i++) {
                zzBJ.zzf((zzqp.zza) list.get(optJSONArray3.getInt(i)));
            }
        }
        if (optJSONArray4 != null) {
            for (i = 0; i < optJSONArray4.length(); i++) {
                zzBJ.zzg((zzqp.zza) list.get(optJSONArray4.getInt(i)));
            }
        }
        if (optJSONArray5 != null) {
            for (i2 = 0; i2 < optJSONArray5.length(); i2++) {
                zzBJ.zzfl(com_google_android_gms_internal_zzag_zzaArr[optJSONArray5.getInt(i2)].zziU);
            }
        }
        if (optJSONArray6 != null) {
            for (i2 = 0; i2 < optJSONArray6.length(); i2++) {
                zzBJ.zzfm(com_google_android_gms_internal_zzag_zzaArr[optJSONArray6.getInt(i2)].zziU);
            }
        }
        if (optJSONArray7 != null) {
            for (i = 0; i < optJSONArray7.length(); i++) {
                zzBJ.zzh((zzqp.zza) list2.get(optJSONArray7.getInt(i)));
            }
        }
        if (optJSONArray8 != null) {
            for (i = 0; i < optJSONArray8.length(); i++) {
                zzBJ.zzi((zzqp.zza) list2.get(optJSONArray8.getInt(i)));
            }
        }
        if (optJSONArray9 != null) {
            for (i2 = 0; i2 < optJSONArray9.length(); i2++) {
                zzBJ.zzfn(com_google_android_gms_internal_zzag_zzaArr[optJSONArray9.getInt(i2)].zziU);
            }
        }
        if (optJSONArray10 != null) {
            for (i2 = 0; i2 < optJSONArray10.length(); i2++) {
                zzBJ.zzfo(com_google_android_gms_internal_zzag_zzaArr[optJSONArray10.getInt(i2)].zziU);
            }
        }
        return zzBJ.zzBU();
    }

    private static <T> T zza(JSONArray jSONArray, int i, String str) throws zzg {
        if (i >= 0 && i < jSONArray.length()) {
            try {
                return jSONArray.get(i);
            } catch (JSONException e) {
            }
        }
        zzfi("Index out of bounds detected: " + i + " in " + str);
        return null;
    }

    private static <T> T zza(T[] tArr, int i, String str) throws zzg {
        if (i < 0 || i >= tArr.length) {
            zzfi("Index out of bounds detected: " + i + " in " + str);
        }
        return tArr[i];
    }

    static List<zzqp.zza> zza(JSONArray jSONArray, JSONArray jSONArray2, JSONArray jSONArray3, zza[] com_google_android_gms_internal_zzag_zzaArr) throws JSONException, zzg {
        List<zzqp.zza> arrayList = new ArrayList();
        for (int i = 0; i < jSONArray.length(); i++) {
            arrayList.add(zza(jSONArray.getJSONObject(i), jSONArray2, jSONArray3, com_google_android_gms_internal_zzag_zzaArr, i));
        }
        return arrayList;
    }

    static zzc zzeN(String str) throws JSONException, zzg {
        JSONObject jSONObject = new JSONObject(str);
        Object obj = jSONObject.get("resource");
        if (obj instanceof JSONObject) {
            JSONObject jSONObject2 = (JSONObject) obj;
            zzd zzBF = zzc.zzBF();
            zza[] zzj = zzj(jSONObject2);
            JSONArray jSONArray = jSONObject2.getJSONArray("properties");
            JSONArray jSONArray2 = jSONObject2.getJSONArray(SettingsFragment.ARG_KEY);
            List zza = zza(jSONObject2.getJSONArray("tags"), jSONArray, jSONArray2, zzj);
            List zza2 = zza(jSONObject2.getJSONArray("predicates"), jSONArray, jSONArray2, zzj);
            List<zzqp.zza> zza3 = zza(jSONObject2.getJSONArray("macros"), jSONArray, jSONArray2, zzj);
            for (zzqp.zza zzc : zza3) {
                zzBF.zzc(zzc);
            }
            jSONArray = jSONObject2.getJSONArray("rules");
            for (int i = 0; i < jSONArray.length(); i++) {
                zzBF.zzb(zza(jSONArray.getJSONObject(i), zza, (List) zza3, zza2, zzj));
            }
            zzBF.zzfk("1");
            zzBF.zzjm(1);
            if (jSONObject.optJSONArray("runtime") != null) {
            }
            return zzBF.zzBI();
        }
        throw new zzg("Resource map not found");
    }

    private static void zzfi(String str) throws zzg {
        zzbg.m1447e(str);
        throw new zzg(str);
    }

    static zza[] zzj(JSONObject jSONObject) throws JSONException, zzg {
        Object opt = jSONObject.opt("values");
        if (opt instanceof JSONArray) {
            JSONArray jSONArray = (JSONArray) opt;
            zza[] com_google_android_gms_internal_zzag_zzaArr = new zza[jSONArray.length()];
            for (int i = 0; i < com_google_android_gms_internal_zzag_zzaArr.length; i++) {
                zza(i, jSONArray, com_google_android_gms_internal_zzag_zzaArr, new HashSet(0));
            }
            return com_google_android_gms_internal_zzag_zzaArr;
        }
        throw new zzg("Missing Values list");
    }
}
