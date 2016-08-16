package com.facebook;

import android.content.SharedPreferences;
import com.facebook.internal.Validate;
import org.json.JSONException;
import org.json.JSONObject;

final class ProfileCache {
    private final SharedPreferences sharedPreferences;

    ProfileCache() {
        this.sharedPreferences = FacebookSdk.getApplicationContext().getSharedPreferences("com.facebook.AccessTokenManager.SharedPreferences", 0);
    }

    Profile load() {
        String jsonString = this.sharedPreferences.getString("com.facebook.ProfileManager.CachedProfile", null);
        if (jsonString != null) {
            try {
                return new Profile(new JSONObject(jsonString));
            } catch (JSONException e) {
            }
        }
        return null;
    }

    void save(Profile profile) {
        Validate.notNull(profile, "profile");
        JSONObject jsonObject = profile.toJSONObject();
        if (jsonObject != null) {
            this.sharedPreferences.edit().putString("com.facebook.ProfileManager.CachedProfile", jsonObject.toString()).apply();
        }
    }

    void clear() {
        this.sharedPreferences.edit().remove("com.facebook.ProfileManager.CachedProfile").apply();
    }
}
