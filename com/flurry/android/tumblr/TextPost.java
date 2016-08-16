package com.flurry.android.tumblr;

import android.os.Bundle;
import android.text.TextUtils;

public class TextPost extends Post {
    private String f557a;
    private String f558b;

    public TextPost(String str) {
        if (TextUtils.isEmpty(str)) {
            throw new IllegalArgumentException("Body to post cannot be null or empty");
        }
        this.f558b = str;
    }

    public void setTitle(String str) {
        this.f557a = str;
    }

    String m495a() {
        return this.f557a;
    }

    String m496b() {
        return this.f558b;
    }

    Bundle m497c() {
        Bundle bundle = new Bundle();
        bundle.putString("com.flurry.android.post_title", m495a());
        bundle.putString("com.flurry.android.post_body", m496b());
        bundle.putString("com.flurry.android.post_ios_deeplinks", m485e());
        bundle.putString("com.flurry.android.post_android_deeplinks", m484d());
        bundle.putString("com.flurry.android.post_weblink", m486f());
        bundle.putBoolean("com.flurry.android.is_text_post", true);
        bundle.putInt("com.flurry.android.post_id", m487g());
        return bundle;
    }
}
