package com.flurry.android.tumblr;

import android.os.Bundle;
import android.text.TextUtils;

public class PhotoPost extends Post {
    private String f547a;
    private String f548b;

    public PhotoPost(String str) {
        if (TextUtils.isEmpty(str)) {
            throw new IllegalArgumentException("Url to post cannot be null or empty");
        }
        this.f547a = str;
    }

    public void setCaption(String str) {
        this.f548b = str;
    }

    String m488a() {
        return this.f547a;
    }

    String m489b() {
        return this.f548b;
    }

    Bundle m490c() {
        Bundle bundle = new Bundle();
        bundle.putString("com.flurry.android.post_caption", m489b());
        bundle.putString("com.flurry.android.post_url", m488a());
        bundle.putString("com.flurry.android.post_ios_deeplinks", m485e());
        bundle.putString("com.flurry.android.post_android_deeplinks", m484d());
        bundle.putString("com.flurry.android.post_weblink", m486f());
        bundle.putBoolean("com.flurry.android.is_image_post", true);
        bundle.putInt("com.flurry.android.post_id", m487g());
        return bundle;
    }
}
