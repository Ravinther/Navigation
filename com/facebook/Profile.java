package com.facebook;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.facebook.internal.Utility;
import com.facebook.internal.Utility.GraphMeRequestWithCacheCallback;
import com.facebook.internal.Validate;
import org.json.JSONException;
import org.json.JSONObject;

public final class Profile implements Parcelable {
    public static final Creator<Profile> CREATOR;
    private final String firstName;
    private final String id;
    private final String lastName;
    private final Uri linkUri;
    private final String middleName;
    private final String name;

    /* renamed from: com.facebook.Profile.1 */
    static class C03191 implements GraphMeRequestWithCacheCallback {
        C03191() {
        }

        public void onSuccess(JSONObject userInfo) {
            String id = userInfo.optString("id");
            if (id != null) {
                String link = userInfo.optString("link");
                Profile.setCurrentProfile(new Profile(id, userInfo.optString("first_name"), userInfo.optString("middle_name"), userInfo.optString("last_name"), userInfo.optString("name"), link != null ? Uri.parse(link) : null));
            }
        }

        public void onFailure(FacebookException error) {
        }
    }

    /* renamed from: com.facebook.Profile.2 */
    static class C03202 implements Creator {
        C03202() {
        }

        public Profile createFromParcel(Parcel source) {
            return new Profile(null);
        }

        public Profile[] newArray(int size) {
            return new Profile[size];
        }
    }

    public static Profile getCurrentProfile() {
        return ProfileManager.getInstance().getCurrentProfile();
    }

    public static void setCurrentProfile(Profile profile) {
        ProfileManager.getInstance().setCurrentProfile(profile);
    }

    public static void fetchProfileForCurrentAccessToken() {
        AccessToken accessToken = AccessToken.getCurrentAccessToken();
        if (accessToken == null) {
            setCurrentProfile(null);
        } else {
            Utility.getGraphMeRequestWithCacheAsync(accessToken.getToken(), new C03191());
        }
    }

    public Profile(String id, String firstName, String middleName, String lastName, String name, Uri linkUri) {
        Validate.notNullOrEmpty(id, "id");
        this.id = id;
        this.firstName = firstName;
        this.middleName = middleName;
        this.lastName = lastName;
        this.name = name;
        this.linkUri = linkUri;
    }

    public String getName() {
        return this.name;
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof Profile)) {
            return false;
        }
        Profile o = (Profile) other;
        if (this.id.equals(o.id) && this.firstName == null) {
            if (o.firstName != null) {
                return false;
            }
            return true;
        } else if (this.firstName.equals(o.firstName) && this.middleName == null) {
            if (o.middleName != null) {
                return false;
            }
            return true;
        } else if (this.middleName.equals(o.middleName) && this.lastName == null) {
            if (o.lastName != null) {
                return false;
            }
            return true;
        } else if (this.lastName.equals(o.lastName) && this.name == null) {
            if (o.name != null) {
                return false;
            }
            return true;
        } else if (!this.name.equals(o.name) || this.linkUri != null) {
            return this.linkUri.equals(o.linkUri);
        } else {
            if (o.linkUri != null) {
                return false;
            }
            return true;
        }
    }

    public int hashCode() {
        int result = this.id.hashCode() + 527;
        if (this.firstName != null) {
            result = (result * 31) + this.firstName.hashCode();
        }
        if (this.middleName != null) {
            result = (result * 31) + this.middleName.hashCode();
        }
        if (this.lastName != null) {
            result = (result * 31) + this.lastName.hashCode();
        }
        if (this.name != null) {
            result = (result * 31) + this.name.hashCode();
        }
        if (this.linkUri != null) {
            return (result * 31) + this.linkUri.hashCode();
        }
        return result;
    }

    JSONObject toJSONObject() {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("id", this.id);
            jsonObject.put("first_name", this.firstName);
            jsonObject.put("middle_name", this.middleName);
            jsonObject.put("last_name", this.lastName);
            jsonObject.put("name", this.name);
            if (this.linkUri == null) {
                return jsonObject;
            }
            jsonObject.put("link_uri", this.linkUri.toString());
            return jsonObject;
        } catch (JSONException e) {
            return null;
        }
    }

    Profile(JSONObject jsonObject) {
        Uri uri = null;
        this.id = jsonObject.optString("id", null);
        this.firstName = jsonObject.optString("first_name", null);
        this.middleName = jsonObject.optString("middle_name", null);
        this.lastName = jsonObject.optString("last_name", null);
        this.name = jsonObject.optString("name", null);
        String linkUriString = jsonObject.optString("link_uri", null);
        if (linkUriString != null) {
            uri = Uri.parse(linkUriString);
        }
        this.linkUri = uri;
    }

    private Profile(Parcel source) {
        this.id = source.readString();
        this.firstName = source.readString();
        this.middleName = source.readString();
        this.lastName = source.readString();
        this.name = source.readString();
        String linkUriString = source.readString();
        this.linkUri = linkUriString == null ? null : Uri.parse(linkUriString);
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.firstName);
        dest.writeString(this.middleName);
        dest.writeString(this.lastName);
        dest.writeString(this.name);
        dest.writeString(this.linkUri == null ? null : this.linkUri.toString());
    }

    static {
        CREATOR = new C03202();
    }
}
