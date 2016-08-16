package com.facebook;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import com.facebook.internal.Logger;
import com.facebook.internal.Utility;
import com.facebook.internal.Validate;
import java.util.ArrayList;
import java.util.Date;
import org.json.JSONException;
import org.json.JSONObject;

final class LegacyTokenHelper {
    private static final String TAG;
    private SharedPreferences cache;
    private String cacheKey;

    static {
        TAG = LegacyTokenHelper.class.getSimpleName();
    }

    public LegacyTokenHelper(Context context) {
        this(context, null);
    }

    public LegacyTokenHelper(Context context, String cacheKey) {
        Validate.notNull(context, "context");
        if (Utility.isNullOrEmpty(cacheKey)) {
            cacheKey = "com.facebook.SharedPreferencesTokenCachingStrategy.DEFAULT_KEY";
        }
        this.cacheKey = cacheKey;
        Context applicationContext = context.getApplicationContext();
        if (applicationContext != null) {
            context = applicationContext;
        }
        this.cache = context.getSharedPreferences(this.cacheKey, 0);
    }

    public Bundle load() {
        Bundle settings = new Bundle();
        for (String key : this.cache.getAll().keySet()) {
            try {
                deserializeKey(key, settings);
            } catch (JSONException e) {
                Logger.log(LoggingBehavior.CACHE, 5, TAG, "Error reading cached value for key: '" + key + "' -- " + e);
                return null;
            }
        }
        return settings;
    }

    public void clear() {
        this.cache.edit().clear().apply();
    }

    public static boolean hasTokenInformation(Bundle bundle) {
        if (bundle == null) {
            return false;
        }
        String token = bundle.getString("com.facebook.TokenCachingStrategy.Token");
        if (token == null || token.length() == 0 || bundle.getLong("com.facebook.TokenCachingStrategy.ExpirationDate", 0) == 0) {
            return false;
        }
        return true;
    }

    public static String getToken(Bundle bundle) {
        Validate.notNull(bundle, "bundle");
        return bundle.getString("com.facebook.TokenCachingStrategy.Token");
    }

    public static AccessTokenSource getSource(Bundle bundle) {
        Validate.notNull(bundle, "bundle");
        if (bundle.containsKey("com.facebook.TokenCachingStrategy.AccessTokenSource")) {
            return (AccessTokenSource) bundle.getSerializable("com.facebook.TokenCachingStrategy.AccessTokenSource");
        }
        return bundle.getBoolean("com.facebook.TokenCachingStrategy.IsSSO") ? AccessTokenSource.FACEBOOK_APPLICATION_WEB : AccessTokenSource.WEB_VIEW;
    }

    public static String getApplicationId(Bundle bundle) {
        Validate.notNull(bundle, "bundle");
        return bundle.getString("com.facebook.TokenCachingStrategy.ApplicationId");
    }

    static Date getDate(Bundle bundle, String key) {
        if (bundle == null) {
            return null;
        }
        long n = bundle.getLong(key, Long.MIN_VALUE);
        if (n != Long.MIN_VALUE) {
            return new Date(n);
        }
        return null;
    }

