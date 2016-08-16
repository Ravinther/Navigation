package com.facebook.internal;

import android.os.Bundle;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class BundleJSONConverter {
    private static final Map<Class<?>, Setter> SETTERS;

    public interface Setter {
        void setOnBundle(Bundle bundle, String str, Object obj) throws JSONException;
    }

    /* renamed from: com.facebook.internal.BundleJSONConverter.1 */
    static class C03341 implements Setter {
        C03341() {
        }

        public void setOnBundle(Bundle bundle, String key, Object value) throws JSONException {
            bundle.putBoolean(key, ((Boolean) value).booleanValue());
        }
    }

    /* renamed from: com.facebook.internal.BundleJSONConverter.2 */
    static class C03352 implements Setter {
        C03352() {
        }

        public void setOnBundle(Bundle bundle, String key, Object value) throws JSONException {
            bundle.putInt(key, ((Integer) value).intValue());
        }
    }

    /* renamed from: com.facebook.internal.BundleJSONConverter.3 */
    static class C03363 implements Setter {
        C03363() {
        }

        public void setOnBundle(Bundle bundle, String key, Object value) throws JSONException {
            bundle.putLong(key, ((Long) value).longValue());
        }
    }

    /* renamed from: com.facebook.internal.BundleJSONConverter.4 */
    static class C03374 implements Setter {
        C03374() {
        }

        public void setOnBundle(Bundle bundle, String key, Object value) throws JSONException {
            bundle.putDouble(key, ((Double) value).doubleValue());
        }
    }

    /* renamed from: com.facebook.internal.BundleJSONConverter.5 */
    static class C03385 implements Setter {
        C03385() {
        }

        public void setOnBundle(Bundle bundle, String key, Object value) throws JSONException {
            bundle.putString(key, (String) value);
        }
    }

    /* renamed from: com.facebook.internal.BundleJSONConverter.6 */
    static class C03396 implements Setter {
        C03396() {
        }

        public void setOnBundle(Bundle bundle, String key, Object value) throws JSONException {
            throw new IllegalArgumentException("Unexpected type from JSON");
        }
    }

    /* renamed from: com.facebook.internal.BundleJSONConverter.7 */
    static class C03407 implements Setter {
        C03407() {
        }

        public void setOnBundle(Bundle bundle, String key, Object value) throws JSONException {
            JSONArray jsonArray = (JSONArray) value;
            ArrayList<String> stringArrayList = new ArrayList();
            if (jsonArray.length() == 0) {
                bundle.putStringArrayList(key, stringArrayList);
                return;
            }
            int i = 0;
            while (i < jsonArray.length()) {
                Object current = jsonArray.get(i);
                if (current instanceof String) {
                    stringArrayList.add((String) current);
                    i++;
                } else {
                    throw new IllegalArgumentException("Unexpected type in an array: " + current.getClass());
                }
            }
            bundle.putStringArrayList(key, stringArrayList);
        }
    }

    static {
        SETTERS = new HashMap();
        SETTERS.put(Boolean.class, new C03341());
        SETTERS.put(Integer.class, new C03352());
        SETTERS.put(Long.class, new C03363());
        SETTERS.put(Double.class, new C03374());
        SETTERS.put(String.class, new C03385());
        SETTERS.put(String[].class, new C03396());
        SETTERS.put(JSONArray.class, new C03407());
    }

    public static Bundle convertToBundle(JSONObject jsonObject) throws JSONException {
        Bundle bundle = new Bundle();
        Iterator<String> jsonIterator = jsonObject.keys();
        while (jsonIterator.hasNext()) {
            String key = (String) jsonIterator.next();
            Object value = jsonObject.get(key);
            if (!(value == null || value == JSONObject.NULL)) {
                if (value instanceof JSONObject) {
                    bundle.putBundle(key, convertToBundle((JSONObject) value));
                } else {
                    Setter setter = (Setter) SETTERS.get(value.getClass());
                    if (setter == null) {
                        throw new IllegalArgumentException("Unsupported type: " + value.getClass());
                    }
                    setter.setOnBundle(bundle, key, value);
                }
            }
        }
        return bundle;
    }
}