    private void deserializeKey(String key, Bundle bundle) throws JSONException {
        JSONObject json = new JSONObject(this.cache.getString(key, "{}"));
        String valueType = json.getString("valueType");
        if (valueType.equals("bool")) {
            bundle.putBoolean(key, json.getBoolean("value"));
        } else if (valueType.equals("bool[]")) {
            jsonArray = json.getJSONArray("value");
            boolean[] array = new boolean[jsonArray.length()];
            i = 0;
            while (true) {
                r17 = array.length;
                if (i < r0) {
                    array[i] = jsonArray.getBoolean(i);
                    i++;
                } else {
                    bundle.putBooleanArray(key, array);
                    return;
                }
            }
        } else if (valueType.equals("byte")) {
            bundle.putByte(key, (byte) json.getInt("value"));
        } else if (valueType.equals("byte[]")) {
            jsonArray = json.getJSONArray("value");
            byte[] array2 = new byte[jsonArray.length()];
            i = 0;
            while (true) {
                r17 = array2.length;
                if (i < r0) {
                    array2[i] = (byte) jsonArray.getInt(i);
                    i++;
                } else {
                    bundle.putByteArray(key, array2);
                    return;
                }
            }
        } else if (valueType.equals("short")) {
            bundle.putShort(key, (short) json.getInt("value"));
        } else if (valueType.equals("short[]")) {
            jsonArray = json.getJSONArray("value");
            short[] array3 = new short[jsonArray.length()];
            i = 0;
            while (true) {
                r17 = array3.length;
                if (i < r0) {
                    array3[i] = (short) jsonArray.getInt(i);
                    i++;
                } else {
                    bundle.putShortArray(key, array3);
                    return;
                }
            }
        } else if (valueType.equals("int")) {
            bundle.putInt(key, json.getInt("value"));
        } else if (valueType.equals("int[]")) {
            jsonArray = json.getJSONArray("value");
            int[] array4 = new int[jsonArray.length()];
            i = 0;
            while (true) {
                r17 = array4.length;
                if (i < r0) {
                    array4[i] = jsonArray.getInt(i);
                    i++;
                } else {
                    bundle.putIntArray(key, array4);
                    return;
                }
            }
        } else if (valueType.equals("long")) {
            bundle.putLong(key, json.getLong("value"));
        } else if (valueType.equals("long[]")) {
            jsonArray = json.getJSONArray("value");
            long[] array5 = new long[jsonArray.length()];
            i = 0;
            while (true) {
                r17 = array5.length;
                if (i < r0) {
                    array5[i] = jsonArray.getLong(i);
                    i++;
                } else {
                    bundle.putLongArray(key, array5);
                    return;
                }
            }
        } else if (valueType.equals("float")) {
            bundle.putFloat(key, (float) json.getDouble("value"));
        } else if (valueType.equals("float[]")) {
            jsonArray = json.getJSONArray("value");
            float[] array6 = new float[jsonArray.length()];
            i = 0;
            while (true) {
                r17 = array6.length;
                if (i < r0) {
                    array6[i] = (float) jsonArray.getDouble(i);
                    i++;
                } else {
                    bundle.putFloatArray(key, array6);
                    return;
                }
            }
        } else if (valueType.equals("double")) {
            bundle.putDouble(key, json.getDouble("value"));
        } else if (valueType.equals("double[]")) {
            jsonArray = json.getJSONArray("value");
            double[] array7 = new double[jsonArray.length()];
            i = 0;
            while (true) {
                r17 = array7.length;
                if (i < r0) {
                    array7[i] = jsonArray.getDouble(i);
                    i++;
                } else {
                    bundle.putDoubleArray(key, array7);
                    return;
                }
            }
        } else if (valueType.equals("char")) {
            charString = json.getString("value");
            if (charString != null && charString.length() == 1) {
                bundle.putChar(key, charString.charAt(0));
            }
        } else if (valueType.equals("char[]")) {
            jsonArray = json.getJSONArray("value");
            char[] array8 = new char[jsonArray.length()];
            i = 0;
            while (true) {
                r17 = array8.length;
                if (i < r0) {
                    charString = jsonArray.getString(i);
                    if (charString != null && charString.length() == 1) {
                        array8[i] = charString.charAt(0);
                    }
                    i++;
                } else {
                    bundle.putCharArray(key, array8);
                    return;
                }
            }
        } else if (valueType.equals("string")) {
            bundle.putString(key, json.getString("value"));
        } else if (valueType.equals("stringList")) {
            jsonArray = json.getJSONArray("value");
            int numStrings = jsonArray.length();
            ArrayList<String> stringList = new ArrayList(numStrings);
            for (i = 0; i < numStrings; i++) {
                Object jsonStringValue = jsonArray.get(i);
                if (jsonStringValue == JSONObject.NULL) {
                    jsonStringValue = null;
                } else {
                    String jsonStringValue2 = (String) jsonStringValue;
                }
                stringList.add(i, jsonStringValue);
            }
            bundle.putStringArrayList(key, stringList);
        } else if (valueType.equals("enum")) {
            try {
                String string = json.getString("value");
                bundle.putSerializable(key, Enum.valueOf(Class.forName(json.getString("enumType")), r17));
            } catch (ClassNotFoundException e) {
            } catch (IllegalArgumentException e2) {
            }
        }
    }
}
